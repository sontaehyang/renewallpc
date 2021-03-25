package saleson.shop.seller.item;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.Const;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.UserUtils;
import saleson.seller.main.SellerService;
import saleson.seller.main.support.SellerParam;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.item.ItemManagerController;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemReview;
import saleson.shop.item.domain.ItemSaleEdit;
import saleson.shop.item.support.ItemListParam;
import saleson.shop.item.support.ItemParam;
import saleson.shop.item.support.ItemSaleEditParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestProperty(template="seller", layout="default")
@RequestMapping("/seller/item")
public class SellerItemController extends ItemManagerController {
	
	private static final Logger log = LoggerFactory.getLogger(SellerItemController.class);
	
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;
	
	/**
	 * 상품 수정
	 * @param itemId
	 * @param model
	 * @return
	 */
	@GetMapping("sale/edit/{itemId}")
	public String editSale(@PathVariable("itemId") int itemId, Model model) {
		
		ItemSaleEditParam itemSaleEditParam = new ItemSaleEditParam();
		
		itemSaleEditParam.setSellerId(SellerUtils.getSellerId());
		itemSaleEditParam.setItemId(itemId);
		itemSaleEditParam.setStatus("0");
		
		int count = itemService.getItemSaleEditCountByParam(itemSaleEditParam);
		
		if ( count > 0) {
			throw new UserException("해당 상품은 가격변경대기중 입니다.");
		}
		
		Item item = itemService.getItemByIdForManager(itemId);

        if (item.getSellerId() != SellerUtils.getSellerId()) {
            throw new PageNotFoundException();
        }

		model.addAttribute("item", item);
		model.addAttribute("listPage", RequestContextUtils.getRequestContext().getPrevPageUrl());
		
		return "view:sale-edit/form";
	}
	
	/**
	 * 상품 수정
	 * @param itemId
	 * @param model
	 * @return
	 */
	@PostMapping("sale/edit/{itemId}")
	public String editSaleAction(ItemSaleEdit itemSaleEdit, Model model) {
		
		String message = "";

		Item item = itemService.getItemByItemSaleEdit(itemSaleEdit);//상품가격변경요청 전 상품가격 가져오기
		
		if (item == null) {
			throw new UserException("상품정보를 가져올 수 없습니다.");
		}
		message = "[변경요청전 상품가격] 판매가격: " + StringUtils.numberFormat(item.getSalePrice()) + "원, 정가: " + StringUtils.numberFormat(item.getItemPrice()) + "원\n";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = new Date();

		String sellerLoginId = "";
		if (SellerUtils.isShadowSellerLogin()) {
			sellerLoginId = "(Shadow)" + UserUtils.getLoginId();
		} else if (SellerUtils.getSeller() != null) {
			sellerLoginId = SellerUtils.getSeller().getLoginId();
		}

		message += "[ "+sdf.format(today) + "_" + sellerLoginId + " ] 가격변경요청(판매가격: " + StringUtils.numberFormat(itemSaleEdit.getSalePrice()) + "원, 정가: " + StringUtils.numberFormat(itemSaleEdit.getItemPrice()) + "원)\n";

		itemSaleEdit.setMessage(message);
		itemSaleEdit.setItemSaleEditId(sequenceService.getId("OP_ITEM_SALE_EDIT"));
		
		itemService.insertItemSaleEdit(itemSaleEdit);
		
		FlashMapUtils.setMessage("등록되었습니다.");
		return "redirect:/seller/item/sale-edit/list";
	}
	
	/**
	 * 가격 수정
	 * @param itemId
	 * @param model
	 * @return
	 */
	@GetMapping("sale-edit/update/{itemSaleEditId}")
	public String editPrice(@PathVariable("itemSaleEditId") int itemSaleEditId, Model model) {
		
		long sellerId = SellerUtils.getSellerId();
		
		ItemSaleEditParam itemSaleEditParam = new ItemSaleEditParam();
		itemSaleEditParam.setItemSaleEditId(itemSaleEditId);
		itemSaleEditParam.setSellerId(sellerId);
		itemSaleEditParam.setStatus("0");
		
		ItemSaleEdit itemSaleEdit = itemService.getItemSaleEditByParam(itemSaleEditParam);
		
		model.addAttribute("itemSaleEdit", itemSaleEdit);
		model.addAttribute("listPage", RequestContextUtils.getRequestContext().getPrevPageUrl());
		
		return "view:sale-edit/edit";
	}
	
