package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum CashbillIssueType implements CodeMapperType {
	NORMAL("정상발급", ""),
	TEMP("자진발급", "결제 금액이 10만원 이상인 경우 고객이 현금영수증을 신청하지 않아도 의무적으로 발행되는 상태");

	private String title;
	private String description;

	CashbillIssueType(String title, String description) {
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
