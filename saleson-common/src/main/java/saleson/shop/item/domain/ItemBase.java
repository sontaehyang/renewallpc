package saleson.shop.item.domain;

import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.utils.EventViewUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.userlevel.domain.UserLevel;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ONPA16072801
 *
 */
public class ItemBase {
	private static final Logger log = LoggerFactory.getLogger(ItemBase.class);

	private int itemId;
	private String itemUserCode;
	private String itemName;
	private String size = "XL";
	private String nonmemberOrderType = "1";
	private String taxType = "1";
	private String itemPrice;
	private int salePrice;
	
	private String soldOut;				// 0: 정상, 1:품절.
	private String itemSoldOutFlag;		// 상품 품절 여부 (쿼리 조회 시 결정 - 옵션 포함)
	private String itemOptionSoldOutFlag;	// 옵션 품절 여부 (쿼리 조회 시 결정)
	private String dataStatusCode;
	
	private String displayFlag = "N";	// 공개유무
	private String salePriceNonmemberFlag = "N";
	private int salePriceNonmember;
	
	
	private String sellerDiscountFlag = "N";
	private String sellerDiscountType = "";
	private int sellerDiscountAmount;
	
	private String spotFlag = "N";
	private String spotDateType;	// 스팟 기간 구분 (1: 시점, 2:기간) (시점 ex: 20190101 ~ 20190131 기간 동안 특정 시간만 노출, 기간 ex: 20190101 07:00 ~ 20190131 08:00 시작일과 종료일)
	private String spotType;
	private String spotApplyGroup = "All";
	private String spotStartDate;
	private String spotEndDate;
	private String spotStartTime = "000000";
	private String spotEndTime = "235959";
	private String spotWeekDay = "0123456";
	private int spotDiscountAmount;
	
	private String sellerPointFlag = "N"; 	 // 판매자 부담 포인트 지급 사용 여부
	
	private String itemSummary;
	private String itemImage; 		// 상품 대표이미지 (상세이미지의 첫번째 이미지가 대표이미지가 됨.)
	private String itemOptionFlag = "N";
	private String itemOptionType;
	private String seoIndexFlag = "Y";	// 
	
	private String noFollow;
	
	private String itemType = "1";
	private float userLevelDiscountRate; // 회원 등급별 할인
	public float getUserLevelDiscountRate() {
		
		// 실시간으로 조회되는 할인율 정보가 없으면 Session에 셋팅된 값으로 처리함
		// 주의할 점은 결제 데이터에는 해당 부분이 Session으로 처리되면 안됨
		if (userLevelDiscountRate == 0) {
			if (UserUtils.isUserLogin()) {
				return UserUtils.getUserDetail().getUserLevelDiscountRate();
			}
		}
		
		return userLevelDiscountRate;
	}

	public void setUserLevelDiscountRate(float userLevelDiscountRate) {
		this.userLevelDiscountRate = userLevelDiscountRate;
	}

	public ItemBase() {}
	
	public ItemBase(int itemId, String itemUserCode, String itemImage) {
		this.itemId = itemId;
		this.itemUserCode = itemUserCode;
		this.itemImage = itemImage;
	}
	
	public String getSpotFlag() {
		return spotFlag;
	}

	public void setSpotFlag(String spotFlag) {
		this.spotFlag = spotFlag;
	}

	public String getSpotDateType() {
		return spotDateType;
	}

	public void setSpotDateType(String spotDateType) {
		this.spotDateType = spotDateType;
	}

	public String getSpotType() {
		return spotType;
	}

	public void setSpotType(String spotType) {
		this.spotType = spotType;
	}

	public String getSellerDiscountFlag() {
		return sellerDiscountFlag;
	}

	public void setSellerDiscountFlag(String sellerDiscountFlag) {
		this.sellerDiscountFlag = sellerDiscountFlag;
	}

	public String getSellerDiscountType() {
		return sellerDiscountType;
	}

	public void setSellerDiscountType(String sellerDiscountType) {
		this.sellerDiscountType = sellerDiscountType;
	}

	public int getSellerDiscountAmount() {
		return sellerDiscountAmount;
	}

