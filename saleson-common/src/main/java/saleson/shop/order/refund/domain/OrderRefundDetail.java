package saleson.shop.order.refund.domain;

import java.util.ArrayList;
import java.util.List;

import saleson.seller.main.domain.Seller;
import saleson.shop.order.addpayment.domain.OrderAddPayment;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.domain.OrderItem;

import com.onlinepowers.framework.util.DateUtils;

public class OrderRefundDetail {

	public OrderRefundDetail() {}
	public OrderRefundDetail(Seller seller, List<OrderCancelApply> cancelList, List<OrderReturnApply> returnList, List<OrderAddPayment> addPaymentList) {
		
		setSeller(seller);
		
		List<OrderCancelApply> newCancelList = new ArrayList<>();
		List<OrderReturnApply> newReturnList = new ArrayList<>();
		List<OrderAddPayment> newAddPaymentList = new ArrayList<>();
		
		// 업체별 취소 정보
		int returnAmount = 0;
		if (cancelList != null) {
			
			int totalAmount = 0;
			for(OrderCancelApply apply : cancelList) {
				OrderItem orderItem = apply.getOrderItem();
				long id = orderItem.getSellerId();
				
				if (seller.getSellerId() == id) {
					
					int amount = orderItem.getSalePrice() * apply.getClaimApplyQuantity();
					
					// 상품 환불금액
					apply.setClaimApplyAmount(amount);
					newCancelList.add(apply);
					
					totalAmount += amount;
				}
			}
			
			if (totalAmount != 0) {
				OrderAddPayment orderAddPayment = new OrderAddPayment();
				orderAddPayment.setAddPaymentType("2");
				orderAddPayment.setAmount(totalAmount);
				orderAddPayment.setSellerId(seller.getSellerId());
				orderAddPayment.setSalesDate(DateUtils.getToday());
				orderAddPayment.setSubject("상품 취소 금액");
				orderAddPayment.setIssueCode("NO_INSERT");
				addPaymentList.add(orderAddPayment);
			}
		}
		
		if (returnList != null) {
			
			int totalAmount = 0;
			for(OrderReturnApply apply : returnList) {
				OrderItem orderItem = apply.getOrderItem();
				long id = orderItem.getSellerId();

				if (seller.getSellerId() == id) {
					
					int amount = orderItem.getSalePrice() * apply.getClaimApplyQuantity();
					
					// 상품 환불금액
					apply.setClaimApplyAmount(amount);
					newReturnList.add(apply);
					
					totalAmount += amount;
				}
			}
			
			if (totalAmount != 0) {
				OrderAddPayment orderAddPayment = new OrderAddPayment();
				orderAddPayment.setAddPaymentType("2");
				orderAddPayment.setAmount(totalAmount);
				orderAddPayment.setSellerId(seller.getSellerId());
				orderAddPayment.setSalesDate(DateUtils.getToday());
				orderAddPayment.setSubject("상품 환불 금액");
				orderAddPayment.setIssueCode("NO_INSERT");
				addPaymentList.add(orderAddPayment);
			}
		}
		
		// 업체별 추가 결제 정보
		for(OrderAddPayment payment : addPaymentList) {
			
			System.out.println("!!->" + ("1".equals(payment.getAddPaymentType()) ? "추가" : "환불") + "/" + payment.getSellerId() + "-" + payment.getSubject() + "/" + payment.getAmount());
			
			if (seller.getSellerId() == payment.getSellerId()) {
				returnAmount += "1".equals(payment.getAddPaymentType()) ? -payment.getAmount() : payment.getAmount();
				newAddPaymentList.add(payment);
			}
		}
		
		setOrderAddPayments(newAddPaymentList);
		setOrderCancelApplys(newCancelList);
		setOrderReturnApplys(newReturnList);
		setReturnAmount(returnAmount);
	}
	
	private Seller seller;
	private List<OrderCancelApply> orderCancelApplys;
	private List<OrderReturnApply> orderReturnApplys;
	private List<OrderAddPayment> orderAddPayments;
	
	private int returnAmount;
	public int getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(int returnAmount) {
		this.returnAmount = returnAmount;
	}
	public List<OrderAddPayment> getOrderAddPayments() {
		return orderAddPayments;
	}
	public void setOrderAddPayments(List<OrderAddPayment> orderAddPayments) {
		this.orderAddPayments = orderAddPayments;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public List<OrderCancelApply> getOrderCancelApplys() {
		return orderCancelApplys;
	}
	public void setOrderCancelApplys(List<OrderCancelApply> orderCancelApplys) {
		this.orderCancelApplys = orderCancelApplys;
	}
	public List<OrderReturnApply> getOrderReturnApplys() {
		return orderReturnApplys;
	}
	public void setOrderReturnApplys(List<OrderReturnApply> orderReturnApplys) {
		this.orderReturnApplys = orderReturnApplys;
	}
	
}
