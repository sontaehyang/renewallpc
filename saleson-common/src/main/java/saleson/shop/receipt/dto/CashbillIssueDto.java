package saleson.shop.receipt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saleson.common.enumeration.CashbillIssueType;
import saleson.common.enumeration.CashbillStatus;
import saleson.common.enumeration.TaxType;
import saleson.model.Cashbill;

@Getter @Setter @NoArgsConstructor
public class CashbillIssueDto {
	private long id;
	private String mgtKey;
	private TaxType taxType;
	private String itemName;            // 상품명
	private long amount;
	private CashbillIssueType cashbillIssueType;			// 발급구분 (NORMAL: 일반발급, TEMP: 자진발급)
	private CashbillStatus cashbillStatus;					// 발급상태 (PENDING, ISSUED, CANCELED)
	private String createdDate;
	private String issuedDate;
	private String canceledDate;
	private String updatedDate;			// 최종 수정일
	private String updateBy; 			// 최종 수정자 - 이름 (loginId)
	private long cashbillId;
	private Cashbill cashbill;
}
