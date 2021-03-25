package saleson.batch.job;

import com.onlinepowers.framework.common.ServiceType;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.notification.NotificationService;
import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.notification.message.SmsMessage;
import com.onlinepowers.framework.notification.sms.SmsService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.enumeration.OrderLogType;
import saleson.common.enumeration.kakao.alimtalk.TemplateInspStatus;
import saleson.common.exception.KakaoAlimTalkException;
import saleson.common.notification.ApplicationInfoService;
import saleson.common.notification.UnifiedMessagingService;
import saleson.common.utils.LocalDateUtils;
import saleson.common.web.Paging;
import saleson.erp.domain.ErpMapper;
import saleson.erp.domain.OrderLineStatus;
import saleson.erp.service.ErpService;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.model.kakao.AlimTalkTemplate;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.shop.campaign.CampaignService;
import saleson.shop.campaign.statistics.CampaignStatisticsService;
import saleson.shop.categories.CategoriesMapper;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupMapper;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.config.ConfigMapper;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.CouponMapper;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.domain.CouponUser;
import saleson.shop.coupon.regular.CouponRegularMapperBatch;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.deliverycompany.DeliveryCompanyService;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.group.GroupService;
import saleson.shop.group.domain.Group;
import saleson.shop.item.ItemService;
import saleson.shop.order.OrderBatchService;
import saleson.shop.order.OrderMapper;
import saleson.shop.order.OrderService;
import saleson.shop.order.claimapply.OrderClaimApplyMapper;
import saleson.shop.order.claimapply.OrderClaimApplyService;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderPayment;
import saleson.shop.order.payment.OrderPaymentMapper;
import saleson.shop.order.shipping.OrderShippingMapper;
import saleson.shop.order.support.BatchKey;
import saleson.shop.order.support.OrderParam;
import saleson.shop.point.PointMapper;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.Point;
import saleson.shop.point.support.OrderPointParam;
import saleson.shop.rankingbatch.RankingBatchService;
import saleson.shop.rankingbatch.support.RankingBatchParam;
import saleson.shop.rankingconfig.RankingConfigMapper;
import saleson.shop.rankingconfig.domain.RankingConfig;
import saleson.shop.remittance.RemittanceMapper;
import saleson.shop.ums.UmsService;
import saleson.shop.ums.kakao.AlimTalkService;
import saleson.shop.ums.kakao.AlimTalkTemplateRepository;
import saleson.shop.ums.support.ConfirmPurchase;
import saleson.shop.ums.support.ConfirmPurchaseRequest;
import saleson.shop.ums.support.ExpirationCoupon;
import saleson.shop.ums.support.ExpirationPoint;
import saleson.shop.user.UserMapper;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.userlevel.UserLevelMapper;
import saleson.shop.userlevel.domain.UserLevel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service("jobService")
public class JobServiceImpl implements JobService {
	private static final String DELIMITER = ","; // 구획문자
	
	private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

	/*
	@Autowired
	private SchedulingMapperBatch schedulingMapperBatch;
	
	@Autowired
	private SchedulingMapper schedulingMapper;
	*/
	
	@Autowired
	SequenceService sequenceService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired 
	@Qualifier("mailService")
	NotificationService mailService;

	@Autowired
	OrderBatchService orderBatchService;


	@Autowired
	ConfigMapper configMapper;	
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	UserService userService;

	@Autowired
	private PointService pointService;
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired 
	private SmsService smsService;

	@Autowired
	private RemittanceMapper remittanceMapper;
	
	@Autowired
	private RankingBatchService rankingBatchService;
	
	@Autowired
	private RankingConfigMapper rankingConfigMapper;
	
	@Autowired
	private CategoriesMapper categoriesMapper;
	
	@Autowired
	private CategoriesTeamGroupMapper categoriesTeamGroupMapper;
	
	@Autowired
	private CouponRegularMapperBatch couponRegularMapperBatch;
	
	@Autowired
	private CouponMapper couponMapper;

	@Autowired
	private OrderService  orderService;

	@Autowired
	GroupService groupService;

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserLevelMapper userLevelMapper;

	@Autowired
	OrderClaimApplyService orderClaimApplyService;

	@Autowired
	OrderPaymentMapper orderPaymentMapper;

	@Autowired
	private AlimTalkService alimtalkService;

	@Autowired
	private AlimTalkTemplateRepository alimTalkTemplateRepository;

	@Autowired
	private CampaignService campaignService;

	@Autowired
	private CampaignStatisticsService campaignStatisticsService;

	@Autowired
	private UmsService umsService;

	@Autowired
	private ApplicationInfoService applicationInfoService;

