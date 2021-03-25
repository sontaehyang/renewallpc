package saleson.shop.point.domain;

import com.onlinepowers.framework.util.NumberUtils;

public class PointConfig {
	private int pointConfigId;
	private String configType;
	private String periodType;
	private String pointType;
	private double point;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime = "23";
	private int itemId;
	private String repeatDay;
	private String statusCode;
	private long createdUserId;
	private String createdDate;
	
	
	public PointConfig() {}
	
	public PointConfig(String configType) {
		this.configType = configType;
	}
	
	public PointConfig(String configType, int itemId) {
		this(configType);
		this.itemId = itemId;
	}
	
	public int getPointConfigId() {
		return pointConfigId;
	}
	public void setPointConfigId(int pointConfigId) {
		this.pointConfigId = pointConfigId;
	}
	public String getConfigType() {
		return configType;
	}
	public void setConfigType(String configType) {
		this.configType = configType;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public String getRepeatDay() {
		return repeatDay;
	}
	public void setRepeatDay(String repeatDay) {
		this.repeatDay = repeatDay;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public long getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(long createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "PointConfig [pointConfigId=" + pointConfigId + ", configType="
				+ configType + ", periodType=" + periodType + ", pointType="
				+ pointType + ", point=" + point + ", startDate=" + startDate
				+ ", startTime=" + startTime + ", endDate=" + endDate
				+ ", endTime=" + endTime + ", itemId=" + itemId
				+ ", repeatDay=" + repeatDay + ", statusCode=" + statusCode
				+ ", createdUserId=" + createdUserId + ", createdDate="
				+ createdDate + "]";
	}

	public String getPointText() {
		return NumberUtils.formatNumber(getPoint(),"#.##");
	}
}
