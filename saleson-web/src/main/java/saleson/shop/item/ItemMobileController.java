package saleson.shop.item;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.enumeration.JavaScript;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import saleson.common.asp28.analytics.Asp28Analytics;
import saleson.common.utils.*;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.shop.cardbenefits.CardBenefitsService;
import saleson.shop.cardbenefits.domain.CardBenefits;
import saleson.shop.categoriesedit.domain.CategoriesEdit;
import saleson.shop.categoriesedit.support.CategoriesEditParam;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.domain.CouponUser;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemRelation;
import saleson.shop.item.domain.ItemReview;
import saleson.shop.item.support.ItemParam;
import saleson.shop.mobilecategoriesedit.MobileCategoriesEditService;
import saleson.shop.openmarket.domain.NaverPay;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.qna.QnaService;
import saleson.shop.qna.domain.Qna;
import saleson.shop.qna.support.QnaParam;
import saleson.shop.ranking.RankingService;
import saleson.shop.restocknotice.RestockNoticeService;
import saleson.shop.restocknotice.domain.RestockNotice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/m/item", "/m/products"})
@RequestProperty(title="상품관련", template="mobile", layout="default")
public class ItemMobileController {
	private static final Logger log = LoggerFactory.getLogger(ItemMobileController.class);

	@Autowired
	private ItemService itemService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private QnaService qnaService;

	@Autowired
	private RankingService rankingService;

	@Autowired
	private MobileCategoriesEditService mobileCategoriesEditService;

	@Autowired
	private CardBenefitsService cardBenefitsService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private RestockNoticeService restockNoticeService;

	@Autowired
	private ItemValidator itemValidator;

