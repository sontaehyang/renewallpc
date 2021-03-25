package saleson.shop.order.pg;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

public interface PgService {
	
	/**
	 * 결제 방식
	 * @param payType
	 * @return
	 */
	public String getPayType(String payType);
	
	/**
	 * 초기값 생성
	 * @param data
	 * @param session
	 * @return
	 */
	public HashMap<String, Object> init(Object data, HttpSession session);
	
	/**
	 * 결제
	 * @param data
	 * @param session
	 * @return
	 */
	public OrderPgData pay(Object data, HttpSession session);
	
	/**
	 * 취소
	 * @param orderPgData
	 * @return
	 */
	public boolean cancel(OrderPgData orderPgData);
	
	/**
	 * 입금확인
	 * @param pgData
	 * @return
	 */
	public String confirmationOfPayment(PgData pgData);
	
	/**
	 * 부분취소
	 * @param orderPgData
	 * @return
	 */
	public OrderPgData partCancel(OrderPgData orderPgData);
	
	/**
	 * 현금영수증 발급
	 * @return
	 */
	public CashbillResponse cashReceiptIssued(CashbillParam cashbillParam);
	
	/**
	 * 현금영수증 발급 취소
	 * @return
	 */
	public CashbillResponse cashReceiptCancel(CashbillParam cashbillParam);

	/**
	 * 배송등록 요청
	 * @param shippingParam
	 * @param orderItem 
	 * @return
	 */
	boolean delivery(HashMap<String, Object> paramMap);
	
	/**
	 * 에스크로 구매확정
	 * @param request 
	 * @return
	 */
	boolean escrowConfirmPurchase(HttpServletRequest request);
	
	/**
	 * 에스크로 구매거절 확인
	 * @param paramMap
	 * @return
	 */
	
	boolean escrowDenyConfirm(List<String> param);
}
