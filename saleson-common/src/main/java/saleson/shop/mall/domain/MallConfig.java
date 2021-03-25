package saleson.shop.mall.domain;

public class MallConfig {
	private int mallConfigId;
	private String mallLoginId;
	private String mallType;
	private String mallApiKey;
	private String dataStatusCode;
	private String createdDate;
	
	
	// 일반주문
	private String lastDate;
	private String statusCode;
	private String lastSearchStartDate;
	private String lastSearchEndDate;

	// 클레임
	private String lastClaimDate;
	private String claimStatusCode;
	private String lastClaimSearchStartDate;
	private String lastClaimSearchEndDate;
	
	
	public String getDataStatusCodeLabel() {
		
		if ("1".equals(dataStatusCode)) {
			return "거래중";
		}
		
		return "거래 중지";
	}
	
	public String getCollectStatusCodeLabel() {
		
		if ("1".equals(statusCode)) {
			return "정상";
		} else if ("2".equals(statusCode)) {
			return "비정상";
		} else if ("3".equals(statusCode)) {
			return "수집중";
		}
		
		return "";
	}
	
	public String getClaimCollectStatusCodeLabel() {
		
		if ("1".equals(claimStatusCode)) {
			return "정상";
		} else if ("2".equals(claimStatusCode)) {
			return "비정상";
		} else if ("3".equals(claimStatusCode)) {
			return "수집중";
		}
		
		return "";
	}
	
	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getLastSearchStartDate() {
		return lastSearchStartDate;
	}

	public void setLastSearchStartDate(String lastSearchStartDate) {
		this.lastSearchStartDate = lastSearchStartDate;
	}

	public String getLastSearchEndDate() {
		return lastSearchEndDate;
	}

	public void setLastSearchEndDate(String lastSearchEndDate) {
		this.lastSearchEndDate = lastSearchEndDate;
	}

	public String getLastClaimDate() {
		return lastClaimDate;
	}

	public void setLastClaimDate(String lastClaimDate) {
		this.lastClaimDate = lastClaimDate;
	}

	public String getClaimStatusCode() {
		return claimStatusCode;
	}

	public void setClaimStatusCode(String claimStatusCode) {
		this.claimStatusCode = claimStatusCode;
	}

	public String getLastClaimSearchStartDate() {
		return lastClaimSearchStartDate;
	}

	public void setLastClaimSearchStartDate(String lastClaimSearchStartDate) {
		this.lastClaimSearchStartDate = lastClaimSearchStartDate;
	}

	public String getLastClaimSearchEndDate() {
		return lastClaimSearchEndDate;
	}

	public void setLastClaimSearchEndDate(String lastClaimSearchEndDate) {
		this.lastClaimSearchEndDate = lastClaimSearchEndDate;
	}

	public int getMallConfigId() {
		return mallConfigId;
	}
	public void setMallConfigId(int mallConfigId) {
		this.mallConfigId = mallConfigId;
	}
	public String getMallLoginId() {
		return mallLoginId;
	}
	public void setMallLoginId(String mallLoginId) {
		this.mallLoginId = mallLoginId;
	}
	public String getMallType() {
		return mallType;
	}
	public void setMallType(String mallType) {
		this.mallType = mallType;
	}
	public String getMallApiKey() {
		return mallApiKey;
	}
	public void setMallApiKey(String mallApiKey) {
		this.mallApiKey = mallApiKey;
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
}
