package saleson.shop.statistics.domain;

import java.util.List;

public class RevenueDetail {
	private String date;
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private long userId;
	private String osType;
	private int sumExcisePrice;
	private int sumDeliveryPrice;
	private int sumUsePoint;
	private int cartCouponDiscountAmount;
	private String userName;
	private String phone;
	private String mobile;
	private String email;
	private String zipcode;
	private String address;
	private String addressDetail;
	private String receiveZipcode;
	private String receiveCompanyName;
	private String receiveAddress;
	private String receiveAddressDetail;
	private String receiveName;
	private String receivePhone;
	private String receiveMobile;
	private String taxType;
	private String orderType;
	private int vendorAddDeliveryExtraCharge;
	private int vendorAddDiscountAmount;
	private int shippingSequence;
	private int price;
	private int itemAmount;

	private List<RevenueDetailItem> items;




	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPrice() {
		/*if (price > 0 && "CANCEL".equals(this.orderType)) {
			price = -(price);
		}*/
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public int getSumExcisePrice() {

		/*if (sumExcisePrice > 0 && "CANCEL".equals(this.orderType)) {
			sumExcisePrice = -(sumExcisePrice);
		}*/

		return sumExcisePrice;
	}

	public void setSumExcisePrice(int sumExcisePrice) {
		this.sumExcisePrice = sumExcisePrice;
	}

	public int getSumDeliveryPrice() {

	/*	if (sumDeliveryPrice > 0 && "CANCEL".equals(this.orderType)) {
			sumDeliveryPrice = -(sumDeliveryPrice);
		}
		*/
		return sumDeliveryPrice;
	}

	public void setSumDeliveryPrice(int sumDeliveryPrice) {
		this.sumDeliveryPrice = sumDeliveryPrice;
	}

	public int getSumUsePoint() {

		/*if (sumUsePoint > 0 && "PAY".equals(this.orderType)) {
			sumUsePoint = -(sumUsePoint);
		}*/

		return sumUsePoint;
	}

	public void setSumUsePoint(int sumUsePoint) {
		this.sumUsePoint = sumUsePoint;
	}

	public int getCartCouponDiscountAmount() {

		/*if (cartCouponDiscountAmount > 0 && "PAY".equals(this.orderType)) {
			cartCouponDiscountAmount = -(cartCouponDiscountAmount);
		}*/

		return cartCouponDiscountAmount;
	}

	public void setCartCouponDiscountAmount(int cartCouponDiscountAmount) {
		this.cartCouponDiscountAmount = cartCouponDiscountAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getReceiveZipcode() {
		return receiveZipcode;
	}

	public void setReceiveZipcode(String receiveZipcode) {
		this.receiveZipcode = receiveZipcode;
	}

	public String getReceiveCompanyName() {
		return receiveCompanyName;
	}

	public void setReceiveCompanyName(String receiveCompanyName) {
		this.receiveCompanyName = receiveCompanyName;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getReceiveAddressDetail() {
		return receiveAddressDetail;
	}

	public void setReceiveAddressDetail(String receiveAddressDetail) {
		this.receiveAddressDetail = receiveAddressDetail;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getReceiveMobile() {
		return receiveMobile;
	}

	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public int getVendorAddDeliveryExtraCharge() {

	/*	if (vendorAddDeliveryExtraCharge > 0 && "CANCEL".equals(this.orderType)) {
			vendorAddDeliveryExtraCharge = -(vendorAddDeliveryExtraCharge);
		}
		*/
		return vendorAddDeliveryExtraCharge;
	}

	public void setVendorAddDeliveryExtraCharge(int vendorAddDeliveryExtraCharge) {
		this.vendorAddDeliveryExtraCharge = vendorAddDeliveryExtraCharge;
	}

	public int getVendorAddDiscountAmount() {

		/*if (vendorAddDiscountAmount > 0 && "PAY".equals(this.orderType)) {
			vendorAddDiscountAmount = -(vendorAddDiscountAmount);
		}*/

		return vendorAddDiscountAmount;
	}

	public void setVendorAddDiscountAmount(int vendorAddDiscountAmount) {
		this.vendorAddDiscountAmount = vendorAddDiscountAmount;
	}

	public List<RevenueDetailItem> getItems() {
		return items;
	}

	public void setItems(List<RevenueDetailItem> items) {
		this.items = items;
	}

	public int getItemCouponDiscountAmount() {

		int amount = 0;
		for(RevenueDetailItem item : items) {
			amount += item.getItemCouponDiscountAmount();
		}

		return amount;
	}

	public int getShippingSequence() {
		return shippingSequence;
	}

	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
	}

	public int getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}
}
