package saleson.common.controller;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import saleson.common.utils.ShopUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
@RequestProperty(layout="default")
public class ErrorController {
	private static final Logger log = LoggerFactory.getLogger(ErrorController.class);
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@GetMapping("/404")
	public String handle(HttpServletRequest request, RequestContext requestContext
			, Model model) {

		log.debug("Orign : {}", requestContext.getRequestUri());
	

		
        model.addAttribute("status", request.getAttribute("javax.servlet.error.status_code"));
        model.addAttribute("reason", request.getAttribute("javax.servlet.error.message"));

        // 모바일 디바이스 체크
		if (ShopUtils.isMobile(request)) {
			RequestContextUtils.setTemplate("mobile");
		}
        return ViewUtils.getView("/error/404");
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@GetMapping("/500")
	public String handle500(HttpServletRequest request, RequestContext requestContext,
			Model model) {
		log.debug("Orign : {}", requestContext.getRequestUri());
		
        model.addAttribute("status", request.getAttribute("javax.servlet.error.status_code"));
        model.addAttribute("reason", request.getAttribute("javax.servlet.error.message"));

        // 모바일 디바이스 체크
 		if (ShopUtils.isMobile(request)) {
 			RequestContextUtils.setTemplate("mobile");
 		}
        return ViewUtils.getView("/error/500");
	}
}
