package saleson.common.module.smarteditor;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.file.domain.SmartEditorUpload;
import com.onlinepowers.framework.file.domain.UploadFile;
import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.sequence.support.SequenceKey;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/smarteditor")
@RequestProperty(layout="blank")
public class SmartEditorController {
	
	private static final Logger log = LoggerFactory.getLogger(SmartEditorController.class);

	@Autowired 
	SequenceService sequenceService;
	
	@Autowired 
	FileService fileService;
	
	/**
	 * 이미지 업로드 
	 * @return
	 */
	@GetMapping("upload-image")
	public String uploadImage(Model model) {
		
		model.addAttribute("token",StringUtils.getToken());
		
		return ViewUtils.view();
	}


	@RequestProperty(layout="blank")
	@PostMapping("upload-image")
	public String uploadImageProcess(RequestContext requestContext,
									 SmartEditorUpload smartEditorUpload,
									 @RequestParam(value="file[]", required=false) MultipartFile[] multipartFiles,
									 Model model) {

		StringBuilder sb = new StringBuilder();

		if (multipartFiles == null || multipartFiles.length == 0) {
			model.addAttribute("imageContent", sb.toString());
			return ViewUtils.getView("/smarteditor/upload-image-result");
		}

		AtomicInteger index = new AtomicInteger();
		Arrays.asList(multipartFiles).stream()
				.filter(f -> isImageFile(f))
				.forEach(f -> {
					int tempFileId = sequenceService.getId(SequenceKey.FILE_TEMP);
					int i = index.getAndIncrement();

					UploadFile uploadFile = new UploadFile();
					uploadFile.setFileId(tempFileId);
					uploadFile.setRefCode(smartEditorUpload.getToken() + "" + i);
					uploadFile.setUseEncrypt("false");
					uploadFile.setOrdering(i);

					fileService.uploadTempEditor(f, uploadFile);

					UploadFile savedUploadFile = fileService.getByTempId(tempFileId);
					String src = "/upload/editor/" + savedUploadFile.getCreatedDate().substring(0, 8) + "/"
							+ savedUploadFile.getRefCode() + "/" + savedUploadFile.getFileName();

					sb.append("<p>");
					sb.append("<img src=\"" + src + "\" alt=\"\" />");
					sb.append("</p>");
				});

		String imageContent = sb.toString();
		model.addAttribute("imageContent", imageContent);
		return ViewUtils.getView("/smarteditor/upload-image-result");
	}
	

	/**
	 * CTP 업로드
	 * @return
	 */
	@GetMapping("upload-ctp")
	public String uploadCtp() {
		return ViewUtils.view();
	}
	
	
	@RequestProperty(layout="base")
	@PostMapping("/upload-ctp")
	public String uploadCtpProcess(RequestContext requestContext, 
			SmartEditorUpload smartEditorUpload, 
			@RequestParam(value="file[]", required=false) MultipartFile[] multipartFiles,
			Model model) { 

		String ctpContent = "";
		
		/*
		boolean isPossibleFileExtension = false;
		isPossibleFileExtension = FileUtils.isPossibleUploadFile(multipartFiles, UPLOAD_POSIBLE_EXTENTIONS[Integer.parseInt(fileParam.getUploadType())]);
		
		if (!isPossibleFileExtension) {
			return ViewUtils.view("업로드 가능한 파일은 (" + UPLOAD_POSIBLE_EXTENTIONS[Integer.parseInt(fileParam.getUploadType())] + ") 입니다.");
		}
		*/
		String[] uploadPosibleExtensions = new String[] {"html", "ctp", "css", "jpg", "jpeg", "png", "gif"};
		
		// 1. 파일 업로드
		int fileCount = 0;
		

		String uploadSizeLimit = "5";
		int ctpFileCount = 0;
		String ctpFilename = "";
		for (MultipartFile multipartFile : multipartFiles) {
			if (multipartFile.getSize() > 0) {
				Long maxUploadSize = Long.parseLong(uploadSizeLimit) * 1000 * 1000;
				
				String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename());
				boolean isMatchFileExtension = false;
				
				for (String ext : uploadPosibleExtensions) {
					if (fileExtension.equals(ext)) {
						isMatchFileExtension = true;
						break;
					}
				}
				
				if (!isMatchFileExtension) {
					model.addAttribute("editorErrorCode", "ERROR_01");
					model.addAttribute("editorErrorMessage", fileExtension);
					return ViewUtils.getView("/smarteditor/upload-ctp-result");
				}
				
				if (multipartFile.getSize() > maxUploadSize) {
					model.addAttribute("editorErrorCode", "ERROR_02");
					model.addAttribute("editorErrorMessage", uploadSizeLimit);
					return ViewUtils.getView("/smarteditor/upload-ctp-result");
				}
				
				if (fileExtension.equals("ctp") || fileExtension.equals("html") ) {
					ctpFilename = FileUtils.getFileNameWithoutExtension(multipartFile.getOriginalFilename());
					ctpFileCount++;
				}
			}
		}
		
