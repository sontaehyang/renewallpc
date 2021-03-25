/*
 * Copyright(c) 2009-2011 Onlinepowers Development Team
 * http://www.onlinepowers.com
 * 
 * @file com.onlinepowers.web.security.UserDetail.java
 * @date 2011. 10. 5.
 */
package saleson.shop.user.domain;

import java.io.Serializable;

import org.springframework.security.core.SpringSecurityCoreVersion;
import saleson.common.utils.ShopUtils;
import saleson.shop.userlevel.domain.UserLevel;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;


public class UserDetail implements Serializable {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private int shadowLoginLogId;
	public int getShadowLoginLogId() {
		return shadowLoginLogId;
	}
	public void setShadowLoginLogId(int shadowLoginLogId) {
		this.shadowLoginLogId = shadowLoginLogId;
	}
	
	private String conditionType;
	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	private String levelName;
	private String groupName;
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	private long userId;
	private String groupCode;
	private String newPost;
	private String post;
	private String address;
	private String addressDetail;
	private String telNumber;
	private String phoneNumber;
	private String faxNumber;
	private String receiveEmail = "0";
	private String receiveSms = "0";
	private String receivePush = "1";
	private String gender = "";
	private String age = "";
	private int point;
	private int buyCount;
	private int buyPrice;
	private String lastBuyDate;
	private String leaveReason;
	private String siteFlag;
	private String useFlag = "Y";
	private String birthdayType;
	private String birthday;
	private int levelId;
	private String levelLabel;
	private String userLevelExpirationDate;
	private float userLevelDiscountRate;
	private float userLevelPointRate;

	public float getUserLevelDiscountRate() {
		return userLevelDiscountRate;
	}

	public void setUserLevelDiscountRate(float userLevelDiscountRate) {
		this.userLevelDiscountRate = userLevelDiscountRate;
	}

	public String getUserLevelExpirationDate() {
		return userLevelExpirationDate;
	}

	public void setUserLevelExpirationDate(String userLevelExpirationDate) {
		this.userLevelExpirationDate = userLevelExpirationDate;
	}

	public float getUserLevelPointRate() {
		return userLevelPointRate;
	}

	public void setUserLevelPointRate(float userLevelPointRate) {
		this.userLevelPointRate = userLevelPointRate;
	}

	private String birthdayYear;
	private String birthdayMonth;
	private String birthdayDay;
	
	private String post1;
	private String post2;
	
	private String telNumber1;
	private String telNumber2;
	private String telNumber3;
	
	private String faxNumber1;
	private String faxNumber2;
	private String faxNumber3;
	
	private String phoneNumber1;
	private String phoneNumber2;
	private String phoneNumber3;

	private String frontPhoneNumber;
	private String backPhoneNumber;
	
	private UserLevel userlevel;

	public UserDetail() {}
	
	public UserDetail(long userId, String phoneNumber, String post,
			String address, String addressDetail, String telNumber, String faxNumber, String receiveEmail, String receiveSms, String receivePush,
			String gender,String age, int point, int buyCount, int buyPrice, String leaveReason, String siteFlag, String useFlag
			) {
		this.userId = userId;
		this.phoneNumber = phoneNumber;
		this.post = post;
		this.address = address;
		this.addressDetail = addressDetail;
		this.telNumber = telNumber;
		this.faxNumber = faxNumber;
		this.receiveEmail = receiveEmail;
		this.receiveSms = receiveSms;
		this.receivePush = receivePush;
		this.gender = gender;
		this.age = age;
		this.point = point;
		this.buyCount = buyCount;
		this.buyPrice = buyPrice;
		this.leaveReason = leaveReason;
		this.siteFlag = siteFlag;
		this.useFlag = useFlag;
	}
	
	public String getNewPost() {
		return newPost;
	}

	public void setNewPost(String newPost) {
		this.newPost = newPost;
	}

	public String getBirthdayYear() {
		return birthdayYear;
	}

