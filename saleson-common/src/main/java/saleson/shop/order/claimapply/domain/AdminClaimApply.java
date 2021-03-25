package saleson.shop.order.claimapply.domain;

import java.util.HashMap;
import saleson.common.utils.CommonUtils;

public class AdminClaimApply {
	private String orderCode;
	private int orderSequence;
	private String claimType;

	
	private String[] adminClaimApplyKey;
	
	private HashMap<String, AdminClaimApplyItem> itemMap;
	
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
	
	public OrderReturnApply getOrderReturnApply() {
		
		OrderReturnApply apply = new OrderReturnApply();
		
		apply.setOrderCode(getOrderCode());
		apply.setOrderSequence(getOrderSequence());
		apply.setClaimStatus("01");
		apply.setClaimApplySubject("02");
		apply.setReturnReason(getCancelClaimReason());
		apply.setReturnReasonDetail(getCancelClaimReasonDetail());
		apply.setReturnReasonText(getCancelClaimReasonText());
		apply.setReturnShippingAskType("1");
		return apply;
	}
	
	
	
	public OrderExchangeApply getOrderExchangeApply() {
		
		OrderExchangeApply apply = new OrderExchangeApply();
		
		apply.setOrderCode(getOrderCode());
		apply.setOrderSequence(getOrderSequence());
		apply.setClaimStatus("01");
		apply.setClaimApplySubject("02");
		apply.setExchangeReason(getExchangeClaimReason());
		apply.setExchangeReasonDetail(getExchangeClaimReasonDetail());
		apply.setExchangeReasonText(getExchangeClaimReasonText());
		apply.setExchangeShippingAskType("1");
		return apply;
	}
	
	public OrderCancelApply getOrderCancelApply() {
		
		OrderCancelApply apply = new OrderCancelApply();
		
		apply.setOrderCode(getOrderCode());
		apply.setOrderSequence(getOrderSequence());
		apply.setClaimStatus("01");
		apply.setClaimApplySubject("02");
		apply.setCancelReason(getCancelClaimReason());
		apply.setCancelReasonDetail(getCancelClaimReasonDetail());
		apply.setCancelReasonText(getCancelClaimReasonText());
		
		return apply;
	}
	
	public HashMap<String, AdminClaimApplyItem> getItemMap() {
		return itemMap;
	}
	public void setItemMap(HashMap<String, AdminClaimApplyItem> itemMap) {
		this.itemMap = itemMap;
	}
	public String[] getAdminClaimApplyKey() {
		return CommonUtils.copy(adminClaimApplyKey);
	}
	public void setAdminClaimApplyKey(String[] adminClaimApplyKey) {
		this.adminClaimApplyKey = CommonUtils.copy(adminClaimApplyKey);
	}
	private String refundFlag;
	private String cancelClaimReason;
	private String cancelClaimReasonText;
	private String cancelClaimReasonDetail;
	
	private String returnClaimReason;
	private String returnClaimReasonText;
	private String returnClaimReasonDetail;

	private String exchangeClaimReason;
	private String exchangeClaimReasonText;
	private String exchangeClaimReasonDetail;
	
	public String getExchangeClaimReason() {
		return exchangeClaimReason;
	}
	public void setExchangeClaimReason(String exchangeClaimReason) {
		this.exchangeClaimReason = exchangeClaimReason;
	}
	public String getExchangeClaimReasonText() {
		return exchangeClaimReasonText;
	}
	public void setExchangeClaimReasonText(String exchangeClaimReasonText) {
		this.exchangeClaimReasonText = exchangeClaimReasonText;
	}
	public String getExchangeClaimReasonDetail() {
		return exchangeClaimReasonDetail;
	}
	public void setExchangeClaimReasonDetail(String exchangeClaimReasonDetail) {
		this.exchangeClaimReasonDetail = exchangeClaimReasonDetail;
	}
	public String getReturnClaimReason() {
		return returnClaimReason;
	}
	public void setReturnClaimReason(String returnClaimReason) {
		this.returnClaimReason = returnClaimReason;
	}
	public String getReturnClaimReasonText() {
		return returnClaimReasonText;
	}
	public void setReturnClaimReasonText(String returnClaimReasonText) {
		this.returnClaimReasonText = returnClaimReasonText;
	}
	public String getReturnClaimReasonDetail() {
		return returnClaimReasonDetail;
	}
	public void setReturnClaimReasonDetail(String returnClaimReasonDetail) {
		this.returnClaimReasonDetail = returnClaimReasonDetail;
	}
	public String getCancelClaimReason() {
		return cancelClaimReason;
	}
	public void setCancelClaimReason(String cancelClaimReason) {
		this.cancelClaimReason = cancelClaimReason;
	}
	public String getCancelClaimReasonText() {
		return cancelClaimReasonText;
	}
	public void setCancelClaimReasonText(String cancelClaimReasonText) {
		this.cancelClaimReasonText = cancelClaimReasonText;
	}
	public String getCancelClaimReasonDetail() {
		return cancelClaimReasonDetail;
	}
	public void setCancelClaimReasonDetail(String cancelClaimReasonDetail) {
		this.cancelClaimReasonDetail = cancelClaimReasonDetail;
	}
	public String getRefundFlag() {
		return refundFlag;
	}
	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}
}
