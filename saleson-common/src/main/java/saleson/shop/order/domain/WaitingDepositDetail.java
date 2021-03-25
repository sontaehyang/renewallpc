package saleson.shop.order.domain;

import java.util.List;

import saleson.seller.main.domain.Seller;

public class WaitingDepositDetail {
	private long sellerId;
	private Seller sellerInfo;
	private List<OrderItem> orderItems;
	
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public Seller getSellerInfo() {
		return sellerInfo;
	}
	public void setSellerInfo(Seller sellerInfo) {
		this.sellerInfo = sellerInfo;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	
}
