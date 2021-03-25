package saleson.common.security;

import com.onlinepowers.framework.security.mapper.SecurityMapper;
import com.onlinepowers.framework.security.service.SecurityService;
import com.onlinepowers.framework.security.userdetails.OpUserDetails;
import com.onlinepowers.framework.security.userdetails.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import saleson.common.enumeration.AuthorityType;
import saleson.common.utils.EventViewUtils;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.shop.cart.CartMapper;
import saleson.shop.cart.CartService;
import saleson.shop.cart.support.CartParam;
import saleson.shop.coupon.CouponService;
import saleson.shop.eventcode.EventCodeService;
import saleson.shop.log.LogManageService;
import saleson.shop.log.LoginLogService;
import saleson.shop.order.OrderMapper;
import saleson.shop.shadowlogin.ShadowLoginLogService;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.userlevel.UserLevelMapper;
import saleson.shop.userlevel.domain.UserLevel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AuthenticationSuccessHandlerService extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandlerService.class);
	
	@Autowired SecurityService securityService;
	@Autowired SecurityMapper securityMapper;
	
	@Autowired
	UserService userService;

	@Autowired
	CartService cartService;
	
	@Autowired
	CouponService couponService; 
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	CartMapper cartMapper;
	
	@Autowired
	UserLevelMapper userLevelMapper;
	
	@Autowired
	LoginLogService loginLogService;
	
	@Autowired
	ShadowLoginLogService shadowLoginLogService;

	@Autowired
	SellerService sellerService;

	@Autowired
	LogManageService logManageService;

	@Autowired
	EventCodeService eventCodeService;

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		
		if (securityService.hasRole("ROLE_OPMANAGER")) {
			String target = request.getParameter("target");
			
			// 로그인 카운트 증가
			User user = securityService.getCurrentUser();

			securityMapper.updateManagerLoginCount(user);
			securityMapper.updateClearLoginFailCountForManager(user.getLoginId());

			loginLogService.insertLoginLogByManager(request, true);

			if (!(target == null || target.equals(""))) {
				getRedirectStrategy().sendRedirect(request, response, target);
	        } else {
	        	super.onAuthenticationSuccess(request, response, authentication);
	        }

			boolean todayCheck = logManageService.getTodayLoginCheck();

			if (!todayCheck) {
				logManageService.logManage();
			}

			// 중복세션 처리
			userService.deleteLoginSessionForManager(user.getUserId());
			userService.insertLoginSessionForManager(request.getSession(), user.getUserId());

			return;
		}

		if (securityService.hasRole("ROLE_SELLER")) {
			String target = request.getParameter("target");

			// 로그인 카운트 증가
			User user = securityService.getCurrentUser();

			securityMapper.updateSellerUserLoginCount(user);
			securityMapper.updateClearLoginFailCountForSellerUser(user.getLoginId());

			Object princial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (princial instanceof UserDetails) {
				long sellerId = 0;

				String prefixSeller = "ROLE_SELLER_";

				List<String> authorities = securityService.getAuthorities();

				for (String authority : authorities) {
					if (!authority.equals(AuthorityType.SELLER_MASTER.getCode())
							&& authority.startsWith(prefixSeller)) {
						sellerId = Long.parseLong(authority.replaceAll(prefixSeller, ""));
						break;
					}
				}

				if (sellerId > 0) {
					Seller seller = sellerService.getSellerById(sellerId);
					((OpUserDetails) princial).getUser().setObject(seller);
				}

			}

			loginLogService.insertLoginLogBySeller(request, true);

			if (!(target == null || target.equals(""))) {
				getRedirectStrategy().sendRedirect(request, response, target);
			} else {
				super.onAuthenticationSuccess(request, response, authentication);
			}

			return;
		}
		
		
		UserDetail userDetail = userService.getUserDetail(securityService.getCurrentUserId());

		// 회원 Level이 설정되어있는경우 해당 Level 정보를 조회해서 상품별 할인율을 구한다. 
		// 상품 상세, 장바구니, 주문을 재외한 페이지에서 등급별 할인을 Session으로 처리하기 위함
		if (userDetail.getLevelId() > 0) {
			UserLevel userLevel = userLevelMapper.getUserLevelById(userDetail.getLevelId());
			if (userLevel != null) {
				userDetail.setUserLevelDiscountRate(userLevel.getDiscountRate());
				userDetail.setUserLevelPointRate(userLevel.getPointRate());
			}
		}

		// 회원 정보에 추가 정보가 있는 경우 조회하여 설정해줌.
		Object princial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (princial instanceof UserDetails) {
			((OpUserDetails) princial).setUserDetail(userDetail);
		}
		
		String target = request.getParameter("target");
		
		// 로그인 카운트 증가
		User user = securityService.getCurrentUser();
		HttpSession session = (HttpSession) request.getSession();

		if (user != null && user.getLoginId() != null) {
			if (!user.isShadowLogin()){
				securityMapper.updateLoginCount(user);
				securityMapper.updateClearLoginFailCountForUser(user.getLoginId());
			} else {
				try {

					if (!user.getLoginId().equals(user.getShadowManagerId())) {

						User manager = securityMapper.getManagerByLoginId(user.getShadowManagerId());
						userDetail.setShadowLoginLogId(shadowLoginLogService.insertShadowLoginLog("user", user.getUserId(), manager.getUserId()));

						if (princial instanceof UserDetails) {
							((OpUserDetails) princial).setUserDetail(userDetail);
						}

					}

				} catch (Exception e) {
					log.warn("getShadowManagerId {}", e.getMessage(), e);
				}
			}

			// 비로그인시 담아놓은 장바구니 목록 회원ID 업데이트 - 바로 구매의 경우 결제금액이 변경되는 이슈가 생김

			cartService.updateUserIdBySessionId(user.getUserId(), session.getId());

		}

		loginLogService.insertLoginLogByUser(request, true);
		// 판매자 로그인 정보 삭제.
		// session.removeAttribute("SELLER");

		// 이벤트 코드 로그 userId 업데이트
		String uid = EventViewUtils.getUidCookieValue(request);
		eventCodeService.updateLogForUserId(uid, user.getUserId());

		if (!(target == null || target.equals(""))) {
			
			// 모바일에서 로그인시 바로 구매에 에러가 있음
        	if ("/m/order/step1".equals(target)) {

        		CartParam cartParam = new CartParam();
        		cartParam.setSessionId(session.getId());

        		if (user != null) {
					cartParam.setUserId(user.getUserId());
				}
				//orderMapper.deleteOrderItemTempByCartParam(cartParam);
        		
        		//orderMapper.updateOrderItemTempUserIdByCartParam(cartParam);
        		
        	}
			
			getRedirectStrategy().sendRedirect(request, response, target);
        } else {
        	super.onAuthenticationSuccess(request, response, authentication);
        }
	}
}

