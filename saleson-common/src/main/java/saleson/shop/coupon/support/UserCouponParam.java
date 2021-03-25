package saleson.shop.coupon.support;

import java.util.List;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.ShopUtils;
import saleson.common.web.Paging;

@SuppressWarnings("serial")
public class UserCouponParam extends SearchParam {
	
	private List<Integer> itemIds;
	private int couponId;
	private int couponUserId;
	private long userId;
	private int itemId;
	private String itemUserCode;
	private String dataStatusCode;
	private int userLevelId;
	private int orderCount; //첫구매 회원쿠폰에 이용
	private String couponTargetTimeType;
	private String viewTarget;

	private String searchStartDate;
	private String searchEndDate;

	private String couponName;

	private String couponDataStatusCode;

	// WEB, MOBILE, APP
	private String couponType = "WEB";

	// API
	private boolean complete;

	private boolean directInputFlag;
	private String directInputValue;

	private String couponApplyEndDate;
	private Paging paging;
	private int day;
	private String couponBirthday;
	private String[] couponTargetUserLevels;

	public String getViewTarget() {
		return viewTarget;
	}
	public void setViewTarget(String viewTarget) {
		this.viewTarget = viewTarget;
	}
	public List<Integer> getItemIds() {
		return itemIds;
	}
	public void setItemIds(List<Integer> itemIds) {
		this.itemIds = itemIds;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getDataStatusCode() {
		return dataStatusCode;
	}
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	public int getUserLevelId() {
		return userLevelId;
	}
	public void setUserLevelId(int userLevelId) {
		this.userLevelId = userLevelId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public int getCouponUserId() {
		return couponUserId;
	}
	public void setCouponUserId(int couponUserId) {
		this.couponUserId = couponUserId;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public String getCouponTargetTimeType() {
		return couponTargetTimeType;
	}
	public void setCouponTargetTimeType(String couponTargetTimeType) {
		this.couponTargetTimeType = couponTargetTimeType;
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponDataStatusCode() {
		return couponDataStatusCode;
	}

	public void setCouponDataStatusCode(String couponDataStatusCode) {
		this.couponDataStatusCode = couponDataStatusCode;
	}

	public String getCouponType() {
		if (ShopUtils.isMobilePage()) {
			couponType = "MOBILE";
		}

		// APP 추가

		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public String getItemUserCode() {
		return itemUserCode;
	}

	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}

	public boolean isDirectInputFlag() {
		return directInputFlag;
	}

	public void setDirectInputFlag(boolean directInputFlag) {
		this.directInputFlag = directInputFlag;
	}

	public String getDirectInputValue() {
		return directInputValue;
	}

	public void setDirectInputValue(String directInputValue) {
		this.directInputValue = directInputValue;
	}


	public String getCouponApplyEndDate() {
		return couponApplyEndDate;
	}

	public void setCouponApplyEndDate(String couponApplyEndDate) {
		this.couponApplyEndDate = couponApplyEndDate;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getCouponBirthday() {
		return couponBirthday;
	}

	public void setCouponBirthday(String couponBirthday) {
		this.couponBirthday = couponBirthday;
	}

	public String[] getCouponTargetUserLevels() {
		return couponTargetUserLevels;
	}

	public void setCouponTargetUserLevels(String[] couponTargetUserLevels) {
		this.couponTargetUserLevels = couponTargetUserLevels;
	}
}
