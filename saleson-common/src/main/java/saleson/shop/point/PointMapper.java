package saleson.shop.point;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.point.domain.OrderPoint;
import saleson.shop.point.domain.Point;
import saleson.shop.point.domain.PointConfig;
import saleson.shop.point.domain.PointDayGroup;
import saleson.shop.point.domain.PointHistory;
import saleson.shop.point.domain.PointPolicy;
import saleson.shop.point.domain.PointUsed;
import saleson.shop.point.domain.PublicationPoint;
import saleson.shop.point.domain.ReturnPoint;
import saleson.shop.point.domain.ReturnPointTargetUsedHistory;
import saleson.shop.point.support.OrderPointParam;
import saleson.shop.point.support.PointParam;
import saleson.shop.point.support.ReturnPointParam;
import saleson.shop.userlevel.domain.UserLevel;


@Mapper("pointMapper")
public interface PointMapper {
	
	/**
	 * 회원별 포인트 보유 카운트
	 * @param pointParam
	 * @return
	 */
	public int getPublicationPointCountByParamForManager(PointParam pointParam);
	
	/**
	 * 회원별 포인트 보유 내역
	 * @param pointParam
	 * @return
	 */
	public List<PublicationPoint> getPublicationPointListByParamForManager(PointParam pointParam);
	
	/**
	 * 포인트 전체 내역 카운트
	 * @param pointParam
	 * @return
	 */
	int getPointTotalHistoryCountByParam(PointParam pointParam);
	
	/**
	 * 포인트 전체 내역
	 * @param pointParam
	 * @return
	 */
	List<PointHistory> getPointTotalHistoryListByParam(PointParam pointParam);
	
	/**
	 * 일자별 포인트 합산 내역 (사용, 적립) 카운트
	 * @param pointParam
	 * @return
	 */
	int getPointDayGroupCountByParam(PointParam pointParam);
	
	/**
	 * 일자별 포인트 합산 내역 (사용, 적립)
	 * @param pointParam
	 * @return
	 */
	List<PointDayGroup> getPointDayGroupListByParam(PointParam pointParam);
	
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
	int getPointCountByParam(PointParam pointParam);
	
	/**
	 * 포인트 적립내역
	 * @param pointParam
	 * @return
	 */
	List<Point> getPointListByParam(PointParam pointParam);
	
	/**
	 * 포인트 사용 내역
	 * @param pointParam
	 * @return
	 */
	int getPointUsedCountByParam(PointParam pointParam);
	
	/**
	 * 포인트 사용 내역
	 * @param pointParam
	 * @return
	 */
	List<PointUsed> getPointUsedListByParam(PointParam pointParam);
	
	/**
	 * 회원별 이번달 포인트 만료 안내 포인트 목록
	 * @param pointParam
	 * @return
	 */
	List<Point> getExpirationSendMessageListByParam(PointParam pointParam);
	
	/**
	 * 이번달 포인트 만료 안내 대상자
	 * @return
	 */
	List<Integer> getExpirationPointSendMessageTargetListAll();
	
	/**
	 * 오늘 만료 대상 포인트 조회
	 * @param pointParam
	 * @return
	 */
	List<Point> getExpirationListByParam(PointParam pointParam);
	
	/**
	 * 정리대상 포인트 타입
	 * @param userId
	 * @return
	 */
	List<String> getPointTheoremPointTypesByUserId(long userId);
	
	/**
	 * 회원 - 사용 가능 포인트 적립 내역
	 * @param OrderPointParam
	 * @return
	 */
	List<Point> getAvailablePointListByParam(OrderPointParam orderPointParam);
	
	/**
	 * 오늘 포인트 만료 대상자
	 * @return
	 */
	List<Integer> getExpirationPointTargetListByParam(PointParam pointParam);
	
	/**
	 * 포인트 타입별 이번달 만료 예정 포인트 조회
	 * @param pointParam
	 * @return
	 */
	Point getExpirationPointAmountByParam(PointParam pointParam);
	
	/**
	 * 상품ID로 등록된 적립금 설정정보를 가져온다.
	 * @param itemId
	 * @return
	 */
	List<PointConfig> getPointConfigListByItemId(int itemId);
	
