package saleson.shop.sendmaillog;

import java.util.List;

import saleson.shop.sendmaillog.domain.SendMailLog;
import saleson.shop.sendmaillog.support.SendMailLogParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("sendMailLogMapper")
public interface SendMailLogMapper {

	/**
	 * 메일링 로그 카운트
	 * @param sendMailLogParam
	 * @return
	 */
	int getSendMailLogCount(SendMailLogParam sendMailLogParam);
	
	/**
	 * 주문 발송 메일 카운트 - ORDER_ID , ORDER_STATUS
	 * @param sendMailLogParam
	 * @return
	 */
	int getOrderMailLogCountByOrderId(SendMailLogParam sendMailLogParam);
	
	/**
	 * 메일 발송 상세
	 * @param sendMailLogId
	 * @return
	 */
	SendMailLog getSendMailLogById(int sendMailLogId);
	
	/**
	 * 메일링 로그 리스트
	 * @param sendMailLogParam
	 * @return
	 */
	List<SendMailLog> getSendMailLogList(SendMailLogParam sendMailLogParam);
	
	/**
	 * 메일링 로그를 조회 - List - 주문 상세
	 * @param sendMailLogParam
	 * @return
	 */
	List<SendMailLog> getSendMailLogListForOrderDetail(SendMailLogParam sendMailLogParam);
	
	/**
	 * 메일 발송 로그를 저장
	 * @param sendMailLog
	 */
	void insertSendMailLog(SendMailLog sendMailLog);
	
	/**
	 * 삭제
	 * @param id
	 */
	void deleteSendMailLog(String sendMailLogId);
}
