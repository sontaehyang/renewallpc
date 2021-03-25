package saleson.shop.smsconfig;

import saleson.shop.smsconfig.domain.SmsConfig;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("smsConfigMapper")
public interface SmsConfigMapper {
	
	/**
	 * TEMPLATE_ID로 SMS Config 조회
	 * @param templateId
	 * @return
	 */
	SmsConfig getSmsConfigByTemplateId(String templateId);
	
	/**
	 * Sms 발송 설정 등록
	 * @param smsConfig
	 */
	void insertSmsConfig(SmsConfig smsConfig);
	
	/**
	 * Sms 발송 설정 수정
	 * @param smsConfig
	 */
	void updateSmsConfig(SmsConfig smsConfig);
}
