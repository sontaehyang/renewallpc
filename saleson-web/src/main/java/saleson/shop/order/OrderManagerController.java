package saleson.shop.order;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.i18n.support.CodeResolver;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import saleson.common.Const;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.PointUtils;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.ConfigPg;
import saleson.seller.main.SellerService;
import saleson.seller.main.support.SellerParam;
import saleson.shop.accountnumber.AccountNumberService;
import saleson.shop.cart.CartService;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.claim.ClaimService;
import saleson.shop.claim.domain.ClaimMemo;
import saleson.shop.claim.support.ClaimMemoParam;
import saleson.shop.customer.CustomerService;
import saleson.shop.deliverycompany.DeliveryCompanyMapper;
import saleson.shop.item.ItemService;
import saleson.shop.mailconfig.MailConfigService;
import saleson.shop.mall.MallService;
import saleson.shop.order.addpayment.domain.OrderAddPayment;
import saleson.shop.order.admin.OrderAdminService;
import saleson.shop.order.admin.support.OrderAdminException;
import saleson.shop.order.admin.support.OrderAdminParam;
import saleson.shop.order.claimapply.OrderClaimApplyService;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderExchangeApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.claimapply.domain.*;
import saleson.shop.order.claimapply.support.ClaimApplyParam;
import saleson.shop.order.claimapply.support.ClaimException;
import saleson.shop.order.domain.*;
import saleson.shop.order.giftitem.OrderGiftItemService;
import saleson.shop.order.pg.PgService;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.order.refund.OrderRefundService;
import saleson.shop.order.refund.domain.OrderRefund;
import saleson.shop.order.refund.support.OrderRefundParam;
import saleson.shop.order.shipping.support.ShippingParam;
import saleson.shop.order.shipping.support.ShippingReadyParam;
import saleson.shop.order.support.*;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.AvailablePoint;
import saleson.shop.receipt.ReceiptService;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.sendmaillog.SendMailLogService;
import saleson.shop.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/opmanager/order")
@RequestProperty(title="????????????", layout="default", template="opmanager")
public class OrderManagerController {

	private static final Logger log = LoggerFactory.getLogger(OrderManagerController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CodeResolver codeResolver;
	
	@Autowired
	private SendMailLogService sendMailLogService;
	
	@Autowired
	private CategoriesTeamGroupService categoryCategoriesTeamGroupService;
	
	@Autowired
	private MailConfigService mailConfigService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private DeliveryCompanyMapper deliveryCompanyMapper;
	
	@Autowired
	private AccountNumberService accountNumberService;
	
	@Autowired
	private MallService openMarketService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ClaimService claimService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private OrderClaimApplyService orderClaimApplyService;
	
	@Autowired
	private OrderRefundService orderRefundService;
	
	@Autowired
	private OrderAdminService orderAdminService;
	
	@Autowired
	private PgService inicisService;

	@Autowired
    private ReceiptService receiptService;

	@Autowired
	private OrderGiftItemService orderGiftItemService;

	@Autowired
	private ConfigPgService configPgService;

	@GetMapping("list")
	public String list(@ModelAttribute OrderParam orderParam, Model model) {
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}
		
		if (StringUtils.isEmpty(orderParam.getSearchDateType())) {
			orderParam.setSearchDateType("OI.CREATED_DATE");
		}
		
		if (StringUtils.isEmpty(orderParam.getSearchStartDate())) {
			orderParam.setSearchStartDate(DateUtils.getToday(Const.DATE_FORMAT));
			orderParam.setSearchEndDate(DateUtils.getToday(Const.DATE_FORMAT));
		}

		orderParam.setAdditionItemFlag("Y");
		List<OrderList> additionList = orderService.getAllOrderListByParamForManager(orderParam);

		orderParam.setAdditionItemFlag("N");
		List<OrderList> itemList = orderService.getAllOrderListByParamForManager(orderParam);

		model.addAttribute("list", itemList);
		model.addAttribute("additionList", additionList);
		model.addAttribute("pagination", orderParam.getPagination());
		model.addAttribute("totalCount", orderParam.getPagination().getTotalItems());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		
		return "view:/order/list/all";
	}

	/**
	 * ???????????? ???????????? ?????? ??????
	 * @param requestContext
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("{pageType}/order-info/change")
	public JsonView orderInfoChange(RequestContext requestContext, @Valid Order order, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return JsonViewUtils.failure("?????? ?????? ????????? ????????? ????????? ?????????.");
		}
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		try {
			orderService.saveOrderInfo(order);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.failure(e.getMessage());
		}
	}

	/**
	 * ???????????? ????????? ?????? ??????
	 * @param requestContext
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("{pageType}/admin-memo/change")
	public JsonView changeAdminMemo(RequestContext requestContext, @Valid Order order, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return JsonViewUtils.failure("?????? ?????? ????????? ????????? ????????? ?????????.");
		}
		 
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		try {
			orderService.updateAdminMemo(order);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.failure(e.getMessage());
		}
	}

	/**
	 * ???????????? ???????????? ??????
	 * @param requestContext
	 * @param orderParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="blank")
	@PostMapping("{pageType}/order-info/reload")
	public String saveOrderInfo(RequestContext requestContext, OrderParam orderParam, Model model) {
		
		orderParam.setConditionType("OPMANAGER");
		
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}
		
		
		Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			throw new PageNotFoundException();
		}

		model.addAttribute("order", order);
		return "view:/order/include/order-shipping-info";
	}
	
	/**
	 * ?????? ??????
	 * @param claimMemo
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("{pageType}/claim-memo/create")
	public JsonView claimMemoCreateAction(RequestContext requestContext, @Valid ClaimMemo claimMemo, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return JsonViewUtils.failure("?????? ?????? ????????? ????????? ????????? ?????????.");
		}
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			claimService.insertClaimMemo(claimMemo);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.failure(e.getMessage());
		}
	}

	/**
	 * ?????? ?????????
	 * @param requestContext
	 * @param param
	 * @param model
	 * @param pageType
	 * @return
	 */
	@PostMapping(value="{pageType}/claim-memo/list")
	@RequestProperty(layout="blank")
	public String claimMemoList(RequestContext requestContext, ClaimMemoParam param, Model model,
			@PathVariable("pageType") String pageType) {
		
		int totalCount = claimService.getClaimMemoCount(param);
		
		Pagination pagination = Pagination.getInstance(totalCount, 5);
		pagination.setLink("javascript:claimMemoList('" + param.getOrderCode() + "', [page])");
		param.setPagination(pagination);
		
		List<ClaimMemo> list = claimService.getClaimMemoList(param);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageType", pageType);
		model.addAttribute("list", list);
		return "view:/order/memo/list";
	}

	/**
	 * ?????? ?????????
	 * @param requestContext
	 * @param param
	 * @param model
	 * @param claimMemoId
	 * @param pageType
	 * @return
	 */
	@GetMapping(value="{pageType}/claim-memo/update/{claimMemoId}")
	@RequestProperty(layout="base")
	public String claimMemoUpdate(RequestContext requestContext, ClaimMemoParam param, Model model,
			@PathVariable("claimMemoId") int claimMemoId,
			@PathVariable("pageType") String pageType) {
		
		ClaimMemo memo = claimService.getClaimMemoById(claimMemoId);
		if (memo == null) {
			throw new PageNotFoundException();
		}
		
		User user = userService.getUserByUserId(memo.getUserId());
		if (user == null) {
			throw new PageNotFoundException();
		}

		user.setUserId(user.getUserId());
		user.setUserName(user.getUserName());
		model.addAttribute("user", user);
		model.addAttribute("pageType", pageType);
		model.addAttribute("memo", memo);
		return "view:/order/memo/form";
	}
	
	@PostMapping("{pageType}/claim-memo/update/{claimMemoId}")
	public String claimMemoUpdate(RequestContext requestContext, ClaimMemo memo, Model model,
			@PathVariable("claimMemoId") int claimMemoId,
			@PathVariable("pageType") String pageType) {
		
		claimService.updateClaimMemo(memo);
		
		return ViewUtils.redirect("/opmanager/order/"+ pageType +"/claim-memo/update/" + claimMemoId,
				MessageUtils.getMessage("M01221"), "try{opener.claimMemoList('"+ memo.getOrderCode() +"', 1);}catch(e){}"); //?????????????????????. 
	}

