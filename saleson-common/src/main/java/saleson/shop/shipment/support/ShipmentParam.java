package saleson.shop.shipment.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class ShipmentParam extends SearchParam {
	private int shipmentId;
	private long sellerId;
	private String defaultAddressFlag;
	public int getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(int shipmentId) {
		this.shipmentId = shipmentId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getDefaultAddressFlag() {
		return defaultAddressFlag;
	}
	public void setDefaultAddressFlag(String shipmentDefaultFlag) {
		this.defaultAddressFlag = shipmentDefaultFlag;
	}

}
