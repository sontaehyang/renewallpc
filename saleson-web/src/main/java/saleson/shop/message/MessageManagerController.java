package saleson.shop.message;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.message.domain.Message;
import saleson.shop.message.support.MessageParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/opmanager/message/**")
@RequestProperty(title="MESSAGE", layout="default")
public class MessageManagerController {
	private static final Logger log = LoggerFactory.getLogger(MessageManagerController.class);
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * MESSAGE 리스트
	 * @param messageParam
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String messageList(MessageParam messageParam, Model model) {
		
		int messageCount = messageService.getMessageCount(messageParam);
		
		Pagination pagination = Pagination.getInstance(messageCount);
		
		messageParam.setPagination(pagination);
		
		List<Message> messageList = messageService.getMessageList(messageParam);

		model.addAttribute("messageCount", messageCount);
		model.addAttribute("messageList", messageList);
		model.addAttribute("messageParam", messageParam);
		model.addAttribute("pagination", pagination);
		
		return ViewUtils.view();
	}
	
	/**
	 * MESSAGE 등록
	 * @param model
	 * @param message
	 * @return
	 */
	@GetMapping("create")
	public String messageInsert(Model model, Message message) {

		//messageService.insertMessage(message);
		//model.addAttribute("languageInfoList", CodeUtils.getCodeList("LANGUAGE"));
		
		return ViewUtils.getManagerView("/message/form");
	}

	/**
	 * MESSAGE 등록처리
	 * @param message
	 * @param request
	 * @return
	 */
	@PostMapping("create")
	public String messageInsertAction(Message message, HttpServletRequest request) {
		
		String inputId = request.getParameter("id");
		
		if (inputId.isEmpty() || inputId.length() == 0) {
			messageService.insertMessage2(message);  // id 자동생성 
			return ViewUtils.redirect("/opmanager/message/list", MessageUtils.getMessage("M00632"));	// 등록되었습니다
		} else {
			messageService.insertMessage1(message);  // id 있는경우
			return ViewUtils.redirect("/opmanager/message/list", MessageUtils.getMessage("M00632"));	// 등록되었습니다
		}
		
	}
	
	/**
	 * MESSAGE 수정
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("edit/{id}")
	public String messageUpdate(@PathVariable("id") String id, Model model) {
		
		Message message = messageService.getMessageById(id);
		
		model.addAttribute("message", message);

		return ViewUtils.getManagerView("/message/form");
	}

	/**
	 * MESSAGE 수정처리
	 * @param id
	 * @param message
	 * @return
	 */
	@PostMapping("edit/{id}")
	public String messageUpdateAction(@PathVariable("id") String id, Message message) {

		message.setId(id);
		messageService.updatekMessage(message);
		messageService.updatejMessage(message);
		
		return ViewUtils.redirect("/opmanager/message/list", MessageUtils.getMessage("M01673"));	// 수정되었습니다.
	}
	
	/**
	 * MESSAGE 데이터 삭제
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping("delete")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		messageService.deleteMessageData(listParam);
		return JsonViewUtils.success();
	}
}