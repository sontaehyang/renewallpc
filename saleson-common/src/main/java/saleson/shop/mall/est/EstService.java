package saleson.shop.mall.est;

import java.util.HashMap;

import saleson.shop.mall.domain.MallConfig;
import saleson.shop.mall.domain.MallOrder;
import saleson.shop.mall.domain.MallOrderCancel;
import saleson.shop.mall.domain.MallOrderExchange;
import saleson.shop.mall.domain.MallOrderReturn;
import saleson.shop.mall.est.domain.Orders;
import saleson.shop.mall.est.domain.Product;

public interface EstService {

	/**
	 * 11번가 신규 주문목록을 조회
	 * @param apiKey
	 * @param searchStartDate
	 * @param searchEndDate
	 * @return
	 */
	public MallConfig newOrderCollect(String apiKey, String searchStartDate, String searchEndDate, MallConfig mallConfig);
	
	/**
	 * 11번가 클레임 수집
	 * @param apiKey
	 * @param searchStartDate
	 * @param searchEndDate
	 * @param mallConfig
	 * @return
	 */
	public MallConfig claimCollect(String apiKey, String searchStartDate, String searchEndDate, MallConfig mallConfig);
	
	/**
	 * 11번가 주문상태를 조회
	 * @param apiKey
	 * @param orderCode
	 * @return
	 */
	public Orders getOrderStatus(String apiKey, String orderCode);
	
	/**
	 * 발주확인
	 * @param apiKey
	 * @param product
	 * @param stockMap
	 * @return
	 */
	public void packaging(String apiKey, MallOrder mallOrder, HashMap<String, Integer> stockMap);
	
	/**
	 * orderStatus를 이용해 주문서 목록을 가져온후 현재 처리할 주문의 정보를 뽑아옴
	 * @param orders
	 * @param orderIndex
	 * @param orderCode
	 * @return
	 */
	public Product getNowProductInfoForOrders(Orders orders, int orderIndex, String orderCode);

	/**
	 * 배송 시작
	 * @param apiKey
	 * @param mallOrder
	 * @param orderMap
	 * @return
	 */
	public void shipping(String apiKey, MallOrder mallOrder);
	
	/**
	 * 주문취소 요청 - 관리자
	 * @param apiKey
	 * @param cancelApply
	 * @return
	 */
	public void cancelApply(String apiKey, MallOrderCancel cancelApply);
	
	/**
	 * 주문 취소 승인
	 * @param apiKey
	 * @param cancelApply
	 * @return
	 */
	public void cancelConfirm(String apiKey, MallOrderCancel cancelApply);
	
	/**
	 * 반품 신청 승인
	 * @param apiKey
	 * @param returnApply
	 */
	public void returnConfirm(String apiKey, MallOrderReturn returnApply);
	
	/**
	 * 교환 신청 승인
	 * @param apiKey
	 * @param exchangeApply
	 */
	public void exchangeConfirm(String apiKey, MallOrderExchange exchangeApply);
	
	/**
	 * 주문 취소 요청 거절
	 * @param apiKey
	 * @param cancelApply
	 * @return
	 */
	public void cancelRefusal(String apiKey, MallOrderCancel cancelApply);
	
	/**
	 * 반품 보류
	 * @param apiKey
	 * @param returnApply
	 */
	public void returnHold(String apiKey, MallOrderReturn returnApply);
	
	/**
	 * 반품 거부
	 * @param apiKey
	 * @param returnApply
	 */
	public void returnRefusal(String apiKey, MallOrderReturn returnApply);
	
	/**
	 * 교환 거부
	 * @param apiKey
	 * @param exchangeApply
	 */
	public void exchangeRefusal(String apiKey, MallOrderExchange exchangeApply);
}
