package saleson.shop.sendmaillog;

import java.util.List;

import saleson.shop.config.domain.Config;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.sendmaillog.domain.SendMailLog;
import saleson.shop.sendmaillog.support.SendMailLogParam;

import com.onlinepowers.framework.web.domain.ListParam;

public interface SendMailLogService {
	
	/**
	 * 메일링 로그 카운트
	 * @param sendMailLogParam
	 * @return
	 */
	public int getSendMailLogCount(SendMailLogParam sendMailLogParam);
	
	/**
	 * 주문 발송 메일 카운트 - ORDER_ID , ORDER_STATUS
	 * @param orderId
	 * @param orderStatus
	 * @return
	 */
	public int getOrderMailLogCountByOrderId(int orderId, String orderStatus);
	
	/**
	 * 메일 발송 상세
	 * @param sendMailLogId
	 * @return
	 */
	public SendMailLog getSendMailLogById(int sendMailLogId);
	
	/**
	 * 메일링 로그 리스트
	 * @param sendMailLogParam
	 * @return
	 */
	public List<SendMailLog> getSendMailLogList(SendMailLogParam sendMailLogParam);
	
	/**
	 * 메일링 로그를 조회 - List - 주문 상세
	 * @param sendMailLogParam
	 * @return
	 */
	public List<SendMailLog> getSendMailLogListForOrderDetail(SendMailLogParam sendMailLogParam);
	
	/**
	 * 메일 발송 로그를 저장
	 * @param sendMailLog
	 */
	public void insertSendMailLogNoTx(SendMailLog sendMailLog);
	
	/**
	 * 리스트 - 선택 삭제
	 * @param listParam
	 */
	public void deleteListData(ListParam listParam);
	
	/**
	 * 메일 발송 + 로그 등록
	 * @param mailConfig
	 * @param sendMailLog
	 * @param email
	 * @param name
	 */
	public void sendMail(MailConfig mailConfig, SendMailLog sendMailLog, String email, String name);
	
	/**
	 * 메일 발송 + 로그 등록
	 * @param mailConfig
	 * @param sendMailLog
	 * @param email
	 * @param name
	 */
	public void sendMail(MailConfig mailConfig, SendMailLog sendMailLog, String email, String name, Config config);
}
