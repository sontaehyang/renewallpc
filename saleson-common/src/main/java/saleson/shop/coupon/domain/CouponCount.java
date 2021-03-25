package saleson.shop.coupon.domain;

public class CouponCount {
	
	private int totalCount;
	private int downloadCouponCount;
	private int usedCouponCount;
	private int expirationCouponCount;

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getDownloadCouponCount() {
		return downloadCouponCount;
	}
	public void setDownloadCouponCount(int downloadCouponCount) {
		this.downloadCouponCount = downloadCouponCount;
	}
	public int getUsedCouponCount() {
		return usedCouponCount;
	}
	public void setUsedCouponCount(int usedCouponCount) {
		this.usedCouponCount = usedCouponCount;
	}
	public int getExpirationCouponCount() {
		return expirationCouponCount;
	}
	public void setExpirationCouponCount(int expirationCouponCount) {
		this.expirationCouponCount = expirationCouponCount;
	}
}
