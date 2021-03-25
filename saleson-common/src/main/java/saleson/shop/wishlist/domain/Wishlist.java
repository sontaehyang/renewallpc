package saleson.shop.wishlist.domain;

import saleson.common.utils.CommonUtils;
import saleson.shop.item.domain.Item;

public class Wishlist {
	private int wishlistId;
	private int wishlistGroupId;
	private int itemId;
	private String itemOption;
	private String itemOptionGroupName;
	private String itemOptionName;
	private String createdDate;
	
	private Item item;
	
	
	/* 관심상품 등록용 */
	private int[] arrayItemId;
	private String[] arrayRequiredOptions;
	
	/* 관심상품 등록용 - 필수 상품 */
	private String[] arrayRequiredItems;
	
	private long userId;
	
	public Wishlist() {}
	
	public Wishlist(int wishlistGroupId, int itemId) {
		this.wishlistGroupId = wishlistGroupId;
		this.itemId = itemId;
	}
	
	
	public int getWishlistId() {
		return wishlistId;
	}
	public void setWishlistId(int wishlistId) {
		this.wishlistId = wishlistId;
	}
	public int getWishlistGroupId() {
		return wishlistGroupId;
	}
	public void setWishlistGroupId(int wishlistGroupId) {
		this.wishlistGroupId = wishlistGroupId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemOption() {
		return itemOption;
	}
	public void setItemOption(String itemOption) {
		this.itemOption = itemOption;
	}
	
	public String getItemOptionGroupName() {
		return itemOptionGroupName;
	}

	public void setItemOptionGroupName(String itemOptionGroupName) {
		this.itemOptionGroupName = itemOptionGroupName;
	}

	public String getItemOptionName() {
		return itemOptionName;
	}

	public void setItemOptionName(String itemOptionName) {
		this.itemOptionName = itemOptionName;
	}

	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int[] getArrayItemId() {
		return CommonUtils.copy(arrayItemId);
	}
	public void setArrayItemId(int[] arrayItemId) {
		this.arrayItemId = CommonUtils.copy(arrayItemId);
	}
	public String[] getArrayRequiredOptions() {
		return CommonUtils.copy(arrayRequiredOptions);
	}
	public void setArrayRequiredOptions(String[] arrayRequiredOptions) {
		this.arrayRequiredOptions = CommonUtils.copy(arrayRequiredOptions);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String[] getArrayRequiredItems() {
		return CommonUtils.copy(arrayRequiredItems);
	}

	public void setArrayRequiredItems(String[] arrayRequiredItems) {
		this.arrayRequiredItems = CommonUtils.copy(arrayRequiredItems);
	}
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	
}
