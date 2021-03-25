package saleson.shop.businesscode;

import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/opmanager/business-code/**")
@RequestProperty(title="코드 설정", layout="default")
public class BusinessCodeManagerContoller {
	private static final Logger log = LoggerFactory.getLogger(BusinessCodeManagerContoller.class);

}
