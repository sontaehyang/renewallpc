package saleson.shop.categoriesteamgroup;

import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.support.CategoriesSearchParam;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.categoriesteamgroup.domain.CategoryTeamItem;
import saleson.shop.categoriesteamgroup.domain.TeamGroupListParam;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupUtils;

@Controller
@RequestMapping("/opmanager/categories-team-group")
@RequestProperty(title="캘린더 관리", layout="default")
public class CategoriesTeamGroupManagerController {
	
	@Autowired
	CategoriesTeamGroupService categoriesTeamGroupService; 
	
	@Autowired
	CategoriesService categoriesService; 
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model, CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam) {
		if ("team".equals(categoriesTeamGroupSearchParam.getCategoryType())) {
			model.addAttribute("categoriesTeamGroupList",categoriesTeamGroupService.getCategoriesTeamList());
		} else if("group".equals(categoriesTeamGroupSearchParam.getCategoryType())) {
			model.addAttribute("categoriesTeamGroupList",categoriesTeamGroupService.getCategoriesGroupList());
		} else {
			model.addAttribute("categoriesTeamGroupList",categoriesTeamGroupService.getCategoriesTeamGroupList());
		}
		
		model.addAttribute("searchParam",categoriesTeamGroupSearchParam);
		
		return ViewUtils.view();
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 순서 변경
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/ordering-edit/{type}")
	public JsonView orderingEdit(Model model, CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam
			, CategoriesTeam categoriesTeam, CategoriesGroup categoriesGroup) {
		
		/*if (!boardContext.getBoardAuthority().isCommentAuthority()) {
			return JsonUtils.exception(MessageUtils.getMessage("board.error.authority.comment"));
		}*/

		if ("team".equals(categoriesTeamGroupSearchParam.getType())){
			categoriesTeamGroupService.updateCategoriesTeamOrdering(categoriesTeam);
		} else {
			categoriesTeamGroupService.updateCategoriesGroupOrdering(categoriesGroup);
		}
		
		
		return JsonViewUtils.success();
		
	}
	
	
	/**
	 * 관리자 팀별 그룹 관리 순서 변경
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/change-ordering/team")
	public JsonView changeTeamOrdering(TeamGroupListParam listParam) {
		categoriesTeamGroupService.updateCategoriesTeamOrdering(listParam);
		
		
		return JsonViewUtils.success();
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 순서 변경
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/change-ordering/group")
	public JsonView changeGroupOrdering(TeamGroupListParam listParam) {
		categoriesTeamGroupService.updateCategoriesGroupOrdering(listParam);
		return JsonViewUtils.success();
		
	}
	
	
	/**
	 * 관리자 팀별 그룹 관리 팀별 등록
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/team/create")
	public String createTeam(Model model, CategoriesTeam categoriesTeam) {
		
		model.addAttribute("categoriesTeam", categoriesTeam);
		
		return ViewUtils.view();
		
	}
	
	@PostMapping("/team/create")
	public String createTeamAction(Model model, CategoriesTeam categoriesTeam, CategoryTeamItem categoryTeamItem) {
		
		categoriesTeamGroupService.insertCategoriesTeam(categoriesTeam);
		
		return ViewUtils.redirect("/opmanager/categories-team-group/list", MessageUtils.getMessage("M00288"));	// 등록 되었습니다. 
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 팀별 수정
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/team/edit/{categoryTeamId}")
	public String updateTeam(Model model, CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam) {
		
		model.addAttribute("categoriesTeam", categoriesTeamGroupService.getCategoriesTeamById(categoriesTeamGroupSearchParam));
		model.addAttribute("itemList",categoriesTeamGroupService.getCategoryTeamItemListByParam(categoriesTeamGroupSearchParam));
		return ViewUtils.view();
		
	}
	
	@PostMapping("/team/edit/{categoryTeamId}")
	public String updateTeamAction(Model model, CategoriesTeam categoriesTeam, CategoryTeamItem categoryTeamItem) {
		
		categoriesTeamGroupService.updateCategoriesTeamById(categoriesTeam);
		
		return ViewUtils.redirect("/opmanager/categories-team-group/list", MessageUtils.getMessage("M00289"));	// 수정 되었습니다. 
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 팀별 삭제
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/team/delete/{categoryTeamId}")
	public String deleteTeam(Model model, CategoriesTeam categoriesTeam, CategoriesGroup categoriesGroup) {
		
		categoriesTeamGroupService.deleteCategoryGorup(categoriesGroup);
		categoriesTeamGroupService.deleteCategoryTeam(categoriesTeam);
		
		return ViewUtils.redirect("/opmanager/categories-team-group/list",  MessageUtils.getMessage("M00205"));	// 삭제 되었습니다.
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 그룹 등록
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/group/create")
	public String createGroup(Model model, CategoriesGroup categoriesGroup) {
		
		model.addAttribute("categoriesTeamSelectBox",CategoriesTeamGroupUtils.getCategoriesTeamListToHaspMap(categoriesTeamGroupService.getCategoriesTeamList()));
		model.addAttribute("categoriesGroupInList",categoriesService.getGroupsInCategoriesList());
		model.addAttribute("categoriesGroup",categoriesGroup);
		
		return ViewUtils.view();
		
	}
	
	@PostMapping("/group/create")
	public String createGroupAction(Model model, CategoriesGroup categoriesGroup, CategoriesSearchParam categoriesSearchParam) {
		
		
		String categorygroupId = Integer.toString(categoriesTeamGroupService.insertCategoriesGroup(categoriesGroup));
		categoriesSearchParam.setCategoryGroupId(categorygroupId);
		categoriesService.updateCategoriesByParam(categoriesSearchParam);
		
		return ViewUtils.redirect("/opmanager/categories-team-group/list", MessageUtils.getMessage("M00288"));	// 등록 되었습니다. 
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 그룹 수정
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/group/edit/{categoryGroupId}")
	public String updateGroup(Model model, CategoriesGroup categoriesGroup, 
				CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam,
				CategoriesSearchParam categoriesSearchParam) {
		
		model.addAttribute("categoriesTeamSelectBox",CategoriesTeamGroupUtils.getCategoriesTeamListToHaspMap(categoriesTeamGroupService.getCategoriesTeamList()));
		model.addAttribute("categoriesGroupInList",categoriesService.getGroupsInCategoriesList());
		model.addAttribute("categoriesList",categoriesService.getCategoriesListById(categoriesSearchParam));
		model.addAttribute("categoriesGroup",categoriesTeamGroupService.getCategoriesGroupById(categoriesTeamGroupSearchParam));
		
		return ViewUtils.view();
		
	}
	
	@PostMapping("/group/edit/{categoryGroupId}")
	public String updateGroupAction(Model model, CategoriesGroup categoriesGroup, 
				CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam,
				CategoriesSearchParam categoriesSearchParam) {
		
		categoriesSearchParam.setCategoryGroupId("0");
		categoriesService.updateCategoriesByParam(categoriesSearchParam);
		categoriesSearchParam.setCategoryGroupId(Integer.toString(categoriesGroup.getCategoryGroupId()));
		categoriesService.updateCategoriesByParam(categoriesSearchParam);
		
		categoriesTeamGroupService.updateCategoriesGroupById(categoriesGroup);
		
		return ViewUtils.redirect("/opmanager/categories-team-group/list", MessageUtils.getMessage("M00289"));	// 수정 되었습니다. 
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 그룹 삭제
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/group/delete/{categoryGroupId}")
	public String deleteGroup(Model model, CategoriesGroup categoriesGroup) {
		
		categoriesTeamGroupService.deleteCategoryGorup(categoriesGroup);
		
		return ViewUtils.redirect("/opmanager/categories-team-group/list", MessageUtils.getMessage("M00205"));	// 삭제 되었습니다. 
		
	}
	
	/**
	 * 카테고리 그룹삭제.
	 * @param categoriesGroup
	 * @return
	 */
	@PostMapping("/group/delete")
	public JsonView deleteCategoryGroup(CategoriesGroup categoriesGroup) {
		
		categoriesTeamGroupService.deleteCategoryGorup(categoriesGroup);
		
		return JsonViewUtils.success();
		
	}
	
	
	/**
	 * Group --> 1차.
	 * @param categoryGroupId
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("/group/change-group-to-category/{categoryGroupId}")
	public String changeGroupToCategory(@PathVariable("categoryGroupId") int categoryGroupId, Model model) {
		// 하위 카테고리 max level
		int maxSubCategoryLevel = categoriesTeamGroupService.getMaxCategoryLevelByGroupId(categoryGroupId);
		
		model.addAttribute("maxSubCategoryLevel", maxSubCategoryLevel);
		model.addAttribute("categoryGroupId", categoryGroupId);
		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		
		return ViewUtils.view();
	}
	
	
	@RequestProperty(layout="base")
	@PostMapping("/group/change-group-to-category")
	public String processToChangeGroupToCategory(CategoriesTeamGroupSearchParam categoriesTeamGroupParam,
			Model model) {
		
		categoriesTeamGroupService.updateGroupToCategory(categoriesTeamGroupParam);
		
		model.addAttribute("maxSubCategoryLevel", -1);
		model.addAttribute("categoriesTeamGroupParam", categoriesTeamGroupParam);
		return ViewUtils.view();
	}
	
}
