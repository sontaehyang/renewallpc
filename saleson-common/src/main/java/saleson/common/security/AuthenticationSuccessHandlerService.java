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
			
			// ????????? ????????? ??????
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

			// ???????????? ??????
			userService.deleteLoginSessionForManager(user.getUserId());
			userService.insertLoginSessionForManager(request.getSession(), user.getUserId());

			return;
		}

		if (securityService.hasRole("ROLE_SELLER")) {
			String target = request.getParameter("target");

			// ????????? ????????? ??????
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

		// ?????? Level??? ???????????????????????? ?????? Level ????????? ???????????? ????????? ???????????? ?????????. 
		// ?????? ??????, ????????????, ????????? ????????? ??????????????? ????????? ????????? Session?????? ???????????? ??????
		if (userDetail.getLevelId() > 0) {
			UserLevel userLevel = userLevelMapper.getUserLevelById(userDetail.getLevelId());
			if (userLevel != null) {
				userDetail.setUserLevelDiscountRate(userLevel.getDiscountRate());
				userDetail.setUserLevelPointRate(userLevel.getPointRate());
			}
		}

		// ?????? ????????? ?????? ????????? ?????? ?????? ???????????? ????????????.
		Object princial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (princial instanceof UserDetails) {
			((OpUserDetails) princial).setUserDetail(userDetail);
		}
		
		String target = request.getParameter("target");
		
		// ????????? ????????? ??????
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

			// ??????????????? ???????????? ???????????? ?????? ??????ID ???????????? - ?????? ????????? ?????? ??????????????? ???????????? ????????? ??????

			cartService.updateUserIdBySessionId(user.getUserId(), session.getId());

		}

		loginLogService.insertLoginLogByUser(request, true);
		// ????????? ????????? ?????? ??????.
		// session.removeAttribute("SELLER");

		// ????????? ?????? ?????? userId ????????????
		String uid = EventViewUtils.getUidCookieValue(request);
		eventCodeService.updateLogForUserId(uid, user.getUserId());

		if (!(target == null || target.equals(""))) {
			
			// ??????????????? ???????????? ?????? ????????? ????????? ??????
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

