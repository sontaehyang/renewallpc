package saleson.shop.order.domain;

import java.math.BigDecimal;
import java.util.List;

import saleson.common.utils.ShopUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.point.domain.PointPolicy;
import saleson.shop.userlevel.domain.UserLevel;

import com.onlinepowers.framework.util.ValidationUtils;

public class OrderPrice {
	private int totalItemPrice; 	// 상품 판매가 * 수량의 총합 (할인 전 ITEM 테이블의 SALE_PRICE의 합)
	private int totalItemDiscountAmount; 	// 상품 총 할인 금액 (즉시 + 스팟)
	private int totalUserLevelDiscountAmount; 	// 회원 등급할인 금액 .


	private int totalItemSaleAmount;
	private int totalItemAmountBeforeDiscounts;
	private int totalExcisePrice;
	private int totalItemPayAmount;
	
	private int totalShippingAmount;
	private int totalItemCouponDiscountAmount = 0;
	private int totalCartCouponDiscountAmount = 0;
	private int totalPointDiscountAmount = 0;
	private int totalShippingCouponUseCount = 0;
	private int totalShippingCouponDiscountAmount = 0;
	
	private int orderPayAmount;
	private int taxFreeAmount;
	private int totalDiscountAmount;
	private int totalEarnPoint;
	private int payAmount;
	
	private int orderPayAmountTotal;	// 전체 결제 금액 (포인트 포함)
	
	private String cartCouponUseData;
	
	
	// 사용 쿠폰 POST용
	private List<String> useCouponKeys;
	
	public OrderPrice() {}
	public OrderPrice(List<Receiver> receivers, int bankPayAmount, Config shopConfig) {
		this.setItems(receivers, bankPayAmount, shopConfig);
	}
	
	public void setItems(List<Receiver> receivers, int bankPayAmount, Config shopConfig) {
		if (receivers == null) {
			return;
		}
		
		// 초기화

		this.totalItemPrice = 0;		// 주문의 총 상품 금액 (상품판매가 + 옵션 추가금) * 수량의 합
		this.totalDiscountAmount = 0;	// 주문의 총 할인금액 (즉시 + 스팟 + 쿠폰 + 회원등급할인) * 수량
		this.totalItemDiscountAmount = 0;	// 상품 총 할인 금액 (즉시 + 스팟)
		this.totalUserLevelDiscountAmount = 0; 	// 회원 등급할인 금액 .

		this.totalShippingAmount = 0;
		this.totalItemCouponDiscountAmount = 0;
		this.totalItemAmountBeforeDiscounts = 0;
		this.totalItemSaleAmount = 0;
		this.totalEarnPoint = 0;
		
		for(Receiver receiver : receivers) {
			
			
			for(Shipping shipping : receiver.getItemGroups()) {


				if (shipping.isSingleShipping()) {
					BuyItem buyItem = shipping.getBuyItem();

					setTotalItemAdditionPrice(shopConfig, buyItem);

					// 총 배송비 - 추가구성상품일 경우 배송비 추가 안 함 (본품 배송비만 적용)
					if ("N".equals(buyItem.getAdditionItemFlag())) {
						this.totalShippingAmount += shipping.getPayShipping();
					}
				} else {

					int count = 0;
					for(BuyItem buyItem : shipping.getBuyItems()) {
						setTotalItemAdditionPrice(shopConfig, buyItem);

						// 총 배송비 - 추가구성상품일 경우 배송비 추가 안 함 (본품 배송비만 적용)
						if ("N".equals(buyItem.getAdditionItemFlag()) && count == 0) {
							this.totalShippingAmount += shipping.getPayShipping();
						}

						count++;
					}

				}
			}
		}
		
		this.totalItemPayAmount = this.totalItemSaleAmount;
		this.orderPayAmountTotal = this.totalItemSaleAmount + this.totalShippingAmount;
		this.payAmount = this.orderPayAmountTotal - bankPayAmount;
 		this.orderPayAmount = this.orderPayAmountTotal - this.totalPointDiscountAmount;
	}

