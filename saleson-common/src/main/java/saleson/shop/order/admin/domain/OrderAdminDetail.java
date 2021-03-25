package saleson.shop.order.admin.domain;

import saleson.shop.item.domain.Item;
import saleson.shop.order.domain.Buyer;
import saleson.shop.order.domain.Receiver;

public class OrderAdminDetail {
	private String workDate;
	private int workSequence;
	private int itemSequence;
	
	private String orderGroupCode;
	private String templateVersion;
	private String excelData;
	
	private int salePrice;
	private String updateManagerName;
	private String updatedDate;
	private String createdDate;
	
	private Item item;
	private Buyer buyer;
	private Receiver receiver;
	
	
	
	private boolean isError = false;
	private String errorMessage;
	
	private String dataStatusCode;
	public String getDataStatusCode() {
		return dataStatusCode;
	}
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	private int quantity;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
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
	public String getOrderGroupCode() {
		return orderGroupCode;
	}
	public void setOrderGroupCode(String orderGroupCode) {
		this.orderGroupCode = orderGroupCode;
	}
	public String getTemplateVersion() {
		return templateVersion;
	}
	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}
	public String getExcelData() {
		return excelData;
	}
	public void setExcelData(String excelData) {
		this.excelData = excelData;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public Receiver getReceiver() {
		return receiver;
	}
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	public String getUpdateManagerName() {
		return updateManagerName;
	}
	public void setUpdateManagerName(String updateManagerName) {
		this.updateManagerName = updateManagerName;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
}
