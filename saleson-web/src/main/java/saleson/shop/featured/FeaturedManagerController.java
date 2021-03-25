package saleson.shop.featured;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.categoriesedit.CategoriesEditService;
import saleson.shop.categoriesedit.domain.CategoriesEdit;
import saleson.shop.categoriesedit.support.CategoriesEditParam;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.featured.domain.Featured;
import saleson.shop.featured.domain.FeaturedReply;
import saleson.shop.featured.support.*;
import saleson.shop.group.GroupService;

import java.util.List;

@Controller
@RequestMapping({"/opmanager/featured", "/opmanager/featured-mobile"})
@RequestProperty(template = "opmanager", layout="default")
public class FeaturedManagerController {

	private static final Logger log = LoggerFactory.getLogger(FeaturedManagerController.class);
	
	@Autowired
	FeaturedService featuredService; 
	
	@Autowired
	CategoriesEditService categoriesEditService;
	
	@Autowired
	CategoriesTeamGroupService categoriesTeamGroupService;

	/**
	 * 기획전 리스트
	 * @param itemsPerPage
	 * @param featuredParam
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(@RequestParam(value="itemsPerPage", required=false) String itemsPerPage, 
			FeaturedParam featuredParam, Model model) {
		// 모바일 / PC 구분
		if (getFeaturedTypeUri().equals("featured")) {
			featuredParam.setFeaturedType("1");
		} else {
			featuredParam.setFeaturedType("2");
		}
		//진행상태	
		/*if (StringUtils.isEmpty(featuredParam.getProgression())) {
			featuredParam.setProgression("2"); //진행중
		}*/
		if (itemsPerPage == null) {
			featuredParam.setItemsPerPage(20);
		}
		
		int count = featuredService.getFeaturedCountByParam(featuredParam);
		String query ="";
		
		if(featuredParam.getQuery() != null){
			query = featuredParam.getQuery();
			featuredParam.setQuery(featuredParam.getQuery().replace("/pages/", "").replace("/pages", "").replace("pages/", ""));
		}
		
		Pagination pagination = Pagination.getInstance(count, featuredParam.getItemsPerPage());
		featuredParam.setPagination(pagination);
		
		model.addAttribute("featuredList",featuredService.getFeaturedListByParam(featuredParam));
		featuredParam.setQuery(query);
		model.addAttribute("featuredParam",featuredParam);
		model.addAttribute("featuredCount",count);
		model.addAttribute("pagination",pagination);
		model.addAttribute("featuredTypeUri", getFeaturedTypeUri());
		
