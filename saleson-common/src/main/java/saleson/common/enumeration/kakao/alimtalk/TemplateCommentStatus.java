package saleson.common.enumeration.kakao.alimtalk;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum TemplateCommentStatus implements CodeMapperType {

    INQ("INQ","문의","문의"),
    APR("APR","승인","승인"),
    REJ("REJ","반려","반려"),
    REP("REP","답변","답변");

    private String code;
    private String title;
    private String description;

    TemplateCommentStatus(String code, String title, String description) {
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
