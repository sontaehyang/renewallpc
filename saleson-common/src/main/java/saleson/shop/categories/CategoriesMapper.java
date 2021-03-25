package saleson.shop.categories;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;

import saleson.shop.categories.domain.Breadcrumb;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categories.domain.ProductsRepCategories;
import saleson.shop.categories.domain.Team;
import saleson.shop.categories.support.CategoriesSearchParam;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("categoriesMapper")
public interface CategoriesMapper {
	
	/**
	 * 상품의 대표 카테고리를 조회
	 * @param itemId
	 * @return
	 */
	ProductsRepCategories getProductsRepCategoriesByItemId(int itemId);
	
	/**
	 * 카테고리 1차 목록
	 * @return
	 */
	List<Categories> getCategoiesListByLevel1(CategoriesSearchParam categoriesSearchParam);
	
	/**
	 * 카테고리 2~4차 목록
	 * @param categoriesSearchParam
	 * @return
	 */
	List<Categories>getCategoiesListByLevels(CategoriesSearchParam categoriesSearchParam);
	
	/**
	 * 카테고리 그룹 IN 1차카테고리 목록
	 * @return
	 */
	List<CategoriesGroup> getGroupsInCategoriesList();
	
	/**
	 * 카테고리 목록 
	 * @param categoriesSearchParam
	 * @return
	 */
	List<Categories> getCategoriesListById(CategoriesSearchParam categoriesSearchParam);
	
	/**
	 * 카테고리 업데이트
	 * @param categoriesSearchParam
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategoriesByParam(CategoriesSearchParam categoriesSearchParam);
	
	/**
	 * 카테고리 상세
	 * @param categoryCode
	 * @return
	 */
	Categories getCategoryByCategoryCode(String categoryCode);
	
	
	/**
	 * 부모의 categoryClass 코드로 자식 카테고리 목록을 가져온다.
	 * <pre>
	 * 카테고리 코드가 121000000000 ==> CategoryClass는 121임.
	 * 카테고리 코드가 121123000000 ==> CategoryClass는 121123임.
	 * </pre>
	 * @param categoryClass
	 * @return
	 */
	List<Categories> getChildCategoriesFromParantCategoryClass(String categoryClass);
	
	
	/**
	 * categoryId로 Breadcrumb을 위한 데이터 목록을 조회함.
	 * @param categoryId
	 */
	List<Breadcrumb> getBreadcrumbListByCategoryId(int categoryId);
	
	
	/**
	 * categoryUrl로 Breadcrumb을 위한 데이터 목록을 조회함.
	 * @param categoryId
	 */
	List<Breadcrumb> getBreadcrumbListByCategoryUrl(String categoryUrl);

	
	/**
	 * categoryGroupId 에 해당하는 1차 카테고리 classCode를 조회한다.
	 * 1차 카테고리의 3자리 수를 리턴.
	 * <pre>
	 * 	113
	 * 	114
	 * 	113
	 * </pre>
	 * @param categoryGroupId
	 * @return
	 */
	List<String> getCategoryClassCodesByCategoryGroupId(int categoryGroupId);
	
	
	/**
	 * 카테고리 그룹코드로 1차 카테고리 목록을 조회한다.
	 * @param categoryGroupCode
	 * @return
	 */
	List<Categories> getCategoriesListByGroupCode(String categoryGroupCode);
	
	
	/**
	 * categroyLevel 당 CLASS 맥스값 + 1 조회
	 * @param categoriesSearchParam
	 * @return
	 */
	String getCategoryMaxClassByCategoryLevel(Categories categories);

	
	/**
	 * 카테고리 등록
	 * @param categories
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void insertCategory(Categories categories);

	
	/**
	 * Breadcrumb 데이터를 조회한다.. 
	 * @param itemCategories List<ItemCategory>형태의 List 
	 * @return
	 */
	List<Breadcrumb> getBreadcrumbListByCollection(
			List<?> object);
	
	
	/**
	 * 
	 * @param categories
	 * @return
	 */
	int getCategoryLevelMaxOrdring(Categories categories);
	
	
	/**
	 * 
	 * @param categories
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategory(Categories categories);
	
	
	/**
	 * 
	 * @param categories
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateChangeCategory(Categories categories);
	
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateOrderingCategory(Categories categories);
	
	List<Categories> getCategoryOrderingDownList(Categories categories);
	
	int getcategoryLevelsCount(Categories categories);
	
	
	@CacheEvict(value="frontCategories", allEntries=true)
	void deleteCategoryByClass(Categories categories);
	
	
	
	
	
	/**
	 * 프론트 GNB, LNB 구성을 위한 카테고리 정보를 조회함.
	 * TEAM > GROUP > 1차 카테고리 > 2차 카테고리 까지. 조회 
	 * @return
	 */
	List<Team> getCategoriesForFront();
	
	
	/**
	 * 카테고리코드로 타입을 조회한다.
	 * <pre>
	 * /categories/index/{categoryCode} 로 접속하는 경우 
	 * {categoryCode}에 따로 team, group, category인지 판단한다. 
	 * </pre>
	 * @param categoryCode
	 * @return String 'team', 'group', 'category', ''
	 */
	String getCategoryTypeByCategoryCode(String categoryCode);
	
	
	/**
	 * 카테고리 정보를 조회한다.
	 * 부모, 형제, 자식 카테고리 정보 조회
	 * 1~4차 카테고리 접속시 조회됨.
	 * @param categoryCode
	 * @return
	 */
	Categories getCategoryAndParentSiblingChildByCategoryUrl(
			String categoryCode);

	
	/**
	 * 현재 카테고리, 부모, 형제 카테고리 정보를 조회한다.
	 * @param categoryCode
	 * @return
	 */
	Categories getCategoryAndParentSiblingByCategoryUrl(String categoryCode);
	
	
	/**
	 * 카테고리 URL로 현재 카테고리 정보를 조회한다. 
	 * @param categoryCode
	 * @return
	 */
	Categories getCategoryByCategoryUrl(String categoryCode);
	
	
	List<Categories> getCategoryLowList(Categories categories);
	
