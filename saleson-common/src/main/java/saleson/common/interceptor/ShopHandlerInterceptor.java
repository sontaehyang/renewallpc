package saleson.common.interceptor;

import com.onlinepowers.framework.common.ServiceType;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.ThreadContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import saleson.common.Const;
import saleson.common.context.ShopContext;
import saleson.common.utils.EventViewUtils;
import saleson.common.utils.PointUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.ConfigPg;
import saleson.shop.cart.CartService;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Group;
import saleson.shop.categories.domain.Team;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.group.GroupService;
import saleson.shop.group.support.GroupSearchParam;
import saleson.shop.item.ItemService;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.AvailablePoint;
import saleson.shop.seo.SeoService;
import saleson.shop.seo.domain.Seo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


public class ShopHandlerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(ShopHandlerInterceptor.class);
	private final boolean IS_INVITATION_ONLY = false; 			// 폐쇄몰인가? true : false;
	private String mobilePrefix;								// 모바일 prefix

	@Autowired
	private ConfigService configService;

	@Autowired
	private SeoService seoService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private PointService pointService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private PathMatcher antPathMatcher;

	@Autowired
	private ConfigPgService configPgService;

	@Autowired
	Environment environment;

	/**
	 * 쇼핑몰 요청인가? (Front, Mobile)
	 * @param request
	 * @param requestContext
	 * @return
	 */
	private boolean isShopRequest(HttpServletRequest request, RequestContext requestContext) {
		String requestUri = requestContext.getRequestUri();

		if (AjaxUtils.isAjaxRequest(request)
				// || request.getMethod().equals("POST")
				|| requestUri.startsWith("/opmanager")
				|| requestUri.startsWith("/seller")) {
			return false;
		}

		return true;
	}

	private boolean isSeoRequest(HttpServletRequest request) {
		String requestUri = request.getRequestURI();

		List<String> ignores = new ArrayList<>();

		addIgnoresPath(ignores,"/sitemap.xml");
		addIgnoresPath(ignores,"/seo/**");
		addIgnoresPath(ignores,"/share/**");

		PathMatcher antPathMatcher = new AntPathMatcher();

		for (String pattern : ignores) {
			if (antPathMatcher.match(pattern, requestUri)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * API 요청인가?
	 * @param request
	 * @return
	 */
	private boolean isApiRequest(HttpServletRequest request) {

		String requestUri = request.getRequestURI();

		List<String> ignores = new ArrayList<>();

		addIgnoresPath(ignores,"/api/**");

		PathMatcher antPathMatcher = new AntPathMatcher();

		for (String pattern : ignores) {
			if (antPathMatcher.match(pattern, requestUri)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 관리자에서 사용하는 Request 인가?
	 * @param request
	 * @return
	 */
	private boolean isPassFrontRequest(HttpServletRequest request) {

		String requestUri = request.getRequestURI();

		List<String> ignores = new ArrayList<>();

		String mobilePrefix = ShopUtils.getMobilePrefix();

		addIgnoresPath(ignores,mobilePrefix+"/ev/**");
		addIgnoresPath(ignores,"/ev/**");
		addIgnoresPath(ignores,mobilePrefix+"/event-log/**");
		addIgnoresPath(ignores,"/event-log/**");
		addIgnoresPath(ignores,"/naver/**");
		addIgnoresPath(ignores,"/smarteditor/**");
		addIgnoresPath(ignores,"/sitemap.xml");
		addIgnoresPath(ignores,"/seo/**");
		addIgnoresPath(ignores,"/share/**");

		PathMatcher antPathMatcher = new AntPathMatcher();

		for (String pattern : ignores) {
			if (antPathMatcher.match(pattern, requestUri)) {
				return true;
			}
		}

		return false;
	}

	private static void addIgnoresPath(List<String> ignores, String path) {
		ignores.add(path);
		ignores.add(ShopUtils.getMobilePrefix() + path);
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		mobilePrefix = ShopUtils.getMobilePrefix();
		if (CommonUtils.isResourceHandler(handler)) {
			return true;
		}


		RequestContext requestContext = (RequestContext) ThreadContext.get(RequestContext.REQUEST_NAME);
		String requestUri = requestContext.getRequestUri();
		String originRequestURI = requestContext.getRequest().getRequestURI();

		// 에러페이지 인가?
		if (isErrorPage(requestContext)) {
			return true;
		}

		// 리소스 요청인 경우 PASS
		if (isResourceRequest(requestUri)) {
			return true;
		}

		// 동기화 API 요청인경우 패스
		if (isApiRequest(request)) {

			passingReauestAfterProcess(request);

			return true;
		}

		// SEO 요청인경우 패스
		if (isSeoRequest(request)) {

			passingReauestAfterProcess(request);

			return true;
		}

		// 접속이 허용된 IP 인가? (PRODUCTION, LOCAL은 체크하지 않음)
		/*if (!isAllowdIp(requestContext)) {
			log.warn("접속제한 : {}", saleson.common.utils.CommonUtils.getClientIp(request));
			response.sendRedirect("/demo/your-ip");
			return false;
		}*/

		// 폐쇄몰인 경우 ==> 비로그인 상태인 경우 로그인 페이지로 이동 , 오픈 사이트는 무시.
		if (!isAccessAllowedByInvitationOnly(requestContext)) {
			log.debug("[폐쇄몰] 로그인 필요한 페이지에 접속하였음. ({}, {})", requestUri, originRequestURI);
			String redirectUri = ShopUtils.getMobilePrefixByPage() + "/users/login";
			response.sendRedirect(redirectUri);
			return false;
		}

		// 휴면 계정 안내페이지로 전환되어야 하나?
		if (shouldRedirectDormantPage(requestContext)) {
			response.sendRedirect(ShopUtils.getMobilePrefixByPage() + "/users/sleep-user");
			return false;
		}

		if (SecurityUtils.isUser() && !AjaxUtils.isAjaxRequest(request)) {
			String userChangePassowrdRedirectUri = ShopUtils.getMobilePrefixByPage() + "/users/change-password";

			// 회원의 패스워드 변경이 해당일자를 넘어셨을경우
			if (UserUtils.isUserLogin() && !UserUtils.isDormantUser() && !isCheckedUri(requestUri, originRequestURI, userChangePassowrdRedirectUri)) {

				User user = UserUtils.getUser();
				int passwordExpiredDateDiff = DateUtils.getDaysDiff(DateUtils.getToday(Const.DATE_FORMAT), user.getPasswordExpiredDate());
				if ("T".equals(user.getPasswordType()) || passwordExpiredDateDiff <= 0) {
					log.debug("회원 패스워드 변경기간 지남");
					String redirectUri = userChangePassowrdRedirectUri;
					response.sendRedirect(redirectUri);
					return false;
				}
			}
		}

		// ShopContext 설정.
		ShopContext shopContext = new ShopContext();


		// 1. 쇼핑몰 설정 정보.
		log.debug("[Cache] configService.getShopConfigCache");
		Config config = configService.getShopConfigCache(Config.SHOP_CONFIG_ID);

		// 1-2. 조회 정보 저장. - 장바구니에서 ShopConfig가 필요함.
		shopContext.setConfig(config);

		// 1-3. 모바일 디바이스 여부
		shopContext.setMobileDevice(DeviceUtils.isMobile(request));


		// 2. 회원 그룹 정보
		List<saleson.shop.group.domain.Group> userGroups = new ArrayList<>();
		GroupSearchParam groupParam = new GroupSearchParam();
		userGroups = groupService.getGroupList(groupParam);

		shopContext.setUserGroups(userGroups);


		// 3. Front에만 적용되는 설정.
		if (isShopRequest(request, requestContext)) {

			// www 없는 도메인 접속이 가능한 경우. www로 전환 처리 (세션문제 처리)
			String redirect = environment.getProperty("saleson.url.shoppingmall");
			String queryString = request.getQueryString();

			if (isApiView() && !isPassFrontRequest(request)) {

				String redirectManagerUrl = redirect+"/opmanager";

				if (UserUtils.isSellerLogin()) {
					redirectManagerUrl = redirect+"/seller";
				}

				log.debug("[Manager Url Redirect!!] {}", redirectManagerUrl);
				response.sendRedirect(redirectManagerUrl);
				return false;
			}

			if (ServiceType.PRODUCTION && redirect.indexOf("www.") > -1 && request.getServerName().indexOf("www.") == -1) {

				if (!StringUtils.isEmpty(queryString) && redirect.indexOf("?") == -1) {
					redirect += "?" + queryString;
				}

				log.debug("[Redirect!!] {}", redirect);
				response.sendRedirect(redirect);
				return false;
			}


			// CJH 2016.4.19 IE8에서 호환성 보기가 설정되는것을 막는다.
			try {
				if (!ShopUtils.isMobilePage()) {
					response.addHeader("X-UA-Compatible", "IE=edge");
				}
			} catch(Exception e) {
				log.error("ERROR: {}", e.getMessage(), e);
			}

			// 301 redirect
			String pettern = "(/[\\.0-9a-zA-Z_-]+)+/{1}$";

			if (Pattern.matches(pettern, requestUri)
					&& (request.getQueryString() == null || "".equals(request.getQueryString()))) {
				response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
				response.setHeader("Location", requestUri.substring(0, requestUri.length() -1));
				log.debug("301 Redirect");
				return false;
			}

			// 2. 카테고리 정보
			log.debug("[Cache] categoriesService.getCategoriesForFront");
			List<Team> categories = categoriesService.getCategoriesForFront();
			String alternateBaseUri = "";

			// PC / 모바일 접속 구분
			if (ShopUtils.isMobilePage(requestUri)) {
				HttpSession session = request.getSession();
				session.setAttribute("SITE_REFERENCE", DeviceUtils.MOBILE);

				// SEO canonical 출력 제외  uri
				String[] excludeUriArray = {
						mobilePrefix + "/mypage",
						mobilePrefix + "/mypage",
						mobilePrefix + "/item/create-review-complete"
				};

				// canonical 출력 링크
				// alternateBaseUri = requestUri.replace(mobilePrefix, "");		==>  /spot에 포함된 /m까지 replace되어 첫번째 mobilePrefix만 replace되게 수정 [2017-05-12 jeognah.choi]
				alternateBaseUri = requestUri.replaceFirst(mobilePrefix, "");
				if (requestUri.equals(mobilePrefix) || requestUri.equals(mobilePrefix + "/")) {
					alternateBaseUri = "/";
				}

				for (String excludeUri : excludeUriArray) {
					if (excludeUri.equals(requestUri)) {
						alternateBaseUri = "";
						break;
					}
				}
			} else {

				// 모바일로 pc링크 접속 시 무조건 mobilePrefix 붙여서 이동
				String redirectUri = mobilePrefix + requestUri;
				alternateBaseUri = requestUri;

				// 모바일 디바이스 체크
				if (ShopUtils.isMobile(request)
						&& !(requestUri.equals("/demo/your-ip")
						|| requestUri.equals("/demo/allow-ip")
						|| requestUri.startsWith("/common")
						|| requestUri.startsWith("/auth")
						|| requestUri.startsWith("/app")
						|| requestUri.startsWith("/users/authCheck")
						|| requestUri.startsWith("/sns-user"))) { // sns-user 로그인 인증 [2018-01-09] yulsun.yoo
					if (!StringUtils.isEmpty(queryString) && redirectUri.indexOf("?") == -1) {
						redirectUri += "?" + queryString;
					}

					log.debug("[Redirect Mobile] {}", redirectUri);
					response.sendRedirect(redirectUri);
					return false;
				}

			}

			// 4. SEO 설정 정보.
			Seo seo = new Seo();
			seo.setTitle(config.getSeoTitle());
			seo.setKeywords(config.getSeoKeywords());
			seo.setDescription(config.getSeoDescription());
			seo.setHeaderContents1(config.getSeoHeaderContents1());
			seo.setThemawordTitle(config.getSeoThemawordTitle());
			seo.setThemawordDescription(config.getSeoThemawordDescription());

			// fixedMetaData (URL에 일치하는 SEO 정보가 등록되었는가?)
			List<Seo> seoList = seoService.getSeoListAll();

			for (Seo seo2 : seoList) {
				if (seo2.getSeoUrl().equalsIgnoreCase(requestUri)) {
					seo.setTitle(seo2.getTitle());
					seo.setKeywords(seo2.getKeywords());
					seo.setDescription(seo2.getDescription());
					seo.setHeaderContents1(seo2.getHeaderContents1());
					seo.setThemawordTitle(seo2.getThemawordTitle());
					seo.setThemawordDescription(seo2.getThemawordDescription());
					seo.setIndexFlag(seo2.getIndexFlag());
					break;
				}
			}


			// 5. 장바구니 정보 --> Ajax로 처리 함 (/common/cart)
        	/*
        	HttpSession session = (HttpSession) request.getSession();
        	CartParam cartParam = new CartParam();
        	cartParam.setUserId(UserUtils.getUserId());
        	cartParam.setSessionId(session.getId());
        	List<OrderItem> cartList = cartService.getCartList(cartParam);
        	*/

			// 6. 오늘 본 상품 --> Ajax로 처리 함 (/common/today-items)
        	/*
        	Cookie[] cookies = request.getCookies();
    		List<Item> todayItems = new ArrayList<>();
    		ItemParam itemParam = new ItemParam();
    		Pagination pagination = Pagination.getInstance(6, 6);
    		itemParam.setPagination(pagination);

    		try{
	    		if (cookies != null && cookies.length > 0) {
	    			for (int i = 0; i < cookies.length; i++) {
	    				if (cookies[i].getName().equals("TODAY_ITEMS")) {
	    					String todayItemsInfo = cookies[i].getValue();

	    					if (!"".equals(todayItemsInfo)) {
		    					itemParam.setTodayItemIds(todayItemsInfo.toUpperCase().replaceAll("SELECT", "").replaceAll("DELETE", "").replaceAll("UPDATE", "").replaceAll("INSERT", ""));
		    					itemParam.setDisplayFlag("Y");

		    					//todayItems = itemService.getItemList(itemParam);
		    					todayItems = itemService.getTodayItemList(itemParam);
	    					}
	    				}
	    			}
	    		}
    		} catch(Exception e){

    			Cookie cookie = new Cookie("TODAY_ITEMS", "");
				cookie.setHttpOnly(true);
    		    cookie.setMaxAge(60*60*24);				// 쿠키 유지 기간 - 1일
    		    cookie.setPath("/");					// 모든 경로에서 접근 가능하도록
    		    response.addCookie(cookie);				// 쿠키저장


    		}
    		*/

			// 5. 모바일 layer
			boolean isMobileLayer = false;
			if (ShopUtils.isMobilePage()) {
				isMobileLayer = true;
			}
			shopContext.setMobileLayer(isMobileLayer);

			// 6. NaverPay 설정
			boolean isNaverPay = "Y".equals(config.getNaverPayFlag());

			if (isNaverPay) {
				ConfigPg configPg = configPgService.getConfigPg();
				isNaverPay = configPg != null && configPg.isUseNpayOrder();

				// 7. 네이버 페이설정.
				shopContext.setNaverPay(isNaverPay);
				if (isNaverPay) {
					shopContext.setNaverWcslogKey(configPg.getNpayLogKey());
				}
			}

			// 포인트 조회.
			if (SecurityUtils.hasRole("ROLE_USER")) {
				AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(UserUtils.getUserId(), PointUtils.DEFAULT_POINT_CODE);
				shopContext.setPoint(avilablePoint.getAvailablePoint());
			}

			List<Group> shopCategoryGroups = new ArrayList<>();
			for(Team team : categories) {
				if (Config.SHOP_CATEGORY_GROUP_KEY.equals(team.getUrl())) {
					shopCategoryGroups.addAll(team.getGroups());
				}
			}

			// 8. 조회 정보 저장.
			shopContext.setShopCategoryGroups(shopCategoryGroups);
			shopContext.setAlternateBaseUri(alternateBaseUri);
			shopContext.setGnbCategories(categories);
			shopContext.setUserGroups(userGroups);
			shopContext.setSeo(seo);

			// 이벤트 코드 추적용 쿠키 생성
			if (!isApiView()) {
				String eventUid = EventViewUtils.getUidCookieValue(request);
				if (StringUtils.isEmpty(eventUid)) {
					EventViewUtils.setUidCookie(response, EventViewUtils.getUid());
				}
			}
		}

		// Session Timeout 설정
		if (UserUtils.isManagerLogin() || UserUtils.isSellerLogin()) {
			shopContext.setManagerTimeout(UserUtils.getUser().getSessionTimeout());
		} else if (UserUtils.isUserLogin()) {
			shopContext.setUserTimeout(UserUtils.getUser().getSessionTimeout());
		}

		// 7-2. 조회 정보 저장.
		shopContext.setConfig(config);

		ThreadContext.remove(ShopContext.REQUEST_NAME);
		ThreadContext.put(ShopContext.REQUEST_NAME, shopContext);

		return true;
	}

	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		RequestContext requestContext = (RequestContext) ThreadContext.get(RequestContext.REQUEST_NAME);

		if (!CommonUtils.isResourceHandler(handler) && requestContext != null && !isResourceRequest(requestContext.getRequestUri())) {
			try{

				// 에러페이지 대응.
				boolean isPageNotFound = false;
				if (request.getQueryString() != null && request.getQueryString().indexOf("PHPSESSID") > -1) {
					isPageNotFound = true;

				}
				/*
				if (request.getQueryString() != null && request.getQueryString().indexOf("PHPSESSID") > -1) {
					isPageNotFound = true;

				} else if (RequestContextUtils.getRequestUri().indexOf("//") > -1) {
					isPageNotFound = true;

				} else if (RequestContextUtils.getFullRequestUri().indexOf("/?") > -1 && RequestContextUtils.getFullRequestUri().indexOf("/?SITE_REFERENCE") == -1) {
					isPageNotFound = true;

				}
				*/

				// @ query string에 PHPSESSION 이 있으면 404
				if (isPageNotFound) {
					//request.getRequestDispatcher("/app/error").forward(request, response);
					response.setStatus(HttpStatus.NOT_FOUND.value());
					// 모바일 디바이스 체크

					if (ShopUtils.isMobile(request)) {
						RequestContextUtils.setTemplate("mobile");
					}

					modelAndView.setViewName(ViewUtils.getView("/error/404"));

				}
				if (!(AjaxUtils.isAjaxRequest(request)
						|| requestContext.getRequestUri().startsWith("/api")
						|| requestContext.getRequestUri().startsWith("/seo")
						|| requestContext.getRequestUri().startsWith("/share")
						|| requestContext.getRequestUri().startsWith("/sitemap.xml"))) {
					modelAndView.addObject("shopContext", ThreadContext.get(ShopContext.REQUEST_NAME));
				}
			} catch (Exception e) {
				log.warn("ShopHandlerInterceptor postHandle Error!!", e);
			}
		}
	}

	/**
	 * 접속이 허용된 IP 인가? (PRODUCTION, LOCAL은 체크하지 않음)
	 * @param requestContext
	 * @return
	 */
	private boolean isAllowdIp(RequestContext requestContext) {

		HttpServletRequest request = requestContext.getRequest();
		String requestUri = requestContext.getRequestUri();

		if (requestUri.equals("/demo/reload") || requestUri.equals("/demo/your-ip") || requestUri.equals("/demo/allow-ip")) {
			return true;
		}

		if (ServiceType.PRODUCTION || ServiceType.LOCAL) {
			return true;
		}

		List<Code> allowIps = CodeUtils.getCodeList("ALLOW_IP", Locale.KOREAN);

		boolean isMatched = false;
		for (Code code : allowIps) {
			if (code.getId().equals(saleson.common.utils.CommonUtils.getClientIp(request))) {
				isMatched = true;
				break;
			} else if (saleson.common.utils.CommonUtils.getClientIp(request).startsWith(code.getId())) {
				isMatched = true;
				break;
			}
		}

		return isMatched;
	}

	/**
	 *
	 * @param requestContext
	 * @return
	 */
	private boolean isErrorPage(RequestContext requestContext) {
		String requestUri = requestContext.getRequestUri();
		String originRequestURI = requestContext.getRequest().getRequestURI();

		if (originRequestURI.startsWith("/error")) {
			if (!ServiceType.LOCAL) {
				if (originRequestURI.indexOf("404") > -1) {
					log.debug("[ERROR 404] {}", requestUri);

				} else if (originRequestURI.indexOf("500") > -1) {
					log.debug("[ERROR 500] {}", requestUri);

				} else {
					log.debug("[ERROR] {}", requestUri);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Resource 요청인가?
	 * @param requestUri
	 * @return
	 */
	private boolean isResourceRequest(String requestUri) {
		String[] resourceFolders = StringUtils.delimitedListToStringArray(environment.getProperty("resource.request.uris"), ",");
		for (String resource : resourceFolders) {
			if (requestUri.startsWith(resource)) {
				return true;
			}
		}

		if (requestUri.indexOf("/thumbnail") != -1 || requestUri.indexOf("/healthcheck") != -1) {
			return true;
		}

		// 이미지는 Pass
		String[] ignores = new String[] {".jpg", ".gif", ".jpeg", ".png", ".ico"};
		for (String value : ignores) {
			if (requestUri.toLowerCase().indexOf(value) > -1) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 휴면 계정 안내페이지로 전환되어야 하는가?
	 * 휴면회원 +
	 * @param requestContext
	 * @return
	 */
	private boolean shouldRedirectDormantPage(RequestContext requestContext) {
		if (!UserUtils.isDormantUser()) {
			return false;
		}

		// 휴면계정 안내 페이지 전환 예외 (WEB 기준 URI 등록)
		String[] ignores = new String[] {
				"/common/**",
				"/users/sleep-user",
				"/users/wakeup-user",
				"/users/login",
				mobilePrefix + "/users/sleep-user",
				mobilePrefix + "/users/wakeup-user",
				mobilePrefix + "/users/login"
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


	/**
	 * 폐쇄몰에 접근 권한이 있는가?
	 * @param requestContext
	 * @return
	 */
	private boolean isAccessAllowedByInvitationOnly(RequestContext requestContext) {
		if (!IS_INVITATION_ONLY || UserUtils.isUserLogin()) {		// 폐쇄몰이 아니거나 로그인 된 경우는 허용!
			return true;
		}

		// 반드시 로그인이 필요한 URI
		String[] requires = new String[] {

		};


		// 폐쇄몰 접속 예외 (WEB 기준 URI 등록)
		String[] ignores = new String[] {
				"/opmanager",
				"/seller",
				"/error",
				"/sso",
				"/common/**",
				"/smarteditor/**",
				"/auth/**",
				"/delivery",
				"/ban-word/list-all",
				"/categories/options",
				"/order/ini-vacct",
				"/order/ini-noti",
				"/order/change-order-status",
				"/users/**",
				"/m/users/**",
				"/products/preview", //관리자 페이지에서 상품 프리뷰시 접속되도록 추가[2017-02-17] minae.yun
				"/island", //'제주/도서산간지역 보기' 팝업 [2017-02-21] minae.yun
				"/m/halla/terms", // 모바일 회원가입 이용약관 페이지 [2017-03-06] yulsun.yoo
				"/m/halla/policy" // 모바일 회원가입 개인정보 페이지 [2017-03-06] yulsun.yoo
				/*
				"/users/login",
				"/users/find-user",
				"/users/find-id",
				"/users/find-password",
				"/users/confirm",
				"/users/agreement",
				"/users/authCheck",
				"/users/entryForm",
				"/users/join",
				"/users/user-availability-check-join",
				"/users/check-account-join"*/
		};
		String requestUri = requestContext.getRequestUri();
		String originRequestURI = requestContext.getRequest().getRequestURI();

		//기획전url예외 처리[2017-02-17]minae.yun
		if (requestUri.indexOf("/pages") > -1 && UserUtils.isManagerLogin()) {
			return true;
		}

		//카테고리 서브페이지 예외 처리[2017-03-17] jeongah.choi
		if (requestUri.indexOf("/categories/index") > -1 && UserUtils.isManagerLogin()) {
			return true;
		}

		//'카드혜택' 팝업 url 예외 처리[2017-02-17]minae.yun
		if (requestUri.indexOf("/item/cardBenefits-popup") > -1 && UserUtils.isManagerLogin()) {
			return true;
		}

		for (String pattern : ignores) {
			if (requestUri.startsWith(pattern)) {
				return true;
			}
			if (requestUri.startsWith(mobilePrefix + pattern)) {		// 모바일 주소 체크.
				return true;
			}
			if (originRequestURI.startsWith(pattern)) {
				return true;
			}
			if (originRequestURI.startsWith(mobilePrefix + pattern)) {		// 모바일 주소 체크.
				return true;
			}
			if (antPathMatcher.match(pattern, requestUri)) {
				return true;
			}
			if (antPathMatcher.match(pattern, originRequestURI)) {
				return true;
			}
		}

		return false;
	}

	private boolean isCheckedUri (String requestUri , String originRequestURI, String uri) {

		if (antPathMatcher.match(uri, requestUri)
				|| antPathMatcher.match(uri, originRequestURI)) {
			return true;
		}
		return false;
	}

	private void passingReauestAfterProcess(HttpServletRequest request) {
		// ShopContext 설정.
		ShopContext shopContext = new ShopContext();

		// 1. 쇼핑몰 설정 정보.
		log.debug("[Cache] configService.getShopConfigCache");
		Config config = configService.getShopConfigCache(Config.SHOP_CONFIG_ID);

		// 1-2. 조회 정보 저장. - 장바구니에서 ShopConfig가 필요함.
		shopContext.setConfig(config);

		// 1-3. 모바일 디바이스 여부
		shopContext.setMobileDevice(DeviceUtils.isMobile(request));

		shopContext.setConfig(config);

		// 2. 회원 그룹 정보
		List<saleson.shop.group.domain.Group> userGroups = new ArrayList<>();
		if (UserUtils.isUserLogin()) {
			GroupSearchParam groupParam = new GroupSearchParam();
			userGroups = groupService.getGroupList(groupParam);
		}

		shopContext.setUserGroups(userGroups);

		ThreadContext.remove(ShopContext.REQUEST_NAME);
		ThreadContext.put(ShopContext.REQUEST_NAME, shopContext);
	}

	private boolean isApiView() {
		return "api".equals(environment.getProperty("saleson.view.type"));
	}
}