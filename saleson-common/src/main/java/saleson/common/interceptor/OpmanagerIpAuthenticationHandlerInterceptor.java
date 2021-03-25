package saleson.common.interceptor;

import com.onlinepowers.framework.security.service.SecurityService;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.web.opmanager.menu.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import saleson.shop.access.AccessService;
import saleson.shop.access.domain.Access;
import saleson.shop.access.support.AccessParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpmanagerIpAuthenticationHandlerInterceptor extends HandlerInterceptorAdapter {
protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	private SecurityService securityService;
	
	@Autowired
	private MenuService menuService;

	@Autowired
	private AccessService accessService;

	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();

		if(requestUri.indexOf("/opmanager") > -1) {

			AccessParam accessParam = new AccessParam();
			accessParam.setAccessType("1");
        	accessParam.setDisplayFlag("Y");
			List<Access> allowIps = accessService.getAllowIpList(accessParam);

			boolean isMatched = false;

			AntPathMatcher pathMatcher = new AntPathMatcher();

			for (Access access : allowIps) {

				if (pathMatcher.match(access.getRemoteAddr().trim(), saleson.common.utils.CommonUtils.getClientIp(request))) {
					isMatched = true;
					break;
				}

			}
			if (!isMatched) {
				log.warn("관리자 페이지 접근 제한 : {}", saleson.common.utils.CommonUtils.getClientIp(request));
				response.sendRedirect("/error/404");
				return false;
			}
        }
        return true;
    }

}
