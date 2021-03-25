package saleson.shop.featured;

import com.onlinepowers.framework.annotation.handler.Authorize;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.OpenGraphUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.categoriesedit.CategoriesEditService;
import saleson.shop.categoriesedit.support.CategoriesEditParam;
import saleson.shop.featured.domain.Featured;
import saleson.shop.featured.domain.FeaturedReply;
import saleson.shop.featured.support.FeaturedItem;
import saleson.shop.featured.support.FeaturedParam;
import saleson.shop.featured.support.FeaturedReplyParam;
import saleson.shop.policy.PolicyService;
import saleson.shop.policy.domain.Policy;
import saleson.shop.policy.support.PolicyParam;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.userlevel.UserLevelService;
import saleson.shop.userlevel.domain.UserLevel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestProperty(title="특집페이지", layout="default")
public class FeaturedController {
	private static final Logger log = LoggerFactory.getLogger(FeaturedController.class);
	
	@Autowired
	private FeaturedService featuredService;
	
	@Autowired
	private CategoriesEditService categoriesEditService;
	
	@Autowired
	private UserLevelService userLevelService;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private SequenceService sequenceService;

	/**
	 * 등급 안내
	 * 
	 * @return
	 */
	@GetMapping("/pages/rating-info")
	@RequestProperty(layout="base")
	public String rating(Model model) {
	
		String groupCode = "default";
		if (UserUtils.isUserLogin()) {
			UserDetail userDetail = UserUtils.getUserDetail();
			if (StringUtils.isEmpty(userDetail.getGroupCode()) == false) {
				groupCode = userDetail.getGroupCode();
			}
		}

		List<UserLevel> userLevels = userLevelService.getUserLevelListByGroupCode(groupCode);
		if (userLevels == null && !"default".equals(groupCode)) {
			userLevels = userLevelService.getUserLevelListByGroupCode("default");
		}

		if (userLevels == null) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("userLevels", userLevels);
		return ViewUtils.getView("/featured/mypage/rating-info");
	}
	
	/**
	 * 기획전 목록
	 */
	@GetMapping("/featured/list")
	public String fearturedList(RequestContext requestContext, FeaturedParam featuredParam, Model model) {

		featuredParam.setFeaturedClass(1);
		featuredParam.setConditionType("FRONT_FEATURED");
		featuredParam.setFeaturedType("1"); 	// PC
		featuredParam.setFeaturedFlag("Y");
		featuredParam.setDisplayListFlag("Y");

		if (featuredParam.getFeaturedCodeChecked() == null) {
			featuredParam.setFeaturedCodeChecked("");
		}
		
		Pagination pagination = Pagination.getInstance(featuredService.getFeaturedCountByParamForFront(featuredParam));
		
		featuredParam.setPagination(pagination);

		List<Featured> featuredList = featuredService.getFeaturedListByParamForFront(featuredParam);
		
		model.addAttribute("featuredList", featuredList);
		model.addAttribute("featuredParam", featuredParam);
		model.addAttribute("pagination", pagination);
		
		return ViewUtils.getView("/featured/list");
	}

	/**
	 * 이벤트 목록
	 */
	@GetMapping({"/event/list"})
	public String eventList(FeaturedParam featuredParam, Model model) {

		featuredParam.setFeaturedClass(2);
		featuredParam.setConditionType("FRONT_EVENT");
		featuredParam.setFeaturedType("1"); 	// PC
		featuredParam.setFeaturedFlag("Y");
		featuredParam.setDisplayListFlag("Y");

		if (featuredParam.getFeaturedCodeChecked() == null) {
			featuredParam.setFeaturedCodeChecked("");
		}

		Pagination pagination = Pagination.getInstance(featuredService.getFeaturedCountByParamForFront(featuredParam));

		featuredParam.setPagination(pagination);

		List<Featured> featuredList = featuredService.getFeaturedListByParamForFront(featuredParam);

		model.addAttribute("featuredList", featuredList);
		model.addAttribute("featuredParam", featuredParam);
		model.addAttribute("pagination", pagination);

		return ViewUtils.getView("/featured/list");
	}

