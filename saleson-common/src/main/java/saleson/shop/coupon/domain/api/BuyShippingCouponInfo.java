package saleson.shop.coupon.domain.api;

public class BuyShippingCouponInfo {

    private String shippingType; // 배송비 구분 (1: 무료배송, 2: 판매자조건부, 3:출고지조건부, 4:상품조건부, 5:개당배송비, 6:고정배송비)
    private String shippingPaymentType; // 배송비 지불 방법 (1 : 선불, 2 : 착불)
    private int shippingSequence;
    private String shippingGroupCode;
    private int itemId;
    private int optionId;

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getShippingPaymentType() {
        return shippingPaymentType;
    }

    public void setShippingPaymentType(String shippingPaymentType) {
        this.shippingPaymentType = shippingPaymentType;
    }

    public int getShippingSequence() {
        return shippingSequence;
    }

    public void setShippingSequence(int shippingSequence) {
        this.shippingSequence = shippingSequence;
    }

    public String getShippingGroupCode() {
        return shippingGroupCode;
    }

    public void setShippingGroupCode(String shippingGroupCode) {
        this.shippingGroupCode = shippingGroupCode;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
}
