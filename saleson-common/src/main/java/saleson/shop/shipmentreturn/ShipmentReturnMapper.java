package saleson.shop.shipmentreturn;

import java.util.List;

import saleson.shop.shipmentreturn.domain.ShipmentReturn;
import saleson.shop.shipmentreturn.support.ShipmentReturnParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("shipmentReturnMapper")
public interface ShipmentReturnMapper {

	List<ShipmentReturn> getShipmentReturnListBySellerId(long sellerId);

	
	/**
	 * 교환/반송지 정보 조회
	 * @param shipmentParam
	 * @return
	 */
	ShipmentReturn getShipmentReturnByParam(ShipmentReturnParam shipmentReturnParam);

	
	/**
	 * 교환/반송지 등록
	 * @param shipmentReturnParam
	 */
	void insertShipmentReturn(ShipmentReturn shipmentReturn);
	
	
	/**
	 * 교환/반송지 수정
	 * @param shipmentReturnParam
	 */
	void updateShipmentReturn(ShipmentReturn shipmentReturn);
	
	
	/**
	 * 기본주소 수정
	 * @param shipmentReturnParam
	 */
	void updateDefaultAddressFlag(ShipmentReturn shipmentReturn);
	
	
	/**
	 * 기본 교환/반송지 조회
	 * @param shipment
	 * @return
	 */
	ShipmentReturn getDefaultShipmentReturn(ShipmentReturn shipmentReturn);
	
	
	/**
	 * 교환/반송지 삭제
	 * @param shipmentId
	 */
	void deleteShipmentReturnById(int shipmentId);
}
