package saleson.shop.userdelivery;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.UserUtils;
import saleson.shop.userdelivery.domain.UserDelivery;
import saleson.shop.userdelivery.support.UserDeliveryParam;
import saleson.shop.zipcode.ZipcodeService;

@Controller
@RequestMapping("/delivery")
public class UserDeliveryController {
	
	@Autowired
	private UserDeliveryService userDeliveryService;
	
	@Autowired
	private ZipcodeService zipcodeService;
	
	/**
	 * 배송지 목록
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	@RequestProperty(layout="base")
	public String delivery(Model model, 
			@RequestParam(value="target", required=false) String target,
			@RequestParam(value="receiverIndex", required=false, defaultValue="0") int receiverIndex) {

		model.addAttribute("target", target);
		model.addAttribute("receiverIndex", receiverIndex);
		model.addAttribute("list", userDeliveryService.getUserDeliveryList(UserUtils.getUserId()));
		return ViewUtils.getView("/user-delivery/list");
		
	}
	
	/**
	 * 배송지 등록 폼
	 * @param model
	 * @return
	 */
	@GetMapping(value="/write")
	@RequestProperty(layout="base")
	public String write(Model model, @RequestParam(value="target", required=false) String target,
			@RequestParam(value="receiverIndex", required=false, defaultValue="0") int receiverIndex) {
		
		String t = "/delivery/write";
		if (StringUtils.isNotEmpty(target)) {
			t += "?target=" + target;
			t += "&receiverIndex=" + receiverIndex;
		}
		
		model.addAttribute("target", target);
		model.addAttribute("receiverIndex", receiverIndex);
		model.addAttribute("action", t);
		model.addAttribute("userDelivery", new UserDelivery());
		model.addAttribute("dodobuhyunList", zipcodeService.getDodobuhyunList());
		return ViewUtils.getView("/user-delivery/form");
		
	}
	
	/**
	 * 배송지 등록 처리
	 * @param model
	 * @param userDelivery
	 * @return
	 */
	@PostMapping("/write")
	public String writeAction(Model model, UserDelivery userDelivery,
			@RequestParam(value="target", required=false) String target,
			@RequestParam(value="receiverIndex", required=false, defaultValue="0") int receiverIndex) {
		
		userDelivery.setUserId(UserUtils.getUserId());
		userDeliveryService.insertUserDelivery(userDelivery);
		
		String t = "/delivery/list";
		if (StringUtils.isNotEmpty(target)) {
			if ("mypage".equals(target)) {
				return ViewUtils.redirect("/delivery/write", 
						MessageUtils.getMessage("M00288"), 
						"opener.location.reload(); self.close();");
			} else if ("order".equals(target)) {
				t = "/order/delivery";
			}
			
			t += "?target=" + target;
			t += "&receiverIndex=" + receiverIndex;
		}
		
		return ViewUtils.redirect(t, MessageUtils.getMessage("M00288")); // 등록 되었습니다.
		
	}
	
	/**
	 * 배송지 수정 폼
	 * @param userDeliveryId
	 * @param model
	 * @return
	 */
	@GetMapping(value="/edit/{id}")
	@RequestProperty(layout="base")
	public String edit(@PathVariable("id") int userDeliveryId, Model model,
			@RequestParam(value="target", required=false) String target,
			@RequestParam(value="receiverIndex", required=false, defaultValue="0") int receiverIndex) {

		String t = "/delivery/edit";
		if (StringUtils.isNotEmpty(target)) {
			t += "?target=" + target;
			t += "&receiverIndex=" + receiverIndex;
		}
		
		model.addAttribute("action", t);
		model.addAttribute("userDelivery", userDeliveryService.getUserDeliveryById(UserUtils.getUserId(), userDeliveryId));
		model.addAttribute("dodobuhyunList", zipcodeService.getDodobuhyunList());
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
			@RequestParam(value="target", required=false) String target,
			@RequestParam(value="receiverIndex", required=false, defaultValue="0") int receiverIndex) {
		
		userDelivery.setUserId(UserUtils.getUserId());
		userDeliveryService.updateUserDelivery(userDelivery);
		String t = "/delivery/list";
		if (StringUtils.isNotEmpty(target)) {
			if ("mypage".equals(target)) {
				return ViewUtils.redirect("/delivery/edit/" + userDeliveryId, 
						MessageUtils.getMessage("M00289"), 
						"opener.location.reload(); self.close();");
			} else if ("order".equals(target)) {
				t = "/order/delivery";
			}
			
			t += "?target=" + target;
			t += "&receiverIndex=" + receiverIndex;
		}
		
		return ViewUtils.redirect(t, MessageUtils.getMessage("M00289")); // 수정 되었습니다.
	}
	
	/**
	 * 삭제, 기본 배송지 설정등
	 * @param userDeliveryParam
	 * @param mode
	 * @param target
	 * @return
	 */
	@PostMapping("/list-action/{mode}")
	public String listAction(UserDeliveryParam userDeliveryParam, @PathVariable("mode") String mode,
			@RequestParam(value="target", required=false) String target,
			@RequestParam(value="receiverIndex", required=false, defaultValue="0") int receiverIndex) {
		
		userDeliveryParam.setUserId(UserUtils.getUserId());
		userDeliveryParam.setMode(mode);
		userDeliveryService.listAction(userDeliveryParam);
		
		String t = "/delivery/list";
		if (StringUtils.isNotEmpty(target)) {
			if (target.startsWith("/mypage")) {
				t = target;
			} else if ("order".equals(target)) {
				t = "/order/delivery";
				t += "?target=order&receiverIndex=" + receiverIndex;
			} else {
				t += "?target=" + target;
			}
		}
		
		return ViewUtils.redirect(t);
	}
	
	
}
