package saleson.shop.categoriesteamgroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.categories.CategoriesMapper;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categories.support.CategoriesSearchParam;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.categoriesteamgroup.domain.CategoryTeamItem;
import saleson.shop.categoriesteamgroup.domain.ItemCategoryTeam;
import saleson.shop.categoriesteamgroup.domain.TeamGroupListParam;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;

import com.onlinepowers.framework.sequence.service.SequenceService;
import saleson.shop.config.ConfigMapper;
import saleson.shop.config.ConfigService;

@Service("categoriesTeamGroupService")
public class CategoriesTeamGroupServiceImpl implements CategoriesTeamGroupService {
	private static final Logger log = LoggerFactory.getLogger(CategoriesTeamGroupServiceImpl.class);

	@Autowired
	ConfigService configService;

	@Autowired
	CategoriesTeamGroupMapper categoriesTeamGroupMapper;
	
	@Autowired
	CategoriesMapper categoriesMapper;
	
	
	@Autowired
	SequenceService sequenceService;

	@Override
	public ArrayList<CategoriesTeam> getCategoriesTeamGroupList() {
		return categoriesTeamGroupMapper.getCategoriesTeamGroupList();
	}

	@Override
	public ArrayList<CategoriesTeam> getCategoriesTeamList() {
		return categoriesTeamGroupMapper.getCategoriesTeamList();
	}

	@Override
	public ArrayList<CategoriesGroup> getCategoriesGroupList() {
		return categoriesTeamGroupMapper.getCategoriesGroupList();
	}

	@Override
	public CategoriesTeam getCategoriesTeamById(
			CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam) {
		return categoriesTeamGroupMapper.getCategoriesTeamById(categoriesTeamGroupSearchParam);
	}

	@Override
	public CategoriesGroup getCategoriesGroupById(
			CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam) {
		return categoriesTeamGroupMapper.getCategoriesGroupById(categoriesTeamGroupSearchParam);
	}
	
	@Override
	public int getCategoriesMaxOrdering(String dataBaseTable) {
		HashMap<String ,String> map = new HashMap<>();

		map.put("dataBaseTable", dataBaseTable);

		return categoriesTeamGroupMapper.getCategoriesMaxOrdering(map);
	}

	@Override
	public void insertCategoriesTeam(CategoriesTeam categoriesTeam) {
		int categoryTeamId = sequenceService.getId("OP_CATEGORY_TEAM");
		categoriesTeam.setCategoryTeamId(categoryTeamId);
		categoriesTeam.setOrdering(getCategoriesMaxOrdering("OP_CATEGORY_TEAM"));
		
		categoriesTeamGroupMapper.insertCategoriesTeam(categoriesTeam);
		
		if(categoriesTeam.getRelatedItemIds() != null) {
			for (int i = 0; i < categoriesTeam.getRelatedItemIds().length; i++) {
				CategoryTeamItem categoryTeamItem = new CategoryTeamItem();
				categoryTeamItem.setCategoryTeamItemId(sequenceService.getId("OP_CATEGORY_TEAM_ITEM"));
				categoryTeamItem.setCategoryTeamId(categoryTeamId);
				categoryTeamItem.setItemId(Integer.parseInt(categoriesTeam.getRelatedItemIds()[i]));
				insertCategoryTeamItem(categoryTeamItem);
			}
		}
	}

	@Override
	public void updateCategoriesTeamOrdering(CategoriesTeam categoriesTeam) {
		configService.updateCategoryUpdatedDate();
		categoriesTeamGroupMapper.updateCategoriesTeamOrdering(categoriesTeam);
	}

	@Override
	public void updateCategoriesGroupOrdering(CategoriesGroup categoriesGroup) {
		configService.updateCategoryUpdatedDate();
		categoriesTeamGroupMapper.updateCategoriesGroupOrdering(categoriesGroup);
	}