	public void setSellerDiscountAmount(int sellerDiscountAmount) {
		this.sellerDiscountAmount = sellerDiscountAmount;
	}

	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getNonmemberOrderType() {
		return nonmemberOrderType;
	}
	public void setNonmemberOrderType(String nonmemberOrderType) {
		this.nonmemberOrderType = nonmemberOrderType;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getSoldOut() {
		return soldOut;
	}

	public void setSoldOut(String soldOut) {
		this.soldOut = soldOut;
	}
	
	public String getItemSoldOutFlag() {
		return itemSoldOutFlag;
	}

	public void setItemSoldOutFlag(String itemSoldOutFlag) {
		this.itemSoldOutFlag = itemSoldOutFlag;
	}
	
	public String getItemOptionSoldOutFlag() {
		return itemOptionSoldOutFlag;
	}

	public void setItemOptionSoldOutFlag(String itemOptionSoldOutFlag) {
		this.itemOptionSoldOutFlag = itemOptionSoldOutFlag;
	}

	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	
	public String getSalePriceNonmemberFlag() {
		return salePriceNonmemberFlag;
	}

	public void setSalePriceNonmemberFlag(String salePriceNonmemberFlag) {
		this.salePriceNonmemberFlag = salePriceNonmemberFlag;
	}

	public int getSalePriceNonmember() {
		return salePriceNonmember;
	}
	public void setSalePriceNonmember(int salePriceNonmember) {
		this.salePriceNonmember = salePriceNonmember;
	}
	
	public String getItemSummary() {
		return itemSummary;
	}

	public void setItemSummary(String itemSummary) {
		this.itemSummary = itemSummary;
	}

	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public String getItemOptionFlag() {
		return itemOptionFlag;
	}
	public void setItemOptionFlag(String itemOptionFlag) {
		this.itemOptionFlag = itemOptionFlag;
	}
	

	public String getItemOptionType() {
		return itemOptionType;
	}

	public void setItemOptionType(String itemOptionType) {
		this.itemOptionType = itemOptionType;
	}

	public String getSeoIndexFlag() {
		return seoIndexFlag;
	}

	public void setSeoIndexFlag(String seoIndexFlag) {
		this.seoIndexFlag = seoIndexFlag;
	}

	public int getSpotDiscountAmount() {
		return spotDiscountAmount;
	}

	public void setSpotDiscountAmount(int spotDiscountAmount) {
		this.spotDiscountAmount = spotDiscountAmount;
	}
	
	public String getSellerPointFlag() {
		return sellerPointFlag;
	}

	public void setSellerPointFlag(String sellerPointFlag) {
		this.sellerPointFlag = sellerPointFlag;
	}

	public String getSpotStartDate() {
		return spotStartDate;
	}

	public void setSpotStartDate(String spotStartDate) {
		this.spotStartDate = spotStartDate;
	}

	public String getSpotEndDate() {
		return spotEndDate;
	}

	public void setSpotEndDate(String spotEndDate) {
		this.spotEndDate = spotEndDate;
	}

	public String getSpotStartTime() {
		return spotStartTime;
	}

	public void setSpotStartTime(String spotStartTime) {
		this.spotStartTime = spotStartTime;
	}

	public String getSpotEndTime() {
		return spotEndTime;
	}

	public void setSpotEndTime(String spotEndTime) {
		this.spotEndTime = spotEndTime;
	}
	
	public String getSpotStartHour() {
		if (this.spotStartTime == null || this.spotStartTime.length() < 6) {
			return "";
		}
		return this.spotStartTime.substring(0, 2);
	}
	public String getSpotStartMinute() {
		if (this.spotStartTime == null || this.spotStartTime.length() < 6) {
			return "";
		}
		return this.spotStartTime.substring(2, 4);
	}
	public String getSpotEndHour() {
		if (this.spotEndTime == null || this.spotEndTime.length() < 6) {
			return "";
		}
		return this.spotEndTime.substring(0, 2);
	}
	public String getSpotEndMinute() {
		if (this.spotEndTime == null || this.spotEndTime.length() < 6) {
			return "";
		}
		return this.spotEndTime.substring(2, 4);
	}
	public String getSpotWeekDay() {
		return spotWeekDay;
	}

	public void setSpotWeekDay(String spotWeekDay) {
		this.spotWeekDay = spotWeekDay;
	}

	public String getSpotApplyGroup() {
		return spotApplyGroup;
	}

	public void setSpotApplyGroup(String spotApplyGroup) {
		this.spotApplyGroup = spotApplyGroup;
	}

	public List<Code> getSpotWeekDayList() {
		return ShopUtils.getDayOfWeekList(this.spotWeekDay);
	}
	
	public int getSpotStartDDay() {
		int daysDiff = 9999;

		if (this.spotStartDate != null && !StringUtils.isEmpty(this.spotStartDate.trim())) {
			try {
				daysDiff = DateUtils.getDaysDiff(DateUtils.getToday(), this.spotStartDate);
			} catch (Exception e) {
				log.error("ItemBase -> getSpotStartDDay Error : {}", e.getMessage());
			}
		}

		return daysDiff;
	}
	
	
	public int getSpotEndDDay() {
		int daysDiff = 9999;

		if (this.spotEndDate != null && !StringUtils.isEmpty(this.spotEndDate.trim())) {
			try {
				daysDiff = DateUtils.getDaysDiff(DateUtils.getToday(), this.spotEndDate);
			} catch (Exception e) {
				log.error("ItemBase -> getSpotEndDDay Error : {}", e.getMessage());
			}
		}

		return daysDiff;
	}

	/**
	 * 목록 이미지 경로
	 * @return
	 */
	public String getImageSrc() {
		return ShopUtils.listImage(this.itemUserCode, this.itemImage);
	}
	
	/**
	 * 상세 이미지 경로
	 * @author minae.yun [2017-05-30]
	 * @return
	 *//*
	public String getImageFileSrc() {
		return ShopUtils.loadImage(this.itemUserCode, this.itemId, this.itemImage, this.size);
	}
	*/
	
	/**
	 * 카탈로그 이미지 경로
	 * @return
	 */
	public String getCatalogImageSrc() {
		return ShopUtils.catalogImage(this.itemUserCode, this.itemImage);
	}
	
	/**
	 * 판매자 할인금액 - 판매가에서 할인금액을 계산
	 * @return
	 */
	public int getSellerDiscountPrice() {
		int sellerDiscountPrice = 0;
		if ("Y".equals(this.sellerDiscountFlag)) {
			
			// 할인구분 (1:금액, 2:비율)
			if ("2".equals(this.sellerDiscountType)) {
				sellerDiscountPrice = (int) ((double) getSalePrice() * ((double) this.sellerDiscountAmount / 100));
				
				// 할인금액 원단위 버림
				sellerDiscountPrice = new BigDecimal(sellerDiscountPrice).divide(new BigDecimal(10)).setScale(0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(10)).intValue();
			} else {
				sellerDiscountPrice = this.sellerDiscountAmount;
			}
		}
		
		return sellerDiscountPrice;
	}
	
	/**
	 * 할인가에서 스팟상품 할인가를 더한다 - 스팟관리 화면용
	 * @return
	 */
	public int getMinusSpotDiscount() {
		
		int spotDiscountAmount = 0;
		if ("Y".equals(this.getSpotFlag())) {
			
			String dayOfWeek = Integer.toString(DateUtils.getDayOfWeek());
			int today = Integer.parseInt(DateUtils.getToday());
			if (StringUtils.isEmpty(this.spotStartDate) 
					|| StringUtils.isEmpty(this.spotEndDate) 
					|| StringUtils.isEmpty(this.spotStartTime) 
					|| StringUtils.isEmpty(this.spotEndTime) 
					|| StringUtils.isEmpty(this.spotWeekDay) 
					|| this.spotWeekDay.indexOf(dayOfWeek) == -1
					
					) {
				
				return getExceptSpotDiscount();
			}
			
			int endDate = Integer.parseInt(this.spotEndDate);
			if (today <= endDate) {
				spotDiscountAmount = this.spotDiscountAmount;
			}
		}
		
		return getExceptSpotDiscount() - spotDiscountAmount;
	}
	
	/**
	 * 스팟 제외 상품판매가
	 * @return
	 */
	public int getExceptSpotDiscount() {
		return getSalePrice() - getSellerDiscountPrice();
	}
	
	/**
	 * 회원 할인가 제외 금액
	 * @return
	 */
	public int getExceptUserDiscountPresentPrice() {
		return getExceptSpotDiscount() - (isSpotItem() ? this.spotDiscountAmount : 0);
	}

	/**
	 * 기본 상품 판매가 (상품 판매가 - 즉시할인 - 스팟할인)
	 * @return
	 */
	public int getDefaultPrice() {
		int defaultPrice = this.salePrice;

		if (isSpotItem()) {
			defaultPrice = defaultPrice - this.spotDiscountAmount;
		}

		return defaultPrice - getSellerDiscountPrice();
	}

	/**
	 * 실제 구매자 판매금액 - 회원할인 정책 적용
	 * @return
	 */
	public int getPresentPrice() {

		return getDefaultPrice() - getUserLevelDiscountAmount();
	}

	/**
	 * 회원 등급 할인 금액
	 * @return
	 */
	public int getUserLevelDiscountAmount() {
		if (!UserUtils.isUserLogin()) {
			return 0;
		}

		UserLevel userLevel = UserUtils.getUserDetail().getUserlevel();
		if (userLevel == null) {
			return 0;
		}

		return (int) ((double) getDefaultPrice() * ((double) getUserLevelDiscountRate() / 100));
	}
	
	/**
	 * 전체 할인 금액 - 회원 할인은 재외
	 * @return
	 */
	public int getTotalDiscountAmount() {
		int discountAmount = getSellerDiscountPrice();
		
		if (isSpotItem()) {
			discountAmount += this.spotDiscountAmount;
		}
		
		return discountAmount;
		
	}

	/**
	 * 화면상에 뿌려주는 정가
	 * @return
	 */
	public int getListPrice() {
		try {
			if (getTotalDiscountAmount() > 0) {
				return getSalePrice();
			}

		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return 0;
		}
				
		return 0;
	}
	
	/**
	 * 할인율 (정가 대비 - 회원 할인가를 제외한 할인 판매가)
	 * @return
	 */
	public int getDiscountRate() {
		try {
			if (getListPrice() > getExceptUserDiscountPresentPrice()) {
				int rate = Math.round(100 - (((float) getExceptUserDiscountPresentPrice() / (float) getListPrice()) * 100));
				return rate == 100 ? 99 : rate;
			}
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return 0;
		}

		return 0;
	}

	/**
	 * 비회원 주문 설정에 따른 링크 설정
	 * NONMEMBER_ORDER_TYPE == '3' 인 경우 상세 페이지 접속 불가!
	 * @return
	 */
	public String getLink() {
		if (!UserUtils.isUserLogin() && this.nonmemberOrderType.equals("3")) {
			return "javascript:Shop.loginToItemDetailPage('" + this.itemUserCode + "')";
		}
		return ShopUtils.getMobilePrefixByPage() + "/products/view/" + this.itemUserCode;
	}

	public String getNoFollow() {
		if ("Y".equalsIgnoreCase(this.seoIndexFlag)) {
			return " rel=\"nofollow\"";
		}
		return "";
	}
	
	
	// 스팟 진행 상품 여부 
	public boolean isSpotItem() {
		
		if (!"Y".equals(this.getSpotFlag())) {
			return false;
		}

		// [2020-01-08 KSH] 스팟 그룹이 설정되어있으면 -> 비회원, 그룹 불일치 회원은 스팟 혜택 없음
		if (!("All".equals(this.getSpotApplyGroup()) || StringUtils.isEmpty(this.getSpotApplyGroup())) &&
			!(
				UserUtils.getUserDetail() != null
				&& StringUtils.isNotEmpty(UserUtils.getUserDetail().getGroupCode())
				&& UserUtils.getUserDetail().getGroupCode().equals(this.getSpotApplyGroup())
			)
		) {
			return false;
		}

		String dayOfWeek = Integer.toString(DateUtils.getDayOfWeek());

		if (StringUtils.isEmpty(this.spotStartDate)
				|| StringUtils.isEmpty(this.spotEndDate)
				|| StringUtils.isEmpty(this.spotStartTime)
				|| StringUtils.isEmpty(this.spotEndTime)
				|| StringUtils.isEmpty(this.spotWeekDay)
				|| this.spotWeekDay.indexOf(dayOfWeek) == -1

		) {
			return false;
		}

		long startTimeStamp = StringUtils.string2long(this.spotStartDate + this.spotStartTime);
		long endTimeStamp = StringUtils.string2long(this.spotEndDate + this.spotEndTime);
		long currentTimeStamp = StringUtils.string2long(DateUtils.getCurrentTimeStamp(false));

		long startDate = StringUtils.string2long(this.spotStartDate);
		long endDate = StringUtils.string2long(this.spotEndDate);
		long startTime = StringUtils.string2long(this.spotStartTime);
		long endTime = StringUtils.string2long(this.spotEndTime);
		long currentDate = StringUtils.string2long(DateUtils.getCurrentTimeStamp(false).substring(0, 8));
		long currentTime = StringUtils.string2long(DateUtils.getCurrentTimeStamp(false).substring(8, 14));

		// [2020-11-25 KSH] 스팟 기간 구분별 로직 분기
		if ("1".equals(this.spotDateType) && startDate <= currentDate && endDate >= currentDate && startTime <= currentTime && endTime >= currentTime) {
			return true;
		} else if ("2".equals(this.spotDateType) && startTimeStamp <= currentTimeStamp && endTimeStamp >= currentTimeStamp) {
			return true;
		}

		return false;
	}

	public String getSpotFormatDate() {
		String spotFormatDate = "";

		if (this.isSpotItem()) {
			spotFormatDate = DateUtils.convertDate(this.spotEndDate + this.spotEndTime, "", "MM/dd/yyyy HH:mm:ss", "");
		}

		return spotFormatDate;
	}
	
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getDataStatusCode() {
		return dataStatusCode;
	}

	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public String getEventViewUrl() {
		return EventViewUtils.getUrl(EventCodeType.SHARE, getItemUserCode());
	}

	public String getEpItemUrl(String channel) {
		return EventViewUtils.getEpItemUrl(channel, getItemUserCode());
	}
}
