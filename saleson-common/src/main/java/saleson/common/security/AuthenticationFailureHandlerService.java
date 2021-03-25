package saleson.common.security;

import com.onlinepowers.framework.common.LoginRequest;
import com.onlinepowers.framework.security.exception.LoginTypeNotMatchException;
import com.onlinepowers.framework.security.exception.TokenValidationException;
import com.onlinepowers.framework.security.service.SecurityService;
import com.onlinepowers.framework.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import saleson.shop.log.LoginLogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandlerService extends SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler{
	private static final Logger log = LoggerFactory.getLogger(AuthenticationFailureHandlerService.class);

	@Autowired
	private LoginLogService loginLogService;

	@Autowired
	private SecurityService securityService;
	
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authentication)
			throws IOException, ServletException {

		log.error("ERROR: {}", authentication.getMessage(), authentication);

		String failureUrl = request.getParameter("failureUrl");

		// BadCredentialsException
		// LoginTypeNotMatchException
		// TokenValidationException
		int cnt = 0;
		if (authentication instanceof BadCredentialsException) {
			failureUrl = failureUrl + "&error=1";
			cnt++;
		}
		
		if (authentication instanceof LoginTypeNotMatchException) {
			failureUrl = failureUrl + "&error=2";
			cnt++;
		}
		
		if (authentication instanceof TokenValidationException) {
			failureUrl = failureUrl + "&error=3";
			cnt++;
		}

		if (authentication instanceof LockedException) {
			failureUrl = failureUrl + "&error=4";
			cnt++;
		}

		if (authentication instanceof CredentialsExpiredException) {
			failureUrl = failureUrl + "&error=5";
			cnt++;
		}

		// 로그인 실패시 위에 조건이 아닐때 에러메세지 보냄 2017-05-01 yulsun.yoo
		if (cnt == 0) {
			failureUrl = failureUrl + "&error=1";
		}
		
        if (ValidationUtils.isNull(failureUrl)) {
        	failureUrl = "/";
        }
        
        log.debug("Redirecting to failure Url: {}", failureUrl);

        String loginType = request.getParameter("op_login_type");
        String loginId = request.getParameter("op_username");

		securityService.updateLoginFailCount(loginType, loginId);

        if (LoginRequest.OPMANAGER.equals(loginType)) {
        	loginLogService.insertLoginLogByManager(request, false);
        }
        if (LoginRequest.SELLER.equals(loginType)) {
        	loginLogService.insertLoginLogBySeller(request, false);
		}
		if (LoginRequest.USER.equals(loginType)) {
			loginLogService.insertLoginLogByUser(request, false);
		}

        getRedirectStrategy().sendRedirect(request, response, failureUrl);
	}
}