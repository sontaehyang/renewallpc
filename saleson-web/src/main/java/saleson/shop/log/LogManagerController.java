package saleson.shop.log;

import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.log.support.LoginLogParam;

@Controller
@RequestMapping("/opmanager/log")
@RequestProperty(title="로그관리", layout="default")
public class LogManagerController {
	
	@Autowired
	private LoginLogService loginLogService;
	
	@GetMapping("/login-log")
	@RequestProperty(title="로그인 로그 관리")
	public String loginLog(Model model, LoginLogParam loginLogParam) {
		
		Pagination pagination = Pagination.getInstance(loginLogService.getLoginLogListCountByParam(loginLogParam));
		loginLogParam.setPagination(pagination);
		
		model.addAttribute("list", loginLogService.getLoginLogListByParam(loginLogParam));
		model.addAttribute("loginLogParam", loginLogParam);
		model.addAttribute("pagination", pagination);
		
		return ViewUtils.getView("/log/login-log-list");
	}
	
}
