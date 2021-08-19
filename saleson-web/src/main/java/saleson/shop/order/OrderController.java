package saleson.shop.order;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import net.sf.json.JSONArray;
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
import saleson.shop.user.domain.UserDetail;
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
@RequestMapping("/order/**")
@RequestProperty(layout="default")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserDeliveryService userDeliveryService;
		
	@Autowired
	private ConfigService configService;

    @Autowired
	@Qualifier("kcpService")
    private PgService kcpService;

	@Autowired
	@Qualifier("nicepayService")
	private PgService nicepayService;

    @Autowired
    private EnumMapper enumMapper;

    @Autowired
    private ConfigPgService configPgService;

    @Autowired
    private Environment environment;

	@Autowired
	private OrderLogService orderLogService;

    @Autowired
    private OrderCancelFailService orderCancelFailService;

    @Autowired
	private PolicyService policyService;

	@GetMapping("test")
	@RequestProperty(layout="blank")
	public @ResponseBody String test(OrderParam orderParam) {
		
		//orderBatchService.updateOrderReturnBatch();
		//orderService.updateOrderCancelBatch(DateUtils.getToday(Const.DATETIME_FORMAT));

		//pointService.userLevelShippingCoupon();
		
		
		//orderService.test();
		
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
		
		Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			throw new OrderException();
		}
		
		boolean isAllCancel = true;
		HashMap<String, Integer> stockMap = new HashMap<>();
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem item : info.getOrderItems()) {
				orderService.makeStockRestorationMap(stockMap, item);
			}
		}

		for(String key : stockMap.keySet()) {
			System.out.println(key);
		}
		
		// 재고량 복원
		orderService.stockRestoration(stockMap);
		
		return "OK";
	}
	
	/**
	 * 비회원 주문 - 약관동의
	 * @return
	 */
	@GetMapping("/no-member")
	public String noMember(Model model) {
		
		if (UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/step1");
		}

		model.addAttribute("agreement", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_AGREEMENT));
		model.addAttribute("protectPolicy", policyService.getCurrentPolicyByType(Policy.POLICY_TYPE_PROTECT_POLICY));

		return ViewUtils.getView("/order/no-member");
	}
	
	@GetMapping("/multiple-delivery")
	@RequestProperty(layout="base")
	public String multipleDelivery(RequestContext requestContext, OrderParam orderParam,
			Model model, HttpSession session, @RequestParam(value="count", required=false, defaultValue="0") int setCount) {
		
		long userId = 0;
		if (UserUtils.isUserLogin()) {
			userId = UserUtils.getUserId();
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
		
		Enumeration params = request.getParameterNames(); 
		while(params.hasMoreElements()){
			String paramName = (String)params.nextElement();
			System.out.println("Attribute1 Name - "+paramName+", Value - "+request.getParameter(paramName));
		}
		
		long userId = 0;
		if (UserUtils.isUserLogin()) {
			userId = UserUtils.getUserId();
		}
		
		try {

			buy.setOrderCode(orderService.getNewOrderCode(OrderCodePrefix.FRONT));
			buy.setUserId(userId);
			buy.setSessionId(session.getId());
			buy.setDeviceType("WEB");
			buy.setRealDeviceType(DeviceUtils.resolveDevice(request));
			buy.setUserIp(request.getRemoteAddr());

			orderLogService.put(buy.getOrderCode());

			return JsonViewUtils.success(orderService.saveOrderTemp(session, buy));
		} catch(Exception e){
			log.debug("order save() : {}", e.getMessage());
			return JsonViewUtils.failure(e.getMessage());
		}
	}

	/**
	 * 결제
	 * @param session
	 * @param orderParam
	 * @param pgData
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("pay")
	public String pay(HttpSession session, OrderParam orderParam, PgData pgData,
					  HttpServletRequest request, HttpServletResponse response) {

		if (pgData == null) {
			return "ERROR";
		}

		pgData.setRequest(request);
		pgData.setResponse(response);

		Enumeration params = request.getParameterNames(); 
		while(params.hasMoreElements()){
			String paramName = (String)params.nextElement();
			System.out.println("Pay Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		}
		
		orderParam.setUserId(UserUtils.getUserId());
		orderParam.setSessionId(session.getId());
		orderParam.setViewTarget("WEB");
		
		if (UserUtils.isUserLogin() == false) {
			orderParam.setUserId(0);
		}

		String orderCode = null;
		try {

			orderLogService.put(orderParam.getOrderCode());

			orderCode = orderService.insertOrder(orderParam, pgData, session, request);
		} catch (Exception e) {
			log.error("[ORDER-ERROR] 주문(결제) 처리 시 오류 발생 : {}", orderParam.getOrderCode(), e);

			throw new UserException(e.getMessage(), "/order/step1");
		}

		return ViewUtils.redirect("/order/step3/" + orderCode);
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
		JSONArray jsonArray = new JSONArray();
		model.addAttribute("jsonOrderList", jsonArray.fromObject(order.getOrderShippingInfos()));
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("order", order);
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("orderSequence", orderSequence);

		model.addAttribute("orderItemUserCodes", JsonViewUtils.objectToJson(order.getOrderItemUserCodes()));


		return ViewUtils.getView("/order/step3");
	}
	
	/**
	 * 쿠폰 목록
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping(value="/coupon")
	@RequestProperty(layout="base")
	public String coupon(HttpSession session, Model model, Buy buy) {
		
		if (!UserUtils.isUserLogin()) {
			throw new UserException("로그인 후 이용이 가능합니다.");
		}
		
		if (buy.getReceivers() == null) {
			throw new OrderException("", "/order/blank", "self.close()");
		}
		
		buy.setDeviceType("WEB");
		
		model.addAttribute("minimumPaymentAmount", ShopUtils.getConfig().getMinimumPaymentAmount());
		model.addAttribute("unit", "원");
		model.addAttribute("buy", orderService.getOrderCouponData(buy));
		model.addAttribute("couponListForCart", null);
		return ViewUtils.view();
	}
	
	/**
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


	@GetMapping(value="/blank")
	@RequestProperty(layout="base")
	public String blankPage() {
		return "view:/order/blank";
	}

	
	/**
	 * 엘지 데이콤 결제값 받기 - 최종 승인전
	 * @param model
	 * @param LGD_RESPCODE
	 * @param LGD_RESPMSG
	 * @param LGD_PAYKEY
	 * @return
	 */
	@GetMapping(value="/lgdacom/return-url")
	@RequestProperty(layout="base")
	public String lgdacomReturnUrl(Model model, 
			@RequestParam("LGD_RESPCODE") String LGD_RESPCODE,
			@RequestParam("LGD_RESPMSG") String LGD_RESPMSG,
			@RequestParam(value="LGD_PAYKEY", required=false, defaultValue="") String LGD_PAYKEY) {

		model.addAttribute("LGD_RESPCODE", LGD_RESPCODE);
		model.addAttribute("LGD_RESPMSG", LGD_RESPMSG);
		model.addAttribute("LGD_PAYKEY", LGD_PAYKEY);
		return ViewUtils.view();
	}

	@PostMapping(value="/lgdacom/return-url")
	@RequestProperty(layout="base")
	public String lgdacomReturnUrl2(Model model,
								   @RequestParam("LGD_RESPCODE") String LGD_RESPCODE,
								   @RequestParam("LGD_RESPMSG") String LGD_RESPMSG,
								   @RequestParam(value="LGD_PAYKEY", required=false, defaultValue="") String LGD_PAYKEY) {

		model.addAttribute("LGD_RESPCODE", LGD_RESPCODE);
		model.addAttribute("LGD_RESPMSG", LGD_RESPMSG);
		model.addAttribute("LGD_PAYKEY", LGD_PAYKEY);
		return ViewUtils.view();
	}
	
	/**
	 * 엘지 데이콤 가상계좌 입금 확인
	 * @param pgData
	 * @return
	 */
	@GetMapping(value="/lgdacom/note-url")
	public @ResponseBody String lgdacomNoteUrl(PgData pgData) {
		return orderService.pgConfirmationOfPayment(pgData);
	}
	@PostMapping(value="/lgdacom/note-url")
	public @ResponseBody String lgdacomNoteUrl2(PgData pgData) {
		return orderService.pgConfirmationOfPayment(pgData);
	}

	
	/**
	 * 페이코 가상계좌 입금 확인
	 * @param response
	 * @return
	 */
	@GetMapping(value="/payco/note-url")
	public @ResponseBody String paycoNoteUrl(@RequestParam(value="response") String response) {
		String jsonString = Base64Utils.decode(response);
		return orderService.paycoConfirmationOfPayment(jsonString);
	}
	@PostMapping(value="/payco/note-url")
	public @ResponseBody String paycoNoteUrl2(@RequestParam(value="response") String response) {
		String jsonString = Base64Utils.decode(response);
		return orderService.paycoConfirmationOfPayment(jsonString);
	}

	
	/**
	 * Payco결제값 받기 (주문완료시 이동하는 URL)
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

			try {
				codeInfo = orderService.insertOrder(orderParam, paycoResponse, session, request);
			} catch (OrderException ox) {
				String javascript = "opener.paycoErrorMessage('" + paycoResponse.getCode() + "', '" + ox.getMessage() + "', '/cart'); self.close();";
				throw new OrderException("", "/order/blank", javascript);
			}

		} else {
			String msg = paycoResponse.getMessage();
			if ("9000".equals(paycoResponse.getCode())) {
				msg = "시스템 점검중입니다.";
			}

			String javascript = "opener.paycoErrorMessage('" + paycoResponse.getCode() + "', '" + msg + "'); self.close();";
			throw new OrderException("", "/order/blank", javascript);
		}

		String url = "/order/step3/" + codeInfo;
		String javascript = "opener.paycoPaySuccess('" + url + "'); self.close();";

		return ViewUtils.redirect(url, "", javascript);
	}


	/**
	 * CJ 결제 처리
	 * @param cjResult
	 * @return
	 */
	@GetMapping(value="/cj/callback-url")
	public @ResponseBody String cjCallbackUrl(CjResult cjResult, HttpSession session, HttpServletRequest request) {

		return handleCjResponse(cjResult, session, request);
	}
	@PostMapping(value="/cj/callback-url")
	public @ResponseBody String cjCallbackUrl2(CjResult cjResult, HttpSession session, HttpServletRequest request) {

		return handleCjResponse(cjResult, session, request);
	}

	private String handleCjResponse(CjResult cjResult, HttpSession session, HttpServletRequest request) {
		if (StringUtils.isEmpty(cjResult.getCJSResultCode())) {
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
			try {

				OrderParam orderParam = new OrderParam();
				orderParam.setUserId(Integer.parseInt(cjResult.getReserve02()));
				orderParam.setSessionId(cjResult.getReserve01());

				// 카드, 계좌이체의 경우 callback에서 DB처리
				if ("Creditcard".equals(cjResult.getCJSPayMethod()) || "Account".equals(cjResult.getCJSPayMethod())) {
					// 카드결제 인경우 넘어오는 데이터가 서로 다르다... 초기화 해주자

					// 어느날 갑자기 해당 데이터로 값이 넘어오지 않는다 메뉴얼대로 변경되었다... 2015.11.20
					if ("CRD".equals(cjResult.getPayType())) {
						// CJ에서 데이터 전달해줄때 이상한 문자열이 붙어서 오내? 뭐지...
						String orderCode = cjResult.getOrderCode();
						orderCode = orderCode.replaceAll(";", "");

						cjResult.setCJSShopOrderNo(orderCode);
						cjResult.setCJSAmountTotal(cjResult.getTotalPrice());
						cjResult.setCJSShopID(cjResult.getCid());
					}

					orderParam.setOrderCode(cjResult.getCJSShopOrderNo());

					orderLogService.put(orderParam.getOrderCode());

					orderService.insertOrder(orderParam, cjResult, session, request);
				} else if ("VirtualAccount".equals(cjResult.getCJSPayMethod())) {

					// 가상계좌의경우 입금 통보
					return orderService.cjPgConfirmationOfPayment(cjResult);
				} else {
					return "9999";
				}


				return "0000";

			} catch (Exception e) {
				log.debug("order cjCallbackUrl : {}", e.getMessage(), e);
				return "9999";
			}
		} else {
			return "9999";
		}
	}

	/**
	 * 이니시스 결제 취소 
	 * @return
	 */
	@GetMapping(value="ini-cancel")
	@RequestProperty(layout="base")
	public String iniCancel() {
		return "view:/order/inipay/ini-cancel";
	}


	/**
	 * 이니시스 가상계좌 입금 통보
	 * @return
	 */
	@GetMapping(value="ini-vacct")
	public @ResponseBody String inipayVacct(PgData pgData, HttpServletRequest request) {
		return handleInipayResponse(pgData, request);
	}

	@PostMapping(value="ini-vacct")
	public @ResponseBody String inipayVacct2(PgData pgData, HttpServletRequest request) {
		return handleInipayResponse(pgData, request);
	}

	private String handleInipayResponse(PgData pgData, HttpServletRequest request) {
		if (pgData == null) {
			return "ERROR";
		}
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			System.out.println("Attribute Name - " + paramName + ", Value - " + request.getParameter(paramName));
		}

		//PG에서 보냈는지 IP로 체크
		String REMOTE_IP = saleson.common.utils.CommonUtils.getClientIp(request);
		String PG_IP = REMOTE_IP.substring(0, 10);

		orderLogService.put(pgData.getOrderCode(),true);

		if (PG_IP.equals("203.238.37") || PG_IP.equals("39.115.212") || PG_IP.equals("210.98.138")) {
			try {
				return orderService.getOrderPaymentByPgDataForInipayVacct(pgData, "WEB");
			} catch (Exception e) {
				log.error("ERROR: {}", e.getMessage(), e);
				return "ERROR";
			}

		} else {
			return "ERROR";
		}
	}


	/**
	 * 이니시스 모바일 입금 통보 및 가상계좌 채번.
	 * @return
	 */
	@GetMapping(value="ini-mobile-noti-url")
	public @ResponseBody String inipayNoti(PgData pgData, HttpServletRequest request, HttpSession session,
			@RequestParam("P_USERID") long userId, @RequestParam("P_NOTI") String sessionId,
			@RequestParam("orderCode") String orderCode) {
		return handleInipayMobileResponse(pgData, request, session, userId, sessionId, orderCode);
	}

	@PostMapping(value="ini-mobile-noti-url")
	public @ResponseBody String inipayNoti2(PgData pgData, HttpServletRequest request, HttpSession session,
										   @RequestParam("P_USERID") long userId, @RequestParam("P_NOTI") String sessionId,
										   @RequestParam("orderCode") String orderCode) {
		return handleInipayMobileResponse(pgData, request, session, userId, sessionId, orderCode);
	}

	private String handleInipayMobileResponse(PgData pgData, HttpServletRequest request, HttpSession session, @RequestParam("P_USERID") long userId, @RequestParam("P_NOTI") String sessionId, @RequestParam("orderCode") String orderCode) {
		if (pgData == null) {
			return "ERROR";
		}

		orderLogService.put(orderCode,true);

		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			System.out.println("Attribute Name - " + paramName + ", Value - " + request.getParameter(paramName));
		}

		String addr = saleson.common.utils.CommonUtils.getClientIp(request).toString();

		if ("118.129.210.25".equals(addr) || "211.219.96.165".equals(addr) || "183.109.71.153".equals(addr)) { //PG에서 보냈는지 IP로 체크

			if ("VBANK".equals(pgData.getP_TYPE())) {
				if ("00".equals(pgData.getP_STATUS())) {
					return "OK";
				}
			}

			// 가상계좌 채번
			// 웹 방식일때는 여기로 가상계좌 채번을 하지 않는다..
			if ("00".equals(pgData.getP_STATUS())) {

				OrderParam orderParam = new OrderParam();
				orderParam.setUserId(userId);
				orderParam.setSessionId(sessionId);

				orderParam.setOrderCode(orderCode);

				orderLogService.put(orderParam.getOrderCode());

				if ("BANK".equals(pgData.getP_TYPE())) {
					pgData.setMobilePage(true);
				}

				if (StringUtils.isEmpty(orderService.insertOrder(orderParam, pgData, session, request))) {
					return "ERROR";
				}

			} else if ("02".equals(pgData.getP_STATUS())) { // 입금통보
				return orderService.getOrderPaymentByPgDataForInipayVacct(pgData, "MOBILE");
			} else {
				return "ERROR";
			}
			return "OK";
		} else {
			return "ERROR";
		}
	}


	/**
	 * KSPAY 결제값 받기 - 2017.05.25 Jun-Eu Son
	 * @param model
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value="/kspay/return")
	@RequestProperty(layout="base")
	public String kspayReturnUrl(Model model, HttpServletRequest request){

		handleKspayResponse(model, request);

		return ViewUtils.view();
	}
	@PostMapping(value="/kspay/return")
	@RequestProperty(layout="base")
	public String kspayReturnUrl2(Model model, HttpServletRequest request){
		handleKspayResponse(model, request);
		return ViewUtils.view();
	}

	private void handleKspayResponse(Model model, HttpServletRequest request) {
		String reCommConId = request.getParameter("reCommConId");
		String reCommType = request.getParameter("reCommType");
		String reHash = request.getParameter("reHash");

		model.addAttribute("reCommConId", reCommConId);
		model.addAttribute("reCommType", reCommType);
		model.addAttribute("reHash", reHash);
	}

	@GetMapping(value="/kcp/cardhub")
	@RequestProperty(layout="base")
	public String kcpCardHubOrder(HttpSession session, HttpServletRequest request, Model model) {
		HashMap<String,String> paramMap = new HashMap<>();
		Enumeration params = request.getParameterNames(); 
		while(params.hasMoreElements()){
			String paramName = (String)params.nextElement();
			paramMap.put(paramName, request.getParameter(paramName));		
		}
		
		String json = JsonViewUtils.objectToJson(paramMap);
				
		model.addAttribute("key", paramMap.keySet());
		model.addAttribute("json", json);
		
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
		pgData.setKcpRequest(kcpRequest);

		orderLogService.put(kcpRequest.getOrder_no(), true);

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
						
        String windowType = request.getParameter("EP_window_type");
        String url = "/order/easypay/popup_req";
        
        if("iframe".equals(windowType)) {
        	url = "/order/easypay/iframe_req";
        }
		
		return ViewUtils.getView(url);
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

	/**
	 * 나이스페이 가상계좌 입금 통보
	 * @return
	 */
	@GetMapping(value="nicepay-vacct")
	public @ResponseBody String nicepayVacct(PgData pgData, HttpServletRequest request) {
		return handleNicepayResponse(pgData, request);
	}

	@PostMapping(value="nicepay-vacct")
	public @ResponseBody String nicepayVacct2(PgData pgData, HttpServletRequest request) {
		return handleNicepayResponse(pgData, request);
	}

	private String handleNicepayResponse(PgData pgData, HttpServletRequest request) {
		if (pgData == null) {
			return "ERROR";
		}

		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			log.debug("[NICEPAY] {} = {} ", paramName, request.getParameter(paramName));
		}

		try {
			pgData.setRequest(request);

			orderLogService.put(pgData.getOrderCode(), true);

			return nicepayService.confirmationOfPayment(pgData);
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return "ERROR";
		}
	}


	/**
	 * 주문 관련 관리자 에러 핸들러
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(OrderException.class)
	public String orderExceptionHandler(OrderException ex, HttpServletRequest request) {

		log.error(ex.getMessage(), ex);

		if (StringUtils.isEmpty(ex.getJavascript())) {
			return ViewUtils.redirect(ex.getRedirectUrl(), ex.getMessage());
		} else {
			return ViewUtils.redirect(ex.getRedirectUrl(), ex.getMessage(), ex.getJavascript());
		}
	}

    @GetMapping("/naverpay/payment")
    public String naverpayPayment(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String url = "/order/step3";

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

        String url = "/order/step1";
        String message = "";

        if ("Success".equals(resultCode)) {
            url = "/order/naverpay/payment";

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
