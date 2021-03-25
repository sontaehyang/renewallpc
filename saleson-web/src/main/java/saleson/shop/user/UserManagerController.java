package saleson.shop.user;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.enumeration.JavaScript;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.security.userdetails.UserRole;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.domain.SearchParam;
import com.onlinepowers.framework.web.opmanager.menu.MenuService;
import com.onlinepowers.framework.web.opmanager.menu.domain.Menu;
import com.onlinepowers.framework.web.opmanager.menu.domain.MenuRight;
import com.onlinepowers.framework.web.opmanager.role.RoleService;
import com.onlinepowers.framework.web.opmanager.role.domain.Role;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import saleson.common.Const;
import saleson.common.utils.PointUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.UmsSendLog;
import saleson.shop.businesscode.BusinessCodeService;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.claim.ClaimService;
import saleson.shop.claim.domain.ClaimMemo;
import saleson.shop.claim.support.ClaimMemoParam;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.group.GroupService;
import saleson.shop.group.domain.Group;
import saleson.shop.log.ChangeLogService;
import saleson.shop.mailconfig.MailConfigService;
import saleson.shop.mailconfig.support.MailTemplate;
import saleson.shop.order.OrderService;
import saleson.shop.order.support.OrderParam;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.AvailablePoint;
import saleson.shop.point.domain.Point;
import saleson.shop.point.domain.PointHistory;
import saleson.shop.point.domain.PointUsed;
import saleson.shop.point.support.PointParam;
import saleson.shop.sendmaillog.SendMailLogService;
import saleson.shop.sendmaillog.domain.SendMailLog;
import saleson.shop.sendmaillog.support.SendMailLogParam;
import saleson.shop.sendsmslog.SendSmsLogService;
import saleson.shop.sendsmslog.domain.SendSmsLog;
import saleson.shop.sendsmslog.support.SendSmsLogParam;
import saleson.shop.smsconfig.SmsConfigService;
import saleson.shop.smsconfig.domain.SmsConfig;
import saleson.shop.smsconfig.support.SmsTemplate;
import saleson.shop.smsconfig.support.UserSms;
import saleson.shop.ums.UmsSendLogRepository;
import saleson.shop.ums.support.UmsSendLogDto;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.user.support.UserExcelView;
import saleson.shop.user.support.UserSearchParam;
import saleson.shop.userdelivery.UserDeliveryService;
import saleson.shop.userdelivery.domain.UserDelivery;
import saleson.shop.userdelivery.support.UserDeliveryParam;
import saleson.shop.usergroup.UserGroupService;
import saleson.shop.usergroup.domain.UserGroup;
import saleson.shop.userlevel.UserLevelService;
import saleson.shop.usersns.UserSnsService;
import saleson.shop.usersns.domain.UserSns;
import saleson.shop.zipcode.ZipcodeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/opmanager/user")
@RequestProperty(title = "회원관리", layout = "default", template="opmanager")
public class UserManagerController {
	private static final Logger log = LoggerFactory
			.getLogger(UserManagerController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private SequenceService sequenceService;

	@Autowired
	private BusinessCodeService businessCodeService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private PointService pointService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CategoriesTeamGroupService categoryCategoriesTeamGroupService;
	
	@Autowired
	private ZipcodeService zipcodeService;
	
	@Autowired
	private UserDeliveryService userDeliveryService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ClaimService claimService;
	
	@Autowired
	private SendMailLogService sendMailLogService;
	
	@Autowired
	private SendSmsLogService sendSmsLogService;
	
	@Autowired
	private SmsConfigService smsConfigService;
	
	@Autowired
	private MailConfigService mailConfigService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private UserLevelService userLevelService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserSnsService userSnsService;

	@Autowired
	private UmsSendLogRepository umsSendLogRepository;

	@Autowired
	private ChangeLogService changeLogService;

	/**
	 * 회원 클레임 등록
	 * @param userId
	 * @param model
	 * @param claimMemo
	 * @return
	 */
	@GetMapping("/popup/claim-write/{userId}")
	@RequestProperty(layout="base")
	public String claimWrite(@PathVariable("userId") long userId, Model model, ClaimMemo claimMemo) {
		
		User user = userService.getUserByUserId(userId);
		if (user == null) {
			throw new PageNotFoundException();
		}

		claimMemo.setUserId(userId);
		claimMemo.setUserName(user.getUserName());
		model.addAttribute("user", user);
		model.addAttribute("claimMemo", claimMemo);
		return "view:/user/popup/claim-write";
	}
	
	/**
	 * 회원 클레임 등록
	 * @param userId
	 * @param model
	 * @param claimMemo
	 * @return
	 */
	@PostMapping("/popup/claim-write/{userId}")
	@RequestProperty(layout="base")
	public String claimWriteAction(@PathVariable("userId") long userId, ClaimMemo claimMemo) {
		
		User user = userService.getUserByUserId(userId);
		if (user == null) {
			throw new PageNotFoundException();
		}
		
		claimMemo.setClaimMemoId(sequenceService.getId("OP_CLAIM_MEMO"));
		claimMemo.setUserId(user.getUserId());
		claimMemo.setUserName(user.getUserName());
		
		claimService.insertClaimMemo(claimMemo);
		
		return ViewUtils.redirect("/opmanager/user/popup/claim-write/" + userId,
				MessageUtils.getMessage("M01221"), "try{opener.claimMemoList(1);}catch(e){}"); //처리되었습니다. 
	}
	
	/**
	 * 메모 리스트
	 * @param claimMemo
	 * @param bindingResult
	 * @return
	 */
	@GetMapping(value="/popup/claim-update/{claimMemoId}")
	@RequestProperty(layout="base")
	public String claimMemoUpdate(RequestContext requestContext, ClaimMemoParam param, Model model,
			@PathVariable("claimMemoId") int claimMemoId) {
		
		ClaimMemo memo = claimService.getClaimMemoById(claimMemoId);
		if (memo == null) {
			throw new PageNotFoundException();
		}
		
		User user = userService.getUserByUserId(memo.getUserId());
		if (user == null) {
			throw new PageNotFoundException();
		}

		user.setUserId(user.getUserId());
		user.setUserName(user.getUserName());
		model.addAttribute("user", user);
		model.addAttribute("claimMemo", memo);
		return "view:/user/popup/claim-write";
	}
	
	@PostMapping("/popup/claim-update/{claimMemoId}")
	public String claimMemoUpdate(RequestContext requestContext, ClaimMemo memo, Model model,
			@PathVariable("claimMemoId") int claimMemoId) {
		
		claimService.updateClaimMemo(memo);
		
		return ViewUtils.redirect("/opmanager/user/popup/claim-update/" + claimMemoId,
				MessageUtils.getMessage("M01221"), "try{self.close(); opener.claimMemoList(1);}catch(e){}"); //처리되었습니다. 
	}
	
	/**
	 * 관리자 리스트
	 * 
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/list")
	public String list(
			@ModelAttribute("searchParam") UserSearchParam searchParam,
			Model model) {

		int count = userService.getUserCount(searchParam);

		Pagination pagination = Pagination.getInstance(count, 10);
		searchParam.setPagination(pagination);

		model.addAttribute("list", userService.getUserList(searchParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("count", count);

		return ViewUtils.view();

	}

	/**
	 * 관리자 등록
	 * 
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/write")
	public String write(User user, Model model) {

		model.addAttribute("user", user);
		model.addAttribute("mode", "insert");
		return ViewUtils.view();

	}

	/**
	 * 아이디 중복확인.
	 * 
	 * @param loginId
	 * @return
	 */
	@PostMapping("/checkid")
	public JsonView checkId(RequestContext requestContext,
			@RequestParam("loginId") String loginId) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		int userCount = userService.getUserCountByManagerId(loginId);

		if (userCount == 0) {
			return JsonViewUtils.success();

		} else {
			return JsonViewUtils.exception(MessageUtils.getMessage("M00160"));

		}
	}

