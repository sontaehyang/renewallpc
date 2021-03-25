package saleson.shop.categories;

import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.model.FilterGroup;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categories.support.CategoriesSearchParam;
import saleson.shop.categoriesfilter.CategoriesFilterService;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.reviewfilter.ReviewFilterService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/opmanager/categories")
@RequestProperty(title="카테고리 관리", layout="default")
public class CategoriesManagerController {
	
	@Autowired
	CategoriesService categoriesService; 
	
	@Autowired
	CategoriesTeamGroupService categoriesTeamGroupService;

	@Autowired
	CategoriesFilterService categoriesFilterService;

	@Autowired
	ReviewFilterService reviewFilterService;

	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param categoriesSearchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model, CategoriesSearchParam categoriesSearchParam) {
		
		Categories categories = new Categories();
		
		List<CategoriesTeam> categoryTeamGroupList = categoriesTeamGroupService.getCategoriesTeamGroupList();
		
		model.addAttribute("categoryTeamGroupList",categoryTeamGroupList);
		
		model.addAttribute("searchParam",categoriesSearchParam);
		model.addAttribute("categories",categories);
		
		return ViewUtils.view();
		
	}
	
	@GetMapping("/create")
	@RequestProperty(layout="blank")
	public String create(Model model) {
		
		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		
		return ViewUtils.getManagerView("/categories/create");
		
	}
	
	@PostMapping("/create")
	public String createAction(Model model, Categories categories) {
		
		categoriesService.insertCategory(categories);
		
		String redirectUri = "/opmanager/categories/list";
		if (categories.getCategoryGroupId() > 0) {
			redirectUri = redirectUri + "?categoryGroupId=" + categories.getCategoryGroupId();
		}
		
		return ViewUtils.redirect(redirectUri, MessageUtils.getMessage("M00288"));	// 등록 되었습니다. 
		
	}
	
	
	@PostMapping("/low-create/{categoryCode}")
	public String lowCreateAction(Model model, Categories categories) {
		
		categoriesService.insertCategorySub(categories);
		
		return ViewUtils.redirect("/opmanager/categories/list", MessageUtils.getMessage("M00288"));	// 등록 되었습니다. 
		
	}
	
	
	@GetMapping("/edit/{categoryCode}")
	@RequestProperty(layout="blank")
	public String edit(Model model, Categories categories) {

		Categories searchCategories = categoriesService.getCategoryByCategoryCode(categories.getCategoryCode());

		List<FilterGroup> filterGroups = categoriesFilterService.getFilterGroupList(searchCategories.getCategoryId());
		List<FilterGroup> reviewFilterGroups = reviewFilterService.getFilterGroupList(searchCategories.getCategoryId());

		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		model.addAttribute("categories",searchCategories);
		model.addAttribute("filterGroups",filterGroups);
		model.addAttribute("reviewFilterGroups",reviewFilterGroups);
		
		return ViewUtils.getManagerView("/categories/edit");
		
	}
	
	@PostMapping("/edit")
	public String editAction(@RequestParam(value="cateGroupId") String cateGroupId, Categories categories, Model model) {
		
		categoriesService.updateCategory(categories);
		
		return ViewUtils.redirect("/opmanager/categories/list?categoryGroupId=" + cateGroupId, MessageUtils.getMessage("M00289"));	// 수정 되었습니다.  
		
	}
	
	@PostMapping("/change")
	public JsonView changeAction(Model model, CategoriesSearchParam categoriesSearchParam) {
		try{
			
			categoriesService.updateChangeCategory(categoriesSearchParam);
		
		} catch(Exception e){
			
			return JsonViewUtils.failure(e.getMessage());
			
		}
		return JsonViewUtils.success();
		
	}
	
	
	/**
	 * 카테고리를 이동한다.
	 * @param model
	 * @param categoriesSearchParam
	 * @return
	 */
	@PostMapping("/move")
	public JsonView move(Model model, CategoriesSearchParam categoriesSearchParam) {
		try{
			categoriesService.updateCategoryPosition(categoriesSearchParam);
		} catch(Exception e){
			return JsonViewUtils.failure(e.getMessage());
		}
		return JsonViewUtils.success();
	}
	
	
	
	
	/**
	 * JS Tree에서 1차 카테고리 순서를 변경한다.
	 * @param model
	 * @param categoriesSearchParam
	 * @return
	 */
	@PostMapping("/change-ordering-category1")
	public JsonView changeOrderingCategory1(Model model, CategoriesSearchParam categoriesSearchParam) {
		try{
			categoriesService.updateChangeOrderingCategory1InGroup(categoriesSearchParam);
		} catch(Exception e){
			return JsonViewUtils.failure(e.getMessage());
		}
		return JsonViewUtils.success();
	}
	
	
	
	
	@ResponseBody
	@GetMapping("/edit-seo/{categoryCode}")
	public Categories editSeo(Categories categories) {
		
		return categoriesService.getCategoryByCategoryCode(categories.getCategoryCode());
		
	}
	
	@ResponseBody
	@GetMapping("/tree-list")
	public List<HashMap<String,Object>> treeList(CategoriesSearchParam categoriesSearchParam){
		
		return categoriesService.getCategoiesTreeListByLevel1(categoriesSearchParam);
		
	}
	
	@ResponseBody
	@GetMapping("/tree-list/{categoryCode}")
	public List<HashMap<String,Object>> treeLists(CategoriesSearchParam categoriesSearchParam){
	
		return categoriesService.getCategoiesTreeListByLevels(categoriesSearchParam);
		
	}
	
	@PostMapping("/delete")
	public JsonView changeAction(Model model, Categories categories) {
		
		categoriesService.deleteCategoryByClass(categories);
		
		return JsonViewUtils.success();
		
	}
	
	@PostMapping("/code-check")
	public JsonView codeCheck(Model model, CategoriesSearchParam categoriesSearchParam) {
		
		int count = categoriesService.getCategoryCountByCode(categoriesSearchParam);
		
		if(count > 0){
			return JsonViewUtils.failure(MessageUtils.getMessage("M01486"));	// 코드 또는 URL 존재 합니다. 
		}
		
		return JsonViewUtils.success();
		
	}
	
	/**
	 * 상품 등록/수정 페이지의 소속팀 목록을 팀/그룹 리스트에서 가져온다.
	 * 에스테틱, 미용, 네일, 속눈썹 에크 스테, 세일/아울렛, 무소속
	 * @param categoryTeamGroupList
	 * @return
	 */
	private List<CategoriesTeam> getCategoryTeamList(List<CategoriesTeam> categoryTeamGroupList) {
		List<CategoriesTeam> categoryTeamList = new ArrayList<>();
		
		for (CategoriesTeam categoriesTeam : categoryTeamGroupList) {
			if (categoriesTeam.getCategoryTeamFlag().equals("Y")) {
				categoryTeamList.add(categoriesTeam);
			}
		}
		
		// 무소속
		CategoriesTeam noTeam = new CategoriesTeam();
		noTeam.setCode("-");
		noTeam.setName("無所属");
		categoryTeamList.add(noTeam);
		
		return categoryTeamList;
	}
	
}
