package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum UserType implements CodeMapperType {
    USER("user","회원","회원"),
    GUEST("guest","비회원","비회원"),
    SELLER("seller","판매자","판매자"),
    MANAGER("manager","관리자","관리자")
    ;

    UserType(String code, String title, String description) {
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
