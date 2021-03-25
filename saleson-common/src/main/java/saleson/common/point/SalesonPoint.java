package saleson.common.point;

public class SalesonPoint {
	private String pointType;
	private String pointName;
	private boolean possibleIssueReceipt;


	public SalesonPoint(String pointType, String pointName, boolean possibleIssueReceipt) {
		this.pointType = pointType;
		this.pointName = pointName;
		this.possibleIssueReceipt = possibleIssueReceipt;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public boolean isPossibleIssueReceipt() {
		return possibleIssueReceipt;
	}

	public void setPossibleIssueReceipt(boolean possibleIssueReceipt) {
		this.possibleIssueReceipt = possibleIssueReceipt;
	}
}
