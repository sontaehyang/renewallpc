package saleson.shop.config.domain;

import com.onlinepowers.framework.util.NumberUtils;
import org.springframework.web.multipart.MultipartFile;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;

import saleson.common.utils.CommonUtils;


public class Config {
	
	public static final int SHOP_CONFIG_ID = 1;
	public static final String SHOP_CATEGORY_GROUP_KEY = "shop";
	
	private int shopConfigId = 1; // 1번 고정!! - OP_CONFIG 테이블 SHOP_CONFIG_ID
	
	private String companyName;
	private String bossName;
	private String categoryType;
	private String businessType;
	private String telNumber;
	private String faxNumber;
	private String post;
	private String dodobuhyun;
	private String address;
	private String addressDetail;
	
	private String userName;
	private String email;
	private String companyNumber;
	private String mailOrderNumber;
	private String sourceOpen;
	private String deniedId;
	private String paymentId;
	private String paymentCard = "N";
	private String paymentBank = "N";
	private String paymentVbank = "N";
	private String paymentEscrow = "N";
	private String paymentConv = "N";
	private String paymentDlv = "N";
	private String paymentHp = "N";
	private String paymentRealtime = "N";
	private String paymentConvLimit;
	private int minimumPaymentAmount;
	private String createdDate;
	private String agreement;
	private String traderRaw;
	private String protectPolicy;
	private String deliveryInfo; 		// 결제, 배송, 주문정보
	
	private String tagOverture;
	private String tagAdwords;
	private String counselTelNumber;
	private String adminTelNumber;
	private String adminName;
	private String adminEmail;
	
	private String companyNumber1;
	private String companyNumber2;
	private String companyNumber3;
	
	private String post1;
	private String post2;
	
	private String telNumber1;
	private String telNumber2;
	private String telNumber3;
	
	private String faxNumber1;
	private String faxNumber2;
	private String faxNumber3;
	
	private String counselTelNumber1;
	private String counselTelNumber2;
	private String counselTelNumber3;
	
	private String adminTelNumber1;
	private String adminTelNumber2;
	private String adminTelNumber3;
	
	// 사이트 환경설정.
	private String shopName;
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	private String seoHeaderContents1;
	private String seoThemawordTitle;
	private String seoThemawordDescription;
	private String sourceFlag;
	
	// TOP, RIGHT 배너 설정
	private	String topBannerLink;
	private String topBannerImage;
	private MultipartFile topBannerImageFile;
	private String topBannerColor;
	private String rightBannerLink;
	private String rightBannerImage;
	private MultipartFile rightBannerImageFile;
	
	private String topBannerImageSrc;
	private String rightBannerImageSrc;

	private String loginText;
	
	private String userHtmlEdit;
	
	
	// 적립금/소비세 설정
	private String pointDefaultType;
	private double pointDefault;
	private String repeatDayStartTime;
	private String repeatDayEndTime;
	private int pointJoin;
	private int pointReview;
	private int photoPointReview;
	private String reviewDisplayType;
	private int pointFirstBuy;
	private int pointVisitDay;
	private int pointRecommend;
	private int pointRecommendPrice;
	private int pointSaveAfterDay = -1;
	private String pointSaveTime;
	private String pointSaveType;
	private int pointUseMax = -1;
	private int pointUseMin = -1;
	private int tax;
	private String taxType;	
	private String taxDisplayType;  // 세금 포함 여부 설정.
	
	// 사용자 페이지 쿠폰 사용 유무
	private String useCouponFlag;
	
	//적립금
	private String[] pointType;
	private String[] point;
	private String[] startDate;
	private String[] startTime;
	private String[] endDate;
	private String[] endTime;
	private String[] pointRepeatDay;
	
	//배송희망일
	private String deliveryHopeFlag;
	private String deliveryHopeStartDate;
	private String deliveryHopeEndDate;

	//구매확정
	private String confirmPurchaseRequestDate;
	private String confirmPurchaseDate;

	private String naverPayFlag;
	
	// 매출 기준 상태
	private String revenueSalesStatus;
	
