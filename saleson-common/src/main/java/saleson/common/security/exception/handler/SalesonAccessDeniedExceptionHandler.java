package saleson.common.security.exception.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;

import com.onlinepowers.framework.security.exception.handler.AbstractOpAccessDeniedExceptionHandler;

public class SalesonAccessDeniedExceptionHandler extends AbstractOpAccessDeniedExceptionHandler {
	private static Logger log = LoggerFactory.getLogger(SalesonAccessDeniedExceptionHandler.class);
	
	/**
	 * OpAccessDeniedException 핸들링.
	 * @param request
	 * @param response
	 * @param validRoleName
	 * @return
	 * @throws IOException
	 */
	protected ModelAndView handleByRole(HttpServletRequest request, HttpServletResponse response, String validRoleName) throws IOException {
		if (validRoleName.equals("ROLE_ANONYMOUS")) {
			log.info("스프링 시큐리티 OpAccessDeniedException 핸들 : 403 Forbidden 처리");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "페이지 접속 권한이 없습니다.");
            return null;
            
		} else if (validRoleName.equals("ROLE_USER")) {
			String requestUri = request.getRequestURI();
			PathMatcher antPathMatcher = new AntPathMatcher();

			String mobilePrefix = "/m/**";

			if (antPathMatcher.match(mobilePrefix,requestUri)) {
				//setRedirectAttributeMessage(request, "로그인 후 이용이 가능합니다.");
				return new ModelAndView("redirect:/m/users/login?target=" + getTargetUri());
			} else {
				//setRedirectAttributeMessage(request, "로그인 후 이용이 가능합니다.");
				return new ModelAndView("redirect:/users/login?target=" + getTargetUri());
			}
			
		} else {
			//setRedirectAttributeMessage(request, "로그인 후 이용이 가능합니다.");
			return new ModelAndView("redirect:/users/login?target=" + getTargetUri());
			
		}
	}
}
