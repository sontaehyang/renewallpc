package saleson.shop.wishlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import saleson.common.utils.ShopUtils;
import saleson.shop.cart.domain.Cart;
import saleson.shop.item.ItemMapper;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.item.domain.ItemOptionGroup;
import saleson.shop.wishlist.domain.Wishlist;
import saleson.shop.wishlist.domain.WishlistGroup;
import saleson.shop.wishlist.support.WishlistListParam;
import saleson.shop.wishlist.support.WishlistParam;

import com.onlinepowers.framework.exception.BusinessException;
import com.onlinepowers.framework.sequence.domain.Sequence;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.ArrayUtils;
import com.onlinepowers.framework.util.ValidationUtils;

@Service("wishlistService")
public class WishlistServiceImpl implements WishlistService {
	private static final Logger log = LoggerFactory.getLogger(WishlistServiceImpl.class);
	
	@Autowired
	private WishlistMapper wishlistMapper;
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	
	@Override
	public int getWishlistCountByUserId(long userId) {
		return wishlistMapper.getWishlistCountByUserId(userId);
	}
	
	@Override
	public List<Wishlist> getWishlistListByUserId(long userId) {
		
		WishlistParam wishlistParam = new WishlistParam(); 
		
		wishlistParam.setUserId(userId);
		
		return wishlistMapper.getWishlistListByUserId(wishlistParam);
	}
	
	@Override
	public int getWishlistCountByParam(WishlistParam wishlistParam) {
		return wishlistMapper.getWishlistCountByParam(wishlistParam);
	}
	
	@Override
	public List<Wishlist> getWishlistListByParam(WishlistParam wishlistParam) {
		return wishlistMapper.getWishlistListByUserId(wishlistParam);
	}
	
	@Override
	public int insertWishlist(Wishlist wishlist) {
		
		if (wishlist.getItemId() == 0) {
			throw new BusinessException("상품정보가 없습니다.");
		}
		
		wishlist.setWishlistId(sequenceService.getId("OP_WISHLIST"));
		
		int addedItemCount = 0;	
		
		// 중복 체크 
		if (wishlistMapper.getWishlistDuplicateCount(wishlist) == 0) {
			
			
			// 관심상품 등록 
			wishlistMapper.insertWishlist(wishlist);
			addedItemCount++;
		}
	
		
		return addedItemCount;
	}

	@Override
	public void deleteWishlistByListParam(WishlistListParam listParm) {
		wishlistMapper.deleteWishlistByListParam(listParm);
	}

	//LCH-WishlistMobile  - 위시리스트  전체삭제  <추가>

	@Override
	public void alldeleteWishlistByListParam(WishlistListParam listParm) {
		wishlistMapper.alldeleteWishlistByListParam(listParm);
	}

	@Override
	public List<Wishlist> getWishlistList(WishlistParam wishlistParam) {
		return wishlistMapper.getWishlistListByUserId(wishlistParam);
	}

}
