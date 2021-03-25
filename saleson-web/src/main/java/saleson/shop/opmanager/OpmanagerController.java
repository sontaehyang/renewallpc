/*
 * Copyright(c) 2009-2011 Onlinepowers Development Team
 * http://www.onlinepowers.com
 * 
 * @author skc
 * @since 2011. 5. 5.
 */
package saleson.shop.opmanager;


import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.repository.support.EarlyLoadingCodeInfoRepository;
import com.onlinepowers.framework.repository.support.EarlyLoadingMessageInfoRepository;
import com.onlinepowers.framework.repository.support.EarlyLoadingRepositoryEvent;
import com.onlinepowers.framework.security.authentication.filter.IdPasswordAuthenticationFilter;
import com.onlinepowers.framework.security.exception.handler.OpAccessDeniedHandlerImpl;
import com.onlinepowers.framework.security.service.SecurityService;
import com.onlinepowers.framework.security.token.TokenService;
import com.onlinepowers.framework.security.token.domain.Token;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.security.userdetails.UserRole;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.UserUtils;
import saleson.shop.item.ItemService;
import saleson.shop.item.support.ItemParam;
import saleson.shop.log.ChangeLogService;
import saleson.shop.notice.NoticeService;
import saleson.shop.notice.support.NoticeParam;
import saleson.shop.order.OrderService;
import saleson.shop.qna.QnaService;
import saleson.shop.qna.domain.Qna;
import saleson.shop.qna.support.QnaParam;
import saleson.shop.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestProperty(title="관리자페이지", layout="base")
public class OpmanagerController {
	private static final Logger log = LoggerFactory.getLogger(OpmanagerController.class);

	
	@Autowired
	QnaService qnaService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	NoticeService noticeService;

	@Autowired
	UserService userService;

	@Autowired
	SecurityService securityService;

	@Autowired
	@Qualifier("smsTokenService")
	TokenService smsTokenService;


	@Autowired
	private EarlyLoadingCodeInfoRepository codeInfoRepository;

	@Autowired
	private EarlyLoadingMessageInfoRepository messageInfoRepository;


	@Autowired
	private ChangeLogService changeLogService;

	@GetMapping({"/opmanager", "/opmanager/", "/opmanager/index"})
	@RequestProperty(title="메인페이지", layout="default")
	public String index(RequestContext requestContext, Model model) {
		//model.addAttribute("main", "main");
		
		if (!SecurityUtils.hasRole("ROLE_OPMANAGER")) {
			return ViewUtils.redirect("/opmanager/login");
		}
		
		User user = UserUtils.getUser();
		
		for (UserRole userRole : user.getUserRoles()) {
			if (userRole.getAuthority().equals("ROLE_ADMIN_MENU3") || userRole.getAuthority().equals("ROLE_SUPERVISOR")) {
				//return ViewUtils.redirect("/opmanager/order/list/1");
			}
		}
		
		QnaParam individualQnaParam = new QnaParam(Qna.QNA_GROUP_TYPE_INDIVIDUAL);
		individualQnaParam.setLimit(5);
		individualQnaParam.setAnswerCount(2);
		
		QnaParam itemQnaParam = new QnaParam(Qna.QNA_GROUP_TYPE_ITEM);
		itemQnaParam.setLimit(5);
		itemQnaParam.setAnswerCount(2);
		
		ItemParam itemParam = new ItemParam();
		itemParam.setLimit(5);
		itemParam.setReviewDisplayFlag("N");
		
		NoticeParam noticeParam = new NoticeParam();
		noticeParam.setLimit(1);
		
		// 이상우 [2017-05-15 추가] 배송,교환,반품 지연 설정일 - 추후 shopConfig 에서 가져오는 방향으로
		// 지연일 설정
		HashMap<String, Integer> map = new HashMap<>();
		map.put("shippingDelay", 1);
		map.put("exchangeDelay", 3);
		map.put("returnDelay", 3);
		
		model.addAttribute("delayDays", map);
		model.addAttribute("noticeList", noticeService.getNoticeList(noticeParam));
		model.addAttribute("itemCountList", itemService.getItemCountForMain(0));
		model.addAttribute("oneByOneCount", qnaService.getQnaListCountByParam(individualQnaParam));
		model.addAttribute("oneByOneList", qnaService.getQnaListByParam(individualQnaParam));
		model.addAttribute("qnaCount", qnaService.getQnaListCountByParam(itemQnaParam));
		model.addAttribute("qnaList", qnaService.getQnaListByParam(itemQnaParam));
		model.addAttribute("reviewCount", itemService.getItemReviewCountByParam(itemParam));
		model.addAttribute("reviewList", itemService.getItemReviewListByParam(itemParam));
		model.addAttribute("main", "main");
		
		return ViewUtils.getManagerView("/main/index");
		//return ViewUtils.redirect(requestContext.getOpmanagerMenu().getFirstMenuList().get(0).getMenuUrl());	
	}
	@GetMapping("/opmanager/login")
	@RequestProperty(title="관리자페이지 > 로그인")
	public String login(HttpServletRequest request,
			@RequestParam(value="error", required=false) String error,
			HttpSession session,
			Model model) {

		String target = "";
		if (request.getParameter("target") != null) {
			target = request.getParameter("target");
		}
		
		model.addAttribute("OP_LOGIN_LAST_USERNAME", session.getAttribute(IdPasswordAuthenticationFilter.OP_LOGIN_LAST_USERNAME_KEY));
		model.addAttribute("error", error);
		model.addAttribute("target", target.equals("") ? "/opmanager/" : target);
		return ViewUtils.getManagerView("/user/login");
	}
	
	
	@GetMapping("/opmanager/accessdenied")
	@RequestProperty(title="접속제한")
	public String accessDenied(HttpSession session, Model model) {
		
		
		AccessDeniedException exception = (AccessDeniedException) session.getAttribute(OpAccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY);
		session.removeAttribute(OpAccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY);

		if (exception != null) {
			model.addAttribute("exception", exception);
			log.info("Access Denied : {}", exception.getMessage());
			//model.addAttribute("target", target.equals("") ? "/opmanager/" : target);
		}
		return "redirect:/opmanager/login?error=1";
	}

