package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum PointType implements CodeMapperType {
    POINT("point"),
    EMONEY("emoney"),
    SHIPPING("shipping"),
    MILEAGE("mileage");

    private String title;
    private String description;
    private Boolean enabled;

    PointType(String title) {
        this.title = title;
        this.description = title;
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
        return description;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }
}
