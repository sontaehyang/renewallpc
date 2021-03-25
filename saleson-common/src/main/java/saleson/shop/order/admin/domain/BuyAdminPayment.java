package saleson.shop.order.admin.domain;

public class BuyAdminPayment {
	
	public BuyAdminPayment() {}
	public BuyAdminPayment(BuyAdmin buy) {
		
		setOrderCode(buy.getOrderCode());
		setOrderSequence(buy.getOrderSequence());
		setPaymentSequence(0);
		setPaymentType("1");
		setDeviceType("ADMIN");
		setApprovalType("cash");
		
		BuyAdminOrderPrice price = buy.getOrderPrice();
		setAmount(price.getOrderPayAmount());
		setTaxFreeAmount(price.getTaxFreeAmount());
		
	}
	
	private String orderCode;
	private int orderSequence;
	private int paymentSequence;
	private String paymentType;
	private String approvalType;
	private String deviceType;
	private int amount;
	private int taxFreeAmount;
	
	public int getTaxFreeAmount() {
		return taxFreeAmount;
	}
	public void setTaxFreeAmount(int taxFreeAmount) {
		this.taxFreeAmount = taxFreeAmount;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	public int getPaymentSequence() {
		return paymentSequence;
	}
	public void setPaymentSequence(int paymentSequence) {
		this.paymentSequence = paymentSequence;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	
}
