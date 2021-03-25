package saleson.shop.coupon.domain;



public class CouponCategory {
	
	private int couponCategoryId;
	private int categoryId;
	private int couponId;
	private String categoryName;
	private String createdDate;
	
	
	public int getCouponCategoryId() {
		return couponCategoryId;
	}
	public void setCouponCategoryId(int couponCategoryId) {
		this.couponCategoryId = couponCategoryId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
}
