package saleson.shop.point.domain;

public class PointDayGroup {
	private String groupKey;
	private int savedPoint;
	private int usedPoint;

	public String getGroupKey() {
		return groupKey;
	}
	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}
	public int getSavedPoint() {
		return savedPoint;
	}
	public void setSavedPoint(int savedPoint) {
		this.savedPoint = savedPoint;
	}
	public int getUsedPoint() {
		return usedPoint;
	}
	public void setUsedPoint(int usedPoint) {
		this.usedPoint = usedPoint;
	}
	
}