	/**
	 * CJH 2016.11.13 포인트 계산을 쿠폰 적용후 계산하는 부분때문에 이쪽으로 옮겨왔다..
	 * @param buyItem
	 * @return
	 */
	private int getEarnPoint(BuyItem buyItem, Config shopConfig) {
		
		int point = 0;
		
		ItemPrice itemPrice = buyItem.getItemPrice();
		
		// 포인트 기준금액 - 쿠폰 할인가를 적용?
		int basePointPrice = itemPrice.getSumPrice();
		if (shopConfig.isPointApplyCouponDiscount()) {
			basePointPrice -= itemPrice.getCouponDiscountPrice();
		}
		
		PointPolicy pointPolicy = buyItem.getPointPolicy();
		if (ValidationUtils.isNotNull(pointPolicy)) {
			point = ShopUtils.getEarnPoint(basePointPrice, pointPolicy);
			
			if ("2".equals(pointPolicy.getConfigType())) {
				itemPrice.setSellerPoint((int) point);
			}
		}
		
		UserLevel userLevel = buyItem.getUserLevel();
		if (userLevel != null) {
			if (userLevel.getPointRate() != 0) {
				point += new BigDecimal(basePointPrice)
					.multiply(BigDecimal.valueOf(userLevel.getPointRate()).divide(new BigDecimal("100")))
					.setScale(0, BigDecimal.ROUND_FLOOR).intValue();
			}
		}
		
		itemPrice.setEarnPoint(point);
		
		return point;
	}
	
	public int getTaxFreeAmount() {
		return taxFreeAmount;
	}
	public void setTaxFreeAmount(int taxFreeAmount) {
		this.taxFreeAmount = taxFreeAmount;
	}
	public int getTotalItemAmountBeforeDiscounts() {
		return totalItemAmountBeforeDiscounts;
	}
	public void setTotalItemAmountBeforeDiscounts(int totalItemAmountBeforeDiscounts) {
		this.totalItemAmountBeforeDiscounts = totalItemAmountBeforeDiscounts;
	}
	public List<String> getUseCouponKeys() {
		return useCouponKeys;
	}
	public void setUseCouponKeys(List<String> useCouponKeys) {
		this.useCouponKeys = useCouponKeys;
	}
	public String getCartCouponUseData() {
		return cartCouponUseData;
	}
	public void setCartCouponUseData(String cartCouponUseData) {
		this.cartCouponUseData = cartCouponUseData;
	}
	public int getTotalItemSaleAmount() {
		return totalItemSaleAmount;
	}
	public void setTotalItemSaleAmount(int totalItemSaleAmount) {
		this.totalItemSaleAmount = totalItemSaleAmount;
	}
	public int getTotalExcisePrice() {
		return totalExcisePrice;
	}

	public void setTotalExcisePrice(int totalExcisePrice) {
		this.totalExcisePrice = totalExcisePrice;
	}

	public int getTotalItemPayAmount() {
		return totalItemPayAmount;
	}

	public void setTotalItemPayAmount(int totalItemPayAmount) {
		this.totalItemPayAmount = totalItemPayAmount;
	}

	public int getTotalShippingAmount() {
		return totalShippingAmount;
	}

	public void setTotalShippingAmount(int totalShippingAmount) {
		this.totalShippingAmount = totalShippingAmount;
	}

	public int getTotalItemCouponDiscountAmount() {
		return totalItemCouponDiscountAmount;
	}

	public void setTotalItemCouponDiscountAmount(int totalItemCouponDiscountAmount) {
		this.totalItemCouponDiscountAmount = totalItemCouponDiscountAmount;
	}

	public int getTotalCartCouponDiscountAmount() {
		return totalCartCouponDiscountAmount;
	}

	public void setTotalCartCouponDiscountAmount(int totalCartCouponDiscountAmount) {
		this.totalCartCouponDiscountAmount = totalCartCouponDiscountAmount;
	}

	public int getTotalPointDiscountAmount() {
		return totalPointDiscountAmount;
	}

