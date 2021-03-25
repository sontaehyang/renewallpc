package saleson.shop.coupon.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.domain.SearchParam;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;
import saleson.common.utils.CommonUtils;
import saleson.shop.coupon.support.CouponTargetItem;
import saleson.shop.coupon.support.CouponTargetUser;
import saleson.shop.item.domain.ItemBase;

@SuppressWarnings("serial")
public class Coupon extends SearchParam {
	
	private int totalDownloadCount;
	private int totalUsedCount;
	public int getTotalDownloadCount() {
		return totalDownloadCount;
	}
	public void setTotalDownloadCount(int totalDownloadCount) {
		this.totalDownloadCount = totalDownloadCount;
	}
	public int getTotalUsedCount() {
		return totalUsedCount;
	}
	public void setTotalUsedCount(int totalUsedCount) {
		this.totalUsedCount = totalUsedCount;
	}

	private CouponTargetUser searchCouponTargetUser;
	public CouponTargetUser getSearchCouponTargetUser() {
		return searchCouponTargetUser;
	}
	public void setSearchCouponTargetUser(CouponTargetUser searchCouponTargetUser) {
		this.searchCouponTargetUser = searchCouponTargetUser;
	}
	
	private CouponTargetItem searchCouponTargetItem;
	public CouponTargetItem getSearchCouponTargetItem() {
		return searchCouponTargetItem;
	}
	public void setSearchCouponTargetItem(CouponTargetItem searchCouponTargetItem) {
		this.searchCouponTargetItem = searchCouponTargetItem;
	}

	private List<CouponTargetUser> couponTargetUsers;
	public List<CouponTargetUser> getCouponTargetUsers() {
		return couponTargetUsers;
	}
	public void setCouponTargetUsers(List<CouponTargetUser> couponTargetUsers) {
		this.couponTargetUsers = couponTargetUsers;
	}

	private String couponTargetUser;
	public String getCouponTargetUser() {
		return couponTargetUser;
	}
	public void setCouponTargetUser(String couponTargetUser) {
		
		if (StringUtils.isEmpty(couponTargetUser) == false) {
			couponTargetUsers = (List<CouponTargetUser>) JsonViewUtils.jsonToObject(couponTargetUser, new TypeReference<List<CouponTargetUser>>(){});
		}
		
		this.couponTargetUser = couponTargetUser;
	}
	public String getCouponTargetUsersToJsonString() {
		
		if (couponTargetUsers == null ||  !"2".equals(couponTargetUserType)) {
			return "";
		}
		
		return JsonViewUtils.objectToJson(couponTargetUsers);
	}

	private List<CouponTargetItem> couponTargetItems;
	public List<CouponTargetItem> getCouponTargetItems() {
		return couponTargetItems;
	}
	public void setCouponTargetItems(List<CouponTargetItem> couponTargetItems) {
		this.couponTargetItems = couponTargetItems;
	}
	
	public String getCouponTargetItemsToJsonString() {
		
		if (couponTargetItems == null || "1".equals(couponTargetItemType)) {
			return "";
		}
		
		return JsonViewUtils.objectToJson(couponTargetItems);
	}
	
	private int couponId;
	
	private String[] selectCouponTypes;
	public String[] getSelectCouponTypes() {

		if (selectCouponTypes == null) {
			return new String[] {"WEB", "MOBILE", "APP"};
		}

		return CommonUtils.copy(selectCouponTypes);
	}
	public void setSelectCouponTypes(String[] selectCouponTypes) {
		this.selectCouponTypes = CommonUtils.copy(selectCouponTypes);
	}
	
	public String getCouponTypeLabel() {
		if (StringUtils.isEmpty(couponType) == false) {
			if (couponType.length() > 2) {
				//couponType = couponType.substring(2, couponType.length() - 2);
				String[] temp = StringUtils.delimitedListToStringArray(couponType, "||");
				
				String r = ""; 
				for (int i = 0; i < temp.length; i++) {
					if ("WEB".equals(temp[i])) {
						r += (i == 0 ? "" : ", ") + "웹";
					} else if ("MOBILE".equals(temp[i])) {
						r += (i == 0 ? "" : ", ") + "모바일";
					} else if ("APP".equals(temp[i])) {
						r += (i == 0 ? "" : ", ") + "앱";
					}
				}
				
				return r;
			}
		}
		
		return "";
		
	}
	