	@Override
	public int insertCategoriesGroup(CategoriesGroup categoriesGroup) {
		categoriesGroup.setCategoryGroupId(sequenceService.getId("OP_CATEGORY_GROUP"));
		categoriesGroup.setOrdering(getCategoriesMaxOrdering("OP_CATEGORY_GROUP"));
		categoriesTeamGroupMapper.insertCategoriesGroup(categoriesGroup);

		configService.updateCategoryUpdatedDate();
		return categoriesGroup.getCategoryGroupId();
	}

	@Override
	public void updateCategoriesGroupById(CategoriesGroup categoriesGroup) {
		categoriesTeamGroupMapper.updateCategoriesGroupById(categoriesGroup);
		configService.updateCategoryUpdatedDate();
	}

	@Override
	public void updateCategoriesTeamById(CategoriesTeam categoriesTeam) {
		categoriesTeamGroupMapper.updateCategoriesTeamById(categoriesTeam);
		
		deleteCategoryTeamItem(categoriesTeam.getCategoryTeamId());
		
		if(categoriesTeam.getRelatedItemIds() != null) {
			for (int i = 0; i < categoriesTeam.getRelatedItemIds().length; i++) {
				CategoryTeamItem categoryTeamItem = new CategoryTeamItem();
				categoryTeamItem.setCategoryTeamItemId(sequenceService.getId("OP_CATEGORY_TEAM_ITEM"));
				categoryTeamItem.setCategoryTeamId(categoriesTeam.getCategoryTeamId());
				categoryTeamItem.setItemId(Integer.parseInt(categoriesTeam.getRelatedItemIds()[i]));
				insertCategoryTeamItem(categoryTeamItem);
			}
		}
		configService.updateCategoryUpdatedDate();
	}

	@Override
	public void deleteCategoryTeam(CategoriesTeam categoriesTeam) {
		categoriesTeamGroupMapper.deleteCategoryTeam(categoriesTeam);
		configService.updateCategoryUpdatedDate();
	}

	@Override
	public void deleteCategoryGorup(CategoriesGroup categoriesGroup) {
		categoriesTeamGroupMapper.deleteCategoryGorup(categoriesGroup);
		configService.updateCategoryUpdatedDate();
	}

	@Override
	public void insertCategoryTeamItem(CategoryTeamItem categoryTeamItem) {
		categoriesTeamGroupMapper.insertCategoryTeamItem(categoryTeamItem);
	}

	@Override
	public void deleteCategoryTeamItem(int categoryTeamId) {
		categoriesTeamGroupMapper.deleteCategoryTeamItem(categoryTeamId);
		configService.updateCategoryUpdatedDate();
	}

	@Override
	public List<ItemCategoryTeam> getCategoryTeamItemListByParam(
			CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam) {
		return categoriesTeamGroupMapper.getCategoryTeamItemListByParam(categoriesTeamGroupSearchParam);
	}

	@Override
	public List<CategoriesGroup> getCategoriesGroupListParam(CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam) {
		return categoriesTeamGroupMapper.getCategoriesGroupListParam(categoriesTeamGroupSearchParam);
	}

	@Override
	public int getMaxCategoryLevelByGroupId(int categoryGroupId) {
		return categoriesTeamGroupMapper.getMaxCategoryLevelByGroupId(categoryGroupId);
	}

