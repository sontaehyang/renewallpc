package saleson.shop.point.domain;

public class PublicationPoint {
	private long userId;
	private String loginId;
	private String userName;
	private String statusCode;
	private String employeeFlag;
	private int savedPoint;
	private int usedPoint;
	
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserName() {
		
		if ("4".equals(statusCode)) {
			return "[휴면회원]";
		}
		
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getEmployeeFlag() {
		return employeeFlag;
	}
	public void setEmployeeFlag(String employeeFlag) {
		this.employeeFlag = employeeFlag;
	}
	public int getSavedPoint() {
		return savedPoint;
	}
	public void setSavedPoint(int savedPoint) {
		this.savedPoint = savedPoint;
	}
	public int getUsedPoint() {
		return usedPoint;
	}
	public void setUsedPoint(int usedPoint) {
		this.usedPoint = usedPoint;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	

}
