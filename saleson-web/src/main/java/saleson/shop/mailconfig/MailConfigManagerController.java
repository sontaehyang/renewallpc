package saleson.shop.mailconfig;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.MailConfigSearchParam;
import saleson.shop.mailconfig.support.MailTemplate;

@Controller
@RequestMapping("/opmanager/mail-config")
@RequestProperty(title="메일설정 관리", layout="default")
public class MailConfigManagerController {
	
	@Autowired
	private MailConfigService mailConfigService;
	
	
	/**
	 * 관리자 이메일 설정 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(@ModelAttribute("searchParam") MailConfigSearchParam searchParam, Model model) {
		
		MailTemplate mailTemplate = new MailTemplate();
		String templateId = mailTemplate.getFirstTemplateCodeKey();
		
		MailConfig mail = mailConfigService.getMailConfigByTemplateId(templateId);
		String redirectUrl = "/opmanager/mail-config/create/" + templateId;

		if (mail != null) {
			redirectUrl = "/opmanager/mail-config/edit/" + templateId;
		}
		
		return ViewUtils.redirect(redirectUrl);
		
		/* cjh - 2014. 05. 27 리스트 페이지 삭제 - 리스트 페이지 접근시 등록/수정 페이지로 이동
		Pagination pagination = Pagination.getInstance(mailConfigService.getMailConfigCount(searchParam), 10);

		
		searchParam.setPagination(pagination);
		
		model.addAttribute("mailConfigList",mailConfigService.getMailConfigList(searchParam));
		model.addAttribute("pagination", pagination);
		
		return ViewUtils.view();
		*/
		
	}
	
	/**
	 * 관리자 이메일 설정 등록폼
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("create/{templateId}")
	public String create(@PathVariable("templateId") String templateId, Model model, MailConfig mailConfig) {
		
		MailTemplate mailTemplate = new MailTemplate();
		
		model.addAttribute("mailTemplateCodeList", mailTemplate.getTemplateCodes());
		model.addAttribute("mailConfig", mailConfig);
		mailConfig.setTemplateId(templateId);
		
		model.addAttribute("mailChangeCodeList", mailConfigService.getMailChangeCodes(templateId));
		model.addAttribute("title", mailTemplate.getTemplateCodeTitle(templateId));
		return ViewUtils.getView("/mail-config/form");
	}
	
	@PostMapping("create/{templateId}")
	public String createAction(MailConfig mailConfig, Model model) {
		
		mailConfigService.insertMailConfig(mailConfig);
		
		return ViewUtils.redirect("/opmanager/mail-config/edit/" + mailConfig.getTemplateId(), MessageUtils.getMessage("M00288")); // 등록 되었습니다. 
	}
	
	/**
	 * 관리자 이메일 설정 등록폼
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("edit/{templateId}")
	public String edit(@PathVariable("templateId") String templateId, Model model) {
		
		MailTemplate mailTemplate = new MailTemplate();
		
		model.addAttribute("mailTemplateCodeList", mailTemplate.getTemplateCodes());
		MailConfig mailConfig = mailConfigService.getMailConfigByTemplateId(templateId);
		if (mailConfig == null) {
			return ViewUtils.redirect("/opmanager/mail-config/create/" + templateId);
		}
		
		model.addAttribute("mailChangeCodeList", mailConfigService.getMailChangeCodes(templateId));
		model.addAttribute("title", mailTemplate.getTemplateCodeTitle(templateId));
		model.addAttribute("mailConfig", mailConfig);
		return ViewUtils.getView("/mail-config/form");
		
		
	}
	
	@PostMapping("edit/{templateId}")
	public String editAction(Model model, MailConfig mailConfig) {
		
		mailConfigService.updateMailConfigById(mailConfig);
		
		return ViewUtils.redirect("/opmanager/mail-config/edit/" + mailConfig.getTemplateId(), MessageUtils.getMessage("M00289")); // 수정 되었습니다.
	}
	
	@PostMapping(value="/delete/{mailConfigId}")
	public String deleteAction(Model model, MailConfig mailConfig) {
		
		mailConfigService.deleteMailConfigById(mailConfig);
		
		return ViewUtils.redirect("/opmanager/mail-config/list", MessageUtils.getMessage("M00205")); // 삭제 되었습니다.
		
	}
	
	@GetMapping("/change-code/{templateId}")
	@RequestProperty(layout="base")
	public String changeCode(Model model, @PathVariable("templateId") String templateId,
			MailConfig mailConfig) {
		
		MailTemplate mailTemplate = new MailTemplate();
		
		model.addAttribute("title", mailTemplate.getTemplateCodeTitle(templateId));
		model.addAttribute("codeList", mailConfigService.getMailChangeCodes(templateId));
		return ViewUtils.getView("/mail-config/change-code");
	}
}
