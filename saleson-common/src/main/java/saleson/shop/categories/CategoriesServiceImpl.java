package saleson.shop.categories;

import com.onlinepowers.framework.exception.BusinessException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import saleson.shop.categories.domain.*;
import saleson.shop.categories.support.CategoriesSearchParam;
import saleson.shop.categoriesfilter.CategoriesFilterService;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.config.ConfigService;
import saleson.shop.item.ItemMapper;
import saleson.shop.item.domain.SalePriceInfo;
import saleson.shop.item.support.ItemParam;
import saleson.shop.reviewfilter.ReviewFilterService;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service("categoriesService")
public class CategoriesServiceImpl implements CategoriesService {

	@Autowired
	CategoriesMapper categoriesMapper;
	
	@Autowired
	CategoriesMapperBatch categoriesMapperBatch;
	
	@Autowired
	SequenceService sequenceService;

	@Autowired
	CategoriesFilterService categoriesFilterService;

	@Autowired
	ReviewFilterService reviewFilterService;

	@Autowired
	ItemMapper itemMapper;

	@Autowired
	ConfigService configService;

	@Override
	public List<Categories> getCategoiesListByLevel1(CategoriesSearchParam categoriesSearchParam) {
		return categoriesMapper.getCategoiesListByLevel1(categoriesSearchParam);
	}
	
	@Override
	public List<HashMap<String,Object>> getCategoiesTreeListByLevel1(CategoriesSearchParam categoriesSearchParam) {
		
		List<Categories> categoriesList = getCategoiesListByLevel1(categoriesSearchParam);
		List<HashMap<String,Object>> categoriesTreeList = new ArrayList<>();
		
		
		for (Categories categories : categoriesList) {
			
			HashMap<String,Object> categoriesObject = new HashMap<>();
			String categoryId = categories.getCategoryClass1() + "-1" + "-" + categories.getOrdering() + "-" + categories.getChildMaxLevel() + "-" + categories.getCategoryGroupId();;
			
			categoriesObject.put("id", categoryId);
			categoriesObject.put("text", categories.getCategoryName() + " <span>[" + categories.getCategoryUrl() + "]</span>");
			categoriesObject.put("type", "root");
			categoriesObject.put("icon", categories.getCategoryFlag().equals("Y") ? "/content/opmanager/images/category/icon_folder.png" : "/content/opmanager/images/category/icon_delete.png");
			categoriesObject.put("children", categories.getCategoryCount() == 1 || categories.getCategoryType().equals("2") ? false : true );
			categoriesTreeList.add(categoriesObject);
		}
		
		return categoriesTreeList;
	}

