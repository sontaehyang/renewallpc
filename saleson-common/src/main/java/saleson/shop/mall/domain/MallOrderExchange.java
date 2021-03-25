package saleson.shop.mall.domain;

public class MallOrderExchange {
	private int mallOrderId;
	private String orderCode;
	private String claimCode;
	private int claimQuantity;
	private String claimStatus;
	private String exReason;
	private String exReasonText;
	private String exCollectionName;
	private String exCollectionTelNumber;
	private String exCollectionPhoneNumber;
	private String exCollectionZipcode;
	private String exCollectionZipcodeSeq;
	private String exCollectionAddress;
	private String exCollectionAddressDetail;
	private String exCollectionAddressType;
	private String exCollectionAddressBilno;
	private String exShippingType;
	private String exReceiverName;
	private String exReceiverTelNumber;
	private String exReceiverPhoneNumber;
	private String exReceiverZipcode;
	private String exReceiverZipcodeSeq;
	private String exReceiverAddress;
	private String exReceiverAddressDetail;
	private String exReceiverAddressType;
	private String exReceiverAddressBilno;
	private int exShippingAmount;
	private int exAddShippingAmount;
	private String exShippingPaymentType;
	private String exShippingNumber;
	private String exShippingCompanyCode;
	private String exApplyDate;
	private String exEndDate;
	
	private String exRefusalReson;
	private String exRefusalResonText;
	
	private String resendDeliveryCompanyCode;
	private String resendDeliveryNumber;
	
	private MallOrder mallOrder;
	
