package saleson.shop.statistics.domain;

public class TotalRevenueStatistics {
	// [2017-04-11] 신규 항목
	private double totalPayCount;
	private double totalPayItemPrice;
	private double totalPayDiscountAmount;
	private double totalPayItemAmount;
	private double totalPayShipping;
	private double totalPayAmount;
	
	private double totalCancelCount;
	private double totalCancelItemPrice;
	private double totalCancelDiscountAmount;
	private double totalCancelItemAmount;
	private double totalCancelShipping;
	private double totalCancelAmount;
	
	private double totalItemAmount;
	private double totalShipping;
	private double totalAmount;

	
	// 이전.
	private double totalItemPrice;
	private double totalDiscountAmount;
	private double totalCostPrice;
	private double totalSupplyAmount;
	private double totalCancelCostPrice;
	private double totalCancelSupplyAmount;
	
	private double totalRevenueItemPrice;
	private double totalRevenueTotalAmount;
	private double totalRevenueCostPrice;
	private double totalRevenueSupplyAmount;
	private double totalRevenueDiscountAmount;
	private double totalRevenueAmount;
	 
	private double totalRevenueSumFeesAmount;
	private double totalRevenueSumProfitAmount;
	private double totalRevenueSumNetProfitAmount;
	
	// 수수료율 (수수료액 / 주문합계 * 100)
	public int getTotalRevenueSumFeesPercent() {
		return (int) ((getTotalRevenueSumFeesAmount() / getTotalRevenueTotalAmount()) * 100);
	}
	
	// 순이익율 (순이익액 / 주문합계 X 100)
	public int getTotalRevenueSumNetProfitPercent() {
		return (int) ((getTotalRevenueSumNetProfitAmount() / getTotalRevenueTotalAmount()) * 100);
	}
	
	// 총이익율 (총이익액 / 주문합계 X 100)
	public int getTotalRevenueSumProfitPercent() {
		return (int) ((getTotalRevenueSumProfitAmount() / getTotalRevenueTotalAmount()) * 100);
	}
	
	public double getTotalRevenueTotalAmount() {
		return totalRevenueTotalAmount;
	}
	public void setTotalRevenueTotalAmount(double totalRevenueTotalAmount) {
		this.totalRevenueTotalAmount = totalRevenueTotalAmount;
	}
	public double getTotalCostPrice() {
		return totalCostPrice;
	}
	public void setTotalCostPrice(double totalCostPrice) {
		this.totalCostPrice = totalCostPrice;
	}
	public double getTotalCancelCostPrice() {
		return totalCancelCostPrice;
	}
	public void setTotalCancelCostPrice(double totalCancelCostPrice) {
		this.totalCancelCostPrice = totalCancelCostPrice;
	}
	public double getTotalRevenueCostPrice() {
		return totalRevenueCostPrice;
	}
	public void setTotalRevenueCostPrice(double totalRevenueCostPrice) {
		this.totalRevenueCostPrice = totalRevenueCostPrice;
	}
	public double getTotalPayCount() {
		return totalPayCount;
	}
	public void setTotalPayCount(double totalPayCount) {
		this.totalPayCount = totalPayCount;
	}
	public double getTotalItemPrice() {
		return totalItemPrice;
	}
	public void setTotalItemPrice(double totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}
	
	public double getTotalDiscountAmount() {
		return totalDiscountAmount;
	}
	public void setTotalDiscountAmount(double totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}
	
	public double getTotalPayAmount() {
		return totalPayAmount;
	}
	public void setTotalPayAmount(double totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}
	public double getTotalCancelCount() {
		return totalCancelCount;
	}
	public void setTotalCancelCount(double totalCancelCount) {
		this.totalCancelCount = totalCancelCount;
	}
	public double getTotalCancelItemPrice() {
		return totalCancelItemPrice;
	}
	public void setTotalCancelItemPrice(double totalCancelItemPrice) {
		this.totalCancelItemPrice = totalCancelItemPrice;
	}
	
	public double getTotalCancelDiscountAmount() {
		return totalCancelDiscountAmount;
	}
	public void setTotalCancelDiscountAmount(double totalCancelDiscountAmount) {
		this.totalCancelDiscountAmount = totalCancelDiscountAmount;
	}
	
