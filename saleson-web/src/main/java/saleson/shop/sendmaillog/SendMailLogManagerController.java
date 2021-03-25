package saleson.shop.sendmaillog;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.mailconfig.support.MailTemplate;
import saleson.shop.sendmaillog.domain.SendMailLog;
import saleson.shop.sendmaillog.support.SendMailLogParam;

@Controller
@RequestMapping("/opmanager/send-mail-log")
@RequestProperty(title="메일발송로그", layout="default")
public class SendMailLogManagerController {

	@Autowired
	private SendMailLogService sendMailLogService;
	
	@GetMapping("list")
	public String list(@ModelAttribute SendMailLogParam sendMailLogParam, Model model) {
		
		int totalCount = sendMailLogService.getSendMailLogCount(sendMailLogParam);
		Pagination pagination = Pagination.getInstance(totalCount, sendMailLogParam.getItemsPerPage());
		sendMailLogParam.setPagination(pagination);
		
		MailTemplate mailTemplate = new MailTemplate();
		
		model.addAttribute("mailTemplateCodeList", mailTemplate.getTemplateCodes());
		model.addAttribute("sendMailLogList", sendMailLogService.getSendMailLogList(sendMailLogParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("totalCount", totalCount);
		
		return ViewUtils.view();
	}
	
	/**
	 * 목록데이터 수정 - 선택삭제.
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping("list/delete")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		sendMailLogService.deleteListData(listParam);
		return JsonViewUtils.success();  
	}
	
	/**
	 * 메일 발송 상세
	 * @param model
	 * @param sendMailLogId
	 * @return
	 */
	@GetMapping("view")
	@RequestProperty(layout="base")
	public String view(Model model, 
			@RequestParam(value="id", required=true) int sendMailLogId) {
		
		SendMailLog sendMailLog = sendMailLogService.getSendMailLogById(sendMailLogId);
		
		if (sendMailLog == null) {
			return "xxx";
		}
		
		String sendType = sendMailLog.getSendType();
		String title = "";
		if (sendType != null) {
			
			MailTemplate mailTemplate = new MailTemplate();
			
			String templateId = "";
			String titleCode = "";
			sendType = sendType.replace("hpmail-", "");
			if (sendType.equals("order")) {
				templateId = "order_stats_" + sendMailLog.getOrderStatus();
			} else {
				templateId = sendType;
			}
			
			if (!templateId.equals("")) {
				for(String s : mailTemplate.getTemplateCodes().keySet()) {
					if (s.equals(templateId)) {
						titleCode = mailTemplate.getTemplateCodes().get(s);
						break;
					}
				}
			}
			
			if (!titleCode.equals("")) {
				title = MessageUtils.getMessage(titleCode);
			} else {
				title = MessageUtils.getMessage("M00604"); // 그 외 메일
			}
		}
		
		
		model.addAttribute("title", title);
		model.addAttribute("sendMailLog", sendMailLog);
		return ViewUtils.view();
	}
}
