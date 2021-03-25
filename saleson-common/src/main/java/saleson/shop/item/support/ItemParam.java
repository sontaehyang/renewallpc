package saleson.shop.item.support;

import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.web.domain.SearchParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.ShopUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class ItemParam extends SearchParam implements Cloneable {
	private static final Logger log = LoggerFactory.getLogger(ItemParam.class);

	@Override
    public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	private String templateId;
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	private int perPages = 10;
	private String categoryCode;
	private String targetId;

	private int itemId;
	private String itemUserCode;
	private long sellerId; 	// 판매자 아이디
	
	private String type;
	private String itemType;
	private String itemDataType;  // 1: 일반상품, 2: 추가구성상품.
	
	// 카테고리 검색
	private int categoryGroupId;
	private String categoryClass;
	private String categoryClass1;
	private String categoryClass2;
	private String categoryClass3;
	private String categoryClass4;
	private String isAdult;
	
	// 결과 내 재 검색어.
	private String addWhere;
	private String addQuery;
	
	// 카테고리별(팀, 그룹, 카테고리) 상품 조회 시
	private String categoryTeamCode = "";
	private String categoryGroupCode = "";
	private String categoryUrl = "";
	
	private String categoryId = "";
	
	//LCH-itemParam 신상품 -코드값  <추가>

	private String code =""; 					// 카테고리 코드
	
	private String categoryString = "";		// 카테고리 선택 리스트 코드 스트링
	
	// 검색 시작일 
	private String searchStartDate;
	
	// 검색 종료일 
	private String searchEndDate;
	
	// 가격
	private String priceRange;
	
	// 품절여부 
	private String soldOut = "";
	
	// 공개여부 
	private String displayFlag = "";
	
	// 랭킹노출 여부
	private String rankingFlag = "";
	
	// 신상품 상단 고정 노출
	private String displayNewItemListTop;
	
	// 데이터 상태여부
	private String dataStatusCode;
	
	// 색상
	private String color;

	// EP 여부
	private String naverShoppingFlag = "";
	
	// 목록 형태 
	private String listType;
	
	// 배송타입
	private String deliveryType;
	
	// MD명
	private String mdName;
	
	// 과세구분
	private String taxType;
	
	private long userId;
	
	private String team = "";
	private String noIndexDisplayFlag = "";

	// 스팟 상품 상태 (판매중, 판매대기)
	private String spotStatus;
	private String spotWeekDay = "0123456";		//사용자 입력된 스팟 요일
	private List<String> spotWeekDays = new ArrayList<>();			//디비 패턴매칭 시 사용하는 spotWeekDay를 요일마다 잘라 삽입한 리스트
	private String spotApplyGroup = "All";
	private List<String> spotApplyGroups;
	private int spotDiscountAmount;
	
	// 리뷰 노출여부
	private String reviewDisplayFlag = "";
	private String recommendFlag = "";		// 리뷰 추천 여부.
	private int reviewScore;

	// 옵션 사용여부
	private String itemOptionFlag = "";

	private String cate00team ="";

	// 상품 라벨값
	private String itemLabelValue;
	private String newItemLabelValue;

	// 페이징 여부
	private boolean isPaging;

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean paging) {
		isPaging = paging;
	}
	
	private String todayItemIds;
	public List<Integer> getTodayIdArray() {

		try {
			String[] tmp = StringUtils.delimitedListToStringArray(todayItemIds, ",");
			if (tmp.length == 0) {
				return null;
			}
			
			List<Integer> array = new ArrayList<>();
			for(String s : tmp) {
				array.add(Integer.parseInt(s));
			}
			
			return array;
		} catch(Exception e) {
			log.error("getTodayIdArray() : {}", e.getMessage(), e);
		}
		
		return null;
		
	}
	
	// groupCategoryClassCodes
	private List<String> groupCategoryClassCodes;

	private int vendorId;
	
	// 엑셀 다운로드 요청 파라미터..
	private String[] excelDownloadData;		// 엑셀 다운로드 데이터 (ITEM, ITEM_OPTION, ITEM_IMAGE...)
	private String excelItemUserCode;		// 다운로드 할 상품코드 (개행 구분)
	
	private String brand;
	private int brandId;
	private String brandString = "";
	
	// HSP 프론트 상품 노출 조건.
	private List<String> pdLcGubnCodes = new ArrayList<>();		// 자격상품 조건
	private List<String> pdExGubnCodes = new ArrayList<>();		// 전용상품 조건

	// HSP 판매상태 구분 
	private String pdRtGubn = ""; 
	private String pdFrGubn = ""; // 대표구분
	private String pdOpCode = ""; // 대표상품만 보기 조회용 '00' 대표상품 
	
	// 전용상품 구분
	private List<String> privateTypes;
	private String privateType; 		// 전용상품
	
	private int recordCntPerPage = 20;
	
	private String include = "";
	
	private String shippingType = "";
	private String shippingGroupCode = "";

	private String pdDlGubn = "";
	
	private List<String> includes;
	
	private String saleStatus = "";	// 판매상태..
	
	private String excludeItems = ""; // 검색 제외상품
	
	// 반품/교환 구분
	private String shipmentReturnType;
	
	//LCH-ItemParam <추가>
	private String spotType;

	// 필터 코드 IDS
	private String fcIds;
	private String[] filterCodeIds;

	// 가격대 검색
	private int minPrice;	// 최소값
	private int maxPrice;	// 최대값

	private boolean historyPage;

	// 상품 리뷰 필터로 조회
	private boolean reviewFilterFlag;

	private String additionItemFlag;

	// 상품 코드 list
	private List<String> itemUserCodes;

	public String getAdditionItemFlag() {
		return additionItemFlag;
	}

	public void setAdditionItemFlag(String additionItemFlag) {
		this.additionItemFlag = additionItemFlag;
	}

	public String getFcIds() {
		return fcIds;
	}

	public void setFcIds(String fcIds) {
		this.fcIds = fcIds;
	}

	public String[] getFilterCodeIds() {
		return filterCodeIds;
	}

	public void setFilterCodeIds(String[] filterCodeIds) {
		this.filterCodeIds = filterCodeIds;
	}

	public String getSpotType() {
		return spotType;
	}

	public void setSpotType(String spotType) {
		this.spotType = spotType;
	}

	public int getPerPages() {
		return perPages;
	}

	public void setPerPages(int perPages) {
		this.perPages = perPages;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getIsAdult() {
		return isAdult;
	}

	public void setIsAdult(String isAdult) {
		this.isAdult = isAdult;
	}
	
	public String getAddWhere() {
		return addWhere;
	}

	public void setAddWhere(String addWhere) {
		this.addWhere = addWhere;
	}

	public String getAddQuery() {
		return addQuery;
	}

	public void setAddQuery(String addQuery) {
		this.addQuery = addQuery;
	}

	public String getPdRtGubn() {
		return pdRtGubn;
	}

	public void setPdRtGubn(String pdRtGubn) {
		this.pdRtGubn = pdRtGubn;
	}
	
	public String getPdFrGubn() {
		return pdFrGubn;
	}

	public void setPdFrGubn(String pdFrGubn) {
		this.pdFrGubn = pdFrGubn;
	}
	
	public String getPdOpCode() {
		return pdOpCode;
	}

	public void setPdOpCode(String pdOpCode) {
		this.pdOpCode = pdOpCode;
	}

	public List<String> getPdExGubnCodes() {
		return pdExGubnCodes;
	}

	public void setPdExGubnCodes(List<String> pdExGubnCodes) {
		this.pdExGubnCodes = pdExGubnCodes;
	}

	public List<String> getPdLcGubnCodes() {
		return pdLcGubnCodes;
	}

	public void setPdLcGubnCodes(List<String> pdLcGubnCodes) {
		this.pdLcGubnCodes = pdLcGubnCodes;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getBrandString() {
		return brandString;
	}

	public void setBrandString(String brandString) {
		this.brandString = brandString;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 엑셀 다운로드 시 textarea에 상품코드를 입력해서 다운로드 받을 경우 
	 * @return
	 */
	public List<String> getExcelItemUserCodes() {
		List<String> excelItemUserCodes = new ArrayList<>();
		if (excelItemUserCode != null && !excelItemUserCode.trim().equals("")) {
			String[] itemUserCodes = StringUtils.delimitedListToStringArray(excelItemUserCode, "\r\n");
			
			for (String itemUserCode : itemUserCodes) {
				if (!itemUserCode.trim().equals("")) {
					excelItemUserCodes.add(itemUserCode);
				}
			}
		}
		
		return excelItemUserCodes;
	}

	public String[] getExcelDownloadData() {
		return CommonUtils.copy(excelDownloadData);
	}

	public void setExcelDownloadData(String[] excelDownloadData) {
		this.excelDownloadData = CommonUtils.copy(excelDownloadData);
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	public String getItemDataType() {
		return itemDataType;
	}

	public void setItemDataType(String itemDataType) {
		this.itemDataType = itemDataType;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	

	public String getSpotStatus() {
		return spotStatus;
	}

	public void setSpotStatus(String spotStatus) {
		this.spotStatus = spotStatus;
	}

	public String getSpotWeekDay() {
		return spotWeekDay;
	}

	public void setSpotWeekDay(String spotWeekDay) {
		this.spotWeekDay = spotWeekDay;
	}
	
	public List<String> getSpotWeekDays() {
		return spotWeekDays;
	}

	public void setSpotWeekDays(List<String> spotWeekDays) {
		this.spotWeekDays = spotWeekDays;
	}

	public List<Code> getSpotWeekDayList() {
		return ShopUtils.getDayOfWeekList(this.spotWeekDay);
	}

	public String getSpotApplyGroup() {
		return spotApplyGroup;
	}

	public void setSpotApplyGroup(String spotApplyGroup) {
		this.spotApplyGroup = spotApplyGroup;
	}
	
	public List<String> getSpotApplyGroups() {
		return spotApplyGroups;
	}
	
	public void setSpotApplyGroups(List<String> spotApplyGroups) {
		this.spotApplyGroups = spotApplyGroups;
	}

	public int getSpotDiscountAmount() {
		return spotDiscountAmount;
	}

	public void setSpotDiscountAmount(int spotDiscountAmount) {
		this.spotDiscountAmount = spotDiscountAmount;
	}

	public String getNoIndexDisplayFlag() {
		return noIndexDisplayFlag;
	}

	public void setNoIndexDisplayFlag(String noIndexDisplayFlag) {
		this.noIndexDisplayFlag = noIndexDisplayFlag;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getExcelItemUserCode() {
		return excelItemUserCode;
	}

	public void setExcelItemUserCode(String excelItemUserCode) {
		this.excelItemUserCode = excelItemUserCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public int getCategoryGroupId() {
		return categoryGroupId;
	}

	public void setCategoryGroupId(int categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}

	public String getCategoryClass() {
		if (this.categoryClass != null) {
			return this.categoryClass;
		}
		if (this.categoryClass4 != null && !this.categoryClass4.equals("")) {
			return this.categoryClass4;
			
		} else if (this.categoryClass3 != null && !this.categoryClass3.equals("")) {
			return this.categoryClass3;
			
		} else if (this.categoryClass2 != null && !this.categoryClass2.equals("")) {
			return this.categoryClass2;
			
		} else if (this.categoryClass1 != null && !this.categoryClass1.equals("")) {
			return this.categoryClass1;
		} 
		return "";
	}

	public void setCategoryClass(String categoryClass) {
		this.categoryClass = categoryClass;
	}

	public String getCategoryClass1() {
		return categoryClass1;
	}

	public void setCategoryClass1(String categoryClass1) {
		this.categoryClass1 = categoryClass1;
	}

	public String getCategoryClass2() {
		return categoryClass2;
	}

	public void setCategoryClass2(String categoryClass2) {
		this.categoryClass2 = categoryClass2;
	}

	public String getCategoryClass3() {
		return categoryClass3;
	}

	public void setCategoryClass3(String categoryClass3) {
		this.categoryClass3 = categoryClass3;
	}

	public String getCategoryClass4() {
		return categoryClass4;
	}

	public void setCategoryClass4(String categoryClass4) {
		this.categoryClass4 = categoryClass4;
	}

	public List<String> getGroupCategoryClassCodes() {
		return groupCategoryClassCodes;
	}

	public void setGroupCategoryClassCodes(List<String> groupCategoryClassCodes) {
		this.groupCategoryClassCodes = groupCategoryClassCodes;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	
	public String getPriceStart() {
		if (this.priceRange != null && !this.priceRange.equals("")) {
			return StringUtils.delimitedListToStringArray(this.priceRange, "|")[0];
		}
		return "";
	}
	
	public String getPriceEnd() {
		if (this.priceRange != null && !this.priceRange.equals("") && this.priceRange.indexOf("|") > -1) {
			return StringUtils.delimitedListToStringArray(this.priceRange, "|")[1];
		}
		return "";
	}

	public String getDisplayNewItemListTop() {
		return displayNewItemListTop;
	}

	public void setDisplayNewItemListTop(String displayNewItemListTop) {
		this.displayNewItemListTop = displayNewItemListTop;
	}

	public String getSoldOut() {
		return soldOut;
	}

	public void setSoldOut(String soldOutFlag) {
		this.soldOut = soldOutFlag;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public String getRankingFlag() {
		return rankingFlag;
	}

	public void setRankingFlag(String rankingFlag) {
		this.rankingFlag = rankingFlag;
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryString() {
		return categoryString;
	}

	public void setCategoryString(String categoryString) {
		this.categoryString = categoryString;
	}

	public String getDataStatusCode() {
		return dataStatusCode;
	}

	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public String getItemUserCode() {
		return itemUserCode;
	}

	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}

	public String getReviewDisplayFlag() {
		return reviewDisplayFlag;
	}

	public void setReviewDisplayFlag(String reviewDisplayFlag) {
		this.reviewDisplayFlag = reviewDisplayFlag;
	}

	public String getRecommendFlag() {
		return recommendFlag;
	}

	public void setRecommendFlag(String recommendFlag) {
		this.recommendFlag = recommendFlag;
	}

	public String getCategoryTeamCode() {
		return categoryTeamCode;
	}

	public void setCategoryTeamCode(String categoryTeamCode) {
		this.categoryTeamCode = categoryTeamCode;
	}

	public String getCategoryGroupCode() {
		return categoryGroupCode;
	}

	public void setCategoryGroupCode(String categoryGroupCode) {
		this.categoryGroupCode = categoryGroupCode;
	}

	public String getCategoryUrl() {
		return categoryUrl;
	}

	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}

	public int getReviewScore() {
		return reviewScore;
	}

	public void setReviewScore(int reviewScore) {
		this.reviewScore = reviewScore;
	}

	public String getCate00team() {
		return cate00team;
	}

	public void setCate00team(String cate00team) {
		this.cate00team = cate00team;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTodayItemIds() {
		return todayItemIds;
	}

	public void setTodayItemIds(String todayItemIds) {
		this.todayItemIds = todayItemIds;
	}

	public int getRecordCntPerPage() {
		return recordCntPerPage;
	}

	public void setRecordCntPerPage(int recordCntPerPage) {
		this.recordCntPerPage = recordCntPerPage;
	}
	
	public List<String> getPrivateTypes() {
		return privateTypes;
	}

	public void setPrivateTypes(List<String> privateTypes) {
		this.privateTypes = privateTypes;
	}

	
	public String getPrivateType() {
		return privateType;
	}

	public void setPrivateType(String privateType) {
		this.privateType = privateType;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.includes = Arrays.asList(include.split(" "));
		this.include = include;
	}

	public List<String> getIncludes() {
		return includes;
	}

	public void setIncludes(List<String> includes) {
		this.includes = includes;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getShippingGroupCode() {
		return shippingGroupCode;
	}

	public void setShippingGroupCode(String shippingGroupCode) {
		this.shippingGroupCode = shippingGroupCode;
	}
	

	public String getPdDlGubn() {
		return pdDlGubn;
	}

	public void setPdDlGubn(String pdDlGubn) {
		this.pdDlGubn = pdDlGubn;
	}

	public String getExcludeItems() {
		return excludeItems;
	}

	public void setExcludeItems(String excludeItems) {
		this.excludeItems = excludeItems;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getMdName() {
		return mdName;
	}

	public void setMdName(String mdName) {
		this.mdName = mdName;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getShipmentReturnType() {
		return shipmentReturnType;
	}

	public void setShipmentReturnType(String shipmentReturnType) {
		this.shipmentReturnType = shipmentReturnType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private long styleBookId;

	public long getStyleBookId() {
		return styleBookId;
	}

	public void setStyleBookId(long styleBookId) {
		this.styleBookId = styleBookId;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public boolean isHistoryPage() {
		return historyPage;
	}

	public void setHistoryPage(boolean historyPage) {
		this.historyPage = historyPage;
	}

	public String getNaverShoppingFlag() {
		return naverShoppingFlag;
	}

	public void setNaverShoppingFlag(String naverShoppingFlag) {
		this.naverShoppingFlag = naverShoppingFlag;
	}

	public boolean isReviewFilterFlag() {
		return reviewFilterFlag;
	}

	public void setReviewFilterFlag(boolean reviewFilterFlag) {
		this.reviewFilterFlag = reviewFilterFlag;
	}

	public String getItemOptionFlag() {
		return itemOptionFlag;
	}

	public void setItemOptionFlag(String itemOptionFlag) {
		this.itemOptionFlag = itemOptionFlag;
	}

	public String getItemLabelValue() {
		return itemLabelValue;
	}

	public void setItemLabelValue(String itemLabelValue) {
		this.itemLabelValue = itemLabelValue;
	}

	public String getNewItemLabelValue() {
		return newItemLabelValue;
	}

	public void setNewItemLabelValue(String newItemLabelValue) {
		this.newItemLabelValue = newItemLabelValue;
	}

	public List<String> getItemUserCodes() {
		return itemUserCodes;
	}

	public void setItemUserCodes(List<String> itemUserCodes) {
		this.itemUserCodes = itemUserCodes;
	}
}
