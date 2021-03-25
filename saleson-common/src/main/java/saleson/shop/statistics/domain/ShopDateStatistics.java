package saleson.shop.statistics.domain;

import java.util.List;

import saleson.common.utils.ShopUtils;

public class ShopDateStatistics {

	private String searchDate;
	
	List<BaseRevenueStatistics> groupList;
	
	public String getSearchDate() {
		return searchDate;
	}
	
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
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
	
	public double getTotalPayItemPrice() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getItemPrice();
		}
		
		return value;
	}
	
	public double getTotalPayItemAmount() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getItemAmount();
		}
		
		return value;
	}
	
	public double getTotalPayShipping() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getShipping();
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
	
	public double getTotalPayDiscountAmount() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getPayDiscountTotal();
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
	
	public double getTotalCancelItemAmount() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCancelItemAmount();
		}
		
		return value;
	}
	
	public double getTotalCancelDiscountAmount() {
		
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCancelDiscountTotal();
		}
		
		return value;
	}
	
	
	public double getTotalCancelShipping() {
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCancelShipping();
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
	
	public double getTotalSupplyAmount() {
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getSupplyAmount();
		}
		
		return value;
	}
	
	public double getTotalCancelSupplyAmount() {
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getCancelSupplyAmount();
		}
		
		return value;
	}
	
	public double getTotalSumFeesAmount() {
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getSumFeesAmount();
		}
		
		return value;
	}
	
	public double getTotalSumNetProfitAmount() {
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getSumNetProfitAmount();
		}
		
		return value;
	}
	
	
	public double getTotalItemAmount() {
		return getTotalPayItemAmount() - ShopUtils.statsNegativeNnumber(getTotalCancelItemAmount());
	}
	
	public double getTotalShipping() {
		return getTotalPayShipping() - ShopUtils.statsNegativeNnumber(getTotalCancelShipping());
	}
	
	public double getTotalAmount() {
		return getTotalPay() - ShopUtils.statsNegativeNnumber(getTotalCancel());
	}
	
	public double getGrandTotal() {
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getGrandTotal();
		}
		
		return value;
	}
	
	
	
	public double getTotalSumProfitAmount() {
		double value = 0;
		
		for(BaseRevenueStatistics item : groupList) {
			value += item.getSumProfitAmount();
		}
		
		return value;
	}
	
	// 수수료율 (수수료액 / 주문합계 * 100)
	public int getTotalSumFeesPercent() {
		return (int) ((getTotalSumFeesAmount() / getTotalAmount()) * 100);
	}
	
	// 순이익율 (순이익액 / 주문합계 X 100)
	public int getTotalSumNetProfitPercent() {
		return (int) ((getTotalSumNetProfitAmount() / getTotalAmount()) * 100);
	}
	
	// 총이익율 (총이익액 / 주문합계 X 100)
	public int getTotalSumProfitPercent() {
		return (int) ((getTotalSumProfitAmount() / getTotalAmount()) * 100);
	}
}
