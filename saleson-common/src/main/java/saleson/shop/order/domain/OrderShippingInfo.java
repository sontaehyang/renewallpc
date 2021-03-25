package saleson.shop.order.domain;

import java.util.List;

import saleson.common.utils.ShopUtils;

import com.onlinepowers.framework.util.StringUtils;


public class OrderShippingInfo {
	
	public OrderShippingInfo() {}
	public OrderShippingInfo(String orderCode, int orderSequence, int shippingInfoSequence, Receiver receiver) {
		
		setOrderCode(orderCode);
		setOrderSequence(orderSequence);
		setShippingInfoSequence(shippingInfoSequence);
		
		setReceiveNewZipcode(receiver.getReceiveNewZipcode());
		setReceiveZipcode(receiver.getFullReceiveZipcode());
		setReceiveSido(receiver.getReceiveSido());
		setReceiveSigungu(receiver.getReceiveSigungu());
		setReceiveEupmyeondong(receiver.getReceiveEupmyeondong());
		setReceiveAddress(receiver.getReceiveAddress());
		setReceiveAddressDetail(receiver.getReceiveAddressDetail());
		setReceiveName(receiver.getReceiveName());
		setReceivePhone(receiver.getFullReceivePhone());
		setReceiveMobile(receiver.getFullReceiveMobile());
		setMemo(receiver.getContent());
		
	}
	
	private List<OrderItem> orderItems;
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	private String orderCode;
	private int orderSequence;
	private int shippingInfoSequence;
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	public int getShippingInfoSequence() {
		return shippingInfoSequence;
	}
	public void setShippingInfoSequence(int shippingInfoSequence) {
		this.shippingInfoSequence = shippingInfoSequence;
	}

	private String receiveZipcode;
	private String receiveNewZipcode;
	private String receiveCompanyName;
	private String receiveSido;
	private String receiveSigungu;
	private String receiveEupmyeondong;
	private String receiveAddress;
	private String receiveAddressDetail;
	private String receiveName;
	private String receivePhone;
	private String receiveMobile;
	private String memo;
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	private String createdDate;
	private String updatedDate;
	
	
	public String getReceiveNewZipcode() {
		return receiveNewZipcode;
	}
	public void setReceiveNewZipcode(String receiveNewZipcode) {
		this.receiveNewZipcode = receiveNewZipcode;
	}

	private String receiveZipcode1;
	private String receiveZipcode2;
	public String getFullReceiveZipcode() {
		
		if (StringUtils.isEmpty(this.receiveZipcode2)) {
			return this.receiveZipcode;
		}
		
		return this.receiveZipcode1 + "-" + this.receiveZipcode2;
	}
	
	private String phone1;
	private String phone2;
	private String phone3;
	public String getFullPhone() {
		return this.phone1 + "-" + this.phone2 + "-" + this.phone3;
	}
	
	private String mobile1;
	private String mobile2;
	private String mobile3;
	public String getFullMobile() {
		return this.mobile1 + "-" + this.mobile2 + "-" + this.mobile3;
	}
	
	private String receivePhone1;
	private String receivePhone2;
	private String receivePhone3;
	public String getFullReceivePhone() {
		return this.receivePhone1 + "-" + this.receivePhone2 + "-" + this.receivePhone3;
	}
	
	private String receiveMobile1;
	private String receiveMobile2;
	private String receiveMobile3;
	public String getFullReceiveMobile() {
		return this.receiveMobile1 + "-" + this.receiveMobile2 + "-" + this.receiveMobile3;
	}
	


	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getMobile3() {
		return mobile3;
	}
	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}
	public String getReceivePhone1() {
		return receivePhone1;
	}
	public void setReceivePhone1(String receivePhone1) {
		this.receivePhone1 = receivePhone1;
	}
	public String getReceivePhone2() {
		return receivePhone2;
	}
	public void setReceivePhone2(String receivePhone2) {
		this.receivePhone2 = receivePhone2;
	}
	public String getReceivePhone3() {
		return receivePhone3;
	}
	public void setReceivePhone3(String receivePhone3) {
		this.receivePhone3 = receivePhone3;
	}
	public String getReceiveMobile1() {
		return receiveMobile1;
	}
	public void setReceiveMobile1(String receiveMobile1) {
		this.receiveMobile1 = receiveMobile1;
	}
	public String getReceiveMobile2() {
		return receiveMobile2;
	}
	public void setReceiveMobile2(String receiveMobile2) {
		this.receiveMobile2 = receiveMobile2;
	}
	public String getReceiveMobile3() {
		return receiveMobile3;
	}
	public void setReceiveMobile3(String receiveMobile3) {
		this.receiveMobile3 = receiveMobile3;
	}
	
	public String getReceiveZipcode1() {
		return receiveZipcode1;
	}
	public void setReceiveZipcode1(String receiveZipcode1) {
		this.receiveZipcode1 = receiveZipcode1;
	}
	public String getReceiveZipcode2() {
		return receiveZipcode2;
	}
	public void setReceiveZipcode2(String receiveZipcode2) {
		this.receiveZipcode2 = receiveZipcode2;
	}
	
	public String getReceiveZipcode() {
		return receiveZipcode;
	}
	public void setReceiveZipcode(String receiveZipcode) {
		
		String[] temp = StringUtils.delimitedListToStringArray(receiveZipcode, "-");
		if (temp.length == 2) {
			this.receiveZipcode1 = temp[0];
			this.receiveZipcode2 = temp[1];
		}
		
		this.receiveZipcode = receiveZipcode;
	}
	public String getReceiveCompanyName() {
		return receiveCompanyName;
	}
	public void setReceiveCompanyName(String receiveCompanyName) {
		this.receiveCompanyName = receiveCompanyName;
	}
	public String getReceiveSido() {
		return receiveSido;
	}
	public void setReceiveSido(String receiveSido) {
		this.receiveSido = receiveSido;
	}
	public String getReceiveSigungu() {
		return receiveSigungu;
	}
	public void setReceiveSigungu(String receiveSigungu) {
		this.receiveSigungu = receiveSigungu;
	}
	public String getReceiveEupmyeondong() {
		return receiveEupmyeondong;
	}
	public void setReceiveEupmyeondong(String receiveEupmyeondong) {
		this.receiveEupmyeondong = receiveEupmyeondong;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getReceiveAddressDetail() {
		return receiveAddressDetail;
	}
	public void setReceiveAddressDetail(String receiveAddressDetail) {
		this.receiveAddressDetail = receiveAddressDetail;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		
		String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(receivePhone);
		
		this.receivePhone1 = cutArray[0];
		this.receivePhone2 = cutArray[1];
		this.receivePhone3 = cutArray[2];
		
		this.receivePhone = receivePhone;
	}
	public String getReceiveMobile() {
		return receiveMobile;
	}
	public void setReceiveMobile(String receiveMobile) {
		
		String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(receiveMobile);
		
		this.receiveMobile1 = cutArray[0];
		this.receiveMobile2 = cutArray[1];
		this.receiveMobile3 = cutArray[2];
		
		this.receiveMobile = receiveMobile;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
}
