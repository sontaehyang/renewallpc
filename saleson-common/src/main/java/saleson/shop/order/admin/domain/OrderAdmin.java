package saleson.shop.order.admin.domain;

public class OrderAdmin {
	private String workDate;
	private int workSequence;
	private String insertManagerName;
	private String createdDate;

	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public int getWorkSequence() {
		return workSequence;
	}
	public void setWorkSequence(int workSequence) {
		this.workSequence = workSequence;
	}
	public String getInsertManagerName() {
		return insertManagerName;
	}
	public void setInsertManagerName(String insertManagerName) {
		this.insertManagerName = insertManagerName;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
