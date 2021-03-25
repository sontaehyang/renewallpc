package saleson.shop.display;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.Const;
import saleson.common.enumeration.DisplayItemType;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Team;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.config.domain.Config;
import saleson.shop.display.domain.*;
import saleson.shop.display.support.DisplayItemParam;
import saleson.shop.display.support.DisplayParam;
import saleson.shop.display.support.DisplaySnsParam;
import saleson.shop.group.domain.Group;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemSpot;
import saleson.shop.item.support.ItemListParam;
import saleson.shop.item.support.ItemParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/opmanager/display")
@RequestProperty(title="화면 관리", layout="default")
public class DisplayManagerController {

	@Autowired
	private DisplayService displayService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;

	@Autowired
	private CategoriesService categoriesService;

	/**
	 * 화면 전시 상품 등록
	 * @param model
	 * @param displayItemParam
	 * @param code
	 * @return
	 */
	@GetMapping("item/{code}")
	public String form(Model model, DisplayItemParam displayItemParam, @PathVariable("code") String code) {

		DisplayItemType displayItemType = DisplayItemType.NORMAL;

		if ("best".equals(code)) {
			List<Team> categories = categoriesService.getCategoriesForFront();
			List<saleson.shop.categories.domain.Group> shopCategoryGroups = new ArrayList<>();

			for (Team team : categories) {
				if (Config.SHOP_CATEGORY_GROUP_KEY.equals(team.getUrl())) {
					shopCategoryGroups.addAll(team.getGroups());
				}
			}

			for (saleson.shop.categories.domain.Group group : shopCategoryGroups) {
				if (group.getUrl().equals(displayItemParam.getDisplaySubCode())) {
					displayItemType = DisplayItemType.BANNER;
					break;
				}
			}

			String initGroupUrl = "";

			if (ValidationUtils.isNotNull(shopCategoryGroups) && !shopCategoryGroups.isEmpty()) {
				initGroupUrl = shopCategoryGroups.get(0).getUrl();
			}

			if (StringUtils.isEmpty(displayItemParam.getDisplaySubCode())){
				displayItemParam.setDisplaySubCode(initGroupUrl);
				displayItemType = DisplayItemType.BANNER;
			}

			model.addAttribute("categories", shopCategoryGroups);

			model.addAttribute("notDisplayItemMap", displayService.getNotDisplayDisplayItemCountForSubCode(code, ""));
			model.addAttribute("soldOutItemMap", displayService.getSoldOutDisplayItemCountForSubCode(code, ""));
			model.addAttribute("totalItemMap", displayService.getTotalDisplayItemCountForSubCode(code, ""));
		}

		displayItemParam.setDisplayGroupCode(code);
		if ("1".equals(displayItemParam.getViewType())) {
			RequestContextUtils.setLayout("blank");
		}

		model.addAttribute("list", displayService.getDisplayItemListByParamForManager(displayItemParam));
		model.addAttribute("action","/opmanager/display/item/" + code);
		model.addAttribute("code", code);
		model.addAttribute("displayItemType", displayItemType);

		return ViewUtils.getView("/display/item/form");
	}

	@GetMapping("item/{code}/{subCode}")
	public String form2(Model model, DisplayItemParam displayItemParam,
		@PathVariable("code") String code,
		@PathVariable("subCode") String subCode) {


		displayItemParam.setDisplayGroupCode(code);
		displayItemParam.setDisplaySubCode(subCode);
		if ("1".equals(displayItemParam.getViewType())) {
			RequestContextUtils.setLayout("blank");
		}

		model.addAttribute("list", displayService.getDisplayItemListByParamForManager(displayItemParam));
		model.addAttribute("action","/opmanager/display/item/" + code +"/"+subCode);
		model.addAttribute("code", code);
		return ViewUtils.getView("/display/item/form");
	}

	/**
	 * 화면 전시 상품 등록
	 * @param displayItemParam
	 * @param code
	 * @return
	 */
	@PostMapping("item/{code}")
	public String formAction(DisplayItemParam displayItemParam,
		@PathVariable("code") String code) {

		String url = "/opmanager/display/item/" + code + displayItemParam.getQueryString();

		displayService.insertDisplayItem(displayItemParam);
		return ViewUtils.redirect(url);
	}

