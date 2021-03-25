package saleson.common.notification.sender;

import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.util.JsonViewUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DefaultPushSender implements PushSender{

    private static final Logger logger = LoggerFactory.getLogger(DefaultPushSender.class);

    @Override
    public void sendMessage(OpMessage opMessage) {
        logger.debug("Push Sender 구현 필요");
    }

    @Override
    public void sendMessages(List<OpMessage> opMessages) { logger.debug("Push Sender 구현 필요"); }
}
