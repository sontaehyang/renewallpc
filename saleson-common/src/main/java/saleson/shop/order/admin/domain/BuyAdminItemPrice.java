package saleson.shop.order.admin.domain;

import java.math.BigDecimal;

import saleson.common.utils.ShopUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.point.domain.PointPolicy;
import saleson.shop.userlevel.domain.UserLevel;

import com.onlinepowers.framework.util.ValidationUtils;

public class BuyAdminItemPrice {

	private int purchasePrice;		// 매입가격
	private int costPrice;			// 원가
	private int supplyPrice;		// 공급
	private int price;				// 단가 (개당 가격)
	private int optionPrice;		// 옵션가 (개당 가격)
	private int quantity;
	
	private String taxType;
	
	private String spotSaleFlag;

	// 상품 쿠폰 적용 방식 (1 : 상품 구매 수량만큼 중복 적용)
	private String couponConcurrently = "";
	private Config shopConfig;
	
	private int couponDiscountPrice;
	private int couponDiscountAmount;
	
	private int sumPrice; 	// 옵션가 + 단가 
	
	// 적립 포인트
	private double earnPoint;
	private String earnPointFlag = "N";
	
	private int commissionBasePrice;
	private int commissionPrice;
	private int sellerDiscountPrice;
	private int sellerDiscountAmount;
	private String sellerDiscountDetail;
	
	private int adminDiscountPrice;
	private int adminDiscountAmount;
	private String adminDiscountDetail;
	
	private int sellerPoint;
	
	public BuyAdminItemPrice() {}
	public BuyAdminItemPrice(BuyAdminItem buyItem) {
		
		Item item = buyItem.getItem();
		this.price = buyItem.getSalePrice();
		this.purchasePrice = item.getSalePrice();
		this.spotSaleFlag = item.isSpotItem() ? "Y" : "N";
		this.quantity = buyItem.getQuantity();
		
		if (buyItem.getOptionList() != null) {
			for(ItemOption itemOption : buyItem.getOptionList()) {
				this.optionPrice += itemOption.getExtraPrice();
			}
		}
		
		// 부과세 면세여부 (1:과세, 2:면세)
		this.taxType = item.getTaxType();
		
		this.sumPrice = this.price + this.optionPrice;
		
		// 상품 판매가(옵션금액 포함) - 수수료 - 판매자 할인
		// 수수료 = 상품판매가(옵션금액 포함) * 수수료율
		

		// 수수료 기준 금액 (회원 상품 판매가 + 옵션)
		this.commissionBasePrice = (item.getSalePrice() + this.optionPrice);
		
		// 수수료 - 수수료는 반올림 처리
		if (item.getCommissionRate() > 0) {
			this.commissionPrice = ((int) Math.round((double) this.commissionBasePrice * ((double) item.getCommissionRate() / 100)));
		}
		
		this.costPrice = item.getCostPrice();
		
		PointPolicy pointPolicy = buyItem.getPointPolicy();
		if (ValidationUtils.isNotNull(pointPolicy)) {
			this.earnPoint = ShopUtils.getEarnPoint(this.sumPrice, pointPolicy);
			
			if ("2".equals(pointPolicy.getConfigType())) {
				this.sellerPoint = (int) this.earnPoint;
			}
		}
		
		UserLevel userLevel = buyItem.getUserLevel();
		if (userLevel != null) {
			if (userLevel.getPointRate() != 0) {
				this.earnPoint += new BigDecimal(this.sumPrice)
					.multiply(BigDecimal.valueOf(userLevel.getPointRate()).divide(new BigDecimal("100")))
					.setScale(0, BigDecimal.ROUND_FLOOR).intValue();
			}
		}
		
		// CJH 2016.10.24 공급가 - 할인전 판매가 (상품[회원 판매가] + 옵션) - 수수료 (할인전 판매가 기준 수수료 책정) 
		this.supplyPrice = this.commissionBasePrice - this.commissionPrice;
	}
	
