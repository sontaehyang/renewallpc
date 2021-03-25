package saleson.shop.order.pg.payco.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayApprovalResponse {
	private int code; // 0 : 완료
	private String message;
	private PayApprovalResult result;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PayApprovalResult getResult() {
		return result;
	}
	public void setResult(PayApprovalResult result) {
		this.result = result;
	}
}
