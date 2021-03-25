package saleson.common.enumeration.kakao.alimtalk;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum TemplateButtonType implements CodeMapperType {

    DS("DS","배송조회","배송조회"),
    WL("WL","웹링크","웹링크"),
    AL("AL","앱링크","앱링크"),
    BK("BK","봇키워드","봇키워드"),
    MD("MD","메시지전달","메시지전달");

    private String code;
    private String title;
    private String description;

    TemplateButtonType(String code, String title, String description) {
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
