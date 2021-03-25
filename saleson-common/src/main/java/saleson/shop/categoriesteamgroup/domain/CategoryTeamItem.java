package saleson.shop.categoriesteamgroup.domain;

public class CategoryTeamItem {

	private int categoryTeamItemId;
	private int categoryTeamId;
	private int itemId;

	private String createdDate;
	
	public int getCategoryTeamItemId() {
		return categoryTeamItemId;
	}
	public void setCategoryTeamItemId(int categoryTeamItemId) {
		this.categoryTeamItemId = categoryTeamItemId;
	}
	public int getCategoryTeamId() {
		return categoryTeamId;
	}
	public void setCategoryTeamId(int categoryTeamId) {
		this.categoryTeamId = categoryTeamId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	
}
