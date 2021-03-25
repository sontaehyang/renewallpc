package saleson.shop.shipmentreturn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import saleson.common.utils.SellerUtils;
import saleson.common.utils.UserUtils;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;



@Controller
@RequestMapping("/shipment-return")
@RequestProperty(layout="default")
public class ShipmentReturnController {
	
	@Autowired
	private ShipmentReturnService shipmentReturnService;
	
	
	/**
	 * 출고지/배송비 목록 (팝업)
	 * @param model
	 * @return
	 */
	@GetMapping("list-popup")
	@RequestProperty(template="opmanager", layout="base")
	public String listPopup(Model model) {
		if (!(UserUtils.isManagerLogin() || SellerUtils.isSellerLogin())) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("list", shipmentReturnService.getShipmentReturnListBySellerId(SellerUtils.getSellerId()));
		return "view";
	}
}