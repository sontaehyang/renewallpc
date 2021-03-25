package saleson.common.enumeration.eventcode;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum EventCodeCookieType implements CodeMapperType {

    _EVENT_VIEW_UID("uid", "uid"),
    _EVENT_VIEW_QUERY_STRING("queryString", "queryString");

    private String title;
    private String description;

    EventCodeCookieType(String title, String description) {
        this.title = title;
        this.description = description;
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
        return true;
    }
}
