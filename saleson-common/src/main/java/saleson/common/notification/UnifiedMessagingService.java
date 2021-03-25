package saleson.common.notification;

import com.onlinepowers.framework.notification.NotificationService;
import com.onlinepowers.framework.notification.exception.NotificationException;
import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.notification.sms.SmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.enumeration.UmsType;
import saleson.common.notification.sender.AlimtalkSender;
import saleson.common.notification.sender.PushSender;
import saleson.model.UmsSendLog;
import saleson.shop.ums.UmsSendLogRepository;

@Service("unifiedMessagingService")
public class UnifiedMessagingService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(UnifiedMessagingService.class);

    @Autowired
    SmsSender smsSender;

    @Autowired
    AlimtalkSender alimtalkSender;

    @Autowired
    PushSender pushSender;

    @Autowired
    UmsSendLogRepository umsSendLogRepository;

    @Override
    public void sendMessage(OpMessage opMessage) throws NotificationException {

        try {

            UmsType umsType = getUmsType(opMessage);
            boolean sendFlag = false;

            if (umsType == UmsType.MESSAGE) {
                smsSender.sendSms(opMessage);
                sendFlag = true;
            } else if (umsType == UmsType.ALIM_TALK) {
                alimtalkSender.sendMessage(opMessage);
                sendFlag = true;
            } else if (umsType == UmsType.PUSH) {
                pushSender.sendMessage(opMessage);
                sendFlag = true;
            } else {
                throw new IllegalArgumentException("지정 되지 않은 UMS 타입 입니다.");
            }

            if (sendFlag) {
                saveUmsLog(opMessage);
            }

        } catch (Exception e) {
            throw new NotificationException(e);
        }
    }

    public UmsType getUmsType(OpMessage opMessage) {

        String smsType = opMessage.getSmsType();

        if (smsType.equals(UmsType.ALIM_TALK.getCode())) {
            return UmsType.ALIM_TALK;
        }

        if (smsType.equals(UmsType.PUSH.getCode())) {
            return UmsType.PUSH;
        }

        if ("sms".equals(smsType) || "lms".equals(smsType) || "mms".equals(smsType)) {
            return UmsType.MESSAGE;
        }

        return null;
    }

    private void saveUmsLog(OpMessage opMessage) {

        try {
            UmsSendLog umsSendLog = new UmsSendLog(opMessage, getUmsType(opMessage));
            umsSendLogRepository.save(umsSendLog);
        } catch (Exception e) {
            logger.error("UMS LOG ERROR", e);
        }
    }


}
