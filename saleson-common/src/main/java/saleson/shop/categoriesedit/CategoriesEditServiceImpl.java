package saleson.shop.categoriesedit;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ThumbnailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.categoriesedit.domain.CategoriesEdit;
import saleson.shop.categoriesedit.support.CategoriesEditParam;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupMapper;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.categoriesteamgroup.domain.CategoryTeamItem;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service("categoriesEditService")
public class CategoriesEditServiceImpl implements CategoriesEditService {
	private static final Logger log = LoggerFactory.getLogger(CategoriesEditServiceImpl.class);
	
	@Autowired
	SequenceService sequenceService;
	
	@Autowired
	CategoriesTeamGroupMapper categoriesTeamGroupMapper;
	
	@Autowired
	CategoriesEditMapper categoriesEditMapper;
	
	@Autowired
	FileService fileService;

	@Override
	public HashMap<String, String> getCategoryOnPosition(
			CategoriesEditParam categoriesEditParam) {
		
		List<CategoriesEdit> list = categoriesEditMapper.getCategoryListById(categoriesEditParam);
		HashMap<String, String> map = new HashMap<>();
		
		for (CategoriesEdit categoriesEdit : list) {
			
			if(categoriesEdit.getEditPosition().equals("promotion")){
				map.put(categoriesEdit.getEditPosition(), "on");
			}
			
			if(categoriesEdit.getEditContent() != null){
				if(!categoriesEdit.getEditContent().equals("")){
					map.put(categoriesEdit.getEditPosition(), "on");
				}
			}
		}
		return map;
	}

	@Override
	public CategoriesEdit getCategoryByParam(
			CategoriesEditParam categoriesEditParam) {
		return categoriesEditMapper.getCategoryByParam(categoriesEditParam);
	}

	@Override
	public void insertCategoryEdit(CategoriesEdit categoriesEdit) {
		
		categoriesEdit.setCategoryEditId(sequenceService.getId("OP_CATEGORY_EDIT"));
		
		categoriesEditMapper.insertCategoryEdit(categoriesEdit);
	}

	@Override
	public void insertCategoryEditFiles(CategoriesEdit categoriesEdit) {
		deleteCategoryEdit(categoriesEdit);
		imageSave(categoriesEdit);
	}
	
	

	@Override
	public void updateCategoryEdit(CategoriesEdit categoriesEdit) {
		categoriesEditMapper.updateCategoryEdit(categoriesEdit);
	}
	
	
	@Override
	public List<CategoriesEdit> getCategoryPromotionListByParam(
			CategoriesEditParam categoriesEditParam) {
		return categoriesEditMapper.getCategoryPromotionListByParam(categoriesEditParam);
	}

	@Override
	public void deleteCategoryEdit(CategoriesEdit categoriesEdit) {
		categoriesEditMapper.deleteCategoryEdit(categoriesEdit);
	}
	
	@Override
	public CategoriesEdit getCategoryById(
			CategoriesEditParam categoriesEditParam) {
		return categoriesEditMapper.getCategoryById(categoriesEditParam);
	}

	@Override
	public void updateCategoryEditFile(CategoriesEdit categoriesEdit) {
		try {
			
			CategoriesEditParam categoriesEditParam = new CategoriesEditParam();
			
			categoriesEditParam.setCategoryEditId(categoriesEdit.getCategoryEditId());
			
			categoriesEdit = getCategoryById(categoriesEditParam);
			
			
			FileUtils.delete( "/" + "category-edit" + "/" + categoriesEdit.getCode()+File.separator + "default" , ShopUtils.unescapeHtml(categoriesEdit.getEditImage()));
			FileUtils.delete( "/"+ "category-edit" + "/" + categoriesEdit.getCode()+File.separator + "thumb" , ShopUtils.unescapeHtml(categoriesEdit.getEditImage()));
			
			categoriesEditMapper.updateCategoryEditFile(categoriesEdit);
			
		} catch (IOException e) {
			log.warn(" updateCategoryEditFile(CategoriesEdit categoriesEdit) : {}", e.getMessage(), e);
		}
		
	}

