package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum LabelType implements CodeMapperType {

    ITEM("ITEM", "상품", "상품 라벨");

    LabelType(String code, String title, String description) {
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