	@Autowired
	private UnifiedMessagingService unifiedMessagingService;

	@Autowired
	private PointMapper pointMapper;

	@Autowired
	private ErpService erpService;

	@Autowired
	private OrderClaimApplyMapper orderClaimApplyMapper;

	@Autowired
	private ErpMapper erpMapper;

	@Autowired
	private DeliveryCompanyService deliveryCompanyService;

	@Override
	public void itemKeywordBatch() {
		log.debug("BATCH EXCUTION");
		//itemService.setKeywordDaily();
	}
	
	@Override
	public void autoConfirmPurchaseBatch() {
		//System.out.println("$$$$$$$$[배치] 구매확정 - 오픈 시 주석 해제 !!!");

		Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
		String confirmPurchaseDate = shopConfig.getConfirmPurchaseDate();

		OrderParam orderParam = new OrderParam();
		orderParam.setConfirmPurchaseDate(confirmPurchaseDate);
		orderParam.setConditionType("AUTO_CONFIRM");
		List<OrderItem> list = orderShippingMapper.getBuyConfirmDelayListByParam(orderParam);

		if (list != null) {
			String pointType = "point";

			// 중복되지 않게 addUserId
			HashSet<Long> userIds = new HashSet<>();
			for (OrderItem orderItem : list) {

				userIds.add(orderItem.getUserId());

				orderParam.setOrderCode(orderItem.getOrderCode());
				orderParam.setOrderSequence(orderItem.getOrderSequence());
				orderParam.setItemSequence(orderItem.getItemSequence());
				
				// 구매확정일 조회
				orderParam.setRemittanceDate(remittanceMapper.getRemittanceDateBySellerId(orderItem.getSellerId()));
				if (orderShippingMapper.updateConfirmPurchase(orderParam) > 0) {

					// 배송비 정보에 정산 예정일을 업데이트
					orderParam.setShippingSequence(orderItem.getShippingSequence());
					orderShippingMapper.updateShippingRemittanceDate(orderParam);

					// 주문 로그
					orderService.insertOrderLog(
							OrderLogType.ORDER_BATCH,
							orderItem.getOrderCode(),
							orderItem.getOrderSequence(),
							orderItem.getItemSequence(),
							orderItem.getOrderStatus()
					);
				}
			}

			// 포인트 적립
			OrderPointParam opp = new OrderPointParam();
			for (Long userId : userIds) {
				opp.setUserId(userId);
				pointService.savePointByOrderPointParam(opp);
			}

		}
	}
	
	@Override
	public void expirationPointBatch() {
		//System.out.println("$$$$$$$$[배치] 포인트 만료 - 오픈 시 주석 해제 !!!");
		//pointService.expirationPoint();
	}

	@Override
	public void expirationPointSendMessageBatch() {
		System.out.println("$$$$$$$$[배치] 포인트 만료 안내 - 오픈 시 주석 해제 !!!");
		pointService.expirationPointSendMessage();
	}
	
	@Override
	public void sendMailToInactiveUserBatch() {
		System.out.println("$$$$$$$$[배치] 휴면계정 안내 메일 발송 시작 - 오픈 시 주석 해제 !!!");
		
		log.info("휴면계정 안내 메일 발송 시작");
		if (!ServiceType.PRODUCTION) {
			userService.sendSleepUserMail();
		} else {
			log.info("휴면계정 안내메일 발송됨");
		}
	}

	@Override
	public void processInactiveUserBatch() {
		System.out.println("$$$$$$$$[배치] 휴면계정 전환처리 - 오픈 시 주석 해제 !!!");
		
		log.info("휴면계정으로 전환 처리 시작");
		if (!ServiceType.PRODUCTION) {
			userService.setSleepUser();
		} else {
			log.info("휴면계정으로 전환");
		}
		

	}
	
	@Override
	public void sendOrderSmsToSellerBatch() {
		//System.out.println("$$$$$$$$[배치] 판매자 주문안내 Sms발송 ");
		
		String Hour = DateUtils.getToday("HH");
		List<Seller> seller = sellerService.getSellerIdBySmsSendTime(Hour);
		
		for(int i = 0; i < seller.size(); i++){
			
			OrderParam orderParam = new OrderParam();
			orderParam.setSellerId(seller.get(i).getSellerId());
			orderParam.setConditionType("SELLER");
			
			int totalCount = orderShippingMapper.getNewOrderCountByParam(orderParam);
			
			if(totalCount > 0){
				
				try {
					
					String smsMessage = Hour + "시 기준 신규주문 " + totalCount + "건이 주문되었으니 확인 해 주시기 바랍니다.";
					OpMessage message = new SmsMessage(seller.get(i).getSecondPhoneNumber(), smsMessage, "");
					smsService.sendMessage(message); // SMS 발송.
					
				} catch (Exception e){
					log.error("ERROR: {}", e.getMessage(), e);
				}
			}
		}
	}
	
