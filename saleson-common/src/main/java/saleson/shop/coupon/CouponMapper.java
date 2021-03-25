
package saleson.shop.coupon;

import java.util.List;

import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.domain.CouponCount;
import saleson.shop.coupon.domain.CouponOffline;
import saleson.shop.coupon.domain.CouponUser;
import saleson.shop.coupon.domain.OrderCoupon;
import saleson.shop.coupon.support.CouponParam;
import saleson.shop.coupon.support.UserCouponParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.security.userdetails.User;
import saleson.shop.item.domain.Item;

@Mapper("couponMapper")
public interface CouponMapper {
	
	/**
	 * 쿠폰 마스터 테이블을 이용해서 사용가능한 상품 목록 보기 - 카운트
	 * @param couponParam
	 * @return
	 */
	public int getCouponAppliesItemCountParamForCoupon(CouponParam couponParam);
	
	/**
	 * 쿠폰 마스터 테이블을 이용해서 사용가능한 상품 목록 보기 - 목록
	 * @param couponParam
	 * @return
	 */
	public Coupon getCouponAppliesItemListParamForCoupon(CouponParam couponParam);
	
	/**
	 * 회원별 쿠폰 내역 테이블을 이용해서 사용가능한 상품 목록 보기 - 카운트
	 * @param couponParam
	 * @return
	 */
	public int getCouponAppliesItemCountParamForCouponUser(CouponParam couponParam);
	
	/**
	 * 회원별 쿠폰 내역 테이블을 이용해서 사용가능한 상품 목록 보기 - 목록
	 * @param couponParam
	 * @return
	 */
	public Coupon getCouponAppliesItemListParamForCouponUser(CouponParam couponParam);
	
	/**
	 * 다운로드한 쿠폰 수량 - 회원
	 * @param userCouponParam
	 * @return
	 */
	public int getDownloadUserCouponCountByUserCouponParam(UserCouponParam userCouponParam);

	/**
	 * 다운로드한 쿠폰 목록 - 회원
	 * @param userCouponParam
	 * @return
	 */
	public List<CouponUser> getDownloadUserCouponListByUserCouponParam(UserCouponParam userCouponParam);

	/**
	 * 사용 + 만료된 쿠폰 수량 - 회원
	 * @param userCouponParam
	 * @return
	 */
	public int getCompletedUserCouponCountByUserCouponParam(UserCouponParam userCouponParam);

	/**
	 * 사용 + 만료된 쿠폰 목록 - 회원
	 * @param userCouponParam
	 * @return
	 */
	public List<CouponUser> getCompletedUserCouponListByUserCouponParam(UserCouponParam userCouponParam);

	/**
	 * 다운로드 가능 목록 - 회원
	 * @param userCouponParam
	 * @return
	 */
	public List<Coupon> getUserDownloadableCouponListByParam(UserCouponParam userCouponParam);

	/**
	 * 다운로드 가능 목록 카운트 - 회원
	 * @param userCouponParam
	 * @return
	 */
	public int getUserDownloadableCouponListCountByParam(UserCouponParam userCouponParam);
	
	/**
	 * 회원 선택 미리보기 카운트
	 * @param coupon
	 * @return
	 */
	public int getCouponTargetUserCountByCoupon(Coupon coupon);
	
	/**
	 * 회원 선택 미리보기 리스트
	 * @param coupon
	 * @return
	 */
	public List<User> getCouponTargetUserListByCoupon(Coupon coupon);
	
	/**
	 * 상품 선택 미리보기 카운트
	 * @param coupon
	 * @return
	 */
	public int getCouponTargetItemCountByCoupon(Coupon coupon);
	
	/**
	 * 상품 선택 미리보기 리스트
	 * @param coupon
	 * @return
	 */
	public List<Item> getCouponTargetItemListByCoupon(Coupon coupon);
	
	/**
	 * 쿠폰 단일 정보 조회
	 * @param couponId
	 * @return
	 */
	public Coupon getCouponById(int couponId);
	
	/**
	 * 관리자 등록 쿠폰 - 카운트
	 * @param couponParam
	 * @return
	 */
	public int getCouponCountByParamForManager(CouponParam couponParam);
	
	/**
	 * 관리자 등록 쿠폰 - 리스트
	 * @param couponParam
	 * @return
	 */
	public List<Coupon> getCouponListByParamForManager(CouponParam couponParam);

	/**
	 * 관리자 사용자 쿠폰 내역 - 카운트
	 * @param couponParam
	 * @return
	 */
	public int getCouponUserCountByParamForManager(CouponParam couponParam);
	
	/**
	 * 관리자 사용자 쿠폰 내역 - 리스트
	 * @param couponParam
	 * @return
	 */
	public List<CouponUser> getCouponUserListByParamForManager(CouponParam couponParam);
	
