package saleson.shop.order.domain;

import java.util.ArrayList;
import java.util.List;

public class ReCalculationForShipping {

	private int orderShippingId;
	private String shippingPaymentType;
	private int payShipping;
	private int realShipping;
	private String mode;
	private int copyTargetOrderShippingId;
	
	List<ReCalculationForItem> items;
	
	public int getCopyTargetOrderShippingId() {
		return copyTargetOrderShippingId;
	}

	public void setCopyTargetOrderShippingId(int copyTargetOrderShippingId) {
		this.copyTargetOrderShippingId = copyTargetOrderShippingId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getOrderShippingId() {
		return orderShippingId;
	}

	public void setOrderShippingId(int orderShippingId) {
		this.orderShippingId = orderShippingId;
	}

	public String getShippingPaymentType() {
		return shippingPaymentType;
	}

	public void setShippingPaymentType(String shippingPaymentType) {
		this.shippingPaymentType = shippingPaymentType;
	}

	public int getPayShipping() {
		return payShipping;
	}

	public void setPayShipping(int payShipping) {
		this.payShipping = payShipping;
	}

	public int getRealShipping() {
		return realShipping;
	}

	public void setRealShipping(int realShipping) {
		this.realShipping = realShipping;
	}

	public List<ReCalculationForItem> getItems() {
		return items;
	}

	public void setItems(List<ReCalculationForItem> items) {
		this.items = items;
	}
	
	public void setItem(ReCalculationForItem item) {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		
		this.items.add(item);
	}
	
}
