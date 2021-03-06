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
	 * sns ???????????? ?????? ?????????
	 * @param snsType
	 * @param model
	 * @return
	 */
	@GetMapping("sns-join")
	@RequestProperty(title="????????????")
	public String chooseSns(String snsType, Model model) {
		Config config = ShopUtils.getConfig();
		
		model.addAttribute("snsType", snsType);
		model.addAttribute("isJoin", true);

		model.addAttribute("agreement", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_AGREEMENT));
		model.addAttribute("protectPolicy", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_PROTECT_POLICY));

		return ViewUtils.getView("/users/sns-join");
	}
	
	// SNS, ?????? ???????????? ??????
	@GetMapping("join-us")
	@RequestProperty(title="????????????")
	public String joinUs(HttpSession session) {
		return ViewUtils.getView("/users/join-us");
	}
	/**
	 * ???????????? ?????????
	 * @return
	 */
	@GetMapping("confirm")
	@RequestProperty(title="????????????")
	public String confirm(HttpSession session) {
		
		if (UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m");
		}
		
		session.removeAttribute(AUTH_USER_KEY);
		return ViewUtils.getView("/users/confirm");
	}
	
	/**
	 * ???????????? ?????? ?????? ?????????
	 * @return
	 */
	@PostMapping("agreement")
	@RequestProperty(title="????????????")
	public String agreement(Model model, HttpSession session,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect) {

		// ???????????? ?????? ??????
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
	@RequestProperty(title="????????????")
	public String detailAgreement(Model model,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect) {

		model.addAttribute("redirect", redirect);

		model.addAttribute("agreement", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_AGREEMENT));
		model.addAttribute("protectPolicy", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_PROTECT_POLICY));

		return ViewUtils.getView("/users/detail-agreement");
	}
	
	
	


	@GetMapping("entryForm")
	@RequestProperty(title="????????????")
	public String occupied(@ModelAttribute Customer customer,RequestContext requestContext,Model model,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect){

		return getJoinEntryForm(customer, model, redirect);
	}

	@PostMapping("entryForm")
	@RequestProperty(title="????????????")
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
			throw new UserException(MessageUtils.getMessage("M01472"));	// ???????????? ????????? ????????????. 
		}
	}
	
	@PostMapping("join")
	@RequestProperty(title="????????????  ??????")
	public String joinAction(HttpServletRequest request, Model model,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect,
			User user, UserDetail userDetail){
		
		// ?????? ?????? ????????? ??????
		String checkResult = userService.checkDuplication(user);
		
		if (StringUtils.isNotEmpty(checkResult)) {
			if(checkResult.equals("isOccupiedId")) {
				return ViewUtils.redirect("/m/users/entryForm?terms=1&privacy=1", "???????????? ?????? ??????????????????");
			}
		}
		
		long userId = sequenceService.getLong("OP_USER");
		user.setUserId(userId);
		userDetail.setUserId(userId);
		
		UserCouponParam userCouponParam = new UserCouponParam();
		if (userDetail.getLevelId() != 0) userCouponParam.setUserLevelId(userDetail.getLevelId());
		
		//?????????????????? ?????? ??????[2017-09-08]minae.yun
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
		
		// MOBILE ???????????? flag ?????? [LSW 2016.08.05]
		userDetail.setSiteFlag("1");
		userService.insertUserAndUserDetail(user,userDetail);
		
		/*
		// ?????????????????? ???????????? ????????? ????????? ???.
		CategoriesEditParam categoriesEditParam  = new CategoriesEditParam();
		
		categoriesEditParam.setCode("main");
		categoriesEditParam.setEditKind("1");
		
		// 2. ????????? ??????
		ItemParam newItemParam = new ItemParam();
		newItemParam.setLimit(5);
		
		List<Item> newItemList = itemService.getNewArrivalItemListForMain(newItemParam);
		
		
		// 3. ?????? ?????? ?????????. > rankingItems (?????? ?????? ??????)
		List<CategoriesTeam> categoriesTeamList = rankingService.getRankingListForMain(5);
		
		model.addAttribute("categoryEdit", categoriesEditService.getCategoryFontPosition(categoriesEditParam));
		model.addAttribute("newItemList", newItemList);
		model.addAttribute("categoriesTeamList", categoriesTeamList);
		
		
		
		
		// ???????????? ??? ???????????????.
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
	@RequestProperty(title="????????????  ??????")
	public String joinComplete(RequestContext requestContext, Model model,
			@RequestParam(value="redirect", required=false, defaultValue="") String redirect){
		
	/*	CategoriesEditParam categoriesEditParam  = new CategoriesEditParam();
		
		categoriesEditParam.setCode("main");
		categoriesEditParam.setEditKind("1");
		
		// 2. ????????? ??????
		ItemParam newItemParam = new ItemParam();
		newItemParam.setLimit(5);
		
		List<Item> newItemList = itemService.getNewArrivalItemListForMain(newItemParam);
		
		
		// 3. ?????? ?????? ?????????. > rankingItems (?????? ?????? ??????)
		List<CategoriesTeam> categoriesTeamList = rankingService.getRankingListForMain(5);
		
		model.addAttribute("categoryEdit", categoriesEditService.getCategoryFontPosition(categoriesEditParam));
		model.addAttribute("newItemList", newItemList);
		model.addAttribute("categoriesTeamList", categoriesTeamList);
		model.addAttribute("redirect", redirect);*/
		//?????????????????? Id ?????????

		return ViewUtils.getView("/users/join-complete");
	}
	
	
	/** kye ??????
	 * ?????? ?????????
	 * @param popup
	 * @param requestContext
	 * @return
	 */
	@GetMapping("login")
	@RequestProperty(title="?????????")
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
		// ????????? ?????? ???????????? ?????? ?????? ???????????? [2018-04-19] yulsun.yoo
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
		
		// ?????? ?????? ????????? ??????
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
		
		// ?????? ??????????????? ??????.
		if (userCount == 0) {
			User user = new User();
			user.setLoginId(loginId);
			userCount = userService.getUserCountByUserInfo(user);
		}
		
		return JsonViewUtils.success(userCount);
	}
	
	/**
	 * ?????? ????????? ??????
	 * @param searchParam
	 * @return
	 */
	@GetMapping("find-id")
	@RequestProperty(title="????????? ??????", layout="base")
	public String findId(UserSearchParam searchParam) {
		
		return ViewUtils.getView("/users/find-id");
	}
	
	/**
	 * ?????? ????????? ????????????
	 * @param searchParam
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("find-id")
	@RequestProperty(title="????????? ??????", layout="base")
	public String lostId(UserSearchParam searchParam, Model model, HttpSession session) {
		
		// ???????????? ?????? ??????
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
	
	
	//LCH-UserMobileController  ????????? ?????? <??????> 

	@GetMapping(value="find-id-result")
	@RequestProperty(title="????????? ??????", layout="base")
	public String lostIdResult(UserSearchParam searchParam, Model model, HttpSession session) {
		
	/*	// ???????????? ?????? ??????
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
	 * ?????? ???????????? ??????
	 * @param searchParam
	 * @return
	 */
	@GetMapping("find-password")
	@RequestProperty(title="???????????? ??????", layout="base")
	public String findPassword(UserSearchParam searchParam) {
		
		return ViewUtils.getView("/users/find-password");
	}
	
	
	/**
	 * ?????? ???????????? ????????????
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("find-password")
	@RequestProperty(title="???????????? ??????", layout="base")
	public String findPasswordAction(UserSearchParam searchParam, Model model, HttpSession session) {
		
		// ???????????? ?????? ??????
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}

		// SNS ??????????????? ?????? ??????????????? ??????
		User user = userService.getUserInfoExceptForSnsUser(authUser);
		
		if(user == null){
			return ViewUtils.redirect("/m/users/find-password?error=1");
		}
		
		model.addAttribute("user", user);
		return ViewUtils.getView("/users/find-password-result");
	}
	
	@GetMapping(value="find-password-result")
	@RequestProperty(title="???????????? ??????", layout="base")
	public String lostPass(UserSearchParam searchParam, Model model, HttpSession session) {
		
		// ???????????? ?????? ??????
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
	 * ?????? ???????????? ??????
	 * @param user
	 * @param model
	 * @return
	 */
	@PostMapping("find-password-change")
	@RequestProperty(title="???????????? ??????")
	public String passChange(User user, Model model) {
		
		try {
			userService.updateUser(user);
		} catch(Exception e) {
			return ViewUtils.redirect("/m/users/find-password-result", "???????????? ????????? ????????? ?????????????????????.");
		}
		
		return ViewUtils.redirect("/m/users/login", "???????????? ????????? ??????????????? ??????????????????.");
	}
	
	
	
	//LCH-UserMobileController  - ???????????????-??????????????????-?????????????????? ????????? <??????>	


	@GetMapping("/editMode")
	@RequestProperty(title="????????????")
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

	//LCH-UserMobileController  - ???????????????-??????????????????-??????????????????????????? <??????>

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
					throw new UserException("???????????? ????????? ????????????.");
				}

				String password = passwordEncoder.encode(userPassword);

				if(!passwordEncoder.matches(userPassword, user.getPassword())){
					throw new UserException(MessageUtils.getMessage("M01473"));    // ??????????????? ????????????.
				}
			}
		} else {
			for(int i = 0; i < userSnsList.size(); i++){
				UserSns userSns = userSnsList.get(i);
				if(!StringUtils.isEmpty(userSns.getCertifiedDate())){
					if (!(RedirectAttributeUtils.get("modify-complete") != null
							&& "Y".equals((String) RedirectAttributeUtils.get("modify-complete")))) {

						if(!modifyResult.equals("1") || modifyResult == null){
							throw new UserException("???????????? ????????? ????????????.");
						}

						String password = passwordEncoder.encode(userPassword);
						if(!passwordEncoder.matches(userPassword, user.getPassword())){
							throw new UserException(MessageUtils.getMessage("M01473"));    // ??????????????? ????????????.
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
			// ?????? ?????? ????????? ??????
			String checkResult = userService.checkDuplication(user);
			if (StringUtils.isNotEmpty(checkResult)) {
				if(checkResult.equals("isOccupiedId")) {
					return ViewUtils.redirect("/m/users/modify", "???????????? ?????? Email ?????????");
				}
			}
		}

		user.setUserDetail(userDetail);
		userService.updateFrontUserAndUserDetail(user);
		
		// SNS ?????? ????????? ????????? ?????????  update
		if (isSnsLogin) {
			user.setPassword("");
			userSnsService.updateUser(user);
			UserUtils.getUser().setUserName(user.getUserName());
			UserUtils.getUser().setLoginId(user.getLoginId());
		}
		
		
		RedirectAttributeUtils.addAttribute("modify-complete", "Y");

		changeLogService.insertUserChangeLog(user.getUserId(), request);

		return ViewUtils.redirect("/m/users/editMode", MessageUtils.getMessage("M00289"));	// ?????????????????????.
	}
	
	
	//LCH-UserMobileController  - ???????????????-????????????????????? <??????>	

	
	@GetMapping(value="/secede")
	@Authorize("hasRole('ROLE_USER')")
	public String secede(Model model) {

		// SNS ????????? ??????????????? ??????
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
	@RequestProperty(title="????????????", layout="mypage")
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
				// SNS ????????? ???????????? ?????? ???????????? ???????????? ??????
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
	 * ????????? ????????? ????????? ??????
	 * @param userDetail
	 * @param guestUsername
	 * @return
	 */
	@PostMapping("guestLogin")
	public String guestLogin(UserDetail userDetail, HttpSession session,
			@RequestParam("guestUsername") String guestUsername) {
		
		// ???????????? ?????? ??????
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}
		
		UserUtils.setGuestLogin(guestUsername, userDetail.getFullPhoneNumber());
		
		return ViewUtils.redirect("/m/mypage/order");
	}
	
	/**
	 * ??????????????? ?????? ??? ?????? ?????? ?????????. (???????????? ??????)
	 * @return
	 */
	public List<WishlistGroup> wishlistGroups() {
		List<WishlistGroup> wishlistGroups = new ArrayList<>();
		
		return wishlistGroups;
	}
	
	
	
	
	
	
	
	
	/**
	 * ???????????? ??????
	 * @param userDetail
	 * @return
	 */
	
	@GetMapping("dormancy")
	public String dormancyLogin(UserDetail userDetail) {
		
/*		// ???????????? ?????? ??????
		AuthUserInfo authUser = (AuthUserInfo) session.getAttribute(AUTH_USER_KEY);
		if (ValidationUtils.isNull(authUser)) {
			throw new PageNotFoundException();
		}
		
		UserUtils.setGuestLogin(guestUsername, userDetail.getFullPhoneNumber());*/
		
		return ViewUtils.getView("/users/dormancy");
	}
	
	
	/**
	 * ?????? ?????? ?????? ??????
	 * @return
	 */
	@GetMapping("sleep-user")
	public String sleepUser() {
    	
		if (!UserUtils.isUserLogin()) {
    		throw new UserException("????????? ???????????????.");
    	}
    	
    	if (!"4".equals(UserUtils.getUser().getStatusCode())) {
    		throw new UserException("?????? ????????? ????????????.");
    	}
    	
		return ViewUtils.getView("/users/sleep-user");
	}
	
	/**
	 * ?????? ?????? ??????
	 * @return
	 */
	@GetMapping("wakeup-user")
	public JsonView wakeupUser() {

		if (!UserUtils.isUserLogin()) {
			return JsonViewUtils.failure("?????? ????????? ????????????.");
		}
    	
		if (!"4".equals(UserUtils.getUser().getStatusCode())) {
			return JsonViewUtils.failure("?????? ????????? ????????????.");
		}
    	
    	try {
			userService.wakeupUser(UserUtils.getUser());
    	} catch (Exception e) {
    		return JsonViewUtils.failure("???????????? ?????? ?????? ??????????????? ?????? ?????????.");
    	}
    	
    	SecurityContextHolder.getContext().setAuthentication(null);
    	return JsonViewUtils.success();
	}
	
	@GetMapping("/change-password")
	public String changePassword(Model model) {

		if (!UserUtils.isUserLogin()) {
			log.error("???????????? ????????? ????????????.");
			return ViewUtils.redirect("/op_security_logout?target=/");
		}

		return ViewUtils.view();
	}

	@PostMapping("/change-password")
	public String changePasswordAction(HttpServletRequest request,
									   @RequestParam("password") String password,
									   @RequestParam("changePassword") String changePassword) {

		if (!UserUtils.isUserLogin()) {
			log.error("???????????? ????????? ????????????.");
			return ViewUtils.redirect("/op_security_logout?target="+ShopUtils.getMobilePrefix());
		}

		String message = "";
		String redirectUrl = "";
		try {
			userService.updatePasswordForUser(UserUtils.getUserId(),password,changePassword);
			changeLogService.insertUserChangeLog(UserUtils.getUserId(), request);
			message = "??????????????? ?????????????????????.";
			redirectUrl = "/op_security_logout?target="+ShopUtils.getMobilePrefix();
		} catch (UserException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			message = e.getMessage();
			redirectUrl = ShopUtils.getMobilePrefix()+"/users/change-password";
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			message = "??????????????? ??????????????????.";
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
				return JsonViewUtils.failure("???????????? ????????? ????????????.");
			}
			userService.updatePasswordExpiredDateForUser(UserUtils.getUserId());
			return JsonViewUtils.success("???????????? ??????????????? ?????????????????????.");
		} catch (UserException e) {
			return JsonViewUtils.failure("?????????????????? ????????? ????????? ?????? ????????????.");
		} catch (Exception e) {
			return JsonViewUtils.failure("?????????????????? ????????? ????????? ?????? ????????????.");
		}
	}
}
