package saleson.shop.popup;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import saleson.shop.popup.domain.Popup;

import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;

@Controller
@RequestMapping("/popup/**")
@RequestProperty(layout="base", title="팝업페이지")
public class PopupController {
	@Autowired PopupService popupService;

	
	@GetMapping("/index/{popupId}")
	public String index (@PathVariable int popupId, ModelMap modelMap, HttpServletRequest request) {
		Popup popup = popupService.getPopup(popupId);
		modelMap.addAttribute("popup", popup);
		modelMap.addAttribute("popupId", popupId);

		return ViewUtils.getView("/main/popup");	
	}	
	
}