	public void setBirthdayYear(String birthdayYear) {
		this.birthdayYear = birthdayYear;
	}

	public String getBirthdayMonth() {
		return birthdayMonth;
	}

	public void setBirthdayMonth(String birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}

	public String getBirthdayDay() {
		return birthdayDay;
	}
	
	public String getFullBirthday() {
		if (StringUtils.isEmpty(birthdayYear) || StringUtils.isEmpty(birthdayMonth) || StringUtils.isEmpty(birthdayDay)) {
			return "";
		}
		return birthdayYear + "-" + birthdayMonth + "-" + birthdayDay;
	}

	public void setBirthdayDay(String birthdayDay) {
		this.birthdayDay = birthdayDay;
	}

	public String getBirthdayType() {
		return birthdayType;
	}

	public void setBirthdayType(String birthdayType) {
		this.birthdayType = birthdayType;
	}

	public String getBirthday() {
		
		String[] birthdays = ShopUtils.phoneNumberForDelimitedToStringArray(birthday);
		
		this.birthdayYear = ValidationUtils.isNotNull(birthdays[0]) ? birthdays[0] : "";
		this.birthdayMonth = ValidationUtils.isNotNull(birthdays[1]) ? birthdays[1] : "";
		this.birthdayDay = ValidationUtils.isNotNull(birthdays[2]) ? birthdays[2] : "";
		
		return birthday;
	}

	public void setBirthday(String birthday) {
		
		String[] birthdays = ShopUtils.phoneNumberForDelimitedToStringArray(birthday);
		
		this.birthdayYear = ValidationUtils.isNotNull(birthdays[0]) ? birthdays[0] : "";
		this.birthdayMonth = ValidationUtils.isNotNull(birthdays[1]) ? birthdays[1] : "";
		this.birthdayDay = ValidationUtils.isNotNull(birthdays[2]) ? birthdays[2] : "";
		
		this.birthday = birthday;
	}

	
	public String getLastBuyDate() {
		return lastBuyDate;
	}

	public void setLastBuyDate(String lastBuyDate) {
		this.lastBuyDate = lastBuyDate;
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		String[] posts = StringUtils.delimitedListToStringArray(StringUtils.trim(post), "-");
		if (posts.length == 0) {
			this.post1 = "";
			this.post2 = "";
			this.post = post;
		} else {
			
			this.post1 = posts[0];
			if (posts.length == 2) {
				this.post2 = posts[1];
			}
			
			this.post = post;
		}
	}

