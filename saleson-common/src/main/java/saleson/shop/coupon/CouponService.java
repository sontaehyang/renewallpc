package saleson.shop.coupon;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.domain.CouponCount;
import saleson.shop.coupon.domain.CouponOffline;
import saleson.shop.coupon.domain.CouponUser;
import saleson.shop.coupon.domain.OrderCoupon;
import saleson.shop.coupon.support.CouponListParam;
import saleson.shop.coupon.support.CouponParam;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemBase;
import saleson.shop.order.domain.BuyItem;

import com.onlinepowers.framework.security.userdetails.User;

public interface CouponService {
	
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
	 * @param userId
	 * @return
	 */
	public int getDownloadUserCouponCountByUserId(long userId);
	
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
	 * 쿠폰 정보
	 * @param couponId
	 * @return
	 */
	public Coupon getCouponById(int couponId);
	
	/**
	 * 주문시 사용가능한 쿠폰 조회 - 특정상품
	 * @param buyItems
	 * @param userId
	 * @param viewTarget
	 * @return
	 */
	public List<OrderCoupon> getUserCouponListForItemTarget(List<BuyItem> buyItems, long userId, String viewTarget);
	
	/**
	 * 주문시 사용가능한 쿠폰 조회 - 전체 상품
	 * @param userId
	 * @param viewTarget
	 * @return
	 */
	public List<OrderCoupon> getUserCouponListForItemAll(long userId, String viewTarget);
	
	/**
	 * 쿠폰 사용 처리
	 * @param orderCoupon
	 */
	public void updateCouponUserUseProcessByOrderCouponUser(OrderCoupon orderCoupon);
	
	/**
	 * 쿠폰 등록
	 * @param coupon
	 */
	public void insertCoupon(Coupon coupon);
	
	/**
	 * 쿠폰 수정
	 * @param coupon
	 */
	public void updateCoupon(Coupon coupon);
	
	/**
	 * 선택 삭제
	 * @param couponListParam
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
	 * @param mode
	 */
	public void updateCouponDownloadStatus(Coupon coupon, String mode);
	
	/**
	 * 회원 쿠폰 다운로드
	 * @param userCouponParam
	 */
	public int userCouponDownload(UserCouponParam userCouponParam);
	
	/**
	 * 관리자용 회원별 쿠폰 카운트 조회
	 * @param userId
	 * @return
	 */
	public CouponCount getCouponUserCountByUserId(long userId);
	
	/**
	 * 상품 페이지용 쿠폰 조회 - 최고 할인율 쿠폰 View
	 * @param userCouponParam
	 * @return
	 */
	public List<Coupon> getCouponForItemView(UserCouponParam userCouponParam);
	
	/**
	 * 회원 선택 정보 등록
	 * @param coupon
	 */
	public void insertCouponTargetUser(Coupon coupon);
	
	/**
	 * 해당 회원에게 쿠폰 발행
	 * @param userCouponParam
	 */
	public void insertCouponTargetUserOne(UserCouponParam userCouponParam);
	
	/**
	 * 발행 시점에 따른 쿠폰 발급을 위한 SELECT
	 * @return
	 */
	List<Coupon> getCouponByTargetTimeType(UserCouponParam userCouponParam);
	
	/**
	 * 오프라인 코드로 오프라인 쿠폰정보 조회
	 * @param couponOffline
	 * @return
	 */
	public CouponOffline getCouponOfflineByOfflineCode(CouponOffline couponOffline);

	/**
	 * 오프라인 쿠폰 사용상태로 전환
	 * @param couponOffline
	 */
	public void updateCouponOffline(CouponOffline couponOffline);

	/**
	 * 오프라인 쿠폰 목록 조회
	 * @param couponId
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
	List<CouponUser> getCouponUserListByUserForManager(UserCouponParam userCouponParam);

	/**
	 * 쿠폰 상품 추가 (엑셀 업로드)
	 * @param multipartFile
	 * @param choseItemSize
	 * @return
	 */
	List<ItemBase> insertItemExcelData(MultipartFile multipartFile);

	/**
	 * 직접 입력 쿠폰 값 중복체크
	 * @param value
	 * @return
	 */
	int getDirectInputValueCount(String value);

	/**
	 * 직접 입력 쿠폰 다운로드
	 */
	boolean downloadDirectInputCoupon(String value);

	/**
	 * 오프라인 쿠폰 코드
	 * @param offlineCode
	 * @return
	 */
	String getOfflineCode(String offlineCode);
}
