package saleson.shop.categoriesteamgroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;

import saleson.shop.calendar.domain.Calendar;
import saleson.shop.calendar.support.CalendarSearchParam;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.categoriesteamgroup.domain.CategoryTeamItem;
import saleson.shop.categoriesteamgroup.domain.ItemCategoryTeam;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("categoriesTeamGroupMapper")
public interface CategoriesTeamGroupMapper {
	
	/**
	 * @param groupCode
	 * @return
	 */
	List<String> getCategoryClass1ListByGroupCode(String groupCode);
	
	/**
	 * 팀별 그룹 관리 전체 리스트
	 * @return
	 */
	ArrayList<CategoriesTeam> getCategoriesTeamGroupList();
	
	/**
	 * 팀별 그룹 관리 팀별 리스트
	 * @return
	 */
	ArrayList<CategoriesTeam> getCategoriesTeamList();
	
	/**
	 * 팀별 그룹 관리 그룹 리스트
	 * @return
	 */
	ArrayList<CategoriesGroup> getCategoriesGroupList();
	
	/**
	 * 팀별 그룹 관리 팀별 상세 정보
	 * @param categoriesTeamGroupSearchParam
	 * @return
	 */
	CategoriesTeam getCategoriesTeamById(CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam);
	
	/**
	 * 팀별 그룹 관리 그룹 상세 정보
	 * @param categoriesTeamGroupSearchParam
	 * @return
	 */
	CategoriesGroup getCategoriesGroupById(CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam);
	
	/**
	 *  팀별 그룹 관리 팀별 순서 최대값
	 * @return
	 */
	int getCategoriesMaxOrdering(HashMap<String, String> map);
	
	/**
	 * 팀별 그룹 관리 팀별 등록
	 * @param categoriesTeam
	 */
	void insertCategoriesTeam(CategoriesTeam categoriesTeam);
	
	/**
	 *  팀별 그룹 관리 팀별 순서 변경
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategoriesTeamOrdering(CategoriesTeam categoriesTeam);
	
	/**
	 *  팀별 그룹 관리 그룹 순서 변경
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategoriesGroupOrdering(CategoriesGroup categoriesGroup);
	
	@CacheEvict(value="frontCategories", allEntries=true)
	void insertCategoriesGroup(CategoriesGroup categoriesGroup);
	
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategoriesGroupById(CategoriesGroup categoriesGroup);
	
	@CacheEvict(value="frontCategories", allEntries=true)
	void updateCategoriesTeamById(CategoriesTeam categoriesTeam);
	
	@CacheEvict(value="frontCategories", allEntries=true)
	void deleteCategoryTeam(CategoriesTeam categoriesTeam);
	
	@CacheEvict(value="frontCategories", allEntries=true)
	void deleteCategoryGorup(CategoriesGroup categoriesGroup);
	
	/**
	 *  팀별 그룹 관리 팀별 추천상품 등록
	 */
	
	void insertCategoryTeamItem(CategoryTeamItem categoryTeamItem);
	
	void deleteCategoryTeamItem(int categoryTeamId);
	
	List<ItemCategoryTeam> getCategoryTeamItemListByParam(CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam);
	
	List<CategoriesGroup> getCategoriesGroupListParam (CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam);

	/**
	 * 
	 * @param categoryGroupId
	 * @return 
	 */
	int getMaxCategoryLevelByGroupId(int categoryGroupId);

	/**
	 * 카테고리 그룹 삭제.
	 * @param parseInt
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	void deleteCategoryGorupById(int parseInt);

	void updateBestItemDisplayType(CategoriesTeam categoriesTeam);
	
	/**
	 * 그룹별 상품코드 등록 (그룹 베너 관리)
	 * @param categoriesGroup
	 */
	public void updateCategoriesGroupItemListById(CategoriesGroup categoriesGroup);
}
