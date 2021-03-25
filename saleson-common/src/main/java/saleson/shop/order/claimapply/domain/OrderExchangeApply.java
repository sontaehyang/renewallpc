package saleson.shop.order.claimapply.domain;

import org.springframework.util.StringUtils;

import saleson.seller.main.domain.Seller;
import saleson.shop.order.claimapply.support.ExchangeApply;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;

public class OrderExchangeApply {
	
	private int copyItemSequence;
	public int getCopyItemSequence() {
		return copyItemSequence;
	}
	public void setCopyItemSequence(int copyItemSequence) {
		this.copyItemSequence = copyItemSequence;
	}
	
	public OrderExchangeApply() {}
	public OrderExchangeApply(ExchangeApply apply) {
		setOrderCode(apply.getOrderCode());
		setOrderSequence(apply.getOrderSequence());
		setItemSequence(apply.getItemSequence());
		setClaimApplyQuantity(apply.getApplyQuantity());
		setExchangeReason(apply.getClaimReason());
		setExchangeReasonText(apply.getClaimReasonText());
		setExchangeReasonDetail(apply.getClaimReasonDetail());
		
		// CJH 2016.11.13 구매자가 직접 발송할경우 회수중으로 기본 등록
		setClaimStatus("2".equals(apply.getExchangeShippingAskType()) ? "10" : "01");
		setClaimApplySubject("01");
		
		setExchangeReceiveName(apply.getExchangeReceiveName());
		setExchangeReceivePhone(apply.getExchangeReceivePhone());
		setExchangeReceiveMobile(apply.getExchangeReceiveMobile());
		setExchangeReceiveZipcode(apply.getExchangeReceiveZipcode());
		
		setExchangeReceiveSido(apply.getExchangeReceiveSido());
		setExchangeReceiveSigungu(apply.getExchangeReceiveSigungu());
		setExchangeReceiveEupmyeondong(apply.getExchangeReceiveEupmyeondong());
		
		setExchangeReceiveAddress(apply.getExchangeReceiveAddress());
		setExchangeReceiveAddress2(apply.getExchangeReceiveAddress2());
		
		setExchangeShippingAskType(apply.getExchangeShippingAskType());
		setExchangeShippingNumber(apply.getExchangeShippingNumber());
		setExchangeShippingCompanyName(apply.getExchangeShippingCompanyName());
	}
	
	public void setExchangeShippingInfo(OrderShippingInfo info) {
		
		setExchangeReceiveName(info.getReceiveName());
		
		String phone = "";
		if (StringUtils.isEmpty(info.getReceivePhone()) == false) {
			if (!info.getReceivePhone().startsWith("null")) {
				phone = info.getReceivePhone();
			}
		}
		
		setExchangeReceivePhone(phone);
		
		String mobile = "";
		if (StringUtils.isEmpty(info.getReceiveMobile()) == false) {
			if (!info.getReceiveMobile().startsWith("null")) {
				mobile = info.getReceiveMobile();
			}
		}
		
		setExchangeReceiveMobile(mobile);
		setExchangeReceiveZipcode(info.getReceiveZipcode());
		setExchangeReceiveSido(info.getReceiveSido());
		setExchangeReceiveSigungu(info.getReceiveSigungu());
		setExchangeReceiveEupmyeondong(info.getReceiveEupmyeondong());
		setExchangeReceiveAddress(info.getReceiveAddress());
		setExchangeReceiveAddress2(info.getReceiveAddressDetail());
		
	}
	
	private String claimCode;
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private int shipmentReturnId;
	private long shipmentReturnSellerId;
	private String claimApplySubject;
	private int claimApplyQuantity;
	private String claimStatus;
	private String exchangeApplyDate;
	private String exchangeReceiveName;
	private String exchangeReceivePhone;
	private String exchangeReceiveMobile;
	private String exchangeReceiveZipcode;
	private String exchangeReceiveSido;
	private String exchangeReceiveSigungu;
	private String exchangeReceiveEupmyeondong;
	private String exchangeReceiveAddress;
	private String exchangeReceiveAddress2;
	private int exchangeDeliveryCompanyId;
	private String exchangeDeliveryCompanyName;
	private String exchangeDeliveryNumber;
	private String exchangeDeliveryCompanyUrl;
	private String exchangeDeliveryDate;
	private String exchangeShippingStartFalg;
	
