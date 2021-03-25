package saleson.shop.user.domain;

import com.onlinepowers.framework.util.ValidationUtils;
import org.springframework.security.core.SpringSecurityCoreVersion;
import saleson.common.utils.ShopUtils;

import java.io.Serializable;

public class AuthUserInfo implements Serializable {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private String userName;
	private String phoneNumber1;
	private String phoneNumber2;
	private String phoneNumber3;
	private String email;
	private String loginId;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullPhoneNumber() {
		return this.phoneNumber1 + "-" + this.phoneNumber2 + "-" + this.phoneNumber3;
	}
	public void setFullPhoneNumber(String fullPhoneNumber) {
		String[] phoneNumbers = ShopUtils.phoneNumberForDelimitedToStringArray(fullPhoneNumber);

		this.phoneNumber1 = ValidationUtils.isNotNull(phoneNumbers[0]) ? phoneNumbers[0] : "";
		this.phoneNumber2 = ValidationUtils.isNotNull(phoneNumbers[1]) ? phoneNumbers[1] : "";
		this.phoneNumber3 = ValidationUtils.isNotNull(phoneNumbers[2]) ? phoneNumbers[2] : "";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public String getPhoneNumber1() {
		return phoneNumber1;
	}
	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	public String getPhoneNumber3() {
		return phoneNumber3;
	}
	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	
}
