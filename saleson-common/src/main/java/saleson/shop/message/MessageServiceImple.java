package saleson.shop.message;

import com.onlinepowers.framework.repository.support.EarlyLoadingMessageInfoRepository;
import com.onlinepowers.framework.repository.support.EarlyLoadingRepositoryEvent;
import com.onlinepowers.framework.web.domain.ListParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.shop.message.domain.Message;
import saleson.shop.message.support.MessageParam;

import java.util.List;


@Service("messageService")
public class MessageServiceImple implements MessageService {

	@Autowired
	private MessageMapper messageMapper;

	@Autowired
	private EarlyLoadingMessageInfoRepository messageInfoRepository;

	@Override
	public int getMessageMaxid() {
		return messageMapper.getMessageMaxid();
	}

	@Override
	public int getMessageCount(MessageParam messageParam) {
		return messageMapper.getMessageCount(messageParam);
	}


	@Override
	public List<Message> getMessageList(MessageParam messageParam) {
		return messageMapper.getMessageList(messageParam);
	}

	@Override
	public void insertMessage1(Message message) {
		
		messageMapper.insertMessage1(message);
		reload();
	}
	@Override
	public void insertMessage2(Message message) {
		messageMapper.insertMessage2(message);
		reload();
	}

	
	@Override
	public void deleteMessageData(ListParam listParam) {
		if (listParam.getId() != null) {
			for (String id : listParam.getId()) {
				messageMapper.deleteMessage(id);
			}	
		}
		reload();
	}

	@Override
	public Message getMessageById(String id) {
		return messageMapper.getMessageById(id);
	}

	@Override
	public void updatekMessage(Message message) {
		messageMapper.updatekMessage(message);
		reload();
	}
	@Override
	public void updatejMessage(Message message) {
		messageMapper.updatejMessage(message);
		reload();
	}

	@Override
	public void reload() {
		// DB 메시지 reload
		EarlyLoadingRepositoryEvent messageReloadEvent = new EarlyLoadingRepositoryEvent("messageInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
		messageInfoRepository.onApplicationEvent(messageReloadEvent);
	}
}
