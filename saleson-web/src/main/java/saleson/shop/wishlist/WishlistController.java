package saleson.shop.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;

import saleson.common.utils.UserUtils;
import saleson.shop.wishlist.domain.Wishlist;
import saleson.shop.wishlist.domain.WishlistGroup;
import saleson.shop.wishlist.support.WishlistListParam;

@Controller
@RequestMapping("/wishlist")
@RequestProperty(layout="default")
public class WishlistController {
	@Autowired
	private WishlistService wishlistService;

	
	/**
	 * 관심상품 추가
	 * @param requestContext
	 * @param wishlist
	 * @return
	 */
	@PostMapping("add")
	public JsonView add(RequestContext requestContext, Wishlist wishlist) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		
		if (!UserUtils.isUserLogin()) {
			throw new UserException(MessageUtils.getMessage("M00540"));	// 로그인하십시오.
		}
		
		wishlist.setUserId(UserUtils.getUserId());
		
		int addedItemCount = 0;

		int[] arrayItemId =  wishlist.getArrayItemId();
		
		if (ValidationUtils.isNotNull(arrayItemId)) {
			
			for (int id : arrayItemId) {
				wishlist.setItemId(id);
				wishlistService.insertWishlist(wishlist);
			}
			
		} else {
			wishlistService.insertWishlist(wishlist);
		}
		
		
				
		return JsonViewUtils.success(addedItemCount);
	}
	

	@PostMapping("list/delete")
	public JsonView delete(RequestContext requestContext,
			WishlistListParam listParm) {
		
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		
		if (!UserUtils.isUserLogin()) {
			throw new UserException(MessageUtils.getMessage("M00540"));	// 로그인하십시오.
		}
		
		listParm.setUserId(UserUtils.getUserId());
		wishlistService.deleteWishlistByListParam(listParm);

		return JsonViewUtils.success();
	}
	
	@PostMapping("list/delete/all")
	public JsonView deleteAll(RequestContext requestContext,
			WishlistListParam listParm) {
		
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		
		if (!UserUtils.isUserLogin()) {
			throw new UserException(MessageUtils.getMessage("M00540"));	// 로그인하십시오.
		}
		
		listParm.setUserId(UserUtils.getUserId());
		wishlistService.alldeleteWishlistByListParam(listParm);

		return JsonViewUtils.success();
	}
	
	
}