	public String getSelectedCouponTypes() {
		
		String s = "";
		
		if (getSelectCouponTypes() == null) {
			return "";
		}
		
		for (int i = 0; i < getSelectCouponTypes().length; i++) {
			s += ((i == 0) ? "" : "||") + getSelectCouponTypes()[i]; 
		}
		
		return "||" + s + "||";
	}
	
	private String[] selectCouponTargetUserLevels;
	public String[] getSelectCouponTargetUserLevels() {
		return CommonUtils.copy(selectCouponTargetUserLevels);
	}

	public void setSelectCouponTargetUserLevels(String[] selectCouponTargetUserLevels) {
		this.selectCouponTargetUserLevels = CommonUtils.copy(selectCouponTargetUserLevels);
	}

	public String getSelectedCouponTargetUserLevels() {
		
		String s = "";
		
		if (getSelectCouponTargetUserLevels() == null || !"3".equals(couponTargetUserType)) {
			return "";
		}
		
		for (int i = 0; i < getSelectCouponTargetUserLevels().length; i++) {
			s += ((i == 0) ? "" : "||") + getSelectCouponTargetUserLevels()[i]; 
		}
		
		return "||" + s + "||";
		
	}
	
	public List<String> getSelectCouponTargetUserLevelArray() {
		List<String> array = new ArrayList<>();
		
		if (getSelectCouponTargetUserLevels() == null) {
			return array;
		}
		
		for(String s : getSelectCouponTargetUserLevels()) {
			array.add(s);
		}
		
		return array;
	}
	
	private String couponType;
	private String couponName;
	private String couponComment;
	
	
	// 다운로드 가능기간
	private String couponIssueType = "0"; // 0 : 제한 없음, 1 : 시작일 종료일 지정
	private String couponIssueStartDate;
	private String couponIssueEndDate;
	
	// 쿠폰 사용 가능 기간
	private String couponApplyType = "0"; // 0 : 제한 없음, 1 : 시작일 종료일 지정, 2 : 기간 설정
	private int couponApplyDay;
	private String couponApplyStartDate;
	private String couponApplyEndDate;
	
	// 발급 시점
	private String couponTargetTimeType = "1"; // 1:일반, 2:회원가입, 3:생일, 4:상품 구매 후 발행, 5:첫구매
	// 발급 대상 - 회원
	private String couponTargetUserType = "1"; // 1 : 전체 회원, 2 : 선택 회원, 3 : 회원 등급
	private String couponTargetUserLevel;

	// 발급 대상 - 상품
	private String couponTargetItemType = "1"; // 1 : 전체 상품, 2 : 특정 상품
	private String couponTargetItem = "";
	
	private int couponDownloadLimit = -1;
	private int couponDownloadUserLimit = -1;
	private String couponMulitpleDownloadFlag = "N";
	public String getCouponMulitpleDownloadFlag() {
		return couponMulitpleDownloadFlag;
	}
	public void setCouponMulitpleDownloadFlag(String couponMulitpleDownloadFlag) {
		this.couponMulitpleDownloadFlag = couponMulitpleDownloadFlag;
	}
	
	public String getCouponTargetTimeTypeLabel() {
		if ("1".equals(couponTargetTimeType)) {
			return "일반";
		} else if ("2".equals(couponTargetTimeType)) {
			return "회원가입";
		} else if ("3".equals(couponTargetTimeType)) {
			return "생일";
		} else if ("4".equals(couponTargetTimeType)) {
			return "상품 구매 후 발행";
		} else if ("5".equals(couponTargetTimeType)) {
			return "첫구매";
		}
		
		return "-";
	}
	
	public String getCouponTargetUserTypeLabel() {
		if ("1".equals(couponTargetUserType)) {
			return "전체 회원";
		} else if ("2".equals(couponTargetUserType)) {
			return "회원 선택";
		} else if ("3".equals(couponTargetUserType)) {
			return "회원 등급별";
		}
		
		return "-";
	}
	
