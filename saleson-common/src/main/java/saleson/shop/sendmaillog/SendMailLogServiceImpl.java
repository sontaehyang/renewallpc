package saleson.shop.sendmaillog;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import saleson.common.Const;
import saleson.common.config.SalesonProperty;
import saleson.common.notification.message.ShopMailMessage;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.MailTemplate;
import saleson.shop.order.OrderMapper;
import saleson.shop.sendmaillog.domain.SendMailLog;
import saleson.shop.sendmaillog.support.SendMailLogParam;

import com.onlinepowers.framework.notification.NotificationService;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.web.domain.ListParam;

@Service("sendMailLogService")
public class SendMailLogServiceImpl implements SendMailLogService{

	@Autowired
	private SendMailLogMapper sendMailLogMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired 
	@Qualifier("mailService")
	NotificationService mailService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public int getSendMailLogCount(SendMailLogParam sendMailLogParam) {
		return sendMailLogMapper.getSendMailLogCount(sendMailLogParam);
	}
	
	@Override
	public int getOrderMailLogCountByOrderId(int orderId, String orderStatus) {
		
		SendMailLogParam sendMailLogParam = new SendMailLogParam();
		//sendMailLogParam.setOrderId(orderId);
		sendMailLogParam.setOrderStatus(orderStatus);
		return sendMailLogMapper.getOrderMailLogCountByOrderId(sendMailLogParam);
	}
	
	@Override
	public SendMailLog getSendMailLogById(int sendMailLogId) {
		return sendMailLogMapper.getSendMailLogById(sendMailLogId);
	}
	
	@Override
	public List<SendMailLog> getSendMailLogList(SendMailLogParam sendMailLogParam) {
		List<SendMailLog> list = sendMailLogMapper.getSendMailLogList(sendMailLogParam);
		
		MailTemplate mailTemplate = new MailTemplate();
		
		for(SendMailLog log : list) {
			for(String s : mailTemplate.getTemplateCodes().keySet()) {
				if (s.equals(log.getSendType())) {
					log.setOrderStatusLabel(mailTemplate.getTemplateCodes().get(s));
					break;
				}
			}
		}
		
		return list;
	}
	
	@Override
	public List<SendMailLog> getSendMailLogListForOrderDetail(SendMailLogParam sendMailLogParam) {
		List<SendMailLog> list = sendMailLogMapper.getSendMailLogListForOrderDetail(sendMailLogParam);

		List<Code> orderStatus = CodeUtils.getCodeList("ORDER_STATUS");
			
		if (ValidationUtils.isNotNull(orderStatus)) {
			for(SendMailLog log : list) {
				for(int i = 0; i < orderStatus.size(); i++) {
					if (orderStatus.get(i).getValue().equals(log.getOrderStatus())) {
						log.setOrderStatusLabel(orderStatus.get(i).getLabel());
						break;
					}
				}
			}
		}
		
		return list;
	}
	
	@Override
	public void insertSendMailLogNoTx(SendMailLog sendMailLog) {
		sendMailLog.setUseTagFlag("Y");
		sendMailLogMapper.insertSendMailLog(sendMailLog);
	}
	
	@Override
	public void deleteListData(ListParam listParam) {
		if (listParam.getId() != null) {
			
			for (String id : listParam.getId()) {
				sendMailLogMapper.deleteSendMailLog(id);
			}
			
		}
	}
	
