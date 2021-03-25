package saleson.shop.categories;

import org.springframework.cache.annotation.CacheEvict;

import saleson.shop.categories.domain.Categories;

import com.onlinepowers.framework.orm.mybatis.annotation.MapperBatch;

@MapperBatch("categoriesMapperBatch")
public interface CategoriesMapperBatch {
	
	/**
	 * 이동하는 카테고리의 자식 카테고리 정보를 수정함.
	 * @param newCategory
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateChildCategoryFromJsTree(Categories category);
}
