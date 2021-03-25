package saleson.shop.order.pg.kakaopay.domain;

public class KakaopayInfo {
	private boolean paySuccess;
	
	private String ResultCode;
	private String ResultMsg;
	private String ErrorCD;
	private String ErrorMsg;
	
	private String TID;				// CNSPay 거래 ID
	private String StateCd;			// 거래상태 - 0:승인, 1:전체취소, 2:부분취소 (여러건부분취소후잔액이 0원일 경우 1:전체취소 로 응답)
	private String AppAmt;			// 승인금액 - 최초 승인요청 금액
	private String CcAmt;			// 취소금액 - 현재 취소완료된 금액
	private String RemainAmt;		// 승인잔액 - 현재 승인상태로 남아있는 금액
	private String CancelYn;		// 요청 취소건 취소결과 - CancelNo를 설정하여 확인요청한 취소 건의 취소성공여부 (Y:성공, N:실패)	* CancelNo를 설정했을 경우에만 응답
	
	
	private String StateNm;
	
	
	public boolean isPaySuccess() {
		return paySuccess;
	}
	public void setPaySuccess(boolean paySuccess) {
		this.paySuccess = paySuccess;
	}
	public String getTID() {
		return TID;
	}
	public void setTID(String tID) {
		TID = tID;
	}
	public String getStateCd() {
		return StateCd;
	}
	public void setStateCd(String stateCd) {
		StateCd = stateCd;
	}
	public String getAppAmt() {
		return AppAmt;
	}
	public void setAppAmt(String appAmt) {
		AppAmt = appAmt;
	}
	public String getCcAmt() {
		return CcAmt;
	}
	public void setCcAmt(String ccAmt) {
		CcAmt = ccAmt;
	}
	public String getRemainAmt() {
		return RemainAmt;
	}
	public void setRemainAmt(String remainAmt) {
		RemainAmt = remainAmt;
	}
	public String getCancelYn() {
		return CancelYn;
	}
	public void setCancelYn(String cancelYn) {
		CancelYn = cancelYn;
	}
	public String getResultCode() {
		return ResultCode;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public String getResultMsg() {
		return ResultMsg;
	}
	public void setResultMsg(String resultMsg) {
		ResultMsg = resultMsg;
	}
	public String getErrorCD() {
		return ErrorCD;
	}
	public void setErrorCD(String errorCD) {
		ErrorCD = errorCD;
	}
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
	public String getStateNm() {
		return StateNm;
	}
	public void setStateNm(String stateNm) {
		StateNm = stateNm;
	}
	@Override
	public String toString() {
		return "KakaopayInfo [paySuccess=" + paySuccess + ", ResultCode="
				+ ResultCode + ", ResultMsg=" + ResultMsg + ", ErrorCD="
				+ ErrorCD + ", ErrorMsg=" + ErrorMsg + ", TID=" + TID
				+ ", StateCd=" + StateCd + ", AppAmt=" + AppAmt + ", CcAmt="
				+ CcAmt + ", RemainAmt=" + RemainAmt + ", CancelYn=" + CancelYn
				+ ", StateNm=" + StateNm + "]";
	}
}
