package saleson.shop.seller;

import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.service.SecurityService;
import com.onlinepowers.framework.security.token.TokenService;
import com.onlinepowers.framework.security.token.domain.Token;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.SellerUtils;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.seller.user.SellerUserService;
import saleson.shop.item.ItemService;
import saleson.shop.item.support.ItemParam;
import saleson.shop.log.LoginLogService;
import saleson.shop.notice.NoticeService;
import saleson.shop.notice.support.NoticeParam;
import saleson.shop.qna.QnaService;
import saleson.shop.qna.support.QnaParam;
import saleson.shop.shadowlogin.ShadowLoginLogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestProperty(template="seller", layout="default")
public class SellerController {
	
	private static final Logger log = LoggerFactory.getLogger(SellerController.class);
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private NoticeService notiveService;
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private LoginLogService loginLogService;
	
	@Autowired
	private ShadowLoginLogService shadowLoginLogService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SellerUserService sellerUserService;

	@Autowired
	@Qualifier("smsTokenService")
	TokenService smsTokenService;

	/**
	 * 판매관리자 메인.
	 * @return
	 */
	@GetMapping({"/seller", "/seller/index"})
	public String index(Model model) {
		
		long sellerId = SellerUtils.getSellerId();
		
		NoticeParam noticeParam = new NoticeParam();
		noticeParam.setVisibleType(3);
		noticeParam.setLimit(1);
		noticeParam.setSellerId(sellerId);
		
		QnaParam qnaParam = new QnaParam();
		qnaParam.setSellerId(sellerId);
		qnaParam.setLimit(5);
		qnaParam.setAnswerCount(2);
		
		ItemParam itemParam = new ItemParam();
		itemParam.setReviewDisplayFlag("N");
		itemParam.setSellerId(sellerId);
		itemParam.setConditionType("SELLER");
		itemParam.setLimit(5);
		
		// 이상우 [2017-05-15 추가] 배송,교환,반품 지연 설정일 - 추후 shopConfig에서 가져오는 방향으로 설정
		HashMap<String, Integer> map = new HashMap<>();
		map.put("shippingDelay", 1);
		map.put("exchangeDelay", 3);
		map.put("returnDelay", 3);
		
		model.addAttribute("delayDays", map);
		model.addAttribute("main", "main");
		model.addAttribute("noticeList", notiveService.getNoticeList(noticeParam));
		model.addAttribute("qnaList", qnaService.getQnaListByParam(qnaParam));
		model.addAttribute("qnaCount", qnaService.getQnaListCountByParam(qnaParam));
		model.addAttribute("reviewCount", itemService.getItemReviewCountByParam(itemParam));
		model.addAttribute("sellerReviewList", itemService.getItemReviewListByParam(itemParam));
		
		return "view:/main/index";
	}
	
	
	/**
	 * 판매자 정보 수정.
	 * @return
	 */
	@GetMapping("/seller/edit")
	public String edit(Model model) {
	
		Seller seller = sellerService.getSellerById(SellerUtils.getSellerId());

		model.addAttribute("telCodes", CodeUtils.getCodeList("TEL"));
		model.addAttribute("phoneCodes", CodeUtils.getCodeList("PHONE"));
		model.addAttribute("seller", seller);
		return "view:/user/edit";
	}
	
	/**
	 * 판매자 정보 수정처리.
	 * @return
	 */
	@PostMapping("/seller/edit")
	public String editAction(Model model, Seller seller) {
		
		sellerService.updateSeller(seller);
		sellerService.updateSellerMinimall(seller);
		return ViewUtils.redirect("/seller/edit", MessageUtils.getMessage("M00289"));	// 수정되었습니다. 
	}
	
	/**
	 * 비밀번호 수정.
	 * @return
	 */
	@GetMapping("/seller/edit/password-change-popup")
	@RequestProperty(template="seller", layout="base")
	public String passwordChangePopup(Model model) {

		model.addAttribute("sellerId", SellerUtils.getSellerId());
		return "view:/user/passwordPopup";
	}
	
	
	/**
	 * 비밀번호 수정처리.
	 * @return
	 */
	@PostMapping("/seller/edit/password-change")
	public String passwordChangeAction(Model model, Seller seller) {
		seller.setSellerId(SellerUtils.getSellerId());
		seller.setUpdatedUserId(SellerUtils.getSellerId());
		sellerService.updateSellerPassword(seller);
		return ViewUtils.redirect("/seller/edit/password-change-popup", MessageUtils.getMessage("M00289"), "self.close();");
	}
	
	
	/**
	 * 판매자 정보 수정.
	 * @return
	 */
	@GetMapping("/seller/edit-minimall")
	public String editMinimall(Model model) {
	
		Seller seller = sellerService.getSellerById(SellerUtils.getSellerId());

		model.addAttribute("seller", seller);
		return "view:/user/edit-minimall";
	}
	
