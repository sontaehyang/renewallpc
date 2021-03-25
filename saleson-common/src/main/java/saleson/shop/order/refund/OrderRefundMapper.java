package saleson.shop.order.refund;

import java.util.List;

import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.refund.domain.OrderRefund;
import saleson.shop.order.refund.support.OrderRefundParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("orderRefundMapper")
public interface OrderRefundMapper {

	/**
	 * 환불 신청정보 상세
	 * @param refundCode
	 * @return
	 */
	public OrderRefund getOrderRefundByCode(String refundCode);

	/**
	 * 목록 카운트
	 * @param param
	 * @return
	 */
	public int getOrderRefundCountByParam(OrderRefundParam param);

	/**
	 * 목록
	 * @param param
	 * @return
	 */
	public List<OrderRefund> getOrderRefundListByParam(OrderRefundParam param);

	/**
	 * 처리 안된 환불 정보가 있으면 코드를 리턴함
	 * @param param
	 * @return
	 */
	public String getActiveRefundCodeByParam(OrderRefundParam param);

	/**
	 * 환불 신청 정보 등록
	 * @param orderRefund
	 */
	public void insertOrderRefund(OrderRefund orderRefund);

	/**
	 * 환불 정보 처리 완료
	 * @param orderRefund
	 */
	public void updateRefundStatus(OrderRefund orderRefund);

	/**
	 * 환불 정보 처리 완료 - 상품
	 * @param orderRefund
	 */
	public void updateRefundFinishedForItem(OrderRefund orderRefund);

	/**
	 * 환불 정보 삭제 - 환불 신청 취소시
	 * @param orderCancelApply
	 */
	public void deleteOrderRefundInfo(OrderCancelApply orderCancelApply);

	public void deleteOrderRefundInfoByReturn(OrderReturnApply orderReturnApply);
}
