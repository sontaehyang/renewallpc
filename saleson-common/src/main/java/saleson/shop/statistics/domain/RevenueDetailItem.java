package saleson.shop.statistics.domain;

import java.util.List;

import saleson.common.utils.ShopUtils;
import saleson.shop.item.domain.ItemOption;

public class RevenueDetailItem {
	private int itemId;
	private int itemSequence;
	private int orderItemId;
	private String itemName;
	private String sellerName;
	private String itemUserCode;
	private String requiredOptions;
	private int quantity;
	private String orderType;
	private int itemPrice;
	private int totalItemPrice;
	private int itemCouponDiscountAmount;
	private String openMarektOption;
	private List<ItemOption> requiredOptionsList;
	private int vendorAddDiscountAmount;

	private int subTotal;

	// 주문 배송비
	private int orderShipping;		// 해당 주문의 배송비 (상품별 배송비는 아님.. 주문 상세에서 표시용)

	public List<ItemOption> getRequiredOptionsList() {
		return requiredOptionsList;
	}

	public void setRequiredOptionsList(List<ItemOption> requiredOptionsList) {
		this.requiredOptionsList = requiredOptionsList;
	}

	public String getOpenMarektOption() {
		return openMarektOption;
	}

	public void setOpenMarektOption(String openMarektOption) {
		this.openMarektOption = openMarektOption;
	}

	public int getItemPrice() {
		if ("CANCEL".equals(this.orderType)) {
			return (int) ShopUtils.statsNegativeNnumber(itemPrice);
		}
		return itemPrice;
	}
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getItemPay() {
		return this.itemPrice + this.itemCouponDiscountAmount;
	}
	public int getQuantity() {
		if ("CANCEL".equals(this.orderType)) {
			return (int) ShopUtils.statsNegativeNnumber(quantity);
		}
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getRequiredOptions() {
		return requiredOptions;
	}
	public void setRequiredOptions(String requiredOptions) {
		this.requiredOptions = requiredOptions;
	}
	public int getTotalItemPrice() {
		if ("CANCEL".equals(this.orderType)) {
			return (int) ShopUtils.statsNegativeNnumber(totalItemPrice);
		}
		return totalItemPrice;
	}
	public void setTotalItemPrice(int totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}
	public int getItemCouponDiscountAmount() {
		if ("PAY".equals(this.orderType)) {
			return (int) ShopUtils.statsNegativeNnumber(itemCouponDiscountAmount);
		}
		return itemCouponDiscountAmount;
	}
	public void setItemCouponDiscountAmount(int itemCouponDiscountAmount) {

		/*if (itemCouponDiscountAmount > 0 && "PAY".equals(this.orderType)) {
			itemCouponDiscountAmount = -(itemCouponDiscountAmount);
		}*/

		this.itemCouponDiscountAmount = itemCouponDiscountAmount;
	}

	public int getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}

	public int getOrderShipping() {
		if ("CANCEL".equals(this.orderType)) {
			return (int) ShopUtils.statsNegativeNnumber(orderShipping);
		}
		return orderShipping;
	}

	public void setOrderShipping(int orderShipping) {
		this.orderShipping = orderShipping;
	}

	public int getVendorAddDiscountAmount() {

		if ("PAY".equals(this.orderType)) {
			return (int) ShopUtils.statsNegativeNnumber(vendorAddDiscountAmount);
		}

		return vendorAddDiscountAmount;
	}

	public void setVendorAddDiscountAmount(int vendorAddDiscountAmount) {
		this.vendorAddDiscountAmount = vendorAddDiscountAmount;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public int getSubTotal() {
		if ("CANCEL".equals(this.orderType)) {
			return (int) ShopUtils.statsNegativeNnumber(subTotal);
		}

		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	/*public int getSubTotal() {
		if ("CANCEL".equals(this.orderType)) {
			return (int) ShopUtils.statsNegativeNnumber(totalItemPrice + orderShipping);
		}
		return totalItemPrice + orderShipping;
	}*/
}
