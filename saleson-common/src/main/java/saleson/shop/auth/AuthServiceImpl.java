package saleson.shop.auth;

import com.onlinepowers.framework.exception.BusinessException;
import com.onlinepowers.framework.notification.NotificationService;
import com.onlinepowers.framework.notification.message.HtmlMailMessage;
import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.notification.message.SmsMessage;
import com.onlinepowers.framework.notification.sms.SmsService;
import com.onlinepowers.framework.security.token.TokenService;
import com.onlinepowers.framework.security.token.domain.Token;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.security.userdetails.UserRole;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.KeyGeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.shop.user.UserMapper;
import saleson.shop.user.domain.Customer;

import java.util.HashMap;
import java.util.List;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired 
	SmsService smsService;

	@Autowired
	Environment environment;
	
	@Autowired 
	@Qualifier("mailService")
	NotificationService mailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	// @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public String sendSmsAuthNumber(String loginId, String phoneNumber) {
		// SMS 인증번호 생성
		String smsAuthNumber = KeyGeneratorUtils.getSmsAuthenticationNumber();
		String smsMessage = "SMS 인증번호 [" + smsAuthNumber + "]를 입력해 주세요.";
		
		// 토큰 생성 정보 
		Token param = new Token("SMS", saleson.common.utils.CommonUtils.getClientIp(), smsAuthNumber);
		
		OpMessage message = new SmsMessage(phoneNumber, smsMessage, "");
		
		// SMS 발송.
		smsService.sendMessage(message);
		
		return tokenService.allocateToken(param).getRequestToken();
	}
	
	@Override
	public String getSmsAuthNumber(String phoneNumber) {
		return sendSmsAuthNumber("GUEST", phoneNumber);
	}
	
	
	@Override
	public String getSmsAuthNumber(String loginId, String phoneNumber) {
		return getSmsAuthNumber(loginId, phoneNumber, false);
	}
	
	
	@Override
	public String getSmsAuthNumber(String loginId, String phoneNumber, boolean isAdmin) {

		if (isAdmin) {
			List<UserRole> userRoles = userMapper.getUserRoleListByLoginId(loginId);
			
			boolean hasManagerRole = false;
			for (UserRole userRole : userRoles) {
				if ("ROLE_KB_SUPERVISOR".equals(userRole.getAuthority()) 
						|| "ROLE_KB_MANAGER".equals(userRole.getAuthority())) {
					
					hasManagerRole = true;
					break;
				}
			}
			
			if (!hasManagerRole) {
				throw new BusinessException("입력한 정보와 일치하는 관리자가 없습니다.\n정보를 정확히 입력해 주세요.");
			}
		} else {

			User user = userMapper.getUserByLoginId(loginId);
			String errorMessage = "입력한 정보와 일치하는 회원이 없습니다.\n정보를 정확히 입력해 주세요.";


			if (user == null) {
				throw new BusinessException(errorMessage);
			}

			String userPhomeNumber = user.getPhoneNumber();
			userPhomeNumber = userPhomeNumber.replaceAll("-","");
			phoneNumber = phoneNumber.replaceAll("-","");

			if (!phoneNumber.equals(userPhomeNumber)) {
				throw new BusinessException(errorMessage);
			}
		}
		
		return sendSmsAuthNumber(loginId, phoneNumber);
	}


	
	@Override
	// @Transactional(readOnly=false)
	public String getEmailAuthNumber(Customer customer) {
		// phoneNumber
		String phoneNumber = customer.getPhoneNumber();

		// 비밀번호 설정.
		String password = passwordEncoder.encode(ShopUtils.shufflePhoneNumber(phoneNumber));
		
				
		User user = new User();
		user.setLoginId(customer.getLoginId());
		user.setPassword(password);
		
		
		// 1. 회원 존재 여부.
		int userCount = userMapper.getUserCustmerCount(user);
		
		if (userCount == 0) {
			throw new BusinessException("입력하신 이메일과 휴대폰 번호를 사용하는 회원이 존재하지 않습니다.");
		}
		
		// 2. 인증번호 요청.
		String emailAuthNumber = KeyGeneratorUtils.getSmsAuthenticationNumber();
		String mailTitle = "[KB희망별] 휴대폰번호 변경을 위한 이메일 인증번호 입니다.";
		
		// 토큰 생성 정보 
		Token param = new Token("EMAIL", saleson.common.utils.CommonUtils.getClientIp(), emailAuthNumber);
		
		
		// 메일발송.
		String templateFile = FileUtils.getDefaultUploadPath() + "/mail-template/email-auth-number.html";
		HashMap<String, String> replaceMap = new HashMap<>();
		replaceMap.put("EMALI_AUTH_NUMBER", emailAuthNumber);
		replaceMap.put("URL", SalesonProperty.getSalesonUrlShoppingmall());
		
		String mailContent = "";
		try {
			mailContent = FileUtils.readTemplateFile(templateFile, replaceMap);
			
		} catch (Exception e) {
			log.error("getEmailAuthNumber - 메일 템틀릿 파일 오픈 오류 : {}", e.getMessage(), e);
			throw new BusinessException("시스템 오류로 인하여 메일 발송에 실패하였습니다. (템플릿오류)");
		}
			
		
		mailService.sendMessage(new HtmlMailMessage(customer.getLoginId(), mailTitle, mailContent));
		
		return tokenService.allocateToken(param).getRequestToken();
	}


	
}
