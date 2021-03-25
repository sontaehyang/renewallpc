package saleson.shop.event;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.ShopUtils;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.display.DisplayService;
import saleson.shop.display.support.DisplayItemParam;
import saleson.shop.item.ItemService;
import saleson.shop.item.ItemValidator;
import saleson.shop.item.support.ItemParam;
import saleson.shop.maindisplayitem.MainDisplayItemService;
import saleson.shop.ranking.RankingService;
import saleson.shop.ranking.support.RankingParam;

//LCH-EventMobile  <추가>
//LCH-EventMobile  <추가>


@Controller
@RequestMapping("/m/event")
@RequestProperty(template="mobile", layout="default")
public class EventMobileController {
	
	private static final Logger log = LoggerFactory.getLogger(EventMobileController.class);
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private MainDisplayItemService mainDisplayItemService;
	
	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;
	
	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private DisplayService displayService;
	
	@Autowired
	private RankingService rankingService;

	@Autowired
	private ItemValidator itemValidator;

	//LCH-eventmobile 스팟 세일 리스트  <수정>
	
	/**
	 * 스팟세일
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping(value="spot")
	public String spotList(ItemParam itemParam, Model model) {
		model.addAttribute("categorylist",categoriesService.getCategoriesGroupId());
		
		itemParam.setConditionType(ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE);
		return makeSpotList(itemParam, model, "/event/spot", false);
		
	}
	
	
	//LCH-eventmobile 스팟 세일 (카테고리 선택 후 리스트) <수정>
	
	/**
	 * 스팟세일
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping(value="spot/list")
	@RequestProperty(layout="blank")
	public String spotItems(ItemParam itemParam, Model model) {
		
		return makeSpotList(itemParam, model, "/include/item-spot-list", true);
		
	}
	
	
	
	
	/**
	 * 스팟세일 목록생성
	 * @param itemParam
	 * @param model
	 * @param returnPath
	 * @param isMore
	 * @return
	 */
	private String makeSpotList(ItemParam itemParam, Model model, String returnPath, boolean isMore){
		
		itemParam.setItemsPerPage(10);
		itemValidator.validateSpotItemParam(itemParam);


		// 사용자단에 노출될 상품 조회에 필요한 기본적인 itemParam bind
		itemParam = ItemUtils.bindItemParam(itemParam);
		
		Pagination pagination = Pagination.getInstance(displayService.getItemCountForSpot(itemParam), itemParam.getItemsPerPage());
		
		ShopUtils.setPaginationInfo(pagination, itemParam.getConditionType(), itemParam.getPage());
		
		if (isMore) {
			pagination.setLink("javascript:paginationMore([page])");
		}
		
		itemParam.setPagination(pagination);
		
		
		model.addAttribute("list", displayService.getItemListForSpot(itemParam));
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		
		return ViewUtils.getView(returnPath);
	}
	
	
	
	
	//LCH-eventmobile 신상품 - 카테고리 선택  <수정> 

	/**
	 * 신상품 목록.
	 * @param displayItemParam
	 * @param model
	 * @return
	 */
	@GetMapping(value="new")
	public String newItemList(DisplayItemParam displayItemParam, Model model) {

		return getNewItemListWithCategory(displayItemParam, model, "all");

	}

	@PostMapping(value="new")
	public String newItemMoreList(DisplayItemParam displayItemParam, Model model) {

		return getNewItemListWithCategory(displayItemParam, model, "all");

	}

	private String getNewItemListWithCategory(DisplayItemParam displayItemParam, Model model, String all) {
		model.addAttribute("categorylist", categoriesService.getCategoriesGroupId());
		return makeNewItemList(displayItemParam, all, model, "/event/new", false);
	}

	/**
	 * 신상품 목록.
	 * @param code
	 * @param displayItemParam
	 * @param model
	 * @return
	 */
	@GetMapping(value="new/{code}")
	public String newItemList(@PathVariable("code") String code, DisplayItemParam displayItemParam, Model model) {

		return getNewItemListWithCategory(displayItemParam, model, code);

	}
	
	/**
	 * 신상품 목록.
	 * @param itemParam
	 * @param team
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="blank")
	@GetMapping("new/list/{code}")
	public String newItems(@PathVariable("code") String code, DisplayItemParam displayItemParam, Model model){
		
		return makeNewItemList(displayItemParam,  code, model, "/include/item-new-list", true);
	}

	/**
	 * 신상품 목록 생성
	 * @param displayItemParam
	 * @param subCode
	 * @param model
	 * @param returnPath
	 * @param isMore
	 * @return
	 */
	private String makeNewItemList( DisplayItemParam displayItemParam, String subCode, Model model, String returnPath, boolean isMore ){

		displayItemParam.setPrivateTypes(ItemUtils.getPrivateTypes());
		displayItemParam.setItemsPerPage(10);
		
		if (displayItemParam.getPage() == 0) {
			displayItemParam.setPage(1);
		}
		
		if (!StringUtils.isEmpty(subCode) && !"all".equals(subCode)) {
			displayItemParam.setDisplaySubCode(subCode);
		}
		
		displayItemParam.setDisplayGroupCode("new");
		
		// 기본 정렬 조건
		if (StringUtils.isEmpty(displayItemParam.getOrderBy())) {
			displayItemParam.setOrderBy("ORDERING");
			displayItemParam.setSort("ASC");
		}

		// 리스트 타입.
		if (StringUtils.isEmpty(displayItemParam.getListType())) {
			displayItemParam.setListType("a");
		}
		
		Pagination pagination = Pagination.getInstance(displayService.getDisplayItemListCountByParam(displayItemParam), displayItemParam.getItemsPerPage());
		
		String tempConditionType = ShopUtils.PAGE_MOBILE_DEFAULT_CONDITION_TYPE;
		
		if (isMore) {
			pagination.setLink("javascript:paginationMore([page])");
			tempConditionType = "";
		}
		
		ShopUtils.setPaginationInfo(pagination, tempConditionType, displayItemParam.getPage());
		
		displayItemParam.setPagination(pagination);

		model.addAttribute("displaySubCodeCount", displayService.getDisplayItemSubCodeCountByGroupCode("new"));
		model.addAttribute("items", displayService.getDisplayItemListByParam(displayItemParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("displayItemParam", displayItemParam);
		model.addAttribute("code", subCode);
		
		return ViewUtils.getView(returnPath);
	}
	
	/**
	 * BEST 추천 상품.
	 * @param model
	 * @return
	 */
	@GetMapping(value="best")
	public String bestItemSelectList(Model model) {
		
		RankingParam rankingParam = new RankingParam();
		rankingParam.setConditionType("FRONT_DISPLAY_ITEM");
		rankingParam.setRankingCode("TOP_100");
		rankingParam.setViewTarget("MOBILE");
		rankingParam.setLimit(20);
		rankingParam.setPrivateTypes(ItemUtils.getPrivateTypes());

		model.addAttribute("items", rankingService.getRankingListForFront(rankingParam));
		
		return ViewUtils.getView("/event/best");
	}
	
	
	
	
	
}
