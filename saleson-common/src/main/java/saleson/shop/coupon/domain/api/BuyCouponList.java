package saleson.shop.coupon.domain.api;

import saleson.shop.coupon.domain.OrderCoupon;

import java.util.List;

public class BuyCouponList {

    private Integer itemId; // 상품 ID

    private int itemOptionId;   // 상품 옵션 ID

    private List<OrderCoupon> couponList;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public List<OrderCoupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<OrderCoupon> couponList) {
        this.couponList = couponList;
    }

    public int getItemOptionId() {
        return itemOptionId;
    }

    public void setItemOptionId(int itemOptionId) {
        this.itemOptionId = itemOptionId;
    }
}