	@PostMapping("item/{code}/{subCode}")
	public String formAction2(DisplayItemParam displayItemParam,
		@PathVariable("code") String code,
		@PathVariable("subCode") String subCode) {

		String url = "/opmanager/display/item/" + code +"/"+subCode+ displayItemParam.getQueryString();

		displayService.insertDisplayItem(displayItemParam);
		return ViewUtils.redirect(url);
	}


	/**
	 * 스팟상품 리스트
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping(value={"spot/list","spot"})
	public String spotList(ItemParam itemParam, Model model) {

		// 정렬조건은 CategoryId가 있는 경우에만 허용.
		if (itemParam.getOrderBy() != null && itemParam.getOrderBy().equals("ORDERING")
			&& (itemParam.getCategoryId() == null || itemParam.getCategoryId().equals(""))) {
			itemParam.setOrderBy("");
			itemParam.setSort("DESC");
		}

		itemParam.setConditionType("OPMANAGER");
		itemParam.setDataStatusCode("1");
		String week = itemParam.getSpotWeekDay();

		//String값으로 한꺼번에 넘어오는 요일정보를 요일마다 쪼개어 리스트에 저장.
		if (week != null && itemParam.getSpotWeekDays() != null) {
			for (int i = 0; i < week.length(); i++){
				itemParam.getSpotWeekDays().add("%"+week.substring(i, i+1)+"%");
			}
		}

		Pagination pagination = Pagination.getInstance(displayService.getItemCountForSpot(itemParam),10);

		itemParam.setPagination(pagination);


		List<Group> list = new ArrayList<>();

		//전체를 제외한 나머지 그룹정보는 DB에서 가져옴.
		list = displayService.getSpotApplyGroup();

		//전체그룹을 추가
		Group g = new Group();
		g.setGroupCode("All");
		g.setGroupName("전체회원");
		list.add(0, g);

		model.addAttribute("list", displayService.getItemListForSpot(itemParam));
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("spotApplyGroups", list);
		return ViewUtils.getView("/display/spot/list");
	}

	/**
	 * 스팟상품 등록
	 * @param item
	 * @param model
	 * @return
	 */
	@GetMapping("spot/create")
	public String spotCreate(Item item, Model model) {

		//model.addAttribute("privateTypes", CodeUtils.getCodeList("ITEM_PRIVATE_TYPE"));
		List<Group> list = new ArrayList<>();
		list = displayService.getSpotApplyGroup();
		Group g = new Group();
		g.setGroupCode("All");
		g.setGroupName("전체회원");
		list.add(0, g);

		model.addAttribute("spotApplyGroups", list);
		return ViewUtils.getView("/display/spot/form");
	}

	/**
	 * 스팟상품 등록 처리
	 * @param item
	 * @param model
	 * @return
	 */
	@PostMapping("spot/create")
	public String spotCreateProcess(Item item, ItemSpot itemSpot,  Model model) {
		item.setSpotType("1"); //관리자 할인
		displayService.updateItemSpot(item, itemSpot);

		return ViewUtils.redirect("/opmanager/display/spot/list", "등록되었습니다.");
	}

	/**
	 * 스팟 상품 수정.
	 * @param strItemId
	 * @param model
	 * @return
	 */
	@GetMapping("spot/edit/{itemId}")
	public String spotEdit(@PathVariable("itemId") String strItemId, Model model) {
		if (StringUtils.isEmpty(strItemId)) {
			throw new PageNotFoundException();
		}
		int itemId = 0;
		try {
			itemId = Integer.parseInt(strItemId);
		}catch (Exception e) {
			throw new PageNotFoundException();
		}

		Item item = itemService.getItemBy(itemId);

		if (item == null) {
			throw new PageNotFoundException();
		}

		String today = DateUtils.getToday(Const.DATE_FORMAT);

		List<Group> list = new ArrayList<>();
		list = displayService.getSpotApplyGroup();
		Group g = new Group();
		g.setGroupCode("All");
		g.setGroupName("전체회원");
		list.add(0, g);

		model.addAttribute("spotApplyGroups", list);
		model.addAttribute("today", today);
		model.addAttribute("item", item);

		return ViewUtils.getView("/display/spot/form");
	}


