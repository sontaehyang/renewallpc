package saleson.shop.point;

import com.onlinepowers.framework.common.ServiceType;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;
import saleson.common.utils.PointUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.config.ConfigMapper;
import saleson.shop.config.domain.Config;
import saleson.shop.mailconfig.MailConfigService;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.MemberExpirationPointMail;
import saleson.shop.order.OrderMapper;
import saleson.shop.point.domain.*;
import saleson.shop.point.exception.PointException;
import saleson.shop.point.support.OrderPointParam;
import saleson.shop.point.support.PointParam;
import saleson.shop.sendmaillog.SendMailLogService;
import saleson.shop.sendmaillog.domain.SendMailLog;
import saleson.shop.sendsmslog.SendSmsLogService;
import saleson.shop.smsconfig.SmsConfigService;
import saleson.shop.user.UserMapper;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.userlevel.UserLevelMapper;
import saleson.shop.userlevel.domain.UserLevel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("pointService")
public class PointServiceImpl implements PointService {
	private static final Logger log = LoggerFactory.getLogger(PointServiceImpl.class);

	@Autowired
	private PointMapper pointMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private ConfigMapper configMapper;
	
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MailConfigService mailConfigService;
	
	@Autowired
	private SmsConfigService smsConfigService;
	
	@Autowired
	private SendMailLogService sendMailLogService;
	
	@Autowired
	private SendSmsLogService sendSmsLogService;
	
	@Autowired
	private UserLevelMapper userLevelMapper;
	
