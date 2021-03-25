package saleson.shop.item.domain;

public class ItemOrdering {
	private int itemOrderingId;
	private int itemId;
	private int categoryId;
	private int ordering;
	private String createdDate;
	public int getItemOrderingId() {
		return itemOrderingId;
	}
	public void setItemOrderingId(int itemOrderingId) {
		this.itemOrderingId = itemOrderingId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
