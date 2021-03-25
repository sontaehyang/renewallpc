package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum FaqType implements CodeMapperType {

    USER_INFO("회원정보", "회원정보", true),
    ITEM("상품관련", "상품관련", true),
    ORDER("주문/결제", "주문/결제", true),
    SHIPMENT("배송", "배송", true),
    CLAIM("교환/반품/수리", "교환/반품/수리", true),
    POINT("포인트", "포인트", true),
    ETC("기타", "기타", true);

    private String title;
    private String description;
    private Boolean enabled;

    FaqType(String title, String description, Boolean enabled) {
        this.title = title;
        this.description = description;
        this.enabled = enabled;
    }

    @Override
    public String getCode() {
        return name();
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
		return enabled;
	}
}
