package saleson.shop.coupon.domain;

import saleson.common.utils.CommonUtils;
import saleson.shop.user.support.UserSearchParam;

@SuppressWarnings("serial")
public class UserSearchAddParam extends UserSearchParam {

	private String couponState;
	private int couponId;
	private int startCouponUserId;

	private String[] userIds;
	
	public String getCouponState() {
		return couponState;
	}
	public void setCouponState(String couponState) {
		this.couponState = couponState;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public int getStartCouponUserId() {
		return startCouponUserId;
	}
	public void setStartCouponUserId(int startCouponUserId) {
		this.startCouponUserId = startCouponUserId;
	}
	public String[] getUserIds() {
		return CommonUtils.copy(userIds);
	}
	public void setUserIds(String[] userIds) {
		this.userIds = CommonUtils.copy(userIds);
	}
	
	
}
