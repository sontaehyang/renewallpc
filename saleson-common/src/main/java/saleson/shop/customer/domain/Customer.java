package saleson.shop.customer.domain;


import com.onlinepowers.framework.web.pagination.Pagination;

import javax.validation.constraints.NotEmpty;

public class Customer {
	
	/**
	 * 2015.1.22 거래처 코드 검증 
	 */
	@NotEmpty
	private String customerCode;
	/**
	 * 2015.1.22 거래처명 검증
	 */
	@NotEmpty
	private String customerName;
	private long userId;
	/**
	 * 2015.1.22 거래처 검증
	 */
	@NotEmpty
	private String customerType;
	private String customerGroup;
	private String businessNumber;
	/**
	 * 2015.1.22 전화번호 검증
	 */
	@NotEmpty
	private String telNumber;
	private String bossName;
	private String category;
	private String event;
	private String zipcode;
	private String address;
	private String addressDetail;
	private String memo;
	private String staffName;
	private String staffDepartment;
	private String staffTelNumber;
	private String staffPhoneNumber;
	private String bankNumber;
	private String bankName;
	private String bankInName;
	private String bankCmsCode;
	private String customerStaffName;
	private String customerStaffPosition;
	private String customerStaffTelNumber;
	private String customerStaffPhoneNumber;
	private String customerStaffEmail;
	private String dmZipcode;
	private String dmAddress;
	private String dmAddressDetail;
	private String businessNumberCode;
	private Pagination pagination;
	private String businessType;
	private String phoneNumber;
	private String email;
	
	private String faxGroup;
	private String faxNumber;
	private String homepage;
	
	private String createDate;
	private String updateDate;
	
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFaxGroup() {
		return faxGroup;
	}
	public void setFaxGroup(String faxGroup) {
		this.faxGroup = faxGroup;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getCustomerGroup() {
		return customerGroup;
	}
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getBusinessNumber() {
		return businessNumber;
	}
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffDepartment() {
		return staffDepartment;
	}
	public void setStaffDepartment(String staffDepartment) {
		this.staffDepartment = staffDepartment;
	}
	public String getStaffTelNumber() {
		return staffTelNumber;
	}
	public void setStaffTelNumber(String staffTelNumber) {
		this.staffTelNumber = staffTelNumber;
	}
	public String getStaffPhoneNumber() {
		return staffPhoneNumber;
	}
	public void setStaffPhoneNumber(String staffPhoneNumber) {
		this.staffPhoneNumber = staffPhoneNumber;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
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
	public String getBankCmsCode() {
		return bankCmsCode;
	}
	public void setBankCmsCode(String bankCmsCode) {
		this.bankCmsCode = bankCmsCode;
	}
	public String getCustomerStaffName() {
		return customerStaffName;
	}
	public void setCustomerStaffName(String customerStaffName) {
		this.customerStaffName = customerStaffName;
	}
	public String getCustomerStaffPosition() {
		return customerStaffPosition;
	}
	public void setCustomerStaffPosition(String customerStaffPosition) {
		this.customerStaffPosition = customerStaffPosition;
	}
	public String getCustomerStaffTelNumber() {
		return customerStaffTelNumber;
	}
	public void setCustomerStaffTelNumber(String customerStaffTelNumber) {
		this.customerStaffTelNumber = customerStaffTelNumber;
	}
	public String getCustomerStaffPhoneNumber() {
		return customerStaffPhoneNumber;
	}
	public void setCustomerStaffPhoneNumber(String customerStaffPhoneNumber) {
		this.customerStaffPhoneNumber = customerStaffPhoneNumber;
	}
	public String getCustomerStaffEmail() {
		return customerStaffEmail;
	}
	public void setCustomerStaffEmail(String customerStaffEmail) {
		this.customerStaffEmail = customerStaffEmail;
	}
	public String getDmZipcode() {
		return dmZipcode;
	}
	public void setDmZipcode(String dmZipcode) {
		this.dmZipcode = dmZipcode;
	}
	public String getDmAddress() {
		return dmAddress;
	}
	public void setDmAddress(String dmAddress) {
		this.dmAddress = dmAddress;
	}
	public String getDmAddressDetail() {
		return dmAddressDetail;
	}
	public void setDmAddressDetail(String dmAddressDetail) {
		this.dmAddressDetail = dmAddressDetail;
	}
	public String getBusinessNumberCode() {
		return businessNumberCode;
	}
	public void setBusinessNumberCode(String businessNumberCode) {
		this.businessNumberCode = businessNumberCode;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
