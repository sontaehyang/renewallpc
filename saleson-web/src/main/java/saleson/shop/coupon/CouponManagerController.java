package saleson.shop.coupon;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import saleson.common.utils.UserUtils;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.*;
import saleson.shop.group.GroupService;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.ItemBase;
import saleson.shop.user.UserService;
import saleson.shop.userlevel.UserLevelService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/opmanager/coupon")
@RequestProperty(title="쿠폰 관리", layout="default")
public class CouponManagerController {
	
	@Autowired
	private CouponService couponService; 
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserLevelService userLevelService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;
	
	/**
	 * 쿠폰 리스트
	 * @param model
	 * @param couponParam
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model, CouponParam couponParam) {

		int count = couponService.getCouponCountByParamForManager(couponParam);
		
		Pagination pagination = Pagination.getInstance(count, 10);
		couponParam.setPagination(pagination);

		model.addAttribute("couponCount", count);
		model.addAttribute("pagination", pagination);
		model.addAttribute("couponList", couponService.getCouponListByParamForManager(couponParam));
		
		return ViewUtils.view();
		
	}
	
	/**
	 * 쿠폰 오프라인 코드 목록 엑셀 다운로드
	 * @param couponId
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("/offline-coupon-download/{couponId}")
	public ModelAndView offlineCouponDownload(@PathVariable("couponId") int couponId) {
		ModelAndView mav = new ModelAndView(new CouponOfflineExcelView());
        
        mav.addObject("couponOfflineList", couponService.getCouponOfflineListByCouponId(couponId));
        return mav;
		
	}
	
	/**
	 * 쿠폰 등록화면에 공통 데이터 등록
	 * @param model
	 */
	public void initFormData(Model model) {
		model.addAttribute("groupList", groupService.getAllGroupList());
		model.addAttribute("userLevelGroup", userLevelService.getUserLevelGroupList(null));
	}
	
	/**
	 * 쿠폰 등록 
	 * @param model
	 * @return
	 */
	@GetMapping("/create")
	public String create(Model model) {
		
		model.addAttribute("coupon", new Coupon());
		initFormData(model);
		
		return ViewUtils.view();
		
	}
	
	/**
	 * 쿠폰 등록 처리
	 * @param coupon
	 * @return
	 */
	@PostMapping("/create")
	public String createAction(Coupon coupon) {

		if ("Y".equals(coupon.getDirectInputFlag())) {
			if(duplicateDirectInputValue(coupon.getCouponId(), coupon.getDirectInputValue())) {
				return ViewUtils.redirect("/opmanager/coupon/create", "직접 입력 쿠폰 정보가 중복 입니다.");
			}
		}

		couponService.insertCoupon(coupon);
		
		return ViewUtils.redirect("/opmanager/coupon/list", MessageUtils.getMessage("M00288")); // 등록 되었습니다. 
		
	}
	
	/**
	 * 쿠폰 수정 - View
	 * @param couponId
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{couponId}")
	public String edit(@PathVariable("couponId") int couponId, Model model) {
		
		Coupon coupon = couponService.getCouponById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		
		if (!coupon.getCouponIssueStartDate().isEmpty()) {
			coupon.setCouponIssueStartDate(coupon.getCouponIssueStartDate().substring(0, 8));
			coupon.setCouponIssueEndDate(coupon.getCouponIssueEndDate().substring(0, 8));
		}
		if (!coupon.getCouponApplyStartDate().isEmpty()) {
			coupon.setCouponApplyStartDate(coupon.getCouponApplyStartDate().substring(0, 8));
			coupon.setCouponApplyEndDate(coupon.getCouponApplyEndDate().substring(0, 8));
		}
		
		model.addAttribute("coupon", coupon);
		initFormData(model);
		
		return ViewUtils.view();
		
	}
	
	/**
	 * 쿠폰 수정 처리
	 * @param model
	 * @param coupon
	 * @return
	 */
	@PostMapping("/edit/{couponId}")
	public String editAction(Model model, Coupon coupon) {

		if ("Y".equals(coupon.getDirectInputFlag())) {
			if(duplicateDirectInputValue(coupon.getCouponId(), coupon.getDirectInputValue())) {
				return ViewUtils.redirect("/opmanager/coupon/edit/"+ coupon.getCouponId(), "직접 입력 쿠폰 정보가 중복 입니다.");
			}
		}

		couponService.updateCoupon(coupon);
		return ViewUtils.redirect("/opmanager/coupon/list", MessageUtils.getMessage("M00289")); 	// 수정 되었습니다. 
		
	}
	