	@Autowired
	private ConfigPgService configPgService;

/*
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(PageNotFoundException.class)
	public ModelAndView handleBadRequest(HttpServletRequest req, Exception ex) {

		ModelAndView mav =  new ModelAndView("//default/views/front/ko/error/404");
		mav.addObject("requestContext", ThreadContextUtils.getRequestContext());
		mav.addObject("shopContext", ThreadContext.get(ShopContext.REQUEST_NAME));
		return mav;
	    //return new ErrorInfo(req.getRequestURL(), ex);
	}
	*/
	/**
	 * 상품 상세 페이지
	 * @param requestContext
	 * @param itemUserCode
	 * @param todayItems
	 * @param response
	 * @param model
	 * @param login
	 * @return
	 */
	@GetMapping({"view/{itemUserCode}", "preview/{itemUserCode}"})
	@RequestProperty(title="상품상세", layout="default")
	public ModelAndView view(RequestContext requestContext,
			@PathVariable("itemUserCode") String itemUserCode,
			@CookieValue(value="TODAY_ITEMS", defaultValue="") String todayItems,
			HttpServletResponse response,
			Model model, String login) {

		// 관리자 미리보기 - 관리자만 가능.
		boolean isPreview = false;
		if (requestContext.getRequestUri().indexOf("preview") > -1) {
			if (SecurityUtils.hasRole("ROLE_OPMANAGER") || SellerUtils.isSellerLogin()) {
				isPreview = true;
			} else {
				throw new PageNotFoundException();
			}
		}


		// 상품 정보 조회 (없는 경우 404)
		Item item = null;
		try {

			if (isPreview) {
				item = itemService.getItemByItemUserCodeForPreview(itemUserCode);
			} else {
				item = itemService.getItemByItemUserCode(itemUserCode);
			}

		} catch (Exception e) {
			throw new PageNotFoundException();
		}

		// 판매자 미리보기 인 경우 현재 상품이 판매자 상품인 경우에만 미리보기가 가능.
		if (isPreview) {
			if (SellerUtils.isSellerLogin() && item.getSellerId() != SellerUtils.getSellerId()) {
				throw new PageNotFoundException();
			} else {
				if (!SellerUtils.isSellerLogin()) {
					if (!SecurityUtils.hasRole("ROLE_OPMANAGER")) {
						throw new PageNotFoundException();
					}
				}
			}
		}

		if (!isPreview) {
			// 상품이 비회원 상세페이지 접속불가
			if (!SecurityUtils.hasRole("ROLE_USER") && item.getNonmemberOrderType().equals("3")) {

				String requestUri = RequestContextUtils.getRequestUri();
				String loginPageUri = "/m/users/login?target=" + requestUri;

				final RedirectView rv = new RedirectView(loginPageUri);
				rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
				return new ModelAndView(rv);
			}

			// 상품 정보가 없거나 비공개 상품인 경우 메인으로...
			if (item == null || !"Y".equals(item.getDisplayFlag())) {
				//throw new UserException("has no item");
				throw new PageNotFoundException();
			}

			// 오늘 본 상품 설정.
			String newTodayItems = ""+item.getItemId();

			if (todayItems.indexOf(newTodayItems) == -1) {
				if (!("".equals(todayItems))) {
					newTodayItems = newTodayItems + ":" + todayItems;
				}
				Cookie cookie = new Cookie("TODAY_ITEMS", newTodayItems);
				cookie.setHttpOnly(true);
				cookie.setMaxAge(60*60*24);				// 쿠키 유지 기간 - 1일
				cookie.setPath("/");					// 모든 경로에서 접근 가능하도록
				response.addCookie(cookie);				// 쿠키저장
			}

			// 사업자 상품인 경우는 사업자만..
			/*
			if ("2".equals(item.getItemType()) && !UserUtils.isBusinessUser()) {
				throw new PageNotFoundException();
			}
				*/

			// 재입고알림 정보
			if (UserUtils.isUserLogin()) {
				RestockNotice restockNotice = new RestockNotice();
				restockNotice.setItemId(item.getItemId());
				restockNotice.setUserId(UserUtils.getUserId());
				model.addAttribute("isRestockNotice", restockNoticeService.isRestockNotice(restockNotice));
			}

		}

		// 다운로드가 가능한 쿠폰 목록 조회 : 상품페이지용
		int couponCount = 0;
		if (UserUtils.isUserLogin() && "Y".equals(item.getCouponUseFlag())) {
			UserCouponParam userCouponParam = new UserCouponParam();
			userCouponParam.setUserId(UserUtils.getUserId());
			userCouponParam.setItemId(item.getItemId());
			userCouponParam.setUserLevelId(UserUtils.getUserDetail().getLevelId());

			couponCount = couponService.getUserDownloadableCouponListCountByParam(userCouponParam);
		}

		// SEO 설정.
		if (isPreview) {
			item.getSeo().setIndexFlag("Y");
		}
		if (!item.getSeo().isSeoNull()) {
			ShopUtils.setSeo(item.getSeo());
		}

		// 판매자 정보
		Seller seller = sellerService.getSellerById(item.getSellerId());

		model.addAttribute("asp28Analytics", new Asp28Analytics(item, "Mobile"));
		model.addAttribute("naverPay", new NaverPay("Mobile", "/m/products/view/"+itemUserCode, configPgService.getConfigPg()));
		model.addAttribute("item", item);
		model.addAttribute("couponCount", couponCount);
		model.addAttribute("seller", seller);		// 판매자 정보
		model.addAttribute("config", configService.getShopConfig(Config.SHOP_CONFIG_ID));

		if ("T".equals(login)) {
			model.addAttribute("loginPopup", login);
		}

		OpenGraphUtils.setOpenGraphModelByItem(model, item);

		return new ModelAndView(ViewUtils.getView("/item/view"));
	}
	
	
	/**
	 * 상품 상세 페이지
	 * @param itemUserCode
	 * @param urlSuffix
	 * @param todayItems
	 * @param response
	 * @param model
	 * @return
	 */
	@GetMapping("view/{itemUserCode}/{urlSuffix}")
	@RequestProperty(title="상품상세", layout="default")
	public ModelAndView viewDetails(@PathVariable("itemUserCode") String itemUserCode, 
			@PathVariable("urlSuffix") String urlSuffix,
			@CookieValue(value="TODAY_ITEMS", defaultValue="") String todayItems, 
			HttpServletResponse response,
			Model model) {

		throw new PageNotFoundException();
		
		//return invokeItemDetails(itemUserCode, todayItems, response, model);
	}
	
	
	/** kye 수정
	 * 확대 이미지 보기.
	 * @param itemId
	 * @param model
	 * @return
	 */
	@GetMapping("details-image-view")
	@RequestProperty(title="상품확대 이미지 ", layout="base")
	public String detailsImageView(@RequestParam("itemId") int itemId, Model model) {
		
		//@RequestParam("itemImageId") int itemImageId
		
		Item item = itemService.getItemById(itemId);
		
		
		if (item == null) {
			return ViewUtils.view("has no item", JavaScript.CLOSE);
		}
		
		model.addAttribute("item", item);
		//model.addAttribute("itemImageId", itemImageId);
		
		return ViewUtils.getView("/item/details-image-view");
	}
	
