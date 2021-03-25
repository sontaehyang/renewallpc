package saleson.shop.categoriesedit;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import saleson.shop.categoriesedit.domain.CategoriesEdit;
import saleson.shop.categoriesedit.support.CategoriesEditParam;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;




public interface CategoriesEditService {
	
	public HashMap<String,String> getCategoryOnPosition(CategoriesEditParam categoriesEditParam);
	
	public CategoriesEdit getCategoryByParam(CategoriesEditParam categoriesEditParam);
	
	public CategoriesEdit getCategoryById(CategoriesEditParam categoriesEditParam);
	
	public void insertCategoryEdit(CategoriesEdit categoriesEdit);
	
	public void insertCategoryEditFiles(CategoriesEdit categoriesEdit);
	
	public void updateCategoryEdit(CategoriesEdit categoriesEdit);
	
	public void updateCategoryEditFile(CategoriesEdit categoriesEdit);
	
	public List<CategoriesEdit> getCategoryPromotionListByParam(CategoriesEditParam categoriesEditParam);
	
	public void deleteCategoryEdit(CategoriesEdit categoriesEdit);
	
	public HashMap<String,String> getCategoryFontPosition(CategoriesEditParam categoriesEditParam);
	
	public void insertRelated(CategoriesTeam categoriesTeam);
	
	public void loginBannerUpdateCategoryEdit(CategoriesEdit categoriesEdit, MultipartFile multipartFile);

	void deleteLoginBannerImage(CategoriesEdit categoriesEdit, String bannerType);
	
	public List<CategoriesEdit> getCategoryLeftBanner(CategoriesEditParam categoriesEditParam);
	
	
}
