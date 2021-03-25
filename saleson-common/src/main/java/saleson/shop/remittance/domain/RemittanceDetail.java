package saleson.shop.remittance.domain;

public class RemittanceDetail {
	
	private int remittanceDetailId;
	private int remittanceId;
	private String orderCode;
	private String itemType;
	private String itemUserCode;
	private String itemName;
	private String options;
	
	private int salePrice;
	private int sellerDiscountPrice;
	private String sellerDiscountDetail;
	private int sellerPoint;
	private int supplyPrice;
	private int quantity;
	private int commissionBasePrice;
	
	private float commissionRate;
	private String commissionType;
	private int commissionPrice;
	private int remittancePrice;
	
	private String createdDate;
	public int getRemittanceDetailId() {
		return remittanceDetailId;
	}
	public void setRemittanceDetailId(int remittanceDetailId) {
		this.remittanceDetailId = remittanceDetailId;
	}
	public int getRemittanceId() {
		return remittanceId;
	}
	public void setRemittanceId(int remittanceId) {
		this.remittanceId = remittanceId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	public int getSellerDiscountPrice() {
		return sellerDiscountPrice;
	}
	public void setSellerDiscountPrice(int sellerDiscountPrice) {
		this.sellerDiscountPrice = sellerDiscountPrice;
	}
	public String getSellerDiscountDetail() {
		return sellerDiscountDetail;
	}
	public void setSellerDiscountDetail(String sellerDiscountDetail) {
		this.sellerDiscountDetail = sellerDiscountDetail;
	}
	public int getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(int supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(float commissionRate) {
		this.commissionRate = commissionRate;
	}
	public int getCommissionPrice() {
		return commissionPrice;
	}
	public void setCommissionPrice(int commissionPrice) {
		this.commissionPrice = commissionPrice;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getSellerPoint() {
		return sellerPoint;
	}
	public void setSellerPoint(int sellerPoint) {
		this.sellerPoint = sellerPoint;
	}
	public int getRemittancePrice() {
		return remittancePrice;
	}
	public void setRemittancePrice(int remittancePrice) {
		this.remittancePrice = remittancePrice;
	}
	public int getCommissionBasePrice() {
		return commissionBasePrice;
	}
	public void setCommissionBasePrice(int commissionBasePrice) {
		this.commissionBasePrice = commissionBasePrice;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}
}