	/**
	 * 회원 등록처리
	 * 
	 * @return
	 */
	@PostMapping("write")
	public String writeAction(
			@RequestParam(value = "tel1", required = true) String tel1,
			@RequestParam(value = "tel2", required = true) String tel2,
			@RequestParam(value = "tel3", required = true) String tel3,
			@RequestParam(value = "email1", required = true) String email1,
			@RequestParam(value = "email2", required = true) String email2,
			User user, Model model) {

		user.setEmail(email1 + "@" + email2);
		user.setStatusCode("9");

		userService.insertUser(user); // 회원등록
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		//user.setPassword(encoder.encodePassword(user.getPassword(),user.getUserId()));

		userService.updateUser(user); // 비번 다시등록

		UserDetail userDetail = new UserDetail();
		userDetail.setUserId(user.getUserId());
		userDetail.setPhoneNumber(tel1 + "-" + tel2 + "-" + tel3);

		userService.insertUserDetail(userDetail); // 전화번호등록

		UserRole userRole = new UserRole();
		userRole.setUserId(user.getUserId());
		userRole.setAuthority(user.getUserRoles().get(0).getAuthority());

		userService.insertUserRole(userRole); // 권한등록

		FlashMapUtils.alert(MessageUtils.getMessage("M00279"));

		return ViewUtils.redirect("/opmanager/user/list");

	}

	/**
	 * 관리자 수정
	 * 
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/edit")
	public String edit(
			@RequestParam(value = "userId", required = true) long userId,
			Model model) {

		User user = userService.getUserByUserId(userId);

		if (user == null) {
			throw new PageNotFoundException();
		}

		model.addAttribute("user", user);
		model.addAttribute("mode", "edit");

		return ViewUtils.getView("/user/write");
	}

	/**
	 * 관리자 수정처리
	 * 
	 * @param requestContext
	 * @return
	 */
	@PostMapping("edit")
	public String editAction(
			@RequestParam(value = "tel1", required = true) String tel1,
			@RequestParam(value = "tel2", required = true) String tel2,
			@RequestParam(value = "tel3", required = true) String tel3,
			@RequestParam(value = "email1", required = true) String email1,
			@RequestParam(value = "email2", required = true) String email2,
			User user, Model model) {

		user.setEmail(email1 + "@" + email2);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userService.updateUser(user); // 회원등록

		UserDetail userDetail = new UserDetail();
		userDetail.setUserId(user.getUserId());
		userDetail.setPhoneNumber(tel1 + "-" + tel2 + "-" + tel3);

		userService.updateUserDetail(userDetail); // 전화번호등록

		UserRole userRole = new UserRole();
		userRole.setUserId(user.getUserId());
		userRole.setAuthority(user.getUserRoles().get(0).getAuthority());

		userService.updateUserRole(userRole); // 권한등록

		FlashMapUtils.alert(MessageUtils.getMessage("M00280"));

		return ViewUtils.redirect("/opmanager/user/list");
	}

	/**
	 * 관리자 삭제
	 * 
	 * @param requestContext
	 * @return
	 */
	@PostMapping("delete")
	public String delete(
			@RequestParam(value = "userId", required = true) long userId) {

		userService.deleteUserDetail(userId);
		userService.deleteUserRole(userId);
		userService.deleteUser(userId);

		FlashMapUtils.alert(MessageUtils.getMessage("M00281"));

		return ViewUtils.redirect("/opmanager/user/list");
	}

	/**
	 * 고객 관리
	 * 
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("customer/list")
	public String customerList(
			@ModelAttribute("searchParam") UserSearchParam searchParam,
			Model model) {
		searchParam.setAuthority("ROLE_USER");

		Pagination pagination = Pagination.getInstance(userService.getUserCount(searchParam));
		searchParam.setPagination(pagination);


		String today = DateUtils.getToday(Const.DATE_FORMAT);

		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month2", DateUtils.addYearMonthDay(today, 0, -2, 0));


		List<Group> groups = groupService.getGroupsAndUserLevelsAll();
		model.addAttribute("groups", groups);
		model.addAttribute("list", userService.getUserList(searchParam));
		model.addAttribute("pagination", pagination);

		HttpSession session = RequestContextUtils.getSession();
		session.setAttribute("reOpmanagerUserSearchParam", searchParam);

		return ViewUtils.view();
	}
	
	/**
	 * 고객 목록 엑셀 다운로드
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value="customer/list/download-excel")
	public ModelAndView downloadExcelProcessByUserList(){

		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new UserExcelView());
		
		HttpSession session = RequestContextUtils.getSession();
		UserSearchParam searchParam = (UserSearchParam)session.getAttribute("reOpmanagerUserSearchParam");
		
		searchParam.setConditionType("EXCEL_DOWNLOAD");
		searchParam.setPagination(null);
		
		mav.addObject("userList", userService.getUserList(searchParam));
		
		return mav;
	}

	/**
	 * 회원등록
	 * @param user
	 * @param model
	 * @return
	 */
	@GetMapping("customer/create")
	public String customerCreate(User user, Model model) {

		//List<CodeInfo> positionTypeCode = CodeUtils.getCodeInfoList("POSITION_TYPE");
		List<CodeInfo> telCodes = CodeUtils.getCodeInfoList("TEL");
		List<CodeInfo> phoneCodes = CodeUtils.getCodeInfoList("PHONE");
		//List<BusinessCode> businessCodes = businessCodeService.getBusinessCodeList(new BusinessCode());
		
		String years = DateUtils.getToday("yyyy");

		model.addAttribute("years", years);
		//model.addAttribute("positionTypeCode", positionTypeCode);
		model.addAttribute("telCodes", telCodes);
		model.addAttribute("phoneCodes", phoneCodes);
		//model.addAttribute("businessCodes", businessCodes);
		return ViewUtils.view();
	}
	
