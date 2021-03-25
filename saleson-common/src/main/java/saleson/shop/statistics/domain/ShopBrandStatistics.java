package saleson.shop.statistics.domain;

import java.util.List;

import org.springframework.util.StringUtils;

public class ShopBrandStatistics {
	
	private String brand;
	
	private List<BaseSellStatistics> groupList;

	public String getBrand() {
		
		if (StringUtils.isEmpty(brand)) {
			return "정보없음";
		}
		
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<BaseSellStatistics> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<BaseSellStatistics> groupList) {
		this.groupList = groupList;
	}

	public double getTotalPayItemCouponDiscountAmount() {
		
		double value = 0;
		
		for(BaseSellStatistics item : groupList) {
			value += item.getItemCouponDiscountAmount();
		}
		
		return value;
	}
	
	public double getTotalCancelItemCouponDiscountAmount() {
		
		double value = 0;
		
		for(BaseSellStatistics item : groupList) {
			value += item.getCancelItemCouponDiscountAmount();
		}
		
		return value;
	}
	
	public double getTotalPayCount() {
		
		double value = 0;
		
		for(BaseSellStatistics item : groupList) {
			value += item.getPayCount();
		}
		
		return value;
	}
	
	public double getTotalPayItemPrice() {
		
		double value = 0;
		
		for(BaseSellStatistics item : groupList) {
			value += item.getItemPrice();
		}
		
		return value;
	}
	

	public double getTotalPay() {
		
		double value = 0;
		
		for(BaseSellStatistics item : groupList) {
			value += item.getPayTotal();
		}
		
		return value;
	}
	
	public double getTotalCancelCount() {
		
		double value = 0;
		
		for(BaseSellStatistics item : groupList) {
			value += item.getCancelCount();
		}
		
		return value;
	}
	
	public double getTotalCancelItemPrice() {
		
		double value = 0;
		
		for(BaseSellStatistics item : groupList) {
			value += item.getCancelItemPrice();
		}
		
		return value;
	}
	
	
	
	public double getTotalCancel() {
		
		double value = 0;
		
		for(BaseSellStatistics item : groupList) {
			value += item.getCancelTotal();
		}
		
		return value;
	}
	
}
