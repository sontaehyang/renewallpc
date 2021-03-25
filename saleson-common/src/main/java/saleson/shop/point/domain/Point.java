package saleson.shop.point.domain;

import org.springframework.web.multipart.MultipartFile;
import saleson.common.utils.CommonUtils;

public class Point {
	
	private boolean isInsert = false;
	private boolean isUpdate = false;
	public boolean isInsert() {
		return isInsert;
	}
	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}
	public boolean isUpdate() {
		return isUpdate;
	}
	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	private int pointId;
	private String pointType;
	private String savedType;
	private String savedYear;
	private String savedMonth;
	private int savedPoint;
	private int point;
	private String reason;
	private long userId;
	private String loginId;
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	private long managerUserId;
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private String expirationDate;
	private String createdDate;
	private int expirationPoint;
	public int getExpirationPoint() {
		return expirationPoint;
	}
	public void setExpirationPoint(int expirationPoint) {
		this.expirationPoint = expirationPoint;
	}

	private String target;
	
	private int[] userIds;
	
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public int[] getGetUserIds() {
		return CommonUtils.copy(getUserIds);
	}
	public void setGetUserIds(int[] getUserIds) {
		this.getUserIds = CommonUtils.copy(getUserIds);
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	private MultipartFile file;		// 엑셀 업로드로 포인트 지급시.
	private int[] getUserIds;

	private String userName;
	private String phoneNumber;
	private int totalPoint;
	
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public String getSavedType() {
		return savedType;
	}
	public void setSavedType(String savedType) {
		this.savedType = savedType;
	}
	public String getSavedYear() {
		return savedYear;
	}
	public void setSavedYear(String savedYear) {
		this.savedYear = savedYear;
	}
	public String getSavedMonth() {
		return savedMonth;
	}
	public void setSavedMonth(String savedMonth) {
		this.savedMonth = savedMonth;
	}
	public int getSavedPoint() {
		return savedPoint;
	}
	public void setSavedPoint(int savedPoint) {
		this.savedPoint = savedPoint;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(long managerUserId) {
		this.managerUserId = managerUserId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public int[] getUserIds() {
		return CommonUtils.copy(userIds);
	}
	public void setUserIds(int[] userIds) {
		this.userIds = CommonUtils.copy(userIds);
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
}
