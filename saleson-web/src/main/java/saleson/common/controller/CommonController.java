package saleson.common.controller;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;

import oracle.jdbc.proxy.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import saleson.common.utils.PointUtils;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.cart.CartService;
import saleson.shop.cart.support.CartParam;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.display.DisplayService;
import saleson.shop.display.domain.DisplayImage;
import saleson.shop.display.support.DisplayParam;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.support.ItemParam;
import saleson.shop.order.OrderService;
import saleson.shop.order.domain.BuyItem;
import saleson.shop.order.domain.ItemPrice;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.AvailablePoint;
import saleson.shop.remittance.RemittanceService;
import saleson.shop.remittance.support.RemittanceParam;
import saleson.shop.shadowlogin.ShadowLoginLogService;
import saleson.shop.stats.StatsService;
import saleson.shop.storeinquiry.StoreInquiryService;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.wishlist.WishlistService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@Controller
@RequestMapping("/common/")
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private CartService cartService;
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StoreInquiryService storeInquiryService;
	
	@Autowired
	private StatsService statsService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private RemittanceService remittanceService;
	
	@Autowired
	private ShadowLoginLogService shadowLoginLogService;

	@Autowired
	private DisplayService displayService;
	
	@GetMapping("shadow-logout-log")
	public JsonView shadowLogoutLog() {
				
		try {
			
			User user = UserUtils.getUser();
			if (user != null && user.isShadowLogin()) {
				UserDetail userDetail = (UserDetail) user.getUserDetail();
				shadowLoginLogService.updateShadowLogoutLog(userDetail.getShadowLoginLogId());
			}
			
		} catch (Exception e) {
			log.warn("shadow logout error : {}" , e.getMessage(), e);
		}
		
		return JsonViewUtils.success();

	}

	
	@PostMapping("opmanager/order-count")
	public JsonView opmanagerOrderLnbCount(RequestContext requestContext,
										   @RequestParam(name = "month", defaultValue = "0") int month) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		if (month > 0) {
			return JsonViewUtils.success(orderService.getOpmanagerOrderCountAllByMonth(month));
		}
		
		return JsonViewUtils.success(orderService.getOpmanagerOrderCountAll());
	}

	
	/**
	 * ????????? [2017-05-11 ??????]
	 * ????????? ?????? ?????????, ??????????????? ?????????
	 * @param requestContext
	 * @return
	 */
	@PostMapping("opmanager/user-count")
	public JsonView opmanagerUserLnbCount(RequestContext requestContext) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		
		return JsonViewUtils.success(userService.getOpmanagerUserCountAll());
	}
	
	/**
	 * ????????? [2017-05-11 ??????]
	 * ?????? ??????, ?????? ?????????
	 * @param requestContext
	 * @return
	 */
	@PostMapping("opmanager/remittance-count")
	public JsonView opmanagerRemittanceLnbCount(RequestContext requestContext) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		//?????? ??????
		RemittanceParam remittanceParam = new RemittanceParam();
		remittanceParam.setStartDate(DateUtils.getToday());
		remittanceParam.setEndDate(DateUtils.getToday());
		
		return JsonViewUtils.success(remittanceService.getOpmanagerRemittanceCountAll(remittanceParam));
	}
	
	/**
	 * ????????? [2017-05-11 ??????]
	 * ??????,??????,?????? ?????? ?????????
	 * @param requestContext
	 * @param type
	 * @return
	 */
	@PostMapping("opmanager/shipping-delay-count")
	public JsonView opmanagerShippingDelayLnbCount(RequestContext requestContext, String type) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		
		//????????? ?????? - ?????? shopconfig?????? ???????????? ???????????? ???????????????.
		HashMap<String, Object> map = new HashMap<>();
		map.put("shippingDelay", 1);
		map.put("exchangeDelay", 3);
		map.put("returnDelay", 3);
		
		
		if (SellerUtils.isSellerLogin()) {
			long sellerId = 0;
			if (SecurityUtils.hasRole("OPMANAGER") && SellerUtils.isShadowSellerLogin()) {
				sellerId = SellerUtils.getShadowSeller().getSellerId();
			} else {
				sellerId = SellerUtils.getSeller().getSellerId();
			}
			
			map.put("sellerId", sellerId);
		}
		
		return JsonViewUtils.success(orderService.getOpmanagerShippingDelayCountAll(map));
	}
	
	@PostMapping("user/order-count")
	public JsonView userOrderLnbCount(RequestContext requestContext,
									  @RequestParam(name = "month", defaultValue = "0") int month) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		if (month > 0) {
			return JsonViewUtils.success(orderService.getUserOrderCountAllByMonth(month));
		}


		return JsonViewUtils.success(orderService.getUserOrderCountAll());
	}
	
	@PostMapping("seller/order-count")
	public JsonView sellerOrderLnbCount(RequestContext requestContext,
										@RequestParam(name = "month", defaultValue = "0") int month) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		if (month > 0) {
			return JsonViewUtils.success(orderService.getSellerOrderCountAllByMonth(month));
		}

		return JsonViewUtils.success(orderService.getSellerOrderCountAll());
	}

	@PostMapping("opmanager/store-count")
	public JsonView opmanagerStoreLnbCount(RequestContext requestContext) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		return JsonViewUtils.success(storeInquiryService.getOpmanagerStoreCountAll());
	}





	@PostMapping("island-type")
	public JsonView island(@RequestParam("zipcode") String zipcode) {

		HashMap<String, String> map = new HashMap<>();
		map.put("islandType", orderService.getIslandTypeByZipcode(zipcode));
		return JsonViewUtils.success(map);
		
	}




	@PostMapping("message")
	public JsonView message(@RequestParam("messageCode") String messageCode) {
		return JsonViewUtils.success(MessageUtils.getMessage(messageCode));
	}
	
	
	/**
	 * ???????????? ??? ???????????? (??????????????? ????????? ??????) 
	 * @param session
	 * @return
	 */
	@PostMapping("wishlist")
	public JsonView wishlist(HttpSession session) {
		
		int count = 0;
		if (UserUtils.isUserLogin()) {
			count = wishlistService.getWishlistCountByUserId(UserUtils.getUserId());
		}
		
		return JsonViewUtils.success(count);
	}
