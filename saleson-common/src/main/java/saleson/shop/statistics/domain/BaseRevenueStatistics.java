package saleson.shop.statistics.domain;

import saleson.common.utils.ShopUtils;

public class BaseRevenueStatistics {
	
	private String osType;
	private double payCount;
	private double cancelCount;

	private double costPrice;
	private double cancelCostPrice;

	private double discountAmount;
	private double cancelDiscountAmount;

	private double supplyAmount;
	private double cancelSupplyAmount;
	
	
	// 2017-04-11 기존 컬럼 그대로 사용
	private double itemPrice;					// 상품 판매가 : 상품 판매가 + 옵션추가금 (정산 대상 금액과 동일)
	private double cancelItemPrice;
	
	
	// 2017-04-11 신규 컬럼 추가
	private double couponDiscount;				// 쿠폰 할인
	private double cancelCouponDiscount;

	private double spotDiscount;				// 스팟 할인 
	private double cancelSpotDiscount;
	
	private double sellerDiscount;				// 즉시 할인
	private double cancelSellerDiscount;

	private double userLevelDiscount;			// 회원 할인
	private double cancelUserLevelDiscount;

	private double itemAmount;					// 판매가 : 상품 판매가 - 할인 = 실제 상품 구매가  
	private double cancelItemAmount;
	
	private double shipping;					// 배송비 (해당 주문의 총 배송비) 
	private double cancelShipping;
	
	
	// 결제 소계
	public double getPayTotal() {
		return itemAmount + shipping;
	}

	// 취소 소계
	public double getCancelTotal() {
		return ShopUtils.statsNegativeNnumber(cancelItemAmount + cancelShipping);
	}
	
	// 판매가 합계
	public double getItemAmountTotal() {
		return itemAmount - cancelItemAmount;
	}
	
	// 배송비 합계
	public double getShippingTotal() {
		return shipping - cancelShipping;
	}
	
	// 합계 
	public double getGrandTotal() {
		return getPayTotal() - ShopUtils.statsNegativeNnumber(getCancelTotal());
	}
	
	// 결제 할인 총액 
	public double getPayDiscountTotal() {
		return ShopUtils.statsNegativeNnumber(couponDiscount + spotDiscount + sellerDiscount + userLevelDiscount);
	}
	
	// 취소 할인 총액 
	public double getCancelDiscountTotal() {
		return cancelCouponDiscount + cancelSpotDiscount + cancelSellerDiscount + cancelUserLevelDiscount;
	}
	
	
	// 주문수 
	public double getSumCount() {
		return payCount - cancelCount;
	}

	// 상품 원가 금액
	public double getSumCostPrice() {
		return costPrice - cancelCostPrice;
	}
	
	// 상품 금액
	public double getSumItemPrice() {
		return itemPrice - cancelItemPrice;
	}
	
	// 할인금액
	public double getSumDiscountAmount() {
		return discountAmount - cancelDiscountAmount;
	}
	
	// 공급액
	public double getSumSupplyAmount() {
		return supplyAmount - cancelSupplyAmount;
	}
	
	// 주문합계 (할인가 적용) 
	public double getSumTotalAmount() {
		return this.getPayTotal() - this.getCancelTotal();
	}
	
	// 수수료액 (주문합계 - 공급합계)
	public double getSumFeesAmount() {
		return getSumTotalAmount() - getSumSupplyAmount();
	}

	// 순이익액 (공급합계 - 원가합계)
	public double getSumNetProfitAmount() {
		return getSumSupplyAmount() - getSumCostPrice();
	}
	
	// 총이익액 (주문합계 - 원가합계)
	public double getSumProfitAmount() {
		return getSumTotalAmount() - getSumCostPrice();
	}
	
	// 수수료율 (수수료액 / 주문합계 * 100)
	public int getSumFeesPercent() {
		return (int) ((getSumFeesAmount() / getSumTotalAmount()) * 100);
	}
	