	/**
	 * 상품상세페이지의 리뷰 목록 (Ajax)
	 * @param itemReviewParam
	 * @param model
	 * @return
	 */
	@PostMapping("review-list")
	@RequestProperty(layout="blank")
	public String reviewList(ItemParam itemReviewParam, Model model) {
		
		// 상품리뷰
		itemReviewParam.setConditionType("FRONT_ITEM_DETAIL");
		
		int reviewCount = itemService.getItemReviewCountByParam(itemReviewParam);
		Pagination pagination = Pagination.getInstance(reviewCount, 3);
		pagination.setLink("javascript:paginationReview([page])");
		itemReviewParam.setPagination(pagination);
		
		List<ItemReview> reviewList = itemService.getItemReviewListByParam(itemReviewParam);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("reviewList", reviewList);
		
		return ViewUtils.getView("/item/review-list");
	}
	
	
	/**
	 * 상품 상세 페이지의 QNA 목록 (Ajax)
	 * @param qnaParam
	 * @param model
	 * @return
	 */
	@PostMapping("qna-list")
	@RequestProperty(layout="blank")
	public String qnaList(QnaParam qnaParam, Model model) {
		
		qnaParam.setQnaType(Qna.QNA_GROUP_TYPE_ITEM);
		
		// QNA 목록 가져오기
		Pagination pagination2 = Pagination.getInstance(qnaService.getQnaListCountByParam(qnaParam), 3);
		pagination2.setLink("javascript:paginationQna([page])");
		qnaParam.setPagination(pagination2);
		
		List<Qna> qnaList = qnaService.getQnaListByParam(qnaParam);
		
		model.addAttribute("pagination2", pagination2);
		model.addAttribute("qnaList", qnaList);
		
		return ViewUtils.getView("/item/qna-list");
	}
	
	
	/**
	 * [팝업] 리뷰 등록
	 * @param itemUserCode
	 * @param orderCode
	 * @param itemReview
	 * @param model
	 * @return
	 */
	@GetMapping("create-review/{orderCode}/{itemUserCode}")
	@RequestProperty(layout="base")
	public String createReview(
			@PathVariable("itemUserCode") String itemUserCode, 
			@PathVariable("orderCode") String orderCode,
			ItemReview itemReview, Model model) {
		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m/users/login?target=/m/item/create-review/" + itemUserCode);
		}
		
		Item item = null;
		try {
			item = itemService.getItemBy(itemUserCode);
			
			if (ValidationUtils.isNull(item) || !"Y".equals(item.getDisplayFlag())) {
				return ViewUtils.redirect("/m/mypage/review-nonregistered", "상품이 존재하지 않습니다.", JavaScript.CLOSE);
			}
			
			itemReview.setItemId(item.getItemId());
			itemReview.setOrderCode(orderCode);
			
		} catch (UserException e) {
			return ViewUtils.getView("/item/create-review", e.getMessage(), JavaScript.CLOSE);
		}
		
