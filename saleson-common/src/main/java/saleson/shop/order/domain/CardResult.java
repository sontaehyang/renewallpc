package saleson.shop.order.domain;

public class CardResult {
	private String mStatus;
	private String orderCode;
	private String mErrMsg;
	private String vResultCode;
	private String reqCardNumber;
	private String resAuthCode;
	private String actionCode;
	private String txnVersion;
	private String marchTxn;
	
	
	public String getMarchTxn() {
		return marchTxn;
	}
	public void setMarchTxn(String marchTxn) {
		this.marchTxn = marchTxn;
	}
	public String getTxnVersion() {
		return txnVersion;
	}
	public void setTxnVersion(String txnVersion) {
		this.txnVersion = txnVersion;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getmStatus() {
		return mStatus;
	}
	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getmErrMsg() {
		return mErrMsg;
	}
	public void setmErrMsg(String mErrMsg) {
		this.mErrMsg = mErrMsg;
	}
	public String getvResultCode() {
		return vResultCode;
	}
	public void setvResultCode(String vResultCode) {
		this.vResultCode = vResultCode;
	}
	public String getReqCardNumber() {
		return reqCardNumber;
	}
	public void setReqCardNumber(String reqCardNumber) {
		this.reqCardNumber = reqCardNumber;
	}
	public String getResAuthCode() {
		return resAuthCode;
	}
	public void setResAuthCode(String resAuthCode) {
		this.resAuthCode = resAuthCode;
	}
	
	
}
