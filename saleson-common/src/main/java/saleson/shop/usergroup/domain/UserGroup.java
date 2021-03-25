package saleson.shop.usergroup.domain;

import saleson.common.utils.CommonUtils;

public class UserGroup {
	
	private String groupCode;
	private long userId;
	private String resetUserLevel;
	public String getResetUserLevel() {
		return resetUserLevel;
	}
	public void setResetUserLevel(String resetUserLevel) {
		this.resetUserLevel = resetUserLevel;
	}
	private String[] userIds = null;


	public String[] getUserIds() {
		return CommonUtils.copy(userIds);
	}
	public void setUserIds(String[] userIds) {
		this.userIds = CommonUtils.copy(userIds);
	}

	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
