package saleson.shop.smsconfig;

import java.util.HashMap;

import saleson.shop.smsconfig.domain.SmsConfig;
import saleson.shop.smsconfig.support.SmsConfigParam;

public interface SmsConfigService {
	
	public HashMap<String, String> getSmsChangeCodes(String templateId);
	
	/**
	 * TEMPLATE_ID로 SMS Config 조회
	 * @param smsConfigParam
	 * @return
	 */
	public SmsConfig getSmsConfigByTemplateId(String templateId);
	
	/**
	 * Sms 발송 설정 등록
	 * @param smsConfig
	 */
	public void insertSmsConfig(SmsConfig smsConfig);
	
	/**
	 * Sms 발송 설정 수정
	 * @param smsConfig
	 */
	public void updateSmsConfig(SmsConfig smsConfig);
}
