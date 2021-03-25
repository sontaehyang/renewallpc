package saleson.shop.categories.domain;

public class BreadcrumbCategory {
	private String categoryId;
	private String categoryName;
	private String categoryUrl;
	private String groupUrl;
	private String indexId;
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
	public String getCategoryUrl() {
		return categoryUrl;
	}
	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}
		public String getGroupUrl() {
		return groupUrl;
	}
	public void setGroupUrl(String groupUrl) {
		this.groupUrl = groupUrl;
	}
	public String getIndexId() { return indexId; }
	public void setIndexId(String indexId) { this.indexId = indexId; }
}
