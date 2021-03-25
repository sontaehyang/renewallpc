package saleson.shop.sendsmslog;

import java.util.List;
import saleson.shop.sendsmslog.domain.SendSmsLog;
import saleson.shop.sendsmslog.support.SendSmsLogParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("sendSmsLogMapper")
public interface SendSmsLogMapper {

    /**
     * Sms 발송 로그 등록
     *
     * @param sendSmsLog
     */
    void insertSendSmsLog(SendSmsLog sendSmsLog);

    /**
     * 문자 발송 내역 count
     *
     * @param sendSmsLogParam
     */
    int getSendSmsLogCount(SendSmsLogParam sendSmsLogParam);


    /**
     * 문자 발송 내역 리스트
     *
     * @param sendSmsLogParam
     */
    List<SendSmsLog> getSendSmsLogList(SendSmsLogParam sendSmsLogParam);

    /**
     * 메일 발송 상세
     *
     * @param sendSmsLogId
     * @return
     */
    SendSmsLog getSendSmsLogById(int sendSmsLogId);

    /**
     * Sms발송 내역
     *
     * @param sendType
     * @return
     */
    List<SendSmsLog> getSendSmsLogListBySendType(String sendType);

}
