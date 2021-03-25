package saleson.shop.remittance.support;

@SuppressWarnings("serial")
public class RemittanceException extends RuntimeException {

	private String redirectUrl;
	
	public RemittanceException(String url) {
		super("잘못된 접근입니다.");
		setRedirectUrl(url);
	}

	public RemittanceException(String url, String message) {
		super(message);
		setRedirectUrl(url);
	}
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
}
