package saleson.shop.event;

import com.onlinepowers.framework.context.ThreadContext;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.context.ShopContext;
import saleson.common.utils.ItemUtils;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categories.domain.Category;
import saleson.shop.categories.domain.Group;
import saleson.shop.display.DisplayService;
import saleson.shop.display.support.DisplayItemParam;
import saleson.shop.item.ItemService;
import saleson.shop.item.ItemValidator;
import saleson.shop.item.domain.Item;
import saleson.shop.item.support.ItemParam;
import saleson.shop.ranking.RankingService;
import saleson.shop.ranking.support.RankingParam;
import saleson.shop.wishlist.WishlistService;

import java.util.HashMap;
import java.util.List;



@Controller
@RequestMapping("/event")
@RequestProperty(title="메인화면 관리", layout="default")
public class EventController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private WishlistService wishlistService;

	@Autowired
	private DisplayService displayService;

	@Autowired
	private RankingService rankingService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private ItemValidator itemValidator;

	/**
	 * 스팟세일
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping(value="spot")
	public String spotList(ItemParam itemParam, Model model) {
		itemValidator.validateSpotItemParam(itemParam);

		// 사용자단에 노출될 상품 조회에 필요한 기본적인 itemParam bind
		itemParam = ItemUtils.bindItemParam(itemParam);

		Pagination pagination = Pagination.getInstance(displayService.getItemCountForSpot(itemParam));

		itemParam.setPagination(pagination);


		model.addAttribute("list", displayService.getItemListForSpot(itemParam));
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);

		return ViewUtils.getView("/event/spot");
	}


	/**
	 * 신상품 목록.
	 * @param displayItemParam
	 * @param model
	 * @return
	 */
	@GetMapping(value="new")
	public String newItemList(DisplayItemParam displayItemParam, Model model) {

		displayItemParam.setPrivateTypes(ItemUtils.getPrivateTypes());
		displayItemParam.setDisplayGroupCode("new");

		// 기본 정렬 조건
		if (StringUtils.isEmpty(displayItemParam.getOrderBy())) {
			displayItemParam.setOrderBy("ORDERING");
			displayItemParam.setSort("ASC");
		}

		// 리스트 타입.
		if (StringUtils.isEmpty(displayItemParam.getListType())
				|| !displayItemParam.getListType().matches("[a|b|c]{1}")) {
			displayItemParam.setListType("a");
		}

		if (displayItemParam.getItemsPerPage() < 20) {
			displayItemParam.setItemsPerPage(20);
		}

		Pagination pagination = Pagination.getInstance(displayService.getDisplayItemListCountByParam(displayItemParam));

		displayItemParam.setPagination(pagination);

		model.addAttribute("displaySubCodeCount", displayService.getDisplayItemSubCodeCountByGroupCode("new"));
		model.addAttribute("items", displayService.getDisplayItemListByParam(displayItemParam));
		model.addAttribute("pagination",pagination);
		model.addAttribute("displayItemParam",displayItemParam);

		return ViewUtils.getView("/event/new");
	}

	/**
	 * BEST 추천 상품
	 * @param model
	 * @return
	 */
	@GetMapping("best")
	public String bestItemList(Model model) {

		RankingParam rankingParam = new RankingParam();
		rankingParam.setConditionType("FRONT_DISPLAY_ITEM");
		rankingParam.setRankingCode("TOP_100");
		rankingParam.setViewTarget("WEB");
		rankingParam.setLimit(100);
		rankingParam.setPrivateTypes(ItemUtils.getPrivateTypes());

		model.addAttribute("items", rankingService.getRankingListForFront(rankingParam));

		return ViewUtils.getView("/event/best");
	}

	/**
	 * 1차 카테고리 BEST 추천 상품
	 * @param model
	 * @return
	 */
	@GetMapping("category-best")
	public String categoryBestItemList(Model model) {

		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);

		HashMap<String, List<Item>> bestItems = new HashMap<String, List<Item>>();

		for(Group group : shopContext.getShopCategoryGroups()) {

			// BEST
			RankingParam rankingParam = new RankingParam();
			rankingParam.setRankingCode(group.getUrl());
			rankingParam.setViewTarget("WEB");
			rankingParam.setLimit(4);
			bestItems.put(group.getUrl(), rankingService.getRankingListForFront(rankingParam));

		}

		model.addAttribute("bestItems", bestItems);

		return ViewUtils.getView("/event/category-best");
	}

	/**
	 * 카테고리 별 BEST 추천 상품
	 * @param model
	 * @return
	 */
	@GetMapping("category-best/{code}")
	public String categoryBestItemListMore(Model model, @PathVariable("code") String code) {

		if (code == null) {
			throw new PageNotFoundException("Code parameter is null.");
		}

		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);

		String contentName = "";
		String contentCode = "";

		for(Group group : shopContext.getShopCategoryGroups()) {

			if (code.equals(group.getUrl())) {
				contentName = group.getName();
				contentCode =group.getUrl();
				break;
			}

			for (Category  category : group.getCategories()) {
				if (code.equals(String.valueOf(category.getCategoryId()))) {

					contentName = category.getName();
					Categories categories = categoriesService.getCategoryByCategoryUrl(code);
					contentCode = categories.getCategoryCode();
					break;
				}
			}
		}

		// BEST
		RankingParam rankingParam = new RankingParam();
		rankingParam.setRankingCode(contentCode);
		rankingParam.setViewTarget("WEB");
		rankingParam.setLimit(20);

		model.addAttribute("contentName", contentName);
		model.addAttribute("items", rankingService.getRankingListForFront(rankingParam));

		return ViewUtils.getView("/event/category-best-detail");
	}

}
