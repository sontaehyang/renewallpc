package saleson.erp.service;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import saleson.erp.domain.ErpOrderType;
import saleson.shop.order.domain.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j @Data @ToString
public class ErpOrder {
	private ErpOrderType erpOrderType;
	private Buyer buyer;
	private List<OrderShippingInfo> orderShippingInfos = new ArrayList<>();
	private List<BuyItem> buyItems = new ArrayList<>();
	private List<OrderItem> orderItems = new ArrayList<>();

	private String payMethod;
	private String billType;

	public ErpOrder() {}

	public ErpOrder(ErpOrderType erpOrderType) {
		this.erpOrderType = erpOrderType;
	}

	public ErpOrder(Order order) {
		this(order, ErpOrderType.ORDER_CLAIM);
	}

	public ErpOrder(Order order, ErpOrderType erpOrderType) {
		this.erpOrderType = erpOrderType;

		Buyer buyer = new Buyer();
		buyer.setUserName("".equals(order.getUserName()) ? order.getBuyerName() : order.getUserName());
		buyer.setLoginId(order.getLoginId());
		buyer.setPhone(order.getPhone());
		buyer.setMobile(order.getMobile());
		buyer.setEmail(order.getEmail());
		this.setBuyer(buyer);

		order.getOrderShippingInfos().stream()
				.forEach(osi -> this.addOrderItems(osi.getOrderItems()));

		this.setOrderShippingInfos(order.getOrderShippingInfos());

		String payMethod = "";
		for (OrderPayment orderPayment : order.getOrderPayments()) {
			if ("point".equals(orderPayment.getApprovalType())) {
				payMethod = "포인트";
			} else  if ("card".equals(orderPayment.getApprovalType())) {
				payMethod = "카드";
			} else if ("realtimebank".equals(orderPayment.getApprovalType())) {
				payMethod = "실시간 계좌이체";
			} else  if ("vbank".equals(orderPayment.getApprovalType())) {
				payMethod = "가상계좌";
			} else if ("naverpay".equals(orderPayment.getApprovalType())) {
				payMethod = "네이버페이";
			} else if ("rentalpay".equals(orderPayment.getApprovalType())) {
				payMethod = "렌탈페이";
			}
		}

		this.setPayMethod(payMethod);
	}

	public void addOrderShippingInfo(OrderShippingInfo orderShippingInfo) {
		this.orderShippingInfos.add(orderShippingInfo);
	}

	public void addBuyItems(List<BuyItem> buyItems) {
		buyItems.stream().forEach(this::addBuyItem);
	}

	public void addBuyItem(BuyItem buyItem) {
		this.buyItems.add(buyItem);
	}

	public void addOrderItems(List<OrderItem> buyItems) {
		buyItems.stream().forEach(this::addOrderItem);
	}

	public void addOrderItem(OrderItem orderItem) {
		if ("0".equals(orderItem.getOrderStatus())) {
			orderItem.setOrderStatus("10");
		}
		this.orderItems.add(orderItem);
	}
}
