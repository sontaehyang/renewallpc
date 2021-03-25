package saleson.shop.point.domain;

import org.springframework.util.StringUtils;

public class PointHistory {
	private int point;
	private String reason;
	private String orderCode;
	private String createdDate;
	private String managerName;
	private String sign;
	
	private long userId;
	private String loginId;
	private String userName;
	private String subject;
	private String statusCode;
	private String expirationDate;
	
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public int getPoint() {
		
		if (StringUtils.isEmpty(getSubject()) == false) {
			if (!"적립".equals(getSubject())) {
				return point * -1;
			}
		}
		
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getOrderCodeLabel() {
		
		if(StringUtils.isEmpty(getOrderCode()) == false) {
			if ("birthday-issue".equals(getOrderCode())) {
				return "생일";
			} else if ("issue".equals(getOrderCode())) {
				return "";
			}
			
			return getOrderCode();
		}
		
		return "";
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
}
