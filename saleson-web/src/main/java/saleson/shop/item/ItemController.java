package saleson.shop.item;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.enumeration.JavaScript;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.notification.NotificationService;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import saleson.common.asp28.analytics.Asp28Analytics;
import saleson.common.notification.message.ShopMailMessage;
import saleson.common.utils.*;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.shop.cardbenefits.CardBenefitsService;
import saleson.shop.cardbenefits.domain.CardBenefits;
import saleson.shop.cart.CartService;
import saleson.shop.cart.domain.Cart;
import saleson.shop.categories.domain.Breadcrumb;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.domain.CouponUser;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.item.domain.*;
import saleson.shop.item.support.ItemParam;
import saleson.shop.openmarket.domain.NaverPay;
import saleson.shop.order.OrderService;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.qna.QnaService;
import saleson.shop.qna.domain.Qna;
import saleson.shop.qna.support.QnaParam;
import saleson.shop.restocknotice.RestockNoticeService;
import saleson.shop.restocknotice.domain.RestockNotice;
import saleson.shop.wishlist.domain.WishlistGroup;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/item", "/products"})
@RequestProperty(title="????????????", layout="sub")
public class ItemController {
	private static final Logger log = LoggerFactory.getLogger(ItemController.class);

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
	private SellerService sellerService;

	@Autowired

	private CardBenefitsService cardBenefitsService;
	@Autowired
	private OrderService orderService;

	@Autowired
	private RestockNoticeService restockNoticeService;

	@Autowired
	@Qualifier("mailService")
	private NotificationService mailService;

	@Autowired
	private ItemValidator itemValidator;

	@Autowired
	private ConfigPgService configPgService;