	/**
	 * 페이지 로드
	 * @param featuredUrl
	 * @param featuredParam
	 * @param model
	 * @param layout
	 * @return
	 */
	@GetMapping("/pages/{featuredUrl}")
	public String details(@PathVariable("featuredUrl") String featuredUrl, FeaturedParam featuredParam, Model model,
			@RequestParam(value="layout", defaultValue="", required=false) String layout, RequestContext requestContext) {
		if ("".equals(layout)
				&& !(requestContext.getQueryString() == null || requestContext.getQueryString().isEmpty())) {
			return "redirect:/pages/" + featuredUrl;
		}

		if (StringUtils.isNotEmpty(layout)) {
			RequestContextUtils.setLayout(layout);
		}
		
		Featured featured = featuredService.getFeaturedById(featuredParam);

		if (featured == null || "N".equals(featured.getFeaturedFlag())) {
			throw new UserException("기획전이 존재하지 않습니다.");
		}

		// 전용등급 접근권한 설정
		if (!StringUtils.isEmpty(featured.getPrivateType())) {
			List<String> privateType = ItemUtils.getPrivateTypes();
			boolean isExists = privateType.stream().anyMatch(type -> type.equals(featured.getPrivateType()));

			if (!isExists) {
				throw new UserException(ItemUtils.getPrivateTypeName(featured.getPrivateType()) + "만 접근 권한이 있습니다.");
			}
		}
		
		if (featured.getStartDate() != null && !"".equals(featured.getStartDate()) && featured.getEndDate() != null && !"".equals(featured.getEndDate())) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
			Date currentDate = new Date();
			
			int startDate = Integer.parseInt(featured.getStartDate()+featured.getStartTime());
			int endDate = Integer.parseInt(featured.getEndDate()+featured.getEndTime());
			int today = Integer.parseInt(sdf.format(currentDate));
			
			// Jun-Eu Son 2017.4.24 기간만료, 비공개상태인 기획전 진입금지
			if ((!UserUtils.isManagerLogin() && (today < startDate || today > endDate)) || 
					(!UserUtils.isManagerLogin() && (!featured.getDisplayListFlag().equals("Y")))) {
				throw new UserException("해당 기획전은 종료되었습니다.");
			}
		}

		
		if (ValidationUtils.isNotNull(featured.getSeo())) {
			if(!featured.getSeo().isSeoNull()) {
				ShopUtils.setSeo(featured.getSeo());
			}
		}

		featuredParam.setConditionType("FRONT_DISPLAY_ITEM");
		featuredParam.setFeaturedId(featured.getFeaturedId());
		featuredParam.setProdState(featured.getProdState());
		featuredParam.setPrivateTypes(ItemUtils.getPrivateTypes());
		
		List<HashMap<String, String>> itemTypeList = featuredService.getItemTypeList(featured.getProdState(), featuredParam);
		
		HashMap<String, List<FeaturedItem>> itemListMap = featuredService.getItemListMap(featured.getProdState(), featuredParam);

		model.addAttribute("featuredUrl", featuredUrl);

		model.addAttribute("itemTypeList", itemTypeList);
		model.addAttribute("itemListMap", itemListMap);
		model.addAttribute("featured",featured);
		model.addAttribute("layout", layout);
		
		model.addAttribute("ITEM_TYPE_ID_KEY",Featured.ITEM_TYPE_ID_KEY);
		model.addAttribute("ITEM_TYPE_NAME_KEY",Featured.ITEM_TYPE_NAME_KEY);

		if (!StringUtils.isEmpty(featured.getEventCode())) {
			model.addAttribute("itemUserCodes", JsonViewUtils.objectToJson(featuredService.getItemUserCodesByItemListMap(itemListMap)));
		}

		OpenGraphUtils.setOpenGraphModelByFeatured(model, featured);