//
//	/**
//	 * ?????? ?????? ????????????
//	 * @param session
//	 * @return
//	 */
//	@PostMapping("cart")
//	public JsonView cart(HttpSession session) {
//		// ???????????? ?????? ?????? ??????
//    	CartParam cartParam = new CartParam();
//
//    	cartParam.setUserId(UserUtils.getUserId());
//    	cartParam.setSessionId(session.getId());
//    	List<BuyItem> cartList = cartService.getCartList(cartParam, false);
//
//    	int cartQuantity = 0;
//    	int cartPrice = 0;
//    	for (BuyItem buyItem : cartList) {
//
//    		ItemPrice itemPrice = buyItem.getItemPrice();
//
//    		cartQuantity += itemPrice.getQuantity();
//    		//cartQuantity++;
//    		cartPrice += (itemPrice.getSaleAmount());
//		}
//
//
//    	HashMap<String, Object> result = new HashMap<>();
//    	result.put("cartQuantity", cartQuantity);
//    	result.put("cartPrice", StringUtils.numberFormat(cartPrice));
//    	result.put("cartList", cartList);
//
//
//		return JsonViewUtils.success(result);
//	}
//

	/**
	 * @param session
	 * @return
	* */
	@PostMapping("cart")
	public JsonView cart(HttpSession session){
		CartParam cartParam = new CartParam();

		cartParam.setUserId(UserUtils.getUserId());
		cartParam.setSessionId(session.getId());

		List<BuyItem> cartList = cartService.getCartList(cartParam, false);

		int cartQuantity = 0;
		int cartPrice = 0;

		for(BuyItem buyItem : cartList){
			ItemPrice itemPrice = buyItem.getItemPrice();
			cartQuantity += itemPrice.getQuantity();
			cartPrice += (itemPrice.getSaleAmount());
		}
		HashMap<String, Object> result = new HashMap<>();
		result.put("cartQuantity", cartQuantity);
		result.put("carPrice",StringUtils.numberFormat(cartPrice));
		result.put("cartList",cartList);

		return JsonViewUtils.success(result);

	}




	
	/**
	 * ?????? ?????? ????????? ??????
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("visitor-log")
	public JsonView visitorLog(HttpServletRequest request,  HttpServletResponse response) {
		try {
			// ?????? ?????? ??????.
			statsService.saveVisitData(request, response);
		} catch(Exception e) {
			log.warn("statsService.saveVisitData(..) : {}" , e.getMessage(), e);
		}
		
		return JsonViewUtils.success();
	}
	
	/**
	 * ?????? ?????? ??? ????????? ?????? ?????? ????????????
	 * @param session
	 * @return
	 */
	@PostMapping("coupon")
	public JsonView coupon (HttpSession session) {
		
		int userCouponCount = 0;
		int userShippingCount = 0;
		if (UserUtils.isUserLogin()) {
			UserCouponParam userCouponParam = new UserCouponParam();
			userCouponParam.setUserId(UserUtils.getUserId());
			
			userCouponCount = couponService.getDownloadUserCouponCountByUserCouponParam(userCouponParam);
			
			// ??? ???????????? ?????? ??????
			AvailablePoint avilableShippingCoupon = pointService.getAvailablePointByUserId(UserUtils.getUserId(), PointUtils.SHIPPING_COUPON_CODE);
			
			userShippingCount = avilableShippingCoupon.getAvailablePoint();
		}
		HashMap<String, Integer> map = new HashMap<>();
		
		map.put("userCouponCount", userCouponCount);
		map.put("userShippingCount", userShippingCount);
		
		return JsonViewUtils.success(map);
	}
	
	/**
	 * ????????? ??????
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("today-items")
	public JsonView todayItems(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		List<Item> todayItems = new ArrayList<>();
		ItemParam itemParam = new ItemParam(); 
		Pagination pagination = Pagination.getInstance(6, 6);
		itemParam.setPagination(pagination);
		
		try{
    		if (cookies != null && cookies.length > 0) {
    			for (int i = 0; i < cookies.length; i++) {
    				String imageRoot = "";
    				if (cookies[i].getName().equals("TODAY_ITEMS")) {
    					String todayItemsInfo = cookies[i].getValue();
    					
    					if (!"".equals(todayItemsInfo)) {
	    					itemParam.setTodayItemIds(todayItemsInfo.toUpperCase().replaceAll("SELECT", "").replaceAll("DELETE", "").replaceAll("UPDATE", "").replaceAll("INSERT", "").replaceAll(":", ","));
	    					itemParam.setDisplayFlag("Y");
	    					
	    					//todayItems = itemService.getItemList(itemParam);
	    					todayItems = itemService.getTodayItemList(itemParam);
	    					//????????? ????????? ????????? ?????? ?????????URL??? ?????????[2017-06-05]minae.yun
	    					for (int j = 0; j < todayItems.size(); j++) {
	    						imageRoot = ShopUtils.loadImage(todayItems.get(j).getItemCode(), todayItems.get(j).getItemImage(), "XS");
	    						if (imageRoot != null) todayItems.get(j).setItemImage(imageRoot);
	    					}
    					}
    				}
    			}
    		}
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);

			Cookie cookie = new Cookie("TODAY_ITEMS", "");
			cookie.setHttpOnly(true);
		    cookie.setMaxAge(60*60*24);				// ?????? ?????? ?????? - 1???
		    cookie.setPath("/");					// ?????? ???????????? ?????? ??????????????? 
		    response.addCookie(cookie);				// ????????????
			
		}
		return JsonViewUtils.success(todayItems);
	}

	/**
	 * ?????? ??? ??????
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("top-banner")
	public JsonView topBanner(HttpServletRequest request, HttpServletResponse response) {
		DisplayParam displayParam = new DisplayParam();
		displayParam.setDisplayGroupCode("front-top");
		displayParam.setViewTarget("ALL");

		List<DisplayImage> displayImages = displayService.getDisplayImageListByParam(displayParam);

		return JsonViewUtils.success(displayImages);
	}
	
	/**
	 * ?????? ????????? ??????
	 * @param item
	 * @return
	 */
	@PostMapping("/update-item-hits")
	public JsonView updateItemHits(Item item) {
		// ?????? ?????? ??? ??????.
		itemService.updateItemHitsByItemId(item.getItemId());
		return JsonViewUtils.success();
	}

}
