package saleson.shop.deliverycompany.domain;

public class DeliveryCompany {
	
	private int deliveryCompanyId;
	private String deliveryCompanyName;
	private String telNumber;
	private String deliveryCompanyUrl;
	private String sendFlag;
	private String deliveryNumberParameter;
	private String useFlag;
	
	
	public int getDeliveryCompanyId() {
		return deliveryCompanyId;
	}
	public void setDeliveryCompanyId(int deliveryCompanyId) {
		this.deliveryCompanyId = deliveryCompanyId;
	}
	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}
	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getDeliveryCompanyUrl() {
		return deliveryCompanyUrl;
	}
	public void setDeliveryCompanyUrl(String deliveryCompanyUrl) {
		this.deliveryCompanyUrl = deliveryCompanyUrl;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getDeliveryNumberParameter() {
		return deliveryNumberParameter;
	}
	public void setDeliveryNumberParameter(String deliveryNumberParameter) {
		this.deliveryNumberParameter = deliveryNumberParameter;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
}
