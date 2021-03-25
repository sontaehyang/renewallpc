package saleson.shop.order.domain;

public class OrderStatusLog {
	private int orderStatusLogId;
	private int vendorId;
	private int orderId;
	private int orderItemId;
	private String orderStatus;
	private String orderStatusLabel;
	
	private long userId;
	private String regIp;
	private String createdDate;
	
	/* USER */
	private String userName;
	private String itemName;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getOrderStatusLabel() {
		return orderStatusLabel;
	}
	public void setOrderStatusLabel(String orderStatusLabel) {
		this.orderStatusLabel = orderStatusLabel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public int getOrderStatusLogId() {
		return orderStatusLogId;
	}
	public void setOrderStatusLogId(int orderStatusLogId) {
		this.orderStatusLogId = orderStatusLogId;
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
