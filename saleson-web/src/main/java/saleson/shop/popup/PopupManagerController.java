package saleson.shop.popup;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.utils.ShopUtils;
import saleson.shop.item.ItemManagerController;
import saleson.shop.popup.domain.Popup;
import saleson.shop.popup.domain.PopupSearchParam;

import java.util.List;

@Controller
@RequestMapping("/opmanager/popup")
@RequestProperty(layout="default")
public class PopupManagerController {
	private static final Logger log = LoggerFactory.getLogger(ItemManagerController.class);
	
	@Autowired
	PopupService popupService;
	
	
	/**
	 * 팝업 목록
	 * @param popupSearchParam
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	@RequestProperty(title="팝업 목록")
	public String popupList(PopupSearchParam popupSearchParam, Model model) {
		
		int popupCount = popupService.popupCount(popupSearchParam);
		
		Pagination pagination = Pagination.getInstance(popupCount);
		
		popupSearchParam.setPagination(pagination);
		
		List<Popup> popupList = popupService.popupList(popupSearchParam);
		
		for (Popup popup : popupList) {
			
			switch (Integer.parseInt(popup.getPopupClose())) {
				case 1 : popup.setPopupClose(MessageUtils.getMessage("M00083")); // 사용 
				break;
				case 2 : popup.setPopupClose(MessageUtils.getMessage("M00732")); // 일시 정지
				break;
				default : popup.setPopupClose(MessageUtils.getMessage("M00733"));	// 종료
				break;
			}
			
			switch (Integer.parseInt(popup.getPopupStyle())) {
				case 1 : popup.setPopupStyle(MessageUtils.getMessage("M00736"));	// 텍스트입력 
				break;
				case 2 : popup.setPopupStyle(MessageUtils.getMessage("M00736")+ "-" + MessageUtils.getMessage("M00737"));	// 텍스트입력-테두리없음
				break;
				default : popup.setPopupStyle(MessageUtils.getMessage("M00738"));	// 이미지등록 
				break;
			}
			
			popup.setPopupType(popup.getPopupType().equals("1") ? MessageUtils.getMessage("M00747") : MessageUtils.getMessage("M00748")); // 윈도우 / 레이어
		}
		
		model.addAttribute("popupCount", popupCount);
		model.addAttribute("popupList", popupList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("popupSearchParam", popupSearchParam);
		
		return ViewUtils.view();
	}
	
	
	/**
	 * 팝업 등록
	 * @param popup
	 * @param model
	 * @return
	 */
	@GetMapping("write")
	@RequestProperty(title="팝업 등록")
	public String popupWrite(Popup popup, Model model) {
		
		model.addAttribute("popup", popup);
		model.addAttribute("hours", ShopUtils.getHours());
		
		return ViewUtils.getView("/popup/form");
	}
	
	
	/**
	 * 팝업 등록처리
	 * @param popup
	 * @param model
	 * @param imageFile
	 * @return
	 */
	@PostMapping("write")
	public String popupWriteAction(Popup popup, Model model, @RequestParam(value="imageFile", required=false) MultipartFile imageFile) {
		
		popup.setPopupImageFile(imageFile);
		popupService.insertPopup(popup); 
		
		return ViewUtils.redirect("/opmanager/popup/list", MessageUtils.getMessage("M00288"));	// 등록되었습니다. 
	}
	
	
	/**
	 * 팝업 수정
	 * @param popupId
	 * @param model
	 * @return
	 */
	@GetMapping("edit/{popupId}")
	@RequestProperty(title="팝업 수정")
	public String updatePopup(@PathVariable("popupId") int popupId, Model model) {
		
		Popup popup = popupService.getPopup(popupId);
		
		model.addAttribute("popup", popup);
		model.addAttribute("hours", ShopUtils.getHours());
		
		return ViewUtils.getView("/popup/form");
	}
	
	
	/**
	 * 팝업 수정처리
	 * @param popupId
	 * @param popup
	 * @param model
	 * @param imageFile
	 * @return
	 */
	@PostMapping("edit/{popupId}")
	public String updatePopupAction(@PathVariable("popupId") int popupId, Popup popup, Model model, @RequestParam(value="imageFile", required=false) MultipartFile imageFile) {
		
		popup.setPopupId(popupId);
		popup.setPopupImageFile(imageFile);
		popupService.updatePopup(popup);
		
		return ViewUtils.redirect("/opmanager/popup/list", MessageUtils.getMessage("M00289"));	// 수정되었습니다. 
	}
	
	
	/**
	 * 팝업리스트 삭제
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping("delete-list")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		popupService.deletePopupData(listParam);
		return JsonViewUtils.success();  
	}
	
	
	/**
	 * 팝업 삭제
	 * @param popupId
	 * @return
	 */
	@GetMapping("delete/{popupId}")
	public String deletePopup(@PathVariable("popupId") int popupId) {
		
		popupService.deletePopup(popupId);
		
		return ViewUtils.redirect("/opmanager/popup/list", MessageUtils.getMessage("M00205")); // 삭제되었습니다. 
	}
	
	
	/**
	 * 상품 대표이미지를 삭제한다.
	 * @param requestContext
	 * @param popupId
	 * @return
	 */
	@PostMapping("delete-item-image")
	public JsonView deletePopupImage(RequestContext requestContext, @RequestParam("popupId") int popupId) {
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		popupService.deletePopupImage(popupId);
		
		return JsonViewUtils.success();
	}
}
