package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum DisplayItemType implements CodeMapperType {

    NORMAL("NORMAL", "일반", "일반"),
    BANNER("BANNER", "배너", "배너");

    private String code;
    private String title;
    private String description;

    DisplayItemType(String code, String title, String description) {
        this.code = code;
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