	public void setTotalPointDiscountAmount(int totalPointDiscountAmount) {
		this.totalPointDiscountAmount = totalPointDiscountAmount;
	}

	public int getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(int orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}

	public int getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(int totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public int getTotalEarnPoint() {
		return totalEarnPoint;
	}

	public void setTotalEarnPoint(int totalEarnPoint) {
		this.totalEarnPoint = totalEarnPoint;
	}

	public int getTotalCouponDiscountAmount() {
		return this.totalItemCouponDiscountAmount + this.totalCartCouponDiscountAmount;
	}
	public int getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}
	public int getOrderPayAmountTotal() {
		return orderPayAmountTotal;
	}
	public void setOrderPayAmountTotal(int orderPayAmountTotal) {
		this.orderPayAmountTotal = orderPayAmountTotal;
	}
	public int getTotalShippingCouponUseCount() {
		return totalShippingCouponUseCount;
	}
	public void setTotalShippingCouponUseCount(int totalShippingCouponUseCount) {
		this.totalShippingCouponUseCount = totalShippingCouponUseCount;
	}
	
	public int getTotalShippingCouponDiscountAmount() {
		return totalShippingCouponDiscountAmount;
	}
	public void setTotalShippingCouponDiscountAmount(
			int totalShippingCouponDiscountAmount) {
		this.totalShippingCouponDiscountAmount = totalShippingCouponDiscountAmount;
	}

	/**
	 * 주문 금액 (상품 판매가 * 수량)
	 * [SKC] 할인 전 금액으로 상품테이블의 판매가(OP_ITEM > SALE_PRICE) * 수량 금액.
	 * @return
	 */
	public int getTotalItemPrice() {
		return totalItemPrice;
	}

	public int getTotalItemDiscountAmount() {
		return totalItemDiscountAmount;
	}

	public int getTotalUserLevelDiscountAmount() {
		return totalUserLevelDiscountAmount;
	}


	public void setTotalItemPrice(int totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}

	public void setTotalItemDiscountAmount(int totalItemDiscountAmount) {
		this.totalItemDiscountAmount = totalItemDiscountAmount;
	}

	public void setTotalUserLevelDiscountAmount(int totalUserLevelDiscountAmount) {
		this.totalUserLevelDiscountAmount = totalUserLevelDiscountAmount;
	}

	private void setTotalItemAdditionPrice(Config shopConfig, BuyItem buyItem) {

		ItemPrice itemPrice = buyItem.getItemPrice();
		if (ValidationUtils.isNull(itemPrice) == false) {

			int point = getEarnPoint(buyItem, shopConfig);
			this.totalEarnPoint += (point * itemPrice.getQuantity());

			this.setTotalOrderPrice(itemPrice);

			// 추가구성상품
			if (buyItem.getAdditionItemList() != null) {
				for (BuyItem addition : buyItem.getAdditionItemList()) {
					ItemPrice additionItemPrice = addition.getItemPrice();

					int additionPoint = getEarnPoint(addition, shopConfig);
					this.totalEarnPoint += (additionPoint * additionItemPrice.getQuantity());

					this.setTotalOrderPrice(additionItemPrice);
				}
			}
		}
	}

	/**
	 * 본상품 금액 + 추가구성상품 금액 계산
	 * @return
	 */
	private void setTotalOrderPrice(ItemPrice itemPrice) {
		this.totalItemPrice += itemPrice.getItemSaleAmount();
		this.totalDiscountAmount += itemPrice.getDiscountAmount();
		this.totalItemDiscountAmount += itemPrice.getItemDiscountAmount();
		this.totalUserLevelDiscountAmount += itemPrice.getUserLevelDiscountAmount();

		this.totalItemCouponDiscountAmount += itemPrice.getCouponDiscountAmount();
		this.totalItemAmountBeforeDiscounts += itemPrice.getBeforeDiscountAmount();
		this.totalItemSaleAmount += itemPrice.getSaleAmount();

		// 부과세가 면세인가?
		if ("2".equals(itemPrice.getTaxType())) {
			this.taxFreeAmount += itemPrice.getSaleAmount();
		}
	}
}
