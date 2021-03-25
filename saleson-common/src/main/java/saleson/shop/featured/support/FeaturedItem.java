package saleson.shop.featured.support;

import saleson.shop.item.domain.Item;

public class FeaturedItem extends Item {
	
	private int featuredId;
	private int itemId;
	private int displayOrder;
	private String createdDate;
	private String userDefGroup;
	private String userDefGroupOrder;
	private String categoryId;
	private String categoryName;
	
	public int getFeaturedId() {
		return featuredId;
	}
	public void setFeaturedId(int featuredId) {
		this.featuredId = featuredId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUserDefGroup() {
		return userDefGroup;
	}
	public void setUserDefGroup(String userDefGroup) {
		this.userDefGroup = userDefGroup;
	}
	public String getUserDefGroupOrder() {
		return userDefGroupOrder;
	}
	public void setUserDefGroupOrder(String userDefGroupOrder) {
		this.userDefGroupOrder = userDefGroupOrder;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
