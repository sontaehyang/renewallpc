package saleson.shop.order.claimapply;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.ConfigPg;
import saleson.shop.deliverycompany.DeliveryCompanyMapper;
import saleson.shop.order.OrderService;
import saleson.shop.order.claimapply.domain.ClaimApply;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.support.ClaimException;
import saleson.shop.order.claimapply.support.ExchangeApply;
import saleson.shop.order.claimapply.support.ReturnApply;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.order.refund.OrderRefundService;
import saleson.shop.order.refund.domain.OrderRefund;
import saleson.shop.order.support.OrderException;
import saleson.shop.order.support.OrderParam;
import saleson.shop.user.domain.UserDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/order-claim-apply", "/m/order-claim-apply"})
@RequestProperty(layout="base")
public class OrderClaimApplyController {
	private static final Logger log = LoggerFactory.getLogger(OrderClaimApplyController.class);

	@Autowired
	private OrderClaimApplyService orderClaimApplyService;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRefundService orderRefundService;
	
	@Autowired
	private DeliveryCompanyMapper deliveryCompanyMapper;

	@Autowired
	Environment environment;

	@Autowired
	private ConfigPgService configPgService;

	/**
	 * 주문 전체 취소
	 * @param orderParam
	 * @param requestContext
	 * @return
	 */
	@PostMapping("/cancel-all")
	public JsonView cancelAll(OrderParam orderParam, RequestContext requestContext, HttpServletRequest request) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		
		try {
			orderClaimApplyService.orderCancelAllProcess(orderParam, request);
		} catch (OrderException ex) {
			log.error("ERROR: {}", ex.getMessage(), ex);

			map.put("isSuccess", false);
			map.put("errorMessage", ex.getMessage());
		} catch (ClaimException ce) {
			map.put("errorCode", ce.getErrorCode());
			map.put("errorMessage", ce.getMessage());
			map.put("isSuccess", false);
		}
		
