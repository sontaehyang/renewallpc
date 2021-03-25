package saleson.shop.order.claimapply.domain;

import java.util.HashMap;
import java.util.List;

import saleson.common.utils.CommonUtils;
import saleson.shop.order.addpayment.domain.OrderAddPayment;
import saleson.shop.order.domain.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClaimApply {
	
	private String separateCharges;
	public String getSeparateCharges() {
		return separateCharges;
	}
	public void setSeparateCharges(String separateCharges) {
		this.separateCharges = separateCharges;
	}

	private HttpServletRequest request;
	private HttpServletResponse response;

	private List<OrderAddPayment> addPayments;
	public List<OrderAddPayment> getAddPayments() {
		return addPayments;
	}
	public void setAddPayments(List<OrderAddPayment> addPayments) {
		this.addPayments = addPayments;
	}
	
	private String claimRefundType;
	public String getClaimRefundType() {
		return claimRefundType;
	}
	public void setClaimRefundType(String claimRefundType) {
		this.claimRefundType = claimRefundType;
	}
	private String refundCode;
	public String getRefundCode() {
		return refundCode;
	}
	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	private String claimType; // 1:취소, 2:환불, 3:교환
	private String claimStatus;
	private String claimReason;
	private String claimReasonDetail;
	
	private String returnBankName;
	private String returnBankInName;
	private String returnVirtualNo;
	
	public String getReturnBankName() {
		return returnBankName;
	}
	public void setReturnBankName(String returnBankName) {
		this.returnBankName = returnBankName;
	}
	public String getReturnBankInName() {
		return returnBankInName;
	}
	public void setReturnBankInName(String returnBankInName) {
		this.returnBankInName = returnBankInName;
	}
	public String getReturnVirtualNo() {
		return returnVirtualNo;
	}
	public void setReturnVirtualNo(String returnVirtualNo) {
		this.returnVirtualNo = returnVirtualNo;
	}
	public String getClaimReasonDetail() {
		return claimReasonDetail;
	}
	public void setClaimReasonDetail(String claimReasonDetail) {
		this.claimReasonDetail = claimReasonDetail;
	}
	private String claimReasonText;
	
	private Order order;
	private String[] id;
	
	// 관리자 페이지 처리용
	private String[] cancelIds;
	private HashMap<String, OrderCancelApply> cancelApplyMap;
	private HashMap<String, OrderCancelShipping> cancelShippingMap;
	public String[] getCancelIds() {
		return CommonUtils.copy(cancelIds);
	}
	public void setCancelIds(String[] cancelIds) {
		this.cancelIds = CommonUtils.copy(cancelIds);
	}
	public HashMap<String, OrderCancelApply> getCancelApplyMap() {
		return cancelApplyMap;
	}
	public void setCancelApplyMap(HashMap<String, OrderCancelApply> cancelApplyMap) {
		this.cancelApplyMap = cancelApplyMap;
	}
	public HashMap<String, OrderCancelShipping> getCancelShippingMap() {
		return cancelShippingMap;
	}
	public void setCancelShippingMap(
			HashMap<String, OrderCancelShipping> cancelShippingMap) {
		this.cancelShippingMap = cancelShippingMap;
	}
	
	private String[] returnIds;
	private HashMap<String, OrderReturnApply> returnApplyMap;
	public String[] getReturnIds() {
		return CommonUtils.copy(returnIds);
	}
	public void setReturnIds(String[] returnIds) {
		this.returnIds = CommonUtils.copy(returnIds);
	}
	public HashMap<String, OrderReturnApply> getReturnApplyMap() {
		return returnApplyMap;
	}
	public void setReturnApplyMap(HashMap<String, OrderReturnApply> returnApplyMap) {
		this.returnApplyMap = returnApplyMap;
	}
	
	private String[] exchangeIds;
	private HashMap<String, OrderExchangeApply> exchangeApplyMap;
	public String[] getExchangeIds() {
		return CommonUtils.copy(exchangeIds);
	}
	public void setExchangeIds(String[] exchangeIds) {
		this.exchangeIds = CommonUtils.copy(exchangeIds);
	}
	public HashMap<String, OrderExchangeApply> getExchangeApplyMap() {
		return exchangeApplyMap;
	}
	public void setExchangeApplyMap(
			HashMap<String, OrderExchangeApply> exchangeApplyMap) {
		this.exchangeApplyMap = exchangeApplyMap;
	}
	// 관리자 페이지 처리용
	
	private List<OrderCancelApply> orderCancelApplys;
	private List<OrderReturnApply> orderReturnApplys;
	public List<OrderReturnApply> getOrderReturnApplys() {
		return orderReturnApplys;
	}
	public void setOrderReturnApplys(List<OrderReturnApply> orderReturnApplys) {
		this.orderReturnApplys = orderReturnApplys;
	}
	private HashMap<String, ClaimApplyItem> claimApplyItemMap;
	public List<OrderCancelApply> getOrderCancelApplys() {
		return orderCancelApplys;
	}
	public void setOrderCancelApplys(List<OrderCancelApply> orderCancelApplys) {
		this.orderCancelApplys = orderCancelApplys;
	}
	public HashMap<String, ClaimApplyItem> getClaimApplyItemMap() {
		return claimApplyItemMap;
	}
	public void setClaimApplyItemMap(
			HashMap<String, ClaimApplyItem> claimApplyItemMap) {
		this.claimApplyItemMap = claimApplyItemMap;
	}
	public String[] getId() {
		return CommonUtils.copy(id);
	}
	public void setId(String[] id) {
		this.id = CommonUtils.copy(id);
	}
	public String getClaimReasonText() {
		return claimReasonText;
	}
	public void setClaimReasonText(String claimReasonText) {
		this.claimReasonText = claimReasonText;
	}
	public String getClaimReason() {
		return claimReason;
	}
	public void setClaimReason(String claimReason) {
		this.claimReason = claimReason;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
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
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
