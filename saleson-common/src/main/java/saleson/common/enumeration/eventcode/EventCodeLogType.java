package saleson.common.enumeration.eventcode;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum EventCodeLogType implements CodeMapperType {

    NONE("기본","기본"),
    VIEW("VIEW","VIEW"),
    ORDER("주문","주문"),
    SHARE("공유","공유"),
    ITEM("상품","상품"),
    USER("회원","회원"),
    FEATURED("기획전","기획전"),
    CAMPAIGN("캠페인","캠페인");

    private String title;
    private String description;

    EventCodeLogType(String title, String description) {
        this.title = title;
        this.description = description;
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
        return true;
    }

}