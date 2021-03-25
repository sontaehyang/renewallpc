package saleson.shop.seo;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.domain.SearchParam;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.seo.domain.Seo;

@Controller
@RequestMapping("/opmanager/seo")
@RequestProperty(title="SEO설정", layout="default")
public class SeoManagerController {
	@Autowired
	private SeoService seoService;

	/**
	 * SEO 목록
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String list(SearchParam searchParam, Model model) {
		Pagination pagination = Pagination.getInstance(seoService.getSeoCount(searchParam));
		searchParam.setPagination(pagination);
		
		model.addAttribute("list", seoService.getSeoList(searchParam));
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("pagination", pagination);
		
		return ViewUtils.view();
	}
	
	
	@GetMapping("create")
	public String create(@ModelAttribute Seo seo) {
		return ViewUtils.view();
	}
	
	
	@PostMapping("create")
	public String createAction(Seo seo) {
		
		seoService.insertSeo(seo);
		return ViewUtils.redirect("/opmanager/seo/list", MessageUtils.getMessage("M00288"));
	}
	
	
	@GetMapping("edit/{seoId}")
	public String edit(@PathVariable("seoId") int seoId, Model model) {
		
		model.addAttribute("seo", seoService.getSeoById(seoId));
		return ViewUtils.view();
	}
	
	
	@PostMapping("edit/{seoId}")
	public String editAction(Seo seo) {
		
		seoService.updateSeo(seo);
		return ViewUtils.redirect("/opmanager/seo/list", MessageUtils.getMessage("M00289"));
	}
	
	/**
	 * 목록데이터 수정 - 선택수정
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping("list/update")
	public JsonView updateListData(RequestContext requestContext, ListParam listParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		seoService.updateListData(listParam);
		return JsonViewUtils.success();  
	}
	
	
	/**
	 * 목록데이터 수정 - 선택삭제.
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping("list/delete")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		seoService.deleteListData(listParam);
		return JsonViewUtils.success();  
	}
}