	@Override
	public List<PublicationPoint> getPublicationPointListByParamForManagerExcelDownload(PointParam pointParam) {
		String conditionType = pointParam.getConditionType();
		List<PublicationPoint> list = pointMapper.getPublicationPointListByParamForManager(pointParam);
		if(list != null){
			for(PublicationPoint point : list){
				if(!(conditionType != null && conditionType.equals("USER-POINT-EXCEL"))){
					publicationPointMaskingDataSet(point);
				} else if(!(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS"))){
					publicationPointMaskingDataSet(point);
				}
			}
		}
		return list;
	}

	private void publicationPointMaskingDataSet(PublicationPoint point){
		String userName = point.getUserName();
		String loginId = point.getLoginId();

		if(userName != null){
			point.setUserName(UserUtils.reMasking(userName, "name"));
		}
		if(loginId != null){
			point.setLoginId(UserUtils.reMasking(loginId, "email"));
		}
	}

	@Override
	public List<PublicationPoint> getPublicationPointListByParamForManager(PointParam pointParam) {
		
		int totalCount = pointMapper.getPublicationPointCountByParamForManager(pointParam);
		
		if (pointParam.getItemsPerPage() == 10) {
			pointParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, pointParam.getItemsPerPage());
		pointParam.setPagination(pagination);
		pointParam.setLanguage(CommonUtils.getLanguage());

		List<PublicationPoint> list = pointMapper.getPublicationPointListByParamForManager(pointParam);

		if(list != null){
			for(PublicationPoint history : list){
				String loginId = history.getLoginId();
				String userName = history.getUserName();

				if(loginId != null){
					history.setLoginId(UserUtils.reMasking(loginId, "email"));
				}
				if(userName != null){
					history.setUserName(UserUtils.reMasking(userName, "name"));
				}
			}
		}

		return list;
		
	}
	
	@Override
	public List<PointHistory> getPointTotalHistoryListByParamForExcelDownload(PointParam pointParam) {
		String conditionType = pointParam.getConditionType();
		List<PointHistory> list = pointMapper.getPointTotalHistoryListByParam(pointParam);

		if(list != null){
			for(PointHistory history : list){
				if(!(conditionType != null && conditionType.equals("HISTORY-POINT-EXCEL"))){
					historyPointMaskingDataSet(history);
				} else if(!(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS"))){
					historyPointMaskingDataSet(history);
				}
			}
		}

		return list;
	}

	private void historyPointMaskingDataSet(PointHistory history){
		String loginId = history.getLoginId();
		String userName = history.getUserName();

		if(loginId != null){
			history.setLoginId(UserUtils.reMasking(loginId, "email"));
		}
		if(userName != null){
			history.setUserName(UserUtils.reMasking(userName, "name"));
		}
	}
	
	@Override
	public List<PointHistory> getPointTotalHistoryListByParam(PointParam pointParam) {
		int totalCount = pointMapper.getPointTotalHistoryCountByParam(pointParam);
		
		if (pointParam.getItemsPerPage() == 10) {
			pointParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, pointParam.getItemsPerPage());
		pointParam.setPagination(pagination);
		pointParam.setLanguage(CommonUtils.getLanguage());

		List<PointHistory> list = pointMapper.getPointTotalHistoryListByParam(pointParam);

		if(list != null){
			for(PointHistory history : list){
				String loginId = history.getLoginId();
				String userName = history.getUserName();

				if(loginId != null){
					history.setLoginId(UserUtils.reMasking(loginId, "email"));
				}
				if(userName != null){
					history.setUserName(UserUtils.reMasking(userName, "name"));
				}
			}
		}

		return list;
	}
	
	@Override
	public List<PointDayGroup> getPointDayGroupListByParam(PointParam pointParam) {
		
		int totalCount = pointMapper.getPointDayGroupCountByParam(pointParam);
		
		if (pointParam.getItemsPerPage() == 10) {
			pointParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, pointParam.getItemsPerPage());
		pointParam.setPagination(pagination);
		pointParam.setLanguage(CommonUtils.getLanguage());
		
		return pointMapper.getPointDayGroupListByParam(pointParam);

	}
	
	@Override
	public List<PointDayGroup> getPointDayGroupListByParamForExcelDownload(PointParam pointParam) {
		return pointMapper.getPointDayGroupListByParam(pointParam);
	}

	@Override
	public int getPointUsedCountByParam(PointParam pointParam) {
		return pointMapper.getPointUsedCountByParam(pointParam);
	}
	
	@Override
	public List<PointUsed> getPointUsedListByParam(PointParam pointParam) {
		return pointMapper.getPointUsedListByParam(pointParam);
	}
	
	@Override
	public int getNextMonthExpirationPointAmountByParam(PointParam pointParam) {
		return pointMapper.getNextMonthExpirationPointAmountByParam(pointParam);
	}
	
	@Override
	public Point getExpirationPointAmountByParam(PointParam pointParam) {
		return pointMapper.getExpirationPointAmountByParam(pointParam);
	}
	
	@Override
	public int getPointCountByParam(PointParam pointParam) {
		return pointMapper.getPointCountByParam(pointParam);
	}
	
	@Override
	public List<Point> getPointListByParam(PointParam pointParam) {
		return pointMapper.getPointListByParam(pointParam);
	}
	
	@Override
	public void pointTheorem(User user) {
		
		/**
		 * 포인트 정리는 기본적으로 동기화 완료 되었다고 생각하고 하는 작업이다.
		 * 포인트가 마이너스 포인트와 적립 포인트가 동시에 존재하는것을 정리하는 작업이다.
		 */
		
		if (user == null) {
			return;
		}
		
		List<String> pointTypes = pointMapper.getPointTheoremPointTypesByUserId(user.getUserId());
		if (pointTypes == null) {
			return;
		}
		
		for(String pointType : pointTypes) {
			
			OrderPointParam orderPointParam = new OrderPointParam();
			orderPointParam.setUserId(user.getUserId());
			orderPointParam.setPointType(pointType);
			List<Point> list = pointMapper.getAvailablePointListByParam(orderPointParam);
			
			if (list == null) {
				continue;
			}
			
			// 포인트 정리해야되는게 있나??
			List<Point> minusPoints = new ArrayList<>();
			List<Point> plusPoints = new ArrayList<>();
			
			int minusPointAmount = 0;
			int plusPointAmount = 0;
			for(Point point : list) {
				if (point.getPoint() > 0) {
					plusPoints.add(point);
					plusPointAmount += point.getPoint();
				} else if (point.getPoint() < 0) {
					minusPoints.add(point);
					minusPointAmount += point.getPoint();
				}
			}
			
			// 포인트 적립 없는경우
			if (minusPoints.isEmpty() && plusPoints.isEmpty()) {
				return;
			}
			
			// 적립과 마이너스가 같이 있나?
			if (!minusPoints.isEmpty() && !plusPoints.isEmpty()) {
			
				if (-minusPointAmount > plusPointAmount) {
					
					// 마이너스 포인트가 더 많은경우 - 최종적으로 사용가능한 포인트는 0보다 작다
					for(Point point : plusPoints) {
						
						point.setUpdate(true);
						
						// 마이너스 포인트에 적립 포인트 내역을 반영
						PointUtils.pointProcessing(minusPoints, point);
					}
				
				} else {
					
					// 적립 포인트가 더 많은 경우 - 최종적으로 사용가능한 포인트는 0보다 크다
					for(Point point : minusPoints) {
						
						point.setUpdate(true);
						
						// 적립 포인트에 마이너스 포인트 내역을 반영
						PointUtils.pointProcessing(plusPoints, point);
					}
					
				}

			}
			
			for(Point editPoint : list) {
				if (editPoint.isUpdate() == true) {
					pointMapper.updateAvailablePointByParam(editPoint);
				}
			}
		}
	}

	@Override
	public void expirationPoint() {
		
		/**
		 * 일배치 - 11시 59분이 적당하긴 한데.. 겹치는게 있을수도..
		 * [오늘] 기준으로 만료 처리할 포인트 보유 사용자를 찾아서 포인트를 만료처리한다.
		 * 2016.4.12 배치를 돌면서 날짜가 변경되는일이 발생할수 있어서 실행시점의 서버 날짜를 기준으로 조회하도록 변경
		 * 2016.4.13 휴먼회원도 포인트 만료시키기 위해서 회원상태 코드는 회원조회시 재외시킴
		 */
		
		PointParam pointParam = new PointParam();
		pointParam.setSearchStartDate(DateUtils.getToday(Const.DATE_FORMAT));
		
		List<Integer> userIds = pointMapper.getExpirationPointTargetListByParam(pointParam);
		if (userIds == null) {
			return;
		}
		
		System.out.println("BATCH 포인트 만료 - 대상자 ["+ userIds.size() +"]");
		
		int i = 0;
		for(Integer userId : userIds) {
			
			System.out.println("BATCH 포인트 만료 - 회원 ID ["+ userId +"]");
			
			User user = null;
			
			try {
				user = userMapper.getUserByUserIdNotStatusCode(userId);
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR 포인트 만료 - 회원정보 조회 에러 [{}]", userId, e);
			}

			if (user == null) {
				continue;
			}
			
			try {
				
				// 정리
				this.pointTheorem(user);
				
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR 포인트 만료 - 포인트 정리 실패 [{}]", userId, e);
			}
			
			try {
				
				// 포인트를 종류에 상관없이 조회
				pointParam.setUserId(userId);
				List<Point> points = pointMapper.getExpirationListByParam(pointParam);
				if (points == null) {
					continue;
				}
				
				// 포인트 종류별로 묶음
				HashMap<String, List<Point>> pointGroup = new HashMap<String, List<Point>>();
				for(Point point : points) {
					
					List<Point> temps = pointGroup.get(point.getPointType());
					if (temps == null) {
						temps = new ArrayList<>();
					}
					
					temps.add(point);
					pointGroup.put(point.getPointType(), temps);
					
				}
				
				// 포인트 변경내역 처리, 발송대상 포인트 모음
				for(String pointType : pointGroup.keySet()) {
					
					List<Point> temps = pointGroup.get(pointType);
					ExpirationPoint expirationPoint = new ExpirationPoint(temps, pointType, false);
					
					
					// 만료처리 대상 포인트 있나?
					if (expirationPoint.getExpirationPoints() != null) {
						
						int groupId = sequenceService.getId("OP_POINT_USED_GROUP_ID"); 
						for(Point point : expirationPoint.getExpirationPoints()) {
							
							String message = "사용기한 만료로 자동 소멸";
							String createdDate = DateUtils.getToday(Const.DATETIME_FORMAT);
							
							
							PointUsed pointUsed = new PointUsed();
							pointUsed.setPointUsedId(sequenceService.getId("OP_POINT_USED"));
							pointUsed.setPointUsedGroupId(groupId);
							pointUsed.setUsedType("2");
							pointUsed.setPointId(point.getPointId());
							pointUsed.setPoint(point.getPoint());
							pointUsed.setDetails(message);
							pointUsed.setCreatedDate(createdDate);
							pointUsed.setRemainingPoint(point.getPoint());
							
							pointMapper.insertPointUsed(pointUsed);
							
							point.setPoint(0);
							pointMapper.updateAvailablePointByParam(point);
						}
					}
					
				}
				
				i++;
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR 포인트 만료 - 포인트 만료처리 실패 [{}]", userId, e);
			}
		}
		
		System.out.println("BATCH 포인트 만료 - 처리완료 ["+ i +"명]");
	}
	
	@Override
	public void expirationPointSendMessage() {
		
		String templateId = "expiration_point";
		MailConfig mailConfig = mailConfigService.getMailConfigByTemplateId(templateId);
		if (mailConfig == null) {
			System.out.println("BATCH 포인트 만료 안내 메일발송 - 템플릿 설정 안됨");
			return;
		}
		
		if (!"Y".equals(mailConfig.getBuyerSendFlag())) {
			System.out.println("BATCH 포인트 만료 안내 메일발송 - 발송상태 체크 필요[관리자설정]");
			return;
		}
		
		PointParam pointParam = new PointParam();
		List<Integer> userIds = pointMapper.getExpirationPointSendMessageTargetListAll();
		if (userIds == null) {
			return;
		}
		
		Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
		
		System.out.println("BATCH 포인트 만료 안내 - 대상자 ["+ userIds.size() +"]");
		
		int i = 0;
		for(Integer userId : userIds) {
			
			System.out.println("BATCH 포인트 만료 안내 - 회원 ID ["+ userId +"]");
			
			User user = null;
			
			try {
				user = userMapper.getUserByUserId(userId);
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR 포인트 만료 안내 - 회원정보 조회 에러 [{}]", userId, e);
			}

			if (user == null) {
				continue;
			}
			
			try {
				
				// 정리
				this.pointTheorem(user);
				
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR 포인트 만료 안내 - 포인트 정리 실패 [{}]", userId, e);
			}
			
					
			try {
				
				if (!"9".equals(user.getStatusCode())) {
					log.debug("ERROR: BATCH-ERROR 포인트 만료 안내 - 회원 상태 9 아님 [{}]", userId);
					continue;
				}
				
				UserDetail userDetail = (UserDetail) user.getUserDetail();
				
				// 로컬 테스트용
				if (ServiceType.LOCAL) {
					//user.setEmail("cjh@onlinepowers.com");
				}
				
				if ("0".equals(userDetail.getReceiveEmail()) && StringUtils.isEmpty(user.getEmail()) == false) {
					
					pointParam.setUserId(userId);
					List<Point> list = pointMapper.getExpirationSendMessageListByParam(pointParam);
					if (list == null) {
						System.out.println("BATCH-ERROR 포인트 만료 안내 - 발송대상 포인트 없음 [" + userId + "]");
						continue;
					}
					
					HashMap<String,Integer> pointMap = new HashMap<>();
					for(Point point : list) {
						pointMap.put(point.getPointType(), point.getPoint());
					}
					
					if (!pointMap.isEmpty()) {
						
						MemberExpirationPointMail mail = new MemberExpirationPointMail(mailConfigService.getMailConfigByTemplateId(templateId), user, DateUtils.getToday("yyyy년MM월dd일"), DateUtils.getToday("yyyy년MM월dd일"), pointMap, shopConfig);
						MailConfig mConfig = mail.getMailConfig();
						
						SendMailLog sendMailLog = new SendMailLog();
						sendMailLog.setUserId(userId);
						sendMailLog.setSendType(templateId);
						
						sendMailLogService.sendMail(mConfig, sendMailLog, user.getEmail(), user.getUserName(), shopConfig);
						
						i++;
					}
				} else {
					log.info("BATCH-ERROR 포인트 만료 안내 - 마케팅 수신 동의 안함 [" + userId + "]");
				}
				
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR 포인트 만료 안내 [{}]", userId, e);
			}
		}

		log.info("BATCH 포인트 만료 안내 - 안내완료 [{}}명]", i);
	}
	
	@Override
	public List<PointConfig> getPointConfigListByItemId(int itemId) {
		return pointMapper.getPointConfigListByItemId(itemId);
	}
	
	@Override
	public PointPolicy getPointPolicyByItemId(int itemId) {
		
		OrderPointParam orderPointParam = new OrderPointParam();
		Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
		orderPointParam.setItemId(itemId);
		orderPointParam.setRepeatDayEndTime(shopConfig.getRepeatDayEndTime());
		orderPointParam.setRepeatDayStartTime(shopConfig.getRepeatDayStartTime());
		
		return pointMapper.getPointPolicyByOrderPointParam(orderPointParam);
	}
	
	@Override
	public PointPolicy getPointPolicyByOrderPointParam(OrderPointParam orderPointParam) {
		
		return pointMapper.getPointPolicyByOrderPointParam(orderPointParam);
	}
	
	@Override
	public void insertPointConfig(PointConfig pointConfig) {
		pointMapper.insertPointConfig(pointConfig);
	}


	@Override
	public void deletePointConfigById(int pointConfigId) {
		pointMapper.deletePointConfigById(pointConfigId);
	}

	@Override
	public List<PointConfig> getShopPointConfig() {
		return pointMapper.getShopPointConfig();
	}

	@Override
	public List<Point> getAvailablePointListByParam(OrderPointParam orderPointParam) {
		return pointMapper.getAvailablePointListByParam(orderPointParam);
	}

	@Override
	public AvailablePoint getAvailablePointByUserId(long userId, String pointType) {

		OrderPointParam orderPointParam = new OrderPointParam();
		orderPointParam.setUserId(userId);
		orderPointParam.setPointType(pointType);
		List<Point> list = pointMapper.getAvailablePointListByParam(orderPointParam);
	
		AvailablePoint point = new AvailablePoint(list);
		return point;
	}

	@Override
	public void deductedPoint(PointUsed pointUsed, long userId, String pointType) {

        String message = MessageUtils.getMessage("M00246") + "를";
        String exMessage = "";

        if (pointUsed.getPoint() <= 0) {
            if (pointUsed.getDetails() != null && pointUsed.getDetails().contains(MessageUtils.getMessage("M00246") + " 회수")) {
                exMessage = " ( 회수 가능한 " + MessageUtils.getMessage("M00246") + " : " + pointUsed.getPoint() + " )";
                throw new PointException("보유 " + message + " 확인해주세요." + exMessage);
            }
            throw new PointException("사용 " + message + " 확인해주세요." + exMessage);
        }

        User user = userMapper.getUserByUserId(userId);
        if (ValidationUtils.isNull(user)) {
            throw new PointException("보유 " + message + " 확인해주세요.");
        }

		AvailablePoint availablePoint = this.getAvailablePointByUserId(userId, pointType);

		
		pointUsed.setUsedType("1");
		pointUsed.setPointUsedGroupId(sequenceService.getId("OP_POINT_USED_GROUP_ID"));
		pointUsed.setCreatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
		
		// 관리자 로그인이면 포인트 사용 관리자 아이디 기록
		if (UserUtils.isManagerLogin()) {
			pointUsed.setManagerUserId(UserUtils.getManagerId());
		}
		
		try {
			
			List<PointUsed> newPointUsedList = new ArrayList<>();
			PointUtils.pointUsedProcessing(availablePoint.getPointList(), newPointUsedList, pointUsed);
			
			for(Point point : availablePoint.getPointList()) {
				if (point.isUpdate()) {
					pointMapper.updateAvailablePointByParam(point);
				}
			}
			
			// 사용 포인트에 아이디 부여
			for(PointUsed pu : newPointUsedList) {
				int pointUsedId = sequenceService.getId("OP_POINT_USED");
				pu.setPointUsedId(pointUsedId);
				pointMapper.insertPointUsed(pu);
			}
			
		} catch(PointException pe) {
			throw new RuntimeException("사용처리도중 에러가 발생하였습니다. 보유 " + message + " 확인하신후 사용을 부탁드립니다.");
		}
		
	}

	private void earnPoint(String mode, Point point, Config shopConfig) {
		if (mode.equals("join")) { // 회원 가입
			point.setPoint(shopConfig.getPointJoin());
			point.setReason("회원가입 " + MessageUtils.getMessage("M00246"));	// 회원가입 포인트
		} else if (mode.equals("admin")) { // 관리자 적립
			if (point.getPoint() == 0) { 
				throw new RuntimeException("부여 " + MessageUtils.getMessage("M00246") + " 누락");
			}
			
			// 관리자인경우 관리자 아이디를 기록
			if (UserUtils.isUserLogin() == false) {
				point.setManagerUserId(UserUtils.getManagerId());
			}
		} else if (mode.equals("return")) {
			
			// 관리자인경우 관리자 아이디를 기록
			if (UserUtils.isUserLogin() == false) {
				point.setManagerUserId(UserUtils.getManagerId());
			}
			
			//point.setReason(MessageUtils.getMessage("M01286"));
		} 
		
		if (point.getPoint() != 0) {
			
			OrderPointParam orderPointParam = new OrderPointParam();
			orderPointParam.setUserId(point.getUserId());
            orderPointParam.setPointType(point.getPointType());
			List<Point> earnPoints = pointMapper.getAvailablePointListByParam(orderPointParam);
			
			String createdDate = DateUtils.getToday(Const.DATETIME_FORMAT);
			//point.setPointId(sequenceService.getId("OP_POINT"));
			point.setSavedType("2"); // 개별 지급
			point.setSavedYear(createdDate.substring(0, 4));
			point.setSavedMonth(createdDate.substring(4, 6));
			point.setSavedPoint(point.getPoint());
			point.setUserId(point.getUserId());
			point.setOrderCode(point.getOrderCode());
			point.setOrderSequence(point.getOrderSequence());
			point.setItemSequence(point.getItemSequence());
			point.setCreatedDate(createdDate);
			
			// 포인트 만료일 설정
			if (StringUtils.isEmpty(point.getExpirationDate()) || point.getExpirationDate().length() != 8) {
				int expirationMonth = PointUtils.getExpirationMonth(point.getPointType(), shopConfig);
				if (expirationMonth > 0) {
					point.setExpirationDate(DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), expirationMonth));
				} else {
					
					// 만료일 없으면 무제한 한 200년쯤 더해버려
					point.setExpirationDate(DateUtils.addYear(DateUtils.getToday(Const.DATE_FORMAT), 200));
				}
			}
			
			point.setInsert(true);
			PointUtils.pointProcessing(earnPoints, point);
			for(Point p : earnPoints) {
				if (p.isInsert() == true) {

					if (mode.equals("return")) {

						this.processReturnPoint(p);

					} else {
                        pointMapper.insertPoint(p);
                    }
				}
				
				if (p.isUpdate() == true) {
					pointMapper.updateAvailablePointByParam(p);
				}
			}
		}
	}

