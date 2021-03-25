package saleson.shop.zipcode;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.zipcode.domain.Zipcode;

@Controller
@RequestMapping("/zipcode/**")
@RequestProperty(title="우편번호", layout="blank")
public class ZipcodeController {
	private static final Logger log = LoggerFactory.getLogger(ZipcodeController.class);
	
	@Autowired
	ZipcodeService zipcodeService;
	
	@PostMapping("find-address")
	public JsonView findAddress(RequestContext requestContext, Model model,Zipcode zipcode){
		
		zipcode = zipcodeService.getZipcode(zipcode);
		model.addAttribute("zipcode", zipcode);
		
		return JsonViewUtils.success();
	}

}
