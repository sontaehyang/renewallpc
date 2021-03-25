package saleson.shop.wishlist.domain;

public class WishlistGroup {
	private int wishlistGroupId;
	private String groupName;
	private long userId;
	private int maxItemCount;
	private int itemCount;
	private int ordering;
	private String createdDate;
	public int getWishlistGroupId() {
		return wishlistGroupId;
	}
	public void setWishlistGroupId(int wishlistGroupId) {
		this.wishlistGroupId = wishlistGroupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getMaxItemCount() {
		return maxItemCount;
	}
	public void setMaxItemCount(int maxItemCount) {
		this.maxItemCount = maxItemCount;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
