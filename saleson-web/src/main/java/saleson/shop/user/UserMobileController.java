package saleson.shop.user;

import com.onlinepowers.framework.annotation.handler.Authorize;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.OpUserDetails;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.enumeration.eventcode.EventCodeLogType;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.categoriesedit.support.CategoriesEditParam;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.eventcode.EventCodeService;
import saleson.shop.log.ChangeLogService;
import saleson.shop.mobilecategoriesedit.MobileCategoriesEditService;
import saleson.shop.policy.PolicyService;
import saleson.shop.policy.domain.Policy;
import saleson.shop.user.domain.AuthUserInfo;
import saleson.shop.user.domain.Customer;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.user.support.UserSearchParam;
import saleson.shop.usersns.UserSnsService;
import saleson.shop.usersns.domain.UserSns;
import saleson.shop.wishlist.domain.WishlistGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m/users")
@RequestProperty(template="mobile", layout="default")
public class UserMobileController {
	private static final Logger log = LoggerFactory.getLogger(UserMobileController.class);
	
	private String AUTH_USER_KEY = "AUTH_USER_KEY";
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserSnsService userSnsService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private MobileCategoriesEditService mobileCategoriesEditService;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ChangeLogService changeLogService;

	@Autowired
	private EventCodeService eventCodeService;

	@Autowired
	private PolicyService policyService;

