package saleson.shop.storeinquiry;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import saleson.shop.storeinquiry.domain.StoreInquiry;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/m/store-inquiry")
@RequestProperty(title="팝업페이지", template="mobile", layout="default")
public class StoreInquiryMobileController {

	private static final Logger log = LoggerFactory.getLogger(StoreInquiryMobileController.class);

	@Autowired 
	StoreInquiryService storeInquiryService;
	
	@Autowired 
	FileService fileService;
	
	@Autowired
	SequenceService sequenceService;
	
	
	//LCH-StoreInquiryMobile - footer 입점문의 페이지        <수정>

	
	@RequestProperty(layout="default_popup")
	@GetMapping("/inquiry")
	public String index (@ModelAttribute StoreInquiry storeInquiry, Model model) {
		
		return ViewUtils.getView("/store-inquiry/inquiry");	
	}
	
	@PostMapping("/inquiry")
	public String create (@ModelAttribute StoreInquiry storeInquiry, Model model) {
		
		int sequenceId = sequenceService.getId("OP_STORE_INQUIRY");
		String message = "등록되었습니다.";
		
		if (storeInquiry.getFile() != null && storeInquiry.getFile().getSize() >0) {
			
			String extension = FileUtils.getExtension(storeInquiry.getFile().getOriginalFilename());
			int maxSize = 5 * 1024 * 1024; // 업로드 가능한 최대 용량 : 5MB
			
			final String[] AVAILABLE_EXTENSION = { "jpg", "jpeg", "gif", "bmp", "png", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "tif", "tiff", "hwp", "zip" };
			 
			boolean extenstion_check = false;
			
			for (int i = 0 ; i < AVAILABLE_EXTENSION.length ; i++) {
				if (extension.equals(AVAILABLE_EXTENSION[i])) {
					extenstion_check = true;
				}
			}
			
			if (extenstion_check) {
				if (maxSize > storeInquiry.getFile().getSize()) {
					
					// 1. 업로드 경로설정
					String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "store-inquiry";
					fileService.makeUploadPath(uploadPath);
					
					String defaultFileName = sequenceId + "." + extension;
					storeInquiry.setFileName(defaultFileName);
					
					// 2. 저장될 파일 
					File saveFile = new File(uploadPath + File.separator + defaultFileName);
					
					try {
						FileCopyUtils.copy(storeInquiry.getFile().getBytes(), saveFile);
					} catch (IOException e) {
						log.error("ERROR: {}", e.getMessage(), e);
					}
					
				} else {
					message = "업로드 가능한 최대 용량 : 5MB 입니다";
					return ViewUtils.getView("/store-inquiry/inquiry", message);		
				}
			} else {
				message = "유효하지 않은 파일입니다.";
				return ViewUtils.getView("/store-inquiry/inquiry", message);		
			}
		}
		
		storeInquiry.setStoreInquiryId(sequenceId);
		storeInquiryService.insertStoreInquiry(storeInquiry); //입점문의 등록
		
		return ViewUtils.redirect("/m", message);
	}
	
}
