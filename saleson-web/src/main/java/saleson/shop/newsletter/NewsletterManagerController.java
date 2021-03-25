package saleson.shop.newsletter;

import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/opmanager/newsletter")
@RequestProperty(title="메일링", layout="default")
public class NewsletterManagerController {
	
	@GetMapping("send/list")
	public String sendList(Model model) {
		
		return ViewUtils.view();
	}
	
	
	@GetMapping("send/create")
	public String create(Model model) {
		
		return ViewUtils.view();
	}
	
	
	@GetMapping("template/list")
	public String templateList(Model model) {
		
		return ViewUtils.view();
	}
	
	
	@GetMapping("template/create")
	public String createTemplate(Model model) {
		
		return ViewUtils.view();
	}
	
	
	
	
}
