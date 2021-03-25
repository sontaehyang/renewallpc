package saleson.shop.coupon.support;

import saleson.common.utils.CommonUtils;

public class CouponTargetUser {
	private String addType;
	private String title;
	private String[] userIds;
	
	private String loginId;
	private String userName;
	private String email;
	private String searchReceiveEmail;
	private String searchReceiveSms;
	
	public String getAddType() {
		return addType;
	}
	public void setAddType(String addType) {
		this.addType = addType;
	}
	public String[] getUserIds() {
		return CommonUtils.copy(userIds);
	}
	public void setUserIds(String[] userIds) {
		this.userIds = CommonUtils.copy(userIds);
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSearchReceiveEmail() {
		return searchReceiveEmail;
	}
	public void setSearchReceiveEmail(String searchReceiveEmail) {
		this.searchReceiveEmail = searchReceiveEmail;
	}
	public String getSearchReceiveSms() {
		return searchReceiveSms;
	}
	public void setSearchReceiveSms(String searchReceiveSms) {
		this.searchReceiveSms = searchReceiveSms;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
