package saleson.shop.userlevel.domain;

import saleson.common.utils.LocalDateUtils;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class UserLevel implements Serializable {

	private int levelId;
	private String groupCode;
	private int depth;
	private String levelName;
	private String fileName;
	private int priceStart;
	private int priceEnd;
	
	private float discountRate;
	private float pointRate;
	private int shippingCouponCount;
	
	private int retentionPeriod;
	private int referencePeriod;
	private int exceptReferencePeriod;

	
	// 월별 회원 등급별 배송비 쿠폰 발행시 Batch 쪽에서 사용
	private String shippingCouponExpirationDate;
	private String shippingCouponReason;
	// 월별 회원 등급별 배송비 쿠폰 발행시 Batch 쪽에서 사용


	// 회원등급 만료일 (회원 배치 처리 시 데이터 조회를 위한 회원등급 만료일)
	private String expirationDate;
	private String userLevelProcessType = "NORMAL";			// 'NORMAL': 조건에 맞는 회원에 등급 적용, 'ALL_OTHER': 처리 안된 나머지 회원에 마지막 회원 등급을 일괄적용..


	public String getShippingCouponReason() {
		return shippingCouponReason;
	}

	public void setShippingCouponReason(String shippingCouponReason) {
		this.shippingCouponReason = shippingCouponReason;
	}

	public String getShippingCouponExpirationDate() {
		return shippingCouponExpirationDate;
	}

	public void setShippingCouponExpirationDate(String shippingCouponExpirationDate) {
		this.shippingCouponExpirationDate = shippingCouponExpirationDate;
	}

	public int getExceptReferencePeriod() {
		return exceptReferencePeriod;
	}

	public void setExceptReferencePeriod(int exceptReferencePeriod) {
		this.exceptReferencePeriod = exceptReferencePeriod;
	}

	private String createdDate;
	private int userCount;
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getPriceStart() {
		return priceStart;
	}

	public void setPriceStart(int priceStart) {
		this.priceStart = priceStart;
	}

	public int getPriceEnd() {
		return priceEnd;
	}

	public void setPriceEnd(int priceEnd) {
		this.priceEnd = priceEnd;
	}

	public float getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(float discountRate) {
		this.discountRate = discountRate;
	}

	public float getPointRate() {
		return pointRate;
	}

	public void setPointRate(float pointRate) {
		this.pointRate = pointRate;
	}

	public int getRetentionPeriod() {
		return retentionPeriod;
	}

	public void setRetentionPeriod(int retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}

	public int getReferencePeriod() {
		return referencePeriod;
	}

	public void setReferencePeriod(int referencePeriod) {
		this.referencePeriod = referencePeriod;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getShippingCouponCount() {
		return shippingCouponCount;
	}

	public void setShippingCouponCount(int shippingCouponCount) {
		this.shippingCouponCount = shippingCouponCount;
	}

	public String getUserLevelProcessType() {
		return userLevelProcessType;
	}

	public void setUserLevelProcessType(String userLevelProcessType) {
		this.userLevelProcessType = userLevelProcessType;
	}

	/**
	 * 회원등급 배치 처리 시 사용
	 * @return
	 */
	public String getExpirationDate() {
		return expirationDate;
	}

	/**
	 * 회원등급 배치 처리 시 사용
	 * @param expirationDate
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * 회원등급 배치 처리 시 사용
	 * 등급 유지 기간 - 새로운 등급 만료일
	 * @return
	 */
	public String getNewExpirationDate() {
		if (expirationDate == null || expirationDate.isEmpty()) {
			return "";
		}
		LocalDate localDate = LocalDateUtils.getLocalDate(expirationDate)
				.plusMonths((long) retentionPeriod);

		return LocalDateUtils.localDateToString(localDate);
	}


	/**
	 * 회원 매출 데이터 조회 시작일
	 * 등급 만료일 기준 - 매출 기준기간(월) - 매출 제외 기준일 (일)
	 * @return
	 */
	public String getStartPaydate() {
		if (expirationDate == null || expirationDate.isEmpty()) {
			return "";
		}
		LocalDate localDate = LocalDateUtils.getLocalDate(expirationDate)
				.minusMonths((long) referencePeriod)
				.minusDays((long) exceptReferencePeriod);

		return LocalDateUtils.localDateToString(localDate);
	}


	/**
	 * 회원 매출 데이터 조회 종료일
	 * 등급 만료일 기준 - 매출 제외 기준일 (일)
	 * @return
	 */
	public String getEndPaydate() {
		if (expirationDate == null || expirationDate.isEmpty()) {
			return "";
		}
		LocalDate localDate = LocalDateUtils.getLocalDate(expirationDate)
				.minusDays((long) exceptReferencePeriod);

		return LocalDateUtils.localDateToString(localDate);
	}
}