	/**
	 * 쿠폰 복사 생성
	 * @param couponId
	 * @param model
	 * @return
	 */
	@GetMapping("/copy-create/{couponId}")
	public String copyCreate(@PathVariable("couponId") int couponId, Model model) {
		
		Coupon coupon = couponService.getCouponById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		
		coupon.setCouponId(0);
		if (!coupon.getCouponIssueStartDate().isEmpty()) {
			coupon.setCouponIssueStartDate(coupon.getCouponIssueStartDate().substring(0, 8));
			coupon.setCouponIssueEndDate(coupon.getCouponIssueEndDate().substring(0, 8));
		}
		if (!coupon.getCouponApplyStartDate().isEmpty()) {
			coupon.setCouponApplyStartDate(coupon.getCouponApplyStartDate().substring(0, 8));
			coupon.setCouponApplyEndDate(coupon.getCouponApplyEndDate().substring(0, 8));
		}
		coupon.setDataStatusCode("0");

		// 직접입력 영역 초기화
		coupon.setDirectInputFlag("N");
		coupon.setDirectInputValue("");

		model.addAttribute("coupon", coupon);
		initFormData(model);
		
		return ViewUtils.getManagerView("/coupon/form");
		
	}
	
	/**
	 * 쿠폰 복사 생성 처리
	 * @param coupon
	 * @return
	 */
	@PostMapping("/copy-create/{couponId}")
	public String copyCreateAction(Coupon coupon) {
	
		couponService.insertCoupon(coupon);
		
		return ViewUtils.redirect("/opmanager/coupon/list", MessageUtils.getMessage("M00288")); // 등록 되었습니다. 
		
	}

	/**
	 * 목록데이터 수정 - 선택삭제
	 * @param requestContext
	 * @param couponListParam
	 * @return
	 */
	@PostMapping("list/delete")
	public JsonView deleteListData(RequestContext requestContext, CouponListParam couponListParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		couponService.deleteListData(couponListParam);
		return JsonViewUtils.success();  
	}