	// 총이익율 (총이익액 / 주문합계 X 100)
	public int getSumProfitPercent() {
		return (int) ((getSumProfitAmount() / getSumTotalAmount()) * 100);
	}
	
	// 순이익율 (순이익액 / 주문합계 X 100)
	public int getSumNetProfitPercent() {
		return (int) ((getSumNetProfitAmount() / getSumTotalAmount()) * 100);
	}
	

	
	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public double getPayCount() {
		return payCount;
	}

	public void setPayCount(double payCount) {
		this.payCount = payCount;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public double getCancelDiscountAmount() {
		return cancelDiscountAmount;
	}

	public void setCancelDiscountAmount(double cancelDiscountAmount) {
		this.cancelDiscountAmount = cancelDiscountAmount;
	}

	public double getCancelCount() {
		return ShopUtils.statsNegativeNnumber(cancelCount);
	}

	public double getCancelItemPrice() {
		return ShopUtils.statsNegativeNnumber(cancelItemPrice);
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setCancelCount(double cancelCount) {
		this.cancelCount = cancelCount;
	}

	public double getCostPrice() {
		return this.costPrice;
	}
	
	public double getCancelCostPrice() {
		return this.cancelCostPrice;
	}
	
	public double getSupplyAmount() {
		return this.supplyAmount;
	}
	
	public double getCancelSupplyAmount() {
		return this.cancelSupplyAmount;
	}
	
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}
	
	public void setCancelCostPrice(double cancelCostPrice) {
		this.cancelCostPrice = cancelCostPrice;
	}

	public void setSupplyAmount(double supplyAmount) {
		this.supplyAmount = supplyAmount;
	}
	
	public void setCancelSupplyAmount(double cancelSupplyAmount) {
		this.cancelSupplyAmount = cancelSupplyAmount;
	}
	
	public void setCancelItemPrice(double cancelItemPrice) {
		this.cancelItemPrice = cancelItemPrice;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(double couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public double getCancelCouponDiscount() {
		return cancelCouponDiscount;
	}

	public void setCancelCouponDiscount(double cancelCouponDiscount) {
		this.cancelCouponDiscount = cancelCouponDiscount;
	}

	public double getSpotDiscount() {
		return spotDiscount;
	}

	public void setSpotDiscount(double spotDiscount) {
		this.spotDiscount = spotDiscount;
	}

	public double getCancelSpotDiscount() {
		return cancelSpotDiscount;
	}

	public void setCancelSpotDiscount(double cancelSpotDiscount) {
		this.cancelSpotDiscount = cancelSpotDiscount;
	}

	public double getSellerDiscount() {
		return sellerDiscount;
	}

	public void setSellerDiscount(double sellerDiscount) {
		this.sellerDiscount = sellerDiscount;
	}

	public double getCancelSellerDiscount() {
		return cancelSellerDiscount;
	}

	public void setCancelSellerDiscount(double cancelSellerDiscount) {
		this.cancelSellerDiscount = cancelSellerDiscount;
	}

	public double getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(double itemAmount) {
		this.itemAmount = itemAmount;
	}

	public double getCancelItemAmount() {
		return ShopUtils.statsNegativeNnumber(cancelItemAmount);
	}

	public void setCancelItemAmount(double cancelItemAmount) {
		this.cancelItemAmount = cancelItemAmount;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public double getCancelShipping() {
		return ShopUtils.statsNegativeNnumber(cancelShipping);
	}

	public void setCancelShipping(double cancelShipping) {
		this.cancelShipping = cancelShipping;
	}

	public double getUserLevelDiscount() {
		return userLevelDiscount;
	}

	public void setUserLevelDiscount(double userLevelDiscount) {
		this.userLevelDiscount = userLevelDiscount;
	}

	public double getCancelUserLevelDiscount() {
		return cancelUserLevelDiscount;
	}

	public void setCancelUserLevelDiscount(double cancelUserLevelDiscount) {
		this.cancelUserLevelDiscount = cancelUserLevelDiscount;
	}
}