package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum TaxType implements CodeMapperType {
	CHARGE("과세"),
	FREE("면세");

	private String title;

	TaxType(String title) {
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
