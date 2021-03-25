package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum GiftOrderStatus implements CodeMapperType {

    NORMAL("normal","정상","사은품 정상 상태"),
    RETURN("return","반환","사은품 반환 상태"),
    CANCEL("cancel","취소","사은품 취소 상태");
    ;

    GiftOrderStatus(String code, String title, String description) {
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