		return ViewUtils.getView("/featured/details");
	}
	
	/**
	 * 회사 소개
	 * @return
	 */
	@GetMapping("/pages/about_us")
	@RequestProperty(layout="pages")
	public String aboutUs() {
		return ViewUtils.getView("/featured/footer/about_us");
	}

	/**
	 * 이용약관
	 * @param model
	 * @return
	 */
	@GetMapping("/pages/etc_clause")
	@RequestProperty(layout="pages")
	public String etc_clause(Model model, @RequestParam(value = "id", defaultValue = "0") int id) {

		setPolicyModel(model, id, Policy.POLICY_TYPE_AGREEMENT);
		
		return ViewUtils.getView("/featured/footer/etc_clause");
	}

	/**
	 * 개인정보보호방침
	 * @param model
	 * @return
	 */
	@GetMapping("/pages/etc_protect")
	@RequestProperty(layout="pages")
	public String etc_protect(Model model,
							  @RequestParam(value = "id", defaultValue = "0") int id) {

		setPolicyModel(model, 1, Policy.POLICY_TYPE_PROTECT_POLICY);


		return ViewUtils.getView("/featured/footer/etc_protect");
	}
	
	@GetMapping("/pages/etc_guide")
	@RequestProperty(layout="pages")
	public String etc_guide() {
		
		return ViewUtils.getView("/featured/footer/etc_guide");
	}


	private void setPolicyModel(Model model, int id, String policyType) {

		Policy policy;


		if (Integer.parseInt(policyType) > 0) {
			policy = policyService.getPolicyByParam(id, policyType);
		} else {
			policy = policyService.getCurrentPolicyByType(policyType);
		}

		PolicyParam policyParam = new PolicyParam();
		policyParam.setPolicyType(policyType);

		model.addAttribute("periodList", policyService.getPeriodListByParam(policyParam));

		model.addAttribute("policyList", policyService.getPolicyListByParam(policyParam));
		model.addAttribute("policy", policy);
	}

	@PostMapping("/detail-policy-data")
	public JsonView getDetailPolicy(int policyId) {

		Policy policy = policyService.getPolicyByPolicyId(policyId);

		return JsonViewUtils.success(policy);
	}


	@PostMapping("/featured/write-reply")
	public JsonView writeFeaturedReply(RequestContext requestContext, FeaturedReply featuredReply) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		if (!UserUtils.isUserLogin()) {
			return JsonViewUtils.failure("댓글 등록은 로그인 후 사용 가능합니다.");
		}

		for (String banWord : ShopUtils.getConfig().getBanWords()) {
			if (featuredReply.getReplyContent().indexOf(banWord) > -1) {
				return JsonViewUtils.failure("내용에 금지어가 포함되어 있습니다.");
			}
		}

		long id = sequenceService.getId("OP_FEATURED_REPLY");

		featuredReply.setId(id);
		featuredReply.setUserId(UserUtils.getUserId());
		featuredReply.setUserName(UserUtils.getLoginId());
		featuredReply.setDataStatus("0");
		featuredReply.setCreatedBy(UserUtils.getUserId());

		featuredService.insertFeaturedReply(featuredReply);

		FeaturedReplyParam featuredReplyParam = new FeaturedReplyParam();
		featuredReplyParam.setId(id);

		return JsonViewUtils.success(getReplyListByParam(featuredReplyParam));
	}



	@PostMapping("/featured-reply-list")
	@RequestProperty(layout="blank")
	public String listFeaturedReply(FeaturedReplyParam featuredReplyParam, Model model, FeaturedParam featuredParam) {

		Featured featured = featuredService.getFeaturedById(featuredParam);

		featuredReplyParam.setFeaturedId(featuredReplyParam.getFeaturedId());
		featuredReplyParam.setDataStatus("0");

		int replyCount = featuredService.getFeaturedReplyCountByParam(featuredReplyParam);

		Pagination pagination = Pagination.getInstance(replyCount);
		pagination.setLink("javascript:paginationFeaturedReply([page])");
		featuredReplyParam.setPagination(pagination);

		List<FeaturedReply> replyList = featuredService.getFeaturedReplyByParam(featuredReplyParam);

		model.addAttribute("featured", featured);
		model.addAttribute("pagination", pagination);
		model.addAttribute("replyList", getReplyListByParam(featuredReplyParam));

		return ViewUtils.getView("/featured/reply-list");
	}

	private List<FeaturedReply> getReplyListByParam(FeaturedReplyParam featuredReplyParam) {

		List<FeaturedReply> list = featuredService.getFeaturedReplyByParam(featuredReplyParam);

		if (list != null && !list.isEmpty()) {

			list.stream().forEach(reply -> {
				reply.setCreated(DateUtils.datetime(reply.getCreated()));
				reply.setUserName(UserUtils.reMasking(reply.getUserName(),"id"));
			});

		}

		return list;
	}

}