	/**
	 * 회원등록 처리
	 * @param user
	 * @param userDetail
	 * @return
	 */
	@PostMapping("customer/create")
	public String customerCreateProcess(User user, UserDetail userDetail) {
	
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
		
		userService.insertUserAndUserDetail(user,userDetail);
		
		return ViewUtils.redirect("/opmanager/user/customer/list", MessageUtils.getMessage("M00288"));	// 등록되었습니다.

	}
	

	/**
	 * 회원 포인트 일괄지급.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "customer/point")
	@RequestProperty(layout = "base")
	public String point(Model model) {
		return ViewUtils.view();
	}

	/**
	 * 회원 포인트 일괄지급.
	 * 
	 * @param model
	 * @return
	 */
	@PostMapping("customer/point")
	@RequestProperty(layout = "base")
	public String pointAction(Model model,	Point point) {
		try {
			
			point.setUserId(UserUtils.getUserId());
			
			pointService.insertPointAllUser(point);

			
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}

		return ViewUtils.redirect("/opmanager/user/customer/point", "처리 되었습니다.", "self.close()");
	}
	
	
	/**
	 * 지정회원 포인트 일괄지급 (엑셀).
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "customer/point-by-excel")
	@RequestProperty(layout = "base")
	public String pointByExcel(Model model) {
		return ViewUtils.view();
	}
	
	/**
	 * 지정회원 포인트 일괄지급 처리 (엑셀).
	 * 
	 * @param model
	 * @return
	 */
	@PostMapping("customer/point-by-excel")
	@RequestProperty(layout = "base")
	public String pointByExcelProcess(Model model,	Point point) {
		try {
			
			pointService.insertPointByExcel(point);

			
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}

		return ViewUtils.redirect("/opmanager/user/customer/point-by-excel", "처리 되었습니다.", "self.close()");
	}

