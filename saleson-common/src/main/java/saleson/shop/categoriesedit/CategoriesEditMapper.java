package saleson.shop.categoriesedit;

import java.util.List;

import saleson.shop.categoriesedit.domain.CategoriesEdit;
import saleson.shop.categoriesedit.support.CategoriesEditParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("categoriesEditMapper")
public interface CategoriesEditMapper {

	List<CategoriesEdit> getCategoryListById(CategoriesEditParam categoriesEditParam);
	
	CategoriesEdit getCategoryByParam(CategoriesEditParam categoriesEditParam);
	
	CategoriesEdit getCategoryById(CategoriesEditParam categoriesEditParam);
	
	void insertCategoryEdit(CategoriesEdit categoriesEdit);
	
	void updateCategoryEdit(CategoriesEdit categoriesEdit);
	
	void updateCategoryEditFile(CategoriesEdit categoriesEdit);
	
	List<CategoriesEdit> getCategoryPromotionListByParam(CategoriesEditParam categoriesEditParam);
	
	void deleteCategoryEdit(CategoriesEdit categoriesEdit);
	
	int loginCountCategoryEdit(CategoriesEdit categoriesEdit);
	
	CategoriesEdit getCountCategoryEdit(int categoryEditId);
	
	void deleteLoginBannerImage(CategoriesEdit categoriesEdit);
	
	void updateLoginCategoryEdit(CategoriesEdit categoriesEdit);
	
	List<CategoriesEdit> getCategoryLeftBanner(CategoriesEditParam categoriesEditParam);
}