	@Override
	public void updateGroupToCategory(CategoriesTeamGroupSearchParam categoriesTeamGroupParam) {

		log.debug("updateGroupToCategory");


		// 1. 1차 카테고리 등록 
		// 1-1. 그룹 정보 조회.
		CategoriesGroup categoryGroup = categoriesTeamGroupMapper.getCategoriesGroupById(categoriesTeamGroupParam);
		
		// 1-2. 비어있는 CATEGORY_CLASS 로 신규 CategoryCode 생성.
		CategoriesSearchParam categoryParam = new CategoriesSearchParam();
		categoryParam.setCategoryLevel("1");
		
		List<String> categoryClassList = categoriesMapper.getCategoryClassListBy(categoryParam);
				
		String newCategoryClass = "";
		for (int i = 100; i < 999; i++) {
			newCategoryClass = "00" + i;
			
			newCategoryClass = newCategoryClass.substring(newCategoryClass.length() - 3, newCategoryClass.length());
			
			boolean isMatched = false;
			for (String categoryClass : categoryClassList) {
				if (categoryClass.equals(newCategoryClass)) {
					isMatched = true;
					break;
				}
			}
			
			if (!isMatched) {
				break;
			}
		}
		
		String categoryCode = newCategoryClass + "000000000";
		
		// 1-3. 신규 ordering
		Categories param = new Categories();
		param.setCategoryLevel("1");
		int newOrdering = categoriesMapper.getCategoryLevelMaxOrdring(param);
		
		// 1-4. 1차 카테고리 등록
		Categories category = new Categories(categoryGroup);
		category.setCategoryId(sequenceService.getId("OP_CATEGORY"));
		category.setCategoryCode(categoryCode);
		category.setCategoryGroupId(Integer.parseInt(categoriesTeamGroupParam.getTargetCategoryGroupId()));		
		category.setCategoryType("1");
		category.setCategoryClass1(newCategoryClass);
		category.setCategoryClass2("000");
		category.setCategoryClass3("000");
		category.setCategoryClass4("000");
		category.setCategoryLevel("1");
		category.setAccessType(categoryGroup.getAccessType());
		category.setOrdering(newOrdering);
		
		categoriesMapper.insertCategory(category);
		
		
		
		// 2. 하위 카테고리 정보 변경. (categoryCode, categoryClass, Level) 1->2차, 2->3차, 3->4차, 레벨 + 1
		param.setCategoryClass1(newCategoryClass);
		param.setCategoryGroupId(Integer.parseInt(categoriesTeamGroupParam.getCategoryGroupId()));
		categoriesMapper.updateSubCategoriesForGroupToCategory(param);
		
		
		// 3. group 정보 삭제.
		categoriesTeamGroupMapper.deleteCategoryGorupById(Integer.parseInt(categoriesTeamGroupParam.getCategoryGroupId()));

		configService.updateCategoryUpdatedDate();

	}

	@Override
	public void updateCategoriesTeamOrdering(TeamGroupListParam listParam) {
		
		int i = 0;
		for (String id : listParam.getId()) {
			CategoriesTeam categoriesTeam = new CategoriesTeam();
			
			categoriesTeam.setCategoryTeamId(Integer.parseInt(id));
			categoriesTeam.setOrdering(Integer.parseInt(listParam.getOrdering()[i]));

			configService.updateCategoryUpdatedDate();
			categoriesTeamGroupMapper.updateCategoriesTeamOrdering(categoriesTeam);
			
			i++;
		}
		
	}

	@Override
	public void updateCategoriesGroupOrdering(TeamGroupListParam listParam) {
		int i = 0;
		for (String id : listParam.getId()) {
			CategoriesGroup categoriesGroup = new CategoriesGroup();
			
			categoriesGroup.setCategoryGroupId(Integer.parseInt(id));
			categoriesGroup.setOrdering(Integer.parseInt(listParam.getOrdering()[i]));

			configService.updateCategoryUpdatedDate();
			categoriesTeamGroupMapper.updateCategoriesGroupOrdering(categoriesGroup);
			
			i++;
		}
		
		
	}

	@Override
	public void updateBestItemDisplayType(String code, String bestItemDisplayType) {
		CategoriesTeam categoriesTeam = new CategoriesTeam();
		categoriesTeam.setCode(code);
		categoriesTeam.setBestItemDisplayType(bestItemDisplayType);
		
		categoriesTeamGroupMapper.updateBestItemDisplayType(categoriesTeam);
	
		
	}
	
	@Override
	public void updateCategoriesGroupItemListById(CategoriesGroup categoriesGroupt) {
		categoriesTeamGroupMapper.updateCategoriesGroupItemListById(categoriesGroupt);
		configService.updateCategoryUpdatedDate();
	}
}
