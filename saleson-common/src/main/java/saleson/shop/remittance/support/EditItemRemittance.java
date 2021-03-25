package saleson.shop.remittance.support;

public class EditItemRemittance {
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private int supplyPrice;
	private String remittanceExpectedDate;
	
	private String conditionType;

	// 리스트에서 확정할때 쓰는것들
	private long sellerId;
	private String startDate;
	private String endDate;
	// 리스트에서 확정할때 쓰는것들
	
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
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
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
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public int getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(int supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	public String getRemittanceExpectedDate() {
		return remittanceExpectedDate;
	}
	public void setRemittanceExpectedDate(String remittanceExpectedDate) {
		this.remittanceExpectedDate = remittanceExpectedDate;
	}
}
