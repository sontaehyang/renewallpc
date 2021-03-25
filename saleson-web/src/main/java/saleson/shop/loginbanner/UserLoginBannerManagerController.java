package saleson.shop.loginbanner;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;

import saleson.shop.categoriesedit.CategoriesEditService;
import saleson.shop.categoriesedit.domain.CategoriesEdit;
import saleson.shop.categoriesedit.support.CategoriesEditParam;

@Controller
@RequestMapping("/opmanager/user-login-banner")
@RequestProperty(title="로그인 배너 관리", layout="default")
public class UserLoginBannerManagerController {
	private static final Logger log = LoggerFactory.getLogger(UserLoginBannerManagerController.class);
	
	@Autowired
	CategoriesEditService categoriesEditService;
	
	/**
	 * 로그인 배너 관리
	 */
	@GetMapping("/index")
	public String loginBanner(RequestContext requestContext, CategoriesEditParam categoriesEditParam, 
			Model model){
		
		categoriesEditParam.setCode("loginWeb");
		categoriesEditParam.setEditKind("5");
		categoriesEditParam.setEditPosition("banner");
		
		CategoriesEdit loginBannerWeb = categoriesEditService.getCategoryByParam(categoriesEditParam);
		
		if (loginBannerWeb == null) {
			loginBannerWeb = new CategoriesEdit();
		}
		
		categoriesEditParam.setCode("loginMobile");
		categoriesEditParam.setEditKind("5");
		categoriesEditParam.setEditPosition("banner");
		
		CategoriesEdit loginBannerMobile = categoriesEditService.getCategoryByParam(categoriesEditParam);
		
		if (loginBannerMobile == null) {
			loginBannerMobile = new CategoriesEdit();
		}
		
		model.addAttribute("loginBannerWeb", loginBannerWeb);
		model.addAttribute("loginBannerMobile", loginBannerMobile);
		
		return ViewUtils.getView("/user-login-banner/form");
	}
	
	// 로그인 배너 저장
	@PostMapping("/loginWeb")
	public String loginWebBannerAction(CategoriesEdit categoriesEdit, HttpServletRequest request) {
		// 로그인 배너 (WEB)
		categoriesEdit.setCode("loginWeb");
		categoriesEdit.setEditKind("5");
		categoriesEdit.setEditPosition("banner");
		categoriesEdit.setEditContent("loginweb");
		
		MultipartFile file = null;
		
		if (ValidationUtils.isNotNull(categoriesEdit.getEditImages()) && categoriesEdit.getEditImages().size() > 0) {
			file = categoriesEdit.getEditImages().get(0);
		}
		
		categoriesEditService.loginBannerUpdateCategoryEdit(categoriesEdit, file);
		
		return ViewUtils.redirect("/opmanager/user-login-banner/index", MessageUtils.getMessage("M00288"));
		
	}
	
	// 로그인 배너 저장
	@PostMapping("/loginMobile")
	public String loginMobileBannerAction(CategoriesEdit categoriesEdit, HttpServletRequest request) {
		// 로그인 배너 (WEB)
		categoriesEdit.setCode("loginMobile");
		categoriesEdit.setEditKind("5");
		categoriesEdit.setEditPosition("banner");
		categoriesEdit.setEditContent("loginMobile");
		
		MultipartFile file = null;
		
		if (ValidationUtils.isNotNull(categoriesEdit.getEditImages()) && categoriesEdit.getEditImages().size() > 0) {
			file = categoriesEdit.getEditImages().get(0);
		}
		
		categoriesEditService.loginBannerUpdateCategoryEdit(categoriesEdit, file);
		
		return ViewUtils.redirect("/opmanager/user-login-banner/index", MessageUtils.getMessage("M00288"));
	}
	
	/**
	 * TOP 공통배너 이미지삭제
	 * @param config
	 * @return
	 */
	@GetMapping("imgDelete/{categoriesEditId}/{bannerType}")
	@RequestProperty(title="TOP 배너관리", layout="base")
	public String topBannerConfigImgDelete(CategoriesEdit categoriesEdit, @PathVariable("bannerType") String bannerType,
											@PathVariable("categoriesEditId") int categoriesEditId) {

		categoriesEdit.setCategoryEditId(categoriesEditId);
		categoriesEditService.deleteLoginBannerImage(categoriesEdit, bannerType);

		return ViewUtils.redirect("/opmanager/user-login-banner/index", MessageUtils.getMessage("M00538"));
	}

}