	private String exchangeShippingAskType;
	
	public String getExchangeShippingAskType() {
		return exchangeShippingAskType;
	}
	public void setExchangeShippingAskType(String exchangeShippingAskType) {
		this.exchangeShippingAskType = exchangeShippingAskType;
	}

	private String exchangeShippingNumber;
	private String exchangeShippingCompanyName;
	private String exchangeShippingCompanyUrl;
	private String exchangeShippingStartDate;
	private String exchangeReason;
	private String exchangeReasonText;
	private String exchangeReasonDetail;
	private int exchangeRealShipping;
	private String exchangeMemo;
	private String exchangeRefusalReasonText;
	private String updatedDate;
	private String createdDate;
	
	private OrderItem orderItem;
	private long userId;
	private String loginId;
	private String buyerName;
	private String receiveName;
	private long sellerId;

	private ShipmentReturn shipmentReturn;
	private Seller seller;

	private int parentItemSequence;

	public int getParentItemSequence() {
		return parentItemSequence;
	}
	public void setParentItemSequence(int parentItemSequence) {
		this.parentItemSequence = parentItemSequence;
	}
	
	public ShipmentReturn getShipmentReturn() {
		return shipmentReturn;
	}
	public void setShipmentReturn(ShipmentReturn shipmentReturn) {
		this.shipmentReturn = shipmentReturn;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
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
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public int getClaimApplyQuantity() {
		return claimApplyQuantity;
	}
	public void setClaimApplyQuantity(int claimApplyQuantity) {
		this.claimApplyQuantity = claimApplyQuantity;
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
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public String getExchangeApplyDate() {
		return exchangeApplyDate;
	}
	public void setExchangeApplyDate(String exchangeApplyDate) {
		this.exchangeApplyDate = exchangeApplyDate;
	}
	public String getExchangeReceiveName() {
		return exchangeReceiveName;
	}
	public void setExchangeReceiveName(String exchangeReceiveName) {
		this.exchangeReceiveName = exchangeReceiveName;
	}
	public String getExchangeReceivePhone() {
		return exchangeReceivePhone;
	}
	public void setExchangeReceivePhone(String exchangeReceivePhone) {
		this.exchangeReceivePhone = exchangeReceivePhone;
	}
	public String getExchangeReceiveMobile() {
		return exchangeReceiveMobile;
	}
	public void setExchangeReceiveMobile(String exchangeReceiveMobile) {
		this.exchangeReceiveMobile = exchangeReceiveMobile;
	}
	public String getExchangeReceiveZipcode() {
		return exchangeReceiveZipcode;
	}
	public void setExchangeReceiveZipcode(String exchangeReceiveZipcode) {
		this.exchangeReceiveZipcode = exchangeReceiveZipcode;
	}
	public String getExchangeReceiveSido() {
		return exchangeReceiveSido;
	}
	public void setExchangeReceiveSido(String exchangeReceiveSido) {
		this.exchangeReceiveSido = exchangeReceiveSido;
	}
	public String getExchangeReceiveSigungu() {
		return exchangeReceiveSigungu;
	}
	public void setExchangeReceiveSigungu(String exchangeReceiveSigungu) {
		this.exchangeReceiveSigungu = exchangeReceiveSigungu;
	}
	public String getExchangeReceiveEupmyeondong() {
		return exchangeReceiveEupmyeondong;
	}
	public void setExchangeReceiveEupmyeondong(String exchangeReceiveEupmyeondong) {
		this.exchangeReceiveEupmyeondong = exchangeReceiveEupmyeondong;
	}
	public String getExchangeReceiveAddress() {
		return exchangeReceiveAddress;
	}
	public void setExchangeReceiveAddress(String exchangeReceiveAddress) {
		this.exchangeReceiveAddress = exchangeReceiveAddress;
	}
	public String getExchangeReceiveAddress2() {
		return exchangeReceiveAddress2;
	}
	public void setExchangeReceiveAddress2(String exchangeReceiveAddress2) {
		this.exchangeReceiveAddress2 = exchangeReceiveAddress2;
	}
	public int getExchangeDeliveryCompanyId() {
		return exchangeDeliveryCompanyId;
	}
	public void setExchangeDeliveryCompanyId(int exchangeDeliveryCompanyId) {
		this.exchangeDeliveryCompanyId = exchangeDeliveryCompanyId;
	}
	public String getExchangeDeliveryCompanyName() {
		return exchangeDeliveryCompanyName;
	}
	public void setExchangeDeliveryCompanyName(String exchangeDeliveryCompanyName) {
		this.exchangeDeliveryCompanyName = exchangeDeliveryCompanyName;
	}
	public String getExchangeDeliveryNumber() {
		return exchangeDeliveryNumber;
	}
	public void setExchangeDeliveryNumber(String exchangeDeliveryNumber) {
		this.exchangeDeliveryNumber = exchangeDeliveryNumber;
	}
	public String getExchangeDeliveryCompanyUrl() {
		return exchangeDeliveryCompanyUrl;
	}
	public void setExchangeDeliveryCompanyUrl(String exchangeDeliveryCompanyUrl) {
		this.exchangeDeliveryCompanyUrl = exchangeDeliveryCompanyUrl;
	}
	public String getExchangeDeliveryDate() {
		return exchangeDeliveryDate;
	}
	public void setExchangeDeliveryDate(String exchangeDeliveryDate) {
		this.exchangeDeliveryDate = exchangeDeliveryDate;
	}
	public String getExchangeShippingStartFalg() {
		return exchangeShippingStartFalg;
	}
	public void setExchangeShippingStartFalg(String exchangeShippingStartFalg) {
		this.exchangeShippingStartFalg = exchangeShippingStartFalg;
	}
	public String getExchangeShippingNumber() {
		return exchangeShippingNumber;
	}
	public void setExchangeShippingNumber(String exchangeShippingNumber) {
		this.exchangeShippingNumber = exchangeShippingNumber;
	}
	public String getExchangeShippingCompanyName() {
		return exchangeShippingCompanyName;
	}
	public void setExchangeShippingCompanyName(String exchangeShippingCompanyName) {
		this.exchangeShippingCompanyName = exchangeShippingCompanyName;
	}
	public String getExchangeShippingCompanyUrl() {
		return exchangeShippingCompanyUrl;
	}
	public void setExchangeShippingCompanyUrl(String exchangeShippingCompanyUrl) {
		this.exchangeShippingCompanyUrl = exchangeShippingCompanyUrl;
	}
	public String getExchangeShippingStartDate() {
		return exchangeShippingStartDate;
	}
	public void setExchangeShippingStartDate(String exchangeShippingStartDate) {
		this.exchangeShippingStartDate = exchangeShippingStartDate;
	}
	public String getExchangeReason() {
		return exchangeReason;
	}
	public void setExchangeReason(String exchangeReason) {
		this.exchangeReason = exchangeReason;
	}
	public String getExchangeReasonText() {
		return exchangeReasonText;
	}
	public void setExchangeReasonText(String exchangeReasonText) {
		this.exchangeReasonText = exchangeReasonText;
	}
	public int getExchangeRealShipping() {
		return exchangeRealShipping;
	}
	public void setExchangeRealShipping(int exchangeRealShipping) {
		this.exchangeRealShipping = exchangeRealShipping;
	}
	public String getExchangeMemo() {
		return exchangeMemo;
	}
	public void setExchangeMemo(String exchangeMemo) {
		this.exchangeMemo = exchangeMemo;
	}
	public String getExchangeRefusalReasonText() {
		return exchangeRefusalReasonText;
	}
	public void setExchangeRefusalReasonText(String exchangeRefusalReasonText) {
		this.exchangeRefusalReasonText = exchangeRefusalReasonText;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getExchangeReasonDetail() {
		return exchangeReasonDetail;
	}
	public void setExchangeReasonDetail(String exchangeReasonDetail) {
		this.exchangeReasonDetail = exchangeReasonDetail;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
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
	public String getClaimApplySubject() {
		return claimApplySubject;
	}
	public void setClaimApplySubject(String claimApplySubject) {
		this.claimApplySubject = claimApplySubject;
	}
	public String getClaimStatusLabel() {
		
		if ("01".equals(claimStatus)) {
			return "신청";
		} else if ("02".equals(claimStatus)) {
			return "보류";
		} else if ("03".equals(claimStatus)) {
			return "교환상품 발송";
		} else if ("10".equals(claimStatus)) {
			return "회수중";
		} else if ("11".equals(claimStatus)) {
			return "회수완료";
		} else if ("99".equals(claimStatus)) {
			return "거절";
		}
		
		return "-";
	}
}
