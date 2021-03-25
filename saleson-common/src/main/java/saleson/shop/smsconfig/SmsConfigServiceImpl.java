package saleson.shop.smsconfig;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.smsconfig.domain.SmsConfig;
import saleson.shop.smsconfig.support.MemberJoinSms;
import saleson.shop.smsconfig.support.OrderDeliveringSms;
import saleson.shop.smsconfig.support.OrderBankSms;
import saleson.shop.smsconfig.support.OrderNewSms;
import saleson.shop.smsconfig.support.PwsearchSms;
import saleson.shop.smsconfig.support.UserSms;

import com.onlinepowers.framework.sequence.service.SequenceService;

@Service("smsConfigService")
public class SmsConfigServiceImpl implements SmsConfigService {

	@Autowired
	private SmsConfigMapper smsConfigMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public SmsConfig getSmsConfigByTemplateId(String templateId) {
		return smsConfigMapper.getSmsConfigByTemplateId(templateId);
	}

	@Override
	public HashMap<String, String> getSmsChangeCodes(String templateId) {
		HashMap<String, String> codeMap = new HashMap<>();
	
		if ("order_deposit_wait".equals(templateId)) {
			codeMap = new OrderBankSms().getCodes();
		} else if ("order_cready_payment".equals(templateId)) {
			codeMap = new OrderNewSms().getCodes();
		} else if ("order_delivering".equals(templateId)) {
			codeMap = new OrderDeliveringSms().getCodes();
		} else if ("pwsearch".equals(templateId)) {
			codeMap = new PwsearchSms().getCodes();
		} else if ("member_join".equals(templateId)) {
			codeMap = new MemberJoinSms().getCodes();
		} else if ("user_sms".equals(templateId)) {
			codeMap = new UserSms().getCodes();
		}
		
		return codeMap;
	}
	
	@Override
	public void insertSmsConfig(SmsConfig smsConfig) {
		
		smsConfig.setSmsConfigId(sequenceService.getId("OP_SMS_CONFIG"));
		smsConfigMapper.insertSmsConfig(smsConfig);
		
	}

	@Override
	public void updateSmsConfig(SmsConfig smsConfig) {
		
		smsConfigMapper.updateSmsConfig(smsConfig);
		
	}

	
	
}
