package saleson.shop.shipment;

import java.util.List;

import saleson.shop.shipment.domain.Shipment;
import saleson.shop.shipment.support.ShipmentParam;

public interface ShipmentService {
	/**
	 * 판매자 출고지/배송비 목록 조회.
	 * @param sellerId
	 * @return
	 */
	public List<Shipment> getShipmentListBySellerId(long sellerId);
	
	
	/**
	 * 출고지/배송비 조회.
	 * @param shipmentId
	 * @return
	 */
	public Shipment getShipmentById(int shipmentId);
	
	
	/**
	 * 출고지/배송비 조회.(param)
	 * @param shipmentParam
	 * @return
	 */
	public Shipment getShipmentByParam(ShipmentParam shipmentParam);
	
	
	/**
	 * 기본 출고지/배송비 조회
	 * @return
	 */
	public Shipment getDefaultShipment(Shipment shipment);
	
	
	/**
	 * 출고지/배송비 등록
	 * @param shipment
	 */
	public void insertShipment(Shipment shipment);
	
	
	/**
	 * 출고지/배송비 수정
	 * @param shipment
	 */
	public void updateShipment(Shipment shipment);
	
	
	/**
	 * 출고지/배송비 삭제
	 * @param shipmentId
	 */
	public void deleteShipmentById(int shipmentId);
	
	
}
