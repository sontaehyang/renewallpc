package saleson.shop.mypage;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.onlinepowers.framework.annotation.handler.Authorize;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.repository.Code;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.chart.ChartService;
import saleson.shop.chart.domain.Chart;
import saleson.shop.chart.support.ChartParam;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.CouponOffline;
import saleson.shop.coupon.domain.CouponUser;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.item.ItemService;
import saleson.shop.item.support.ItemParam;
import saleson.shop.order.OrderMapper;
import saleson.shop.order.OrderService;
import saleson.shop.order.claimapply.OrderClaimApplyService;
import saleson.shop.order.claimapply.support.ReturnApply;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.order.pg.PgService;
import saleson.shop.order.support.OrderException;
import saleson.shop.order.support.OrderParam;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.Point;
import saleson.shop.point.domain.PointUsed;
import saleson.shop.point.support.PointParam;
import saleson.shop.qna.QnaService;
import saleson.shop.qna.domain.Qna;
import saleson.shop.qna.support.QnaParam;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.userdelivery.UserDeliveryService;
import saleson.shop.wishlist.WishlistService;
import saleson.shop.wishlist.domain.Wishlist;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mypage")
@RequestProperty(layout="mypage")
public class MypageController {
	private static final Logger log = LoggerFactory.getLogger(MypageController.class);
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PgService inicisService; 
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private UserDeliveryService userDeliveryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderClaimApplyService orderClaimApplyService;
	
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private ChartService chartService;
	
	/**
	 * ?????? ??????
	 * @param requestContext
	 * @param orderSearchParam
	 * @return
	 */
	@PostMapping("confirm-purchase")
	public JsonView confirmPurchase(RequestContext requestContext, OrderParam orderSearchParam, HttpServletRequest request) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		if (UserUtils.isUserLogin()) {
			orderSearchParam.setUserId(UserUtils.getUserId());
		}
		
