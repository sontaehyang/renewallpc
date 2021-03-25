package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum GiftGroupType implements CodeMapperType {
    NONE("NONE","해당없음","해당없음"),
    ORDER("order","주문서 전체","주문서 전체"),
    ORDER_PRICE("orderPrice","주문 금액별","주문 금액별")
    ;

    GiftGroupType(String code, String title, String description) {
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