	/**
	 * 구매후 적립금 정책 정보 조회
	 * @param itemId
	 * @return
	 */
	PointPolicy getPointPolicyByOrderPointParam(OrderPointParam orderPointParam);
	
	/**
	 * 회원 - 사용 가능 포인트 적립 내역
	 * @param userId
	 * @return
	 */
	List<Point> getAvailablePointListByUserId(long userId);
	
	/**
	 * 주문취소시 해당 주문으로 인해 적립된 포인트 내역 조회
	 * @param returnPointParam
	 * @return
	 */
	List<Point> getReturnPointListByParam(ReturnPointParam returnPointParam);
	
	/**
	 * 배송완료후 N일후 포인트 지급대상 주문 조회
	 * @param orderPointParam
	 * @return
	 */
	List<OrderPoint> getOrderPointByParam(OrderPointParam orderPointParam);
	
	/**
	 * 적립금 설정정보를 등록한다.
	 * @param pointConfig
	 */
	void insertPointConfig(PointConfig pointConfig);
	
	/**
	 * 사용자 포인트 정보를 저장
	 * @param point
	 */
	void insertPoint(Point point);
	
	/**
	 * 포인트 사용 기록
	 * @param point
	 */
	void updateAvailablePointByParam(Point point);
	
	/**
	 * 사용자 포인트 사용 로그
	 * @param pointUsed
	 */
	void insertPointUsed(PointUsed pointUsed);
	
	/**
	 * 적립금 설정 정보를 삭제한다 (StatusCode = 2로 업데이트)
	 * @param pointConfigId
	 */
	 void deletePointConfigById(int pointConfigId);


	/**
	 * 상품ID에 해당하는 적립금 설정정보를 삭제한다.
	 * @param itemId
	 */
	void deletePointConfigByItemId(int itemId);
	
	/**
	 * 공통설정으로 되어있는 적립금 조회 
	 * @return
	 */
	List<PointConfig> getShopPointConfig();
	
	
	/**
	 * 공통설정으로 되어있는 적립금 제거
	 * @param pointConfig
	 */
	void deleteShopPointConfig(PointConfig pointConfig);
	
	/**
	 * 사용자 포인트 사용 내역 카운트
	 * @param userId
	 * @return
	 */
	int getPointHistoryCountByParam(PointParam pointParam);
	
	/**
	 * 사용자 포인트 사용 내역
	 * @param userId
	 * @return
	 */
	List<PointHistory> getPointHistoryListByParam(PointParam pointParam);
	
	/**
	 * 해당 주문에서 적립된 포인트 조회
	 * @param returnPointParam
	 * @return
	 */
	ReturnPoint getOrderReturnPointByParam(ReturnPointParam returnPointParam);
	
	/**
	 * 반환 대상 포인트를 사용한 주문 내역
	 * @param returnPointParam
	 * @return
	 */
	List<ReturnPointTargetUsedHistory> getOrderReturnPointTargetUsedHistoryByParam(ReturnPointParam returnPointParam);
	
	/**
	 * 전체 회원 포인트 지급
	 * @param point
	 */
	void insertPointAllUser(Point point);
	
	void updatePointSequence();

	
	/**
	 * 지정 회원 포인트 지급 (엑셀 로드 후 )
	 * @param points
	 */
	void insertPointByList(List<Point> points);
	
	/**
	 * 회원 등급별 배송비 쿠폰 발행
	 * @param userLevel
	 */
	void insertShippingCouponByUserLevel(UserLevel userLevel);

	/**
	 * 포인트 만료일
	 * @param pointId
	 * @return
	 */
	String getPointExpirationDateByPointId(int pointId);

    /**
     * OrderCode에 해당하는 포인트 사용 내역 조회
     * @param OrderCode
     * @return
     */
    List<PointUsed> getPointUsedListByOrderCode(String OrderCode);

	/**
	 * 포인트 복원시 남은 포인트 업데이트
	 * @param pointUsed
	 */
	void updateRemainingPointByPointUsed(PointUsed pointUsed);

	/**
	 * 포인트만료 day일 전,
	 * 포인트만료 예정 회원 목록 조회 (UMS)
	 * @return
	 */
	List<Point> getExpirationPointSendMessage(int day);
}
