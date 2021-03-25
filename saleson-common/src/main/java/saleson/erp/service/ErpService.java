package saleson.erp.service;

import saleson.erp.domain.HPR100T;
import saleson.erp.domain.OrderLine;
import saleson.erp.domain.OrderLineStatus;
import saleson.shop.order.domain.OrderItem;

import java.util.List;

public interface ErpService {
	/**
	 * ERP 품목코드로 품목정보(상품명) 조회
	 * @param itemCode 품목코드
	 * @return HPR100T
	 */
	HPR100T findHPR100TByItemCode(String itemCode);


	/**
	 * ERP 주문데이터 validate
	 * @param erpOrder
	 */
	List<OrderLine> validateOrderListGetForClaim(ErpOrder erpOrder);


	/**
	 * ERP 주문데이터 전송
	 * IF_ORDER_LIST_GET 테이블에 주문정보 저장
	 * @param erpOrder
	 */
	void saveOrderListGet(ErpOrder erpOrder);

	/**
	 * ERP 주문데이터 전송 (즉시취소용)
	 * IF_ORDER_LIST_GET 테이블에 주문정보 저장
	 * @param orderLines
	 */
	void saveOrderListGetAutoCancel(List<OrderLine> orderLines);

	/**
	 * ERP 주문 상태 정보를 조회함.
	 * IF_ORDER_LIST_PUT 테이블에 uniq 번호로 주문 정보 조회.
	 *
	 * @param List<String> uniqs
	 * @return List<OrderLineStatus>
	 */

	List<OrderLineStatus> findOrderLineStatusAll(List<String> uniqs);

	List<OrderLineStatus> findOrderLineStatusAll(String uniq);

	List<OrderItem> findOrderItemAll(List<OrderItem> orderItems);

	/**
	 * IF_ORDER_LIST_PUT 테이블 APPLY_FLAG 업데이트
	 * @param orderItems
	 */
	void updateApplyFlagIfOrderListPut(List<OrderItem> orderItems);
}
