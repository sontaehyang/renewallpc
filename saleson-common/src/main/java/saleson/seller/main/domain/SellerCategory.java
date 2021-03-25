package saleson.seller.main.domain;

public class SellerCategory {
	private int categoryId;
	private String categoryClass;
	private String categoryName;
	private int itemCount;
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategoryClass() {
		return categoryClass;
	}
	public void setCategoryClass(String categoryClass) {
		this.categoryClass = categoryClass;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
}
