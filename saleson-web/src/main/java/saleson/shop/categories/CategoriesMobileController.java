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

		// 1.코드구분 ('team', 'group', 'category')
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

		// 2. 카테고리 타입별 분기처리.
		try {
			// 2-1. 팀메인
			if ("team".equals(categoryType)) {
				//invokeTeamIndex(categoryCode, model);
			}

			// 2-2. 그룹메인
			if ("group".equals(categoryType)) {
				//invokeGroupIndex(categoryCode, itemParam, model);
			}

			// 2-3. 카테고리 메인.
			if ("category".equals(categoryType)) {
				invokeCategoryIndex(categoryCode, itemParam, model);

				ShopUtils.setCookieWhenServerLoad(response);
			}
		} catch (NotLoginUserException ex) {
			// 접근 권한이 회원만이고 로그인이 안된 경우.
			return new ModelAndView(ViewUtils.redirect(ex.getRedirectUrl()));
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

		if (!categoriesSearchParam.getCategoryClass().equals("")) {
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


		List<Team> categories = categoriesService.getCategoriesForFront();
		List<Code> options = new ArrayList<>();

		// 팀정보만 리턴
		if (categoryParam.getCategoryTeamCode().equals("") && categoryParam.getCategoryGroupCode().equals("")
				&& categoryParam.getCategoryUrl().equals("")) {

			for (Team team : categories) {
				options.add(new Code(team.getName(), team.getUrl()));
			}

			// 그룹정보 리턴.
		} else if (!categoryParam.getCategoryTeamCode().equals("")) {

			for (Team team : categories) {
				if (team.getUrl().equals(categoryParam.getCategoryTeamCode())) {
					for (Group group : team.getGroups()) {
						options.add(new Code(group.getName(), group.getUrl()));
					}
				}
			}

			// 1차 카테고리 정보 리턴.
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
	 * 1~4차 카테고리 상품목록
	 * @param categoryCode
	 * @param itemParam
	 * @param model
	 */
	private void invokeCategoryIndex(String categoryCode, ItemParam itemParam, Model model) {

		// 1. 현재 경로
		List<Breadcrumb> breadcrumbs = categoriesService.getBreadcrumbListByCategoryUrl(categoryCode);


		// 2. 1차 카테고리 코드
		String firstCategoryUrl = breadcrumbs.get(0).getBreadcrumbCategories().get(0).getCategoryUrl();
		String firstCategoryName = breadcrumbs.get(0).getBreadcrumbCategories().get(0).getCategoryName();


		// 3. 카테고리 정보 조회
		Categories category = categoriesService.getCategoryByCategoryUrl(categoryCode);

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

		// 회원만 접근인 경우 로그인 페이지로 이동.
		if (!UserUtils.isUserLogin() && category.getAccessType().equals("2")) {
			throw new NotLoginUserException("/m/users/login?target=/m/categories/index/" + categoryCode + "&error=99");
		}

		// 4.상품 목록
		String categoryClass = category.getCategoryCode().substring(0, Integer.parseInt(category.getCategoryLevel()) * 3);
		itemParam.setCategoryClass(categoryClass);
		itemParam.setDisplayNewItemListTop("1");
		itemParam.setCategoryId(Integer.toString(category.getCategoryId()));

		if (itemParam.getPage() == 0) {
			itemParam.setPage(1);
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

		ShopUtils.setPaginationInfo(pagination, ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE, itemParam.getPage());

		itemParam.setPagination(pagination);

		List<Item> itemList = itemService.getItemList(itemParam);


		// 카테고리 SEO 설정.
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

		// 3. 카테고리 정보 조회 
		Categories category = categoriesService.getCategoryByCategoryUrl(categoryCode);


		// 4.상품 목록
		String categoryClass = "";

		if(!"".equals(itemParam.getCategoryGroupCode())){ // 그룹 리스트 일때
			List<Categories> categorys = categoriesService.getCategoryByCategorygroupId(categoryCode); // 현재 카테고리와 같은 그룹인 카테고리 조회.

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

		// 사용자단에 노출될 상품 조회에 필요한 기본적인 itemParam bind
		itemParam = ItemUtils.bindItemParam(itemParam);

		// 필터 값이 존재 할 경우
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
