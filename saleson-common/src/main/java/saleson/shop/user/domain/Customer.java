package saleson.shop.user.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import com.onlinepowers.framework.annotation.Title;

public class Customer {
	@NotNull
	@Length(max=40)
	@Email
	@Title("이메일")
	private String loginId;
	

	private String userName;
	
	@NotNull
	@Length(max=4)
	private String phoneNumber1;
	
	@NotNull
	@Length(max=4)
	private String phoneNumber2;
	
	@NotNull
	@Length(max=4)
	private String phoneNumber3;
	
	private String phoneNumber = "--";

	private long userId;
	private String terms;
	private String privacy;
	private String smsAuthNumber;
	private String emailAuthNumber;
	private String requestToken;
	
	
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getPhoneNumber1() {
		return getPhoneNumbers()[0];
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return getPhoneNumbers()[1];
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public String getPhoneNumber3() {
		return getPhoneNumbers()[2];
	}

	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}

	public String getPhoneNumber() {
		return phoneNumber1 + "-" + phoneNumber2 + "-" + phoneNumber3;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	private String[] getPhoneNumbers() {
		return StringUtils.delimitedListToStringArray(this.phoneNumber, "-");
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getSmsAuthNumber() {
		return smsAuthNumber;
	}

	public void setSmsAuthNumber(String smsAuthNumber) {
		this.smsAuthNumber = smsAuthNumber;
	}

	public String getEmailAuthNumber() {
		return emailAuthNumber;
	}

	public void setEmailAuthNumber(String emailAuthNumber) {
		this.emailAuthNumber = emailAuthNumber;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}
	
	
}
