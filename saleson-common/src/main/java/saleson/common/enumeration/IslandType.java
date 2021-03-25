package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum IslandType implements CodeMapperType {
    JEJU("제주", true),
    ISLAND("도서산간", true),
    QUICK("퀵", true)
    ;

    IslandType(String title, Boolean enabled) {
        this.title = title;
        this.enabled = enabled;
    }

    private String title;
    private String description;
    private Boolean enabled;

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
