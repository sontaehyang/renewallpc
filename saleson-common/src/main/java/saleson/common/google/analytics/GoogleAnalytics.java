package saleson.common.google.analytics;

import java.util.List;

import saleson.common.utils.ShopUtils;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.order.domain.OrderItem;

public class GoogleAnalytics {
	 
	private String account = "";	// 계정 (PC : UA-829260-1, Mobile : UA-829260-6)
	private String orderId = ""; 	// 주문번호 
	private String storeName = "7esthe"; 	// 사이트명
	private String total = "";				// 구입금액（세금포함）
	private String tax = "";				// 소비세
	private String shipping = "";			// 배송료
	private String city = "";				// 주문자 주소 (도도부현)
	//private String state = "";				// state or province
	private String country = "JAPAN";		// 주문자주소（국적）- 'JAPAN' 고정.
	
	private String code = "";				// 상품번호 (복수는 ;로 구분)
	private String productName = "";		// 상품명 (복수는 ;로 구분)
	private String unitPrice = "";			// 각상품별 합계금액 (복수는 ;로 구분)
	private String quantity = "";			// 상품 수량 (복수는 ;로 구분)
	
	public GoogleAnalytics(OrderShippingInfo order, String deviceType) {
		/*
		List<OrderItem> list = order.getOrderItems();
		
		if (list == null) {
			return;
		}
		
		account = "UA-829260-1";
		if ("Mobile".equals(deviceType)) {
			account = "UA-829260-6";
		}
		
		orderId = order.getOrderCode();
		total = Integer.toString(order.getOrderPayAmount());
		tax = Integer.toString(order.getSumExcisePrice());
		shipping = Integer.toString(order.getOrderPayment().getSumDeliveryPrice());
		city = order.getDodobuhyun();
		
		int i = 0;
		for(OrderItem item : list) {
			
			if (i > 0) {
				code += ";";
				productName += ";";
				unitPrice += ";";
				quantity += ";";
			}
			
			code += item.getItemUserCode();
			productName += item.getItemName();
			unitPrice += ( item.getItemPrice() + item.getTotalRequiredOptionsPrice() ) * item.getQuantity();
			quantity += item.getQuantity();
			
			i++;
		}
		*/
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	/*
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	*/
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
