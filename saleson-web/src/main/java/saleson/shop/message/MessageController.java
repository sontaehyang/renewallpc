package saleson.shop.message;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.message.domain.Message;
import saleson.shop.message.support.MessageParam;

import java.util.List;


@Controller
@RequestMapping("/message")
@RequestProperty(title="MESSAGE", layout="customer")
public class MessageController {
	private static final Logger log = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping
	public String messageIndex(MessageParam messageParam, Model model) {

		String searchKey = messageParam.getQuery(); 
		
		if (StringUtils.isEmpty(searchKey)) {
			searchKey = "";
		}
		
		messageParam.setQuery(searchKey);
		messageParam.setItemsPerPage(10);
		
		int messageCount = messageService.getMessageCount(messageParam);
		
		Pagination pagination = Pagination.getInstance(messageCount);
		
		messageParam.setPagination(pagination);
		
		List<Message> messageList = messageService.getMessageList(messageParam);
		
		for (Message message : messageList) {
			message.setkMessage(message.getkMessage().replace("</p>", "</p><br>"));
		}
		
		model.addAttribute("messageList", messageList);
		model.addAttribute("messageParam", messageParam);
		model.addAttribute("pagination", pagination);
		
		return ViewUtils.getView("/message/list");
	}
	
}


