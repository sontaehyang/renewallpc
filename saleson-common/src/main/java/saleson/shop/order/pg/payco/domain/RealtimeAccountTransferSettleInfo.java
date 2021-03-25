package saleson.shop.order.pg.payco.domain;

public class RealtimeAccountTransferSettleInfo {
	private String bankName; // 은행명
	private String bankCode; // 은행코드

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
}
