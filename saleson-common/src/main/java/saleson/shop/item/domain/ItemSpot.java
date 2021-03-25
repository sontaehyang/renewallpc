package saleson.shop.item.domain;

import java.util.ArrayList;
import java.util.List;
import saleson.common.utils.CommonUtils;

public class ItemSpot {

	private String[] id;
	private String discountType;
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String[] getId() {
		return CommonUtils.copy(id);
	}
	public void setId(String[] id) {
		this.id = CommonUtils.copy(id);
	}
	
	private String[] spotItemIds;
	private String[] spotDiscountAmounts;
	
	//일괄 할인 
	private int discountAmount;
	private String spotStatus;
	private String searchStartDate;
	private String searchEndDate;
	private String query;
	private String spotApplyGroup;
	private String spotWeekDay;
	private List<String> spotWeekDays = new ArrayList<>();
	private int allDiscountFlag;
	//private List<Integer> itemIds = new ArrayList<>();
	private String itemIds;
	
	//한 개 항목만 할인
	private int itemId;
	private int oneDiscountAmount;


	public String[] getSpotItemIds() {
		return CommonUtils.copy(spotItemIds);
	}
	public void setSpotItemIds(String[] spotItemIds) {
		this.spotItemIds = CommonUtils.copy(spotItemIds);
	}
	public String[] getSpotDiscountAmounts() {
		return CommonUtils.copy(spotDiscountAmounts);
	}
	public void setSpotDiscountAmounts(String[] spotDiscountAmounts) {
		this.spotDiscountAmounts = CommonUtils.copy(spotDiscountAmounts);
	}
	
	//일괄할인
	public int getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getSpotStatus() {
		return spotStatus;
	}
	public void setSpotStatus(String spotStatus) {
		this.spotStatus = spotStatus;
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
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getSpotApplyGroup() {
		return spotApplyGroup;
	}
	public void setSpotApplyGroup(String spotApplyGroup) {
		this.spotApplyGroup = spotApplyGroup;
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
	public int getAllDiscountFlag() {
		return allDiscountFlag;
	}
	public void setAllDiscountFlag(int allDiscountFlag) {
		this.allDiscountFlag = allDiscountFlag;
	}
/*	public List<Integer> getItemIds() {
		return itemIds;
	}
	public void setItemIds(List<Integer> itemIds) {
		this.itemIds = itemIds;
	}*/
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	
	//한 개 항목 할인
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getOneDiscountAmount() {
		return oneDiscountAmount;
	}
	public void setOneDiscountAmount(int oneDiscountAmount) {
		this.oneDiscountAmount = oneDiscountAmount;
	}
	
}
