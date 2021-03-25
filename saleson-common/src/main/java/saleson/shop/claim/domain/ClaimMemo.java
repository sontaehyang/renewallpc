package saleson.shop.claim.domain;

public class ClaimMemo {
	private int claimMemoId;
	private long userId;
	private String userName;
	private String orderCode;
	private String claimStatus;
	private String memo;
	private long managerUserId;
	private String managerLoginId;
	private String dataStatusCode;
	private String createdDate;
	public int getClaimMemoId() {
		return claimMemoId;
	}
	public void setClaimMemoId(int claimMemoId) {
		this.claimMemoId = claimMemoId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public long getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(long managerUserId) {
		this.managerUserId = managerUserId;
	}
	public String getManagerLoginId() {
		return managerLoginId;
	}
	public void setManagerLoginId(String managerLoginId) {
		this.managerLoginId = managerLoginId;
	}
	public String getDataStatusCode() {
		return dataStatusCode;
	}
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public String getClaimStatusLabel() {
		if ("1".equals(getClaimStatus())) {
			return "처리중";
		} else if ("2".equals(getClaimStatus())) {
			return "처리완료";
		}
		
		return "-";
	}
}
