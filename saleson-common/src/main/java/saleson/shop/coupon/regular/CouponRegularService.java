package saleson.shop.coupon.regular;

import java.util.List;

import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.CouponListParam;
import saleson.shop.coupon.support.CouponParam;

public interface CouponRegularService {
	
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
	 * 쿠폰 선택 삭제
	 * @param coupon
	 */
	public void deleteListData(CouponListParam couponListParam);

	/**
	 * 쿠폰 발행
	 * @param coupon
	 */
	public void updateCouponPublish(Coupon coupon);
	
	/**
	 * 쿠폰 다운로드 상태 변경
	 * @param coupon
	 */
	public void updateCouponDownloadStatus(Coupon coupon, String mode);

}
