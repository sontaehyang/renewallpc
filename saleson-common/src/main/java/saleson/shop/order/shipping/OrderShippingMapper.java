package saleson.shop.order.shipping;

import java.util.List;

import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderList;
import saleson.shop.order.domain.OrderShipping;
import saleson.shop.order.shipping.support.ShippingParam;
import saleson.shop.order.shipping.support.ShippingReadyParam;
import saleson.shop.order.support.OrderParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("orderShippingMapper")
public interface OrderShippingMapper {
	
	OrderShipping getOrderShippingByParam(OrderParam orderParam);
	
	/**
	 * 같은 배송 정책 상품 조회
	 * @param orderParam
	 * @return
	 */
	List<OrderItem> getOrderItemListByShippingParam(OrderParam orderParam);
	
	/**
	 * 구매확정 지연된 목록 조회
	 * @param orderParam
	 * @return
	 */
	List<OrderItem> getBuyConfirmDelayListByParam(OrderParam orderParam);
	
	/**
	 * 신규주문 카운트
	 * @param orderParam
	 * @return
	 */
	int getNewOrderCountByParam(OrderParam orderParam);
	
	/**
	 * 신규주문 리스트
	 * @param orderParam
	 * @return
	 */
	List<OrderList> getNewOrderListByParam(OrderParam orderParam);
	
	/**
	 * 배송준비중 카운트
	 * @param orderParam
	 * @return
	 */
	int getShippingReadyCountByParam(OrderParam orderParam);
	
	/**
	 * 배송준비중 리스트
	 * @param orderParam
	 * @return
	 */
	List<OrderList> getShippingReadyListByParam(OrderParam orderParam);
	
	/**
	 * 배송중 카운트
	 * @param orderParam
	 * @return
	 */
	int getShippingCountByParam(OrderParam orderParam);
	
	/**
	 * 배송중 리스트
	 * @param orderParam
	 * @return
	 */
	List<OrderList> getShippingListByParam(OrderParam orderParam);
	
	/**
	 * 구매확정 카운트
	 * @param orderParam
	 * @return
	 */
	int getConfirmCountByParam(OrderParam orderParam);
	
	/**
	 * 구매확정 리스트
	 * @param orderParam
	 * @return
	 */
	List<OrderList> getConfirmListByParam(OrderParam orderParam);
	
	/**
	 * 주문상품 복사 - 출고 지시
	 * @param orderItem
	 */
	void copyOrderItemForShippingReady(OrderItem orderItem);
	
	/**
	 * 주문상품 복사 - 직접 수령
	 * @param orderItem
	 */
	void copyOrderItemForShippingDirect(OrderItem orderItem);
	
	/**
	 * 출고 지시
	 * @param shippingReadyParam
	 * @return
	 */
	int updateShippingReady(ShippingReadyParam shippingReadyParam);
	
	/**
	 * 직접 수령
	 * @param shippingReadyParam
	 * @return
	 */
	int updateShippingDirect(ShippingReadyParam shippingReadyParam);
	
	
	/**
	 * 출고 지시 취소
	 * @param orderParam
	 * @return
	 */
	int updateShippingReadyCancel(ShippingParam shippingParam);
	
	/**
	 * 배송 시작
	 * @param orderParam
	 * @return
	 */
	int updateShippingStart(ShippingParam shippingParam);
	
	/**
	 * 배송 취소(배송준비중으로 수정)
	 * @param shippingParam
	 * @return
	 */
	int updateShippingCancel(ShippingParam shippingParam);
	
	/**
	 * 송장 번호 수정
	 * @param orderItem
	 */
	int updateShippingNumber(ShippingParam shippingParam);
	
	/**
	 * 구매 확정
	 * @param orderParam
	 * @return
	 */
	int updateConfirmPurchase(OrderParam orderParam);
	
	/**
	 * 배송비 정보에 정산 예정일을 셋팅
	 * @param orderParam
	 */
	void updateShippingRemittanceDate(OrderParam orderParam);
	
	/**
	 * 배송비 정보 변경
	 * @param orderShipping
	 */
	int updateCancelShipping(OrderShipping orderShipping);

    /**
     * 배송비 정보 백업
     * @param orderParam
     * @return
     */
    int updatePreviousShipping(OrderParam orderParam);

    void updateShippingForCancelRefund(OrderShipping orderShipping);

    /**
     * 배송전인 상태의 상품이 있는지 조회
     * @param shippingParam
     * @return
     */
    int getPreShippingCount(ShippingParam shippingParam);
}