		return ViewUtils.getView("/featured/list");
		
	}


	/**
	 * 기획전 노출 순서 설정
	 * @param requestContext
	 * @param featuredListParam
	 * @return
	 */
	@PostMapping("list/change-ordering")
	public JsonView changeOrdering(RequestContext requestContext, FeaturedListParam featuredListParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		featuredService.updateFeaturedOrdering(featuredListParam);
		return JsonViewUtils.success();  
		
	}
	
	/**
	 * 기획전 등록
	 * @param model
	 * @param featured
	 * @return
	 */
	@GetMapping("/create")
	public String create(Model model, Featured featured) {
		if (getFeaturedTypeUri().equals("featured")) {
			featured.setFeaturedType("1");
		} else {
			featured.setFeaturedType("2");
		}
		String mainPart = "기획전";

		model.addAttribute("mode","create");
		model.addAttribute("mainPart",mainPart);
		model.addAttribute("featured",featured);
		model.addAttribute("featuredCheck","0");
		model.addAttribute("featuredTypeUri", getFeaturedTypeUri());
		model.addAttribute("mainPart", "기획전");
		model.addAttribute("hours", ShopUtils.getHours());
		model.addAttribute("categoryGroupList", categoriesTeamGroupService.getCategoriesGroupList());
		// model.addAttribute("pdExGubnCodes", hspCodeService.getHspSubCodeList("82"));			// 전용구분 코드

		// 전용등급
		model.addAttribute("privateTypes", ItemUtils.getPrivateTypeCodes());

		return ViewUtils.getView("/featured/form");
		
	}
	
	@PostMapping("/create")
	public String createAction(Model model, Featured featured, FeaturedItemParam featuredItemParam) {
		/*if (getFeaturedTypeUri().equals("featured")) {
			featured.setFeaturedType("1");
		} else {
			featured.setFeaturedType("2");
		}*/
		
		// featured.setFeaturedFile(imageFile);
		
		featuredService.insertFeatured(featured, featuredItemParam);
		
		return ViewUtils.redirect("/opmanager/" + getFeaturedTypeUri() + "/list", MessageUtils.getMessage("M00288")); 	// 등록 되었습니다. 
		
	}


	/**
	 * 기획전 수정
	 * @param model
	 * @param featuredParam
	 * @return
	 */
	@GetMapping("/edit/{featuredId}")
	public String edit(Model model, FeaturedParam featuredParam) {
		Featured featured = featuredService.getFeaturedById(featuredParam);
		
		List<FeaturedItem> list = featuredService.getFeaturedItemListByParam(featuredParam);
		List<String> userGroupList = featuredService.getUserDefGroupById(featuredParam);

		String mainPart = "기획전";

		model.addAttribute("mode","edit");
		model.addAttribute("mainPart",mainPart);
		model.addAttribute("list",list);
		model.addAttribute("userGroupList", userGroupList);
		model.addAttribute("featured",featured);
		model.addAttribute("featuredCheck","1");
		model.addAttribute("featuredTypeUri", getFeaturedTypeUri());
		model.addAttribute("hours", ShopUtils.getHours());
		model.addAttribute("categoryGroupList", categoriesTeamGroupService.getCategoriesGroupList());
		// model.addAttribute("pdExGubnCodes", hspCodeService.getHspSubCodeList("82"));			// 전용구분 코드

		// 전용등급
		model.addAttribute("privateTypes", ItemUtils.getPrivateTypeCodes());

 		return ViewUtils.getView("/featured/form");
	}
	
	@PostMapping("/edit/{featuredId}")
	public String editAction(Model model, Featured featured, FeaturedItemParam featuredItemParam) {
		if (getFeaturedTypeUri().equals("featured")) {
			featured.setFeaturedType("1");
		} else {
			featured.setFeaturedType("2");
		}
		// featured.setFeaturedFile(imageFile);
		featuredService.updateFeaturedById(featured, featuredItemParam);
		
		return ViewUtils.redirect("/opmanager/" + getFeaturedTypeUri() + "/edit/"+featured.getFeaturedId(), MessageUtils.getMessage("M00289")); 	// 수정 되었습니다. 
		
	}

	/**
	 * 기획전 선택 삭제
	 * @param model
	 * @param featuredParam
	 * @return
	 */
	@PostMapping("/checked-delete")
	public String checkedDelete(Model model, FeaturedParam featuredParam) {
		
		featuredService.deleteFeaturedsById(featuredParam);
		
		return ViewUtils.redirect("/opmanager/" + getFeaturedTypeUri() + "/list", MessageUtils.getMessage("M00205")); // 삭제 되었습니다. 
		
	}

	/**
	 * 기획전 이미지 삭제
	 * @param requestContext
	 * @param featuredParam
	 * @return
	 */
	@PostMapping("delete-image")
	public JsonView deleteImage(RequestContext requestContext, FeaturedParam featuredParam) {
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		featuredService.deleteImageByItemId(featuredParam);
		
		return JsonViewUtils.success();
	}

	/**
	 * 기획전 배너 생성
	 * @param model
	 * @param featured
	 * @param categoriesEditParam
	 * @return
	 */
	@GetMapping("/banner-create")
	public String bannerCreate(Model model, Featured featured, CategoriesEditParam categoriesEditParam) {
		categoriesEditParam.setCode("featured");
		categoriesEditParam.setEditPosition("promotion");
		categoriesEditParam.setEditKind("1");
		categoriesEditParam.setType("1");
		
		model.addAttribute("categoriesEditParam",categoriesEditParam);
		model.addAttribute("categoryPromotionList",categoriesEditService.getCategoryPromotionListByParam(categoriesEditParam));
		
		
		return ViewUtils.view();
		
	}

	/**
	 * 기획전 배너 생성 처리
	 * @param model
	 * @param featured
	 * @param categoriesEdit
	 * @return
	 */
	@PostMapping("/banner-create")
	public String bannerCreateAction(Model model, Featured featured, CategoriesEdit categoriesEdit) {
		
		categoriesEditService.insertCategoryEditFiles(categoriesEdit);
		
		return ViewUtils.redirect("/opmanager/" + getFeaturedTypeUri() + "/banner-create", MessageUtils.getMessage("M00288")); 	// 등록 되었습니다.
		
	}
	
	@PostMapping("/url-search")
	public JsonView codeCheck(Model model, @RequestParam("featuredUrl") String featuredUrl, 
			FeaturedParam featuredParam) {
		
		if (getFeaturedTypeUri().equals("featured")) {
			featuredParam.setFeaturedType("1");
		} else {
			featuredParam.setFeaturedType("2");
		}
		
		
		// url 조회 (pc/모바일 구분없음)
		int count = featuredService.getFeaturedCountByParam(featuredParam);
	
		
		
		/* url 조회 (pc/모바일 구분없음)
		int count = featuredService.getFeaturedCountByUrl(featuredUrl);
		*/
		if(count > 0){
			return JsonViewUtils.failure(MessageUtils.getMessage("M01486"));	// 코드 또는 URL 존재 합니다.
		}
		
		return JsonViewUtils.success();
		
	}
	
	private String getFeaturedTypeUri() {
		if (RequestContextUtils.getRequestUri().indexOf("/opmanager/featured-mobile") > -1) {
			return "featured-mobile";
		}
		return "featured";
	}

	@GetMapping("/manage-event-reply")
	@RequestProperty(layout = "base")
	public String manageEventReply(int featuredId, Model model, @ModelAttribute("featuredReplyParam") FeaturedReplyParam featuredReplyParam) {

		int replyCount = featuredService.getFeaturedReplyCountByParam(featuredReplyParam);

		Pagination pagination = Pagination.getInstance(replyCount);

		featuredReplyParam.setPagination(pagination);

		List<FeaturedReply> replyList = featuredService.getFeaturedReplyByParam(featuredReplyParam);

		model.addAttribute("pagination", pagination);
		model.addAttribute("replyList", replyList);

		return "view:/featured/reply-list";
	}

	@PostMapping("/display-reply")
	public JsonView updateDisplayReply(FeaturedReply featuredReply) {

		if (featuredReply.getIds().length > 0) {
			featuredReply.setUpdatedBy(UserUtils.getManagerId());
			featuredService.updateDisplayReply(featuredReply);
		}

		return JsonViewUtils.success();
	}

	@PostMapping("/update-event-code/{id}")
	public JsonView updateEventCode(@PathVariable("id")int id) {

		String errorMessage = "이벤트 코드 생성에 실패 했습니다.";

		try {

			if (featuredService.updateEventCode(id) > 0) {
				return JsonViewUtils.success();
			} else {
				return JsonViewUtils.failure(errorMessage);
			}

		} catch (Exception ignore) {
			log.error("updateEventCode {}", ignore.getMessage(), ignore);
			return JsonViewUtils.failure(errorMessage);
		}
	}

}