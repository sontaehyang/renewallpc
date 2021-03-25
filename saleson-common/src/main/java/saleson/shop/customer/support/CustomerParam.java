package saleson.shop.customer.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class CustomerParam extends SearchParam {
	private String customerType;
	private String customerCode;

	private String email;
	private String telNumber;
	
	private String newCustomerFlag;
	private String searchStartDate;
	private String searchEndDate;
	private String searchStartDateTime;
	private String searchEndDateTime;	
	
	private String userName;
	private String businessNumber;
	
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getNewCustomerFlag() {
		return newCustomerFlag;
	}

	public void setNewCustomerFlag(String newCustomerFlag) {
		this.newCustomerFlag = newCustomerFlag;
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getSearchStartDateTime() {
		return searchStartDateTime;
	}

	public void setSearchStartDateTime(String searchStartDateTime) {
		this.searchStartDateTime = searchStartDateTime;
	}

	public String getSearchEndDateTime() {
		return searchEndDateTime;
	}

	public void setSearchEndDateTime(String searchEndDateTime) {
		this.searchEndDateTime = searchEndDateTime;
	}
	
	
	
}
