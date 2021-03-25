package saleson.shop.seller.mall;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.seller.main.domain.SellerCategory;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.support.ItemParam;

import java.util.List;

@Controller
@RequestMapping("/mall")
@RequestProperty(template="seller", layout="blank")
public class MallController {

	@Autowired
	private SellerService serllerService;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 미니몰 메인
	 */
	@GetMapping("{sellerLoginId}")
	public String index(@PathVariable("sellerLoginId") String sellerLoginId, 
			ItemParam itemParam, Model model) {
		 
		Seller seller = serllerService.getSellerByLoginId(sellerLoginId);
		
		if (seller == null || !"2".equals(seller.getStatusCode())) {
			throw new PageNotFoundException();
		}
		
		// 판매자 카테고리 조회.
		List<SellerCategory> sellerCategories = serllerService.getSellerCategoriesById(seller.getSellerId());
		
		
		// 판매자 상품 조회
		if (itemParam.getOrderBy() != null && itemParam.getOrderBy().equals("ORDERING")
				&& (itemParam.getCategoryId() == null || itemParam.getCategoryId().equals(""))) {
			itemParam.setOrderBy("");
			itemParam.setSort("DESC");
		}
		if (StringUtils.isEmpty(itemParam.getListType())) {
			itemParam.setListType("a");
		}
		if (itemParam.getItemsPerPage() == 10) {
			itemParam.setItemsPerPage(20);
		}
		itemParam.setItemDataType("1");
		itemParam.setDataStatusCode("1");
		itemParam.setWhere("ITEM_NAME");
		
		// 판매자인 경우.
		itemParam.setSellerId(seller.getSellerId());
		
		
		Pagination pagination = Pagination.getInstance(itemService.getItemCount(itemParam));
		itemParam.setPagination(pagination);

		List<Item> items = itemService.getItemList(itemParam);
		
		model.addAttribute("seller", seller);
		model.addAttribute("sellerCategories", sellerCategories);
		model.addAttribute("items", items);
		model.addAttribute("pagination", pagination);
		
		return "view:/seller/mall/index";
	}
	
	
	@GetMapping("{sellerLoginId}/qna")
	public String qna(@PathVariable("sellerLoginId") String sellerLoginId, 
			ItemParam itemParam, Model model) {
		
		Seller seller = serllerService.getSellerByLoginId(sellerLoginId);
		
		if (seller == null || !"2".equals(seller.getStatusCode())) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("seller", seller);
		return "view:/seller/mall/qna";
	}
	
	
	@GetMapping("{sellerLoginId}/review")
	public String review(@PathVariable("sellerLoginId") String sellerLoginId, 
			ItemParam itemParam, Model model) {
		Seller seller = serllerService.getSellerByLoginId(sellerLoginId);
		
		if (seller == null || !"2".equals(seller.getStatusCode())) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("seller", seller);
		
		return "view:/seller/mall/review";
	}
}
