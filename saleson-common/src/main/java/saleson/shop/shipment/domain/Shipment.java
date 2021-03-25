package saleson.shop.shipment.domain;

public class Shipment {
	private int shipmentId;
	private long sellerId;
	private String addressName;
	private String name;
	private String telephoneNumber;
	private String zipcode;
	private String address;
	private String addressDetail;
	private String defaultAddressFlag;
	private int shipping;
	private int shippingFreeAmount;
	private int shippingExtraCharge1;
	private int shippingExtraCharge2;
	private String createdDate;
	private String updatedDate;
	private String shipmentGroupCode;
	
	private int deleteShipmentId;
	
	
	public int getDeleteShipmentId() {
		return deleteShipmentId;
	}
	public void setDeleteShipmentId(int deleteShipmentId) {
		this.deleteShipmentId = deleteShipmentId;
	}
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
	public void setDefaultAddressFlag(String shipmentDefaultFlag) {
		this.defaultAddressFlag = shipmentDefaultFlag;
	}
	public int getShipping() {
		return shipping;
	}
	public void setShipping(int shipping) {
		this.shipping = shipping;
	}
	public int getShippingFreeAmount() {
		return shippingFreeAmount;
	}
	public void setShippingFreeAmount(int shippingFreeAmount) {
		this.shippingFreeAmount = shippingFreeAmount;
	}
	public int getShippingExtraCharge1() {
		return shippingExtraCharge1;
	}
	public void setShippingExtraCharge1(int shippingExtraCharge1) {
		this.shippingExtraCharge1 = shippingExtraCharge1;
	}
	public int getShippingExtraCharge2() {
		return shippingExtraCharge2;
	}
	public void setShippingExtraCharge2(int shippingExtraCharge2) {
		this.shippingExtraCharge2 = shippingExtraCharge2;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getShipmentGroupCode() {
		return shipmentGroupCode;
	}
	public void setShipmentGroupCode(String shipmentGroupCode) {
		this.shipmentGroupCode = shipmentGroupCode;
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
