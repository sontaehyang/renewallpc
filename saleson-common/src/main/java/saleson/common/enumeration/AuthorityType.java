package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum AuthorityType implements CodeMapperType {

    USER("ROLE_USER","회원","회원"),
    SELLER("ROLE_SELLER","판매자","판매자"),
    SELLER_MASTER("ROLE_SELLER_MASTER","기본 판매 운영자","기본 판매 운영자"),
    MANAGER("ROLE_OPMANAGER","관리자","관리자")
    ;

    private String code;
    private String title;
    private String description;

    AuthorityType(String code, String title, String description) {
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
