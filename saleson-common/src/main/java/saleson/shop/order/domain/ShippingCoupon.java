package saleson.shop.order.domain;

public class ShippingCoupon {
	private String orderCode;
	private long userId;
	private String shippingGroupCode;
	private int useCouponCount;
	private int discountAmount;
	private String useFlag;
	
	public int getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public int getUseCouponCount() {
		return useCouponCount;
	}
	public void setUseCouponCount(int useCouponCount) {
		this.useCouponCount = useCouponCount;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getShippingGroupCode() {
		return shippingGroupCode;
	}
	public void setShippingGroupCode(String shippingGroupCode) {
		this.shippingGroupCode = shippingGroupCode;
	}
}
