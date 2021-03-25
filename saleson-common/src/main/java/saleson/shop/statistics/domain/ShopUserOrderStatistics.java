package saleson.shop.statistics.domain;

import java.util.List;

import saleson.shop.item.domain.ItemBase;


public class ShopUserOrderStatistics {

	private String orderId;
	private String itemId;
	private String itemUserCode;
	private String itemName; 
	private String orderCode;
	private String priceTotal;
	private String discountAmount;
	private String deliveryPrice;
	private String cancelDeliveryPrice;
	private String cancelPriceTotal;
	private String cancelDiscountAmount;
	private String orderItemId;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getPriceTotal() {
		return priceTotal;
	}
	public void setPriceTotal(String priceTotal) {
		this.priceTotal = priceTotal;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getDeliveryPrice() {
		return deliveryPrice;
	}
	public void setDeliveryPrice(String deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
	public String getCancelDeliveryPrice() {
		return cancelDeliveryPrice;
	}
	public void setCancelDeliveryPrice(String cancelDeliveryPrice) {
		this.cancelDeliveryPrice = cancelDeliveryPrice;
	}
	public String getCancelPriceTotal() {
		return cancelPriceTotal;
	}
	public void setCancelPriceTotal(String cancelPriceTotal) {
		this.cancelPriceTotal = cancelPriceTotal;
	}
	public String getCancelDiscountAmount() {
		return cancelDiscountAmount;
	}
	public void setCancelDiscountAmount(String cancelDiscountAmount) {
		this.cancelDiscountAmount = cancelDiscountAmount;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	
	
	
}
