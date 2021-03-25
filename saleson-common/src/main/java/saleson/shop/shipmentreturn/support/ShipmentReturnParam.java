package saleson.shop.shipmentreturn.support;

public class ShipmentReturnParam {
	private long sellerId;
	private int shipmentReturnId;
	private String defaultAddressFlag;
	private int itemId;
	
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	
	public int getShipmentReturnId() {
		return shipmentReturnId;
	}
	public void setShipmentReturnId(int shipmentReturnId) {
		this.shipmentReturnId = shipmentReturnId;
	}
	public String getDefaultAddressFlag() {
		return defaultAddressFlag;
	}
	public void setDefaultAddressFlag(String defaultAddressFlag) {
		this.defaultAddressFlag = defaultAddressFlag;
	}
	
	
	
}
