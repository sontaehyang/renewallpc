package saleson.shop.categoriesedit;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categoriesedit.domain.CategoriesEdit;
import saleson.shop.categoriesedit.support.CategoriesEditParam;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/opmanager/categories-edit")
@RequestProperty(title="메인 화면관리", layout="default")
public class CategoriesEditManagerController {
	private static final Logger log = LoggerFactory.getLogger(CategoriesEditManagerController.class);
	
	@Autowired
	CategoriesEditService categoriesEditService; 
	
	@Autowired
	CategoriesTeamGroupService categoriesTeamGroupService;
	
	@Autowired
	CategoriesService categoriesService; 
	
	@Autowired
	private ConfigService configService;
	
	/**
	 * TOP 공통배너 설정
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@GetMapping("top-banner/{position}")
	@RequestProperty(title="TOP 배너관리", layout="base")
	public String topBannerConfig(RequestContext requestContext, Model model, @PathVariable("position") String position){
		
		Config config = configService.getShopConfig(Config.SHOP_CONFIG_ID);
		
		model.addAttribute("config", config);
		model.addAttribute("position", position);
		
		return ViewUtils.getView("/categories-edit/top-banner");
	}
	
	/**
	 * TOP 공통배너 저장
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@PostMapping("top-banner/{position}")
	@RequestProperty(title="TOP 배너관리", layout="base")
	public String topBannerConfigAction(@RequestParam(value="imageFileTop", required=false) MultipartFile imageFileTop, 
										@RequestParam(value="imageFileRight", required=false) MultipartFile imageFileRight, Config config,
										@PathVariable("position") String position) {
			
		config.setTopBannerImageFile(imageFileTop);
		config.setRightBannerImageFile(imageFileRight);
		configService.updateShopConfigTopBanner(config);
		
		return ViewUtils.redirect("/opmanager/categories-edit/top-banner/" + position, MessageUtils.getMessage("M00288"), "self.close();");
	}
	
	/**
	 * TOP 공통배너 삭제
	 * @param config
	 * @return
	 */
	/*@GetMapping("top-banner/delete")
	public String topBannerConfigDelete(Config config) {
		
		configService.deleteShopConfigTopBanner(config);
		
		return ViewUtils.redirect("/opmanager/config/top-banner", MessageUtils.getMessage("M00205"));
	}*/
	
	/**
	 * TOP 공통배너 이미지삭제
	 * @param config
	 * @return
	 */
	@GetMapping("top-banner/imgDelete/{bannerType}/{position}")
	@RequestProperty(title="TOP 배너관리", layout="base")
	public String topBannerConfigImgDelete(Config config, @PathVariable("bannerType") String bannerType, 
											@PathVariable("position") String position) {
			
		configService.deleteShopConfigBannerImage(config, bannerType);

		return ViewUtils.redirect("/opmanager/categories-edit/top-banner/" + position, MessageUtils.getMessage("M00538"));
	}
	
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
			
			model.addAttribute("categoryEditPosition",categoriesEditService.getCategoryOnPosition(categoriesEditParam));
			model.addAttribute("categoryTeamGroupList",categoryTeamGroupList);
			model.addAttribute("categoriesEditParam",categoriesEditParam);
			
