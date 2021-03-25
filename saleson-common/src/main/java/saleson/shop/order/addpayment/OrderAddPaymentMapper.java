package saleson.shop.order.addpayment;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.shop.order.addpayment.domain.OrderAddPayment;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderCancelShipping;
import saleson.shop.order.claimapply.domain.OrderReturnApply;

import java.util.List;

@Mapper("orderAddPaymentMapper")
public interface OrderAddPaymentMapper {

	/**
	 * 중복등록 체크
	 * @param orderAddPayment
	 * @return
	 */
	public int getDuplicateRegistrationCheckByIssueCode(OrderAddPayment orderAddPayment);
	
	/**
	 * 환불 코드로 신청된 추가 결제 정보 조회
	 * @param refundCode
	 * @return
	 */
	public List<OrderAddPayment> getOrderAddPaymentListByRefundCode(String refundCode);
	
	/**
	 * 추가금액 정보 등록
	 * @param orderAddPayment
	 */
	public void insertOrderAddPayment(OrderAddPayment orderAddPayment);
	
	/**
	 * 추가 결제 정보 삭제 - 데이터 완전 삭제
	 * @param addPaymentId
	 */
	public int deleteOrderAddPaymentById(int addPaymentId);

    /**
     * 주문 취소 신청-> 환불 취소 신청시 추가 결제 정보 삭제
     * @param orderCancelApply
     */
    public void deleteOrderAddPaymentByCancel(OrderCancelApply orderCancelApply);

    /**
     * 반품 신청 -> 환불 취소 신청시 추가 결제 정보 삭제
     * @param orderReturnApply
     */
    public void deleteOrderAddPaymentByReturn(OrderReturnApply orderReturnApply);

    /**
     * 추가 배송비 정보 카운트
     * @param orderCancelShipping
     * @return
     */
    public int getOrderAddPaymentCount(OrderCancelApply orderCancelShipping);
}
