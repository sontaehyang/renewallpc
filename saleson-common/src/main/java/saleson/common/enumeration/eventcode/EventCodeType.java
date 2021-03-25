package saleson.common.enumeration.eventcode;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum EventCodeType implements CodeMapperType {

    NONE("기본","기본"),
    CAMPAIGN("캠페인","캠페인"),
    SHARE("공유","공유"),
    EP_ITEM("EP 상품","EP 상품"),
    FEATURED("기획전","기획전");

    private String title;
    private String description;

    EventCodeType(String title, String description) {
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