	@Autowired
	private CartService cartService;

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
	 * @return
	 */
	@GetMapping({"view/{itemUserCode}", "preview/{itemUserCode}"})
	@RequestProperty(title="????????????", layout="default")
	public ModelAndView view(RequestContext requestContext,
			@PathVariable("itemUserCode") String itemUserCode,
			@CookieValue(value="TODAY_ITEMS", defaultValue="") String todayItems,
			HttpServletResponse response,
			Model model) {

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
			throw new UserException(e.getMessage());
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
				String loginPageUri = "/users/login?target=" + requestUri;

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


		// CJH 2017.03.27 ?????? ????????? ?????? ??????
		if (UserUtils.isUserLogin() && "Y".equals(item.getCouponUseFlag())) {

			UserCouponParam userCouponParam = new UserCouponParam();
			userCouponParam.setUserId(UserUtils.getUserId());
			userCouponParam.setItemId(item.getItemId());
			userCouponParam.setViewTarget("WEB");

			List<Coupon> couponForItem = couponService.getCouponForItemView(userCouponParam);
			model.addAttribute("coupon", ShopUtils.getLargestRateCoupon(couponForItem, item));
		}

		// SEO ??????.
		if (isPreview) {
			item.getSeo().setIndexFlag("Y");
		}

		if(!item.getSeo().isSeoNull()) {
			ShopUtils.setSeo(item.getSeo());
		}



		// ?????? ????????????
		List<List<Code>> breadcrumbsForSelectbox = null;
		if (ValidationUtils.isNotNull(item.getBreadcrumbs())) {
			if (item.getBreadcrumbs().size() > 0) {
				Breadcrumb breadcrumb = item.getBreadcrumbs().get(0);
				breadcrumbsForSelectbox = ShopUtils.getBreadcrumbsForSelectbox(breadcrumb);
			}
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

		model.addAttribute("asp28Analytics", new Asp28Analytics(item, "Web"));
		model.addAttribute("naverPay", new NaverPay("Web", "/products/view/"+itemUserCode, configPgService.getConfigPg()));
		model.addAttribute("item", item);
		model.addAttribute("breadcrumbsForSelectbox", breadcrumbsForSelectbox);
		model.addAttribute("couponCount", couponCount);
		model.addAttribute("seller", seller);		// ????????? ??????
		model.addAttribute("config", configService.getShopConfig(Config.SHOP_CONFIG_ID));
		model.addAttribute("additionCategory", additionCategory);

		OpenGraphUtils.setOpenGraphModelByItem(model, item);

		return new ModelAndView(ViewUtils.getView("/item/view"));
	}

	/**
	 * ?????? ?????? [popup]
	 * @param model
	 * @return
	 */
	@GetMapping("cardBenefits-popup")
	@RequestProperty(layout="base")
	public String cardBenefitsPopup(CardBenefits cardBenefits, Model model) {

		cardBenefits = cardBenefitsService.getCardBenefits(cardBenefits.getBenefitsId());
		
		model.addAttribute("cardBenefits", cardBenefits);
		
		
		return ViewUtils.getView("/item/cardBenefits-popup");
	}
	
	/**
	 * ?????? ?????? ?????????
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


	/**
	 * ?????? ????????? ??????.
	 * @param itemId
	 * @param itemImageId
	 * @param model
	 * @return
	 */
	@GetMapping("details-image-view")
	@RequestProperty(layout="base")
	public String detailsImageView(@RequestParam("itemId") int itemId, 
			@RequestParam("itemImageId") int itemImageId,
			Model model) {

		Item item = itemService.getItemById(itemId);


		if (item == null) {
			return ViewUtils.view("has no item", JavaScript.CLOSE);
		}

		model.addAttribute("item", item);
		model.addAttribute("itemImageId", itemImageId);

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

		itemReviewParam.setConditionType("FRONT_ITEM_DETAIL");
		
		int reviewCount = itemService.getItemReviewCountByParam(itemReviewParam);
		Pagination pagination = Pagination.getInstance(reviewCount, 5);
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

		// QNA ?????? ????????????
		qnaParam.setQnaType(Qna.QNA_GROUP_TYPE_ITEM);

		Pagination pagination2 = Pagination.getInstance(qnaService.getQnaListCountByParam(qnaParam));
		pagination2.setLink("javascript:paginationQna([page])");
		qnaParam.setPagination(pagination2);

		List<Qna> qnaList = qnaService.getQnaListByParam(qnaParam);

		List<Code> qnaGroups = CodeUtils.getCodeList("QNA_GROUPS");

		for (Qna qnaCheck : qnaList) {

			for (Code code : qnaGroups) {

				if (qnaCheck.getQnaGroup().equals(code.getId())) {
					qnaCheck.setQnaGroup(code.getLabel());
				}
			}
		}

		model.addAttribute("pagination2", pagination2);
		model.addAttribute("qnaList", qnaList);

		return ViewUtils.getView("/item/qna-list");
	}


	/**
	 * ???????????? ??????
	 * @param openerReload
	 * @param orderCode
	 * @param itemUserCode
	 * @param itemReview
	 * @param model
	 * @param orderItem
	 * @return
	 */
	@GetMapping("create-review{openerReload}/{orderCode}/{itemUserCode}")
	@RequestProperty(layout="base")
	public String createReview(
			@PathVariable("openerReload") String openerReload, 
			@PathVariable("orderCode") String orderCode,
			@PathVariable("itemUserCode") String itemUserCode, 
			ItemReview itemReview, Model model, OrderItem orderItem) {
		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/users/login?target=/item/create-review-after-login/" + itemUserCode + "&popup=1");
		}
		else {
			long userId = UserUtils.getUserId();
			
			orderItem.setOrderCode(orderCode);
			orderItem.setUserId(userId);
			orderItem.setItemUserCode(itemUserCode);
			
			/*Map<String, String> map = new HashMap<>();
			map.put("userId", String.valueOf(userId));
			map.put("itemUserCode", itemUserCode);*/
			
//			int reviewCount = itemService.getreviewCount(itemParam);
			int reviewCount = orderService.getOrderItemCntForReview(orderItem);
			
			if(!(reviewCount > 0)) {
				//throw new UserException("????????? ?????? ???????????????.");
				return ViewUtils.getView("/item/create-review", "??????????????? ????????????.", JavaScript.CLOSE);
			}

			Item item = itemService.getItemBy(itemUserCode);
			
			if (ValidationUtils.isNull(item) || !"Y".equals(item.getDisplayFlag())) {
				return ViewUtils.getView("/item/create-review", "????????? ???????????? ????????????.", JavaScript.CLOSE);
			}
			
			try {

				itemReview.setItemId(item.getItemId());
				itemReview.setOrderCode(orderCode);

			} catch (UserException e) {
				return ViewUtils.getView("/item/create-review", e.getMessage(), JavaScript.CLOSE);
			}

			model.addAttribute("openerReload", openerReload);
			model.addAttribute("item", item);
			model.addAttribute("itemReview", itemReview);
			return ViewUtils.getView("/item/create-review");
		}
	}

	/**
	 * ???????????? ?????? ??????
	 * @param itemUserCode
	 * @param orderCode
	 * @param imageFiles
	 * @param itemReview
	 * @return
	 */
	@PostMapping("create-review{openerReload}/{orderCode}/{itemUserCode}")
	@RequestProperty(layout="blank")
	public String createReviewAction(
			@PathVariable("itemUserCode") String itemUserCode, 
			@PathVariable("orderCode") String orderCode,
			@RequestParam(value="imageFiles[]", required=false) MultipartFile[] imageFiles,
			ItemReview itemReview) {

		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/users/login?target=/item/create-review/"+ orderCode +"/"+ itemUserCode + "&popup=1");
		}
		
