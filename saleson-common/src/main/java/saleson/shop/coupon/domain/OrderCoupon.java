package saleson.shop.coupon.domain;

import java.util.List;

public class OrderCoupon extends CouponUser {
	
	private int discountPrice;
	private int discountAmount;
	
	private List<CouponItem> couponItems;

	public List<CouponItem> getCouponItems() {
		return couponItems;
	}

	public void setCouponItems(List<CouponItem> couponItems) {
		this.couponItems = couponItems;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}

}