	@GetMapping("manager/list")
	public String managerLsit(
			@ModelAttribute("searchParam") UserSearchParam searchParam,
			Model model) {

		searchParam.setAuthority("ROLE_OPMANAGER");
		int managerCount = userService.getManagerCount(searchParam);

		Pagination pagination = Pagination.getInstance(managerCount);
		searchParam.setPagination(pagination);
		
		// 운영자 역할 목록
		SearchParam roleParam = new SearchParam();
		roleParam.setConditionType("ROLE_ADMIN");
		model.addAttribute("adminMenuRoles", roleService.getRoleListByParam(roleParam));
		
		List<User> managerList = userService.getManagerList(searchParam);
		model.addAttribute("list", managerList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("totalCount", managerCount);
 
		return "view";
	}

	/**
	 * 관리자 등록 폼
	 * 
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @return
	 */
	@GetMapping(value = "manager/create")
	public String managerCreate(RequestContext requestContext, Model model,
			User user, UserDetail userDetail) {

		SearchParam roleParam = new SearchParam();
		roleParam.setConditionType("ROLE_ADMIN");
		
		
		int userCount = userService.getUserCountByLoginId(user.getLoginId());
		
		if (userCount > 0) {
			return ViewUtils.redirect("/opmanager/user/manager/list", MessageUtils.getMessage("M00160"));	
		}
		
		userCount = userService.getUserCountByManagerId(user.getLoginId());
		
		if (userCount > 0) {
			return ViewUtils.redirect("/opmanager/user/manager/list", MessageUtils.getMessage("M01588"));	
		}

		model.addAttribute("formAction", "create");
		model.addAttribute("user", user);
		model.addAttribute("userDetails", userDetail);
		model.addAttribute("adminMenuRoles", roleService.getRoleListByParam(roleParam));
		model.addAttribute("requestContext", requestContext);

		return "view";
	}

	/**
	 * 관리자 등록 폼
	 * 
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @return
	 */
	@PostMapping("manager/create")
	public String managerCreateAction(RequestContext requestContext,
			Model model, User user, UserDetail userDetail) {
		
		UserSearchParam param = new UserSearchParam();
		param.setLoginId(user.getLoginId());
		
		int userCount = userService.getManagerCount(param);
		if (userCount > 0) {
			return ViewUtils.redirect("/opmanager/user/manager/list", MessageUtils.getMessage("M01588"));	// 사용할수 없는 아이디 
		}
		
		
		userService.insertManager(user);

		return ViewUtils.redirect("/opmanager/user/manager/list",
				MessageUtils.getMessage("M00288"));
		
		/*
		int userCount = userService.getUserCountByEmail(user.getEmail());
		
		if (userCount > 0) {
			return ViewUtils.redirect("/opmanager/user/manager/list", MessageUtils.getMessage("M00160"));	// 사용할수 없는 Email 주소.. 
		}
		
		userCount = userService.getUserCountByManagerId(user.getLoginId());
		if (userCount > 0) {
			return ViewUtils.redirect("/opmanager/user/manager/list", MessageUtils.getMessage("M01588"));	// 사용할수 없는 Email 주소.. 
		}
		
		userService.insertUserAndUserDetailByManager(user, userDetail);

		return ViewUtils.redirect("/opmanager/user/manager/list",
				MessageUtils.getMessage("M00288"));
		*/
	}

	/**
	 * 관리자 수정 폼
	 * 
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @return
	 */
	@GetMapping(value = "manager/edit/{userId}")
	public String managerEdit(RequestContext requestContext, Model model,
			@PathVariable("userId") long userId) {
		User user = userService.getManagerByUserId(userId);

		SearchParam roleParam = new SearchParam();
		roleParam.setConditionType("ROLE_ADMIN");
        String phoneNumber1 = "";
        String phoneNumber2 = "";
        String phoneNumber3 = "";

		if (user.getPhoneNumber() != null) {
            String[] phoneNumbers =ShopUtils.phoneNumberForDelimitedToStringArray(user.getPhoneNumber());
            phoneNumber1 = ValidationUtils.isNotNull(phoneNumbers[0]) ? phoneNumbers[0] : "";
            phoneNumber2 = ValidationUtils.isNotNull(phoneNumbers[1]) ? phoneNumbers[1] : "";
            phoneNumber3 = ValidationUtils.isNotNull(phoneNumbers[2]) ? phoneNumbers[2] : "";
        }

		model.addAttribute("formAction", "edit");
		model.addAttribute("user", user);
		model.addAttribute("adminMenuRoles", roleService.getRoleListByParam(roleParam));
		model.addAttribute("requestContext", requestContext);
        model.addAttribute("phoneNumber1", phoneNumber1);
        model.addAttribute("phoneNumber2", phoneNumber2);
        model.addAttribute("phoneNumber3", phoneNumber3);

		return "view";
	}

	/**
	 * 관리자 등록 폼
	 * 
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @return
	 */
	@PostMapping("manager/edit/{userId}")
	public String managerEditAction(RequestContext requestContext, Model model,
			User user, UserDetail userDetail) {
		
		/*
		User managerInfo = userService.getManagerByUserId(user.getUserId());
		
	
		if (!managerInfo.getEmail().equals(user.getEmail())) {
			int userCount = userService.getManagerCountByEmail(user.getEmail());
			
			if (userCount > 0) {
				return ViewUtils.redirect("/opmanager/user/manager/list", MessageUtils.getMessage("M00160"));	// 사용할수 없는 Email 주소.. 
			}
		}*/
		
		userService.updateManager(user);

		return ViewUtils.redirect("/opmanager/user/manager/list",
				MessageUtils.getMessage("M00289"));
	}

	/**
	 * 관리자 삭제
	 * 
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @return
	 */
	@GetMapping(value = "manager/delete/{userId}")
	public String managerDelete(RequestContext requestContext,
			@PathVariable("userId") long userId) {
		userService.deleteUser(userId);

		return ViewUtils.redirect("/opmanager/user/manager/list",
				MessageUtils.getMessage("M00205"));
	}

	/**
	 * 관리자 선택 삭제
	 * 
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @return
	 */
	@PostMapping("manager/delete/list")
	public JsonView managerDeleteList(RequestContext requestContext,
			ListParam listParam) {
		try {
			userService.deleteUserByListParam(listParam);
			return JsonViewUtils.success();

		} catch (Exception e) {
			return JsonViewUtils.failure(e.getMessage());
		}
	}

	@GetMapping("manager-role/list")
	public String managerRoleList(
			@ModelAttribute("roleParam") SearchParam roleParam,
			Model model) {
		roleParam.setConditionType("ROLE_ADMIN");
		
		int totalCount = roleService.getRoleCountByParam(roleParam);
		
		Pagination pagination = Pagination.getInstance(totalCount);
		roleParam.setPagination(pagination);

		model.addAttribute("list", roleService.getRoleListByParam(roleParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("totalCount", totalCount);
 
		return "view";
	}
	
	@GetMapping("manager-role/create")
	public String managerRoleCreate(Role role, Model model) {

		List<Menu> menuList = menuService.getAllMenuList();
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("role", role);
		return "view";
	}
	
	
	@PostMapping("manager-role/create")
	public String managerRoleCreateProcess(Role role, Model model) {
		roleService.insertRole(role);
		JavascriptUtils.alert("등록되었습니다.");
		return "redirect:/opmanager/user/manager-role/list";
	}
	
	
	@GetMapping(value = "manager-role/edit/{authority}")
	public String managerRoleEdit(RequestContext requestContext,
			@PathVariable("authority") String authority, Model model) {
		Role role = roleService.getRoleByAuthority(authority);
		
		List<Menu> menuList = menuService.getAllMenuList();
		
		List<MenuRight> menuRightList = menuService.getMenuRightListByAuthority(authority);
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("menuRightList", menuRightList);
		model.addAttribute("role", role);
		return "view:/user/manager-role/form";
	}
	
	@PostMapping("manager-role/edit/{authority}")
	public String managerRoleEditProcess(Role role,
			@PathVariable("authority") String authority, Model model) {
		roleService.updateRole(role);
		JavascriptUtils.alert("수정되었습니다.");
		return "redirect:/opmanager/user/manager-role/list";
	}
	
	/**
	 * 목록데이터 수정 - 선택삭제.
	 * @param requestContext
	 * @param itemListParam
	 * @return
	 */
	@PostMapping("manager-role/list/delete")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		roleService.deleteListData(listParam);
		return JsonViewUtils.success();  
	}
	
	
	/**
	 * /** Date 조회
	 * 
	 * @param requestContext
	 * @return
	 */
	@PostMapping("search-date")
	public JsonView searchDate(RequestContext requestContext, Model model,
			@RequestParam(value = "date", required = false) int date,
			@RequestParam(value = "type", required = false) String type) {

		int month = 0;
		int day = 0;

		if (type.equals("week")) {
			day = date;
		} else if (type.equals("month")) {
			month = date;
		}

		String nowDate = DateUtils.getToday(Const.DATE_FORMAT);
		String searchDate = DateUtils.addYearMonthDay(nowDate, 0, month, day);

		model.addAttribute("nowDate", nowDate);
		model.addAttribute("searchDate", searchDate);

		return JsonViewUtils.success();
	}

	/**
	 * 회원 정보 중복 확인
	 * @param requestContext
	 * @param model
	 * @param type
	 * @param value
	 * @return
	 */
	@PostMapping("user-availability-check")
	public JsonView userAvailabilityCheck(RequestContext requestContext, Model model,
			@RequestParam("type") String type, @RequestParam("value") String value) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		// 가입 불가 아이디 체크
		Config config = configService.getShopConfigCache(Config.SHOP_CONFIG_ID);
		
		boolean availability = true;
		if (config.getDeniedId() != null && !config.getDeniedId().equals("")) {
			String[] denyIds = StringUtils.tokenizeToStringArray(config.getDeniedId(), ",");
			
			for (String userId : denyIds) {
				if (userId.trim().equals("")) {
					continue;
				}
				
				if (userId.trim().equals(value.trim())) {
					availability = false;
					break;
				}
			}
		}
		
		if (availability == true) {
			
			int count = 0;
			if ("email".equals(type)) {
				count = userService.getUserCountByEmail(value);
			} else if ("nickname".equals(type)) {
				count = userService.getUserCountByNickname(value);
			} else if ("phoneNumber".equals(type)) {
				count = userService.getUserCountByPhoneNumber(value);
			}else {
				 throw new RuntimeException("잘못된 접근 - 정의되지 않은 타입");
			}
			
			if (count > 0) {
				availability = false;
			}
		}
		
		return JsonViewUtils.success(availability);
	}
	
	@RequestProperty(layout = "blank")
	@PostMapping("manager/find-user")
	public JsonView findManager(RequestContext requestContext, Model model,
			@RequestParam("loginId") String loginId) {
		int userCount = userService.getUserCountByManagerId(loginId);

		
		HashMap<String, Object> map = new HashMap<>();
		map.put("userCount", userCount);

		return JsonViewUtils.success(map);
	}

	/*
	 * 회원정보관리 (팝업창) - 기본정보
	 */
	@GetMapping("popup/details/{userId}")
	@RequestProperty(layout = "user_new")
	public String userDetails(@PathVariable("userId") long userId, Model model) {

		User user = userService.getUserByUserId(userId);

		if (user == null) {
			log.error("회원 정보가 없습니다.");
			throw new PageNotFoundException("회원 정보가 없습니다.");
		}
		
		PointParam pointParam = new PointParam();
		pointParam.setUserId(userId);
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		
		AvailablePoint avilablePoint2 = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);
		
		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("OPMANAGER");
		orderParam.setUserId(userId);
		orderParam.setItemsPerPage(1);
		
		Pagination pagination = Pagination.getInstance(orderService.getOrderCountByParam(orderParam), orderParam.getItemsPerPage());
		orderParam.setPagination(pagination);
		
		model.addAttribute("orderList", orderService.getOrderListByParam(orderParam));
		model.addAttribute("user", user);
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());

