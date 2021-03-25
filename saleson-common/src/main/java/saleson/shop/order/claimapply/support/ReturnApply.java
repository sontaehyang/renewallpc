package saleson.shop.order.claimapply.support;

import org.springframework.util.StringUtils;

import saleson.common.utils.ShopUtils;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;

public class ReturnApply {
	
	private String claimCode;
	public String getClaimCode() {
		return claimCode;
	}

	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}

	private String returnShippingAskType = "1";
	public String getReturnShippingAskType() {
		return returnShippingAskType;
	}

	public void setReturnShippingAskType(String returnShippingAskType) {
		this.returnShippingAskType = returnShippingAskType;
	}

	private int shipmentReturnId;
	public int getShipmentReturnId() {
		return shipmentReturnId;
	}

	public void setShipmentReturnId(int shipmentReturnId) {
		this.shipmentReturnId = shipmentReturnId;
	}

	private OrderItem orderItem;
	
	private int applyQuantity;
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	
	private String returnReserveName;
	private String returnReservePhone;
	private String returnReservePhone1;
	private String returnReservePhone2;
	private String returnReservePhone3;
	
	private String returnReserveMobile;
	private String returnReserveMobile1;
	private String returnReserveMobile2;
	private String returnReserveMobile3;
	
	private String returnReserveZipcode;
	private String returnReserveSido;
	private String returnReserveSigungu;
	private String returnReserveEupmyeondong;
	private String returnReserveAddress;
	private String returnReserveAddress2;
	
	private String returnShippingCompanyName;
	public String getReturnShippingCompanyName() {
		return returnShippingCompanyName;
	}

	public void setReturnShippingCompanyName(String returnShippingCompanyName) {
		this.returnShippingCompanyName = returnShippingCompanyName;
	}

	private String returnShippingNumber;

	private String claimReason;
	private String claimReasonText;
	private String claimReasonDetail;

	//환불정보
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
	
	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public int getApplyQuantity() {
		return applyQuantity;
	}

	public void setApplyQuantity(int applyQuantity) {
		this.applyQuantity = applyQuantity;
	}

	public String getReturnReserveName() {
		return returnReserveName;
	}

	public void setReturnReserveName(String returnReserveName) {
		this.returnReserveName = returnReserveName;
	}

	public String getReturnReservePhone() {
		
		if (StringUtils.isEmpty(getReturnReservePhone1()) == false) {
			return getReturnReservePhone1() + "-" + getReturnReservePhone2() + "-" + getReturnReservePhone3();
		}
		
		return returnReservePhone;
	}

	public void setReturnReservePhone(String returnReservePhone) {
		this.returnReservePhone = returnReservePhone;
	}

	public String getReturnReserveMobile() {
		
		if (StringUtils.isEmpty(getReturnReserveMobile1()) == false) {
			return getReturnReserveMobile1() + "-" + getReturnReserveMobile2() + "-" + getReturnReserveMobile3();
		}
		
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

	public void setReturnApply(OrderShippingInfo info) {
		
		setReturnReserveName(info.getReceiveName());
		
		String phone = "";
		if (StringUtils.isEmpty(info.getReceivePhone()) == false) {
			if (!info.getReceivePhone().startsWith("null")) {
				phone = info.getReceivePhone();
			}
		}
		
		String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(phone);
		
		setReturnReservePhone1(cutArray[0]);
		setReturnReservePhone2(cutArray[1]);
		setReturnReservePhone3(cutArray[2]);
		
		setReturnReservePhone(phone);
		
		String mobile = "";
		if (StringUtils.isEmpty(info.getReceiveMobile()) == false) {
			if (!info.getReceiveMobile().startsWith("null")) {
				mobile = info.getReceiveMobile();
			}
		}
		
		String[] cutArray1 = ShopUtils.phoneNumberForDelimitedToStringArray(mobile);
		
		setReturnReserveMobile1(cutArray1[0]);
		setReturnReserveMobile2(cutArray1[1]);
		setReturnReserveMobile3(cutArray1[2]);
		
		setReturnReserveMobile(mobile);
		//setReturnReserveZipcode(info.getReceiveZipcode());
		setReturnReserveZipcode(info.getReceiveNewZipcode());
		
		setReturnReserveSido(info.getReceiveSido());
		setReturnReserveSigungu(info.getReceiveSigungu());
		setReturnReserveEupmyeondong(info.getReceiveEupmyeondong());
		
		setReturnReserveAddress(info.getReceiveAddress());
		setReturnReserveAddress2(info.getReceiveAddressDetail());
		
	}

	public String getClaimReason() {
		return claimReason;
	}

	public void setClaimReason(String claimReason) {
		this.claimReason = claimReason;
	}

	public String getClaimReasonDetail() {
		return claimReasonDetail;
	}

	public void setClaimReasonDetail(String claimReasonDetail) {
		this.claimReasonDetail = claimReasonDetail;
	}

	public String getClaimReasonText() {
		return claimReasonText;
	}

	public void setClaimReasonText(String claimReasonText) {
		this.claimReasonText = claimReasonText;
	}

	public String getReturnReservePhone1() {
		return returnReservePhone1;
	}

	public void setReturnReservePhone1(String returnReservePhone1) {
		this.returnReservePhone1 = returnReservePhone1;
	}

	public String getReturnReservePhone2() {
		return returnReservePhone2;
	}

	public void setReturnReservePhone2(String returnReservePhone2) {
		this.returnReservePhone2 = returnReservePhone2;
	}

	public String getReturnReservePhone3() {
		return returnReservePhone3;
	}

	public void setReturnReservePhone3(String returnReservePhone3) {
		this.returnReservePhone3 = returnReservePhone3;
	}

	public String getReturnReserveMobile1() {
		return returnReserveMobile1;
	}

	public void setReturnReserveMobile1(String returnReserveMobile1) {
		this.returnReserveMobile1 = returnReserveMobile1;
	}

	public String getReturnReserveMobile2() {
		return returnReserveMobile2;
	}

	public void setReturnReserveMobile2(String returnReserveMobile2) {
		this.returnReserveMobile2 = returnReserveMobile2;
	}

	public String getReturnReserveMobile3() {
		return returnReserveMobile3;
	}

	public void setReturnReserveMobile3(String returnReserveMobile3) {
		this.returnReserveMobile3 = returnReserveMobile3;
	}

}
