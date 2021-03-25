package saleson.shop.coupon;

import com.onlinepowers.framework.annotation.handler.Authorize;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.CouponParam;
import saleson.shop.qna.QnaService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/m/coupon")
@RequestProperty(template="mobile", layout="base")
public class CouponMobileController {

	@Autowired
	private QnaService qnaService; 

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

		initViewData(model, target, couponParam);
		
		return ViewUtils.getView("/coupon/applies-to");
	}

	/**
	 * 적용상품 보기 - 더보기
	 * @param model
	 * @param couponId
	 * @param target
	 * @return
	 */
	@RequestProperty(layout = "blank")
	@PostMapping("/applies-to/{couponId}/{target}/{page}")
	@Authorize("hasRole('ROLE_USER')")
	public String inqueryList(Model model, @PathVariable("couponId") int couponId,
			@PathVariable("target") String target,
			@PathVariable("page") int page,
			HttpServletResponse response) {
		
		CouponParam couponParam = new CouponParam();
		couponParam.setCouponId(couponId);
		couponParam.setPage(page);

		initViewData(model, target, couponParam);
		
		return ViewUtils.getView("/include/mypage-applies-list");
	}
	
	private void initViewData(Model model, String target, CouponParam couponParam) {
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
	}
}