package saleson.shop.mall.domain;

public class MallOrderCancel {
	private int mallOrderId;
	private String claimCode;
	private int claimQuantity;
	private String orderCode;
	private String claimApplySubject;
	private String cancelReason;
	private String cancelReasonText;
	private String cancelRefusalReson;
	private String cancelRefusalResonText;
	private String cancelApplyDate;
	
	private String cancelDeliveryDate;
	private String cancelDeliveryCompanyCode;
	private String cancelDeliveryNumber;
	
	private MallOrder mallOrder;
	
	private String claimStatus;
	
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
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
	public int getClaimQuantity() {
		return claimQuantity;
	}
	public void setClaimQuantity(int claimQuantity) {
		this.claimQuantity = claimQuantity;
	}
	public String getCancelDeliveryDate() {
		return cancelDeliveryDate;
	}
	public void setCancelDeliveryDate(String cancelDeliveryDate) {
		this.cancelDeliveryDate = cancelDeliveryDate;
	}
	public String getCancelDeliveryCompanyCode() {
		return cancelDeliveryCompanyCode;
	}
	public void setCancelDeliveryCompanyCode(String cancelDeliveryCompanyCode) {
		this.cancelDeliveryCompanyCode = cancelDeliveryCompanyCode;
	}
	public String getCancelDeliveryNumber() {
		return cancelDeliveryNumber;
	}
	public void setCancelDeliveryNumber(String cancelDeliveryNumber) {
		this.cancelDeliveryNumber = cancelDeliveryNumber;
	}
	public String getCancelReasonText() {
		return cancelReasonText;
	}
	public void setCancelReasonText(String cancelReasonText) {
		this.cancelReasonText = cancelReasonText;
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
	public String getClaimApplySubject() {
		return claimApplySubject;
	}
	public void setClaimApplySubject(String claimApplySubject) {
		this.claimApplySubject = claimApplySubject;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getCancelRefusalReson() {
		return cancelRefusalReson;
	}
	public void setCancelRefusalReson(String cancelRefusalReson) {
		this.cancelRefusalReson = cancelRefusalReson;
	}
	public String getCancelRefusalResonText() {
		return cancelRefusalResonText;
	}
	public void setCancelRefusalResonText(String cancelRefusalResonText) {
		this.cancelRefusalResonText = cancelRefusalResonText;
	}
	public String getCancelApplyDate() {
		return cancelApplyDate;
	}
	public void setCancelApplyDate(String cancelApplyDate) {
		this.cancelApplyDate = cancelApplyDate;
	}
}
