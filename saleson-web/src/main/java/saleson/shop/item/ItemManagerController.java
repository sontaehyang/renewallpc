package saleson.shop.item;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.enumeration.JavaScript;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.repository.CodeService;
import com.onlinepowers.framework.repository.support.EarlyLoadingCodeInfoRepository;
import com.onlinepowers.framework.repository.support.EarlyLoadingRepositoryEvent;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import saleson.common.Const;
import saleson.common.enumeration.LabelType;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.erp.domain.HPR100T;
import saleson.erp.service.ErpService;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.seller.main.support.SellerParam;
import saleson.shop.brand.BrandService;
import saleson.shop.brand.support.BrandParam;
import saleson.shop.categories.domain.Breadcrumb;
import saleson.shop.categoriesfilter.CategoriesFilterService;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.deliverycompany.DeliveryCompanyService;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.deliverycompany.support.DeliveryCompanyParam;
import saleson.shop.item.domain.*;
import saleson.shop.item.support.*;
import saleson.shop.label.LabelService;
import saleson.shop.label.support.LabelDto;
import saleson.shop.point.PointService;
import saleson.shop.restocknotice.RestockNoticeService;
import saleson.shop.restocknotice.domain.RestockNotice;
import saleson.shop.shipment.ShipmentService;
import saleson.shop.shipment.domain.Shipment;
import saleson.shop.shipment.support.ShipmentParam;
import saleson.shop.shipmentreturn.ShipmentReturnService;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;
import saleson.shop.shipmentreturn.support.ShipmentReturnParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/opmanager/item")
@RequestProperty(title="????????????", layout="default", template="opmanager")
public class ItemManagerController {
	private static final Logger log = LoggerFactory.getLogger(ItemManagerController.class);

	@Autowired
	private EarlyLoadingCodeInfoRepository codeInfoRepository;

	@Autowired
	private ItemService itemService;

	@Autowired
	private PointService pointService;

	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;

	@Autowired
	private DeliveryCompanyService deliveryCompanyService;

	@Autowired
	private SellerService sellerService;	// ????????? ??????.

	@Autowired
	private ShipmentService shipmentService;	// ?????????/?????????.

	@Autowired
	private ShipmentReturnService shipmentReturnService;	// ?????????.

	@Autowired
	private CodeService codeService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private RestockNoticeService restockNoticeService;

	@Autowired
	private ErpService erpService;

	@Autowired
	private CategoriesFilterService categoriesFilterService;

	@Autowired
	private LabelService labelService;

	@Autowired
	Environment environment;

	@RequestProperty(layout="base")
	@GetMapping("make-shopping-how")
	public String makeShoppingHow(Model model, @RequestParam(value="r", required=false,defaultValue="0") String reMake) {

		String fileName = environment.getProperty("shop.api.text.save.folder") + "/shopping_how.txt";
		if ("1".equals(reMake)) {
			itemService.makeShoppingHowFile(fileName);
		}

		model.addAttribute("fileName", fileName);
		return ViewUtils.view();
	}

