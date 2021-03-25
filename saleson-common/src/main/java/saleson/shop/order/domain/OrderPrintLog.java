package saleson.shop.order.domain;

public class OrderPrintLog {
	private int orderPrintLogId;
	private int vendorId;
	private int orderId;
	private String title;
	private long userId;
	private String action;
	private String regIp;
	private String createdDate;
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public int getOrderPrintLogId() {
		return orderPrintLogId;
	}
	public void setOrderPrintLogId(int orderPrintLogId) {
		this.orderPrintLogId = orderPrintLogId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getRegIp() {
		return regIp;
	}
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
