package saleson.shop.item.domain;

public class ItemOther {
	private int itemOtherId;
	private int itemId;
	private int otherItemId;
	private int counting;
	
	// 같이 주문한 상품정보
	private ItemBase item;
	
	public int getItemOtherId() {
		return itemOtherId;
	}
	public void setItemOtherId(int itemOtherId) {
		this.itemOtherId = itemOtherId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getOtherItemId() {
		return otherItemId;
	}
	public void setOtherItemId(int otherItemId) {
		this.otherItemId = otherItemId;
	}
	public int getCounting() {
		return counting;
	}
	public void setCounting(int counting) {
		this.counting = counting;
	}
	public ItemBase getItem() {
		return item;
	}
	public void setItem(ItemBase item) {
		this.item = item;
	}
	
	
}
