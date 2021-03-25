package saleson.shop.brand;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
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
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.brand.domain.Brand;
import saleson.shop.brand.support.BrandParam;

import java.util.List;

@Controller
@RequestMapping("/opmanager/brand")
@RequestProperty(title="환경설정", layout="default")
public class BrandManagerController {
	private static final Logger log = LoggerFactory.getLogger(BrandManagerController.class);
	
	@Autowired
	BrandService brandService;
	
	
	@GetMapping("list")
	public String brandList(BrandParam brandParam, Model model) {
		
		int brandCount = brandService.getBrandCount(brandParam);
		
		Pagination pagination = Pagination.getInstance(brandCount);
		
		brandParam.setPagination(pagination);
		
		List<Brand> brandList = brandService.getBrandList(brandParam);
		
		model.addAttribute("brandCount", brandCount);
		model.addAttribute("brandList", brandList);
		model.addAttribute("brandParam", brandParam);
		model.addAttribute("pagination",pagination);
		return ViewUtils.view();
	}
	
	
	@GetMapping("create")
	public String brandCreate(Brand brand, Model model) {
		
		model.addAttribute("brand", brand);
		
		return ViewUtils.getManagerView("/brand/form");
	}
	
	
	@PostMapping("create")
	public String brandCreateAction(Brand brand) {
		
		brandService.insertBrand(brand);
		
		return ViewUtils.redirect("/opmanager/brand/list", MessageUtils.getMessage("M00288"));	// 등록되었습니다. 
	}
	
	
	@GetMapping("edit/{brandId}")
	public String brandEdit(@PathVariable("brandId") int brandId, Model model) {
		
		Brand brand = brandService.getBrandById(brandId);
			
		model.addAttribute("brand", brand);
		
		return ViewUtils.getManagerView("/brand/form");
	}
	
	
	@PostMapping("edit/{brandId}")
	public String brandEditAction(@PathVariable("brandId") int brandId, Brand brand) {
		
		brandService.updateBrand(brand);
		
		return ViewUtils.redirect("/opmanager/brand/list/", MessageUtils.getMessage("M00289"));	// 수정되었습니다.
	}
	
	@GetMapping("delete/{brandId}")
	public String brandDelete(@PathVariable("brandId") int brandId) {
		
		brandService.deleteBrandById(brandId);
		
		return ViewUtils.redirect("/opmanager/brand/list", MessageUtils.getMessage("M00205")); //삭제되었습니다. 
	}
	
	
	/**
	 * 팝업리스트 삭제
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping("delete-list")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		brandService.deleteBrandData(listParam);
		return JsonViewUtils.success();  
	}
}
