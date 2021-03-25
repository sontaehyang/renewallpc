package saleson.shop.order.support;

public class ItemCouponParam {
	private int couponUserId;
	private int userOrderItemSequence;
	private int itemId;
	private long userId;
	
	public int getCouponUserId() {
		return couponUserId;
	}
	public void setCouponUserId(int couponUserId) {
		this.couponUserId = couponUserId;
	}
	public int getUserOrderItemSequence() {
		return userOrderItemSequence;
	}
	public void setUserOrderItemSequence(int userOrderItemSequence) {
		this.userOrderItemSequence = userOrderItemSequence;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
