package saleson.shop.coupon;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.notification.ApplicationInfoService;
import saleson.common.notification.UnifiedMessagingService;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.ConfigMapper;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.domain.*;
import saleson.shop.coupon.support.CouponListParam;
import saleson.shop.coupon.support.CouponParam;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemBase;
import saleson.shop.order.domain.BuyItem;
import saleson.shop.ums.UmsService;
import saleson.shop.ums.support.BirthdayCoupon;
import saleson.shop.user.domain.UserDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("couponService")
public class CouponServiceImpl implements CouponService {
	private static final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);
	
	@Autowired
	CouponMapper couponMapper;
	
	@Autowired
	SequenceService sequenceService;


	@Autowired
	ConfigMapper configMapper;

	@Autowired
	private UmsService umsService;

	@Autowired
	private UnifiedMessagingService unifiedMessagingService;

	@Autowired
	private ApplicationInfoService applicationInfoService;

	@Override
	public int getCouponAppliesItemCountParamForCoupon(CouponParam couponParam) {
		return couponMapper.getCouponAppliesItemCountParamForCoupon(couponParam);
	}
	
	@Override
	public Coupon getCouponAppliesItemListParamForCoupon(CouponParam couponParam) {
		return couponMapper.getCouponAppliesItemListParamForCoupon(couponParam);
	}
	
	@Override
	public int getCouponAppliesItemCountParamForCouponUser(CouponParam couponParam) {
		return couponMapper.getCouponAppliesItemCountParamForCouponUser(couponParam);
	}
	
	@Override
	public Coupon getCouponAppliesItemListParamForCouponUser(CouponParam couponParam) {
		return couponMapper.getCouponAppliesItemListParamForCouponUser(couponParam);
	}
	
	@Override
	public int getDownloadUserCouponCountByUserId(long userId) {
		
		UserCouponParam userCouponParam = new UserCouponParam();
		userCouponParam.setUserId(userId);
		
		return couponMapper.getDownloadUserCouponCountByUserCouponParam(userCouponParam);
	}
	
	@Override
	public int getDownloadUserCouponCountByUserCouponParam(UserCouponParam userCouponParam) {
		return couponMapper.getDownloadUserCouponCountByUserCouponParam(userCouponParam);
	}
	
	@Override
	public List<CouponUser> getDownloadUserCouponListByUserCouponParam(UserCouponParam userCouponParam) {
		return couponMapper.getDownloadUserCouponListByUserCouponParam(userCouponParam);
	}
	
	@Override
	public int getCompletedUserCouponCountByUserCouponParam(UserCouponParam userCouponParam) {
		return couponMapper.getCompletedUserCouponCountByUserCouponParam(userCouponParam);
	}
	
	@Override
	public List<CouponUser> getCompletedUserCouponListByUserCouponParam(UserCouponParam userCouponParam) {
		return couponMapper.getCompletedUserCouponListByUserCouponParam(userCouponParam);
	}

	@Override
	public List<Coupon> getUserDownloadableCouponListByParam(UserCouponParam userCouponParam) {
		return couponMapper.getUserDownloadableCouponListByParam(userCouponParam);
	}

	@Override
	public int getUserDownloadableCouponListCountByParam(UserCouponParam userCouponParam) {
		return couponMapper.getUserDownloadableCouponListCountByParam(userCouponParam);
	}
	
	@Override
	public int getCouponTargetUserCountByCoupon(Coupon coupon) {
		return couponMapper.getCouponTargetUserCountByCoupon(coupon);
	}
	
	@Override
	public List<User> getCouponTargetUserListByCoupon(Coupon coupon) {
		return couponMapper.getCouponTargetUserListByCoupon(coupon);
	}
	
	@Override
	public int getCouponTargetItemCountByCoupon(Coupon coupon) {
		return couponMapper.getCouponTargetItemCountByCoupon(coupon);
	}
	
	@Override
	public List<Item> getCouponTargetItemListByCoupon(Coupon coupon) {
		return couponMapper.getCouponTargetItemListByCoupon(coupon);
	}
	
	@Override
	public int getCouponCountByParamForManager(CouponParam couponParam) {
		return couponMapper.getCouponCountByParamForManager(couponParam);
	}
	
	@Override
	public List<Coupon> getCouponListByParamForManager(CouponParam couponParam) {
		return couponMapper.getCouponListByParamForManager(couponParam);
	}
	
	@Override
	public int getCouponUserCountByParamForManager(CouponParam couponParam) {
		return couponMapper.getCouponUserCountByParamForManager(couponParam);
	}
	
	@Override
	public List<CouponUser> getCouponUserListByParamForManager(CouponParam couponParam) {
		return couponMapper.getCouponUserListByParamForManager(couponParam);
	}
	
	@Override
	public Coupon getCouponById(int couponId) {
		return couponMapper.getCouponById(couponId);
	}
	
	@Override
	public List<OrderCoupon> getUserCouponListForItemTarget(List<BuyItem> buyItems, long userId, String viewTarget) {
		
		if (userId == 0) {
			return null;
		}
			
		UserCouponParam userCouponParam = new UserCouponParam();
		
		if (buyItems == null) {
			return null;
		}
		
		userCouponParam.setViewTarget(viewTarget);
		List<Integer> itemIds = new ArrayList<>();
		for (BuyItem buyItem : buyItems) {
			itemIds.add(buyItem.getItemId());
		}
		userCouponParam.setItemIds(itemIds);

		if (userCouponParam.getItemIds().isEmpty()) {
			return null;
		}
		
		userCouponParam.setConditionType("TARGET_SELECT_ITEMS");
		userCouponParam.setUserId(userId);
		return couponMapper.getAvailableCouponListByParam(userCouponParam);
		
	}
	
	@Override
	public List<OrderCoupon> getUserCouponListForItemAll(long userId, String viewTarget) {
		
		UserCouponParam userCouponParam = new UserCouponParam();
		userCouponParam.setViewTarget(viewTarget);
		userCouponParam.setUserId(userId);
		userCouponParam.setConditionType("TARGET_ALL_ITEMS");
		
		return couponMapper.getAvailableCouponListByParam(userCouponParam);
	
	}
	
	@Override
	public void updateCouponUserUseProcessByOrderCouponUser(OrderCoupon orderCoupon) {
		couponMapper.updateCouponUserUseProcessByOrderCouponUser(orderCoupon);
	}
	
	@Override
	public CouponOffline getCouponOfflineByOfflineCode(CouponOffline couponOffline) {
		return couponMapper.getCouponOfflineByOfflineCode(couponOffline);
	}
	
	@Override
	public void insertCoupon(Coupon coupon) {
		if (coupon.getCouponFlag().isEmpty()) {
			coupon.setCouponFlag("N");
		}

		if ( !"3".equals(coupon.getCouponTargetTimeType()) ) {
			coupon.setCouponBirthday(null);
		}
		
		int couponId = sequenceService.getId("OP_COUPON");
		coupon.setCouponId(couponId);
		couponMapper.insertCoupon(coupon);
	}
	
	@Override
	public void updateCoupon(Coupon coupon) {
		couponMapper.updateCoupon(coupon);
	}
	
	@Override
	public void deleteListData(CouponListParam couponListParam) {
		if (couponListParam.getId() != null) {

			for (String itemId : couponListParam.getId()) {
				
				// DATA_STATUS_CODE = '9'로 업데이트 한다.
				Coupon coupon = new Coupon();
				coupon.setCouponId(Integer.parseInt(itemId));
				coupon.setDataStatusCode("9");
				coupon.setUpdateUserName(UserUtils.getManagerName());
				
				couponMapper.deleteCoupon(coupon);
			}
			
		}
	}
	
	@Override
	public void updateCouponPublish(Coupon coupon) {
		coupon.setUpdateUserName(UserUtils.getManagerName());
		coupon.setDataStatusCode("1");
		couponMapper.updateCouponPublish(coupon);
		
		// 회원 선택 추가 - OP_COUPON_TARGET_USER 테이블에 데이터 생성
		if ("2".equals(coupon.getCouponTargetUserType())) {
			
			if (ValidationUtils.isNull(coupon.getCouponTargetUsers())) {
				throw new UserException("회원 선택 추가 조건이 지정되지 않았습니다.");
			}
			
			couponMapper.insertCouponTargetUser(coupon);
		}
		
		// 상품 선택 추가 - OP_COUPON_TARGET_ITEM 테이블에 데이터 생성
		if ("2".equals(coupon.getCouponTargetItemType())) {
			
			if (ValidationUtils.isNull(coupon.getCouponTargetItems())) {
				throw new UserException("상품 선택 추가 조건이 지정되지 않았습니다.");
			}
			
			couponMapper.insertCouponTargetItem(coupon);
		}
		
		boolean result = false;
		
		//오프라인 쿠폰 발행[2017-09-11]minae.yun
		if ( "Y".equals(coupon.getCouponOfflineFlag()) ) {
			CouponOffline couponOffline = new CouponOffline();
			couponOffline.setCouponId(coupon.getCouponId());
			couponOffline.setCouponAmount(coupon.getCouponDownloadLimit());
			result = insertCouponOffline(couponOffline);
		} else {
			result = true;
		}

		if (!result) {
			throw new UserException("쿠폰 등록에 실패했습니다.");
		}


		// 생일쿠폰 발행 메시지 발송
		if ("3".equals(coupon.getCouponTargetTimeType())) {

			UserCouponParam userCouponParam = new UserCouponParam();

			if ("3".equals(coupon.getCouponTargetUserType())) {
				String[] userLevels = StringUtils.delimitedListToStringArray(coupon.getCouponTargetUserLevel(), "||");
				userCouponParam.setCouponTargetUserLevels(userLevels);
			}

			userCouponParam.setCouponBirthday(coupon.getCouponBirthday());
			List<CouponUser> couponUser = couponMapper.getBirthdayUserList(userCouponParam);

			String templateId = "birthday_coupon";
			Ums ums = umsService.getUms(templateId);
			if (umsService.isValidUms(ums)) {
				Config config = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
				couponUser.stream()
						.filter(list -> !StringUtils.isEmpty(list.getPhoneNumber()))
						.forEach(user -> {
							ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(user.getUserId());
							user.setCouponName(coupon.getCouponName());

							unifiedMessagingService.sendMessage(new BirthdayCoupon(ums, user, user.getUserId(), user.getPhoneNumber(), config, applicationInfo));
						});
			}
		}
	}
	
	@Override
	public void updateCouponDownloadStatus(Coupon coupon, String mode) {
		coupon.setUpdateUserName(UserUtils.getManagerName());
		coupon.setCouponFlag("start".equals(mode) ? "Y" : "N");
		couponMapper.updateCouponDownloadStatus(coupon);
	}
	
	@Override
	public int userCouponDownload(UserCouponParam userCouponParam) {
		
		UserDetail userDetail = UserUtils.getUserDetail();
		
		if (userCouponParam.getUserId() == 0) {
			userCouponParam.setUserId(UserUtils.getUserId());
		}
		userCouponParam.setUserLevelId(userDetail.getLevelId());
//		userCouponParam.setCouponUserId(sequenceService.getId("OP_COUPON_USER"));
		
		return couponMapper.insertDownloadCoupon(userCouponParam);
		
	}
	
	@Override
	public CouponCount getCouponUserCountByUserId(long userId) {
		return couponMapper.getCouponUserCountByUserId(userId);
	}
	
	@Override
	public List<Coupon> getCouponForItemView(UserCouponParam userCouponParam) {
		return couponMapper.getCouponForItemView(userCouponParam);
	}
	
	/**
	 * 회원 선택 정보 등록
	 * @param coupon
	 */
	@Override
	public void insertCouponTargetUser(Coupon coupon) {
		couponMapper.insertCouponTargetUser(coupon);
	}
	
	/**
	 * 해당 회원에게 쿠폰 발행
	 * @param userCouponParam
	 */
	@Override
	public void insertCouponTargetUserOne(UserCouponParam userCouponParam) {
		couponMapper.insertCouponTargetUserOne(userCouponParam);
	}
	
	/**
	 * 발행 시점에 따른 쿠폰 발급을 위한 SELECT
	 * @return
	 */
	@Override
	public List<Coupon> getCouponByTargetTimeType(UserCouponParam userCouponParam) {
		return couponMapper.getCouponByTargetTimeType(userCouponParam);
	}

	/**
	 * 오프라인 쿠폰 번호 생성
	 * @author [2017-09-11]minae.yun
	 * @param couponOffline
	 * @return
	 */
	private boolean insertCouponOffline(CouponOffline couponOffline) {
		
		boolean result = false;
		for (int i = 0; i < couponOffline.getCouponAmount(); i++) {
			couponOffline.setCouponOfflineId(sequenceService.getId("OP_COUPON_OFFLINE"));
			
			//오프라인 쿠폰번호 UUID생성
			String code = ShopUtils.getUUID().replace("-", "").substring(0, 16).toUpperCase();
			String offlineCode = code.substring(0, 4)+"-"+code.substring(4, 8)+"-"+code.substring(8, 12)+"-"+code.substring(12, 16);
			
			couponOffline.setCouponOfflineCode(offlineCode);
			result = couponMapper.insertCouponOffline(couponOffline);
		}
		
		return result;
	}
	
	/**
	 * 오프라인 쿠폰 사용상태로 전환
	 * @author [2017-09-11]minae.yun
	 * @param couponOffline
	 */
	public void updateCouponOffline(CouponOffline couponOffline) {
		couponMapper.updateCouponOffline(couponOffline);
	}
	
	public List<CouponOffline> getCouponOfflineListByCouponId(Integer couponId) {
		return couponMapper.getCouponOfflineListByCouponId(couponId);
	}
	
	@Override
	public int getUserCouponListForNewUserCoupon(UserCouponParam userCouponParam) {
		return couponMapper.getUserCouponListForNewUserCoupon(userCouponParam);
	}

	@Override
	public List<CouponUser> getCouponUserListByUserForManager(UserCouponParam userCouponParam) {

		int itemPerPage = 10;

		Pagination pagination
				= Pagination.getInstance(couponMapper.getCouponUserListCountByUserForManager(userCouponParam), itemPerPage);

		userCouponParam.setPagination(pagination);

		return couponMapper.getCouponUserListByUserForManager(userCouponParam);
	}

	@Override
	public List<ItemBase> insertItemExcelData(MultipartFile multipartFile) {

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
		String maxUploadFileSize = "20";
		Long maxUploadSize = Long.parseLong(maxUploadFileSize) * 1000 * 1000;

		if (multipartFile.getSize() > maxUploadSize) {
			throw new UserException("Maximum upload file Size : " + maxUploadFileSize + "MB");
		}

		// 엑셀 셀 읽기 : http://poi.apache.org/spreadsheet/quick-guide.html#CellContents 자세한 건 여기서 확인 - skc
		XSSFWorkbook wb = null;
		List<ItemBase> couponItems = null;
		try {
			wb = new XSSFWorkbook(multipartFile.getInputStream());

            // 쿠폰 상품 정보
            couponItems = processCouponItemExcelSheet(wb.getSheetAt(0));

		} catch (IOException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // 엑셀 파일 로드 시 오류가 발생하였습니다.
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // 엑셀 파일 로드 시 오류가 발생하였습니다.
		}

		return couponItems;
	}

    /**
     * 쿠폰 상품 정보
     * @param sheet
     * @return
     */
    @SuppressWarnings("static-access")
    private List<ItemBase> processCouponItemExcelSheet(XSSFSheet sheet) {

        if (sheet == null) {
            return new ArrayList<ItemBase>();
        }

        List<ItemBase> couponItems = new ArrayList<>();
        Coupon coupon = new Coupon();

        for (Row row : sheet) {

            if (row.getRowNum() < 2) {
                continue;
            }

            // 해당 로우의 셀 값이 전부 비어있는 경우는 SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }

            String itemUserCode = ShopUtils.getString(row.getCell(0));

            // 상품코드가 입력되지 않은 경우 SKIP
            if (StringUtils.isEmpty(itemUserCode)) {
                continue;
            }

			ItemBase item = new Item();
            item.setItemUserCode(itemUserCode);
			couponItems.add(item);

        } // row

        // 쿠폰 상품 data 생성
        if (!couponItems.isEmpty()) {
            coupon.setItems(couponItems);

            List<Item> existItems = couponMapper.getCouponExcelItemListByCoupon(coupon);
			List<ItemBase> excludeItems = new ArrayList<>();

            // 미등록 상품 찾기
			for (ItemBase couponItem : couponItems) {
				boolean check = false;

				for (Item existItem : existItems) {
					if (couponItem.getItemUserCode().equals(existItem.getItemUserCode())) {
						couponItem.setItemId(existItem.getItemId());
						check = true;
						continue;
					}
				}

				if (!check) {
					excludeItems.add(couponItem);
				}
			}

			// 미등록 상품 존재시 에러 메세지
            if (!excludeItems.isEmpty()) {
                StringBuffer errorMessage = new StringBuffer();
                errorMessage.append("상품이 존재하지 않습니다.");
                errorMessage.append(" (");

                int count = 1;
                for (ItemBase excludeItem : excludeItems) {
                    errorMessage.append(excludeItem.getItemUserCode());
                    if (count == excludeItems.size()) {
                        errorMessage.append(")");
                    } else {
                        errorMessage.append(", ");
                    }
                    count++;
                }

                throw new UserException(errorMessage.toString());
            }
        }

        return couponItems;
    }

	@Override
	public int getDirectInputValueCount(String value) {
		return couponMapper.getDirectInputValueCount(value);
	}

	@Override
	public boolean downloadDirectInputCoupon(String value) {

		if (StringUtils.isEmpty(value)) {
			return false;
		}

		value = value.toUpperCase();

		UserDetail userDetail = UserUtils.getUserDetail();
		UserCouponParam userCouponParam = new UserCouponParam();
		userCouponParam.setUserId(UserUtils.getUserId());
		userCouponParam.setUserLevelId(userDetail.getLevelId());
		userCouponParam.setDirectInputFlag(true);
		userCouponParam.setDirectInputValue(value);
		List<Coupon> coupons = getUserDownloadableCouponListByParam(userCouponParam);

		if (coupons != null && !coupons.isEmpty()) {
			Coupon coupon = coupons.get(0);

			UserCouponParam downloadParam = new UserCouponParam();
			downloadParam.setCouponId(coupon.getCouponId());
			downloadParam.setDirectInputFlag(true);
			downloadParam.setDirectInputValue(value);
			return userCouponDownload(downloadParam) > 0;
		}

		return false;
	}

	@Override
	public String getOfflineCode(String offlineCode) {

    	if (StringUtils.isEmpty(offlineCode)) {
    		return "";
		}
    	int offlineCodeLength = 16;

		if (offlineCode.length() >= offlineCodeLength) {
			offlineCode = offlineCode
					.replaceAll("-","")
					.replaceAll("(.{4})","$1-")
					.substring(0,19);
		}

		return offlineCode;
	}
}
