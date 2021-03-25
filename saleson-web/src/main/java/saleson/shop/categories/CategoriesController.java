package saleson.shop.categories;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.FilterGroup;
import saleson.shop.categories.domain.*;
import saleson.shop.categories.support.CategoriesSearchParam;
import saleson.shop.categories.support.CategoryParam;
import saleson.shop.categoriesfilter.CategoriesFilterService;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;
import saleson.shop.item.ItemService;
import saleson.shop.item.ItemValidator;
import saleson.shop.item.domain.Item;
import saleson.shop.item.support.ItemParam;
import saleson.shop.ranking.RankingService;
import saleson.shop.ranking.support.RankingParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/categories")
@RequestProperty(title="상품카테고리", layout="sub")
public class CategoriesController {
	private static final Logger log = LoggerFactory.getLogger(CategoriesController.class);

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private RankingService rankingService;

	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;

	@Autowired
	CategoriesFilterService categoriesFilterService;

	@Autowired
	private ItemValidator itemValidator;

	@GetMapping("/index/{categoryCode}/{urlSuffix}")
	public ModelAndView indexNotFound(@PathVariable("categoryCode") String categoryCode,
		@PathVariable("urlSuffix") String urlSuffix,
		ItemParam itemParam,
		Model model,
		RequestContext requestContext) {

		throw new PageNotFoundException();
	}

	@GetMapping(value="/index/{categoryCode}")
	public ModelAndView index(@PathVariable("categoryCode") String categoryCode,
		@RequestParam(value="PHPSESSID", required=false) String phpSessionId,
		ItemParam itemParam,
		Model model,
		RequestContext requestContext) {

		if (phpSessionId != null) {
			throw new PageNotFoundException();
		}

		itemValidator.validateCategoryItemParam(itemParam);

		// 1.코드구분 ('team', 'group', 'category')
		String categoryType = categoriesService.getCategoryTypeByCategoryCode(categoryCode);

		if (categoryType == null) {
			throw new PageNotFoundException();
		}

		final String queryString = requestContext.getQueryString();

		if (!StringUtils.isEmpty(queryString)
			&& (queryString.indexOf("=") == -1 || queryString.indexOf("%27") > -1 || queryString.indexOf("\\'") > -1)) {
			throw new PageNotFoundException();
		}

		String resolvedUrl = "/categories/index/" + categoryCode;

		if (!resolvedUrl.equals(requestContext.getRequestUri())) {
			if (requestContext.getRequestUri().equals(resolvedUrl + "/")) {
				if (!StringUtils.isEmpty(queryString)) {
					resolvedUrl = resolvedUrl + queryString;
				}

				final RedirectView rv = new RedirectView(resolvedUrl);
				rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);		// 301 Redirect
				return new ModelAndView(rv);
			}

			throw new PageNotFoundException();
		}

		// 2. 카테고리 타입별 분기처리.
		// 2-1. 팀메인
		if ("team".equals(categoryType)) {
			//requestContext.getRequestPropertyData().setLayout("main");
			//invokeTeamIndex(categoryCode, model);
			return new ModelAndView(ViewUtils.redirect("/"));
		}

		// 2-2. 그룹메인
		if ("group".equals(categoryType)) {
			//invokeGroupIndex(categoryCode, model);
			return new ModelAndView(ViewUtils.redirect("/"));
		}

		// 2-3. 카테고리 메인.
		if ("category".equals(categoryType)) {
			invokeCategoryIndex(categoryCode, itemParam, model);
		}


		model.addAttribute("categoryType", categoryType);

