package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum  UmsType implements CodeMapperType {

    MESSAGE("MESSAGE", "SMS/LMS", "SMS/LMS"),
    ALIM_TALK("ALIM_TALK", "알림톡", "알림톡"),
    PUSH("PUSH", "푸쉬", "푸쉬"),
    ;


    UmsType(String code, String title, String description) {
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