	/**
	 * 메시지 보기
	 * @param itemId
	 * @param model
	 * @return
	 */
	@GetMapping("sale-edit/view/{itemSaleEditId}")
	public String viewMessage(@PathVariable("itemSaleEditId") int itemSaleEditId, Model model) {
		
		long sellerId = SellerUtils.getSellerId();
		
		ItemSaleEditParam itemSaleEditParam = new ItemSaleEditParam();
		itemSaleEditParam.setItemSaleEditId(itemSaleEditId);
		itemSaleEditParam.setSellerId(sellerId);
		
		ItemSaleEdit itemSaleEdit = itemService.getItemSaleEditByParam(itemSaleEditParam);
		
		model.addAttribute("itemSaleEdit", itemSaleEdit);
		model.addAttribute("listPage", RequestContextUtils.getRequestContext().getPrevPageUrl());
		
		return "view:sale-edit/view";
	}
	
	/**
	 * 가격 수정
	 * @param itemId
	 * @param model
	 * @return
	 */
	@PostMapping("sale-edit/update/{itemSaleEditId}")
	public String editPriceAction(@PathVariable("itemSaleEditId") int itemSaleEditId, ItemSaleEdit itemSaleEdit, Model model) {
		
		String message = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = new Date();
		
		if (SellerUtils.isShadowSellerLogin()) {
			message = "[ "+sdf.format(today)+"_(Shadow)"+UserUtils.getLoginId() +
					" ] 가격변경요청수정(판매가격: " + StringUtils.numberFormat(itemSaleEdit.getSalePrice()) + "원, 정가: " + StringUtils.numberFormat(itemSaleEdit.getItemPrice()) + "원)\n";
			message = "[ "+sdf.format(today)+"_"+SellerUtils.getSeller().getLoginId() +
					" ] 가격변경요청수정(판매가격: " + StringUtils.numberFormat(itemSaleEdit.getSalePrice()) + "원, 정가: " + StringUtils.numberFormat(itemSaleEdit.getItemPrice()) + "원)\n";
		}
		
		long sellerId = SellerUtils.getSellerId();
		
		itemSaleEdit.setMessage(message);
		itemSaleEdit.setSellerId(sellerId);
		itemService.updateSaleEdit(itemSaleEdit); // 가격정보수정
		
		ItemSaleEditParam itemSaleEditParam = new ItemSaleEditParam();
		itemSaleEditParam.setItemSaleEditId(itemSaleEditId);
		itemSaleEditParam.setSellerId(sellerId);
		
		ItemSaleEdit saleEdit = itemService.getItemSaleEditByParam(itemSaleEditParam); //수정된 정보
		
		model.addAttribute("itemSaleEdit", saleEdit);
		model.addAttribute("listPage", RequestContextUtils.getRequestContext().getPrevPageUrl());
		
		FlashMapUtils.setMessage("수정되었습니다.");
		return "redirect:"+itemSaleEditId;
	}
	
	/**
	 * 가격변경대기 상품목록
	 * @param itemSaleEditParam
	 * @param model
	 * @return
	 */
	@GetMapping("sale-edit/list")
	public String editSaleList(ItemSaleEditParam itemSaleEditParam, Model model) {
		
		itemSaleEditParam.setSellerId(SellerUtils.getSellerId());
		Pagination pagination = Pagination.getInstance(itemService.getItemSaleEditCountByParam(itemSaleEditParam));
		itemSaleEditParam.setPagination(pagination);
		
		List<ItemSaleEdit> list = itemService.getItemSaleEdit(itemSaleEditParam);

		model.addAttribute("list", list);
		model.addAttribute("itemSaleEditParam",itemSaleEditParam);
		model.addAttribute("pagination",pagination);
		return "view:sale-edit/list";
	}

	
	@PostMapping("sale-edit/delete")
	public JsonView deleteSaleEdit(RequestContext requestContext, ItemListParam itemListParam) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		ItemSaleEdit itemSaleEdit = new ItemSaleEdit();
		
