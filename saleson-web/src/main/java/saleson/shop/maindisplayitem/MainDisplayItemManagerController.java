package saleson.shop.maindisplayitem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;

import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;
import saleson.shop.item.ItemService;
import saleson.shop.maindisplayitem.domain.MainDisplayItem;
import saleson.shop.maindisplayitem.support.MainDisplayItemParam;

@Controller
@RequestMapping("/opmanager/main-display")
@RequestProperty(title="메인화면 관리", layout="default")
public class MainDisplayItemManagerController {

	@Autowired
	private MainDisplayItemService mainDisplayItemService;
	
	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;
	
	
	@Autowired
	private ItemService itemService;
	
	
	
	
	@GetMapping("{viewType}")
	public String form(Model model, @PathVariable("viewType") String viewType, 
			MainDisplayItemParam mainDisplayItemParam) {

		List<CategoriesTeam> categoryTeamList = categoriesTeamGroupService.getCategoriesTeamList();
		
		String templateId = viewType;
		String prefix = templateId + "_";

		if (!"md".equals(viewType)) {
		//	String prefix = "best_";
			if (StringUtils.isEmpty(mainDisplayItemParam.getCategoryTeamCode())) {
				templateId = prefix + categoryTeamList.get(0).getCode();
				mainDisplayItemParam.setCategoryTeamCode(categoryTeamList.get(0).getCode());
			} else {
				templateId = prefix + mainDisplayItemParam.getCategoryTeamCode();
			}
			
			String teamCode = mainDisplayItemParam.getCategoryTeamCode();
			CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam = new CategoriesTeamGroupSearchParam();
			categoriesTeamGroupSearchParam.setCode(teamCode);
			model.addAttribute("teamInfo", categoriesTeamGroupService.getCategoriesTeamById(categoriesTeamGroupSearchParam));
		}
		
		mainDisplayItemParam.setTemplateId(templateId);
		
		List<MainDisplayItem> list = mainDisplayItemService.getMainDisplayItemListByParam(mainDisplayItemParam);
		
		model.addAttribute("list", list);
		model.addAttribute("viewType", viewType);
		model.addAttribute("mainDisplayItemParam", mainDisplayItemParam);
		model.addAttribute("categoryTeamList", categoryTeamList);
		return ViewUtils.getView("/main-display/form");
		
	}
	
	@PostMapping("{viewType}")
	public String formAction(Model model, MainDisplayItemParam mainDisplayItemParam,
			@PathVariable("viewType") String viewType) {
		
		String redirectUrl = "/opmanager/main-display/" + viewType;
		
		if ("best".equals(viewType)) {
			
			if (StringUtils.isEmpty(mainDisplayItemParam.getCategoryTeamCode())) {
				throw new PageNotFoundException();
			}
			
			redirectUrl += "?categoryTeamCode=" + mainDisplayItemParam.getCategoryTeamCode();
			
			// BEST 노출 방식 설정 - OP_CATEGORY_TEAM
			categoriesTeamGroupService.updateBestItemDisplayType(mainDisplayItemParam.getCategoryTeamCode(), mainDisplayItemParam.getBestItemDisplayType());
		}
		
		// 등록일경우 최소 등록수를 검증함
		if ("1".equals(mainDisplayItemParam.getDisplayType())) {
			if (mainDisplayItemParam != null
				&& mainDisplayItemParam.getMainDisplayItemIds() != null
				&& mainDisplayItemParam.getMinItemSize() > 0
				&& mainDisplayItemParam.getMainDisplayItemIds().length < mainDisplayItemParam.getMinItemSize()) {
				return ViewUtils.redirect(redirectUrl, "최소 " + mainDisplayItemParam.getMinItemSize() + "개의 상품을 선택하셔야 합니다.");

			}
		}
		
		mainDisplayItemService.deleteMainDisplayItemByTemplateId(mainDisplayItemParam.getTemplateId());
		
		// 선택해서 등록인경우 선택된 상품을 등록함
		if ("1".equals(mainDisplayItemParam.getDisplayType())) {
			mainDisplayItemService.insertMainDisplayItemByParam(mainDisplayItemParam);
		}
		return ViewUtils.redirect(redirectUrl);
	}
	
}
