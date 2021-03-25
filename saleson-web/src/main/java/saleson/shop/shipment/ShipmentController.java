package saleson.shop.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import saleson.common.utils.SellerUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.shipment.domain.Shipment;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;



@Controller
@RequestMapping("/shipment")
@RequestProperty(layout="default")
public class ShipmentController {
	
	@Autowired
	private ShipmentService shipmentService;
	
}
