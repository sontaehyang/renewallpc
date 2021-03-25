package saleson.shop.user.support;

import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;

@SuppressWarnings("serial")
public class UserSearchParam extends SearchParam {
	
	private String sendType;
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	
	private String authority;
	private String levelId;
	
	private long userId;
	private String loginId;
	private String userName;
	private String startCreated;
	private String endCreated;
	private String startLoginDate;
	private String endLoginDate;
	private String startBuyPrice;
	private String endBuyPrice;
	private String startBuyCount;
	private String endBuyCount;
	private String startLoginCount;
	private String endLoginCount;
	private String loginCount;
	private String receiveEmail="";
	private String receiveSms="";
	private String receiveDm;
	private String siteFlag;
	private String useFlag;
	private String email;
	private String businessFlag;
	
	// 제외 등급
	private String excludeAuth="";
	
	//UserDetail
	private String userType;
	private String phoneNumber;
	
	//UserBusiness
	private String pnCode;
	
	private String[] userIds;
	
	private String groupCode;
	
	
	// TargetId // 부모창에 값을 전달할 경우 selector #id
	private String targetId;

	private long sellerId;

	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getPnCode() {
		return pnCode;
	}
	public void setPnCode(String pnCode) {
		this.pnCode = pnCode;
	}
	public String getReceiveSms() {
		return receiveSms;
	}
	public void setReceiveSms(String receiveSms) {
		this.receiveSms = receiveSms;
	}
	public String getReceiveDm() {
		return receiveDm;
	}
	public void setReceiveDm(String receiveDm) {
		this.receiveDm = receiveDm;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getBusinessFlag() {
		return businessFlag;
	}
	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag;
	}
	public String getExcludeAuth() {
		return excludeAuth;
	}
	public void setExcludeAuth(String excludeAuth) {
		this.excludeAuth = excludeAuth;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public String getStartCreated() {
		return startCreated;
	}
	public void setStartCreated(String startCreated) {
		this.startCreated = startCreated;
	}
	public String getEndCreated() {
		return endCreated;
	}
	public void setEndCreated(String endCreated) {
		this.endCreated = endCreated;
	}
	public String getStartLoginDate() {
		return startLoginDate;
	}
	public void setStartLoginDate(String startLoginDate) {
		this.startLoginDate = startLoginDate;
	}
	public String getEndLoginDate() {
		return endLoginDate;
	}
	public void setEndLoginDate(String endLoginDate) {
		this.endLoginDate = endLoginDate;
	}
	public String getStartBuyPrice() {
		return startBuyPrice;
	}
	public void setStartBuyPrice(String startBuyPrice) {
		this.startBuyPrice = startBuyPrice;
	}
	public String getEndBuyPrice() {
		return endBuyPrice;
	}
	public void setEndBuyPrice(String endBuyPrice) {
		this.endBuyPrice = endBuyPrice;
	}
	public String getStartBuyCount() {
		return startBuyCount;
	}
	public void setStartBuyCount(String startBuyCount) {
		this.startBuyCount = startBuyCount;
	}
	public String getEndBuyCount() {
		return endBuyCount;
	}
	public void setEndBuyCount(String endBuyCount) {
		this.endBuyCount = endBuyCount;
	}
	public String getStartLoginCount() {
		return startLoginCount;
	}
	public void setStartLoginCount(String startLoginCount) {
		this.startLoginCount = startLoginCount;
	}
	public String getEndLoginCount() {
		return endLoginCount;
	}
	public void setEndLoginCount(String endLoginCount) {
		this.endLoginCount = endLoginCount;
	}
	public String getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(String loginCount) {
		this.loginCount = loginCount;
	}
	public String getReceiveEmail() {
		return receiveEmail;
	}
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
	public String getSiteFlag() {
		return siteFlag;
	}
	public void setSiteFlag(String siteFlag) {
		this.siteFlag = siteFlag;
	}
	
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String[] getUserIds() {
		return CommonUtils.copy(userIds);
	}
	public void setUserIds(String[] userIds) {
		this.userIds = CommonUtils.copy(userIds);
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
}
