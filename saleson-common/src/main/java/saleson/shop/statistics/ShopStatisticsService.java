package saleson.shop.statistics;

import java.util.List;

import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.statistics.domain.*;
import saleson.shop.statistics.support.StatisticsParam;

import com.onlinepowers.framework.security.userdetails.User;


public interface ShopStatisticsService {

	/**
	 * 결제 타입별 통계
	 * @param statisticsParam
	 * @return
	 */
	public List<PaymentStatistics> getPaymentStatisticsListByParam(StatisticsParam statisticsParam);
	
	/**
	 * 회원별 통계 리스트
	 * @param statisticsParam
	 * @return
	 */
	public List<ShopItemDetailStatistics> getUserStatisticsListByParam(StatisticsParam statisticsParam);
	
	/**
	 * 회원별 통계 페이징용 카운트
	 * @param statisticsParam
	 * @return
	 */
	public int getUserStatisticsCountByParam(StatisticsParam statisticsParam);
	
	/**
	 * 회원별 통계 합계 - 페이징 때문에 쿼리로 처리함.
	 * @param statisticsParam
	 * @return
	 */
	public TotalRevenueStatistics getUserTotalRevenueStatisticsByParam(StatisticsParam statisticsParam);
	
	/**
	 * 브랜드별 통계 리스트
	 * @param statisticsParam
	 * @return
	 */
	public List<ShopBrandStatistics> getBrandStatisticsListByParam(StatisticsParam statisticsParam);
	
	/**
	 * 안팔린 상품 내역 카운트 - 페이징용
	 * @param statisticsParam
	 * @return
	 */
	public int getDoNotSellItemCountByParam(StatisticsParam statisticsParam);
	
	/**
	 * 안팔린 상품 내역
	 * @param statisticsParam
	 * @return
	 */
	public List<DoNotSellItem> getDoNotSellItemListByParam(StatisticsParam statisticsParam);
	
	/**
	 * 매출 통계 상세 화면
	 * @param statisticsParam
	 * @return
	 */
	public List<RevenueBaseForDate> getRevenueDetailListForDateByParam(StatisticsParam statisticsParam);
	
	public List<ShopItemDetailStatistics> getShopItemDetailList(StatisticsParam statisticsParam);
	
	public List<ShopOrderStatistics> getOrderListByParam(StatisticsParam statisticsParam);
	
	public List<ShopItemDetailStatistics> getAreaDetailList(StatisticsParam statisticsParam);
	
	public List<ShopItemStatistics> getUserOrderItemListByParam(StatisticsParam statisticsParam);
	
	public OrderShippingInfo getUsetOrderTotalDetailById(StatisticsParam statisticsParam);
	
	public List<ShopOrderStatistics> getUserOrderListByParam(StatisticsParam statisticsParam);
	
	public List<User> getNotUserList(StatisticsParam statisticsParam);
	
	public int getNotUserCount(StatisticsParam statisticsParam);
	
	public ShopItemStatistics getAreaCountParam(StatisticsParam statisticsParam);
	
	public List<ShopItemDateStatistics> getItemDateListByParam(StatisticsParam statisticsParam);
	
	public List<RevenueBaseForDate> getBrandStatisticsDetailByParam(StatisticsParam statisticsParam);

	/**
	 * 통계 > 판매자별 통계 요약 정보
	 * @param statisticsParam
	 * @return
	 */
	StatsSummary getSellerStatsSummary(StatisticsParam statisticsParam);

	/**
	 * 통계 > 상품별 통계 요약 정보
	 * @param statisticsParam
	 * @return
	 */
	StatsSummary getItemStatsSummary(StatisticsParam statisticsParam);

	/**
	 * 통계 > 일자/월/년도별 통계 리스트
	 * @param statisticsParam
	 * @return
	 */
	List<DateStatsSummary> getDateStatsList(StatisticsParam statisticsParam);

	/**
	 * 통계 > 판매자별 통계 리스트
	 * @param statisticsParam
	 * @return
	 */
	List<SellerStatsSummary> getSellerStatsList(StatisticsParam statisticsParam);

	/**
	 * 통계 > 상품별 통계 리스트
	 * @param statisticsParam
	 * @return
	 */
	List<ItemStatsSummary> getItemStatsList(StatisticsParam statisticsParam);

	/**
	 * 통계 > 카테고리별 통계 리스트
	 * @param statisticsParam
	 * @return
	 */
	List<CategoryStatsSummary> getCategoryStatsList(StatisticsParam statisticsParam);

	/**
	 * 통계 > 지역별 통계 리스트
	 * @param statisticsParam
	 * @return
	 */
	List<AreaStatsSummary> getAreaStatsList(StatisticsParam statisticsParam);
}
