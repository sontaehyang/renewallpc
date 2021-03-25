package saleson.shop.order.payment;

import java.util.List;

import saleson.shop.order.domain.OrderPayment;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.pg.kcp.domain.KcpRequest;
import saleson.shop.order.support.OrderParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("orderPaymentMapper")
public interface OrderPaymentMapper {
	
	/**
	 * 입금대기 결제 카운트
	 * @param orderParam
	 * @return
	 */
	int getWaitingDepositCountByParam(OrderParam orderParam);
	
	/**
	 * 입금대기 결제 목록
	 * @param orderParam
	 * @return
	 */
	List<OrderPayment> getWaitingDepositListByParam(OrderParam orderParam);
	
	/**
	 * 결제 확인 처리
	 * @param orderCode
	 */
	int updateConfirmationOfPaymentStep1(OrderParam orderParam);
	int updateConfirmationOfPaymentStep2(OrderParam orderParam);
	int updateConfirmationOfPaymentStep3(OrderParam orderParam);
	
	/**
	 * 주문취소시 데이터 변경
	 * @param orderPayment
	 * @return
	 */
	int updateOrderPaymentForCancel(OrderPayment orderPayment);
	
	/**
	 * 은행 입금전 취소
	 * @param orderPayment
	 * @return
	 */
	int updateOrderPaymentForBankCancel(OrderPayment orderPayment);
	
	/**
	 * 결제 정보 추가
	 * @param orderPayment
	 * @return
	 */
	int insertOrderPayment(OrderPayment orderPayment);
	
	/**
	 * 이니시스용 가상계좌 결제 정보 조회 - 모바일
	 * @param pgData
	 * @return
	 */
	OrderPayment getOrderPaymentByPgDataForInipayVacctForMobile(PgData pgData);
	
	/**
	 * 이니시스용 가상계좌 결제 정보 조회
	 * @param pgData
	 * @return
	 */
	OrderPayment getOrderPaymentByPgDataForInipayVacct(PgData pgData);

	/**
	 * 결제 총액 수정
	 * @param orderParam
	 */
	void updateTotalPayAmount(OrderParam orderParam);
	
	/**
	 * 결제 정보 삭제
	 * @param orderParam
	 */
	void deleteOrderPayment(OrderParam orderParam);

	/**
	 * KCP용 가상계좌 결제 정보 조회
	 * @param order_no
	 * @return
	 */
	OrderPayment getOrderPaymentByPgDataForKcpVacct(String order_no);

	/**
	 * OrderPgData 조회
	 * @param orderParam  (orderCode, orderSequence, paymentSequence)
	 * @return
	 */
	OrderPgData getOrderPgDataByOrderParam(OrderParam orderParam);

	/**
	 * [배치용] 입금대기 주문 카운트
	 * @param orderParam
	 * @return
	 */
	int getWaitingDepositCountByParamForBatch(OrderParam orderParam);

	/**
	 * [배치용] 입금대기 주문목록
	 * @param orderParam
	 * @return
	 */
	List<OrderPayment> getWaitingDepositListByParamForBatch(OrderParam orderParam);
}
