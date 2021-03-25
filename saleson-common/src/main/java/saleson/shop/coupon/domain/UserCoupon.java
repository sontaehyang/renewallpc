package saleson.shop.coupon.domain;



public class UserCoupon extends CouponUser {
	
	private String loginId;
	private String orderCode;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	} 
	
	
}
