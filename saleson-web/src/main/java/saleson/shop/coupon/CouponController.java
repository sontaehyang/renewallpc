package saleson.shop.coupon;

import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.CouponParam;

@Controller
@RequestMapping("/coupon")
public class CouponController {
	
	@Autowired
	private CouponService couponService;
	
	/**
	 * 적용상품 보기
	 * @param model
	 * @param couponId
	 * @param target
	 * @return
	 */
	@GetMapping("/applies-to/{couponId}/{target}")
	@RequestProperty(layout="base")
	public String appliesTo(Model model, @PathVariable("couponId") int couponId, 
			@PathVariable("target") String target) {
		
		CouponParam couponParam = new CouponParam();
		couponParam.setCouponId(couponId);

		if ("coupon-user".equals(target)) {
			
			int totalCount = couponService.getCouponAppliesItemCountParamForCouponUser(couponParam);
			
			Pagination pagination = Pagination.getInstance(totalCount, couponParam.getItemsPerPage());
			couponParam.setPagination(pagination);
			
			Coupon coupon = couponService.getCouponAppliesItemListParamForCouponUser(couponParam);
			
			model.addAttribute("coupon", coupon);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("pagination", pagination);
			
		} else {
			
			int totalCount = couponService.getCouponAppliesItemCountParamForCoupon(couponParam);
			
			Pagination pagination = Pagination.getInstance(totalCount, couponParam.getItemsPerPage());
			couponParam.setPagination(pagination);
			
			Coupon coupon = couponService.getCouponAppliesItemListParamForCoupon(couponParam);
			
			model.addAttribute("coupon", coupon);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("pagination", pagination);
		}
		
		return ViewUtils.getView("/coupon/applies-to");
	}
	
}