	@Override
	public void updateItemOptionSoldoutBatch() {
		itemService.updateItemOptionSoldout();
	}
	
	@Override
	public void itemRankingType1Batch() {
		String rankingCode = "TOP_100";
		RankingConfig config = rankingConfigMapper.getRankingConfigByRankConfigCode(rankingCode);
					
		RankingBatchParam param = new RankingBatchParam(config);
		param.setRankingType("1");
		param.setRankingCode(rankingCode);
		param.setLimit(100);
		
		rankingBatchService.insertItemRankingBatch(param);
	}
	
	@Override
	public void itemRankingType2Batch() {
		List<CategoriesGroup> list = categoriesTeamGroupMapper.getCategoriesGroupList();
		for(CategoriesGroup group : list) {
			
			if ("Y".equals(group.getCategoryGroupFlag())) {
				
				String rankingCode = group.getCode();
				RankingConfig config = rankingConfigMapper.getRankingConfigByRankConfigCode(rankingCode);
				
				List<String> categoryClass1List = categoriesTeamGroupMapper.getCategoryClass1ListByGroupCode(rankingCode);
				if (categoryClass1List == null) {
					continue;
				}
				
				if (categoryClass1List.isEmpty()) {
					continue;
				}

				RankingBatchParam param = new RankingBatchParam(config);
				param.setRankingType("2");
				param.setRankingCode(rankingCode);
				param.setCategoryGroupId(group.getCategoryGroupId());
				param.setLimit(100);
				param.setGroupCategoryClassCodes(categoryClass1List);
				
				rankingBatchService.insertItemRankingBatch(param);
			}
		}
	}
	
	@Override
	public void itemRankingType3Batch() {
		int maxLevel = 1;
		
		List<Categories> list = categoriesMapper.getCategoryListByMaxLevel(maxLevel);
		for(Categories category : list) {
			
			if ("Y".equals(category.getCategoryFlag())) {
				
				String rankingCode = Integer.toString(category.getCategoryId());
				RankingConfig config = rankingConfigMapper.getRankingConfigByRankConfigCode(rankingCode);
				
				RankingBatchParam param = new RankingBatchParam(config);
				param.setRankingType("3");
				param.setRankingCode(rankingCode);
				param.setCategoryCode(category.getCategoryCode());
				param.setCategoryLevel(Integer.parseInt(category.getCategoryLevel()));
				param.setLimit(100);
				
				rankingBatchService.insertItemRankingBatch(param);
			}
		}
	}
	
	@Override
	public void couponRegularBatch() {
		//배치 실행 날짜상수 설정(일단은 매달 1일 실행)
		final int batchDay = 1;
		
		//발행가능 쿠폰 조회
		List<Coupon> couponRegularList = couponRegularMapperBatch.getCouponRegularBatchList();
		
		if (couponRegularList != null && couponRegularList.size() != 0) {
			
			//배치실행 달의 설정일부터 한달이 다운로드/사용 가능 기간
			Calendar calendar = new GregorianCalendar(Locale.KOREA);
			SimpleDateFormat formatter = new SimpleDateFormat(Const.DATE_FORMAT, Locale.getDefault());
			calendar.set(Calendar.DAY_OF_MONTH, batchDay);
			String startDate = formatter.format(calendar.getTime());
			calendar.add(Calendar.MONTH, +1); //배치 월에서 한달 더함
			String endDate = formatter.format(calendar.getTime());
			calendar.add(Calendar.DATE, -1); //하루를 뺌
			endDate = formatter.format(calendar.getTime());

			//쿠폰테이블에 옮겨 저장
			for (int i=0; i<couponRegularList.size(); i++) {
				
				couponRegularList.get(i).setCouponId(sequenceService.getId("OP_COUPON"));
				couponRegularList.get(i).setCouponApplyStartDate(startDate);
				couponRegularList.get(i).setCouponIssueStartDate(startDate);
				couponRegularList.get(i).setCouponApplyEndDate(endDate);
				couponRegularList.get(i).setCouponIssueEndDate(endDate);
				
				couponRegularMapperBatch.insertSelectCouponRegular(couponRegularList.get(i));
				
				// 회원 선택 추가 - OP_COUPON_TARGET_USER 테이블에 데이터 생성
				if ("2".equals(couponRegularList.get(i).getCouponTargetUserType())) {
					
					if (ValidationUtils.isNull(couponRegularList.get(i).getCouponTargetUsers())) {
						throw new UserException("회원 선택 추가 조건이 지정되지 않았습니다.");
					}
					
					couponMapper.insertCouponTargetUser(couponRegularList.get(i));
				}
				
				// 상품 선택 추가 - OP_COUPON_TARGET_ITEM 테이블에 데이터 생성
				if ("2".equals(couponRegularList.get(i).getCouponTargetItemType())) {
					
					if (ValidationUtils.isNull(couponRegularList.get(i).getCouponTargetItems())) {
						throw new UserException("상품 선택 추가 조건이 지정되지 않았습니다.");
					}
					
					couponMapper.insertCouponTargetItem(couponRegularList.get(i));
				}
			}
		}
	
	}


