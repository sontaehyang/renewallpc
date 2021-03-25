package saleson.seller.main.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Seller implements Serializable {
	private long sellerId;
	private String sellerName;
	private String loginId;
	private String password;
	private String userName;
	private String telephoneNumber;
	private String telephoneNumber1;
	private String telephoneNumber2;
	private String telephoneNumber3;
	private String phoneNumber;
	private String phoneNumber1;
	private String phoneNumber2;
	private String phoneNumber3;
	private String faxNumber;
	private String faxNumber1;
	private String faxNumber2;
	private String faxNumber3;
	private String email;
	private String email1;
	private String email2;
	
	
	private String secondUserName;
	private String secondTelephoneNumber;
	private String secondTelephoneNumber1;
	private String secondTelephoneNumber2;
	private String secondTelephoneNumber3;
	private String secondPhoneNumber;
	private String secondPhoneNumber1;
	private String secondPhoneNumber2;
	private String secondPhoneNumber3;
	private String secondEmail;
	private String secondEmail1;
	private String secondEmail2;
	
	
	private String post;
	private String post1;
	private String post2;
	private String address;
	private String addressDetail;
	private String companyName;
	private String representativeName;
	private String businessNumber;
	private String businessNumber1;
	private String businessNumber2;
	private String businessNumber3;
	private String businessLocation;
	private String businessType;
	private String businessItems;
	private float commissionRate;
	private String remittanceType;
	private String remittanceDay;
	private String bankName;
	private String bankInName;
	private String bankAccountNumber;
	private String shippingFlag;
	private int shipping;
	private int shippingFreeAmount;
	private int shippingExtraCharge1;
	private int shippingExtraCharge2;
	private String headerContent;

	private String itemApprovalType;
	private String smsSendTime;
	private Integer mdId;
	private String mdName;
	
	private String statusCode;
	private String createdDate;
	private long createdUserId;
	private String updatedDate;
	private long updatedUserId;
	
	private Integer currentMdId;
	
	private int shadowLoginLogId;
	public int getShadowLoginLogId() {
		return shadowLoginLogId;
	}
	public void setShadowLoginLogId(int shadowLoginLogId) {
		this.shadowLoginLogId = shadowLoginLogId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	public String getTelephoneNumber1() {
		return telephoneNumber1;
	}
	public void setTelephoneNumber1(String telephoneNumber1) {
		this.telephoneNumber1 = telephoneNumber1;
	}
	public String getTelephoneNumber2() {
		return telephoneNumber2;
	}
	public void setTelephoneNumber2(String telephoneNumber2) {
		this.telephoneNumber2 = telephoneNumber2;
	}
	public String getTelephoneNumber3() {
		return telephoneNumber3;
	}
	public void setTelephoneNumber3(String telephoneNumber3) {
		this.telephoneNumber3 = telephoneNumber3;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getPhoneNumber1() {
		return phoneNumber1;
	}
	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	public String getPhoneNumber3() {
		return phoneNumber3;
	}
	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	
	public String getFaxNumber1() {
		return faxNumber1;
	}
	public void setFaxNumber1(String faxNumber1) {
		this.faxNumber1 = faxNumber1;
	}
	public String getFaxNumber2() {
		return faxNumber2;
	}
	public void setFaxNumber2(String faxNumber2) {
		this.faxNumber2 = faxNumber2;
	}
	public String getFaxNumber3() {
		return faxNumber3;
	}
	public void setFaxNumber3(String faxNumber3) {
		this.faxNumber3 = faxNumber3;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getEmail() {
		return email;
	}
	
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPost() {
		return post;
	}
	
	public String getPost1() {
		return post1;
	}
	public void setPost1(String post1) {
		this.post1 = post1;
	}
	public String getPost2() {
		return post2;
	}
	public void setPost2(String post2) {
		this.post2 = post2;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	

	public String getSecondUserName() {
		return secondUserName;
	}
	public void setSecondUserName(String secondUserName) {
		this.secondUserName = secondUserName;
	}
	public String getSecondTelephoneNumber() {
		return secondTelephoneNumber;
	}
	public void setSecondTelephoneNumber(String secondTelephoneNumber) {
		this.secondTelephoneNumber = secondTelephoneNumber;
	}
	public String getSecondTelephoneNumber1() {
		return secondTelephoneNumber1;
	}
	public void setSecondTelephoneNumber1(String secondTelephoneNumber1) {
		this.secondTelephoneNumber1 = secondTelephoneNumber1;
	}
	public String getSecondTelephoneNumber2() {
		return secondTelephoneNumber2;
	}
	public void setSecondTelephoneNumber2(String secondTelephoneNumber2) {
		this.secondTelephoneNumber2 = secondTelephoneNumber2;
	}
	public String getSecondTelephoneNumber3() {
		return secondTelephoneNumber3;
	}
	public void setSecondTelephoneNumber3(String secondTelephoneNumber3) {
		this.secondTelephoneNumber3 = secondTelephoneNumber3;
	}
	public String getSecondPhoneNumber() {
		return secondPhoneNumber;
	}
	public void setSecondPhoneNumber(String secondPhoneNumber) {
		this.secondPhoneNumber = secondPhoneNumber;
	}
	public String getSecondPhoneNumber1() {
		return secondPhoneNumber1;
	}
	public void setSecondPhoneNumber1(String secondPhoneNumber1) {
		this.secondPhoneNumber1 = secondPhoneNumber1;
	}
	public String getSecondPhoneNumber2() {
		return secondPhoneNumber2;
	}
	public void setSecondPhoneNumber2(String secondPhoneNumber2) {
		this.secondPhoneNumber2 = secondPhoneNumber2;
	}
	public String getSecondPhoneNumber3() {
		return secondPhoneNumber3;
	}
	public void setSecondPhoneNumber3(String secondPhoneNumber3) {
		this.secondPhoneNumber3 = secondPhoneNumber3;
	}
	public String getSecondEmail() {
		return secondEmail;
	}
	public void setSecondEmail(String secondEmail) {
		this.secondEmail = secondEmail;
	}
	public String getSecondEmail1() {
		return secondEmail1;
	}
	public void setSecondEmail1(String secondEmail1) {
		this.secondEmail1 = secondEmail1;
	}
	public String getSecondEmail2() {
		return secondEmail2;
	}
	public void setSecondEmail2(String secondEmail2) {
		this.secondEmail2 = secondEmail2;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getRepresentativeName() {
		return representativeName;
	}
	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}
	public String getBusinessNumber() {
		return businessNumber;
	}
	
	public String getBusinessNumber1() {
		return businessNumber1;
	}
	public void setBusinessNumber1(String businessNumber1) {
		this.businessNumber1 = businessNumber1;
	}
	public String getBusinessNumber2() {
		return businessNumber2;
	}
	public void setBusinessNumber2(String businessNumber2) {
		this.businessNumber2 = businessNumber2;
	}
	public String getBusinessNumber3() {
		return businessNumber3;
	}
	public void setBusinessNumber3(String businessNumber3) {
		this.businessNumber3 = businessNumber3;
	}
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}
	public String getBusinessLocation() {
		return businessLocation;
	}
	public void setBusinessLocation(String businessLocation) {
		this.businessLocation = businessLocation;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getBusinessItems() {
		return businessItems;
	}
	public void setBusinessItems(String businessItems) {
		this.businessItems = businessItems;
	}
	
	public float getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(float commissionRate) {
		this.commissionRate = commissionRate;
	}
	public String getRemittanceType() {
		return remittanceType;
	}
	public void setRemittanceType(String remittanceType) {
		this.remittanceType = remittanceType;
	}
	public String getRemittanceDay() {
		return remittanceDay;
	}
	public void setRemittanceDay(String remittanceDay) {
		this.remittanceDay = remittanceDay;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankInName() {
		return bankInName;
	}
	public void setBankInName(String bankInName) {
		this.bankInName = bankInName;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	
	public String getShippingFlag() {
		return shippingFlag;
	}
	public void setShippingFlag(String shippingFlag) {
		this.shippingFlag = shippingFlag;
	}
	public int getShipping() {
		return shipping;
	}
	public void setShipping(int shipping) {
		this.shipping = shipping;
	}
	public int getShippingFreeAmount() {
		return shippingFreeAmount;
	}
	public void setShippingFreeAmount(int shippingFreeAmount) {
		this.shippingFreeAmount = shippingFreeAmount;
	}
	public int getShippingExtraCharge1() {
		return shippingExtraCharge1;
	}
	public void setShippingExtraCharge1(int shippingExtraCharge1) {
		this.shippingExtraCharge1 = shippingExtraCharge1;
	}
	public int getShippingExtraCharge2() {
		return shippingExtraCharge2;
	}
	public void setShippingExtraCharge2(int shippingExtraCharge2) {
		this.shippingExtraCharge2 = shippingExtraCharge2;
	}
	public String getHeaderContent() {
		return headerContent;
	}
	public void setHeaderContent(String headerContent) {
		this.headerContent = headerContent;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getItemApprovalType() {
		return itemApprovalType;
	}
	public void setItemApprovalType(String itemApprovalType) {
		this.itemApprovalType = itemApprovalType;
	}
	
	public String getSmsSendTime() {
		return smsSendTime;
	}
	public void setSmsSendTime(String smsSendTime) {
		this.smsSendTime = smsSendTime;
	}
	public Integer getMdId() {
		return mdId;
	}
	public void setMdId(Integer mdId) {
		this.mdId = mdId;
	}
	public String getMdName() {
		return mdName;
	}
	public void setMdName(String mdName) {
		this.mdName = mdName;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public long getUpdatedUserId() {
		return updatedUserId;
	}
	public void setUpdatedUserId(long updatedUserId) {
		this.updatedUserId = updatedUserId;
	}
	public Integer getCurrentMdId() {
		return currentMdId;
	}
	public void setCurrentMdId(Integer currentMdId) {
		this.currentMdId = currentMdId;
	}
}
