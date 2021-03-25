package saleson.shop.help;

import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help")
@RequestProperty(layout="default")
public class HelpController {
	
		
	/**
	 * 이용안내
	 * @return
	 */
	@GetMapping("guide")
	@RequestProperty(title="이용안내")
	public String index() {
		
		return ViewUtils.view();
	}
	
}
