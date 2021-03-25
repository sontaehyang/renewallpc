package saleson.common.notification.sender;

import com.onlinepowers.framework.notification.message.OpMessage;

import java.util.List;

public interface PushSender {
    void sendMessage(OpMessage opMessage);
    void sendMessages(List<OpMessage> opMessages);
}
