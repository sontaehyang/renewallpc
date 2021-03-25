package saleson.common.mail;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.shop.mailconfig.domain.MailConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailTemplate {
	private static final Logger log = LoggerFactory.getLogger(MailTemplate.class);
	
	private MailConfig mailConfig;		// 메일 템플릿
	
	private String siteName;			// 상점명 
	private String siteUrl;				// 사이트 URL
	private String compName;			// 회사명

	
	
	@SuppressWarnings("unused")
	private MailTemplate() {}
	
	public MailTemplate(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}

	public MailConfig getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	
	/**
	 * 변환된 MailConfig 정보를 반환한다..
	 * @return
	 */
	public MailConfig replace() {
		MailConfig config = null;
		try {
			config = (MailConfig) this.mailConfig.clone();
			
			config.setBuyerSubject(getBuyerSubject());
			config.setBuyerContent(getBuyerContent());
			config.setMobileBuyerSubject(getMobileBuyerSubject());
			config.setMobileBuyerContent(getMobileBuyerContent());
			config.setAdminSubject(getAdminSubject());
			config.setAdminContent(getAdminContent());
		} catch (CloneNotSupportedException e) {
			log.warn("Mail Template {}", e.getMessage(), e);
		}
		
		return config;
	}
	
	
	/**
	 * 고객 발송용 메일 제목을 치환하여 리턴한다.
	 * @return
	 */
	public String getBuyerSubject() {
		return replace(mailConfig.getBuyerSubject());
	}
	
	
	/**
	 * 고객 발송용 메일 내용을 치환하여 리턴한다.
	 * @return
	 */
	public String getBuyerContent() {
		return replace(mailConfig.getBuyerContent());
	}
	
	/**
	 * 고객 발송용 메일 제목을 치환하여 리턴한다. (모바일)
	 * @return
	 */
	public String getMobileBuyerSubject() {
		return replace(mailConfig.getMobileBuyerSubject());
	}
	
	
	/**
	 * 고객 발송용 메일 내용을 치환하여 리턴한다. (모바일)
	 * @return
	 */
	public String getMobileBuyerContent() {
		return replace(mailConfig.getMobileBuyerContent());
	}
	
	
	/**
	 * 운영자 발송용 메일 제목을 치환하여 리턴한다.
	 * @return
	 */
	public String getAdminSubject() {
		return replace(mailConfig.getAdminSubject());
	}
	
	
	/**
	 * 관리자 발송용 메일 내용을 치환하여 리턴한다.
	 * @return
	 */
	public String getAdminContent() {
		return replace(mailConfig.getAdminContent());
	}
	
	
	/**
	 * 템플릿 내용을 치환하여 리턴한다.
	 * @param mailTemplate
	 * @return
	 */
	public String replace(String mailTemplate) {
		
		if (ValidationUtils.isNull(mailTemplate)) {
			return "";
		}

		Class<?> clazz = getClass();
		
		Pattern pattern = Pattern.compile("\\{[a-z_-]+\\}");
		Matcher matcher = pattern.matcher(mailTemplate);
		
		List<HashMap<String, String>> fields = new ArrayList<>();
		while(matcher.find()) {
			HashMap<String, String> fieldInfo = new HashMap<>();
			String patternString = matcher.group().replaceAll("\\{", "").replaceAll("\\}", "");
			String methodName = StringUtils.convertToCamel("get_" + patternString);
			
			fieldInfo.put("patternString", patternString);
			fieldInfo.put("methodName", methodName);
			
			fields.add(fieldInfo);
		}
		
		for (HashMap<String, String> fieldInfo : fields) {

			String methodValue;
			
			try {
				String patternString = fieldInfo.get("patternString");
				String methodName = fieldInfo.get("methodName");
				
				Method invokeMethod = clazz.getMethod(methodName);
				methodValue = StringUtils.nullToString((String) invokeMethod.invoke(this));
				
				mailTemplate = mailTemplate.replaceAll("\\{" + patternString + "\\}", methodValue);
				
			} catch (Exception e) {
				log.warn("[경고] 메일 템플릿 변환 : 패턴에 해당되는 메서드를 찾을 수 없습니다. ({}) - {}"
						, "{" + fieldInfo.get("patternString") + "} --> " + fieldInfo.get("methodName")
						, e.getMessage(), e);
			}
			 
		}
		
		return mailTemplate;
	}
}
