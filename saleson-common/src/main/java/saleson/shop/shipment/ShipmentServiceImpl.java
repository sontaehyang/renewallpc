package saleson.shop.shipment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;

import saleson.shop.shipment.domain.Shipment;
import saleson.shop.shipment.support.ShipmentParam;


@Service("shipmentService")
public class ShipmentServiceImpl implements ShipmentService {
	private static final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

	@Autowired
	private ShipmentMapper shipmentMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public List<Shipment> getShipmentListBySellerId(long sellerId) {
		return shipmentMapper.getShipmentListBySellerId(sellerId);
	}

	@Override
	public Shipment getShipmentById(int shipmentId) {
		return shipmentMapper.getShipmentById(shipmentId);
	}
	
	@Override
	public Shipment getShipmentByParam(ShipmentParam shipmentParam) {
		return shipmentMapper.getShipmentByParam(shipmentParam);
	}

	@Override
	public void insertShipment(Shipment shipment) {
		shipment.setShipmentId(sequenceService.getId("OP_SHIPMENT"));
		updateDefaultAddressFlag(shipment);
		
		shipmentMapper.insertShipment(shipment);
	}

	@Override
	public void updateShipment(Shipment shipment) {
		updateDefaultAddressFlag(shipment);
		
		shipmentMapper.updateShipment(shipment);
	}

	@Override
	public void deleteShipmentById(int shipmentId) {
		shipmentMapper.deleteShipmentById(shipmentId);
	}

	public void updateDefaultAddressFlag(Shipment shipment) {
		if (shipment.getDefaultAddressFlag().equals("Y")) {
			shipmentMapper.updateDefaultAddressFlag(shipment);
		}
	}

	@Override
	public Shipment getDefaultShipment(Shipment shipment) {
		return shipmentMapper.getDefaultShipment(shipment);
	}

}