	/**
	 * ???????????? ????????? ?????? ?????? POPUP (???????????? ?????? ????????? ????????????)
	 * @param orderCode
	 * @param model
	 * @return
	 */
	@GetMapping("{pageType}/order-detail-popup/{orderSequence}/{orderCode}")
	@RequestProperty(layout = "base")
	public String detailPopup(@PathVariable("orderCode") String orderCode,
							  @PathVariable("orderSequence") int orderSequence,
							  @PathVariable("pageType") String pageType,
							  Model model) {

		setModelOrderDetail(model, orderCode, pageType, orderSequence, "popup");
		return "view:/order/detail/order-detail";
	}

	private void setModelOrderDetail(Model model, String orderCode, String pageType, int orderSequence, String mode) {
		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(orderCode);
		orderParam.setOrderSequence(orderSequence);

		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}

        Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			throw new PageNotFoundException();
		}

		bindItemDetail(model, orderParam, pageType);

		// ??????????????? ???????????? ?????? ????????? ???????????? ?????? orderItems??? ????????? ?????????????????? ??????
		for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
			List<OrderItem> itemList = orderShippingInfo.getOrderItems()
					.stream().filter(l -> "N".equals(l.getAdditionItemFlag())).collect(Collectors.toList());

			orderShippingInfo.getOrderItems().clear();
			orderShippingInfo.getOrderItems().addAll(itemList);
		}

		model.addAttribute("order", order);
		model.addAttribute("isSellerPage", ShopUtils.isSellerPage());

		CashbillParam cashbillParam = new CashbillParam();
		cashbillParam.setWhere("orderCode");
		cashbillParam.setQuery(orderCode);

		// ???????????? ?????? ?????? (???????????????)
		model.addAttribute("cashbillIssues", receiptService.findAllCashbillIssue(cashbillParam.getPredicate()));

		OrderLog orderLog = new OrderLog();
		orderLog.setOrderCode(orderCode);

		model.addAttribute("orderLogs", orderService.getOrderLogListByOrderCode(orderLog));
		model.addAttribute("mode", mode);
	}

	/**
	 * ?????? ?????? (???????????? ?????? ????????? ????????????)
	 * @param orderCode
	 * @param model
	 * @return
	 */
	@GetMapping("{pageType}/order-detail/{orderSequence}/{orderCode}")
	public String detail(@PathVariable("orderCode") String orderCode,
			@PathVariable("pageType") String pageType,
			@PathVariable("orderSequence") int orderSequence, Model model) {

		setModelOrderDetail(model, orderCode, pageType, orderSequence, "");

		return "view:/order/detail/order-detail";
	}
	
	/**
	 * ?????? ?????? (???????????? ?????? ????????? ????????????)
	 * @param orderCode
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="blank")
	@PostMapping("{pageType}/item-detail/{orderSequence}/{orderCode}")
	public String itemDetail(@PathVariable("orderCode") String orderCode,
			@PathVariable("pageType") String pageType,
			@PathVariable("orderSequence") int orderSequence, Model model) {

		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(orderCode);
		orderParam.setOrderSequence(orderSequence);

		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}

		Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			throw new PageNotFoundException();
		}

		bindItemDetail(model, orderParam, pageType);
		model.addAttribute("order", order);
		model.addAttribute("isSellerPage", ShopUtils.isSellerPage());
		return "view:/order/include/item-detail";
	}


	/**
	 * ??????????????? ???????????? ????????? ?????? - ?????????
	 * @param model
	 * @param orderParam
	 */
	private void bindItemDetail(Model model, OrderParam orderParam, String pageType) {

		ClaimApplyParam claimApplyParam = new ClaimApplyParam();
		claimApplyParam.setOrderCode(orderParam.getOrderCode());
		claimApplyParam.setOrderSequence(orderParam.getOrderSequence());
		
		claimApplyParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			claimApplyParam.setConditionType("SELLER");
			claimApplyParam.setSellerId(SellerUtils.getSellerId());
		}
		
		model.addAttribute("activeExchanges", orderClaimApplyService.getActiveExchangeListByParam(claimApplyParam));
		model.addAttribute("exchangeHistorys", orderClaimApplyService.getExchangeHistoryListByParam(claimApplyParam));
		
		model.addAttribute("activeCancels", orderClaimApplyService.getActiveCancelListByParam(claimApplyParam));
		model.addAttribute("cancelHistorys", orderClaimApplyService.getCancelHistoryListByParam(claimApplyParam));
		
		model.addAttribute("activeReturns", orderClaimApplyService.getActiveReturnListByParam(claimApplyParam));
		model.addAttribute("returnHistorys", orderClaimApplyService.getReturnHistoryListByParam(claimApplyParam));

		model.addAttribute("deliveryCompanyList", deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
		
		model.addAttribute("cancelClaimReasons", CodeUtils.getCodeInfoList("CANCEL_REASON"));	// ????????????
		model.addAttribute("exchangeClaimReasons", CodeUtils.getCodeInfoList("EXCHANGE_REASON"));	// ????????????
		model.addAttribute("returnClaimReasons", CodeUtils.getCodeInfoList("RETURN_REASON"));	// ????????????
		
		String viewTabIndex = "0";
		if ("cancel".equals(pageType)) {
			viewTabIndex = "1";
		} else if ("return".equals(pageType)) {
			viewTabIndex = "2";
		} else if ("exchange".equals(pageType)) {
			viewTabIndex = "3";
		}
		
		model.addAttribute("pageType", pageType);
		model.addAttribute("viewTabIndex", viewTabIndex);

		// ?????? ?????? ????????? ??????
		model.addAttribute("orderGiftItems", orderGiftItemService.getOrderGiftItemListByOrderCode(orderParam.getOrderCode()));

	}
	
	/**
	 * ?????? ??????
	 * @return
	 */
	@PostMapping("{pageType}/claim/return/process")
	public JsonView claimReturnProcess(ClaimApply claimApply, HttpServletResponse response,
			@PathVariable("pageType") String pageType, RequestContext requestContext,HttpServletRequest request) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			String orderCode = claimApply.getOrderCode();
			int orderSequence = claimApply.getOrderSequence();
		
			boolean isGetRefundCode = false;
			
			// ??????
			String[] returnIds = claimApply.getReturnIds();
			if (returnIds != null) {
				
				for(String claimCode : claimApply.getReturnIds()) {
					OrderReturnApply returnApply = claimApply.getReturnApplyMap().get(claimCode);
					if (returnApply == null) {
						throw new OrderException();
					}
					
					String claimStatus = returnApply.getClaimStatus();
					if ("03".equals(claimStatus)) {
						isGetRefundCode = true;
					}					
				}
			}
			
			if (isGetRefundCode) {
				OrderRefundParam orderRefundParam = new OrderRefundParam();
				orderRefundParam.setOrderCode(orderCode);
				orderRefundParam.setOrderSequence(orderSequence);
				
				String refundCode = orderRefundService.getActiveRefundCodeByParam(orderRefundParam);
				if (StringUtils.isEmpty(refundCode)) {
					throw new OrderException();
				}
			
				claimApply.setRefundCode(refundCode);
			}
			
			orderClaimApplyService.orderReturnProcess(claimApply);
			
		} catch (ClaimException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			try {
				response.sendError(Integer.parseInt(e.getErrorCode()));
			} catch (Exception e1) {
				log.debug(e1.getMessage());
			}
		}
		
		return JsonViewUtils.success();
	}
	
	/**
	 * ?????? - ???????????? (????????? ?????? ??? ?????? ??????)
	 * @return
	 */
	@RequestProperty(layout="blank")
	@PostMapping("{pageType}/claim/return/view")
	public String claimReturnView(ClaimApply claimApply,
			@PathVariable("pageType") String pageType, RequestContext requestContext,
			Model model, HttpServletResponse response) {
		
		try {
			if (!requestContext.isAjaxRequest()) { 
			    throw new NotAjaxRequestException();
			}
			
			String[] returnIds = claimApply.getReturnIds();
			if (returnIds == null) {
				throw new ClaimException("9000");
			}
			
			OrderParam orderParam = new OrderParam();
			orderParam.setConditionType("OPMANAGER");
			orderParam.setOrderCode(claimApply.getOrderCode());
			orderParam.setOrderSequence(claimApply.getOrderSequence());
			Order order = orderService.getOrderByParam(orderParam);
	
			if (order == null) {
				throw new ClaimException();
			}
			
			List<OrderAddPayment> list = null;
			
			boolean isError = false;
			String errorMessage = "";
			//????????? [2017-03-23 ??????] exception ???????????????, ??????????????? html??? ?????? ??? ajax ???????????? ?????? ??????
			try {
				list = orderClaimApplyService.orderReturnViewData(claimApply, order);
			} catch (OrderException oe) {
				log.debug(oe.getMessage());
				isError = true;
				errorMessage = oe.getMessage(); 
			}
			model.addAttribute("errorMessage", errorMessage);
			model.addAttribute("isError", isError);
			
			int returnAmount = 0;
			int addAmount = 0;
			if (list != null) {
				for(OrderAddPayment addPayment : list) {
					if ("2".equals(addPayment.getAddPaymentType())) {
						returnAmount += addPayment.getAmount();
					} else {
						addAmount += addPayment.getAmount();
					}
				}
			}
			
			List<OrderItem> itemList = new ArrayList<>();
			for(OrderShippingInfo info : order.getOrderShippingInfos()) {
				for(OrderItem item : info.getOrderItems()) {
					itemList.add(item);
				}
			}
			
			int itemReturnAmount = 0;
			
			List<OrderReturnApply> newApplyList = new ArrayList<>();
			for(String claimCode : claimApply.getReturnIds()) {
				
				OrderReturnApply returnApply = claimApply.getReturnApplyMap().get(claimCode);
				if (returnApply == null) {
					throw new ClaimException();
				}
				
				for(OrderItem item : itemList) {
					
					if (returnApply.getItemSequence() == item.getItemSequence()) {
						
						if (returnApply.getClaimCode().equals(claimCode)) {
							
							if (!"99".equals(returnApply.getClaimStatus())) {
								item.setQuantity(item.getQuantity() - returnApply.getClaimApplyQuantity());
							}
							
							if (!"99".equals(returnApply.getClaimStatus())) {
								item.setQuantity(item.getQuantity() - returnApply.getClaimApplyQuantity());
							}
							
							returnApply.setOrderItem(item);
							newApplyList.add(returnApply);
							
							itemReturnAmount += (item.getSalePrice() * item.getQuantity());
							break;
						}
					}
				}
			}
			
			long sellerId = SellerUtils.DEFAULT_OPMANAGER_SELLER_ID;
			if (ShopUtils.isSellerPage()) {
				sellerId = SellerUtils.getSellerId();
			}
			
			model.addAttribute("loginSellerId", sellerId);
			model.addAttribute("order", order);
			model.addAttribute("applys", newApplyList);
			model.addAttribute("itemReturnAmount", itemReturnAmount);
			model.addAttribute("returnAmount", returnAmount + itemReturnAmount);
			model.addAttribute("addAmount", addAmount);
			model.addAttribute("addPayments", list);
			
		} catch (ClaimException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			try {
				response.sendError(Integer.parseInt(e.getErrorCode()));
			} catch (Exception e1) {
				log.debug(e1.getMessage(), e1);
			}
		}
		
		return "view:/order/popup/claim-return-view";
	}
	
	/**
	 * ?????? - ?????? (03 : ?????? ???????????? ????????? ?????? ?????? ?????? ??????)
	 * @return
	 */
	@RequestProperty(layout="blank")
	@PostMapping("{pageType}/claim/return/save")
	public JsonView claimReturnSave(ClaimApply claimApply,
			@PathVariable("pageType") String pageType, RequestContext requestContext,
			HttpServletResponse response) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			orderClaimApplyService.orderReturnSaveProcess(claimApply);
		} catch (ClaimException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			try {
				response.sendError(Integer.parseInt(e.getErrorCode()));
			} catch (Exception e1) {
				log.debug(e1.getMessage(), e);
			}
			
		}
		return JsonViewUtils.success();
	}
	
	/**
	 * ?????? ??????
	 * @return
	 */
	@PostMapping("{pageType}/claim/exchange/process")
	public JsonView claimExchangeProcess(ClaimApply claimApply,
			@PathVariable("pageType") String pageType, RequestContext requestContext) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		orderClaimApplyService.orderExchangeProcess(claimApply);
		return JsonViewUtils.success();
		
	}
	
	/**
	 * ?????? ??????
	 * @return
	 */
	@PostMapping("{pageType}/claim/cancel/process")
	public JsonView claimCancelProcess(ClaimApply claimApply,
			@PathVariable("pageType") String pageType, RequestContext requestContext) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		String orderCode = claimApply.getOrderCode();
		int orderSequence = claimApply.getOrderSequence();
	
		String[] cancelIds = claimApply.getCancelIds();
		boolean isGetRefundCode = false;
		
		// ??????
		if (cancelIds != null) {
			
			for(String claimCode : cancelIds) {
				OrderCancelApply cancelApply = claimApply.getCancelApplyMap().get(claimCode);
				if (cancelApply == null) {
					throw new OrderException();
				}
				
				String claimStatus = cancelApply.getClaimStatus();
				if ("03".equals(claimStatus)) {
					isGetRefundCode = true;
				}
			}
		}
		
		if (isGetRefundCode) {
			OrderRefundParam orderRefundParam = new OrderRefundParam();
			orderRefundParam.setOrderCode(orderCode);
			orderRefundParam.setOrderSequence(orderSequence);
			
			String refundCode = orderRefundService.getActiveRefundCodeByParam(orderRefundParam);
			if (StringUtils.isEmpty(refundCode)) {
				throw new OrderException();
			}
		
			claimApply.setRefundCode(refundCode);
		}
		
		orderClaimApplyService.orderCancelProcess(claimApply);
		return JsonViewUtils.success();
		
	}
	
	/**
	 * ????????? ??????
	 * @param pageType
	 * @param claimApplyParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="blank")
	@PostMapping("{pageType}/claim/cancel/list")
	public String claimCancelForm(@PathVariable("pageType") String pageType, RequestContext requestContext,
			ClaimApplyParam claimApplyParam, Model model) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(claimApplyParam.getOrderCode());
		orderParam.setOrderSequence(claimApplyParam.getOrderSequence());
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}
		
		Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			 throw new NotAjaxRequestException();
		}
		
		model.addAttribute("order", order);
		model.addAttribute("activeCancels", orderClaimApplyService.getActiveCancelListByParam(claimApplyParam));
		model.addAttribute("cancelHistorys", orderClaimApplyService.getCancelHistoryListByParam(claimApplyParam));
		model.addAttribute("pageType", pageType);
		
		return "view:/order/include/order-item-cancel";
	}
	
	/**
	 * ????????? ??????
	 * @param pageType
	 * @param claimApplyParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="blank")
	@PostMapping("{pageType}/claim/return/list")
	public String claimReturnForm(@PathVariable("pageType") String pageType, RequestContext requestContext,
			ClaimApplyParam claimApplyParam, Model model) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(claimApplyParam.getOrderCode());
		orderParam.setOrderSequence(claimApplyParam.getOrderSequence());
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}
		
		Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			 throw new NotAjaxRequestException();
		}
		
		model.addAttribute("order", order);
		model.addAttribute("deliveryCompanyList", deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
		model.addAttribute("activeReturns", orderClaimApplyService.getActiveReturnListByParam(claimApplyParam));
		model.addAttribute("returnHistorys", orderClaimApplyService.getReturnHistoryListByParam(claimApplyParam));
		model.addAttribute("pageType", pageType);
		
		return "view:/order/include/order-item-return";
	}
	
	/**
	 * ?????? ??????
	 * @param claimCode
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value="/claim/return-log/{claimCode}")
	public String returnLog(@PathVariable("claimCode") String[] claimCode, Model model) {
		
		List<OrderReturnApply> apply = orderClaimApplyService.getReturnApplyByClaimCode(claimCode);
		if (apply == null) {
			throw new PageNotFoundException();
		}

		// ??????????????????
		List<OrderReturnApply> applyAddition = apply.stream().filter(addition -> "Y".equals(addition.getOrderItem().getAdditionItemFlag())).collect(Collectors.toList());
		orderClaimApplyService.setOrderAdditionItemForOrderReturnApply(apply, applyAddition);
		
		model.addAttribute("apply", apply);
		return "view:/order/popup/return-log";
	}
	
	/**
	 * ?????? ??????
	 * @param claimCode
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value="/claim/exchange-log/{claimCode}")
	public String exchangeLog(@PathVariable("claimCode") String[] claimCode, Model model) {

		List<OrderExchangeApply> apply = orderClaimApplyService.getExchangeApplyByClaimCode(claimCode);
		if (apply == null) {
			throw new PageNotFoundException();
		}

		// ??????????????????
		List<OrderExchangeApply> applyAddition = apply.stream().filter(addition -> "Y".equals(addition.getOrderItem().getAdditionItemFlag())).collect(Collectors.toList());
		orderClaimApplyService.setOrderAdditionItemForOrderExchangeApply(apply, applyAddition);

		model.addAttribute("apply", apply);
		return "view:/order/popup/exchange-log";
	}
	
	/**
	 * ????????? ??????
	 * @param pageType
	 * @param claimApplyParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="blank")
	@PostMapping("{pageType}/claim/exchange/list")
	public String claimExchangeForm(@PathVariable("pageType") String pageType, RequestContext requestContext,
			ClaimApplyParam claimApplyParam, Model model) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(claimApplyParam.getOrderCode());
		orderParam.setOrderSequence(claimApplyParam.getOrderSequence());
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}

		Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			 throw new NotAjaxRequestException();
		}
		
		model.addAttribute("order", order);
		model.addAttribute("deliveryCompanyList", deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
		model.addAttribute("activeExchanges", orderClaimApplyService.getActiveExchangeListByParam(claimApplyParam));
		model.addAttribute("exchangeHistorys", orderClaimApplyService.getExchangeHistoryListByParam(claimApplyParam));
		model.addAttribute("pageType", pageType);
		
		return "view:/order/include/order-item-exchange";
	}
	
	/**
	 * ????????? ????????? ??????????????? - ???????????? ???????????? ??????
	 * @param requestContext
	 * @return
	 */
	@PostMapping("{pageType}/re-shipping-amount")
	public JsonView reShippingAmount(RequestContext requestContext, ClaimApply claimApply) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		List<OrderShipping> list = orderClaimApplyService.getReShippingAmountForManager(claimApply);
		
		if (list != null) {
			
			/**
			 * CJH JSON????????? ???????????? ????????? ???????????? ????????? ??????.
			 */
			for(OrderShipping shipping : list) {
				shipping.setOrderItems(null);
			}
		}
		
		return JsonViewUtils.success(list);
	}
	
	/**
	 * ???????????? ??????
	 * @return
	 */
	@GetMapping("waiting-deposit")
	public String waitingDeposit(@ModelAttribute OrderParam orderParam, Model model) {
		
		model.addAttribute("list", orderService.getWaitingDepositListByParam(orderParam));
		model.addAttribute("pagination", orderParam.getPagination());
		model.addAttribute("totalCount", orderParam.getPagination().getTotalItems());
		return "view:/order/list/waiting-deposit";
		
	}
	
	/**
	 * ???????????? ?????? ????????? ?????? ??????
	 * @return
	 */
	@PostMapping("waiting-deposit/listUpdate/{mode}")
	public JsonView waitingDepositListUpdate(OrderParam orderParam,
			@PathVariable("mode") String mode, RequestContext requestContext) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			orderService.waitingDepositListUpdate(mode, orderParam);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.failure("??????????????? ??????????????????. ( " + e.getMessage() + ")");
		}
	}

	/**
	 * ???????????? ??????
	 * @param orderParam
	 * @param key
	 * @param requestContext
	 * @return
	 */
	@PostMapping("waiting-deposit/payment-verification-cancel")
	public JsonView paymentVerificationCancel(OrderParam orderParam,
			@RequestParam("key") String key, RequestContext requestContext) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		try {
			
			String[] temp = StringUtils.delimitedListToStringArray(key, OrderPayment.PAYMENT_KEY_DIVISION_STRING);
			
			if (temp.length != 3) {
				throw new NotAjaxRequestException();
			}
			
			orderParam.setOrderCode(temp[0]);
			orderParam.setOrderSequence(Integer.parseInt(temp[1]));
			orderParam.setPaymentSequence(Integer.parseInt(temp[2]));
			
			orderService.paymentVerificationCancel(orderParam);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.failure(e.getMessage());
		}
	}
	
	/**
	 * ???????????? ??????
	 * @return
	 */
	@GetMapping("new-order")
	public String newOrder(@ModelAttribute OrderParam orderParam, Model model) {
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId()); 
		}
		
		HttpSession session = RequestContextUtils.getSession();		
		session.setAttribute("reOrderParam", orderParam);
		
		orderParam.setAdditionItemFlag("Y");
		List<OrderList> additionList = orderService.getNewOrderListByParam(orderParam);

		orderParam.setAdditionItemFlag("N");
		List<OrderList> itemList = orderService.getNewOrderListByParam(orderParam);

		model.addAttribute("list", itemList);
		model.addAttribute("additionList", additionList);
		model.addAttribute("pagination", orderParam.getPagination());
		model.addAttribute("totalCount", orderParam.getPagination().getTotalItems());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return "view:/order/list/new-order";
		
	}
	
	/**
	 * ???????????? ?????? ????????? ?????? ??????
	 * @return
	 */
	@PostMapping("new-order/listUpdate/{mode}")
	public JsonView newOrderListUpdate(OrderParam orderParam,
			@PathVariable("mode") String mode, RequestContext requestContext) {
		
		if (!requestContext.isAjaxRequest()) {
		    throw new NotAjaxRequestException();
		}
		
		if (orderParam.getId() == null) {
			throw new OrderManagerException();
		}

		orderParam.setConditionType("OPMANAGER");
		// ????????? ????????? ??????
		String adminName = UserUtils.getManagerName();
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}
		
		orderParam.setAdminUserName(adminName);
		
		int totalCount = orderParam.getId().length;
		int updateCount = 0;
		for(String key : orderParam.getId()) {
			
			String[] temp = StringUtils.delimitedListToStringArray(key, OrderItem.ITEM_KEY_DIVISION_STRING);
			
			if (temp.length != 3) {
				continue;
			}
			
			orderParam.setOrderCode(temp[0]);
			orderParam.setOrderSequence(Integer.parseInt(temp[1]));
			orderParam.setItemSequence(Integer.parseInt(temp[2]));
			
			OrderItem orderItem = orderService.getOrderItemByParam(orderParam);
			if (orderItem == null) {
				continue;
			}
			
			ShippingReadyParam shippingReadyParam = null;
			for(ShippingReadyParam param : orderParam.getShippingReadys()) {
				if (key.equals(param.getKey())) {
					shippingReadyParam = param;
					break;
				}
			}
			
			if (shippingReadyParam == null) {
				continue;
			}
			
			// ?????? ?????????????????? ?????? ??????????????? ??? ????????? ?????? ??????????????? ???????????? ??????.
			if (shippingReadyParam.getQuantity() > orderItem.getShippingReadyPossibleQuantity()) {
				continue;
			}
			
			shippingReadyParam.setOrderCode(orderParam.getOrderCode());
			shippingReadyParam.setOrderSequence(orderParam.getOrderSequence());
			shippingReadyParam.setItemSequence(orderParam.getItemSequence());
			shippingReadyParam.setSellerId(orderParam.getSellerId());
			shippingReadyParam.setAdminUserName(adminName);
			shippingReadyParam.setConditionType(orderParam.getConditionType());
			
			try {
				orderService.newOrderListUpdate(mode, shippingReadyParam, orderItem, orderParam);
				updateCount++;
			} catch (Exception e) {
				log.error("orderService.newOrderListUpdate(..) : {}", e.getMessage(), e);
			}
		}
		
		// ?????? ???????????? ???????????? ??????????
		if (totalCount != updateCount) {
			String errorMessage = "????????????????????? ??????????????? ?????? ???????????? ????????????.\n(?????? : " + NumberUtils.formatNumber(totalCount, "#,###") + ", ?????? : " + NumberUtils.formatNumber(updateCount, "#,###") + ")";
			return JsonViewUtils.failure(errorMessage);
		}
		
		return JsonViewUtils.success();
	}

	/**
	 * ??????????????? ??????
	 * @return
	 */
	@GetMapping("shipping-ready")
	public String shippingReady(@ModelAttribute OrderParam orderParam, Model model) {
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}
		
		HttpSession session = RequestContextUtils.getSession();		
		session.setAttribute("reOrderParam", orderParam);

		orderParam.setAdditionItemFlag("Y");
		List<OrderList> additionList = orderService.getShippingReadyListByParam(orderParam);

		orderParam.setAdditionItemFlag("N");
		List<OrderList> itemList = orderService.getShippingReadyListByParam(orderParam);
		
		model.addAttribute("list", itemList);
		model.addAttribute("additionList", additionList);
		model.addAttribute("pagination", orderParam.getPagination());
		model.addAttribute("totalCount", orderParam.getPagination().getTotalItems());
		model.addAttribute("deliveryCompanyList", deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return "view:/order/list/shipping-ready";
		
	}
	
	/**
	 * ??????????????? ?????? ????????? ?????? ??????
	 * @return
	 */
	@PostMapping("shipping-ready/listUpdate/{mode}")
	public JsonView shippingReadyListUpdate(OrderParam orderParam,
			@PathVariable("mode") String mode, RequestContext requestContext) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		if (orderParam.getId() == null) {
			throw new OrderManagerException();
		}

		orderParam.setConditionType("OPMANAGER");
		// ????????? ????????? ??????
		// ????????? [2017-05-15 ??????] ????????? ??????????????? ????????? ?????? ??? getUser() null??? ?????? else??? ??????.
		String adminName = "";
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());

			if (SellerUtils.getSeller() != null) {
				adminName = SellerUtils.getSeller().getSellerName();
			}
		} else {
			if (UserUtils.getUser() != null) {
				adminName = UserUtils.getUser().getUserName();
			}
		}
		
		orderParam.setAdminUserName(adminName);
		
		int totalCount = orderParam.getId().length;
		int updateCount = 0;
		for(String key : orderParam.getId()) {
			
			String[] temp = StringUtils.delimitedListToStringArray(key, OrderItem.ITEM_KEY_DIVISION_STRING);
			
			if (temp.length != 3) {
				continue;
			}
			
			orderParam.setOrderCode(temp[0]);
			orderParam.setOrderSequence(Integer.parseInt(temp[1]));
			orderParam.setItemSequence(Integer.parseInt(temp[2]));
			
			OrderItem orderItem = orderService.getOrderItemByParam(orderParam);
			if (orderItem == null) {
				continue;
			}
			
			ShippingParam shippingParam = null;
			for(ShippingParam param : orderParam.getShippings()) {
 				if (key.equals(param.getKey())) {
					shippingParam = param;
					break;
				}
			}
			
			if (shippingParam == null) {
				continue;
			}

			shippingParam.setOrderCode(orderParam.getOrderCode());
			shippingParam.setOrderSequence(orderParam.getOrderSequence());
			shippingParam.setItemSequence(orderParam.getItemSequence());
			shippingParam.setSellerId(orderParam.getSellerId());
			shippingParam.setAdminUserName(adminName);
			shippingParam.setConditionType(orderParam.getConditionType());

			try {
				orderService.shippingReadyListUpdate(mode, shippingParam, orderItem, orderParam);
				updateCount++;
			} catch (Exception e) {
				log.error("orderService.shippingReadyListUpdate(..) : {}", e.getMessage(), e);
			}
		}
		
		// ?????? ???????????? ???????????? ??????????
		if (totalCount != updateCount) {
			String errorMessage = "????????????????????? ??????????????? ?????? ???????????? ????????????.\n(?????? : " + NumberUtils.formatNumber(totalCount, "#,###") + ", ?????? : " + NumberUtils.formatNumber(updateCount, "#,###") + ")";
			return JsonViewUtils.failure(errorMessage);
		}
		
		return JsonViewUtils.success();
	}
	
	/**
	 * ????????? ??????
	 * @return
	 */
	@GetMapping("shipping")
	public String shipping(@ModelAttribute OrderParam orderParam, Model model) {
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}
		
		HttpSession session = RequestContextUtils.getSession();		
		session.setAttribute("reOrderParam", orderParam);

		orderParam.setAdditionItemFlag("Y");
		List<OrderList> additionList = orderService.getShippingListByParam(orderParam);

		orderParam.setAdditionItemFlag("N");
		List<OrderList> itemList = orderService.getShippingListByParam(orderParam);

		model.addAttribute("list", itemList);
		model.addAttribute("additionList", additionList);
		model.addAttribute("pagination", orderParam.getPagination());
		model.addAttribute("totalCount", orderParam.getPagination().getTotalItems());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return "view:/order/list/shipping";
		
	}
	
	/**
	 * ????????? ?????? ????????? ?????? ??????
	 * @author minae.yun[2017-09-06]
	 * @return
	 */
	@PostMapping("shipping/listUpdate/{mode}")
	public JsonView shippingListUpdate(OrderParam orderParam,
			@PathVariable("mode") String mode, RequestContext requestContext) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}

		int updateCount = 0;
		int totalCount = 0;
		if (orderParam.getId() != null) {
			totalCount = orderParam.getId().length;
		}

		for (String key : orderParam.getId()) {
			String[] temp = StringUtils.delimitedListToStringArray(key, OrderItem.ITEM_KEY_DIVISION_STRING);
		
			if (temp.length != 3) continue;
			
			orderParam.setOrderCode(temp[0]);
			orderParam.setOrderSequence(Integer.parseInt(temp[1]));
			orderParam.setItemSequence(Integer.parseInt(temp[2]));
			
			OrderItem orderItem = orderService.getOrderItemByParam(orderParam);
			
			ShippingParam shippingParam = new ShippingParam();
			shippingParam.setOrderCode(orderItem.getOrderCode());
			shippingParam.setOrderSequence(orderItem.getOrderSequence());
			shippingParam.setItemSequence(orderItem.getItemSequence());
			shippingParam.setDeliveryCompanyId(orderItem.getDeliveryCompanyId());
			shippingParam.setDeliveryCompanyName(orderItem.getDeliveryCompanyName());
			
			try {
				orderService.shippingListUpdate(mode, orderParam, shippingParam);
				updateCount++;
			} catch (Exception e) {
				log.error("ERROR: ????????????????????? ?????? ?????? ??? ??????  {}", e.getMessage(), e);
			}
			
		}

		// ?????? ???????????? ???????????? ??????????
		if (totalCount != updateCount) {
			String errorMessage = "????????????????????? ??????????????? ?????? ???????????? ????????????.\n(?????? : " + NumberUtils.formatNumber(totalCount, "#,###") + ", ?????? : " + NumberUtils.formatNumber(updateCount, "#,###") + ")";
			return JsonViewUtils.failure(errorMessage);
		}
		
		return JsonViewUtils.success();
		
	}
	
	/**
	 * ???????????? ??????
	 * @return
	 */
	@GetMapping("confirm")
	public String confirm(@ModelAttribute OrderParam orderParam, Model model) {
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}

		orderParam.setAdditionItemFlag("Y");
		List<OrderList> additionList = orderService.getConfirmListByParam(orderParam);

		orderParam.setAdditionItemFlag("N");
		List<OrderList> itemList = orderService.getConfirmListByParam(orderParam);
		
		model.addAttribute("list", itemList);
		model.addAttribute("additionList", additionList);
		model.addAttribute("pagination", orderParam.getPagination());
		model.addAttribute("totalCount", orderParam.getPagination().getTotalItems());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return "view:/order/list/confirm";
		
	}

	/**
	 * ???????????? ?????????
	 * @param claimApplyParam
	 * @param model
	 * @return
	 */
	@GetMapping("cancel/list")
	public String cancelList(@ModelAttribute ClaimApplyParam claimApplyParam, Model model) {
		
		claimApplyParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			claimApplyParam.setConditionType("SELLER");
			claimApplyParam.setSellerId(SellerUtils.getSellerId());
		}
		
		model.addAttribute("list", orderClaimApplyService.getCancelListByParam(claimApplyParam));
		model.addAttribute("pagination", claimApplyParam.getPagination());
		model.addAttribute("totalCount", claimApplyParam.getPagination().getTotalItems());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return "view:/order/list/cancel";
	}
	
	/**
	 * ???????????? ?????????
	 * @param claimApplyParam
	 * @param model
	 * @return
	 */
	@GetMapping("return/list")
	public String returnList(@ModelAttribute ClaimApplyParam claimApplyParam, Model model) {
		
		claimApplyParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			claimApplyParam.setConditionType("SELLER");
			claimApplyParam.setSellerId(SellerUtils.getSellerId());
		}
		
		model.addAttribute("list", orderClaimApplyService.getReturnListByParam(claimApplyParam));
		model.addAttribute("pagination", claimApplyParam.getPagination());
		model.addAttribute("totalCount", claimApplyParam.getPagination().getTotalItems());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return "view:/order/list/return";
	}
	
	/**
	 * ???????????? ?????????
	 * @param claimApplyParam
	 * @param model
	 * @return
	 */
	@GetMapping("exchange/list")
	public String exchangeList(@ModelAttribute ClaimApplyParam claimApplyParam, Model model) {
		
		claimApplyParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			claimApplyParam.setConditionType("SELLER");
			claimApplyParam.setSellerId(SellerUtils.getSellerId());
		}
		
		model.addAttribute("list", orderClaimApplyService.getExchangeListByParam(claimApplyParam));
		model.addAttribute("pagination", claimApplyParam.getPagination());
		model.addAttribute("totalCount", claimApplyParam.getPagination().getTotalItems());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return "view:/order/list/exchange";
	}
	
	/**
	 * ?????? ?????? ?????????
	 * @param param
	 * @param model
	 * @return
	 */
	@GetMapping("refund/list")
	public String refund(@ModelAttribute OrderRefundParam param, Model model) {
		
		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("list", orderRefundService.getOrderRefundListByParam(param));
		model.addAttribute("pagination", param.getPagination());
		model.addAttribute("totalCount", param.getPagination().getTotalItems());
		
		return "view:/order/list/refund";
	}
	
	/**
	 * ?????? ?????? ?????????
	 * @param refundCode
	 * @param model
	 * @return
	 */
	@GetMapping("refund/detail/{refundCode}")
	public String refundDetail(@PathVariable("refundCode") String refundCode, Model model) {
		
		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}
		
		OrderRefund refund = orderRefundService.getOrderRefundByCode(refundCode);
		if (refund == null) {
			throw new PageNotFoundException();
		}
		
		if (refund.getUserId() > 0) {
			AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(refund.getUserId(), PointUtils.DEFAULT_POINT_CODE);
			model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		}

		ConfigPg configPg = configPgService.getConfigPg();
		String pgType = "";

		if (configPg != null) {
			pgType = configPg.getPgType().getCode().toLowerCase();
		} else {
			pgType = SalesonProperty.getPgService();
		}

		if ("nicepay".equals(pgType)) {

			String partCancel = "0";
			int payTotal = 0;
			OrderPgData orderPgData = null;

			for(OrderPayment orderPayment : refund.getOrderPayments()) {
				payTotal += orderPayment.getRemainingAmount();

				if (orderPayment.getOrderPgData().getOrderPgDataId() > 0) {
					orderPgData = orderPayment.getOrderPgData();
				}
			}

			if (refund.getTotalReturnAmount() != payTotal) {
				partCancel = "1";
			}

			model.addAttribute("partCancel", partCancel);
			model.addAttribute("nicepayPgData", orderPgData);
		}

		model.addAttribute("bankListByKey", ShopUtils.getBankListByKey(pgType));
		model.addAttribute("refund", refund);
		return "view:/order/detail/refund";
	}
	
	/**
	 * ?????? ??????
	 * @param refundCode
     * @param  editPayment
	 * @param model
	 * @return
	 */
	@PostMapping("refund/detail/{refundCode}")
	public String refundProcess(@PathVariable("refundCode") String refundCode, EditPayment editPayment, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}
		
		OrderRefund refund = orderRefundService.getOrderRefundByCode(refundCode);
		
		if (refund == null) {
			throw new PageNotFoundException();
		}
		
		try {

			editPayment.setRequest(request);
			editPayment.setResponse(response);
			orderRefundService.orderRefundProcess(refund, editPayment);

            OrderParam orderParam = new OrderParam();
            orderParam.setOrderCode(editPayment.getOrderCode());
            orderParam.setOrderSequence(editPayment.getOrderSequence());
            orderParam.setConditionType("OPMANAGER");
            orderParam.setUserId(refund.getUserId());

            // ??????????????? ??????,?????????
            if (editPayment.isCashbillReissueFlag()) {
                receiptService.cashbillReIssue(orderParam);
            }
			
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return ViewUtils.redirect("/opmanager/order/refund/detail/" + refundCode, e.getMessage());
		}
		
		return ViewUtils.redirect("/opmanager/order/refund/detail/" + refundCode, "?????? ???????????????.");
	}
	
	/**
	 * ?????? ?????? ??????
	 * @param requestContext
	 * @param refundCode
	 * @return
	 */
	@PostMapping("refund/cancel/{refundCode}")
	public JsonView cancelRefund(RequestContext requestContext,
								 @PathVariable("refundCode") String refundCode) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		try {
			orderRefundService.cancelRefund(refundCode);
			return JsonViewUtils.success();
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.failure("?????? ?????? ?????? ??? ????????? ?????????????????????");
		}
	}

	/**
	 * ???????????? ????????????
	 * @param orderSequence
	 * @param itemSequence
	 * @param orderCode
	 * @param orderParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value="/shipping/change-shipping-number/{orderSequence}/{itemSequence}/{orderCode}")
	public String changeShippingNumber(@PathVariable("orderSequence") int orderSequence, 
			@PathVariable("itemSequence") int itemSequence,
			@PathVariable("orderCode") String orderCode,
			OrderParam orderParam, Model model) {
		
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
		}
		
		orderParam.setOrderCode(orderCode);
		orderParam.setOrderSequence(orderSequence);
		orderParam.setItemSequence(itemSequence);
		OrderItem orderItem = orderService.getOrderItemByParam(orderParam);
		if (orderItem == null) {
			throw new PageNotFoundException();
		}

		orderParam.setAdditionItemFlag("Y");
		orderParam.setOrderSequence(-1);
		List<OrderItem> additionList = orderService.getOrderItemListByParam(orderParam);
		
		model.addAttribute("deliveryCompanyList", deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
		model.addAttribute("orderItem", orderItem);
		model.addAttribute("additionList", additionList);
		return "view:/order/popup/change-shipping-number";
	}

	/**
	 * ???????????? ???????????? ??????
	 * @param orderSequence
	 * @param itemSequence
	 * @param orderCode
	 * @param shippingParam
	 * @return
	 */
	@PostMapping("/shipping/change-shipping-number/{orderSequence}/{itemSequence}/{orderCode}")
	public String changeShippingNumberProcess(@PathVariable("orderSequence") int orderSequence, 
			@PathVariable("itemSequence") int itemSequence,
			@PathVariable("orderCode") String orderCode, ShippingParam shippingParam) {

		String redirectUrl = "/opmanager";
		if (ShopUtils.isSellerPage()) {
			redirectUrl = "/seller";
			shippingParam.setSellerId(SellerUtils.getSellerId());
		}
		
		redirectUrl += "/order/shipping/change-shipping-number/" + orderSequence + "/" + itemSequence + "/" + orderCode;
		
		shippingParam.setOrderCode(orderCode);
		shippingParam.setOrderSequence(orderSequence);
		shippingParam.setItemSequence(itemSequence);
		
		try {
			orderService.changeShippingNumber(shippingParam);

			OrderParam orderParam = new OrderParam();
			orderParam.setConditionType("OPMANAGER");
			if (ShopUtils.isSellerPage()) {
				orderParam.setSellerId(SellerUtils.getSellerId());
			}
			orderParam.setOrderCode(orderCode);
			orderParam.setAdditionItemFlag("Y");
			orderParam.setOrderSequence(-1);

			List<OrderItem> additionList = orderService.getOrderItemListByParam(orderParam);

			// ????????????????????? ????????? ???????????? ????????????
			additionList.stream().forEach(list -> {
				shippingParam.setOrderSequence(list.getOrderSequence());
				shippingParam.setItemSequence(list.getItemSequence());
				orderService.changeShippingNumber(shippingParam);
			});

			return ViewUtils.redirect(redirectUrl, "?????????????????????.", "opener.location.reload()");
		} catch(OrderException oe) {
			return ViewUtils.redirect(redirectUrl, oe.getMessage(), "opener.location.reload()");
		}
	}
	
	/**
	 * ??????????????? ????????????
	 * @return
	 */
	@GetMapping("{pageType}/success-popup-close")
	public @ResponseBody String popupClose() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");

		//sb.append("alert(\"?????? ?????????????????????.\");");
		sb.append("opener.location.reload();");
		sb.append("self.close();");
		
		sb.append("</script>");
		return sb.toString();
	}
	
	@GetMapping("waiting-deposit/order-excel-download")
	public ModelAndView WaitingDepositExcelDownload(OrderParam orderParam, HttpServletRequest request) {

		// ?????? ????????? ?????? ??????
		if (!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("?????? ???????????? ????????? ????????????.");
			// ?????? ???????????? + ???????????? ????????? ?????? ??????
		} else if(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS")){
			// excelReadingLogService.insertExcelReadingUserLog(request,ExcelReadingLog.EXCEL_TYPE_WAITING_DEPOSIT,"");
		}

		ModelAndView mav = new ModelAndView(new OrderWaitingDetailExcelView());
		orderParam.setConditionType("ORDER-DETAIL-EXCEL");
		
		if (orderParam.getId() != null) {
		
			List<String> orderCodes = new ArrayList<>();
	
			for(String id : orderParam.getId()) {
				
				String[] temp = StringUtils.delimitedListToStringArray(id, OrderPayment.PAYMENT_KEY_DIVISION_STRING);
				
				if (temp.length != 3) {
					continue;
				}

				orderCodes.add(temp[0]);
				
			}
			
			orderParam.setOrderCodes(orderCodes);
		}

		mav.addObject("orderWaitingdetailList", orderService.getWaitingDepositListByParam(orderParam));
		
		return mav;
		
	}
	
	@GetMapping("new-order/order-excel-download")
	public ModelAndView NewOrderExcelDownload(OrderParam orderParam, HttpServletRequest request) {

		// ?????? ????????? ?????? ??????
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("?????? ???????????? ????????? ????????????.");
			// ?????? ???????????? + ???????????? ????????? ?????? ??????
		} else if(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS")){
			// excelReadingLogService.insertExcelReadingUserLog(request,ExcelReadingLog.EXCEL_TYPE_NEW_ORDER,"");
		}

		ModelAndView mav = new ModelAndView(new OrderDetailExcelView());
		
		// HttpSession session = RequestContextUtils.getSession();
		// OrderParam orderParam = (OrderParam)session.getAttribute("reOrderParam");

		orderParam.setConditionType("OPMANAGER");

		if (orderParam.getId() != null) {
			orderParam.setConditionType("ORDER-DETAIL-EXCEL");
		} else if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}
		
		if (orderParam.getId() != null) {
		
			List<OrderItem> orderItems = new ArrayList<>();
	
			for(String id : orderParam.getId()) {
				
				String[] temp = StringUtils.delimitedListToStringArray(id, OrderItem.ITEM_KEY_DIVISION_STRING);
				
				if (temp.length != 3) {
					continue;
				}

				OrderItem orderItem = new OrderItem();
				orderItem.setOrderCode(temp[0]);
				orderItem.setItemSequence(Integer.parseInt(temp[2]));

				orderItems.add(orderItem);
				
			}
			
			orderParam.setOrderItems(orderItems);
		}

		orderParam.setAdditionItemFlag("Y");
		List<OrderList> additionList = orderService.getNewOrderListByParam(orderParam);

		orderParam.setAdditionItemFlag("N");
		List<OrderList> orderDetailList = orderService.getNewOrderListByParam(orderParam);

		mav.addObject("orderDetailList", orderDetailList);
		mav.addObject("additionList", additionList);
		mav.addObject("deliveryCompanyList", deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
		
		return mav;
		
	}
	
	@GetMapping("shipping-ready/order-excel-download")
	public ModelAndView shippingReadyExcelDownload(OrderParam orderParam, HttpServletRequest request) {

		// ?????? ????????? ?????? ??????
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("?????? ???????????? ????????? ????????????.");
			// ?????? ???????????? + ???????????? ????????? ?????? ??????
		} else if(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS")){
			// excelReadingLogService.insertExcelReadingUserLog(request,ExcelReadingLog.EXCEL_TYPE_SHIPPING_READY,"");
		}

		ModelAndView mav = new ModelAndView(new OrderDetailExcelView());
		
		// HttpSession session = RequestContextUtils.getSession();
		// OrderParam orderParam = (OrderParam)session.getAttribute("reOrderParam");

		orderParam.setConditionType("OPMANAGER");

		if (orderParam.getId() != null) {
			orderParam.setConditionType("ORDER-DETAIL-EXCEL");
		} else if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}
		
		if (orderParam.getId() != null) {
		
			List<OrderItem> orderItems = new ArrayList<>();
	
			for(String id : orderParam.getId()) {
				
				String[] temp = StringUtils.delimitedListToStringArray(id, OrderItem.ITEM_KEY_DIVISION_STRING);
				
				if (temp.length != 3) {
					continue;
				}

				OrderItem orderItem = new OrderItem();
				orderItem.setOrderCode(temp[0]);
				orderItem.setItemSequence(Integer.parseInt(temp[2]));

				orderItems.add(orderItem);
				
			}
			
			orderParam.setOrderItems(orderItems);
		}

		orderParam.setAdditionItemFlag("Y");
		List<OrderList> additionList = orderService.getShippingReadyListByParam(orderParam);

		orderParam.setAdditionItemFlag("N");
		List<OrderList> orderDetailList = orderService.getShippingReadyListByParam(orderParam);

		mav.addObject("orderDetailList", orderDetailList);
		mav.addObject("additionList", additionList);
		mav.addObject("deliveryCompanyList", deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
		return mav;
		
	}
	
	@GetMapping("shipping/order-excel-download")
	public ModelAndView ShippingExcelDownload(OrderParam orderParam, HttpServletRequest request) {

		// ?????? ????????? ?????? ??????
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("?????? ???????????? ????????? ????????????.");
			// ?????? ???????????? + ???????????? ????????? ?????? ??????
		} else if(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS")){
			// excelReadingLogService.insertExcelReadingUserLog(request,ExcelReadingLog.EXCEL_TYPE_SHIPPING,"");
		}

		ModelAndView mav = new ModelAndView(new ShippingDetailExcelView());
		
		// HttpSession session = RequestContextUtils.getSession();
		// OrderParam orderParam = (OrderParam)session.getAttribute("reOrderParam");

		orderParam.setConditionType("OPMANAGER");

		if (orderParam.getId() != null) {
			orderParam.setConditionType("ORDER-DETAIL-EXCEL");
		} else if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}
		
		if (orderParam.getId() != null) {
		
			List<OrderItem> orderItems = new ArrayList<>();
	
			for(String id : orderParam.getId()) {
				
				String[] temp = StringUtils.delimitedListToStringArray(id, OrderItem.ITEM_KEY_DIVISION_STRING);
				
				if (temp.length != 3) {
					continue;
				}

				OrderItem orderItem = new OrderItem();
				orderItem.setOrderCode(temp[0]);
				orderItem.setItemSequence(Integer.parseInt(temp[2]));

				orderItems.add(orderItem);
				
			}
			
			orderParam.setOrderItems(orderItems);
		}

		orderParam.setAdditionItemFlag("Y");
		List<OrderList> additionList = orderService.getShippingListByParam(orderParam);

		orderParam.setAdditionItemFlag("N");
		List<OrderList> ShippingOrderDetailList = orderService.getShippingListByParam(orderParam);

		mav.addObject("ShippingOrderDetailList", ShippingOrderDetailList);
		mav.addObject("additionList", additionList);

		return mav;
		
	}
	
	/**
	 * ?????? ?????????
	 * @author minae.yun[2017-09-05]
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("/upload-excel")
	public String shippingReadyExcelUpload(Model model) {  
        
		if (RedirectAttributeUtils.hasRedirectAttributes()) {
			model.addAttribute("result", RedirectAttributeUtils.get("result"));
		}	
        return "view";  
	}
	

	/**	 
	 * ??????????????? ???????????? ?????????????????? ???????????? ??????
	 * @author minae.yun[2017-09-05]
	 * @param orderParam
	 * @param multipartFile
	 * @param model
	 * @param redirectAttribute
	 * @return
	 */
	@RequestProperty(layout="base")
	@PostMapping("/upload-excel")
	public String shippingReadyExcelUpload(OrderParam orderParam, @RequestParam(value="file", required=false) MultipartFile multipartFile
			, Model model, RedirectAttributes redirectAttribute) {
		
		//???????????? ?????????, ??????????????? ???????????? shippingReadyListUpdate??? ???????????? ????????????
		Map<String, Object> resp = orderService.shippingReadyExcelUpload(orderParam, multipartFile);
		String result = (String) resp.get("result");

		// ????????? ????????? ??????
		List<String> keyList = (List<String>) resp.get("key");
		if (keyList != null) {
			orderService.sendOrderDeliveryMessageByExcelUpload(keyList);
		}

		model.addAttribute("result", result);
		RedirectAttributeUtils.addAttribute("result", result);

		String redirectUrl = "/opmanager";
		if (ShopUtils.isSellerPage()) {
			redirectUrl = "/seller";
		}

		return ViewUtils.redirect(redirectUrl+"/order/upload-excel");
	}
	
	/**
	 * ?????? ?????? ??????
	 * @param pageType
	 * @param orderSequence
	 * @param orderCode
	 * @param model
	 * @return
	 */
	@GetMapping("{pageType}/change-pay/{orderSequence}/{orderCode}")
	@RequestProperty(layout="base")
	public String payChange(@PathVariable("pageType") String pageType, 
			@PathVariable("orderSequence") int orderSequence,
			@PathVariable("orderCode") String orderCode , Model model) {
		
		
		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(orderCode);
		orderParam.setOrderSequence(orderSequence);
		orderParam.setPayChangeType("Y");
		
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}
		
		Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			throw new PageNotFoundException();
		}
		
		if (order.getUserId() > 0) {
			AvailablePoint avilablePoint = pointService.getAvailablePointByUserId(order.getUserId(), PointUtils.DEFAULT_POINT_CODE);
			model.addAttribute("avilablePoint", avilablePoint.getAvailablePoint());
		}

		ConfigPg configPg = configPgService.getConfigPg();
		String pgType = "";

		if (configPg != null) {
			pgType = configPg.getPgType().getCode().toLowerCase();
		} else {
			pgType = SalesonProperty.getPgService();
		}

		model.addAttribute("bankListByKey", ShopUtils.getBankListByKey(pgType));

		model.addAttribute("pageType", pageType);
		model.addAttribute("order", order);
		
		return "view:/order/popup/change-pay";
	}

    /**
     * ?????? ?????? ?????? ??????
     * @param pageType
     * @param editPayment
     * @return
     */
	@PostMapping("{pageType}/change-pay/process")
	public String payChangeProcess(@PathVariable("pageType") String pageType, 
			EditPayment editPayment) {

		try {
			orderService.changePayment(editPayment);

            OrderParam orderParam = new OrderParam();
            orderParam.setOrderCode(editPayment.getOrderCode());
            orderParam.setOrderSequence(editPayment.getOrderSequence());
            orderParam.setConditionType("OPMANAGER");
            orderParam.setUserId(editPayment.getUserId());

            // ??????????????? ??????,?????????
            if (editPayment.isCashbillReissueFlag()) {
                receiptService.cashbillReIssue(orderParam);
            }
		} catch (Exception e) {
			log.error("orderService.changePayment(..) : {}", e.getMessage(), e);
			return ViewUtils.redirect("/opmanager/order/"+pageType+"/change-pay/"+ editPayment.getOrderSequence() +"/" + editPayment.getOrderCode(), e.getMessage());
		}
		
		return ViewUtils.redirect("/opmanager/order/"+pageType+"/change-pay/"+ editPayment.getOrderSequence() +"/" + editPayment.getOrderCode(), "?????????????????????.", "opener.location.reload()");
	}
	
	/**
	 * ????????? ???????????? ?????? ??????
	 * @param model
	 * @param orderAdminParam
	 * @return
	 */
	@GetMapping("admin/list")
	public String orderAdminList(Model model, OrderAdminParam orderAdminParam) {  
		
		model.addAttribute("list", orderAdminService.getOrderAdminListByParam(orderAdminParam));
		model.addAttribute("pagination", orderAdminParam.getPagination());
		model.addAttribute("totalCount", orderAdminParam.getPagination().getTotalItems());
		
		return "view:/order/admin/list";
	}
	

	/**
	 * ????????? ???????????? ?????? ??????
	 * @param model
	 * @param orderAdminParam
	 * @return
	 */
	@PostMapping("admin/list")
	public String orderAdminListProcess(Model model, OrderAdminParam orderAdminParam, HttpServletRequest request) {  
		
		try {
		orderAdminService.insertOrderAdmin(orderAdminParam, request);
		} catch (OrderAdminException oae) {
			log.debug(oae.getMessage(), oae);
			return ViewUtils.redirect("/opmanager/order/admin/list", oae.getMessage());
		}
		
		return ViewUtils.redirect("/opmanager/order/admin/list", "?????? ???????????????.");
	}
	
	/**
	 * ?????? ????????? 
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("/admin/upload-excel")
	public String uploadExcel(Model model) {  
        
		if (RedirectAttributeUtils.hasRedirectAttributes()) {
			model.addAttribute("result", RedirectAttributeUtils.get("result"));
		}	
		
        return "view:/order/admin/upload-excel";  
	}

	/**
	 * ?????? ????????? ??????.
	 * @param multipartFile
	 * @param model
	 * @param redirectAttribute
	 * @return
	 */
	@RequestProperty(layout="base")
	@PostMapping("/admin/upload-excel")
	public String uploadExcelProcess(@RequestParam(value="file", required=false) MultipartFile multipartFile
			, Model model, RedirectAttributes redirectAttribute) {  
		
		String result = orderAdminService.insertExcelData("v01", multipartFile);

		model.addAttribute("result", result);
		RedirectAttributeUtils.addAttribute("result", result);
        return ViewUtils.redirect("/opmanager/order/admin/upload-excel");
	}
	
	/**
	 * ????????? ????????? ??????
	 * @param pageType
	 * @param adminClaimApply
	 * @return
	 */
	@PostMapping("{pageType}/admin-claim-apply")
	public String adminClaimApply(@PathVariable("pageType") String pageType, AdminClaimApply adminClaimApply) {
		
		String redirectUrl = "/opmanager";
		if (ShopUtils.isSellerPage()) {
			redirectUrl = "/seller";
		}
		
		orderService.adminClaimApply(adminClaimApply);
		
		// ??????????????? ?????? ?????? ???????????? ?????????
		if ("1".equals(adminClaimApply.getClaimType())) { // ??????
			OrderCancelApply apply = adminClaimApply.getOrderCancelApply();
			if ("Y".equals(adminClaimApply.getRefundFlag())) {
				
				List<OrderCancelApply> list = orderClaimApplyService.getAdminApplyCancelListByIds(adminClaimApply.getAdminClaimApplyKey());
				if (list != null) {
					
					ClaimApply claimApply = new ClaimApply();
					
					OrderRefundParam orderRefundParam = new OrderRefundParam();
					orderRefundParam.setOrderCode(adminClaimApply.getOrderCode());
					orderRefundParam.setOrderSequence(adminClaimApply.getOrderSequence());
					
					String refundCode = orderRefundService.getActiveRefundCodeByParam(orderRefundParam);
					if (StringUtils.isEmpty(refundCode)) {
						throw new OrderException();
					}
				
					claimApply.setRefundCode(refundCode);
					HashMap<String, OrderCancelApply> cancelApplyMap = new HashMap<String, OrderCancelApply>();
					HashMap<String, OrderCancelShipping> cancelShippingMap = new HashMap<String, OrderCancelShipping>();
					List<String> keyList = new ArrayList<>();
					for(OrderCancelApply item : list) {
						
						item.setClaimStatus("03");
						OrderCancelShipping cancelShipping = new OrderCancelShipping();
						
						cancelShipping.setShippingSequence(item.getShippingSequence());
						cancelShipping.setRePayShipping("Y");
						
						keyList.add(item.getClaimCode());
						cancelApplyMap.put(item.getClaimCode(), item);
						cancelShippingMap.put(Integer.toString(item.getShippingSequence()), cancelShipping);
					}
					
					String[] cancelIds = new String[keyList.size()];
					for(int i = 0; i < keyList.size(); i++) {
						cancelIds[i] = keyList.get(i);
					}
					
					
					claimApply.setOrderCode(adminClaimApply.getOrderCode());
					claimApply.setOrderSequence(adminClaimApply.getOrderSequence());
					claimApply.setCancelShippingMap(cancelShippingMap);
					claimApply.setCancelApplyMap(cancelApplyMap);
					claimApply.setCancelIds(cancelIds);
					orderClaimApplyService.orderCancelProcess(claimApply);
				}
				
			}
		}
		
		redirectUrl += "/order/" + pageType + "/order-detail/" + adminClaimApply.getOrderSequence() + "/" + adminClaimApply.getOrderCode();
		return ViewUtils.redirect(redirectUrl, "?????? ???????????????.");
	}

}
