package saleson.shop.faq;

import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/m/faq")
@RequestProperty(template="mobile", layout="default")
public class FaqMobileController extends FaqController {


}
