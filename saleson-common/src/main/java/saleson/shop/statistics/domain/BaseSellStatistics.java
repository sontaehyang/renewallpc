package saleson.shop.statistics.domain;

import java.math.BigDecimal;

import saleson.common.utils.ShopUtils;

public class BaseSellStatistics {
	
	private String osType;
	
	private double payCount;
	private double cancelCount;
	
	private double itemPrice;
	private double cancelItemPrice;

	private double itemCouponDiscountAmount;
	private double cancelItemCouponDiscountAmount;
	
	// 이상우 [2017-04-13 추가] 판매자, 스팟 할인, 총 할인 추가
	private double sellerDiscountPrice;
	private double cancelSellerDiscountPrice;
	
	private double spotDiscountPrice;
	private double cancelSpotDiscountPrice;

	// 권세희 [2019-02-26 추가] 회원 할인 추가
	private double userLevelDiscountPrice;
	private double cancelUserLevelDiscountPrice;
	
	public double getSellerDiscountPrice() {
		return sellerDiscountPrice;
	}
	public void setSellerDiscountPrice(double sellerDiscountPrice) {
		this.sellerDiscountPrice = sellerDiscountPrice;
	}
	public double getCancelSellerDiscountPrice() {
		return cancelSellerDiscountPrice;
	}
	public void setCancelSellerDiscountPrice(double cancelSellerDiscountPrice) {
		this.cancelSellerDiscountPrice = cancelSellerDiscountPrice;
	}
	public double getSpotDiscountPrice() {
		return spotDiscountPrice;
	}
	public void setSpotDiscountPrice(double spotDiscountPrice) {
		this.spotDiscountPrice = spotDiscountPrice;
	}
	public double getCancelSpotDiscountPrice() {
		return cancelSpotDiscountPrice;
	}
	public void setCancelSpotDiscountPrice(double cancelSpotDiscountPrice) {
		this.cancelSpotDiscountPrice = cancelSpotDiscountPrice;
	}
	//할인 총 합
	public double getTotalDiscountAmount() {
		return ShopUtils.statsNegativeNnumber(new BigDecimal(""+this.itemCouponDiscountAmount).add(new BigDecimal(""+this.sellerDiscountPrice)).add(new BigDecimal(""+this.spotDiscountPrice)).add(new BigDecimal(""+this.userLevelDiscountPrice)).doubleValue());
	}
	//취소 할인 총 합
	public double getCancelTotalDiscountAmount() {
		return new BigDecimal(""+this.cancelItemCouponDiscountAmount).add(new BigDecimal(""+this.cancelSellerDiscountPrice)).add(new BigDecimal(""+this.cancelSpotDiscountPrice)).add(new BigDecimal(""+this.cancelUserLevelDiscountPrice)).doubleValue();
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	// WEB 주문수 (결제 + 취소(음수)) 
	public double getSumCount() {
		return payCount - cancelCount;
	}

	// WEB 상품 금액 (결제 + 취소(음수))
	public double getSumItemPrice() {
		return itemPrice - cancelItemPrice;
	}
	
	// WEB 쿠폰 할인 금액 (결제(음수) + 취소)
	public double getSumItemCouponDiscountAmount() {
		return itemCouponDiscountAmount - cancelItemCouponDiscountAmount;
	}
	
	// 이상우 [2017-04-13 추가] 판매자, 스팟, 총 할인
	public double getSumSellerDiscountPrice() {
		return new BigDecimal(""+this.sellerDiscountPrice).subtract(new BigDecimal(""+this.cancelSellerDiscountPrice)).doubleValue();
	}
	
	public double getSumSpotDiscountPrice() {
		return new BigDecimal(""+this.spotDiscountPrice).subtract(new BigDecimal(""+this.cancelSpotDiscountPrice)).doubleValue();
	}

	public double getSumDiscountAmount() {
		return new BigDecimal(""+this.getTotalDiscountAmount()).subtract(new BigDecimal(""+ShopUtils.statsNegativeNnumber(this.getCancelTotalDiscountAmount()))).doubleValue();
	}

	// 권세희 [2019-02-26 추가] 회원 할인
	public double getSumUserLevelDiscountPrice() {
		return new BigDecimal(""+this.userLevelDiscountPrice).subtract(new BigDecimal(""+this.cancelUserLevelDiscountPrice)).doubleValue();
	}

	public double getPayCount() {
		return payCount;
	}
	public void setPayCount(double payCount) {
		this.payCount = payCount;
	}
	public double getCancelCount() {
		return ShopUtils.statsNegativeNnumber(cancelCount);
	}
	public void setCancelCount(double cancelCount) {
		this.cancelCount = cancelCount;
	}
	
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public double getCancelItemPrice() {
		return ShopUtils.statsNegativeNnumber(cancelItemPrice);
	}
	public void setCancelItemPrice(double cancelItemPrice) {	
		this.cancelItemPrice = cancelItemPrice;
	}
	
	public double getItemCouponDiscountAmount() {
		return itemCouponDiscountAmount;
	}
	public void setItemCouponDiscountAmount(double itemCouponDiscountAmount) {
		this.itemCouponDiscountAmount = itemCouponDiscountAmount;
	}
	public double getCancelItemCouponDiscountAmount() {
		return cancelItemCouponDiscountAmount;
	}
	public void setCancelItemCouponDiscountAmount(
			double cancelItemCouponDiscountAmount) {
		this.cancelItemCouponDiscountAmount = cancelItemCouponDiscountAmount;
	}
	
	public double getPayTotal() {
		return itemPrice - ShopUtils.statsNegativeNnumber(this.getTotalDiscountAmount());
	}
	
	public double getCancelTotal() {
		return ShopUtils.statsNegativeNnumber(cancelItemPrice - this.getCancelTotalDiscountAmount());
	}
	
	public double getSumTotalAmount() {
		return this.getPayTotal() - ShopUtils.statsNegativeNnumber(this.getCancelTotal());
	}

	public double getUserLevelDiscountPrice() {
		return userLevelDiscountPrice;
	}

	public void setUserLevelDiscountPrice(double userLevelDiscountPrice) {
		this.userLevelDiscountPrice = userLevelDiscountPrice;
	}

	public double getCancelUserLevelDiscountPrice() {
		return cancelUserLevelDiscountPrice;
	}

	public void setCancelUserLevelDiscountPrice(double cancelUserLevelDiscountPrice) {
		this.cancelUserLevelDiscountPrice = cancelUserLevelDiscountPrice;
	}
}