		return new ModelAndView(ViewUtils.getView("/categories/index/" + categoryType));
	}

	/**
	 * 카테고리 코드로 서브카테고리를 조회하여 Selectbox option을 구성함.
	 * @param categoriesSearchParam > categoryClass, categoryClass1 (활성화코드)
	 * @return
	 */
	@PostMapping("/options")
	@RequestProperty(title="옵션", layout="blank")
	public String options(@ModelAttribute CategoriesSearchParam categoriesSearchParam, Model model) {

		if (!"".equals(categoriesSearchParam.getCategoryClass())) {
			model.addAttribute("list", categoriesService.getChildCategoriesFromParantCategoryClass(categoriesSearchParam.getCategoryClass()));
		}
		return ViewUtils.view();

	}


	/**
	 * 그룹ID로 서브카테고리를 조회하여 Selectbox option을 구성함.
	 * @param categoriesSearchParam > categoryGroupId, categoryClass1 (활성화코드)
	 * @return
	 */
	@PostMapping("/options-by-groupid")
	@RequestProperty(title="옵션", layout="blank")
	public String optionsByGroupId(@ModelAttribute CategoriesSearchParam categoriesSearchParam, Model model) {

		model.addAttribute("list", categoriesService.getCategoriesListById(categoriesSearchParam));
		return ViewUtils.view();

	}

	@PostMapping("/options-by-groupid2")
	@RequestProperty(title="옵션", layout="blank")
	public String optionsByGroupId2(@ModelAttribute CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam, Model model) {

		model.addAttribute("list", categoriesTeamGroupService.getCategoriesGroupListParam(categoriesTeamGroupSearchParam));

		return ViewUtils.view();

	}


	/**
	 * 해당 차수에 child category의 옵션을 가져온다.
	 * 파라미터가 없으면 : TEAM 목록
	 * categoryTeamCode : GROUP 목록
	 * categoryGroupCode : 1차 카테고리 목록
	 * categoryUrl : 하위 카테고리 목록.
	 *
	 * @param categoryParam
	 * @param model
	 * @return
	 */
	@PostMapping("/options-by-category-code")
	@RequestProperty(title="옵션", layout="blank")
	public String optionsByCategoryCode(@ModelAttribute CategoryParam categoryParam, Model model) {


		List<Code> options = new ArrayList<>();
		List<Team> categories = categoriesService.getCategoriesForFront();
		if (categories == null) {
			categories = new ArrayList<>();
		}

		// 팀정보만 리턴
		if ("".equals(categoryParam.getCategoryTeamCode()) && "".equals(categoryParam.getCategoryGroupCode())
			&& "".equals(categoryParam.getCategoryUrl())) {

			for (Team team : categories) {
				options.add(new Code(team.getName(), team.getUrl()));
			}

			// 그룹정보 리턴.
		} else if (!"".equals(categoryParam.getCategoryTeamCode())) {

			for (Team team : categories) {
				if (team.getUrl().equals(categoryParam.getCategoryTeamCode())) {
					for (Group group : team.getGroups()) {
						options.add(new Code(group.getName(), group.getUrl()));
					}
				}
			}

			// 1차 카테고리 정보 리턴.
		} else if (!"".equals(categoryParam.getCategoryGroupCode())) {

			for (Team team : categories) {
				for (Group group : team.getGroups()) {
					if (group.getUrl().equals(categoryParam.getCategoryGroupCode())) {
						for (Category category : group.getCategories()) {
							options.add(new Code(category.getName(), category.getUrl()));

						}
					}
				}
			}
		}

		model.addAttribute("options", options);
		return ViewUtils.view();

	}

	/**
	 * 카테고리 화면
	 * @param categoryCode
	 * @param itemParam
	 * @param model
	 */
	private void invokeCategoryIndex(String categoryCode, ItemParam itemParam, Model model) {

		// 1. 현재 경로
		List<Breadcrumb> breadcrumbs = categoriesService.getBreadcrumbListByCategoryUrl(categoryCode);
		List<List<Code>> breadcrumbsForSelectbox = ShopUtils.getBreadcrumbsForSelectbox(breadcrumbs.get(0));

		// 2. 1차 카테고리 코드 
		String firstCategoryUrl = breadcrumbs.get(0).getBreadcrumbCategories().get(0).getCategoryUrl();
		String firstCategoryName = breadcrumbs.get(0).getBreadcrumbCategories().get(0).getCategoryName();

		// 3. 카테고리 정보 조회 
		Categories category = categoriesService.getCategoryByCategoryUrl(categoryCode);
		if (ValidationUtils.isNull(category)) {
			throw new PageNotFoundException();
		}

		// 3-1. 부모형제자식
		Category parentCategory = ShopUtils.getParentCategory(categoryCode);

		if (parentCategory == null) {
			parentCategory = new Category();
		}

		parentCategory.setParentCategory(ShopUtils.getParentCategory(parentCategory.getUrl())); // 부모의 부모

		category.setParentCategory(parentCategory);
		category.setParentSiblingCategories(ShopUtils.getSiblingCategories(parentCategory.getUrl()));
		category.setSiblingCategories(ShopUtils.getSiblingCategories(categoryCode));
		category.setChildCategories(ShopUtils.getChildCategoriesForCategory(categoryCode));


		// 4.상품 목록
		String categoryClass = category.getCategoryCode().substring(0, Integer.parseInt(category.getCategoryLevel()) * 3);
		itemParam.setCategoryClass(categoryClass);
		itemParam.setDisplayNewItemListTop("1");
		itemParam.setCategoryId(Integer.toString(category.getCategoryId()));

		if (itemParam.getItemsPerPage() < 20) {
			itemParam.setItemsPerPage(20);
		}

		List<FilterGroup> categoryFilterList = categoriesFilterService.getBreadcrumbFilterGroupList(category.getCategoryId());
		List<PriceArea> priceAreaList = categoriesService.getPriceAreaListById(category.getCategoryId());

		// 사용자단에 노출될 상품 조회에 필요한 기본적인 itemParam bind
		itemParam = ItemUtils.bindItemParam(itemParam);

		// 필터 값이 존재 할 경우
		if(!"".equals(itemParam.getFcIds()) && itemParam.getFcIds() != null){
			itemParam.setFilterCodeIds(itemParam.getFcIds().split("N"));
		}

		Pagination pagination = Pagination.getInstance(itemService.getItemCount(itemParam), itemParam.getItemsPerPage());
		itemParam.setPagination(pagination);

		List<Item> itemList = itemService.getItemList(itemParam);

		// 5. 랭킹 리스트
		if ("1".equals(category.getCategoryLevel())) {
			RankingParam rankingParam = new RankingParam();
			rankingParam.setRankingCode(Integer.toString(category.getCategoryId()));
			rankingParam.setViewTarget("WEB");
			rankingParam.setLimit(10);
			// 전용 상품 조회 2017-06-01 yulsun.yoo
			rankingParam.setConditionType("FRONT_DISPLAY_ITEM");
			rankingParam.setPrivateTypes(ItemUtils.getPrivateTypes());
			//E 전용 상품 조회 2017-06-01 yulsun.yoo
			List<Item> rankingList = rankingService.getRankingListForFront(rankingParam);
			model.addAttribute("rankingList", rankingList);
		}



		// 7.카테고리 SEO 설정.
		/* seo null check 추가 2017-04-25 jeongah.choi*/
		if (!category.getCategoriesSeo().isSeoNull()) {
			ShopUtils.setSeo(category.getCategoriesSeo());
		}

		model.addAttribute("categoryTeamCode", breadcrumbs.get(0).getTeamUrl());
		model.addAttribute("categoryGroupCode", breadcrumbs.get(0).getGroupUrl());
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("breadcrumbsForSelectbox", breadcrumbsForSelectbox);

		model.addAttribute("category", category);
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("items", itemList);

		model.addAttribute("rankingCode", firstCategoryUrl + "_" + breadcrumbs.get(0).getTeamUrl());
		model.addAttribute("firstCategoryName", firstCategoryName);

		model.addAttribute("colors", CodeUtils.getCodeList("ITEM_COLOR"));
		model.addAttribute("priceOrderByColumn", UserUtils.isUserLogin() ? "SALE_PRICE" : "SALE_PRICE_NONMEMBER");

		model.addAttribute("categoryFilterList", categoryFilterList);
		model.addAttribute("priceAreaList", priceAreaList);

	}

}
