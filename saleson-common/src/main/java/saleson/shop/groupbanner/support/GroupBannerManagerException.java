package saleson.shop.groupbanner.support;

@SuppressWarnings("serial")
public class GroupBannerManagerException extends RuntimeException {
	private static String BASE_REDIRECT_URL = "/opmanager/group-banner/list";
	private String redirectUrl;
	
	public GroupBannerManagerException() {
		super("잘못된 접근입니다.");
		setRedirectUrl(BASE_REDIRECT_URL);
	}
	
	public GroupBannerManagerException(String message) {
		super(message);
		setRedirectUrl(BASE_REDIRECT_URL);
	}
	public GroupBannerManagerException(String message, Throwable e) {
		super(message, e);
		setRedirectUrl(BASE_REDIRECT_URL);
	}
	
	public GroupBannerManagerException(String message, String redirectUrl) {
		super(message);
		setRedirectUrl(redirectUrl);
	}
	
	public String getRedirectUrl() {
		return this.redirectUrl;
	}
	
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