	@Override
	public void sendMail(MailConfig mailConfig, SendMailLog sendMailLog, String email, String name, Config config) {
		
		List<Code> hpMailList = CodeUtils.getCodeList("HPMAIL_DOMAIN");
		boolean isHpMail = false;
		if (ValidationUtils.isNotNull(hpMailList)) {
			for(Code c : hpMailList) {
				if (email.endsWith(c.getId())) {
					isHpMail = true;
					break;
				}
			}
		}
		
		String prefix = isHpMail ? "hpmail-" : "";
		
		String adminEmail = SalesonProperty.getMailSender();

		sendMailLog.setSendName(config.getShopName());
		
		
		
		if( SecurityUtils.hasRole("ROLE_OPMANAGER") ){
			sendMailLog.setSendLoginId(UserUtils.getLoginId());
		}
		
		if ("Y".equals(mailConfig.getBuyerSendFlag())) {
			
			String buyerSubject = isHpMail ? mailConfig.getMobileBuyerSubject() : mailConfig.getBuyerSubject();
			String buyerContent = isHpMail ? mailConfig.getMobileBuyerContent() : mailConfig.getBuyerContent();
			
			//회원가입 확인 메일 보낼 때 오류발생하여 주석처리 2017-03-14 yulsun.yoo
			//buyerContent = buyerContent.replaceAll("\\{(?:.|\\s)*?\\}", "");
			buyerSubject = buyerSubject.replaceAll("\\{(?:.|\\s)*?\\}", "");
			
			
			
			try {
				
				ShopMailMessage mail = new ShopMailMessage(email, buyerSubject, buyerContent, adminEmail);
				mail.setHtml("Y".equals(mailConfig.getBuyerTagUse()) ? true : false);
				if (mail.isHTML() == false) {
					buyerContent = buyerContent.replaceAll("<!--(?:.|\\s)*?-->", "");
					mail = new ShopMailMessage(email, buyerSubject, buyerContent, adminEmail);
				}
				
				if (mailConfig.getBcc() != null) {
					if (!"".equals(mailConfig.getBcc())) {
						mail.setBcc(mailConfig.getBcc());
					}
				}

				sendMailLog.setSendDate(DateUtils.getToday(Const.DATETIME_FORMAT));
				sendMailLog.setSendFlag("Y");

				mailService.sendMessage(mail);



			} catch(Exception e) {
				sendMailLog.setSendFlag("N");
				sendMailLog.setSendDate("");
			} finally {
			
				sendMailLog.setSendEmail(adminEmail);
				sendMailLog.setContent(buyerContent);
				sendMailLog.setSubject(buyerSubject);
				sendMailLog.setReceiveName(name);
				sendMailLog.setReceiveEmail(email);
				sendMailLog.setMailType("buyer");
				sendMailLog.setSendType(prefix + sendMailLog.getSendType());
				
				insertSendMailLogNoTx(sendMailLog);
			}
		}
		
		if ("Y".equals(mailConfig.getAdminSendFlag())) {
			
			if (StringUtils.isNotEmpty(adminEmail)) {
				String adminContent = mailConfig.getAdminContent();
				String adminSubject = mailConfig.getAdminSubject();
				
				adminContent = adminContent.replaceAll("\\{(?:.|\\s)*?\\}", "");
				adminSubject = adminSubject.replaceAll("\\{(?:.|\\s)*?\\}", "");
				
				String adminSendEmail = adminEmail;
				if (sendMailLog.getSendType().trim().indexOf("qna_complete") > -1) {
					adminSendEmail = email;
				};
				try {

					
					ShopMailMessage mail = new ShopMailMessage(adminEmail, adminSubject, adminContent, adminSendEmail);
					mail.setHtml("Y".equals(mailConfig.getAdminTagUse()) ? true : false);
					
					if (mail.isHTML() == false) {
						adminContent = adminContent.replaceAll("<!--(?:.|\\s)*?-->", "");
						mail = new ShopMailMessage(adminEmail, adminSubject, adminContent, adminSendEmail);
					}

					sendMailLog.setSendFlag("Y");
					sendMailLog.setSendDate(DateUtils.getToday(Const.DATETIME_FORMAT));

					mailService.sendMessage(mail);

				} catch(Exception e) {
					sendMailLog.setSendFlag("N");
					sendMailLog.setSendDate("");
				} finally {
					sendMailLog.setSendEmail(adminSendEmail);
					sendMailLog.setContent(adminContent);
					sendMailLog.setSubject(adminSubject);
					sendMailLog.setReceiveName(config.getCompanyName());
					sendMailLog.setReceiveEmail(adminEmail);
					sendMailLog.setMailType("admin");
					sendMailLog.setSendType(sendMailLog.getSendType().replace(prefix, ""));
					
					insertSendMailLogNoTx(sendMailLog);
				}
			}
		}
	}
	
	@Override
	public void sendMail(MailConfig mailConfig, SendMailLog sendMailLog, String email, String name) {
		Config config = ShopUtils.getConfig();
		this.sendMail(mailConfig, sendMailLog, email, name, config);
	}
}
