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
import saleson.shop.item.domain.ItemAddition;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/m/item", "/m/products"})
@RequestProperty(title="????????????", template="mobile", layout="default")
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
	 * ?????? ?????? ?????????
	 * @param requestContext
	 * @param itemUserCode
	 * @param todayItems
	 * @param response
	 * @param model
	 * @param login
	 * @return
	 */
	@GetMapping({"view/{itemUserCode}", "preview/{itemUserCode}"})
	@RequestProperty(title="????????????", layout="default")
	public ModelAndView view(RequestContext requestContext,
			@PathVariable("itemUserCode") String itemUserCode,
			@CookieValue(value="TODAY_ITEMS", defaultValue="") String todayItems,
			HttpServletResponse response,
			Model model, String login) {

		// ????????? ???????????? - ???????????? ??????.
		boolean isPreview = false;
		if (requestContext.getRequestUri().indexOf("preview") > -1) {
			if (SecurityUtils.hasRole("ROLE_OPMANAGER") || SellerUtils.isSellerLogin()) {
				isPreview = true;
			} else {
				throw new PageNotFoundException();
			}
		}


		// ?????? ?????? ?????? (?????? ?????? 404)
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

		// ????????? ???????????? ??? ?????? ?????? ????????? ????????? ????????? ???????????? ??????????????? ??????.
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
			// ????????? ????????? ??????????????? ????????????
			if (!SecurityUtils.hasRole("ROLE_USER") && item.getNonmemberOrderType().equals("3")) {

				String requestUri = RequestContextUtils.getRequestUri();
				String loginPageUri = "/m/users/login?target=" + requestUri;

				final RedirectView rv = new RedirectView(loginPageUri);
				rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
				return new ModelAndView(rv);
			}

			// ?????? ????????? ????????? ????????? ????????? ?????? ????????????...
			if (item == null || !"Y".equals(item.getDisplayFlag())) {
				//throw new UserException("has no item");
				throw new PageNotFoundException();
			}

			// ?????? ??? ?????? ??????.
			String newTodayItems = ""+item.getItemId();

			if (todayItems.indexOf(newTodayItems) == -1) {
				if (!("".equals(todayItems))) {
					newTodayItems = newTodayItems + ":" + todayItems;
				}
				Cookie cookie = new Cookie("TODAY_ITEMS", newTodayItems);
				cookie.setHttpOnly(true);
				cookie.setMaxAge(60*60*24);				// ?????? ?????? ?????? - 1???
				cookie.setPath("/");					// ?????? ???????????? ?????? ???????????????
				response.addCookie(cookie);				// ????????????
			}

			// ????????? ????????? ????????? ????????????..
			/*
			if ("2".equals(item.getItemType()) && !UserUtils.isBusinessUser()) {
				throw new PageNotFoundException();
			}
				*/

			// ??????????????? ??????
			if (UserUtils.isUserLogin()) {
				RestockNotice restockNotice = new RestockNotice();
				restockNotice.setItemId(item.getItemId());
				restockNotice.setUserId(UserUtils.getUserId());
				model.addAttribute("isRestockNotice", restockNoticeService.isRestockNotice(restockNotice));
			}

		}

		// ??????????????? ????????? ?????? ?????? ?????? : ??????????????????
		int couponCount = 0;
		if (UserUtils.isUserLogin() && "Y".equals(item.getCouponUseFlag())) {
			UserCouponParam userCouponParam = new UserCouponParam();
			userCouponParam.setUserId(UserUtils.getUserId());
			userCouponParam.setItemId(item.getItemId());
			userCouponParam.setUserLevelId(UserUtils.getUserDetail().getLevelId());

			couponCount = couponService.getUserDownloadableCouponListCountByParam(userCouponParam);
		}

		// SEO ??????.
		if (isPreview) {
			item.getSeo().setIndexFlag("Y");
		}
		if (!item.getSeo().isSeoNull()) {
			ShopUtils.setSeo(item.getSeo());
		}

		// ????????? ??????
		Seller seller = sellerService.getSellerById(item.getSellerId());

		// ???????????? ????????????
		List<Integer> categoryIds = item.getItemAdditions().stream().map(ia -> ia.getCategoryId()).distinct().collect(Collectors.toList());
		Map<Integer, String> additionCategory = new HashMap<>();
		for (ItemAddition itemAddition : item.getItemAdditions()) {
			for (int categoryId : categoryIds) {
				if (itemAddition.getCategoryId() == categoryId) {
					additionCategory.put(categoryId, itemAddition.getCategoryName());
				}
			}
		}

		model.addAttribute("asp28Analytics", new Asp28Analytics(item, "Mobile"));
		model.addAttribute("naverPay", new NaverPay("Mobile", "/m/products/view/"+itemUserCode, configPgService.getConfigPg()));
		model.addAttribute("item", item);
		model.addAttribute("couponCount", couponCount);
		model.addAttribute("seller", seller);		// ????????? ??????
		model.addAttribute("config", configService.getShopConfig(Config.SHOP_CONFIG_ID));
		model.addAttribute("additionCategory", additionCategory);

		if ("T".equals(login)) {
			model.addAttribute("loginPopup", login);
		}

		OpenGraphUtils.setOpenGraphModelByItem(model, item);

		return new ModelAndView(ViewUtils.getView("/item/view"));
	}
	
	
	/**
	 * ?????? ?????? ?????????
	 * @param itemUserCode
	 * @param urlSuffix
	 * @param todayItems
	 * @param response
	 * @param model
	 * @return
	 */
	@GetMapping("view/{itemUserCode}/{urlSuffix}")
	@RequestProperty(title="????????????", layout="default")
	public ModelAndView viewDetails(@PathVariable("itemUserCode") String itemUserCode, 
			@PathVariable("urlSuffix") String urlSuffix,
			@CookieValue(value="TODAY_ITEMS", defaultValue="") String todayItems, 
			HttpServletResponse response,
			Model model) {

		throw new PageNotFoundException();
		
		//return invokeItemDetails(itemUserCode, todayItems, response, model);
	}
	
	
	/** kye ??????
	 * ?????? ????????? ??????.
	 * @param itemId
	 * @param model
	 * @return
	 */
	@GetMapping("details-image-view")
	@RequestProperty(title="???????????? ????????? ", layout="base")
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
	 * ???????????????????????? ?????? ?????? (Ajax)
	 * @param itemReviewParam
	 * @param model
	 * @return
	 */
	@PostMapping("review-list")
	@RequestProperty(layout="blank")
	public String reviewList(ItemParam itemReviewParam, Model model) {
		
		// ????????????
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
	 * ?????? ?????? ???????????? QNA ?????? (Ajax)
	 * @param qnaParam
	 * @param model
	 * @return
	 */
	@PostMapping("qna-list")
	@RequestProperty(layout="blank")
	public String qnaList(QnaParam qnaParam, Model model) {
		
		qnaParam.setQnaType(Qna.QNA_GROUP_TYPE_ITEM);
		
		// QNA ?????? ????????????
		Pagination pagination2 = Pagination.getInstance(qnaService.getQnaListCountByParam(qnaParam), 3);
		pagination2.setLink("javascript:paginationQna([page])");
		qnaParam.setPagination(pagination2);
		
		List<Qna> qnaList = qnaService.getQnaListByParam(qnaParam);
		
		model.addAttribute("pagination2", pagination2);
		model.addAttribute("qnaList", qnaList);
		
		return ViewUtils.getView("/item/qna-list");
	}
	
	
	/**
	 * [??????] ?????? ??????
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
				return ViewUtils.redirect("/m/mypage/review-nonregistered", "????????? ???????????? ????????????.", JavaScript.CLOSE);
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
	 * ???????????? ?????? ??????
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
			// ???????????? ?????? (?????? ??????, ??????)
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

		/* ????????? ????????? ???????????????.<br />?????? ???????????? ???????????? ?????? ????????? ?????? ??? ???????????????.<br />?????????????????? ?????? ????????? ????????????, ?????? ?????? ????????????.*/
		String message = MessageUtils.getMessage("M01562");	
		
		if (!StringUtils.isEmpty(target)) {
			return ViewUtils.redirect(target , message);
		}
		
		return ViewUtils.redirect("/m/products/view/" + itemUserCode , message);
		//return ViewUtils.redirect("/m/item/create-review/"+itemUserCode, message);
	}
	
	
	/**
	 * ?????? ?????? ??????
	 * @param model
	 * @return
	 */
	@GetMapping("create-review-complete")
	@RequestProperty(layout="default")
	public String createReviewComplete(CategoriesEditParam categoriesEditParam, Model model) {
		
		// 1. ?????? ?????? HTML
		
		categoriesEditParam.setType("etc");
		categoriesEditParam.setCode("review");
		categoriesEditParam.setEditKind("1");
		categoriesEditParam.setEditPosition("html1");
		
		
		CategoriesEdit categoriesEdit = mobileCategoriesEditService.getCategoryByParam(categoriesEditParam);

		
		// 2. ????????? ??????
		ItemParam newItemParam = new ItemParam();
		newItemParam.setLimit(5);
		
		List<Item> newItemList = itemService.getNewArrivalItemListForMain(newItemParam);
		
		
		// 3. ?????? ?????? ?????????. > rankingItems (?????? ?????? ??????)
		List<CategoriesTeam> categoriesTeamList = rankingService.getRankingListForMain(5);
						
		
		model.addAttribute("categoriesEdit", categoriesEdit);
		model.addAttribute("newItemList", newItemList);
		model.addAttribute("categoriesTeamList", categoriesTeamList);
		
		return ViewUtils.getView("/item/create-review-complete");
	}
	
	
	/**
	 * [??????] ??????????????? ?????? ??????.
	 * @return
	 */
	@GetMapping("wishlist-information")
	@RequestProperty(layout="base")
	public String wishlistInformation() {
		
		return ViewUtils.getView("/item/wishlist-information");
	}
	
	
	/**
	 * [??????] ???????????? ?????? ?????? ?????? ??????.
	 * @return
	 */
	@GetMapping("resale-information")
	@RequestProperty(layout="base")
	public String resaleInformation() {
		
		return ViewUtils.getView("/item/resale-information");
	}
	
	
	/**
	 * [??????] ?????? ????????????
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
		
		// ??????????????? ????????? ?????? ?????? ?????? : ??????????????????
		Item item = itemService.getItemById(itemId);
		if (item == null) {
			throw new PageNotFoundException();
		}

		setModelUserDownloadableCouponList(model, userCouponParam, item.getItemId());

		model.addAttribute("item", item);

		return ViewUtils.getView("/item/coupon");
	}

	/**
	 * [??????] ?????? ????????????
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

		// ??????????????? ????????? ?????? ?????? ?????? : ??????????????????
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
			return JsonViewUtils.exception("????????? ???????????? ?????? ???????????????.");
		}

		return JsonViewUtils.success();
	}
	
	
	/**
	 * ?????? ?????? ?????????
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String list(ItemParam itemParam, Model model) {
		
		// ??????????????? CategoryId??? ?????? ???????????? ??????.
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
	 * ???????????? ?????? 
	 * @return
	 */
	@GetMapping("searchForm")
	public String searchForm() {
		
		return ViewUtils.getView("/item/searchForm");
	}
	

	/**
	 * ?????? ??????
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
	 * ?????? ?????? - ?????????
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

		// ??????????????? ????????? ?????? ????????? ????????? ???????????? itemParam bind
		itemParam = ItemUtils.bindItemParam(itemParam);

		// ????????? ?????? ????????????
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
	 * ?????? ??????
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("searchResult")
	@RequestProperty(title="?????????")
	public String newSearchResult(ItemParam itemParam, Model model) {
		
		itemParam.setCategoryTeamCode(itemParam.getCate00team());
		
		if( itemParam.getCate00team() == null ){
			itemParam.setCategoryTeamCode("esthetic");	
		}
		
		itemParam.setDataStatusCode("1");
		if (itemParam.getItemsPerPage() == 10) {
			itemParam.setItemsPerPage(20);
		}

		// ??????????????? ????????? ?????? ????????? ????????? ???????????? itemParam bind
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
	 * ?????? ?????? ??????
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
	 * [??????] ???????????? ??????
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
	 * ???????????? ?????? ??????.
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
	 * ??????????????????
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
