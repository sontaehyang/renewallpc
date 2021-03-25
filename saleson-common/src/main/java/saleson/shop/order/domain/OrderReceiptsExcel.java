package saleson.shop.order.domain;

public class OrderReceiptsExcel {
	private String orderCode;
	private String userName;
	private int orderPayAmount;
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public int getOrderPayAmount() {
		return orderPayAmount;
	}
	public void setOrderPayAmount(int orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}
}
