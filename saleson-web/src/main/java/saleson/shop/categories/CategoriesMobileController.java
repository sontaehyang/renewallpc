package saleson.shop.categories;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.util.CodeUtils;
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
import saleson.common.exception.NotLoginUserException;
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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/m/categories")
@RequestProperty(template="mobile", layout="default")
public class CategoriesMobileController {
	private static final Logger log = LoggerFactory.getLogger(CategoriesMobileController.class);


	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;

	@Autowired
	private ItemValidator itemValidator;

	@Autowired
	CategoriesFilterService categoriesFilterService;

	@RequestProperty(layout="search")
	@GetMapping("/index/{categoryCode}/{urlSuffix}")
	public ModelAndView indexNotFound(@PathVariable("categoryCode") String categoryCode,
									  @PathVariable("urlSuffix") String urlSuffix,
									  ItemParam itemParam,
									  Model model,
									  RequestContext requestContext) {

		throw new PageNotFoundException();
	}

	@RequestProperty(layout="search")
	@GetMapping(value="/index/{categoryCode}")
	public ModelAndView index(@PathVariable("categoryCode") String categoryCode,
							  ItemParam itemParam,
							  Model model,
							  RequestContext requestContext,
							  HttpServletResponse response) {

		// 1.???????????? ('team', 'group', 'category')
		String categoryType = categoriesService.getCategoryTypeByCategoryCode(categoryCode);

		if (categoryType == null) {
			throw new PageNotFoundException();
		}

		itemValidator.validateCategoryItemParam(itemParam);


		final String queryString = requestContext.getQueryString();
		if (!StringUtils.isEmpty(queryString)
				&& (queryString.indexOf("=") == -1 || queryString.indexOf("%27") > -1 || queryString.indexOf("\\'") > -1)) {
			throw new PageNotFoundException();
		}

		String resolvedUrl = "/m/categories/index/" + categoryCode;

		if (!resolvedUrl.equals(requestContext.getRequestUri())) {
			if (requestContext.getRequestUri().equals(resolvedUrl + "/")) {
				if (!StringUtils.isEmpty(requestContext.getQueryString())) {
					resolvedUrl = resolvedUrl + requestContext.getQueryString();
				}

				final RedirectView rv = new RedirectView(resolvedUrl);
				rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);		// 301 Redirect
				return new ModelAndView(rv);
			}

			throw new PageNotFoundException();
		}

		// 2. ???????????? ????????? ????????????.
		try {
			// 2-1. ?????????
			if ("team".equals(categoryType)) {
				//invokeTeamIndex(categoryCode, model);
			}

			// 2-2. ????????????
			if ("group".equals(categoryType)) {
				//invokeGroupIndex(categoryCode, itemParam, model);
			}

			// 2-3. ???????????? ??????.
			if ("category".equals(categoryType)) {
				invokeCategoryIndex(categoryCode, itemParam, model);

				ShopUtils.setCookieWhenServerLoad(response);
			}
		} catch (NotLoginUserException ex) {
			// ?????? ????????? ??????????????? ???????????? ?????? ??????.
			return new ModelAndView(ViewUtils.redirect(ex.getRedirectUrl()));
		}


		model.addAttribute("categoryType", categoryType);