	@GetMapping("/opmanager/login-disconnect")
	@RequestProperty(title="세션종료")
	public String disconnectSession() {
		return ViewUtils.redirect("/opmanager/login?target=/opmanager&error=98");
	}

	@GetMapping("opmanager/login-change-password")
	@RequestProperty(title="비밀번호 변경", layout="base")
	public String changePassword(Model model,
								 @RequestParam(value = "lock", defaultValue = "") String lock) {

		model.addAttribute("lock", lock);

		return ViewUtils.getManagerView("/main/change-password");
	}

	@PostMapping("opmanager/login-change-password")
		public String changePasswordAction(HttpServletRequest request,
				@RequestParam(value = "lock") String lock,
				@RequestParam("loginId") String loginId,
				@RequestParam("password") String password,
				@RequestParam("changePassword") String changePassword) {

		String message = "";
		String redirectUrl = "";
		try {
			User user = securityService.getManagerByLoginId(loginId);

			if (ValidationUtils.isNull(user)) {
				throw new UserException("사용자가 존재하지 않습니다.");
			}

			userService.updatePasswordForManager(user.getUserId(),password,changePassword);
			securityService.updateClearLoginFailCountForManager(loginId);

			changeLogService.insertManagerChangeLog(request, user);

			message = "비밀번호가 변경되었습니다.";
			redirectUrl = "/op_security_logout?target=/opmanager";

		} catch (UserException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			message = e.getMessage();
			redirectUrl = "opmanager/login-change-password";
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			message = "비밀번호에 실패했습니다.";
			redirectUrl = "opmanager/login-change-password";
		}

		return ViewUtils.redirect(redirectUrl,message);
	}

	@GetMapping(value = "/opmanager/login-lock")
	@RequestProperty(title="접속제한")
	public String loginLock (HttpSession session) {
		return ViewUtils.getManagerView("/main/login-lock");
	}

	@PostMapping("/opmanager/login-lock")
	public String loginLockAction (@RequestParam("requestToken") String requestToken,
								   @RequestParam("loginId") String loginId,
								   @RequestParam("phoneNumber") String phoneNumber,
								   @RequestParam("smsAuth") String smsAuth) {

		Token token = new Token();

		token.setRequestToken(requestToken);
		token.setAccessToken(smsAuth);
		token.setRequestType("SMS");

		if(!smsTokenService.isValidToken(token)) {
			return ViewUtils.redirect("/opmanager/login-lock","SMS 인증에 실패 했습니다.");
		}

		User user = securityService.getManagerByLoginId(loginId);

		if (ValidationUtils.isNull(user)) {
			return ViewUtils.redirect("/opmanager/login-lock","계정이 존재하지 않습니다.");
		}

		if (!phoneNumber.equals(user.getPhoneNumber())) {
			return ViewUtils.redirect("/opmanager/login-lock","계정이 존재하지 않습니다.");
		}

		userService.updateTempPasswordForManager(user.getUserId());
		securityService.updateClearLoginFailCountForManager(loginId);

		return ViewUtils.redirect("/opmanager/login");
	}

	@ResponseBody
	@GetMapping("/opmanager/reload-cache")
	public String reloadCache() {
		// reload
		// DB 메시지 reload
		EarlyLoadingRepositoryEvent messageReloadEvent = new EarlyLoadingRepositoryEvent("messageInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
		messageInfoRepository.onApplicationEvent(messageReloadEvent);

		// Code reload
		EarlyLoadingRepositoryEvent codeReloadEvent = new EarlyLoadingRepositoryEvent("codeInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
		codeInfoRepository.onApplicationEvent(codeReloadEvent);

		return "Success!";
	}
}
