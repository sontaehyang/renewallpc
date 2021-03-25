package saleson.shop.order.support;

@SuppressWarnings("serial")
public class OrderManagerException extends RuntimeException {
	private static String BASE_REDIRECT_URL = "/opmanager/order/list";
	private String redirectUrl;
	private String javascript;
	
	public OrderManagerException() {
		super("잘못된 접근입니다.");
		setRedirectUrl(BASE_REDIRECT_URL);
	}
	
	public OrderManagerException(String message) {
		super(message);
	}
	
	public OrderManagerException(String message, String redirectUrl) {
		super(message);
		setRedirectUrl(redirectUrl);
	}
	
	public OrderManagerException(String message, String redirectUrl, String javascript) {
		super(message);
		setRedirectUrl(redirectUrl);
		setJavascript(javascript);
	}
	
	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public String getRedirectUrl() {
		return this.redirectUrl;
	}
	
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
