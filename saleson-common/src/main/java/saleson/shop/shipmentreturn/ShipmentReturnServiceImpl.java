package saleson.shop.shipmentreturn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;

import saleson.common.utils.SellerUtils;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;
import saleson.shop.shipmentreturn.support.ShipmentReturnParam;

@Service("shipmentReturnService")
public class ShipmentReturnServiceImpl implements ShipmentReturnService {
	@Autowired
	private ShipmentReturnMapper shipmentReturnMapper;
	
	@Autowired
	private SequenceService sequenceService;	
	
	@Override
	public List<ShipmentReturn> getShipmentReturnListBySellerId(long sellerId) {
		return shipmentReturnMapper.getShipmentReturnListBySellerId(sellerId);
	}


	@Override
	public ShipmentReturn getShipmentReturnByParam(ShipmentReturnParam shipmentReturnParam) {
		return shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
	}


	@Override
	public void insertShipmentReturn(ShipmentReturn shipmentReturn) {
		shipmentReturn.setShipmentReturnId(sequenceService.getId("OP_SHIPMENT_RETURN"));
		shipmentReturn.setSellerId(SellerUtils.getSellerId());
		updateDefaultAddressFlag(shipmentReturn);
		
		shipmentReturnMapper.insertShipmentReturn(shipmentReturn);
	}


	@Override
	public void updateShipmentReturn(ShipmentReturn shipmentReturn) {
		updateDefaultAddressFlag(shipmentReturn);
		
		shipmentReturnMapper.updateShipmentReturn(shipmentReturn);
	}


	@Override
	public void updateDefaultAddressFlag(ShipmentReturn shipmentReturn) {
		if (shipmentReturn.getDefaultAddressFlag().equals("Y")) {
			shipmentReturnMapper.updateDefaultAddressFlag(shipmentReturn);
		}
	}


	@Override
	public ShipmentReturn getDefaultShipmentReturn(ShipmentReturn shipmentReturn) {
		return shipmentReturnMapper.getDefaultShipmentReturn(shipmentReturn);
	}


	@Override
	public void deleteShipmentReturnById(int shipmentId) {
		shipmentReturnMapper.deleteShipmentReturnById(shipmentId);
	}


	@Override
	public ShipmentReturn getShipmentReturnById(int shipmentReturnId) {

		return null;
	}

}
