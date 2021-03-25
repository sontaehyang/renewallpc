package saleson.shop.coupon.support;

import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;

@SuppressWarnings("serial")
public class CouponParam extends SearchParam {

	private int couponId;

	private String[] couponTypes;
	private String couponFlag;
	private String couponTargetTimeType;
	private String couponTargetUserType;
	private String couponTargetItemType;
	private String dataStatusCode;

	private boolean campaignFlag;
	private String levelId;

	private String couponOfflineFlag;
	private int couponDownloadLimit;
	private int couponDownloadUserLimit;

	private String couponApplyStartDate;
	private String couponApplyEndDate;
	private String sendDate;
	private String endSendDate;
	private String sendTime;

	private boolean directInputFlag;
	private String directInputValue;

	public String getCouponApplyStartDate() {
		return couponApplyStartDate;
	}

	public void setCouponApplyStartDate(String couponApplyStartDate) {
		this.couponApplyStartDate = couponApplyStartDate;
	}

	public String getCouponApplyEndDate() {
		return couponApplyEndDate;
	}

	public void setCouponApplyEndDate(String couponApplyEndDate) {
		this.couponApplyEndDate = couponApplyEndDate;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getEndSendDate() {
		return endSendDate;
	}

	public void setEndSendDate(String endSendDate) {
		this.endSendDate = endSendDate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String[] getCouponTypes() {
		
		if (couponTypes == null) {
			return new String[] {"WEB", "MOBILE", "APP"};
		}

		return CommonUtils.copy(couponTypes);
	}
	public void setCouponTypes(String[] couponTypes) {
		this.couponTypes = CommonUtils.copy(couponTypes);
	}
	public String getCouponFlag() {
		return couponFlag;
	}
	public void setCouponFlag(String couponFlag) {
		this.couponFlag = couponFlag;
	}
	public String getCouponTargetTimeType() {
		return couponTargetTimeType;
	}
	public void setCouponTargetTimeType(String couponTargetTimeType) {
		this.couponTargetTimeType = couponTargetTimeType;
	}
	public String getCouponTargetUserType() {
		return couponTargetUserType;
	}
	public void setCouponTargetUserType(String couponTargetUserType) {
		this.couponTargetUserType = couponTargetUserType;
	}
	public String getCouponTargetItemType() {
		return couponTargetItemType;
	}
	public void setCouponTargetItemType(String couponTargetItemType) {
		this.couponTargetItemType = couponTargetItemType;
	}
	public String getDataStatusCode() {
		return dataStatusCode;
	}
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public boolean isCampaignFlag() {
		return campaignFlag;
	}

	public void setCampaignFlag(boolean campaignFlag) {
		this.campaignFlag = campaignFlag;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}


	public String getCouponOfflineFlag() {
		return couponOfflineFlag;
	}

	public void setCouponOfflineFlag(String couponOfflineFlag) {
		this.couponOfflineFlag = couponOfflineFlag;
	}

	public int getCouponDownloadLimit() {
		return couponDownloadLimit;
	}

	public void setCouponDownloadLimit(int couponDownloadLimit) {
		this.couponDownloadLimit = couponDownloadLimit;
	}

	public int getCouponDownloadUserLimit() {
		return couponDownloadUserLimit;
	}

	public void setCouponDownloadUserLimit(int couponDownloadUserLimit) {
		this.couponDownloadUserLimit = couponDownloadUserLimit;
	}

	public boolean isDirectInputFlag() {
		return directInputFlag;
	}

	public void setDirectInputFlag(boolean directInputFlag) {
		this.directInputFlag = directInputFlag;
	}
}