	// 포인트 만료일
	private int pointExpirationMonth;
	private int shippingCouponExpirationMonth;
	
	// 은행 입금 만료일
	private int bankDepositDueDay;
	
	//썸네일 설정
	private String thumbnailType;
	
	//썸네일 사이즈 정보(Json형식)[2017-06-08]minae.yun
	private String thumbnailSize;
	private String[] size;
	private String[] sizeName;
	private String sizeString;
	private String sizeNameString;
	
	// 회원등록 불가능 ID 등록수정삭제 키
	private String deniedKey;

	// 포인트 사용비율
	private int pointUseRatio;

	private String categoryUpdatedDate;

	// 임시 주문정보 유지기간
    private int retentionPeriod;

    // 상품 상/하단 내용
    private String itemHeaderContent;
    private String itemFooterContent;
    private String itemHeaderContentMobile;
    private String itemFooterContentMobile;

	public String getDeniedKey() { return deniedKey; }

	public void setDeniedKey(String deniedKey) {
		this.deniedKey = deniedKey;
	}

	// CJH 2016.11.13 쿠폰 사용금액을 적립 포인트에 반영할것인가의 여부
	private boolean isPointApplyCouponDiscount = false;
	public boolean isPointApplyCouponDiscount() {
		return isPointApplyCouponDiscount;
	}

	public void setPointApplyCouponDiscount(boolean isPointApplyCouponDiscount) {
		this.isPointApplyCouponDiscount = isPointApplyCouponDiscount;
	}

	public int getBankDepositDueDay() {
		return bankDepositDueDay;
	}

	public void setBankDepositDueDay(int bankDepositDueDay) {
		this.bankDepositDueDay = bankDepositDueDay;
	}

	public int getShippingCouponExpirationMonth() {
		return shippingCouponExpirationMonth;
	}

	public void setShippingCouponExpirationMonth(int shippingCouponExpirationMonth) {
		this.shippingCouponExpirationMonth = shippingCouponExpirationMonth;
	}

	public int getPointExpirationMonth() {
		return pointExpirationMonth;
	}

	public void setPointExpirationMonth(int pointExpirationMonth) {
		this.pointExpirationMonth = pointExpirationMonth;
	}

	public String getRevenueSalesStatus() {
		return revenueSalesStatus;
	}

    public int getRetentionPeriod() {
        return retentionPeriod;
    }

    public void setRetentionPeriod(int retentionPeriod) {
        this.retentionPeriod = retentionPeriod;
    }

    public void setRevenueSalesStatus(String revenueSalesStatus) {
		this.revenueSalesStatus = revenueSalesStatus;
	}

	private String banWord; 		// 금지어.
	
	public String getNaverPayFlag() {
		return naverPayFlag;
	}

	public void setNaverPayFlag(String naverPayFlag) {
		this.naverPayFlag = naverPayFlag;
	}

	public MultipartFile getTopBannerImageFile() {
		return topBannerImageFile;
	}

	public void setTopBannerImageFile(MultipartFile topBannerImageFile) {
		this.topBannerImageFile = topBannerImageFile;
	}

	public MultipartFile getRightBannerImageFile() {
		return rightBannerImageFile;
	}

	public void setRightBannerImageFile(MultipartFile rightBannerImageFile) {
		this.rightBannerImageFile = rightBannerImageFile;
	}

	public String getRightBannerLink() {
		return rightBannerLink;
	}

	public void setRightBannerLink(String rightBannerLink) {
		this.rightBannerLink = rightBannerLink;
	}

	public String getRightBannerImage() {
		return rightBannerImage;
	}

	public void setRightBannerImage(String rightBannerImage) {
		this.rightBannerImage = rightBannerImage;
	}

	public String getTopBannerColor() {
		return topBannerColor;
	}

	public void setTopBannerColor(String topBannerColor) {
		this.topBannerColor = topBannerColor;
	}

	public int getPointFirstBuy() {
		return pointFirstBuy;
	}

	public void setPointFirstBuy(int pointFirstBuy) {
		this.pointFirstBuy = pointFirstBuy;
	}

