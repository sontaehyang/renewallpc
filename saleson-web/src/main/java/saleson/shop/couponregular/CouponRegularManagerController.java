package saleson.shop.couponregular;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.UserUtils;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.regular.CouponRegularService;
import saleson.shop.coupon.support.CouponDiscountLimitPricePropertyEditor;
import saleson.shop.coupon.support.CouponListParam;
import saleson.shop.coupon.support.CouponParam;
import saleson.shop.coupon.support.CouponPayRestrictionPropertyEditor;
import saleson.shop.group.GroupService;
import saleson.shop.item.ItemService;
import saleson.shop.user.UserService;
import saleson.shop.userlevel.UserLevelService;

@Controller
@RequestMapping("/opmanager/coupon-regular")
@RequestProperty(title="정기발행쿠폰 관리", layout="default")
public class CouponRegularManagerController {
	
	@Autowired
	private CouponRegularService couponRegularService;
	
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

		int count = couponRegularService.getCouponRegularListCount(couponParam);
		
		Pagination pagination = Pagination.getInstance(count, 10);
		couponParam.setPagination(pagination);

		model.addAttribute("couponCount", count);
		model.addAttribute("pagination", pagination);
		model.addAttribute("couponList", couponRegularService.getCouponRegularList(couponParam));
		
		return ViewUtils.view();
		
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
	 * @param model
	 * @param coupon
	 * @return
	 */
	@PostMapping("/create")
	public String createAction(Model model, Coupon coupon) {
		
		couponRegularService.insertCouponRegular(coupon);
		
		return ViewUtils.redirect("/opmanager/coupon-regular/list", MessageUtils.getMessage("M00288")); // 등록 되었습니다. 
		
	}
	
	/**
	 * 쿠폰 수정 - View
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{couponId}")
	public String edit(@PathVariable("couponId") int couponId, Model model) {
		
		Coupon coupon = couponRegularService.getCouponRegularById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		
		if (!coupon.getCouponIssueStartDate().isEmpty()) {
			coupon.setCouponIssueStartDate(coupon.getCouponIssueStartDate().substring(0, 8));
			coupon.setCouponIssueEndDate(coupon.getCouponIssueEndDate().substring(0, 8));
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

		couponRegularService.updateCouponRegular(coupon);
		return ViewUtils.redirect("/opmanager/coupon-regular/list", MessageUtils.getMessage("M00289")); 	// 수정 되었습니다. 
		
	}
	
	/**
	 * 쿠폰 복사 생성
	 * @param couponId
	 * @param model
	 * @return
	 */
	@GetMapping("/copy-create/{couponId}")
	public String copyCreate(@PathVariable("couponId") int couponId, Model model) {
		
		Coupon coupon = couponRegularService.getCouponRegularById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		coupon.setCouponId(0);
		if (!coupon.getCouponIssueStartDate().isEmpty()) {
			coupon.setCouponIssueStartDate(coupon.getCouponIssueStartDate().substring(0, 8));
			coupon.setCouponIssueEndDate(coupon.getCouponIssueEndDate().substring(0, 8));
		}
		coupon.setDataStatusCode("0");
		model.addAttribute("coupon", coupon);
		initFormData(model);
		
		return ViewUtils.getManagerView("/coupon-regular/form");
		
	}
	
	/**
	 * 쿠폰 복사 생성 처리
	 * @param model
	 * @param coupon
	 * @return
	 */
	@PostMapping("/copy-create/{couponId}")
	public String copyCreateAction(Model model, Coupon coupon) {
	
		couponRegularService.insertCouponRegular(coupon);
		
		return ViewUtils.redirect("/opmanager/coupon-regular/list", MessageUtils.getMessage("M00288")); // 등록 되었습니다. 
		
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
		
		couponRegularService.deleteListData(couponListParam);
		return JsonViewUtils.success();  
	}

	/**
	 * 쿠폰 발행
	 * @param requestContext
	 * @param couponId
	 * @return
	 */
	@PostMapping(value="/publish/{couponId}")
	public JsonView couponPublish(RequestContext requestContext, @PathVariable("couponId") int couponId) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		Coupon coupon = couponRegularService.getCouponRegularById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		coupon.setUpdateUserName(UserUtils.getManagerName());
		couponRegularService.updateCouponPublish(coupon);
		return JsonViewUtils.success();  
	}
	
	/**
	 * 쿠폰 다운로드 상태 변경
	 * @param requestContext
	 * @param couponId
	 * @return
	 */
	@PostMapping(value="/download-{mode}/{couponId}")
	public JsonView couponDownloadChange(RequestContext requestContext, 
			@PathVariable("couponId") int couponId, @PathVariable("mode") String mode) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		Coupon coupon = couponRegularService.getCouponRegularById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		coupon.setUpdateUserName(UserUtils.getManagerName());
		couponRegularService.updateCouponDownloadStatus(coupon, mode);
		return JsonViewUtils.success();  
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(int.class, "couponDiscountLimitPrice", new CouponDiscountLimitPricePropertyEditor());
		dataBinder.registerCustomEditor(int.class, "couponDownloadLimit", new CouponDiscountLimitPricePropertyEditor());
		dataBinder.registerCustomEditor(int.class, "couponDownloadUserLimit", new CouponDiscountLimitPricePropertyEditor());
		dataBinder.registerCustomEditor(int.class, "couponPayRestriction", new CouponPayRestrictionPropertyEditor());
	}
	
	
}
