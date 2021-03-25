package saleson.shop.coupon.domain;

import saleson.common.utils.CommonUtils;
import saleson.shop.user.support.UserSearchParam;

@SuppressWarnings("serial")
public class ItemSearchAddParam extends UserSearchParam {

	private String couponState;
	private int couponId;
	private int startCouponUserId;

	private String categoryGroupId;
	private String categoryClass1;
	private String categoryClass2;
	private String categoryClass3;
	private String categoryClass4;
	private String where;
	private String query;
	private String excludeItems;
	private String itemCount;
	
	
	private String[] itemIds;
	
	public String getCouponState() {
		return couponState;
	}
	public void setCouponState(String couponState) {
		this.couponState = couponState;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public int getStartCouponUserId() {
		return startCouponUserId;
	}
	public void setStartCouponUserId(int startCouponUserId) {
		this.startCouponUserId = startCouponUserId;
	}
	
	public String getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(String categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public String getCategoryClass1() {
		return categoryClass1;
	}
	public void setCategoryClass1(String categoryClass1) {
		this.categoryClass1 = categoryClass1;
	}
	public String getCategoryClass2() {
		return categoryClass2;
	}
	public void setCategoryClass2(String categoryClass2) {
		this.categoryClass2 = categoryClass2;
	}
	public String getCategoryClass3() {
		return categoryClass3;
	}
	public void setCategoryClass3(String categoryClass3) {
		this.categoryClass3 = categoryClass3;
	}
	public String getCategoryClass4() {
		return categoryClass4;
	}
	public void setCategoryClass4(String categoryClass4) {
		this.categoryClass4 = categoryClass4;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getExcludeItems() {
		return excludeItems;
	}
	public void setExcludeItems(String excludeItems) {
		this.excludeItems = excludeItems;
	}
	public String getItemCount() {
		return itemCount;
	}
	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}
	public String[] getItemIds() {
		return CommonUtils.copy(itemIds);
	}
	public void setItemIds(String[] itemIds) {
		this.itemIds = CommonUtils.copy(itemIds);
	}
	
	public String getCategoryClass() {
		return this.categoryClass4 != null ? this.categoryClass4 : this.categoryClass3 != null ? this.categoryClass3 : this.categoryClass2 != null ? this.categoryClass2 : this.categoryClass1 != null ? this.categoryClass1 : null;
	}
}
