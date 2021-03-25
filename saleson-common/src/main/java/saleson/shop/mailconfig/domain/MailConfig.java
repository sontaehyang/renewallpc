package saleson.shop.mailconfig.domain;



public class MailConfig implements Cloneable {
	
	private int mailConfigId;
	private String smsConfig;
	private String templateId;
	private String title;
	private String buyerSubject;
	private String adminSubject;
	private String sellerSubject;
	private String buyerContent;
	private String adminContent;
	private String sellerContent;
	private String buyerSendFlag;
	private String adminSendFlag;
	private String sellerSendFlag;
	private String buyerTagUse;
	private String adminTagUse;
	private String sellerTagUse;
	private String createdDate;
	
	private String mobileBuyerSubject;
	private String mobileAdminSubject;
	private String mobileSellerSubject;
	private String mobileBuyerContent;
	private String mobileAdminContent;
	private String mobileSellerContent;
	
	
	private String content;
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	private String bcc;
	private String adminEmail;
	
	
	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	
	public String getMobileBuyerSubject() {
		return mobileBuyerSubject;
	}
	public void setMobileBuyerSubject(String mobileBuyerSubject) {
		this.mobileBuyerSubject = mobileBuyerSubject;
	}
	public String getMobileAdminSubject() {
		return mobileAdminSubject;
	}
	public void setMobileAdminSubject(String mobileAdminSubject) {
		this.mobileAdminSubject = mobileAdminSubject;
	}
	public String getMobileSellerSubject() {
		return mobileSellerSubject;
	}
	public void setMobileSellerSubject(String mobileSellerSubject) {
		this.mobileSellerSubject = mobileSellerSubject;
	}
	public String getMobileBuyerContent() {
		return mobileBuyerContent;
	}
	public void setMobileBuyerContent(String mobileBuyerContent) {
		this.mobileBuyerContent = mobileBuyerContent;
	}
	public String getMobileAdminContent() {
		return mobileAdminContent;
	}
	public void setMobileAdminContent(String mobileAdminContent) {
		this.mobileAdminContent = mobileAdminContent;
	}
	public String getMobileSellerContent() {
		return mobileSellerContent;
	}
	public void setMobileSellerContent(String mobileSellerContent) {
		this.mobileSellerContent = mobileSellerContent;
	}
	public int getMailConfigId() {
		return mailConfigId;
	}
	public void setMailConfigId(int mailConfigId) {
		this.mailConfigId = mailConfigId;
	}
	public String getSmsConfig() {
		return smsConfig;
	}
	public void setSmsConfig(String smsConfig) {
		this.smsConfig = smsConfig;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBuyerSubject() {
		return buyerSubject;
	}
	public void setBuyerSubject(String buyerSubject) {
		this.buyerSubject = buyerSubject;
	}
	public String getAdminSubject() {
		return adminSubject;
	}
	public void setAdminSubject(String adminSubject) {
		this.adminSubject = adminSubject;
	}
	public String getSellerSubject() {
		return sellerSubject;
	}
	public void setSellerSubject(String sellerSubject) {
		this.sellerSubject = sellerSubject;
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
	public String getSellerContent() {
		return sellerContent;
	}
	public void setSellerContent(String sellerContent) {
		this.sellerContent = sellerContent;
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
	public String getSellerSendFlag() {
		return sellerSendFlag;
	}
	public void setSellerSendFlag(String sellerSendFlag) {
		this.sellerSendFlag = sellerSendFlag;
	}
	public String getBuyerTagUse() {
		return buyerTagUse;
	}
	public void setBuyerTagUse(String buyerTagUse) {
		this.buyerTagUse = buyerTagUse;
	}
	public String getAdminTagUse() {
		return adminTagUse;
	}
	public void setAdminTagUse(String adminTagUse) {
		this.adminTagUse = adminTagUse;
	}
	public String getSellerTagUse() {
		return sellerTagUse;
	}
	public void setSellerTagUse(String sellerTagUse) {
		this.sellerTagUse = sellerTagUse;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getBuyerSendFlagText(){
		String useText = "발송";
		if (buyerSendFlag != null) {
			if( buyerSendFlag.equals("N") ){
				useText = "발송안함";
			}
		}
		
		return useText;
	}
	
	public String getAdminSendFlagText(){
		String useText = "발송";
		if (adminSendFlag != null) {
			if( adminSendFlag.equals("N") ){
				useText = "발송안함";
			}
		}
		return useText;
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
