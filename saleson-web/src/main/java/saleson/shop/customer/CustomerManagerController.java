package saleson.shop.customer;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.RedirectAttributeUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import saleson.shop.customer.domain.Customer;
import saleson.shop.customer.support.CustomerExcelView;
import saleson.shop.customer.support.CustomerExcelView2;
import saleson.shop.customer.support.CustomerParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/opmanager/customer")
@RequestProperty(title="customer", layout="default")
public class CustomerManagerController {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerManagerController.class);
	
	@Autowired
	CustomerService customerSerivce;
	
	@GetMapping("download-excel")
	public ModelAndView downloadExcel() {
		ModelAndView mav = new ModelAndView(new CustomerExcelView());
		
		HttpSession session = RequestContextUtils.getSession();
		CustomerParam customerParam = null;

		if (session.getAttribute("reCustomerParam") == null) {
			customerParam = (CustomerParam) session.getAttribute("reCustomerParam");
		}

		if (customerParam != null) {
			customerParam.setPagination(null);
		}
	
		mav.addObject("customerList", customerSerivce.getCustomerList(customerParam));	
		return mav;
	}
	
	@GetMapping("download-excel2")
	public ModelAndView downloadExcel2() {
		ModelAndView mav = new ModelAndView(new CustomerExcelView2());
		
		HttpSession session = RequestContextUtils.getSession();
		CustomerParam customerParam = null;

		if (session.getAttribute("reCustomerParam") == null) {
			customerParam = (CustomerParam) session.getAttribute("reCustomerParam");
		}
		
		if (customerParam != null) {
			customerParam.setPagination(null);
		}
	
		mav.addObject("customerList", customerSerivce.getCustomerList(customerParam));	
		return mav;
	}
	
	/**
	 * 거래처 정보 Excel업로드 팝업
	 * @param session
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("/upload-excel")
	public String uploadExcel(Model model) {  
        
		if (RedirectAttributeUtils.hasRedirectAttributes()) {
			model.addAttribute("result", RedirectAttributeUtils.get("result"));
		}
		
        return ViewUtils.view();  
	}
	
	/**
	 * 거래처 정보 Excel업로드 처리
	 * @param multipartFiles
	 * @param session
	 * @return
	 */
	@PostMapping("/upload-excel")
	public String uploadExcelProcess(@RequestParam(value="file", required=false) MultipartFile multipartFile, 
			HttpSession session) {  
        
		String result = customerSerivce.insertExcelData(multipartFile);
		RedirectAttributeUtils.addAttribute("result", result);
		
        return ViewUtils.redirect("/opmanager/customer/upload-excel");
	}
	
	
	/**
	 * 2015.1.22 거래처 등록폼
	 * @param customer
	 * @param model
	 * @return
	 */
	@GetMapping("/create")
	public String customerInsert(@ModelAttribute("customer") Customer customer, Model model) {
		
		if (RedirectAttributeUtils.hasRedirectAttributes()) {
			customer = (Customer) RedirectAttributeUtils.get("customer");
		}
		
		model.addAttribute("customer", customer);
		model.addAttribute("buttonName", "등록");
		return ViewUtils.view();
	}
	
	/**
	 * 2015.1.22 
	 * @param customer 
	 * @return
	 */
	
	@PostMapping("/create")
	public String customerInsertAction(Customer customer, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			RedirectAttributeUtils.addAttribute("customer", customer);
			return ViewUtils.redirect("/opmanager/customer/create", "필수 정보를 정확히 입력해 주세요");
		}
		
		customerSerivce.insertCustomer(customer);
		return ViewUtils.redirect("/opmanager/customer/create");
	}
	
	/**
	 * 코드 중복 채크
	 * @param customerCode
	 * @param requestContext
	 * @return
	 */
	@PostMapping(value="/codeCheck/{customerCode}")
	public JsonView codeCheckAction(@PathVariable("customerCode") String customerCode, 
			RequestContext requestContext) {
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		Customer customer = customerSerivce.getCustomerById(customerCode);
		if(customer == null) {
			return JsonViewUtils.success();
		}
		
		return JsonViewUtils.failure("중복된 코드가 있습니다.");
	}
	
	/**
	 * 2015.1.23 목록
	 * @param customerParam
	 * @param model
	 * @return
	 */
	@GetMapping(value="/list")
	public String customerList(CustomerParam customerParam, Model model) {
		int customerCount = customerSerivce.getCustomerCountByParam(customerParam);
		Pagination pagination = Pagination.getInstance(customerCount);
		customerParam.setPagination(pagination);
		
		if (StringUtils.isEmpty(customerParam.getSearchEndDateTime())) {
			customerParam.setSearchEndDateTime("23");
		}
		
		List<Customer> customerlist = customerSerivce.getCustomerList(customerParam);
		
		HttpSession session = RequestContextUtils.getSession();
		session.setAttribute("reCustomerParam", customerParam);
		
		model.addAttribute("customerParam",customerParam);
		model.addAttribute("customerlist", customerlist);
		model.addAttribute("pagination", pagination);
		model.addAttribute("customerCount", customerCount);
		return ViewUtils.view();
	}
	
	/**
	 * 2015.1.23 수정폼
	 * @param customerCode
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{customerCode}")
	public String customerUpdate(@PathVariable("customerCode") String customerCode, Model model) {
		Customer customer = customerSerivce.getCustomerById(customerCode);
		model.addAttribute("customer", customer);
		model.addAttribute("buttonName", "수정");
		return ViewUtils.getManagerView("/customer/form");
	}
	
	/**
	 * 2015.1.23 수정
	 * @param customerCode
	 * @return
	 */
	@PostMapping("/edit/{customerCode}")
	public String customerUpdateAction(@PathVariable("customerCode") String customerCode, Customer customer) {
		customerSerivce.updateCustomer(customer);
		return ViewUtils.redirect("/opmanager/customer/list");
	}
	
}
