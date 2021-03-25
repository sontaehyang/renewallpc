package saleson.erp.domain;

import lombok.Getter;
import saleson.common.enumeration.mapper.CodeMapperType;

@Getter
public enum ErpOrderStatus {
	ORDERED("신규주문", "10"),
	READY_FOR_DELIVERY("배송준비중", "20"),
	DELIVERING("배송중", "30"),
	EXCHANGE_APPLY("상품교환", "50"),
	RETURN_APPLY("반품신청", "60"),
	RETURNED("반품승인", "65"),
	CANCEL_APPLY("취소신청", "70"),
	CANCELED("취소승인", "75");

	private String erpCode;			// ERP 주문상태
	private String code;			// SalesOn 주문상태

	ErpOrderStatus(String erpCode, String code) {
		this.erpCode = erpCode;
		this.code = code;
	}

	public boolean isEqualToErpCode(String erpCode) {
		return this.erpCode.equals(erpCode);
	}
}
