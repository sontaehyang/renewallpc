package saleson.common.userauth.domain;

public class UserAuth {
	private String appKey;
	private String serviceType;
	private String serviceMode; // IPIN, PCC
	private String serviceTarget;	// JOIN, FIND-ID, FIND-PASSWORD
	private String userIp;
	private String authKey;
	private String authName;
	private String authSex;
	private String authBirthDay;
	private String dataStatusCode;
	private String createdDate;
	
	public String getServiceTarget() {
		return serviceTarget;
	}
	public void setServiceTarget(String serviceTarget) {
		this.serviceTarget = serviceTarget;
	}
	public String getDataStatusCode() {
		return dataStatusCode;
	}
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceMode() {
		return serviceMode;
	}
	public void setServiceMode(String serviceMode) {
		this.serviceMode = serviceMode;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getAuthSex() {
		return authSex;
	}
	public void setAuthSex(String authSex) {
		this.authSex = authSex;
	}
	public String getAuthBirthDay() {
		return authBirthDay;
	}
	public void setAuthBirthDay(String authBirthDay) {
		this.authBirthDay = authBirthDay;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
