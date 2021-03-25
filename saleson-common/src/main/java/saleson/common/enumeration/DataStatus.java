package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum DataStatus implements CodeMapperType {

    NORMAL("normal","정상","데이터 정상 상태"),
    DELETE("delete","삭제","데이터 삭제 상태");
    ;

    DataStatus(String code, String title, String description) {
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

