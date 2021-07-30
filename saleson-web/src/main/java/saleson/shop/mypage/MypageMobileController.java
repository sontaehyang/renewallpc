package saleson.shop.mypage;

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
import saleson.shop.coupon.support.CouponParam;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.ItemReview;
import saleson.shop.item.support.ItemParam;
import saleson.shop.order.OrderService;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderCount;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;
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
import saleson.shop.userlevel.UserLevelService;
import saleson.shop.userlevel.domain.UserLevel;
import saleson.shop.userlevel.support.UserLevelSearchParam;
import saleson.shop.wishlist.WishlistService;
import saleson.shop.wishlist.domain.Wishlist;
import saleson.shop.wishlist.domain.WishlistGroup;
import saleson.shop.wishlist.support.WishlistParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/m/mypage")
@RequestProperty(template="mobile", layout="default")
public class MypageMobileController {
	
	private static final Logger log = LoggerFactory.getLogger(MypageMobileController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private UserDeliveryService userDeliveryService;
	//LCH-MypageMobileController  마이페이지 <추가>
	@Autowired
	private UserLevelService userLevelService;

	@Autowired
	private ChartService chartService;

	//LCH-MypageMobileController  마이페이지 <수정>

	/**
	 * KDJ 마이페이지 (추가)
	 * @param model
	 * @return
	 */
	@GetMapping
	@Authorize("hasRole('ROLE_USER')")
	public String index(Model model) {

		
		List<OrderCount> orderCount = orderService.getUserOrderCountAll();
		
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("userDetail", UserUtils.getUserDetail());
		model.addAttribute("orderCount", orderCount);
		
		userService.setMypageUserInfoForFront(model);
		
		
		return ViewUtils.getView("/mypage/index");
	}
		
	
	/**
	 * 보유 쿠폰 내역
	 * @param model
	 * @param userCouponParam
	 * @return
	 */
	@GetMapping("download-coupon-list")
	@Authorize("hasRole('ROLE_USER')")
	public String downloadCouponList(Model model, @ModelAttribute("userCouponParam") UserCouponParam userCouponParam,
			HttpServletResponse response) {
		
		userCouponParam.setConditionType(ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE);
		initDownloadCouponList(userCouponParam, model);
	
		model.addAttribute("couponDownloadCount", 0);
		
		return ViewUtils.view();
	}
	
	/**
	 * 보유 쿠폰 내역 더보기
	 * @param model
	 * @param userCouponParam
	 * @param
	 * @return
	 */
	//LCH-MypageMobileController   - 쿠폰사용페이지 / 더보기버튼 이벤트   <수정>
	@RequestProperty(layout="blank")
	@GetMapping("download-coupon-list/list")
	@Authorize("hasRole('ROLE_USER')")
	public String downloadCouponListPaging(Model model, @ModelAttribute("userCouponParam") UserCouponParam userCouponParam) {

		initDownloadCouponList(userCouponParam, model);
		
		return ViewUtils.getView("/include/mypage-coupon-list");
	}
	
	/**
	 * 보유 쿠폰 내역
	 * @param userCouponParam
	 * @param model
	 */
	private void initDownloadCouponList(UserCouponParam userCouponParam, Model model) {
		userCouponParam.setItemsPerPage(10);
		
		if (userCouponParam.getPage() == 0) {
			userCouponParam.setPage(1);
		}
		
		userCouponParam.setUserId(UserUtils.getUserId());
		int totalCount = couponService.getDownloadUserCouponCountByUserCouponParam(userCouponParam);
		
		Pagination pagination = Pagination.getInstance(totalCount, userCouponParam.getItemsPerPage());
		
		ShopUtils.setPaginationInfo(pagination, userCouponParam.getConditionType(), userCouponParam.getPage());
		
		userCouponParam.setPagination(pagination);
			
		List<CouponUser> list = couponService.getDownloadUserCouponListByUserCouponParam(userCouponParam);

		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("completedUserCouponCount", couponService.getCompletedUserCouponCountByUserCouponParam(userCouponParam));
		
	}
	
	/**
	 * 사용 + 만료 쿠폰 내역
	 * @param model
	 * @param userCouponParam
	 * @return
	 */
	@GetMapping("completed-coupon-list")
	@Authorize("hasRole('ROLE_USER')")
	public String completedCouponList(Model model, @ModelAttribute("userCouponParam") UserCouponParam userCouponParam,
			HttpServletResponse response) {

		userCouponParam.setConditionType(ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE);
		initCompletedCouponList(userCouponParam, model);
		
		model.addAttribute("downloadCouponCount", couponService.getDownloadUserCouponCountByUserCouponParam(userCouponParam));
		
		return ViewUtils.view();

	}
	
	/**
	 * 사용 + 만료 쿠폰 내역 더보기
	 * @param model
	 * @param userCouponParam
	 * @param
	 * @return
	 */
	//LCH-MypageMobileController   - 쿠폰사용페이지 / 더보기버튼 이벤트   <수정>
	@RequestProperty(layout="blank")
	@PostMapping("completed-coupon-list/list")
	@Authorize("hasRole('ROLE_USER')")
	public String completedCouponListPaging(Model model, @ModelAttribute("userCouponParam") UserCouponParam userCouponParam) {

		initCompletedCouponList(userCouponParam, model);
		
		return ViewUtils.getView("/include/mypage-coupon-list");
		
	}
	
	/**
	 * 사용 + 만료 쿠폰 내역
	 * @param userCouponParam
	 * @param model
	 */
	private void initCompletedCouponList(UserCouponParam userCouponParam, Model model) {
		
		userCouponParam.setItemsPerPage(10);
		
		if (userCouponParam.getPage() == 0) {
			userCouponParam.setPage(1);
		}
		
		userCouponParam.setUserId(UserUtils.getUserId());
		int totalCount = couponService.getCompletedUserCouponCountByUserCouponParam(userCouponParam);
		
		Pagination pagination = Pagination.getInstance(totalCount, userCouponParam.getItemsPerPage());
		
		ShopUtils.setPaginationInfo(pagination, userCouponParam.getConditionType(), userCouponParam.getPage());
		
		userCouponParam.setPagination(pagination);
			
		List<CouponUser> list = couponService.getCompletedUserCouponListByUserCouponParam(userCouponParam);
		
		model.addAttribute("userCouponParam", userCouponParam);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("downloadCouponCount", couponService.getDownloadUserCouponCountByUserCouponParam(userCouponParam));
		
	}

	/**
	 * 마이페이지 쿠폰 다운로드 화면
	 * @param model
	 * @param couponParam
	 * @param userCouponParam
	 * @return
	 */
	//LCH-MypageMobileController   - 쿠폰다운로드페이지   <수정>
	@GetMapping("coupon-download")
	@Authorize("hasRole('ROLE_USER')")
	public String couponDownload(Model model, CouponParam couponParam,
								 UserCouponParam userCouponParam) {

		setModelUserDownloadableCouponList(model, couponParam, userCouponParam);

		return ViewUtils.getView("/mypage/coupon-download");
	}

	@GetMapping("coupon-download/list")
	@Authorize("hasRole('ROLE_USER')")
	@RequestProperty(layout = "blank")
	public String couponDownloadMore(Model model, CouponParam couponParam,
									 UserCouponParam userCouponParam) {

		setModelUserDownloadableCouponList(model, couponParam, userCouponParam);

		return ViewUtils.getView("/include/mypage-coupon-download-list");
	}

	private void setModelUserDownloadableCouponList(Model model, CouponParam couponParam, UserCouponParam userCouponParam) {
		int itemsPerPage = 10;

		UserDetail userDetail = UserUtils.getUserDetail();

		userCouponParam.setUserId(UserUtils.getUserId());
		userCouponParam.setUserLevelId(userDetail.getLevelId());

		//orderCount로 첫구매인지 아닌지 판단
		OrderParam orderParam = new OrderParam();
		orderParam.setUserId(userCouponParam.getUserId());
		int orderCount = orderService.getOrderCountByParam(orderParam);
		userCouponParam.setOrderCount(orderCount);
		userCouponParam.setViewTarget("list"); // 자동발행 쿠폰은 목록에서 보이지 않도록 구분[2017-09-18]minae.yun

		Pagination pagination
				= Pagination.getInstance(couponService.getUserDownloadableCouponListCountByParam(userCouponParam), itemsPerPage);
		userCouponParam.setPagination(pagination);

		userCouponParam.setPagination(pagination);

		model.addAttribute("list", couponService.getUserDownloadableCouponListByParam(userCouponParam));
		model.addAttribute("pagination", pagination);
		/*model.addAttribute("list", couponService.getDownloadUserCouponListByUserCouponParam(userCouponParam));*/

	}
	
	/**
	 * 마이페이지 쿠폰 다운로드
	 * @param couponId
	 * @param requestContext
	 * @return
	 */
	//LCH-MypageMobileController   - 쿠폰다운로드페이지 / 쿠폰다운버튼 이벤트    <수정>
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
			return JsonViewUtils.exception("쿠폰이 다운로드 되지 않았습니다.");
		}
		