	@Override
	public List<HashMap<String,Object>> getCategoiesTreeListByLevels(CategoriesSearchParam categoriesSearchParam) {
		
		List<Categories> categoriesList =  new ArrayList<Categories>();
		
		String categoryClass1 = categoriesSearchParam.getCategoryCode().split("-")[0];
		int categorylevel = Integer.parseInt(categoriesSearchParam.getCategoryCode().split("-")[1])+1;
		
		if( categorylevel == 2 ){
			
			categoriesSearchParam.setCategoryClass1(categoryClass1);
			categoriesSearchParam.setCategoryLevel(""+categorylevel);
			categoriesList = categoriesMapper.getCategoiesListByLevels(categoriesSearchParam);
			
		}else if(categorylevel == 3) {
			
			categoriesSearchParam.setCategoryClass1(categoryClass1.substring(0,3));
			categoriesSearchParam.setCategoryClass2(categoryClass1.substring(3,6));
			categoriesSearchParam.setCategoryLevel(""+categorylevel);
			categoriesList = categoriesMapper.getCategoiesListByLevels(categoriesSearchParam);
			
		} else if(categorylevel == 4){
			
			categoriesSearchParam.setCategoryClass1(categoryClass1.substring(0,3));
			categoriesSearchParam.setCategoryClass2(categoryClass1.substring(3,6));
			categoriesSearchParam.setCategoryClass3(categoryClass1.substring(6,9));
			categoriesSearchParam.setCategoryLevel(""+categorylevel);
			categoriesList = categoriesMapper.getCategoiesListByLevels(categoriesSearchParam);
			
		}
		
		List<HashMap<String,Object>> categoriesTreeList = new ArrayList<>();
		
		for (Categories categories : categoriesList) {
			
			HashMap<String,Object> categoriesObject = new HashMap<>();
			String categoryId = "";
			if(categorylevel == 2){
				categoryId = categories.getCategoryClass1()+categories.getCategoryClass2()+"-"+categorylevel;
				
			} else if(categorylevel == 3){
				categoryId = categories.getCategoryClass1()+categories.getCategoryClass2()+categories.getCategoryClass3()+"-"+categorylevel;
				
			} else if(categorylevel == 4){
				categoryId = categories.getCategoryClass1()+categories.getCategoryClass2()+categories.getCategoryClass3()+categories.getCategoryClass4()+"-"+categorylevel;
			
			}
			
			categoryId = categoryId + "-" + categories.getOrdering() + "-" + categories.getChildMaxLevel() + "-" + categories.getCategoryGroupId();
			categoriesObject.put("id", categoryId);
			categoriesObject.put("text", categories.getCategoryName() + " <span>[" + categories.getCategoryUrl() + "]</span>");
			categoriesObject.put("icon", categories.getCategoryFlag().equals("Y") ? "/content/opmanager/images/category/icon_folder.png" : "/content/opmanager/images/category/icon_delete.png");
			categoriesObject.put("children", categories.getCategoryCount() == 1 ? false : true );
			categoriesTreeList.add(categoriesObject);
		}
		
		return categoriesTreeList;
	}

	@Override
	public List<CategoriesGroup> getGroupsInCategoriesList() {
		return categoriesMapper.getGroupsInCategoriesList();
	}

	@Override
	public List<Categories> getCategoriesListById(
			CategoriesSearchParam categoriesSearchParam) {
		return categoriesMapper.getCategoriesListById(categoriesSearchParam);
	}

	@Override
	public void updateCategoriesByParam(
			CategoriesSearchParam categoriesSearchParam) {
		if( categoriesSearchParam.getCategoryCodes() != null ){
			for(int i = 0; i < categoriesSearchParam.getCategoryCodes().length; i++){
				System.out.println(categoriesSearchParam.getCategoryCodes()[i]);
				categoriesSearchParam.setCategoryCode(categoriesSearchParam.getCategoryCodes()[i]);
				categoriesMapper.updateCategoriesByParam(categoriesSearchParam);
			}

			configService.updateCategoryUpdatedDate();
		}
	}
	
	@Override
	public List<Categories> getChildCategoriesFromParantCategoryClass(
			String categoryClass) {
		return categoriesMapper.getChildCategoriesFromParantCategoryClass(categoryClass);
	}
	

	@Override
	public List<Breadcrumb> getBreadcrumbListByCategoryId(int categoryId) {
		return categoriesMapper.getBreadcrumbListByCategoryId(categoryId);
	}

	@Override
	public List<Breadcrumb> getBreadcrumbListByCategoryUrl(String categoryUrl) {
		return categoriesMapper.getBreadcrumbListByCategoryUrl(categoryUrl);
	}

	@Override
	public Categories getCategoryByCategoryCode(String categoryCode) {
		return categoriesMapper.getCategoryByCategoryCode(categoryCode);
	}

	
	
	@Override
	public void insertCategory(Categories categories) {
		String categoryCode = categoriesMapper.getCategoryMaxClassByCategoryLevel(categories);

		if (categoryCode == null) {
			categoryCode = "100000000000";
		}

		categories.setCategoryId(sequenceService.getId("OP_CATEGORY"));
		categories.setCategoryCode(categoryCode);
		categories.setOrdering(categoriesMapper.getCategoryLevelMaxOrdring(categories));
		getCategoryClassAndCode(categories);

		categoriesMapper.insertCategory(categories);

		saveCategoriesFilter(categories);
		saveReviewFilter(categories);

		configService.updateCategoryUpdatedDate();
	}
	
	

