package saleson.shop.order.support;

public class OrderItemStatusParam {
	
	private int orderId;
	private int vendorId;
	
	private int orderItemId;
	
	private String orgOrderStatus;
	private String orderStatus;
	
	private String deliveryNumber;
	private String deliveryCompanyId;
	private String orderDeliveryId;
	
	
	private String returnPointFlag;
	
	private String saveOrgOrderStatus;
	
	
	public String getSaveOrgOrderStatus() {
		return saveOrgOrderStatus;
	}
	public void setSaveOrgOrderStatus(String saveOrgOrderStatus) {
		this.saveOrgOrderStatus = saveOrgOrderStatus;
	}
	public String getReturnPointFlag() {
		return returnPointFlag;
	}
	public void setReturnPointFlag(String returnPointFlag) {
		this.returnPointFlag = returnPointFlag;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getOrgOrderStatus() {
		return orgOrderStatus;
	}
	public void setOrgOrderStatus(String orgOrderStatus) {
		this.orgOrderStatus = orgOrderStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	public String getDeliveryCompanyId() {
		return deliveryCompanyId;
	}
	public void setDeliveryCompanyId(String deliveryCompanyId) {
		this.deliveryCompanyId = deliveryCompanyId;
	}
	public String getOrderDeliveryId() {
		return orderDeliveryId;
	}
	public void setOrderDeliveryId(String orderDeliveryId) {
		this.orderDeliveryId = orderDeliveryId;
	}
	
	
	
}