			viewUrl = "/categories-edit/index";
		} else if("categories".equals(categoriesEditParam.getType())){
			
			model.addAttribute("categoryTeamGroupList", categoryTeamGroupList);
			model.addAttribute("categoryTeamList", getCategoryTeamList(categoryTeamGroupList));		// 카테고리 팀 목록 (무소속 포함)
			viewUrl = "/categories-edit/index2";
		} else if("etc".equals(categoriesEditParam.getType())){
			
			if( categoriesEditParam.getCode() == null ){
				categoriesEditParam.setCode("user");
				categoriesEditParam.setEditKind("1");
			}
			
			model.addAttribute("categoriesEditParam",categoriesEditParam);
			model.addAttribute("categoryEditPosition",categoriesEditService.getCategoryOnPosition(categoriesEditParam));
			
			viewUrl = "/categories-edit/index3";
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
		
		model.addAttribute("categoryEditPosition",categoriesEditService.getCategoryOnPosition(categoriesEditParam));
		model.addAttribute("categories",categories2);
		
		return ViewUtils.view();
		
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
		
		return ViewUtils.view();
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/create")
	public String createAction(Model model, CategoriesEdit categoriesEdit) {
		
		categoriesEditService.insertCategoryEdit(categoriesEdit);

		// 등록 되었습니다. 		
		return ViewUtils.redirect("/opmanager/categories-edit/action", MessageUtils.getMessage("M00288"), "opener.categoryLayout(); self.close();");

		
	}
	
	@PostMapping("/update")
	public String updateAction(Model model, CategoriesEdit categoriesEdit) {
		
		categoriesEditService.updateCategoryEdit(categoriesEdit);

		// 수정 되었습니다. 
		return ViewUtils.redirect("/opmanager/categories-edit/action", MessageUtils.getMessage("M00289"), "opener.categoryLayout(); self.close();");
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/related")
	@RequestProperty(title="메인 화면관리")
	public String relatedAction(Model model, CategoriesTeam categoriesTeam) {
		
		categoriesEditService.insertRelated(categoriesTeam);
	
		// 등록 되었습니다. 	
		return ViewUtils.redirect("/opmanager/categories-edit/index", MessageUtils.getMessage("M00288"), "self.close();");
		
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/popup/related")
	@RequestProperty(title="메인 화면관리", layout="base")
	public String relatedPopup(Model model, CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam, CategoriesEditParam categoriesEditParam) {
		
		
		categoriesTeamGroupSearchParam.setCode(categoriesEditParam.getCode());
		categoriesTeamGroupSearchParam.setCategoryTeamId(""+categoriesTeamGroupService.getCategoriesTeamById(categoriesTeamGroupSearchParam).getCategoryTeamId());
		
		model.addAttribute("categoryTeam",categoriesTeamGroupService.getCategoriesTeamById(categoriesTeamGroupSearchParam));
		model.addAttribute("itemList",categoriesTeamGroupService.getCategoryTeamItemListByParam(categoriesTeamGroupSearchParam));
		model.addAttribute("categoriesEditParam", categoriesEditParam);
		
		return ViewUtils.view();
		
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
		
		CategoriesEdit categoriesEdit = categoriesEditService.getCategoryByParam(categoriesEditParam);
		
		model.addAttribute("categoriesEditParam", categoriesEditParam);
		if(categoriesEdit == null){
			model.addAttribute("mode", "create");
		}else {
			model.addAttribute("mode", "update");
		}
		model.addAttribute("categoriesEdit", categoriesEdit);
		
		return ViewUtils.view();
		
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
		
		model.addAttribute("categoriesEditParam", categoriesEditParam);
		model.addAttribute("categoryPromotionList",categoriesEditService.getCategoryPromotionListByParam(categoriesEditParam));
		
		return ViewUtils.view();
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/promotion-banner/create")
	
	public String promotionCreateAction(Model model, CategoriesEdit categoriesEdit) {
		
		categoriesEditService.insertCategoryEditFiles(categoriesEdit);
		// 등록 되었습니다. 	
		String redirectUrl = "/opmanager/categories-edit/popup/promotion-banner?"
										+"editPosition="+categoriesEdit.getEditPosition()
										+"&code="+categoriesEdit.getCode()
										+"&editKind="+categoriesEdit.getEditKind();
		
		return ViewUtils.redirect(redirectUrl , MessageUtils.getMessage("M00288"), "self.close();");
		
		
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/file-delete")
	public JsonView fileDelete(Model model, CategoriesEdit categoriesEdit) {
		
		categoriesEditService.updateCategoryEditFile(categoriesEdit);
		
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
		
		return ViewUtils.view();
		
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
		String footerContent = categories.getCategoryFooter();
		
		categories = categoriesService.getCategoriesById(categories.getCategoryId());
		categories.setCategoryHeader(headerContent);
		categories.setCategoryFooter(footerContent);
		
		categoriesService.updateCategoryEdit(categories);
		
		// 수정 되었습니다. 
		return ViewUtils.redirect("/opmanager/categories-edit/popup/header", MessageUtils.getMessage("M00289"), "self.close();");
		
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
	
	
	/**
	 * 왼쪽 배너 관리
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/popup/left-banner")
	@RequestProperty(title="왼쪽배너 화면관리", layout="base")
	public String leftBannerPopup(Model model, CategoriesEditParam categoriesEditParam) {
		List<CategoriesEdit> leftBanners = categoriesEditService.getCategoryLeftBanner(categoriesEditParam);

		
		
		model.addAttribute("categoriesEditParam", categoriesEditParam);
		model.addAttribute("categoryLeftBanner",leftBanners);
		
		return ViewUtils.view();
		
	}
	
	/**
	 * 왼쪽 배너 관리
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/left-banner/create")
	
	public String leftCreateAction(Model model, CategoriesEdit categoriesEdit) {
		
		categoriesEditService.insertCategoryEditFiles(categoriesEdit);
		
		// 등록 되었습니다. 	
		String redirectUrl = "/opmanager/categories-edit/popup/left-banner?"
										+"editPosition="+categoriesEdit.getEditPosition()
										+"&code="+categoriesEdit.getCode()
										+"&editKind="+categoriesEdit.getEditKind();
		
		return ViewUtils.redirect(redirectUrl , MessageUtils.getMessage("M00288"), "self.close();");
		
		
		
	}
	
}
