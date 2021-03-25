package saleson.shop.userdelivery;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.UserUtils;
import saleson.shop.userdelivery.domain.UserDelivery;
import saleson.shop.userdelivery.support.UserDeliveryParam;

@Controller
@RequestMapping("/m/delivery")
@RequestProperty(template="mobile", layout="default")
public class UserDeliveryMobileController {
	
	@Autowired
	private UserDeliveryService userDeliveryService;
	
	//LCH-UserDeliveryMobile  배송지 폼 <수정> 

	/**
	 * 배송지 등록 폼
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value="/write")
	public String write(Model model, @RequestParam(value="target", required=false) String target) {

		model.addAttribute("target", target);
		model.addAttribute("action", "/m/delivery/write");
		model.addAttribute("userDelivery", new UserDelivery());

		
		return ViewUtils.getView("/user-delivery/form");
		
	}
	
	//LCH-UserDeliveryMobile  배송지 등록 <수정> 

	/**
	 * 배송지 등록 처리
	 * @param model
	 * @param userDelivery
	 * @return
	 */
	@PostMapping("/write")
	public String writeAction(Model model, UserDelivery userDelivery,
			@RequestParam(value="target", required=false) String target) {
		
		userDelivery.setUserId(UserUtils.getUserId());
		userDeliveryService.insertUserDelivery(userDelivery);

		String url = "/m/mypage/delivery";
				
		if("order".equals(target)){
			url = "/m/order/delivery";
		}
		
		return ViewUtils.redirect(url, MessageUtils.getMessage("M00288")); // 등록 되었습니다.
		
	}
	
	
	//LCH-UserDeliveryMobile  배송지 폼 <수정> 


	/**
	 * 배송지 수정 폼
	 * @param userDeliveryId
	 * @param model
	 * @return
	 */
	
	@RequestProperty(layout="base")
	@GetMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") int userDeliveryId, Model model,
			@RequestParam(value="target", required=false) String target) {

		model.addAttribute("action", "/m/delivery/edit");
		model.addAttribute("userDelivery", userDeliveryService.getUserDeliveryById(UserUtils.getUserId(), userDeliveryId));
		
		return ViewUtils.getView("/user-delivery/form");
		
	}
	
	/**
	 * 배송지 수정 처리
	 * @param model
	 * @param userDelivery
	 * @return
	 */
	@PostMapping("/edit")
	public String editAction(UserDelivery userDelivery,
			@RequestParam(value="userDeliveryId", required=true) int userDeliveryId,
			@RequestParam(value="target", required=false) String target) {
		
		userDelivery.setUserId(UserUtils.getUserId());
		userDeliveryService.updateUserDelivery(userDelivery);
		
		return ViewUtils.redirect("/m/mypage/delivery", MessageUtils.getMessage("M00289")); // 수정 되었습니다.
	}


	//LCH-UserDeliveryMobile  삭제,기본 배송지 설정 <수정> 

	
	/**kye 수정
	 * 삭제, 기본 배송지 설정등
	 * @param userDeliveryParam
	 * @param mode
	 * @param target
	 * @return
	 */
	@GetMapping(value="/list-action/{mode}")
	public String listAction(UserDeliveryParam userDeliveryParam, @PathVariable("mode") String mode,
			@RequestParam(value="target", required=false) String target, 
			@RequestParam(value="userDeliveryId", required=false) int userDeliveryId) {
		
		userDeliveryParam.setUserId(UserUtils.getUserId());
		userDeliveryParam.setMode(mode);
		userDeliveryService.listAction(userDeliveryParam);
		
		String url = "/m/mypage/delivery";
		
		if("order".equals(target)){
			url = "/m/order/delivery";
		}
		
		return ViewUtils.redirect(url);
	}
}
