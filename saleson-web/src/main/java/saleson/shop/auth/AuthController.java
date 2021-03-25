package saleson.shop.auth;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.BusinessException;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import saleson.shop.user.domain.Customer;

@Controller
@RequestMapping("/auth")
@RequestProperty(layout="blank")
public class AuthController {
	private static Logger log = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthService authService;
	
	/**
	 * SMS 인증번호 요청
	 * @param phoneNumber
	 * @return
	 */
	@PostMapping("sms-request")
	public JsonView smsAuthNumber(RequestContext requestContext,
			@RequestParam(value="loginId", required=false, defaultValue="") String loginId,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam(value="loginType", required=false, defaultValue="") String loginType
			) {
		
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();  
		}
		
		try {
			String requestToken = "";

			if ("".equals(loginId)) {
				requestToken = authService.getSmsAuthNumber(phoneNumber);
			} else {
				if ("opmanager".equals(loginType)) {
					requestToken = authService.getSmsAuthNumber(loginId, phoneNumber, true);
				} else {
					requestToken = authService.getSmsAuthNumber(loginId, phoneNumber);
				}
				
			}
			
			return JsonViewUtils.success(requestToken);
			
		} catch (BusinessException e) {
			return JsonViewUtils.exception(e.getMessage());    
			
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.exception("인증번호 발송에 실패하였습니다."); 
			
		}
		
	}
	
	
	/**
	 * 이메일 인증번호 요청
	 * @param phoneNumber
	 * @return
	 */
	@PostMapping("email-request")
	public JsonView emailAuthNumber(RequestContext requestContext,
			Customer customer) {
		
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();  
		}
		
		try {
			String requestToken = authService.getEmailAuthNumber(customer);
			return JsonViewUtils.success(requestToken);
			
		} catch (BusinessException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.exception(e.getMessage());     
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.exception("인증번호 발송에 실패하였습니다.");     
		}
		
	}
	
}
