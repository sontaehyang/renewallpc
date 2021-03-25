package saleson.shop.order.domain;


public class ReCalculationForItem {
	
	private int orderItemId;
	private int orderShippingId;
	private String orderStatus;
	private int quantity;
	private int couponDiscountAmount;
	private int commissionBasePrice;
	private int commissionPrice;
	private int adminDiscountAmount;
	private String adminDiscountDetail;
	private int sellerDiscountAmount;
	private String sellerDiscountDetail;
	private int supplyPrice;
	private int saleAmount;
	private double earnPoint;
	
	private int copyTargetOrderItemId;
	private String mode; // INSERT, UPDATE
	
	public ReCalculationForItem() {}
	public ReCalculationForItem(ItemPrice itemPrice, int orderItemId, String orderStatus, int copyTargetOrderItemId) {
		
		this.orderItemId = orderItemId;
		this.orderStatus = orderStatus;
		this.quantity = itemPrice.getQuantity();
		this.couponDiscountAmount = itemPrice.getCouponDiscountAmount();
		this.commissionBasePrice = itemPrice.getCommissionBasePrice();
		this.commissionPrice = itemPrice.getCommissionPrice();
		this.adminDiscountAmount = itemPrice.getAdminDiscountAmount();
		this.adminDiscountDetail = itemPrice.getAdminDiscountDetail();
		this.sellerDiscountAmount = itemPrice.getSellerDiscountAmount();
		this.sellerDiscountDetail = itemPrice.getSellerDiscountDetail();
		this.supplyPrice = itemPrice.getSupplyPrice();
		this.earnPoint = itemPrice.getEarnPoint();
		this.saleAmount = itemPrice.getSaleAmount();
		this.copyTargetOrderItemId = copyTargetOrderItemId;
		this.mode = "update";
		if (copyTargetOrderItemId > 0) {
			this.mode = "insert";
		}
	}
	
	public int getOrderShippingId() {
		return orderShippingId;
	}
	public void setOrderShippingId(int orderShippingId) {
		this.orderShippingId = orderShippingId;
	}
	public int getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(int saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getCouponDiscountAmount() {
		return couponDiscountAmount;
	}
	public void setCouponDiscountAmount(int couponDiscountAmount) {
		this.couponDiscountAmount = couponDiscountAmount;
	}
	public int getCommissionBasePrice() {
		return commissionBasePrice;
	}
	public void setCommissionBasePrice(int commissionBasePrice) {
		this.commissionBasePrice = commissionBasePrice;
	}
	public int getCommissionPrice() {
		return commissionPrice;
	}
	public void setCommissionPrice(int commissionPrice) {
		this.commissionPrice = commissionPrice;
	}
	public int getAdminDiscountAmount() {
		return adminDiscountAmount;
	}
	public void setAdminDiscountAmount(int adminDiscountAmount) {
		this.adminDiscountAmount = adminDiscountAmount;
	}
	public String getAdminDiscountDetail() {
		return adminDiscountDetail;
	}
	public void setAdminDiscountDetail(String adminDiscountDetail) {
		this.adminDiscountDetail = adminDiscountDetail;
	}
	public int getSellerDiscountAmount() {
		return sellerDiscountAmount;
	}
	public void setSellerDiscountAmount(int sellerDiscountAmount) {
		this.sellerDiscountAmount = sellerDiscountAmount;
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
	public double getEarnPoint() {
		return earnPoint;
	}
	public void setEarnPoint(double earnPoint) {
		this.earnPoint = earnPoint;
	}
	public int getCopyTargetOrderItemId() {
		return copyTargetOrderItemId;
	}
	public void setCopyTargetOrderItemId(int copyTargetOrderItemId) {
		this.copyTargetOrderItemId = copyTargetOrderItemId;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