	private void imageSave(CategoriesEdit categoriesEdit){
		
		if (categoriesEdit.getEditImages() != null) {
			
				for (int i = 0; i < categoriesEdit.getEditUrls().size(); i++) {
					
				String newFileName = "";
				
				int categoryEditId = sequenceService.getId("OP_CATEGORY_EDIT");
				
				System.out.println(" :::::::::::: "+ i + " = >" +categoriesEdit.getEditImages().get(i));
				
				//if (categoriesEdit.getEditImages().get(i) != null) {
					
					MultipartFile multipartFile = categoriesEdit.getEditImages().get(i);
					
					if(!multipartFile.isEmpty()){
						// 1. 업로드 경로설정
						String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "category-edit" + File.separator + categoriesEdit.getCode();
						fileService.makeUploadPath(uploadPath);
				    	
						// 2. 파일명 
						String defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
						
						
						String uploadPathDefault = uploadPath + File.separator + "default";
						
						fileService.makeUploadPath(uploadPathDefault);
						newFileName = FileUtils.getNewFileName(uploadPathDefault, defaultFileName);
						
						// 3. 저장될 파일 
						File saveFile = new File(uploadPathDefault + File.separator + newFileName);
						
						// 4. 섬네일 사이즈 
						
						try {
				            //FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
							//if( categoriesEdit.getEditKind().equals("1") ){
								// 원본 이미지 
							//if(categoriesEdit.getCode().equals("featured")) {
								// thumbnail 생성
								//ThumbnailUtils.create(multipartFile, saveFile, 840, 150);
							//} else {
								FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
								//ThumbnailUtils.create(multipartFile, saveFile, 670, 320);
							//}  
							
							String uploadPathThumb = uploadPath + File.separator + "thumb";
							fileService.makeUploadPath(uploadPathThumb);
							
							File thumbnailFile = new File(uploadPathThumb + File.separator + newFileName);
							FileCopyUtils.copy(multipartFile.getBytes(), thumbnailFile);
							 
					        //ThumbnailUtils.create(multipartFile, thumbnailFile, 167, 80);
					        
						 	/*} else {
								
								ThumbnailUtils.create(multipartFile, saveFile, 840, 320);
								// thumbnail 생성
					            File thumbnailFile = new File(uploadPath + File.separator + "thumb" + File.separator + newFileName);
					            ThumbnailUtils.create(multipartFile, thumbnailFile, 210, 80);
								
							}*/
			            
				        } catch (IOException e) {
				            throw new RuntimeException(e);
				        }
						
					} else {
						newFileName = categoriesEdit.getFileNames().get(i);
					}   
					
			//	} 
				
				categoriesEdit.setCategoryEditId(categoryEditId);
				categoriesEdit.setEditImage(newFileName);
				categoriesEdit.setEditUrl(categoriesEdit.getEditUrls().get(i));
				categoriesEdit.setEditContent(categoriesEdit.getEditContents().get(i));
				
				categoriesEditMapper.insertCategoryEdit(categoriesEdit);
				
			}
		}
	}

	
	
	@Override
	public void insertRelated(CategoriesTeam categoriesTeam) {
		categoriesTeamGroupMapper.deleteCategoryTeamItem(categoriesTeam.getCategoryTeamId());
		
		if(categoriesTeam.getRelatedItemIds() != null) {
			for (int i = 0; i < categoriesTeam.getRelatedItemIds().length; i++) {
				CategoryTeamItem categoryTeamItem = new CategoryTeamItem();
				categoryTeamItem.setCategoryTeamItemId(sequenceService.getId("OP_CATEGORY_TEAM_ITEM"));
				categoryTeamItem.setCategoryTeamId(categoriesTeam.getCategoryTeamId());
				categoryTeamItem.setItemId(Integer.parseInt(categoriesTeam.getRelatedItemIds()[i]));
				categoriesTeamGroupMapper.insertCategoryTeamItem(categoryTeamItem);
			}
		}
		
	}

	@Override
	public HashMap<String, String> getCategoryFontPosition(	CategoriesEditParam categoriesEditParam) {
		List<CategoriesEdit> list = categoriesEditMapper.getCategoryListById(categoriesEditParam);
		HashMap<String, String> map = new HashMap<>();
		
		for (CategoriesEdit categoriesEdit : list) {
			if(!categoriesEdit.getEditPosition().equals("promotion")){
				map.put(categoriesEdit.getEditPosition(), categoriesEdit.getEditContent());
			}
		}
		
		return map;
	}

