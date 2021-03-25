package saleson.shop.order.claimapply.domain;

import java.util.List;

import org.springframework.util.StringUtils;

import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShipping;

public class OrderCancelShipping {
	
	public OrderCancelShipping() {}
	public OrderCancelShipping(OrderShipping orderShipping) {
		setShippingSequence(orderShipping.getShippingSequence());
	}
	
	private String orderCode;
	private int orderSequence;
	private int shippingSequence;
	
	private String rePayShipping;
	private String rePayShippingPaymentType;
	public String getRePayShippingPaymentType() {
		if (StringUtils.isEmpty(rePayShippingPaymentType)) {
			return "1";
		}
		
		return rePayShippingPaymentType;
	}
	public void setRePayShippingPaymentType(String rePayShippingPaymentType) {
		this.rePayShippingPaymentType = rePayShippingPaymentType;
	}
	public String getRePayShipping() {
		return rePayShipping;
	}
	public void setRePayShipping(String rePayShipping) {
		this.rePayShipping = rePayShipping;
	}

	private List<OrderItem> orderItems;
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
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
	public int getShippingSequence() {
		return shippingSequence;
	}
	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
	}
}
