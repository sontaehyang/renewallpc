package saleson.erp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderLineStatus {
	private int solNo;
	private String uniq;
	private String oriUniq;
	private String bundleNo;
	private String ordStatus;
	private String skuCd;
	private int carrNo;
	private String carrName;
	private String invoiceNo;
	private String deliveryCompanyUrl;
	private String originStatus;


	public String getOrderCode() {
		return bundleNo.substring(0, 11);
	}

	public int getItemSequence() {
		if (uniq == null || uniq.length() < 18) {
			throw new IllegalStateException("uniq length error!");
		}

		String sequence = uniq.substring(12, 14);
		if ("0".equals(uniq.substring(12, 13))) {
			sequence = uniq.substring(13, 14);
		}
		return Integer.parseInt(sequence);
	}
	
	public String getOrderStatus() {
		ErpOrderStatus erpOrderStatus = findErpOrderStatus(this.ordStatus);

		if (erpOrderStatus == null) {
			return "";

		// 교환일 경우 GET, PUT 테이블의 ORDER_STATUS가 모두 '상품교환'이므로 code값 55로 세팅
		} else if ("50".equals(erpOrderStatus.getCode())) {
			return "55";
		}

		return erpOrderStatus.getCode();
	}


	private ErpOrderStatus findErpOrderStatus(String erpCode) {
		ErpOrderStatus[] es =  ErpOrderStatus.class.getEnumConstants();

		for(int i = 0; i < es.length; ++i) {
			ErpOrderStatus e = es[i];
			if (e.getErpCode().equals(erpCode)) {
				return e;
			}
		}
		return null;
	}
}
