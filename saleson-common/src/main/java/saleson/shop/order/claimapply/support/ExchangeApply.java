package saleson.shop.order.claimapply.support;

import org.springframework.util.StringUtils;

import saleson.common.utils.ShopUtils;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;

public class ExchangeApply {
	private String orderCode;
	private int orderSequence;
	private int itemSequence;

	private String exchangeReceiveName;
	private String exchangeReceivePhone;
	private String exchangeReceivePhone1;
	private String exchangeReceivePhone2;
	private String exchangeReceivePhone3;
	
	private String exchangeReceiveMobile;
	private String exchangeReceiveMobile1;
	private String exchangeReceiveMobile2;
	private String exchangeReceiveMobile3;
	
	private String exchangeReceiveZipcode;
	private String exchangeReceiveSido;
	private String exchangeReceiveSigungu;
	private String exchangeReceiveEupmyeondong;
	private String exchangeReceiveAddress;
	private String exchangeReceiveAddress2;
	
	private OrderItem orderItem;
	private int shipmentReturnId;
	
	private String claimReason;
	private String claimReasonText;
	private String claimReasonDetail;
	
	private String exchangeShippingCompanyName;
	public String getExchangeShippingCompanyName() {
		return exchangeShippingCompanyName;
	}

	public void setExchangeShippingCompanyName(String exchangeShippingCompanyName) {
		this.exchangeShippingCompanyName = exchangeShippingCompanyName;
	}

	private String exchangeShippingNumber;
	private String exchangeShippingAskType = "1";
	
	private int applyQuantity;

	private int parentItemSequence;

	public int getParentItemSequence() {
		return parentItemSequence;
	}
	public void setParentItemSequence(int parentItemSequence) {
		this.parentItemSequence = parentItemSequence;
	}

	public int getApplyQuantity() {
		return applyQuantity;
	}

	public void setApplyQuantity(int applyQuantity) {
		this.applyQuantity = applyQuantity;
	}

	public String getClaimReason() {
		return claimReason;
	}

	public void setClaimReason(String claimReason) {
		this.claimReason = claimReason;
	}

	public String getClaimReasonText() {
		return claimReasonText;
	}

	public void setClaimReasonText(String claimReasonText) {
		this.claimReasonText = claimReasonText;
	}

	public String getClaimReasonDetail() {
		return claimReasonDetail;
	}

	public void setClaimReasonDetail(String claimReasonDetail) {
		this.claimReasonDetail = claimReasonDetail;
	}

	public String getExchangeShippingNumber() {
		return exchangeShippingNumber;
	}

	public void setExchangeShippingNumber(String exchangeShippingNumber) {
		this.exchangeShippingNumber = exchangeShippingNumber;
	}

	public String getExchangeShippingAskType() {
		return exchangeShippingAskType;
	}

	public void setExchangeShippingAskType(String exchangeShippingAskType) {
		this.exchangeShippingAskType = exchangeShippingAskType;
	}

	public int getShipmentReturnId() {
		return shipmentReturnId;
	}

	public void setShipmentReturnId(int shipmentReturnId) {
		this.shipmentReturnId = shipmentReturnId;
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
	
	public String getExchangeReceiveName() {
		return exchangeReceiveName;
	}
	public void setExchangeReceiveName(String exchangeReceiveName) {
		this.exchangeReceiveName = exchangeReceiveName;
	}
	public String getExchangeReceivePhone() {
		
		if (StringUtils.isEmpty(getExchangeReceivePhone1()) == false) {
			return getExchangeReceivePhone1() + "-" + getExchangeReceivePhone2() + "-" + getExchangeReceivePhone3();
		}
		
		return exchangeReceivePhone;
	}
	public void setExchangeReceivePhone(String exchangeReceivePhone) {
		this.exchangeReceivePhone = exchangeReceivePhone;
	}
	public String getExchangeReceiveMobile() {
		
		if (StringUtils.isEmpty(getExchangeReceiveMobile1()) == false) {
			return getExchangeReceiveMobile1() + "-" + getExchangeReceiveMobile2() + "-" + getExchangeReceiveMobile3();
		}
		
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
	
	public void setExchangeApply(OrderShippingInfo info) {
		
		setExchangeReceiveName(info.getReceiveName());
		
		String phone = "";
		if (StringUtils.isEmpty(info.getReceivePhone()) == false) {
			if (!info.getReceivePhone().startsWith("null")) {
				phone = info.getReceivePhone();
			}
		}
		
		String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(phone);
		
		setExchangeReceivePhone1(cutArray[0]);
		setExchangeReceivePhone2(cutArray[1]);
		setExchangeReceivePhone3(cutArray[2]);
		
		setExchangeReceivePhone(phone);
		
		String mobile = "";
		if (StringUtils.isEmpty(info.getReceiveMobile()) == false) {
			if (!info.getReceiveMobile().startsWith("null")) {
				mobile = info.getReceiveMobile();
			}
		}
		
		String[] cutArray1 = ShopUtils.phoneNumberForDelimitedToStringArray(mobile);
		
		setExchangeReceiveMobile1(cutArray1[0]);
		setExchangeReceiveMobile2(cutArray1[1]);
		setExchangeReceiveMobile3(cutArray1[2]);
		
		setExchangeReceiveMobile(mobile);
		
		//setExchangeReceiveZipcode(info.getReceiveZipcode());
		setExchangeReceiveZipcode(info.getReceiveNewZipcode());
		
		setExchangeReceiveSido(info.getReceiveSido());
		setExchangeReceiveSigungu(info.getReceiveSigungu());
		setExchangeReceiveEupmyeondong(info.getReceiveEupmyeondong());
		
		setExchangeReceiveAddress(info.getReceiveAddress());
		setExchangeReceiveAddress2(info.getReceiveAddressDetail());
		
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public String getExchangeReceivePhone1() {
		return exchangeReceivePhone1;
	}

	public void setExchangeReceivePhone1(String exchangeReceivePhone1) {
		this.exchangeReceivePhone1 = exchangeReceivePhone1;
	}

	public String getExchangeReceivePhone2() {
		return exchangeReceivePhone2;
	}

	public void setExchangeReceivePhone2(String exchangeReceivePhone2) {
		this.exchangeReceivePhone2 = exchangeReceivePhone2;
	}

	public String getExchangeReceivePhone3() {
		return exchangeReceivePhone3;
	}

	public void setExchangeReceivePhone3(String exchangeReceivePhone3) {
		this.exchangeReceivePhone3 = exchangeReceivePhone3;
	}

	public String getExchangeReceiveMobile1() {
		return exchangeReceiveMobile1;
	}

	public void setExchangeReceiveMobile1(String exchangeReceiveMobile1) {
		this.exchangeReceiveMobile1 = exchangeReceiveMobile1;
	}

	public String getExchangeReceiveMobile2() {
		return exchangeReceiveMobile2;
	}

	public void setExchangeReceiveMobile2(String exchangeReceiveMobile2) {
		this.exchangeReceiveMobile2 = exchangeReceiveMobile2;
	}

	public String getExchangeReceiveMobile3() {
		return exchangeReceiveMobile3;
	}

	public void setExchangeReceiveMobile3(String exchangeReceiveMobile3) {
		this.exchangeReceiveMobile3 = exchangeReceiveMobile3;
	}
	
	
}
