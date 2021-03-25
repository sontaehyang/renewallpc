package saleson.shop.remittance.support;

public class EditShippingRemittance {
	private String orderCode;
	private int orderSequence;
	private int shippingSequence;
	private String remittanceExpectedDate;
	
	private String conditionType;

	// 리스트에서 확정할때 쓰는것들
	private long sellerId;
	private String startDate;
	private String endDate;
	// 리스트에서 확정할때 쓰는것들
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	public int getShippingSequence() {
		return shippingSequence;
	}
	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
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