		return new ModelAndView(ViewUtils.getView("/categories/index/" + categoryType));
	}

	/**
	 * ???????????? ????????? ????????????????????? ???????????? Selectbox option??? ?????????.
	 * @param categoriesSearchParam > categoryClass, categoryClass1 (???????????????)
	 * @return
	 */
	@PostMapping("/options")
	@RequestProperty(title="??????", layout="blank")
	public String options(@ModelAttribute CategoriesSearchParam categoriesSearchParam, Model model) {

		if (!categoriesSearchParam.getCategoryClass().equals("")) {
			model.addAttribute("list", categoriesService.getChildCategoriesFromParantCategoryClass(categoriesSearchParam.getCategoryClass()));
		}
		return ViewUtils.view();

	}


	/**
	 * ??????ID??? ????????????????????? ???????????? Selectbox option??? ?????????.
	 * @param categoriesSearchParam > categoryGroupId, categoryClass1 (???????????????)
	 * @return
	 */
	@PostMapping("/options-by-groupid")
	@RequestProperty(title="??????", layout="blank")
	public String optionsByGroupId(@ModelAttribute CategoriesSearchParam categoriesSearchParam, Model model) {

		model.addAttribute("list", categoriesService.getCategoriesListById(categoriesSearchParam));
		return ViewUtils.view();

	}

	@PostMapping("/options-by-groupid2")
	@RequestProperty(title="??????", layout="blank")
	public String optionsByGroupId2(@ModelAttribute CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam, Model model) {

		model.addAttribute("list", categoriesTeamGroupService.getCategoriesGroupListParam(categoriesTeamGroupSearchParam));

		return ViewUtils.view();

	}


	/**
	 * ?????? ????????? child category??? ????????? ????????????.
	 * ??????????????? ????????? : TEAM ??????
	 * categoryTeamCode : GROUP ??????
	 * categoryGroupCode : 1??? ???????????? ??????
	 * categoryUrl : ?????? ???????????? ??????.
	 *
	 * @param categoryParam
	 * @param model
	 * @return
	 */
	@PostMapping("/options-by-category-code")
	@RequestProperty(title="??????", layout="blank")
	public String optionsByCategoryCode(@ModelAttribute CategoryParam categoryParam, Model model) {


		List<Team> categories = categoriesService.getCategoriesForFront();
		List<Code> options = new ArrayList<>();

		// ???????????? ??????
		if (categoryParam.getCategoryTeamCode().equals("") && categoryParam.getCategoryGroupCode().equals("")
				&& categoryParam.getCategoryUrl().equals("")) {

			for (Team team : categories) {
				options.add(new Code(team.getName(), team.getUrl()));
			}

			// ???????????? ??????.
		} else if (!categoryParam.getCategoryTeamCode().equals("")) {

			for (Team team : categories) {
				if (team.getUrl().equals(categoryParam.getCategoryTeamCode())) {
					for (Group group : team.getGroups()) {
						options.add(new Code(group.getName(), group.getUrl()));
					}
				}
			}

			// 1??? ???????????? ?????? ??????.
		} else if (!categoryParam.getCategoryGroupCode().equals("")) {

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
	 * 1~4??? ???????????? ????????????
	 * @param categoryCode
	 * @param itemParam
	 * @param model
	 */
	private void invokeCategoryIndex(String categoryCode, ItemParam itemParam, Model model) {

		// 1. ?????? ??????
		List<Breadcrumb> breadcrumbs = categoriesService.getBreadcrumbListByCategoryUrl(categoryCode);


		// 2. 1??? ???????????? ??????
		String firstCategoryUrl = breadcrumbs.get(0).getBreadcrumbCategories().get(0).getCategoryUrl();
		String firstCategoryName = breadcrumbs.get(0).getBreadcrumbCategories().get(0).getCategoryName();


		// 3. ???????????? ?????? ??????
		Categories category = categoriesService.getCategoryByCategoryUrl(categoryCode);

		// 3-1. ??????????????????
		Category parentCategory = ShopUtils.getParentCategory(categoryCode);

		if (parentCategory == null) {
			parentCategory = new Category();
		}

		parentCategory.setParentCategory(ShopUtils.getParentCategory(parentCategory.getUrl())); // ????????? ??????

		category.setParentCategory(parentCategory);
		category.setParentSiblingCategories(ShopUtils.getSiblingCategories(parentCategory.getUrl()));
		category.setSiblingCategories(ShopUtils.getSiblingCategories(categoryCode));
		category.setChildCategories(ShopUtils.getChildCategoriesForCategory(categoryCode));

		// ????????? ????????? ?????? ????????? ???????????? ??????.
		if (!UserUtils.isUserLogin() && category.getAccessType().equals("2")) {
			throw new NotLoginUserException("/m/users/login?target=/m/categories/index/" + categoryCode + "&error=99");
		}

		// 4.?????? ??????
		String categoryClass = category.getCategoryCode().substring(0, Integer.parseInt(category.getCategoryLevel()) * 3);
		itemParam.setCategoryClass(categoryClass);
		itemParam.setDisplayNewItemListTop("1");
		itemParam.setCategoryId(Integer.toString(category.getCategoryId()));

		if (itemParam.getPage() == 0) {
			itemParam.setPage(1);
		}

		List<FilterGroup> categoryFilterList = categoriesFilterService.getBreadcrumbFilterGroupList(category.getCategoryId());
		List<PriceArea> priceAreaList = categoriesService.getPriceAreaListById(category.getCategoryId());

		// ??????????????? ????????? ?????? ????????? ????????? ???????????? itemParam bind
		itemParam = ItemUtils.bindItemParam(itemParam);

		// ?????? ?????? ?????? ??? ??????
		if(!"".equals(itemParam.getFcIds()) && itemParam.getFcIds() != null){
			itemParam.setFilterCodeIds(itemParam.getFcIds().split("N"));
		}

		Pagination pagination = Pagination.getInstance(itemService.getItemCount(itemParam), itemParam.getItemsPerPage());

		ShopUtils.setPaginationInfo(pagination, ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE, itemParam.getPage());

		itemParam.setPagination(pagination);

		List<Item> itemList = itemService.getItemList(itemParam);


		// ???????????? SEO ??????.
		ShopUtils.setSeo(category.getCategoriesSeo());




		model.addAttribute("categoryTeamCode", breadcrumbs.get(0).getTeamUrl());
		model.addAttribute("categoryGroupCode", breadcrumbs.get(0).getGroupUrl());
		model.addAttribute("breadcrumbs", breadcrumbs);

		model.addAttribute("category", category);
		model.addAttribute("categoryCode", categoryCode);
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


	@RequestProperty(layout="blank")
	@PostMapping("list/{categoryCode}")
	public String categoryItems(@PathVariable("categoryCode") String categoryCode, ItemParam itemParam, Model model) {

		// 3. ???????????? ?????? ?????? 
		Categories category = categoriesService.getCategoryByCategoryUrl(categoryCode);


		// 4.?????? ??????
		String categoryClass = "";

		if(!"".equals(itemParam.getCategoryGroupCode())){ // ?????? ????????? ??????
			List<Categories> categorys = categoriesService.getCategoryByCategorygroupId(categoryCode); // ?????? ??????????????? ?????? ????????? ???????????? ??????.

			List<String> categoryClasses = new ArrayList<>();
			for(Categories groupcategory : categorys ){
				categoryClass = groupcategory.getCategoryCode().substring(0, Integer.parseInt(groupcategory.getCategoryLevel()) * 3);
				categoryClasses.add(categoryClass);
			}
			itemParam.setGroupCategoryClassCodes(categoryClasses);

		}else{

			categoryClass = category.getCategoryCode().substring(0, Integer.parseInt(category.getCategoryLevel()) * 3);
			itemParam.setCategoryClass(categoryClass);
			itemParam.setCategoryId(Integer.toString(category.getCategoryId()));
		}

		itemParam.setDisplayNewItemListTop("1");
		itemParam.setItemsPerPage(10);
		if (itemParam.getPage() == 0) {
			itemParam.setPage(1);
		}

		// ??????????????? ????????? ?????? ????????? ????????? ???????????? itemParam bind
		itemParam = ItemUtils.bindItemParam(itemParam);

		// ?????? ?????? ?????? ??? ??????
		if(!"".equals(itemParam.getFcIds()) && itemParam.getFcIds() != null){
			itemParam.setFilterCodeIds(itemParam.getFcIds().split("N"));
		}

		Pagination pagination = Pagination.getInstance(itemService.getItemCount(itemParam), itemParam.getItemsPerPage());

		ShopUtils.setPaginationInfo(pagination, "", itemParam.getPage());

		pagination.setLink("javascript:paginationMore([page])");

		itemParam.setPagination(pagination);

		List<Item> itemList = itemService.getItemList(itemParam);

		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("items", itemList);
		return ViewUtils.getView("/include/item-list");
	}

}
