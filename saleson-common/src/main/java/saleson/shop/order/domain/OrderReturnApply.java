package saleson.shop.order.domain;

import java.util.List;

import saleson.common.utils.UserUtils;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;

public class OrderReturnApply {
	private long userId;
	private long sellerId;
	
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	
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

	private int applyQuantity;
	
	private int orderReturnApplyId;
	private String returnShippingStartFlag = "Y";
	private int returnShippingDeliveryCompanyId;
	private String returnShippingCompanyName;
	private String returnShippingNumber;
	private String returnShippingCompanyUrl;
	private String returnShippingStartDate;

	
	
	private String returnBankName;
	private String returnBankInName;
	private String returnVirtualNo;

	private String returnReason;
	private String returnReasonText;
	private String returnMemo;
	
	// 환불 거절 사유
	private String returnRefusalReasonText;
	
	private OrderItem orderItem;
	private boolean isWriteBankInfo;
	private List<OrderPayment> payments;
	private ShipmentReturn shipmentReturn;
	
	// 무료배송?
	private boolean isFreeShipping = false;
	
	
	// 실제 계산된 배송비 - 환불 사유에 영향받지 않음
	private int returnRealShipping;
	
	private String conditionType;
	
	// 발송 택배비 - 무료배송인경우
	private int sendShipping;
	
	// 회수 택배비
	private int collectionShipping;
	
	private List<DeliveryCompany> deliveryCompanys;
	
	private String bankListKey;
	
	private String returnReserveName;
	private String returnReservePhone;
	private String returnReserveMobile;
	private String returnReserveZipcode;
	private String returnReserveSido;
	private String returnReserveSigungu;
	private String returnReserveEupmyeondong;
	private String returnReserveAddress;
	private String returnReserveAddress2;
	
	private String returnReserveZipcode1;
	private String returnReserveZipcode2;
	
	public String getBankListKey() {
		return bankListKey;
	}

	public void setBankListKey(String bankListKey) {
		this.bankListKey = bankListKey;
	}
	
	public int getApplyQuantity() {
		return applyQuantity;
	}
	public void setApplyQuantity(int applyQuantity) {
		this.applyQuantity = applyQuantity;
	}
	public String getReturnRefusalReasonText() {
		return returnRefusalReasonText;
	}
	public void setReturnRefusalReasonText(String returnRefusalReasonText) {
		this.returnRefusalReasonText = returnRefusalReasonText;
	}
	public String getReturnShippingStartFlag() {
		return returnShippingStartFlag;
	}
	public void setReturnShippingStartFlag(String returnShippingStartFlag) {
		this.returnShippingStartFlag = returnShippingStartFlag;
	}
	public int getReturnShippingDeliveryCompanyId() {
		return returnShippingDeliveryCompanyId;
	}
	public void setReturnShippingDeliveryCompanyId(
			int returnShippingDeliveryCompanyId) {
		this.returnShippingDeliveryCompanyId = returnShippingDeliveryCompanyId;
	}

