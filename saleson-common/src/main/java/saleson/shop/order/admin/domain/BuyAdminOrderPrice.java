package saleson.shop.order.admin.domain;

import java.util.List;

import com.onlinepowers.framework.util.ValidationUtils;

public class BuyAdminOrderPrice {
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
	
	public BuyAdminOrderPrice() {}
	public BuyAdminOrderPrice(List<BuyAdminReceiver> receivers) {
		this.setItems(receivers);
	}
	
	public void setItems(List<BuyAdminReceiver> receivers) {
		if (receivers == null) {
			return;
		}
		
		// 초기화
		this.totalShippingAmount = 0;
		this.totalItemCouponDiscountAmount = 0;
		this.totalItemAmountBeforeDiscounts = 0;
		this.totalItemSaleAmount = 0;
		this.totalEarnPoint = 0;
		
		for(BuyAdminReceiver receiver : receivers) {
			for(BuyAdminShipping shipping : receiver.getItemGroups()) {
				
				// 총 배송비
				this.totalShippingAmount += shipping.getPayShipping();
				if (shipping.isSingleShipping()) {
					BuyAdminItem buyItem = shipping.getBuyItem();
					BuyAdminItemPrice itemPrice = buyItem.getItemPrice();
					
					if (ValidationUtils.isNull(itemPrice) == false) {
						
						
						this.totalItemCouponDiscountAmount += itemPrice.getCouponDiscountAmount();
						this.totalItemAmountBeforeDiscounts += itemPrice.getBeforeDiscountAmount();
						this.totalItemSaleAmount += itemPrice.getSaleAmount();
						this.totalEarnPoint += (itemPrice.getEarnPoint() * itemPrice.getQuantity());
						
						// 부과세가 면세인가?
						if ("2".equals(itemPrice.getTaxType())) {
							this.taxFreeAmount += itemPrice.getSaleAmount();
						}
					}
					
				} else {
				
					for(BuyAdminItem buyItem : shipping.getBuyItems()) {
						BuyAdminItemPrice itemPrice = buyItem.getItemPrice();
						
						if (ValidationUtils.isNull(itemPrice) == false) {
							this.totalItemCouponDiscountAmount += itemPrice.getCouponDiscountAmount();
							this.totalItemAmountBeforeDiscounts += itemPrice.getBeforeDiscountAmount();
							this.totalItemSaleAmount += itemPrice.getSaleAmount();
							this.totalEarnPoint += (itemPrice.getEarnPoint() * itemPrice.getQuantity());
							
							// 부과세가 면세인가?
							if ("2".equals(itemPrice.getTaxType())) {
								this.taxFreeAmount += itemPrice.getSaleAmount();
							}
						}
						
					}
					
				}
			}
		}
		
		this.totalItemPayAmount = this.totalItemSaleAmount;
		this.orderPayAmountTotal = this.totalItemSaleAmount + this.totalShippingAmount;
		this.payAmount = this.orderPayAmountTotal;
		this.orderPayAmount = this.orderPayAmountTotal - this.totalPointDiscountAmount;
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
}
