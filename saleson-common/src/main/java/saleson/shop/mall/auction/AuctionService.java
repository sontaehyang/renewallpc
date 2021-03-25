package saleson.shop.mall.auction;

import java.util.HashMap;

import saleson.shop.mall.domain.MallConfig;
import saleson.shop.mall.domain.MallOrder;
import saleson.shop.mall.domain.MallOrderCancel;
import saleson.shop.mall.domain.MallOrderExchange;
import saleson.shop.mall.domain.MallOrderReturn;

public interface AuctionService {
	
	/**
	 * 옥션 신규 주문목록을 조회
	 * @param apiKey
	 * @param searchStartDate
	 * @param searchEndDate
	 * @return
	 */
	public MallConfig newOrderCollect(String apiKey, String searchStartDate, String searchEndDate, MallConfig mallConfig);
	
	/**
	 * 옥션 클레임 수집
	 * @param apiKey
	 * @param searchStartDate
	 * @param searchEndDate
	 * @param mallConfig
	 * @return
	 */
	public MallConfig claimCollect(String apiKey, String searchStartDate, String searchEndDate, MallConfig mallConfig);
	
	/**
	 * 발주확인
	 * @param apiKey
	 * @param product
	 * @param stockMap
	 * @return
	 */
	public void packaging(String apiKey, MallOrder mallOrder, HashMap<String, Integer> stockMap);
	
	/**
	 * 배송 처리
	 * @param apiKey
	 * @param mallOrder
	 */
	public void shipping(String apiKey, MallOrder mallOrder);
	
	/**
	 * 주문 취소 승인
	 * @param apiKey
	 * @param cancel
	 * @return
	 */
	public void cancelConfirm(String apiKey, MallOrderCancel cancel);
	
	/**
	 * 주문 취소 요청 거절
	 * @param apiKey
	 * @param cancel
	 * @return
	 */
	public void cancelRefusal(String apiKey, MallOrderCancel cancel);
	
	/**
	 * 주문취소 요청 - 관리자
	 * @param apiKey
	 * @param cancel
	 * @return
	 */
	public void cancel(String apiKey, MallOrderCancel cancel);
	
	/**
	 * 반품 신청 승인
	 * @param apiKey
	 * @param returnApply
	 */
	public void returnConfirm(String apiKey, MallOrderReturn returnApply);
	
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
	 * 교환 신청 승인
	 * @param apiKey
	 * @param exchangeApply
	 */
	public void exchangeConfirm(String apiKey, MallOrderExchange exchangeApply);
}
