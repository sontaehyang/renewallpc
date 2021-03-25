package saleson.shop.order.addpayment;

import saleson.shop.order.addpayment.domain.OrderAddPayment;

public interface OrderAddPaymentService {
	
	/**
	 * 중복등록 체크
	 * @param orderAddPayment
	 * @return
	 */
	public int getDuplicateRegistrationCheckByIssueCode(OrderAddPayment orderAddPayment);
	
	/**
	 * 추가금액 정보 등록
	 * @param orderAddPayment
	 */
	public void insertOrderAddPayment(OrderAddPayment orderAddPayment);
}