	@Override
	public void insertCategorySub(Categories categories) {
		categories.setCategoryId(sequenceService.getId("OP_CATEGORY"));
		categories.setCategoryCode(categoriesMapper.getNewCategoryCodeByParent(categories));
		categories.setOrdering(categoriesMapper.getCategoryLevelMaxOrdring(categories));
		getCategoryClassAndCode(categories);
		
		categoriesMapper.insertCategory(categories);

		configService.updateCategoryUpdatedDate();
	}
	
	
	@Override
	public void updateCategory(Categories categories) {
		log.debug("updateCategory");
		
		categoriesMapper.updateCategory(categories);
		
		categories.setCategoryCode(getCategorySearchCode(categories.getCategoryCode(),Integer.parseInt(categories.getCategoryLevel())));
		
		// 하위 카테고리의 CATEGORY_GROUP_ID 일괄 수정
		categoriesMapper.updateCategoryGroupIdOfChild(categories);
		
		
		// 하위 카테고리 게시 상태를 모두 업데이트 
		// categoriesMapper.updateCategoryFalg(categories);
		saveCategoriesFilter(categories);
		saveReviewFilter(categories);

		configService.updateCategoryUpdatedDate();
	}

	public Categories getCategoryClassAndCode(Categories categories){
		
		for (int i = 0; i < 4; i++) {
			
			int j = i * 3;
			int z = (i+1) * 3;
			
			if(i == 0){
				categories.setCategoryClass1(categories.getCategoryCode().substring(j,z));
			} else if( i == 1){
				categories.setCategoryClass2(categories.getCategoryCode().substring(j,z));
			} else if( i == 2){
				categories.setCategoryClass3(categories.getCategoryCode().substring(j,z));
			} else if( i == 3){
				categories.setCategoryClass4(categories.getCategoryCode().substring(j,z));
			}
		}
		
		return categories;
	}
	
	public String getCategorySearchCode(String code, int level){
		code = code.substring(0,level*3);
		return code;
	}
	
	public String getCategoryCodeToAllCode(String code, int level){
		
		if(code.length() < 12){
			for (int i = 0; i < 4 - level; i++) {
				code += "000";
			}
		}
		
		return code;
		
	}

