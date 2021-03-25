package saleson.shop.categories;

import java.util.HashMap;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import saleson.shop.categories.domain.Breadcrumb;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categories.domain.PriceArea;
import saleson.shop.categories.domain.Team;
import saleson.shop.categories.support.CategoriesSearchParam;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;



public interface CategoriesService {
	
	/**
	 * 카테고리 1차 목록
	 * @return List<Categories>
	 */
	public List<Categories> getCategoiesListByLevel1(CategoriesSearchParam categoriesSearchParam);
	
	
	/**
	 * 카테고리 1차 목록
	 * @return List<HashMap<String,Object>>
	 */
	public List<HashMap<String,Object>> getCategoiesTreeListByLevel1(CategoriesSearchParam categoriesSearchParam);
	
	
	/**
	 * 카테고리 2~4차 목록
	 * @param categoriesSearchParam
	 * @return
	 */
	public List<HashMap<String,Object>> getCategoiesTreeListByLevels(CategoriesSearchParam categoriesSearchParam);
	

	/**
	 * 카테고리 그룹 IN 1차카테고리 목록
	 * @return
	 */
	public List<CategoriesGroup> getGroupsInCategoriesList();
	
	
	/**
	 * 카테고리 목록
	 * @param categoriesSearchParam
	 * @return
	 */
	public List<Categories> getCategoriesListById(CategoriesSearchParam categoriesSearchParam);
	
	
	/**
	 * 카테고리 업데이트
	 * @param categoriesSearchParam
	 */
	public void updateCategoriesByParam(CategoriesSearchParam categoriesSearchParam);
	
	
	/**
	 * 카테고리 상세
	 * @param categoryCode
	 * @return
	 */
	public Categories getCategoryByCategoryCode(String categoryCode);
	
	
	/**
	 * 부모의 categoryClass 코드로 자식 카테고리 목록을 가져온다.
	 * <pre>
	 * 카테고리 코드가 121000000000 ==> CategoryClass는 121임.
	 * 카테고리 코드가 121123000000 ==> CategoryClass는 121123임.
	 * </pre>
	 * @param categoryClass
	 * @return
	 */
	public List<Categories> getChildCategoriesFromParantCategoryClass(String categoryClass);
	
	
	/**
	 * categoryId로 Breadcrumb을 위한 데이터 목록을 조회함.
	 * @param categoryId
	 */
	public List<Breadcrumb> getBreadcrumbListByCategoryId(int categoryId);
	
	
	/**
	 * categoryUrl로 Breadcrumb을 위한 데이터 목록을 조회함.
	 * @param categoryId
	 */
	public List<Breadcrumb> getBreadcrumbListByCategoryUrl(String categoryUrl);
	
	
	/**
	 * 카테고리 그룹코드로 1차 카테고리 목록을 조회한다.
	 * @param categoryGroupCode
	 * @return
	 */
	public List<Categories> getCategoriesListByGroupCode(
			String categoryGroupCode);
	
	/**
	 * 카테고리 등록
	 * @param categories
	 */
	public void insertCategory(Categories categories);
	
	
	/**
	 * 하위 카테고리 등록
	 * @param categories
	 */
	public void insertCategorySub(Categories categories);
	
	
	public void updateCategory(Categories categories);
	
	public void updateChangeCategory(CategoriesSearchParam categoriesSearchParam);
	
	public void deleteCategoryByClass(Categories categories);


	/**
	 * 프론트 GNB, LNB 구성을 위한 카테고리 정보를 조회함.
	 * TEAM > GROUP > 1차 카테고리 > 2차 카테고리 까지. 조회 
	 * @return
	 */
	@Cacheable("frontCategories")
	public List<Team> getCategoriesForFront();

	public List<Team> getCategoriesForApi();

	/**
	 * 카테고리코드로 타입을 조회한다.
	 * <pre>
	 * /categories/index/{categoryCode} 로 접속하는 경우 
	 * {categoryCode}에 따로 team, group, category인지 판단한다. 
	 * </pre>
	 * @param categoryCode
	 * @return String 'team', 'group', 'category', ''
	 */
	public String getCategoryTypeByCategoryCode(String categoryCode);


	/**
	 * 카테고리 정보를 조회한다.
	 * 부모, 형제, 자식 카테고리 정보 조회
	 * 1~4차 카테고리 접속시 조회됨.
	 * @param categoryCode
	 * @return
	 */
	public Categories getCategoryAndParentSiblingChildByCategoryUrl(
			String categoryCode);


	/**
	 * 현재 카테고리, 부모, 형제 카테고리 정보를 조회한다.
	 * @param categoryCode
	 * @return
	 */
	public Categories getCategoryAndParentSiblingByCategoryUrl(
			String categoryCode);

	/**
	 * 카테고리 URL로 현재 카테고리 정보를 조회한다. 
	 * @param categoryCode
	 * @return
	 */
	public Categories getCategoryByCategoryUrl(String categoryCode);
	
	
	public Categories getCategoriesById(int categoryId);
	
	public void updateCategoryEdit(Categories categories);

	public Categories getCategoriesByParam(CategoriesSearchParam categoriesSearchParam);
	
	public List<Team> getCategoryGroupRanking(String code);
	
	public int getCategoryCountByCode(CategoriesSearchParam categoriesSearchParam);
	
	//LCH-CategiriesService 스팟 세일 - 카테고리 1차 리스트  <추가>
	
	public List<CategoriesGroup> getCategoriesGroupId();

	/**
	 * JS Tree에서 1차 카테고리 순서를 변경한다. 
	 * (Group 안에서 변경함)
	 * @param categoriesSearchParam
	 */
	public void updateChangeOrderingCategory1InGroup(
			CategoriesSearchParam categoriesSearchParam);


	/**
	 * jsTree 카테고리 이동처리.
	 * @param categoriesSearchParam
	 */
	public void updateCategoryPosition(
			CategoriesSearchParam categoriesSearchParam);


	public List<Categories> getCategoryByCategorygroupId(String categoryCode);

	/**
	 * 카테고리에 속한 상품 금액대 목록 조회
	 * @param categoryId
	 * @return
	 */
	List<PriceArea> getPriceAreaListById(int categoryId);
}
