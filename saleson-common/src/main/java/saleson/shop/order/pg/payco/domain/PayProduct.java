package saleson.shop.order.pg.payco.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayProduct {
	private String orderProductNo;
	private String sellerOrderProductReferenceKey;
	private String orderProductStatusCode;
	private String orderProductStatusName;
	private String cpId;
	private String productId;
	private String productKindCode;
	private int productPaymentAmt;
	private int originalProductPaymentAmt;
	
	public String getOrderProductNo() {
		return orderProductNo;
	}
	public void setOrderProductNo(String orderProductNo) {
		this.orderProductNo = orderProductNo;
	}
	public String getSellerOrderProductReferenceKey() {
		return sellerOrderProductReferenceKey;
	}
	public void setSellerOrderProductReferenceKey(
			String sellerOrderProductReferenceKey) {
		this.sellerOrderProductReferenceKey = sellerOrderProductReferenceKey;
	}
	public String getOrderProductStatusCode() {
		return orderProductStatusCode;
	}
	public void setOrderProductStatusCode(String orderProductStatusCode) {
		this.orderProductStatusCode = orderProductStatusCode;
	}
	public String getOrderProductStatusName() {
		return orderProductStatusName;
	}
	public void setOrderProductStatusName(String orderProductStatusName) {
		this.orderProductStatusName = orderProductStatusName;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductKindCode() {
		return productKindCode;
	}
	public void setProductKindCode(String productKindCode) {
		this.productKindCode = productKindCode;
	}
	public int getProductPaymentAmt() {
		return productPaymentAmt;
	}
	public void setProductPaymentAmt(int productPaymentAmt) {
		this.productPaymentAmt = productPaymentAmt;
	}
	public int getOriginalProductPaymentAmt() {
		return originalProductPaymentAmt;
	}
	public void setOriginalProductPaymentAmt(int originalProductPaymentAmt) {
		this.originalProductPaymentAmt = originalProductPaymentAmt;
	}
}
