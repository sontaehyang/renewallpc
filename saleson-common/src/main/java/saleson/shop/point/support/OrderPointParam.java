package saleson.shop.point.support;

public class OrderPointParam {
	
	private int itemId;
	private String pointType;
	private String repeatDayStartTime;
	private String repeatDayEndTime;
	
	private long userId;
	private int pointSaveAfterDay;

	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getPointSaveAfterDay() {
		return pointSaveAfterDay;
	}
	public void setPointSaveAfterDay(int pointSaveAfterDay) {
		this.pointSaveAfterDay = pointSaveAfterDay;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getRepeatDayStartTime() {
		return repeatDayStartTime;
	}
	public void setRepeatDayStartTime(String repeatDayStartTime) {
		this.repeatDayStartTime = repeatDayStartTime;
	}
	public String getRepeatDayEndTime() {
		return repeatDayEndTime;
	}
	public void setRepeatDayEndTime(String repeatDayEndTime) {
		this.repeatDayEndTime = repeatDayEndTime;
	}

}
