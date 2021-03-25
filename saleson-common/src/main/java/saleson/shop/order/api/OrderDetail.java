package saleson.shop.order.api;

import saleson.shop.coupon.domain.api.BuyCouponList;
import saleson.shop.coupon.domain.api.BuyShippingCouponInfo;
import saleson.shop.item.domain.api.ItemInfo;
import saleson.shop.order.domain.*;

import java.util.HashMap;
import java.util.List;

public class OrderDetail {

    // 주문번호
    private String orderCode;

    // 주문일자
    private String createdDate;

    // 주문자
    private String userName;

    // 연락처
    private String mobile;

    // 상품금액
    private int totalItemAmount;

    // 배송비
    private int totalShippingAmount;

    // 할인금액
    private int totalDiscountAmount;

    // 쿠폰할인금액
    private int totalCouponDiscountAmount;

    // 회원할인금액
    private int totalUserLevelDiscountAmount;

    // 총 결제 금액
    private int totalOrderAmount;

    // 보유 포인트
    private int retentionPoint;

    // 결제금액정보
    private OrderPrice orderPrice;

    // 배송정보
    private ShippingInfo shippingInfo;

    // 상품정보
    private List<ItemInfo> item;

    // 결제정보
    private PaymentInfo paymentInfo;

    // 결제정보 리스트
    private List<PaymentInfo> paymentList;

    // groups
    private List<Shipping> itemGroups;
    private List<BuyItem> items;

    //
    private int shippingIndex;

    //
    private List<BuyQuantity> buyQuantitys;

    // 적용가능 쿠폰 리스트
    private List<BuyCouponList> couponList;

    // 사용가능 배송비 쿠폰
    private int shippingCoupon;

    // 배송비 쿠폰 리스트
    private List<BuyShippingCouponInfo> shippingCouponList;

    private HashMap<String, BuyPayment> buyPayments;

    private boolean isAdditionItem;
    private List<String> makeUseCouponKeys;
    private String sellerNames;

    public HashMap<String, BuyPayment> getBuyPayments() {
        return buyPayments;
    }
    public void setBuyPayments(HashMap<String, BuyPayment> buyPayments) {
        this.buyPayments = buyPayments;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getTotalItemAmount() {
        return totalItemAmount;
    }

    public void setTotalItemAmount(int totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
    }

    public int getTotalShippingAmount() {
        return totalShippingAmount;
    }

    public void setTotalShippingAmount(int totalShippingAmount) {
        this.totalShippingAmount = totalShippingAmount;
    }

    public int getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(int totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public int getTotalCouponDiscountAmount() {
        return totalCouponDiscountAmount;
    }

    public void setTotalCouponDiscountAmount(int totalCouponDiscountAmount) {
        this.totalCouponDiscountAmount = totalCouponDiscountAmount;
    }

    public int getTotalUserLevelDiscountAmount() {
        return totalUserLevelDiscountAmount;
    }

    public void setTotalUserLevelDiscountAmount(int totalUserLevelDiscountAmount) {
        this.totalUserLevelDiscountAmount = totalUserLevelDiscountAmount;
    }

    public int getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(int totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public List<ItemInfo> getItem() {
        return item;
    }

    public void setItem(List<ItemInfo> item) {
        this.item = item;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getRetentionPoint() {
        return retentionPoint;
    }

    public void setRetentionPoint(int retentionPoint) {
        this.retentionPoint = retentionPoint;
    }

    public OrderPrice getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(OrderPrice orderPrice) {
        this.orderPrice = orderPrice;
    }

    public List<BuyQuantity> getBuyQuantitys() {
        return buyQuantitys;
    }

    public void setBuyQuantitys(List<BuyQuantity> buyQuantitys) {
        this.buyQuantitys = buyQuantitys;
    }

    public List<BuyCouponList> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<BuyCouponList> couponList) {
        this.couponList = couponList;
    }

    public int getShippingIndex() {
        return shippingIndex;
    }

    public void setShippingIndex(int shippingIndex) {
        this.shippingIndex = shippingIndex;
    }

    public int getShippingCoupon() {
        return shippingCoupon;
    }

    public void setShippingCoupon(int shippingCoupon) {
        this.shippingCoupon = shippingCoupon;
    }

    public List<BuyShippingCouponInfo> getShippingCouponList() {
        return shippingCouponList;
    }

    public void setShippingCouponList(List<BuyShippingCouponInfo> shippingCouponList) {
        this.shippingCouponList = shippingCouponList;
    }

    public List<PaymentInfo> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<PaymentInfo> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Shipping> getItemGroups() {
        return itemGroups;
    }

    public void setItemGroups(List<Shipping> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public boolean isAdditionItem() {
        return isAdditionItem;
    }

    public void setAdditionItem(boolean additionItem) {
        isAdditionItem = additionItem;
    }

    public List<String> getMakeUseCouponKeys() {
        return makeUseCouponKeys;
    }

    public void setMakeUseCouponKeys(List<String> makeUseCouponKeys) {
        this.makeUseCouponKeys = makeUseCouponKeys;
    }

    public String getSellerNames() {
        return sellerNames;
    }

    public void setSellerNames(String sellerNames) {
        this.sellerNames = sellerNames;
    }

    public List<BuyItem> getItems() {
        return items;
    }

    public void setItems(List<BuyItem> items) {
        this.items = items;
    }
}
