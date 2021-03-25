package saleson.shop.smsconfig;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.smsconfig.domain.SmsConfig;
import saleson.shop.smsconfig.support.SmsConfigParam;
import saleson.shop.smsconfig.support.SmsTemplate;

@Controller
@RequestMapping("/opmanager/sms-config")
@RequestProperty(title="SMS설정 관리", layout="default")
public class SmsConfigManagerController {

	@Autowired
	private SmsConfigService smsConfigService;
	
	@GetMapping("list")
	public String list(@ModelAttribute("smsConfigParam") SmsConfigParam smsConfigParam) {
		
		SmsTemplate smsTemplate = new SmsTemplate();
		String templateId = smsTemplate.getFirstTemplateCodeKey();
		
		SmsConfig smsConfig = smsConfigService.getSmsConfigByTemplateId(templateId);
		String redirectUrl = "/opmanager/sms-config/create/" + templateId;
		
		if (smsConfig != null) {
			redirectUrl = "/opmanager/sms-config/edit/" + templateId;
		}
		
		return ViewUtils.redirect(redirectUrl);
	}
	
	/**
	 * 관리자 이메일 설정 등록폼
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/create/{templateId}")
	public String create(@PathVariable("templateId") String templateId, Model model, SmsConfig smsConfig) {
		
		SmsTemplate smsTemplate = new SmsTemplate();
		
		model.addAttribute("smsTemplateCodeList", smsTemplate.getTemplateCodes());
		model.addAttribute("smsConfig", smsConfig);
		
		smsConfig.setTemplateId(templateId);
		model.addAttribute("smsChangeCodeList", smsConfigService.getSmsChangeCodes(templateId));
		model.addAttribute("title", smsTemplate.getTemplateCodeTitle(templateId));
		return ViewUtils.getView("/sms-config/form");
	}
	
	@PostMapping("/create/{templateId}")
	public String createAction(SmsConfig smsConfig, Model model) {
		
		smsConfigService.insertSmsConfig(smsConfig);
		
		return ViewUtils.redirect("/opmanager/sms-config/edit/" + smsConfig.getTemplateId(), MessageUtils.getMessage("M00288")); // 등록 되었습니다. 
	}
	
	/**
	 * 관리자 이메일 설정 등록폼
	 * @param templateId
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{templateId}")
	public String edit(@PathVariable("templateId") String templateId, Model model) {
		
		SmsTemplate smsTemplate = new SmsTemplate();
		
		model.addAttribute("smsTemplateCodeList", smsTemplate.getTemplateCodes());
		SmsConfig smsConfig = smsConfigService.getSmsConfigByTemplateId(templateId);
		
		if (smsConfig == null) {
			return ViewUtils.redirect("/opmanager/sms-config/create/" + templateId);
		}
		
		model.addAttribute("smsChangeCodeList", smsConfigService.getSmsChangeCodes(templateId));
		model.addAttribute("title", smsTemplate.getTemplateCodeTitle(templateId));
		model.addAttribute("smsConfig", smsConfig);
		return ViewUtils.getView("/sms-config/form");
		
	}
	
	@PostMapping("/edit/{templateId}")
	public String editAction(Model model, SmsConfig smsConfig) {
		
		smsConfigService.updateSmsConfig(smsConfig);
		
		return ViewUtils.redirect("/opmanager/sms-config/edit/" + smsConfig.getTemplateId(), MessageUtils.getMessage("M00289")); // 수정 되었습니다.
	}
	
	@GetMapping("/change-code/{templateId}")
	@RequestProperty(layout="base")
	public String changeCode(Model model, @PathVariable("templateId") String templateId,
			SmsConfig smcConfig) {
		
		SmsTemplate smsTemplate = new SmsTemplate();
		
		model.addAttribute("title", smsTemplate.getTemplateCodeTitle(templateId));
		model.addAttribute("codeList", smsConfigService.getSmsChangeCodes(templateId));
		return ViewUtils.getView("/sms-config/change-code");
	}
}