		model.addAttribute("item", item);
		model.addAttribute("itemReview", itemReview);
		return ViewUtils.getView("/item/create-review");
	}


	/**
	 * 상품리뷰 등록 처리
	 * @param itemUserCode
	 * @param orderCode
	 * @param imageFiles
	 * @param target
	 * @param itemReview
	 * @return
	 */
	@PostMapping("create-review/{orderCode}/{itemUserCode}")
	@RequestProperty(layout="blank")
	public String createReviewAction(
			@PathVariable("itemUserCode") String itemUserCode,
			@PathVariable("orderCode") String orderCode,
			@RequestParam(value="imageFiles[]", required=false) MultipartFile[] imageFiles,
			@RequestParam(value="target", required=false, defaultValue="") String target,
			ItemReview itemReview) {
		
		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m/users/login?target=/item/create-review/"+ orderCode + "/" + itemUserCode);
		}

		Item item = itemService.getItemBy(itemUserCode);

		if (ValidationUtils.isNull(item) || !"Y".equals(item.getDisplayFlag())) {
			return ViewUtils.redirect("/m/item/create-review/"+ orderCode +"/"+ itemUserCode);
		}

		try {
			// 이모티콘 제거 (얼굴 모양, 등등)
			itemReview.setSubject(EmojiUtils.removeEmoticon(itemReview.getSubject()));
			itemReview.setContent(EmojiUtils.removeEmoticon(itemReview.getContent()));

			itemReview.setOrderCode(orderCode);
			if (imageFiles != null) {
				itemReview.setItemReviewImageFiles(Arrays.asList(imageFiles));
			}

			itemService.insertItemReview(itemReview);

		} catch (UserException e) {
			return ViewUtils.redirect("/m/item/create-review/"+ orderCode +"/"+ itemUserCode, e.getMessage(), JavaScript.CLOSE);
		}

		/* 등록해 주셔서 감사합니다.<br />스팸 게시물을 방지하기 위해 관리자 확인 후 게재됩니다.<br />게재되기까지 다소 시간이 걸리므로, 미리 양해 바랍니다.*/
		String message = MessageUtils.getMessage("M01562");	
		
		if (!StringUtils.isEmpty(target)) {
			return ViewUtils.redirect(target , message);
		}
		
		return ViewUtils.redirect("/m/products/view/" + itemUserCode , message);
		//return ViewUtils.redirect("/m/item/create-review/"+itemUserCode, message);
	}
	
	
	/**
	 * 리뷰 작성 완료
	 * @param model
	 * @return
	 */
	@GetMapping("create-review-complete")
	@RequestProperty(layout="default")
	public String createReviewComplete(CategoriesEditParam categoriesEditParam, Model model) {
		
		// 1. 리뷰 프리 HTML
		
		categoriesEditParam.setType("etc");
		categoriesEditParam.setCode("review");
		categoriesEditParam.setEditKind("1");
		categoriesEditParam.setEditPosition("html1");
		
		
		CategoriesEdit categoriesEdit = mobileCategoriesEditService.getCategoryByParam(categoriesEditParam);

		
		// 2. 신상품 조회
		ItemParam newItemParam = new ItemParam();
		newItemParam.setLimit(5);
		
		List<Item> newItemList = itemService.getNewArrivalItemListForMain(newItemParam);
		
		
		// 3. 팀별 랭킹 리스트. > rankingItems (랭킹 상품 목록)
		List<CategoriesTeam> categoriesTeamList = rankingService.getRankingListForMain(5);
						
		
		model.addAttribute("categoriesEdit", categoriesEdit);
		model.addAttribute("newItemList", newItemList);
		model.addAttribute("categoriesTeamList", categoriesTeamList);
		
		return ViewUtils.getView("/item/create-review-complete");
	}
	
	
	/**
	 * [팝업] 위시리스트 안내 팝업.
	 * @return
	 */
	@GetMapping("wishlist-information")
	@RequestProperty(layout="base")
	public String wishlistInformation() {
		
		return ViewUtils.getView("/item/wishlist-information");
	}
	
	
	/**
	 * [팝업] 입하소식 신청 메일 안내 팝업.
	 * @return
	 */
	@GetMapping("resale-information")
	@RequestProperty(layout="base")
	public String resaleInformation() {
		
		return ViewUtils.getView("/item/resale-information");
	}
	
	
	/**
	 * [팝업] 쿠폰 다운로드
	 * @param itemId
	 * @param model
	 * @return
	 */
	@GetMapping("coupon/{itemId}")
	@RequestProperty(layout="base")
	public String coupon(@PathVariable("itemId") int itemId, Model model,
						 UserCouponParam userCouponParam) {
		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m/users/login?target=/m/item/coupon/" + itemId + "&popup=1");
		}
		
		// 다운로드가 가능한 쿠폰 목록 조회 : 상품페이지용
		Item item = itemService.getItemById(itemId);
		if (item == null) {
			throw new PageNotFoundException();
		}

		setModelUserDownloadableCouponList(model, userCouponParam, item.getItemId());

		model.addAttribute("item", item);

		return ViewUtils.getView("/item/coupon");
	}

	/**
	 * [팝업] 쿠폰 다운로드
	 * @param itemId
	 * @param model
	 * @return
	 */
	@GetMapping("coupon/{itemId}/list")
	@RequestProperty(layout="blank")
	public String couponMore(@PathVariable("itemId") int itemId, Model model,
							 UserCouponParam userCouponParam) {

		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m/users/login?target=/m/item/coupon/" + itemId + "&popup=1");
		}

		// 다운로드가 가능한 쿠폰 목록 조회 : 상품페이지용
		Item item = itemService.getItemById(itemId);
		if (item == null) {
			throw new PageNotFoundException();
		}

		setModelUserDownloadableCouponList(model, userCouponParam, item.getItemId());

		model.addAttribute("item", item);

		return ViewUtils.getView("/include/item-coupon-list");
	}


	private void setModelUserDownloadableCouponList(Model model, UserCouponParam userCouponParam, int itemId) {

		int itemsPerPage = 10;

		userCouponParam.setUserId(UserUtils.getUserId());
		userCouponParam.setItemId(itemId);
		userCouponParam.setUserLevelId(UserUtils.getUserDetail().getLevelId());

		Pagination pagination
				= Pagination.getInstance(couponService.getUserDownloadableCouponListCountByParam(userCouponParam), itemsPerPage);
		userCouponParam.setPagination(pagination);

		List<Coupon> coupons = couponService.getUserDownloadableCouponListByParam(userCouponParam);

		model.addAttribute("coupons", coupons);
		model.addAttribute("pagination", pagination);
	}

	@PostMapping("download-coupon")
	@RequestProperty(layout="base")
	public JsonView downloadCoupon(RequestContext requestContext, CouponUser couponUser) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		
		UserCouponParam userCouponParam = new UserCouponParam();
		userCouponParam.setCouponId(couponUser.getCouponId());
		if (couponService.userCouponDownload(userCouponParam) == 0) {
			return JsonViewUtils.exception("쿠폰이 다운로드 되지 않았습니다.");
		}

		return JsonViewUtils.success();
	}
	
	
	/**
	 * 상품 목록 페이지
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String list(ItemParam itemParam, Model model) {
		
		// 정렬조건은 CategoryId가 있는 경우에만 허용.
		if (itemParam.getOrderBy() != null && itemParam.getOrderBy().equals("ORDERING")
				&& (itemParam.getCategoryId() == null || itemParam.getCategoryId().equals(""))) {
			itemParam.setOrderBy("");
			itemParam.setSort("DESC");
		}

		itemParam.setDataStatusCode("1");
		
		Pagination pagination = Pagination.getInstance(itemService.getItemCount(itemParam));
		
		itemParam.setPagination(pagination);
		

		model.addAttribute("list",itemService.getItemList(itemParam));
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		
		return ViewUtils.getView("/item/list");
	}
	
	
	
	/**
	 * 카테고리 목록 
	 * @return
	 */
	@GetMapping("searchForm")
	public String searchForm() {
		
		return ViewUtils.getView("/item/searchForm");
	}
	

	/**
	 * 상단 검색
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("result")
	@RequestProperty(layout="search")
	public String searchResult(ItemParam itemParam, Model model) {
		try {
			bindResultItems(itemParam, model, false);
		} catch (RuntimeException e) {
			if ("NO_RESULT".equals(e.getMessage())) {
				model.addAttribute("itemParam",itemParam);
				model.addAttribute("asp28Analytics", new Asp28Analytics(itemParam.getQuery(), "N", "Mobile"));
				return ViewUtils.getView("/item/no-result");
			}
			throw new RuntimeException(e);
		}
		
		
		return ViewUtils.getView("/item/result");
	}
	
	
	/**
	 * 상단 검색 - 더보기
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("result/list")
	@RequestProperty(layout="blank")
	public String searchResultList(ItemParam itemParam, Model model) {
		try {
			bindResultItems(itemParam, model, true);
		} catch (RuntimeException e) {
			log.warn(e.getMessage());
		}
		
		return ViewUtils.getView("/include/item-list");
	}
	
	private void bindResultItems(ItemParam itemParam, Model model, boolean isMore) {
		itemValidator.validateItemParam(itemParam);
		itemParam.setWhere("ITEM_NAME");

		// 사용자단에 노출될 상품 조회에 필요한 기본적인 itemParam bind
		itemParam = ItemUtils.bindItemParam(itemParam);

		// 사업자 상품 조회여부
		/*
		if (!UserUtils.isBusinessUser()) {
			itemParam.setItemType("1");
		}
		*/
		
		int itemCount = itemService.getItemCount(itemParam);
		
		if (itemCount == 0 || StringUtils.isEmpty(itemParam.getQuery())) {
			throw new RuntimeException("NO_RESULT");
		}
		
		Pagination pagination = Pagination.getInstance(itemCount, itemParam.getItemsPerPage());

		String tempConditionType = ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE;

		if (isMore) {
			pagination.setLink("javascript:paginationMore([page])");
			tempConditionType = "";
		}

		ShopUtils.setPaginationInfo(pagination, tempConditionType, itemParam.getPage());

		itemParam.setPagination(pagination);
		
		List<Item> items = itemService.getItemList(itemParam);
		
		model.addAttribute("asp28Analytics", new Asp28Analytics(itemParam.getQuery(), "Y", "Mobile"));
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("items", items);
		
	}
	
	
	
	
	
	
	
	/**
	 * 상단 검색
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("searchResult")
	@RequestProperty(title="신상품")
	public String newSearchResult(ItemParam itemParam, Model model) {
		
		itemParam.setCategoryTeamCode(itemParam.getCate00team());
		
		if( itemParam.getCate00team() == null ){
			itemParam.setCategoryTeamCode("esthetic");	
		}
		
		itemParam.setDataStatusCode("1");
		if (itemParam.getItemsPerPage() == 10) {
			itemParam.setItemsPerPage(20);
		}

		// 사용자단에 노출될 상품 조회에 필요한 기본적인 itemParam bind
		itemParam = ItemUtils.bindItemParam(itemParam);
		
		Pagination pagination = Pagination.getInstance(itemService.getSearchNewArrivalItemCount(itemParam), itemParam.getItemsPerPage());
		itemParam.setPagination(pagination);
		
		List<Item> itemList = itemService.getSearchNewArrivalItemList(itemParam);
		
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("items", itemList);
		model.addAttribute("categoryTeamCode", itemParam.getCategoryTeamCode());
		model.addAttribute("priceOrderByColumn", UserUtils.isUserLogin() ? "SALE_PRICE" : "SALE_PRICE_NONMEMBER");
		
		
		return ViewUtils.getView("/item/new-search-result");
	}


	/**
	 * 상단 검색 목록
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("searchResult/list")
	@RequestProperty(layout="blank")
	public String newSearchResultList(ItemParam itemParam, Model model) {
		
		itemParam.setCategoryTeamCode(itemParam.getCate00team());
		
		if( itemParam.getCate00team() == null ){
			itemParam.setCategoryTeamCode("esthetic");	
		}
		
		itemParam = ItemUtils.bindItemParam(itemParam);

		Pagination pagination = Pagination.getInstance(itemService.getSearchNewArrivalItemCount(itemParam), itemParam.getItemsPerPage());
		pagination.setLink("javascript:paginationMore([page])");
		itemParam.setPagination(pagination);
		
		List<Item> itemList = itemService.getSearchNewArrivalItemList(itemParam);
		
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("items", itemList);
		model.addAttribute("categoryTeamCode", itemParam.getCategoryTeamCode());
		model.addAttribute("priceOrderByColumn", UserUtils.isUserLogin() ? "SALE_PRICE" : "SALE_PRICE_NONMEMBER");
		
		
		return ViewUtils.getView("/include/item-list");
	}
	
	
	/**
	 * [팝업] 상품문의 등록
	 * @param itemUserCode
	 * @param itemQna
	 * @param model
	 * @return
	 */
	@GetMapping("create-qna/{itemUserCode}")
	@RequestProperty(layout="base")
	public String createQna(@PathVariable("itemUserCode") String itemUserCode, 
		@ModelAttribute("itemQna") Qna itemQna, Model model) {

		itemUserCode = com.onlinepowers.framework.util.StringUtils.stripXSS(itemUserCode);
		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m/users/login?target=/m/item/create-qna/" + itemUserCode);
		}
		
		Item item = null;
		try {
			item = itemService.getItemBy(itemUserCode);
			
		} catch (UserException e) {
			return ViewUtils.getView("/item/create-qna", e.getMessage(), JavaScript.CLOSE);
		}

		List<Code> groups = CodeUtils.getCodeList("QNA_GROUPS");

		model.addAttribute("groups", groups);
		model.addAttribute("item", item);
		model.addAttribute("itemQna", itemQna);
		return ViewUtils.getView("/item/create-qna");
	}
	
	
	/**
	 * 상품문의 등록 처리.
	 * @param itemUserCode
	 * @param itemQna
	 * @return
	 */
	@PostMapping("create-qna/{itemUserCode}")
	@RequestProperty(layout="blank")
	public String createQnaAction(@PathVariable("itemUserCode") String itemUserCode, @ModelAttribute("itemQna") Qna itemQna) {
		
		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/m/users/login?target=/item/create-qna/" + itemUserCode);
		}
		
		Item item = null;
		try {
			item = itemService.getItemBy(itemUserCode);
			itemQna.setItemId(item.getItemId());
			//itemQna.setQnaGroup("1");
			itemQna.setQnaType(Qna.QNA_GROUP_TYPE_ITEM);
			qnaService.insertQna(itemQna);
			
		} catch (UserException e) {
			return ViewUtils.getView("/item/create-qna", e.getMessage());
		}
		
		return ViewUtils.redirect("/m/products/view/"+itemUserCode);
	}
	
	/**
	 * 카드혜택안내
	 * @param model
	 * @return
	 */
	@GetMapping("cardBenefitsPopup")
	@RequestProperty(layout="base")
	public String cardBenefitsPopup (Model model) {

		CardBenefits cardBenefits = cardBenefitsService.getTodayCardBenefits(DateUtils.getToday());
		
		model.addAttribute("cardBenefits", cardBenefits);
		return ViewUtils.getView("/item/cardBenefitsPopup");
	}

	@GetMapping("/item-others/{itemId}")
	@RequestProperty(layout="blank")
	public String getItemOthers (Model model, @PathVariable("itemId")int itemId) {

		model.addAttribute("itemOtherList", itemService.getItemOtherList(itemId));

		return "view:/item/item-others";
	}

	@GetMapping("/item-relations/{itemId}")
	@RequestProperty(layout="blank")
	public String getItemRelations (Model model,
									@PathVariable("itemId")int itemId,
									@RequestParam(name = "displayType", defaultValue = "1") String displayType) {

		List<ItemRelation> itemRelations = itemService.getItemRelationsByItemId(displayType, itemId);

		model.addAttribute("itemRelations", itemRelations);

		return "view:/item/item-relations";
	}

	@GetMapping("/benefit-info/{itemId}")
	public JsonView getItemBenefitInfo(@PathVariable("itemId")int itemId) {

		try {
			return JsonViewUtils.success(itemService.getBenefitInfoByItemId(itemId));
		} catch (Exception ignore) {
			log.error("getItemBenefitInfo", ignore);
		}

		return JsonViewUtils.success(null);

	}

	@GetMapping("/customer-info/{itemId}")
	public JsonView getItemCustomerInfo(@PathVariable("itemId")int itemId) {
		try {
			return JsonViewUtils.success(itemService.getCustomerInfoByItemId(itemId));
		} catch (Exception ignore) {
			log.error("getItemBenefitInfo", ignore);
		}

		return JsonViewUtils.success(null);
	}
}
