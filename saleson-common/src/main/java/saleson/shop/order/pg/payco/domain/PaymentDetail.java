package saleson.shop.order.pg.payco.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDetail {
	private String paymentTradeNo; // 결제번호
	private String paymentMethodCode; // 결제수단코드
	private String paymentMethodName; // 결제수단명
	private int paymentAmt; // 결제 금액
	private String tradeYmdt; // 결제 일시
	private String pgAdmissionNo; // PG승인번호
	private String pgAdmissionYmdt; // PG승인일시
	private String easyPaymentYn; // 간편결제여부(Y/N)
	
	@JsonProperty(required=false)
	private CardSettleInfo cardSettleInfo; // 카드결제 정보
	
	@JsonProperty(required=false)
	private CellphoneSettleInfo cellphoneSettleInfo; // 핸드폰 결제 정보
	
	@JsonProperty(required=false)
	private RealtimeAccountTransferSettleInfo realtimeAccountTransferSettleInfo; // 실시간계좌이체
	
	@JsonProperty(required=false)
	private NonBankbookSettleInfo nonBankbookSettleInfo; // 무통장입금
	
	public CellphoneSettleInfo getCellphoneSettleInfo() {
		return cellphoneSettleInfo;
	}
	public void setCellphoneSettleInfo(CellphoneSettleInfo cellphoneSettleInfo) {
		this.cellphoneSettleInfo = cellphoneSettleInfo;
	}
	public RealtimeAccountTransferSettleInfo getRealtimeAccountTransferSettleInfo() {
		return realtimeAccountTransferSettleInfo;
	}
	public void setRealtimeAccountTransferSettleInfo(
			RealtimeAccountTransferSettleInfo realtimeAccountTransferSettleInfo) {
		this.realtimeAccountTransferSettleInfo = realtimeAccountTransferSettleInfo;
	}
	public NonBankbookSettleInfo getNonBankbookSettleInfo() {
		return nonBankbookSettleInfo;
	}
	public void setNonBankbookSettleInfo(
			NonBankbookSettleInfo nonBankbookSettleInfo) {
		this.nonBankbookSettleInfo = nonBankbookSettleInfo;
	}
	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}
	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}
	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}
	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}
	public String getPaymentMethodName() {
		return paymentMethodName;
	}
	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}
	public int getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(int paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getTradeYmdt() {
		return tradeYmdt;
	}
	public void setTradeYmdt(String tradeYmdt) {
		this.tradeYmdt = tradeYmdt;
	}
	public String getPgAdmissionNo() {
		return pgAdmissionNo;
	}
	public void setPgAdmissionNo(String pgAdmissionNo) {
		this.pgAdmissionNo = pgAdmissionNo;
	}
	public String getPgAdmissionYmdt() {
		return pgAdmissionYmdt;
	}
	public void setPgAdmissionYmdt(String pgAdmissionYmdt) {
		this.pgAdmissionYmdt = pgAdmissionYmdt;
	}
	public String getEasyPaymentYn() {
		return easyPaymentYn;
	}
	public void setEasyPaymentYn(String easyPaymentYn) {
		this.easyPaymentYn = easyPaymentYn;
	}
	public CardSettleInfo getCardSettleInfo() {
		return cardSettleInfo;
	}
	public void setCardSettleInfo(CardSettleInfo cardSettleInfo) {
		this.cardSettleInfo = cardSettleInfo;
	}
}
