package saleson.shop.order.domain;

import java.util.List;

import saleson.common.utils.CommonUtils;
import saleson.common.utils.UserUtils;


public class OrderCancelApply {
	
	private String orderStatus;
	
	private int orderCancelApplyId;
	
	private long userId;
	private long sellerId;
	private String mode;
	
	private int orderShippingId;
	private String orderCode;
	
	private int orderItemId;
	private int applyQuantity;
	
	private boolean isWriteBankInfo;
	private List<OrderPayment> payments;
	
	private String conditionType;
	
	// 환불 계좌 정보
	private String cancelBankName;
	private String cancelVirtualNo;
	private String cancelBankInName;
	
	private String cancelReason;
	private String cancelReasonText;
	private String cancelMemo;
	
	private String adminUserName;
	
	private String cancelRefusalReasonText;

	/**
	 * 배송 처리
	 */
	private int cancelDeliveryCompanyId;
	private String cancelDeliveryNumber;
	private String cancelDeliveryCompanyName;
	private String cancelDeliveryCompanyUrl;
	
	private int[] ids;
	private int[] applyQuantitys;
	private int[] orderItemIds;
	
	private OrderItem orderItem;
	
	private String bankListKey;
	public String getBankListKey() {
		return bankListKey;
	}

	public void setBankListKey(String bankListKey) {
		this.bankListKey = bankListKey;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int[] getIds() {
		return CommonUtils.copy(ids);
	}

	public void setIds(int[] ids) {
		this.ids = CommonUtils.copy(ids);
	}

	public int[] getApplyQuantitys() {
		return CommonUtils.copy(applyQuantitys);
	}

	public void setApplyQuantitys(int[] applyQuantitys) {
		this.applyQuantitys = CommonUtils.copy(applyQuantitys);
	}

	public int[] getOrderItemIds() {
		return CommonUtils.copy(orderItemIds);
	}

	public void setOrderItemIds(int[] orderItemIds) {
		this.orderItemIds = CommonUtils.copy(orderItemIds);
	}
	public int getApplyQuantity() {
		return applyQuantity;
	}

	public void setApplyQuantity(int applyQuantity) {
		this.applyQuantity = applyQuantity;
	}

	public String getCancelDeliveryCompanyName() {
		return cancelDeliveryCompanyName;
	}

	public void setCancelDeliveryCompanyName(String cancelDeliveryCompanyName) {
		this.cancelDeliveryCompanyName = cancelDeliveryCompanyName;
	}

	public String getCancelDeliveryCompanyUrl() {
		return cancelDeliveryCompanyUrl;
	}

	public void setCancelDeliveryCompanyUrl(String cancelDeliveryCompanyUrl) {
		this.cancelDeliveryCompanyUrl = cancelDeliveryCompanyUrl;
	}

	public int getCancelDeliveryCompanyId() {
		return cancelDeliveryCompanyId;
	}

	public void setCancelDeliveryCompanyId(int cancelDeliveryCompanyId) {
		this.cancelDeliveryCompanyId = cancelDeliveryCompanyId;
	}

	public String getCancelDeliveryNumber() {
		return cancelDeliveryNumber;
	}

	public void setCancelDeliveryNumber(String cancelDeliveryNumber) {
		this.cancelDeliveryNumber = cancelDeliveryNumber;
	}

	public String getCancelRefusalReasonText() {
		return cancelRefusalReasonText;
	}

	public void setCancelRefusalReasonText(String cancelRefusalReasonText) {
		this.cancelRefusalReasonText = cancelRefusalReasonText;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCancelMemo() {
		return cancelMemo;
	}

	public void setCancelMemo(String cancelMemo) {
		this.cancelMemo = cancelMemo;
	}

	public int getOrderCancelApplyId() {
		return orderCancelApplyId;
	}

	public void setOrderCancelApplyId(int orderCancelApplyId) {
		this.orderCancelApplyId = orderCancelApplyId;
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

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public int getOrderShippingId() {
		return orderShippingId;
	}

	public void setOrderShippingId(int orderShippingId) {
		this.orderShippingId = orderShippingId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public boolean isWriteBankInfo() {
		return isWriteBankInfo;
	}

	public void setWriteBankInfo(boolean isWriteBankInfo) {
		this.isWriteBankInfo = isWriteBankInfo;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<OrderPayment> getPayments() {
		return payments;
	}

	public void setPayments(List<OrderPayment> payments) {
		this.payments = payments;
	}

	public String getCancelBankName() {
		return cancelBankName;
	}

	public void setCancelBankName(String cancelBankName) {
		this.cancelBankName = cancelBankName;
	}

	public String getCancelVirtualNo() {
		return cancelVirtualNo;
	}

	public void setCancelVirtualNo(String cancelVirtualNo) {
		this.cancelVirtualNo = cancelVirtualNo;
	}

	public String getCancelBankInName() {
		return cancelBankInName;
	}

	public void setCancelBankInName(String cancelBankInName) {
		this.cancelBankInName = cancelBankInName;
	}
	
	public boolean getIsLogin() {
		
		if (this.userId > 0) {
			return true;
		}
		
		return UserUtils.isUserLogin();
	}
}