	Categories getCategoriesById(int categoryId);
	
	//LCH-CategoriesMapper 스팟 세일 - 카테고리 1차 리스트  <추가>
	
	List<CategoriesGroup> getCategoriesGroupId();
	
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategoryEdit(Categories categories);
	
	Categories getCategoriesByParam(CategoriesSearchParam categoriesSearchParam);
	
	List<Team> getCategoryGroupRanking(String code);
	
	int getCategoryCountByCode(CategoriesSearchParam categoriesSearchParam);

	
	/**
	 * [엑셀업로드] 카테고리URL에 해당하는 Category ID를 조회함.
	 * 데이터가 없는 경우 BindingException 발생한 예외처리 해야함. (NULL->int)
	 * @param categoryUrl
	 * @return
	 */
	int getCategoryIdByCategoryUrl(String categoryUrl);
	
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategoryFalg(Categories categories);

	
	/**
	 * 1차 카테고리 순서 업데이트 (일괄)
	 * @param categoriesSearchParam
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategoryOrderingAfterIncreaseInGroup(
			CategoriesSearchParam categoriesSearchParam);

	/**
	 * 카테고리 순서를 업데이트 한다.
	 * @param categoriesSearchParam
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateOrderingCategory1FromJsTree(
			CategoriesSearchParam categoriesSearchParam);

	/**
	 * 이동할 카테고리의 자식 놈들의 Ordering을 일괄 업데이트한다.
	 * @param categoriesSearchParam
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategoryOrderingFromJsTree(
			CategoriesSearchParam categoriesSearchParam);

	/**
	 * CATEGORY_CLASS 목록 조회 
	 * @param categoriesSearchParam
	 * @return
	 */
	List<String> getCategoryClassListBy(
			CategoriesSearchParam categoriesSearchParam);

	/**
	 * 이동하는 카테고리 정보를 수정함.
	 * @param newCategory
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCurrentCategoryFromJsTree(CategoriesSearchParam newCategory);

	
	/**
	 * 현재 카테고리의 자식 카테고리 목록
	 * @param categoriesSearchParam
	 * @return
	 */
	List<Categories> getChildCategoriesFromJsTree(
			CategoriesSearchParam categoriesSearchParam);

	
	/**
	 * 카테고리 코드 베이스 (112113000000 -> 112113 %)
	 * @param categoryCodeBase
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void deleteCategoryWithChilrenByCategoryCodeBase(String categoryCodeBase);

	/**
	 * 하위 카테고리 정보 변경. (categoryCode, categoryClass, Level) 1->2차, 2->3차, 3->4차, 레벨 + 1
	 * @param category
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateSubCategoriesForGroupToCategory(Categories category);

	/**
	 * 부모 카테고리 정보로 새로운 자식 카테고리의 CATEGORY_CODE를 가져옴.
	 * @param categories
	 * @return
	 */
	String getNewCategoryCodeByParent(Categories categories);

	/**
	 * 하위 카테고리의 CATEGORY_GROUP_ID 일괄 수정
	 * @param categories
	 */
	void updateCategoryGroupIdOfChild(Categories categories);

	/**
	 * 현재 그룹의 전체 카테고리 가져옴.
	 * @param categoryCode
	 * @return
	 */
	List<Categories> getCategoryByCategorygroupId(String categoryCode);
	
	/**
	 * MaxLevel보다 작거나 같은 Level의 카테고리를 조회
	 * @param maxLevel
	 * @return
	 */
	List<Categories> getCategoryListByMaxLevel(int maxLevel);

	/**
	 * Sitemap용 카테고리 정보 조회
	 * @return
	 */
	List<Categories> getCategoryListBySitemap();
}
