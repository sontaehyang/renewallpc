package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum FilterType implements CodeMapperType {
    NORMAL("NORMAL", "일반", "일반"),
    COLOR("COLOR", "색상", "색상"),
    IMAGE("IMAGE", "이미지", "이미지"),
    ;

    FilterType(String code, String title, String description) {
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
