package saleson.shop.shipment;

import java.util.List;

import saleson.shop.shipment.domain.Shipment;
import saleson.shop.shipment.support.ShipmentParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("shipmentMapper")
public interface ShipmentMapper {
	
	/**
	 * 판매자 출고지/배송비 목록 조회.
	 * @param sellerId
	 * @return
	 */
	List<Shipment> getShipmentListBySellerId(long sellerId);
	
	
	/**
	 * 출고지/배송비 조회.
	 * @param shipmentId
	 * @return
	 */
	Shipment getShipmentById(int shipmentId);
	
	
	/**
	 * 출고지/배송비 조회.(param)
	 * @param shipmentParam
	 * @return
	 */
	Shipment getShipmentByParam(ShipmentParam shipmentParam);
	
	
	/**
	 * 출고지/배송비 등록
	 * @param shipment
	 */
	void insertShipment(Shipment shipment);
	
	
	/**
	 * 출고지/배송비 수정
	 * @param shipment
	 */
	void updateShipment(Shipment shipment);
	
	
	/**
	 * 출고지/배송비 삭제
	 * @param shipmentId
	 */
	void deleteShipmentById(int shipmentId);
	
	
	/**
	 * 기본주소 수정
	 * @param shipment
	 */
	void updateDefaultAddressFlag(Shipment shipment);
	
	
	/**
	 * 기본 출고지/배송비 조회
	 * @return
	 */
	Shipment getDefaultShipment(Shipment shipment);
}
