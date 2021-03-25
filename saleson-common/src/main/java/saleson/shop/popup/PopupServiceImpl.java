package saleson.shop.popup;


import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ThumbnailUtils;
import com.onlinepowers.framework.web.domain.ListParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.utils.ShopUtils;
import saleson.shop.popup.domain.Popup;
import saleson.shop.popup.domain.PopupSearchParam;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("popupService")
public class PopupServiceImpl implements PopupService {

	private static final Logger log = LoggerFactory.getLogger(PopupServiceImpl.class);


	@Autowired
	private PopupMapper popupMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private FileService fileService;
	

	@Override
	public int popupCount(PopupSearchParam popupSearchParam) {
		return popupMapper.popupCount(popupSearchParam);
	}

	@Override
	public List<Popup> popupList(PopupSearchParam popupSearchParam) {
		return popupMapper.popupList(popupSearchParam);
	}

	@Override
	public Popup getPopup(int popupId) {
		return popupMapper.getPopup(popupId);
	}

	@Override
	public void insertPopup(Popup popup) {
		
		popup.setPopupId(sequenceService.getId("OP_POPUP"));
		
		if (popup.getPopupImageFile() != null && popup.getPopupStyle().equals("3")) {
			if (popup.getPopupImageFile().getSize() > 0) {
			
				String imgSize = popup.getWidth() + "x" + popup.getHeight();
				
				MultipartFile multipartFile = popup.getPopupImageFile();
				
				String[] ITEM_DEFAULT_IMAGE_SAVE_FOLDER = new String[] {"popup"};
				String[] ITEM_DEFAULT_IMAGE_SAVE_SIZE = new String[] {imgSize, "0x0"};
				
				String defaultFileName = popup.getPopupId() + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
				
				for (int i = 0; i < ITEM_DEFAULT_IMAGE_SAVE_FOLDER.length; i++) {
					String saveFolderName = ITEM_DEFAULT_IMAGE_SAVE_FOLDER[i];
						
					// 1. 업로드 경로설정
					String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "popup" + File.separator + popup.getPopupId() + File.separator + saveFolderName;
					fileService.makeUploadPath(uploadPath);
			    	
					// 2. 파일명 중복파일 삭제
					try {
						FileUtils.delete(uploadPath, ShopUtils.unescapeHtml(defaultFileName));
					} catch (IOException e1) {
						log.warn(e1.getMessage(), e1);
					}
					
					// 2-1. 새로운 파일명.
					defaultFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);
	
					// 3. 저장될 파일 
					File saveFile = new File(uploadPath + File.separator + defaultFileName);
					
					// 4. 섬네일 사이즈 
					String[] thumbnailSize = StringUtils.delimitedListToStringArray(ITEM_DEFAULT_IMAGE_SAVE_SIZE[i], "x");
					
					// 생성.
					try {
						
						ThumbnailUtils.create(multipartFile, saveFile, Integer.parseInt(thumbnailSize[0]), Integer.parseInt(thumbnailSize[1]));
					} catch (IOException e) {
						log.error("ERROR: {}", e.getMessage(), e);
					}
			        //fileService.createThumbnail(saveFile, uploadPath, newFileName, thumbnailSize, "0");
				}
				// 대표이미지 파일명
				popup.setPopupImage(defaultFileName);
				popup.setContent("");
			} else {
				popup.setImageLink("");
				popup.setBackgroundColor("");
			}
		}
		popupMapper.insertPopup(popup);
	}

	@Override
	public void updatePopup(Popup popup) {
		Popup secondPopup = popupMapper.getPopup(popup.getPopupId());
		
		if (popup.getPopupImageFile() != null && popup.getPopupStyle().equals("3")) {
			if (popup.getPopupImageFile().getSize() > 0) {
				if (secondPopup.getPopupImage() != null) {
					deletePopupImage(popup.getPopupId());
				}
			
				String imgSize = popup.getWidth() + "x" + popup.getHeight();
				
				MultipartFile multipartFile = popup.getPopupImageFile();
				
				String[] ITEM_DEFAULT_IMAGE_SAVE_FOLDER = new String[] {"popup"};
				String[] ITEM_DEFAULT_IMAGE_SAVE_SIZE = new String[] {imgSize, "0x0"};
				
				String defaultFileName = popup.getPopupId() + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
				
				for (int i = 0; i < ITEM_DEFAULT_IMAGE_SAVE_FOLDER.length; i++) {
					String saveFolderName = ITEM_DEFAULT_IMAGE_SAVE_FOLDER[i];
						
					// 1. 업로드 경로설정
					String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "popup" + File.separator + popup.getPopupId() + File.separator + saveFolderName;
					fileService.makeUploadPath(uploadPath);
			    	
					// 2. 파일명 중복파일 삭제
					try {
						FileUtils.delete(uploadPath, ShopUtils.unescapeHtml(defaultFileName));
					} catch (IOException e1) {
						log.warn(e1.getMessage(), e1);
					}
					
					// 2-1. 새로운 파일명.
					defaultFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);
	
					// 3. 저장될 파일 
					File saveFile = new File(uploadPath + File.separator + defaultFileName);
					
					// 4. 섬네일 사이즈 
					String[] thumbnailSize = StringUtils.delimitedListToStringArray(ITEM_DEFAULT_IMAGE_SAVE_SIZE[i], "x");
					
					// 생성.
					try {
						
						ThumbnailUtils.create(multipartFile, saveFile, Integer.parseInt(thumbnailSize[0]), Integer.parseInt(thumbnailSize[1]));
					} catch (IOException e) {
						log.error("ERROR: {}", e.getMessage(), e);
					}
			        //fileService.createThumbnail(saveFile, uploadPath, newFileName, thumbnailSize, "0");
				}
				// 대표이미지 파일명
				popup.setPopupImage(defaultFileName);
				popup.setContent("");
			} else {
				popup.setPopupImage(secondPopup.getPopupImage());
			}
		} else { 
			if (secondPopup.getPopupImage() != null) {
				deletePopupImage(popup.getPopupId());
				popup.setPopupImage(null);
			}
			popup.setImageLink("");
			popup.setBackgroundColor("");
		}
		popupMapper.updatePopup(popup);
	}

	@Override
	public void deletePopup(int popupId) {
		popupMapper.deletePopup(popupId);
	}
	
	@Override
	public void deletePopupData(ListParam listparam) {
		
		if (listparam.getId() != null) {

			for (String popupId : listparam.getId()) {
				popupMapper.deletePopup(Integer.parseInt(popupId));
			}	
		}
	}
	
	@Override
	public void deletePopupImage(int popupId) {
		Popup popup = popupMapper.getPopup(popupId);
		
		// 1. 이미지 파일 삭제
		if (popup.getPopupImage().indexOf("/") > -1) {
			FileUtils.delete(new File((FileUtils.getWebRootPath() + ShopUtils.unescapeHtml(popup.getPopupImage())).replaceAll("/",  File.separator)));
		} else {
			String popupImageSrc = "/popup/" + popupId + "/popup/" + ShopUtils.unescapeHtml(popup.getPopupImage());
			FileUtils.delete(popupImageSrc);
		}
		// 2. 상품 이미지 정보 업데이트.
		popupMapper.updatePopupImage(popupId);
	}

	@Override
	public List<Popup> displayPopupList() {
		return popupMapper.displayPopupList();
	}
}