	private void processReturnPoint(Point point) {

		List<PointUsed> pointUsedList = pointMapper.getPointUsedListByOrderCode(point.getOrderCode());

		int returnPoint = point.getPoint();
		for (PointUsed pu : pointUsedList) {

			int remainingPoint = pu.getRemainingPoint();

			if (remainingPoint <= 0) {
				continue;
			}

			// 남은 사용된 포인트 업데이트

			int insertPoint = remainingPoint;

			if (remainingPoint > returnPoint ) {
				insertPoint = returnPoint;
				remainingPoint = remainingPoint - returnPoint;
			} else {
				remainingPoint = 0;
			}

			pu.setRemainingPoint(remainingPoint);
			pointMapper.updateRemainingPointByPointUsed(pu);

			point.setSavedPoint(insertPoint);
			point.setPoint(insertPoint);
			point.setExpirationDate(pointMapper.getPointExpirationDateByPointId(pu.getPointId()));
			pointMapper.insertPoint(point);

			returnPoint = returnPoint - insertPoint;

			if (returnPoint <= 0) {
				break;
			}
		}

	}


	@Override
	public void earnPoint(String mode, Point point) {
		
		if (point.getUserId() == 0) {
			throw new PointException("회원번호(userId) 누락");
		}
		
		Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
		this.earnPoint(mode, point, shopConfig);
	}
	
