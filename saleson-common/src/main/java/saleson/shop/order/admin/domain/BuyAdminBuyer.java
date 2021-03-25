package saleson.shop.order.admin.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.utils.ShopUtils;
import saleson.shop.order.admin.support.OrderAdminData;
import saleson.shop.user.domain.UserDetail;

import com.onlinepowers.framework.security.userdetails.User;

public class BuyAdminBuyer {
	private static final Logger log = LoggerFactory.getLogger(BuyAdminBuyer.class);

	public BuyAdminBuyer() {}
	public BuyAdminBuyer(OrderAdminData data) {
		
		
		User user = data.getUser();
		if (user != null) {
			
			UserDetail userDetail = (UserDetail) user.getUserDetail();
			
			setZipcode(userDetail.getPost());
			setAddress(userDetail.getAddress());
			setAddressDetail(userDetail.getAddressDetail());
			
			try {
				setSido(ShopUtils.getSido(userDetail.getAddress()));
				setSigungu(ShopUtils.getSigungu(userDetail.getAddress()));
				setEupmyeondong(ShopUtils.getEupmyeondong(userDetail.getAddress()));
			} catch(Exception e) {
				log.error("ERROR: 시도, 시군구 정보 파싱 오류 ({})", e.getMessage(), e);
			}
			
			setPhone(userDetail.getTelNumber());
		}
		
		setUserName(data.getUserName());
		setMobile(data.getMobile());
		
	}
	
	private String userName;
	private String phone;
	private String mobile;
	private String email;
	private String zipcode;
	private String newZipcode;
	private String companyName;
	private String sido;
	private String sigungu;
	private String eupmyeondong;
	private String address;
	private String addressDetail;
	
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
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
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
}
