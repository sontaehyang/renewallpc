package saleson.shop.main;

import com.onlinepowers.framework.i18n.support.CodeResolver;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.UserUtils;
import saleson.model.Faq;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Breadcrumb;
import saleson.shop.categories.domain.Category;
import saleson.shop.categories.domain.Group;
import saleson.shop.categories.domain.Team;
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
import saleson.shop.item.support.ItemParam;
import saleson.shop.mobilecategoriesedit.MobileCategoriesEditService;
import saleson.shop.notice.NoticeService;
import saleson.shop.notice.support.NoticeParam;
import saleson.shop.popup.PopupService;
import saleson.shop.ranking.RankingService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/m")
@RequestProperty(template="mobile", layout="main")
public class MainMobileController {

	private static final Logger log = LoggerFactory.getLogger(MainMobileController.class);

	@Autowired
	MobileCategoriesEditService mobileCategoriesEditService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	RankingService rankingService;
	
	@Autowired
	CodeResolver codeResolver;
	
	@Autowired
	NoticeService noticeService;

	@Autowired
	private FaqService faqService;
	
	@Autowired
	private DisplayService displayService;
	
	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private FeaturedService featuredService;

	@Autowired
	private PopupService popupService;

	@GetMapping("/")
	public ModelAndView indexRedirect(HttpServletResponse response, Model model) {
		final RedirectView rv = new RedirectView("/m");
		rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);		// 301 Redirect
		return new ModelAndView(rv);
	}
	
	@GetMapping
	public String index(Model model) {

		displayService.setMainDisplayByGroupCode(model, "mobile-promotion", "mobile-middle", "mobile-cash-specials");

		return ViewUtils.getView("/main/index");

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

		// 카테고리별 상품 매핑
		HashMap<String, List<DisplayItem>> displayItemMap = new HashMap<>();

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

		// 카테고리 정보
		List<Team> teams = categoriesService.getCategoriesForFront();

		List<Breadcrumb> breadcrumbs = new ArrayList<>();
		for (Team team : teams) {
			if (Config.SHOP_CATEGORY_GROUP_KEY.equals(team.getUrl())) {

				// 그룹별 세팅
				for (Group group : team.getGroups()) {
					Breadcrumb breadcrumb = new Breadcrumb();

					for (Category category : group.getCategories()) {

						// 상품이 설정되어 있을 경우, 카테고리 정보 세팅
						if (!ObjectUtils.isEmpty(displayItemMap.get(group.getUrl()))) {
							breadcrumb.setGroupUrl(group.getUrl());
							breadcrumb.setGroupName(group.getName());
						}

						// 1차 카테고리 상품을 그룹으로 포함, 카테고리 정보 세팅
						if (!ObjectUtils.isEmpty(displayItemMap.get(category.getCategoryId()))) {
							if (displayItemMap.get(group.getUrl()) == null) {
								displayItemMap.put(group.getUrl(), new ArrayList<>());
							}

							for (DisplayItem displayItem : displayItemMap.get(category.getCategoryId())) {
								// 최대 출력개수 검사
								if ((displayItemMap.get(group.getUrl()).size() >= itemLimit)) {
									break;
								}

								// 중복 상품 검사
								boolean isExist = displayItemMap.get(group.getUrl()).stream().anyMatch(groupItem -> displayItem.getItemId() == groupItem.getItemId());
								if (isExist) {
									continue;
								}

								breadcrumb.setGroupUrl(group.getUrl());
								breadcrumb.setGroupName(group.getName());
								displayItemMap.get(group.getUrl()).add(displayItem);
							}

							displayItemMap.remove(category.getCategoryId());
						}
					}

					// 카테고리 정보 add
					if (!StringUtils.isEmpty(breadcrumb.getGroupName()) && !StringUtils.isEmpty(breadcrumb.getGroupUrl())) {
						breadcrumbs.add(breadcrumb);
					}
				}

			}
		}

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("displayItemMap", displayItemMap);

		return "view";
	}

	/**
	 * 메인 - 타임세일
	 * @param model
	 * @return
	 */
	@PostMapping("/main/spot-items")
	@RequestProperty(layout="blank")
	public String spotitems(Model model) {

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
		param.setLimit(9);

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
	 * 헬스 체크 URI
	 * @return
	 */
	@GetMapping(value="/healthcheck")
	@ResponseBody
	public String healthCheck() {

		return "OK";
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
}