	@Override
	public void updateChangeCategory(CategoriesSearchParam categoriesSearchParam) {
		
		int newLevel = Integer.parseInt(categoriesSearchParam.getNewLevel());
	    int newLevel2 = Integer.parseInt(categoriesSearchParam.getNewLevel2());
		
		//4단계 검증 처리 
		Categories categoriesCheck = new Categories();
		
		categoriesCheck.setCategoryCode(getCategoryCodeToAllCode(categoriesSearchParam.getNewCode(),newLevel2));
		getCategoryClassAndCode(categoriesCheck);
		categoriesCheck.setCategoryLevel(categoriesSearchParam.getNewLevel2());
		
		int levelCount = Integer.parseInt(categoriesSearchParam.getParentLevel()) + categoriesMapper.getcategoryLevelsCount(categoriesCheck);
		
		if( levelCount > 4 ) {
			throw new BusinessException("카테고리 4단계 이상 만들수 없습니다.");
		}
		
		Categories categories = new Categories();
		Categories categoriesParent = new Categories();
		
		categories.setCategoryCode(getCategoryCodeToAllCode(categoriesSearchParam.getParentCode(),Integer.parseInt(categoriesSearchParam.getParentLevel())));
		categories.setCategoryLevel(categoriesSearchParam.getNewLevel());
		categories.setOrdering(categoriesSearchParam.getOrdering());
		getCategoryClassAndCode(categories);
		
		categories.setCategoryCode(getCategoryCodeToAllCode(categoriesSearchParam.getNewCode(),Integer.parseInt(categoriesSearchParam.getNewLevel2())));
		List<Categories> categoriesList = categoriesMapper.getCategoryOrderingDownList(categories);
		
		categories.setCategoryCode(categoriesMapper.getCategoryMaxClassByCategoryLevel(categories));
		getCategoryClassAndCode(categories);
		
		categories.setCategoryUrl(getCategoryCodeToAllCode(categoriesSearchParam.getNewCode(),Integer.parseInt(categoriesSearchParam.getNewLevel2())));
		categoriesParent.setCategoryCode(categoriesSearchParam.getNewCode());
		categoriesParent.setCategoryLevel(categoriesSearchParam.getNewLevel2());
		
		List<Categories> categoreisLowList = categoriesMapper.getCategoryLowList(categoriesParent);
		
		categoriesMapper.updateChangeCategory(categories);
		
		boolean lowUpdateFlag = true;
		String code = ""; 
		
		if(categoriesSearchParam.getNewLevel2().equals("1") || categoriesSearchParam.getNewLevel2().equals("4")){
			lowUpdateFlag = false;
		}
		
		System.out.println(categoriesSearchParam.getNewLevel2());
		
		if(lowUpdateFlag){
			for(Categories categories3 : categoreisLowList){
				categories3.setCategoryUrl(categories3.getCategoryCode());
				
				if(categoriesSearchParam.getNewLevel().equals("2")){
					
					code = categories.getCategoryClass1()+categories.getCategoryClass2();
					
					categories3.setCategoryCode(code+categories3.getCategoryClass3()+categories3.getCategoryClass4());
					categories3.setCategoryClass1(categories.getCategoryClass1());
					categories3.setCategoryClass2(categories.getCategoryClass2());
					
					if(newLevel < newLevel2){
						categories3.setCategoryCode(code+categories3.getCategoryClass4()+"000");
						categories3.setCategoryClass3(categories3.getCategoryClass4());
						categories3.setCategoryLevel("3");
						categories3.setCategoryClass4("000");
					} 
					
				}else if(categoriesSearchParam.getNewLevel().equals("3")){
					code = categories.getCategoryClass1()+categories.getCategoryClass2()+categories.getCategoryClass3();
					String class3 = categories3.getCategoryClass3();
					categories3.setCategoryCode(code+categories3.getCategoryClass4());
					categories3.setCategoryClass1(categories.getCategoryClass1());
					categories3.setCategoryClass2(categories.getCategoryClass2());
					categories3.setCategoryClass3(categories.getCategoryClass3());
					
					if(newLevel > newLevel2){
						categories3.setCategoryCode(code+class3);
						categories3.setCategoryLevel("4");
						categories3.setCategoryClass4(class3);
					}
					
				}
				
				categoriesMapper.updateChangeCategory(categories3);
			}
		}
		
		int ordering = categoriesSearchParam.getOrdering()+1;
		
		for (Categories categories2 : categoriesList) {
			categories2.setOrdering(ordering);
			categoriesMapper.updateOrderingCategory(categories2);
			ordering++;
		}

		configService.updateCategoryUpdatedDate();
	}

	@Override
	public void deleteCategoryByClass(Categories categories) {

		String categoryCode = categories.getCategoryCode();
		String categoryCodeBase = categoryCode.substring(0, Integer.parseInt(categories.getCategoryLevel()) * 3);
		categories.setCategoryCode(categoryCodeBase);

		// 필터 초기화
		Categories orgCategories = categoriesMapper.getCategoryByCategoryCode(categoryCode);
		List<Categories> orgChildCategories = categoriesMapper.getChildCategoriesFromParantCategoryClass(categoryCodeBase);
		categoriesFilterService.initCategoryFilter(orgCategories, orgChildCategories);

		// 해당 상품 카테고리 삭제
		itemMapper.deleteItemCategoryByCategoryClass(categoryCodeBase);


		// 카테고리 삭제
		categoriesMapper.deleteCategoryWithChilrenByCategoryCodeBase(categoryCodeBase);

		//

		//categoriesMapper.deleteCategoryByClass(categories);

		configService.updateCategoryUpdatedDate();
	}

