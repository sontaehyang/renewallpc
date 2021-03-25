package saleson.shop.mall;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.mall.domain.MallOrder;
import saleson.shop.mall.domain.MallOrderCancel;
import saleson.shop.mall.domain.MallOrderExchange;
import saleson.shop.mall.domain.MallOrderReturn;
import saleson.shop.mall.support.MallConfigParam;
import saleson.shop.mall.support.MallOrderParam;

@Controller
@RequestMapping("/opmanager/mall")
@RequestProperty(layout="default", template="opmanager")
public class MallManagerController {
	private static final Logger log = LoggerFactory.getLogger(MallManagerController.class);

	
	@Autowired
	private MallService mallService;
	

	/**
	 * 주문서 수집 화면
	 * @param model
	 * @param mallConfigParam
	 * @return
	 */
	@GetMapping("collect")
	public String collect(Model model, @ModelAttribute MallConfigParam mallConfigParam) {
		model.addAttribute("list", mallService.getMallConfigListByParam(mallConfigParam));
		return "view:/mall/collect";
	}
	
	/**
	 * 주문서 수집 처리
	 * @param model
	 * @param mallConfigParam
	 * @return
	 */
	@PostMapping("collect/action")
	public String collectAction(Model model, @ModelAttribute MallConfigParam mallConfigParam) {

		mallService.orderCollectTx(mallConfigParam);
		return "redirect:/opmanager/mall/collect";
	}
	
	/**
	 * 마켓 주문취소(판매 불가 처리)
	 * @param mallType
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("{pageType}/cancel/{mallType}")
	public String cancel(@PathVariable("mallType") String mallType,
			@PathVariable("pageType") String pageType, Model model, 
			@RequestParam("mallOrderId") int mallOrderId) {
		
		if (!("new-order".equals(pageType) || "shipping-ready".equals(pageType))) {
			throw new PageNotFoundException();
		}
		
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setMallOrderId(mallOrderId);
		
		MallOrder mallOrder = mallService.getMallOrderDetailByParam(mallOrderParam);
		if (mallOrder == null) {
			throw new PageNotFoundException();
		}
		
		MallOrderCancel cancelApply = new MallOrderCancel();
		cancelApply.setMallOrder(mallOrder);
		cancelApply.setMallOrderId(mallOrder.getMallOrderId());
		
		model.addAttribute("cancelApply", cancelApply);
		return "view:/mall/" + mallType + "/cancel";
	}
	
	/**
	 * 마켓 주문취소(판매 불가 처리) 처리
	 * @param mallType
	 * @return
	 */
	@ResponseBody
	@PostMapping("{pageType}/cancel/{mallType}")
	public String cancelAction(@PathVariable("mallType") String mallType,
			@PathVariable("pageType") String pageType, 
			@RequestParam("mallOrderId") int mallOrderId, MallOrderCancel cancelApply) {
		
		if (!("new-order".equals(pageType) || "shipping-ready".equals(pageType))) {
			throw new PageNotFoundException();
		}
		
		StringBuffer sb = new StringBuffer();
		
		mallService.cancelAction(mallType, cancelApply);
		sb.append("<script type=\"text/javascript\">");
		sb.append("opener.location.reload();");
		sb.append("self.close();");
		sb.append("</script>");
		
		return sb.toString();
	}
	
	/**
	 * 신규주문 목록
	 * @param model
	 * @param mallOrderParam
	 * @return
	 */
	@GetMapping("new-order")
	public String newOrder(Model model, @ModelAttribute MallOrderParam mallOrderParam) {
		
		model.addAttribute("list", mallService.getNewOrderListByParam(mallOrderParam));
		model.addAttribute("pagination", mallOrderParam.getPagination());
		model.addAttribute("totalCount", mallOrderParam.getPagination().getTotalItems());
		
		return "view:/mall/list/new-order";
	}
	
