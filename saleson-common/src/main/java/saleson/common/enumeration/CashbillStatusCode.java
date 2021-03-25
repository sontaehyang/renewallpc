package saleson.common.enumeration;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum CashbillStatusCode implements CodeMapperType {
	TEMP("임시저장","임시저장 상태"),
	ISSUED("발급완료","발급이 완료된 상태"),
    SENDING("전송중","국세청 전송 처리중"),
	COMPLETED("전송완료","국세청에 전송이 완료된 상태"),
	FAILED("전송실패","국세청에 전송이 되었으나, 국세청에서 오류를 반환한 상태"),
	CANCEL("취소","국세청 전송 전 발행자에 의해 발행취소");


	private String title;
    private String description;

	CashbillStatusCode(String title, String description) {
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
		return description;
	}

	@Override
	public Boolean isEnabled() {
		return true;
	}
}
