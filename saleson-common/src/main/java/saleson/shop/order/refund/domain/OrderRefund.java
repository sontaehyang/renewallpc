package saleson.shop.order.refund.domain;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import saleson.common.utils.PointUtils;
import saleson.shop.order.claimapply.domain.ClaimApply;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.support.ReturnApply;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderPayment;
import saleson.shop.order.domain.OrderPgData;

public class OrderRefund {
	
	private String claimCode;
	public String getClaimCode() {
		return claimCode;
	}
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}
	public OrderRefund() {}
	public OrderRefund(ClaimApply claimApply) {
		setOrderCode(claimApply.getOrderCode());
		setOrderSequence(claimApply.getOrderSequence());
	}
	
	public OrderRefund(ReturnApply returnApply) {
		setOrderCode(returnApply.getOrderCode());
		setOrderSequence(returnApply.getOrderSequence());
	}
	
	private int totalAddShippingAmount;
	private int totalItemReturnAmount;
	
	public int getTotalItemReturnAmount() {
		return totalItemReturnAmount;
	}
	public void setTotalItemReturnAmount(int totalItemReturnAmount) {
		this.totalItemReturnAmount = totalItemReturnAmount;
	}
	public int getTotalAddShippingAmount() {
		return totalAddShippingAmount;
	}
	public void setTotalAddShippingAmount(int totalAddShippingAmount) {
		this.totalAddShippingAmount = totalAddShippingAmount;
	}
	public boolean isAutoCancel() {
		
		boolean isAutoCancel = true;
		
		// 사용자 환불 처리 가능 여부
		int payTotal = 0;
		int mallPayCount = 0;
		int paymentCount = 0;

		for(OrderPayment orderPayment : getOrderPayments()) {
			if (orderPayment.getRemainingAmount() > 0 && "1".equals(orderPayment.getPaymentType())) {
				OrderPgData pg = orderPayment.getOrderPgData();
				if (pg != null) {
					if ("N".equals(pg.getPartCancelFlag())) {
						isAutoCancel = false;
						paymentCount++;
						break;
					}
				}

				// 무통장결제가 있으면 무조건 바로 취소 안됨
				if ("bank".equals(orderPayment.getApprovalType())) {
					isAutoCancel = false;
                    paymentCount++;
					break;
				}
				
				if (PointUtils.isPointType(orderPayment.getApprovalType())) {
					mallPayCount++;
				}
				
				payTotal += orderPayment.getRemainingAmount();
				paymentCount++;
			}

			// 포인트 + 부분취소 불가능한 결제수단일 경우 - 첫번째 부분취소 후 다음 부분취소 시 전액 포인트 결제조건이 true가 돼서 즉시 취소가 됨. 즉시 취소 안되게 조건 추가 함.
			if ("1".equals(orderPayment.getPaymentType()) && orderPayment.getRemainingAmount() == 0 && PointUtils.isPointType(orderPayment.getApprovalType())) {
				paymentCount++;
				mallPayCount++;
			}
		}
		
		if (payTotal < getTotalReturnAmount()) {
			isAutoCancel = false;
		}
		
		if (isAutoCancel) {
			
			// 단일 결제 타입의 경우만 즉시 취소가 가능
			if (paymentCount != mallPayCount + 1) {
				isAutoCancel = false;
			}
		}
		
		// 주문상태가 결제완료가 아니면 즉시취소 불가 
		for(OrderRefundDetail detail : groups) {
			for(OrderCancelApply apply : detail.getOrderCancelApplys()) {
				OrderItem orderItem = apply.getOrderItem();
				if (!"10".equals(orderItem.getOrderStatus())) {
					isAutoCancel = false;
					break;
				}
			}
		}

        // 전액 포인트 결제일 때 자동환불처리
        if(paymentCount == 1 && "point".equals(orderPayments.get(0).getApprovalType())) {
            for(OrderPayment orderPayment : getOrderPayments()) {
                isAutoCancel = true;
                isAllPaidByPoint = true;
            }
        }
		
		if (isAutoCancel) {
			for(OrderPayment orderPayment : getOrderPayments()) {
				if ("vbank".equals(orderPayment.getApprovalType())) {
					isWriteBankInfo = true;
					break;
				}
			}
		}
		
		return isAutoCancel;
	}
    // 전액 포인트 결제인지 여부
    private boolean isAllPaidByPoint = false;
    public boolean isAllPaidByPoint() {
        return isAllPaidByPoint;
    }

	private boolean isWriteBankInfo;
	public boolean isWriteBankInfo() {
		return isWriteBankInfo;
	}
	public void setWriteBankInfo(boolean isWriteBankInfo) {
		this.isWriteBankInfo = isWriteBankInfo;
	}

	private String refundCode;
	private String refundDate;
	
	private String orderCode;
	private int orderSequence;
	private String refundStatusCode;
	private String requestManagerUserName;
	private String processManagerUserName;
	private String returnBankName;
	private String returnVirtualNo;
	private String returnBankInName;
	private String createdDate;
	private String updatedDate;
	
	private long userId;
	private String loginId;
	private String buyerName;
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	private List<OrderRefundDetail> groups;
	
	private List<OrderPayment> orderPayments;

	private int totalOrderQuantity;

	private int returnPayAmount;

	public int getTotalReturnAmount() {

		if (groups == null) {
			return 0;
		}
		
		int totalReturnAmount = 0;
		for(OrderRefundDetail detail : groups) {
			totalReturnAmount += detail.getReturnAmount();
		}
		
		return totalReturnAmount;
	}
	
	public List<OrderPayment> getOrderPayments() {
		return orderPayments;
	}
	public void setOrderPayments(List<OrderPayment> orderPayments) {
		this.orderPayments = orderPayments;
	}
	public List<OrderRefundDetail> getGroups() {
		return groups;
	}
	public void setGroups(List<OrderRefundDetail> groups) {
		this.groups = groups;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}
	public String getRefundCode() {
		return refundCode;
	}
	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
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
	public String getRefundStatusCode() {
		return refundStatusCode;
	}
	public void setRefundStatusCode(String refundStatusCode) {
		this.refundStatusCode = refundStatusCode;
	}
	public String getRequestManagerUserName() {
		return requestManagerUserName;
	}
	public void setRequestManagerUserName(String requestManagerUserName) {
		this.requestManagerUserName = requestManagerUserName;
	}
	public String getProcessManagerUserName() {
		return processManagerUserName;
	}
	public void setProcessManagerUserName(String processManagerUserName) {
		this.processManagerUserName = processManagerUserName;
	}
	public String getReturnBankName() {
		return returnBankName;
	}
	public void setReturnBankName(String returnBankName) {
		this.returnBankName = returnBankName;
	}
	public String getReturnVirtualNo() {
		return returnVirtualNo;
	}
	public void setReturnVirtualNo(String returnVirtualNo) {
		this.returnVirtualNo = returnVirtualNo;
	}
	public String getReturnBankInName() {
		return returnBankInName;
	}
	public void setReturnBankInName(String returnBankInName) {
		this.returnBankInName = returnBankInName;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getRefundStatusLabel() {
		
		if ("1".equals(getRefundStatusCode())) {
			return "신청";
		} else if ("2".equals(getRefundStatusCode())) {
			return "완료";
		}
		
		return "-";
	}

	public int getTotalOrderQuantity() { return totalOrderQuantity;	}
	public void setTotalOrderQuantity(int totalOrderQuantity) { this.totalOrderQuantity = totalOrderQuantity; }

	public int getReturnPayAmount() { return returnPayAmount; }
	public void setReturnPayAmount(int returnPayAmount) { this.returnPayAmount = returnPayAmount; }
}
