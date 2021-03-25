package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum CashbillType implements CodeMapperType {
    NONE("발급안함", ""),
	BUSINESS("사업자증빙용", ""),
	PERSONAL("개인소득공제용", "");


	private String title;
	private String description;

	CashbillType(String title, String description) {
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