	public int getPointVisitDay() {
		return pointVisitDay;
	}

	public void setPointVisitDay(int pointVisitDay) {
		this.pointVisitDay = pointVisitDay;
	}

	public String getUseCouponFlag() {
		return useCouponFlag;
	}

	public void setUseCouponFlag(String useCouponFlag) {
		this.useCouponFlag = useCouponFlag;
	}

	public String getDeliveryHopeFlag() {
		return deliveryHopeFlag;
	}

	public void setDeliveryHopeFlag(String deliveryHopeFlag) {
		this.deliveryHopeFlag = deliveryHopeFlag;
	}

	public String getDeliveryHopeStartDate() {
		return deliveryHopeStartDate;
	}

	public void setDeliveryHopeStartDate(String deliveryHopeStartDate) {
		this.deliveryHopeStartDate = deliveryHopeStartDate;
	}

	public String getDeliveryHopeEndDate() {
		return deliveryHopeEndDate;
	}

	public void setDeliveryHopeEndDate(String deliveryHopeEndDate) {
		this.deliveryHopeEndDate = deliveryHopeEndDate;
	}

	public String getRepeatDayStartTime() {
		return repeatDayStartTime;
	}

	public void setRepeatDayStartTime(String repeatDayStartTime) {
		this.repeatDayStartTime = repeatDayStartTime;
	}

	public String getRepeatDayEndTime() {
		return repeatDayEndTime;
	}

	public void setRepeatDayEndTime(String repeatDayEndTime) {
		this.repeatDayEndTime = repeatDayEndTime;
	}

	private String stockQuantityFlag = "N";
	
	public int getPointUseMin() {
		return pointUseMin;
	}

	public void setPointUseMin(int pointUseMin) {
		this.pointUseMin = pointUseMin;
	}

	public String getStockQuantityFlag() {
		return stockQuantityFlag;
	}

	public void setStockQuantityFlag(String stockQuantityFlag) {
		this.stockQuantityFlag = stockQuantityFlag;
	}

	public int getMinimumPaymentAmount() {
		return minimumPaymentAmount;
	}

	public void setMinimumPaymentAmount(int minimumPaymentAmount) {
		this.minimumPaymentAmount = minimumPaymentAmount;
	}

	public String getPointDefaultType() {
		return pointDefaultType;
	}

	public void setPointDefaultType(String pointDefaultType) {
		this.pointDefaultType = pointDefaultType;
	}

	public String[] getPointType() {
		return CommonUtils.copy(pointType);
	}

	public void setPointType(String[] pointType) {
		this.pointType = CommonUtils.copy(pointType);
	}

	public String[] getPoint() {
		return point;
	}

	public void setPoint(String[] point) {
		this.point = point;
	}

	public String[] getStartDate() {
		return CommonUtils.copy(startDate);
	}

	public void setStartDate(String[] startDate) {
		this.startDate = CommonUtils.copy(startDate);
	}

	public String[] getStartTime() {
		return CommonUtils.copy(startTime);
	}

	public void setStartTime(String[] startTime) {
		this.startTime = CommonUtils.copy(startTime);
	}

	public String[] getEndDate() {
		return CommonUtils.copy(endDate);
	}

	public void setEndDate(String[] endDate) {
		this.endDate = CommonUtils.copy(endDate);
	}

	public String[] getEndTime() {
		return CommonUtils.copy(endTime);
	}

	public void setEndTime(String[] endTime) {
		this.endTime = CommonUtils.copy(endTime);
	}

	public String[] getPointRepeatDay() {
		return CommonUtils.copy(pointRepeatDay);
	}

	public void setPointRepeatDay(String[] pointRepeatDay) {
		this.pointRepeatDay = CommonUtils.copy(pointRepeatDay);
	}

	public double getPointDefault() {
		return pointDefault;
	}

	public void setPointDefault(double pointDefault) {
		this.pointDefault = pointDefault;
	}

	public int getPointJoin() {
		return pointJoin;
	}

	public void setPointJoin(int pointJoin) {
		this.pointJoin = pointJoin;
	}

	public int getPointReview() {
		return pointReview;
	}

