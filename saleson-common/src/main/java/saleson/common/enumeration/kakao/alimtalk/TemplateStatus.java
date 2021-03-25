package saleson.common.enumeration.kakao.alimtalk;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum TemplateStatus implements CodeMapperType {

    S("S","중단","중단"),
    A("A","정상","정상"),
    R("R","대기(발송전)","대기(발송전)");

    private String code;
    private String title;
    private String description;

    TemplateStatus(String code, String title, String description) {
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
