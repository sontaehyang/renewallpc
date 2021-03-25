package saleson.shop.order.admin.support;

@SuppressWarnings("serial")
public class OrderAdminException extends RuntimeException {
	private static String BASE_REDIRECT_URL = "/opmanager/order/admin/list";
	private String redirectUrl;
	private String javascript;
	
	public OrderAdminException() {
		super("잘못된 접근입니다.");
		setRedirectUrl(BASE_REDIRECT_URL);
	}
	
	public OrderAdminException(String message) {
		super(message);
	}
	
	public OrderAdminException(String message, String redirectUrl) {
		super(message);
		setRedirectUrl(redirectUrl);
	}
	
	public OrderAdminException(String message, String redirectUrl, String javascript) {
		super(message);
		setRedirectUrl(redirectUrl);
		setJavascript(javascript);
	}
	
	public static String getBASE_REDIRECT_URL() {
		return BASE_REDIRECT_URL;
	}
	public static void setBASE_REDIRECT_URL(String bASE_REDIRECT_URL) {
		BASE_REDIRECT_URL = bASE_REDIRECT_URL;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getJavascript() {
		return javascript;
	}
	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}
	
	
}
