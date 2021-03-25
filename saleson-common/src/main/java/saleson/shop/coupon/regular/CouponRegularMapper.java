
package saleson.shop.coupon.regular;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.CouponParam;

@Mapper("couponRegularMapper")
public interface CouponRegularMapper {
	
	/**
	 * 쿠폰 단일 정보 조회
	 * @param couponId
	 * @return
	 */
	public Coupon getCouponRegularById(int couponId);
	
	/**
	 * 관리자 등록 쿠폰 - 카운트
	 * @param couponParam
	 * @return
	 */
	public int getCouponRegularListCount(CouponParam couponParam);
	
	/**
	 * 관리자 등록 쿠폰 - 리스트
	 * @param couponParam
	 * @return
	 */
	public List<Coupon> getCouponRegularList(CouponParam couponParam);
	
	/**
	 * 정기발행 쿠폰 배치확인 리스트
	 * @param couponParam
	 * @return
	 */
	public List<Coupon> getCouponRegularBatchList(Coupon coupon);
	
	/**
	 * 쿠폰 등록
	 * @param coupon
	 */
	public void insertCouponRegular(Coupon coupon);
	
	/**
	 * 쿠폰 수정
	 * @param coupon
	 */
	public void updateCouponRegular(Coupon coupon);
	
	/**
	 * 쿠폰 삭제
	 * @param coupon
	 */
	public void deleteCouponRegular(Coupon coupon);
	
	/**
	 * 회원 선택 정보 등록
	 * @param coupon
	 */
	public void insertCouponTargetUser(Coupon coupon);

	/**
	 * 상품 선택 정보 등록
	 * @param coupon
	 */
	public void insertCouponTargetItem(Coupon coupon);

	/**
	 * 쿠폰 발행
	 * @param coupon
	 */
	public void updateCouponPublish(Coupon coupon);
	
	/**
	 * 쿠폰 다운로드 상태 변경
	 * @param coupon
	 */
	public void updateCouponDownloadStatus(Coupon coupon);

}
