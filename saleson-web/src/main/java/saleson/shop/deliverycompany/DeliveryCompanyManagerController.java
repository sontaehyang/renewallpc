package saleson.shop.deliverycompany;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
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
import org.springframework.web.bind.annotation.*;
import saleson.shop.config.ConfigManagerController;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.deliverycompany.support.DeliveryCompanyParam;

import java.util.List;


@Controller
@RequestMapping("/opmanager/delivery-company")
@RequestProperty(title="환경설정", layout="default")
public class DeliveryCompanyManagerController {
	private static final Logger log = LoggerFactory.getLogger(ConfigManagerController.class);
	
	@Autowired
	DeliveryCompanyService deliveryCompanyService;
	
	/**
	 * 배송업체 리스트 조회
	 * @param model
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/list")
	public String deliveryCompanyList(Model model, RequestContext requestContext, DeliveryCompanyParam deliveryCompanyParam) {
		
		int deliveryCompanyCount = deliveryCompanyService.getDeliveryCompanyCount(deliveryCompanyParam);
		
		Pagination	pagination = Pagination.getInstance(deliveryCompanyCount);
		
		deliveryCompanyParam.setPagination(pagination);
		
		List<DeliveryCompany> deliveryCompanyList = deliveryCompanyService.getDeliveryCompanyList(deliveryCompanyParam);
		
		model.addAttribute("deliveryCompanyList", deliveryCompanyList);
		model.addAttribute("deliveryCompanyCount", deliveryCompanyCount);
		model.addAttribute("pagination", pagination);
		
		return ViewUtils.view();
	}
	
	/**
	 * 배송업체 수정
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{deliveryCompanyId}")
	public String deliveryCompanyUpdate(@PathVariable("deliveryCompanyId") int deliveryCompanyId, RequestContext requestContext, Model model) {
		
		DeliveryCompany deliveryCompany = deliveryCompanyService.getDeliveryCompanyById(deliveryCompanyId);
		
		model.addAttribute("deliveryCompany", deliveryCompany);
		model.addAttribute("kind", "수정");
		
		return ViewUtils.getManagerView("/delivery-company/form");
	}
	
	/**
	 * 배송업체 수정 처리
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@PostMapping("/edit/{deliveryCompanyId}")
	public String deliveryCompanyUpdateAction(@PathVariable("deliveryCompanyId") int deliveryCompanyId, DeliveryCompany deliveryCompany) {

		deliveryCompany.setDeliveryCompanyId(deliveryCompanyId);
		
		deliveryCompanyService.updateDeliveryCompanyById(deliveryCompany);
		
		return ViewUtils.redirect("/opmanager/delivery-company/list", MessageUtils.getMessage("M00289"));// 수정되었습니다. 
	}
	
	/**
	 * 배송업체 등록
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@GetMapping("/create")
	public String deliveryCompanyInsert(DeliveryCompany deliveryCompany, Model model) {

		model.addAttribute("deliveryCompany", deliveryCompany);
		model.addAttribute("kind", "등록");
		
		return ViewUtils.view();
	}
	
	/**
	 * 배송업체 등록 처리
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@PostMapping("/create")
	public String deliveryCompanyInsertAction(DeliveryCompany deliveryCompany) {

		deliveryCompanyService.insertDeliveryCompany(deliveryCompany);
		
		return ViewUtils.redirect("/opmanager/delivery-company/list", MessageUtils.getMessage("M00288"));// 등록되었습니다.
	}
	
	
	/**
	 * 배송업체 선택삭제
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping("/delete")
	public JsonView deleteCompanyListData(RequestContext requestContext, ListParam listParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		deliveryCompanyService.deleteDeliveryCompanyById(listParam);
		return JsonViewUtils.success();  
	}
}
