package saleson.shop.mall.domain;

public class MallOrderReturn {
	private int mallOrderId;
	private String orderCode;
	private String claimStatus;
	private String claimCode;
	private int claimQuantity;
	private String rtReason;
	private String rtReasonText;
	private String rtCollectionName;
	private String rtCollectionTelNumber;
	private String rtCollectionPhoneNumber;
	private String rtCollectionZipcode;
	private String rtCollectionZipcodeSeq;
	private String rtCollectionAddress;
	private String rtCollectionAddressDetail;
	private String rtCollectionAddressType;
	private String rtCollectionAddressBilno;
	private String rtShippingType;
	private int rtShippingAmount;
	private int rtDefaultShippingAmount;
	private String rtShippingPaymentType;
	private String rtShippingNumber;
	private String rtShippingCompanyCode;
	private String rtApplyDate;
	
	private String rtHoldReson;
	private String rtHoldResonText;
	
	private String rtRefusalReson;
	private String rtRefusalResonText;
	
	private MallOrder mallOrder;
	
	
	public MallOrder getMallOrder() {
		return mallOrder;
	}
	public void setMallOrder(MallOrder mallOrder) {
		this.mallOrder = mallOrder;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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
	public String getRtReason() {
		return rtReason;
	}
	public void setRtReason(String rtReason) {
		this.rtReason = rtReason;
	}
	public String getRtReasonText() {
		return rtReasonText;
	}
	public void setRtReasonText(String rtReasonText) {
		this.rtReasonText = rtReasonText;
	}
	public String getRtCollectionName() {
		return rtCollectionName;
	}
	public void setRtCollectionName(String rtCollectionName) {
		this.rtCollectionName = rtCollectionName;
	}
	public String getRtCollectionTelNumber() {
		return rtCollectionTelNumber;
	}
	public void setRtCollectionTelNumber(String rtCollectionTelNumber) {
		this.rtCollectionTelNumber = rtCollectionTelNumber;
	}
	public String getRtCollectionPhoneNumber() {
		return rtCollectionPhoneNumber;
	}
	public void setRtCollectionPhoneNumber(String rtCollectionPhoneNumber) {
		this.rtCollectionPhoneNumber = rtCollectionPhoneNumber;
	}
	public String getRtCollectionZipcode() {
		return rtCollectionZipcode;
	}
	public void setRtCollectionZipcode(String rtCollectionZipcode) {
		this.rtCollectionZipcode = rtCollectionZipcode;
	}
	public String getRtCollectionZipcodeSeq() {
		return rtCollectionZipcodeSeq;
	}
	public void setRtCollectionZipcodeSeq(String rtCollectionZipcodeSeq) {
		this.rtCollectionZipcodeSeq = rtCollectionZipcodeSeq;
	}
	public String getRtCollectionAddress() {
		return rtCollectionAddress;
	}
	public void setRtCollectionAddress(String rtCollectionAddress) {
		this.rtCollectionAddress = rtCollectionAddress;
	}
	public String getRtCollectionAddressDetail() {
		return rtCollectionAddressDetail;
	}
	public void setRtCollectionAddressDetail(String rtCollectionAddressDetail) {
		this.rtCollectionAddressDetail = rtCollectionAddressDetail;
	}
	public String getRtCollectionAddressType() {
		return rtCollectionAddressType;
	}
	public void setRtCollectionAddressType(String rtCollectionAddressType) {
		this.rtCollectionAddressType = rtCollectionAddressType;
	}
	public String getRtCollectionAddressBilno() {
		return rtCollectionAddressBilno;
	}
	public void setRtCollectionAddressBilno(String rtCollectionAddressBilno) {
		this.rtCollectionAddressBilno = rtCollectionAddressBilno;
	}
	public String getRtShippingType() {
		return rtShippingType;
	}
	public void setRtShippingType(String rtShippingType) {
		this.rtShippingType = rtShippingType;
	}
	public int getRtShippingAmount() {
		return rtShippingAmount;
	}
	public void setRtShippingAmount(int rtShippingAmount) {
		this.rtShippingAmount = rtShippingAmount;
	}
	public int getRtDefaultShippingAmount() {
		return rtDefaultShippingAmount;
	}
	public void setRtDefaultShippingAmount(int rtDefaultShippingAmount) {
		this.rtDefaultShippingAmount = rtDefaultShippingAmount;
	}
	public String getRtShippingPaymentType() {
		return rtShippingPaymentType;
	}
	public void setRtShippingPaymentType(String rtShippingPaymentType) {
		this.rtShippingPaymentType = rtShippingPaymentType;
	}
	public String getRtShippingNumber() {
		return rtShippingNumber;
	}
	public void setRtShippingNumber(String rtShippingNumber) {
		this.rtShippingNumber = rtShippingNumber;
	}
	public String getRtShippingCompanyCode() {
		return rtShippingCompanyCode;
	}
	public void setRtShippingCompanyCode(
			String rtShippingCompanyCode) {
		this.rtShippingCompanyCode = rtShippingCompanyCode;
	}
	public String getRtApplyDate() {
		return rtApplyDate;
	}
	public void setRtApplyDate(String rtApplyDate) {
		this.rtApplyDate = rtApplyDate;
	}
	public String getRtHoldReson() {
		return rtHoldReson;
	}
	public void setRtHoldReson(String rtHoldReson) {
		this.rtHoldReson = rtHoldReson;
	}
	public String getRtHoldResonText() {
		return rtHoldResonText;
	}
	public void setRtHoldResonText(String rtHoldResonText) {
		this.rtHoldResonText = rtHoldResonText;
	}
	public String getRtRefusalReson() {
		return rtRefusalReson;
	}
	public void setRtRefusalReson(String rtRefusalReson) {
		this.rtRefusalReson = rtRefusalReson;
	}
	public String getRtRefusalResonText() {
		return rtRefusalResonText;
	}
	public void setRtRefusalResonText(String rtRefusalResonText) {
		this.rtRefusalResonText = rtRefusalResonText;
	}
	
	
	
}
