package saleson.shop.order.domain;

public class Option {
	private int itemOptionId;
	private String itemOptionName;
	private int itemOptionPrice;
	private int itemOptionPriceNonMember;
	
	public int getItemOptionPriceNonMember() {
		return itemOptionPriceNonMember;
	}
	public void setItemOptionPriceNonMember(int itemOptionPriceNonMember) {
		this.itemOptionPriceNonMember = itemOptionPriceNonMember;
	}
	public int getItemOptionPrice() {
		return itemOptionPrice;
	}
	public void setItemOptionPrice(int itemOptionPrice) {
		this.itemOptionPrice = itemOptionPrice;
	}
	public int getItemOptionId() {
		return itemOptionId;
	}
	public void setItemOptionId(int itemOptionId) {
		this.itemOptionId = itemOptionId;
	}
	public String getItemOptionName() {
		return itemOptionName;
	}
	public void setItemOptionName(String itemOptionName) {
		this.itemOptionName = itemOptionName;
	}
}
