package saleson.shop.wishlist.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class WishlistParam extends SearchParam {
	private long userId;
	
	
	public WishlistParam() {}
	
	public WishlistParam(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
}
