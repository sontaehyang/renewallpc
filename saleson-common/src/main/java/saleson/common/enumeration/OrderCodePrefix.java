package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum OrderCodePrefix implements CodeMapperType {

    FRONT("K","프론트 주문코드 Prefix", "프론트 주문코드 Prefix"),
    MOBILE("K","모바일 주문코드 Prefix", "모바일 주문코드 Prefix");

    private String code;
    private String title;
    private String description;

    OrderCodePrefix(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

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
