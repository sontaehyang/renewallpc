package saleson.shop.message;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.shop.message.domain.Message;
import saleson.shop.message.support.MessageParam;

import java.util.List;

@Mapper("messageMapper")
public interface MessageMapper {
	
	/**
	 * MESSAGE max id + 1
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
	 * MESSAGE 등록
	 * @param message
	 */
	void insertMessage1(Message message);
	/**
	 * MESSAGE 등록
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
	/**
	 * MESSAGE 수정처리
	 * @param message
	 */
	void updatejMessage(Message message);
	
	/**
	 * MESSAGE 삭제
	 * @param id
	 */
	void deleteMessage(String id);
}
