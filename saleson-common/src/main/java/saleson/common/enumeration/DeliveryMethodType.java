package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum DeliveryMethodType implements CodeMapperType {

    NORMAL("일반택배"),
    QUICK("퀵서비스"),
    PICK_UP("방문수령");

    private String title;

    DeliveryMethodType(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Boolean isEnabled() {
        return true;
    }
}
