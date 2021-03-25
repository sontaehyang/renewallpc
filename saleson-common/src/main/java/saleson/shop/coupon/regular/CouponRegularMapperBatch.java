package saleson.shop.coupon.regular;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.MapperBatch;

import saleson.shop.coupon.domain.Coupon;

@MapperBatch("couponRegularMapperBatch")
public interface CouponRegularMapperBatch {
	
	/**
	 * 정기발행쿠폰 배치용 리스트
	 */
	List<Coupon> getCouponRegularBatchList();
	
	/**
	 * 정기발행쿠폰을 select하여 쿠폰테이블에 insert
	 */
	void insertSelectCouponRegular(Coupon coupon);

}
