package saleson.shop.shipmentreturn;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.item.ItemService;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;

import javax.servlet.http.HttpServletRequest;



@Controller
@RequestMapping({"/opmanager/shipment-return"})
@RequestProperty(template="opmanager", layout="default")
public class ShipmentReturnManagerController {
	@Autowired
	private ShipmentReturnService shipmentReturnService;
	
	@Autowired
	private ItemService itemService;
	
	
	@GetMapping("list")
	public String list(ShipmentReturn shipmentReturn, Model model) {
		if (!(UserUtils.isManagerLogin() || SellerUtils.isSellerLogin())) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("list", shipmentReturnService.getShipmentReturnListBySellerId(SellerUtils.getSellerId()));
		model.addAttribute("shipmentReturn", shipmentReturn);
		return "view";
	}
	
	
	/**
	 * 출고지/배송비 목록 (팝업)
	 * @param model
	 * @return
	 */
	@GetMapping("list-popup")
	@RequestProperty(template="opmanager", layout="base")
	public String listPopup(ShipmentReturn shipmentReturn, Model model) {
		if (!(UserUtils.isManagerLogin() || SellerUtils.isSellerLogin())) {
			throw new PageNotFoundException();
		}
		
		shipmentReturn.setSellerId(SellerUtils.DEFAULT_OPMANAGER_SELLER_ID);
		model.addAttribute("list", shipmentReturnService.getShipmentReturnListBySellerId(SellerUtils.DEFAULT_OPMANAGER_SELLER_ID));
		model.addAttribute("shipmentReturn", shipmentReturn);
		model.addAttribute("isPopup", true);
		
		model.addAttribute("hasModifyPermission", true);
		
		return "view";
	}
	
	@GetMapping("list-popup/{sellerId}")
	@RequestProperty(template="opmanager", layout="base")
	public String listPopup(@PathVariable("sellerId") long sellerId, ShipmentReturn shipmentReturn, Model model) {
		if (!(UserUtils.isManagerLogin() || SellerUtils.isSellerLogin())) {
			throw new PageNotFoundException();
		}
		shipmentReturn.setSellerId(sellerId);
		model.addAttribute("list", shipmentReturnService.getShipmentReturnListBySellerId(sellerId));
		model.addAttribute("shipmentReturn", shipmentReturn);
		model.addAttribute("isPopup", true);
		model.addAttribute("hasModifyPermission", false);
		return "view:/opmanager/shipment-return/list-popup";
	}
	
	
	@PostMapping("create")
	public String create(HttpServletRequest request, ShipmentReturn shipmentReturn) {
		shipmentReturnService.insertShipmentReturn(shipmentReturn);
		
		FlashMapUtils.setMessage("등록되었습니다.");
		if (request.getHeader("referer").indexOf("popup") > -1) {
			if (shipmentReturn.getSellerId() == SellerUtils.DEFAULT_OPMANAGER_SELLER_ID) {
				return "redirect:/opmanager/shipment-return/list-popup";
			} 
			return "redirect:/opmanager/shipment-return/list-popup/" + SellerUtils.getSellerId();
		}	
		
		return "redirect:/opmanager/shipment-return/list";
	}
	
	@PostMapping("edit")
	public String edit(HttpServletRequest request, ShipmentReturn shipmentReturn) {
		shipmentReturnService.updateShipmentReturn(shipmentReturn);
		
		FlashMapUtils.setMessage("수정되었습니다.");
		if (request.getHeader("referer").indexOf("popup") > -1) {
			if (shipmentReturn.getSellerId() == SellerUtils.DEFAULT_OPMANAGER_SELLER_ID) {
				return "redirect:/opmanager/shipment-return/list-popup";
			} 
			return "redirect:/opmanager/shipment-return/list-popup/" + SellerUtils.getSellerId();
		}	
		
		return "redirect:/opmanager/shipment-return/list";
	}
	
	@PostMapping("delete")
	public String delete(HttpServletRequest request, ShipmentReturn shipmentReturn) {
		shipmentReturnService.deleteShipmentReturnById(shipmentReturn.getShipmentReturnId());
		
		ShipmentReturn defaultShipment = shipmentReturnService.getDefaultShipmentReturn(shipmentReturn);
		defaultShipment.setDeleteShipmentReturnId(shipmentReturn.getShipmentReturnId());
		
		itemService.updateShipmentReturn(defaultShipment);
		
		FlashMapUtils.setMessage("삭제되었습니다.");
		if (request.getHeader("referer").indexOf("popup") > -1) {
			if (shipmentReturn.getSellerId() == SellerUtils.DEFAULT_OPMANAGER_SELLER_ID) {
				return "redirect:/opmanager/shipment-return/list-popup";
			} 
			return "redirect:/opmanager/shipment-return/list-popup/" + SellerUtils.getSellerId();
		}	
		
		return "redirect:/opmanager/shipment-return/list";
		
	}
}
