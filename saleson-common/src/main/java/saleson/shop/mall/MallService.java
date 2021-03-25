package saleson.shop.mall;

import java.util.List;

import saleson.shop.mall.domain.MallConfig;
import saleson.shop.mall.domain.MallOrder;
import saleson.shop.mall.domain.MallOrderCancel;
import saleson.shop.mall.domain.MallOrderExchange;
import saleson.shop.mall.domain.MallOrderReturn;
import saleson.shop.mall.support.MallConfigParam;
import saleson.shop.mall.support.MallOrderParam;


public interface MallService {

	/**
	 * 마켓 주문 상세 조회
	 * @param mallOrderParam
	 * @return
	 */
	public MallOrder getMallOrderDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 신규 주문내역 조회
	 * @param mallOrderParam
	 * @return
	 */
	public List<MallOrder> getNewOrderListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 신규 주문내역 상세
	 * @param mallOrderParam
	 * @return
	 */
	public MallOrder getNewOrderDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 신규주문 리스트 상태변경
	 * @param mode
	 * @param mallOrderParam
	 */
	public void newOrderListUpdateTx(String mode, MallOrderParam mallOrderParam);
	
	/**
	 * 배송 준비중 내역 조회
	 * @param mallOrderParam
	 * @return
	 */
	public List<MallOrder> getShippingReadyListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 배송 준비중 리스트 상태변경
	 * @param mode
	 * @param mallOrderParam
	 */
	public void shippingReadyListUpdate(String mode, MallOrderParam mallOrderParam);
	
	/**
	 * 배송중 내역 조회
	 * @param mallOrderParam
	 */
	public List<MallOrder> getShippingListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 주문취소 요청목록
	 * @param mallOrderParam
	 * @return
	 */
	public List<MallOrderCancel> getCancelRequestListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 주문취소 상세
	 * @param mallOrderParam
	 * @return
	 */
	public MallOrderCancel getCancelRequestDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 주문취소 요청 리스트 상태변경
	 * @param mode
	 * @param mallOrderParam
	 */
	public void cancelRequestListUpdate(String mode, MallOrderParam mallOrderParam);
	
	/**
	 * 주문취소 완료목록
	 * @param mallOrderParam
	 * @return
	 */
	public List<MallOrderCancel> getCancelFinishListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 반품신청 목록
	 * @param mallOrderParam
	 * @return
	 */
	public List<MallOrderReturn> getReturnRequestListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 반품신청 상세
	 * @param mallOrderParam
	 * @return
	 */
	public MallOrderReturn getReturnRequestDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 반품 완료 목록
	 * @param mallOrderParam
	 * @return
	 */
	public List<MallOrderReturn> getReturnFinishListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 반품요청 리스트 상태 변경
	 * @param mode
	 * @param mallOrderParam
	 */
	public void returnRequestListUpdate(String mode, MallOrderParam mallOrderParam);
	
	/**
	 * 교환요청 리스트
	 * @param mallOrderParam
	 * @return
	 */
	public List<MallOrderExchange> getExchangeRequestListByParam(MallOrderParam mallOrderParam);

	/**
	 * 교환요청 상세
	 * @param mallOrderParam
	 * @return
	 */
	public MallOrderExchange getExchangeRequestDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 교환요청 리스트 상태 변경
	 * @param mode
	 * @param mallOrderParam
	 */
	public void exchangeRequestListUpdate(String mode, MallOrderParam mallOrderParam);
	
	/**
	 * 교환 완료 리스트
	 * @param mallOrderParam
	 * @return
	 */
	public List<MallOrderExchange> getExchangeFinishListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 등록된 오픈마켓 목록을 조회
	 * @param mallConfigParam
	 * @return
	 */
	public List<MallConfig> getMallConfigListByParam(MallConfigParam mallConfigParam);
	
	/**
	 * 신규 주문 취합
	 * @param mallConfigParam
	 */
	public void orderCollectTx(MallConfigParam mallConfigParam);
	
	/**
	 * 주문 취소
	 * @param mallType
	 * @param postCancelApply
	 */
	public void cancelAction(String mallType, MallOrderCancel postCancelApply);
	
	/**
	 * 주문취소 거절
	 * @param mallType
	 * @param mallOrder
	 */
	public void cancelRefusalAction(String mallType, MallOrderCancel postCancelApply);
	
	/**
	 * 반품 보류
	 * @param mallType
	 * @param postReturnApply
	 */
	public void returnHoldAction(String mallType, MallOrderReturn postReturnApply);
	
	/**
	 * 반품 거부
	 * @param mallType
	 * @param postReturnApply
	 */
	public void returnRefusalAction(String mallType, MallOrderReturn postReturnApply);
	
	/**
	 * 교환 거부
	 * @param mallType
	 * @param postReturnApply
	 */
	public void exchangeRefusalAction(String mallType, MallOrderExchange postExchangeApply);
}
