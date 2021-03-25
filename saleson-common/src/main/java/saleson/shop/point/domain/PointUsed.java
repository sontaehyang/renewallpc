package saleson.shop.point.domain;

public class PointUsed {
	private int pointUsedId;
	
	private String pointType;
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	private int pointId;
	private int pointUsedGroupId;
	
	private String usedType;
	private int point;
	private String details;
	private String orderCode;
	private long managerUserId;
	private String createdDate;

	private int remainingPoint;

	public long getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(long managerUserId) {
		this.managerUserId = managerUserId;
	}
	public int getPointUsedGroupId() {
		return pointUsedGroupId;
	}
	public void setPointUsedGroupId(int pointUsedGroupId) {
		this.pointUsedGroupId = pointUsedGroupId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getPointUsedId() {
		return pointUsedId;
	}
	public void setPointUsedId(int pointUsedId) {
		this.pointUsedId = pointUsedId;
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public String getUsedType() {
		return usedType;
	}
	public void setUsedType(String usedType) {
		this.usedType = usedType;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getRemainingPoint() {
		return remainingPoint;
	}

	public void setRemainingPoint(int remainingPoint) {
		this.remainingPoint = remainingPoint;
	}
}
