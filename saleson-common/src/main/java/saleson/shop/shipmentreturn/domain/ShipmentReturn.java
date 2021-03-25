package saleson.shop.shipmentreturn.domain;

public class ShipmentReturn {
	private int shipmentReturnId;
	private long sellerId;
	private String addressName;
	private String name;
	private String telephoneNumber;
	private String zipcode;
	private String address;
	private String addressDetail;
	private String defaultAddressFlag;
	private String createdDate;
	
	private int deleteShipmentReturnId;
	
	public int getDeleteShipmentReturnId() {
		return deleteShipmentReturnId;
	}
	public void setDeleteShipmentReturnId(int deleteShipmentReturnId) {
		this.deleteShipmentReturnId = deleteShipmentReturnId;
	}
	public int getShipmentReturnId() {
		return shipmentReturnId;
	}
	public void setShipmentReturnId(int shipmentReturnId) {
		this.shipmentReturnId = shipmentReturnId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public String getDefaultAddressFlag() {
		return defaultAddressFlag;
	}
	public void setDefaultAddressFlag(String defaultAddressFlag) {
		this.defaultAddressFlag = defaultAddressFlag;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getFullAddress() {
		return "[" + this.zipcode + "] " + this.address + " " + this.addressDetail;
		
	}
}
