package saleson.shop.coupon.regular;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.ValidationUtils;

import saleson.common.utils.UserUtils;
import saleson.shop.coupon.CouponMapper;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.domain.CouponOffline;
import saleson.shop.coupon.support.CouponListParam;
import saleson.shop.coupon.support.CouponParam;

@Service("couponRegularService")
public class CouponRegularServiceImpl implements CouponRegularService {
	private static final Logger log = LoggerFactory.getLogger(CouponRegularServiceImpl.class);
	
	@Autowired
	CouponRegularMapper couponRegularMapper;
	
	@Autowired
	CouponMapper couponMapper;
	
	@Autowired
	SequenceService sequenceService;

	@Override
	public int getCouponRegularListCount(CouponParam couponParam) {
		return couponRegularMapper.getCouponRegularListCount(couponParam);
	}
	
	@Override
	public List<Coupon> getCouponRegularList(CouponParam couponParam) {
		return couponRegularMapper.getCouponRegularList(couponParam);
	}
	
	@Override
	public Coupon getCouponRegularById(int couponId) {
		return couponRegularMapper.getCouponRegularById(couponId);
	}

	@Override
	public void insertCouponRegular(Coupon coupon) {
		if (coupon.getCouponFlag().isEmpty()) {
			coupon.setCouponFlag("N");
		}

		if ( !"3".equals(coupon.getCouponTargetTimeType()) ) {
			coupon.setCouponBirthday(null);
		}
		
		int couponId = sequenceService.getId("OP_COUPON");
		coupon.setCouponId(couponId);
		couponRegularMapper.insertCouponRegular(coupon);
	}
	
	@Override
	public void updateCouponRegular(Coupon coupon) {
		couponRegularMapper.updateCouponRegular(coupon);
	}
	
	@Override
	public void deleteListData(CouponListParam couponListParam) {
		if (couponListParam.getId() != null) {

			for (String itemId : couponListParam.getId()) {
				
				// DATA_STATUS_CODE = '9'??? ???????????? ??????.
				Coupon coupon = new Coupon();
				coupon.setCouponId(Integer.parseInt(itemId));
				coupon.setDataStatusCode("9");
				coupon.setUpdateUserName(UserUtils.getManagerName());
				
				couponRegularMapper.deleteCouponRegular(coupon);
			}
			
		}
	}
	
	@Override
	public void updateCouponPublish(Coupon coupon) {
		coupon.setUpdateUserName(UserUtils.getManagerName());
		coupon.setDataStatusCode("1");
		couponRegularMapper.updateCouponPublish(coupon);
		
		// ?????? ?????? ?????? - OP_COUPON_TARGET_USER ???????????? ????????? ??????
		if ("2".equals(coupon.getCouponTargetUserType())) {
			
			if (ValidationUtils.isNull(coupon.getCouponTargetUsers())) {
				throw new UserException("?????? ?????? ?????? ????????? ???????????? ???????????????.");
			}
			
			couponMapper.insertCouponTargetUser(coupon);
		}
		
		// ?????? ?????? ?????? - OP_COUPON_TARGET_ITEM ???????????? ????????? ??????
		if ("2".equals(coupon.getCouponTargetItemType())) {
			
			if (ValidationUtils.isNull(coupon.getCouponTargetItems())) {
				throw new UserException("?????? ?????? ?????? ????????? ???????????? ???????????????.");
			}
			
			couponMapper.insertCouponTargetItem(coupon);
		}
		
	}
	
	@Override
	public void updateCouponDownloadStatus(Coupon coupon, String mode) {
		coupon.setUpdateUserName(UserUtils.getManagerName());
		coupon.setCouponFlag("start".equals(mode) ? "Y" : "N");
		couponRegularMapper.updateCouponDownloadStatus(coupon);
	}
	
}