		return JsonViewUtils.success(map);
	}
	
	@PostMapping("/cancel/refund-amount")
	@RequestProperty(layout="base")
	public String cancelRefundAmount(RequestContext requestContext, 
			ClaimApply claimApply, Model model) {
		
		claimApply.setClaimType("1");
		OrderRefund orderRefund = orderRefundService.getOrderCancelRefundForUser(claimApply);
		if (orderRefund == null) {
			throw new PageNotFoundException();
		}
		
		if (ShopUtils.isMobilePage()) {
			RequestContextUtils.setTemplate("mobile");
		}

		ConfigPg configPg = configPgService.getConfigPg();
		String pgType = "";

		if (configPg != null) {
			pgType = configPg.getPgType().getCode().toLowerCase();
		} else {
			pgType = SalesonProperty.getPgService();
		}

		// 2017.05.25 Kspay 현금영수증정보 추가
		if("kspay".equals(pgType)) {
//			int cashReceiptId = cashReceiptMapper.getCashReceiptIdByParam(orderRefund.getOrderCode()) == null ? 0 : cashReceiptMapper.getCashReceiptIdByParam(orderRefund.getOrderCode());
//			model.addAttribute("cashReceiptId", cashReceiptId);
		}

		int claimApplyQuantity = 0;
		OrderPgData orderPgData = orderService.getOrderPgDataByOrderCode(orderRefund.getOrderCode());

		int claimApplyAmount = 0;

		String partCancel = "0";

		for (OrderCancelApply orderCancelApply : claimApply.getOrderCancelApplys()) {
			claimApplyAmount += orderCancelApply.getClaimApplyAmount();
			claimApplyQuantity += orderCancelApply.getClaimApplyQuantity();
		}

		if (claimApply.getOrder().getPayAmount() != claimApplyAmount) {
			partCancel = "1";
		}

		model.addAttribute("claimApplyQuantity", claimApplyQuantity);
		model.addAttribute("partCancel", partCancel);
		model.addAttribute("orderPgData", orderPgData);


		model.addAttribute("bankListByKey", ShopUtils.getBankListByKey(pgType));
		model.addAttribute("orderRefund", orderRefund);

		return "view:/order-claim-apply/cancel-form";
	}
	
	@GetMapping("/return")
	@RequestProperty(layout="base")
	public String returnApplyForm(ReturnApply returnApply, Model model, OrderParam orderParam) {

		return getReturnApply(returnApply, model, orderParam);

	}

	@PostMapping("/return")
	@RequestProperty(layout="base")
	public String returnApply(ReturnApply returnApply, Model model, OrderParam orderParam) {

		return getReturnApply(returnApply, model, orderParam);

	}

	private String getReturnApply(ReturnApply returnApply, Model model, OrderParam orderParam) {
		if (UserUtils.isUserLogin()) {
			orderParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {
			User user = UserUtils.getGuestLogin();
			UserDetail userDetail = (UserDetail) user.getUserDetail();

			orderParam.setGuestUserName(user.getUserName());
			orderParam.setGuestPhoneNumber(userDetail.getPhoneNumber());
		} else {
			throw new PageNotFoundException();
		}

		if (ShopUtils.isMobilePage()) {
			RequestContextUtils.setTemplate("mobile");
			RequestContextUtils.setLayout("base");
		}

		orderParam.setItemSequence(returnApply.getItemSequence());
		orderParam.setOrderCode(returnApply.getOrderCode());
		orderParam.setOrderSequence(returnApply.getOrderSequence());

		OrderItem orderItem = orderService.getOrderItemByParam(orderParam);
		if (orderItem == null) {
			throw new PageNotFoundException();
		}

		orderService.setOrderGiftItemForOrderItem(orderItem);

		if (!ShopUtils.checkOrderStatusChange("return", orderItem.getOrderStatus())) {
			throw new OrderException("상품을 환불 신청 하실수 있는 상태가 아닙니다.");
		}

		orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());
		OrderShippingInfo info = orderService.getOrderShippingInfoByParam(orderParam);
		returnApply.setReturnApply(info);
		returnApply.setOrderItem(orderItem);
		returnApply.setApplyQuantity(orderItem.getQuantity());
		returnApply.setShipmentReturnId(orderItem.getShipmentReturnId());

		if (!StringUtils.isEmpty(returnApply.getReturnReservePhone()) && !returnApply.getReturnReservePhone().equals("--")) {
			returnApply.setReturnReservePhone1(returnApply.getReturnReservePhone().split("-")[0]);
		}

		if (!StringUtils.isEmpty(returnApply.getReturnReserveMobile()) && !returnApply.getReturnReserveMobile().equals("--")) {
			returnApply.setReturnReserveMobile1(returnApply.getReturnReserveMobile().split("-")[0]);
		}

		ConfigPg configPg = configPgService.getConfigPg();
		String pgType = "";

		if (configPg != null) {
			pgType = configPg.getPgType().getCode().toLowerCase();
		} else {
			pgType = SalesonProperty.getPgService();
		}

		orderParam.setAdditionItemFlag("Y");
		orderParam.setParentItemId(orderItem.getItemId());
		orderParam.setParentItemOptions(orderItem.getOptions());
		List<OrderItem> additionOrderItem = orderService.getOrderItemListByParam(orderParam);

		orderClaimApplyService.setOrderAdditionItemForOrderItem(orderItem, additionOrderItem);

		try {
			model.addAttribute("bankListByKey", ShopUtils.getBankListByKey(pgType));

			model.addAttribute("deliveryCompanyList", deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
			model.addAttribute("claimReasons", CodeUtils.getCodeInfoList("RETURN_REASON"));	// 환불 사유
			model.addAttribute("returnApply", returnApply);
			return "view:/order-claim-apply/return";
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);

			model.addAttribute("errorMessage", "ERROR - " +e.getMessage());
			return ViewUtils.getView("/mypage/apply-error");
		}
	}

	@GetMapping("/exchange")
	@RequestProperty(layout="base")
	public String exchangeApplyForm(ExchangeApply exchangeApply, Model model, OrderParam orderParam) {

		return getExchangeApply(exchangeApply, model, orderParam);
	}

	@PostMapping("/exchange")
	@RequestProperty(layout="base")
	public String exchangeApply(ExchangeApply exchangeApply, Model model, OrderParam orderParam) {

		return getExchangeApply(exchangeApply, model, orderParam);
	}

	private String getExchangeApply(ExchangeApply exchangeApply, Model model, OrderParam orderParam) {
		if (UserUtils.isUserLogin()) {
			orderParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {
			User user = UserUtils.getGuestLogin();
			UserDetail userDetail = (UserDetail) user.getUserDetail();

			orderParam.setGuestUserName(user.getUserName());
			orderParam.setGuestPhoneNumber(userDetail.getPhoneNumber());
		} else {
			throw new PageNotFoundException();
		}

		if (ShopUtils.isMobilePage()) {
			RequestContextUtils.setTemplate("mobile");
			RequestContextUtils.setLayout("base");
		}

		orderParam.setItemSequence(exchangeApply.getItemSequence());
		orderParam.setOrderCode(exchangeApply.getOrderCode());
		orderParam.setOrderSequence(exchangeApply.getOrderSequence());

		OrderItem orderItem = orderService.getOrderItemByParam(orderParam);
		if (orderItem == null) {
			throw new PageNotFoundException();
		}

		orderService.setOrderGiftItemForOrderItem(orderItem);

		if (!ShopUtils.checkOrderStatusChange("exchange", orderItem.getOrderStatus())) {
			throw new OrderException("상품을 교환 신청 하실수 있는 상태가 아닙니다.");
		}

		orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());
		OrderShippingInfo info = orderService.getOrderShippingInfoByParam(orderParam);
		exchangeApply.setExchangeApply(info);
		exchangeApply.setOrderItem(orderItem);
		exchangeApply.setApplyQuantity(orderItem.getQuantity());
		exchangeApply.setShipmentReturnId(orderItem.getShipmentReturnId());

		if(!StringUtils.isEmpty(exchangeApply.getExchangeReceivePhone()) && !exchangeApply.getExchangeReceivePhone().equals("--")) {
			exchangeApply.setExchangeReceiveMobile1(exchangeApply.getExchangeReceivePhone().split("-")[0]);
		}

		if(!StringUtils.isEmpty(exchangeApply.getExchangeReceiveMobile()) && !exchangeApply.getExchangeReceiveMobile().equals("--")) {
			exchangeApply.setExchangeReceiveMobile1(exchangeApply.getExchangeReceiveMobile().split("-")[0]);
		}

		orderParam.setAdditionItemFlag("Y");
		orderParam.setParentItemId(orderItem.getItemId());
		orderParam.setParentItemOptions(orderItem.getOptions());
		List<OrderItem> additionOrderItem = orderService.getOrderItemListByParam(orderParam);

		orderClaimApplyService.setOrderAdditionItemForOrderItem(orderItem, additionOrderItem);

		try {
			model.addAttribute("deliveryCompanyList", deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
			model.addAttribute("claimReasons", CodeUtils.getCodeInfoList("EXCHANGE_REASON"));	// 환불 사유
			model.addAttribute("exchangeApply", exchangeApply);
			return "view:/order-claim-apply/exchange";
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);

			model.addAttribute("errorMessage", "ERROR - " +e.getMessage());
			return ViewUtils.getView("/mypage/apply-error");
		}

	}

    /**
     * 주문취소 신청
     * @param claimApply
     * @param model
     * @param orderParam
     * @return
     */
	@GetMapping("/cancel")
	@RequestProperty(layout="base")
	public String cancelApplyForm(ClaimApply claimApply, Model model, OrderParam orderParam) {

		return getCancelApply(claimApply, model, orderParam);
	}

	/**
	 * 주문취소 신청
	 * @param claimApply
	 * @param model
	 * @param orderParam
	 * @return
	 */
	@PostMapping("/cancel")
	@RequestProperty(layout="base")
	public String cancelApply(ClaimApply claimApply, Model model, OrderParam orderParam) {

		return getCancelApply(claimApply, model, orderParam);
	}

	private String getCancelApply(ClaimApply claimApply, Model model, OrderParam orderParam) {
		if (UserUtils.isUserLogin()) {
			orderParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {
			User user = UserUtils.getGuestLogin();
			UserDetail userDetail = (UserDetail) user.getUserDetail();

			orderParam.setGuestUserName(user.getUserName());
			orderParam.setGuestPhoneNumber(userDetail.getPhoneNumber());
		} else {
			throw new PageNotFoundException();
		}

		if (ShopUtils.isMobilePage()) {
			RequestContextUtils.setTemplate("mobile");
			//RequestContextUtils.setLayout("base");
		}

		orderParam.setOrderCode(claimApply.getOrderCode());
		orderParam.setOrderSequence(claimApply.getOrderSequence());
		Order order = orderService.getOrderByParam(orderParam);

		if (order == null) {
			throw new PageNotFoundException();
		}

		// CJH 2016.11.13 사용자가 클릭하고 들어온 상품의 주문상태와 동일한것만 화면에 노출하는 조건 추가
		String userClickItemStatus = "";
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem orderItem : info.getOrderItems()) {
				if (orderItem.getItemSequence() == claimApply.getItemSequence()) {
					userClickItemStatus = orderItem.getOrderStatus();
					break;
				}
			}

			if (StringUtils.isEmpty(userClickItemStatus) == false) {
				break;
			}
		}

		if (StringUtils.isEmpty(userClickItemStatus)) {
			throw new ClaimException();
		}

		int count = 0;
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem orderItem : info.getOrderItems()) {

				// 신청
				if ("1".equals(claimApply.getClaimType())) {

					// 주문 취소 신청은 배송준비중, 결제 확인일때 가능함
					if (userClickItemStatus.equals(orderItem.getOrderStatus())) {
						orderItem.setClaimApplyFlag("Y");
						count++;

						orderItem.setClaimApplyItemKey(Integer.toString(orderItem.getItemSequence()));

						// 추가구성상품
						if (orderItem.getAdditionItemList() != null) {
							for (OrderItem additionItem : orderItem.getAdditionItemList()) {
								additionItem.setClaimApplyFlag("Y");
								additionItem.setClaimApplyItemKey(Integer.toString(additionItem.getItemSequence()));
							}
						}

					}

				} else if ("4".equals(claimApply.getClaimType())) { // CJH 2016.11.13 4번은 검증이 되지 않았습니다. 사용하지 말아주세요.

					// 즉시 주문 취소 - 결제 확인 상품만 가능
					if ("0".equals(orderItem.getOrderStatus()) || "10".equals(orderItem.getOrderStatus())) {
						//orderItem.setClaimApplyFlag("Y");
						//count++;

						orderItem.setClaimApplyItemKey(Integer.toString(orderItem.getItemSequence()));

						// 추가구성상품
						if (orderItem.getAdditionItemList() != null) {
							for (OrderItem additionItem : orderItem.getAdditionItemList()) {
								additionItem.setClaimApplyItemKey(Integer.toString(additionItem.getItemSequence()));
							}
						}
					}
				}

				orderService.setOrderGiftItemForOrderItem(orderItem);
			}
		}

		if (count == 0) {
			throw new PageNotFoundException();
		}

		claimApply.setOrder(order);

		try {
			model.addAttribute("userClickItemStatus", userClickItemStatus);
			model.addAttribute("claimReasons", CodeUtils.getCodeInfoList("CANCEL_REASON"));	//취소사유
			model.addAttribute("claimApply", claimApply);

			return "view:/order-claim-apply/cancel";
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);

			model.addAttribute("errorMessage", "ERROR - " +e.getMessage());
			return ViewUtils.getView("/mypage/apply-error");
		}
	}

    /**
     * 주문취소 처리
     * @param requestContext
     * @param claimApply
     * @return
     */
	@PostMapping("cancel/process")
	public JsonView cancelApplyProcess(RequestContext requestContext, ClaimApply claimApply, HttpServletRequest request, HttpServletResponse response) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		if (UserUtils.isUserLogin()) {
			
		} else if (UserUtils.isGuestLogin()) {
			
		} else {
			throw new PageNotFoundException();
		}
		
		
		try {
			claimApply.setRequest(request);
			claimApply.setResponse(response);

			orderClaimApplyService.insertOrderCancelApply(claimApply);
			return JsonViewUtils.success();
			
		} catch (OrderException ex) {
			log.error("ERROR: {}", ex.getMessage(), ex);

			return JsonViewUtils.exception(ex.getMessage());
		}
		
	}

    /**
     * 환불 신청
     * @param requestContext
     * @param returnApply
     * @return
     */
	@PostMapping("return/process")
	public JsonView returnApplyProcess(RequestContext requestContext, ReturnApply returnApply) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			//returnApply.setReturnReserveMobile(returnApply.getReturnReserveMobile1() + "-" + returnApply.getReturnReserveMobile2() + "-" + returnApply.getReturnReserveMobile3());
			//returnApply.setReturnReservePhone(returnApply.getReturnReservePhone1() + "-" + returnApply.getReturnReservePhone2() + "-" + returnApply.getReturnReservePhone3());
			orderClaimApplyService.insertOrderReturnApply(returnApply);

			orderClaimApplyService.insertAdditionReturnApply(returnApply);

			return JsonViewUtils.success();
			
		} catch (OrderException ex) {
			return JsonViewUtils.exception(ex.getMessage());
		}
		
	}

    /**
     * 환불 신청
     * @param requestContext
     * @param exchangeApply
     * @return
     */
	@PostMapping("exchange/process")
	public JsonView exchangeApplyProcess(RequestContext requestContext, ExchangeApply exchangeApply) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			//exchangeApply.setExchangeReceiveMobile(exchangeApply.getExchangeReceiveMobile1() + "-" + exchangeApply.getExchangeReceiveMobile2() + "-" + exchangeApply.getExchangeReceiveMobile3());
			//exchangeApply.setExchangeReceivePhone(exchangeApply.getExchangeReceivePhone1() + "-" + exchangeApply.getExchangeReceivePhone2() + "-" + exchangeApply.getExchangeReceivePhone3());
			orderClaimApplyService.insertOrderExchangeApply(exchangeApply);

			orderClaimApplyService.insertAdditionExchangeApply(exchangeApply);

			return JsonViewUtils.success();
			
		} catch (OrderException ex) {
			return JsonViewUtils.exception(ex.getMessage());
		}
		
	}

}
