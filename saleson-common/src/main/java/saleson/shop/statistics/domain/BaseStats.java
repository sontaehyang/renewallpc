package saleson.shop.statistics.domain;

public class BaseStats {
    private String deviceType;

    private long couponDiscountPrice;
    private long sellerDiscountPrice;
    private long spotDiscountPrice;
    private long userLevelDiscountPrice;
    private long cancelCouponDiscountPrice;
    private long cancelSellerDiscountPrice;
    private long cancelSpotDiscountPrice;
    private long cancelUserLevelDiscountPrice;
    private long itemPrice;
    private long cancelItemPrice;
    private long saleCount;
    private long saleAmount;
    private long cancelCount;
    private long cancelAmount;

    private long shipping;
    private long cancelShipping;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public long getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(long saleCount) {
        this.saleCount = saleCount;
    }

    public long getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(long saleAmount) {
        this.saleAmount = saleAmount;
    }

    public long getCancelCount() {
        return cancelCount;
    }

    public void setCancelCount(long cancelCount) {
        this.cancelCount = cancelCount;
    }

    public long getCancelAmount() {
        return cancelAmount;
    }

    public void setCancelAmount(long cancelAmount) {
        this.cancelAmount = cancelAmount;
    }

    public long getCouponDiscountPrice() {
        return couponDiscountPrice;
    }

    public void setCouponDiscountPrice(long couponDiscountPrice) {
        this.couponDiscountPrice = couponDiscountPrice;
    }

    public long getSellerDiscountPrice() {
        return sellerDiscountPrice;
    }

    public void setSellerDiscountPrice(long sellerDiscountPrice) {
        this.sellerDiscountPrice = sellerDiscountPrice;
    }

    public long getSpotDiscountPrice() {
        return spotDiscountPrice;
    }

    public void setSpotDiscountPrice(long spotDiscountPrice) {
        this.spotDiscountPrice = spotDiscountPrice;
    }

    public long getUserLevelDiscountPrice() {
        return userLevelDiscountPrice;
    }

    public void setUserLevelDiscountPrice(long userLevelDiscountPrice) {
        this.userLevelDiscountPrice = userLevelDiscountPrice;
    }

    public long getCancelCouponDiscountPrice() {
        return cancelCouponDiscountPrice;
    }

    public void setCancelCouponDiscountPrice(long cancelCouponDiscountPrice) {
        this.cancelCouponDiscountPrice = cancelCouponDiscountPrice;
    }

    public long getCancelSellerDiscountPrice() {
        return cancelSellerDiscountPrice;
    }

    public void setCancelSellerDiscountPrice(long cancelSellerDiscountPrice) {
        this.cancelSellerDiscountPrice = cancelSellerDiscountPrice;
    }

    public long getCancelSpotDiscountPrice() {
        return cancelSpotDiscountPrice;
    }

    public void setCancelSpotDiscountPrice(long cancelSpotDiscountPrice) {
        this.cancelSpotDiscountPrice = cancelSpotDiscountPrice;
    }

    public long getCancelUserLevelDiscountPrice() {
        return cancelUserLevelDiscountPrice;
    }

    public void setCancelUserLevelDiscountPrice(long cancelUserLevelDiscountPrice) {
        this.cancelUserLevelDiscountPrice = cancelUserLevelDiscountPrice;
    }

    public long getDiscountAmount() {
        return (this.couponDiscountPrice + this.sellerDiscountPrice + this.spotDiscountPrice + this.userLevelDiscountPrice);
    }

    public long getCancelDiscountAmount() {
        return (this.cancelCouponDiscountPrice + this.cancelSellerDiscountPrice + this.cancelSpotDiscountPrice + this.cancelUserLevelDiscountPrice);
    }

    public long getPayAmount() {
        return (this.saleAmount + this.shipping);
    }

    public long getCancelPayAmount() {
        return (this.cancelAmount + this.cancelShipping);
    }

    public long getTotalCount() {
        return this.saleCount + this.cancelCount;
    }

    public long getTotalItemPrice() {
        return this.itemPrice + this.cancelItemPrice;
    }

    public long getTotalDiscountAmount() { return this.getDiscountAmount() + this.getCancelDiscountAmount(); }

    public long getTotalAmount() {
        return this.saleAmount + this.cancelAmount;
    }

    public long getTotalShipping() {
        return this.shipping + this.cancelShipping;
    }

    public long getTotalPayAmount() {
        return this.getTotalAmount() + this.getTotalShipping();
    }

    public long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public long getCancelItemPrice() {
        return cancelItemPrice;
    }

    public void setCancelItemPrice(long cancelItemPrice) {
        this.cancelItemPrice = cancelItemPrice;
    }

    public long getShipping() {
        return shipping;
    }

    public void setShipping(long shipping) {
        this.shipping = shipping;
    }

    public long getCancelShipping() {
        return cancelShipping;
    }

    public void setCancelShipping(long cancelShipping) {
        this.cancelShipping = cancelShipping;
    }
}