	/**
	 * 사용자가 주문시 사용 가능한 쿠폰을 조회함 - 상품에 사용 할수 있는 상품 쿠폰 (카테고리 발행 + 상품 발행) . 해당 상품에 사용할수 있는 쿠폰만!!
	 * @param userCouponParam
	 * @return
	 */
	public List<OrderCoupon> getAvailableCouponListByParam(UserCouponParam userCouponParam);
	
	/**
	 * 쿠폰 등록
	 * @param coupon
	 */
	public void insertCoupon(Coupon coupon);
	
	/**
	 * 오프라인 쿠폰 등록
	 * @param couponOffline
	 * @return
	 */
	public boolean insertCouponOffline(CouponOffline couponOffline);
	
	/**
	 * 쿠폰 수정
	 * @param coupon
	 */
	public void updateCoupon(Coupon coupon);
	
	/**
	 * 쿠폰 삭제
	 * @param coupon
	 */
	public void deleteCoupon(Coupon coupon);
	
	/**
	 * 쿠폰 발행
	 * @param coupon
	 */
	public void updateCouponPublish(Coupon coupon);
	
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
	 * 쿠폰 다운로드 상태 변경
	 * @param coupon
	 */
	public void updateCouponDownloadStatus(Coupon coupon);

	/**
	 * 쿠폰 다운로드
	 * @param userCouponParam
	 * @return
	 */
	public int insertDownloadCoupon(UserCouponParam userCouponParam);
	
	/**
	 * 관리자용 회원별 쿠폰 카운트 조회
	 * @param userId
	 * @return
	 */
	public CouponCount getCouponUserCountByUserId(long userId);
	
	/**
	 * 회원 쿠폰 사용 업데이트
	 * @param orderCouponUser
	 */
	void updateCouponUserUseProcessByOrderCouponUser(OrderCoupon orderCoupon);
	
	/**
	 * 회원 쿠폰 반환 처리
	 * @param orderCoupon
	 */
	void updateCouponUserReturnsByOrderCouponUser(OrderCoupon orderCoupon);
	
	/**
	 * 상품 페이지용 쿠폰 조회 - 최고 할인율 쿠폰 View
	 * @param userCouponParam
	 * @return
	 */
	List<Coupon> getCouponForItemView(UserCouponParam userCouponParam);
	
	/**
	 * 발행 시점에 따른 쿠폰 발급을 위한 SELECT
	 * @return
	 */
	List<Coupon> getCouponByTargetTimeType(UserCouponParam userCouponParam);
	
	/**
	 * 해당 회원에게 쿠폰 발행
	 * @param userCouponParam
	 */
	public void insertCouponTargetUserOne(UserCouponParam userCouponParam);
	
	/**
	 * 오프라인 코드로 오프라인 쿠폰정보 조회
	 * @param offlineCode
	 * @return
	 */
	public CouponOffline getCouponOfflineByOfflineCode(CouponOffline couponOffline);

	/**
	 * 오프라인 쿠폰 사용상태로 전환
	 * @param offlineCode
	 */
	public void updateCouponOffline(CouponOffline couponOffline);

	/**
	 * 오프라인 쿠폰 목록 조회
	 * @param couponOffline
	 * @return
	 */
	public List<CouponOffline> getCouponOfflineListByCouponId(Integer couponId);

	/**
	 * SNS 가입시 신규쿠폰 발급 여부 확인
	 * @param userCouponParam
	 * @return
	 */
	public int getUserCouponListForNewUserCoupon(UserCouponParam userCouponParam);

	/**
	 * 관리자용 회원 다운로드 쿠폰 목록
	 * @param userCouponParam
	 * @return
	 */
	int getCouponUserListCountByUserForManager(UserCouponParam userCouponParam);

	/**
	 * 관리자용 회원 다운로드 쿠폰 목록
	 * @param userCouponParam
	 * @return
	 */
	List<CouponUser> getCouponUserListByUserForManager(UserCouponParam userCouponParam);

	/**
	 * 엑셀로 입력된 상품 정보 조회
	 * @param coupon
	 * @return
	 */
	List<Item> getCouponExcelItemListByCoupon(Coupon coupon);

	/**
	 * 직접 입력 쿠폰 값 중복 여부 확인
	 * @param value
	 * @return
	 */
	int getDirectInputValueCount(String value);

	/**
	 * 쿠폰만료 day일 전,
	 * 쿠폰만료 예정 회원 목록 조회 (UMS)
	 * @return
	 */
	List<CouponUser> getExpirationCouponUserList(UserCouponParam userCouponParam);

	/**
	 * 쿠폰만료 day일 전,
	 * 쿠폰만료 예정 회원 수 조회 (UMS)
	 * @return
	 */
	int getExpirationCouponUserCount(int day);

	/**
	 * 쿠폰만료 day일 전,
	 * 쿠폰만료 예정 목록 조회 (UMS)
	 * @return
	 */
	List<CouponUser> getExpirationCouponList(UserCouponParam userCouponParam);

	/**
	 * 생일쿠폰 발행 회원 조회 (UMS)
	 * @return
	 */
	List<CouponUser> getBirthdayUserList(UserCouponParam userCouponParam);
}
