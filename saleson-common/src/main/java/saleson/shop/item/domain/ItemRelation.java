package saleson.shop.item.domain;


public class ItemRelation {
	private int itemRelationId;
	private int itemId;
	private int relatedItemId;
	private int ordering;
	private String createdDate;

	// 같이 주문한 상품정보
	private Item item;
	
	public int getItemRelationId() {
		return itemRelationId;
	}
	public void setItemRelationId(int itemRelationId) {
		this.itemRelationId = itemRelationId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getRelatedItemId() {
		return relatedItemId;
	}
	public void setRelatedItemId(int relatedItemId) {
		this.relatedItemId = relatedItemId;
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
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
}