	public void setPointReview(int pointReview) {
		this.pointReview = pointReview;
	}

	public int getPointRecommend() {
		return pointRecommend;
	}

	public void setPointRecommend(int pointRecommend) {
		this.pointRecommend = pointRecommend;
	}

	public int getPointRecommendPrice() {
		return pointRecommendPrice;
	}

	public void setPointRecommendPrice(int pointRecommendPrice) {
		this.pointRecommendPrice = pointRecommendPrice;
	}

	public int getPointSaveAfterDay() {
		return pointSaveAfterDay;
	}

	public void setPointSaveAfterDay(int pointSaveAfterDay) {
		this.pointSaveAfterDay = pointSaveAfterDay;
	}

	public String getPointSaveTime() {
		return pointSaveTime;
	}

	public void setPointSaveTime(String pointSaveTime) {
		this.pointSaveTime = pointSaveTime;
	}

	public String getPointSaveType() {
		return pointSaveType;
	}

	public void setPointSaveType(String pointSaveType) {
		this.pointSaveType = pointSaveType;
	}

	public int getPointUseMax() {
		return pointUseMax;
	}

	public void setPointUseMax(int pointUseMax) {
		this.pointUseMax = pointUseMax;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public void setTopBannerImageSrc(String topBannerImageSrc) {
		this.topBannerImageSrc = topBannerImageSrc;
	}

	public String getTopBannerImageSrc() {
		return "/upload/config/" + SHOP_CONFIG_ID + "/topBanner/" + this.topBannerImage;
	}

	public String getRightBannerImageSrc() {
		return "/upload/config/" + SHOP_CONFIG_ID + "/rightBanner/" + this.rightBannerImage;
	}

	public void setRightBannerImageSrc(String rightBannerImageSrc) {
		this.rightBannerImageSrc = rightBannerImageSrc;
	}

	public String getTopBannerLink() {
		return topBannerLink;
	}
	public void setTopBannerLink(String topBannerLink) {
		this.topBannerLink = topBannerLink;
	}
	public String getTopBannerImage() {
		return topBannerImage;
	}
	public void setTopBannerImage(String topBannerImage) {
		this.topBannerImage = topBannerImage;
	}
	public int getShopConfigId() {
		return shopConfigId;
	}
	public void setShopConfigId(int shopConfigId) {
		this.shopConfigId = shopConfigId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getTelNumber() {
		this.telNumber = this.telNumber1 + '-' + this.telNumber2 + '-' + this.telNumber3;
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		String[] telNumbers = StringUtils.delimitedListToStringArray(telNumber, "-");
		
		this.telNumber1 = telNumbers.length > 0 ? telNumbers[0] : "";
		this.telNumber2 = telNumbers.length > 1 ? telNumbers[1] : "";
		this.telNumber3 = telNumbers.length > 2 ? telNumbers[2] : "";

		this.telNumber = telNumber;
	}
	
	public String getFaxNumber() {
		this.faxNumber = this.faxNumber1 + '-' + this.faxNumber2 + '-' + this.faxNumber3;
		return faxNumber;
	}
	
	public void setFaxNumber(String faxNumber) {
		String[] faxNumbers =StringUtils.delimitedListToStringArray(faxNumber, "-");
		this.faxNumber1 = faxNumbers.length > 0 ? faxNumbers[0] : "";
		this.faxNumber2 = faxNumbers.length > 1 ? faxNumbers[1] : "";
		this.faxNumber3 = faxNumbers.length > 2 ? faxNumbers[2] : "";
		this.faxNumber = faxNumber;
	}
	public String getPost() {
		
		if (StringUtils.isEmpty(this.post2)) {
			return post;
		}
		
		post = this.post1 + "-" + this.post2;
		return post;
	}
	public void setPost(String post) {
		String[] posts = StringUtils.delimitedListToStringArray(post, "-");
		
		if (posts.length == 2) {
			this.post1 = posts[0];
			this.post2 = posts[1];
		}
		this.post = post;
	}
	public String getDodobuhyun() {
		return dodobuhyun;
	}
	public void setDodobuhyun(String dodobuhyun) {
		this.dodobuhyun = dodobuhyun;
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
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyNumber() {
		this.companyNumber = this.companyNumber1 + '-' + this.companyNumber2 + '-' + this.companyNumber3;
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		String[] companyNumbers = StringUtils.delimitedListToStringArray(companyNumber, "-");
		this.companyNumber1 = companyNumbers.length > 0 ? companyNumbers[0] : "";
		this.companyNumber2 = companyNumbers.length > 1 ? companyNumbers[1] : "";
		this.companyNumber3 = companyNumbers.length > 2 ? companyNumbers[2] : "";
		this.companyNumber = companyNumber;
	}
	public String getMailOrderNumber() {
		return mailOrderNumber;
	}
	public void setMailOrderNumber(String mailOrderNumber) {
		this.mailOrderNumber = mailOrderNumber;
	}
	public String getSourceOpen() {
		return sourceOpen;
	}
	public void setSourceOpen(String sourceOpen) {
		this.sourceOpen = sourceOpen;
	}
	public String getDeniedId() {
		return deniedId;
	}
	public void setDeniedId(String deniedId) {
		this.deniedId = deniedId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentCard() {
		paymentCard = paymentCard == null ? "N" : paymentCard;
		return paymentCard;
	}
	public void setPaymentCard(String paymentCard) {
		this.paymentCard = paymentCard;
	}
	public String getPaymentBank() {
		paymentBank = paymentBank == null ? "N" : paymentBank;
		return paymentBank;
	}
	public void setPaymentBank(String paymentBank) {
		this.paymentBank = paymentBank;
	}
	public String getPaymentConv() {
		paymentConv = paymentConv == null ? "N" : paymentConv;
		return paymentConv;
	}
	public void setPaymentConv(String paymentConv) {
		this.paymentConv = paymentConv;
	}
	public String getPaymentDlv() {
		paymentDlv = paymentDlv == null ? "N" : paymentDlv;
		return paymentDlv;
	}
	public void setPaymentDlv(String paymentDlv) {
		this.paymentDlv = paymentDlv;
	}
	public String getPaymentConvLimit() {
		return paymentConvLimit;
	}
	public void setPaymentConvLimit(String paymentConvLimit) {
		this.paymentConvLimit = paymentConvLimit;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	public String getTraderRaw() {
		return traderRaw;
	}
	public void setTraderRaw(String traderRaw) {
		this.traderRaw = traderRaw;
	}
	public String getProtectPolicy() {
		return protectPolicy;
	}
	public void setProtectPolicy(String protectPolicy) {
		this.protectPolicy = protectPolicy;
	}
	
	public String getSourceFlag() {
		return sourceFlag;
	}
	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}
	public String getTagAdwords() {
		return tagAdwords;
	}
	public void setTagAdwords(String tagAdwords) {
		this.tagAdwords = tagAdwords;
	}
	public String getTagOverture() {
		return tagOverture;
	}
	public void setTagOverture(String tagOverture) {
		this.tagOverture = tagOverture;
	}
	public String getCounselTelNumber() {
		this.counselTelNumber = this.counselTelNumber1 + '-' + this.counselTelNumber2 + '-' + this.counselTelNumber3;
		return counselTelNumber;
	}
	public void setCounselTelNumber(String counselTelNumber) {
		String[] counselTelNumbers = StringUtils.delimitedListToStringArray(counselTelNumber, "-");
		this.counselTelNumber1 = counselTelNumbers.length > 0 ? counselTelNumbers[0] : "";
		this.counselTelNumber2 = counselTelNumbers.length > 1 ? counselTelNumbers[1] : "";
		this.counselTelNumber3 = counselTelNumbers.length > 2 ? counselTelNumbers[2] : "";
		this.counselTelNumber = counselTelNumber;
	}
	public String getAdminTelNumber() {
		this.adminTelNumber = this.adminTelNumber1 + '-' + this.adminTelNumber2 + '-' + this.adminTelNumber3;
		return adminTelNumber;
	}
	public void setAdminTelNumber(String adminTelNumber) {
		String[] adminTelNumbers = StringUtils.delimitedListToStringArray(adminTelNumber, "-");
		this.adminTelNumber1 = adminTelNumbers.length > 0 ? adminTelNumbers[0] : "";
		this.adminTelNumber2 = adminTelNumbers.length > 1 ? adminTelNumbers[1] : "";
		this.adminTelNumber3 = adminTelNumbers.length > 2 ? adminTelNumbers[2] : "";
		this.adminTelNumber = adminTelNumber;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	
	public void setCompanyNumber1(String companyNumber1){
		this.companyNumber1 = companyNumber1;
	}
	
	public void setCompanyNumber2(String companyNumber2){
		this.companyNumber2 = companyNumber2;
	}
	
	public void setCompanyNumber3(String companyNumber3){
		this.companyNumber3 = companyNumber3;
	}
	public String getCompanyNumber1() {
		return companyNumber1;
	}
	public String getCompanyNumber2() {
		return companyNumber2;
	}
	public String getCompanyNumber3() {
		return companyNumber3;
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
	public String getAdminTelNumber1() {
		return adminTelNumber1;
	}
	public void setAdminTelNumber1(String adminTelNumber1) {
		this.adminTelNumber1 = adminTelNumber1;
	}
	public String getAdminTelNumber2() {
		return adminTelNumber2;
	}
	public void setAdminTelNumber2(String adminTelNumber2) {
		this.adminTelNumber2 = adminTelNumber2;
	}
	public String getAdminTelNumber3() {
		return adminTelNumber3;
	}
	public void setAdminTelNumber3(String adminTelNumber3) {
		this.adminTelNumber3 = adminTelNumber3;
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
	public String getCounselTelNumber1() {
		return counselTelNumber1;
	}
	public void setCounselTelNumber1(String counselTelNumber1) {
		this.counselTelNumber1 = counselTelNumber1;
	}
	public String getCounselTelNumber2() {
		return counselTelNumber2;
	}
	public void setCounselTelNumber2(String counselTelNumber2) {
		this.counselTelNumber2 = counselTelNumber2;
	}
	public String getCounselTelNumber3() {
		return counselTelNumber3;
	}
	public void setCounselTelNumber3(String counselTelNumber3) {
		this.counselTelNumber3 = counselTelNumber3;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public String getSeoHeaderContents1() {
		return seoHeaderContents1;
	}
	public void setSeoHeaderContents1(String seoHeaderContents1) {
		this.seoHeaderContents1 = seoHeaderContents1;
	}
	public String getSeoThemawordTitle() {
		return seoThemawordTitle;
	}
	public void setSeoThemawordTitle(String seoThemawordTitle) {
		this.seoThemawordTitle = seoThemawordTitle;
	}
	public String getSeoThemawordDescription() {
		return seoThemawordDescription;
	}
	public void setSeoThemawordDescription(String seoThemawordDescription) {
		this.seoThemawordDescription = seoThemawordDescription;
	}

	public String getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(String deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public String getTaxDisplayType() {
		return taxDisplayType;
	}

	public void setTaxDisplayType(String taxDisplayType) {
		this.taxDisplayType = taxDisplayType;
	}
	
	
	
	public String getTaxDisplayTypeText() {
		return taxDisplayType.equals("2") ? MessageUtils.getMessage("M00421") : MessageUtils.getMessage("M00608");	// 세금별도 : 세금포함.
	}

	public String getLoginText() {
		return loginText;
	}

	public void setLoginText(String loginText) {
		this.loginText = loginText;
	}

	public String getUserHtmlEdit() {
		return userHtmlEdit;
	}

	public void setUserHtmlEdit(String userHtmlEdit) {
		this.userHtmlEdit = userHtmlEdit;
	}

	public String getBanWord() {
		return banWord;
	}

	public void setBanWord(String banWord) {
		this.banWord = banWord;
	}
	
	public String[] getBanWords() {
		if (this.banWord == null) {
			return new String[0];
		}
		
		return StringUtils.delimitedListToStringArray(this.banWord, ",");
	}

	public String getPaymentVbank() {
		return paymentVbank;
	}

	public void setPaymentVbank(String paymentVbank) {
		this.paymentVbank = paymentVbank;
	}

	public String getPaymentEscrow() {
		return paymentEscrow;
	}

	public void setPaymentEscrow(String paymentEscrow) {
		this.paymentEscrow = paymentEscrow;
	}

	public String getPaymentHp() {
		return paymentHp;
	}

	public void setPaymentHp(String paymentHp) {
		this.paymentHp = paymentHp;
	}

	public String getPaymentRealtime() {
		return paymentRealtime;
	}

	public void setPaymentRealtime(String paymentRealtime) {
		this.paymentRealtime = paymentRealtime;
	}

	public String getThumbnailType() {
		return thumbnailType;
	}

	public void setThumbnailType(String thumbnailType) {
		this.thumbnailType = thumbnailType;
	}

	public String getThumbnailSize() {
		return thumbnailSize;
	}

	public void setThumbnailSize(String thumbnailSize) {
		this.thumbnailSize = thumbnailSize;
	}

	public String[] getSizeName() {
		return CommonUtils.copy(sizeName);
	}

	public void setSizeName(String[] sizeName) {
		this.sizeName = CommonUtils.copy(sizeName);
	}

	public String[] getSize() {
		return CommonUtils.copy(size);
	}

	public void setSize(String[] size) {
		this.size = CommonUtils.copy(size);
	}

	public String getSizeString() {
		return sizeString;
	}

	public void setSizeString(String sizeString) {
		this.sizeString = sizeString;
	}

	public String getSizeNameString() {
		return sizeNameString;
	}

	public void setSizeNameString(String sizeNameString) {
		this.sizeNameString = sizeNameString;
	}

	public int getPhotoPointReview() {
		return photoPointReview;
	}

	public void setPhotoPointReview(int photoPointReview) {
		this.photoPointReview = photoPointReview;
	}

	public String getPointDefaultText() {
		return NumberUtils.formatNumber(getPointDefault(),"#.##");
	}

	public String getCategoryUpdatedDate() {
		return categoryUpdatedDate;
	}

	public void setCategoryUpdatedDate(String categoryUpdatedDate) {
		this.categoryUpdatedDate = categoryUpdatedDate;
	}
	public int getPointUseRatio() {
		return pointUseRatio;
	}

	public void setPointUseRatio(int pointUseRatio) {
		this.pointUseRatio = pointUseRatio;
	}

	public String getConfirmPurchaseRequestDate() {
		return confirmPurchaseRequestDate;
	}

	public void setConfirmPurchaseRequestDate(String confirmPurchaseRequestDate) {
		this.confirmPurchaseRequestDate = confirmPurchaseRequestDate;
	}

	public String getConfirmPurchaseDate() {
		return confirmPurchaseDate;
	}

	public void setConfirmPurchaseDate(String confirmPurchaseDate) {
		this.confirmPurchaseDate = confirmPurchaseDate;
	}

	public String getReviewDisplayType() {
		return reviewDisplayType;
	}

	public void setReviewDisplayType(String reviewDisplayType) {
		this.reviewDisplayType = reviewDisplayType;
	}

	public String getItemHeaderContent() {
		return itemHeaderContent;
	}

	public void setItemHeaderContent(String itemHeaderContent) {
		this.itemHeaderContent = itemHeaderContent;
	}

	public String getItemFooterContent() {
		return itemFooterContent;
	}

	public void setItemFooterContent(String itemFooterContent) {
		this.itemFooterContent = itemFooterContent;
	}

	public String getItemHeaderContentMobile() {
		return itemHeaderContentMobile;
	}

	public void setItemHeaderContentMobile(String itemHeaderContentMobile) {
		this.itemHeaderContentMobile = itemHeaderContentMobile;
	}

	public String getItemFooterContentMobile() {
		return itemFooterContentMobile;
	}

	public void setItemFooterContentMobile(String itemFooterContentMobile) {
		this.itemFooterContentMobile = itemFooterContentMobile;
	}
}
