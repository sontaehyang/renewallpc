package saleson.shop.order.pg.cj.domain;

public class CjResult {
	private String CJSResultCode; // 결제 성공여부 (성공: sucess / 실패:error)
	private String CJSResultMessage; // 결과메시지
	private String CJSAmountTotal; // 거래금액
	private String CJSShopOrderNo; // 상점 측 주문번호
	private String CJSPayMethod; // 결제수단
	private String CJSPayID; // PayID
	private String CJSTradeID; // CJSTradeID
	private String CJSShopID;
	private String CJSPayDate; // 결제일시
	
	
	// 신용카드
	private String CJSCardCode; // 카드 코드
	private String CJSCardName; // 카드사 명
	private String CJSApprovalNo; // 승인번호
	private String CJSNoInt; // 무이자 여부
	private String CJSHalbu; // 할부 개월수
	
	
	// 계좌이체
	private String CJSDepositDate; // 입금일자
	private String CJSBankName; // 입금은행명
	private String CJSBankCode; // 입금은행 코드
	private String CJSCashApprovalNo; // 현금영수증 승인번호
	
	
	// 가상계좌
	//CJSBankName 입금은행명
	private String CJSAccountOWNER; // 입금자명
	private String CJSAccountNo; // 가상계좌번호
	private String closeDate;
	//CJSCashApprovalNo 현금영수증 승인번호
	
	// RedirectUrl에서 받는 정보.. 왜 이걸 다르게 했지??
	private String payType; // 해당 정보가 있는경우 카드결제로 판단하자 DATA = CRD
	private String orderCode; // -> CJSShopOrderNo
	private String totalPrice; // -> CJSAmountTotal
	private String cid; // -> CJSShopID
	
	private String reserve01; // SessionId 셋팅
	private String reserve02; // UserId 셋팅
	
	public String getReserve02() {
		return reserve02;
	}
	public void setReserve02(String reserve02) {
		this.reserve02 = reserve02;
	}
	public String getReserve01() {
		return reserve01;
	}
	public void setReserve01(String reserve01) {
		this.reserve01 = reserve01;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCJSResultCode() {
		return CJSResultCode;
	}
	public void setCJSResultCode(String cJSResultCode) {
		CJSResultCode = cJSResultCode;
	}
	public String getCJSResultMessage() {
		return CJSResultMessage;
	}
	public void setCJSResultMessage(String cJSResultMessage) {
		CJSResultMessage = cJSResultMessage;
	}
	public String getCJSAmountTotal() {
		return CJSAmountTotal;
	}
	public void setCJSAmountTotal(String cJSAmountTotal) {
		CJSAmountTotal = cJSAmountTotal;
	}
	public String getCJSShopOrderNo() {
		return CJSShopOrderNo;
	}
	public void setCJSShopOrderNo(String cJSShopOrderNo) {
		CJSShopOrderNo = cJSShopOrderNo;
	}
	public String getCJSPayMethod() {
		return CJSPayMethod;
	}
	public void setCJSPayMethod(String cJSPayMethod) {
		CJSPayMethod = cJSPayMethod;
	}
	public String getCJSPayID() {
		return CJSPayID;
	}
	public void setCJSPayID(String cJSPayID) {
		CJSPayID = cJSPayID;
	}
	public String getCJSTradeID() {
		return CJSTradeID;
	}
	public void setCJSTradeID(String cJSTradeID) {
		CJSTradeID = cJSTradeID;
	}
	public String getCJSShopID() {
		return CJSShopID;
	}
	public void setCJSShopID(String cJSShopID) {
		CJSShopID = cJSShopID;
	}
	public String getCJSPayDate() {
		return CJSPayDate;
	}
	public void setCJSPayDate(String cJSPayDate) {
		CJSPayDate = cJSPayDate;
	}
	public String getCJSCardCode() {
		return CJSCardCode;
	}
	public void setCJSCardCode(String cJSCardCode) {
		CJSCardCode = cJSCardCode;
	}
	public String getCJSCardName() {
		return CJSCardName;
	}
	public void setCJSCardName(String cJSCardName) {
		CJSCardName = cJSCardName;
	}
	public String getCJSApprovalNo() {
		return CJSApprovalNo;
	}
	public void setCJSApprovalNo(String cJSApprovalNo) {
		CJSApprovalNo = cJSApprovalNo;
	}
	public String getCJSNoInt() {
		return CJSNoInt;
	}
	public void setCJSNoInt(String cJSNoInt) {
		CJSNoInt = cJSNoInt;
	}
	public String getCJSHalbu() {
		return CJSHalbu;
	}
	public void setCJSHalbu(String cJSHalbu) {
		CJSHalbu = cJSHalbu;
	}
	public String getCJSDepositDate() {
		return CJSDepositDate;
	}
	public void setCJSDepositDate(String cJSDepositDate) {
		CJSDepositDate = cJSDepositDate;
	}
	public String getCJSBankName() {
		return CJSBankName;
	}
	public void setCJSBankName(String cJSBankName) {
		CJSBankName = cJSBankName;
	}
	public String getCJSBankCode() {
		return CJSBankCode;
	}
	public void setCJSBankCode(String cJSBankCode) {
		CJSBankCode = cJSBankCode;
	}
	public String getCJSCashApprovalNo() {
		return CJSCashApprovalNo;
	}
	public void setCJSCashApprovalNo(String cJSCashApprovalNo) {
		CJSCashApprovalNo = cJSCashApprovalNo;
	}
	public String getCJSAccountOWNER() {
		return CJSAccountOWNER;
	}
	public void setCJSAccountOWNER(String cJSAccountOWNER) {
		CJSAccountOWNER = cJSAccountOWNER;
	}
	public String getCJSAccountNo() {
		return CJSAccountNo;
	}
	public void setCJSAccountNo(String cJSAccountNo) {
		CJSAccountNo = cJSAccountNo;
	}
}
