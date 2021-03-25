package saleson.shop.point.domain;

public class PointPolicy {
	private String configType;
	private String pointType;
	private float point;
	private int rank;
	private String pointLog;
	
	public String getConfigType() {
		return configType;
	}
	public void setConfigType(String configType) {
		this.configType = configType;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public float getPoint() {
		return point;
	}
	public void setPoint(float point) {
		this.point = point;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getPointLog() {
		return pointLog;
	}
	public void setPointLog(String pointLog) {
		this.pointLog = pointLog;
	}
}
