package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum OrderLogType implements CodeMapperType {

    WAITING_DEPOSIT("WAITING_DEPOSIT", "입금 대기", "입금 대기 FlOW"),
    ORDER_PAYMENT("ORDER_PAYMENT", "주문 결제", "주문 결제 FlOW"),
    ORDER_SHIPPING("ORDER_SHIPPING", "주문 배송", "주문 배송 FlOW"),
    ORDER_CONFIRM("ORDER_CONFIRM", "주문 구매확정", "주문 구매확정 FlOW"),
    ORDER_REFUND("ORDER_REFUND", "주문 환불", "주문 환불 FlOW"),
    CLAIM_CANCEL("CLAIM_CANCEL", "주문 취소", "주문 취소 FlOW"),
    CLAIM_RETURN("CLAIM_RETURN", "주문 반품", "주문 반품 FlOW"),
    CLAIM_EXCHANGE("CLAIM_EXCHANGE", "주문 교환", "주문 교환 FlOW"),
    ORDER_BATCH("ORDER_BATCH", "주문 BATCH", "주문 BATCH FlOW")
    ;

    OrderLogType(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    private String code;
    private String title;
    private String description;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

	@Override
	public Boolean isEnabled() {
		return true;
	}
}
