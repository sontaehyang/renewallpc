package saleson.shop.mall.domain;


public class MallBase {
	private int mallOrderId;

	private String cancelRefusalReson;
	private String cancelRefusalResonText;
	
	private String returnHoldReson;
	private String returnHoldResonText;
	
	private String exchangeRefusalReson;
	private String exchangeRefusalResonText;
	
	private String returnRefusalReson;
	private String returnRefusalResonText;
	
	private int mallConfigId; 
	private String mallType; 
	private String systemMessage;
	
	private int itemId;
	private String matchedOptions;

	private String resendDeliveryCompanyCode;
	private String resendDeliveryNumber;
	
	private String claimStatus;
	
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public String getReturnRefusalReson() {
		return returnRefusalReson;
	}
	public void setReturnRefusalReson(String returnRefusalReson) {
		this.returnRefusalReson = returnRefusalReson;
	}
	public String getReturnRefusalResonText() {
		return returnRefusalResonText;
	}
	public void setReturnRefusalResonText(String returnRefusalResonText) {
		this.returnRefusalResonText = returnRefusalResonText;
	}
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
	public String getExchangeRefusalReson() {
		return exchangeRefusalReson;
	}
	public void setExchangeRefusalReson(String exchangeRefusalReson) {
		this.exchangeRefusalReson = exchangeRefusalReson;
	}
	public String getExchangeRefusalResonText() {
		return exchangeRefusalResonText;
	}
	public void setExchangeRefusalResonText(String exchangeRefusalResonText) {
		this.exchangeRefusalResonText = exchangeRefusalResonText;
	}
	public String getReturnHoldReson() {
		return returnHoldReson;
	}
	public void setReturnHoldReson(String returnHoldReson) {
		this.returnHoldReson = returnHoldReson;
	}
	public String getReturnHoldResonText() {
		return returnHoldResonText;
	}
	public void setReturnHoldResonText(String returnHoldResonText) {
		this.returnHoldResonText = returnHoldResonText;
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
	public String getMatchedOptions() {
		return matchedOptions;
	}
	public void setMatchedOptions(String matchedOptions) {
		this.matchedOptions = matchedOptions;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getSystemMessage() {
		return systemMessage;
	}
	public void setSystemMessage(String systemMessage) {
		this.systemMessage = systemMessage;
	}
	public int getMallOrderId() {
		return mallOrderId;
	}
	public void setMallOrderId(int mallOrderId) {
		this.mallOrderId = mallOrderId;
	}
	public int getMallConfigId() {
		return mallConfigId;
	}
	public void setMallConfigId(int mallConfigId) {
		this.mallConfigId = mallConfigId;
	}
	public String getMallType() {
		return mallType;
	}
	public void setMallType(String mallType) {
		this.mallType = mallType;
	}
	
	
}