	/**
	 * ?????? ?????? ?????????
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String list(ItemParam itemParam, Model model) {
		// ???????????? / ?????? ????????????.
		//if(!itemParam.getSoldOut().equals("")) itemParam.setConditionType("ITEM_LIST_APPROVAL");
		//if(itemParam.getSoldOut().equals("pending")) itemParam.setConditionType("ITEM_LIST_PENDING_APPROVAL");

		// ???????????? / ?????? ????????????.
		itemParam.setConditionType("ITEM_LIST_APPROVAL");

		invokeItemList(itemParam, model);

		return "view";
	}

	@GetMapping("/seller/list")
	public String listPending(ItemParam itemParam, Model model) {

		// ???????????? / ?????? ????????????.
		itemParam.setConditionType("ITEM_LIST_PENDING_APPROVAL");

		invokeItemList(itemParam, model);

		return "view";
	}

	@GetMapping("/simple/list")
	public String listSimple(ItemParam itemParam, Model model) {

		// ???????????? / ?????? ????????????.
		itemParam.setConditionType("ITEM_SIMPLE_LIST");

		invokeItemList(itemParam, model);
		return "view";
	}

	/**
	 * ?????? ????????? ??????.
	 * @param itemParam
	 * @param model
	 */
	protected void invokeItemList(ItemParam itemParam, Model model) {

		// ??????????????? CategoryId??? ?????? ???????????? ??????.
		if (itemParam.getOrderBy() != null && itemParam.getOrderBy().equals("ORDERING")
			&& (itemParam.getCategoryId() == null || itemParam.getCategoryId().equals(""))) {
			itemParam.setOrderBy("");
			itemParam.setSort("DESC");
		}
		itemParam.setItemDataType("1");

		//itemParam.setDataStatusCode("1");

		// ???????????? ??????.
		if (ShopUtils.isSellerPage() && SellerUtils.isSellerLogin()) {
			itemParam.setSellerId(SellerUtils.getSellerId());
		}

		//2017-01-18 ????????? ????????? ????????? MD?????? ??????MD??? ?????? ???????????? ????????? ??????????????? ??????
		if (!UserUtils.isSupervisor() && UserUtils.isMd() && itemParam.getMdName() == null)
		{
			itemParam.setMdName(UserUtils.getUser().getUserName());
		}

		Pagination pagination = Pagination.getInstance(itemService.getItemCount(itemParam));

		itemParam.setPagination(pagination);

		List<Item> items = itemService.getItemList(itemParam);

		// ????????? (????????? ??????)
		List<CategoriesTeam> categoryTeamList = getCategoryTeamList(categoriesTeamGroupService.getCategoriesTeamGroupList());

		model.addAttribute("brandList", brandService.getBrandList(new BrandParam()));
		model.addAttribute("list",items);
		model.addAttribute("categoryTeamList", categoryTeamList);		// ???????????? ??? ?????? (????????? ??????)
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());

		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam()));
	}

	/**
	 * ??????????????? ?????? - ????????????
	 * @param requestContext
	 * @param itemListParam
	 * @return
	 */
	@PostMapping("list/update")
	public JsonView updateListData(RequestContext requestContext, ItemListParam itemListParam) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		itemService.updateListData(itemListParam);
		return JsonViewUtils.success();
	}

	/**
	 * Seller?????? ??????
	 * @param requestContext
	 * @param itemId
	 * @return
	 */
	@PostMapping("edit/seller-item-approval/{itemId}")
	public JsonView sellerItemApproval(RequestContext requestContext, @PathVariable("itemId") int itemId) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		Item item = new Item();
		item.setItemId(itemId);

		itemService.updateItemApproval(item);
		return JsonViewUtils.success();
	}

	/**
	 * ??????????????? ???????????? ?????? - ???????????? 
	 * @param flag
	 * @param requestContext
	 * @param itemListParam
	 * @return
	 */
	@PostMapping("list/update-display/{flag}")
	public JsonView updateListDataByDisplay(@PathVariable("flag") String flag, RequestContext requestContext, ItemListParam itemListParam) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		itemListParam.setDisplayFlag(flag);

		itemService.updateListDataByDisplay(itemListParam);
		return JsonViewUtils.success();
	}

	/**
	 * ??????????????? ???????????? ?????? - ????????????
	 * @param flag
	 * @param requestContext
	 * @param itemListParam
	 * @return
	 */
	@PostMapping("list/update-label/{flag}")
	public JsonView updateListDataByLabel(@PathVariable("flag") String flag, RequestContext requestContext, ItemListParam itemListParam) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}


		itemListParam.setSoldOut(flag);

		//itemListParam.setStockQuantity(0);

		itemService.updateListDataByLabel(itemListParam);
		return JsonViewUtils.success();
	}

	/**
	 * ??????????????? ?????? - ????????????.
	 * @param requestContext
	 * @param itemListParam
	 * @return
	 */
	@PostMapping("list/delete")
	public JsonView deleteListData(RequestContext requestContext, ItemListParam itemListParam) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		itemService.deleteListData(itemListParam);
		return JsonViewUtils.success();
	}



	/**
	 * ??????????????? ?????? - ?????? ?????? ????????? ????????????.
	 * @param requestContext
	 * @param itemListParam
	 * @return
	 */
	@PostMapping("list/change-ordering")
	public JsonView changeOrdering(RequestContext requestContext, ItemListParam itemListParam) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		itemService.updateItemOrdering(itemListParam);
		return JsonViewUtils.success();

	}


	/**
	 * ???????????? ????????? ????????? ?????? ??????????????? ????????????.
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value = "add-items-to-category")
	public String addItemsToCategory(ItemParam itemParam, Model model) {

		String user = "seller";
		if(UserUtils.isManagerLogin()) {
			user = "opmanager";
		}

		model.addAttribute("itemParam", itemParam);
		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		return "view:/" + user + "/item/add-items-to-category";
	}


	/**
	 * ???????????? ????????? ????????? ?????? ??????????????? ????????????.
	 * ?????? ??????..
	 * @return
	 */
	@RequestProperty(layout="base")
	@PostMapping("add-items-to-category")
	public String addItemsToCategoryAction(ItemListParam itemListParam, Model model) {

		String user = "seller";
		if(UserUtils.isManagerLogin()) {
			user = "opmanager";
		}

		itemService.insertItemCategoryByItemListParam(itemListParam);

		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());

		return ViewUtils.redirect("/" + user + "/item/add-items-to-category", MessageUtils.getMessage("M00691"), JavaScript.CLOSE);	// ??????????????? ?????????????????????.
	}


	/**
	 * ????????????
	 * @param model
	 * @return
	 */
	@GetMapping("create")
	public String create(Model model) {

		Item item = new Item();

		item.setItemReturnFlag("Y");
		item.setNonmemberOrderType("1");
		item.setDisplayFlag("N");
		item.setItemLabel("0");

		if (ShopUtils.isSellerPage()) {
			item.setDeliveryType("2");	// ????????????

		} else {

		}

		// CJH 2016.12.01 ?????? ?????? ????????? ?????? ????????? ?????? 1 : ????????? ??????, 2 : ????????? ??????
		item.setSpotType(ShopUtils.isSellerPage() ? "2" :  "1");

		//item.setSellerId(SellerUtils.getSellerId());


		// ???????????? ???/?????? ?????? 
		List<CategoriesTeam> categoryTeamGroupList = categoriesTeamGroupService.getCategoriesTeamGroupList();

		// ????????? ??????.
		Seller seller = sellerService.getSellerById(SellerUtils.getSellerId());
		if (seller != null) {
			item.setSellerId(seller.getSellerId());
			item.setCommissionRate(seller.getCommissionRate());
		}

		// ?????? ????????? ??????
		ShipmentParam shipmentParam = new ShipmentParam();
		shipmentParam.setSellerId(SellerUtils.getSellerId());
		shipmentParam.setDefaultAddressFlag("Y");
		Shipment shipment = shipmentService.getShipmentByParam(shipmentParam);

		if (shipment != null) {
			item.setShipmentId(shipment.getShipmentId());
			item.setShipmentAddress(shipment.getFullAddress());
		}

		// ?????? ?????? / ?????????
		ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
		shipmentReturnParam.setSellerId(SellerUtils.getSellerId());
		shipmentReturnParam.setDefaultAddressFlag("Y");
		ShipmentReturn shipmentReturn = shipmentReturnService.getShipmentReturnByParam(shipmentReturnParam);

		if (shipmentReturn != null) {
			item.setShipmentReturnId(shipmentReturn.getShipmentReturnId());
			item.setShipmentReturnAddress(shipmentReturn.getFullAddress());
		}

		// ????????? ?????? 
		DeliveryCompanyParam deliveryCompanyParam = new DeliveryCompanyParam();
		deliveryCompanyParam.setLimit(200);
		List<DeliveryCompany> deliveryCompanyList = deliveryCompanyService.getDeliveryCompanyList(deliveryCompanyParam);

		// ????????? ??????  
		SellerParam sellerParam = new SellerParam();
		sellerParam.setStatusCode("2"); // ?????? ???.
		model.addAttribute("sellerList", sellerService.getSellerListByParam(sellerParam));

		// ????????????
		model.addAttribute("privateTypes", ItemUtils.getPrivateTypeCodes());

		model.addAttribute("brandList", brandService.getBrandList(new BrandParam()));
		model.addAttribute("mode", "create");
		model.addAttribute("action", "/opmanager/item/create");
		model.addAttribute("useItemUserCode", "N");
		model.addAttribute("hours", ShopUtils.getHours());
		model.addAttribute("categoryTeamGroupList", categoryTeamGroupList);
		model.addAttribute("categoryTeamList", getCategoryTeamList(categoryTeamGroupList));		// ???????????? ??? ?????? (????????? ??????)
		model.addAttribute("deliveryCompanyList", deliveryCompanyList);
		model.addAttribute("colors", CodeUtils.getCodeList("ITEM_COLOR"));
		model.addAttribute("item", item);
		model.addAttribute("itemNoticeCodes", itemService.getItemNoticeCodes());		// ?????????????????? ?????? 

		model.addAttribute("seller", seller);		// ????????? ??????.

		model.addAttribute("shipment", shipment);		// ?????? ????????? ??????.

		model.addAttribute("restockNoticeCount", 0);
		model.addAttribute("isSellerPage", ShopUtils.isSellerPage());

		// ????????????
		LabelDto labelDto = new LabelDto();
		labelDto.setLabelType(LabelType.ITEM);
		model.addAttribute("labels", labelService.findAll(labelDto.getPredicate()));

		return "view";
	}


	/**
	 * ???????????? ??????
	 * @param item
	 * @param imageFile
	 * @param detailImageFiles
	 * @return
	 */
	@PostMapping("create")
	public String createAction(Item item,
		@RequestParam(value="imageFile", required=false) MultipartFile imageFile,
		@RequestParam(value="detailImageFiles[]", required=false) MultipartFile[] detailImageFiles) {

		item.setItemImageFile(imageFile);
		item.setItemDetailImageFiles(detailImageFiles);

		itemService.insertItem(item);

		try {
			categoriesFilterService.saveItemFilter(item.getItemId(), item.getFilterCodes());
		} catch(Exception e){
			throw new UserException("?????? ???????????? ????????? ?????????????????????.");
		}

		RequestContextUtils.setMessage(MessageUtils.getMessage("M00288"));	// ?????????????????????.

		if (ShopUtils.isSellerPage()) {
			String message = "????????? ?????????????????????.<br/>";
			String returnUrl = "/seller/item/list";

			Seller seller = sellerService.getSellerById(item.getSellerId());

			// ?????????????????? ????????????????????? ????????? ????????? ??????
			if(seller.getItemApprovalType().equals("1")) {
				message += "???????????? ?????? ??? ???????????? ?????????.";
				returnUrl = "/seller/item/pending/list";
			}

			return ViewUtils.redirect(returnUrl, message + item.getItemCode()); // ????????? ???????????? ???????????? alert??? ??????, ????????? ??????
		}

		return "redirect:/opmanager/item/list";
	}


	/**
	 * ?????? ??????
	 * @param itemUserCode
	 * @param model
	 * @return
	 */
	@GetMapping(value={"edit/{itemUserCode}", "seller/edit/{itemUserCode}"})
	public String edit(@PathVariable("itemUserCode") String itemUserCode, Model model) {

		//????????????????????? ????????????
		ItemSaleEditParam itemSaleEditParam = new ItemSaleEditParam();

		Integer itemId = itemService.getItemIdByItemUserCode(itemUserCode);
		if (itemId == null) {
			throw new UserException("??????????????? ????????????.");
		}

		itemSaleEditParam.setSellerId(SellerUtils.getSellerId());
		itemSaleEditParam.setItemId(itemId);
		itemSaleEditParam.setStatus("0");

		int count = itemService.getItemSaleEditCountByParam(itemSaleEditParam);

		if ( count > 0) {
			throw new UserException("?????? ????????? ????????????????????? ?????????.");
		}

		// ???????????? ???/?????? ??????
		List<CategoriesTeam> categoryTeamGroupList = categoriesTeamGroupService.getCategoriesTeamGroupList();

		Item item = itemService.getItemByIdForManager(itemId);

		if (ShopUtils.isSellerPage() && item.getSellerId() != SellerUtils.getSellerId()) {
			throw new PageNotFoundException();
		}

		// ????????? ??????.
		Seller seller = sellerService.getSellerById(item.getSellerId());


		// ?????? ????????? ??????
		ShipmentParam shipmentParam = new ShipmentParam();
		shipmentParam.setShipmentId(item.getShipmentId());

		Shipment shipment = shipmentService.getShipmentByParam(shipmentParam);

		if (shipment != null) {
			item.setShipmentAddress(shipment.getFullAddress());
		}

		// ?????? ?????? / ?????????
		if (item.getShipmentReturnId() > 0) {
			ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
			shipmentReturnParam.setShipmentReturnId(item.getShipmentReturnId());
			ShipmentReturn shipmentReturn = shipmentReturnService.getShipmentReturnByParam(shipmentReturnParam);

			if (shipmentReturn != null) {
				item.setShipmentReturnAddress(shipmentReturn.getFullAddress());
			}

			model.addAttribute("shipmentReturn", shipmentReturn);		// ?????? ????????? ??????.
		}

		// ???????????? ?????? & ????????? ?????? (????????????)
		if ("Y".equals(item.getSpotFlag())
			&& Integer.parseInt(item.getSpotEndDate()) < Integer.parseInt(DateUtils.getToday())) {

			item.setSpotFlag("N");
			item.setSpotStartDate("");
			item.setSpotEndDate("");
			item.setSpotDiscountAmount(0);
		}

		// CJH 2016.12.01 ?????? ???????????? ????????? ?????? ????????? ????????????.
		if ("N".equals(item.getSpotFlag())) {
			item.setSpotType(ShopUtils.isSellerPage() ? "2" :  "1");
		}

		// ????????? ??????
		DeliveryCompanyParam deliveryCompanyParam = new DeliveryCompanyParam();
		deliveryCompanyParam.setLimit(200);
		List<DeliveryCompany> deliveryCompanyList = deliveryCompanyService.getDeliveryCompanyList(deliveryCompanyParam);

		String today = DateUtils.getToday(Const.DATE_FORMAT);

		// ????????? ??????
		SellerParam sellerParam = new SellerParam();
		sellerParam.setStatusCode("2"); // ?????? ???.
		model.addAttribute("sellerList", sellerService.getSellerListByParam(sellerParam));

		// ????????????
		model.addAttribute("privateTypes", ItemUtils.getPrivateTypeCodes());

		if (!StringUtils.isEmpty(item.getSpotStartTime()) && !StringUtils.isEmpty(item.getSpotEndTime())) {
			String spotStarttime = item.getSpotStartTime();
			String spotEndtime = item.getSpotEndTime();

			model.addAttribute("spotStartHour", spotStarttime.substring(0, 2));
			model.addAttribute("spotStartMinute", spotStarttime.substring(2, 4));
			model.addAttribute("spotEndHour", spotEndtime.substring(0, 2));
			model.addAttribute("spotEndMinute", spotEndtime.substring(2, 4));
		}

		model.addAttribute("today", today);
		model.addAttribute("brandList", brandService.getBrandList(new BrandParam()));
		model.addAttribute("mode", "edit");
		model.addAttribute("action", "/opmanager/item/edit/" + itemId);
		model.addAttribute("useItemUserCode", "Y");
		model.addAttribute("item", item);

		List<Breadcrumb> a = item.getBreadcrumbs();
		if (a != null && a.size() > 0) {
			Breadcrumb b = a.get(a.size()-1);
			String categoryId = b.getBreadcrumbCategories().get(b.getBreadcrumbCategories().size()-1).getCategoryId();
//			categoryList = categoriesFilterService.getBreadcrumbFilterGroupList(Integer.parseInt(categoryId));
			model.addAttribute("categoryId", categoryId);
		}

		model.addAttribute("itemNoticeCodes", itemService.getItemNoticeCodes());		// ?????????????????? ??????
		model.addAttribute("hours", ShopUtils.getHours());
		model.addAttribute("categoryTeamGroupList", categoryTeamGroupList);
		model.addAttribute("categoryTeamList", getCategoryTeamList(categoryTeamGroupList));		// ???????????? ??? ?????? (????????? ??????)
		model.addAttribute("deliveryCompanyList", deliveryCompanyList);
		model.addAttribute("colors", CodeUtils.getCodeList("ITEM_COLOR"));
		model.addAttribute("pointConfigList", pointService.getPointConfigListByItemId(itemId));
		model.addAttribute("listPage", RequestContextUtils.getRequestContext().getPrevPageUrl());

		model.addAttribute("seller", seller);		// ????????? ??????.
		model.addAttribute("shipment", shipment);		// ?????? ????????? ??????.

		model.addAttribute("isSellerPage", ShopUtils.isSellerPage());

		// ???????????????
		RestockNotice restockNotice = new RestockNotice();
		restockNotice.setItemId(item.getItemId());
		model.addAttribute("restockNoticeCount", restockNoticeService.getRestockNoticeCount(restockNotice));

		// ????????????
		LabelDto labelDto = new LabelDto();
		labelDto.setLabelType(LabelType.ITEM);
		model.addAttribute("labels", labelService.findAll(labelDto.getPredicate()));

		return "view:/item/form";
	}


	/**
	 * ?????? ?????? ??????
	 * @param itemUserCode
	 * @param item
	 * @param imageFile
	 * @param detailImageFiles
	 * @param listPage
	 * @return
	 */
	@PostMapping(value={"edit/{itemUserCode}", "seller/edit/{itemUserCode}"})
	public String editAction(@PathVariable("itemUserCode") String itemUserCode, Item item,
		@RequestParam(value="imageFile", required=false) MultipartFile imageFile,
		@RequestParam(value="detailImageFiles[]", required=false) MultipartFile[] detailImageFiles,
		@RequestParam(required = false) String listPage) {

		Item originalItem = itemService.getItemByItemUserCode(itemUserCode);
		if (originalItem == null) {
			throw new UserException("??????????????? ????????????.");
		}

		item.setItemId(originalItem.getItemId());
		item.setItemImageFile(imageFile);
		item.setItemDetailImageFiles(detailImageFiles);

		itemService.updateItem(item);

		try {
			categoriesFilterService.saveItemFilter(item.getItemId(), item.getFilterCodes());
		} catch (Exception e) {
			throw new UserException("?????? ???????????? ????????? ?????????????????????.");
		}

		String returnUrl = "";

		if (StringUtils.isEmpty(listPage)) {
			returnUrl = "/opmanager/item/list";
		} else {
			returnUrl = listPage;
		}

		String message = MessageUtils.getMessage("M00289");	// ?????????????????????.

		if (ShopUtils.isSellerPage()) {
			Seller seller = sellerService.getSellerById(item.getSellerId());
			if (seller.getItemApprovalType().equals("1")) {
				message += " ???????????? ?????? ??? ???????????? ?????????.";
				returnUrl = "/seller/item/pending/list";
			}
		}

		//return ViewUtils.redirect("/opmanager/item/edit/" + itemId, MessageUtils.getMessage("M00289"));	// ?????????????????????.
		return ViewUtils.redirect(returnUrl, message);
	}

	@PostMapping("seller-info/{sellerId}")
	public JsonView sellerInfo(@PathVariable("sellerId") long sellerId) {
		// ????????? ??????.
		Seller seller = sellerService.getSellerById(sellerId);


		// ?????? ????????? ??????
		ShipmentParam shipmentParam = new ShipmentParam();
		shipmentParam.setSellerId(sellerId);

		Shipment shipment = shipmentService.getShipmentByParam(shipmentParam);


		// ?????? ?????? / ?????????
		ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
		shipmentReturnParam.setSellerId(sellerId);
		ShipmentReturn shipmentReturn = shipmentReturnService.getShipmentReturnByParam(shipmentReturnParam);

		HashMap<String, Object> result = new HashMap<>();
		result.put("seller", seller);
		result.put("shipment", shipment);
		result.put("shipmentReturn", shipmentReturn);

		return JsonViewUtils.success(result);
	}

	/**
	 * ?????? ??????
	 * @param itemId
	 * @param model
	 * @return
	 */
	@GetMapping("copy/{itemId}")
	public String copy(@PathVariable("itemId") int itemId, Model model) {
		// ???????????? ???/?????? ?????? 
		List<CategoriesTeam> categoryTeamGroupList = categoriesTeamGroupService.getCategoriesTeamGroupList();

//		Item item = itemService.getItemById(itemId);
		Item item = itemService.getItemByIdForManager(itemId);

		item.setItemId(0);
		item.setItemUserCode("");
		item.setItemCode("");

		item.setItemImage("");
		item.setItemImages(null);

		// ?????? ???????????? ?????? ???????????? ????????? ?????? ?????? ?????????
		if (item.getItemOptions() != null) {
			for (ItemOption itemOption : item.getItemOptions()) {
				itemOption.setItemOptionId(0);
			}
		}

		// ????????? ??????.
		Seller seller = sellerService.getSellerById(item.getSellerId());


		// ?????? ????????? ??????
		ShipmentParam shipmentParam = new ShipmentParam();
		shipmentParam.setShipmentId(item.getShipmentId());

		Shipment shipment = shipmentService.getShipmentByParam(shipmentParam);

		if (shipment != null) {
			item.setShipmentAddress(shipment.getFullAddress());
		}

		// ?????? ?????? / ?????????
		ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
		if (item.getShipmentReturnId() == 0) {
			shipmentReturnParam.setDefaultAddressFlag("Y");
		}

		shipmentReturnParam.setShipmentReturnId(item.getShipmentReturnId());
		ShipmentReturn shipmentReturn = shipmentReturnService.getShipmentReturnByParam(shipmentReturnParam);

		if (shipmentReturn != null) {
			item.setShipmentReturnAddress(shipmentReturn.getFullAddress());
		}

		model.addAttribute("categoryTeamGroupList", categoryTeamGroupList);
		model.addAttribute("categoryTeamList", getCategoryTeamList(categoryTeamGroupList)); // ???????????? ??? ?????? (????????? ??????)

		// ????????? ?????? 
		DeliveryCompanyParam deliveryCompanyParam = new DeliveryCompanyParam();
		deliveryCompanyParam.setLimit(200);
		List<DeliveryCompany> deliveryCompanyList = deliveryCompanyService.getDeliveryCompanyList(deliveryCompanyParam);

		// ????????? ??????  
		SellerParam sellerParam = new SellerParam();
		sellerParam.setStatusCode("2"); // ?????? ???.
		model.addAttribute("sellerList", sellerService.getSellerListByParam(sellerParam));

		// ????????? ??????
		model.addAttribute("brandList", brandService.getBrandList(new BrandParam()));

		// ????????????
		model.addAttribute("privateTypes", ItemUtils.getPrivateTypeCodes());

		model.addAttribute("shipmentReturn", shipmentReturn);		// ?????? ????????? ??????.
		model.addAttribute("mode", "copy");
		model.addAttribute("action", "/opmanager/item/copy/" + itemId);
		model.addAttribute("useItemUserCode", "N");
		model.addAttribute("item", item);

		List<Breadcrumb> a = item.getBreadcrumbs();
		if(a != null && a.size() > 0){
			Breadcrumb b = a.get(a.size()-1);
			String categoryId = b.getBreadcrumbCategories().get(b.getBreadcrumbCategories().size()-1).getCategoryId();
			model.addAttribute("categoryId", categoryId);
		}

		model.addAttribute("hours", ShopUtils.getHours());
		model.addAttribute("categoryTeamGroupList", categoryTeamGroupList);
		model.addAttribute("categoryTeamList", getCategoryTeamList(categoryTeamGroupList));		// ???????????? ??? ?????? (????????? ??????)
		model.addAttribute("deliveryCompanyList", deliveryCompanyList);
		model.addAttribute("colors", CodeUtils.getCodeList("ITEM_COLOR"));
		model.addAttribute("pointConfigList", pointService.getPointConfigListByItemId(itemId));

		model.addAttribute("seller", seller);		// ????????? ??????. 
		model.addAttribute("shipment", shipment);		// ?????? ????????? ??????.

		model.addAttribute("itemNoticeCodes", itemService.getItemNoticeCodes());		// ?????????????????? ?????? 
		model.addAttribute("isSellerPage", ShopUtils.isSellerPage());

		// ????????????
		LabelDto labelDto = new LabelDto();
		labelDto.setLabelType(LabelType.ITEM);
		model.addAttribute("labels", labelService.findAll(labelDto.getPredicate()));

		return "view:/item/form";
	}

	/**
	 * ?????? ?????? ??????.
	 * @param item
	 * @param imageFile
	 * @param detailImageFiles
	 * @return
	 */
	@PostMapping("copy/{itemId}")
	public String copyAction(Item item,
		@RequestParam(value="imageFile", required=false) MultipartFile imageFile,
		@RequestParam(value="detailImageFiles[]", required=false) MultipartFile[] detailImageFiles) {

		try {
			categoriesFilterService.saveItemFilter(item.getItemId(), item.getFilterCodes());
		} catch(Exception e){
			throw new UserException("?????? ???????????? ????????? ?????????????????????.");
		}

		String result = createAction(item, imageFile, detailImageFiles);
		if (result == null) {
			return "redirect:/opmanager/item/list";
		}

		return result;
	}


	/**
	 * ?????? ?????????????????? ????????????.
	 * @param requestContext
	 * @param itemId
	 * @return
	 */
	@PostMapping("delete-item-image")
	public JsonView deleteItemImage(RequestContext requestContext, @RequestParam("itemId") int itemId) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		itemService.deleteItemImageByItemId(itemId);

		return JsonViewUtils.success();
	}


	/**
	 * ?????? ?????? ???????????? ????????????.
	 * @param requestContext
	 * @param itemImageId
	 * @return
	 */
	@PostMapping("delete-item-details-image")
	public JsonView deleteItemDetailsImage(RequestContext requestContext, @RequestParam("itemImageId") int itemImageId) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		itemService.deleteItemImageById(itemImageId);

		return JsonViewUtils.success();
	}



	/**
	 * ????????????.
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("find-item")
	public String findItem(ItemParam itemParam, Model model) {
		setFindItemModel(itemParam, model);
		return ViewUtils.view();
	}

	protected void setFindItemModel(ItemParam itemParam, Model model) {

		itemParam.setDataStatusCode("1");
		itemParam.setDiscriminator("item_option");

		// ?????? ????????? ????????? ????????? ??????
		if (!itemParam.getTargetId().startsWith("itemOption")) {
			itemParam.setDisplayFlag("Y");
		}

		// ??????????????? (?????????????????? ??????)
		itemParam.setItemDataType("1");
		itemParam.setConditionType("FIND_ITEM_POPUP");

		// ??????????????? ?????????
		if ("recommend".equals(itemParam.getTargetId())) {
			itemParam.setRecommendFlag("Y");
		}

		// ??????????????? ??????
		if ("spot".equals(itemParam.getTargetId())) {
			itemParam.setConditionType("SPOT_ITEM");
		}

		// ????????????????????? ??????
		if ("addition".equals(itemParam.getTargetId())) {
			itemParam.setItemOptionFlag("N");
		}

		int totalItems = itemService.getItemCount(itemParam);

		Pagination pagination = Pagination.getInstance(totalItems);
		//pagination.setItemsPerPage(6);

		itemParam.setPagination(pagination);

		List<Item> list = itemService.getItemList(itemParam);
		model.addAttribute("list",list);
		model.addAttribute("itemParam",itemParam);
		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam()));
		model.addAttribute("pagination",pagination);
	}

	@RequestProperty(layout="base")
	@GetMapping("color")
	public String color(Model model) {

		model.addAttribute("colors", CodeUtils.getCodeList("ITEM_COLOR"));
		return ViewUtils.view();
	}


	@PostMapping("color")
	public JsonView color(RequestContext requestContext, Code code) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		code.setLanguage(LocaleContextHolder.getLocale().getLanguage());
		codeService.insertCommonCode(code);


		// Code reload
		EarlyLoadingRepositoryEvent codeReloadEvent = new EarlyLoadingRepositoryEvent("codeInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
		codeInfoRepository.onApplicationEvent(codeReloadEvent);

		return JsonViewUtils.success();

	}


	/**
	 * ?????? ??????????????? ????????????.
	 * @param requestContext
	 * @param code
	 * @return
	 */
	@PostMapping("delete-color")
	public JsonView deleteColor(RequestContext requestContext, Code code) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		code.setLanguage(LocaleContextHolder.getLocale().getLanguage());
		codeService.deleteCommonCode(code);


		// Code reload
		EarlyLoadingRepositoryEvent codeReloadEvent = new EarlyLoadingRepositoryEvent("codeInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
		codeInfoRepository.onApplicationEvent(codeReloadEvent);

		return JsonViewUtils.success();

	}


	/**
	 * ????????? ???????????? ????????????
	 * @param itemUserCode
	 * @return
	 */
	@ResponseBody
	@PostMapping("check-for-duplicate-item-user-code")
	public JsonView checkForDuplicateItemUserCode(@RequestParam("itemUserCode") String itemUserCode) {
		int count = itemService.getItemCountByItemUserCode(itemUserCode);

		if (count == 0) {
			return JsonViewUtils.success();
		} else {
			return JsonViewUtils.failure("??????");
		}

	}

	/**
	 * ?????? ??????/?????? ???????????? ????????? ????????? ???/?????? ??????????????? ????????????.
	 * ????????????, ??????, ??????, ????????? ?????? ??????, ??????/?????????, ?????????
	 * @param categoryTeamGroupList
	 * @return
	 */
	private List<CategoriesTeam> getCategoryTeamList(List<CategoriesTeam> categoryTeamGroupList) {
		List<CategoriesTeam> categoryTeamList = new ArrayList<>();

		// ?????????
		CategoriesTeam noTeam = new CategoriesTeam();
		noTeam.setCode("-");
		noTeam.setName("??????");
		categoryTeamList.add(noTeam);

		for (CategoriesTeam categoriesTeam : categoryTeamGroupList) {
			if (categoriesTeam.getCategoryTeamFlag().equals("Y")) {
				categoryTeamList.add(categoriesTeam);
			}
		}

		return categoryTeamList;
	}

	/**
	 * ?????? ?????????
	 * @param itemParam
	 * @param itemReview
	 * @param model
	 * @return
	 */
	@GetMapping("review/list")
	public String reviewList(ItemParam itemParam, ItemReview itemReview, Model model) {

		int reviewCount = itemService.getItemReviewCountByParam(itemParam);

		Pagination pagination = Pagination.getInstance(reviewCount);

		itemParam.setPagination(pagination);

		String today = DateUtils.getToday(Const.DATE_FORMAT);

		List<ItemReview> reviewList = itemService.getItemReviewListByParam(itemParam);

		for (ItemReview review : reviewList) {
			String star = "";
			for (int i = 0; i < review.getScore(); i++) {
				star += "???";
			}
			review.setStarScore(star);
		}

		SellerParam sellerParam = new SellerParam();
		sellerParam.setStatusCode("2");
		model.addAttribute("sellerList", sellerService.getSellerListByParam(sellerParam));
		model.addAttribute("reviewCount", reviewCount);
		model.addAttribute("itemParam", itemParam);
		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month3", DateUtils.addYearMonthDay(today, 0, -3, 0));
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("pagination", pagination);

		return "view";
	}

	/**
	 * ???????????? ??????
	 * @param itemReviewId
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("review/edit/{itemReviewId}")
	public String updateReview(@PathVariable("itemReviewId") int itemReviewId, ItemParam itemParam, Model model) {

		ItemReview itemReview = itemService.getItemReviewById(itemReviewId);

		model.addAttribute("itemReview", itemReview);
		model.addAttribute("itemParam", itemParam);

		return ViewUtils.getView("/item/review/form");
	}

	/**
	 * ??????????????????
	 * @param itemReviewId
	 * @param itemReview
	 * @return
	 */
	@PostMapping("review/edit/{itemReviewId}")
	public String updateReviewAction(@PathVariable("itemReviewId") int itemReviewId, ItemReview itemReview) {

		itemService.updateItemReview(itemReview);

		return ViewUtils.redirect("/opmanager/item/review/list", MessageUtils.getMessage("M00289"));	// ?????????????????????. 
	}

	/**
	 * ??????????????????
	 * @param itemReviewId
	 * @return
	 */
	@PostMapping("review/delete/{itemReviewId}")
	public String deleteReview(@PathVariable("itemReviewId") int itemReviewId) {

		itemService.deleteItemReview(itemReviewId);

		return ViewUtils.redirect("/opmanager/item/review/list", MessageUtils.getMessage("M00205")); //?????????????????????.  
	}

	/**
	 * ?????? ????????? ??????
	 * @param requestContext
	 * @param itemReview
	 * @param itemReviewImage
	 * @return
	 */
	@PostMapping("delete-item-review-image")
	public JsonView deleteItemReviewImage(RequestContext requestContext,
										  ItemReview itemReview, ItemReviewImage itemReviewImage) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		itemService.deleteItemReviewImage(itemReview, itemReviewImage);

		return JsonViewUtils.success();
	}

	/**
	 * Q&A ????????? ??????
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@GetMapping("review/recommend")
	public String recommendReview(RequestContext requestContext, ListParam listParam, Model model) {

		model.addAttribute("listParam", listParam);

		return ViewUtils.getView("/item/review/popup");
	}


	@RequestProperty(layout="base")
	@GetMapping("/download-item-csv")
	public ModelAndView downloadItemCsv(ItemParam itemParam) {

		// ??????????????? CategoryId??? ?????? ???????????? ??????.
		if (itemParam.getOrderBy() != null && itemParam.getOrderBy().equals("ORDERING")
			&& (itemParam.getCategoryId() == null || itemParam.getCategoryId().equals(""))) {
			itemParam.setOrderBy("");
			itemParam.setSort("DESC");
		}
		itemParam.setDataStatusCode("1");

		Pagination pagination = Pagination.getInstance(itemService.getItemCount(itemParam), 40000);
		pagination.setItemsPerPage(100000);
		itemParam.setPagination(pagination);


		// CSV
		//ModelAndView mav = new ModelAndView(new ItemCsvView("item_" + DateUtils.getToday() + ".csv"));

		// Excel
		ModelAndView mav = new ModelAndView(new ItemExcelView());
		mav.addObject("itemList", itemService.getItemList(itemParam));
		mav.addObject("itemCategoryList", itemService.getItemCategoryListForExcel(itemParam));		// ?????? ????????????
		mav.addObject("itemRelationList", itemService.getItemRelationListForExcel(itemParam));		// ????????? ????????????
		mav.addObject("itemPointConfigList", itemService.getItemPointListForExcel(itemParam));		// ????????? ????????? ??????.


		return mav;
	}

	/**
	 * ?????? ???????????? ??????
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("/download-excel")
	public String downloadExcel(ItemParam itemParam, Model model) {
		model.addAttribute("itemParam", itemParam);
		return "view";
	}


	/**
	 * ?????? ????????????
	 * @param itemParam
	 * @return
	 */
	@RequestProperty(layout="base")
	@PostMapping("/download-excel")
	public ModelAndView downloadExcelProcess(ItemParam itemParam) {

		// ?????? ????????? ?????? ??????
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("?????? ???????????? ????????? ????????????.");
		}

		// Excel
		ModelAndView mav = new ModelAndView(new ItemExcelView());

		// ????????? ??????.
		mav.addObject("brandList", brandService.getBrandList(new BrandParam()));

		mav.addObject("itemParam", itemParam);

		itemParam.setConditionType("EXCEL_DOWNLOAD");

		// ??????????????? ?????????????????? ???????????? ?????? ?????? ?????? ????????? ???????????? ????????? ????????? ???????????????.
		if (itemParam.getExcelItemUserCodes().size() > 0) {
			itemParam.setConditionType("EXCEL_ITEM_USER_CODE_FIX");
		}


		// ?????? ?????? (ITEM, ITEM_OPTION, ITEM_IMAGE) ??? ?????? OrderManagerController
		if (Arrays.asList(itemParam.getExcelDownloadData()).contains("item_main")
			|| Arrays.asList(itemParam.getExcelDownloadData()).contains("item_seo")
			|| Arrays.asList(itemParam.getExcelDownloadData()).contains("item_check")
			|| Arrays.asList(itemParam.getExcelDownloadData()).contains("item_option")
			|| Arrays.asList(itemParam.getExcelDownloadData()).contains("item_image")) {


			// ??????????????? CategoryId??? ?????? ???????????? ??????.
			if (itemParam.getOrderBy() != null && itemParam.getOrderBy().equals("ORDERING")
				&& (itemParam.getCategoryId() == null || itemParam.getCategoryId().equals(""))) {
				itemParam.setOrderBy("");
				itemParam.setSort("DESC");
			}

			Pagination pagination = Pagination.getInstance(itemService.getItemCount(itemParam), 40000);
			pagination.setItemsPerPage(100000);
			itemParam.setPagination(pagination);

			// ????????? ?????? 
			DeliveryCompanyParam deliveryCompanyParam = new DeliveryCompanyParam();
			deliveryCompanyParam.setLimit(200);
			List<DeliveryCompany> deliveryCompanyList = deliveryCompanyService.getDeliveryCompanyList(deliveryCompanyParam);

			SellerParam sellerParam = new SellerParam();

			// ?????????????????? ?????? ????????? ??????
			if (ShopUtils.isSellerPage()) {
				itemParam.setSellerId(SellerUtils.getSellerId());
				sellerParam.setSellerId(SellerUtils.getSellerId());
			}

			List<Item> itemList = itemService.getItemList(itemParam);
			for (Item item : itemList) {
				for (ItemOptionGroup itemOptionGroup : item.getItemOptionGroups()) {
					if (itemOptionGroup.getItemOptions() != null) {
						item.setItemOptions(itemOptionGroup.getItemOptions());
						itemOptionGroup.setItemOptions(itemService.getItemOptionList(item, false));
					}
				}
			}

			mav.addObject("itemList", itemList);
			mav.addObject("deliveryCompanyList", deliveryCompanyList);	// ????????? ??????

			sellerParam.setStatusCode("2"); // ?????? ???.
			sellerParam.setConditionType("SELLER_LIST_FOR_SELECTBOX");

			mav.addObject("sellerList", sellerService.getSellerListByParam(sellerParam));	// ????????? ??????

			mav.addObject("itemPointConfigList", itemService.getItemPointListForExcel(itemParam));			// ????????? ????????? ??????.

			long deliverySellerId = itemParam.getSellerId();
			long shipmentReturnSellerId = itemParam.getSellerId();

			if ("1".equals(itemParam.getDeliveryType())) {
				deliverySellerId = 90000000;
			}

			if ("1".equals(itemParam.getShipmentReturnType())) {
				shipmentReturnSellerId = 90000000;
			}

			mav.addObject("shipmentList", shipmentService.getShipmentListBySellerId(deliverySellerId));	// ????????? ?????? ??????
			mav.addObject("shipmentReturnList", shipmentReturnService.getShipmentReturnListBySellerId(shipmentReturnSellerId));	// ??????/?????? ?????? ??????
		}

		if (Arrays.asList(itemParam.getExcelDownloadData()).contains("item_table")) {
			mav.addObject("itemInfoList", itemService.getItemInfoListForExcel(itemParam));				// ?????? ?????? ??????
			mav.addObject("itemNoticeCodes", itemService.getItemNoticeCodes());
		}
		
		/*
		if (Arrays.asList(itemParam.getExcelDownloadData()).contains("item_table_mobile")) {
			mav.addObject("itemInfoMobileList", itemService.getItemInfoMobileListForExcel(itemParam));				// ?????? ?????? ?????? (?????????)
		}
		*/

		if (Arrays.asList(itemParam.getExcelDownloadData()).contains("item_category")) {
			mav.addObject("itemCategoryList", itemService.getItemCategoryListForExcel(itemParam));		// ?????? ????????????
		}

		if (Arrays.asList(itemParam.getExcelDownloadData()).contains("item_addition")) {
			mav.addObject("itemAdditionList", itemService.getItemAdditionListForExcel(itemParam));		// ????????? ??????????????????
		}

		if (Arrays.asList(itemParam.getExcelDownloadData()).contains("item_relation")) {
			mav.addObject("itemRelationList", itemService.getItemRelationListForExcel(itemParam));		// ????????? ????????????
		}
		
		/*
		if (Arrays.asList(itemParam.getExcelDownloadData()).contains("item_point")) {
			mav.addObject("itemPointConfigList", itemService.getItemPointListForExcel(itemParam));			// ????????? ????????? ??????.
		}
		*/

		if (Arrays.asList(itemParam.getExcelDownloadData()).contains("item_keyword")) {
			mav.addObject("itemKeywordList", itemService.getItemKeywordListForExcel(itemParam));			// ????????? ?????????
		}

		return mav;
	}




	/**
	 * ?????? ????????? 
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("/upload-excel")
	public String uploadExcel(Model model) {

		if (RedirectAttributeUtils.hasRedirectAttributes()) {
			model.addAttribute("result", RedirectAttributeUtils.get("result"));
		}
		return "view";
	}

	/**
	 * ?????? ????????? ??????.
	 * @param multipartFile
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@PostMapping("/upload-excel")
	public String uploadExcelProcess(@RequestParam(value="file", required=false) MultipartFile multipartFile, Model model) {
		String result = itemService.insertExcelData(multipartFile);

		model.addAttribute("result", result);
		//redirectAttribute.addAttribute("result", result);
		RedirectAttributeUtils.addAttribute("result", result);

		if(ShopUtils.isSellerPage()) {
			return "redirect:/seller/item/upload-excel";
		}

		return ViewUtils.redirect("/opmanager/item/upload-excel");
	}



	@PostMapping("item-notice-list")
	@RequestProperty(layout="base")
	public JsonView itemNoticeList(RequestContext requestContext, String itemNoticeCode) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		List<ItemNotice> itemNoticeList = itemService.getItemNoticeListByCode(itemNoticeCode);
		return JsonViewUtils.success(itemNoticeList);
	}



	@RequestProperty(layout="base")
	@GetMapping("/upload-csv")
	public String uploadCsv(HttpSession session) {

		return ViewUtils.view();
	}


	@PostMapping("/upload-csv")
	public String uploadCsvProcess(@RequestParam(value="file[]", required=false) MultipartFile[] multipartFiles, HttpSession session) {
		Future<AsyncReport> asyncReport = itemService.uploadCsv(multipartFiles);

		// session.setAttribute("asyncReport", asyncReport);

		return ViewUtils.redirect("/opmanager/item/upload-csv","", JavaScript.CLOSE_AND_OPENER_RELOAD);
	}



	@SuppressWarnings("unchecked")
	@GetMapping("/upload-csv-status")
	public JsonView uploadCsvStatus(HttpSession session) {
		if (session.getAttribute("asyncReport") == null || session.getAttribute("asyncReport").equals("")) {
			return JsonViewUtils.success(new AsyncReport());
		}

		Future<AsyncReport> future = (Future<AsyncReport>) session.getAttribute("asyncReport");
		AsyncReport asyncReport;
		try {
			asyncReport = (AsyncReport) future.get();

			if(future.isDone()) {
				asyncReport.setStatus(AsyncReport.COMPLETE);
				// session.removeAttribute("asyncReport");
				return JsonViewUtils.success(asyncReport);
			}

			asyncReport.setStatus(AsyncReport.WORKING);

			return JsonViewUtils.success(asyncReport);

		} catch (Exception e) {
			log.warn("[Excepton] uploadCsvStatus : {}", e.getMessage());
			asyncReport = new AsyncReport();
			asyncReport.setMessage(e.getMessage());
			return JsonViewUtils.success(asyncReport);
		}

	}




	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(int.class, "salePrice", new ItemPricePropertyEditor());
		dataBinder.registerCustomEditor(int.class, "salePriceNonmember", new ItemPricePropertyEditor());
		dataBinder.registerCustomEditor(int.class, "stockQuantity", new ItemQuantityPropertyEditor());
		dataBinder.registerCustomEditor(int.class, "orderMinQuantity", new ItemQuantityPropertyEditor());
		dataBinder.registerCustomEditor(int.class, "orderMaxQuantity", new ItemQuantityPropertyEditor());
	}

	/**
	 * (??????) ?????? ?????? ?????? ?????? ??????
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="log")
	@GetMapping("popup/log/{itemId}")
	public String logList(ItemParam itemParam, @PathVariable("itemId") int itemId, Model model) {

		itemParam.setItemId(itemId);

		int itemCount = itemService.getItemLogCountById(itemParam);

		// ?????????
		Pagination pagination = Pagination.getInstance(itemCount);
		itemParam.setPagination(pagination);

		model.addAttribute("list", itemService.getItemLogListById(itemParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("itemCount", itemCount);

		return ViewUtils.getView("/item/popup/log-list");
	}

	/**
	 * ?????????????????? ????????????
	 * @param itemSaleEditParam
	 * @param model
	 * @return
	 */
	@GetMapping("sale-edit/list")
	public String editSaleList(ItemSaleEditParam itemSaleEditParam, Model model) {

		if ( itemSaleEditParam.getStatus() == null ) { //????????? : ????????????
			itemSaleEditParam.setStatus("0");
		}

		Pagination pagination = Pagination.getInstance(itemService.getItemSaleEditCountByParam(itemSaleEditParam));
		itemSaleEditParam.setPagination(pagination);

		List<ItemSaleEdit> list = itemService.getItemSaleEdit(itemSaleEditParam);

		model.addAttribute("list", list);
		model.addAttribute("itemSaleEditParam",itemSaleEditParam);
		model.addAttribute("pagination",pagination);
		return "view:sale-edit/list";
	}

	@PostMapping("sale-edit/update")
	public JsonView updateSaleEdit(HttpServletRequest request, RequestContext requestContext,
		ItemListParam itemListParam) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		String message = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = new Date();

		ItemSaleEdit itemSaleEdit = new ItemSaleEdit();

		if ("complete".equals(itemListParam.getRequestStatus())) {
			message = "[ "+sdf.format(today)+"_"+UserUtils.getLoginId()+" ] ??????????????????\n";
			itemSaleEdit.setMessage(message);
			itemSaleEdit.setStatus("1");//????????????
		} else {
			message = "[ "+sdf.format(today)+"_"+UserUtils.getLoginId()+" ] ??????????????????\n";
			itemSaleEdit.setMessage(message);
			itemSaleEdit.setStatus("2");//????????????
		}

		ItemSaleEditParam itemSaleEditParam = new ItemSaleEditParam();
		ItemSaleEdit itemPriceInfo = new ItemSaleEdit();

		for (String itemSaleEditId : itemListParam.getId()) {

			if ("complete".equals(itemListParam.getRequestStatus())) { //???????????? ???, ????????????????????????
				//????????????????????????
				itemSaleEditParam.setItemSaleEditId(Integer.parseInt(itemSaleEditId));
				itemPriceInfo = itemService.getItemSaleEditByParam(itemSaleEditParam);//??????????????? ???????????? ????????????

				if (itemPriceInfo == null) {
					throw new UserException("??????????????? ????????????????????????.");
				}

				itemService.updateItemPrice(itemPriceInfo);//????????????????????????
			}

			itemSaleEdit.setItemSaleEditId(Integer.parseInt(itemSaleEditId));
			itemService.updateSaleEditStatus(itemSaleEdit);//????????????
		}

		return JsonViewUtils.success();
	}

	/**
	 * ????????? ??????
	 * @param itemSaleEditId
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("sale-edit/view/{itemSaleEditId}")
	public String viewMessage(@PathVariable("itemSaleEditId") int itemSaleEditId, Model model) {

		ItemSaleEditParam itemSaleEditParam = new ItemSaleEditParam();
		itemSaleEditParam.setItemSaleEditId(itemSaleEditId);

		ItemSaleEdit itemSaleEdit = itemService.getItemSaleEditByParam(itemSaleEditParam);

		model.addAttribute("itemSaleEdit", itemSaleEdit);
		model.addAttribute("listPage", RequestContextUtils.getRequestContext().getPrevPageUrl());

		return "view:sale-edit/view";
	}

	/**
	 * ??????????????? ????????? ??????
	 * @param requestContext
	 * @param itemId
	 * @return
	 */
	@ResponseBody
	@PostMapping("restock-notice/message")
	public JsonView message(RequestContext requestContext, @RequestParam("itemId") int itemId) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		try {
			restockNoticeService.sendRestockNotice(itemId);
		} catch(Exception e) {
			return JsonViewUtils.failure(e.getMessage());
		}

		return JsonViewUtils.success();
	}

	/**
	 * ?????? ERP ?????? ?????????
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("find-erp-item")
	public String findErpItem() {
		return ViewUtils.view();
	}

	/**
	 * ?????? ERP ?????? ?????? (?????? ???, ????????? ??????)
	 * @param requestContext
	 * @param itemCode
	 * @return
	 */
	@ResponseBody
	@PostMapping("find-erp-item")
	public JsonView findErpItemAction(RequestContext requestContext, @RequestParam("itemCode") String itemCode) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		HPR100T item = null;

		try {
			item = erpService.findHPR100TByItemCode(itemCode);
		} catch(Exception e) {
			return JsonViewUtils.failure(e.getMessage());
		}

		return JsonViewUtils.success(item);
	}
}
