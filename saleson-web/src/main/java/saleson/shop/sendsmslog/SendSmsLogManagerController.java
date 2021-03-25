package saleson.shop.sendsmslog;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import saleson.shop.sendsmslog.domain.SendSmsLog;
import saleson.shop.sendsmslog.support.SendSmsLogParam;
import saleson.shop.smsconfig.support.SmsTemplate;

@Controller
@RequestMapping("/opmanager/send-sms-log")
@RequestProperty(title="SMS발송로그", layout="default")
public class SendSmsLogManagerController {
	
	@Autowired
	private SendSmsLogService sendSmsLogService;
	
	@GetMapping("list")
	public String list(@ModelAttribute SendSmsLogParam sendSmsLogParam, Model model) {
		
		int totalCount = sendSmsLogService.getSendSmsLogCount(sendSmsLogParam);
		Pagination pagination = Pagination.getInstance(totalCount, sendSmsLogParam.getItemsPerPage());
		sendSmsLogParam.setPagination(pagination);
		
		SmsTemplate smsTemplate = new SmsTemplate();
		
		model.addAttribute("smsTemplateCodeList", smsTemplate.getTemplateCodes());
		model.addAttribute("list", sendSmsLogService.getSendSmsLogList(sendSmsLogParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("totalCount", totalCount);
		
		return ViewUtils.view();
	}
	
	/**
	 * 메일 발송 상세
	 * @param model
	 * @param sendSmsLogId
	 * @return
	 */
	@GetMapping("view")
	@RequestProperty(layout="base")
	public String view(Model model, 
			@RequestParam(value="id", required=true) int sendSmsLogId) {
		
		SendSmsLog sendSmsLog = sendSmsLogService.getSendSmsLogById(sendSmsLogId);
		
		if (sendSmsLog == null) {
			return "xxx";
		}
		
		String sendType = sendSmsLog.getSendType();
		String title = "";
		if (sendType != null) {
			
			SmsTemplate smsTemplate = new SmsTemplate();
			
			String templateId = sendSmsLog.getSendType();
			String titleCode = "";
			
			if (!templateId.equals("")) {
				for(String s : smsTemplate.getTemplateCodes().keySet()) {
					if (s.equals(templateId)) {
						titleCode = smsTemplate.getTemplateCodes().get(s);
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
		model.addAttribute("sendSmsLog", sendSmsLog);
		return ViewUtils.view();
	}
}