	public String getResendDeliveryCompanyCode() {
		return resendDeliveryCompanyCode;
	}
	public void setResendDeliveryCompanyCode(String resendDeliveryCompanyCode) {
		this.resendDeliveryCompanyCode = resendDeliveryCompanyCode;
	}
	public String getResendDeliveryNumber() {
		return resendDeliveryNumber;
	}
	public void setResendDeliveryNumber(String resendDeliveryNumber) {
		this.resendDeliveryNumber = resendDeliveryNumber;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public MallOrder getMallOrder() {
		return mallOrder;
	}
	public void setMallOrder(MallOrder mallOrder) {
		this.mallOrder = mallOrder;
	}
	public int getMallOrderId() {
		return mallOrderId;
	}
	public void setMallOrderId(int mallOrderId) {
		this.mallOrderId = mallOrderId;
	}
	public String getClaimCode() {
		return claimCode;
	}
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}
	public int getClaimQuantity() {
		return claimQuantity;
	}
	public void setClaimQuantity(int claimQuantity) {
		this.claimQuantity = claimQuantity;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public String getExReason() {
		return exReason;
	}
	public void setExReason(String exReason) {
		this.exReason = exReason;
	}
	public String getExReasonText() {
		return exReasonText;
	}
	public void setExReasonText(String exReasonText) {
		this.exReasonText = exReasonText;
	}
	public String getExCollectionName() {
		return exCollectionName;
	}
	public void setExCollectionName(String exCollectionName) {
		this.exCollectionName = exCollectionName;
	}
	public String getExCollectionTelNumber() {
		return exCollectionTelNumber;
	}
	public void setExCollectionTelNumber(String exCollectionTelNumber) {
		this.exCollectionTelNumber = exCollectionTelNumber;
	}
	public String getExCollectionPhoneNumber() {
		return exCollectionPhoneNumber;
	}
	public void setExCollectionPhoneNumber(String exCollectionPhoneNumber) {
		this.exCollectionPhoneNumber = exCollectionPhoneNumber;
	}
	public String getExCollectionZipcode() {
		return exCollectionZipcode;
	}
	public void setExCollectionZipcode(String exCollectionZipcode) {
		this.exCollectionZipcode = exCollectionZipcode;
	}
	public String getExCollectionZipcodeSeq() {
		return exCollectionZipcodeSeq;
	}
	public void setExCollectionZipcodeSeq(String exCollectionZipcodeSeq) {
		this.exCollectionZipcodeSeq = exCollectionZipcodeSeq;
	}
	public String getExCollectionAddress() {
		return exCollectionAddress;
	}
	public void setExCollectionAddress(String exCollectionAddress) {
		this.exCollectionAddress = exCollectionAddress;
	}
	public String getExCollectionAddressDetail() {
		return exCollectionAddressDetail;
	}
	public void setExCollectionAddressDetail(String exCollectionAddressDetail) {
		this.exCollectionAddressDetail = exCollectionAddressDetail;
	}
	public String getExCollectionAddressType() {
		return exCollectionAddressType;
	}
	public void setExCollectionAddressType(String exCollectionAddressType) {
		this.exCollectionAddressType = exCollectionAddressType;
	}
	public String getExCollectionAddressBilno() {
		return exCollectionAddressBilno;
	}
	public void setExCollectionAddressBilno(String exCollectionAddressBilno) {
		this.exCollectionAddressBilno = exCollectionAddressBilno;
	}
	public String getExShippingType() {
		return exShippingType;
	}
	public void setExShippingType(String exShippingType) {
		this.exShippingType = exShippingType;
	}
	public String getExReceiverName() {
		return exReceiverName;
	}
	public void setExReceiverName(String exReceiverName) {
		this.exReceiverName = exReceiverName;
	}
	public String getExReceiverTelNumber() {
		return exReceiverTelNumber;
	}
	public void setExReceiverTelNumber(String exReceiverTelNumber) {
		this.exReceiverTelNumber = exReceiverTelNumber;
	}
	public String getExReceiverPhoneNumber() {
		return exReceiverPhoneNumber;
	}
	public void setExReceiverPhoneNumber(String exReceiverPhoneNumber) {
		this.exReceiverPhoneNumber = exReceiverPhoneNumber;
	}
	public String getExReceiverZipcode() {
		return exReceiverZipcode;
	}
	public void setExReceiverZipcode(String exReceiverZipcode) {
		this.exReceiverZipcode = exReceiverZipcode;
	}
	public String getExReceiverZipcodeSeq() {
		return exReceiverZipcodeSeq;
	}
	public void setExReceiverZipcodeSeq(String exReceiverZipcodeSeq) {
		this.exReceiverZipcodeSeq = exReceiverZipcodeSeq;
	}
	public String getExReceiverAddress() {
		return exReceiverAddress;
	}
	public void setExReceiverAddress(String exReceiverAddress) {
		this.exReceiverAddress = exReceiverAddress;
	}
	public String getExReceiverAddressDetail() {
		return exReceiverAddressDetail;
	}
	public void setExReceiverAddressDetail(String exReceiverAddressDetail) {
		this.exReceiverAddressDetail = exReceiverAddressDetail;
	}
	public String getExReceiverAddressType() {
		return exReceiverAddressType;
	}
	public void setExReceiverAddressType(String exReceiverAddressType) {
		this.exReceiverAddressType = exReceiverAddressType;
	}
	public String getExReceiverAddressBilno() {
		return exReceiverAddressBilno;
	}
	public void setExReceiverAddressBilno(String exReceiverAddressBilno) {
		this.exReceiverAddressBilno = exReceiverAddressBilno;
	}
	public int getExShippingAmount() {
		return exShippingAmount;
	}
	public void setExShippingAmount(int exShippingAmount) {
		this.exShippingAmount = exShippingAmount;
	}
	public int getExAddShippingAmount() {
		return exAddShippingAmount;
	}
	public void setExAddShippingAmount(int exAddShippingAmount) {
		this.exAddShippingAmount = exAddShippingAmount;
	}
	public String getExShippingPaymentType() {
		return exShippingPaymentType;
	}
	public void setExShippingPaymentType(String exShippingPaymentType) {
		this.exShippingPaymentType = exShippingPaymentType;
	}
	public String getExShippingNumber() {
		return exShippingNumber;
	}
	public void setExShippingNumber(String exShippingNumber) {
		this.exShippingNumber = exShippingNumber;
	}
	public String getExShippingCompanyCode() {
		return exShippingCompanyCode;
	}
	public void setExShippingCompanyCode(
			String exShippingCompanyCode) {
		this.exShippingCompanyCode = exShippingCompanyCode;
	}
	public String getExApplyDate() {
		return exApplyDate;
	}
	public void setExApplyDate(String exApplyDate) {
		this.exApplyDate = exApplyDate;
	}
	public String getExEndDate() {
		return exEndDate;
	}
	public void setExEndDate(String exEndDate) {
		this.exEndDate = exEndDate;
	}
	public String getExRefusalReson() {
		return exRefusalReson;
	}
	public void setExRefusalReson(String exRefusalReson) {
		this.exRefusalReson = exRefusalReson;
	}
	public String getExRefusalResonText() {
		return exRefusalResonText;
	}
	public void setExRefusalResonText(String exRefusalResonText) {
		this.exRefusalResonText = exRefusalResonText;
	}
	
}
