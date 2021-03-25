package saleson.shop.coupon.domain;

import org.springframework.util.StringUtils;



public class CouponUser {
	
	private int couponUserId;
	private int couponId;
	private long userId;
	private String couponName;
	private String couponComment;
	private String couponType;
	private String couponApplyType;
	private String couponApplyStartDate;
	private String couponApplyEndDate;
	private int couponPayRestriction;
	private String couponConcurrently;
	private String couponPayType;
	private int couponPay;
	private int couponDiscountLimitPrice;
	private String couponTargetItemType;
	private String dataStatusCode;
	private String couponDownloadDate;
	private String couponUseDate;
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private int discountAmount;
	private String createdDate;

	private String userName;
	private String loginId;
	private String email;
	private String couponDataStatusCode;

	private String phoneNumber;

	private int couponCount;

	public int getCouponUserId() {
		return couponUserId;
	}
	public void setCouponUserId(int couponUserId) {
		this.couponUserId = couponUserId;
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
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getCouponApplyType() {
		return couponApplyType;
	}
	public void setCouponApplyType(String couponApplyType) {
		this.couponApplyType = couponApplyType;
	}
	public String getCouponApplyStartDate() {
		return couponApplyStartDate;
	}
	public void setCouponApplyStartDate(String couponApplyStartDate) {
		this.couponApplyStartDate = couponApplyStartDate;
	}
	public String getCouponApplyEndDate() {
		return couponApplyEndDate;
	}
	public void setCouponApplyEndDate(String couponApplyEndDate) {
		this.couponApplyEndDate = couponApplyEndDate;
	}
	public int getCouponPayRestriction() {
		return couponPayRestriction;
	}
	public void setCouponPayRestriction(int couponPayRestriction) {
		this.couponPayRestriction = couponPayRestriction;
	}
	public String getCouponConcurrently() {
		return couponConcurrently;
	}
	public void setCouponConcurrently(String couponConcurrently) {
		this.couponConcurrently = couponConcurrently;
	}
	public String getCouponPayType() {
		return couponPayType;
	}
	public void setCouponPayType(String couponPayType) {
		this.couponPayType = couponPayType;
	}
	public int getCouponPay() {
		return couponPay;
	}
	public void setCouponPay(int couponPay) {
		this.couponPay = couponPay;
	}
	public int getCouponDiscountLimitPrice() {
		return couponDiscountLimitPrice;
	}
	public void setCouponDiscountLimitPrice(int couponDiscountLimitPrice) {
		this.couponDiscountLimitPrice = couponDiscountLimitPrice;
	}
	public String getCouponTargetItemType() {
		return couponTargetItemType;
	}
	public void setCouponTargetItemType(String couponTargetItemType) {
		this.couponTargetItemType = couponTargetItemType;
	}
	public String getDataStatusCode() {
		return dataStatusCode;
	}
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	public String getCouponDownloadDate() {
		return couponDownloadDate;
	}
	public void setCouponDownloadDate(String couponDownloadDate) {
		this.couponDownloadDate = couponDownloadDate;
	}
	public String getCouponUseDate() {
		return couponUseDate;
	}
	public void setCouponUseDate(String couponUseDate) {
		this.couponUseDate = couponUseDate;
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
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public int getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getCouponComment() {
		return couponComment;
	}
	public void setCouponComment(String couponComment) {
		this.couponComment = couponComment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCouponDataStatusCode() {
		return couponDataStatusCode;
	}
	public void setCouponDataStatusCode(String couponDataStatusCode) {
		this.couponDataStatusCode = couponDataStatusCode;
	}
	public String getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
	public int getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}
}
