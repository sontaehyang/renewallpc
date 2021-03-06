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
		 * ????????? ????????? ??????????????? ????????? ?????? ???????????? ???????????? ?????? ????????????.
		 * ???????????? ???????????? ???????????? ?????? ???????????? ????????? ?????????????????? ???????????? ????????????.
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
			
			// ????????? ????????????????????? ????????
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
			
			// ????????? ?????? ????????????
			if (minusPoints.isEmpty() && plusPoints.isEmpty()) {
				return;
			}
			
			// ????????? ??????????????? ?????? ???????
			if (!minusPoints.isEmpty() && !plusPoints.isEmpty()) {
			
				if (-minusPointAmount > plusPointAmount) {
					
					// ???????????? ???????????? ??? ???????????? - ??????????????? ??????????????? ???????????? 0?????? ??????
					for(Point point : plusPoints) {
						
						point.setUpdate(true);
						
						// ???????????? ???????????? ?????? ????????? ????????? ??????
						PointUtils.pointProcessing(minusPoints, point);
					}
				
				} else {
					
					// ?????? ???????????? ??? ?????? ?????? - ??????????????? ??????????????? ???????????? 0?????? ??????
					for(Point point : minusPoints) {
						
						point.setUpdate(true);
						
						// ?????? ???????????? ???????????? ????????? ????????? ??????
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
		 * ????????? - 11??? 59?????? ???????????? ??????.. ???????????? ????????????..
		 * [??????] ???????????? ?????? ????????? ????????? ?????? ???????????? ????????? ???????????? ??????????????????.
		 * 2016.4.12 ????????? ????????? ????????? ?????????????????? ???????????? ????????? ??????????????? ?????? ????????? ???????????? ??????????????? ??????
		 * 2016.4.13 ??????????????? ????????? ??????????????? ????????? ???????????? ????????? ??????????????? ????????????
		 */
		
		PointParam pointParam = new PointParam();
		pointParam.setSearchStartDate(DateUtils.getToday(Const.DATE_FORMAT));
		
		List<Integer> userIds = pointMapper.getExpirationPointTargetListByParam(pointParam);
		if (userIds == null) {
			return;
		}
		
		System.out.println("BATCH ????????? ?????? - ????????? ["+ userIds.size() +"]");
		
		int i = 0;
		for(Integer userId : userIds) {
			
			System.out.println("BATCH ????????? ?????? - ?????? ID ["+ userId +"]");
			
			User user = null;
			
			try {
				user = userMapper.getUserByUserIdNotStatusCode(userId);
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR ????????? ?????? - ???????????? ?????? ?????? [{}]", userId, e);
			}

			if (user == null) {
				continue;
			}
			
			try {
				
				// ??????
				this.pointTheorem(user);
				
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR ????????? ?????? - ????????? ?????? ?????? [{}]", userId, e);
			}
			
			try {
				
				// ???????????? ????????? ???????????? ??????
				pointParam.setUserId(userId);
				List<Point> points = pointMapper.getExpirationListByParam(pointParam);
				if (points == null) {
					continue;
				}
				
				// ????????? ???????????? ??????
				HashMap<String, List<Point>> pointGroup = new HashMap<String, List<Point>>();
				for(Point point : points) {
					
					List<Point> temps = pointGroup.get(point.getPointType());
					if (temps == null) {
						temps = new ArrayList<>();
					}
					
					temps.add(point);
					pointGroup.put(point.getPointType(), temps);
					
				}
				
				// ????????? ???????????? ??????, ???????????? ????????? ??????
				for(String pointType : pointGroup.keySet()) {
					
					List<Point> temps = pointGroup.get(pointType);
					ExpirationPoint expirationPoint = new ExpirationPoint(temps, pointType, false);
					
					
					// ???????????? ?????? ????????? ???????
					if (expirationPoint.getExpirationPoints() != null) {
						
						int groupId = sequenceService.getId("OP_POINT_USED_GROUP_ID"); 
						for(Point point : expirationPoint.getExpirationPoints()) {
							
							String message = "???????????? ????????? ?????? ??????";
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
				log.error("ERROR: BATCH-ERROR ????????? ?????? - ????????? ???????????? ?????? [{}]", userId, e);
			}
		}
		
		System.out.println("BATCH ????????? ?????? - ???????????? ["+ i +"???]");
	}
	
	@Override
	public void expirationPointSendMessage() {
		
		String templateId = "expiration_point";
		MailConfig mailConfig = mailConfigService.getMailConfigByTemplateId(templateId);
		if (mailConfig == null) {
			System.out.println("BATCH ????????? ?????? ?????? ???????????? - ????????? ?????? ??????");
			return;
		}
		
		if (!"Y".equals(mailConfig.getBuyerSendFlag())) {
			System.out.println("BATCH ????????? ?????? ?????? ???????????? - ???????????? ?????? ??????[???????????????]");
			return;
		}
		
		PointParam pointParam = new PointParam();
		List<Integer> userIds = pointMapper.getExpirationPointSendMessageTargetListAll();
		if (userIds == null) {
			return;
		}
		
		Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
		
		System.out.println("BATCH ????????? ?????? ?????? - ????????? ["+ userIds.size() +"]");
		
		int i = 0;
		for(Integer userId : userIds) {
			
			System.out.println("BATCH ????????? ?????? ?????? - ?????? ID ["+ userId +"]");
			
			User user = null;
			
			try {
				user = userMapper.getUserByUserId(userId);
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR ????????? ?????? ?????? - ???????????? ?????? ?????? [{}]", userId, e);
			}

			if (user == null) {
				continue;
			}
			
			try {
				
				// ??????
				this.pointTheorem(user);
				
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR ????????? ?????? ?????? - ????????? ?????? ?????? [{}]", userId, e);
			}
			
					
			try {
				
				if (!"9".equals(user.getStatusCode())) {
					log.debug("ERROR: BATCH-ERROR ????????? ?????? ?????? - ?????? ?????? 9 ?????? [{}]", userId);
					continue;
				}
				
				UserDetail userDetail = (UserDetail) user.getUserDetail();
				
				// ?????? ????????????
				if (ServiceType.LOCAL) {
					//user.setEmail("cjh@onlinepowers.com");
				}
				
				if ("0".equals(userDetail.getReceiveEmail()) && StringUtils.isEmpty(user.getEmail()) == false) {
					
					pointParam.setUserId(userId);
					List<Point> list = pointMapper.getExpirationSendMessageListByParam(pointParam);
					if (list == null) {
						System.out.println("BATCH-ERROR ????????? ?????? ?????? - ???????????? ????????? ?????? [" + userId + "]");
						continue;
					}
					
					HashMap<String,Integer> pointMap = new HashMap<>();
					for(Point point : list) {
						pointMap.put(point.getPointType(), point.getPoint());
					}
					
					if (!pointMap.isEmpty()) {
						
						MemberExpirationPointMail mail = new MemberExpirationPointMail(mailConfigService.getMailConfigByTemplateId(templateId), user, DateUtils.getToday("yyyy???MM???dd???"), DateUtils.getToday("yyyy???MM???dd???"), pointMap, shopConfig);
						MailConfig mConfig = mail.getMailConfig();
						
						SendMailLog sendMailLog = new SendMailLog();
						sendMailLog.setUserId(userId);
						sendMailLog.setSendType(templateId);
						
						sendMailLogService.sendMail(mConfig, sendMailLog, user.getEmail(), user.getUserName(), shopConfig);
						
						i++;
					}
				} else {
					log.info("BATCH-ERROR ????????? ?????? ?????? - ????????? ?????? ?????? ?????? [" + userId + "]");
				}
				
			} catch(Exception e) {
				log.error("ERROR: BATCH-ERROR ????????? ?????? ?????? [{}]", userId, e);
			}
		}

		log.info("BATCH ????????? ?????? ?????? - ???????????? [{}}???]", i);
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

        String message = MessageUtils.getMessage("M00246") + "???";
        String exMessage = "";

        if (pointUsed.getPoint() <= 0) {
            if (pointUsed.getDetails() != null && pointUsed.getDetails().contains(MessageUtils.getMessage("M00246") + " ??????")) {
                exMessage = " ( ?????? ????????? " + MessageUtils.getMessage("M00246") + " : " + pointUsed.getPoint() + " )";
                throw new PointException("?????? " + message + " ??????????????????." + exMessage);
            }
            throw new PointException("?????? " + message + " ??????????????????." + exMessage);
        }

        User user = userMapper.getUserByUserId(userId);
        if (ValidationUtils.isNull(user)) {
            throw new PointException("?????? " + message + " ??????????????????.");
        }

		AvailablePoint availablePoint = this.getAvailablePointByUserId(userId, pointType);

		
		pointUsed.setUsedType("1");
		pointUsed.setPointUsedGroupId(sequenceService.getId("OP_POINT_USED_GROUP_ID"));
		pointUsed.setCreatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
		
		// ????????? ??????????????? ????????? ?????? ????????? ????????? ??????
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
			
			// ?????? ???????????? ????????? ??????
			for(PointUsed pu : newPointUsedList) {
				int pointUsedId = sequenceService.getId("OP_POINT_USED");
				pu.setPointUsedId(pointUsedId);
				pointMapper.insertPointUsed(pu);
			}
			
		} catch(PointException pe) {
			throw new RuntimeException("?????????????????? ????????? ?????????????????????. ?????? " + message + " ??????????????? ????????? ??????????????????.");
		}
		
	}

	private void earnPoint(String mode, Point point, Config shopConfig) {
		if (mode.equals("join")) { // ?????? ??????
			point.setPoint(shopConfig.getPointJoin());
			point.setReason("???????????? " + MessageUtils.getMessage("M00246"));	// ???????????? ?????????
		} else if (mode.equals("admin")) { // ????????? ??????
			if (point.getPoint() == 0) { 
				throw new RuntimeException("?????? " + MessageUtils.getMessage("M00246") + " ??????");
			}
			
			// ?????????????????? ????????? ???????????? ??????
			if (UserUtils.isUserLogin() == false) {
				point.setManagerUserId(UserUtils.getManagerId());
			}
		} else if (mode.equals("return")) {
			
			// ?????????????????? ????????? ???????????? ??????
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
			point.setSavedType("2"); // ?????? ??????
			point.setSavedYear(createdDate.substring(0, 4));
			point.setSavedMonth(createdDate.substring(4, 6));
			point.setSavedPoint(point.getPoint());
			point.setUserId(point.getUserId());
			point.setOrderCode(point.getOrderCode());
			point.setOrderSequence(point.getOrderSequence());
			point.setItemSequence(point.getItemSequence());
			point.setCreatedDate(createdDate);
			
			// ????????? ????????? ??????
			if (StringUtils.isEmpty(point.getExpirationDate()) || point.getExpirationDate().length() != 8) {
				int expirationMonth = PointUtils.getExpirationMonth(point.getPointType(), shopConfig);
				if (expirationMonth > 0) {
					point.setExpirationDate(DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), expirationMonth));
				} else {
					
					// ????????? ????????? ????????? ??? 200?????? ????????????
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

			// ?????? ????????? ????????? ????????????

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
			throw new PointException("????????????(userId) ??????");
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
		
		// ????????? ????????? ??????
		if (StringUtils.isEmpty(point.getExpirationDate()) || point.getExpirationDate().length() != 8) {
			int expirationMonth = PointUtils.getExpirationMonth(point.getPointType(), shopConfig);
			if (expirationMonth > 0) {
				point.setExpirationDate(DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), expirationMonth));
			} else {
				
				// ????????? ????????? ????????? ??? 200?????? ????????????
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
			
			// ????????? ????????? ????????? ??? 200?????? ????????????
			expirationDate = DateUtils.addYear(DateUtils.getToday(Const.DATE_FORMAT), 200);
		}
		
		if (multipartFile == null) {
			throw new UserException(MessageUtils.getMessage("M01532")); // ????????? ????????? ?????????. 
		}
		
		String fileName = multipartFile.getOriginalFilename();
		String fileExtension = FileUtils.getExtension(fileName);
		
		// ????????? ??????
		if (!(fileExtension.equalsIgnoreCase("xlsx"))) {
			throw new UserException(MessageUtils.getMessage("M01533"));	// ?????? ??????(.xlsx)??? ???????????? ???????????????. 
		}
		
		// ???????????? 
		String maxUploadFileSize = "10";
		Long maxUploadSize = Long.parseLong(maxUploadFileSize) * 1000 * 1000;
		
		if (multipartFile.getSize() > maxUploadSize) {
			throw new UserException("Maximum upload file Size : " + maxUploadFileSize + "MB");
		}
			
		// ?????? ??? ?????? : http://poi.apache.org/spreadsheet/quick-guide.html#CellContents ????????? ??? ????????? ?????? - skc
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
		    	
		    	// ?????? - ????????? ???????????? 
		    	if (row.getRowNum() == 1) {
		    		for (Cell cell : row) {
		    			String title = ShopUtils.getString(cell);
//		    			if ("????????????".equals(title)) {
		    			if ("ID".equals(title)) {
		    				cellIndex = cell.getColumnIndex();
		    				break;
		    			}
		    		}
		    		continue;
		    	}
		    	
		    	
		    	// ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
		    	if (PoiUtils.isEmptyAllCell(row)) {
		    		continue;
		    	}
		    	
		    	if (cellIndex == -1) {
//		    		throw new Exception("Column is not exist. (????????????)");
		    		throw new Exception("ID ????????? ????????????.");
		    	}
		    	
		    	Cell cell = row.getCell(cellIndex);
		    	
		    	String loginId = ShopUtils.getString(cell);
		    	User user = userMapper.getUserByLoginId(loginId);
		    	String createdDate = DateUtils.getToday(Const.DATETIME_FORMAT);
		    	
		    	try {
			    	Point savePoint = new Point();
			    	//savePoint.setPointId(0);
			    	savePoint.setPointType("point");;
			    	savePoint.setSavedType("3"); // ?????? ?????? (????????????)
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
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // ?????? ?????? ?????? ??? ????????? ?????????????????????.
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // ?????? ?????? ?????? ??? ????????? ?????????????????????. 
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
			
			// ????????? ????????? ????????? ??? 200?????? ????????????
			expirationDate = DateUtils.addYear(DateUtils.getToday(Const.DATE_FORMAT), 200);
		}
		
		if (StringUtils.isEmpty(expirationDate)) {
			System.out.println("BATCH-ERROR ?????? ????????? ????????? ?????? ?????? ?????? - ????????? ?????? ??????");
		} else {
		
			try {
				for(UserLevel userLevel : list) {
					
					userLevel.setShippingCouponReason(DateUtils.getToday("yyyy???MM???") + " ?????? ????????? ????????? ?????? ??????");
					userLevel.setShippingCouponExpirationDate(expirationDate);
					pointMapper.insertShippingCouponByUserLevel(userLevel);

				}
			} catch(Exception e) {
				log.error("ERROR: {}", e.getMessage(), e);
				System.out.println("BATCH-ERROR ?????? ????????? ????????? ?????? ?????? ??????");
			}
			
		}
		
	}

	@Override
	public void insertPointPay(Point point) {
		try{
			String expirationDate = "";
			
			Config shopConfig = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
			
			// ????????? ????????? ??????
			if (StringUtils.isEmpty(point.getExpirationDate()) || point.getExpirationDate().length() != 8) {
				int expirationMonth = PointUtils.getExpirationMonth(point.getPointType(), shopConfig);
				if (expirationMonth > 0) {
					expirationDate = DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), expirationMonth);
				} else {
					
					// ????????? ????????? ????????? ??? 200?????? ????????????
					expirationDate = DateUtils.addYear(DateUtils.getToday(Const.DATE_FORMAT), 200);
				}
			}
			
			List<Point> points = new ArrayList<>();
			
			String createdDate = DateUtils.getToday(Const.DATETIME_FORMAT);
			
			
			for(long userId : point.getUserIds()) {
				Point savePoint = new Point();
		    	// savePoint.setPointId(0);
		    	savePoint.setPointType("point");
		    	savePoint.setSavedType("1"); // ???????????? ????????? ??????
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
        // ???????????????...
        if (PointUtils.DEFAULT_POINT_CODE.equals(pointType)) {

            // ?????? ?????? ?????? ????????? ??????
            List<OrderPoint> pointSaveList = getOrderPointByParam(param);
            if (ValidationUtils.isNotNull(pointSaveList)) {
                Point point = new Point();
                point.setUserId(userId);

                for (OrderPoint orderPoint : pointSaveList) {
                    point.setPoint(orderPoint.getPoint());

                    String message = "????????????:" + orderPoint.getOrderCode() + " ?????? " + MessageUtils.getMessage("M00246") + "??????";
                    point.setReason(message);

                    point.setOrderCode(orderPoint.getOrderCode());
                    point.setOrderSequence(orderPoint.getOrderSequence());
                    point.setItemSequence(orderPoint.getItemSequence());

                    orderMapper.updateEarnPoint(orderPoint);

                    // ?????? ??????
                    point.setPointType(pointType);
                    this.earnPoint("order", point);
                }
            }

        }
    }
}
