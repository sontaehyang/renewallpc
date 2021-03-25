package saleson.shop.smsconfig.domain;

public class SmsConfig {
	private int smsConfigId;
	private String templateId;
	private String buyerTitle;
	private String buyerContent;
	private String adminTitle;
	private String adminContent;
	private String buyerSendFlag;
	private String adminSendFlag;
	private String createdDate;
	private String smsType;
	
	public String getBuyerTitle() {
		return buyerTitle;
	}
	public void setBuyerTitle(String buyerTitle) {
		this.buyerTitle = buyerTitle;
	}
	public String getAdminTitle() {
		return adminTitle;
	}
	public void setAdminTitle(String adminTitle) {
		this.adminTitle = adminTitle;
	}
	public String getSmsType() {
		return smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	public int getSmsConfigId() {
		return smsConfigId;
	}
	public void setSmsConfigId(int smsConfigId) {
		this.smsConfigId = smsConfigId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	public String getBuyerContent() {
		return buyerContent;
	}
	public void setBuyerContent(String buyerContent) {
		this.buyerContent = buyerContent;
	}
	
	public String getAdminContent() {
		return adminContent;
	}
	public void setAdminContent(String adminContent) {
		this.adminContent = adminContent;
	}
	public String getBuyerSendFlag() {
		return buyerSendFlag;
	}
	public void setBuyerSendFlag(String buyerSendFlag) {
		this.buyerSendFlag = buyerSendFlag;
	}
	public String getAdminSendFlag() {
		return adminSendFlag;
	}
	public void setAdminSendFlag(String adminSendFlag) {
		this.adminSendFlag = adminSendFlag;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
