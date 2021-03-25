package saleson.common.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinepowers.framework.common.ServiceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.service.SecurityService;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.util.ThreadContextUtils;
import com.onlinepowers.framework.web.opmanager.menu.MenuService;
import com.onlinepowers.framework.web.opmanager.menu.domain.Menu;
import com.onlinepowers.framework.web.opmanager.menu.domain.OpmanagerMenu;

import saleson.common.utils.UserUtils;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.ManagerLogin;


public class OpmanagerHandlerInterceptor extends HandlerInterceptorAdapter {
    protected Logger log = LoggerFactory.getLogger(getClass());
    private String ROLE_SUPERVISOR = "ROLE_SUPERVISOR";
    private String BASE_URI = "/opmanager";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (CommonUtils.isResourceHandler(handler) || !isManagerPage(request)) {
            return true;
        }

        boolean isLogin = UserUtils.isManagerLogin();
        String requestUri = request.getRequestURI();
        String forwardRequestUri = CommonUtils.getRequestUri(request);

        if (!isLogin) {
            log.debug("관리자페이지({})는 로그인 후 접근 가능. 로그인페이지로 이동", forwardRequestUri);

            String target = forwardRequestUri;
            if (request.getQueryString() != null) {
                target = forwardRequestUri + "?" + request.getQueryString();
            }

            response.sendRedirect(request.getContextPath() + "/opmanager/login?target=" + target);
            return false;
        }


        if (!securityService.hasRole("ROLE_OPMANAGER")) {
            log.debug("관리자페이지({}) 접근권한(ROLE_OPMANAGER)이 없음. 로그인페이지로 이동", forwardRequestUri);
            response.sendRedirect("/opmanager/login?error=2");
            return false;
        }

        // 중복세션 처리 세션정보가 DB에 없으면 로그아웃 처리
        List<ManagerLogin> loginSessionList = userService.getLoginSessionForManagerByUserId(UserUtils.getManagerId());
        String sessionId = request.getSession().getId();

        long loginSessionCount = loginSessionList.stream()
                .filter(m -> sessionId.equals(m.getSessionId()))
                .count();

        if (loginSessionCount == 0) {

            if (!ServiceType.LOCAL && !"saleson".equals(UserUtils.getLoginId())) {
                log.debug("중복 사용자 체크로 인한 로그아웃");
                response.sendRedirect("/op_security_logout?target=/opmanager/login-disconnect");
                return false;
            }
        }

        // 권한이 있는가?
        RequestContext requestContext = ThreadContextUtils.getRequestContext();
        List<String> menuCodes = CommonUtils.getMenuCode(requestContext.getRequestUri());

        HashMap<String, Object> map = new HashMap<>();
        if (SecurityUtils.isSupervisor()) {
            map.put("authority", ROLE_SUPERVISOR);
        }
        map.put("menuCodes", menuCodes);
        map.put("userId", SecurityUtils.getCurrentUserId());

        // 메뉴 코드
        String menuCode = menuService.getMenuCode(map);

        if (menuCode == null && !isMainPage(requestUri)) {
            log.info("관리자 메뉴 접근 권한이 없습니다. ({})", requestContext.getRequestUri());
            throw new UserException("관리자 메뉴 접근 권한이 없습니다.", "/opmanager/");
        }

        if ("GET".equals(request.getMethod()) || "POST".equals(request.getMethod())) {

            Menu menu = new Menu();
            menu.setCacheKey(Long.toString(SecurityUtils.getCurrentUserId()));
            menu.setUserId(SecurityUtils.getCurrentUserId());
            menu.setMenuCode(menuCode);

            if (securityService.hasRole(ROLE_SUPERVISOR)) {
                menu.setAuthority(ROLE_SUPERVISOR);
            }

            List<Menu> firstMenuList = menuService.getFirstMenuList(menu);
            List<Menu> secondAndThirdMenuList = menuService.getSecondAndThirdMenuList(menu);

            int firstMenuId = 0;
            if (secondAndThirdMenuList != null && !secondAndThirdMenuList.isEmpty()) {
                firstMenuId = secondAndThirdMenuList.get(0).getMenuParentId();
            }

            OpmanagerMenu opmanagerMenu = new OpmanagerMenu();
            opmanagerMenu.setFirstMenuId(firstMenuId);
            opmanagerMenu.setMenuCode(menuCode);
            opmanagerMenu.setFirstMenuList(firstMenuList);
            opmanagerMenu.setSecondAndThirdMenuList(secondAndThirdMenuList);

            requestContext.setOpmanagerMenu(opmanagerMenu);
            ThreadContextUtils.setRequestContext(requestContext);
        }
        return true;
    }

    /**
     * 관리자페이지 접속인가?
     * @param request
     * @return
     */
    private boolean isManagerPage(HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        if (requestUri == null) {
            return false;
        }

        if (!requestUri.startsWith(BASE_URI)) {
            return false;
        }

        if (requestUri.indexOf(BASE_URI + "/login") > -1 || requestUri.indexOf(BASE_URI + "/accessdenied") > -1) {
            return false;
        }

        return true;
    }

    /**
     * 관리자 메인 페이지 인가?
     * @param requestUri
     * @return
     */
    private boolean isMainPage(String requestUri) {
        String[] uriPatterns = new String[] {
            BASE_URI,
			BASE_URI + "/",
			BASE_URI + "/index",
			BASE_URI + "/reload-cache",
        };

        for (String uri : uriPatterns) {
            if (uri.equals(requestUri)) {
                return true;
            }
        }
        return false;
    }
}