	public String getReturnShippingCompanyName() {
		return returnShippingCompanyName;
	}
	public void setReturnShippingCompanyName(String returnShippingCompanyName) {
		this.returnShippingCompanyName = returnShippingCompanyName;
	}
	public String getReturnShippingNumber() {
		return returnShippingNumber;
	}
	public void setReturnShippingNumber(String returnShippingNumber) {
		this.returnShippingNumber = returnShippingNumber;
	}
	public String getReturnShippingCompanyUrl() {
		return returnShippingCompanyUrl;
	}
	public void setReturnShippingCompanyUrl(String returnShippingCompanyUrl) {
		this.returnShippingCompanyUrl = returnShippingCompanyUrl;
	}
	public String getReturnShippingStartDate() {
		
		if (StringUtils.isEmpty(returnShippingStartDate)) {
			return DateUtils.getToday();
		}
		
		return returnShippingStartDate;
	}
	public void setReturnShippingStartDate(String returnShippingStartDate) {
		this.returnShippingStartDate = returnShippingStartDate;
	}
	public String getReturnMemo() {
		return returnMemo;
	}
	public void setReturnMemo(String returnMemo) {
		this.returnMemo = returnMemo;
	}
	public int getOrderReturnApplyId() {
		return orderReturnApplyId;
	}
	public void setOrderReturnApplyId(int orderReturnApplyId) {
		this.orderReturnApplyId = orderReturnApplyId;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public String getReturnReasonText() {
		return returnReasonText;
	}
	public void setReturnReasonText(String returnReasonText) {
		this.returnReasonText = returnReasonText;
	}
	public boolean isFreeShipping() {
		return isFreeShipping;
	}
	public void setFreeShipping(boolean isFreeShipping) {
		this.isFreeShipping = isFreeShipping;
	}
	public int getReturnRealShipping() {
		return returnRealShipping;
	}
	public void setReturnRealShipping(int returnRealShipping) {
		this.returnRealShipping = returnRealShipping;
	}
	public int getSendShipping() {
		return sendShipping;
	}
	public void setSendShipping(int sendShipping) {
		this.sendShipping = sendShipping;
	}
	public int getCollectionShipping() {
		return collectionShipping;
	}
	public void setCollectionShipping(int collectionShipping) {
		this.collectionShipping = collectionShipping;
	}
	public List<DeliveryCompany> getDeliveryCompanys() {
		return deliveryCompanys;
	}
	public void setDeliveryCompanys(List<DeliveryCompany> deliveryCompanys) {
		this.deliveryCompanys = deliveryCompanys;
	}
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
	
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public boolean isWriteBankInfo() {
		return isWriteBankInfo;
	}
	public void setWriteBankInfo(boolean isWriteBankInfo) {
		this.isWriteBankInfo = isWriteBankInfo;
	}
	public List<OrderPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<OrderPayment> payments) {
		this.payments = payments;
	}
	public ShipmentReturn getShipmentReturn() {
		return shipmentReturn;
	}
	public void setShipmentReturn(ShipmentReturn shipmentReturn) {
		this.shipmentReturn = shipmentReturn;
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
	
	public boolean getIsLogin() {
		
		if (this.userId > 0) {
			return true;
		}
		
		return UserUtils.isUserLogin();
	}

	public String getReturnReserveName() {
		return returnReserveName;
	}

	public void setReturnReserveName(String returnReserveName) {
		this.returnReserveName = returnReserveName;
	}

	public String getReturnReservePhone() {
		return returnReservePhone;
	}

	public void setReturnReservePhone(String returnReservePhone) {
		this.returnReservePhone = returnReservePhone;
	}

	public String getReturnReserveMobile() {
		return returnReserveMobile;
	}

	public void setReturnReserveMobile(String returnReserveMobile) {
		this.returnReserveMobile = returnReserveMobile;
	}

	public String getReturnReserveZipcode() {
		return returnReserveZipcode;
	}

	public void setReturnReserveZipcode(String returnReserveZipcode) {
		this.returnReserveZipcode = returnReserveZipcode;
	}

	public String getReturnReserveSido() {
		return returnReserveSido;
	}

	public void setReturnReserveSido(String returnReserveSido) {
		this.returnReserveSido = returnReserveSido;
	}

	public String getReturnReserveSigungu() {
		return returnReserveSigungu;
	}

	public void setReturnReserveSigungu(String returnReserveSigungu) {
		this.returnReserveSigungu = returnReserveSigungu;
	}

	public String getReturnReserveEupmyeondong() {
		return returnReserveEupmyeondong;
	}

	public void setReturnReserveEupmyeondong(String returnReserveEupmyeondong) {
		this.returnReserveEupmyeondong = returnReserveEupmyeondong;
	}

	public String getReturnReserveAddress() {
		return returnReserveAddress;
	}

	public void setReturnReserveAddress(String returnReserveAddress) {
		this.returnReserveAddress = returnReserveAddress;
	}

	public String getReturnReserveAddress2() {
		return returnReserveAddress2;
	}

	public void setReturnReserveAddress2(String returnReserveAddress2) {
		this.returnReserveAddress2 = returnReserveAddress2;
	}

	public String getReturnReserveZipcode1() {
		return returnReserveZipcode1;
	}

	public void setReturnReserveZipcode1(String returnReserveZipcode1) {
		this.returnReserveZipcode1 = returnReserveZipcode1;
	}

	public String getReturnReserveZipcode2() {
		return returnReserveZipcode2;
	}

	public void setReturnReserveZipcode2(String returnReserveZipcode2) {
		this.returnReserveZipcode2 = returnReserveZipcode2;
	}
	
	
}