	/**
	 * 쿠폰 발행
	 * @param requestContext
	 * @param couponId
	 * @return
	 */
	@ PostMapping(value="/publish/{couponId}")
	public JsonView couponPublish(RequestContext requestContext, @PathVariable("couponId") int couponId) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		Coupon coupon = couponService.getCouponById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		coupon.setUpdateUserName(UserUtils.getManagerName());
		couponService.updateCouponPublish(coupon);
		return JsonViewUtils.success();  
	}

	/**
	 * 쿠폰 다운로드 상태 변경
	 * @param requestContext
	 * @param couponId
	 * @return
	 */
	@ PostMapping(value="/download-{mode}/{couponId}")
	public JsonView couponDownloadChange(RequestContext requestContext, 
			@PathVariable("couponId") int couponId, @PathVariable("mode") String mode) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		Coupon coupon = couponService.getCouponById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		coupon.setUpdateUserName(UserUtils.getManagerName());
		couponService.updateCouponDownloadStatus(coupon, mode);
		return JsonViewUtils.success();  
	}
	
	/**
	 * 회원 검색 팝업
	 * @param model
	 * @param coupon
	 * @return
	 */
	@GetMapping("/find-user")
	@RequestProperty(layout="base")
	public String findUser(Model model, Coupon coupon){

		List<CouponTargetUser> array = new ArrayList<>();
		
		CouponTargetUser param = coupon.getSearchCouponTargetUser();
		if (param == null) {
			param = new CouponTargetUser();
		}
		
		param.setAddType("search");
		array.add(param);
		coupon.setCouponTargetUsers(array);
		
		int totalCount = couponService.getCouponTargetUserCountByCoupon(coupon);
		
		Pagination pagination = Pagination.getInstance(totalCount, 10);
		coupon.setPagination(pagination);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", couponService.getCouponTargetUserListByCoupon(coupon));

		return ViewUtils.view();
	}
	
	/**
	 * 선택회원 미리보기
	 * @return
	 */
	@GetMapping("/target-user-preview")
	@RequestProperty(layout="base")
	public String targetUserPreview(Coupon coupon, Model model) {
		
		if (coupon.getCouponTargetUsers() == null) {
			throw new PageNotFoundException();
		}
		
		int totalCount = couponService.getCouponTargetUserCountByCoupon(coupon);
		
		Pagination pagination = Pagination.getInstance(totalCount, 10);
		pagination.setLink("javascript:paging([page])");
		coupon.setPagination(pagination);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("coupon", coupon);
		model.addAttribute("list", couponService.getCouponTargetUserListByCoupon(coupon));

		return ViewUtils.view();
	}
	
	/**
	 * 상품 검색 팝업
	 * @param model
	 * @param coupon
	 * @return
	 */
	@GetMapping("/find-item")
	@RequestProperty(layout="base")
	public String findItem(Model model, Coupon coupon) {
		
		List<CouponTargetItem> array = new ArrayList<>();
		
		CouponTargetItem param = coupon.getSearchCouponTargetItem();
		if (param == null) {
			param = new CouponTargetItem();
		}
		
		param.setAddType("search");
		array.add(param);
		coupon.setCouponTargetItems(array);
		
		int totalCount = couponService.getCouponTargetItemCountByCoupon(coupon);
		
		Pagination pagination = Pagination.getInstance(totalCount, 10);
		coupon.setPagination(pagination);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", couponService.getCouponTargetItemListByCoupon(coupon));
		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		
		return ViewUtils.view();
	}
	
	/**
	 * 선택상품 미리보기
	 * @return
	 */
	@GetMapping("/target-item-preview")
	@RequestProperty(layout="base")
	public String targetItemPreview(Coupon coupon, Model model) {
		
		if (coupon.getCouponTargetItems() == null) {
			throw new PageNotFoundException();
		}
		
		int totalCount = couponService.getCouponTargetItemCountByCoupon(coupon);
		
		Pagination pagination = Pagination.getInstance(totalCount, 10);
		coupon.setPagination(pagination);

		model.addAttribute("coupon", coupon);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", couponService.getCouponTargetItemListByCoupon(coupon));

		return ViewUtils.view();
	}

	/**
	 * 엑셀 업로드
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("/item-upload-excel")
	public String uploadExcel(Model model) {

		return ViewUtils.view();
	}

	/**
	 * 엑셀 업로드 처리.
	 * @param multipartFile
	 * @Param chosenItemSize
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="blank")
	@PostMapping("/item-upload-excel")
	public String uploadExcelProcess(@RequestParam(value="file", required=false) MultipartFile multipartFile,
												  @RequestParam(value="chosenItemSize", required=false) int chosenItemSize, Model model) {

		try {
			List<ItemBase> couponItems = couponService.insertItemExcelData(multipartFile);
			model.addAttribute("items", couponItems);
			model.addAttribute("size", chosenItemSize);
		} catch (Exception e) {
			return e.getMessage();
		}

		return ViewUtils.getView("/coupon/chosen-item");
	}

	/**
	 * 엑셀 샘플 다운로드
	 * @return
	 */
	@RequestProperty(layout="base")
	@PostMapping("/sample-download-excel")
	public ModelAndView downloadExcelProcess() {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		// Excel
		ModelAndView mav = new ModelAndView(new CouponItemExcelView());

		return mav;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(int.class, "couponDiscountLimitPrice", new CouponDiscountLimitPricePropertyEditor());
		dataBinder.registerCustomEditor(int.class, "couponDownloadLimit", new CouponDiscountLimitPricePropertyEditor());
		dataBinder.registerCustomEditor(int.class, "couponDownloadUserLimit", new CouponDiscountLimitPricePropertyEditor());
		dataBinder.registerCustomEditor(int.class, "couponPayRestriction", new CouponPayRestrictionPropertyEditor());
	}

	/**
	 * 캠페인 쿠폰 리스트 (팝업)
	 * @param model
	 * @param couponParam
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping({"/campaign-list", "/campaign-list/{pageType}"})
	public String campaignlist(@PathVariable(value="pageType", required=false) String pageType, Model model, CouponParam couponParam) {

//		couponParam.setConditionType("COUPON_USE_LIST");
		couponParam.setCouponFlag("Y");
		couponParam.setCampaignFlag(true);

		couponParam.setCouponTargetTimeType("1");
		couponParam.setCouponOfflineFlag("N");
		couponParam.setCouponDownloadLimit(-1); 	// 무제한
		couponParam.setCouponDownloadUserLimit(-1); // 무제한

		couponParam.setSendDate(couponParam.getSendDate()); // 예약발송 날짜
		couponParam.setSendTime(couponParam.getSendTime()); // 예약발송 시간

		if ("regular".equals(pageType)) {
			couponParam.setEndSendDate(couponParam.getEndSendDate()); // 정기발송 종료날짜
		}

		int count = couponService.getCouponCountByParamForManager(couponParam);

		Pagination pagination = Pagination.getInstance(count, 10);
		couponParam.setPagination(pagination);

		model.addAttribute("couponCount", count);
		model.addAttribute("pagination", pagination);
		model.addAttribute("couponList", couponService.getCouponListByParamForManager(couponParam));

		return ViewUtils.getManagerView("/coupon/campaign-list");

	}

	@GetMapping("/duplicate-direct-input-value")
	public JsonView duplicateDirectInputValue(RequestContext requestContext,
											  @RequestParam(name="couponId", defaultValue = "0") String couponId,
											  @RequestParam(name="value", defaultValue = "") String value) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		try {

			if (StringUtils.isEmpty(value)) {
				return JsonViewUtils.failure("직접입력 쿠폰 정보가 없습니다.");
			}

			int id = StringUtils.string2integer(couponId);

			return JsonViewUtils.success(duplicateDirectInputValue(id, value));
		} catch (Exception e) {
			return JsonViewUtils.failure("직접 입력 중복체크를 실패 했습니다.");
		}
	}

	private boolean duplicateDirectInputValue(int id, String value){

		if (id > 0) {
			// 쿠폰 수정일경우 같은 값이면 중복체크 예외
			Coupon coupon = couponService.getCouponById(id);
			if (coupon != null && value.equals(coupon.getDirectInputValue())) {
				return false;
			}
		}

		return couponService.getDirectInputValueCount(value) > 0;

	}
}