	/**
	 * 판매자 정보 수정처리.
	 * @return
	 */
	@PostMapping("/seller/edit-minimall")
	public String editMinimall(Model model, Seller seller) {
		
		sellerService.updateSellerMinimall(seller);
		
		return ViewUtils.redirect("/seller/edit-minimall", MessageUtils.getMessage("M00289"));	// 수정되었습니다. 
	}
	
	
	@GetMapping("/seller/login")
	@RequestProperty(layout="base")
	public String login() {
		
		return "view:/user/login";
	}
	
	
	@PostMapping("/seller/login")
	@RequestProperty(layout="base")
	public String loginProcess(Seller loginInfo, 
			@RequestParam(value="target", defaultValue="/seller") String target,
			HttpSession session) {
		
		if (StringUtils.isEmpty(loginInfo.getLoginId()) 
				|| StringUtils.isEmpty(loginInfo.getPassword())) {
			
			RequestContextUtils.setMessage("아이디/비밀번호를 입력해 주세요.");
			
			insertLoginLog(false);
			
			return "view:/user/login";
		}
		
		Seller seller = sellerService.getSellerByLoginId(loginInfo.getLoginId());
		
		if (seller == null) {
			RequestContextUtils.setMessage("아이디/비밀번호가 일치하지 않습니다.");
			
			insertLoginLog(false);
			
			return "view:/user/login";
		}
		

		if (!passwordEncoder.matches(loginInfo.getPassword(), seller.getPassword())
				|| "3".equals(seller.getStatusCode())
				|| "4".equals(seller.getStatusCode())) {
			RequestContextUtils.setMessage("아이디/비밀번호가 일치하지 않습니다.");
			
			insertLoginLog(false);
			
			return "view:/user/login";
		}
		
		if ("1".equals(seller.getStatusCode())) {
			RequestContextUtils.setMessage("판매자 승인 대기 중입니다.");
			
			insertLoginLog(false);
			
			return "view:/user/login";
		}
		
		session.setAttribute("SELLER", seller);
		
		insertLoginLog(true);
		
		return "redirect:" + target;
		
	}

	@GetMapping("/seller/logout")
	@RequestProperty(layout="base")
	public String logout(HttpSession session) {
		session.removeAttribute("SELLER");
		session.removeAttribute("SHADOW_SELLER");
		
		return "redirect:/seller/login";
	}
	
	@PostMapping("/seller/shadow-logout")
	@RequestProperty(layout="base")
	public String shadowLogout(HttpSession session) {
		
		try {
			Seller seller = SellerUtils.getShadowSeller();
			if (seller != null) {
				shadowLoginLogService.updateShadowLogoutLog(seller.getShadowLoginLogId());
			}
		} catch (Exception e) {
			log.error("shadowLoginLogService.updateShadowLogoutLog(..) : {}", e.getMessage(), e);
		}
		
		session.removeAttribute("SELLER");
		session.removeAttribute("SHADOW_SELLER");
		
		return "redirect:/opmanager/seller/list";
	}
	
	private void insertLoginLog(boolean isSuccess) {
		loginLogService.insertLoginLogBySeller(RequestContextUtils.getRequestContext().getRequest(), isSuccess);
	}

	@GetMapping("/seller/login-change-password")
	@RequestProperty(title="비밀번호 변경", layout="base")
	public String changePassword(Model model,
								 @RequestParam(value = "lock", defaultValue = "") String lock) {
		model.addAttribute("lock", lock);

		return "view:/user/change-password";
	}

	@PostMapping("/seller/login-change-password")
	public String changePasswordAction(HttpServletRequest request,
									   @RequestParam(value = "lock") String lock,
									   @RequestParam("loginId") String loginId,
									   @RequestParam("password") String password,
									   @RequestParam("changePassword") String changePassword) {
		String message = "";
		String redirectUrl = "";
		try {
			User user = securityService.getSellerUserByLoginId(loginId);

			if (ValidationUtils.isNull(user)) {
				throw new UserException("사용자가 존재하지 않습니다.");
			}

			sellerUserService.updatePasswordForSellerUser(user.getUserId(),password,changePassword);
			securityService.updateClearLoginFailCountForSellerUser(loginId);

			message = "비밀번호가 변경되었습니다.";
			redirectUrl = "/op_security_logout?target=/seller";

		} catch (UserException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			message = e.getMessage();
			redirectUrl = "seller/login-change-password";
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			message = "비밀번호에 실패했습니다.";
			redirectUrl = "seller/login-change-password";
		}

		return ViewUtils.redirect(redirectUrl,message);
	}

	@GetMapping(value = "/seller/login-lock")
	@RequestProperty(title="접속제한", layout="base")
	public String loginLock (HttpSession session) {
		return "view:/user/login-lock";
	}

	@PostMapping("/seller/login-lock")
	public String loginLockAction (@RequestParam("requestToken") String requestToken,
								   @RequestParam("loginId") String loginId,
								   @RequestParam("phoneNumber") String phoneNumber,
								   @RequestParam("smsAuth") String smsAuth) {

		Token token = new Token();

		token.setRequestToken(requestToken);
		token.setAccessToken(smsAuth);
		token.setRequestType("SMS");

		if(!smsTokenService.isValidToken(token)) {
			return ViewUtils.redirect("/seller/login-lock","SMS 인증에 실패 했습니다.");
		}

		User user = securityService.getSellerUserByLoginId(loginId);

		if (ValidationUtils.isNull(user)) {
			return ViewUtils.redirect("/seller/login-lock","계정이 존재하지 않습니다.");
		}

		if (!phoneNumber.equals(user.getPhoneNumber())) {
			return ViewUtils.redirect("/seller/login-lock","계정이 존재하지 않습니다.");
		}

		sellerUserService.updateTempPasswordForSellerUser(user.getUserId());
		securityService.updateClearLoginFailCountForSellerUser(loginId);

		return ViewUtils.redirect("/seller/login");
	}

}