	@Override
	public List<Categories> getCategoriesListByGroupCode(
			String categoryGroupCode) {
		return categoriesMapper.getCategoriesListByGroupCode(categoryGroupCode);
	}

	@Override
	@Cacheable(value="frontCategories")
	public List<Team> getCategoriesForFront() {
		return categoriesMapper.getCategoriesForFront();
	}

	@Override
	public List<Team> getCategoriesForApi() {
		return categoriesMapper.getCategoriesForFront();
	}

	@Override
	public String getCategoryTypeByCategoryCode(String categoryCode) {
		return categoriesMapper.getCategoryTypeByCategoryCode(categoryCode);
	}

	@Override
	public Categories getCategoryAndParentSiblingChildByCategoryUrl(
			String categoryCode) {
		return categoriesMapper.getCategoryAndParentSiblingChildByCategoryUrl(categoryCode);
	}
	
	
	
	@Override
	public Categories getCategoryByCategoryUrl(String categoryCode) {

		return categoriesMapper.getCategoryByCategoryUrl(categoryCode);
	}
	

	@Override
	public Categories getCategoryAndParentSiblingByCategoryUrl(
			String categoryCode) {
		return categoriesMapper.getCategoryAndParentSiblingByCategoryUrl(categoryCode);
	}

	@Override
	public Categories getCategoriesById(int categoryId) {
		return categoriesMapper.getCategoriesById(categoryId);
	}
	
	//LCH-CategoriesServiceImpl 스팟 세일 - 카테고리 1차 리스트  <추가>
	
	@Override
	public List<CategoriesGroup> getCategoriesGroupId(){
		return categoriesMapper.getCategoriesGroupId();
	}

	@Override
	public void updateCategoryEdit(Categories categories) {
		categoriesMapper.updateCategoryEdit(categories);

		configService.updateCategoryUpdatedDate();
	}

	@Override
	public Categories getCategoriesByParam(	CategoriesSearchParam categoriesSearchParam) {
		return categoriesMapper.getCategoriesByParam(categoriesSearchParam);
	}

	@Override
	public List<Team> getCategoryGroupRanking(String code) {
		return categoriesMapper.getCategoryGroupRanking(code);
	}

	@Override
	public int getCategoryCountByCode(
			CategoriesSearchParam categoriesSearchParam) {
		return categoriesMapper.getCategoryCountByCode(categoriesSearchParam);
	}

	@Override
	public void updateChangeOrderingCategory1InGroup(
			CategoriesSearchParam categoriesSearchParam) {
		
		
		// 이동할 대상 보다 큰놈은 ordering + 1  씩 해주자 
		// 리턴값으로 이동할 카테고리의 ordering 값을 반환하면 좋겠지? (selectKey)
		categoriesMapper.updateCategoryOrderingAfterIncreaseInGroup(categoriesSearchParam);
		
		
		// 대상 카테고리 순서 변경.
		categoriesSearchParam.setOrdering(categoriesSearchParam.getOrdering() + 1);
		categoriesMapper.updateOrderingCategory1FromJsTree(categoriesSearchParam);
		
		configService.updateCategoryUpdatedDate();
	}

