package saleson.shop.order.domain;

public class BuyQuantity {
	
	private int shippingIndex;
	private int itemSequence;
	private int quantity;
	
	public int getShippingIndex() {
		return shippingIndex;
	}
	public void setShippingIndex(int shippingIndex) {
		this.shippingIndex = shippingIndex;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
