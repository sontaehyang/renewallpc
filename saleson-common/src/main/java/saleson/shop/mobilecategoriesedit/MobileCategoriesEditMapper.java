package saleson.shop.mobilecategoriesedit;

import java.util.List;

import saleson.shop.categoriesedit.domain.CategoriesEdit;
import saleson.shop.categoriesedit.support.CategoriesEditParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("moblieCategoriesEditMapper")
public interface MobileCategoriesEditMapper {

	List<CategoriesEdit> getCategoryListById(CategoriesEditParam categoriesEditParam);
	
	CategoriesEdit getCategoryByParam(CategoriesEditParam categoriesEditParam);
	
	CategoriesEdit getCategoryById(CategoriesEditParam categoriesEditParam);
	
	void insertCategoryEdit(CategoriesEdit categoriesEdit);
	
	void updateCategoryEdit(CategoriesEdit categoriesEdit);
	
	void updateCategoryEditFile(CategoriesEdit categoriesEdit);
	
	List<CategoriesEdit> getCategoryPromotionListByParam(CategoriesEditParam categoriesEditParam);
	
	void deleteCategoryEdit(CategoriesEdit categoriesEdit);
	
}
