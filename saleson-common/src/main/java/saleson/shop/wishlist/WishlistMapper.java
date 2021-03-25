package saleson.shop.wishlist;

import java.util.List;

import saleson.shop.wishlist.domain.Wishlist;
import saleson.shop.wishlist.domain.WishlistGroup;
import saleson.shop.wishlist.support.WishlistListParam;
import saleson.shop.wishlist.support.WishlistParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("wishlistMapper")
public interface WishlistMapper {
	/**
	 * 회원의 관심상품 수
	 * @param userId
	 * @return
	 */
	int getWishlistCountByUserId(long userId);
	
	int getWishlistCountByParam(WishlistParam param);

	public List<Wishlist> getWishlistList(WishlistParam wishlistParam);
	
	public List<Wishlist> getWishlistListByUserId(WishlistParam param);
	
	/**
	 * 관련 상품 그룹에 동일한 상품이 담긴 수
	 * @param wishlist
	 * @return
	 */
	int getWishlistDuplicateCount(Wishlist wishlist);
	
	
	/**
	 * 관련 상품 그룹에 동일한 상품이 담긴 경우 데이터 조
	 * @param wishlist
	 * @return
	 */
	Wishlist getWishlistDuplicate(Wishlist wishlist);
	
	
	/**
	 * 내 관심 상품 목록에 추가.
	 * @param wishlist
	 */
	void insertWishlist(Wishlist wishlist);


	
	/**
	 * 관심상품을 조회한다.
	 * @param wishlistId
	 * @return
	 */
	Wishlist getWishlistById(String wishlistId);


	/**
	 * 관심상품을 삭제한다.
	 * @param wishlistId
	 */
	void deleteWishlistById(int wishlistId);


	/**
	 * 관심상품 데이트를 선택 삭제한다.
	 * @param listParam
	 */
	void deleteWishlistByListParam(WishlistListParam listParam);

	
	/**
	 * 관심상품 데이트를 일괄 삭제한다.
	 * @param listParam
	 */
	
	//LCH-WishlistMobile  - 위시리스트  전체삭제  <추가>

	
	void alldeleteWishlistByListParam(WishlistListParam listParam);
	

}
