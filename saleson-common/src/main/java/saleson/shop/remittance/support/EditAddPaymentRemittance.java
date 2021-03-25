package saleson.shop.remittance.support;

public class EditAddPaymentRemittance {
	private int addPaymentId;
	private String remittanceExpectedDate;
	
	private String conditionType;

	// 리스트에서 확정할때 쓰는것들
	private long sellerId;
	private String startDate;
	private String endDate;
	// 리스트에서 확정할때 쓰는것들
	
	public int getAddPaymentId() {
		return addPaymentId;
	}
	public void setAddPaymentId(int addPaymentId) {
		this.addPaymentId = addPaymentId;
	}
	public String getRemittanceExpectedDate() {
		return remittanceExpectedDate;
	}
	public void setRemittanceExpectedDate(String remittanceExpectedDate) {
		this.remittanceExpectedDate = remittanceExpectedDate;
	}
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