	@Override
	public void updateCategoryPosition(
			CategoriesSearchParam categoriesSearchParam) {

		// 변경전 원 카테고리 경로 조회
		String orgCategoryCode = (categoriesSearchParam.getCurrentCode() + "000000000").substring(0, 12);
		Categories orgCategories = categoriesMapper.getCategoryByCategoryCode(orgCategoryCode);

		// 1. 이동될 카테고리의 자식 카테고리의 ORDERING 을 +1씩 업데이트
		int newCategoryLevel = Integer.parseInt(categoriesSearchParam.getParentLevel()) + 1;
		categoriesSearchParam.setCategoryLevel(Integer.toString(newCategoryLevel));
		
		categoriesMapper.updateCategoryOrderingFromJsTree(categoriesSearchParam);
		
		
		// 2. 이동할 카테고리의 정보를 업데이트
		int newOrdering = Integer.parseInt(categoriesSearchParam.getPreviousOrdering()) + 1;
		
		// 2-1. 비어있는 CATEGORY_CLASS 로 설정.
		List<String> categoryClassList = categoriesMapper.getCategoryClassListBy(categoriesSearchParam);
		
		int startIndex = newCategoryLevel == 1 ? 100 : 1;
		
		String newCategoryClass = "";
		for (int i = startIndex; i < 999; i++) {
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
		
		// 신규 categoryCode
		String newCategoryCodeBase = categoriesSearchParam.getParentCode() + newCategoryClass;
		String newCategoryCode = (newCategoryCodeBase + "000000000").substring(0, 12);
		String[] newCategoryClasses = getCategoryClasses(newCategoryCode);
		
		// current 카테고리 정보 업데이트.
		CategoriesSearchParam newCategory = new CategoriesSearchParam();
		
		newCategory.setCategoryCode(newCategoryCode);
		newCategory.setCategoryClass1(newCategoryClasses[0]);
		newCategory.setCategoryClass2(newCategoryClasses[1]);
		newCategory.setCategoryClass3(newCategoryClasses[2]);
		newCategory.setCategoryClass4(newCategoryClasses[3]);
		newCategory.setCategoryLevel(Integer.toString(newCategoryLevel));
		newCategory.setOrdering(newOrdering);
		
		if (newCategoryLevel == 1) {
			newCategory.setCategoryGroupId(categoriesSearchParam.getCategoryGroupId());
		} else {
			newCategory.setCategoryGroupId(categoriesSearchParam.getParentGroupId());
		}
		newCategory.setCurrentCode(categoriesSearchParam.getCurrentCode());
		newCategory.setCurrentLevel(categoriesSearchParam.getCurrentLevel());
		
		categoriesMapper.updateCurrentCategoryFromJsTree(newCategory);

		// 3. 현재 카테고리의 자식 카테고리 정보 수정
		// 3-1. 자식 카테고리 목록.
		List<Categories> orgChildCategories = categoriesMapper.getChildCategoriesFromJsTree(categoriesSearchParam);
		int differentCategoryLevel = newCategoryLevel - Integer.parseInt(categoriesSearchParam.getCurrentLevel());

		for (Categories category : orgChildCategories) {
			String oldCategoryCodeSuffix = category.getCategoryCode().substring(categoriesSearchParam.getCurrentCode().length(), 12);
			String newCateCode = (newCategoryCodeBase + oldCategoryCodeSuffix + "000000000").substring(0, 12);
			String[] newCateClasses = getCategoryClasses(newCateCode);
			int newCateLevel = Integer.parseInt(category.getCategoryLevel()) + differentCategoryLevel;
			
			category.setCategoryCode(newCateCode);
			category.setCategoryClass1(newCateClasses[0]);
			category.setCategoryClass2(newCateClasses[1]);
			category.setCategoryClass3(newCateClasses[2]);
			category.setCategoryClass4(newCateClasses[3]);
			category.setCategoryLevel(Integer.toString(newCateLevel));
			if (!StringUtils.isEmpty(newCategory.getCategoryGroupId())) {
				category.setCategoryGroupId(Integer.parseInt(newCategory.getCategoryGroupId()));
			}
			
			categoriesMapperBatch.updateChildCategoryFromJsTree(category);

		}

		categoriesFilterService.initCategoryFilter(orgCategories, orgChildCategories);

		configService.updateCategoryUpdatedDate();
	}

	private String[] getCategoryClasses(String categoryCode) {
		String[] result = new String[4];
		String cateCode = (categoryCode + "000000000").substring(0, 12);
		
		for (int i = 0; i < 4; i++) {
			result[i] = cateCode.substring(i * 3, (i + 1) * 3);
		}
		return result;
	}
	
	//현재 그룹의 전체 카테고리 가져옴.
	@Override
	public List<Categories> getCategoryByCategorygroupId(String categoryCode) {
		return categoriesMapper.getCategoryByCategorygroupId(categoryCode);
	}

	private void saveCategoriesFilter(Categories categories) {

		try {
			categoriesFilterService.saveCategoriesFilter(categories.getCategoryId(), categories.getFilterGroupIdSet());
		} catch (Exception e) {
			log.error("saveCategoriesFilter error {}", e.getMessage(), e);
		}

	}

	private void saveReviewFilter(Categories categories) {
		try {
			reviewFilterService.saveReviewFilter(categories.getCategoryId(), categories.getReviewFilterGroupIdSet());
		} catch (Exception e) {
			log.error("saveReviewFilter error {}", e.getMessage(), e);
		}
	}

	@Override
	public List<PriceArea> getPriceAreaListById(int categoryId) {

		List<PriceArea> list = new ArrayList<>();
		Categories categories = categoriesMapper.getCategoriesById(categoryId);

		if (categories != null) {

			String categoryClass = getCategorySearchCode(categories.getCategoryCode(),Integer.parseInt(categories.getCategoryLevel()));

			ItemParam itemParam = new ItemParam();
			itemParam.setCategoryClass(categoryClass);

			SalePriceInfo priceInfo = itemMapper.getSalePriceInfoByCategoryClass(itemParam);

			Set<Integer> priceSet = new HashSet<>();

			setPriceSet(priceSet, priceInfo.getMinPrice(), priceInfo.getAveragePrice());
			setPriceSet(priceSet, priceInfo.getMaxPrice(), priceInfo.getAveragePrice());

			list = getPriceAreaList(priceSet);
		}
		return list;
	}

	private List<PriceArea> getPriceAreaList(Set<Integer> priceSet) {

		List<PriceArea> list = new ArrayList<>();
		List<Integer> priceList = new ArrayList<>();
		priceSet.stream().sorted(Comparator.naturalOrder()).forEach(price -> {
			priceList.add(price);
		});

		int maxIndex = priceList.size() -1;
		for (int index = 0; index < priceList.size(); index++) {

			if (index == 0) {
				list.add(new PriceArea(0, priceList.get(index)));
				continue;
			}

			int beginValue = priceList.get(index -1);
			int endValue = priceList.get(index);

			list.add(new PriceArea(beginValue, endValue));

			if (index == maxIndex) {
				list.add(new PriceArea(priceList.get(index), 0));
				continue;
			}
		}

		return list;
	}

	private void setPriceSet(Set<Integer> priceSet, int value1, int value2) {

		if (priceSet == null) {
			priceSet = new HashSet<>();
		}

		value1 = getScaleValue(value1);
		value2 = getScaleValue(value2);

		int medianPrice = getMedian(value1, value2);
		priceSet.add(value1);
		priceSet.add(value2);
		priceSet.add(medianPrice);
		priceSet.add(getMedian(value1, medianPrice));
		priceSet.add(getMedian(value2, medianPrice));
	}

	private int getScaleValue(int value) {

		try {
			int initValue = 10000;
			int returnValue = 0;

			BigDecimal bigDecimal = new BigDecimal(value);
			bigDecimal = bigDecimal.setScale(-4, BigDecimal.ROUND_DOWN);

			returnValue = bigDecimal.intValue();

			if (returnValue <= 0) {
				returnValue = initValue;
			}

			return returnValue;
		} catch (Exception ignore) {

		}

		return value;
	}

	private int getMedian(int value1, int value2) {

		try {
			int value = getScaleValue(value1) + getScaleValue(value2);

			if (value > 0) {
				return getScaleValue(value / 2);
			}

		} catch (Exception ignore) {}

		return 0;
	}
}
