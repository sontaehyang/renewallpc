package saleson.shop.deliveryhope;

import java.util.List;

import saleson.shop.deliveryhope.domain.DeliveryHope;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("deliveryHopeMapper")
public interface DeliveryHopeMapper {
	
	/**
	 * 배송희망 시간 리스트조회
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
