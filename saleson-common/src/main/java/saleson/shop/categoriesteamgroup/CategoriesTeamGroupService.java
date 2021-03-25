package saleson.shop.categoriesteamgroup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;

import saleson.shop.calendar.domain.Calendar;
import saleson.shop.calendar.support.CalendarSearchParam;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.categoriesteamgroup.domain.CategoryTeamItem;
import saleson.shop.categoriesteamgroup.domain.ItemCategoryTeam;
import saleson.shop.categoriesteamgroup.domain.TeamGroupListParam;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;



public interface CategoriesTeamGroupService {
	
	/**
	 * 팀별 그룹 관리 전체 리스트
	 * @return
	 */
	public ArrayList<CategoriesTeam> getCategoriesTeamGroupList();
	
	/**
	 * 팀별 그룹 관리 팀별 리스트
	 * @return
	 */
	public ArrayList<CategoriesTeam> getCategoriesTeamList();
	
	/**
	 * 팀별 그룹 관리 그룹 리스트
	 * @return
	 */
	public ArrayList<CategoriesGroup> getCategoriesGroupList();
	
	/**
	 * 팀별 그룹 관리 팀별 상세 정보
	 * @param categoriesTeamGroupSearchParam
	 * @return
	 */
	public CategoriesTeam getCategoriesTeamById(CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam);
	
	/**
	 * 팀별 그룹 관리 그룹 상세 정보
	 * @param categoriesTeamGroupSearchParam
	 * @return
	 */
	public CategoriesGroup getCategoriesGroupById(CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam);
	
	/**
	 *  팀별 그룹 관리 팀별 순서 최대값
	 * @return
	 */
	public int getCategoriesMaxOrdering(String dataBaseTable);
	
	/**
	 * 팀별 그룹 관리 팀별 등록
	 * @param categoriesTeam
	 */
	public void insertCategoriesTeam(CategoriesTeam categoriesTeam);
	
	/**
	 *  팀별 그룹 관리 팀별 순서 변경
	 *  @param categoriesTeam
	 */
	public void updateCategoriesTeamOrdering(CategoriesTeam categoriesTeam);
	
	/**
	 *  팀별 그룹 관리 그룹 순서 변경
	 *  @param categoriesGroup
	 */
	public void updateCategoriesGroupOrdering(CategoriesGroup categoriesGroup);
	
	public int insertCategoriesGroup(CategoriesGroup categoriesGroup);
	
	public void updateCategoriesGroupById(CategoriesGroup categoriesGroup);
	
	public void updateCategoriesTeamById(CategoriesTeam categoriesTeam);
	
	public void deleteCategoryTeam(CategoriesTeam categoriesTeam);
	
	public void deleteCategoryGorup(CategoriesGroup categoriesGroup);
	
	/**
	 *  팀별 그룹 관리 팀별 추천상품 등록
	 */
	public void insertCategoryTeamItem(CategoryTeamItem categoryTeamItem);
	
	public void deleteCategoryTeamItem(int categoryTeamId); 
	
	public List<ItemCategoryTeam> getCategoryTeamItemListByParam(CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam);
	
	public List<CategoriesGroup> getCategoriesGroupListParam (CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam);

	public int getMaxCategoryLevelByGroupId(int categoryGroupId);

	
	/**
	 * Group --> 1차로 변경.
	 * @param categoriesTeamGroupParam
	 */
	public void updateGroupToCategory(
			CategoriesTeamGroupSearchParam categoriesTeamGroupParam);

	public void updateCategoriesTeamOrdering(TeamGroupListParam listParam);

	public void updateCategoriesGroupOrdering(TeamGroupListParam listParam);

	public void updateBestItemDisplayType(String categoryTeamCode,
			String bestItemDisplayType);
	
	/**
	 * 그룹별 상품코드 등록 (그룹 베너 관리)
	 * @param categoriesGroup
	 */
	@CacheEvict(value="frontCategories", allEntries=true)
	public void updateCategoriesGroupItemListById(CategoriesGroup categoriesGroup);
	
}
