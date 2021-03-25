package saleson.common.notification.sender;

import com.onlinepowers.framework.notification.message.OpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAlimtalkSender implements AlimtalkSender{


    private static final Logger logger = LoggerFactory.getLogger(DefaultAlimtalkSender.class);

    @Override
    public void sendMessage(OpMessage opMessage) {
        logger.debug("Alimtalk Sender 구현 필요");
    }
}
