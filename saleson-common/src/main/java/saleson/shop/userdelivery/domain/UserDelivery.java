package saleson.shop.userdelivery.domain;

import com.onlinepowers.framework.util.ValidationUtils;
import org.springframework.util.StringUtils;

import saleson.common.utils.ShopUtils;

import javax.validation.constraints.NotEmpty;

public class UserDelivery {

	private int userDeliveryId;
	private long userId;
	@NotEmpty
	private String defaultFlag;
	private String title;
	@NotEmpty
	private String userName;
	private String phone;

	private String phone1;
	private String phone2;
	private String phone3;

	private String mobile;

	private String mobile1;
	private String mobile2;
	private String mobile3;

	private String newZipcode;
	@NotEmpty
	private String zipcode;

	private String zipcode1;
	private String zipcode2;

	private String sido;
	private String sigungu;
	private String eupmyeondong;

	@NotEmpty
	private String address;
	@NotEmpty
	private String addressDetail;
	private String createdDate;

	@NotEmpty
	private String frontMobile;
	@NotEmpty
	private String backMobile;

	public String getNewZipcode() {
		return newZipcode;
	}

	public void setNewZipcode(String newZipcode) {
		this.newZipcode = newZipcode;
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

	public String getFullMobile() {
		if (!StringUtils.isEmpty(this.frontMobile) && !StringUtils.isEmpty(this.backMobile)) {
			String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(this.frontMobile + this.backMobile);

			this.mobile1 = ValidationUtils.isNotNull(cutArray[0]) ? cutArray[0] : "";
			this.mobile2 = ValidationUtils.isNotNull(cutArray[1]) ? cutArray[1] : "";
			this.mobile3 = ValidationUtils.isNotNull(cutArray[2]) ? cutArray[2] : "";
		}

		return this.mobile1 + "-" + this.mobile2 + "-" + this.mobile3;
	}

	public String getFullPhone() {
		return this.phone1 + "-" + this.phone2 + "-" + this.phone3;
	}

	public String getFullZipcode() {

		if (StringUtils.isEmpty(this.zipcode2)) {
			return this.zipcode;
		}

		return this.zipcode1 + "-" + this.zipcode2;
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

	public int getUserDeliveryId() {
		return userDeliveryId;
	}

	public void setUserDeliveryId(int userDeliveryId) {
		this.userDeliveryId = userDeliveryId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		if ("--".equals(phone)) {
			return "";
		}

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
		if ("--".equals(mobile)) {
			return "";
		}

		return mobile;
	}

	public void setMobile(String mobile) {

		String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(mobile);

		this.mobile1 = ValidationUtils.isNotNull(cutArray[0]) ? cutArray[0] : "";
		this.mobile2 = ValidationUtils.isNotNull(cutArray[1]) ? cutArray[1] : "";
		this.mobile3 = ValidationUtils.isNotNull(cutArray[2]) ? cutArray[2] : "";

		this.frontMobile = this.mobile1;
		this.backMobile = this.mobile2 + this.mobile3;

		this.mobile = this.mobile1 + "-" + this.mobile2 + "-" + this.mobile3;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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

	public String getFrontMobile() {
		return frontMobile;
	}

	public void setFrontMobile(String frontMobile) {
		this.frontMobile = frontMobile;
	}

	public String getBackMobile() {
		return backMobile;
	}

	public void setBackMobile(String backMobile) {
		this.backMobile = backMobile;
	}
}