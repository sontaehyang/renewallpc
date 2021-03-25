package saleson.shop.message;

import com.onlinepowers.framework.web.domain.ListParam;
import saleson.shop.message.domain.Message;
import saleson.shop.message.support.MessageParam;

import java.util.List;


public interface MessageService {

	/**
	 * MESSAGE 아이디생성용 max id + 1 값
	 * @return
	 */
	int getMessageMaxid();
	
	/**
	 * MESSAGE 수량 조회
	 * @param messageParam
	 * @return
	 */
	int getMessageCount(MessageParam messageParam);
	
	/**
	 * MESSAGE 리스트 조회
	 * @param messageParam
	 * @return
	 */
	List<Message> getMessageList(MessageParam messageParam);
	
	/**
	 * MESSAGE 한국어, 일본어 등록 
	 * @param message
	 */
	void insertMessage1(Message message);
	
	/**
	 * MESSAGE 한국어, 일본어 등록 
	 * @param message
	 */
	void insertMessage2(Message message);

	/**
	 * MESSAGE 수정
	 * @param id
	 * @return
	 */
	Message getMessageById(String id);
	
	/**
	 * MESSAGE 수정처리
	 * @param message
	 */
	void updatekMessage(Message message);
	void updatejMessage(Message message);
	
	/**
	 * MESSAGE 삭제
	 * @param listParam
	 */
	void deleteMessageData(ListParam listParam);

	/**
	 * Message Reload
	 * @throws Exception
	 */
	void reload();
}
