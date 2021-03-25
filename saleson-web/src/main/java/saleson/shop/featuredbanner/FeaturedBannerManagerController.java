package saleson.shop.featuredbanner;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.featuredbanner.domain.FeaturedBanner;

@Controller
@RequestMapping("/opmanager/categories-edit/featured-banner")
@RequestProperty(title="기획전 배너관리", layout="base")
public class FeaturedBannerManagerController {
	private static final Logger log = LoggerFactory.getLogger(FeaturedBannerManagerController.class);

	@Autowired
	FeaturedBannerService featuredBannerService;
	
	/**
	 * 기획전 배너조회
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String bannerList(Model model) {
		
		model.addAttribute("featuredBanner", featuredBannerService.getFeaturedBanner(1));
		
		return ViewUtils.getView("/featured-banner/list");
	}
	
	/**
	 * 기획전 배너수정
	 * @param featuredBanner
	 * @return
	 */
	@PostMapping("edit")
	public String bannerEdit(FeaturedBanner featuredBanner) {
		
		for (String imageName : featuredBanner.getDeleteImage()) {
			featuredBannerService.deleteBannerImage(imageName);
		}
		
		featuredBannerService.updateFeaturedBanner(featuredBanner);
		
		return ViewUtils.redirect("/opmanager/categories-edit/featured-banner/list", MessageUtils.getMessage("M00289"));
	}
	
	/**
	 * 기획전 이미지삭제
	 * @param imageName
	 * @return
	 */
	@PostMapping("deleteImage/{imageName}")
	public String deleteBannerImage(@PathVariable("imageName") String imageName) {
		
		featuredBannerService.deleteBannerImage(imageName);
		
		// 이미지파일이 삭제되었습니다.
		return ViewUtils.redirect("/opmanager/categories-edit/featured-banner/list", MessageUtils.getMessage("M00538"));
	}
	
}	
