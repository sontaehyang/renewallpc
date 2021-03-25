package saleson.shop.statistics.domain;

import java.util.HashMap;
import java.util.List;


public class ShopItemDateStatistics {

	private String itemId;
	private String itemName;
	private String itemImage;
	private String itemUserCode;
	private String categoryId;
	private String categoryCode;
	private String categoryName;
	private String groupCode;
	private String groupName;
	private String code;
	private String name;
	
	private List<ShopDateStatistics> dateList;
	
	private List<ShopItemPrice> dateList2;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public String getItemUserCode() {
		return itemUserCode;
	}

	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}

	public List<ShopDateStatistics> getDateList() {
		return dateList;
	}

	public void setDateList(List<ShopDateStatistics> dateList) {
		this.dateList = dateList;
	}

	public List<ShopItemPrice> getDateList2() {
		return dateList2;
	}

	public void setDateList2(List<ShopItemPrice> dateList2) {
		this.dateList2 = dateList2;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
   
}
