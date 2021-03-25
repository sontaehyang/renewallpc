package saleson.common.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import saleson.common.context.SellerContext;
import saleson.common.utils.UserUtils;
import saleson.seller.menu.MenuService;
import saleson.seller.menu.SellerMenu;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.service.SecurityService;
import com.onlinepowers.framework.util.AjaxUtils;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.util.ThreadContextUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.web.opmanager.menu.domain.Menu;



public class SellerHandlerInterceptor extends HandlerInterceptorAdapter {
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityService securityService;

	@Autowired
	private MenuService sellerMenuService;

	@Autowired
	private PathMatcher antPathMatcher;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (CommonUtils.isResourceHandler(handler)) {
			return true;
		}


		String requestUri = RequestContextUtils.getRequestUri();

		if (requestUri.startsWith("/seller/login")
			|| requestUri.startsWith("/seller/logout")) {
			return true;
		}

		if (!isLogin(request)) {
			//String target = RequestContextUtils.getR
			response.sendRedirect(request.getContextPath() + "/seller/login?target=" + RequestContextUtils.getRequestUri());
			return false;
		};


		return true;
	}

	public void postHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
		throws Exception {
		if (!CommonUtils.isResourceHandler(handler)
			&& isLogin(request)) {


			SellerContext sellerContext = new SellerContext(request);

			// 판매관리자 정보
            sellerContext.setSellerUserLogin(UserUtils.isSellerLogin());
            sellerContext.setSellerMasterUserLogin(UserUtils.isSellerMasterUser());
            sellerContext.setSellerUser(UserUtils.getUser());

            // 권한이 있는가?
			RequestContext requestContext = ThreadContextUtils.getRequestContext();
			List<String> menuCodes = CommonUtils.getMenuCode(requestContext.getRequestUri());

			HashMap<String, Object> map = new HashMap<>();

			map.put("authority", "ROLE_SUPERVISOR");
			map.put("menuCodes", menuCodes);
			map.put("userId", sellerContext.getSeller().getSellerId());

			// 메뉴 코드
			log.debug("[cache] menuService.getMenuCode");
			String menuCode = sellerMenuService.getMenuCode(map);


			if (ValidationUtils.isNull(menuCode) &&
				!(requestContext.getRequestUri().equals("/seller") || requestContext.getRequestUri().equals("/seller/"))) {

				if (isLoadMenuPage(requestContext)) {
					log.info("판매관리자 메뉴 접근 권한이 없습니다. ({})", requestContext.getRequestUri());
					throw new UserException("판매관리자 메뉴 접근 권한이 없습니다.", "/seller/");
				}
			}

			if (request.getMethod().equals("GET") || "POST".equals(request.getMethod())) {

				Menu menu = new Menu();
				menu.setCacheKey(Long.toString(sellerContext.getSeller().getSellerId()));
				menu.setUserId(sellerContext.getSeller().getSellerId());
				menu.setMenuCode(menuCode);



				log.debug("[cache] sellerMenuService.getFirstMenuList");
				List<Menu> firstMenuList = sellerMenuService.getFirstMenuList(menu);

				log.debug("[cache] sellerMenuService.getSecondAndThirdMenuList");
				List<Menu> secondAndThirdMenuList = sellerMenuService.getSecondAndThirdMenuList(menu);

				int firstMenuId = 0;

				int i = 0;
				for (Menu menu2 : secondAndThirdMenuList) {
					if (i == 0){
						firstMenuId = menu2.getMenuParentId();
					}
					i++;
				}


				SellerMenu sellerMenu = new SellerMenu();
				sellerMenu.setFirstMenuId(firstMenuId);
				sellerMenu.setMenuCode(menuCode);

				sellerMenu.setFirstMenuList(firstMenuList);
				sellerMenu.setSecondAndThirdMenuList(secondAndThirdMenuList);

				sellerContext.setSellerMenu(sellerMenu);
			}

			HttpSession session = request.getSession();

			if (session.getAttribute("SHADOW_SELLER") != null){
				sellerContext.setShadowlogin(true);
			}

			if (!AjaxUtils.isAjaxRequest(request)) {
				modelAndView.addObject("sellerContext", sellerContext);
			}
		}
	}


	private boolean isLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (UserUtils.isSellerLogin()) {
			return true;
		}

		if (session.getAttribute("SELLER") == null && session.getAttribute("SHADOW_SELLER") == null) {
			return false;
		}

		return true;
	}

	/**
	 * 메뉴 호출이 필요 없는 페이지인가?
	 * @param requestContext
	 * @return
	 */
	private boolean isLoadMenuPage(RequestContext requestContext) {
		// 휴면계정 안내 페이지 전환 예외 (WEB 기준 URI 등록)
		String[] ignores = new String[] {
				"/seller/index",
				"/seller/login",
				"/seller/login-lock",
				"/seller/login-change-password"
		};

		String requestUri = requestContext.getRequestUri();
		String originRequestURI = requestContext.getRequest().getRequestURI();

		for (String pattern : ignores) {
			if (antPathMatcher.match(pattern, requestUri)
					|| antPathMatcher.match(pattern, originRequestURI)) {
				return false;
			}
		}

		return true;
	}
}
