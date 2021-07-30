package saleson.erp.domain;

import saleson.erp.configuration.annotation.MapperErp;
import saleson.shop.chart.domain.Chart;
import saleson.shop.chart.support.ChartParam;
import saleson.shop.order.domain.OrderItem;

import java.util.HashMap;
import java.util.List;

@MapperErp("erpMapper")
public interface ErpMapper {
    long test();

	HPR100T findHPR100TByItemCode(String itemCode);

	void saveOrderListGet(List<OrderLine> orderLines);

	List<OrderLineStatus> findOrderLineAll(List<String> uniqs);

	/**
	 * ERP 주문 상태 코드 연동을 위함 임시 테이블 데이터 저장.
	 * @param orderItems
	 */
	void saveErpOrderItems(List<OrderItem> orderItems);

	void updateApplyFlagIfOrderListPut(List<String> uniqs);

	List<OrderLineStatus> findIfOrderListPutAll();

	/**
	 * 매입단가표 상품정보 ERP 에서 가져와 표시
	 */
	List<Chart> getChartItemList(ChartParam chartParam);

	List<Chart> getChartCategory1(HashMap categoryMap);

	List<Chart> getChartCategory2(String ItemLevel1);

	List<Chart> getChartCategory3(String ItemLevel2);

	List<Chart> getCategoryInfo(ChartParam chartParam);
}
