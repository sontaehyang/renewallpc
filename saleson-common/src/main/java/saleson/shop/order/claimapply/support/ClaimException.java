package saleson.shop.order.claimapply.support;

@SuppressWarnings("serial")
public class ClaimException extends RuntimeException {
	
	private String errorCode;
	
	public ClaimException() {
		
		super("잘못된 접근입니다.");
		setErrorCode("9999");
	}
	
	public ClaimException(String errorCode) {
		super("잘못된 접근입니다.");
		setErrorCode(errorCode);
	}

	public ClaimException(String errorCode, String message) {
		super(message);
		setErrorCode(errorCode);
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
