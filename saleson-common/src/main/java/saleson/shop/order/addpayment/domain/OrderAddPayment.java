package saleson.shop.order.addpayment.domain;

import saleson.shop.order.domain.OrderShipping;

public class OrderAddPayment {
	
	public OrderAddPayment() {}
	public OrderAddPayment(OrderShipping shipping) {
		setOrderCode(shipping.getOrderCode());
		setOrderSequence(shipping.getOrderSequence());
		setSellerId(shipping.getSellerId());
		
		setOrderShipping(shipping);
	}
	
	private OrderShipping orderShipping;
	public OrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

	private int addPaymentId;
	private long sellerId;
	private String orderCode;
	private int orderSequence;
	private String refundCode;
	private String issueCode;
	private String subject;
	private String addPaymentType;
	private int amount;
	
	private String salesDate;
	private String salesCancelDate;
	
	private int remittanceId;
	private int remittanceAmount;
	private String remittanceDate;
	private String remittanceExpectedDate;
	private String remittanceStatusCode;
	
	public int getRemittanceId() {
		return remittanceId;
	}
	public void setRemittanceId(int remittanceId) {
		this.remittanceId = remittanceId;
	}
	public String getRemittanceExpectedDate() {
		return remittanceExpectedDate;
	}
	public void setRemittanceExpectedDate(String remittanceExpectedDate) {
		this.remittanceExpectedDate = remittanceExpectedDate;
	}
	public String getRemittanceStatusCode() {
		return remittanceStatusCode;
	}
	public void setRemittanceStatusCode(String remittanceStatusCode) {
		this.remittanceStatusCode = remittanceStatusCode;
	}
	public int getAddPaymentId() {
		return addPaymentId;
	}
	public void setAddPaymentId(int addPaymentId) {
		this.addPaymentId = addPaymentId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
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
	public String getRefundCode() {
		return refundCode;
	}
	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}
	public String getAddPaymentType() {
		return addPaymentType;
	}
	public void setAddPaymentType(String addPaymentType) {
		this.addPaymentType = addPaymentType;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getRemittanceAmount() {
		return remittanceAmount;
	}
	public void setRemittanceAmount(int remittanceAmount) {
		this.remittanceAmount = remittanceAmount;
	}
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	public String getSalesCancelDate() {
		return salesCancelDate;
	}
	public void setSalesCancelDate(String salesCancelDate) {
		this.salesCancelDate = salesCancelDate;
	}
	public String getRemittanceDate() {
		return remittanceDate;
	}
	public void setRemittanceDate(String remittanceDate) {
		this.remittanceDate = remittanceDate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}
	
}
