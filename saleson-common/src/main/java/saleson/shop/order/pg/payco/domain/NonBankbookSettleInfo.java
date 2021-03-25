package saleson.shop.order.pg.payco.domain;

public class NonBankbookSettleInfo {
	private String bankName; // 은행명
	private String bankCode; // 은행 코드
	private String accountNo; // 계좌번호
	private String paymentExpirationYmd; // 입금만료일
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getPaymentExpirationYmd() {
		return paymentExpirationYmd;
	}
	public void setPaymentExpirationYmd(String paymentExpirationYmd) {
		this.paymentExpirationYmd = paymentExpirationYmd;
	}
	
	
}
