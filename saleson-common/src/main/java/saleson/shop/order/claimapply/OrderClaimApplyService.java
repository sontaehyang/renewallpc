package saleson.shop.order.claimapply;

import saleson.shop.order.addpayment.domain.OrderAddPayment;
import saleson.shop.order.claimapply.domain.*;
import saleson.shop.order.claimapply.support.ClaimApplyParam;
import saleson.shop.order.claimapply.support.ExchangeApply;
import saleson.shop.order.claimapply.support.ReturnApply;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShipping;
import saleson.shop.order.support.OrderParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderClaimApplyService {
	
	/**
	 * 배송비 재계산 - 사용자
	 * @param claimApply
	 * @return
	 */
	public List<OrderShipping> getReShippingAmountForUser(ClaimApply claimApply);
	
	/**
	 * 배송비 재계산 - 관리자
	 * @param claimApply
	 */
	public List<OrderShipping> getReShippingAmountForManager(ClaimApply claimApply);
	
	/**
	 * 처리해야하는 취소 목록
	 * @param claimApplyParam
	 * @return
	 */
	public List<OrderCancelShipping> getActiveCancelListByParam(ClaimApplyParam claimApplyParam);
	
	/**
	 * 주문 상세용 히스토리 (승인대기, 완료, 거절)
	 * @param claimApplyParam
	 * @return
	 */
	public List<OrderCancelApply> getCancelHistoryListByParam(ClaimApplyParam claimApplyParam);
	
	/**
	 * 주문취소 목록
	 * @param orderParam
	 */
	public List<OrderCancelApply> getCancelListByParam(ClaimApplyParam claimApplyParam);
	
	/**
	 * 반품 처리완료 로그 보기
	 * @param claimCode
	 * @return
	 */
	public List<OrderReturnApply> getReturnApplyByClaimCode(String[] claimCode);
	
	/**
	 * 교환 처리완료 로그 보기
	 * @param claimCode
	 * @return
	 */
	public List<OrderExchangeApply> getExchangeApplyByClaimCode(String[] claimCode);
	
	
	/**
	 * 처리해야하는 반품 목록
	 * @param claimApplyParam
	 * @return
	 */
	public List<OrderReturnApply> getActiveReturnListByParam(ClaimApplyParam claimApplyParam);
	
	/**
	 * 주문 상세용 히스토리 (승인대기, 완료, 거절)
	 * @param claimApplyParam
	 * @return
	 */
	public List<OrderReturnApply> getReturnHistoryListByParam(ClaimApplyParam claimApplyParam);
	
	/**
	 * 반품 목록
	 * @param orderParam
	 */
	public List<OrderReturnApply> getReturnListByParam(ClaimApplyParam claimApplyParam);
	
	/**
	 * 교환 목록
	 * @param orderParam
	 */
	public List<OrderExchangeApply> getExchangeListByParam(ClaimApplyParam claimApplyParam);
	
	/**
	 * 주문취소 신청
	 * @param claimApply
	 */
	public void insertOrderCancelApply(ClaimApply claimApply);

	/**
	 * 반품 신청
	 * @param returnApply
	 */
	public void insertOrderReturnApply(ReturnApply returnApply);
	
	/**
	 * 주문취소 처리
	 */
	public void orderCancelProcess(ClaimApply claimApply);

	/**
	 * 주분 반품 처리
	 * @param claimApply
	 */
	public void orderReturnProcess(ClaimApply claimApply);
	
	/**
	 * 주문 반품 저장
	 * @param claimApply
	 */
	public void orderReturnSaveProcess(ClaimApply claimApply);
	
	/**
	 * 주문 반품 미리보기
	 * @param claimApply
	 * @param order
	 * @return
	 */
	public List<OrderAddPayment> orderReturnViewData(ClaimApply claimApply, Order order);
	
	/**
	 * 주문 교환 처리
	 * @param claimApply
	 */
	public void orderExchangeProcess(ClaimApply claimApply);
	
	/**
	 * 교환 신청
	 * @param orderExchangeApply
	 */
	public void insertOrderExchangeApply(ExchangeApply exchangeApply);
	
	/**
	 * 교환 처리중 목록
	 * @param claimApplyParam
	 */
	public List<OrderExchangeApply> getActiveExchangeListByParam(ClaimApplyParam claimApplyParam);
	
	/**
	 * 주문 상세용 히스토리 (승인대기, 완료, 거절)
	 * @param claimApplyParam
	 * @return
	 */
	public List<OrderExchangeApply> getExchangeHistoryListByParam(ClaimApplyParam claimApplyParam);
	
	/**
	 * 주문 전체 취소
	 * @param orderParam
	 */
	public void orderCancelAllProcess(OrderParam orderParam, HttpServletRequest request);


	/**
	 * [배치처리용] 주문 전체 취소 - REQUIRES_NEW
	 * @param orderParam
	 */
	void orderCancelAllProcessNewTx(OrderParam orderParam);

	/**
	 * 관리자 주문 상세에서 취소 신청시 환불내역으로 바로보내기 할때 등록된 취소 신청 목록 조회
	 * @param ids
	 * @return
	 */
	public List<OrderCancelApply> getAdminApplyCancelListByIds(String[] ids);

	/**
	 * 교환 목록에 사은품 정보 세팅
	 * @param list
	 */
	void setOrderGiftItemForOrderExchangeApply(List<OrderExchangeApply> list);

	/**
	 * 환불 목록에 사은품 정보 세팅
	 * @param list
	 */
	void setOrderGiftItemForOrderReturnApply(List<OrderReturnApply> list);

	/**
	 * 취소 목록에 사은품 정보 세팅
	 * @param list
	 */
	void setOrderGiftItemForOrderCancelApply(List<OrderCancelApply> list);


	/**
	 * 취소 목록에 추가구성상품 정보 세팅
	 * @param list
	 */
	void setOrderAdditionItemForOrderCancelApply(List<OrderCancelApply> list, List<OrderCancelApply> additionList);

	/**
	 * 환불 목록에 추가구성상품 정보 세팅
	 * @param list
	 * @param additionList
	 */
	void setOrderAdditionItemForOrderReturnApply(List<OrderReturnApply> list, List<OrderReturnApply> additionList);

	/**
	 * 교환목록에 추가구성상품 정보 세팅
	 * @param list
	 * @param additionList
	 */
	void setOrderAdditionItemForOrderExchangeApply(List<OrderExchangeApply> list, List<OrderExchangeApply> additionList);

	/**
	 * 교환 추가구성상품 정보 세팅
	 * @param orderItem
	 * @param additionList
	 */
	void setOrderAdditionItemForOrderItem(OrderItem orderItem, List<OrderItem> additionList);

	/**
	 * 본상품 교환신청 시 추가구성상품도 신청
	 * @param exchangeApply
	 */
	void insertAdditionExchangeApply(ExchangeApply exchangeApply);

	/**
	 * 본상품 반품 신청 시 추가구성상품도 신청
	 * @param returnApply
	 */
	void insertAdditionReturnApply(ReturnApply returnApply);
}
