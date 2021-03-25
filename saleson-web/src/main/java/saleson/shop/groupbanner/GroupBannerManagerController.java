package saleson.shop.groupbanner;

import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;
import saleson.shop.groupbanner.domain.GroupBanner;
import saleson.shop.groupbanner.support.GroupBannerManagerException;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/opmanager/group-banner")
@RequestProperty(title="그룹별 베너 관리", layout="default")
public class GroupBannerManagerController {
	private static final Logger log = LoggerFactory.getLogger(GroupBannerManagerController.class);


	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;
	
	@Autowired
	private GroupBannerService groupBannerService;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 목록
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String list(Model model) {
		
		model.addAttribute("categoriesTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		return ViewUtils.getView("/group-banner/list");
		
	}
	
	/**
	 * 등록 OR 수정
	 * @param model
	 * @param categoryGroupId
	 * @return
	 */
	@GetMapping("form/{categoryGroupId}")
	public String form(Model model, @PathVariable("categoryGroupId") int categoryGroupId) {
		
		CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam = new CategoriesTeamGroupSearchParam(); 
		
		categoriesTeamGroupSearchParam.setCategoryGroupId(String.valueOf(categoryGroupId));
		
		CategoriesGroup categoryGroup = categoriesTeamGroupService.getCategoriesGroupById(categoriesTeamGroupSearchParam);
		
		/* 파라미터로 아이템 받아오기 2016-11-08_LSI */
		List<Item> itemList = new ArrayList<>();
		
		if (categoryGroup.getItemList() != null) {
			itemList = itemService.getItemListForGroupBanner(categoryGroup.getItemList());
		}
		
		model.addAttribute("groupBanner", new GroupBanner());
		model.addAttribute("categoryGroupId", categoryGroupId);
		model.addAttribute("list2", itemList);
		
		model.addAttribute("list", groupBannerService.getGroupBannerByCategoryGroupId(categoryGroupId));
		model.addAttribute("categoryGroup", categoryGroup);
		
		return ViewUtils.getView("/group-banner/form");
		
	}
	
	/**
	 * 등록 OR 수정 처리
	 * @param model
	 * @param categoryGroupId
	 * @param groupBanner
	 * @return
	 */
	@PostMapping("form/{categoryGroupId}")
	public String formAction(Model model, @PathVariable("categoryGroupId") int categoryGroupId,
			@RequestParam(value = "itemList",  required = false ) String itemList,
			GroupBanner groupBanner) {
		
		groupBannerService.editGroupBanner(groupBanner);
		CategoriesGroup categoryGroup = new CategoriesGroup();
		categoryGroup.setCategoryGroupId(categoryGroupId);
		categoryGroup.setItemList(itemList);
		categoriesTeamGroupService.updateCategoriesGroupItemListById(categoryGroup);
		
		return ViewUtils.redirect("/opmanager/group-banner/form/" + categoryGroupId);
	}
	
	/**
	 * 베너 등록 에러 핸들
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(GroupBannerManagerException.class)
	public String handleGroupBannerManagerException(GroupBannerManagerException ex, HttpServletRequest request) {

		log.error("ERROR: {}", ex.getMessage(), ex);
		
		return ViewUtils.redirect(ex.getRedirectUrl(), ex.getMessage());
	}
}
