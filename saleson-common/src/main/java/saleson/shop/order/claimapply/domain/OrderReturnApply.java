package saleson.shop.order.claimapply.domain;

import java.util.HashMap;
import org.springframework.util.StringUtils;
import saleson.common.utils.CommonUtils;
import saleson.seller.main.domain.Seller;
import saleson.shop.order.claimapply.support.ReturnApply;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;

public class OrderReturnApply {

	private String refundCancelFlag;
	private int shippingSequence;
	public int getShippingSequence() {
		return shippingSequence;
	}
	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
	}

	private int copyItemSequence;
	public int getCopyItemSequence() {
		return copyItemSequence;
	}
	public void setCopyItemSequence(int copyItemSequence) {
		this.copyItemSequence = copyItemSequence;
	}

	private int claimApplyAmount;
	public int getClaimApplyAmount() {
		return claimApplyAmount;
	}
	public void setClaimApplyAmount(int claimApplyAmount) {
		this.claimApplyAmount = claimApplyAmount;
	}

	private String claimStatus;
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	private String deliveryNumber;
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	private long sellerId;
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	//환불
	private String returnBankName;
	private String returnBankInName;
	private String returnVirtualNo;

	// 반품신청전 주문상태
	private String previousOrderStatus;

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

	public OrderReturnApply() {}
	public OrderReturnApply(OrderItem orderItem, ReturnApply apply) {
		setSellerId(orderItem.getSellerId());
		setOrderCode(orderItem.getOrderCode());
		setOrderSequence(orderItem.getOrderSequence());
		setItemSequence(orderItem.getItemSequence());

		setReturnReason(apply.getClaimReason());
		setReturnReasonText(apply.getClaimReasonText());
		setReturnReasonDetail(apply.getClaimReasonDetail());

		setOrderItem(orderItem);

		setClaimApplySubject("01");

		// CJH 2016.11.13 구매자가 직접 발송할경우 회수중으로 기본 등록
		setClaimStatus("2".equals(apply.getReturnShippingAskType()) ? "10" : "01");
		setClaimStatus("01");

		setClaimApplyQuantity(apply.getApplyQuantity());

		setReturnReserveName(apply.getReturnReserveName());
		setReturnReservePhone(apply.getReturnReservePhone());
		setReturnReserveMobile(apply.getReturnReserveMobile());
		setReturnReserveZipcode(apply.getReturnReserveZipcode());

		setReturnReserveSido(apply.getReturnReserveSido());
		setReturnReserveSigungu(apply.getReturnReserveSigungu());
		setReturnReserveEupmyeondong(apply.getReturnReserveEupmyeondong());

		setReturnReserveAddress(apply.getReturnReserveAddress());
		setReturnReserveAddress2(apply.getReturnReserveAddress2());

		setReturnShippingAskType(apply.getReturnShippingAskType());
		setReturnShippingNumber(apply.getReturnShippingNumber());
		setReturnShippingCompanyName(apply.getReturnShippingCompanyName());
		setDeliveryNumber(orderItem.getDeliveryNumber());

		setPreviousOrderStatus(orderItem.getOrderStatus());
	}

	public OrderReturnApply(OrderShippingInfo info) {
		setReturnShippingInfo(info);
	}

	public void setReturnShippingInfo(OrderShippingInfo info) {

		setReturnReserveName(info.getReceiveName());

		String phone = "";
		if (StringUtils.isEmpty(info.getReceivePhone()) == false) {
			if (!info.getReceivePhone().startsWith("null")) {
				phone = info.getReceivePhone();
			}
		}

		setReturnReservePhone(phone);

		String mobile = "";
		if (StringUtils.isEmpty(info.getReceiveMobile()) == false) {
			if (!info.getReceiveMobile().startsWith("null")) {
				mobile = info.getReceiveMobile();
			}
		}

		setReturnReserveMobile(mobile);
		setReturnReserveZipcode(info.getReceiveZipcode());
		setReturnReserveSido(info.getReceiveSido());
		setReturnReserveSigungu(info.getReceiveSigungu());
		setReturnReserveEupmyeondong(info.getReceiveEupmyeondong());
		setReturnReserveAddress(info.getReceiveAddress());
		setReturnReserveAddress2(info.getReceiveAddressDetail());

	}

	private OrderItem orderItem;
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	private long userId;
	private String loginId;
	private String buyerName;
	private String receiveName;
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
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	private String[] id;
	private HashMap<String, Integer> itemSequenceMap;
	public String[] getId() {
		return CommonUtils.copy(id);
	}
	public void setId(String[] id) {
		this.id = CommonUtils.copy(id);
	}
	public HashMap<String, Integer> getItemSequenceMap() {
		return itemSequenceMap;
	}
	public void setItemSequenceMap(HashMap<String, Integer> itemSequenceMap) {
		this.itemSequenceMap = itemSequenceMap;
	}



	private long shipmentReturnSellerId;
	private int shipmentReturnId;
	public int getShipmentReturnId() {
		return shipmentReturnId;
	}
	public void setShipmentReturnId(int shipmentReturnId) {
		this.shipmentReturnId = shipmentReturnId;
	}
	public long getShipmentReturnSellerId() {
		return shipmentReturnSellerId;
	}
	public void setShipmentReturnSellerId(long shipmentReturnSellerId) {
		this.shipmentReturnSellerId = shipmentReturnSellerId;
	}

	private String claimCode;
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private String returnApplyDate;
	private String refundCode;

	private int claimApplyQuantity;

	private String returnShippingAskType;
	public String getReturnShippingAskType() {
		return returnShippingAskType;
	}
	public void setReturnShippingAskType(String returnShippingAskType) {
		this.returnShippingAskType = returnShippingAskType;
	}

	private String claimApplySubject;
	private String returnReserveName;
	private String returnReservePhone;
	private String returnReserveMobile;
	private String returnReserveZipcode;
	private String returnReserveSido;
	private String returnReserveSigungu;
	private String returnReserveEupmyeondong;
	private String returnReserveAddress;
	private String returnReserveAddress2;
	private String returnShippingNumber;
	private String returnShippingCompanyName;
	private String returnShippingCompanyUrl;

	private String returnReason;
	private String returnReasonText;
	private String returnReasonDetail;

	public int getClaimApplyQuantity() {
		return claimApplyQuantity;
	}
	public void setClaimApplyQuantity(int claimApplyQuantity) {
		this.claimApplyQuantity = claimApplyQuantity;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
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
	public String getReturnReasonDetail() {
		return returnReasonDetail;
	}
	public void setReturnReasonDetail(String returnReasonDetail) {
		this.returnReasonDetail = returnReasonDetail;
	}

	private int collectionShippingAmount;
	public int getCollectionShippingAmount() {
		return collectionShippingAmount;
	}
	public void setCollectionShippingAmount(int collectionShippingAmount) {
		this.collectionShippingAmount = collectionShippingAmount;
	}

	private String returnMemo;

	private String returnRefusalReasonText;
	private String createdDate;

	private ShipmentReturn shipmentReturn;
	private Seller seller;

	private int parentItemSequence;

	public int getParentItemSequence() {
		return parentItemSequence;
	}
	public void setParentItemSequence(int parentItemSequence) {
		this.parentItemSequence = parentItemSequence;
	}

	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public ShipmentReturn getShipmentReturn() {
		return shipmentReturn;
	}
	public void setShipmentReturn(ShipmentReturn shipmentReturn) {
		this.shipmentReturn = shipmentReturn;
	}
	public String getClaimCode() {
		return claimCode;
	}
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
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

	public String getReturnApplyDate() {
		return returnApplyDate;
	}
	public void setReturnApplyDate(String returnApplyDate) {
		this.returnApplyDate = returnApplyDate;
	}
	public String getRefundCode() {
		return refundCode;
	}
	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}
	public String getClaimApplySubject() {
		return claimApplySubject;
	}
	public void setClaimApplySubject(String claimApplySubject) {
		this.claimApplySubject = claimApplySubject;
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

	public String getReturnShippingNumber() {
		return returnShippingNumber;
	}
	public void setReturnShippingNumber(String returnShippingNumber) {
		this.returnShippingNumber = returnShippingNumber;
	}
	public String getReturnShippingCompanyName() {
		return returnShippingCompanyName;
	}
	public void setReturnShippingCompanyName(String returnShippingCompanyName) {
		this.returnShippingCompanyName = returnShippingCompanyName;
	}
	public String getReturnShippingCompanyUrl() {
		return returnShippingCompanyUrl;
	}
	public void setReturnShippingCompanyUrl(String returnShippingCompanyUrl) {
		this.returnShippingCompanyUrl = returnShippingCompanyUrl;
	}
	public String getReturnMemo() {
		return returnMemo;
	}
	public void setReturnMemo(String returnMemo) {
		this.returnMemo = returnMemo;
	}
	public String getReturnRefusalReasonText() {
		return returnRefusalReasonText;
	}
	public void setReturnRefusalReasonText(String returnRefusalReasonText) {
		this.returnRefusalReasonText = returnRefusalReasonText;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getClaimStatusLabel() {

		if ("01".equals(claimStatus)) {
			return "신청";
		} else if ("02".equals(claimStatus)) {
			return "보류";
		} else if ("03".equals(claimStatus)) {
			return "환불승인 대기";
		} else if ("04".equals(claimStatus)) {
			return "환불 완료";
		} else if ("10".equals(claimStatus)) {
			return "회수중";
		} else if ("11".equals(claimStatus)) {
			return "회수완료";
		} else if ("99".equals(claimStatus)) {
			return "거절";
		}

		return "-";
	}

	public void addInfo(OrderReturnApply orderReturnApply) {

		setClaimCode(orderReturnApply.getClaimCode());
		setOrderCode(orderReturnApply.getOrderCode());
		setOrderSequence(orderReturnApply.getOrderSequence());
		setItemSequence(orderReturnApply.getItemSequence());
		setClaimApplyQuantity(orderReturnApply.getClaimApplyQuantity());

	}
	public String getPreviousOrderStatus() {
		return previousOrderStatus;
	}
	public void setPreviousOrderStatus(String previousOrderStatus) {
		this.previousOrderStatus = previousOrderStatus;
	}
	public String getRefundCancelFlag() {
		return refundCancelFlag;
	}
	public void setRefundCancelFlag(String refundCancelFlag) {
		this.refundCancelFlag = refundCancelFlag;
	}

}