		Item item = itemService.getItemBy(itemUserCode);
		
		if (ValidationUtils.isNull(item) || !"Y".equals(item.getDisplayFlag())) {
			return ViewUtils.redirect("/item/create-review/"+ orderCode +"/"+ itemUserCode);
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
			return ViewUtils.redirect("/item/create-review/"+ orderCode +"/"+ itemUserCode, e.getMessage(), JavaScript.CLOSE);
		}

		/* ????????? ????????? ???????????????.<br />?????? ???????????? ???????????? ?????? ????????? ?????? ??? ???????????????.<br />?????????????????? ?????? ????????? ????????????, ?????? ?????? ????????????.*/
		String message = MessageUtils.getMessage("M01562");
		return ViewUtils.redirect("/item/create-review/"+ orderCode +"/"+ itemUserCode, message, JavaScript.CLOSE_AND_OPENER_RELOAD);
	}


	/**
	 * ???????????? ??????
	 * @param qna
	 * @param openerReload
	 * @param itemUserCode
	 * @param itemReview
	 * @param model
	 * @return
	 */
	@GetMapping("create-qna{openerReload}/{itemUserCode}")
	@RequestProperty(layout="base")
	public String createQna(
			Qna qna,
			@PathVariable("openerReload") String openerReload, 
			@PathVariable("itemUserCode") String itemUserCode, 
			ItemReview itemReview, Model model) {
		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/users/login?target=/item/create-qna-after-login/" + itemUserCode + "&popup=1");
		}

		Item item = null;
		try {
			item = itemService.getItemBy(itemUserCode);
			itemReview.setItemId(item.getItemId());

		} catch (UserException e) {
			return ViewUtils.getView("/item/create-qna", e.getMessage(), JavaScript.CLOSE);
		}

		List<Code> groups = CodeUtils.getCodeList("QNA_GROUPS");
		
		model.addAttribute("groups", groups);
		model.addAttribute("openerReload", openerReload);
		model.addAttribute("item", item);
		model.addAttribute("qna", qna);
		return ViewUtils.getView("/item/create-qna");
	}


	/**
	 * ???????????? ?????? ??????
	 * @param itemUserCode
	 * @param qna
	 * @return
	 */
	@PostMapping("create-qna{openerReload}/{itemUserCode}")
	@RequestProperty(layout="blank")
	public String createQnaAction(@PathVariable("itemUserCode") String itemUserCode, Qna qna) {

		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/users/login?target=/item/create-qna/" + itemUserCode + "&popup=1");
		}

		try {
			// ???????????? ?????? (?????? ??????, ??????)
			qna.setSubject(EmojiUtils.removeEmoticon(qna.getSubject()));
			qna.setQuestion(EmojiUtils.removeEmoticon(qna.getQuestion()));

			// ?????? ?????? ?????? ??????
			//qna.setQnaGroup("1");
			qna.setQnaType(Qna.QNA_GROUP_TYPE_ITEM);
			qnaService.insertQna(qna);
		} catch (UserException e) {
			return ViewUtils.getView("/item/create-qna", e.getMessage(), JavaScript.CLOSE);
		}


		String message = "?????????????????????.";	
		return ViewUtils.redirect("/item/create-qna/" + itemUserCode, message, JavaScript.CLOSE_AND_OPENER_RELOAD);
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
	 * @param openerReload
	 * @return
	 */
	@GetMapping("coupon{openerReload}/{itemId}")
	@RequestProperty(layout="base")
	public String coupon(@PathVariable("itemId") int itemId, Model model,
			@PathVariable("openerReload") String openerReload) {
		
		if (!UserUtils.isUserLogin()) {
			return ViewUtils.redirect("/users/login?target=/item/coupon-after-login/" + itemId + "&popup=1");
		}

		// ??????????????? ????????? ?????? ?????? ?????? : ??????????????????
		Item item = itemService.getItemById(itemId);
		if (item == null) {
			throw new PageNotFoundException();
		}
		
		UserCouponParam userCouponParam = new UserCouponParam();
		userCouponParam.setUserId(UserUtils.getUserId());
		userCouponParam.setItemId(item.getItemId());
		userCouponParam.setUserLevelId(UserUtils.getUserDetail().getLevelId());
		
		List<Coupon> coupons = couponService.getUserDownloadableCouponListByParam(userCouponParam);

		model.addAttribute("openerReload", openerReload);
		model.addAttribute("item", item);
		model.addAttribute("coupons", coupons);
		return ViewUtils.getView("/item/coupon");
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
	 * ???????????? ?????????
	 * @param itemUserCode
	 * @param model
	 * @return
	 */
	@GetMapping("send-recommend-mail/{itemUserCode}")
	@RequestProperty(layout="base")
	public String sendRecommendMail(@PathVariable("itemUserCode") String itemUserCode, Model model) {

		Item item = itemService.getItemByItemUserCode(itemUserCode);

		if (item == null) {
			throw new PageNotFoundException();
		}

		model.addAttribute("itemUserCode", itemUserCode);

		return ViewUtils.getView("/item/send-recommend-mail");
	}



	/**
	 * ?????? ?????? ??????.
	 * @param recommendMail
	 * @param model
	 * @return
	 */
	@PostMapping("send-recommend-mail/{itemUserCode}")
	@RequestProperty(layout="base")
	public String sendRecommendMail(RecommendMail recommendMail, Model model) {

		Item item = itemService.getItemByItemUserCode(recommendMail.getItemUserCode());

		if (item == null) {
			throw new PageNotFoundException();
		}
		String title = "[????????????] " +  recommendMail.getTitle();
		String content = recommendMail.getContent() + "\n\n <a href=\"/products/view/" + recommendMail.getItemUserCode() + "\" target=\"_blank\">????????????</a>";

		// ????????????.
		try {

			ShopMailMessage shopMailMessage = new ShopMailMessage(recommendMail.getReceiverEmail(), title, content, recommendMail.getSenderEmail());
			shopMailMessage.setHtml(true);

			mailService.sendMessage(shopMailMessage);

		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);

		}

		model.addAttribute("itemUserCode", recommendMail.getItemUserCode());
		model.addAttribute("recommendMail", recommendMail);
		return ViewUtils.getView("/item/send-recommend-mail", "?????? ????????? ?????????????????????.", JavaScript.CLOSE);

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
	@RequestProperty(layout="default")
	public String searchResult(ItemParam itemParam, Model model) {
		itemValidator.validateItemParam(itemParam);
		itemParam.setWhere("ITEM_NAME");

		/*
		// SEO
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);
		Seo seo = shopContext.getSeo();

		String searchWord = itemParam.getQuery();

		seo.setTitle(seo.getTitle().replace("{search_word}", searchWord));
		seo.setKeywords(seo.getKeywords().replace("{search_word}", searchWord));
		seo.setDescription(seo.getDescription().replace("{search_word}", searchWord));
		seo.setHeaderContents1(seo.getHeaderContents1().replace("{search_word}", searchWord));
		seo.setThemawordTitle(seo.getThemawordTitle().replace("{search_word}", searchWord));
		seo.setThemawordDescription(seo.getThemawordDescription().replace("{search_word}", searchWord));

		shopContext.setSeo(seo);
		 */

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
			model.addAttribute("itemParam",itemParam);
			model.addAttribute("asp28Analytics", new Asp28Analytics(itemParam.getQuery(), "N", "Web"));
			return ViewUtils.getView("/item/no-result");
		}

		Pagination pagination = Pagination.getInstance(itemCount, itemParam.getItemsPerPage());
		itemParam.setPagination(pagination);

		List<Item> items = itemService.getItemList(itemParam);

		// ??????????????? ?????? ????????????
		List<WishlistGroup> wishlistGroups = new ArrayList<>();
		
		String wishlistGroupId = "";

		if (!wishlistGroups.isEmpty()) {
			wishlistGroupId = String.valueOf(wishlistGroups.get(0).getWishlistGroupId());
		}

		model.addAttribute("wishlistGroupId", wishlistGroupId);

		model.addAttribute("asp28Analytics", new Asp28Analytics(itemParam.getQuery(), "Y", "Web"));
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("items", items);


		return ViewUtils.getView("/item/result");
	}


	/**
	 * ?????? ??????
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("searchResult")
	@RequestProperty(title="?????????", layout="sub")
	public String newSearchResult(ItemParam itemParam, Model model) {

		itemParam.setCategoryTeamCode(itemParam.getCate00team());

		if( itemParam.getCate00team() == null ){
			itemParam.setCategoryTeamCode("esthetic");	
		}

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
		model.addAttribute("priceOrderByColumn", "SALE_PRICE");


		return ViewUtils.getView("/item/new-search-result");
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
	public JsonView getItemCustomerInfo(@PathVariable("itemId") int itemId) {
		try {
			return JsonViewUtils.success(itemService.getCustomerInfoByItemId(itemId));
		} catch (Exception ignore) {
			log.error("getItemBenefitInfo", ignore);
		}

		return JsonViewUtils.success(null);
	}

	@PostMapping("/popup/quotation")
	@RequestProperty(layout="base")
	public String popupQuotation(Cart cart, Model model) {

		// ???????????? ????????? ??????
		Buy buy = cartService.getBuyInfoByQuotation(cart);

		model.addAttribute("buy", buy);
		model.addAttribute("today", DateUtils.getToday());
		return ViewUtils.view();
	}
}
