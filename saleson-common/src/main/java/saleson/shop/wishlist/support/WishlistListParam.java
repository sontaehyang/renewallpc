package saleson.shop.wishlist.support;

import com.onlinepowers.framework.web.domain.ListParam;

public class WishlistListParam extends ListParam {
	private int wishlistGroupId;
	//LCH-WishlistMobile  - 위시리스트  선택삭제  <수정>
	private int wishlistId;
	
	private long userId;
	
	public int getWishlistGroupId() {
		return wishlistGroupId;
	}

	public void setWishlistGroupId(int wishlistGroupId) {
		this.wishlistGroupId = wishlistGroupId;
	}

	public int getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(int wishlistId) {
		this.wishlistId = wishlistId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