	@Override
	public void userLevelBatch() {
		// 배치 실행일로 부터 1일 전 (배치는 새벽에 실행. 만료일은 어제)
		String expirationDate = LocalDateUtils.localDateToString(LocalDate.now().minusDays(1L));
		userLevelBatch(expirationDate);
	}

	@Override
	public void userLevelBatch(String expirationDate) {
		// 1. Group, UserLevel 정보 조회
		List<Group> groups = groupService.getGroupsAndUserLevelsAll();


		log.debug("[UserLevelBatch] START ------------------------------------------");
		log.debug("[UserLevelBatch] group count : {}", groups.size());

		for (Group group : groups) {

			// 2. 등급 조건에 맞는 회원 정보를 조회하여 등급 업데이트 (등급 재산정 대상 회원 기준)
			int i = 0;
			boolean shouldExcuteLastUserLevel = true;
			for (UserLevel userLevel : group.getUserLevels()) {
				i++;

				userLevel.setExpirationDate(expirationDate);

				// 2.1. 해당 등급의 조건에 맞는 회원수를 조회 (주문데이터 등) - 로그 일괄 등록을 위해 필요 (데이터가 없는 경우 insert 오류 방지)
				//      제일 낮은 등급의 구매금액 시작 조건(priceStart) == 0 인 경우 나머지 회원(등급 산정이 안된 모든 회원을 제일 낮은 등급으로 일괄처리
				if (i == group.getUserLevels().size() && userLevel.getPriceStart() == 0) {
					userLevel.setUserLevelProcessType("ALL_OTHER");
					shouldExcuteLastUserLevel = false;		// 마지막 등급의 금액 시작이 0이 아닌 경우는 등급없음 상태로 처리해야함.
				}
				processUserLevel(userLevel);
			}


			// 3. 등급 조건에 해당하지 않는 회원은 '등급없음'으로 업데이트 (등급 재산정 대상 회원 기준)
			if (shouldExcuteLastUserLevel) {
				UserLevel userLevel = new UserLevel();
				userLevel.setExpirationDate(expirationDate);
				userLevel.setGroupCode(group.getGroupCode());
				userLevel.setLevelId(0);
				userLevel.setLevelName("등급없음");
				userLevel.setRetentionPeriod(1);                // 등급없음은 1달간 유지

				// 3.1. 해당 그룹의 회원이 등급 산정이 안된 경우 일괄 업데이트 (userLevel = 0 : 등급없음, 등급 유지 기간 1달)
				processUserLevel(userLevel);
			}
		}

		log.debug("[UserLevelBatch] END --------------------------------------------");
		log.debug("");
	}

	/**
	 * 회원 등급 업데이트
	 * @param userLevel
	 */
	private void processUserLevel(UserLevel userLevel) {

		/*// 1. 해당 등급의 조건에 맞는 회원수를 조회 (주문데이터 등) - 로그 일괄 등록을 위해 필요 (데이터가 없는 경우 insert 오류 방지)
		int userCount = userMapper.getUserCountByUserLevel(userLevel);

		log.debug("[UserLevelBatch] groupCode: {}, levelName: {}, userCount: {}", userLevel.getGroupCode(), userLevel.getLevelName(), userCount);

		// 2. 처리할 회원이 있는 경우 로그 등록 및 등급 정보 UPDATE
		if (userCount > 0) {
			// 2.1. 회원 등급 로그 일괄 업데이트
			userMapper.insertUserLevelLogByUserLevel(userLevel);

			// 2.2. 해당 등급의 조건에 맞는 회원을 조회하여 일괄 업데이트
			userMapper.updateUserDetailByUserLevel(userLevel);
		}*/

		// 회원 등급 로그 일괄 업데이트
		int updateCount = userMapper.insertUserLevelLogByUserLevel(userLevel);

		// 2. 업데이트 데이터가 있는 경우 - 해당 등급의 조건에 맞는 회원을 조회하여 일괄 업데이트
		if (updateCount > 0) {
			// 2.1. 회원 등급 로그 일괄 업데이트
			userMapper.updateUserDetailByUserLevel(userLevel);
		}
	}

