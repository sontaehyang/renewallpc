package saleson.shop.sendsmslog;

import java.util.List;

import saleson.shop.sendsmslog.domain.SendSmsLog;
import saleson.shop.sendsmslog.support.SendSmsLogParam;
import saleson.shop.smsconfig.domain.SmsConfig;

public interface SendSmsLogService {

	/**
	 * 문자 발송
	 * @param smsConfig
	 * @param sendSmsLog
	 * @param telNumber
	 */
	public void sendSms(SmsConfig smsConfig, SendSmsLog sendSmsLog, String telNumber);
	
	/**
	 * 문자 발송 내역 count
	 * @param sendSmsLogParam
	 */
	public int getSendSmsLogCount(SendSmsLogParam sendSmsLogParam);

	/**
	 * 문자 발송 내역 리스트
	 * @param sendSmsLogParam
	 */
	public List<SendSmsLog> getSendSmsLogList(SendSmsLogParam sendSmsLogParam);
	
	/**
	 * 메일 발송 상세
	 * @param sendSmsLogId
	 * @return
	 */
	public SendSmsLog getSendSmsLogById(int sendSmsLogId);

	/**
	 * Sms 발송 내역
	 * @param sendType
	 * @return
	 */
	public List<SendSmsLog> getSendSmsLogListBySendType(String sendType);
}
