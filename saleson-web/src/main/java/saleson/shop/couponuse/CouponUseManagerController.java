package saleson.shop.couponuse;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.CouponParam;
import saleson.shop.item.ItemService;
import saleson.shop.user.UserService;
import saleson.shop.userlevel.UserLevelService;

@Controller
@RequestMapping("/opmanager/coupon-use")
@RequestProperty(title="캘린더 관리", layout="default")
public class CouponUseManagerController {
	
	@Autowired
	CouponService couponService; 
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserLevelService userLevelService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired 
	CategoriesTeamGroupService categoriesTeamGroupService;
	
	/**
	 * 쿠폰 사용 내역
	 * @param model
	 * @param couponParam
	 * @param coupon
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model, CouponParam couponParam, Coupon coupon) {
		
		couponParam.setConditionType("COUPON_USE_LIST");
		int count = couponService.getCouponCountByParamForManager(couponParam);
		
		Pagination pagination = Pagination.getInstance(count, 10);
		couponParam.setPagination(pagination);
		
		
		model.addAttribute("couponCount",count);
		model.addAttribute("pagination", pagination);
		model.addAttribute("couponList", couponService.getCouponListByParamForManager(couponParam));
		
		return ViewUtils.view();
		
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/detail/{couponId}")
	public String detail(Model model, CouponParam couponParam, @PathVariable("couponId") int couponId) {
		
		Coupon coupon = couponService.getCouponById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		
		model.addAttribute("coupon", coupon);
		
		couponParam.setCouponId(couponId);
		int count = couponService.getCouponUserCountByParamForManager(couponParam);
		
		Pagination pagination = Pagination.getInstance(count, 10);
		couponParam.setPagination(pagination);
		
		model.addAttribute("couponCount", count);
		model.addAttribute("pagination", pagination);
		model.addAttribute("userCouponList", couponService.getCouponUserListByParamForManager(couponParam));
		
		return ViewUtils.view();
		
	}
	
	@GetMapping("/popup/target-item/{couponId}")
	@RequestProperty(layout="base")
	public String targetItem(Model model, @PathVariable("couponId") int couponId) {
		
		Coupon coupon = couponService.getCouponById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		
		if (coupon.getCouponTargetItems() == null) {
			throw new PageNotFoundException();
		}
		
		int totalCount = couponService.getCouponTargetItemCountByCoupon(coupon);
		
		Pagination pagination = Pagination.getInstance(totalCount, 10);
		coupon.setPagination(pagination);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", couponService.getCouponTargetItemListByCoupon(coupon));
		
		return ViewUtils.getView("/coupon-use/popup/target-item");
	}
	
	@GetMapping("/popup/target-user/{couponId}")
	@RequestProperty(layout="base")
	public String targetUser(Model model, @PathVariable("couponId") int couponId) {
		
		Coupon coupon = couponService.getCouponById(couponId);
		if (coupon == null) {
			throw new PageNotFoundException();
		}
		
		if (coupon.getCouponTargetUsers() == null) {
			throw new PageNotFoundException();
		}
		
		int totalCount = couponService.getCouponTargetUserCountByCoupon(coupon);
		
		Pagination pagination = Pagination.getInstance(totalCount, 10);
		coupon.setPagination(pagination);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", couponService.getCouponTargetUserListByCoupon(coupon));

		return ViewUtils.getView("/coupon-use/popup/target-user");
	}
	
	
}
