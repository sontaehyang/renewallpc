package saleson.shop.accountnumber;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.accountnumber.domain.AccountNumber;
import saleson.shop.accountnumber.support.AccountNumberParam;

import java.util.List;

@Controller
@RequestMapping("/opmanager/account-number/**")
@RequestProperty(title="회원가입 설정", layout="default")
public class AccountNumberManagerController {
	private static final Logger log = LoggerFactory.getLogger(AccountNumberManagerController.class);

	@Autowired
	private AccountNumberService accountNumberService;
	
	@GetMapping("list")
	public String accountNumberList(RequestContext requestContext, Model model, AccountNumberParam accountNumberParam){
		
		Pagination pagination = Pagination.getInstance(accountNumberService.getAccountNumberCount(accountNumberParam));
		
		accountNumberParam.setPagination(pagination);
		
		log.debug("pagination {}", pagination);
		
		List<AccountNumber> accountNumbers = accountNumberService.getAccountNumberList(accountNumberParam);
		
		model.addAttribute("list", accountNumbers);
		model.addAttribute("pagination", pagination);
		return ViewUtils.view();
	}
	
	@GetMapping("create")
	public String accountNumberCreate(RequestContext requestContext, AccountNumber accountNumber, Model model){
		
		model.addAttribute("accountNumber", accountNumber);
		return ViewUtils.view();
	}
	
	@PostMapping("create")
	public String accountNumberCreateAction(RequestContext requestContext, AccountNumber accountNumber){
		
		accountNumberService.insertAccountNumber(accountNumber);
		
		return ViewUtils.redirect("/opmanager/account-number/list",MessageUtils.getMessage("M00288"));
	}
	
	@GetMapping("edit/{accountNumberId}")
	public String accountNumberEdit(RequestContext requestContext, Model model, AccountNumberParam accountNumberParam){
		
		AccountNumber accountNumber = accountNumberService.getAccountNumber(accountNumberParam);
		
		model.addAttribute("accountNumber", accountNumber);
		return ViewUtils.view();
	}
	
	@PostMapping("edit/{accountNumberId}")
	public String accountNumberEditAction(RequestContext requestContext, AccountNumber accountNumber){
		
		accountNumberService.updateAccountNumber(accountNumber);
		return ViewUtils.redirect("/opmanager/account-number/list",MessageUtils.getMessage("M00289"));
	}
	
	@GetMapping("delete/{accountNumberId}")
	public String accountNumberDelete(RequestContext requestContext, AccountNumberParam accountNumberParam){
		
		accountNumberService.deleteAccountNumber(accountNumberParam);
		
		return ViewUtils.redirect("/opmanager/account-number/list",MessageUtils.getMessage("M00205"));
	}
	
	/**
	 * 관리자 선택 삭제
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping("delete/list")
	public JsonView managerDeleteList(RequestContext requestContext, ListParam listParam)
	{
		try {
			accountNumberService.deleteAccountNumberListParam(listParam);
			return JsonViewUtils.success();  
		} catch (Exception e) {
			return JsonViewUtils.failure(e.getMessage());  
		}
	}
}