	public double getTotalCancelAmount() {
		return totalCancelAmount;
	}
	public void setTotalCancelAmount(double totalCancelAmount) {
		this.totalCancelAmount = totalCancelAmount;
	}
	public double getTotalRevenueItemPrice() {
		return totalRevenueItemPrice;
	}
	public void setTotalRevenueItemPrice(double totalRevenueItemPrice) {
		this.totalRevenueItemPrice = totalRevenueItemPrice;
	}
	
	public double getTotalRevenueDiscountAmount() {
		return totalRevenueDiscountAmount;
	}
	public void setTotalRevenueDiscountAmount(double totalRevenueDiscountAmount) {
		this.totalRevenueDiscountAmount = totalRevenueDiscountAmount;
	}
	
	public double getTotalRevenueAmount() {
		return totalRevenueAmount;
	}
	public void setTotalRevenueAmount(double totalRevenueAmount) {
		this.totalRevenueAmount = totalRevenueAmount;
	}
	public double getTotalSupplyAmount() {
		return totalSupplyAmount;
	}
	public void setTotalSupplyAmount(double totalSupplyAmount) {
		this.totalSupplyAmount = totalSupplyAmount;
	}
	public double getTotalCancelSupplyAmount() {
		return totalCancelSupplyAmount;
	}
	public void setTotalCancelSupplyAmount(double totalCancelSupplyAmount) {
		this.totalCancelSupplyAmount = totalCancelSupplyAmount;
	}
	public double getTotalRevenueSupplyAmount() {
		return totalRevenueSupplyAmount;
	}
	public void setTotalRevenueSupplyAmount(double totalRevenueSupplyAmount) {
		this.totalRevenueSupplyAmount = totalRevenueSupplyAmount;
	}
	public double getTotalRevenueSumFeesAmount() {
		return totalRevenueSumFeesAmount;
	}
	public void setTotalRevenueSumFeesAmount(double totalRevenueSumFeesAmount) {
		this.totalRevenueSumFeesAmount = totalRevenueSumFeesAmount;
	}
	public double getTotalRevenueSumProfitAmount() {
		return totalRevenueSumProfitAmount;
	}
	public void setTotalRevenueSumProfitAmount(double totalRevenueSumProfitAmount) {
		this.totalRevenueSumProfitAmount = totalRevenueSumProfitAmount;
	}
	public double getTotalRevenueSumNetProfitAmount() {
		return totalRevenueSumNetProfitAmount;
	}
	public void setTotalRevenueSumNetProfitAmount(
			double totalRevenueSumNetProfitAmount) {
		this.totalRevenueSumNetProfitAmount = totalRevenueSumNetProfitAmount;
	}

	public double getTotalPayItemPrice() {
		return totalPayItemPrice;
	}

	public void setTotalPayItemPrice(double totalPayItemPrice) {
		this.totalPayItemPrice = totalPayItemPrice;
	}

	public double getTotalPayDiscountAmount() {
		return totalPayDiscountAmount;
	}

	public void setTotalPayDiscountAmount(double totalPayDiscountAmount) {
		this.totalPayDiscountAmount = totalPayDiscountAmount;
	}

	public double getTotalPayItemAmount() {
		return totalPayItemAmount;
	}

	public void setTotalPayItemAmount(double totalPayItemAmount) {
		this.totalPayItemAmount = totalPayItemAmount;
	}

	public double getTotalPayShipping() {
		return totalPayShipping;
	}

	public void setTotalPayShipping(double totalPayShipping) {
		this.totalPayShipping = totalPayShipping;
	}

	public double getTotalCancelItemAmount() {
		return totalCancelItemAmount;
	}

	public void setTotalCancelItemAmount(double totalCancelItemAmount) {
		this.totalCancelItemAmount = totalCancelItemAmount;
	}

	public double getTotalCancelShipping() {
		return totalCancelShipping;
	}

	public void setTotalCancelShipping(double totalCancelShipping) {
		this.totalCancelShipping = totalCancelShipping;
	}

	public double getTotalItemAmount() {
		return totalItemAmount;
	}

	public void setTotalItemAmount(double totalItemAmount) {
		this.totalItemAmount = totalItemAmount;
	}

	public double getTotalShipping() {
		return totalShipping;
	}

	public void setTotalShipping(double totalShipping) {
		this.totalShipping = totalShipping;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
