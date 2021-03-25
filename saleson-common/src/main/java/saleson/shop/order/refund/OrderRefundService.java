package saleson.shop.order.refund;

import java.util.List;

import saleson.shop.order.claimapply.domain.ClaimApply;
import saleson.shop.order.claimapply.support.ReturnApply;
import saleson.shop.order.refund.domain.OrderRefund;
import saleson.shop.order.refund.support.OrderRefundParam;
import saleson.shop.order.support.EditPayment;

public interface OrderRefundService {
	
	/**
	 * 사용자 주문취소시 환불정보 만들기
	 * @return
	 */
	public OrderRefund getOrderCancelRefundForUser(ClaimApply claimApply);
	
	/**
	 * 환불 신청정보 상세
	 * @param refundCode
	 * @return
	 */
	public OrderRefund getOrderRefundByCode(String refundCode);
	
	/**
	 * 환불 신청 목록
	 * @param param
	 * @return
	 */
	public List<OrderRefund> getOrderRefundListByParam(OrderRefundParam param);
	
	/**
	 * 환불 코드를 생성하거나 조회함
	 * @param param
	 * @return
	 */
	public String getActiveRefundCodeByParam(OrderRefundParam param);
	
	/**
	 * 환불 코드를 생성하거나 조회함
	 * @param param
	 * @return
	 */
	public String getNewRefundCodeByParam(OrderRefundParam param);

	/**
	 * 환불 처리
	 * @param orderRefund
	 * @param editPayment
	 */
	public void orderRefundProcess(OrderRefund orderRefund, EditPayment editPayment);
	
	/**
	 * 환불신청 취소
	 * @param refundCode
	 */
	void cancelRefund(String refundCode);
}