		if (ctpFileCount == 0) {
			model.addAttribute("editorErrorCode", "ERROR_03");
			model.addAttribute("editorErrorMessage", "");
			return ViewUtils.getView("/smarteditor/upload-ctp-result");
		}
		
		
		// 등록 처리.
		List<HashMap<String, String>> uploadImages = new ArrayList<>();
		String uniqueFolderName = DateUtils.getToday(Const.DATETIME_FORMAT);
		
		
		for (MultipartFile multipartFile : multipartFiles) {
			if (multipartFile.getSize() > 0) {
				
				String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename());
				
				if (fileExtension.equals("ctp") || fileExtension.equals("html")) {
					
					String row = "";
					StringBuffer sb = new StringBuffer();
					try {
						BufferedReader bf = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
				   
						while ((row = bf.readLine()) != null) {
							sb.append(row);
							sb.append(StringUtils.LINE_SEPARATOR);
						}
					} catch (IOException e) {
						log.warn("SmartEditor {}", e.getMessage(), e);
					}
					ctpContent = sb.toString();
					
					log.debug("CTP : {}", row);
					
					
				} 
				
				// 파일처리..
					
				// 1. 업로드 경로설정
				String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "ctp" + File.separator + ctpFilename + "_" + uniqueFolderName;
				fileService.makeUploadPath(uploadPath);
		    	
				// 2. 파일명 
				String newFileName = FileUtils.getNewFileName(uploadPath, multipartFile.getOriginalFilename());

				// 3. 저장될 파일 
				File saveFile = new File(uploadPath + File.separator + newFileName);
						
		        try {
					FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
					
					if (!(fileExtension.equals("ctp") || fileExtension.equals("html"))) {
						HashMap<String, String> fileInfo = new HashMap<>();
						
						fileInfo.put("originalFilename",  multipartFile.getOriginalFilename());
						fileInfo.put("newFilename",  newFileName);
						
						uploadImages.add(fileInfo);
					}
					
				} catch (IOException e) {
					log.warn("SmartEditor {}", e.getMessage(), e);
				}
			}
		}
		
		// 컨텐츠에 이미지 변환 
		for (HashMap<String, String> fileInfo : uploadImages) {
			ctpContent = ctpContent.replaceAll("./" + fileInfo.get("originalFilename"), "/upload/ctp/" + ctpFilename + "_" + uniqueFolderName + "/" + fileInfo.get("newFilename"));
		}
	
		// body 안의 내용만 가져온다. <body> ~ </body>
		Pattern bodyPattern = Pattern.compile("<body>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher bodyMatcher = bodyPattern.matcher(ctpContent);
		while(bodyMatcher.find()) {
			ctpContent = bodyMatcher.group(1);
		}
		
		
		model.addAttribute("ctpContent", ctpContent);
		return ViewUtils.getView("/smarteditor/upload-ctp-result");

	}
	
	@GetMapping("upload-movie")
	public String uploadMovie() {
		return ViewUtils.view();
	}


	private boolean isImageFile(MultipartFile multipartFile) {
		String[] uploadPosibleExtensions = new String[] {"jpg", "jpeg", "png", "gif"};
		String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename());

		for (String ext : uploadPosibleExtensions) {
			if (fileExtension.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}
}

