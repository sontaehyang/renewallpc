package saleson.shop.order.claimapply.domain;

import java.util.List;

import saleson.seller.main.domain.Seller;
import saleson.shop.order.domain.OrderItem;

public class OrderReturnSeller {
	
	public OrderReturnSeller() {}
	public OrderReturnSeller(Seller seller) {
		setSellerId(seller.getSellerId());
		setSeller(seller);
	}
	
	private String orderCode;
	private int orderSequence;
	private long sellerId;
	private Seller seller;
	
	private List<OrderItem> orderItems;
	
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
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
}
