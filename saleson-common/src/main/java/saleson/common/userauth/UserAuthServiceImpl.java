package saleson.common.userauth;

import com.onlinepowers.framework.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import saleson.common.userauth.domain.UserAuth;
import saleson.common.userauth.utils.Siren24Utils;
import saleson.common.utils.ShopUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {

	@Autowired
	private UserAuthMapper userAuthMapper;

	@Autowired
	Environment environment;
	
	@Override
	public String getIpinEncryptString(HttpServletRequest request, String target) {
	
		HttpSession session = request.getSession();
		
		String serviceType = environment.getProperty("user.auth.ipin.service");
		if (StringUtils.isEmpty(serviceType)) {
			throw new UserException("본인인증 서비스(IPIN)이 정검중 입니다. 잠시후 다시 시도해 주시기 바랍니다.");
		}
		
		String appKey = Long.toString(System.currentTimeMillis());
		if ("siren24".equals(serviceType)) {
			for(int i=appKey.length(); i < 16; i++) {
				appKey = "0" + appKey;
			}
		}
		
		UserAuth userAuth = new UserAuth();
		userAuth.setAppKey(appKey);
		userAuth.setServiceType(serviceType);
		userAuth.setServiceMode("IPIN");
		
		if (userAuthMapper.getCountUserAuthTemp(userAuth) > 0) {
			throw new UserException("잠시후 다시 시도해 주시기 바랍니다.");
		}
		
		String encryptString = "";
		if ("siren24".equals(serviceType)) {
			
			String serviceNo = environment.getProperty("user.auth.ipin.front.serviceNo");
			if (ShopUtils.isMobilePage() == true) {
				serviceNo = environment.getProperty("user.auth.ipin.mobile.serviceNo");;
			}
			
			encryptString = Siren24Utils.getIpinEncryptString(appKey, serviceNo);
			Siren24Utils.setUserAuthAppKey(session, appKey);
			
		} else {
			throw new UserException("본인인증 서비스(IPIN)이 정검중 입니다. 잠시후 다시 시도해 주시기 바랍니다.");
		}
		
		userAuth.setServiceTarget(target);
		userAuth.setUserIp(saleson.common.utils.CommonUtils.getClientIp(request));
		userAuthMapper.insertUserAuth(userAuth);
		return encryptString;
	}
	
	@Override
	public void setIpinAuthSuccess(HttpServletRequest request) {
		
		String serviceType = environment.getProperty("user.auth.ipin.service");
		if (StringUtils.isEmpty(serviceType)) {
			throw new RuntimeException("본인인증 서비스(IPIN)이 정검중 입니다. 잠시후 다시 시도해 주시기 바랍니다.");
		}
		
		UserAuth userAuth = new UserAuth();
		userAuth.setServiceType(serviceType);
		userAuth.setServiceMode("IPIN");
		
		if ("siren24".equals(serviceType)) {
			
			Siren24Utils.getIpinSuccessData(request, userAuth);
			
			userAuthMapper.updateUserAuth(userAuth);
			
			if (!"1".equals(userAuth.getDataStatusCode())) {
				throw new UserException("잘못된 접근입니다.");
			}
			
		} else {
			throw new UserException("본인인증 서비스(IPIN)이 정검중 입니다. 잠시후 다시 시도해 주시기 바랍니다.");
		}
		
	}
	
	@Override
	public String getPccEncryptString(HttpServletRequest request, String target) {
		HttpSession session = request.getSession();
		
		String serviceType = environment.getProperty("user.auth.pcc.service");
		if (StringUtils.isEmpty(serviceType)) {
			throw new UserException("본인인증 서비스(휴대폰)이 정검중 입니다. 잠시후 다시 시도해 주시기 바랍니다.");
		}
		
		String appKey = Long.toString(System.currentTimeMillis());
		if ("siren24".equals(serviceType)) {
			for(int i=appKey.length(); i < 16; i++) {
				appKey = "0" + appKey;
			}
		}
		
		UserAuth userAuth = new UserAuth();
		userAuth.setAppKey(appKey);
		userAuth.setServiceType(serviceType);
		userAuth.setServiceMode("PCC");
		
		if (userAuthMapper.getCountUserAuthTemp(userAuth) > 0) {
			throw new UserException("잠시후 다시 시도해 주시기 바랍니다.");
		}
		
		String encryptString = "";
		if ("siren24".equals(serviceType)) {
			
			String serviceNo = environment.getProperty("user.auth.pcc.front.serviceNo");
			if (ShopUtils.isMobilePage() == true) {
				serviceNo = environment.getProperty("user.auth.pcc.mobile.serviceNo");;
			}
			
			encryptString = Siren24Utils.getPccEncryptString(appKey, serviceNo);
			Siren24Utils.setUserAuthAppKey(session, appKey);
			
		} else {
			throw new UserException("본인인증 서비스(휴대폰)이 정검중 입니다. 잠시후 다시 시도해 주시기 바랍니다.");
		}
		
		userAuth.setServiceTarget(target);
		userAuth.setUserIp(saleson.common.utils.CommonUtils.getClientIp(request));
		userAuthMapper.insertUserAuth(userAuth);
		return encryptString;
	}
	
	@Override
	public void setPccAuthSuccess(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String serviceType = environment.getProperty("user.auth.pcc.service");
		if (StringUtils.isEmpty(serviceType)) {
			throw new UserException("본인인증 서비스(휴대폰)이 정검중 입니다. 잠시후 다시 시도해 주시기 바랍니다.");
		}
		
		UserAuth userAuth = new UserAuth();
		userAuth.setServiceType(serviceType);
		userAuth.setServiceMode("PCC");
		
		if ("siren24".equals(serviceType)) {
			
			Siren24Utils.getPccSuccessData(request, userAuth);
			
			userAuthMapper.updateUserAuth(userAuth);
			
			if (!"1".equals(userAuth.getDataStatusCode())) {
				throw new UserException("잘못된 접근입니다.");
			}
			
		} else {
			throw new UserException("본인인증 서비스(휴대폰)이 정검중 입니다. 잠시후 다시 시도해 주시기 바랍니다.");
		}
		
	}
	
	@Override
	public UserAuth getUserAuth(HttpServletRequest request, String dataStatusCode) {
		HttpSession session = request.getSession();
		
		String serviceType = environment.getProperty("user.auth.pcc.service");
		if (StringUtils.isEmpty(serviceType)) {
			throw new UserException("잠시후 다시 시도해 주시기 바랍니다.");
		}
		
		if ("siren24".equals(serviceType)) {
			
			String appKey = Siren24Utils.getUserAuthAppKey(session);
			if (StringUtils.isEmpty(appKey)) {
				return null;
			}
			
			UserAuth param = new UserAuth();
			param.setAppKey(appKey);
			param.setDataStatusCode(dataStatusCode);
			param.setServiceType(serviceType);
			
			UserAuth userAuth = userAuthMapper.getUserAuth(param);
			if (userAuth == null) {
				Siren24Utils.removeUserAuthAppKey(session);
			}
			
			return userAuth;
			
		} else {
			throw new UserException("잠시후 다시 시도해 주시기 바랍니다.");
		}
	}
}
