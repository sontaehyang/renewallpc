package saleson.shop.user;

import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.isms.ConfigIsmsService;
import com.onlinepowers.framework.security.userdetails.OpUserDetails;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.security.userdetails.UserRole;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.domain.ListParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import saleson.common.Const;
import saleson.common.enumeration.UmsType;
import saleson.common.notification.UnifiedMessagingService;
import saleson.common.opmanager.count.OpmanagerCount;
import saleson.common.utils.RandomStringUtils;
import saleson.common.utils.*;
import saleson.model.Ums;
import saleson.shop.config.ConfigMapper;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.ChosenUser;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.group.GroupMapper;
import saleson.shop.group.domain.Group;
import saleson.shop.group.support.GroupSearchParam;
import saleson.shop.log.ChangeLogService;
import saleson.shop.mailconfig.MailConfigService;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.MemberJoinMail;
import saleson.shop.mailconfig.support.MemberSleepMail;
import saleson.shop.mailconfig.support.PwsearchMail;
import saleson.shop.order.domain.Buyer;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.AvailablePoint;
import saleson.shop.point.domain.Point;
import saleson.shop.sendmaillog.SendMailLogService;
import saleson.shop.sendmaillog.domain.SendMailLog;
import saleson.shop.sendsmslog.SendSmsLogService;
import saleson.shop.sendsmslog.domain.SendSmsLog;
import saleson.shop.smsconfig.SmsConfigService;
import saleson.shop.smsconfig.domain.SmsConfig;
import saleson.shop.ums.UmsService;
import saleson.shop.ums.support.MemberJoin;
import saleson.shop.ums.support.Pwsearch;
import saleson.shop.user.domain.AuthUserInfo;
import saleson.shop.user.domain.ManagerLogin;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.user.support.UserSearchParam;
import saleson.shop.usergroup.UserGroupMapper;
import saleson.shop.usergroup.domain.UserGroupLog;
import saleson.shop.userlevel.UserLevelMapper;
import saleson.shop.userlevel.UserLevelService;
import saleson.shop.userlevel.domain.UserLevel;
import saleson.shop.userlevel.domain.UserLevelLog;
import saleson.shop.userlevel.support.UserLevelSearchParam;
import saleson.shop.userrole.UserRoleService;
import saleson.shop.usersns.UserSnsService;
import saleson.shop.usersns.domain.UserSns;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserMapper userMapper;

	@Autowired
	SequenceService sequenceService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	UserLevelService userLevelService;

	@Autowired
	ConfigService configService;

	@Autowired
	MailConfigService mailConfigService;

	@Autowired
	SendMailLogService sendMailLogService;

	@Autowired
	private PointService pointService;

	@Autowired
	private SendSmsLogService sendSmsLogService;

	@Autowired
	private SmsConfigService smsConfigService;

	@Autowired
	private UserLevelMapper userLevelMapper;

	@Autowired
	private UserGroupMapper userGroupMapper;

	@Autowired
	private GroupMapper groupMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ConfigMapper configMapper;

	@Autowired
	private CouponService couponService;

	@Autowired
	private UserSnsService userSnsService;

	@Autowired
	private ConfigIsmsService configIsmsService;

    @Autowired
	private ManagerLoginRepository managerLoginRepository;

    @Autowired
	private UmsService umsService;

    @Autowired
	private UnifiedMessagingService unifiedMessagingService;

    @Autowired
	private ChangeLogService changeLogService;

	@Override
	public void setMypageUserInfoForFront(Model model) {
		UserCouponParam userCouponParam = new UserCouponParam();
		userCouponParam.setUserId(UserUtils.getUserId());

		int totalCount = couponService.getDownloadUserCouponCountByUserCouponParam(userCouponParam);

		// 총 사용가능 포인트
		AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(UserUtils.getUserId(), PointUtils.DEFAULT_POINT_CODE);

		// 총 사용가능 배송 쿠폰
		AvailablePoint avilableShippingCoupon = pointService.getAvailablePointByUserId(UserUtils.getUserId(), PointUtils.SHIPPING_COUPON_CODE);

		// 총 사용가능 캐시
		AvailablePoint avilableEmoney = pointService.getAvailablePointByUserId(UserUtils.getUserId(), PointUtils.EMONEY_CODE);

		String today = DateUtils.getToday(Const.DATE_FORMAT);

		model.addAttribute("userCouponCount", totalCount);
		model.addAttribute("userShippingCount", avilableShippingCoupon.getAvailablePoint());
		model.addAttribute("userPoint", avilablePoint.getAvailablePoint());
		model.addAttribute("userEmoney", avilableEmoney.getAvailablePoint());

		// 유저 레벨
		model.addAttribute("userLevel", UserUtils.getUserDetail().getUserlevel());
		model.addAttribute("today", today);
	}

	@Override
	public void updateUserPasswod(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userMapper.updateUserPasswordByUserId(user);

	}

	@Override
	public UserDetail getUserDetail(long userId) {
		return userMapper.getUserDetail(userId);
	}

	@Override
	public int getUserCount(UserSearchParam searchParam) {
		return userMapper.getUserCount(searchParam);

	}

	@Override
	public int getUserCountByPhoneNumber(String phoneNumber) {
		return userMapper.getUserCountByPhoneNumber(phoneNumber);
	}


	@Override
	public List<User> getUserList(UserSearchParam searchParam) {

		List<User> list = userMapper.getUserList(searchParam);
		for(User user : list) {
			// 엑셀 다운로드가 아닌 경우 무조건 마스킹
			if(!(searchParam.getConditionType()!=null && searchParam.getConditionType().equals("EXCEL_DOWNLOAD"))){
				privacyMaskingDataSet(user);
				// 엑셀 다운로드 이면서 엑셀+개인정보 권한이 아닌 경우 마스킹
			} else if(!(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS"))){
				privacyMaskingDataSet(user);
			}
		}
		return list;

	}

	private void privacyMaskingDataSet(User user){
		UserDetail detail = (UserDetail)user.getUserDetail();
		String telNo = detail.getTelNumber();		// 전화번호
		String phoneNo = detail.getPhoneNumber();	// 휴대번호
		String birthDay = detail.getBirthday();		// 생일
		String email = user.getEmail();				// 이메일
		String name = user.getUserName();			// 이름
		String zipCode = detail.getPost();			// 우편번호
		String address = detail.getAddress(); // 주소
		String addressDetail = detail.getAddressDetail(); // 상세주소

		if(user.getUserName() != null && user.getUserName().length() > 1) {
			user.setUserName(UserUtils.masking(user.getUserName(), "name"));
		}
		if(address != null){
			detail.setAddress(UserUtils.reMasking(address, "addr"));
		}
		if(addressDetail != null){
			detail.setAddressDetail(UserUtils.reMasking(addressDetail, "addrDetail"));
		}
		if(zipCode != null){
			detail.setPost(UserUtils.reMasking(zipCode, "zipCode"));
		}
		if(name != null){
			user.setUserName(UserUtils.reMasking(name, "name"));
		}
		if(email != null){
			user.setEmail(UserUtils.reMasking(email, "email"));
		}
		if(telNo != null && telNo.length() > 9) {
			detail.setTelNumber(UserUtils.reMasking(telNo, "tel"));
		}
		if(phoneNo != null && phoneNo.length() > 9) {
			detail.setPhoneNumber(UserUtils.reMasking(phoneNo, "tel"));
		}
		if(birthDay != null) {
			detail.setBirthday(UserUtils.masking(birthDay, "day"));
		}
	}

	@Override
	public void insertManager(User user) {

		long userId = sequenceService.getLong("OP_USER");
		user.setUserId(userId);
		user.setStatusCode("9");

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setPasswordExpiredDate(this.getPasswordExpiredDate());
		userMapper.insertManager(user);


		List<UserRole> userRoles = user.getUserRoles();

		if (ValidationUtils.isNotNull(user.getUserRoles())) {
			for (UserRole userRole : userRoles) {
				if (ValidationUtils.isNotNull(userRole.getAuthority())) {
					userRole.setUserId(userId);
					userRole.setAuthority(userRole.getAuthority());
					userRoleService.insertUserRole(userRole);
				}
			}
		}

		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setAuthority("ROLE_OPMANAGER");
		userRoleService.insertUserRole(userRole);
	}

	@Override
	public void insertUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (StringUtils.isEmpty(user.getPasswordExpiredDate())) {
			user.setPasswordExpiredDate(this.getPasswordExpiredDate());
		}

		if (user.getLoginId() == null || "".equals(user.getLoginId())) {
			user.setLoginId("rndm" + (int) (System.currentTimeMillis() / 1000L));
		}

		// 이메일이 입력되지 않았을 경우 로그인 아이디를 이메일 컬럼에 insert
		if(StringUtils.isEmpty(user.getEmail())) {
			user.setEmail(user.getLoginId());
		}

		userMapper.insertUser(user);
	}

	@Override
	public void insertUserAndUserDetailByManager(User user, UserDetail userDetail){

		long userId = sequenceService.getLong("OP_USER");
		user.setUserId(userId);
		userDetail.setUserId(userId);
		//userDetail.setVenderId(1000);

		this.insertManager(user);
		this.insertUserDetail(userDetail);


		List<UserRole> userRoles = user.getUserRoles();

		if (ValidationUtils.isNotNull(user.getUserRoles())) {
			for (UserRole userRole : userRoles) {
				if (ValidationUtils.isNotNull(userRole.getAuthority())) {
					userRole.setUserId(userId);
					userRole.setAuthority(userRole.getAuthority());
					userRoleService.insertUserRole(userRole);
				}
			}
		}

		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setAuthority("ROLE_OPMANAGER");
		userRoleService.insertUserRole(userRole);

	}

	@Override
	public void insertUserAndUserDetail(User user, UserDetail userDetail){

		/*
		//이부분 Controller로 이동
		long userId = sequenceService.getLong("OP_USER");
		user.setUserId(userId);

		userDetail.setUserId(userId);*/
		long userId = user.getUserId();

		userDetail.setPoint(ShopUtils.getConfig().getPointJoin());
		userDetail.setBirthday(userDetail.getFullBirthday());
		//userDetail.setBusinessNumber(userDetail.getFullBusinessNumber());

		this.insertUser(user);
		this.insertUserDetail(userDetail);

		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setAuthority("ROLE_USER");
		userRoleService.insertUserRole(userRole);

		user = getUserByUserId(userId);

		// 회원 등급 적용
		userLevelService.setUserLevel(user);

		// 포인트 지급
		Point point = new Point();
		point.setUserId(userId);
		point.setPointType(PointUtils.DEFAULT_POINT_CODE);
		pointService.earnPoint("join", point);

		String templateId = "member_join";

		MailConfig checkMailConfig = mailConfigService.getMailConfigByTemplateId(templateId);
		Ums ums = umsService.getUms(templateId);

		if (checkMailConfig != null) {
			sendMail(user, templateId);
		}

		sendSms(ums, user, templateId);

	}

	@Override
	public void insertUserAndUserDetailForSns(User user, UserDetail userDetail, UserSns UserSns) {
		long userId = sequenceService.getLong("OP_USER");
		user.setUserId(userId);
		user.setPasswordExpiredDate("99991231");

		userDetail.setUserId(userId);
		userDetail.setPoint(ShopUtils.getConfig().getPointJoin());
		userDetail.setBirthday(userDetail.getFullBirthday());
		//userDetail.setBusinessNumber(userDetail.getFullBusinessNumber());

		this.insertUser(user);
		this.insertUserDetail(userDetail);

		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setAuthority("ROLE_USER");
		userRoleService.insertUserRole(userRole);

		user = getUserByUserId(userId);

		// 회원 등급 적용
		userLevelService.setUserLevel(user);

		// 포인트 지급
		Point point = new Point();
		point.setUserId(userId);
		point.setPointType(PointUtils.DEFAULT_POINT_CODE);
		pointService.earnPoint("join", point);

		UserSns.setUserId(userId);
		// sns아이디 정보 삽입
		userSnsService.insertUserSns(UserSns);
	}

	private void sendMail(User user, String templateId) {

		MailConfig mailConfig = null;

		if("member_join".equals(templateId)){

			MemberJoinMail memberJoin = new MemberJoinMail(user, mailConfigService.getMailConfigByTemplateId(templateId));
			mailConfig = memberJoin.getMailConfig();

		} else {

			PwsearchMail pwsearch = new PwsearchMail(user, mailConfigService.getMailConfigByTemplateId(templateId));
			mailConfig = pwsearch.getMailConfig();

		}

		SendMailLog sendMailLog = new SendMailLog();
		sendMailLog.setReceiveLoginId(""+user.getUserId());
		sendMailLog.setSendType(templateId);

		sendMailLogService.sendMail(mailConfig, sendMailLog, user.getEmail(), user.getUserName());

	}

	private void sendSms(Ums ums, User user, String templateId){

		try {

			UserDetail userDetail = (UserDetail)user.getUserDetail();

			String phoneNumber = userDetail.getPhoneNumber();

			if (StringUtils.isNotEmpty(phoneNumber)) {

				Config config = ShopUtils.getConfig();

				// 가입 완료
				if ("member_join".equals(templateId)) {
					unifiedMessagingService.sendMessage(new MemberJoin(ums, user, config, phoneNumber));
				}

				// PW 확인 수정 KSH 2019.06.11
				if ("pwsearch".equals(templateId)) {
					unifiedMessagingService.sendMessage(new Pwsearch(ums, phoneNumber, user));

				}

			}

		} catch (Exception e) {
			log.error("User sendSms Error templateId- > [{}] userId -> [{}]", templateId, user.getUserId(), e);
		}
	}


	@Override
	public void insertUserDetail(UserDetail userDetail) {
		userMapper.insertUserDetail(userDetail);
	}
	
	
	@Override
	public void insertUserRole(UserRole userRole) {
		userMapper.insertUserRole(userRole);
	}
	
	@Override
	public void updateUser(User user) {
		user = passwordFlagResult(user);
		userMapper.updateUser(user);
	}
	
	@Override
	public void updateUserDetail(UserDetail userDetail) {
		if (userMapper.getUserDetailCountByUserId(userDetail.getUserId()) == 0) {
			userMapper.insertUserDetail(userDetail);
		} else {
			userMapper.updateUserDetail(userDetail);
		}
	}
	
	@Override
	public void updateUserRole(UserRole userRole) {
		userMapper.updateUserRole(userRole);
	}
	
	@Override
	public void updateUserAndUserDetail(User user, UserDetail userDetail) {

		
		
		
		this.updateUser(user);
		this.updateUserDetail(userDetail);
		
		userRoleService.deleteUserRole(user.getUserId());
		
		List<UserRole> userRoles = user.getUserRoles();
		
		if (ValidationUtils.isNotNull(user.getUserRoles())) {
			for (UserRole userRole : userRoles) {
				if (ValidationUtils.isNotNull(userRole.getAuthority())) {
					userRole.setUserId(user.getUserId());
					userRole.setAuthority(userRole.getAuthority());
					userRoleService.insertUserRole(userRole);
				}
			}
		}
			
		UserRole userRole = new UserRole();
		userRole.setUserId(user.getUserId());
		userRole.setAuthority("ROLE_OPMANAGER");
		userRoleService.insertUserRole(userRole);
	}
	
	@Override
	public void deleteUser(long userId) {
		userMapper.deleteUser(userId);
	}
	
	@Override
	public void deleteUserDetail(long userId) {
		userMapper.deleteUserDetail(userId);
	}
	
	@Override
	public void deleteUserRole(long userId) {
		userMapper.deleteUserRole(userId);
	}
	
	
	@Override
	public int getUserManagerCount(UserSearchParam searchParam) {
		return userMapper.getUserManagerCount(searchParam);
	}

	@Override
	public int getUserCountByLoginId(String loginId) {
		return userMapper.getUserCountByLoginId(loginId);
	}
	
	@Override
	public int getUserCountByEmail(String email) {
		return userMapper.getUserCountByEmail(email);
	}

	@Override
	public int getUserCountByNickname(String nickname) {
		return userMapper.getUserCountByNickname(nickname);
	}
	
	@Override
	public int getUserCountByUserInfo(User user) {
		return userMapper.getUserCountByUserInfo(user);
	}
	
	@Override
	public int getUserCountByManagerId(String loginId) {
		return userMapper.getUserCountByManagerId(loginId);
	}

	@Override
	public User getUserByUserId(long userId) {
		return userMapper.getUserByUserId(userId);
	}
	
	@Override
	public User getUserByLoginId(String loginId) {
		return userMapper.getUserByLoginId(loginId);
	}

	@Override
	public int getUserTotalCount(String authority) {

		return userMapper.getUserTotalCount(authority);
	}

	@Override
	public void deleteUserByListParam(ListParam listParam) {

		if (listParam.getId() != null) {
			for (String userId : listParam.getId()) {
				if (StringUtils.isNotEmpty(userId)) {
					//userMapper.deleteUser(Integer.parseInt(userId));
					userMapper.deleteManagerByUserId(Integer.parseInt(userId));
				}
			}
		}
	}

	@Override
	public void updateUserBuyInfoForOrder(long userId, int price) {
		
		UserDetail userDetail = new UserDetail();
		userDetail.setUserId(userId);
		userDetail.setBuyPrice(price);
		
		userMapper.updateUserBuyInfoForOrder(userDetail);
	}

	@Override
	public void getUserPasswordSearch(UserSearchParam searchParam) {
		
		User user = userMapper.getUserByParam(searchParam);
		if (user == null) {
			throw new PageNotFoundException();
		}
		
		String radomPassword = UUID.randomUUID().toString().substring(0, 8);

		user.setPassword(passwordEncoder.encode(radomPassword));

		userMapper.updateUserByLoginId(user);

		user.setPassword(radomPassword);
		
		String templateId = "pwsearch";

		int sendCount = 0;
		if ("email".equals(searchParam.getSendType()) || "all".equals(searchParam.getSendType())) {
			MailConfig mailConfig = mailConfigService.getMailConfigByTemplateId(templateId);
			if (mailConfig != null) {
				if (StringUtils.isNotEmpty(user.getEmail())) {
					
					if ("Y".equals(mailConfig.getBuyerSendFlag())) {
						PwsearchMail pwsearch = new PwsearchMail(user, mailConfig);
						mailConfig = pwsearch.getMailConfig();
			
						SendMailLog sendMailLog = new SendMailLog();
						sendMailLog.setUserId(user.getUserId());
						sendMailLog.setSendType(templateId);
			
						sendMailLogService.sendMail(mailConfig, sendMailLog, user.getEmail(), user.getUserName());
						sendCount++;
					}
				}
			}
		}
		
		if ("sms".equals(searchParam.getSendType()) || "all".equals(searchParam.getSendType())) {
			UserDetail userDetail = (UserDetail) user.getUserDetail();
			String phoneNumber = userDetail.getPhoneNumber();

			if (StringUtils.isNotEmpty(phoneNumber)) {
				Ums ums = umsService.getUms(templateId);

				if (umsService.isValidUms(ums)) {
					unifiedMessagingService.sendMessage(new Pwsearch(ums, phoneNumber, user));
					sendCount++;
				}
			}
		}
		
		if (sendCount == 0) {
			throw new UserException("비밀번호는 변경 되었으나, 비밀번호 변경 알림에 실패 하였습니다.");
		}
	}
	
	@Override
	public void updateFrontUserAndUserDetail(User user) {
		
		this.updateUser(user);
		this.updateUserDetail((UserDetail) user.getUserDetail());
		
		
		// 로그인 한 경우 세션정보 업데이트 (우선 이렇게 처리 - 추후 변경 로직 추가 하자!!)
		if (UserUtils.isUserLogin()) {
			UserDetail userDetail = getUserDetail(user.getUserId());
	
			// 회원 정보에 추가 정보가 있는 경우 조회하여 설정해줌.
			Object princial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (princial instanceof UserDetails) {
				((OpUserDetails) princial).setUserDetail(userDetail);
			}
		}
	}

	@Override
	public void updateUserForAdmin(User user) {
		
		UserDetail userDetail = (UserDetail) user.getUserDetail();
		
		User orgUserInfo = userMapper.getUserByUserId(user.getUserId());
		
		if (!orgUserInfo.getEmail().equals(user.getEmail())) {
			
			// 가입 불가 아이디 체크
			Config config = configService.getShopConfigCache(Config.SHOP_CONFIG_ID);
			
			int userCount = 0;
			if (config.getDeniedId() != null && !config.getDeniedId().equals("")) {
				String[] denyIds = StringUtils.tokenizeToStringArray(config.getDeniedId(), ",");
				
				for (String id : denyIds) {
					if (id.trim().equals("")) {
						continue;
					}
					
					if (id.trim().equals(user.getLoginId().trim())) {
						userCount = 1;
						break;
					}
				}
			}
			
			// 회원 테이블에서 조회.
			if (userCount == 0) {
				userCount = userMapper.getUserCountByLoginId(user.getLoginId());
			}
			
			if (userCount > 0) {
				
			}
		}
		
		
		UserDetail orgUserDetail = (UserDetail) orgUserInfo.getUserDetail(); 
		/*if (StringUtils.isNotEmpty(userDetail.getNickname())) {
			
			if(StringUtils.isEmpty(orgUserDetail.getNickname())){
				orgUserDetail.setNickname("");
			}
			
			if (!orgUserDetail.getNickname().equals(userDetail.getNickname())) {
				if (userMapper.getUserCountByNickname(userDetail.getNickname()) > 0) {
					throw new UserException("이미 등록된 닉네임입니다.");
				}
			}
		}*/
		
		this.updateUser(user);
		userDetail.setConditionType("OPMANAGER");
		
		// 회원 그룹 변경
		if (!userDetail.getGroupCode().equals(orgUserDetail.getGroupCode())) {
			
			GroupSearchParam groupSearchParam = new GroupSearchParam();
			groupSearchParam.setGroupCode(userDetail.getGroupCode());
			
			Group group = groupMapper.getGroupDetail(groupSearchParam);
			if (group == null) {
				userDetail.setGroupCode("");
			} else {
				
				UserGroupLog log = new UserGroupLog();
				log.setUserId(user.getUserId());
				log.setGroupCode(userDetail.getGroupCode());
				log.setGroupName(group.getGroupName());
				log.setAdminUserName(UserUtils.getManagerName());
				
				userGroupMapper.insertUserGroupLog(log);
				
			}
		}
		
		// 회원 레벨이 수동 변경되는경우
		if (userDetail.getLevelId() != orgUserDetail.getLevelId()) {
			
			UserLevelSearchParam userLevelSearchParam = new UserLevelSearchParam();
			userLevelSearchParam.setLevelId(userDetail.getLevelId());
			
			UserLevel userLevel = userLevelService.getUserLevelDetail(userLevelSearchParam);
			if (userLevel == null) {
				userDetail.setLevelId(0);
				userDetail.setUserLevelExpirationDate("");
			} else {
				
				// 등급 유지기간을 설정된 개월수를 더해서 변경
				userDetail.setUserLevelExpirationDate(DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), userLevel.getRetentionPeriod()));
				
				UserLevelLog log = new UserLevelLog();
				log.setUserId(user.getUserId());
				log.setGroupCode(userDetail.getGroupCode());
				log.setLevelId(userLevel.getLevelId());
				log.setLevelName(userLevel.getLevelName());
				log.setAdminUserName(UserUtils.getManagerName());
				userLevelMapper.insertUserLevelLog(log);
				
			}
		} else {
			userDetail.setUserLevelExpirationDate(orgUserDetail.getUserLevelExpirationDate());
		}
		
		this.updateUserDetail(userDetail);

	}
	
	@Override
	public User getUserByParam(UserSearchParam searchParam) {
		return userMapper.getUserByParam(searchParam);
	}
	
	public User passwordFlagResult(User user){
		
		if(StringUtils.isEmpty(user.getPassword())){
			//user.setPassword(UserUtils.getUser().getPassword());
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		return user;
	}


	@Override
	public User getUserInfoByUserName(AuthUserInfo authUserInfo) {

		return userMapper.getUserInfoByUserName(authUserInfo);
	}
	
	@Override
	public int getSecedeUserCount(UserSearchParam searchParam) {
		return userMapper.getSecedeUserCount(searchParam);
	}
	
	@Override
	public List<User> getSecedeUserList(UserSearchParam searchParam) {
		return userMapper.getSecedeUserList(searchParam);
	}
	
	@Override
	public void updateSecedeFrontUserAndUserDetail(User user) {

		// 로그인아이디의가 회원 sns 고유 아이디라 재가입을 위해 탈퇴일 추가
		user.setLoginId(user.getLoginId() + "_" + DateUtils.getToday(Const.DATETIME_FORMAT));
		
		userMapper.updateSecedeUser(user);
		userMapper.updateSecedeUpdateUserDetail((UserDetail)user.getUserDetail());
		userSnsService.secedeSnsProcess(user);
		
		// 로그인 한 경우 세션정보 업데이트 (우선 이렇게 처리 - 추후 변경 로직 추가 하자!!)
		if (UserUtils.isUserLogin()) {
			UserDetail userDetail = getUserDetail(user.getUserId());
	
			// 회원 정보에 추가 정보가 있는 경우 조회하여 설정해줌.
			Object princial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (princial instanceof UserDetails) {
				((OpUserDetails) princial).setUserDetail(userDetail);
			}
		}
	}

	@Override
	public List<HashMap<String, String>> getAdminMenuRoleList() {
		return userMapper.getAdminMenuRoleList();
	}
	
	@Override
	public List<User> getManagerList(UserSearchParam searchParam) {
		List<User> managerList = userMapper.getManagerList(searchParam);
		
		for (User user : managerList) {
			List<UserRole> userRoles = userMapper.getManagerRoleListByUserId(user.getUserId());
			user.setUserRoles(userRoles);
		}
		
		return managerList;
	}

	/*@Override
	public void sendSmsAndEmail(User user, String templateId) {

		this.sendMail(user, templateId);
		this.sendSms(user, templateId);
	}*/

	public List<ChosenUser> getChosenUserList(List<String> list){
		return userMapper.getChosenUserList(list);
	}
	
	public List<ChosenUser> getChosenUserListbyParam(ChosenUser chosenUser){
		return userMapper.getChosenUserListbyParam(chosenUser);
	}

	@Override
	public List<ChosenUser> getUserListForChosen(UserSearchParam searchParam){
		
		return userMapper.getUserListForChosen(searchParam);
	}

	@Override
	public int getManagerCount(UserSearchParam searchParam) {
		return userMapper.getManagerCount(searchParam);
	}

	@Override
	public User getManagerByUserId(long userId) {
		User user = userMapper.getManagerByUserId(userId);
		
		List<UserRole> userRoles = userMapper.getManagerRoleListByUserId(user.getUserId());
		user.setUserRoles(userRoles);
		return user;
	}

	@Override
	public int getManagerCountByEmail(String email) {
		return userMapper.getManagerCountByEmail(email);
	}

	@Override
	public void updateManager(User user) {
		if (!StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		userMapper.updateManager(user);
		
		userRoleService.deleteUserRole(user.getUserId());
		
		List<UserRole> userRoles = user.getUserRoles();

		String logRoles = "";

		if (ValidationUtils.isNotNull(user.getUserRoles())) {
			for (UserRole userRole : userRoles) {
				if (ValidationUtils.isNotNull(userRole.getAuthority())) {
					userRole.setUserId(user.getUserId());
					userRole.setAuthority(userRole.getAuthority());
					userRoleService.insertUserRole(userRole);
					logRoles += userRole.getAuthority() + "/"; // 로그 남길 때 권한 명 yulsun.yoo [2018-10-18]
				}
			}
		}
			
		UserRole userRole = new UserRole();
		userRole.setUserId(user.getUserId());
		userRole.setAuthority("ROLE_OPMANAGER");
		userRoleService.insertUserRole(userRole);

		logRoles += "ROLE_OPMANAGER";

		changeLogService.insertManagerChangeLog(RequestContextUtils.getRequestContext().getRequest(), user);
	}

	@Override
	public void deleteManagerByListParam(ListParam listParam) {

		if (listParam.getId() != null) {
			for (String userId : listParam.getId()) {
				if (StringUtils.isNotEmpty(userId)) {
					userMapper.deleteManagerByUserId(Integer.parseInt(userId));
					userMapper.deleteUserRole(Integer.parseInt(userId));
				}
			}
		}
	}

	/**
	 * 휴면계정 안내메일 송신
	 */
	@Override
	public void sendSleepUserMail() {
		List<User> list = userMapper.getWaitSleepUser();
		
		if (list == null) {
			return;
		}
		
		if (list.isEmpty()) {
			return;
		}
		
		String templateId = "member_sleep";
		Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
		
		MailConfig mailConfig = mailConfigService.getMailConfigByTemplateId(templateId);
		if (mailConfig == null) {
			return;
		}
		
		if (!"Y".equals(mailConfig.getBuyerSendFlag())) {
			return;
		}
		
		try {
			for(User mail : list) {
				MemberSleepMail memberSleepMail = new MemberSleepMail(mail, mailConfig, shopConfig);
				MailConfig mConfig = memberSleepMail.getMailConfig();
				
				SendMailLog sendMailLog = new SendMailLog();
				sendMailLog.setUserId(mail.getUserId());
				sendMailLog.setSendType(templateId);
				
				sendMailLogService.sendMail(mConfig, sendMailLog, mail.getEmail(), mail.getUserName(), shopConfig);
			}
		} catch (Exception e) {
			log.error("메일발송 송출 에러::: {}", e.getMessage(), e);
		}
		
		userMapper.updateWaitSleepUser(list);
	}
	
	@Override
	public void setSleepUser() {
		List<User> list = userMapper.getUserListForSleepTarget();
		if (list == null) {
			return;
		}
		
		List<User> insertList = new ArrayList<>();

		int count = 0;
		for(User user : list) {
			
			try {
				encryptSleepUser(user);							//휴면유저정보 암호화
			} catch (Exception e) {
				log.debug("휴면정보 암호화중 오류 ::  {}",e.getMessage(), e);
			}
			
			insertList.add(user);
			
			if (count % 100 == 0 && count > 0) {
				
				userMapper.insertSleepUser(insertList);
				userMapper.updateUserToSleep(insertList);
				userMapper.updateUserDetailToSleep(insertList);
				insertList = new ArrayList<>();
				
			}
			
			count++;
		}
		
		if (!insertList.isEmpty()) {
			userMapper.insertSleepUser(insertList);
			userMapper.updateUserToSleep(insertList);
			userMapper.updateUserDetailToSleep(insertList);
		}
		
	}
	
	@Override
	public void wakeupUser(User currentUser) {
		User wakeUpData = userMapper.getUserForWakeup(currentUser);
		if (wakeUpData == null) {
			throw new UserException("휴면회원 정보를 찾을수 없습니다.");
		}
		
		//데이터 복호화
		try {
			decryptSleepUser(wakeUpData);
		} catch (Exception e) {
			log.error("휴면계정정보 복호화중 오류 ::  {}",e.getMessage(), e);
		}
		
		//user, userDetail 정보 업데이트
		userMapper.wakeupUser(wakeUpData);
		userMapper.wakeupUserDetail(wakeUpData);
		//OP_USER_SLEEP데이터 삭제
		userMapper.deleteSleepUser(currentUser);
	}
	
	private String encrypt(String message) {
		
		try {
			return CipherUtils.encrypt(message);
		} catch (Exception e) {
			log.error(" CipherUtils.encrypt(message) : {}", e.getMessage(), e);

		}
		
		return message;
	}
	
	/**
	 * 휴면대상정보 암호화
	 * @param user
	 * @throws Exception 
	 */
	public void encryptSleepUser(User user) throws Exception {
		user.setUserName(encrypt(user.getUserName()));
		user.setEmail(encrypt(user.getEmail()));
		
		UserDetail userDetail = (UserDetail) user.getUserDetail();
		//userDetail.setNickname(encrypt(userDetail.getNickname()));
		userDetail.setNewPost(encrypt(userDetail.getNewPost()));
		userDetail.setPost(encrypt(userDetail.getPost()));
		userDetail.setAddress(encrypt(userDetail.getAddress()));
		userDetail.setAddressDetail(encrypt(userDetail.getAddressDetail()));
		userDetail.setTelNumber(encrypt(userDetail.getTelNumber()));
		userDetail.setPhoneNumber(encrypt(userDetail.getPhoneNumber()));
		userDetail.setFaxNumber(encrypt(userDetail.getFaxNumber()));
		userDetail.setBirthdayType(encrypt(userDetail.getBirthdayType()));
		userDetail.setBirthday(encrypt(userDetail.getBirthday()));
		//userDetail.setCompanyName(encrypt(userDetail.getCompanyName()));
		
		user.setUserDetail(userDetail);
	}
	
	/**
	 * 휴면대상정보 복호화
	 * @Date 2017-03-07
	 * @author 이상우
	 * @param user
	 * @throws Exception 
	 */
	public void decryptSleepUser(User user) throws Exception {
		user.setUserName(CipherUtils.decrypt(user.getUserName()));
		user.setEmail(CipherUtils.decrypt(user.getEmail()));
		
		UserDetail userDetail = (UserDetail) user.getUserDetail();
		//userDetail.setNickname(CipherUtils.decrypt(userDetail.getNickname()));
		userDetail.setNewPost(CipherUtils.decrypt(userDetail.getNewPost()));
		userDetail.setPost(CipherUtils.decrypt(userDetail.getPost()));
		userDetail.setAddress(CipherUtils.decrypt(userDetail.getAddress()));
		userDetail.setAddressDetail(CipherUtils.decrypt(userDetail.getAddressDetail()));
		userDetail.setTelNumber(CipherUtils.decrypt(userDetail.getTelNumber()));
		userDetail.setPhoneNumber(CipherUtils.decrypt(userDetail.getPhoneNumber()));
		userDetail.setFaxNumber(CipherUtils.decrypt(userDetail.getFaxNumber()));
		userDetail.setBirthdayType(CipherUtils.decrypt(userDetail.getBirthdayType()));
		userDetail.setBirthday(CipherUtils.decrypt(userDetail.getBirthday()));
		//userDetail.setCompanyName(CipherUtils.decrypt(userDetail.getCompanyName()));
		
		user.setUserDetail(userDetail);
	}

	@Override
	public List<OpmanagerCount> getOpmanagerUserCountAll() {

		// SKC 쿼리 튜닝
		Map<String, String> param = new HashMap<>();
		param.put("today", LocalDateUtils.localDateToString(LocalDate.now()));
		param.put("days7ago", LocalDateUtils.localDateToString(LocalDate.now().minusWeeks(1)));


		return userMapper.getOpmanagerUserCountAll(param);
	}

	@Override
	public void updateUserDetailForOrder(Buyer buyer) {
		userMapper.updateUserDetailForOrder(buyer);
		
	}

	@Override
	public void updateTempPasswordForManager(long userId) throws UserException{
		User user = userMapper.getManagerByUserId(userId);

		if (user.getPhoneNumber() == null) {
			throw new UserException("핸드폰 번호가 없습니다.");
		} else {

			String tempPassword = RandomStringUtils.getRandomString("!",7,10);

			User tempUser = new User();

			tempUser.setUserId(userId);
			tempUser.setPasswordType("T");
			tempUser.setPassword(passwordEncoder.encode(tempPassword));
			tempUser.setPasswordExpiredDate(this.getPasswordExpiredDate());

			int count = userMapper.updatePasswordForManager(tempUser);

			if (count > 0) {

				SmsConfig smsConfig = new SmsConfig();
				smsConfig.setBuyerSendFlag("Y");
				smsConfig.setBuyerContent("임시비밀번호\n"+tempPassword);
				smsConfig.setSmsType("sms");

				SendSmsLog sendSmsLog = new SendSmsLog();

				sendSmsLog.setContent(smsConfig.getBuyerContent());
				sendSmsLog.setSendType("MANAGER_TEMP_PASSOWRD");
				sendSmsLogService.sendSms(smsConfig, sendSmsLog, user.getPhoneNumber());

			}
		}
	}

	@Override
	public void updatePasswordForManager(long userId, String passowrd, String changePassowrd) throws UserException {

		User user = userMapper.getManagerByUserId(userId);

		if (passwordEncoder.matches(passowrd, user.getPassword())) {

			if (passwordEncoder.matches(changePassowrd, user.getPassword())) {
				throw new UserException("변경 하려는 비밀번호가 동일합니다.");
			}

			User tempUser = new User();

			tempUser.setUserId(userId);
			tempUser.setPasswordType("N");
			tempUser.setPassword(passwordEncoder.encode(changePassowrd));
			tempUser.setPasswordExpiredDate(this.getPasswordExpiredDate());

			int count = userMapper.updatePasswordForManager(tempUser);

			if (count == 0) {
				throw new UserException("비밀번호가 변경되지 않았습니다.");
			}

		} else {
			throw new UserException("비밀번호가 일치하지 않습니다.");
		}
	}

	@Override
	public void updatePasswordForUser(long userId, String passowrd, String changePassowrd) throws UserException {
		User user = userMapper.getUserByUserId(userId);

		if (passwordEncoder.matches(passowrd, user.getPassword())) {

			if (passwordEncoder.matches(changePassowrd, user.getPassword())) {
				throw new UserException("변경 하려는 비밀번호가 동일합니다.");
			}

			User tempUser = new User();

			tempUser.setUserId(userId);
			tempUser.setPasswordType("N");
			tempUser.setPassword(passwordEncoder.encode(changePassowrd));
			tempUser.setPasswordExpiredDate(this.getPasswordExpiredDate());

			int count = userMapper.updatePasswordForUser(tempUser);

			if (count == 0) {
				throw new UserException("비밀번호가 변경되지 않았습니다.");
			}

		} else {
			throw new UserException("비밀번호가 일치하지 않습니다.");
		}
	}

	@Override
	public void updatePasswordExpiredDateForUser(long userId) throws UserException {

		try {


			User user = new User();

			user.setUserId(userId);
			user.setPasswordExpiredDate(this.getPasswordExpiredDate());

			if (userMapper.updatePasswordExpiredDateForUser(user) == 0) {
				throw new UserException("비밀번호관련 정보가 변경에 실패 했습니다.");
			}

		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new UserException("비밀번호관련 정보가 변경에 실패 했습니다.");
		}
	}

	@Override
	public String getPasswordExpiredDate() {

		String passwordExpiredDate = "";

		try {
			String lifeTimePassword = configIsmsService.getIsmsConfigValueByKey("LIFE_TIME_PASSWORD");
			passwordExpiredDate = DateUtils.addDay(DateUtils.getToday(Const.DATE_FORMAT), StringUtils.string2integer(lifeTimePassword));
		} catch (Exception e) {
			passwordExpiredDate = DateUtils.addDay(DateUtils.getToday(Const.DATE_FORMAT), 90);
			log.error("ERROR: {}", e.getMessage(), e);
		}

		return passwordExpiredDate;
	}

	@Override
	public void insertLoginSessionForManager(HttpSession session, long userId) {
		ManagerLogin loginSession = new ManagerLogin(userId, session.getId());

		managerLoginRepository.save(loginSession);
	}

	@Override
	public void deleteLoginSessionForManager(long userId) {
		managerLoginRepository.deleteManagerLoginsByUserId(userId);
	}

	@Override
	public List<ManagerLogin> getLoginSessionForManagerByUserId(long userId) {
		return  managerLoginRepository.findManagerLoginsByUserId(userId);
	}

	@Override
	public String checkDuplication(User user) {
		String checkResult = "";

		// 가입 불가 아이디 체크
		Config config = configService.getShopConfigCache(Config.SHOP_CONFIG_ID);

		int userCount = 0;
		if (config.getDeniedId() != null && !config.getDeniedId().equals("")) {
			String[] denyIds = StringUtils.tokenizeToStringArray(config.getDeniedId(), ",");

			for (String userId : denyIds) {
				if (userId.trim().equals("")) {
					continue;
				}

				if (userId.trim().equals(user.getLoginId().trim())) {
					userCount = 1;
					checkResult = "isOccupiedId";
					break;
				}
			}
		}

		// 회원 테이블에서 조회.
		if (userCount == 0) {
			userCount = getUserCountByUserInfo(user);
		}

		if (userCount > 0) {
			checkResult = "isOccupiedId";
		}

		return checkResult;
	}

	@Override
	public void updatePasswordByAsisUser(long userId, String password) {
		User user = userMapper.getUserByUserId(userId);

		if (user != null && "PASSWORD".equals(user.getPassword())) {

			user.setPassword(passwordEncoder.encode(password));
			user.setPasswordExpiredDate(this.getPasswordExpiredDate());

			userMapper.updatePasswordByAsisUser(user);
		}
	}

	@Override
	public User getUserInfoExceptForSnsUser(AuthUserInfo authUserInfo) {
		return userMapper.getUserInfoExceptForSnsUser(authUserInfo);
	}
}