	public String getFullPost() {
		
		if (StringUtils.isEmpty(this.post2)) {
			return this.post;
		}
		
		return this.post1 + "-" + this.post2;
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

	public String getTelNumber() {
		return telNumber;
	}
	
	public String getFullTelNumber() {
		
		if (this.telNumber1 == null) {
			return "";
		}
		
		return this.telNumber1 + '-' + this.telNumber2 + '-' + this.telNumber3;
	}
	
	public void setTelNumber(String telNumber) {
		String[] telNumbers = ShopUtils.phoneNumberForDelimitedToStringArray(telNumber);
		
		this.telNumber1 = ValidationUtils.isNotNull(telNumbers[0]) ? telNumbers[0] : "";
		this.telNumber2 = ValidationUtils.isNotNull(telNumbers[1]) ? telNumbers[1] : "";
		this.telNumber3 = ValidationUtils.isNotNull(telNumbers[2]) ? telNumbers[2] : "";
	

		this.telNumber = telNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}
	
	public void setFaxNumber(String faxNumber) {
		String[] faxNumbers = ShopUtils.phoneNumberForDelimitedToStringArray(faxNumber);

		this.faxNumber1 = faxNumbers[0];
		this.faxNumber2 = faxNumbers[1];
		this.faxNumber3 = faxNumbers[2];
		this.faxNumber = faxNumber;
	}
	
	public String getFullFaxNumber() {
		
		if (this.faxNumber1 == null) {
			return "";
		}
		
		return this.faxNumber1 + '-' + this.faxNumber2 + '-' + this.faxNumber3;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getFullPhoneNumber() {
		
		if (this.phoneNumber1 == null) {
			return "";
		}
		
		return this.phoneNumber1 + '-' + this.phoneNumber2 + '-' + this.phoneNumber3;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		String[] phoneNumbers = ShopUtils.phoneNumberForDelimitedToStringArray(phoneNumber);
		this.phoneNumber1 = ValidationUtils.isNotNull(phoneNumbers[0]) ? phoneNumbers[0] : "";
		this.phoneNumber2 = ValidationUtils.isNotNull(phoneNumbers[1]) ? phoneNumbers[1] : "";
		this.phoneNumber3 = ValidationUtils.isNotNull(phoneNumbers[2]) ? phoneNumbers[2] : "";

		this.frontPhoneNumber = this.phoneNumber1;
		this.backPhoneNumber = this.phoneNumber2 + this.phoneNumber3;
		
		this.phoneNumber = phoneNumber;
	}

	public String getReceiveEmail() {
		return receiveEmail;
	}

	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}

	public String getReceiveSms() {
		return receiveSms;
	}

	public void setReceiveSms(String receiveSms) {
		this.receiveSms = receiveSms;
	}

	public String getReceivePush() {
		return receivePush;
	}

	public void setReceivePush(String receivePush) {
		this.receivePush = receivePush;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public int getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getSiteFlag() {
		return siteFlag;
	}

	public void setSiteFlag(String siteFlag) {
		this.siteFlag = siteFlag;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	
	public String getLevelLabel(){
		String label = "";
		if (this.levelId > 0){
			String level = String.valueOf(this.levelId);
			if("1".equals(level)){
				label = "일반";
			}else if("2".equals(level)){
				label = "우수";
			}else if("3".equals(level)){
				label = "프리미엄";
			}else if("4".equals(level)){
				label = "VIP";
			}
		} else {
			label = "-";
		}
		return label;
	}

	public String getPost1() {
		return post1;
	}
	public void setPost1(String post1) {
		this.post1 = post1;
	}
	
	public String getPost2() {
		return post2;
	}

	public void setPost2(String post2) {
		this.post2 = post2;
	}
	
	public String getTelNumber1() {
		return telNumber1;
	}
	public void setTelNumber1(String telNumber1) {
		this.telNumber1 = telNumber1;
	}
	public String getTelNumber2() {
		return telNumber2;
	}
	public void setTelNumber2(String telNumber2) {
		this.telNumber2 = telNumber2;
	}
	public String getTelNumber3() {
		return telNumber3;
	}
	public void setTelNumber3(String telNumber3) {
		this.telNumber3 = telNumber3;
	}
	public String getFaxNumber1() {
		return faxNumber1;
	}
	public void setFaxNumber1(String faxNumber1) {
		this.faxNumber1 = faxNumber1;
	}
	public String getFaxNumber2() {
		return faxNumber2;
	}
	public void setFaxNumber2(String faxNumber2) {
		this.faxNumber2 = faxNumber2;
	}
	public String getFaxNumber3() {
		return faxNumber3;
	}
	public void setFaxNumber3(String faxNumber3) {
		this.faxNumber3 = faxNumber3;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public String getPhoneNumber3() {
		return phoneNumber3;
	}

	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}

	public UserLevel getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(UserLevel userlevel) {
		this.userlevel = userlevel;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getFrontPhoneNumber() {
		return frontPhoneNumber;
	}

	public void setFrontPhoneNumber(String frontPhoneNumber) {
		this.frontPhoneNumber = frontPhoneNumber;
	}

	public String getBackPhoneNumber() {
		return backPhoneNumber;
	}

	public void setBackPhoneNumber(String backPhoneNumber) {
		this.backPhoneNumber = backPhoneNumber;
	}
}
