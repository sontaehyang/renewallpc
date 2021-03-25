package saleson.shop.point;

import java.util.List;

import saleson.shop.point.domain.*;
import saleson.shop.point.support.OrderPointParam;
import saleson.shop.point.support.PointParam;

import com.onlinepowers.framework.security.userdetails.User;

public interface PointService {
	
	/**
	 * 회원별 포인트 보유 내역 - Excel
	 * @param pointParam
	 * @return
	 */
	public List<PublicationPoint> getPublicationPointListByParamForManagerExcelDownload(PointParam pointParam);
	
	/**
	 * 회원별 포인트 보유 내역
	 * @param pointParam
	 * @return
	 */
	public List<PublicationPoint> getPublicationPointListByParamForManager(PointParam pointParam);
	
	/**
	 * 포인트 전체 내역 - 액셀 다운로드
	 * @param pointParam
	 * @return
	 */
	public List<PointHistory> getPointTotalHistoryListByParamForExcelDownload(PointParam pointParam);
	
	/**
	 * 포인트 전체 내역
	 * @param pointParam
	 * @return
	 */
	public List<PointHistory> getPointTotalHistoryListByParam(PointParam pointParam);
	
	/**
	 * 일자별 포인트 합산 내역 (사용, 적립)
	 * @param pointParam
	 * @return
	 */
	public List<PointDayGroup> getPointDayGroupListByParam(PointParam pointParam);
	

	/**
	 * 일자별 포인트 합산 내역 (사용, 적립) (엑셀)
	 * @param pointParam
	 * @return
	 */
	public List<PointDayGroup> getPointDayGroupListByParamForExcelDownload(PointParam pointParam);
	
	/**
	 * 다음달 만료예정 포인트
	 * @param pointParam
	 * @return
	 */
	int getNextMonthExpirationPointAmountByParam(PointParam pointParam);
	
	/**
	 * 포인트 적립내역
	 * @param pointParam
	 * @return
	 */
	public int getPointCountByParam(PointParam pointParam);
	
	/**
	 * 포인트 적립내역
	 * @param pointParam
	 * @return
	 */
	public List<Point> getPointListByParam(PointParam pointParam);
	
	/**
	 * 포인트 사용 내역
	 * @param pointParam
	 * @return
	 */
	public int getPointUsedCountByParam(PointParam pointParam);
	
	/**
	 * 포인트 사용 내역
	 * @param pointParam
	 * @return
	 */
	public List<PointUsed> getPointUsedListByParam(PointParam pointParam);
	
	/**
	 * 포인트 종류별 이번달 만료 예정 금액 조회 
	 * @param pointParam
	 * @return
	 */
	public Point getExpirationPointAmountByParam(PointParam pointParam);
	
	/**
	 * 오늘날짜기준 포인트 만료 처리
	 */
	public void expirationPoint();
	
	/**
	 * 이번달 만료 포인트 안내
	 * 주의사항!! 안내 메일 발송 여부등은 업으니.. 1달에 1번만 실행하도록 하자
	 * 협의된 내용은 매월 1일 이번달 만료 예정 안내를 발송함 
	 */
	public void expirationPointSendMessage();
	
	/**
	 * 포인트 정리
	 */
	public void pointTheorem(User user);
	
	
	/**
	 * 상품ID로 등록된 적립금 설정정보를 가져온다.
	 * @param itemId
	 * @return
	 */
	public List<PointConfig> getPointConfigListByItemId(int itemId);
	
	/**
	 * 구매후 적립금 정보 조회
	 * @param itemId
	 * @return
	 */
	public PointPolicy getPointPolicyByItemId(int itemId);
	
	/**
	 * 구매후 적립금 정보 조회
	 * @param orderPointParam
	 * @return
	 */
	public PointPolicy getPointPolicyByOrderPointParam(OrderPointParam orderPointParam);
	
	/**
	 * 적립금 설정정보를 등록한다.
	 * @param pointConfig
	 */
	public void insertPointConfig(PointConfig pointConfig);
	
	
	/**
	 * 적립금 설정 정보를 삭제한다 (StatusCode = 2로 업데이트)
	 * @param pointConfigId
	 */
	public void deletePointConfigById(int pointConfigId);
	
	
	/**
	 * 공통설정으로 되어있는 적립금 조회 
	 * @return 
	 */
	public List<PointConfig> getShopPointConfig();

	/**
	 * 사용자별 사용 가능 포인트 목록 조회
	 * @param userId
	 * @return
	 */
	public AvailablePoint getAvailablePointByUserId(long userId, String pointType);
	
	/**
	 * 포인트 리스트 조회
	 * @param orderPointParam
	 * @return
	 */
	public List<Point> getAvailablePointListByParam(OrderPointParam orderPointParam);
	
	/**
	 * 포인트 사용 처리
	 * @param pointUsed
	 * @param userId
	 * @param ptCode
	 */
	public void deductedPoint(PointUsed pointUsed, long userId, String ptCode);
	
	/**
	 * 사용자 포인트 적립
	 */
	public void earnPoint(String mode, Point point);
	
	/**
	 * 공통설정으로 되어있는 적립금 제거
	 * @param pointConfig
	 */
	public void deleteShopPointConfig(PointConfig pointConfig);
	
		
	/**
	 * 사용자 포인트 사용 내역 카운트
	 * @param userId
	 * @return
	 */
	public int getPointHistoryCountByParam(PointParam pointParam);
	
	
	/**
	 * 사용자 포인트 사용 내역
	 * @param userId
	 * @return
	 */
	public List<PointHistory> getPointHistoryListByParam(PointParam pointParam);
	
	/**
	 * 전체 회원 포인트 지급
	 * @param point
	 */
	public void insertPointAllUser(Point point);
	
	/**
	 * 지정회원 포인트 지급 (엑셀)
	 * @param point
	 */
	public void insertPointByExcel(Point point);
	
	/**
	 * 회원 등급별 배송비 무료쿠폰 발행
	 */
	public void userLevelShippingCoupon();
	
	/**
	 * 선택회원 포인트 지급
	 * @param point
	 */
	public void insertPointPay(Point point);

	/**
	 * 배송완료후 N일후 포인트 지급대상 주문 조회
	 * @param orderPointParam
	 * @return
	 */
	List<OrderPoint> getOrderPointByParam(OrderPointParam orderPointParam);

	/**
	 * 포인트 적립
	 */
	public void savePointByOrderPointParam(OrderPointParam orderPointParam);

}
