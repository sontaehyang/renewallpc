package saleson.shop.order;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.api.common.enumerated.ApiError;
import saleson.common.asp28.analytics.Asp28Analytics;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.OrderCodePrefix;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.ConfigPg;
import saleson.model.OrderCancelFail;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.order.domain.*;
import saleson.shop.order.log.OrderLogService;
import saleson.shop.order.pg.PgService;
import saleson.shop.order.pg.cj.domain.CjResult;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.pg.kcp.domain.KcpRequest;
import saleson.shop.order.pg.payco.domain.ReservationResponse;
import saleson.shop.order.support.OrderException;
import saleson.shop.order.support.OrderParam;
import saleson.shop.orderCancelFail.OrderCancelFailService;
import saleson.shop.policy.PolicyService;
import saleson.shop.policy.domain.Policy;
import saleson.shop.userdelivery.UserDeliveryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/m/order/**"})
@RequestProperty(template="mobile", layout="default")
public class OrderMobileController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderMobileController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserDeliveryService userDeliveryService;
	
	@Autowired
	private Environment environment;

	@Autowired
	private ConfigService configService;

	@Autowired
	@Qualifier("cjService")
	private PgService cjService;
	
	@Autowired
	@Qualifier("kcpService")
	private PgService kcpService;

    @Autowired
    private EnumMapper enumMapper;

    @Autowired
    private ConfigPgService configPgService;

	@Autowired
	private OrderLogService orderLogService;

    @Autowired
    private OrderCancelFailService orderCancelFailService;

	@Autowired
	private PolicyService policyService;

	@GetMapping("/multiple-delivery")
	@RequestProperty(layout="base")
	public String multipleDelivery(RequestContext requestContext, OrderParam orderParam,
			Model model, HttpSession session, @RequestParam(value="count", required=false, defaultValue="0") int setCount) {
		
		long userId = 0;
		if (UserUtils.isUserLogin()) {
			userId = UserUtils.getUserId();
			
			model.addAttribute("userDeliveryList", userDeliveryService.getUserDeliveryList(userId));
		}
		
		orderParam.setSessionId(session.getId());
		orderParam.setUserId(userId);
		
		Buy buy = orderService.getBuyForStep1(orderParam);
		if (buy.getMultipleDeliveryCount() <= 1) {
			throw new PageNotFoundException();
		}
		
		if (setCount == 0) {
			setCount = 2;
		}
		
		model.addAttribute("setCount", setCount);
		model.addAttribute("buy", buy);
		return "view:/order/multiple-delivery";
	}

	/**
	 * 주문서 작성
	 * @param requestContext
	 * @param orderParam
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/step1")
	public String step1(RequestContext requestContext, OrderParam orderParam, 
			HttpSession session, Model model) {

		orderLogService.put("");
		
		HashMap<String, BuyPayment> buyPayments = orderService.getPaymentType();
		if (buyPayments.keySet().isEmpty()) {
			throw new OrderException(MessageUtils.getMessage("M01256"), "/");
		}
		
		Config shopConfig = configService.getShopConfig(Config.SHOP_CONFIG_ID);
		
		long userId = 0;
		if (UserUtils.isUserLogin()) {
			userId = UserUtils.getUserId();
			model.addAttribute("userDeliveryList", userDeliveryService.getUserDeliveryList(userId));
		}
		
		orderParam.setSessionId(session.getId());
		orderParam.setUserId(userId);
		
		Buy buy = orderService.getBuyForStep1(orderParam);

		orderLogService.put(buy.getOrderCode());

		buy.setBuyPayments(buyPayments);

		ConfigPg configPg = configPgService.getConfigPg();
		String pgService = "";
		String autoCashReceipt = "";
		String useEscrow = "";

		if (configPg != null) {
			pgService = configPg.getPgType().getCode().toLowerCase();
			autoCashReceipt = configPg.isUseAutoCashReceipt() ? "Y" : "N";
			useEscrow = configPg.isUseEscroow() ? "Y" : "N";
		} else {
			pgService = SalesonProperty.getPgService();
			autoCashReceipt = environment.getProperty("pg.autoCashReceipt");
			useEscrow = environment.getProperty("pg.useEscrow");
		}
		
		model.addAttribute("nowYear", DateUtils.getToday("yyyy"));
		model.addAttribute("buy", buy);
		model.addAttribute("shopConfig", shopConfig);
		model.addAttribute("asp28Analytics", new Asp28Analytics(buy, "Web", "Order"));
		model.addAttribute("useCoupon", ShopUtils.useCoupon());
		model.addAttribute("userData", buy.getUserData());
        model.addAttribute("cashbillTypes", enumMapper.get("CashbillType"));
		model.addAttribute("pgService", pgService);
		model.addAttribute("autoCashReceipt", autoCashReceipt);
		model.addAttribute("useEscrow", useEscrow);

		return ViewUtils.view();
	}
	
	/**
	 * 주문정보 임시 저장
	 * @param requestContext
	 * @param session
	 * @return
	 */
	@PostMapping("save")
	public JsonView save(RequestContext requestContext, HttpServletRequest request, 
			HttpSession session, Buy buy) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		long userId = 0;
		if (UserUtils.isUserLogin()) {
			userId = UserUtils.getUserId();
		}
		
		try {
			buy.setOrderCode(orderService.getNewOrderCode(OrderCodePrefix.MOBILE));
			buy.setUserId(userId);
			buy.setSessionId(session.getId());
			buy.setDeviceType("MOBILE");
			buy.setRealDeviceType(DeviceUtils.resolveDevice(request));

			orderLogService.put(buy.getOrderCode());

			return JsonViewUtils.success(orderService.saveOrderTemp(session, buy));
		} catch(Exception e){
			log.error("ERROR: {}", e.getMessage(), e);
			return JsonViewUtils.failure(e.getMessage());
		}
		
	}

	/**
	 * 결제
	 * @param request
	 * @param response
	 * @param session
	 * @param orderParam
	 * @param pgData
	 * @return
	 */
	@PostMapping("pay")
	public String pay(HttpServletRequest request, HttpServletResponse response, HttpSession session,
					  OrderParam orderParam, PgData pgData) {
		
		pgData.setRequest(request);
		pgData.setResponse(response);
		
		Enumeration params = request.getParameterNames(); 
		while(params.hasMoreElements()){
			String paramName = (String)params.nextElement();
			System.out.println("Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		}
		
		orderParam.setUserId(UserUtils.getUserId());
		orderParam.setSessionId(session.getId());
		
		if (UserUtils.isUserLogin() == false && orderParam.getUserId() == 0L) {
			orderParam.setUserId(0);
		}

		String orderCode = null;
		try {

			orderLogService.put(orderParam.getOrderCode());

			orderCode = orderService.insertOrder(orderParam, pgData, session, request);

			System.out.println("insert order");
		} catch (Exception e) {
			log.error("[ORDER-MOBILE-ERROR] 주문(결제) 처리 시 오류 발생 : {}", orderParam.getOrderCode(), e);

			throw new UserException(e.getMessage(), "/m/order/step1");
		}

		return ViewUtils.redirect("/m/order/step3/" + orderCode);
	}


	/**
	 * 주문 복구
	 * @param orderParam
	 * @param pgData
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("recovery")
	public String recovery(OrderParam orderParam, PgData pgData, HttpSession session,
					  HttpServletRequest request) {
		orderParam.setPayMode("RECOVERY");



		pgData.setRequest(request);

		Enumeration params = request.getParameterNames();
		while(params.hasMoreElements()){
			String paramName = (String)params.nextElement();
			System.out.println("Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		}




		if(StringUtils.isEmpty(orderParam.getOrderCode())){
			orderParam.setOrderCode(request.getParameter("sp_order_no"));
		}

		if (UserUtils.isUserLogin() == false && orderParam.getUserId() == 0L) {
			orderParam.setUserId(0);
		}

		String orderCode = null;
		try {
			orderCode = orderService.insertOrder(orderParam, pgData, session, request);

			System.out.println("insert order");
		} catch (Exception e) {
			log.error("[ORDER-MOBILE-ERROR] 주문(결제) 처리 시 오류 발생 : {}", orderParam.getOrderCode(), e);

			return e.getMessage();
		}

		return "OK";
	}

	/**
	 * 결제 완료 페이지
	 * @param orderCode
	 * @param model
	 * @return
	 */
	@GetMapping("step3/{orderSequence}/{orderCode}")
	public String step3(@PathVariable("orderSequence") int orderSequence, 
			@PathVariable("orderCode") String orderCode, Model model,
						HttpServletRequest request) {

		orderLogService.put(orderCode);

		// CJH 2016.12.23 orderSequence가 99로 넘어오는경우 비동기 결제 방식인경우 사용한다. (EX. 이니시스 모바일 실시간 계좌 이체)
		if (orderSequence != 99) {
			OrderParam orderParam = new OrderParam();
			
			orderParam.setOrderCode(orderCode);
			orderParam.setUserId(UserUtils.getUserId());
			
			if (UserUtils.isUserLogin() == false) {
				orderParam.setUserId(0);
			}
			
			orderParam.setOrderSequence(orderSequence);
			orderParam.setOrderCode(orderCode);
			
			Order order = orderService.getOrderByParam(orderParam);
			if (order == null) {
				throw new OrderException("주문정보가 없습니다.", "/");
			}

			// 상품단위로 처리되는 기존 로직을 유지하기 위해 orderItems에 추가한 추가구성상품 삭제
			for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
				List<OrderItem> itemList = orderShippingInfo.getOrderItems()
						.stream().filter(l -> "N".equals(l.getAdditionItemFlag())).collect(Collectors.toList());

				orderShippingInfo.getOrderItems().clear();
				orderShippingInfo.getOrderItems().addAll(itemList);
			}

			model.addAttribute("user", UserUtils.getUser());
			model.addAttribute("order", order);

			model.addAttribute("orderItemUserCodes", JsonViewUtils.objectToJson(order.getOrderItemUserCodes()));
		}
		
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("orderSequence", orderSequence);

		return ViewUtils.getView("/order/step3");
	}
	
	/**
	 * 엘지 데이콤 결제값 받기 - 최종 승인전
	 * @param model
	 * @param LGD_RESPCODE
	 * @param LGD_RESPMSG
	 * @param LGD_PAYKEY
	 * @return
	 */
	@GetMapping(value="/lgdacom/return-url/{orderCode}")
	@RequestProperty(layout="base")
	public String lgdacomReturnUrl(Model model,
			@PathVariable("orderCode") String orderCode,
			@RequestParam("LGD_RESPCODE") String LGD_RESPCODE,
			@RequestParam("LGD_RESPMSG") String LGD_RESPMSG,
			@RequestParam(value="LGD_PAYKEY", required=false, defaultValue="") String LGD_PAYKEY) {

		model.addAttribute("orderCode", orderCode);
		model.addAttribute("LGD_RESPCODE", LGD_RESPCODE);
		model.addAttribute("LGD_RESPMSG", LGD_RESPMSG);
		model.addAttribute("LGD_PAYKEY", LGD_PAYKEY);
		return ViewUtils.getView("/order/lgdacom/return-url");
	}

	@PostMapping(value="/lgdacom/return-url/{orderCode}")
	@RequestProperty(layout="base")
	public String lgdacomReturnUrl2(Model model,
								   @PathVariable("orderCode") String orderCode,
								   @RequestParam("LGD_RESPCODE") String LGD_RESPCODE,
								   @RequestParam("LGD_RESPMSG") String LGD_RESPMSG,
								   @RequestParam(value="LGD_PAYKEY", required=false, defaultValue="") String LGD_PAYKEY) {

		model.addAttribute("orderCode", orderCode);
		model.addAttribute("LGD_RESPCODE", LGD_RESPCODE);
		model.addAttribute("LGD_RESPMSG", LGD_RESPMSG);
		model.addAttribute("LGD_PAYKEY", LGD_PAYKEY);
		return ViewUtils.getView("/order/lgdacom/return-url");
	}
	
	
	/**
	 * CJ 결제 승인처리
	 * 카드 결제 일때는 화면 분기용..
	 * 카드 결제가 아닐때는 DB처리용.. 
	 * @param cjResult
	 * @param session
	 * @return
	 */
	@GetMapping(value="/cj/redirect-url")
	public String cjRedirectUrl(CjResult cjResult, HttpSession session, HttpServletRequest request) {

		return handleCjResponse(cjResult, session, request);
	}

	@PostMapping(value="/cj/redirect-url")
	public String cjRedirectUrl2(CjResult cjResult, HttpSession session, HttpServletRequest request) {

		return handleCjResponse(cjResult, session, request);
	}

	private String handleCjResponse(CjResult cjResult, HttpSession session, HttpServletRequest request) {
		if (cjResult == null || StringUtils.isEmpty(cjResult.getCJSResultCode())) {
			throw new PageNotFoundException();
		}

		orderLogService.put("");

		// CJ에서 리턴되는 값을 확인할 상황이 있어서 해당내용은 주석으로 남겨놓았음.. CJH 2015.11.20
		//Enumeration params = request.getParameterNames();
		//while(params.hasMoreElements()){
		// String paramName = (String)params.nextElement();
		// System.out.println("Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		//}

		if ("sucess".equals(cjResult.getCJSResultCode()) || "0000".equals(cjResult.getCJSResultCode())) {
			OrderParam orderParam = new OrderParam();
			orderParam.setUserId(UserUtils.getUserId());
			if (UserUtils.isUserLogin() == false) {
				orderParam.setUserId(0);
			}

			// CallBack에는 카드결제 관련 파라미터가 정리되었지만 여기서는 안되었다... 어느순간 바뀔지도...
			// 카드결제, 계좌이체는 CallbackUrl에서 DB 처리한다.. 근데 다른 결제 수단은 CallbackUrl을 사용안한다... 뭐이럼...
			if ("CRD".equals(cjResult.getPayType()) || "Account".equals(cjResult.getCJSPayMethod())) {

				// 카드결제 인경우 넘어오는 데이터가 서로 다르다... 초기화 해주자
				if ("CRD".equals(cjResult.getPayType())) {
					// CJ에서 데이터 전달해줄때 이상한 문자열이 붙어서 오내? 뭐지...
					String orderCode = cjResult.getOrderCode();

					orderCode = orderCode == null ? "" : orderCode.replaceAll(";", "");

					orderLogService.put(orderCode);

					cjResult.setCJSShopOrderNo(orderCode);
					cjResult.setCJSAmountTotal(cjResult.getTotalPrice());
					cjResult.setCJSShopID(cjResult.getCid());
				}

				orderParam.setOrderCode(cjResult.getCJSShopOrderNo());
				Order order = orderService.getOrderByParam(orderParam);

				if (order == null) {

					OrderPgData orderPgData = new OrderPgData();
					orderPgData.setPgAuthCode(cjResult.getCJSPayID());
					orderPgData.setPgKey(cjResult.getCJSTradeID());
					if (cjService.cancel(orderPgData) == false) {
						return ViewUtils.redirect("/m/order/step1", "[PG 취소 실패] 주문이 정상적으로 처리되지 않았습니다.");
					}

					return ViewUtils.redirect("/m/order/step1", "주문내역을 찾을수 없습니다.");

				}

			} else if ("VirtualAccount".equals(cjResult.getCJSPayMethod())) {

				orderParam.setSessionId(session.getId());
				orderParam.setOrderCode(cjResult.getCJSShopOrderNo());
				String orderCode = orderService.insertOrder(orderParam, cjResult, session, request);

				orderLogService.put(orderCode);

				if (StringUtils.isEmpty(orderCode)) {

					OrderPgData orderPgData = new OrderPgData();
					orderPgData.setPgAuthCode(cjResult.getCJSPayID());
					orderPgData.setPgKey(cjResult.getCJSTradeID());
					if (cjService.cancel(orderPgData) == false) {
						return ViewUtils.redirect("/m/order/step1", "[PG 취소 실패] 주문이 정상적으로 처리되지 않았습니다.");
					}

					return ViewUtils.redirect("/m/order/step1", "주문이 정상적으로 처리되지 않았습니다.");
				}

			}

		} else {
			return ViewUtils.redirect("/m/order/step1", cjResult.getCJSResultMessage());
		}

		return ViewUtils.redirect("/m/order/step3/" + cjResult.getCJSShopOrderNo());
	}

	@GetMapping(value="/blank")
	@RequestProperty(layout="base")
	public String blankPage() {
		return "view:/m/order/blank";
	}
	
	/**
	 * Payco결제값 받기 
	 * @param model
	 * @param paycoResponse
	 * @return
	 */
	@GetMapping(value="/payco/return-url")
	public String paycoReturnUrl(Model model, ReservationResponse paycoResponse, HttpSession session, HttpServletRequest request) {
		return handlePaycoResponse(paycoResponse, session, request);
	}

	@PostMapping(value="/payco/return-url")
	public String paycoReturnUrl2(Model model, ReservationResponse paycoResponse, HttpSession session, HttpServletRequest request) {
		return handlePaycoResponse(paycoResponse, session, request);
	}

	private String handlePaycoResponse(ReservationResponse paycoResponse, HttpSession session, HttpServletRequest request) {
		String codeInfo = "";
		if ("0".equals(paycoResponse.getCode())) {

			OrderParam orderParam = new OrderParam();
			orderParam.setUserId(UserUtils.getUserId());
			orderParam.setSessionId(session.getId());

			if (UserUtils.isUserLogin() == false) {
				orderParam.setUserId(0);
			}
			orderParam.setOrderCode(paycoResponse.getSellerOrderReferenceKey());

			orderLogService.put(orderParam.getOrderCode());
			//orderService.insertOrder(orderParam, paycoResponse, session, request);

			try {
				codeInfo = orderService.insertOrder(orderParam, paycoResponse, session, request);
			} catch (OrderException ox) {
				String javascript = "opener.paycoErrorMessage('" + paycoResponse.getCode() + "', '" + ox.getMessage() + "', '/cart'); self.close();";
				throw new OrderException("", "/m/order/blank", javascript);
			}

		} else {
			String msg = "ERROR - " + paycoResponse.getCode();
			if ("9000".equals(paycoResponse.getCode())) {
				msg = "시스템 점검중입니다.";
			}

			throw new OrderException(msg, "/m/order/step1");
		}

		String url = "/m/order/step3/" + codeInfo;
		String javascript = "opener.paycoPaySuccess('" + url + "'); self.close();";


		//return ViewUtils.redirect("/m/order/step3/" + paycoResponse.getSellerOrderReferenceKey());
		return ViewUtils.redirect(url, "", javascript);
	}


	/**
	 * 이니시스 결제 취소 
	 * @return
	 */
	@GetMapping(value="ini-cancel")
	@RequestProperty(layout="base")
	public String inipayCancel() {
		return "view:/order/inipay/ini-cancel";
	}

	@PostMapping(value="ini-cancel")
	@RequestProperty(layout="base")
	public String inipayCancel2() {
		return "view:/order/inipay/ini-cancel";
	}
	
	/**
	 * 이니시스 결제 취소 
	 * @return
	 */
	@GetMapping(value="ini-next")
	@RequestProperty(layout="base")
	public String inipayNext(PgData pgData, Model model, @RequestParam("orderCode") String orderCode, 
			HttpServletRequest request) {
		
		
		Enumeration params = request.getParameterNames(); 
		while(params.hasMoreElements()){
			String paramName = (String)params.nextElement();
			log.debug("[INIPAY] {} = {} ", paramName, request.getParameter(paramName));
		}
		
		model.addAttribute("pgData", pgData);
		model.addAttribute("orderCode", orderCode);
		return "view:/order/inipay/ini-next";
	}
	
	/**
	 * 쿠폰 목록
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping(value="/coupon")
	@RequestProperty(layout="base")
	public String coupon(HttpSession session, Model model, Buy buy, HttpServletRequest request) {
		
		if (!UserUtils.isUserLogin()) {
			throw new UserException("로그인 후 이용이 가능합니다.");
		}
		
		if (buy.getReceivers() == null) {
			throw new OrderException("", "/m/order/blank", "self.close()");
		}
		
		buy.setDeviceType("MOBILE");
		
		model.addAttribute("minimumPaymentAmount", ShopUtils.getConfig().getMinimumPaymentAmount());
		model.addAttribute("unit", "원");
		model.addAttribute("buy", orderService.getOrderCouponData(buy));
		model.addAttribute("couponListForCart", null);
		return ViewUtils.view();
	}
		
	/**
	 * 주문 관련 관리자 에러 핸들러
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(OrderException.class)
	public String orderExceptionHandler(OrderException ex, HttpServletRequest request) {
		log.error("ERROR: {}", ex.getMessage(), ex);

		String redirectUrl = ex.getRedirectUrl();
		if (!redirectUrl.startsWith("/m")) {
			redirectUrl = "/m" + redirectUrl;
		}
		
		if (StringUtils.isEmpty(ex.getJavascript())) {
			return ViewUtils.redirect(ex.getRedirectUrl(), ex.getMessage());
		} else {
			return ViewUtils.redirect(ex.getRedirectUrl(), ex.getMessage(), ex.getJavascript());
		}
	}
	/**kye 추가
	 * 배송지 목록
	 * @param model
	 * @return
	 */
	@GetMapping(value="/delivery")
	@RequestProperty(layout="base")
	public String delivery(Model model, 
			@RequestParam(value="receiverIndex", required=false, defaultValue="0") int receiverIndex) {

		model.addAttribute("target", "order");
		model.addAttribute("receiverIndex", receiverIndex);
		model.addAttribute("list", userDeliveryService.getUserDeliveryList(UserUtils.getUserId()));
		
		return ViewUtils.getView("/user-delivery/list");
		
	}

	/**
	 * 모바일 비회원 주문 - 약관동의
	 * @param model
	 * @return
	 */
	@GetMapping("/no-member")
	public String noMember(Model model) {
		
		if (UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m/order/step1");
		}

		model.addAttribute("agreement", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_AGREEMENT));
		model.addAttribute("protectPolicy", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_PROTECT_POLICY));

		return ViewUtils.getView("/order/no-member");
	}
	
	/**kye 추가
	 * 모바일 주문 - 결제안내
	 * @return
	 */
	@GetMapping("/payment-guide")
	@RequestProperty(layout="base")
	public String paymentGuide( Model model, 
			@RequestParam(value="paymentType", required=false) String paymentType) {
		
		model.addAttribute("paymentType", paymentType);
		return ViewUtils.getView("/order/payment-guide");
	}
	
	@GetMapping(value="/kcp/callback-url")
	@RequestProperty(layout="base")
	public String kcpCallbackUrl(KcpRequest kcpRequest, HttpSession session, HttpServletRequest request, Model model) {

		return handleKcpCallback(kcpRequest, model);
	}

	@PostMapping(value="/kcp/callback-url")
	@RequestProperty(layout="base")
	public String kcpCallbackUrl2(KcpRequest kcpRequest, HttpSession session, HttpServletRequest request, Model model) {

		return handleKcpCallback(kcpRequest, model);
	}

	private String handleKcpCallback(KcpRequest kcpRequest, Model model) {
		String json = "";
		if ("0000".equals(kcpRequest.getRes_cd())) {
			json = JsonViewUtils.objectToJson(kcpRequest);
		}

		model.addAttribute("json", json);
		model.addAttribute("kcpRequest", kcpRequest);

		return ViewUtils.view();
	}


	@GetMapping(value="/kcp/order_approval")
	@RequestProperty(layout="blank")
	public String order_kcp_order_proc_win(HttpServletRequest request, Model model) {		
		return ViewUtils.view();
	}
	@PostMapping(value="/kcp/order_approval")
	@RequestProperty(layout="blank")
	public String order_kcp_order_proc_win2(HttpServletRequest request, Model model) {
		return ViewUtils.view();
	}

	/**
	 * KCP 가상계좌 입금 통보(DB처리후 성공시 0000 실패시 0000이 아닌 문자열 반환)
	 * @return
	 */
	@GetMapping(value="kcp-vacct")
	public @ResponseBody String kcpVacct(PgData pgData, HttpServletRequest request) {
		return handleKcpResponse(pgData, request);
	}

	@PostMapping(value="kcp-vacct")
	public @ResponseBody String kcpVacct2(PgData pgData, HttpServletRequest request) {
		return handleKcpResponse(pgData, request);
	}

	private String handleKcpResponse(PgData pgData, HttpServletRequest request) {
		if (pgData == null) {
			return "ERROR";
		}

		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			log.debug("[KCP] {} = {} ", paramName, request.getParameter(paramName));
		}

		String REMOTE_IP = saleson.common.utils.CommonUtils.getClientIp(request);
		String PG_IP = REMOTE_IP.substring(0, 10);

		KcpRequest kcpRequest = new KcpRequest(request);

		orderLogService.put(kcpRequest.getOrder_no(), true);

		pgData.setKcpRequest(kcpRequest);

		//PG에서 보냈는지 IP로 체크
		if (PG_IP.equals("210.122.73") || PG_IP.equals("203.238.36")) {
			return kcpService.confirmationOfPayment(pgData);
		} else {
			return "ERROR";
		}
	}
	
	/**
	 * 이지페이 iframe
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value="/easypay/payment-window")
	@RequestProperty(layout="blank")
	public String easypayIframe(HttpSession session, HttpServletRequest request, Model model) {		
		
		return ViewUtils.getView("/order/easypay/iframe_req");
	}


	/**
	 * 이지페이 결제창 응답결과 페이지
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value="/easypay/order_res")
	@RequestProperty(layout="base")
	public String easypayOrderRes(HttpSession session, HttpServletRequest request, Model model) {
		return handleEasypayResponse(request);
	}

	@PostMapping(value="/easypay/order_res")
	@RequestProperty(layout="base")
	public String easypayOrderRes2(HttpSession session, HttpServletRequest request, Model model) {
		return handleEasypayResponse(request);
	}

	private String handleEasypayResponse(HttpServletRequest request) {
		log.debug("[EASYPAY] ORDER_RES 들어옴");

		HashMap<String,String> paramMap = new HashMap<>();

		Enumeration params = request.getParameterNames();
		while(params.hasMoreElements()){
			String paramName = (String)params.nextElement();
			log.debug("[EASYPAY] {} = {} ", paramName, request.getParameter(paramName));
		}
		return ViewUtils.view();
	}
	
	/**
	 * 이지페이 결제창 응답결과 페이지
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value="/easypay/easypay_request")
	@RequestProperty(layout="base")
	public String easypayRequest(HttpSession session, HttpServletRequest request, Model model) {
				
		return ViewUtils.view();
	}

    @GetMapping("/naverpay/payment")
    public String naverpayPayment(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String url = "/m/order/step3";

        Map<String, Object> resultMap = new HashMap<>();
        OrderParam orderParam = new OrderParam();

        String orderCode = request.getParameter("orderCode");
        String paymentId = request.getParameter("paymentId");
        String amount = request.getParameter("amount");

        PgData pgData = new PgData();
        pgData.setOrderCode(orderCode);
        pgData.setPaymentId(paymentId);
        pgData.setAmount(amount);

        int orderSequence = 0;

        try {
            orderParam.setOrderCode(pgData.getOrderCode());
            orderParam.setUserId(UserUtils.getUserId());
            orderParam.setSessionId(ShopUtils.getSalesOnIdByHeader(request));

            orderCode = orderService.insertOrder(orderParam, pgData, session, request);

            url += "/"+orderCode;

        } catch (OrderException oe) {
            if (null != oe.getOrderCancelFail()) {
                OrderCancelFail orderCancelFail = oe.getOrderCancelFail();
                orderCancelFailService.save(orderCancelFail);
            }

            if (oe.getMessage().indexOf("결제 요청 실패") > -1) {
                log.error("[/api/order/pay] ERROR : {}",  oe.getMessage());
                return ViewUtils.redirect("/order/step1", oe.getMessage());
            } else if (oe.getMessage().indexOf("금액오류(200원 이하 이체불가)") > -1) {
                log.error("[/api/order/pay] ERROR : {}", ApiError.BAD_REQUEST_PAY_MIN_FAIL.getMessage(), oe);
                return ViewUtils.redirect("/order/step1", oe.getMessage());
            } else {
                log.error("[/api/order/pay] ERROR : {}", ApiError.SYSTEM_ERROR.getMessage(), oe);
                return ViewUtils.redirect("/order/step1", oe.getMessage());
            }
        } catch (Exception e) {
            log.error("[/api/order/pay] ERROR : {}", ApiError.SYSTEM_ERROR.getMessage(), e);
            return ViewUtils.redirect("/order/step1", e.getMessage());
        }

        return ViewUtils.redirect(url);
    }

    @GetMapping(value="/naverpay/return-url")
    @RequestProperty(layout="base")
    public String naverpayReturnUrl(HttpSession session, HttpServletRequest request, Model model,
                                    @RequestParam("resultCode") String resultCode) {

        String url = "/m/order/step1";
        String message = "";

        if ("Success".equals(resultCode)) {
            url = "/m/order/naverpay/payment";

            String paymentId = request.getParameter("paymentId");
            String orderCode = request.getParameter("orderCode");
            String amount = request.getParameter("amount");

            model.addAttribute("paymentId", paymentId);
            model.addAttribute("orderCode", orderCode);
            model.addAttribute("amount", amount);
        } else {
            String resultMessage = request.getParameter("resultMessage");

            if("userCancel".equals(resultMessage)) {
                message = "결제를 취소하셨습니다.";
            } else if("webhookFail".equals(resultMessage)) {
                message = "webhookUrl 호출 응답에 실패하였습니다.";
            } else if("paymentTimeExpire".equals(resultMessage)) {
                message = "결제 시간이 초과되었습니다.";
            } else if("OwnerAuthFail".equals(resultMessage)) {
                message = "본인 카드 인증 오류가 발생했습니다.";
            }
        }

        if ("".equals(message)) {
            return ViewUtils.redirect(url);
        } else {
            return ViewUtils.redirect(url, message);
        }
    }
}
