package saleson.shop.order.shipping.support;

import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderExchangeApply;

public class ShippingParam {
	
	private String mode;
	private String conditionType;
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	public ShippingParam() {}
	public ShippingParam(OrderCancelApply orderCancelApply) {
		setOrderCode(orderCancelApply.getOrderCode());
		setOrderSequence(orderCancelApply.getOrderSequence());
		setItemSequence(orderCancelApply.getItemSequence());
		setDeliveryCompanyId(orderCancelApply.getDeliveryCompanyId());
		setDeliveryNumber(orderCancelApply.getDeliveryNumber());
		setMode("CANCEL");
	}
	
	public ShippingParam(OrderExchangeApply orderExchangeApply) {
		setOrderCode(orderExchangeApply.getOrderCode());
		setOrderSequence(orderExchangeApply.getOrderSequence());
		setItemSequence(orderExchangeApply.getItemSequence());
		setDeliveryCompanyId(orderExchangeApply.getExchangeDeliveryCompanyId());
		setDeliveryNumber(orderExchangeApply.getExchangeDeliveryNumber());
		setMode("EXCHANGE");
	}
	
	private String key;
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private long sellerId;
	private String adminUserName;
	
	private String deliveryNumber;
	private int deliveryCompanyId;
	private String deliveryCompanyName;
	private String deliveryCompanyUrl;
	
	private String dlv_report;	// 이니시스 에스크로 등록형태
	private String dlv_charge;	// 이니시스 에스크로 배송비 부담형태	
	
	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}
	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}
	public String getDeliveryCompanyUrl() {
		return deliveryCompanyUrl;
	}
	public void setDeliveryCompanyUrl(String deliveryCompanyUrl) {
		this.deliveryCompanyUrl = deliveryCompanyUrl;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getAdminUserName() {
		return adminUserName;
	}
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	public int getDeliveryCompanyId() {
		return deliveryCompanyId;
	}
	public void setDeliveryCompanyId(int deliveryCompanyId) {
		this.deliveryCompanyId = deliveryCompanyId;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getDlv_report() {
		return dlv_report;
	}
	public void setDlv_report(String dlv_report) {
		this.dlv_report = dlv_report;
	}
	public String getDlv_charge() {
		return dlv_charge;
	}
	public void setDlv_charge(String dlv_charge) {
		this.dlv_charge = dlv_charge;
	}
	
}
