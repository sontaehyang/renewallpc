package saleson.shop.group.domain;

import saleson.shop.userlevel.domain.UserLevel;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private String groupCode;
	private String groupName;
	private String groupExp;
	private String createdDate;
	private String createdUserId;
	private String updatedDate;
	private	String updatedUserId;

	private int userCount;


	private List<UserLevel> userLevels = new ArrayList<>();

	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupExp() {
		return groupExp;
	}
	public void setGroupExp(String groupExp) {
		this.groupExp = groupExp;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedUserId() {
		return updatedUserId;
	}
	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public List<UserLevel> getUserLevels() {
		return userLevels;
	}

	public void setUserLevels(List<UserLevel> userLevels) {
		this.userLevels = userLevels;
	}
}
