package saleson.shop.statistics.domain;

import java.math.BigDecimal;

import saleson.common.utils.ShopUtils;

public class TotalSellStatistics {
	
	private double totalPayCount;
	private double totalItemPrice;
	private double totalItemCouponDiscountAmount;
	//이상우 [2017-04-13 추가] 판매자, 스팟 할인
	private double totalSellerDiscountPrice;
	private double totalSpotDiscountPrice;
	//이상우 [2017-05-01 추가] 총 할인액
	private double totalDiscountAmount;
	
	private double totalPayAmount;
	
	private double totalCancelCount;
	private double totalCancelItemPrice;
	private double totalCancelItemCouponDiscountAmount;
	//이상우 [2017-04-13 추가] 판매자, 스팟 할인
	private double totalCancelSellerDiscountPrice;
	private double totalCancelSpotDiscountPrice;
	//이상우 [2017-05-01 추가] 총 취소할인액
	private double totalCancelDiscountAmount;
	
	private double totalCancelAmount;
	
	private double totalRevenueItemPrice;
	private double totalRevenueItemCouponDiscountAmount;
	private double totalRevenueSellerDiscountPrice;
	private double totalRevenueSpotDiscountPrice;
	//이상우 [2017-05-01 추가] 총 할인액 (결제 할인 - 취소 할인)
	private double TotalRevenueDiscountAmount;
	private double totalRevenueAmount;
	
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
	public double getTotalItemCouponDiscountAmount() {
		return totalItemCouponDiscountAmount;
	}
	public void setTotalItemCouponDiscountAmount(
			double totalItemCouponDiscountAmount) {
		this.totalItemCouponDiscountAmount = totalItemCouponDiscountAmount;
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
	public double getTotalCancelItemCouponDiscountAmount() {
		return totalCancelItemCouponDiscountAmount;
	}
	public void setTotalCancelItemCouponDiscountAmount(
			double totalCancelItemCouponDiscountAmount) {
		this.totalCancelItemCouponDiscountAmount = totalCancelItemCouponDiscountAmount;
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
	public double getTotalRevenueItemCouponDiscountAmount() {
		return totalRevenueItemCouponDiscountAmount;
	}
	public void setTotalRevenueItemCouponDiscountAmount(
			double totalRevenueItemCouponDiscountAmount) {
		this.totalRevenueItemCouponDiscountAmount = totalRevenueItemCouponDiscountAmount;
	}
	public double getTotalRevenueAmount() {
		return totalRevenueAmount;
	}
	public void setTotalRevenueAmount(double totalRevenueAmount) {
		this.totalRevenueAmount = totalRevenueAmount;
	}
	public double getTotalSellerDiscountPrice() {
		return totalSellerDiscountPrice;
	}
	public void setTotalSellerDiscountPrice(double totalSellerDiscountPrice) {
		this.totalSellerDiscountPrice = totalSellerDiscountPrice;
	}
	public double getTotalSpotDiscountPrice() {
		return totalSpotDiscountPrice;
	}
	public void setTotalSpotDiscountPrice(double totalSpotDiscountPrice) {
		this.totalSpotDiscountPrice = totalSpotDiscountPrice;
	}
	public double getTotalCancelSellerDiscountPrice() {
		return totalCancelSellerDiscountPrice;
	}
	public void setTotalCancelSellerDiscountPrice(double totalCancelSellerDiscountPrice) {
		this.totalCancelSellerDiscountPrice = totalCancelSellerDiscountPrice;
	}
	public double getTotalCancelSpotDiscountPrice() {
		return totalCancelSpotDiscountPrice;
	}
	public void setTotalCancelSpotDiscountPrice(double totalCancelSpotDiscountPrice) {
		this.totalCancelSpotDiscountPrice = totalCancelSpotDiscountPrice;
	}
	public double getTotalRevenueSellerDiscountPrice() {
		return totalRevenueSellerDiscountPrice;
	}
	public void setTotalRevenueSellerDiscountPrice(double totalRevenueSellerDiscountPrice) {
		this.totalRevenueSellerDiscountPrice = totalRevenueSellerDiscountPrice;
	}
	public double getTotalRevenueSpotDiscountPrice() {
		return totalRevenueSpotDiscountPrice;
	}
	public void setTotalRevenueSpotDiscountPrice(double totalRevenueSpotDiscountPrice) {
		this.totalRevenueSpotDiscountPrice = totalRevenueSpotDiscountPrice;
	}
	// 이상우 [2017-04-13 추가] 통계 합계 할인액
	public double getTotalRevenueDiscountAmount() {
		return ShopUtils.statsNegativeNnumber(new BigDecimal(""+this.totalRevenueItemCouponDiscountAmount).add(new BigDecimal(""+this.totalRevenueSellerDiscountPrice)).add(new BigDecimal(""+this.totalRevenueSpotDiscountPrice)).doubleValue());
	}
	// 이상우 [2017-05-02 수정] 설정에 따른 부호표시 추가
	public double getTotalDiscountAmount() {
		return ShopUtils.statsNegativeNnumber(new BigDecimal(""+this.totalItemCouponDiscountAmount).add(new BigDecimal(""+this.totalSellerDiscountPrice)).add(new BigDecimal(""+this.totalSpotDiscountPrice)).doubleValue());
	}
	public double getTotalCancelDiscountAmount() {
		return new BigDecimal(""+this.totalCancelItemCouponDiscountAmount).add(new BigDecimal(""+this.totalCancelSellerDiscountPrice)).add(new BigDecimal(""+this.totalCancelSpotDiscountPrice)).doubleValue();
	}
	
	//이상우 [2017-05-02 추가] 합계 갯 수 (결제 갯 수-취소 갯 수)
	public double getTotalCount() {
		return this.getTotalPayCount() - ShopUtils.statsNegativeNnumber(this.getTotalCancelCount());
	}
	
}
