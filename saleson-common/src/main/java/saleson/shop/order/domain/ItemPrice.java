package saleson.shop.order.domain;


import com.onlinepowers.framework.util.NumberUtils;
import com.onlinepowers.framework.util.StringUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;

public class ItemPrice implements Cloneable{
	
	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
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
	private String commissionType;
	
	private int sumPrice; 	// 옵션가 + 단가
	
	// 적립 포인트
	private double earnPoint;
	private String earnPointFlag = "N";
	
	private int commissionBasePrice;
	private int commissionPrice;
	private int sellerDiscountPrice;			// 즉시할인
	private int sellerDiscountAmount;			// 즉시할인 + 판매자 SPOT
	private String sellerDiscountDetail;

	private int adminDiscountPrice;				// 운영사 SPOT
	private int adminDiscountAmount;
	private String adminDiscountDetail;

	private int discountAmount; 			// 할인 총액 (운영자할인 + 판매자할인 + 쿠폰할인 + 등급할인)

	private int sellerPoint;

	private int spotDiscountAmount;
	private int itemSalePrice;

	// 회원 등급 할인 금액
	private int userLevelDiscountAmount;

	// 상품 할인 - (즉시할인 + 스팟할인)
	private int itemDiscountAmount;


	public ItemPrice() {}
	public ItemPrice(BuyItem buyItem) {
		
		ItemPrice itemPrice = buyItem.getItemPrice();
		Item item = buyItem.getItem();
		this.itemSalePrice = item.getSalePrice();
		this.price = item.getPresentPrice();
		this.purchasePrice = item.getSalePrice();
		this.spotSaleFlag = item.isSpotItem() ? "Y" : "N";
		
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
		
		// 수수료는 반올림 처리
		if (item.getCommissionRate() > 0) {
			this.commissionPrice = ((int) Math.round((double) this.commissionBasePrice * ((double) item.getCommissionRate() / 100)));
		}
		
		// 공급자 할인 총액
		int totalAdminDiscount = 0;
		
		
		// 판매자 할인 총액
		this.sellerDiscountPrice = item.getSellerDiscountPrice();
		int totalSellerDiscount = item.getSellerDiscountPrice();
		
		// 스팟 할인 총액
		int totalSellerSpotDiscount = 0;
		
		StringBuffer sbSeller = new StringBuffer();
		StringBuffer sbAdmin = new StringBuffer();
		if (totalSellerDiscount > 0) {
			sbSeller.append("판매자 할인 : " + NumberUtils.formatNumber(totalSellerDiscount, "#,###") + "원");
		}
		
		// 판매자 부담 스팟인경우
		if ("Y".equals(this.spotSaleFlag)) {
			if ("2".equals(item.getSpotType())) {
				totalSellerSpotDiscount = (item.getSpotDiscountAmount());
				
				if (totalSellerSpotDiscount > 0) {
					if (StringUtils.isNotEmpty(sbSeller.toString())) {
						sbSeller.append(" / ");
					}
					
					sbSeller.append("판매자 스팟 할인 : "+NumberUtils.formatNumber(totalSellerSpotDiscount, "#,###") + "원");
				}
				
			} else {
				this.adminDiscountPrice = item.getSpotDiscountAmount();
				totalAdminDiscount = (item.getSpotDiscountAmount());
				sbAdmin.append("운영사 스팟 할인 : "+NumberUtils.formatNumber(item.getSpotDiscountAmount(), "#,###") + "원");
			}

			this.spotDiscountAmount = item.getSpotDiscountAmount();

		}
		
		//if (item.getUserLevelDiscountPrice() > 0) {
		//	totalAdminDiscount += item.getUserLevelDiscountPrice();
		//	sbAdmin.append("운영사 회원 등급 할인 : "+NumberUtils.formatNumber(item.getUserLevelDiscountPrice(), "#,###") + "원");
		//}
		
		// 판매자 할인 총액
		this.sellerDiscountAmount = totalSellerDiscount + totalSellerSpotDiscount;
		this.sellerDiscountDetail = sbSeller.toString();
		
		// 운영사 할인 총액
		this.adminDiscountAmount = totalAdminDiscount;
		this.adminDiscountDetail = sbAdmin.toString();

		// 회원 등급할인 금액
		this.userLevelDiscountAmount = item.getUserLevelDiscountAmount() * itemPrice.getQuantity();

		// 상품할인 (즉시할인 + 스팟할인)
		this.itemDiscountAmount = (sellerDiscountAmount + adminDiscountAmount) * itemPrice.getQuantity();

		// 할인 총액
		this.discountAmount = ((sellerDiscountAmount + adminDiscountAmount) * itemPrice.getQuantity()) + this.userLevelDiscountAmount + getCouponDiscountAmount();


		this.costPrice = itemPrice.getCostPrice();
		this.quantity = itemPrice.getQuantity();

		// CJH 2016.10.24 공급가 - 할인전 판매가 (상품[회원 판매가] + 옵션) - 수수료 (할인전 판매가 기준 수수료 책정) 
		this.supplyPrice = this.commissionBasePrice - this.commissionPrice;

		// 2019.01.07 손준의 commissionType에 따라 공급가 설정
		this.commissionType = buyItem.getItem().getCommissionType();

 		if ("3".equals(commissionType)) {
			this.supplyPrice = buyItem.getItem().getSupplyPrice();
		}

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

	public int getBaseAmountForShipping() {

		if (1 == 2) {
			return getSaleAmount();			// 상품 결제 금액 기준
		} else {
			return getItemSaleAmount();		// 상품 판매가 기준 (상품판매가(할인전) + 옵션추가금) * 수량
		}
	}


	/**
	 * 상품 결제 금액 (할인 적용금액)
	 * @return
	 */
	public int getSaleAmount() {
		return this.getBeforeDiscountAmount() - this.getCouponDiscountAmount();
	}

	/**
	 * 상품금액 (상품판매가(할인전) + 옵션추가금)
	 * @return
	 */
	public int getItemSaleAmount() {
		return (getItemSalePrice() + getOptionPrice()) * getQuantity();		// 상품 판매가 기준 (상품판매가(할인전) + 옵션추가금)
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

	public int getSalePrice() {
		return this.sumPrice - this.getCouponDiscountPrice();
	}

	public int getItemSalePrice() {
		return this.itemSalePrice;
	}

	public int getSpotDiscountAmount() {
		return spotDiscountAmount;
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

	// 회원등급 할인 금액
	public int getUserLevelDiscountAmount() {
		return userLevelDiscountAmount;
	}

	public void setUserLevelDiscountAmount(int userLevelDiscountAmount) {
		this.userLevelDiscountAmount = userLevelDiscountAmount;
	}


	// 상품할인
	public int getItemDiscountAmount() {
		return itemDiscountAmount;
	}
}