	@RequestProperty(layout="base")
	@GetMapping("new-order/detail/{mallOrderId}")
	public String newOrderDetail(@PathVariable("mallOrderId") int mallOrderId,
			Model model, MallOrderParam mallOrderParam) {
		
		mallOrderParam.setMallOrderId(mallOrderId);
		MallOrder mallOrder = mallService.getNewOrderDetailByParam(mallOrderParam);
		
		if (mallOrder == null) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("order", mallOrder);
		return "view:/mall/detail/new-order";
	}
	/**
	 * 신규주문 리스트 상태 변경
	 * @param requestContext
	 * @param mallOrderParam
	 * @param mode
	 * @return
	 */
	@PostMapping("new-order/listUpdate/{mode}")
	public JsonView newOrderListUpdate(RequestContext requestContext, MallOrderParam mallOrderParam,
			@PathVariable("mode") String mode) {
	
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			
			// 재고 실시간 반영때문에..
			mallService.newOrderListUpdateTx(mode, mallOrderParam);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.failure(e.getMessage());
		}

	}
	
	/**
	 * 배송 준비중 목록
	 * @param model
	 * @param mallOrderParam
	 * @return
	 */
	@GetMapping("shipping-ready")
	public String shippingReady(Model model, @ModelAttribute MallOrderParam mallOrderParam) {
		
		model.addAttribute("list", mallService.getShippingReadyListByParam(mallOrderParam));
		model.addAttribute("pagination", mallOrderParam.getPagination());
		model.addAttribute("totalCount", mallOrderParam.getPagination().getTotalItems());
		
		return "view:/mall/list/shipping-ready";
	}
	
	
	/**
	 * 배송준비중 리스트 업데이트
	 * @param requestContext
	 * @param mallOrderParam
	 * @param mode
	 * @return
	 */
	@PostMapping("shipping-ready/listUpdate/{mode}")
	public JsonView shippingReadyListUpdate(RequestContext requestContext, MallOrderParam mallOrderParam,
			@PathVariable("mode") String mode) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			mallService.shippingReadyListUpdate(mode, mallOrderParam);
			return JsonViewUtils.success();
		} catch (Exception e) {
			return JsonViewUtils.failure(e.getMessage());
		}

	}
	
	/**
	 * 배송 준비중 목록
	 * @param model
	 * @param mallOrderParam
	 * @return
	 */
	@GetMapping("shipping")
	public String shipping(Model model, @ModelAttribute MallOrderParam mallOrderParam) {
		
		model.addAttribute("list", mallService.getShippingListByParam(mallOrderParam));
		model.addAttribute("pagination", mallOrderParam.getPagination());
		model.addAttribute("totalCount", mallOrderParam.getPagination().getTotalItems());
		
		return "view:/mall/list/shipping";
	}
	
	/**
	 * 반품 신청 목록
	 * @param model
	 * @param mallOrderParam
	 * @return
	 */
	@GetMapping("return-request")
	public String returnRequest(Model model, @ModelAttribute MallOrderParam mallOrderParam) {
		
		model.addAttribute("list", mallService.getReturnRequestListByParam(mallOrderParam));
		model.addAttribute("pagination", mallOrderParam.getPagination());
		model.addAttribute("totalCount", mallOrderParam.getPagination().getTotalItems());
		
		return "view:/mall/list/return-request";
	}
	
	/**
	 * 반품 완료 목록
	 * @param model
	 * @param mallOrderParam
	 * @return
	 */
	@GetMapping("return-finish")
	public String returnFinish(Model model, @ModelAttribute MallOrderParam mallOrderParam) {
		
		model.addAttribute("list", mallService.getReturnFinishListByParam(mallOrderParam));
		model.addAttribute("pagination", mallOrderParam.getPagination());
		model.addAttribute("totalCount", mallOrderParam.getPagination().getTotalItems());
		
		return "view:/mall/list/return-finish";
	}
	
	/**
	 * 마켓 반품 보류
	 * @param mallType
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("return-request/return-hold/{mallType}")
	public String returnHold(@PathVariable("mallType") String mallType,
			Model model, @RequestParam("id") int mallOrderId,
			@RequestParam("code") String claimCode) {
		
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setClaimCode(claimCode); 
		mallOrderParam.setMallOrderId(mallOrderId);
		
		MallOrderReturn returnApply = mallService.getReturnRequestDetailByParam(mallOrderParam);
		if (returnApply == null) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("returnApply", returnApply);
		return "view:/mall/" + mallType + "/return-hold";
	}
	
	/**
	 * 마켓 반품 보류 처리
	 * @param mallType
	 * @return
	 */
	@ResponseBody
	@PostMapping("return-request/return-hold/{mallType}")
	public String returnHoldAction(@PathVariable("mallType") String mallType,
			MallOrderReturn returnApply) {
		
		StringBuffer sb = new StringBuffer();
		
		mallService.returnHoldAction(mallType, returnApply);
		
		sb.append("<script type=\"text/javascript\">");
		sb.append("opener.location.reload();");
		sb.append("self.close();");
		sb.append("</script>");
		return sb.toString();
	}
	
	/**
	 * 마켓 반품 거부
	 * @param mallType
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("return-request/return-refusal/{mallType}")
	public String returnRefusal(@PathVariable("mallType") String mallType,
			Model model, @RequestParam("id") int mallOrderId,
			@RequestParam("code") String claimCode) {
		
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setClaimCode(claimCode); 
		mallOrderParam.setMallOrderId(mallOrderId);
		
		MallOrderReturn returnApply = mallService.getReturnRequestDetailByParam(mallOrderParam);
		if (returnApply == null) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("returnApply", returnApply);
		return "view:/mall/" + mallType + "/return-refusal";
	}
	
	/**
	 * 마켓 교환 거부 처리
	 * @param mallType
	 * @return
	 */
	@ResponseBody
	@PostMapping("return-request/return-refusal/{mallType}")
	public String returnRefusalAction(@PathVariable("mallType") String mallType,
			MallOrderReturn returnApply) {
		
		StringBuffer sb = new StringBuffer();
		
		mallService.returnRefusalAction(mallType, returnApply);
		
		sb.append("<script type=\"text/javascript\">");
		sb.append("opener.location.reload();");
		sb.append("self.close();");
		sb.append("</script>");
		return sb.toString();
	}
	
	/**
	 * 반품 요청 리스트 업데이트
	 * @param requestContext
	 * @param mallOrderParam
	 * @param mode
	 * @return
	 */
	@PostMapping("return-request/listUpdate/{mode}")
	public JsonView returnRequestListUpdate(RequestContext requestContext, MallOrderParam mallOrderParam,
			@PathVariable("mode") String mode) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			mallService.returnRequestListUpdate(mode, mallOrderParam);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("Error", e);
			return JsonViewUtils.failure(e.getMessage());
		}

	}
	
	/**
	 * 교환 요청 목록
	 * @param model
	 * @param mallOrderParam
	 * @return
	 */
	@GetMapping("exchange-request")
	public String exchangeRequest(Model model, @ModelAttribute MallOrderParam mallOrderParam) {
		
		model.addAttribute("list", mallService.getExchangeRequestListByParam(mallOrderParam));
		model.addAttribute("pagination", mallOrderParam.getPagination());
		model.addAttribute("totalCount", mallOrderParam.getPagination().getTotalItems());
		
		return "view:/mall/list/exchange-request";
	}
	
	/**
	 * 반품 요청 리스트 업데이트
	 * @param requestContext
	 * @param mallOrderParam
	 * @param mode
	 * @return
	 */
	@PostMapping("exchange-request/listUpdate/{mode}")
	public JsonView exchangeRequestListUpdate(RequestContext requestContext, MallOrderParam mallOrderParam,
			@PathVariable("mode") String mode) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			mallService.exchangeRequestListUpdate(mode, mallOrderParam);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("Error", e);
			return JsonViewUtils.failure(e.getMessage());
		}

	}
	
	/**
	 * 마켓 교환 거부
	 * @param mallType
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("exchange-request/exchange-refusal/{mallType}")
	public String exchangeHold(@PathVariable("mallType") String mallType,
			Model model, @RequestParam("id") int mallOrderId,
			@RequestParam("code") String claimCode) {
		
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setClaimCode(claimCode); 
		mallOrderParam.setMallOrderId(mallOrderId);
		
		MallOrderExchange exchangeApply = mallService.getExchangeRequestDetailByParam(mallOrderParam);
		if (exchangeApply == null) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("exchangeApply", exchangeApply);
		return "view:/mall/" + mallType + "/exchange-refusal";
	}
	
	/**
	 * 마켓 교환 거부 처리
	 * @param mallType
	 * @return
	 */
	@ResponseBody
	@PostMapping("exchange-request/exchange-refusal/{mallType}")
	public String exchangeHoldAction(@PathVariable("mallType") String mallType,
			MallOrderExchange exchangeApply) {
		
		StringBuffer sb = new StringBuffer();
		
		mallService.exchangeRefusalAction(mallType, exchangeApply);
		
		sb.append("<script type=\"text/javascript\">");
		sb.append("opener.location.reload();");
		sb.append("self.close();");
		sb.append("</script>");
		return sb.toString();
	}
	
	/**
	 * 교환 완료 목록
	 * @param model
	 * @param mallOrderParam
	 * @return
	 */
	@GetMapping("exchange-finish")
	public String exchangeFinish(Model model, @ModelAttribute MallOrderParam mallOrderParam) {
		
		model.addAttribute("list", mallService.getExchangeFinishListByParam(mallOrderParam));
		model.addAttribute("pagination", mallOrderParam.getPagination());
		model.addAttribute("totalCount", mallOrderParam.getPagination().getTotalItems());
		
		return "view:/mall/list/exchange-finish";
	}
	
	/**
	 * 주문취소 요청 목록
	 * @param model
	 * @param mallOrderParam
	 * @return
	 */
	@GetMapping("cancel-request")
	public String cancelRequest(Model model, @ModelAttribute MallOrderParam mallOrderParam) {
		
		model.addAttribute("list", mallService.getCancelRequestListByParam(mallOrderParam));
		model.addAttribute("pagination", mallOrderParam.getPagination());
		model.addAttribute("totalCount", mallOrderParam.getPagination().getTotalItems());
		
		return "view:/mall/list/cancel-request";
	}
	
	/**
	 * 마켓 주문취소 거부
	 * @param mallType
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("cancel-request/cancel-refusal/{mallType}")
	public String cancelRefusal(@PathVariable("mallType") String mallType,
			Model model, @RequestParam("id") int mallOrderId,
			@RequestParam("code") String claimCode) {
		
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setClaimCode(claimCode); 
		mallOrderParam.setMallOrderId(mallOrderId);
		
		MallOrderCancel cancelApply = mallService.getCancelRequestDetailByParam(mallOrderParam);
		if (cancelApply == null) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("cancelApply", cancelApply);
		return "view:/mall/" + mallType + "/cancel-refusal";
	}
	
	@ResponseBody
	@PostMapping("cancel-request/cancel-refusal/{mallType}")
	public String cancelRefusalAction(@PathVariable("mallType") String mallType,
		@RequestParam("mallOrderId") int mallOrderId, MallOrderCancel cancelApply) {
	
		StringBuffer sb = new StringBuffer();
		
		mallService.cancelRefusalAction(mallType, cancelApply);
		
		sb.append("<script type=\"text/javascript\">");
		sb.append("opener.location.reload();");
		sb.append("self.close();");
		sb.append("</script>");
		return sb.toString();
		
	}
	
	/**
	 * 주문취소 요청 리스트 업데이트
	 * @param requestContext
	 * @param mallOrderParam
	 * @param mode
	 * @return
	 */
	@PostMapping("cancel-request/listUpdate/{mode}")
	public JsonView cancelRequestListUpdate(RequestContext requestContext, MallOrderParam mallOrderParam,
			@PathVariable("mode") String mode) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			mallService.cancelRequestListUpdate(mode, mallOrderParam);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("Error", e);
			return JsonViewUtils.failure(e.getMessage());
		}

	}
	
	/**
	 * 주문취소 요청 목록
	 * @param model
	 * @param mallOrderParam
	 * @return
	 */
	@GetMapping("cancel-finish")
	public String cancelFinish(Model model, @ModelAttribute MallOrderParam mallOrderParam) {
		
		model.addAttribute("list", mallService.getCancelFinishListByParam(mallOrderParam));
		model.addAttribute("pagination", mallOrderParam.getPagination());
		model.addAttribute("totalCount", mallOrderParam.getPagination().getTotalItems());
		
		return "view:/mall/list/cancel-finish";
	}
}