	public String getCouponTargetItemTypeLabel() {
		if ("1".equals(couponTargetItemType)) {
			return "전체 상품";
		} else if ("2".equals(couponTargetItemType)) {
			return "상품 선택";
		}
		
		return "-";
	}
	
	private int couponPayRestriction = -1; // 사용가능 상품 판매가 (개당)
	private String couponConcurrently = "1"; // 1 : 1개 수량만 할인, 2 : 구매 수량만큼 할인
	
	private String couponPayType; // 1 : 원, 2 : %
	private String couponPay;

	private int discountPrice;
	public int getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	private int couponDiscountLimitPrice = -1; // %할인 최대 할인금액
	
	private String couponFlag = "Y";
	private String couponOfflineFlag = "N"; //오프라인 쿠폰 발행 여부
	private String couponBirthday;
		
	private String dataStatusCode = "0";
	private String createdDate;
	private String updatedDate;
	
	private String updateUserName;

	private String directInputFlag = "N";
	private String directInputValue;

	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	// 검색된 아이템 추가
	private List<ItemSearchAddParam> itemSearchAdd;
	public List<ItemSearchAddParam> getItemSearchAdd() {
		return itemSearchAdd;
	}
	public void setItemSearchAdd(List<ItemSearchAddParam> itemSearchAdd) {
		this.itemSearchAdd = itemSearchAdd;
	}

	// 사용가능 아이템 정보
	private List<ItemBase> items;
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		
		if (StringUtils.isEmpty(couponType) == false) {
			if (couponType.length() > 4) {
				couponType = couponType.substring(2, couponType.length() - 2);
				this.selectCouponTypes = StringUtils.delimitedListToStringArray(couponType, "||");
			}
		}
		
