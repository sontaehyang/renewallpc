package saleson.shop.order.claimapply;

import java.util.HashMap;
import java.util.List;

import saleson.erp.domain.OrderLineStatus;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderExchangeApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.claimapply.support.ClaimApplyParam;
import saleson.shop.order.claimapply.support.ReturnApply;
import saleson.shop.order.domain.OrderItem;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.shop.order.support.BatchKey;

@Mapper("orderClaimApplyMapper")
public interface OrderClaimApplyMapper {

	/**
	 * 교환 카운트
	 * @param claimApplyParam
	 * @return
	 */
	int getExchangeCountByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 교환 리스트
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderExchangeApply> getExchangeListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 처리해야되는 교환 목록
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderExchangeApply> getActiveExchangeListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 주문 상세용 히스토리 (승인대기, 완료, 거절)
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderExchangeApply> getExchangeHistoryListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 송장번호 입력 유효성 검사
	 * @param returnApply
	 * @return
	 */
	String getReturnShippingNumberCheck(ReturnApply returnApply);

	/**
	 * 반품 카운트
	 * @param claimApplyParam
	 * @return
	 */
	int getReturnCountByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 교환 처리완료 로그 보기
	 * @param claimCode
	 * @return
	 */
	List<OrderExchangeApply> getExchangeApplyByClaimCode(String[] claimCode);

	/**
	 * 반품 처리완료 로그 보기
	 * @param claimCode
	 * @return
	 */
	List<OrderReturnApply> getReturnApplyByClaimCode(String[] claimCode);


	/**
	 * 주문 상세용 히스토리 (승인대기, 완료, 거절)
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderReturnApply> getReturnHistoryListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 반품 리스트
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderReturnApply> getReturnListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 환불 코드로 반품 목록 조회
	 * @param refundCode
	 * @return
	 */
	List<OrderReturnApply> getOrderReturnApplyListByRefundCode(String refundCode);

	/**
	 * 처리해야하는 반품 목록
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderReturnApply> getActiveReturnListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 반품 목록
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderReturnApply> getOrderReturnApplyListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 반품 마스터 등록
	 * @param orderReturnApply
	 */
	int insertOrderReturnApply(OrderReturnApply orderReturnApply);

	/**
	 * 부분 반품 신청
	 * @param orderReturnApply
	 */
	void copyOrderItemForReturnApply(OrderReturnApply orderReturnApply);

	/**
	 * 반품 상태 수정
	 * @param orderReturnApply
	 */
	int updateReturnStatus(OrderReturnApply orderReturnApply);

	/**
	 * 반품 상태 변경후 클래임 수량 조절
	 * @param orderReturnApply
	 */
	void updateClaimQuantityForReturn(OrderReturnApply orderReturnApply);

	/**
	 * 클레임 수량 조정 - 반품
	 * @param orderReturnApply
	 */
	void updateClaimQuantityForReturnApply(OrderReturnApply orderReturnApply);

	/**
	 *
	 * @param orderReturnApply
	 */
	void updateOrderItemQuantityForReturn(OrderReturnApply orderReturnApply);

	/**
	 * 반품 정보 수정
	 * @param orderReturnApply
	 */
	int updateOrderReturnApply(OrderReturnApply orderReturnApply);

	/**
	 * 주문취소 카운트
	 * @param claimApplyParam
	 * @return
	 */
	int getCancelCountByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 처리해야하는 취소 목록
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderCancelApply> getActiveCancelListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 주문 상세용 히스토리 (승인대기, 완료, 거절)
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderCancelApply> getCancelHistoryListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 주문취소 리스트
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderCancelApply> getCancelListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 환불 코드로 주문취소 목록 조회
	 * @param refundCode
	 * @return
	 */
	List<OrderCancelApply> getOrderCancelApplyListByRefundCode(String refundCode);

	/**
	 * 주문취소 목록
	 * @param claimApplyParam
	 * @return
	 */
	List<OrderCancelApply> getOrderCancelApplyListByParam(ClaimApplyParam claimApplyParam);

	/**
	 * 주문취소 신청
	 * @param orderCancelApply
	 */
	void insertOrderCancelApply(OrderCancelApply orderCancelApply);

	/**
	 * 주문취소 상태 수정
	 * @param orderCancelApply
	 */
	int updateOrderCancelApply(OrderCancelApply orderCancelApply);

	/**
	 * 주문취소 상태 변경후 클래임 수량 조절
	 * @param orderCancelApply
	 */
	void updateClaimQuantityForCancel(OrderCancelApply orderCancelApply);

	/**
	 * 클레임 수량 조정 - 주문취소
	 * @param claimApplyItem
	 */
	void updateClaimQuantityForCancelApply(OrderCancelApply orderCancelApply);

	/**
	 *
	 * @param orderCancelApply
	 */
	void updateOrderItemQuantityForCancel(OrderCancelApply orderCancelApply);

	/**
	 * 부분 취소 신청
	 * @param orderCancelApply
	 */
	void copyOrderItemForCancelApply(OrderCancelApply orderCancelApply);

	/**
	 * 교환 신청
	 * @param orderExchangeApply
	 */
	void insertOrderExchangeApply(OrderExchangeApply orderExchangeApply);

	/**
	 * 부분 교환 신청
	 * @param orderExchangeApply
	 */
	void copyOrderItemForExchangeApply(OrderExchangeApply orderExchangeApply);

	void updateClaimQuantityForExchange(OrderExchangeApply orderExchangeApply);

	void updateClaimQuantityForExchangeApply(OrderExchangeApply orderExchangeApply);

	void updateOrderItemQuantityForExchange(OrderExchangeApply orderExchangeApply);

	/**
	 * 관리자용 교환 신청정보 수정
	 * @param orderExchangeApply
	 * @return
	 */
	int updateOrderExchangeApply(OrderExchangeApply orderExchangeApply);

	/**
	 * 취소 처리 완료 - 환불까지 완료
	 * @param refundCode
	 */
	void updateCancelStatusForRefund(String refundCode);

	/**
	 * 반품 처리 완료 - 환불까지 완료
	 * @param refundCode
	 */
	void updateReturnStatusForRefund(String refundCode);

	/**
	 * 관리자 주문 상세에서 취소 신청시 환불내역으로 바로보내기 할때 등록된 취소 신청 목록 조회
	 * @param param
	 * @return
	 */
	List<OrderCancelApply> getAdminApplyCancelListByParam(List<HashMap<String, String>> param);

	/**
	 * 교환, 반품 사유 상품별로 불러오기
	 * @param orderItem
	 */
	String getClaimRefusalReasonText(OrderItem orderItem);

	/**
	 * 환불신청 취소정보 조회
	 * @param refundCode
	 * @return
	 */
	List<OrderCancelApply> getOrderRefundCancel(String refundCode);

	/**
	 * 환불신청 반품정보 조회
	 * @param refundCode
	 * @return
	 */
	List<OrderReturnApply> getOrderRefundReturn(String refundCode);

	/**
	 * ERP 주문 상태 임시 테이블과 맵핑하여 OP_ORDER_EXCHANGE_APPLY 테이블의 교환 배송정보를 업데이트 한다.
	 * @param batchKey
	 * @return
	 */
	void updateOrderExchangeWithErpTempTable(BatchKey batchKey);

	/**
	 *
	 * @param ols
	 */
    void updateOrderExchangeByIfOrderListPutData(OrderLineStatus ols);
}
