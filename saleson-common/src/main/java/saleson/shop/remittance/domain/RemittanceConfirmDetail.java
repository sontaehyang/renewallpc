package saleson.shop.remittance.domain;

public class RemittanceConfirmDetail {
	private String orderCode;
	private String productType;
	private String remittanceStatusCode;
	private String remittanceDate;
	private String itemName;
	private String options;
	private int commissionBasePrice;
	private int commissionPrice;
	private float commissionRate;
	private String commissionType;
	private int sellerDiscountPrice;
	private String sellerDiscountDetail;
	private int sellerPoint;
	private int salePrice;
	private int supplyPrice;
	private int quantity;
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getRemittanceDate() {
		return remittanceDate;
	}
	public void setRemittanceDate(String remittanceDate) {
		this.remittanceDate = remittanceDate;
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
	public int getCommissionPrice() {
		return commissionPrice;
	}
	public void setCommissionPrice(int commissionPrice) {
		this.commissionPrice = commissionPrice;
	}
	public int getSellerDiscountPrice() {
		return sellerDiscountPrice;
	}
	public void setSellerDiscountPrice(int sellerDiscountPrice) {
		this.sellerDiscountPrice = sellerDiscountPrice;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
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
	public String getSellerDiscountDetail() {
		return sellerDiscountDetail;
	}
	public void setSellerDiscountDetail(String sellerDiscountDetail) {
		this.sellerDiscountDetail = sellerDiscountDetail;
	}
	public int getSellerPoint() {
		return sellerPoint;
	}
	public void setSellerPoint(int sellerPoint) {
		this.sellerPoint = sellerPoint;
	}
	public String getRemittanceStatusCode() {
		return remittanceStatusCode;
	}
	public void setRemittanceStatusCode(String remittanceStatusCode) {
		this.remittanceStatusCode = remittanceStatusCode;
	}
	public int getCommissionBasePrice() {
		return commissionBasePrice;
	}
	public void setCommissionBasePrice(int commissionBasePrice) {
		this.commissionBasePrice = commissionBasePrice;
	}
	public float getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(float commissionRate) {
		this.commissionRate = commissionRate;
	}
	public double getRemittancePrice() {
		return (supplyPrice - sellerDiscountPrice - sellerPoint);
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}
}
