package saleson.shop.coupon.domain;

public class CouponOffline {

	private int couponOfflineId;
	private int couponId;
	private long userId;
	private String couponOfflineCode;
	private String couponUsedFlag = "N";
	private String couponUsedDate;
	private String publishedDate;
	private int couponAmount;
	
	public int getCouponOfflineId() {
		return couponOfflineId;
	}
	public void setCouponOfflineId(int couponOfflineId) {
		this.couponOfflineId = couponOfflineId;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getCouponOfflineCode() {
		return couponOfflineCode;
	}
	public void setCouponOfflineCode(String couponOfflineCode) {
		this.couponOfflineCode = couponOfflineCode;
	}
	public String getCouponUsedFlag() {
		return couponUsedFlag;
	}
	public void setCouponUsedFlag(String couponUsedFlag) {
		this.couponUsedFlag = couponUsedFlag;
	}
	public String getCouponUsedDate() {
		return couponUsedDate;
	}
	public void setCouponUsedDate(String couponUsedDate) {
		this.couponUsedDate = couponUsedDate;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public int getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(int couponAmount) {
		this.couponAmount = couponAmount;
	}

	
}