	@Override
	public List<CategoriesEdit> getCategoryLeftBanner(CategoriesEditParam categoriesEditParam) {
		
		return categoriesEditMapper.getCategoryLeftBanner(categoriesEditParam);
	}
	
	public void loginBannerUpdateCategoryEdit(CategoriesEdit categoriesEdit, MultipartFile multipartFile) {
		
		int loginBannerSearch = categoriesEditMapper.loginCountCategoryEdit(categoriesEdit);
		
		int index;
		String loginCode = categoriesEdit.getCode();
		if (loginCode.equals("loginWeb")) {
			index = 0;
		} else {
			index = 1;
		}
		
		if (loginBannerSearch == 0 && categoriesEdit.getCode().equals("loginMobile")) {
			categoriesEdit.setCategoryEditId(sequenceService.getId("OP_CATEGORY_EDIT"));
		}
		
		if (categoriesEdit.getCategoryEditId() == 0) {
			categoriesEdit.setCategoryEditId(sequenceService.getId("OP_CATEGORY_EDIT"));
		}
		
		if (loginBannerSearch == 0) {
			// insert
			if (categoriesEdit.getEditImages().get(0).getSize() > 0) {
			String fileName = createLoginBannerImage(categoriesEdit, multipartFile, loginCode, index);
			categoriesEdit.setEditImage(fileName);
			}
			
			categoriesEditMapper.insertCategoryEdit(categoriesEdit);
			
			
		} else {
			// update
			if (categoriesEdit.getEditImages().get(0).getSize() > 0) {
				deleteLoginBannerImage(categoriesEdit, loginCode);
				String fileName = createLoginBannerImage(categoriesEdit, multipartFile, loginCode, index);
				categoriesEdit.setEditImage(fileName);
			}
			categoriesEditMapper.updateLoginCategoryEdit(categoriesEdit);
		}
		
	}
	
	@Override
	public void deleteLoginBannerImage(CategoriesEdit categoriesEdit, String bannerType) {
		CategoriesEdit secordCategoriesEdit = categoriesEditMapper.getCountCategoryEdit(categoriesEdit.getCategoryEditId());
			
			if (secordCategoriesEdit.getEditImage() != null) {
				// 1. 이미지 파일 삭제
				if (secordCategoriesEdit.getEditImage().indexOf("/") > -1) {
					FileUtils.delete(new File((FileUtils.getWebRootPath() + ShopUtils.unescapeHtml(secordCategoriesEdit.getEditImage())).replaceAll("/",  File.separator)));
				} else {
					String popupImageSrc = "/loginbanner/" + secordCategoriesEdit.getCategoryEditId() + "/" + bannerType + "/" + secordCategoriesEdit.getEditImage();
					FileUtils.delete(popupImageSrc);
				}
				// 2. 상품 이미지 정보 업데이트.
				secordCategoriesEdit.setEditImage("");
			}
		
			categoriesEditMapper.deleteLoginBannerImage(secordCategoriesEdit);
	}
	
	public String createLoginBannerImage(CategoriesEdit categoriesEdit, MultipartFile multipartFile, String bannerType, int index) {
		String[] ITEM_DEFAULT_IMAGE_WIDTHS = { "1150x-1", "200x-1" };
		String ITEM_DEFAULT_IMAGE_SAVE_SIZE = ITEM_DEFAULT_IMAGE_WIDTHS[index];
		
		String defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
		
			String saveFolderName = bannerType;
				
			// 1. 업로드 경로설정
			String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "loginbanner" + File.separator + categoriesEdit.getCategoryEditId() + File.separator + saveFolderName;
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
			String[] thumbnailSize = StringUtils.delimitedListToStringArray(ITEM_DEFAULT_IMAGE_SAVE_SIZE, "x");
			
		
			// 생성.
			try {		
				ThumbnailUtils.create(multipartFile, saveFile, Integer.parseInt(thumbnailSize[0]), Integer.parseInt(thumbnailSize[1]));
			} catch (IOException e) {
				log.warn("ThumbnailUtils.create(multipartFile...: {}", e.getMessage(), e);
			}
	        //fileService.createThumbnail(saveFile, uploadPath, newFileName, thumbnailSize, "0");
		
		return defaultFileName;
	}

	
}