		itemSaleEdit.setSellerId(SellerUtils.getSellerId());
		
		for (String itemSaleEditId : itemListParam.getId()) {
			itemSaleEdit.setItemSaleEditId(Integer.parseInt(itemSaleEditId));
			itemService.deleteItemSaleEdit(itemSaleEdit);
		}
		
		return JsonViewUtils.success();
	}
	
	
	@GetMapping("/{type}/list")
	public String listPending(@PathVariable("type") String type, ItemParam itemParam, Model model) {
		
		// 타입별 조회 조건.
		if ("sale".equals(type)) {
			itemParam.setSaleStatus("sale");
			
		} else if ("sold-out".equals(type)) {
			itemParam.setSaleStatus("soldOut");
			
		} else if ("pending".equals(type)) {
			itemParam.setSaleStatus("pending");
			
		} else if ("sale-end".equals(type)) {
			itemParam.setSaleStatus("saleEnd");
		}
		
		invokeItemList(itemParam, model);
		
		return "view";
	}
	
	/**
	 * 상품리뷰 
	 * @param itemParam
	 * @param itemReview
	 * @param model
	 * @return
	 */
	@GetMapping("review/list")
	public String reviewList(ItemParam itemParam, ItemReview itemReview, Model model) {
		
		long sellerId = SellerUtils.getSellerId();
		itemParam.setSellerId(sellerId);
		itemParam.setConditionType("SELLER");
		
		int sellerReviewCount = itemService.getItemReviewCountByParam(itemParam);
		
		Pagination pagination = Pagination.getInstance(sellerReviewCount);
		
		itemParam.setPagination(pagination);
		itemParam.setSellerId(sellerId);
		
		String today = DateUtils.getToday(Const.DATE_FORMAT);
		
		List<ItemReview> reviewList = itemService.getItemReviewListByParam(itemParam);
		
		for (ItemReview review : reviewList) {
			String star = ""; 
			for (int i = 0; i < review.getScore(); i++) {
				star += "★";
			}
			review.setStarScore(star);
		}
		
		SellerParam sellerParam = new SellerParam();
		sellerParam.setStatusCode("2");
		
		model.addAttribute("pagination",pagination);
		model.addAttribute("itemParam", itemParam);
		model.addAttribute("sellerId", SellerUtils.getSellerId());
		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month3", DateUtils.addYearMonthDay(today, 0, -3, 0));
		model.addAttribute("sellerReviewCount", sellerReviewCount);
		model.addAttribute("sellerReviewList", reviewList);
		
		return "view:item/review/list";
	}

	/**
	 * 상품리뷰 수정
	 * @param itemReviewId
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@GetMapping("review/edit/{itemReviewId}")
	public String updateReview(@PathVariable("itemReviewId") int itemReviewId, ItemParam itemParam, Model model) {
		
		ItemReview itemReview = itemService.getItemReviewById(itemReviewId);

		if (SellerUtils.getSellerId() != itemReview.getSellerId()) {
			throw new PageNotFoundException();
		}

		Item item = itemService.getItemById(itemReview.getItemId());

		model.addAttribute("itemUserCode", item.getItemUserCode());
		model.addAttribute("itemReview", itemReview);
		model.addAttribute("itemParam", itemParam);
		
		return ViewUtils.getView("seller/item/review/form");
	}

	/**
	 * 리뷰수정처리
	 * @param itemReviewId
	 * @param itemReview
	 * @return
	 */
	@PostMapping("review/edit/{itemReviewId}")
	public String updateReviewAction(@PathVariable("itemReviewId") int itemReviewId, ItemReview itemReview) {
				
		itemReview.setRecommendFlag(itemReview.getRecommendFlag() == null ? "N" : "Y");
		itemService.updateItemReview(itemReview);
		
		return ViewUtils.redirect("/seller/item/review/list", MessageUtils.getMessage("M00289"));	// 수정되었습니다. 
	}
	
	/**
	 * 상품검색.
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("find-item")
	public String findItem(ItemParam itemParam, Model model) {
		
		itemParam.setSellerId(SellerUtils.getSellerId());
		
		super.setFindItemModel(itemParam, model);
		return "view";
	}
}
