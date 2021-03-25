package saleson.shop.sendsmslog;

import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.notification.message.SmsMessage;
import com.onlinepowers.framework.notification.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.shop.sendsmslog.domain.SendSmsLog;
import saleson.shop.sendsmslog.support.SendSmsLogParam;
import saleson.shop.smsconfig.domain.SmsConfig;

import java.util.List;

@Service("sendSmsLogService")
public class SendSmsLogServiceImpl implements SendSmsLogService {

	@Autowired
	private SendSmsLogMapper sendSmsLogMapper;

	@Autowired
	SmsService smsService;

	@Override
	public void sendSms(SmsConfig smsConfig, SendSmsLog sendSmsLog, String telNumber) {

		if ("Y".equals(smsConfig.getBuyerSendFlag())) {

			OpMessage message = new SmsMessage(telNumber, smsConfig.getBuyerContent(), "", smsConfig.getSmsType(), smsConfig.getBuyerTitle());

			// SMS 발송.
			smsService.sendMessage(message);

			sendSmsLog.setReceiveTelNumber(telNumber);
			sendSmsLog.setContent(message.getMessage());

			sendSmsLogMapper.insertSendSmsLog(sendSmsLog);
		}
	}

	@Override
	public int getSendSmsLogCount(SendSmsLogParam sendSmsLogParam) {
		return sendSmsLogMapper.getSendSmsLogCount(sendSmsLogParam);
	}

	@Override
	public List<SendSmsLog> getSendSmsLogList(SendSmsLogParam sendSmsLogParam) {
		return sendSmsLogMapper.getSendSmsLogList(sendSmsLogParam);
	}

	@Override
	public SendSmsLog getSendSmsLogById(int sendSmsLogId) {
		return sendSmsLogMapper.getSendSmsLogById(sendSmsLogId);
	}

	@Override
	public List<SendSmsLog> getSendSmsLogListBySendType(String sendType) {
		return sendSmsLogMapper.getSendSmsLogListBySendType(sendType);
	}
}
