package saleson.shop.wishlist;

import java.util.List;

import saleson.shop.wishlist.domain.Wishlist;
import saleson.shop.wishlist.domain.WishlistGroup;
import saleson.shop.wishlist.support.WishlistListParam;
import saleson.shop.wishlist.support.WishlistParam;

public interface WishlistService {
	/**
	 * 회원의 관심상품 수
	 * @param userId
	 * @return
	 */
	public int getWishlistCountByUserId(long userId);
	
	public List<Wishlist> getWishlistListByUserId(long userId);

	public List<Wishlist> getWishlistList(WishlistParam wishlistParam);
	
	public int getWishlistCountByParam(WishlistParam wishlistParam);
	
	public List<Wishlist> getWishlistListByParam(WishlistParam wishlistParam);
	
	/**
	 * 내 관심 상품 목록에 추가.
	 * @param wishlist
	 * @return 실제 추가된 카운
	 */
	public int insertWishlist(Wishlist wishlist);

	/**
	 * 목록에서 체크한 상품을 선택 삭제한다.
	 * @param listParm
	 */
	public void deleteWishlistByListParam(WishlistListParam listParm);
	
	/**
	 * 목록에서 체크한 상품을 모두 삭제한다.
	 * @param listParm
	 */
	//LCH-WishlistMobile  - 위시리스트  전체삭제  <추가>
	
	public void alldeleteWishlistByListParam(WishlistListParam listParm);

}