	/**
	 * 특가 상품 수정. 처리.
	 * @param item
	 * @param model
	 * @return
	 */
	@PostMapping("spot/edit/{itemId}")
	public String spotEditProcess(Item item, ItemSpot itemSpot,  Model model) {

		item.setSpotType("1"); //관리자 할인
		displayService.updateItemSpot(item, itemSpot);

		return ViewUtils.redirect("/opmanager/display/spot/list", "수정되었습니다.");
	}

	/**
	 * 스팟 목록 데이터 삭제.
	 * @param requestContext
	 * @param itemListParam
	 * @return
	 */
	@PostMapping("spot/list/delete")
	public JsonView deleteListData(RequestContext requestContext, ItemListParam itemListParam) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		displayService.deleteItemSpotFromListData(itemListParam);
		return JsonViewUtils.success();
	}

	/**
	 * 스팟 일괄 할인가 조정
	 * @param itemSpot
	 * @param requestContext
	 * @return
	 */
	@PostMapping("spot/update/discount")
	public JsonView updateDiscountAction(ItemSpot itemSpot, RequestContext requestContext) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		displayService.updateAllSpotDiscount(itemSpot);

		return JsonViewUtils.success();
	}

	/**
	 * 스팟 할인가 조정
	 * @param itemSpot
	 * @param requestContext
	 * @return
	 */
	@PostMapping("spot/update/one/discount")
	public JsonView updateOneDiscountAction(ItemSpot itemSpot, RequestContext requestContext) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		displayService.updateOneSpotDiscount(itemSpot);

		return JsonViewUtils.success();

	}

	/**
	 * 프론트 화면 관리
	 * @return
	 */
	@GetMapping("front-display")
	public String frontDisplay() {

		return ViewUtils.view();
	}

	/**
	 * 모바일 화면 관리
	 * @return
	 */
	@GetMapping("mobile-display")
	public String mobileDisplay() {

		return ViewUtils.getView("/display/mobile-display");
	}

	/**
	 * 반응형 화면 관리
	 * @return
	 */
	@GetMapping("responsive-display")
	public String responsiveDisplay() {

		return ViewUtils.view();
	}

	/**
	 * 해당 화면 전시영역 조회
	 * @param groupCode
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping({"template/{groupCode}", "template/{groupCode}/{viewTarget}"})
	public String displayTemplate(@PathVariable(name="groupCode") String groupCode,
								  @PathVariable(name="viewTarget", required=false) String viewTarget, Model model) {

		DisplayTemplate displayTemplate = displayService.getDisplayTemplateByGroupCode(groupCode);
		DisplayGroupCode displayGroupCode = displayService.getDisplayGroupCodeByGroupCode(groupCode);

		DisplayItemParam displayItemParam = new DisplayItemParam();
		displayItemParam.setDisplayGroupCode(groupCode);
		displayItemParam.setViewTarget(viewTarget);

		List<DisplayItem> displayItemList = displayService.getDisplayItemListByParamForManager(displayItemParam);

		DisplayParam displayParam = new DisplayParam();
		displayParam.setDisplayGroupCode(groupCode);
		displayParam.setViewTarget(viewTarget);

		List<DisplayImage> displayImageList = displayService.getDisplayImageListByParam(displayParam);
		List<DisplayEditor> displayEditorList = displayService.getDisplayEditorListByParam(displayParam);

		model.addAttribute("displayGroupCode", displayGroupCode);
		model.addAttribute("viewTarget", viewTarget);

		model.addAttribute("displayTemplate", displayTemplate);
		model.addAttribute("displayEditorList", displayEditorList);
		model.addAttribute("displayItemList", displayItemList);
		model.addAttribute("displayImageList", displayImageList);

		return ViewUtils.getView("/display/template/form");
	}

	/**
	 *  해당 화면 전시영역 수정 및 입력
	 * @param groupCode
	 * @param sendData
	 * @return
	 */
	@PostMapping({"template/{groupCode}", "template/{groupCode}/{viewTarget}"})
	public String displayTemplateAction(@PathVariable(name="groupCode") String groupCode,
										@PathVariable(name="viewTarget", required=false) String viewTarget, DisplaySendData sendData) {

		String url = "/opmanager/display/template/" + groupCode;
		if (!StringUtils.isEmpty(viewTarget)) {
			url += "/" + viewTarget;
		}

		displayService.updateDisplayDataByGroupCode(sendData);

		return ViewUtils.redirect(url, "수정 되었습니다.");
	}


	@PostMapping("template/{groupCode}/file-delete")
	public JsonView updateOneDiscountAction(@PathVariable("groupCode") String groupCode,
		DisplayImage displayImage, RequestContext requestContext) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		displayService.deleteDisplayImageFile(displayImage);

		return JsonViewUtils.success();

	}

	/**
	 * 전시용 SNS 연동 목록 조회
	 * @param displaySnsParam
	 * @param model
	 * @return
	 */
	@GetMapping("sns/list")
	public String snsList(DisplaySnsParam displaySnsParam, Model model) {

		if (StringUtils.isEmpty(displaySnsParam.getOrderBy())) {
			displaySnsParam.setOrderBy("SNS_ID");
			displaySnsParam.setSort("DESC");
		}

		Pagination pagination = Pagination.getInstance(displayService.getDisplaySnsCount(displaySnsParam),10);
		displaySnsParam.setPagination(pagination);

		model.addAttribute("pagination", pagination);
		model.addAttribute("displaySnsParam", displaySnsParam);
		model.addAttribute("list", displayService.getDisplaySnsList(displaySnsParam));

		return ViewUtils.getView("/display/sns/list");
	}

	/**
	 * 전시용 SNS 연동 정보 등록
	 * @param displaySns
	 * @param model
	 * @return
	 */
	@GetMapping("sns/create")
	public String snsCreate(DisplaySns displaySns, Model model) {

		model.addAttribute("displaySns", displaySns);

		return ViewUtils.getView("/display/sns/form");
	}

	/**
	 * 전시용 SNS 연동 정보 등록 처리
	 * @param displaySns
	 * @return
	 */
	@PostMapping("sns/create")
	public String snsCreateProcess(DisplaySns displaySns) {

		displayService.insertDisplaySns(displaySns);

		return ViewUtils.redirect("/opmanager/display/sns/list", "등록되었습니다.");
	}

	/**
	 * 전시용 SNS 연동 정보 수정
	 * @param snsId
	 * @param model
	 * @return
	 */
	@GetMapping("sns/edit/{snsId}")
	public String snsEdit(@PathVariable("snsId") String snsId, Model model) {
		if (StringUtils.isEmpty(snsId)) {
			throw new PageNotFoundException();
		}

		DisplaySns displaySns = displayService.getDisplaySnsById(Integer.parseInt(snsId));

		if (displaySns == null) {
			throw new PageNotFoundException();
		}

		model.addAttribute("displaySns", displaySns);

		return ViewUtils.getView("/display/sns/form");
	}


	/**
	 * 전시용 SNS 연동 정보 수정 처리
	 * @param displaySns
	 * @return
	 */
	@PostMapping("sns/edit/{snsId}")
	public String snsEditProcess(DisplaySns displaySns) {

		displayService.updateDisplaySns(displaySns);

		return ViewUtils.redirect("/opmanager/display/sns/list", "수정되었습니다.");
	}

	/**
	 * 목록데이터 수정 - 전시용 SNS 노출 순서 설정
	 * @param requestContext
	 * @param displaySnsParam
	 * @return
	 */
	@PostMapping("sns/list/change-ordering")
	public JsonView snsChangeOrdering(RequestContext requestContext, DisplaySnsParam displaySnsParam) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		displayService.updateDisplaySnsOrdering(displaySnsParam);
		return JsonViewUtils.success();

	}

	/**
	 * 전시용 SNS 관리 선택 삭제
	 * @param displaySnsParam
	 * @return
	 */
	@PostMapping("sns/checked-delete")
	public String snsCheckedDelete(DisplaySnsParam displaySnsParam) {

		displayService.deleteDisplaySnsByIds(displaySnsParam);

		return ViewUtils.redirect("/opmanager/display/sns/list", MessageUtils.getMessage("M00205")); // 삭제 되었습니다.

	}

}
