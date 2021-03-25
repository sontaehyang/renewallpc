package saleson.common.enumeration.kakao.alimtalk;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum TemplateInspStatus implements CodeMapperType {

    REG("REG","등록","등록"),
    REQ("REQ","심사요청","심사요청"),
    APR("APR","승인","승인"),
    REJ("REJ","반려","반려");

    private String code;
    private String title;
    private String description;

    TemplateInspStatus(String code, String title, String description) {
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