		try {
			orderService.updateConfirmPurchase(orderSearchParam);

			// ?????????????????? ??????
			OrderItem orderItem = orderService.getOrderItemByParam(orderSearchParam);

			orderSearchParam.setAdditionItemFlag("Y");
			orderSearchParam.setParentItemId(orderItem.getItemId());
			orderSearchParam.setParentItemOptions(orderItem.getOptions());
			List<OrderItem> additionOrderList = orderService.getOrderItemListByParam(orderSearchParam);

			for (OrderItem addition :  additionOrderList) {
				orderSearchParam.setItemSequence(addition.getItemSequence());
				orderSearchParam.setOrderSequence(addition.getOrderSequence());

				orderService.updateConfirmPurchase(orderSearchParam);
			}

			return JsonViewUtils.success(); 
		} catch (OrderException ex) {
			return JsonViewUtils.exception(ex.getMessage());
		}
		
	}

	/**
	 * ???????????? ???????????? ???????????? ??????
	 * @param model
	 * @param orderCode
	 * @param orderSequence
	 * @param itemSequence
	 * @return
	 */
	@GetMapping(value="popup/iniescrow")
	@RequestProperty(layout="blank")
	public String iniEscrowConfirm(Model model,
			@RequestParam("orderCode") String orderCode,
			@RequestParam("orderSequence") int orderSequence,
			@RequestParam("itemSequence") int itemSequence) {

		String tid = orderService.getTidByParam(orderCode);
		
		OrderParam orderParam = new OrderParam();
				
		orderParam.setPgKey(tid);
		orderParam.setOrderCode(orderCode);
		orderParam.setOrderSequence(orderSequence);
		orderParam.setItemSequence(itemSequence);
		
		model.addAttribute("tid", tid);
		model.addAttribute("orderParam", orderParam);
		
		return ViewUtils.getView("/order/inipay/iniescrow_confirm");
		
	}
	
	/**
	 * ???????????? ???????????? ???????????? ??????
	 * @param requestContext
	 * @param request
	 * @return
	 */	
	@PostMapping(value="INIescrow_result")
	public String iniEscrowConfirmProcess(RequestContext requestContext, HttpServletRequest request, Model model) {
		
		boolean isSuccess = false;
		
		OrderParam orderParam = new OrderParam();
		
		orderParam.setOrderCode(request.getParameter("orderCode"));
		orderParam.setOrderSequence(Integer.parseInt(request.getParameter("orderSequence")));
		
		// ???????????? ???????????? plugin?????? ??????????????? ??????????????? ??????????????? ??????????????? ??????( 1:????????????, 2:????????????)

		String encrypted = request.getParameter("encrypted");

		String iniescr_type = "2";
		if (encrypted != null && encrypted.contains("iniescr_type=1") == true) {
			iniescr_type = "1";
		}
		
		if(iniescr_type.equals("1")) {
			orderParam.setItemSequence(Integer.parseInt(request.getParameter("itemSequence")));
			
			orderParam.setEscrowStatus("30");
			orderMapper.updateEscrowStatus(orderParam);
		}
		
		// PG?????? ???????????? ?????? ?????? ?????? ?????? ??? ????????? ?????????(????????????????????? ????????????,?????????????????? ??????, ????????????????????? ????????????????????? ??????)		
		isSuccess = inicisService.escrowConfirmPurchase(request);

		// PG??? ???????????? ????????? ???????????? ?????? ?????????????????? ???????????? ??????
		if(isSuccess) {
			
			if (UserUtils.isUserLogin()) {
				orderParam.setUserId(UserUtils.getUserId());
			}
			
			if(iniescr_type.equals("1")) {
				orderParam.setEscrowStatus("50");
				orderMapper.updateEscrowStatus(orderParam);
				
				orderService.updateConfirmPurchase(orderParam);
			} else if (iniescr_type.equals("2")) {
				orderParam.setEscrowStatus("40");
				orderMapper.updateEscrowStatus(orderParam);
				
				//???????????? ????????????????????????, ?????? ????????? ?????????????????? ???????????????????????????, ???????????? ??????
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
				
				ReturnApply returnApply = new ReturnApply();
			
//				OrderItem orderItem = orderService.getOrderItemByParam(orderParam);
				List<OrderItem> orderItemList = orderService.getOrderItemListByParam(orderParam);
				
				for(OrderItem orderItem : orderItemList){
								
					if (orderItem == null) {
						throw new PageNotFoundException();
					}
					
					if (!ShopUtils.checkOrderStatusChange("return", orderItem.getOrderStatus())) {
						throw new OrderException("????????? ?????? ?????? ????????? ?????? ????????? ????????????.");
					}
					
					orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());
					OrderShippingInfo info = orderService.getOrderShippingInfoByParam(orderParam);
													
					returnApply.setReturnShippingAskType("1");	// 1: ??????????????? 2: ????????????
					returnApply.setOrderCode(orderParam.getOrderCode());
					returnApply.setOrderSequence(orderParam.getOrderSequence());
					returnApply.setItemSequence(orderParam.getItemSequence());				
					returnApply.setReturnApply(info);
					returnApply.setOrderItem(orderItem);
					returnApply.setApplyQuantity(orderItem.getOrderQuantity());
					returnApply.setShipmentReturnId(orderItem.getShipmentReturnId());
					
					returnApply.setReturnBankInName("????????????");
					returnApply.setReturnBankName("????????????");
					returnApply.setReturnVirtualNo("????????????");
					
					returnApply.setClaimReason("2");
					returnApply.setClaimReasonDetail("???????????? ???????????? ??????");
					returnApply.setClaimReasonText("??????");
					
					orderClaimApplyService.insertOrderReturnApply(returnApply);
				}
			}			
		}
		
		model.addAttribute("isSuccess", isSuccess);
		return ViewUtils.getView("/order/inipay/iniescrow_result");
		
	}
	
			
	/**
	 * ????????????
	 * @param model
	 * @return
	 */
	@GetMapping("order")
	public String order(Model model, @ModelAttribute("orderSearchParam") OrderParam orderParam) {
		orderParam.setSearchDateType("OI.CREATED_DATE");
		if (UserUtils.isUserLogin()) {
			orderParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {
			User user = UserUtils.getGuestLogin();
			UserDetail userDetail = (UserDetail) user.getUserDetail();
			
			orderParam.setGuestUserName(user.getUserName());
			orderParam.setGuestPhoneNumber(userDetail.getPhoneNumber());
		} else {
			return "redirect:/users/login";
			//throw new PageNotFoundException();
		}

		orderParam.setAdditionItemFlag("N");
		List<Order> orderList = orderService.getOrderListByParam(orderParam);

		model.addAttribute("list", orderList);
		model.addAttribute("totalCount", orderParam.getPagination().getTotalItems());
		model.addAttribute("pagination", orderParam.getPagination());
		
		ItemParam itemParam = new ItemParam();
		itemParam.setUserId(UserUtils.getUserId());
		model.addAttribute("nonregisteredReviewList", itemService.getItemNonregisteredReviewList(itemParam));
		
		userService.setMypageUserInfoForFront(model);
		
		setSearchDate(model, orderParam.getSearchStartDate(), orderParam.getSearchEndDate());
		
		return ViewUtils.view();
	}
	
	/**
	 * ???????????? ??????
	 * @param model
	 * @return
	 */
	@GetMapping("order-detail/{orderSequence}/{orderCode}")
	public String orderDetail(Model model, @ModelAttribute("orderSearchParam") OrderParam orderSearchParam, 
			@PathVariable("orderCode") String orderCode,
			@PathVariable("orderSequence") int orderSequence) {
		
		if (UserUtils.isUserLogin()) {
			orderSearchParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {
			User user = UserUtils.getGuestLogin();
			UserDetail userDetail = (UserDetail) user.getUserDetail();
			
			orderSearchParam.setGuestUserName(user.getUserName());
			orderSearchParam.setGuestPhoneNumber(userDetail.getPhoneNumber());
		} else {
			throw new PageNotFoundException();
		}
		
		orderSearchParam.setOrderCode(orderCode);
		orderSearchParam.setOrderSequence(orderSequence);
		
		Order order = orderService.getOrderByParam(orderSearchParam);
		if (order == null) {
			throw new PageNotFoundException();
		}

		// ??????????????? ???????????? ?????? ????????? ???????????? ?????? orderItems??? ????????? ?????????????????? ??????
		for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
			List<OrderItem> itemList = orderShippingInfo.getOrderItems()
					.stream().filter(l -> "N".equals(l.getAdditionItemFlag())).collect(Collectors.toList());

			orderShippingInfo.getOrderItems().clear();
			orderShippingInfo.getOrderItems().addAll(itemList);
		}
		
		userService.setMypageUserInfoForFront(model);
		
		model.addAttribute("order", order);
		return ViewUtils.getView("/mypage/order-detail");
	}
	
	/**
	 * ????????? ?????? ??????
	 * @param model
	 * @return
	 */
	@GetMapping("{pointType}-save-list")
	@Authorize("hasRole('ROLE_USER')")
	public String pointSaveList(Model model, @PathVariable("pointType") String pointType, 
			@ModelAttribute("pointParam") PointParam pointParam) {
		pointParam.setItemsPerPage(10);
		pointParam.setPointType(pointType);
		pointParam.setUserId(UserUtils.getUserId());
		
		int totalCount = pointService.getPointCountByParam(pointParam);
		
		Pagination pagination = Pagination.getInstance(totalCount, pointParam.getItemsPerPage());
		pointParam.setPagination(pagination);
		
		List<Point> list = pointService.getPointListByParam(pointParam);
		
		userService.setMypageUserInfoForFront(model);
		
		model.addAttribute("expirationPointAmount", pointService.getNextMonthExpirationPointAmountByParam(pointParam));
		model.addAttribute("pointType", pointType);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		
		setSearchDate(model, pointParam.getSearchStartDate(), pointParam.getSearchEndDate());
		
		return "view:/mypage/point-save-list";
	}
	
	/**
	 * ????????? ?????? ??????
	 * @param model
	 * @return
	 */
	@GetMapping("{pointType}-used-list")
	@Authorize("hasRole('ROLE_USER')")
	public String pointUsedList(Model model, @PathVariable("pointType") String pointType, 
			@ModelAttribute("pointParam") PointParam pointParam) {
		pointParam.setItemsPerPage(10);
		pointParam.setPointType(pointType);
		pointParam.setUserId(UserUtils.getUserId());
		
		int totalCount = pointService.getPointUsedCountByParam(pointParam);
		
		Pagination pagination = Pagination.getInstance(totalCount, pointParam.getItemsPerPage());
		pointParam.setPagination(pagination);
		
		List<PointUsed> list = pointService.getPointUsedListByParam(pointParam);
		
		userService.setMypageUserInfoForFront(model);
		
		model.addAttribute("expirationPointAmount", pointService.getNextMonthExpirationPointAmountByParam(pointParam));
		model.addAttribute("pointType", pointType);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		
		setSearchDate(model, pointParam.getSearchStartDate(), pointParam.getSearchEndDate());
		
		return "view:/mypage/point-used-list";
	}
	
	/**
	 * ??????????????? ?????? ????????????
	 * @param couponId
	 * @param requestContext
	 * @return
	 */
	@PostMapping("/coupon-download/{couponId}")
	@Authorize("hasRole('ROLE_USER')")
	@RequestProperty(layout="base")
	public JsonView couponDownload(@PathVariable("couponId") int couponId,
			RequestContext requestContext) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		UserCouponParam userCouponParam = new UserCouponParam();
		userCouponParam.setCouponId(couponId);
		if (couponService.userCouponDownload(userCouponParam) == 0) {
			return JsonViewUtils.exception("????????? ???????????? ?????? ???????????????.");
		}
		
		return JsonViewUtils.success();
	}
	
	/**
	 * ?????? ?????? ???????????? ??????
	 * @param model
	 * @param userCouponParam
	 * @return
	 */
	@GetMapping("popup/coupon-download")
	@Authorize("hasRole('ROLE_USER')")
	@RequestProperty(layout="base")
	public String couponDownload(Model model, UserCouponParam userCouponParam) {

		int itemsPerPage = 10;

		UserDetail userDetail = UserUtils.getUserDetail();
		
		userCouponParam.setUserId(UserUtils.getUserId());
		userCouponParam.setUserLevelId(userDetail.getLevelId());
		
		//orderCount??? ??????????????? ????????? ??????
		OrderParam orderParam = new OrderParam();
		orderParam.setUserId(userCouponParam.getUserId());
		int orderCount = orderService.getOrderCountByParam(orderParam);
		userCouponParam.setOrderCount(orderCount);

		Pagination pagination
				= Pagination.getInstance(couponService.getUserDownloadableCouponListCountByParam(userCouponParam), itemsPerPage);
		userCouponParam.setPagination(pagination);


		userCouponParam.setViewTarget("list"); // ???????????? ????????? ???????????? ????????? ????????? ??????[2017-09-18]minae.yun
		model.addAttribute("list", couponService.getUserDownloadableCouponListByParam(userCouponParam));
		model.addAttribute("pagination", pagination);
		/*model.addAttribute("list", couponService.getDownloadUserCouponListByUserCouponParam(userCouponParam));*/
		
		return ViewUtils.view();
	}
	
	/**
	 * ?????? ?????? ??????
	 * @param model
	 * @param userCouponParam
	 * @return
	 */
	@GetMapping("download-coupon-list")
	@Authorize("hasRole('ROLE_USER')")
	public String downloadCouponList(Model model, @ModelAttribute("userCouponParam") UserCouponParam userCouponParam) {

		userCouponParam.setUserId(UserUtils.getUserId());
		int totalCount = couponService.getDownloadUserCouponCountByUserCouponParam(userCouponParam);
		
		Pagination pagination = Pagination.getInstance(totalCount, 10);
		userCouponParam.setPagination(pagination);
			
		List<CouponUser> list = couponService.getDownloadUserCouponListByUserCouponParam(userCouponParam);
		
		model.addAttribute("userCouponParam", userCouponParam);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("completedUserCouponCount", couponService.getCompletedUserCouponCountByUserCouponParam(userCouponParam));
		
		userService.setMypageUserInfoForFront(model);
		
		return ViewUtils.view();
	}
	
	/**
	 * ???????????? ?????? ??????
	 * @author [2017-09-11]minae.yun
	 * @param model
	 * @return
	 */
	@GetMapping("offline-coupon-exchange")
	@Authorize("hasRole('ROLE_USER')")
	public String exchangeOfflineCoupon(Model model) {
		
		userService.setMypageUserInfoForFront(model);
		
		return ViewUtils.view();
	}
	
	/**
	 * ???????????? ?????? ????????????
	 * @author [2017-09-11]minae.yun
	 * @param offlineCode
	 * @param requestContext
	 * @return
	 */
	@PostMapping("offline-coupon-exchange")
	@Authorize("hasRole('ROLE_USER')")
	public JsonView exchangeOfflineCouponProcess(@RequestParam(name="offlineCode", defaultValue = "") String offlineCode,
												 RequestContext requestContext) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}

		if (couponService.downloadDirectInputCoupon(offlineCode)) {
			return JsonViewUtils.success();
		}

		long userId = UserUtils.getUserId();

		offlineCode = couponService.getOfflineCode(offlineCode);

		//???????????? ?????? ????????? ?????? ?????? ??????
		CouponOffline couponOffline = new CouponOffline();
		couponOffline.setCouponOfflineCode(offlineCode);
		couponOffline.setUserId(userId);
		couponOffline = couponService.getCouponOfflineByOfflineCode(couponOffline);
		if (StringUtils.isEmpty(couponOffline)) {
			return JsonViewUtils.failure(MessageUtils.getMessage("M00297")); // ????????? ???????????? ????????????.
		} else {
			//????????????????????? ???????????? ?????? ???????????? ??? ???????????? ????????? ??????
			couponOffline.setUserId(userId);
			couponService.updateCouponOffline(couponOffline);
			
			UserCouponParam userCouponParam = new UserCouponParam();
			userCouponParam.setCouponId(couponOffline.getCouponId());
			userCouponParam.setViewTarget("offlineCoupon"); //???????????? where ?????? ???????????? ?????? viewTarget??????
			if (couponService.userCouponDownload(userCouponParam) == 0) {
				return JsonViewUtils.exception("????????? ???????????? ?????? ???????????????.");
			}
		}
		
		return JsonViewUtils.success();
	}
	
	/**
	 * ?????? + ?????? ?????? ??????
	 * @param model
	 * @param userCouponParam
	 * @return
	 */
	@GetMapping("completed-coupon-list")
	@Authorize("hasRole('ROLE_USER')")
	public String completedCouponList(Model model, @ModelAttribute("userCouponParam") UserCouponParam userCouponParam) {

		userCouponParam.setUserId(UserUtils.getUserId());
		int totalCount = couponService.getCompletedUserCouponCountByUserCouponParam(userCouponParam);
		
		Pagination pagination = Pagination.getInstance(totalCount, 10);
		userCouponParam.setPagination(pagination);
			
		List<CouponUser> list = couponService.getCompletedUserCouponListByUserCouponParam(userCouponParam);
		
		model.addAttribute("userCouponParam", userCouponParam);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("downloadCouponCount", couponService.getDownloadUserCouponCountByUserCouponParam(userCouponParam));
		
		userService.setMypageUserInfoForFront(model);
		
		return ViewUtils.view();
	}
	
	/**
	 * ????????????.
	 * @param model
	 * @return
	 */
	@GetMapping("/wishlist") // /mypage/wishlist
	@Authorize("hasRole('ROLE_USER')")
	public String wishlist(Model model) {
		invokeWishlistProcess("0", model);
		
		userService.setMypageUserInfoForFront(model);
		
		return ViewUtils.view();
	}
	
	/**
	 * 
	 * @param groupId
	 * @param model
	 * @return
	 */
	@GetMapping("/wishlist/{wishlistGroupId}")
	@Authorize("hasRole('ROLE_USER')")
	public String wishlist(@PathVariable("wishlistGroupId") String groupId, Model model) {
		if (groupId.indexOf("/") > -1) {
			throw new PageNotFoundException();
		}
		invokeWishlistProcess(groupId, model);
		return ViewUtils.view();
	}
	
	/**
	 * ???????????? ????????? ???????????? ?????? ?????? 
	 * @param groupId
	 * @param model
	 */
	private void invokeWishlistProcess(String groupId, Model model) {
		
		List<Wishlist> wishlists = wishlistService.getWishlistListByUserId(UserUtils.getUserId());
		
		
		model.addAttribute("totalItemCount", wishlists.size());
		model.addAttribute("wishlists", wishlists);
	}
	
	
	/**
	 * 1:1??????
	 * @param qnaParam
	 * @param qna
	 * @param model
	 * @return
	 */
	@GetMapping("inquiry")
	@Authorize("hasRole('ROLE_USER')")
	public String inquiry(QnaParam qnaParam, Qna qna, Model model) {
		
		User user = UserUtils.getUser();
		qnaParam.setQnaType(Qna.QNA_GROUP_TYPE_INDIVIDUAL);
		qnaParam.setUserId(user.getUserId());
		qnaParam.setItemsPerPage(5);
		qnaService.setQnaListPagination(qnaParam);
		List<Qna> qnaList = qnaService.getQnaListByParam(qnaParam);
		
		List<Code> qnaGroups = CodeUtils.getCodeList("QNA_GROUPS");
		for (Qna qnaCheck : qnaList) {
			for (Code code : qnaGroups) {

				if (qnaCheck.getQnaGroup().equals(code.getId())) {
					qnaCheck.setQnaGroup(code.getLabel());
				}
			}
		}
		
		model.addAttribute("qnaGroups", qnaGroups);
		model.addAttribute("count",qnaParam.getPagination().getTotalItems());
		model.addAttribute("pagination", qnaParam.getPagination());
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("qnaTypeList", CodeUtils.getCodeList("QNA_GROUPS"));
		
		userService.setMypageUserInfoForFront(model);
		
		setSearchDate(model, qnaParam.getSearchStartDate(), qnaParam.getSearchEndDate());
		
		return ViewUtils.view();
	}
	
	/**
	 * 2015.02.06
	 * 1:1?????? ??????
	 * @param
	 * @param viewName
	 * @param qnaId
	 * @return
	 */
	@GetMapping("/inquiry-delete/{viewName}/{qnaId}")
	@Authorize("hasRole('ROLE_USER')")
	public String deleteinquiry(@PathVariable("viewName") String viewName,
								@PathVariable("qnaId") int qnaId) {
		
		Qna tempQna = qnaService.getQnaByQnaId(qnaId);
		
		if (tempQna.getAnswerCount() > 0) {
			return ViewUtils.redirect("/mypage/" + viewName, "??????????????? ????????? ???????????? ??? ????????????.");
		}
		
		Qna qna = new Qna();
		qna.setQnaId(qnaId);
		qnaService.deleteQna(qna);
				
		return ViewUtils.redirect("/mypage/" + viewName);
	}

	
	@GetMapping("inquiry-item")
	@Authorize("hasRole('ROLE_USER')")
	public String inquiryItem(QnaParam qnaParam, Qna qna, Model model) {
		
		User user = UserUtils.getUser();
		qnaParam.setQnaType(Qna.QNA_GROUP_TYPE_ITEM);
		qnaParam.setUserId(user.getUserId());
		qnaParam.setItemsPerPage(5);
		qnaService.setQnaListPagination(qnaParam);
				
		List<Qna> qnaList = qnaService.getQnaListByParam(qnaParam);
		
		model.addAttribute("count",qnaParam.getPagination().getTotalItems());
		model.addAttribute("pagination", qnaParam.getPagination());
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("qnaParam", qnaParam);
		
		userService.setMypageUserInfoForFront(model);
		
		setSearchDate(model, qnaParam.getSearchStartDate(), qnaParam.getSearchEndDate());
		
		return ViewUtils.view();
	}
	
	/**
	 * 
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("review")
	@Authorize("hasRole('ROLE_USER')")
	public String review(ItemParam searchParam, Model model) {
		
		
		User user = UserUtils.getUser();
		
		searchParam.setUserId(user.getUserId());
		searchParam.setConditionType("FRONT_MYPAGE");

		int reviewCount = itemService.getItemReviewCountByParam(searchParam);

		searchParam.setAdditionItemFlag("N");
		int nonReviewCount = itemService.getItemNonregisteredReviewCount(searchParam);
		
		Pagination pagination = Pagination.getInstance(reviewCount,5);
		
		searchParam.setPagination(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("reviewList", itemService.getItemReviewListByParam(searchParam));
		model.addAttribute("searchParam",searchParam);
		
		model.addAttribute("reviewCount",reviewCount);
		model.addAttribute("nonReviewCount",nonReviewCount);
		
		userService.setMypageUserInfoForFront(model);
		
		setSearchDate(model, searchParam.getSearchStartDate(), searchParam.getSearchEndDate());
		
		return ViewUtils.view();
	}
	
	/**
	 * 
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("review-nonregistered")
	@Authorize("hasRole('ROLE_USER')")
	public String reviewNone(ItemParam searchParam, Model model) {
		
		
		if (UserUtils.isUserLogin()) {
			
		}else {
			throw new PageNotFoundException();
		}
		
		searchParam.setUserId(UserUtils.getUserId());
		
		int reviewCount = itemService.getItemReviewCountByParam(searchParam);

		searchParam.setAdditionItemFlag("N");
		int nonReviewCount = itemService.getItemNonregisteredReviewCount(searchParam);
		
		Pagination pagination = Pagination.getInstance(nonReviewCount,5);
		
		searchParam.setPagination(pagination);
		
		model.addAttribute("reviewCount",reviewCount);
		model.addAttribute("nonReviewCount",nonReviewCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("reviewList", itemService.getItemNonregisteredReviewList(searchParam));
		model.addAttribute("searchParam",searchParam);
		
		userService.setMypageUserInfoForFront(model);
		
		return ViewUtils.view();
	}
	
	
	/**
	 * ?????????????????? 
	 * @param itemReviewId
	 * @return
	 */
	@GetMapping("review/delete/{itemReviewId}")
	public String deleteReview(@PathVariable("itemReviewId") int itemReviewId) {
				
		itemService.deleteItemReview(itemReviewId);
		
		return ViewUtils.redirect("/mypage/review");  
	}
	
	
	@GetMapping("delivery")
	@Authorize("hasRole('ROLE_USER')")
	public String delivery(Model model) {

		userService.setMypageUserInfoForFront(model);
		
		model.addAttribute("list", userDeliveryService.getUserDeliveryList(UserUtils.getUserId()));
		return ViewUtils.view();
	}
	
	/*@GetMapping(value="/user")
	public String user(@ModelAttribute("qnaParam") QnaParam qnaParam, Model model) {
		int qnaCount = qnaService.getQnaCountByParam(qnaParam);
		Pagination pagination = Pagination.getInstance(qnaCount);
		qnaParam.setPagination(pagination);		
		
		model.addAttribute("qnaCount", qnaCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("qnaParam", qnaParam);
		model.addAttribute("qnaList", qnaService.getQnaListByParam(qnaParam));
		return ViewUtils.view();
	}*/
	
	
	// ????????? ?????? ?????? Active
	public void setSearchDate(Model model, String startDate, String endDate) {
		String today = DateUtils.getToday(Const.DATE_FORMAT);
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate) && endDate.equals(today)) {
			
			String oneWeekAgo =  DateUtils.addDay(today, -7);
			String oneMonthAgo = DateUtils.addMonth(today, -1);
			String threeMonthAgo = DateUtils.addMonth(today, -3);
			String sixMonthAgo = DateUtils.addMonth(today, -6);
			
			if(startDate.equals(oneWeekAgo)) {
				model.addAttribute("searchTerm", "week-1");
			} else if(startDate.equals(oneMonthAgo)) {
				model.addAttribute("searchTerm", "month-1");
			} else if(startDate.equals(threeMonthAgo)) {
				model.addAttribute("searchTerm", "month-3");
			} else if(startDate.equals(sixMonthAgo)) {
				model.addAttribute("searchTerm", "month-6");
			}
		}
		
		
	}

	/**
	 * ???????????????
	 * @param
	 * @return
	 */
	@GetMapping("/chart")
	public String chart(Model model, Chart chart, @RequestParam(value="searchName", required = false) String searchName ) {
		ChartParam chartParam = new ChartParam();
		List<Chart> chartCategory1, chartCategory2, chartCategory3 = null;

		if(searchName != null){
			chartParam.setItemName(searchName);
			model.addAttribute("itemLevel1", "itemLevel1");
			model.addAttribute("itemLevel2", "itemLevel2");
		} else {
			chartParam.setItemLevel1(chart.getItemLevel1());
			chartParam.setItemLevel2(chart.getItemLevel2());
			chartParam.setItemLevel3(chart.getItemLevel3());

			if (chartParam.getItemLevel1() == null && chartParam.getItemLevel2() == null && chartParam.getItemLevel3() == null && chartParam.getItemName() == null) {
				chartParam.setItemLevel1("50000");
				chartParam.setItemLevel2("50100");

				chartCategory2 = chartService.getChartCategory2(chartParam.getItemLevel1());
				chartCategory3 = chartService.getChartCategory3(chartParam.getItemLevel2());
				model.addAttribute("itemLevel1", chartParam.getItemLevel1());
				model.addAttribute("itemLevel2", chartParam.getItemLevel2());
				model.addAttribute("chartCategory2", chartCategory2);
				model.addAttribute("chartCategory3", chartCategory3);
			} else {
				// ??????????????? ???????????? ERP DB ???????????? ???????????? HDD/SSD ?????? ?????? ?????? ?????? ?????? ?????? ??????
				if (chartParam.getItemLevel1() != null && (chartParam.getItemLevel1().equals("56000") || chartParam.getItemLevel1().equals("58000"))) {
					if (chartParam.getItemLevel1().equals("58000")) {
						chartParam.setItemLevel1_2("56000");
					} else {
						chartParam.setItemLevel1_2("58000");
					}

					List<Chart> categoryInfo = chartService.getCategoryInfo(chartParam);
					HashMap categoryMap = new HashMap();
					categoryMap.put("itemLevel1", chartParam.getItemLevel1());
					categoryMap.put("itemLevel1_2", chartParam.getItemLevel1_2());
					chartCategory1 = chartService.getChartCategory1(categoryMap);

					if (chartParam.getItemLevel1().equals("58000")) {
						model.addAttribute("itemLevel1", "58000");
						model.addAttribute("itemLevel2", "58000");
					} else {
						model.addAttribute("itemLevel1", "56000");
						model.addAttribute("itemLevel2", "56000");
					}

					chartCategory2 = chartService.getChartCategory2(chartParam.getItemLevel1());
					model.addAttribute("HDD_SSDCategory1", chartCategory1);
					model.addAttribute("HDD_SSDCategory2", chartCategory2);

				}else {
					List<Chart> categoryInfo = chartService.getCategoryInfo(chartParam);
					chartParam.setItemLevel1(categoryInfo.get(0).getItemLevel1());
					chartParam.setItemLevel2(categoryInfo.get(0).getItemLevel2());

					chartCategory2 = chartService.getChartCategory2(chartParam.getItemLevel1());
					chartCategory3 = chartService.getChartCategory3(chartParam.getItemLevel2());
					model.addAttribute("itemLevel1", chartParam.getItemLevel1());
					model.addAttribute("itemLevel2", chartParam.getItemLevel2());
					model.addAttribute("chartCategory2", chartCategory2);
					model.addAttribute("chartCategory3", chartCategory3);
				}
			}
		}

		List<Chart> chartList = chartService.getChartItemList(chartParam);
		model.addAttribute("chartList", chartList);

		return ViewUtils.view();
	}
}