	@Override
	public void cancelWaitingDepositOrderBatch() {

		log.debug("[cancelWaitingDepositOrderBatch] START ------------------------------------------");

        OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("CANCEL-BATCH");
        orderParam.setSearchDelayDay("1");

        // 입금지연 주문 카운트
        int totalCount = orderPaymentMapper.getWaitingDepositCountByParamForBatch(orderParam);

        log.debug("[cancelWaitingDepositOrderBatch] totalCount : {}", totalCount);

        Paging paging = new Paging(totalCount, 100);

		int totalPage = paging.getTotalPages();
		for (int i = totalPage; i >= 1; i--) {

			paging.setCurrentPage(i);
            orderParam.setPaging(paging);


            // 입금지연 주문 목록 조회
            List<OrderPayment> list = orderPaymentMapper.getWaitingDepositListByParamForBatch(orderParam);

			for (OrderPayment payment : list) {
				orderParam.setOrderCode(payment.getOrderCode());
				orderParam.setOrderSequence(payment.getOrderSequence());
				orderParam.setUserId(payment.getUserId());
				orderParam.setUserName(payment.getUserName());

				// 취소
				try {
					orderClaimApplyService.orderCancelAllProcessNewTx(orderParam);
				} catch (Exception e) {
					log.error("[cancelWaitingDepositOrderBatch] 전체 취소 실패 : {}", payment.getOrderCode(), e);
				}
			}

        }

		log.debug("[cancelWaitingDepositOrderBatch] END --------------------------------------------");
		log.debug("");
	}

	@Override
	public void autoCompleteKeywordBatch() {
		//
	}

	@Override
	public void updateKakaoAlimTalkBatch() {
		log.debug("[updateKakaoAlimTalkBatch] START ------------------------------------------");

		try {
			List<AlimTalkTemplate> vendorAlimTalks = alimtalkService.getTemplateList();
			List<AlimTalkTemplate> salesonAlimTalks = alimTalkTemplateRepository.findAll();

			HashSet<String> updateCodeSet = new HashSet<>();
			HashSet<String> removeCodeSet = new HashSet<>();

			if (vendorAlimTalks != null && vendorAlimTalks.size() > 0) {
				// Vendor 알림톡 승인코드 세팅
				vendorAlimTalks.forEach(vendor -> {
					// 검수상태가 승인(APR)일 경우 삭제 대상에서 배제
					if (!TemplateInspStatus.APR.getCode().equals(vendor.getInspStatus())) {
						removeCodeSet.add(vendor.getApplyCode());
					}
				});

				// Vendor <-> SalesOn 일치하는 데이터 승인코드 세팅 및 SalesOn에 동기화
				vendorAlimTalks.forEach(
						vendor -> salesonAlimTalks.forEach(
								saleson -> {
									if (vendor.getApplyCode().equals(saleson.getApplyCode())) {
										vendor.setTemplateCode(saleson.getTemplateCode());
										alimtalkService.setAlimTalkTemplate(saleson, vendor);
										alimTalkTemplateRepository.save(vendor);

										updateCodeSet.add(vendor.getApplyCode());
									}
								}
						)
				);
			}

			// Vendor <-> SalesOn 불일치 데이터 삭제 (Vendor에만 남아있는 데이터)
			removeCodeSet.removeAll(updateCodeSet);

			if (removeCodeSet.size() > 0) {
				removeCodeSet.forEach(code -> {
					try {
						alimtalkService.deleteTemplate(code);
					} catch (Exception e) {
						log.error("Vendor 데이터 삭제 실패 : {}", e.getMessage(), e);
						throw new KakaoAlimTalkException(e.getMessage(), e);
					}
				});
			}

		} catch (Exception e) {
			log.error("[updateKakaoAlimTalkBatch] 카카오 알림톡 업데이트 실패 : {}", e.getMessage(), e);
		}

		log.debug("[updateKakaoAlimTalkBatch] END --------------------------------------------");
	}

	@Override
	public void updateUserCampaignBatch() {

		try {
			campaignService.insertCampaignBatch();
		} catch (Exception e) {
			log.error("[updateUserCampaignBatch] 캠페인용 유저 배치 오류 : {}", e.getMessage(), e);
		}
	}

	@Override
	public void sendCampaignMessageBatch() {

		try {
			campaignService.insertCampaignMessageBatch();
		} catch (Exception e) {
			log.error("[sendCampaignMessageBatch] 캠페인용 예약발송 발송처리 배치 오류 : {}", e.getMessage(), e);
		}
	}

