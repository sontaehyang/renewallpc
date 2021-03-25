package saleson.shop.mailconfig;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.MailConfigSearchParam;
import saleson.shop.mailconfig.support.MemberExpirationPointMail;
import saleson.shop.mailconfig.support.MemberJoinMail;
import saleson.shop.mailconfig.support.MemberSleepMail;
import saleson.shop.mailconfig.support.OrderMail;
import saleson.shop.mailconfig.support.OrderDeliveringMail;
import saleson.shop.mailconfig.support.PwsearchMail;

import com.onlinepowers.framework.sequence.service.SequenceService;


@Service("mailConfigService")
public class MailConfigServiceImpl implements MailConfigService {

	@Autowired
	MailConfigMapper mailConfigMapper;
	
	@Autowired
	SequenceService sequenceService;
	
	@Override
	public HashMap<String, String> getMailChangeCodes(String templateId) {
		HashMap<String, String> codeMap = new HashMap<>();
	
		if ("order_deposit_wait".equals(templateId)) {
			codeMap = new OrderMail().getCodes();
		} else if ("order_cready_payment".equals(templateId)) {
			codeMap = new OrderMail().getCodes();
		} else if ("order_delivering".equals(templateId)) {
			codeMap = new OrderDeliveringMail().getCodes();
		} else if ("pwsearch".equals(templateId)) {
			codeMap = new PwsearchMail().getCodes();
		} else if ("member_join".equals(templateId)) {
			codeMap = new MemberJoinMail().getCodes();
		} else if ("expiration_point".equals(templateId)) {
			codeMap = new MemberExpirationPointMail().getCodes();
		} else if ("member_sleep".equals(templateId)) {
			codeMap = new MemberSleepMail().getCodes();
		} 
		
		return codeMap;
	}
	
	@Override
	public int getMailConfigCount(MailConfigSearchParam mailConfigSearchParam) {
		return mailConfigMapper.getMailConfigCount(mailConfigSearchParam);
	}

	@Override
	public ArrayList<MailConfig> getMailConfigList(
			MailConfigSearchParam mailConfigSearchParam) {
		return mailConfigMapper.getMailConfigList(mailConfigSearchParam);
	}

	@Override
	public MailConfig getMailConfigDetailsById(
			MailConfigSearchParam mailConfigSearchParam) {
		return mailConfigMapper.getMailConfigDetailsById(mailConfigSearchParam);
	}

	@Override
	public MailConfig getMailConfigByTemplateId(String templateId) {
		return mailConfigMapper.getMailConfigByTemplateId(templateId.toLowerCase());
	}
	
	@Override
	public void insertMailConfig(MailConfig mailConfig) {
		mailConfig.setMailConfigId(sequenceService.getId("OP_MAIL_CONFIG"));
		mailConfigMapper.insertMailConfig(mailConfig);
		
	}

	@Override
	public void updateMailConfigById(MailConfig mailConfig) {
		mailConfigMapper.updateMailConfigById(mailConfig);
	}

	@Override
	public void deleteMailConfigById(MailConfig mailConfig) {
		mailConfigMapper.deleteMailConfigById(mailConfig);
	}
}
