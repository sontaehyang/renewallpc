package saleson.shop.order.claimapply.domain;

import java.util.HashMap;

import saleson.common.utils.CommonUtils;
import saleson.shop.order.domain.OrderItem;

public class OrderCancelApply {

	private String refundCancelFlag;
	private int copyItemSequence;
	public int getCopyItemSequence() {
		return copyItemSequence;
	}
	public void setCopyItemSequence(int copyItemSequence) {
		this.copyItemSequence = copyItemSequence;
	}

	private String[] id;
	private HashMap<String, Integer> itemSequenceMap;

	private boolean isSalesCancel;
	public boolean isSalesCancel() {
		return isSalesCancel;
	}
	public void setSalesCancel(boolean isSalesCancel) {
		this.isSalesCancel = isSalesCancel;
	}

	private int deliveryCompanyId;
	private String deliveryNumber;
	public int getDeliveryCompanyId() {
		return deliveryCompanyId;
	}
	public void setDeliveryCompanyId(int deliveryCompanyId) {
		this.deliveryCompanyId = deliveryCompanyId;
	}
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	public HashMap<String, Integer> getItemSequenceMap() {
		return itemSequenceMap;
	}
	public void setItemSequenceMap(HashMap<String, Integer> itemSequenceMap) {
		this.itemSequenceMap = itemSequenceMap;
	}
	public String[] getId() {
		return CommonUtils.copy(id);
	}
	public void setId(String[] id) {
		this.id = CommonUtils.copy(id);
	}


	private long userId;
	private String loginId;
	private String buyerName;
	private String receiveName;
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
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

	public OrderCancelApply() {}
	public OrderCancelApply(ClaimApply claimApply) {
		setOrderCode(claimApply.getOrderCode());
		setOrderSequence(claimApply.getOrderSequence());
		setClaimStatus("01");
		setClaimApplySubject("01");
		setCancelReason(claimApply.getClaimReason());
		setCancelReasonDetail(claimApply.getClaimReasonDetail());
		setCancelReasonText(claimApply.getClaimReasonText());
	}

	private OrderItem orderItem;
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	private String claimCode;

	private String orderCode;
	private int orderSequence;
	private int itemSequence;

	private String cancelApplyDate;
	private String claimStatus;
	public String getClaimStatusLabel() {

		if ("01".equals(claimStatus)) {
			return "신청";
		} else if ("02".equals(claimStatus)) {
			return "보류";
		} else if ("03".equals(claimStatus)) {
			return "환불대기";
		} else if ("04".equals(claimStatus)) {
			return "완료";
		} else if ("99".equals(claimStatus)) {
			return "거절";
		}

		return "-";
	}

	private int claimApplyAmount;
	public int getClaimApplyAmount() {
		return claimApplyAmount;
	}
	public void setClaimApplyAmount(int claimApplyAmount) {
		this.claimApplyAmount = claimApplyAmount;
	}

	private String claimApplySubject;
	private int claimApplyQuantity;
	private String cancelReason;
	private String cancelReasonText;
	private String cancelReasonDetail;
	private String cancelMemo;
	private String cancelRefusalReasonText;
	private String createdDate;

	private String refundCode;

	private int shippingSequence;

	private int parentItemSequence;

	public int getParentItemSequence() {
		return parentItemSequence;
	}
	public void setParentItemSequence(int parentItemSequence) {
		this.parentItemSequence = parentItemSequence;
	}

	public int getShippingSequence() {
		return shippingSequence;
	}
	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
	}

	public String getRefundCode() {
		return refundCode;
	}
	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public String getClaimCode() {
		return claimCode;
	}
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public String getClaimApplySubject() {
		return claimApplySubject;
	}
	public void setClaimApplySubject(String claimApplySubject) {
		this.claimApplySubject = claimApplySubject;
	}
	public int getClaimApplyQuantity() {
		return claimApplyQuantity;
	}
	public void setClaimApplyQuantity(int claimApplyQuantity) {
		this.claimApplyQuantity = claimApplyQuantity;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getCancelReasonText() {
		return cancelReasonText;
	}
	public void setCancelReasonText(String cancelReasonText) {
		this.cancelReasonText = cancelReasonText;
	}
	public String getCancelMemo() {
		return cancelMemo;
	}
	public void setCancelMemo(String cancelMemo) {
		this.cancelMemo = cancelMemo;
	}
	public String getCancelRefusalReasonText() {
		return cancelRefusalReasonText;
	}
	public void setCancelRefusalReasonText(String cancelRefusalReasonText) {
		this.cancelRefusalReasonText = cancelRefusalReasonText;
	}
	public String getCancelApplyDate() {
		return cancelApplyDate;
	}
	public void setCancelApplyDate(String cancelApplyDate) {
		this.cancelApplyDate = cancelApplyDate;
	}
	public String getCancelReasonDetail() {
		return cancelReasonDetail;
	}
	public void setCancelReasonDetail(String cancelReasonDetail) {
		this.cancelReasonDetail = cancelReasonDetail;
	}
	public String getRefundCancelFlag() {
		return refundCancelFlag;
	}
	public void setRefundCancelFlag(String refundCancelFlag) {
		this.refundCancelFlag = refundCancelFlag;
	}
}
