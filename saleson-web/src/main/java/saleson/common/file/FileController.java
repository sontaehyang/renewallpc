package saleson.common.file;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.enumeration.JavaScript;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.file.domain.UploadFile;
import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.sequence.support.SequenceKey;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.file.domain.FileParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestProperty(layout="base", template="front")
@RequestMapping({"/file", "/mobile/file"})
public class FileController {
	private static Logger log = LoggerFactory.getLogger(FileController.class);
	
	private static final String[] UPLOAD_TITLES = new String[] {
		"사진첨부", "파일첨부", "내용이미지첨부"
	};
	
	private static final String[] UPLOAD_POSIBLE_EXTENTIONS = new String[] {
		"*.jpg;*.jpeg;*.gif;*.bmp;*.png",	// 사진첨부 uploadType = "0"
		"*.doc;*.docx;*.xls;*xlsx;*.ppt;*.pptx;*.pdf;*.tif;*.tiff;*.hwp;*.zip",
		"*.jpg;*.jpeg;*.gif;*.bmp;*.png;*.doc;*.docx;*.xls;*xlsx;*.ppt;*.pptx;*.pdf;*.tif;*.tiff;*.hwp;*.zip"
	};
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private FileService fileService;
	
	private void checkPermission(FileParam fileParam) {
		
	}
	
	@GetMapping("upload")
	@RequestProperty(title="파일첨부")
	public String upload(@ModelAttribute FileParam fileParam, Model model,
			RequestContext requestContext
			) {
		// 접근 권한 체크 
		checkPermission(fileParam);
		
		if (fileParam == null || fileParam.getMaxUploadSize() == null) {
			return ViewUtils.view("필수 정보가 넘어오지 않았습니다.", JavaScript.CLOSE);
		}
		
		
		if (fileParam.getMaxUploadSize().equals("")) {
			fileParam.setMaxUploadSize("3");
		}

		int uploadType = Integer.parseInt(fileParam.getUploadType());

		if (uploadType < 0 || uploadType >= UPLOAD_TITLES.length) {
			throw new PageNotFoundException();
		}
		String title = UPLOAD_TITLES[uploadType];
		model.addAttribute("title", title);
		
		requestContext.getRequestPropertyData().setTitle(title);
		
		if (isMobile()) {
			requestContext.getRequestPropertyData().setTemplate("mobile");
		}
		return ViewUtils.view();
	}
	
	
	@PostMapping("upload")
	public String uploadAction(RequestContext requestContext, 
			@ModelAttribute FileParam fileParam,
			@RequestParam(value="file[]", required=false) MultipartFile[] multipartFiles,
			@RequestParam(value="useEncrypt", defaultValue="0") String useEncrypt,
			Model model) {
		
		boolean isPossibleFileExtension = false;
		isPossibleFileExtension = FileUtils.isPossibleUploadFile(multipartFiles, UPLOAD_POSIBLE_EXTENTIONS[Integer.parseInt(fileParam.getUploadType())]);
		
		if (!isPossibleFileExtension) {
			return ViewUtils.view("업로드 가능한 파일은 (" + UPLOAD_POSIBLE_EXTENTIONS[Integer.parseInt(fileParam.getUploadType())] + ") 입니다.");
		}
		
		// 1. 파일 업로드
		int fileCount = 0;

		List<UploadFile> fileList = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			if (multipartFile.getSize() > 0) {
				Long maxUploadSize = Long.parseLong(fileParam.getMaxUploadSize()) * 1000 * 1000;
				
				if (multipartFile.getSize() > maxUploadSize) {
					return ViewUtils.view("업로드가 가능한 파일 용량은 " + fileParam.getMaxUploadSize() + "MB 입니다.");
				}
				
				int fileTempId = sequenceService.getId(SequenceKey.FILE_TEMP);
				
				UploadFile uploadFile = new UploadFile();
				uploadFile.setFileId(fileTempId);
				uploadFile.setRefCode(requestContext.getRequestToken());
				uploadFile.setUploadType(fileParam.getUploadType());
				uploadFile.setUseEncrypt(useEncrypt);
				uploadFile.setFileName(multipartFile.getOriginalFilename());
				
				fileCount++;
				
				uploadFile.setOrdering(fileCount);
				fileService.uploadTempSWF(multipartFile, uploadFile);
				
				fileList.add(uploadFile);
			}
		}
		
		model.addAttribute("fileList", fileList);
		
		if (isMobile()) {
			requestContext.getRequestPropertyData().setTemplate("mobile");
		}
		
		return ViewUtils.getView("/file/uploadComplete");
	}
	
	private boolean isMobile() {
		RequestContext requestContext = RequestContextUtils.getRequestContext();
		return requestContext.getRequestUri().indexOf("/mobile") > -1 ? true : false;
	}
}
