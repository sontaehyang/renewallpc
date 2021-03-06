package saleson.shop.featured;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.OpenGraphUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.banword.BanWordService;
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
@RequestProperty(title="???????????????", template="mobile", layout="default")
public class FeaturedMobileController {
	private static final Logger log = LoggerFactory.getLogger(FeaturedMobileController.class);
	
	@Autowired
	private FeaturedService featuredService;

	@Autowired
	private UserLevelService userLevelService;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private SequenceService sequenceService;

    @Autowired
    private BanWordService banWordService;


    /**
	 * ????????? ?????????
	 * @param model
	 * @param featuredParam
	 * @return
	 */
	@GetMapping("/m/featured")
	public String featuredList(Model model, FeaturedParam featuredParam) {
		featuredParam.setConditionType("FRONT_FEATURED");
		featuredParam.setFeaturedClass(1);

		return makeList(model, featuredParam, "/featured/list", false);
	}
	
	/**
	 * ????????? ????????? (?????????)
	 * @param model
	 * @param featuredParam
	 * @return
	 */
	@PostMapping("/m/featured/list")
	@RequestProperty(layout="blank")
	public String moreFeaturedList(Model model, FeaturedParam featuredParam) {
		featuredParam.setConditionType("FRONT_FEATURED");
		featuredParam.setFeaturedClass(1);

		return makeList(model, featuredParam, "/include/pages-item-list", true);
		
	}

	/**
	 * ????????? ?????????
	 * @param model
	 * @param featuredParam
	 * @return
	 */
	@GetMapping("/m/event")
	public String eventList(Model model, FeaturedParam featuredParam) {
		featuredParam.setConditionType("FRONT_EVENT");
		featuredParam.setFeaturedClass(2);

		return makeList(model, featuredParam, "/featured/list", false);

	}

	/**
	 * ????????? ????????? (?????????)
	 * @param model
	 * @param featuredParam
	 * @return
	 */
	@PostMapping("/m/event/list")
	@RequestProperty(layout="blank")
	public String moreEventList(Model model, FeaturedParam featuredParam) {
		featuredParam.setConditionType("FRONT_EVENT");
		featuredParam.setFeaturedClass(2);

		return makeList(model, featuredParam, "/include/pages-item-list", true);
	}

	/**
	 * ?????????/????????? ????????? ??????
	 * @param model
	 * @param featuredParam
	 * @param returnPath
	 * @param isMore
	 * @return
	 */
	private String makeList(Model model, FeaturedParam featuredParam, String returnPath, boolean isMore) {
		
			featuredParam.setFeaturedType("1");
			featuredParam.setFeaturedFlag("Y");
			featuredParam.setDisplayListFlag("Y");

			if (featuredParam.getFeaturedCodeChecked() == null){
				featuredParam.setFeaturedCodeChecked("");
			}

			int count = featuredService.getFeaturedCountByParamForFront(featuredParam);
			Pagination pagination = Pagination.getInstance(count, 15);
			
			if (isMore) {
				pagination.setLink("javascript:paginationMore([page])");
			}
			
			featuredParam.setPagination(pagination);
			
			model.addAttribute("pagination",pagination);
			model.addAttribute("featuredList",featuredService.getFeaturedListByParamForFront(featuredParam));
			model.addAttribute(featuredParam.getFeaturedCodeChecked(),"class=\"default\"");
			model.addAttribute("categoryTeamCode",featuredParam.getFeaturedCodeChecked());
			model.addAttribute("featuredParam",featuredParam);
			
			return ViewUtils.getView(returnPath);
	}
	
	/**
	 * ??????????????? ??????
	 * @param featuredUrl
	 * @param featuredParam
	 * @param model
	 * @param layout
	 * @return
	 */
	@GetMapping("/m/pages/{featuredUrl}")
	public String details(@PathVariable("featuredUrl") String featuredUrl, FeaturedParam featuredParam, Model model,
			@RequestParam(value="layout", defaultValue="", required=false) String layout) {
		// /pages/qanda ==> /faq ??? forward
		if ("qanda".equals(featuredUrl)) {
			log.debug("[Forward] /faq/");
			return ViewUtils.forward("/faq/");
		}
		
		if (StringUtils.isNotEmpty(layout)) {
			RequestContextUtils.setLayout(layout);
		}
		
		Featured featured = featuredService.getFeaturedById(featuredParam);

		if (featured == null || "N".equals(featured.getFeaturedFlag())) {
			throw new UserException("???????????? ???????????? ????????????.");
		}

		// ???????????? ???????????? ??????
		if (!StringUtils.isEmpty(featured.getPrivateType())) {
			List<String> privateType = ItemUtils.getPrivateTypes();
			boolean isExists = privateType.stream().anyMatch(type -> type.equals(featured.getPrivateType()));

			if (!isExists) {
				throw new UserException(ItemUtils.getPrivateTypeName(featured.getPrivateType()) + "??? ?????? ????????? ????????????.");
			}
		}
		
		if (featured.getStartDate() != null && !"".equals(featured.getStartDate()) && featured.getEndDate() != null && !"".equals(featured.getEndDate())) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
			Date currentDate = new Date();
			
			int startDate = Integer.parseInt(featured.getStartDate()+featured.getStartTime());
			int endDate = Integer.parseInt(featured.getEndDate()+featured.getEndTime());
			int today = Integer.parseInt(sdf.format(currentDate));

			// Jun-Eu Son 2017.4.24 ????????????, ?????????????????? ????????? ????????????
			if ((!UserUtils.isManagerLogin() && (today < startDate || today > endDate)) ||
					(!UserUtils.isManagerLogin() && (!featured.getDisplayListFlag().equals("Y")))) {
				throw new UserException("?????? ???????????? ?????????????????????.");
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
	 * ????????????
	 * @return
	 */
	@GetMapping("/m/pages/about_us")
	public String about_us(){
		
		return ViewUtils.getView("/featured/footer/about_us");
	}

	/**
	 * ????????????
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/m/pages/etc_clause")
	public String etc_clause(Model model, @RequestParam(value = "id", defaultValue = "0") int id){

		setPolicyModel(model, id, Policy.POLICY_TYPE_AGREEMENT);
		
		return ViewUtils.getView("/featured/footer/etc_clause");
	}

	/**
	 * ???????????? ????????????
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/m/pages/etc_protect")
	public String etc_protect(Model model,
							  @RequestParam(value = "id", defaultValue = "0") int id){

		setPolicyModel(model, 1, Policy.POLICY_TYPE_PROTECT_POLICY);

		return ViewUtils.getView("/featured/footer/etc_protect");
	}

	/**
	 * ?????? ??????
	 * @param model
	 * @return
	 */
	@GetMapping("/m/pages/rating-info")
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

	@PostMapping("/m/detail-policy-data")
	public JsonView getDetailPolicy(int policyId) {

		Policy policy = policyService.getPolicyByPolicyId(policyId);

		return JsonViewUtils.success(policy);
	}

	@PostMapping("/m/featured/write-reply")
	public JsonView writeFeaturedReply(RequestContext requestContext, FeaturedReply featuredReply) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		if (!UserUtils.isUserLogin()) {
			return JsonViewUtils.failure("?????? ????????? ????????? ??? ?????? ???????????????.");
		}

		for (String banWord : ShopUtils.getConfig().getBanWords()) {
			if (featuredReply.getReplyContent().indexOf(banWord) > -1) {
				return JsonViewUtils.failure("????????? ???????????? ???????????? ????????????.");
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

	@GetMapping("/m/featured-reply-list")
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