	@Override
	public void updateCampaignSentBatch() {

		String date = "";

		try {

			String today = DateUtils.getToday(Const.DATE_FORMAT);

			campaignStatisticsService.updateCampaignSentBatch(today);

		} catch (Exception e) {
			log.error("[updateCampaignSentBatch] 캠페인용 발송통계 배치 오류 : [{}] - {}", date, e.getMessage(), e);
		}
	}

	@Override
	public void autoConfirmPurchaseUmsBatch() {
		String templateId = "confirm_purchase";
		int day = 1;	// 1일전

		try {
			Ums ums = umsService.getUms(templateId);

			if (umsService.isValidUms(ums)) {
				List<UserDetail> users = userMapper.getConfirmPurchaseUserList(day);

				if (users != null) {
					Config config = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);

					users.stream()
						.filter(user -> !StringUtils.isEmpty(user.getPhoneNumber()))
						.forEach(user -> {
							ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(user.getUserId());
							unifiedMessagingService.sendMessage(new ConfirmPurchase(ums, user.getUserId(), user.getPhoneNumber(), config, applicationInfo));
						});
				}
			}
		} catch (Exception e) {
			log.error("[autoConfirmPurchaseUmsBatch] 구매확정 UMS 배치 오류 : [{}] - {}", e.getMessage(), e);
		}
	}

	@Override
	public void autoConfirmPurchaseRequestUmsBatch() {
		String templateId = "confirm_purchase_request";

		try {
			Ums ums = umsService.getUms(templateId);

			if (umsService.isValidUms(ums)) {
				Config config = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
				int day = Integer.parseInt(config.getConfirmPurchaseRequestDate());
				
				List<UserDetail> users = userMapper.getConfirmPurchaseRequestUserList(day);

				if (users != null) {
					users.stream()
						.filter(user -> !StringUtils.isEmpty(user.getPhoneNumber()))
						.forEach(user -> {
							ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(user.getUserId());
							unifiedMessagingService.sendMessage(new ConfirmPurchaseRequest(ums, user.getUserId(), user.getPhoneNumber(), config, applicationInfo));
						});
				}
			}

		} catch (Exception e) {
			log.error("[autoConfirmPurchaseRequestUmsBatch] 구매확정 요청 UMS 배치 오류 : [{}] - {}", e.getMessage(), e);
		}

	}

    @Override
    public void deleteOrderTempInfoBatch() {

        try {
            Config config = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
            int retentionPeriod = config.getRetentionPeriod();

            // 주문 임시 저장 정보 삭제
            orderMapper.deleteOrderTempForBatch(retentionPeriod);
            orderMapper.deleteOrderItemBuyTempForBatch(retentionPeriod);
            orderMapper.deleteOrderPaymentBuyTempForBatch(retentionPeriod);
            orderMapper.deleteOrderShippingBuyTempForBatch(retentionPeriod);

        } catch (Exception e) {
            log.error("[deleteOrderTempInfoBatch] 임시 주문 정보 삭제처리 배치 오류 : [{}] - {}", e.getMessage(), e);
        }
    }

	@Override
	public void expirationCouponSendMessageBatch() {
		String templateId = "expiration_coupon";
		int day = 2;	// 쿠폰만료 2일전

		try {
			Ums ums = umsService.getUms(templateId);
			UserCouponParam userCouponParam = new UserCouponParam();
			userCouponParam.setDay(day);

			if (umsService.isValidUms(ums)) {
				int totalCount = couponMapper.getExpirationCouponUserCount(day);
				log.debug("[expirationCouponSendMessageBatch] totalCount : {}", totalCount);

				Paging paging = new Paging(totalCount, 100);

				int totalPage = paging.getTotalPages();
				for (int i = totalPage; i >= 1; i--) {

					paging.setCurrentPage(i);
					userCouponParam.setPaging(paging);

					// 회원별 쿠폰 개수 조회
					List<CouponUser> userList = couponMapper.getExpirationCouponUserList(userCouponParam);
					// 회원별 쿠폰명 조회 (쿠폰이 여러 개일 경우 1개만 조회)
					List<CouponUser> couponList = couponMapper.getExpirationCouponList(userCouponParam);

					String date = DateUtils.formatDate(DateUtils.addDay(DateUtils.getToday(Const.DATE_FORMAT), day), "-") + " 23:59:59 까지";

					for (CouponUser couponUser : userList) {
						couponList.stream().forEach(coupon -> {
							if (couponUser.getUserId() == coupon.getUserId()) {
								couponUser.setCouponApplyEndDate(date);

								if (couponUser.getCouponCount() > 1) {
									int count = couponUser.getCouponCount() - 1;

									String couponName = coupon.getCouponName() + " 외 " + count +"건";
									couponUser.setCouponName(couponName);

								} else {
									couponUser.setCouponName(coupon.getCouponName());
								}
							}
						});
					}

					Config config = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
					userList.stream()
							.filter(list -> !StringUtils.isEmpty(list.getPhoneNumber()))
							.forEach(couponUser -> {
								ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(couponUser.getUserId());
								unifiedMessagingService.sendMessage(new ExpirationCoupon(ums, couponUser, couponUser.getUserId(), couponUser.getPhoneNumber(), config, applicationInfo));
							});
				}
			}
		} catch (Exception e) {
			log.error("[expirationCouponSendMessageBatch] 쿠폰 만료 기간 안내 배치 오류 : [{}] - {}", e.getMessage(), e);
		}
	}

	public void expirationPointMessageBatch() {
		String templateId = "expiration_point";
		int day = 2;	// 포인트 만료 2일전

		try {
			Ums ums = umsService.getUms(templateId);

			List<Point> targetList = pointMapper.getExpirationPointSendMessage(day);

			if (umsService.isValidUms(ums)) {

				Config config = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
				targetList.stream()
						.filter(user -> !StringUtils.isEmpty(user.getPhoneNumber()))
						.forEach(user -> {
							ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(user.getUserId());
							user.setExpirationDate(DateUtils.addDay(DateUtils.getToday("yyyy-MM-dd"), day));

							unifiedMessagingService.sendMessage(new ExpirationPoint(ums, user, user.getUserId(), user.getPhoneNumber(), config, applicationInfo));
						});
			}
		} catch (Exception e) {
			log.error("[expirationCouponSendMessageBatch] 쿠폰 만료 기간 안내 배치 오류 : [{}] - {}", e.getMessage(), e);
		}
	}

	@Override
	public void erpOrderStatusBatch() {
		/*// 0. BatchKey 생성
		String batchKey = DateUtils.getToday("yyyyMMddHHmmss");
		BatchKey batchKey10 = new BatchKey(batchKey, "10");
		BatchKey batchKey20 = new BatchKey(batchKey, "20");
		BatchKey batchKey30 = new BatchKey(batchKey, "30");
		BatchKey batchKey50 = new BatchKey(batchKey, "50");
		BatchKey batchKey55 = new BatchKey(batchKey, "55");


		// 1. 결제 완료 / 배송 준비 중인 order_item 데이터 조회
		List<OrderItem> orderItems = orderMapper.getOrderItemListForErpBatch();

		List<OrderItem> orderedList = orderItems.stream()
				.filter(o -> "10".equals(o.getOrderStatus()))
				.collect(Collectors.toList());

		List<OrderItem> deliveryReadyList = orderItems.stream()
				.filter(o -> "20".equals(o.getOrderStatus()))
				.collect(Collectors.toList());

		List<OrderItem> returnedList = orderItems.stream()
				.filter(o -> "50".equals(o.getOrderStatus()))
				.collect(Collectors.toList());


		// 2. 결제완료(10) -> 데이터 조회 및 업데이트 대상 데이터를 임시 테이블에 저장
		saveErpOrderItems(orderedList, batchKey10);

		// 3. 배송준비중(20) -> 데이터 조회 및 업데이트 대상 데이터를 임시 테이블에 저장
		saveErpOrderItems(deliveryReadyList, batchKey20);

		// 4. 회수완료(50) -> 데이터 조회 및 업데이트 대상 데이터를 임시 테이블에 저장
		saveErpOrderItems(returnedList, batchKey50);


		// 4. Update ORDER_ITEM Status
		orderMapper.updateOrderItemStatusWithErpTempTable(batchKey20);
		orderMapper.updateOrderItemStatusWithErpTempTable(batchKey30);
		orderMapper.updateOrderItemStatusWithErpTempTable(batchKey55);

		// 5. UPDATE OP_ORDER_EXCHANGE_APPLY
		orderClaimApplyMapper.updateOrderExchangeWithErpTempTable(batchKey55);


		// 6. UPDATE IF_ORDER_LIST_PUT
		erpService.updateApplyFlagIfOrderListPut(orderedList);
		erpService.updateApplyFlagIfOrderListPut(deliveryReadyList);
		erpService.updateApplyFlagIfOrderListPut(returnedList);*/



		// 1. 데이터 조회
		List<OrderLineStatus> orderLineStatuses = erpMapper.findIfOrderListPutAll();

		List<OrderItem> orderItems = new ArrayList<>();
		if (!orderLineStatuses.isEmpty()) {
			orderItems = orderMapper.getOrderItemListForErpBatchStatus(orderLineStatuses);
		}

		List<String> uniqs = new ArrayList<>();
		List<OrderItem> finalOrderItems = orderItems;

		orderLineStatuses.stream().forEach(ols -> {

			// CJ대한통운 - ERP의 택배사코드는 4, OP_DELIVERY_COMPANY는 3이라 3으로 조회
			if (ols.getCarrNo() == 4) {
				DeliveryCompany deliveryCompany = deliveryCompanyService.getDeliveryCompanyById(3);
				ols.setDeliveryCompanyUrl(deliveryCompany.getDeliveryCompanyUrl());
			}

			// erpOriginUnique와 일치하는지 비교 - 클레임 상품일 경우 oriUniq(원주문고유값)와 비교, 그 외에는 uniq와 비교)
			Optional<OrderItem> optionalOrderItem = finalOrderItems.stream().filter(oi -> oi.getErpOriginUnique().equals((ols.getOriUniq() != null && !ols.getOriUniq().isEmpty()) ? ols.getOriUniq() : ols.getUniq())).findFirst();

			if (!optionalOrderItem.isPresent()) {
				try {
					orderMapper.saveErpOrderStatus(ols);

				} catch (Exception e) {
					log.error("[erpOrderStatusBatch] 로그 저장 실패 erpOriginUnique: {}, error: {}", optionalOrderItem.get().getErpOriginUnique(), e.getMessage(), e);
				}
			} else {

				ols.setOriginStatus(optionalOrderItem.get().getOrderStatus());

				try {
					// 2. Update ORDER_ITEM Status
					orderMapper.updateOrderItemByIfOrderListPutData(ols);

					if ("55".equals(ols.getOrderStatus())) {
						// 3. 교환배송 시 Update OP_ORDER_EXCHANGE_APPLY
						orderClaimApplyMapper.updateOrderExchangeByIfOrderListPutData(ols);
					}

					// 4. ERP_ORDER_STATUS_LOG 저장
					orderMapper.saveErpOrderStatus(ols);

				} catch (Exception e) {
					log.error("[erpOrderStatusBatch] uniq:{}, error: {}", ols.getUniq(), e.getMessage(), e);
				}
			}

			// IF_ORDER_LIST_PUT 테이블 업데이트를 위한 uniq (ERP에서 데이터를 읽어오면 무조건 'Y'처리)
			uniqs.add(ols.getUniq());

		});


		if (!uniqs.isEmpty()) {
			// 5. IF_ORDER_LIST_PUT 업데이트
			erpMapper.updateApplyFlagIfOrderListPut(uniqs);
		}
	}

	private void saveErpOrderItems(List<OrderItem> orderItems, BatchKey batchKey) {
		if (orderItems.isEmpty()) {
			log.debug("[ERP BATCH] ORDER_STATUS({}): 처리할 데이터가 없습니다. ", batchKey.getOrderStatus());
			return;
		}

		// 1. 상태가 변경된 건만 업데이트 대상으로 선별
//		List<OrderItem> orderedResult = erpService.findOrderItemAll(orderItems).stream()
//				.filter(o -> !batchKey.getOrderStatus().equals(o.getOrderStatus())).collect(Collectors.toList());
		log.debug("[ERP BATCH] orderItems ----------------------------:");
		orderItems.forEach(System.out::println);

		List<OrderItem> orderItemList = erpService.findOrderItemAll(orderItems);
		if (orderItemList == null) {
			log.debug("[ERP BATCH] orderItemList is null !!");
		}


		List<OrderItem> orderedResult = new ArrayList<>();
		if (orderItemList != null) {
			for (OrderItem orderItem : orderItemList) {
				log.debug("[ERP BATCH] batchKey: {}, orderStatus: {}", batchKey, orderItem.getOrderStatus());
				if (batchKey.equals(orderItem.getOrderStatus())) {
					continue;
				}
				orderedResult.add(orderItem);
			}
		}

		log.debug("[ERP BATCH] ORDER_STATUS({}): request({}), response({})",
				batchKey.getOrderStatus(), orderItems.size(), orderedResult.size());

		if (orderedResult.isEmpty()) {
			log.debug("[ERP BATCH] orderedResult is empty !!");
			return;
		}

		// 2. 상태가 변경된 건을 임시 테이블에 저장
		log.debug("[ERP BATCH] ORDER_STATUS({}): insert ERP_ORDER_ITEM => {}건", batchKey.getOrderStatus(), orderedResult.size());
		batchKey.setOrderItems(orderedResult);
		orderMapper.saveErpOrderItems(batchKey);
	}
}
