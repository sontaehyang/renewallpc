package saleson.shop.deliveryhope;

import java.util.List;

import saleson.shop.deliveryhope.domain.DeliveryHope;


public interface DeliveryHopeService {
	

	/**
	 * 배송희망 시간 리스트 조회
	 * @return
	 */
	List<DeliveryHope> getDeliveryHopeList();
	
	/**
	 * 배송희망 시간 추가
	 * @param deliveryHope
	 */
	void insertDeliveryHope(DeliveryHope deliveryHope);
	
	/**
	 * 배송희망 시간 수정
	 * @param deliveryHope
	 */
	void updateDeliveryHope(DeliveryHope deliveryHope);
	
	/**
	 * 배송희망 시간 삭제
	 * @param deliveryHopeId
	 */
	void deleteDeliveryHope(int deliveryHopeId);
}
