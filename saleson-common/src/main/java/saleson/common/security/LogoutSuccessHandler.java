package saleson.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.onlinepowers.framework.util.ValidationUtils;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	private static final Logger log = LoggerFactory.getLogger(LogoutSuccessHandler.class);
	
	@Override  
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,  
            Authentication authentication) throws IOException, ServletException {  
  
		// 자동로그인 쿠키 삭제.
		Cookie cookie = new Cookie("OP_REMEMBER_ME_COOKIE", "");
		cookie.setHttpOnly(true);
	    cookie.setMaxAge(0);
	    cookie.setPath("/");

	    response.addCookie(cookie);
	    
	    
	    // 로그아웃 메세지
	    HttpSession session = request.getSession();
	    session.setAttribute("LOGOUT", "true");
	    
		setDefaultTargetUrl("/"); 
		
		String target = request.getParameter("target") == null ? "/" : request.getParameter("target");

		
		log.debug("Logout target : {}", target);
		if (!ValidationUtils.isNull(target) && !ValidationUtils.isEmpty(target) && !target.equals("/")) {
			setDefaultTargetUrl(target);  
		}
		
        super.onLogoutSuccess(request, response, authentication);         
    }  
}