		this.couponType = couponType;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getCouponComment() {
		return couponComment;
	}
	public void setCouponComment(String couponComment) {
		this.couponComment = couponComment;
	}
	public String getCouponIssueType() {
		return couponIssueType;
	}
	public void setCouponIssueType(String couponIssueType) {
		
		if (!"1".equals(couponIssueType)) {
			setCouponIssueStartDate("");
			setCouponIssueEndDate("");
		}
		
		this.couponIssueType = couponIssueType;
	}
	public String getCouponIssueStartDate() {
		return couponIssueStartDate;
	}
	public void setCouponIssueStartDate(String couponIssueStartDate) {
		this.couponIssueStartDate = couponIssueStartDate;
	}
	public String getCouponIssueEndDate() {
		return couponIssueEndDate;
	}
	public void setCouponIssueEndDate(String couponIssueEndDate) {
		this.couponIssueEndDate = couponIssueEndDate;
	}
	public String getCouponApplyType() {
		return couponApplyType;
	}
	public void setCouponApplyType(String couponApplyType) {
		
		if (!"1".equals(couponApplyType)) {
			setCouponApplyStartDate("");
			setCouponApplyEndDate("");
		}
		
		if (!"2".equals(couponApplyType)) {
			setCouponApplyDay(0);
		}
		
		this.couponApplyType = couponApplyType;
	}
	public int getCouponApplyDay() {
		return couponApplyDay;
	}
	public void setCouponApplyDay(int couponApplyDay) {
		this.couponApplyDay = couponApplyDay;
	}
	public String getCouponApplyStartDate() {
		return couponApplyStartDate;
	}
	public void setCouponApplyStartDate(String couponApplyStartDate) {
		this.couponApplyStartDate = couponApplyStartDate;
	}
	public String getCouponApplyEndDate() {
		return couponApplyEndDate;
	}
	public void setCouponApplyEndDate(String couponApplyEndDate) {
		this.couponApplyEndDate = couponApplyEndDate;
	}
	public String getCouponTargetTimeType() {
		return couponTargetTimeType;
	}
	public void setCouponTargetTimeType(String couponTargetTimeType) {
		this.couponTargetTimeType = couponTargetTimeType;
	}
	public String getCouponTargetUserType() {
		return couponTargetUserType;
	}
	public void setCouponTargetUserType(String couponTargetUserType) {
		this.couponTargetUserType = couponTargetUserType;
	}
	public int getCouponPayRestriction() {
		return couponPayRestriction;
	}
	public void setCouponPayRestriction(int couponPayRestriction) {
		this.couponPayRestriction = couponPayRestriction;
	}
	public String getCouponConcurrently() {
		return couponConcurrently;
	}
	public void setCouponConcurrently(String couponConcurrently) {
		this.couponConcurrently = couponConcurrently;
	}
	public String getCouponPayType() {
		return couponPayType;
	}
	public void setCouponPayType(String couponPayType) {
		this.couponPayType = couponPayType;
	}
	public String getCouponPay() {
		return couponPay;
	}
	public void setCouponPay(String couponPay) {
		this.couponPay = couponPay;
	}
	public int getCouponDiscountLimitPrice() {
		return couponDiscountLimitPrice;
	}
	public void setCouponDiscountLimitPrice(int couponDiscountLimitPrice) {
		this.couponDiscountLimitPrice = couponDiscountLimitPrice;
	}
	public String getCouponFlag() {
		return couponFlag;
	}
	public void setCouponFlag(String couponFlag) {
		this.couponFlag = couponFlag;
	}
	public String getCouponOfflineFlag() {
		return couponOfflineFlag;
	}
	public void setCouponOfflineFlag(String couponOfflineFlag) {
		this.couponOfflineFlag = couponOfflineFlag;
	}
	public String getCouponBirthday() {
		return couponBirthday;
	}
	public void setCouponBirthday(String couponBirthday) {
		this.couponBirthday = couponBirthday;
	}
	public String getCouponTargetItemType() {
		return couponTargetItemType;
	}
	public void setCouponTargetItemType(String couponTargetItemType) {
		this.couponTargetItemType = couponTargetItemType;
	}
	public String getCouponTargetItem() {
		return couponTargetItem;
	}
	public void setCouponTargetItem(String couponTargetItem) {
		
		if (StringUtils.isEmpty(couponTargetItem) == false) {
			couponTargetItems = (List<CouponTargetItem>) JsonViewUtils.jsonToObject(couponTargetItem, new TypeReference<List<CouponTargetItem>>(){});
		}
		
		this.couponTargetItem = couponTargetItem;
	}
	public String getDataStatusCode() {
		return dataStatusCode;
	}
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
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
	public List<ItemBase> getItems() {
		return items;
	}
	public void setItems(List<ItemBase> items) {
		this.items = items;
	}
	public String getCouponTargetUserLevel() {
		return couponTargetUserLevel;
	}
	public void setCouponTargetUserLevel(String couponTargetUserLevel) {
		
		if (StringUtils.isEmpty(couponTargetUserLevel) == false) {
			if (couponTargetUserLevel.length() > 4) {
				couponTargetUserLevel = couponTargetUserLevel.substring(2, couponTargetUserLevel.length() - 2);
				this.selectCouponTargetUserLevels = StringUtils.delimitedListToStringArray(couponTargetUserLevel, "||");
			}
		}
		
		this.couponTargetUserLevel = couponTargetUserLevel;
	}
	public int getCouponDownloadLimit() {
		return couponDownloadLimit;
	}
	public void setCouponDownloadLimit(int couponDownloadLimit) {
		this.couponDownloadLimit = couponDownloadLimit;
	}
	public int getCouponDownloadUserLimit() {
		return couponDownloadUserLimit;
	}
	public void setCouponDownloadUserLimit(int couponDownloadUserLimit) {
		this.couponDownloadUserLimit = couponDownloadUserLimit;
	}

	public String getDirectInputFlag() {
		return directInputFlag;
	}

	public void setDirectInputFlag(String directInputFlag) {
		this.directInputFlag = directInputFlag;
	}

	public String getDirectInputValue() {
		return directInputValue;
	}

	public void setDirectInputValue(String directInputValue) {
		this.directInputValue = directInputValue;
	}
}
