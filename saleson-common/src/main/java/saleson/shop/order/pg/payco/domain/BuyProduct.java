package saleson.shop.order.pg.payco.domain;

import saleson.shop.order.domain.BuyItem;
import saleson.shop.order.domain.ItemPrice;

public class BuyProduct {
	private String cpId;	// 상점 아이디 - PAYCO에서 발급
	private String productId; // 상품 아이디 - PAYCO에서 발급
	private int productAmt;
	private int productPaymentAmt;
	private int sortOrdering;
	private String productName;
	private int orderQuantity;
	private String sellerOrderProductReferenceKey;
	
	public BuyProduct() {}
	public BuyProduct(BuyItem buyItem, String cpId, String productId, int sortOrdering) {
		
		ItemPrice itemPrice = buyItem.getItemPrice();
		
		this.cpId = cpId;
		this.productId = productId;
		this.productAmt = itemPrice.getSaleAmount();
		this.productPaymentAmt = itemPrice.getSaleAmount();
		this.sortOrdering = sortOrdering;
		this.productName = buyItem.getItemName();
		this.orderQuantity = itemPrice.getQuantity();
		this.sellerOrderProductReferenceKey = buyItem.getItemUserCode();
		
	}
	
	public BuyProduct(String cpId, String productId, int payShipping, int sortOrdering) {
		
		this.cpId = cpId;
		this.productId = productId;
		this.productAmt = payShipping;
		this.productPaymentAmt = payShipping;
		this.sortOrdering = sortOrdering;
		this.productName = "배송비";
		this.orderQuantity = 1;
		this.sellerOrderProductReferenceKey = "SHIPPING-ITEM";
		
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
	public int getProductAmt() {
		return productAmt;
	}
	public void setProductAmt(int productAmt) {
		this.productAmt = productAmt;
	}
	public int getProductPaymentAmt() {
		return productPaymentAmt;
	}
	public void setProductPaymentAmt(int productPaymentAmt) {
		this.productPaymentAmt = productPaymentAmt;
	}
	public int getSortOrdering() {
		return sortOrdering;
	}
	public void setSortOrdering(int sortOrdering) {
		this.sortOrdering = sortOrdering;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getSellerOrderProductReferenceKey() {
		return sellerOrderProductReferenceKey;
	}
	public void setSellerOrderProductReferenceKey(
			String sellerOrderProductReferenceKey) {
		this.sellerOrderProductReferenceKey = sellerOrderProductReferenceKey;
	}

}
