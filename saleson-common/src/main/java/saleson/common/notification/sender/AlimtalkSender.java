package saleson.common.notification.sender;

import com.onlinepowers.framework.notification.message.OpMessage;

public interface AlimtalkSender {
    void sendMessage(OpMessage opMessage);
}