		return ViewUtils.view();
	}

	/**
	 * 회원정보관리 (팝업창) - 수정
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "popup/edit/{userId}")
	@RequestProperty(layout = "user_new")
	public String userEdit(@PathVariable("userId") long userId, Model model) {

		User user = userService.getUserByUserId(userId);

		String years = DateUtils.getToday("yyyy");

		model.addAttribute("user", user);
		model.addAttribute("years", years);
		model.addAttribute("groupList", groupService.getAllGroupList());
		model.addAttribute("userLevelGroup", userLevelService.getUserLevelGroupList(null));
		
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		AvailablePoint avilablePoint2 = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);
		
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());
		return ViewUtils.view();
	}

	@PostMapping("popup/edit/{userId}")
	@RequestProperty(layout = "user")
	public String modifyAction(Model model, User user, UserDetail userDetail,
							   @PathVariable("userId") long userId,
							   HttpServletRequest request) {

		userDetail.setBirthday(userDetail.getFullBirthday());
		user.setUserDetail(userDetail);
		
		try {
			userService.updateUserForAdmin(user);
			changeLogService.insertUserChangeLog(userId, request);
		} catch (UserException ue) {
			return ViewUtils.redirect("/opmanager/user/popup/edit/" + userId, ue.getErrorMessage());	// 사용할수 없는 Email 주소..
		}
		
		return ViewUtils.redirect("/opmanager/user/popup/edit/" + userId,
				MessageUtils.getMessage("M00289")); // 수정되었습니다. 
	}
	
	// 사용자별 SNS상태 popup 2017-06-05_seungil.lee
	@GetMapping("popup/sns-user/{userId}")
	@RequestProperty(layout="user_new")
	public String main(@PathVariable("userId") long userId, Model model) {
		UserSns userSnsData = new UserSns(userId);
//		UserSns userSns = userSnsService.getUserSns(userSnsData);

		String certified = "";
		List<UserSns> userSnsList = userSnsService.getUserSnsList(userSnsData);
		if(userSnsList != null && !userSnsList.isEmpty()){
			for(UserSns userData : userSnsList){
				if(userData.getCertifiedDate() != null && !"".equals(userData.getCertifiedDate())){
					certified = userData.getCertifiedDate();
				}
			}
		}
		model.addAttribute("userSnsList", userSnsList);
		model.addAttribute("certified", certified);
		model.addAttribute("userName", UserUtils.getUser().getUserName());
		model.addAttribute("isMypage", true);

		User user = userService.getUserByUserId(userId);
		
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		AvailablePoint avilablePoint2 = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);
		
		model.addAttribute("user", user);
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());
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
			userCount = userService.getUserCountByLoginId(loginId);
		}

		return JsonViewUtils.success(userCount);
	}
	
	/**
	 * 회원정보관리 (팝업창) - 수정
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "popup/delete/{userId}")
	@RequestProperty(layout = "user_new")
	public String userDelete(@PathVariable("userId") long userId, Model model) {

		User user = userService.getUserByUserId(userId);

		model.addAttribute("user", user);
		
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		AvailablePoint avilablePoint2 = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());
		
		return ViewUtils.view();
	}

	@PostMapping("popup/delete/{userId}")
	@RequestProperty(layout = "user")
	public String secedeAction(@PathVariable("userId") long userId, Model model,
			@RequestParam("leaveReason") String leaveReason) {

		User user = userService.getUserByUserId(userId);
		UserDetail userDetail = (UserDetail) user.getUserDetail();

		user.setLeaveDate(DateUtils.getToday());
		user.setStatusCode("3");

		userDetail.setUserId(user.getUserId());
		userDetail.setUseFlag("N");
		userDetail.setLeaveReason(leaveReason);
		user.setUserDetail(userDetail);
		userService.updateSecedeFrontUserAndUserDetail(user);

		return ViewUtils.redirect("/opmanager/user/customer/list", "회원탈퇴 처리되었습니다.", JavaScript.CLOSE_AND_OPENER_RELOAD); 
	}

	@GetMapping(value = "popup/password/{userId}")
	@RequestProperty(layout = "base")
	public String userPassword(@PathVariable("userId") long userId, Model model) {

		User user = userService.getUserByUserId(userId);
		if (user == null) {
			throw new PageNotFoundException();
		}
		
		String templateId = "pwsearch";
		
		model.addAttribute("smsConfig", smsConfigService.getSmsConfigByTemplateId(templateId));
		model.addAttribute("mailConfig", mailConfigService.getMailConfigByTemplateId(templateId));
		model.addAttribute("user", user);
		
		return ViewUtils.view();
	}

	@PostMapping("popup/password/{userId}")
	@RequestProperty(layout = "base")
	public String passwordAction(@PathVariable("userId") long userId, @RequestParam("password") String password) {

		User user = userService.getUserByUserId(userId);
		if (user == null) {
			throw new PageNotFoundException();
		}
		
		if (StringUtils.isEmpty(password)) {
			return ViewUtils.redirect("/opmanager/user/popup/password/" + userId, "비밀번호를 입력해 주세요.");
		}
		
		user.setPassword(password);
		userService.updateUserPasswod(user);
		
		return ViewUtils.redirect("/opmanager/user/popup/password/" + userId, "비밀번호 변경되었습니다.");
	}

	@GetMapping(value = "popup/password/{userId}/{sendType}")
	@RequestProperty(layout = "base")
	public String userPasswordAction(@PathVariable("userId") long userId, @PathVariable("sendType") String sendType) {

		UserSearchParam searchParam = new UserSearchParam();
		searchParam.setSendType(sendType);
		searchParam.setUserId(userId);

		try {
			userService.getUserPasswordSearch(searchParam);
			return ViewUtils.redirect("/opmanager/user/popup/password/" + userId, "비밀번호 변경되었습니다.");
		} catch(Exception e) {
			return ViewUtils.redirect("/opmanager/user/popup/password/" + userId, e.getMessage());
		}
	}
	
	/**
	 * 회원 쿠폰 내역 팝업
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@GetMapping("popup/coupon/{userId}")
	@RequestProperty(layout = "user_new")
	public String coupon(@PathVariable("userId") long userId, Model model,
						 boolean maskingCheck, HttpServletRequest request,
						 UserCouponParam userCouponParam) {
		User user = userService.getUserByUserId(userId);
		setPopupModel(model, userId, user, request, "회원상세-쿠폰조회");

		userCouponParam.setUserId(userId);

		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		model.addAttribute("couponList", couponService.getCouponUserListByUserForManager(userCouponParam));
		model.addAttribute("pagination", userCouponParam.getPagination());
		model.addAttribute("userId", userId);
		model.addAttribute("userCouponParam", userCouponParam);

		return ViewUtils.view();
	}

	/**
	 * 회원 적립금 내역 팝업
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@GetMapping("popup/point/{pointType}/{userId}")
	@RequestProperty(layout = "user_new")
	public String point(@PathVariable("userId") long userId,
			@PathVariable("pointType") String pointType,
			@ModelAttribute("pointParam") PointParam pointParam, Model model) {

		User user = userService.getUserByUserId(userId);

		pointParam.setUserId(userId);
		AvailablePoint avilablePoint = pointService
				.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		AvailablePoint avilablePoint2 = pointService
				.getAvailablePointByUserId(userId,PointUtils.SHIPPING_COUPON_CODE);

		AvailablePoint pageAvilablePoint = pointService
				.getAvailablePointByUserId(userId, pointType);

		int totalCount = pointService.getPointHistoryCountByParam(pointParam);
		List<PointHistory> list = null;
		Pagination pagination = null;
		if (totalCount > 0) {

			pagination = Pagination.getInstance(totalCount, 500);
			pointParam.setPagination(pagination);
			list = pointService.getPointHistoryListByParam(pointParam);

		}

		model.addAttribute("user", user);
		model.addAttribute("pointList", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());
		model.addAttribute("pointType", pointType);
		model.addAttribute("pageAvilablePoint", pageAvilablePoint.getAvailablePoint());
		return ViewUtils.getView("/user/popup/point");
	}
	
	/**
	 * 회원 포인트 일괄지급.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "popup/point-create/{pointType}/{userId}")
	@RequestProperty(layout = "base")
	public String pointCreate(Model model, @PathVariable("userId") long userId,
			@PathVariable("pointType") String pointType) {

		User user = userService.getUserByUserId(userId);

		model.addAttribute("user", user);
		model.addAttribute("pointType", pointType);
		return ViewUtils.getView("/user/popup/point-create");
	}

	/**
	 * 회원 포인트 일괄지급.
	 * 
	 * @param model
	 * @return
	 */
	@PostMapping("popup/point-create/{pointType}/{userId}")
	@RequestProperty(layout = "base")
	public String pointCreateAction(Model model, @PathVariable("userId") long userId,
			@PathVariable("pointType") String pointType, @RequestParam("mode") String mode, Point point) {
		try {
			
			if (mode.equals("1")) {

				point.setPointType(pointType);
				pointService.earnPoint("admin", point);

			} else if (mode.equals("2")) {
				
				PointUsed pointUsed = new PointUsed();
				pointUsed.setOrderCode(point.getOrderCode());
				pointUsed.setDetails(point.getReason());
				pointUsed.setPoint(point.getPoint());
				
				pointService.deductedPoint(pointUsed, userId, pointType);
			}
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}

		return ViewUtils.redirect("/opmanager/user/popup/point-create/" + pointType + "/" + userId,
				MessageUtils.getMessage("M01221"), "self.close();opener.location.reload();"); //처리되었습니다. 
	}
	

	/*
	 * 회원정보관리 (팝업창) - 주문내역
	 */
	@GetMapping("popup/order/{userId}")
	@RequestProperty(layout = "user")
	public String order(@PathVariable("userId") long userId, Model model,
			@ModelAttribute OrderParam orderParam) {
		
		User user = userService.getUserByUserId(userId);
		
		/*orderParam.setUserId(userId);
		
		List<Code> searchOrderStatusList = CodeUtils.getCodeList("ORDER_SEARCH_STATUS");
		List<String> orderStatusList = null; 
		
		if (ValidationUtils.isNotNull(orderParam.getSearchOrderStatus())) {
			orderStatusList = new ArrayList<>();
			for (String s : orderParam.getSearchOrderStatus()) {
				for (Code c : searchOrderStatusList) {
					if (c.getValue().equals(s)) {
						orderStatusList.add(c.getDetail());
						break;
					}
				}
			}
		}
		
		orderParam.setOrderStatus(orderStatusList);
		
		orderParam.setVendorId(UserUtils.getVendorId());
		
		int totalCount = orderService.getOrderCountByParam(orderParam);
		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());
		orderParam.setPagination(pagination);
		orderParam.setLanguage(CommonUtils.getLanguage());

		model.addAttribute("categoryCategoriesTeamList", categoryCategoriesTeamGroupService.getCategoriesTeamList());
		model.addAttribute("pagination", pagination);
		model.addAttribute("orderList", orderService.getOrderListByParam(orderParam));
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("payTypeList", CodeUtils.getCodeList("ORDER_PAY_TYPE"));
		
		model.addAttribute("title", MessageUtils.getMessage("M00055"));
		model.addAttribute("searchOrderStatusList", searchOrderStatusList);
		model.addAttribute("user", user);
		model.addAttribute("userOrderCountInfo", null);*/
		
		return ViewUtils.redirect("/opmanager/order/list/1?where=USER_ID&query=" + user.getLoginId());
	}

	@GetMapping("popup/delivery-list/{userId}")
	@RequestProperty(layout = "user_new")
	public String deliveryList (Model model, @PathVariable("userId") long userId) {
		
		User user = userService.getUserByUserId(userId);

		model.addAttribute("list", userDeliveryService.getUserDeliveryList(userId));
		model.addAttribute("user", user);
		model.addAttribute("userId", userId);
		
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		AvailablePoint avilablePoint2 = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());
		return ViewUtils.view();
	}
	
	@GetMapping("popup/delivery-write/{userId}")
	@RequestProperty(layout = "user_new")
	public String deliveryWrite (Model model, @PathVariable("userId") long userId) {
		
		User user = userService.getUserByUserId(userId);

		UserDelivery userDelivery = new UserDelivery();
		userDelivery.setUserId(userId);
		
		model.addAttribute("user", user);
		model.addAttribute("userDelivery", userDelivery);
		
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());

		model.addAttribute("action", "/opmanager/user/popup/delivery-write");
		return ViewUtils.view();
	}
	
	/**
	 * 배송지 등록 처리
	 * @param model
	 * @param userDelivery
	 * @return
	 */
	@PostMapping("popup/delivery-write")
	public String writeAction(Model model, UserDelivery userDelivery) {

		if (userDelivery.getUserId() == 0) {
			return "xxx";
		}
		
		userDeliveryService.insertUserDelivery(userDelivery);
		return ViewUtils.redirect("/opmanager/user/popup/delivery-list/" + userDelivery.getUserId(), MessageUtils.getMessage("M00288")); // 등록 되었습니다.
	}
	
	/**
	 * 배송지 수정 폼
	 * @param userDeliveryId
	 * @param model
	 * @return
	 */
	@GetMapping(value="popup/delivery-edit/{userId}/{id}")
	@RequestProperty(layout = "user_new")
	public String edit(@PathVariable("id") int userDeliveryId, Model model,
			@PathVariable("userId") long userId) {

		User user = userService.getUserByUserId(userId);
		
		model.addAttribute("action", "/opmanager/user/popup/delivery-edit");
		model.addAttribute("userDelivery", userDeliveryService.getUserDeliveryById(userId, userDeliveryId));
		model.addAttribute("user", user);
		return ViewUtils.getView("/user/popup/delivery-write");
	}
	
	/**
	 * 배송지 수정 처리
	 * @param model
	 * @param userDelivery
	 * @return
	 */
	@PostMapping("popup/delivery-edit")
	public String editAction(UserDelivery userDelivery,
			@RequestParam(value="userDeliveryId", required=true) int userDeliveryId,
			@RequestParam(value="userId", required=true) long userId) {
		
		userDeliveryService.updateUserDelivery(userDelivery);
		
		return ViewUtils.redirect("/opmanager/user/popup/delivery-edit/" + userId + "/" + userDeliveryId,
				MessageUtils.getMessage("M00289")); // 수정 되었습니다.
	}

	/**
	 * 삭제, 기본 배송지 설정등
	 * @param userDeliveryParam
	 * @param mode
	 * @param userDeliveryId
	 * @param userId
	 * @return
	 */
	@PostMapping("popup/delivery-list-action/{mode}")
	public String listAction(UserDeliveryParam userDeliveryParam, @PathVariable("mode") String mode,
							 @RequestParam(value="userDeliveryId", required=true) int userDeliveryId,
							 @RequestParam(value="userId", required=true) long userId) {

		userDeliveryParam.setMode(mode);
		userDeliveryService.listAction(userDeliveryParam);
		return ViewUtils.redirect("/opmanager/user/popup/delivery-list/" + userId);
	}
	
	/**
	 * 탈퇴한 회원 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("secede-user/list")
	public String secedeUserList(
			@ModelAttribute("searchParam") UserSearchParam searchParam,
			Model model) {
		
		int count = userService.getSecedeUserCount(searchParam);

		Pagination pagination = Pagination.getInstance(count, 10);
		searchParam.setPagination(pagination);

		model.addAttribute("list", userService.getSecedeUserList(searchParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("count", count);
		
		return ViewUtils.view();
	}

	/**
	 * 회원 상담내역
	 * @param userId
	 * @param param
	 * @param model
	 * @return
	 */
	@GetMapping("popup/claim-memo-list/{userId}")
	@RequestProperty(layout = "user_new")
	public String claimList(@PathVariable("userId") long userId, ClaimMemoParam param, Model model) {
		
		User user = userService.getUserByUserId(userId);
		if (user == null) {
			throw new PageNotFoundException();
		}
		
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		AvailablePoint avilablePoint2 = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);
		
		model.addAttribute("user", user);
		model.addAttribute("userId", userId);
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		return "view:/user/popup/claim-memo-list";
	}

	/**
	 * 회원 상담내역 리스트
	 * @param requestContext
	 * @param param
	 * @param model
	 * @return
	 */
	@GetMapping(value="popup/claim-memo/list")
	@RequestProperty(layout="blank")
	public String claimMemoList(RequestContext requestContext, @ModelAttribute("searchParam") ClaimMemoParam param, Model model) {
		
		ClaimMemoParam totalLog = new ClaimMemoParam();
		totalLog.setUserId(param.getUserId());		
		int totalCount = claimService.getClaimMemoCount(totalLog);
		
		int Count = claimService.getClaimMemoCount(param);
		
		Pagination pagination = Pagination.getInstance(Count , 5);
		pagination.setLink("javascript:claimMemoList([page])");
		param.setPagination(pagination);
		
		List<ClaimMemo> list = claimService.getClaimMemoList(param);
		
		
		String today = DateUtils.getToday(Const.DATE_FORMAT);

		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month2", DateUtils.addYearMonthDay(today, 0, -2, 0));
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", list);
		return "view:/user/popup/memo/list";
	}
	
	/**
	 * 회원 메일 발송 내역
	 * @param userId
	 * @param model
	 * @param SendMailLogParam
	 * @return
	 */
	@GetMapping("popup/send-mail-log-list/{userId}")
	@RequestProperty(layout = "user_new")
	public String sendMailList(@PathVariable("userId") long userId, SendMailLogParam sendMailLogParam, Model model) {
		
		User user = userService.getUserByUserId(userId);
		if (user == null) {
			throw new PageNotFoundException();
		}
		
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		AvailablePoint avilablePoint2 = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);
		
		
		MailTemplate mailTemplate = new MailTemplate();
		model.addAttribute("mailTemplateCodeList", mailTemplate.getTemplateCodes());
		
		model.addAttribute("user", user);
		model.addAttribute("userId", userId);
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		return "view:/user/popup/mail-log-list";
	}
	
	/**
	 * 회원 메일 발송 내역 리스트
	 * @param requestContext
	 * @param SendMailLogParam
	 * @param model
	 * @return
	 */
	@GetMapping(value="popup/send-mail-log/list")
	@RequestProperty(layout="blank")
	public String SendMailLogList(RequestContext requestContext, @ModelAttribute("searchParam") SendMailLogParam sendMailLogParam, Model model) {
		
		SendMailLogParam totalLog = new SendMailLogParam();
		totalLog.setUserId(sendMailLogParam.getUserId());		
		int totalCount = sendMailLogService.getSendMailLogCount(totalLog);
		
		int Count = sendMailLogService.getSendMailLogCount(sendMailLogParam);
		
		Pagination pagination = Pagination.getInstance(Count , 5);
		pagination.setLink("javascript:SendMailLogList([page])");
		sendMailLogParam.setPagination(pagination);
		
		List<SendMailLog> list = sendMailLogService.getSendMailLogList(sendMailLogParam);	
		String today = DateUtils.getToday(Const.DATE_FORMAT);

		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month2", DateUtils.addYearMonthDay(today, 0, -2, 0));
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", list);
		return "view:/user/popup/maillog/list";
	}
	
	/**
	 * 회원 Sms 발송 내역
	 * @param userId
	 * @param model
	 * @param SendSmsLogParam
	 * @return
	 */
	@GetMapping("popup/send-sms-log-list/{userId}")
	@RequestProperty(layout = "user_new")
	public String sendSmsList(@PathVariable("userId") long userId, SendSmsLogParam sendSmsLogParam, Model model) {
		
		User user = userService.getUserByUserId(userId);
		if (user == null) {
			throw new PageNotFoundException();
		}
		
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		AvailablePoint avilablePoint2 = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);
		
		SmsTemplate smsTemplate = new SmsTemplate();
		model.addAttribute("smsTemplateCodeList", smsTemplate.getTemplateCodes());
		
		model.addAttribute("user", user);
		model.addAttribute("userId", userId);
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());
		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		return "view:/user/popup/sms-log-list";
	}
	
	
	
	/**
	 * 회원 메일 발송 내역 리스트
	 * @param requestContext
	 * @param ClaimMemoParam
	 * @param model
	 * @return
	 */
	@GetMapping(value="popup/send-sms-log/list")
	@RequestProperty(layout="blank")
	public String SendSmsLogList(RequestContext requestContext, @ModelAttribute("searchParam") SendSmsLogParam sendSmsLogParam,
								 Model model,
								 @PageableDefault(size = 10, sort = "created", direction = Sort.Direction.DESC) Pageable pageable) {

		SendSmsLogParam totalLog = new SendSmsLogParam();
		totalLog.setUserId(sendSmsLogParam.getUserId());
		UmsSendLogDto umsSendLogDto = new UmsSendLogDto();
        int totalCount =  (int)umsSendLogRepository.count(umsSendLogDto.getPredicateForInit(sendSmsLogParam));

		Predicate predicate= umsSendLogDto.getPredicate(sendSmsLogParam);
		Page<UmsSendLog> umsList = umsSendLogRepository.findAll(predicate, pageable);
		int count = (int)umsList.getTotalElements();

		Pagination pagination = Pagination.getInstance(count, pageable.getPageSize());
		pagination.setLink("javascript:SendSmsLogList([page])");

		String today = DateUtils.getToday(Const.DATE_FORMAT);

		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month2", DateUtils.addYearMonthDay(today, 0, -2, 0));
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("count", count);
		model.addAttribute("list", umsList.getContent());
		SmsTemplate smsTemplate = new SmsTemplate();
		model.addAttribute("smsTemplateCodeList", smsTemplate.getTemplateCodes());
		return "view:/user/popup/smslog/list";
	}
	
	/**
	 * 그룹 설정 팝업
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestProperty(layout = "base")
	@GetMapping("/popup/group")
	public String customerGroup(UserGroup userGroup, Model model) {
		
		if(userGroup.getUserIds() != null){
			model.addAttribute("userIds", userGroup.getUserIds());
		}
		
		long userId = userGroup.getUserId();
		
		model.addAttribute("user", userService.getUserByUserId(userId));
		model.addAttribute("groupList", groupService.getAllGroupList());
		//model.addAttribute("checkGroupList", groupService.getGroupIdByuserId(userId));
		model.addAttribute("userLevelGroup", userLevelService.getUserLevelGroupList(null));
		model.addAttribute("userId", userId);
		
		return ViewUtils.view();
	}

	@PostMapping("/group-setting")
	public String groupSetting(Model model, UserGroup userGroup) {
		
		try {
			userGroupService.userGroupSetting(userGroup);
			return ViewUtils.redirect("/opmanager/user/popup/group", "설정되었습니다.", "opener.location.reload();self.close()");
		} catch (Exception e) {
			return ViewUtils.redirect("/opmanager/user/popup/group", e.getMessage(), "opener.location.reload();self.close()");
		}
	}
	
	/**
	 * 회원별 SMS 발송 Form
	 * @param userSms
	 * @param model
	 * @param userId
	 * @return
	 */
	@GetMapping("/popup/send-sms/{userId}")
	@RequestProperty(layout="base")
	public String sendSms(UserSms userSms, Model model, @PathVariable("userId") long userId) {
		
		User user = userService.getUserByUserId(userId);
		if (user == null) {
			throw new UserException("잘못된 접근입니다.");
		}
		
		SmsConfig smsConfig = smsConfigService.getSmsConfigByTemplateId("user_sms");
		if (smsConfig != null) {
			userSms.setTitle(smsConfig.getBuyerTitle());
			userSms.setContent(smsConfig.getBuyerContent());
			userSms.setSmsType(smsConfig.getSmsType());
		}

		model.addAttribute("smsConfig", smsConfig);
		model.addAttribute("user", user);
		model.addAttribute("userSms", userSms);
		return "view:/user/popup/send-sms";
	}
	
	@PostMapping("/popup/send-sms/{userId}")
	public String sendSmsAction(UserSms postUserSms, Model model, @PathVariable("userId") long userId) {
		
		User user = userService.getUserByUserId(userId);
		if (user == null) {
			throw new UserException("잘못된 접근입니다.");
		}

		UserDetail userDetail = (UserDetail) user.getUserDetail();

		if (userDetail == null) {
			userDetail = new UserDetail();
		}

		String templateId = "user_sms";
		SmsConfig smsConfig = smsConfigService.getSmsConfigByTemplateId(templateId);
		if (smsConfig != null) {
			if (StringUtils.isEmpty(userDetail.getPhoneNumber()) == false) {
				
				smsConfig.setSmsType(postUserSms.getSmsType());
				smsConfig.setBuyerTitle(postUserSms.getTitle());
				smsConfig.setBuyerContent(postUserSms.getContent());
				
				UserSms userSms = new UserSms(user, smsConfig, ShopUtils.getConfig());
				smsConfig = userSms.getSmsConfig();
				
				SendSmsLog sendSmsLog = new SendSmsLog();
				sendSmsLog.setSendType(templateId);
				sendSmsLog.setUserId(userId);
				
				sendSmsLogService.sendSms(smsConfig, sendSmsLog, userDetail.getPhoneNumber());
				
				return ViewUtils.redirect("/opmanager/user/popup/send-sms/"+userId, "발송되었습니다.", "self.close()");
			}
		}

		return ViewUtils.redirect("/opmanager/user/popup/send-sms/"+userId, "SMS 발송에 실패 하였습니다.", "self.close()");
	}
	
	/**
	 * 선택회원 포인트 지급.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "customer/point-pay")
	@RequestProperty(layout = "base")
	public String pointPay(Point point, Model model) {
		if(point.getUserIds() != null){
			model.addAttribute("userIds", point.getUserIds());
		}
		return ViewUtils.view();
	}
	
	/**
	 * 선택회원 포인트 지급 처리.
	 * 
	 * @param model
	 * @return
	 */
	@PostMapping("point-setting")
	public String pointPayProcess(Model model,	Point point) {
		try {
			pointService.insertPointPay(point);

		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}

		return ViewUtils.redirect("/opmanager/user/customer/point-pay", "처리 되었습니다.", "self.close()");
	}
	
	/**
	 * Shadow Login
	 * @param loginId
	 * @return
	 */
	@PostMapping("/shadow-login")
	public JsonView shadowLogin(@RequestParam("loginId") String loginId) {
		
		HashMap<String, String> map = new HashMap<>();
		map.put("shadowLoginId", ShadowUtils.getShadowLoginKey(loginId, UserUtils.getLoginId()));
		map.put("shadowLoginPassword", ShadowUtils.getShadowLoginPassword(loginId));
		map.put("shadowLoginSignature", ShadowUtils.getShadowLoginSignature(loginId));
		
		return JsonViewUtils.success(map);
	}

    /**
     * 관리자 임시 비밀번호 발급 yulsun.yoo [2018-10-18]
     * @param userId
     * @return
     */
	@PostMapping("/manager/tempPassword")
    public JsonView tempPassword (@RequestParam("userId") Long userId) {

	    try {
            userService.updateTempPasswordForManager(userId);
        }catch (Exception e) {
	        return JsonViewUtils.failure("임시비밀번호 발송이 실패하였습니다.");
        }

	    return JsonViewUtils.success();
    }

	private void setPopupModel(Model model, long userId, User user, HttpServletRequest request, String description) {

		model.addAttribute("user", user);

		PointParam pointParam = new PointParam();
		pointParam.setUserId(userId);
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		AvailablePoint avilablePoint2 = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);

		model.addAttribute("couponCount", couponService.getCouponUserCountByUserId(userId));
		model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		model.addAttribute("avilablePoint2", avilablePoint2.getAvailablePoint());
	}
}
