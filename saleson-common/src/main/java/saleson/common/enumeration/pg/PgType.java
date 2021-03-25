package saleson.common.enumeration.pg;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum PgType implements CodeMapperType {

    CJ("cj","cj"),
    INICIS("inicis","inicis"),
    KSPAY("kspay","kspay"),
    LGDACOM("lgdacom","lgdacom"),
    NICEPAY("nicepay","nicepay"),
    KCP("kcp","kcp"),
    EASYPAY("easypay","easypay");

    PgType(String title, String description) {
        this.title = title;
        this.description = description;
    }

    private String title;
    private String description;

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
