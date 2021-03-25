package saleson.shop.shipmentreturn;

import java.util.List;

import saleson.shop.shipmentreturn.domain.ShipmentReturn;
import saleson.shop.shipmentreturn.support.ShipmentReturnParam;

public interface ShipmentReturnService {
	
	List<ShipmentReturn> getShipmentReturnListBySellerId(long sellerId);

	
	/**
	 * 교환/반송지 정보 조회.
	 * @param shipmentParam
	 * @return
	 */
	ShipmentReturn getShipmentReturnByParam(ShipmentReturnParam shipmentReturnParam);

	
	/**
	 * 기본 교환/반송지 조회
	 * @return
	 */
	public ShipmentReturn getDefaultShipmentReturn(ShipmentReturn shipmentReturn);
	
	
	/**
	 * 교환/반송지 등록
	 * @param shipmentReturnParam
	 */
	public void insertShipmentReturn(ShipmentReturn shipmentReturn);
	
	
	/**
	 * 교환/반송지 수정
	 * @param shipmentReturnParam
	 */
	public void updateShipmentReturn(ShipmentReturn shipmentReturn);
	
	
	/**
	 * 기본주소 수정
	 * @param shipmentReturnParam
	 */
	public void updateDefaultAddressFlag(ShipmentReturn shipmentReturn); 
	
	
	/**
	 * 교환/반송지 삭제
	 * @param shipmentId
	 */
	public void deleteShipmentReturnById(int shipmentId);


	public ShipmentReturn getShipmentReturnById(int shipmentReturnId);
	
}
