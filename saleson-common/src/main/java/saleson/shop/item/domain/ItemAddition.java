package saleson.shop.item.domain;

public class ItemAddition {
	private int itemAdditionId;
	private int itemId;
	private int additionItemId;
	private int ordering;
	private String createdDate;

	// 연관 상품정보
	private ItemBase item;

	public int getItemAdditionId() {
		return itemAdditionId;
	}

	public void setItemAdditionId(int itemAdditionId) {
		this.itemAdditionId = itemAdditionId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getAdditionItemId() {
		return additionItemId;
	}

	public void setAdditionItemId(int additionItemId) {
		this.additionItemId = additionItemId;
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

	public ItemBase getItem() {
		return item;
	}

	public void setItem(ItemBase item) {
		this.item = item;
	}
}
