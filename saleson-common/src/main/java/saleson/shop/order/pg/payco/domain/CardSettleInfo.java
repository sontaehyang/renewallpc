package saleson.shop.order.pg.payco.domain;

public class CardSettleInfo {
	private String cardCompanyName; // 카드사명
	private String cardCompanyCode; // 카드사코드
	private String cardNo; // 카드번호
	private String cardInstallmentMonthNumber; // 할부개월(MM)
	private String cardAdmissionNo;	// 카드사 승인번호
	private String cardInterestFreeYn; // 무이자여부(Y/N)
	private String corporateCardYn; // 법인카드여부 (개인 N ,법인 Y)
	private String partCancelPossibleYn; // 부분취소가능유무(Y/N)
	
	public String getCardCompanyName() {
		return cardCompanyName;
	}
	public void setCardCompanyName(String cardCompanyName) {
		this.cardCompanyName = cardCompanyName;
	}
	public String getCardCompanyCode() {
		return cardCompanyCode;
	}
	public void setCardCompanyCode(String cardCompanyCode) {
		this.cardCompanyCode = cardCompanyCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardInstallmentMonthNumber() {
		return cardInstallmentMonthNumber;
	}
	public void setCardInstallmentMonthNumber(String cardInstallmentMonthNumber) {
		this.cardInstallmentMonthNumber = cardInstallmentMonthNumber;
	}
	public String getCardAdmissionNo() {
		return cardAdmissionNo;
	}
	public void setCardAdmissionNo(String cardAdmissionNo) {
		this.cardAdmissionNo = cardAdmissionNo;
	}
	public String getCardInterestFreeYn() {
		return cardInterestFreeYn;
	}
	public void setCardInterestFreeYn(String cardInterestFreeYn) {
		this.cardInterestFreeYn = cardInterestFreeYn;
	}
	public String getCorporateCardYn() {
		return corporateCardYn;
	}
	public void setCorporateCardYn(String corporateCardYn) {
		this.corporateCardYn = corporateCardYn;
	}
	public String getPartCancelPossibleYn() {
		return partCancelPossibleYn;
	}
	public void setPartCancelPossibleYn(String partCancelPossibleYn) {
		this.partCancelPossibleYn = partCancelPossibleYn;
	}
	
	
}
