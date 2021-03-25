package saleson.shop.mobilecategoriesedit;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
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
import saleson.shop.config.ConfigService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service("mobileCategoriesEditService")
public class MoblieCategoriesEditServiceImpl implements MobileCategoriesEditService {
	private static final Logger log = LoggerFactory.getLogger(MoblieCategoriesEditServiceImpl.class);
	
	@Autowired
	MobileCategoriesEditMapper mobileCategoriesMainMapper;
	
	@Autowired
	SequenceService sequenceService;
	
	@Autowired
	CategoriesTeamGroupMapper categoriesTeamGroupMapper;
	
	@Autowired
	FileService fileService;

	@Autowired
	ConfigService configService;

	@Override
	public HashMap<String, String> getCategoryOnPosition(
			CategoriesEditParam categoriesEditParam) {
		
		List<CategoriesEdit> list = mobileCategoriesMainMapper.getCategoryListById(categoriesEditParam);
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
		return mobileCategoriesMainMapper.getCategoryByParam(categoriesEditParam);
	}

	@Override
	public void insertCategoryEdit(CategoriesEdit categoriesEdit) {
		
		categoriesEdit.setCategoryEditId(sequenceService.getId("OP_CATEGORY_EDIT"));
		
		mobileCategoriesMainMapper.insertCategoryEdit(categoriesEdit);
	}

	@Override
	public void insertCategoryEditFiles(CategoriesEdit categoriesEdit) {
		deleteCategoryEdit(categoriesEdit);
		imageSave(categoriesEdit);
	}
	
	

	@Override
	public void updateCategoryEdit(CategoriesEdit categoriesEdit) {
		mobileCategoriesMainMapper.updateCategoryEdit(categoriesEdit);
	}
	
	
	@Override
	public List<CategoriesEdit> getCategoryPromotionListByParam(
			CategoriesEditParam categoriesEditParam) {
		return mobileCategoriesMainMapper.getCategoryPromotionListByParam(categoriesEditParam);
	}

	@Override
	public void deleteCategoryEdit(CategoriesEdit categoriesEdit) {
		//
		mobileCategoriesMainMapper.deleteCategoryEdit(categoriesEdit);
	}
	
	@Override
	public CategoriesEdit getCategoryById(
			CategoriesEditParam categoriesEditParam) {
		return mobileCategoriesMainMapper.getCategoryById(categoriesEditParam);
	}

	@Override
	public void updateCategoryEditFile(CategoriesEdit categoriesEdit) {
		try {
			
			CategoriesEditParam categoriesEditParam = new CategoriesEditParam();
			
			categoriesEditParam.setCategoryEditId(categoriesEdit.getCategoryEditId());
			
			categoriesEdit = getCategoryById(categoriesEditParam);
			
			
			FileUtils.delete( "/" + "mobile-category-edit" + "/" + categoriesEdit.getCode()+File.separator + "defult" , ShopUtils.unescapeHtml(categoriesEdit.getEditImage()));
			//FileUtils.delete( "/"+ "mobile-category-edit" + "/" + categoriesEdit.getCode()+File.separator + "thumb" , categoriesEdit.getEditImage());
			
			mobileCategoriesMainMapper.updateCategoryEditFile(categoriesEdit);
			
		} catch (IOException e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
		
	}

	private void imageSave(CategoriesEdit categoriesEdit){
		
		if (categoriesEdit.getEditImages() != null) {
			
				for (int i = 0; i < categoriesEdit.getEditUrls().size(); i++) {
					
				String newFileName = "";
				
				int categoryEditId = sequenceService.getId("OP_MOBILE_CATEGORY_EDIT");
				
				System.out.println(" :::::::::::: "+ i + " = >" +categoriesEdit.getEditImages().get(i));
				
				//if (categoriesEdit.getEditImages().get(i) != null) {
					
					MultipartFile multipartFile = categoriesEdit.getEditImages().get(i);
					
					if(!multipartFile.isEmpty()){
						// 1. 업로드 경로설정
						String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "mobile-category-edit" + File.separator + categoriesEdit.getCode() + File.separator + categoriesEdit.getEditPosition();
						fileService.makeUploadPath(uploadPath);
				    	
						// 2. 파일명 
						String defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
						newFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);
	
						// 3. 저장될 파일 
						File saveFile = new File(uploadPath + File.separator + newFileName);
						
						// 4. 섬네일 사이즈 
						
						try {
							
				            FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
				            
							//if( categoriesEdit.getEditKind().equals("1") ){
								// 원본 이미지 
							//ThumbnailUtils.create(multipartFile, saveFile, 320, 188);
					            
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
				
				mobileCategoriesMainMapper.insertCategoryEdit(categoriesEdit);
				
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
		configService.updateCategoryUpdatedDate();
	}

	@Override
	public HashMap<String, String> getCategoryFontPosition(	CategoriesEditParam categoriesEditParam) {
		List<CategoriesEdit> list = mobileCategoriesMainMapper.getCategoryListById(categoriesEditParam);
		HashMap<String, String> map = new HashMap<>();
		
		for (CategoriesEdit categoriesEdit : list) {
			if(!categoriesEdit.getEditPosition().equals("promotion")){
				map.put(categoriesEdit.getEditPosition(), categoriesEdit.getEditContent());
			}
		}
		
		return map;
	}
	
	
	
}
