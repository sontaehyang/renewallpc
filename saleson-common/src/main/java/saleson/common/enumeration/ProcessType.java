package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum ProcessType implements CodeMapperType {
    PROGRESS("progress","진행","진행 중"),
    END("end","진행 종료","진행 종료"),
    NOT_PROGRESS("not_progress","진행 전","진행 전")
    ;

    ProcessType(String code, String title, String description) {
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
