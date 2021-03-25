package saleson.shop.main;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.DeviceUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.UserUtils;
import saleson.model.Faq;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.*;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.config.domain.Config;
import saleson.shop.display.DisplayService;
import saleson.shop.display.domain.DisplayImage;
import saleson.shop.display.domain.DisplayItem;
import saleson.shop.display.support.DisplayItemParam;
import saleson.shop.faq.FaqDto;
import saleson.shop.faq.FaqService;
import saleson.shop.featured.FeaturedService;
import saleson.shop.featured.support.FeaturedParam;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.support.ItemParam;
import saleson.shop.notice.NoticeService;
import saleson.shop.notice.support.NoticeParam;
import saleson.shop.popup.PopupService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
@RequestProperty(template="front", layout="main", title="메인")
public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private PopupService popupService;
	
	@Autowired
	private NoticeService noticeService;

	@Autowired
	private FaqService faqService;

	@Autowired
	private	 CategoriesTeamGroupService categoriesTeamGroupService;
	
	@Autowired
	private DisplayService displayService;

	@Autowired
	private FeaturedService featuredService;
	
	@Autowired
	Environment environment;

	/**
	 * 메인페이지
	 * @return
	 */
	@GetMapping(value="/")
	@RequestProperty(template="front", layout="main", title="메인")
	public String index(Model model) {

		// 전시 배너 조회 (메인, 상단, 중단, 텍스트, 현금특가...)
		displayService.setMainDisplayByGroupCode(model, "front-promotion", "front-text", "front-middle", "front-cash-specials");
		model.addAttribute("lnbType", "main");

		return ViewUtils.getView("/main/index");
	}
	
	@PostMapping("/main/lnb-group-banners")
	@RequestProperty(layout="blank")
	public String lnbGroupBanners(Model model) {
		// 1. 카테고리 정보
    	log.debug("[Cache] categoriesService.getCategoriesForFront");
    	List<Team> categories = categoriesService.getCategoriesForFront();
    	
    	List<Group> shopCategoryGroups = new ArrayList<>();
    	HashMap<String, List<Item>> groupBannerItemsByGroup = new HashMap<>();
    	for(Team team : categories) {
    		if (Config.SHOP_CATEGORY_GROUP_KEY.equals(team.getUrl())) {
				shopCategoryGroups.addAll(team.getGroups());
    		}
    	}
    	
    	for (Group group : shopCategoryGroups) {
			if (StringUtils.isNotEmpty(group.getItemList())) {
				groupBannerItemsByGroup.put(group.getUrl(), itemService.getItemListForGroupBanner(group.getItemList()));
			}
		}

    	model.addAttribute("shopCategoryGroups", shopCategoryGroups);
		model.addAttribute("groupBannerItemsByGroup", groupBannerItemsByGroup);
		model.addAttribute("groupBanners", categoriesTeamGroupService.getCategoriesTeamGroupList()); 	// 0. LNB 그룹 배너.   	
		return "view";
	}
	
	/**
	 * 메인 - 카테고리별 인기상품
	 * @param model
	 * @return
	 */
	@PostMapping("/main/best-items")
	@RequestProperty(layout="blank")
	public String bestItems(Model model) {

		DisplayItemParam param = new DisplayItemParam();
		param.setDisplayGroupCode("best");
		if (!UserUtils.isManagerLogin()) {
			// 전용상품 조회
			param.setPrivateTypes(ItemUtils.getPrivateTypes());
		}

		// 전체 DISPLAY ITEM 조회
		List<DisplayItem> displayItems = displayService.getDisplayItemListByParam(param);

		// 전체 DISPLAY IMAGE 조회
		param.setViewTarget(null);
		List<DisplayImage> displayImages = displayService.getDisplayImageListByParam(param);

		// 그룹별 이미지, 카테고리별 상품 매핑
		HashMap<String, List<DisplayItem>> displayItemMap = new HashMap<>();
		HashMap<String, List<DisplayImage>> displayImageMap = new HashMap<>();

		int itemLimit = 6;
		for (DisplayItem displayItem : displayItems) {
			if (displayItemMap.get(displayItem.getDisplaySubCode()) == null) {
				displayItemMap.put(displayItem.getDisplaySubCode(), new ArrayList<>());
			}

			if (displayItemMap.get(displayItem.getDisplaySubCode()).size() >= itemLimit) {
				continue;
			}

			displayItemMap.get(displayItem.getDisplaySubCode()).add(displayItem);
		}

		int imageLimit = 3;
		for (DisplayImage displayImage : displayImages) {
			if (displayImageMap.get(displayImage.getViewTarget()) == null) {
				displayImageMap.put(displayImage.getViewTarget(), new ArrayList<>());
			}

			if (displayImageMap.get(displayImage.getViewTarget()).size() >= imageLimit) {
				continue;
			}

			displayImageMap.get(displayImage.getViewTarget()).add(displayImage);
		}

		// 전시 설정된 카테고리 정보만 세팅
		List<Team> teams = categoriesService.getCategoriesForFront();

		List<Breadcrumb> breadcrumbs = new ArrayList<>();
		for (Team team : teams) {
			if (Config.SHOP_CATEGORY_GROUP_KEY.equals(team.getUrl())) {

				for (Group group : team.getGroups()) {
					Breadcrumb breadcrumb = new Breadcrumb();
					List<BreadcrumbCategory> breadcrumbCategories = new ArrayList<>();

					// 이미지 배너가 설정되어 있을 경우, 카테고리 정보 세팅
					if (!ObjectUtils.isEmpty(displayImageMap.get(group.getUrl()))) {
						breadcrumb.setGroupUrl(group.getUrl());
						breadcrumb.setGroupName(group.getName());
					}

					for (Category category1 : group.getCategories()) {
						// 상품이 설정되어 있을 경우, 카테고리 정보 세팅
						if (!ObjectUtils.isEmpty(displayItemMap.get(category1.getCategoryId()))) {
							breadcrumb.setGroupUrl(group.getUrl());
							breadcrumb.setGroupName(group.getName());

							BreadcrumbCategory breadcrumbCategory = new BreadcrumbCategory();

							breadcrumbCategory.setCategoryId(category1.getCategoryId());
							breadcrumbCategory.setCategoryName(category1.getName());
							breadcrumbCategory.setCategoryUrl(category1.getUrl());
							breadcrumbCategory.setGroupUrl(group.getUrl());

							breadcrumbCategories.add(breadcrumbCategory);
						}
					}

					// 카테고리 정보 add
					if (!StringUtils.isEmpty(breadcrumb.getGroupName()) && !StringUtils.isEmpty(breadcrumb.getGroupUrl())) {
						breadcrumb.setBreadcrumbCategories(breadcrumbCategories);
						breadcrumbs.add(breadcrumb);
					}
				}

			}
		}

		model.addAttribute("breadcrumbs", breadcrumbs);

		model.addAttribute("displayItemMap", displayItemMap);
		model.addAttribute("displayImageMap", displayImageMap);

		return "view";
	}

	/**
	 * 메인 - 타임세일
	 * @param model
	 * @return
	 */
	@PostMapping("/main/spot-items")
	@RequestProperty(layout="blank")
	public String spotItems(Model model) {

		int limit = 4;
		model.addAttribute("spotItems", displayService.getMainSpotItems(limit));

		return "view";
	}

	/**
	 * 메인 - 득템의 기쁨
	 * @param model
	 * @return
	 */
	@PostMapping("/main/big-deal-items")
	@RequestProperty(layout="blank")
	public String bigDealItems(Model model) {
		DisplayItemParam param = new DisplayItemParam();
		param.setDisplayGroupCode("big-deal");
		param.setLimit(5);

		if (!UserUtils.isManagerLogin()) {
			// 전용상품 조회
			param.setPrivateTypes(ItemUtils.getPrivateTypes());
		}

		model.addAttribute("bigDealItems", displayService.getDisplayItemListByParam(param));
		return "view";
	}

	/**
	 * 메인 - 리뷰
	 * @param model
	 * @return
	 */
	@PostMapping("/main/review")
	@RequestProperty(layout="blank")
	public String review(Model model) {
		ItemParam itemParam = new ItemParam();
		itemParam.setConditionType("FRONT_MAIN");
		itemParam.setRecommendFlag("Y");
		itemParam.setLimit(4);

		if (!UserUtils.isManagerLogin()) {
			// 전용상품 조회
			itemParam.setPrivateTypes(ItemUtils.getPrivateTypes());
		}

		model.addAttribute("reviewList", itemService.getItemReviewListByParam(itemParam));
		return "view";
	}

	/**
	 * 메인 - 이벤트
	 * @param model
	 * @return
	 */
	@PostMapping("/main/event")
	@RequestProperty(layout="blank")
	public String event(Model model) {
		FeaturedParam featuredParam = new FeaturedParam();
		featuredParam.setConditionType("FRONT_EVENT");
		featuredParam.setFeaturedClass(2);	// 이벤트
		featuredParam.setFeaturedType("1");
		featuredParam.setDisplayListFlag("Y");
		featuredParam.setLimit(10);

		model.addAttribute("eventList", featuredService.getFeaturedListByParamForFront(featuredParam));
		return "view";
	}

	/**
	 * 메인 - 공지사항
	 * @param model
	 * @return
	 */
	@PostMapping("/main/notice")
	@RequestProperty(layout="blank")
	public String notice(Model model) {
		NoticeParam noticeParam = new NoticeParam();
		noticeParam.setConditionType("main");
		noticeParam.setLimit(3);
		model.addAttribute("noticeList", noticeService.getFrontNoticeList(noticeParam));
		return "view";
	}

	/**
	 * 메인 - FAQ
	 * @param pageable
	 * @param model
	 * @return
	 */
	@PostMapping("/main/faq")
	@RequestProperty(layout="blank")
	public String faq(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 3) Pageable pageable, Model model) {
		FaqDto faqDto = new FaqDto();
		Page<Faq> pageContent = faqService.findAll(faqDto.getPredicate(), pageable);

		model.addAttribute("pageContent", pageContent);
		return "view";
	}

	/**
	 * 메인 - 팝업
	 * @return
	 */
	@GetMapping("/main/popup")
	@RequestProperty(layout="blank")
	public JsonView popup() {
		return JsonViewUtils.success(popupService.displayPopupList());
	}
	
	/**
	 * 메인페이지 (//로 요청이 오면 404)
	 * @return
	 */
	@ResponseBody
	@GetMapping("/{another}")
	@RequestProperty(template="front", layout="main", title="메인")
	public String index2(HttpServletRequest request,  HttpServletResponse response, HttpSession session,
			Model model, RequestContext requestContext) {
		throw new PageNotFoundException();
	}

	/**
	 * 모바일 디바이스 처리
	 * @param request
	 * @return
	 */
	private boolean isMobile(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String siteReference = request.getParameter("SITE_REFERENCE");
		String sessionSiteReference = (String) session.getAttribute("SITE_REFERENCE");
		
		if (DeviceUtils.isMobile(request)) {
			if (siteReference != null && siteReference.equals(DeviceUtils.NORMAL)) {
				session.setAttribute("SITE_REFERENCE", DeviceUtils.NORMAL);
				return false;
			} else if (sessionSiteReference != null && sessionSiteReference.equals(DeviceUtils.NORMAL)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 헬스 체크 URI
	 * @return
	 */
	@GetMapping(value="/healthcheck")
	@ResponseBody
	public String healthCheck() {

		return "OK";
	}

}
