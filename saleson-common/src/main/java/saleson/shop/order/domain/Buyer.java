package saleson.shop.order.domain;

import saleson.common.utils.ShopUtils;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.StringUtils;

public class Buyer {
	
	public Buyer() {}
	public Buyer(User user) {
		
		setUserId(user.getUserId());
		setUserName(user.getUserName());
		setLoginId(user.getLoginId());
		
	}
	
	private String orderCode;
	private int orderSequence;
	private String ip;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	private OrderPrice orderPrice;
	public OrderPrice getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(OrderPrice orderPrice) {
		this.orderPrice = orderPrice;
	}
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

	private long userId;
	private String loginId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	private String userName;
	private String phone;
	private String mobile;
	private String email;
	private String email1;
	private String email2;
	private String zipcode;
	private String newZipcode;
	private String companyName;
	private String sido;
	private String sigungu;
	private String eupmyeondong;
	private String address;
	private String addressDetail;
	
	private String zipcode1;
	private String zipcode2;
	public String getFullZipcode() {
		
		if (StringUtils.isEmpty(this.zipcode2)) {
			return this.zipcode;
		}
		
		return this.zipcode1 + "-" + this.zipcode2;
	}
	public String getZipcode1() {
		return zipcode1;
	}
	public void setZipcode1(String zipcode1) {
		this.zipcode1 = zipcode1;
	}
	public String getZipcode2() {
		return zipcode2;
	}
	public void setZipcode2(String zipcode2) {
		this.zipcode2 = zipcode2;
	}
	
	private String phone1;
	private String phone2;
	private String phone3;
	public String getFullPhone() {
		return this.phone1 + "-" + this.phone2 + "-" + this.phone3;
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
	
	private String mobile1;
	private String mobile2;
	private String mobile3;
	public String getFullMobile() {
		return this.mobile1 + "-" + this.mobile2 + "-" + this.mobile3;
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

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		
		String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(phone);
		
		this.phone1 = cutArray[0];
		this.phone2 = cutArray[1];
		this.phone3 = cutArray[2];
		
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		
		String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(mobile);
		
		this.mobile1 = cutArray[0];
		this.mobile2 = cutArray[1];
		this.mobile3 = cutArray[2];
		
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		
		if (StringUtils.isNotEmpty(email)) {
			String[] temp = StringUtils.delimitedListToStringArray(email, "@");
			
			if (temp.length == 2) {
				this.email1 = temp[0];
				this.email2 = temp[1];
			} else {
				email = "";
			}
		}
		
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		String[] temp = StringUtils.delimitedListToStringArray(zipcode, "-");
		if (temp.length == 2) {
			this.zipcode1 = temp[0];
			this.zipcode2 = temp[1];
		}
		
		this.zipcode = zipcode;
	}
	public String getNewZipcode() {
		return newZipcode;
	}
	public void setNewZipcode(String newZipcode) {
		this.newZipcode = newZipcode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getSigungu() {
		return sigungu;
	}
	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
	}
	public String getEupmyeondong() {
		return eupmyeondong;
	}
	public void setEupmyeondong(String eupmyeondong) {
		this.eupmyeondong = eupmyeondong;
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
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}



	//RENTAL_PAY_BUY 렌탈 결제용
	String rentalPayBuy = "N";
	int rentalPer;
	int rentalMonthAmt;

	public String getRentalPayBuy() {
		return rentalPayBuy;
	}
	public void setRentalPayBuy(String rentalPayBuy) {
		this.rentalPayBuy = rentalPayBuy;
	}

	public int getRentalPer() {
		return rentalPer;
	}

	public void setRentalPer(int rentalPer) {
		this.rentalPer = rentalPer;
	}

	public int getRentalMonthAmt() {
		return rentalMonthAmt;
	}

	public void setRentalMonthAmt(int rentalMonthAmt) {
		this.rentalMonthAmt = rentalMonthAmt;
	}
}
