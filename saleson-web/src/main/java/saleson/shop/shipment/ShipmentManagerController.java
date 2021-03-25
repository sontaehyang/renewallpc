package saleson.shop.shipment;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.item.ItemService;
import saleson.shop.shipment.domain.Shipment;

import javax.servlet.http.HttpServletRequest;



@Controller
@RequestMapping({"/opmanager/shipment"})
@RequestProperty(template="opmanager", layout="default")
public class ShipmentManagerController {
	private static final Logger log = LoggerFactory.getLogger(ShipmentManagerController.class);
	
	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private ItemService itemService;
	
	
	/**
	 * 출고지 / 배송비 
	 * @param model
	 * @param shipment
	 * @return
	 */
	@GetMapping("list")
	public String list(Model model, Shipment shipment) {
		if (!(UserUtils.isManagerLogin() || SellerUtils.isSellerLogin())) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("list", shipmentService.getShipmentListBySellerId(SellerUtils.getSellerId()));
		model.addAttribute("shipment", shipment);

		
		return "view";
	}
	
	
	/**
	 * 출고지/배송비 목록 (팝업) - 본사용
	 * @param model
	 * @return
	 */
	@GetMapping("list-popup")
	@RequestProperty(template="opmanager", layout="base")
	public String listPopup(Model model, Shipment shipment) {
		if (!(UserUtils.isManagerLogin() || SellerUtils.isSellerLogin())) {
			throw new PageNotFoundException();
		}
		
		shipment.setSellerId(SellerUtils.DEFAULT_OPMANAGER_SELLER_ID);
		model.addAttribute("shipmentGroupCodeValue", "HQ_SGC");
		model.addAttribute("list", shipmentService.getShipmentListBySellerId(SellerUtils.DEFAULT_OPMANAGER_SELLER_ID));
		model.addAttribute("shipment", shipment);
		model.addAttribute("isPopup", true);
		
		if (UserUtils.isManagerLogin()) {
			model.addAttribute("hasModifyPermission", true);
		}
		return "view";
	}
	
	
	/**
	 * 출고지/배송비 목록 (업체)
	 * @param sellerId
	 * @param model
	 * @param shipment
	 * @return
	 */
	@GetMapping("list-popup/{sellerId}")
	@RequestProperty(template="opmanager", layout="base")
	public String listPopup(@PathVariable("sellerId") long sellerId, Model model, Shipment shipment) {
		if (!(UserUtils.isManagerLogin() || SellerUtils.isSellerLogin())) {
			throw new PageNotFoundException();
		}
		
		shipment.setSellerId(sellerId);
		
		model.addAttribute("shipmentGroupCodeValue", "SELLER_" + sellerId + "_SGC");
		model.addAttribute("list", shipmentService.getShipmentListBySellerId(sellerId));
		model.addAttribute("shipment", shipment);
		model.addAttribute("isPopup", true);
		return "view:/opmanager/shipment/list-popup";
	}
	
	@PostMapping("create")
	public String create(HttpServletRequest request, Shipment shipment) {
		shipmentService.insertShipment(shipment);
		
		
		FlashMapUtils.setMessage("등록되었습니다.");
		
		if (request.getHeader("referer").indexOf("popup") > -1) {
			if (shipment.getSellerId() == SellerUtils.DEFAULT_OPMANAGER_SELLER_ID) {
				return "redirect:/opmanager/shipment/list-popup";
			} 
			return "redirect:/opmanager/shipment/list-popup/" + shipment.getSellerId();
			
		}
		
		return "redirect:/opmanager/shipment/list";
	}
	
	@PostMapping("edit")
	public String edit(HttpServletRequest request, Shipment shipment) {
		shipmentService.updateShipment(shipment);
		itemService.updateShipmentPrice(shipment);
		
		FlashMapUtils.setMessage("수정되었습니다.");
		if (request.getHeader("referer").indexOf("popup") > -1) {
			if (shipment.getSellerId() == SellerUtils.DEFAULT_OPMANAGER_SELLER_ID) {
				return "redirect:/opmanager/shipment/list-popup";
			} 
			return "redirect:/opmanager/shipment/list-popup/" + shipment.getSellerId();
		}
		
		return "redirect:/opmanager/shipment/list";
	}
	
	@PostMapping("delete")
	public String delete(HttpServletRequest request, Shipment shipment) {
		shipmentService.deleteShipmentById(shipment.getShipmentId());
		
		Shipment defaultShipment = shipmentService.getDefaultShipment(shipment);
		
		if (defaultShipment != null) {
			defaultShipment.setDeleteShipmentId(shipment.getShipmentId());
			itemService.updateShipment(defaultShipment);
		}
		
		FlashMapUtils.setMessage("삭제되었습니다.");
		if (request.getHeader("referer").indexOf("popup") > -1) {
			if (shipment.getSellerId() == SellerUtils.DEFAULT_OPMANAGER_SELLER_ID) {
				return "redirect:/opmanager/shipment/list-popup";
			} 
			return "redirect:/opmanager/shipment/list-popup/" + shipment.getSellerId();
		}
		
		return "redirect:/opmanager/shipment/list";
	}
}