	public int getCouponDiscountPrice() {
		return couponDiscountPrice;
	}
	public void setCouponDiscountPrice(int couponDiscountPrice) {
		this.couponDiscountPrice = couponDiscountPrice;
	}
	public int getSellerDiscountPrice() {
		return sellerDiscountPrice;
	}
	public void setSellerDiscountPrice(int sellerDiscountPrice) {
		this.sellerDiscountPrice = sellerDiscountPrice;
	}
	public int getAdminDiscountPrice() {
		return adminDiscountPrice;
	}
	public void setAdminDiscountPrice(int adminDiscountPrice) {
		this.adminDiscountPrice = adminDiscountPrice;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	
	/**
	 * 상품 결제 금액
	 * @return
	 */
	public int getSaleAmount() {
		return this.getBeforeDiscountAmount() - this.getCouponDiscountAmount();
	}
	
	public int getSalePrice() {
		return this.sumPrice - this.getCouponDiscountPrice();
	}
	
	/**
	 * 상품 할인전 판매가
	 * @return
	 */
	public int getBeforeDiscountAmount() {
		return (this.sumPrice * this.quantity);
	}

	public int getAdminDiscountAmount() {
		return adminDiscountAmount;
	}
	public void setAdminDiscountAmount(int adminDiscountAmount) {
		this.adminDiscountAmount = adminDiscountAmount;
	}
	public String getAdminDiscountDetail() {
		return adminDiscountDetail;
	}
	public void setAdminDiscountDetail(String adminDiscountDetail) {
		this.adminDiscountDetail = adminDiscountDetail;
	}
	public int getCommissionPrice() {
		return commissionPrice;
	}
	public void setCommissionPrice(int commissionPrice) {
		this.commissionPrice = commissionPrice;
	}
	public int getSellerDiscountAmount() {
		return sellerDiscountAmount;
	}
	public void setSellerDiscountAmount(int sellerDiscountAmount) {
		this.sellerDiscountAmount = sellerDiscountAmount;
	}
	public String getSellerDiscountDetail() {
		return sellerDiscountDetail;
	}
	public void setSellerDiscountDetail(String sellerDiscountDetail) {
		this.sellerDiscountDetail = sellerDiscountDetail;
	}
	public int getCommissionBasePrice() {
		return commissionBasePrice;
	}
	public void setCommissionBasePrice(int commissionBasePrice) {
		this.commissionBasePrice = commissionBasePrice;
	}
	public int getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(int supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	public int getCouponDiscountAmount() {
		return couponDiscountAmount;
	}
	public void setCouponDiscountAmount(int couponDiscountAmount) {
		this.couponDiscountAmount = couponDiscountAmount;
	}
	public double getEarnPoint() {
		return earnPoint;
	}
	public void setEarnPoint(int earnPoint) {
		this.earnPoint = earnPoint;
	}
	public String getEarnPointFlag() {
		return earnPointFlag;
	}
	public void setEarnPointFlag(String earnPointFlag) {
		this.earnPointFlag = earnPointFlag;
	}
	public int getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}
	public int getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public int getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(int costPrice) {
		this.costPrice = costPrice;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getOptionPrice() {
		return optionPrice;
	}
	public void setOptionPrice(int optionPrice) {
		this.optionPrice = optionPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSpotSaleFlag() {
		return spotSaleFlag;
	}
	public void setSpotSaleFlag(String spotSaleFlag) {
		this.spotSaleFlag = spotSaleFlag;
	}
	public String getCouponConcurrently() {
		return couponConcurrently;
	}
	public void setCouponConcurrently(String couponConcurrently) {
		this.couponConcurrently = couponConcurrently;
	}
	public Config getShopConfig() {
		return shopConfig;
	}
	public void setShopConfig(Config shopConfig) {
		this.shopConfig = shopConfig;
	}
	public int getSellerPoint() {
		return sellerPoint;
	}
	public void setSellerPoint(int sellerPoint) {
		this.sellerPoint = sellerPoint;
	}
	
}
