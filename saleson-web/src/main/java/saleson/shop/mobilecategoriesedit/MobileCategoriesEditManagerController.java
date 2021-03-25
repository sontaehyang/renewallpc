package saleson.shop.mobilecategoriesedit;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categoriesedit.domain.CategoriesEdit;
import saleson.shop.categoriesedit.support.CategoriesEditParam;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/opmanager/mobile-category-edit")
@RequestProperty(title="메인 화면관리", layout="default")
public class MobileCategoriesEditManagerController {
	
	@Autowired
	MobileCategoriesEditService mobileCategoriesEditService; 
	
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
	@GetMapping("/index")
	public String list(Model model, CategoriesEditParam categoriesEditParam) {
		
		String viewUrl = "";
		List<CategoriesTeam> categoryTeamGroupList = categoriesTeamGroupService.getCategoriesTeamGroupList();
		if ("".equals(categoriesEditParam.getType()) || "main".equals(categoriesEditParam.getType())) {
			
			if( categoriesEditParam.getCode() == null ){
				categoriesEditParam.setCode("main");
				categoriesEditParam.setEditKind("1");
			}

			if ("2".equals(categoriesEditParam.getEditKind())) {
				
				
				
				CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam = new CategoriesTeamGroupSearchParam();
				categoriesTeamGroupSearchParam.setCode(categoriesEditParam.getCode());
				categoriesTeamGroupSearchParam.setCategoryTeamId(""+categoriesTeamGroupService.getCategoriesTeamById(categoriesTeamGroupSearchParam).getCategoryTeamId());
				
				model.addAttribute("categoriesTeamCount", categoriesTeamGroupService.getCategoryTeamItemListByParam(categoriesTeamGroupSearchParam).size());
					
			}
			
			model.addAttribute("categoryEditPosition",mobileCategoriesEditService.getCategoryOnPosition(categoriesEditParam));
			model.addAttribute("categoryTeamGroupList",categoryTeamGroupList);
			model.addAttribute("categoriesEditParam",categoriesEditParam);
			
			viewUrl = "/mobile-category-edit/index";
		} else if("categories".equals(categoriesEditParam.getType())){
			
			model.addAttribute("categoryTeamGroupList", categoryTeamGroupList);
			model.addAttribute("categoryTeamList", getCategoryTeamList(categoryTeamGroupList));		// 카테고리 팀 목록 (무소속 포함)
			viewUrl = "/mobile-category-edit/index2";
		} else if("etc".equals(categoriesEditParam.getType())){
			
			if( categoriesEditParam.getCode() == null ){
				categoriesEditParam.setCode("user");
				categoriesEditParam.setEditKind("1");
			}
			
			model.addAttribute("categoriesEditParam",categoriesEditParam);
			model.addAttribute("categoryEditPosition",mobileCategoriesEditService.getCategoryOnPosition(categoriesEditParam));
			
			viewUrl = "/mobile-category-edit/index3";
			
		} else{
		  throw new UserException("잘못된 접근입니다.");
		}
		
		return ViewUtils.getManagerView(viewUrl);
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/category-layout/{categoryId}")
	@RequestProperty(title="메인 화면관리", layout="blank")
	public String categoryLayout(Model model, Categories categories,
			CategoriesEditParam categoriesEditParam) {
		Categories categories2 =  categoriesService.getCategoriesById(categories.getCategoryId());
		
		categoriesEditParam.setCode(categories2.getCategoryUrl());
		
		model.addAttribute("categoryEditPosition",mobileCategoriesEditService.getCategoryOnPosition(categoriesEditParam));
		model.addAttribute("categories",categories2);
		
		return ViewUtils.getManagerView("/mobile-category-edit/category-layout");
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/action")
	@RequestProperty(title="메인 화면관리", layout="base")
	public String action(Model model) {
		
		return ViewUtils.getManagerView("/mobile-category-edit/action");
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/create")
	public String createAction(Model model, CategoriesEdit categoriesEdit) {
		
		mobileCategoriesEditService.insertCategoryEdit(categoriesEdit);
		
		return ViewUtils.redirect("/opmanager/mobile-category-edit/action", MessageUtils.getMessage("M00288"), "opener.categoryLayout(); self.close();");
		
	}
	
	@PostMapping("/update")
	public String updateAction(Model model, CategoriesEdit categoriesEdit) {
		
		mobileCategoriesEditService.updateCategoryEdit(categoriesEdit);
		
		return ViewUtils.redirect("/opmanager/mobile-category-edit/action", MessageUtils.getMessage("M00289"), "opener.categoryLayout(); self.close();");
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/popup/html-edit")
	@RequestProperty(title="메인 화면관리", layout="base")
	public String bannerPopup(Model model, CategoriesEditParam categoriesEditParam) {
		
		/*CategoriesEdit categoriesEdit = mobileCategoriesEditService.getCategoryByParam(categoriesEditParam);
		
		model.addAttribute("categoriesEditParam", categoriesEditParam);
		if(categoriesEdit == null){
			model.addAttribute("mode", "create");
		}else {
			model.addAttribute("mode", "update");
		}*/
		
		categoriesEditParam.setEditPosition("html");
		
		model.addAttribute("categoriesEditParam", categoriesEditParam);
		model.addAttribute("categoryPromotionList",mobileCategoriesEditService.getCategoryPromotionListByParam(categoriesEditParam));
		
		return ViewUtils.getManagerView("/mobile-category-edit/popup/html-edit");
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/popup/promotion-banner")
	@RequestProperty(title="메인 화면관리", layout="base")
	public String promotionBannerPopup(Model model, CategoriesEditParam categoriesEditParam) {
		
		categoriesEditParam.setEditPosition("promotion");
		
		model.addAttribute("categoriesEditParam", categoriesEditParam);
		model.addAttribute("categoryPromotionList",mobileCategoriesEditService.getCategoryPromotionListByParam(categoriesEditParam));
		return ViewUtils.getManagerView("/mobile-category-edit/popup/promotion-banner");
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/promotion-banner/create")
	public String promotionCreateAction(Model model, CategoriesEdit categoriesEdit) {
		
		mobileCategoriesEditService.insertCategoryEditFiles(categoriesEdit);
		
		return ViewUtils.redirect("/opmanager/mobile-category-edit/popup/promotion-banner", MessageUtils.getMessage("M00288"), "self.close();");
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/file-delete")
	public JsonView fileDelete(Model model, CategoriesEdit categoriesEdit) {
		
		mobileCategoriesEditService.updateCategoryEditFile(categoriesEdit);
		
		return JsonViewUtils.success();
		
	}
	
	
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/popup/header")
	@RequestProperty(title="메인 화면관리", layout="base")
	public String headerPopup(Model model, Categories categories) {
		
		model.addAttribute("categories",categoriesService.getCategoriesById(categories.getCategoryId()));
		
		return ViewUtils.getManagerView("/mobile-category-edit/popup/header");
		
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/popup/footer")
	@RequestProperty(title="메인 화면관리", layout="base")
	public String footerPopup(Model model, Categories categories) {
		
		model.addAttribute("categories",categoriesService.getCategoriesById(categories.getCategoryId()));
		
		return ViewUtils.view();
		
	}
	
	
	@PostMapping("/category-create")
	public String categroyCreateAction(Model model, Categories categories) {
		
		String headerContent = categories.getCategoryHeader();
		
		categories = categoriesService.getCategoriesById(categories.getCategoryId());
		categories.setCategoryMobileHtml(headerContent);
		
		categoriesService.updateCategoryEdit(categories);
		
		return ViewUtils.redirect("/opmanager/mobile-category-edit/popup/header", MessageUtils.getMessage("M00289"), " opener.categoryLayout(); self.close();");
		
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
