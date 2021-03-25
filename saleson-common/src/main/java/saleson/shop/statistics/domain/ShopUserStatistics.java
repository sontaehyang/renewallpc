package saleson.shop.statistics.domain;

import java.util.List;

public class ShopUserStatistics {

	private String customerCode;
	private String userName;
	private String customerTelNumber;
	private String loginId;
	private String email;

	
	List<BaseRevenueStatistics> groupList;
	
	
	
	public String getCustomerTelNumber() {
		return customerTelNumber;
	}

	public void setCustomerTelNumber(String customerTelNumber) {
		this.customerTelNumber = customerTelNumber;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<BaseRevenueStatistics> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<BaseRevenueStatistics> groupList) {
		this.groupList = groupList;
	}

	public double getTotalPayCount() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getPayCount();
		}
		
		return value;
	}
	
	public double getTotalPayCostPrice() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCostPrice();
		}
		
		return value;
	}
	
	public double getTotalPayItemPrice() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getItemPrice();
		}
		
		return value;
	}
	
	public double getTotalPayDiscountAmount() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getDiscountAmount();
		}
		
		return value;
	}
	
	public double getTotalPay() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getPayTotal();
		}
		
		return value;
	}
	
	public double getTotalCancelCount() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCancelCount();
		}
		
		return value;
	}
	
	public double getTotalCancelCostPrice() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCancelCostPrice();
		}
		
		return value;
	}
	
	public double getTotalCancelItemPrice() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCancelItemPrice();
		}
		
		return value;
	}
	
	public double getTotalCancelDiscountAmount() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCancelDiscountAmount();
		}
		
		return value;
	}
	
	public double getTotalCancel() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCancelTotal();
		}
		
		return value;
	}
}