		return JsonViewUtils.success();
	}
	
	/**
	 * 오프라인 쿠폰 전환
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
	 * 오프라인 쿠폰 사용처리
	 * @author [2017-09-11]minae.yun
	 * @param offlineCode
	 * @param requestContext
	 * @return
	 */
	@PostMapping("offline-coupon-exchange")
	@Authorize("hasRole('ROLE_USER')")
	public JsonView exchangeOfflineCouponProcess(String offlineCode, RequestContext requestContext) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}

		if (couponService.downloadDirectInputCoupon(offlineCode)) {
			return JsonViewUtils.success();
		}

		long userId = UserUtils.getUserId();

		offlineCode = couponService.getOfflineCode(offlineCode);

		//오프라인 쿠폰 전환시 전환 가능 확인
		CouponOffline couponOffline = new CouponOffline();
		couponOffline.setCouponOfflineCode(offlineCode);
		couponOffline.setUserId(userId);
		couponOffline = couponService.getCouponOfflineByOfflineCode(couponOffline);
		if (StringUtils.isEmpty(couponOffline)) {
			return JsonViewUtils.failure(MessageUtils.getMessage("M00297")); // 조회된 데이터가 없습니다.
		} else {
			//사용가능하도록 업데이트 상태 업데이트 후 사용가능 쿠폰에 넣기
			couponOffline.setUserId(userId);
			couponService.updateCouponOffline(couponOffline);
			
			UserCouponParam userCouponParam = new UserCouponParam();
			userCouponParam.setCouponId(couponOffline.getCouponId());
			userCouponParam.setViewTarget("offlineCoupon"); //쿼리에서 where 조건 구분하기 위해 viewTarget사용
			if (couponService.userCouponDownload(userCouponParam) == 0) {
				return JsonViewUtils.exception("쿠폰이 다운로드 되지 않았습니다.");
			}
		}
		
		return JsonViewUtils.success();
	}
	
	
	/**
	 * 관심상품
	 * @param response
	 * @param model
	 * @return
	 */
	@GetMapping("/wishlist")
	@Authorize("hasRole('ROLE_USER')")
	public String wishlist(HttpServletResponse response, Model model, WishlistParam wishlistParam) {
		
		wishlistParam.setConditionType(ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE);
		invokeWishlistProcess(wishlistParam, model);
		return ViewUtils.view();
	}
	
	/**
	 * 관심상품
	 * @param response
	 * @param model
	 * @return
	 */
	@PostMapping("/wishlist/list")
	@RequestProperty(layout="blank")
	@Authorize("hasRole('ROLE_USER')")
	public String wishlistMore(HttpServletResponse response, Model model, WishlistParam wishlistParam) {
		
		invokeWishlistProcess(wishlistParam, model);
		return ViewUtils.getView("/include/mypage-wishlist-list");
	}
	
	/**
	 * 관심상품 그룹에 해당하는 상품 조회 
	 * @param wishlistParam
	 * @param model
	 */
	private void invokeWishlistProcess(WishlistParam wishlistParam, Model model) {
		
		wishlistParam.setItemsPerPage(10);
		
		if (wishlistParam.getPage() == 0) {
			wishlistParam.setPage(1);
		}
		
		wishlistParam.setUserId(UserUtils.getUserId());
		
		int totalCount = wishlistService.getWishlistCountByParam(wishlistParam);
		Pagination pagination = Pagination.getInstance(totalCount, wishlistParam.getItemsPerPage());
		
		ShopUtils.setPaginationInfo(pagination, wishlistParam.getConditionType(), wishlistParam.getPage());
		
		wishlistParam.setPagination(pagination);
		
		List<Wishlist> wishlists = wishlistService.getWishlistListByParam(wishlistParam);
		
		
		model.addAttribute("totalItemCount", totalCount);
		model.addAttribute("wishlists", wishlists);
		model.addAttribute("pagination", pagination);
	}
	
	/**
	 * 1:1문의
	 * @param qnaParam
	 * @param qna
	 * @param model
	 * @return
	 */
	//LCH-MypageMobileController  - 1:1문의페이지   <수정>
	@GetMapping("inquiry")
	@Authorize("hasRole('ROLE_USER')")
	public String inquiry(@ModelAttribute("qnaParam") QnaParam qnaParam, Qna qna, Model model) {
		
		qnaParam.setConditionType(ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE);
		setQnaList(model, qnaParam, Qna.QNA_GROUP_TYPE_INDIVIDUAL);
		
		String today = DateUtils.getToday(Const.DATE_FORMAT);
		
		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month3", DateUtils.addYearMonthDay(today, 0, -3, 0));
		model.addAttribute("count",qnaParam.getPagination().getTotalItems());
		model.addAttribute("pagination", qnaParam.getPagination());
		
		return ViewUtils.view();
	}
	
	

	/**
	 * 1:1문의 더보기 
	 * @param model
	 * @return
	 */
	//LCH-MypageMobileController  - 1:1문의페이지   / 더보기 이벤트 <추가>
	@RequestProperty(layout="blank")
	@PostMapping("inquiry/list")
	@Authorize("hasRole('ROLE_USER')")
	public String inquiryList(Model model, @ModelAttribute("qnaParam") QnaParam qnaParam, Qna qna) {
		
		setQnaList(model, qnaParam, Qna.QNA_GROUP_TYPE_INDIVIDUAL);
		
		return ViewUtils.getView("/include/mypage-inquiry-list");
	}
	
	/**
	 * 
	 * @param qnaId
	 * @return
	 */
	@GetMapping(value="/inquiry-delete/{viewName}/{qnaId}")
	@Authorize("hasRole('ROLE_USER')")
	public String inquiryItemListDelete(@PathVariable("viewName") String viewName,
			@PathVariable("qnaId") int qnaId) {
		
		Qna tempQna = qnaService.getQnaByQnaId(qnaId);
		
		if (tempQna.getAnswerCount() > 0) {
			return ViewUtils.redirect("/m/mypage/" + viewName, "답변완료된 문의는 삭제하실 수 없습니다.");
		}
		
		
		Qna qna = new Qna();
		qna.setQnaId(qnaId);
		qnaService.deleteQna(qna);
		return ViewUtils.redirect("/m/mypage/" + viewName);
	}
	
	
	
	@GetMapping("inquiry-item")
	@Authorize("hasRole('ROLE_USER')")
	public String inquiryItem(@ModelAttribute("qnaParam") QnaParam qnaParam, Qna qna, Model model) {
		
		qnaParam.setConditionType(ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE);
		setQnaList(model, qnaParam, Qna.QNA_GROUP_TYPE_ITEM);
		
		String today = DateUtils.getToday(Const.DATE_FORMAT);
		
		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month3", DateUtils.addYearMonthDay(today, 0, -3, 0));
		model.addAttribute("count",qnaParam.getPagination().getTotalItems());
		model.addAttribute("pagination", qnaParam.getPagination());
		
		return ViewUtils.view();
	}
	
	/**
	 * 1:1문의 더보기 
	 * @param model
	 * @return
	 */
	//LCH-MypageMobileController  - 1:1문의페이지   / 더보기 이벤트 <추가>
	@RequestProperty(layout="blank")
	@PostMapping("inquiry-item/list")
	@Authorize("hasRole('ROLE_USER')")
	public String inquiryItemList(Model model, @ModelAttribute("qnaParam") QnaParam qnaParam, Qna qna) {
		
		setQnaList(model, qnaParam, Qna.QNA_GROUP_TYPE_ITEM);
		return ViewUtils.getView("/include/mypage-inquiry-item-list");
	}
	
	private void setQnaList(Model model, QnaParam qnaParam, String qnaType){
		User user = UserUtils.getUser();
		qnaParam.setUserId(user.getUserId());
		qnaParam.setQnaType(qnaType);
		
		qnaParam.setItemsPerPage(10);
		
		if (qnaParam.getPage() == 0) {
			qnaParam.setPage(1);
		}
		
		qnaService.setQnaListPagination(qnaParam);
		
		List<Qna> qnaList = qnaService.getQnaListByParam(qnaParam);	
		
		if (qnaType.equals(Qna.QNA_GROUP_TYPE_INDIVIDUAL)) {
			
			List<Code> qnaGroups = CodeUtils.getCodeList("QNA_GROUPS");
			for (Qna qnaCheck : qnaList) {
				for (Code code : qnaGroups) {
	
					if (qnaCheck.getQnaGroup().equals(code.getId())) {
						qnaCheck.setQnaGroup(code.getLabel());
					}
				}
			}
			
		}
		model.addAttribute("qnaList", qnaList);
	}
	
	/**kye 수정
	 * 마이페이지 - 리뷰리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("review")
	@Authorize("hasRole('ROLE_USER')")
	public String review(ItemParam searchParam, Model model) {
		
		if (!UserUtils.isUserLogin()) {
			throw new PageNotFoundException();
		}
		
		setReviewList(searchParam, model, false);
		
		String today = DateUtils.getToday(Const.DATE_FORMAT);
	
		return ViewUtils.view();
	}
	
	
	
	/**
	 * 마이페이지 - 리뷰 더보기
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("review/list")
	@RequestProperty(layout="blank")
	@Authorize("hasRole('ROLE_USER')")
	public String reviewPage(ItemParam searchParam, Model model) {

		setReviewList(searchParam, model, true);
		
		return ViewUtils.getView("/include/mypage-review-list");
	}
	
	public void setReviewList(ItemParam searchParam, Model model, boolean isMore){
		
		searchParam.setItemsPerPage(5);
		
		if (searchParam.getPage() == 0) {
			searchParam.setPage(1);
		}
		
		searchParam.setUserId(UserUtils.getUserId());
		
		searchParam.setConditionType("FRONT_MYPAGE");
		int reviewCount = itemService.getItemReviewCountByParam(searchParam);

		searchParam.setAdditionItemFlag("N");
		int nonReviewCount = itemService.getItemNonregisteredReviewCount(searchParam);
		
		String tempConditionType = "";
		
		if (!isMore) {
			tempConditionType = ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE;
		}
		
		Pagination pagination = Pagination.getInstance(reviewCount,searchParam.getItemsPerPage());
		
		ShopUtils.setPaginationInfo(pagination, tempConditionType, searchParam.getPage());
		
		searchParam.setPagination(pagination);
		
		model.addAttribute("reviewCount",reviewCount);
		model.addAttribute("nonReviewCount",nonReviewCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("recommendFlag", searchParam.getRecommendFlag());
		model.addAttribute("reviewList", itemService.getItemReviewListByParam(searchParam));
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("reviewPageType", "review");
	}
	
	/** kye 추가
	 * 마이페이지 - 미등록 리뷰리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("review-nonregistered")
	@Authorize("hasRole('ROLE_USER')")
	public String reviewNone(ItemParam searchParam, Model model) {
		
		
		if (!UserUtils.isUserLogin()) {
			throw new PageNotFoundException();
		}
		
		setNonregisteredReviewList(searchParam, model, false);
		
		return ViewUtils.view();
	}
	
	
	
	/** kye 추가
	 * 마이페이지 - 리뷰 더보기
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("review-nonregistered/{page}")
	@RequestProperty(layout="blank")
	@Authorize("hasRole('ROLE_USER')")
	public String reviewNonePage(ItemParam searchParam, Model model) {

		setNonregisteredReviewList(searchParam, model, true);
		
		return ViewUtils.getView("/include/mypage-review-list");
	}
	
	
	public void setNonregisteredReviewList(ItemParam searchParam, Model model, boolean isMore){
		
		searchParam.setItemsPerPage(5);
		
		if (searchParam.getPage() == 0) {
			searchParam.setPage(1);
		}
		
		searchParam.setUserId(UserUtils.getUserId());

		int reviewCount = itemService.getItemReviewCountByParam(searchParam);

		searchParam.setAdditionItemFlag("N");
		int nonReviewCount = itemService.getItemNonregisteredReviewCount(searchParam);
		
		String tempConditionType = "";
		
		if (!isMore) {
			tempConditionType = ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE;
		}
		
		Pagination pagination = Pagination.getInstance(nonReviewCount,searchParam.getItemsPerPage());
		
		ShopUtils.setPaginationInfo(pagination, tempConditionType, searchParam.getPage());
		
		searchParam.setPagination(pagination);
		
		model.addAttribute("reviewCount",reviewCount);
		model.addAttribute("nonReviewCount",nonReviewCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("recommendFlag", searchParam.getRecommendFlag());
		model.addAttribute("reviewList", itemService.getItemNonregisteredReviewList(searchParam));
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("reviewPageType", "nonReview");
	}
	
	
	/**
	 * 마이페이지 - 리뷰 상세
	 * @param reviewId
	 * @param model
	 * @return
	 */
	@GetMapping("review-detail/{reviewId}")
	@Authorize("hasRole('ROLE_USER')")
	public String reviewDetail(@PathVariable("reviewId") int reviewId, Model model) {
		
		ItemReview itemReview = itemService.getItemReviewById(reviewId);
		
		model.addAttribute("itemReview", itemReview);
		
		return ViewUtils.getView("/mypage/review-detail");
	}
	
	
	
	/**
	 * 마이페이지 - 리뷰 수정
	 * @param reviewId
	 * @param model
	 * @return
	 */
	@GetMapping("review-edit/{reviewId}")
	@Authorize("hasRole('ROLE_USER')")
	public String reviewEdit(@PathVariable("reviewId") int reviewId, Model model) {
		
		ItemReview itemReview = itemService.getItemReviewById(reviewId);
		
		model.addAttribute("itemReview", itemReview);
		
		return ViewUtils.getView("/mypage/review-edit");
	}
	
	
	
	/**
	 * 마이페이지 - 리뷰 수정처리
	 * @param itemReview
	 * @param model
	 * @return
	 */
	@PostMapping("review-edit/{reviewId}")
	@Authorize("hasRole('ROLE_USER')")
	public String reviewEditAction(@PathVariable("reviewId") int reviewId, ItemReview itemReview, Model model) {
		
		itemReview.setItemReviewId(reviewId);
		itemService.updateItemReview(itemReview);
		
		return ViewUtils.redirect("/m/mypage/review-detail/" + reviewId, "수정되었습니다.");
	}
	
	
	
	/**
	 * 마이페이지 - 리뷰 삭제
	 * @param reviewId
	 * @return
	 */
	@GetMapping("review/delete/{reviewId}")
	@Authorize("hasRole('ROLE_USER')")
	public String reviewDelete(@PathVariable("reviewId") int reviewId) {
		
		itemService.deleteItemReview(reviewId);
		
		return ViewUtils.redirect("/m/mypage/review?recommendFlag=N", "삭제되었습니다.");
	}
	
	
	
	/**
	 * 마이페이지 접속 시 공통 모델 바인딩. (관심상품 그룹)
	 * @return
	 */
	@ModelAttribute("wishlistGroups")
	public List<WishlistGroup> wishlistGroups() {
		List<WishlistGroup> wishlistGroups = new ArrayList<>();
		
		return wishlistGroups;
	}
	
	
	
	@GetMapping("delivery")
	@Authorize("hasRole('ROLE_USER')")
	public String delivery(Model model) {
		model.addAttribute("list", userDeliveryService.getUserDeliveryList(UserUtils.getUserId()));
		return ViewUtils.view();
	}
	
	
	
	/*@GetMapping(value="delivery/edit", method=RequestMethod.POST)
	@Authorize("hasRole('ROLE_USER')")
	public String deliveryedit(@PathVariable("userDeliveryId") int userDeliveryId, Model model, UserDelivery userDelivery ){
		public String deliveryedit(UserDelivery userDelivery, 
								   @RequestParam(value="userDeliveryId", required=true) int userDeliveryId
								  ){
		userDelivery.setUserDeliveryId(userDeliveryId);
		userDeliveryService.updateUserDelivery(userDelivery);
		System.out.println("^&^&^&^&^&&^&^&^^");
		System.out.println(userDelivery.getUserDeliveryId());
		
		return ViewUtils.redirect("sp/mypage/delivery", "수정되었습니다.");
		
	}*/
	
	
	
	/**
	 * 구매 확정
	 * @param requestContext
	 * @param orderSearchParam
	 * @return
	 */
	@PostMapping("confirm-purchase")
	public JsonView confirmPurchase(RequestContext requestContext, OrderParam orderSearchParam) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		if (UserUtils.isUserLogin()) {
			orderSearchParam.setUserId(UserUtils.getUserId());
		}
		
		try {
			orderService.updateConfirmPurchase(orderSearchParam);
			return JsonViewUtils.success(); 
		} catch (OrderException ex) {
			return JsonViewUtils.exception(ex.getMessage());
		}
		
	} 
	
	/**
	 * 주문내역
	 * @param model
	 * @param orderParam
	 * @return
	 */
	@GetMapping("order")
	public String order(Model model, @ModelAttribute("orderParam") OrderParam orderParam) {
		
		orderParam.setConditionType(ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE);
		orderList(orderParam, model);
		
		return ViewUtils.view();
	}
	
	/**
	 * 주문내역 - 더보기
	 * @param orderParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="blank")
	@PostMapping("order/list")
	public String orderItems( OrderParam orderParam, Model model) {
		
		orderList(orderParam, model);
		return ViewUtils.getView("/include/mypage-order-list");
		
	}
	
	/**
	 * 주문내역 - 목록
	 * @param orderParam
	 * @param model
	 */
	private void orderList(OrderParam orderParam, Model model) {
		orderParam.setItemsPerPage(10);
		if (orderParam.getPage() == 0) {
			orderParam.setPage(1);
		}
		
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

		if (StringUtils.isEmpty(orderParam.getSearchDate()) == false) {
			
			orderParam.setSearchDateType("OI.CREATED_DATE");
			orderParam.setSearchStartDate(ShopUtils.getSearchStartDate(orderParam.getSearchDate()));
			orderParam.setSearchEndDate(DateUtils.getToday(Const.DATE_FORMAT));
			
		}

		orderParam.setAdditionItemFlag("N");
		List<Order> orderList = orderService.getOrderListByParam(orderParam);

		model.addAttribute("list", orderList);
		model.addAttribute("totalCount", orderParam.getPagination().getTotalItems());
		model.addAttribute("pagination", orderParam.getPagination());
	}
	
	
	/**
	 * KDJ (추가)
	 * 모바일 - 주문내역 상세
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

		// 상품단위로 처리되는 기존 로직을 유지하기 위해 orderItems에 추가한 추가구성상품 삭제
		for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
			List<OrderItem> itemList = orderShippingInfo.getOrderItems()
					.stream().filter(l -> "N".equals(l.getAdditionItemFlag())).collect(Collectors.toList());

			orderShippingInfo.getOrderItems().clear();
			orderShippingInfo.getOrderItems().addAll(itemList);
		}
		
		model.addAttribute("order", order);
		return ViewUtils.getView("/mypage/order-detail");
	}
	
	
	
	/**
	 * KDJ (추가)
	 * 모바일 - 나의 등급안내
	 * @param model
	 * @param userLevelSearchParam
	 * @return
	 */
	@GetMapping("grade")
	public String grade(Model model, @ModelAttribute("userLevelSearchParam") UserLevelSearchParam userLevelSearchParam,
			@ModelAttribute("groupSearchParam") UserLevelSearchParam groupSearchParam){
		
		if (UserUtils.isUserLogin()) {
			UserDetail userDetail = UserUtils.getUserDetail();
			userLevelSearchParam.setLevelId(userDetail.getLevelId());
			
			if("".equals(userDetail.getGroupCode()) || userDetail.getGroupCode() == null){
				userDetail.setGroupCode("default");
			}
			groupSearchParam.setGroupCode(userDetail.getGroupCode());
			
		} else {
			throw new PageNotFoundException();
			
		}
		
		User user = UserUtils.getUser();
		UserLevel userLevel = UserUtils.getUserDetail().getUserlevel();
		List<UserLevel> userLevels = userLevelService.getUserLevelList(groupSearchParam);
		
		model.addAttribute("user", user);
		model.addAttribute("userLevel", userLevel);
		model.addAttribute("userLevels", userLevels);
		
		return ViewUtils.getView("/mypage/grade");
	}
	
	/**
	 * 포인트 적립 내역
	 * @param model
	 * @return
	 */
	@GetMapping("{pointType}-save-list")
	@Authorize("hasRole('ROLE_USER')")
	public String pointSaveList(Model model, @PathVariable("pointType") String pointType, 
			@ModelAttribute("pointParam") PointParam pointParam) {
		
		pointParam.setConditionType(ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE);
		setPointSaveListModel(model, pointType, pointParam);
		
		return "view:/mypage/point-save-list";
	}
	
	/**
	 * 포인트 적립 내역
	 * @param model
	 * @return
	 */
	@PostMapping("{pointType}-save-list/list")
	@Authorize("hasRole('ROLE_USER')")
	@RequestProperty(layout="blank")
	public String pointSaveListMore(Model model, @PathVariable("pointType") String pointType, 
			@ModelAttribute("pointParam") PointParam pointParam) {
		
		setPointSaveListModel(model, pointType, pointParam);
		
		return ViewUtils.getView("/include/mypage-point-save-list");
	}
	
	private void setPointSaveListModel(Model model, String pointType, PointParam pointParam) {
		pointParam.setItemsPerPage(10);
		
		if (pointParam.getPage() == 0) {
			pointParam.setPage(1);
		}
		
		pointParam.setPointType(pointType);
		pointParam.setUserId(UserUtils.getUserId());
		
		int totalCount = pointService.getPointCountByParam(pointParam);
		
		Pagination pagination = Pagination.getInstance(totalCount, pointParam.getItemsPerPage());
		
		ShopUtils.setPaginationInfo(pagination, pointParam.getConditionType(), pointParam.getPage());
		
		pointParam.setPagination(pagination);
		
		List<Point> list = pointService.getPointListByParam(pointParam);
		
		userService.setMypageUserInfoForFront(model);
		
		model.addAttribute("expirationPointAmount", pointService.getNextMonthExpirationPointAmountByParam(pointParam));
		model.addAttribute("pointType", pointType);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		
	}
	
	/**
	 * 포인트 적립 내역
	 * @param model
	 * @return
	 */
	@GetMapping("{pointType}-used-list")
	@Authorize("hasRole('ROLE_USER')")
	public String pointUsedList(Model model, @PathVariable("pointType") String pointType, 
			@ModelAttribute("pointParam") PointParam pointParam) {
		
		pointParam.setConditionType(ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE);
		setPointUsedListModel(model, pointType, pointParam);
		
		return "view:/mypage/point-used-list";
	}
	
	/**
	 * 포인트 적립 내역
	 * @param model
	 * @return
	 */
	@PostMapping("{pointType}-used-list/{page}")
	@Authorize("hasRole('ROLE_USER')")
	@RequestProperty(layout="blank")
	public String pointUsedListMore(Model model, @PathVariable("pointType") String pointType, 
			@ModelAttribute("pointParam") PointParam pointParam) {
		
		setPointUsedListModel(model, pointType, pointParam);
		
		return ViewUtils.getView("/include/mypage-point-used-list");
	}
	
	private void setPointUsedListModel(Model model, String pointType, PointParam pointParam) {
		
		pointParam.setItemsPerPage(10);
		
		if (pointParam.getPage() == 0) {
			pointParam.setPage(1);
		}
		
		pointParam.setPointType(pointType);
		pointParam.setUserId(UserUtils.getUserId());
		
		int totalCount = pointService.getPointUsedCountByParam(pointParam);
		Pagination pagination = Pagination.getInstance(totalCount, pointParam.getItemsPerPage());
		
		ShopUtils.setPaginationInfo(pagination, pointParam.getConditionType(), pointParam.getPage());
		
		pointParam.setPagination(pagination);
		
		List<PointUsed> list = pointService.getPointUsedListByParam(pointParam);
		
		userService.setMypageUserInfoForFront(model);
		
		model.addAttribute("expirationPointAmount", pointService.getNextMonthExpirationPointAmountByParam(pointParam));
		model.addAttribute("pointType", pointType);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		
		
		
	}



	/**
	 * 매입단가표
	 * @param
	 * @return
	 */
	@GetMapping("/chart")
	public String chart(Model model, Chart chart, @RequestParam(value="searchName", required = false) String searchName ) {
		ChartParam chartParam = new ChartParam();
		List<Chart> chartCategory1, chartCategory2, chartCategory3 = null;

		if(searchName != null){
			chartParam.setItemName(searchName);
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
				// 월드와이드 메모리와 ERP DB 데이터가 상이해서 HDD/SSD 전용 로직 생성 추후 분리 되면 제거
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
					model.addAttribute("itemLevel3", chart.getItemLevel3());
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