package saleson.shop.remittance.domain;

import saleson.seller.main.domain.Seller;

@SuppressWarnings("serial")
public class RemittanceExpected extends Seller {
	private double itemTotalCommissionBaseAmount;
	private double itemTotalCommissionAmount;
	private double itemTotalSupplyAmount;
	private double itemTotalSellerDiscountAmount;
	private double itemTotalSellerPointAmount;
	private double shippingTotalAmount;
	private double addPaymentTotalAmount;
	
	public double getAddPaymentTotalAmount() {
		return addPaymentTotalAmount;
	}
	public void setAddPaymentTotalAmount(double addPaymentTotalAmount) {
		this.addPaymentTotalAmount = addPaymentTotalAmount;
	}
	public double getItemTotalCommissionAmount() {
		return itemTotalCommissionAmount;
	}
	public void setItemTotalCommissionAmount(double itemTotalCommissionAmount) {
		this.itemTotalCommissionAmount = itemTotalCommissionAmount;
	}
	public double getItemTotalSupplyAmount() {
		return itemTotalSupplyAmount;
	}
	public void setItemTotalSupplyAmount(double itemTotalSupplyAmount) {
		this.itemTotalSupplyAmount = itemTotalSupplyAmount;
	}
	public double getItemTotalSellerDiscountAmount() {
		return itemTotalSellerDiscountAmount;
	}
	public void setItemTotalSellerDiscountAmount(
			double itemTotalSellerDiscountAmount) {
		this.itemTotalSellerDiscountAmount = itemTotalSellerDiscountAmount;
	}
	public double getShippingTotalAmount() {
		return shippingTotalAmount;
	}
	public void setShippingTotalAmount(double shippingTotalAmount) {
		this.shippingTotalAmount = shippingTotalAmount;
	}
	public double getItemTotalCommissionBaseAmount() {
		return itemTotalCommissionBaseAmount;
	}
	public void setItemTotalCommissionBaseAmount(
			double itemTotalCommissionBaseAmount) {
		this.itemTotalCommissionBaseAmount = itemTotalCommissionBaseAmount;
	}
	public double getItemTotalSellerPointAmount() {
		return itemTotalSellerPointAmount;
	}
	public void setItemTotalSellerPointAmount(double itemTotalSellerPointAmount) {
		this.itemTotalSellerPointAmount = itemTotalSellerPointAmount;
	}
	public double getItemRemittanceAmount() {
		return itemTotalSupplyAmount - itemTotalSellerDiscountAmount - itemTotalSellerPointAmount;
	}
}
