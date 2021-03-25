package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum CashbillStatus implements CodeMapperType {
	PENDING("발급대기"),
	ISSUED("발급완료"),
	CANCELED("발급취소");

	private String title;

	CashbillStatus(String title) {
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
