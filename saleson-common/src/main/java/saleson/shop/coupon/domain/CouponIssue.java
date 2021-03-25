package saleson.shop.coupon.domain;


public class CouponIssue extends CouponUser {
	
	private String couponIssueCount;
	private String couponApplyCount;
	
	public String getCouponIssueCount() {
		return couponIssueCount;
	}
	public void setCouponIssueCount(String couponIssueCount) {
		this.couponIssueCount = couponIssueCount;
	}
	public String getCouponApplyCount() {
		return couponApplyCount;
	}
	public void setCouponApplyCount(String couponApplyCount) {
		this.couponApplyCount = couponApplyCount;
	}
	
	
}