	/**
	 * sns 회원가입 약관 페이지
	 * @param snsType
	 * @param model
	 * @return
	 */
	@GetMapping("sns-join")
	@RequestProperty(title="회원가입")
	public String chooseSns(String snsType, Model model) {
		Config config = ShopUtils.getConfig();
		
		model.addAttribute("snsType", snsType);
		model.addAttribute("isJoin", true);

		model.addAttribute("agreement", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_AGREEMENT));
		model.addAttribute("protectPolicy", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_PROTECT_POLICY));

		return ViewUtils.getView("/users/sns-join");
	}
	
	// SNS, 일반 회원가입 선택
	@GetMapping("join-us")
	@RequestProperty(title="회원가입")
	public String joinUs(HttpSession session) {
		return ViewUtils.getView("/users/join-us");
	}
	/**
	 * 본인인증 페이지
	 * @return
	 */
	@GetMapping("confirm")
	@RequestProperty(title="회원가입")
	public String confirm(HttpSession session) {
		
		if (UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m");
		}
		
		session.removeAttribute(AUTH_USER_KEY);
		return ViewUtils.getView("/users/confirm");
	}
	
	/**
	 * 회원가입 약관 동의 페이지
	 * @return
	 */
	@PostMapping("agreement")
	@RequestProperty(title="회원가입")
	public String agreement(Model model, HttpSession session,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect) {

		// 본인확인 성공 유무
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}

		model.addAttribute("redirect", redirect);

		model.addAttribute("agreement", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_AGREEMENT));
		model.addAttribute("protectPolicy", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_PROTECT_POLICY));

		return ViewUtils.getView("/users/agreement");
	}
	
	@GetMapping("detail-agreement")
	@RequestProperty(title="회원가입")
	public String detailAgreement(Model model,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect) {

		model.addAttribute("redirect", redirect);

		model.addAttribute("agreement", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_AGREEMENT));
		model.addAttribute("protectPolicy", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_PROTECT_POLICY));

		return ViewUtils.getView("/users/detail-agreement");
	}
	
	
	


	@GetMapping("entryForm")
	@RequestProperty(title="회원가입")
	public String occupied(@ModelAttribute Customer customer,RequestContext requestContext,Model model,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect){

		return getJoinEntryForm(customer, model, redirect);
	}

	@PostMapping("entryForm")
	@RequestProperty(title="회원가입")
	public String join(@ModelAttribute Customer customer,RequestContext requestContext,Model model,
							@RequestParam(value="redirect", required=false, defaultValue="") String redirect){

		return getJoinEntryForm(customer, model, redirect);
	}

	private String getJoinEntryForm(@ModelAttribute Customer customer, Model model, @RequestParam(value = "redirect", required = false, defaultValue = "") String redirect) {
		checkAgreeTerms(customer);
		String years = DateUtils.getToday("yyyy");
		User user = UserUtils.getGuestLogin();

		model.addAttribute("user", user);
		model.addAttribute("years", years);
		model.addAttribute("redirect", redirect);
		return ViewUtils.getView("/users/join");
	}
	
	private void checkAgreeTerms(Customer customer) {
		if (ValidationUtils.isEmpty(customer.getTerms())
				|| ValidationUtils.isEmpty(customer.getPrivacy())
				|| !customer.getTerms().equals("1")
				|| !customer.getPrivacy().equals("1")) {
			throw new UserException(MessageUtils.getMessage("M01472"));	// 정상적인 접근이 아닙니다. 
		}
	}
	
	@PostMapping("join")
	@RequestProperty(title="회원가입  처리")
	public String joinAction(HttpServletRequest request, Model model,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect,
			User user, UserDetail userDetail){
		
		// 가입 불가 아이디 체크
		String checkResult = userService.checkDuplication(user);
		
		if (StringUtils.isNotEmpty(checkResult)) {
			if(checkResult.equals("isOccupiedId")) {
				return ViewUtils.redirect("/m/users/entryForm?terms=1&privacy=1", "사용할수 없는 아이디입니다");
			}
		}
		
		long userId = sequenceService.getLong("OP_USER");
		user.setUserId(userId);
		userDetail.setUserId(userId);
		
		UserCouponParam userCouponParam = new UserCouponParam();
		if (userDetail.getLevelId() != 0) userCouponParam.setUserLevelId(userDetail.getLevelId());
		
		//신규회원가입 쿠폰 발급[2017-09-08]minae.yun
		userCouponParam.setCouponTargetTimeType("2");
		List<Coupon> newUserCouponList = couponService.getCouponByTargetTimeType(userCouponParam);

		if (newUserCouponList != null && newUserCouponList.size() != 0) {
			for (Coupon coupon : newUserCouponList) {
				userCouponParam.setCouponId(coupon.getCouponId());
				userCouponParam.setUserId(userId);
				couponService.insertCouponTargetUserOne(userCouponParam);
				couponService.userCouponDownload(userCouponParam);
			}
		}
		
		// MOBILE 회원가입 flag 추가 [LSW 2016.08.05]
		userDetail.setSiteFlag("1");
		userService.insertUserAndUserDetail(user,userDetail);
		
		/*
		// 완료페이지를 보여주고 로그인 처리를 함.
		CategoriesEditParam categoriesEditParam  = new CategoriesEditParam();
		
		categoriesEditParam.setCode("main");
		categoriesEditParam.setEditKind("1");
		
		// 2. 신상품 조회
		ItemParam newItemParam = new ItemParam();
		newItemParam.setLimit(5);
		
		List<Item> newItemList = itemService.getNewArrivalItemListForMain(newItemParam);
		
		
		// 3. 팀별 랭킹 리스트. > rankingItems (랭킹 상품 목록)
		List<CategoriesTeam> categoriesTeamList = rankingService.getRankingListForMain(5);
		
		model.addAttribute("categoryEdit", categoriesEditService.getCategoryFontPosition(categoriesEditParam));
		model.addAttribute("newItemList", newItemList);
		model.addAttribute("categoriesTeamList", categoriesTeamList);
		
		
		
		
		// 회원가입 후 자동로그인.
		String userId = user.getLoginId();
		
		String loginId = ShadowUtils.getShadowLoginKey(userId, "");
		String password = ShadowUtils.getShadowLoginPassword(userId);
		String signature = ShadowUtils.getShadowLoginSignature(userId);*/
		
		/*model.addAttribute("loginId", loginId);
		model.addAttribute("password", password);
		model.addAttribute("signature", signature);*/
		model.addAttribute("redirect", redirect);
		
		//return ViewUtils.getView("/users/join-login");

		eventCodeService.insertLog(request, userId, EventCodeType.NONE, EventCodeLogType.USER);

		return ViewUtils.redirect("/m/users/join-complete", MessageUtils.getMessage("M00341"));
	}
	
	
	
	@GetMapping("join-complete")
	@RequestProperty(title="회원가입  완료")
	public String joinComplete(RequestContext requestContext, Model model,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect){
		
	/*	CategoriesEditParam categoriesEditParam  = new CategoriesEditParam();
		
		categoriesEditParam.setCode("main");
		categoriesEditParam.setEditKind("1");
		
		// 2. 신상품 조회
		ItemParam newItemParam = new ItemParam();
		newItemParam.setLimit(5);
		
		List<Item> newItemList = itemService.getNewArrivalItemListForMain(newItemParam);
		
		
		// 3. 팀별 랭킹 리스트. > rankingItems (랭킹 상품 목록)
		List<CategoriesTeam> categoriesTeamList = rankingService.getRankingListForMain(5);
		
		model.addAttribute("categoryEdit", categoriesEditService.getCategoryFontPosition(categoriesEditParam));
		model.addAttribute("newItemList", newItemList);
		model.addAttribute("categoriesTeamList", categoriesTeamList);
		model.addAttribute("redirect", redirect);*/
		//에이스카운터 Id 확인용

		return ViewUtils.getView("/users/join-complete");
	}
	
	
	/** kye 수정
	 * 회원 로그인
	 * @param popup
	 * @param requestContext
	 * @return
	 */
	@GetMapping("login")
	@RequestProperty(title="로그인")
	public String login(@RequestParam(value="popup", required=false, defaultValue="") String popup,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect,
			@RequestParam(value="target", required=false, defaultValue="") String target,
			@RequestParam(value="uri", required=false, defaultValue="") String uri,
			RequestContext requestContext, Model model) {
		
		if (UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m");
		}
		
		if (popup.equals("1")) {
			
		}	
		
		model.addAttribute("redirect", StringUtils.stripXSS(redirect));
		// 모바일 팝업 로그인시 상품 상세 구매처리 [2018-04-19] yulsun.yoo
		requestContext.getSession().removeAttribute("guestDomain");
		model.addAttribute("popup", StringUtils.stripXSS(popup));
		model.addAttribute("target", StringUtils.stripXSS(target));
		model.addAttribute("uri", StringUtils.stripXSS(uri));
		model.addAttribute("loginCheck", "T");
		return ViewUtils.getView("/users/login");
	}
	
	@GetMapping("popup-login")
	public String popupLogin(RequestContext requestContext) {
		RequestContextUtils.setLayout("base");
		return ViewUtils.view();
	}
	
	@RequestProperty(layout="blank")
	@PostMapping("find-user")
	public JsonView findUser(RequestContext requestContext, Model model, 
			@RequestParam("loginId") String loginId ){
		
		// 가입 불가 아이디 체크
		Config config = configService.getShopConfigCache(Config.SHOP_CONFIG_ID);
		
		int userCount = 0;
		if (config.getDeniedId() != null && !config.getDeniedId().equals("")) {
			String[] denyIds = StringUtils.tokenizeToStringArray(config.getDeniedId(), ",");
			
			for (String userId : denyIds) {
				if (userId.trim().equals("")) {
					continue;
				}
				
				if (userId.trim().equals(loginId.trim())) {
					userCount = 1;
					break;
				}
			}
		}
		
		// 회원 테이블에서 조회.
		if (userCount == 0) {
			User user = new User();
			user.setLoginId(loginId);
			userCount = userService.getUserCountByUserInfo(user);
		}
		
		return JsonViewUtils.success(userCount);
	}
	
	/**
	 * 회원 아이디 찾기
	 * @param searchParam
	 * @return
	 */
	@GetMapping("find-id")
	@RequestProperty(title="아이디 찾기", layout="base")
	public String findId(UserSearchParam searchParam) {
		
		return ViewUtils.getView("/users/find-id");
	}
	
	/**
	 * 회원 아이디 찾기실행
	 * @param searchParam
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("find-id")
	@RequestProperty(title="아이디 찾기", layout="base")
	public String lostId(UserSearchParam searchParam, Model model, HttpSession session) {
		
		// 본인확인 성공 유무
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}
		
		User user = userService.getUserInfoByUserName(authUser);
				
		if(user == null){
			return ViewUtils.redirect("/m/users/find-id?error=1");
		}
		
		model.addAttribute("user", user);
		return ViewUtils.getView("/users/find-id-result");
	}
	
	
	//LCH-UserMobileController  아이디 찾기 <수정> 

	@GetMapping(value="find-id-result")
	@RequestProperty(title="아이디 찾기", layout="base")
	public String lostIdResult(UserSearchParam searchParam, Model model, HttpSession session) {
		
	/*	// 본인확인 성공 유무
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}
		
		User user = userService.getUserInfoByUserName(authUser);
				
		if(user == null){
			return ViewUtils.redirect("/m/users/find-id?error=1");
		}
		
		model.addAttribute("user", user);*/
		return ViewUtils.getView("/users/find-id-result");
	
	}
	
	
	
	
	
	/**
	 * 회원 비밀번호 찾기
	 * @param searchParam
	 * @return
	 */
	@GetMapping("find-password")
	@RequestProperty(title="비밀번호 찾기", layout="base")
	public String findPassword(UserSearchParam searchParam) {
		
		return ViewUtils.getView("/users/find-password");
	}
	
	
	/**
	 * 회원 비밀번호 찾기실행
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("find-password")
	@RequestProperty(title="비밀번호 찾기", layout="base")
	public String findPasswordAction(UserSearchParam searchParam, Model model, HttpSession session) {
		
		// 본인확인 성공 유무
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}

		// SNS 연동계정이 아닌 회원정보만 조회
		User user = userService.getUserInfoExceptForSnsUser(authUser);
		
		if(user == null){
			return ViewUtils.redirect("/m/users/find-password?error=1");
		}
		
		model.addAttribute("user", user);
		return ViewUtils.getView("/users/find-password-result");
	}
	
	@GetMapping(value="find-password-result")
	@RequestProperty(title="비밀번호 찾기", layout="base")
	public String lostPass(UserSearchParam searchParam, Model model, HttpSession session) {
		
		// 본인확인 성공 유무
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}
		
		User user = userService.getUserInfoByUserName(authUser);
		
		if(user == null){
			return ViewUtils.redirect("/m/users/find-password?error=1");
		}
		
		model.addAttribute("user", user);
		return ViewUtils.getView("/users/find-password-result");
	}
	
	
	
	
	
	/**
	 * 회원 비밀번호 변경
	 * @param user
	 * @param model
	 * @return
	 */
	@PostMapping("find-password-change")
	@RequestProperty(title="비밀번호 변경")
	public String passChange(User user, Model model) {
		
		try {
			userService.updateUser(user);
		} catch(Exception e) {
			return ViewUtils.redirect("/m/users/find-password-result", "비밀번호 변경중 에러가 발생하였습니다.");
		}
		
		return ViewUtils.redirect("/m/users/login", "비밀번호 변경이 정상적으로 처리됬습니다.");
	}
	
	
	
	//LCH-UserMobileController  - 마이페이지-회원정보수정-비밀번호확인 페이지 <수정>	


	@GetMapping("/editMode")
	@RequestProperty(title="회원수정")
	@Authorize("hasRole('ROLE_USER')")
	public String editMode(Model model) {
		List<UserSns> userSnsList = userSnsService.getUserSnsList(new UserSns(UserUtils.getUserId()));
		if(userSnsList != null && !userSnsList.isEmpty()){
			for(int i = 0; i < userSnsList.size(); i++){
				UserSns userSns = userSnsList.get(i);
				if (userSns != null && StringUtils.isEmpty(userSns.getCertifiedDate())) {
					return ViewUtils.redirect("/m/users/modify");
				}
			}
		}
		model.addAttribute("user",UserUtils.getUser());
		model.addAttribute("wishlistGroups", wishlistGroups());
		return ViewUtils.getView("/users/editMode");
	}

	//LCH-UserMobileController  - 마이페이지-회원정보수정-회원정보수정페이지 <수정>

	@GetMapping("modify")
	@Authorize("hasRole('ROLE_USER')")
	public String getModify(Model model,
							 @RequestParam(value="modifyResult", required=false) String modifyResult,
							 @RequestParam(value="userPassword", required=false) String userPassword) {

		setModifyInfo(model, modifyResult, userPassword);

		return ViewUtils.getView("/users/modify");
	}

	@PostMapping("modify")
	@Authorize("hasRole('ROLE_USER')")
	public String postModify(Model model,
							@RequestParam(value="modifyResult", required=false) String modifyResult,
							@RequestParam(value="userPassword", required=false) String userPassword) {

		setModifyInfo(model, modifyResult, userPassword);
		
		return ViewUtils.getView("/users/modify");
	}

	public void setModifyInfo(Model model, String modifyResult, String userPassword) {
		User user = userService.getUserByUserId(UserUtils.getUser().getUserId());
		List<UserSns> userSnsList = userSnsService.getUserSnsList(new UserSns(UserUtils.getUserId()));
		if(userSnsList.isEmpty()){
			if (!(RedirectAttributeUtils.get("modify-complete") != null
					&& "Y".equals((String) RedirectAttributeUtils.get("modify-complete")))) {

				if(!modifyResult.equals("1") || modifyResult == null){
					throw new UserException("정상적인 접근이 아닙니다.");
				}

				String password = passwordEncoder.encode(userPassword);

				if(!passwordEncoder.matches(userPassword, user.getPassword())){
					throw new UserException(MessageUtils.getMessage("M01473"));    // 비밀번호가 다릅니다.
				}
			}
		} else {
			for(int i = 0; i < userSnsList.size(); i++){
				UserSns userSns = userSnsList.get(i);
				if(!StringUtils.isEmpty(userSns.getCertifiedDate())){
					if (!(RedirectAttributeUtils.get("modify-complete") != null
							&& "Y".equals((String) RedirectAttributeUtils.get("modify-complete")))) {

						if(!modifyResult.equals("1") || modifyResult == null){
							throw new UserException("정상적인 접근이 아닙니다.");
						}

						String password = passwordEncoder.encode(userPassword);
						if(!passwordEncoder.matches(userPassword, user.getPassword())){
							throw new UserException(MessageUtils.getMessage("M01473"));    // 비밀번호가 다릅니다.
						}
					}
				} else {
					model.addAttribute("userSns", userSns);
					model.addAttribute("isSnsLogin",true);
				}
			}
		}

		UserDetail userDetail = (UserDetail) user.getUserDetail();

		String years = DateUtils.getToday("yyyy");

		Object princial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (princial instanceof UserDetails) {
			((OpUserDetails) princial).setUserDetail(userDetail);
			((OpUserDetails) princial).getUser().setUserName(user.getUserName());
			((OpUserDetails) princial).getUser().setEmail(user.getEmail());
		}

		userService.setMypageUserInfoForFront(model);

		model.addAttribute("years", years);
		model.addAttribute("user",user);

		model.addAttribute("wishlistGroups", wishlistGroups());
	}

	@PostMapping("/modify-action")
	@Authorize("hasRole('ROLE_USER')")
	public String modifyAction(Model model, User user, UserDetail userDetail, @RequestParam(value="isSnsLogin", required=false, defaultValue="false") Boolean isSnsLogin,
							   HttpServletRequest request) {
		//System.out.println("birthdayType:: " + userDetail.getBirthdayType());
		
		if (isSnsLogin) {
			// 가입 불가 아이디 체크
			String checkResult = userService.checkDuplication(user);
			if (StringUtils.isNotEmpty(checkResult)) {
				if(checkResult.equals("isOccupiedId")) {
					return ViewUtils.redirect("/m/users/modify", "사용할수 없는 Email 입니다");
				}
			}
		}

		user.setUserDetail(userDetail);
		userService.updateFrontUserAndUserDetail(user);
		
		// SNS 계정 미인증 일경우 인증일  update
		if (isSnsLogin) {
			user.setPassword("");
			userSnsService.updateUser(user);
			UserUtils.getUser().setUserName(user.getUserName());
			UserUtils.getUser().setLoginId(user.getLoginId());
		}
		
		
		RedirectAttributeUtils.addAttribute("modify-complete", "Y");

		changeLogService.insertUserChangeLog(user.getUserId(), request);

		return ViewUtils.redirect("/m/users/editMode", MessageUtils.getMessage("M00289"));	// 수정되었습니다.
	}
	
	
	//LCH-UserMobileController  - 마이페이지-회원탈퇴페이지 <수정>	

	
	@GetMapping(value="/secede")
	@Authorize("hasRole('ROLE_USER')")
	public String secede(Model model) {

		// SNS 미인증 사용자인지 확인
		List<UserSns> userSnsList = userSnsService.getUserSnsList(new UserSns(UserUtils.getUserId()));
		if (userSnsList != null && !userSnsList.isEmpty()) {
			for (UserSns userSns : userSnsList) {
				if (StringUtils.isEmpty(userSns.getCertifiedDate())) {
					model.addAttribute("isSnsLogin", true);
					break;
				}
			}
		}

		model.addAttribute("user", UserUtils.getUser());
		return ViewUtils.getView("/users/secede");
	}

	@GetMapping(value="/secede-success")
	public String secedeSuccess(Model model) {
		
		CategoriesEditParam categoriesEditParam  = new CategoriesEditParam();
		categoriesEditParam.setCode("user_delete");
		categoriesEditParam.setEditKind("1");
		
		model.addAttribute("freeHtml", mobileCategoriesEditService.getCategoryFontPosition(categoriesEditParam));
		
		return ViewUtils.getView("/users/secede-success");
	}
	

	@PostMapping("/secede")
	@RequestProperty(title="회원탈퇴", layout="mypage")
	@Authorize("hasRole('ROLE_USER')")
	public String secedeAction(UserDetail userDetail,
			@RequestParam(value="userPassword", required=false) String userPassword) {

		/*ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(userPassword, user.getUserId());*/

		User user = userService.getUserByUserId(UserUtils.getUser().getUserId());

		boolean isCheck = true;
		List<UserSns> userSnsList = userSnsService.getUserSnsList(new UserSns(UserUtils.getUserId()));
		if (userSnsList != null && !userSnsList.isEmpty()) {
			for (UserSns userSns : userSnsList) {
				// SNS 미인증 사용자의 경우 비밀번호 체크하지 않음
				if (StringUtils.isEmpty(userSns.getCertifiedDate())) {
					isCheck = false;
					break;
				}
			}
		}

		if (isCheck && !passwordEncoder.matches(userPassword, user.getPassword())) {
			throw new UserException(MessageUtils.getMessage("M01473"));
		}

		user.setLeaveDate(DateUtils.getToday());
		user.setStatusCode("3");
		
		userDetail.setUserId(user.getUserId());
		userDetail.setUseFlag("N");
		user.setUserDetail(userDetail);

		userService.updateSecedeFrontUserAndUserDetail(user);

		return ViewUtils.redirect("/op_security_logout?target=/m", MessageUtils.getMessage("M01474")) ;
	}
	
	/**
	 * 비회원 인증후 로그인 처리
	 * @param userDetail
	 * @param guestUsername
	 * @return
	 */
	@PostMapping("guestLogin")
	public String guestLogin(UserDetail userDetail, HttpSession session,
			@RequestParam("guestUsername") String guestUsername) {
		
		// 본인확인 성공 유무
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}
		
		UserUtils.setGuestLogin(guestUsername, userDetail.getFullPhoneNumber());
		
		return ViewUtils.redirect("/m/mypage/order");
	}
	
	/**
	 * 마이페이지 접속 시 공통 모델 바인딩. (관심상품 그룹)
	 * @return
	 */
	public List<WishlistGroup> wishlistGroups() {
		List<WishlistGroup> wishlistGroups = new ArrayList<>();
		
		return wishlistGroups;
	}
	
	
	
	
	
	
	
	
	/**
	 * 휴먼계정 처리
	 * @param userDetail
	 * @return
	 */
	
	@GetMapping("dormancy")
	public String dormancyLogin(UserDetail userDetail) {
		
/*		// 본인확인 성공 유무
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}
		
		UserUtils.setGuestLogin(guestUsername, userDetail.getFullPhoneNumber());*/
		
		return ViewUtils.getView("/users/dormancy");
	}
	
	
	/**
	 * 휴면 계정 복구 화면
	 * @return
	 */
	@GetMapping("sleep-user")
	public String sleepUser() {
    	
		if (!UserUtils.isUserLogin()) {
    		throw new UserException("잘못된 접근입니다.");
    	}
    	
    	if (!"4".equals(UserUtils.getUser().getStatusCode())) {
    		throw new UserException("휴면 회원이 아닙니다.");
    	}
    	
		return ViewUtils.getView("/users/sleep-user");
	}
	
	/**
	 * 휴면 계정 복구
	 * @return
	 */
	@GetMapping("wakeup-user")
	public JsonView wakeupUser() {

		if (!UserUtils.isUserLogin()) {
			return JsonViewUtils.failure("휴면 회원이 아닙니다.");
		}
    	
		if (!"4".equals(UserUtils.getUser().getStatusCode())) {
			return JsonViewUtils.failure("휴면 회원이 아닙니다.");
		}
    	
    	try {
			userService.wakeupUser(UserUtils.getUser());
    	} catch (Exception e) {
    		return JsonViewUtils.failure("휴면계정 해지 실패 고객센터에 문의 하세요.");
    	}
    	
    	SecurityContextHolder.getContext().setAuthentication(null);
    	return JsonViewUtils.success();
	}
	
	@GetMapping("/change-password")
	public String changePassword(Model model) {

		if (!UserUtils.isUserLogin()) {
			log.error("로그인이 안되어 있습니다.");
			return ViewUtils.redirect("/op_security_logout?target=/");
		}

		return ViewUtils.view();
	}

	@PostMapping("/change-password")
	public String changePasswordAction(HttpServletRequest request,
									   @RequestParam("password") String password,
									   @RequestParam("changePassword") String changePassword) {

		if (!UserUtils.isUserLogin()) {
			log.error("로그인이 안되어 있습니다.");
			return ViewUtils.redirect("/op_security_logout?target="+ShopUtils.getMobilePrefix());
		}

		String message = "";
		String redirectUrl = "";
		try {
			userService.updatePasswordForUser(UserUtils.getUserId(),password,changePassword);
			changeLogService.insertUserChangeLog(UserUtils.getUserId(), request);
			message = "비밀번호가 변경되었습니다.";
			redirectUrl = "/op_security_logout?target="+ShopUtils.getMobilePrefix();
		} catch (UserException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			message = e.getMessage();
			redirectUrl = ShopUtils.getMobilePrefix()+"/users/change-password";
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			message = "비밀번호에 실패했습니다.";
			redirectUrl = ShopUtils.getMobilePrefix()+"/users/change-password";
		}

		return ViewUtils.redirect(redirectUrl,message);
	}

	@PostMapping("/delay-change-password")
	public JsonView delayChangePassword(RequestContext requestContext) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		try {

			if (!UserUtils.isUserLogin()) {
				return JsonViewUtils.failure("로그인이 안되어 있습니다.");
			}
			userService.updatePasswordExpiredDateForUser(UserUtils.getUserId());
			return JsonViewUtils.success("비밀번호 유효기간이 연장되었습니다.");
		} catch (UserException e) {
			return JsonViewUtils.failure("비밀번호관련 정보가 변경에 실패 했습니다.");
		} catch (Exception e) {
			return JsonViewUtils.failure("비밀번호관련 정보가 변경에 실패 했습니다.");
		}
	}
}
