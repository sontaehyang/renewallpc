package saleson.common.notification.micesoft;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.common.mapper.annotation.UnifiedMessagingMapper;
import saleson.common.notification.AgentMapper;
import saleson.common.notification.domain.CampaignStatistics;
import saleson.common.notification.domain.Table;
import saleson.common.notification.micesoft.domain.kakao.KkoMsg;
import saleson.common.notification.micesoft.domain.message.MmsMsg;
import saleson.common.notification.micesoft.domain.message.SmsMsg;
import saleson.common.notification.micesoft.domain.push.Pushmsg;
import saleson.common.notification.support.StatisticsParam;

import java.util.List;

//@UnifiedMessagingMapper
@Mapper
public interface MiceMapper extends AgentMapper {

    /**
     * 카카오 알림톡 메세지 등록
     * @param kkoMsg
     */
    int insertKkoMsg(KkoMsg kkoMsg);

    /**
     * 카카오 알림톡 대량 등록
     * @param kkoMsgs
     * @return
     */
    int insertKkoMsgs(List<KkoMsg> kkoMsgs);

    /**
     * MMS 메세지 등록
     * @param mmsMsg
     * @return
     */
    int insertMmsMsg(MmsMsg mmsMsg);

    /**
     * MMS 메세지 등록
     * @param mmsMsgs
     * @return
     */
    int insertMmsMsgBatch(List<MmsMsg> mmsMsgs);

    /**
     * SMS 메세지 등록
     * @param smsMsg
     * @return
     */
    int insertSmsMsg(SmsMsg smsMsg);

    /**
     * SMS 메세지 등록
     * @param smsMsgs
     * @return
     */
    int insertSmsMsgBatch(List<SmsMsg> smsMsgs);

    /**
     * PUSH 등록
     * @param pushmsg
     * @return
     */
    int insertPushmsg(Pushmsg pushmsg);

    /**
     * 대량 PUSH 등록
     * @param pushmsgs
     * @return
     */
    int insertPushmsgBatch(List<Pushmsg> pushmsgs);

}
