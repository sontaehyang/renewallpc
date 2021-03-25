package saleson.shop.access.domain;

public class Access {

	private int allowIpId;
	private String accessType;
	private String remoteAddr;
	private String displayFlag;
	private String createdUser;
	private String createdDate;
	private String updatedUser;
	private String updatedDate;

	public int getAllowIpId() {
		return allowIpId;
	}

	public void setAllowIpId(int allowIpId) {
		this.allowIpId = allowIpId;
	}

	public String getAccessType() {

		return accessType;

	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
}