	@Override
	public void deleteShopPointConfig(PointConfig pointConfig) {
		pointMapper.deleteShopPointConfig(pointConfig);
	}
	
	@Override
	public int getPointHistoryCountByParam(PointParam pointParam) {
		return pointMapper.getPointHistoryCountByParam(pointParam);
	}
	
	@Override
	public List<PointHistory> getPointHistoryListByParam(PointParam pointParam) {
		return pointMapper.getPointHistoryListByParam(pointParam);
	}
	
	@Override
	public void insertPointAllUser(Point point) {
		Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
		
		// 포인트 만료일 설정
		if (StringUtils.isEmpty(point.getExpirationDate()) || point.getExpirationDate().length() != 8) {
			int expirationMonth = PointUtils.getExpirationMonth(point.getPointType(), shopConfig);
			if (expirationMonth > 0) {
				point.setExpirationDate(DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), expirationMonth));
			} else {
				
				// 만료일 없으면 무제한 한 200년쯤 더해버려
				point.setExpirationDate(DateUtils.addYear(DateUtils.getToday(Const.DATE_FORMAT), 200));
			}
		}
		pointMapper.insertPointAllUser(point);
		pointMapper.updatePointSequence();
	}
	
	@Override
	public void insertPointByExcel(Point point) {
		MultipartFile multipartFile = point.getFile();
		Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
		String expirationDate = "";
		int expirationMonth = PointUtils.getExpirationMonth(point.getPointType(), shopConfig);
		if (expirationMonth > 0) {
			expirationDate = DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), expirationMonth);
		} else {
			
			// 만료일 없으면 무제한 한 200년쯤 더해버려
			expirationDate = DateUtils.addYear(DateUtils.getToday(Const.DATE_FORMAT), 200);
		}
		
		if (multipartFile == null) {
			throw new UserException(MessageUtils.getMessage("M01532")); // 파일을 선택해 주세요. 
		}
		
		String fileName = multipartFile.getOriginalFilename();
		String fileExtension = FileUtils.getExtension(fileName);
		
		// 확장자 체크
		if (!(fileExtension.equalsIgnoreCase("xlsx"))) {
			throw new UserException(MessageUtils.getMessage("M01533"));	// 엑셀 파일(.xlsx)만 업로드가 가능합니다. 
		}
		
		// 용량체크 
		String maxUploadFileSize = "10";
		Long maxUploadSize = Long.parseLong(maxUploadFileSize) * 1000 * 1000;
		
		if (multipartFile.getSize() > maxUploadSize) {
			throw new UserException("Maximum upload file Size : " + maxUploadFileSize + "MB");
		}
			
		// 엑셀 셀 읽기 : http://poi.apache.org/spreadsheet/quick-guide.html#CellContents 자세한 건 여기서 확인 - skc
		XSSFWorkbook wb = null;
		
		String excelUploadReport = "";
		try {
			wb = new XSSFWorkbook(multipartFile.getInputStream());
			
			excelUploadReport += "<p class=\"upload_file\">" + multipartFile.getOriginalFilename() + "</p>\n";
			
			Sheet sheet = wb.getSheetAt(0);
			
			List<Point> points = new ArrayList<>();
			
			int cellIndex = -1;
			int rowCount = 0;
			for (Row row : sheet) {
				int rowIndex = row.getRowNum() + 1;
				
		    	if (row.getRowNum() < 1) {
		    		continue;
		    	}
		    	
		    	// 헤더 - 타이틀 가져오기 
		    	if (row.getRowNum() == 1) {
		    		for (Cell cell : row) {
		    			String title = ShopUtils.getString(cell);
//		    			if ("会員番号".equals(title)) {
		    			if ("ID".equals(title)) {
		    				cellIndex = cell.getColumnIndex();
		    				break;
		    			}
		    		}
		    		continue;
		    	}
		    	
		    	
		    	// 해당 로우의 셀 값이 전부 비어있는 경우는 SKIP
		    	if (PoiUtils.isEmptyAllCell(row)) {
		    		continue;
		    	}
		    	
		    	if (cellIndex == -1) {
//		    		throw new Exception("Column is not exist. (会員番号)");
		    		throw new Exception("ID 컬럼이 없습니다.");
		    	}
		    	
		    	Cell cell = row.getCell(cellIndex);
		    	
		    	String loginId = ShopUtils.getString(cell);
		    	User user = userMapper.getUserByLoginId(loginId);
		    	String createdDate = DateUtils.getToday(Const.DATETIME_FORMAT);
		    	
		    	try {
			    	Point savePoint = new Point();
			    	//savePoint.setPointId(0);
			    	savePoint.setPointType("point");;
			    	savePoint.setSavedType("3"); // 엑셀 지급 (지정회원)
					savePoint.setSavedYear(createdDate.substring(0, 4));
					savePoint.setSavedMonth(createdDate.substring(4, 6));
					savePoint.setSavedPoint(point.getPoint());
					savePoint.setPoint(point.getPoint());
					savePoint.setLoginId(loginId);
					savePoint.setUserId(user.getUserId());
					savePoint.setReason(point.getReason());
					savePoint.setManagerUserId(UserUtils.getManagerId());
					
					savePoint.setExpirationDate(expirationDate);
					savePoint.setCreatedDate(createdDate);
	
					points.add(savePoint);
			    	rowCount++;
		    	} catch (Exception e) {
		    		log.warn(e.getMessage(), e);
		    		continue;
		    	}
			} // row
			
			if (!points.isEmpty()) {
/*				int pointId = sequenceService.getId("OP_POINT");
				sequenceService.updateSequence(new Sequence("OP_POINT", pointId + points.size()));
				
				for (Point insertPoint : points) {
					insertPoint.setPointId(pointId);
					pointId++;
				}*/
				pointMapper.insertPointByList(points);
			}


		} catch (IOException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // 엑셀 파일 로드 시 오류가 발생하였습니다.
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // 엑셀 파일 로드 시 오류가 발생하였습니다. 
		}
	}
	
	@Override
	public void userLevelShippingCoupon() {
		List<UserLevel> list = userLevelMapper.getUserLevelListForGiftShippingCoupon();
		if (list == null) {
			return;
		}
		
		Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
		int expirationMonth = PointUtils.getExpirationMonth(PointUtils.SHIPPING_COUPON_CODE, shopConfig);
		
		String expirationDate = "";
		if (expirationMonth > 0) {
			expirationDate = DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), expirationMonth);
		} else {
			
			// 만료일 없으면 무제한 한 200년쯤 더해버려
			expirationDate = DateUtils.addYear(DateUtils.getToday(Const.DATE_FORMAT), 200);
		}
		
		if (StringUtils.isEmpty(expirationDate)) {
			System.out.println("BATCH-ERROR 회원 등급별 배송비 쿠폰 발행 에러 - 만료일 설정 안됨");
		} else {
		
			try {
				for(UserLevel userLevel : list) {
					
					userLevel.setShippingCouponReason(DateUtils.getToday("yyyy년MM월") + " 회원 등급별 배송비 쿠폰 발행");
					userLevel.setShippingCouponExpirationDate(expirationDate);
					pointMapper.insertShippingCouponByUserLevel(userLevel);

				}
			} catch(Exception e) {
				log.error("ERROR: {}", e.getMessage(), e);
				System.out.println("BATCH-ERROR 회원 등급별 배송비 쿠폰 발행 에러");
			}
			
		}
		
	}

	@Override
	public void insertPointPay(Point point) {
		try{
			String expirationDate = "";
			
			Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
			
			// 포인트 만료일 설정
			if (StringUtils.isEmpty(point.getExpirationDate()) || point.getExpirationDate().length() != 8) {
				int expirationMonth = PointUtils.getExpirationMonth(point.getPointType(), shopConfig);
				if (expirationMonth > 0) {
					expirationDate = DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), expirationMonth);
				} else {
					
					// 만료일 없으면 무제한 한 200년쯤 더해버려
					expirationDate = DateUtils.addYear(DateUtils.getToday(Const.DATE_FORMAT), 200);
				}
			}
			
			List<Point> points = new ArrayList<>();
			
			String createdDate = DateUtils.getToday(Const.DATETIME_FORMAT);
			
			
			for(long userId : point.getUserIds()) {
				Point savePoint = new Point();
		    	// savePoint.setPointId(0);
		    	savePoint.setPointType("point");
		    	savePoint.setSavedType("1"); // 선택회원 포인트 지급
				savePoint.setSavedYear(createdDate.substring(0, 4));
				savePoint.setSavedMonth(createdDate.substring(4, 6));
				savePoint.setSavedPoint(point.getPoint());
				savePoint.setPoint(point.getPoint());
				savePoint.setUserId(userId);
				savePoint.setReason(point.getReason());
				savePoint.setManagerUserId(UserUtils.getManagerId());
				savePoint.setExpirationDate(expirationDate);
				savePoint.setCreatedDate(createdDate);
				
				points.add(savePoint);
			}
			
			if (!points.isEmpty()) {
				/*int pointId = sequenceService.getId("OP_POINT");
				sequenceService.updateSequence(new Sequence("OP_POINT", pointId + points.size()));
				
				for (Point insertPoint : points) {
					insertPoint.setPointId(pointId);
					pointId++;
				}*/
				pointMapper.insertPointByList(points);
			}
		}catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new UserException(e.getMessage());
		}
	}

	@Override
	public List<OrderPoint> getOrderPointByParam(OrderPointParam orderPointParam) {
		return pointMapper.getOrderPointByParam(orderPointParam);
	}

    @Override
    public void savePointByOrderPointParam(OrderPointParam orderPointParam) {
        Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);

        long userId = orderPointParam.getUserId();
        String pointType = "point";

        OrderPointParam param = new OrderPointParam();
        param.setUserId(userId);
        param.setPointSaveAfterDay(shopConfig.getPointSaveAfterDay());
        // 마일리지만...
        if (PointUtils.DEFAULT_POINT_CODE.equals(pointType)) {

            // 적립 대상 목록 조회후 적립
            List<OrderPoint> pointSaveList = getOrderPointByParam(param);
            if (ValidationUtils.isNotNull(pointSaveList)) {
                Point point = new Point();
                point.setUserId(userId);

                for (OrderPoint orderPoint : pointSaveList) {
                    point.setPoint(orderPoint.getPoint());

                    String message = "주문번호:" + orderPoint.getOrderCode() + " 상품 " + MessageUtils.getMessage("M00246") + "적립";
                    point.setReason(message);

                    point.setOrderCode(orderPoint.getOrderCode());
                    point.setOrderSequence(orderPoint.getOrderSequence());
                    point.setItemSequence(orderPoint.getItemSequence());

                    orderMapper.updateEarnPoint(orderPoint);

                    // 포인 적립
                    point.setPointType(pointType);
                    this.earnPoint("order", point);
                }
            }

        }
    }
}
