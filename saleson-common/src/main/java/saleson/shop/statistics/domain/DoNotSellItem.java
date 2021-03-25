package saleson.shop.statistics.domain;

import com.onlinepowers.framework.util.StringUtils;

import saleson.common.utils.ShopUtils;

public class DoNotSellItem {
	private int categoryTeamId;
	private String teamCode;
	private String teamName;
	private int categoryGroupId;
	private String groupCode;
	private String groupName;
	private String categoryName;
	private int categoryId;
	private String categoryUrl;
	private String categoryCode;
	private String categoryClass1;
	private String categoryClass2;
	private String categoryClass3;
	private String categoryClass4;
	private String categoryLevel;
	private int itemId;
	private String displayFlag;
	private String itemName;
	private String itemImage;
	private String itemUserCode;
	private String payCount;
	private String totalItemPrice;
	
	public String getTitle() {
		
		String title = "";
		String code = "";
		
		if (StringUtils.isNotEmpty(this.teamName)) {
			
			title += this.teamName;
			
			code = this.teamCode;
			
		}
		
		if (StringUtils.isNotEmpty(this.groupName)) {
			title += " > " + this.groupName;
			
			code = this.groupCode;
		}
		
		if (StringUtils.isNotEmpty(this.categoryName)) {
			title += " > " + this.categoryName;
			
			code = this.categoryCode;
		}
		
		if (StringUtils.isEmpty(title)) {
			title = "정보없음";
		}
		
		if (StringUtils.isNotEmpty(code)) {
			title += " (" + code + ")";
		}
		
		return title;
	}
	
	/**
	 * 목록 이미지 경로
	 * @return
	 */
	public String getImageSrc() {
		return ShopUtils.listImage(this.itemUserCode, this.itemImage);
	}
	
	public int getCategoryTeamId() {
		return categoryTeamId;
	}
	public void setCategoryTeamId(int categoryTeamId) {
		this.categoryTeamId = categoryTeamId;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(int categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryUrl() {
		return categoryUrl;
	}
	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
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
	public String getCategoryLevel() {
		return categoryLevel;
	}
	public void setCategoryLevel(String categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
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

	public String getPayCount() {
		return payCount;
	}

	public void setPayCount(String payCount) {
		this.payCount = payCount;
	}

	public String getTotalItemPrice() {
		return totalItemPrice;
	}

	public void setTotalItemPrice(String totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}
	
}
