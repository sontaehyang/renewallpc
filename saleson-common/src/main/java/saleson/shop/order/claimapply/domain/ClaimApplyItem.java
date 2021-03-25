package saleson.shop.order.claimapply.domain;

public class ClaimApplyItem {
	private String orderCode;
	private int orderSequence;
	private int applyQuantity;
	private int itemSequence;

	public int getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}

	public int getApplyQuantity() {
		return applyQuantity;
	}

	public void setApplyQuantity(int applyQuantity) {
		this.applyQuantity = applyQuantity;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	
}
