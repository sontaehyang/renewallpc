package saleson.shop.shadowlogin.domain;

public class ShadowLoginLog {
	
	public ShadowLoginLog() {}
	public ShadowLoginLog(int shadowLoginLogId, String loginTarget, long loginTargetId, long managerId) {
		
		setShadowLoginLogId(shadowLoginLogId);
		setManagerId(managerId);
		setLoginTarget(loginTarget);
		setLoginTargetId(loginTargetId);
		
	}
	
	private int shadowLoginLogId;
	private long managerId;
	private long loginTargetId;
	private String loginTarget;
	private String loginDate;
	private String logoutDate;
	
	
	public int getShadowLoginLogId() {
		return shadowLoginLogId;
	}
	public void setShadowLoginLogId(int shadowLoginLogId) {
		this.shadowLoginLogId = shadowLoginLogId;
	}
	public long getManagerId() {
		return managerId;
	}
	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}
	public long getLoginTargetId() {
		return loginTargetId;
	}
	public void setLoginTargetId(long loginTargetId) {
		this.loginTargetId = loginTargetId;
	}
	public String getLoginTarget() {
		return loginTarget;
	}
	public void setLoginTarget(String loginTarget) {
		this.loginTarget = loginTarget;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getLogoutDate() {
		return logoutDate;
	}
	public void setLogoutDate(String logoutDate) {
		this.logoutDate = logoutDate;
	}
}
