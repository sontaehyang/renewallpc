package saleson.common.notification.sender;

import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.notification.sms.SmsSender;

import java.util.List;

public interface MessageSender extends SmsSender {

    @Override
    void sendSms(OpMessage opMessage);

    void sendSmsList(List<OpMessage> opMessages);
}