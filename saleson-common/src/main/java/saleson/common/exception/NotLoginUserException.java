package saleson.common.exception;

import com.onlinepowers.framework.exception.OpRuntimeException;

@SuppressWarnings("serial")
public class NotLoginUserException  extends OpRuntimeException {
	private String redirectUrl;
	
	public NotLoginUserException(String redirectUrl) {
		super("로그인 후 이용이 가능합니다.");
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
