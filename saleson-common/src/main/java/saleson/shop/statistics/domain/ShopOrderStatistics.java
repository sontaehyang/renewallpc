package saleson.shop.statistics.domain;

import java.util.List;

import saleson.shop.item.domain.ItemBase;


public class ShopOrderStatistics {

	private String orderId;
	private String orderCode;
	private String orderCount;
	private String itemExcisePrice;
	private String couponDiscountAmount;
	private String cartCouponDiscountAmount;
	private String sumUsePoint;
	private String vendorAddDiscountAmount;
	private String sumDeliveryPrice;
	private String vendorAddDeliveryExtraCharge;
	private String orderStatus;
	private String osType;
	private String searchDate;
	private String loginId;
	private String userName;
	private String orderType;
	
	private List<ShopItemStatistics> itemsList;
	
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
	public String getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}
	public String getItemExcisePrice() {
		return itemExcisePrice;
	}
	public void setItemExcisePrice(String itemExcisePrice) {
		this.itemExcisePrice = itemExcisePrice;
	}
	public String getCouponDiscountAmount() {
		return couponDiscountAmount;
	}
	public void setCouponDiscountAmount(String couponDiscountAmount) {
		this.couponDiscountAmount = couponDiscountAmount;
	}
	public String getCartCouponDiscountAmount() {
		return cartCouponDiscountAmount;
	}
	public void setCartCouponDiscountAmount(String cartCouponDiscountAmount) {
		this.cartCouponDiscountAmount = cartCouponDiscountAmount;
	}
	public String getSumUsePoint() {
		return sumUsePoint;
	}
	public void setSumUsePoint(String sumUsePoint) {
		this.sumUsePoint = sumUsePoint;
	}
	public String getVendorAddDiscountAmount() {
		return vendorAddDiscountAmount;
	}
	public void setVendorAddDiscountAmount(String vendorAddDiscountAmount) {
		this.vendorAddDiscountAmount = vendorAddDiscountAmount;
	}
	public String getSumDeliveryPrice() {
		return sumDeliveryPrice;
	}
	public void setSumDeliveryPrice(String sumDeliveryPrice) {
		this.sumDeliveryPrice = sumDeliveryPrice;
	}
	public String getVendorAddDeliveryExtraCharge() {
		return vendorAddDeliveryExtraCharge;
	}
	public void setVendorAddDeliveryExtraCharge(String vendorAddDeliveryExtraCharge) {
		this.vendorAddDeliveryExtraCharge = vendorAddDeliveryExtraCharge;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
	public List<ShopItemStatistics> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<ShopItemStatistics> itemsList) {
		this.itemsList = itemsList;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
	
	
}
