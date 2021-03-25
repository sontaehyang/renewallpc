package saleson.shop.notice;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.i18n.support.CodeResolver;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.seller.main.SellerService;
import saleson.seller.main.support.SellerParam;
import saleson.shop.notice.domain.Notice;
import saleson.shop.notice.support.NoticeParam;

@Controller
@RequestMapping("/opmanager/notice/**")
@RequestProperty(title="공지사항", layout="default", template="opmanager")
public class NoticeManagerController {
	private static final Logger log = LoggerFactory.getLogger(NoticeManagerController.class);

	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private CodeResolver codeResolver;
	
	@Autowired
	private SellerService sellerService;
	
	@GetMapping("list")
	public String list(@ModelAttribute("searchParam") NoticeParam searchParam , Model model) {
		
		int count = noticeService.getNoticeCount(searchParam);
		
		Pagination pagination = Pagination.getInstance(count, searchParam.getItemsPerPage());
		searchParam.setPagination(pagination);
		//List<Code> subCategoryCode = CodeUtils.getCodeList("NOTICE_SUB_CATEGORY");
		//List<Code> categoryTeamCode = CodeUtils.getCodeList("NOTICE_CATEGORY_TEAM");
		
		model.addAttribute("list", noticeService.getNoticeList(searchParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("count", count);
		//model.addAttribute("subCategoryCode", subCategoryCode);
		//model.addAttribute("categoryTeamCode", categoryTeamCode);
		
		return "view:/notice/list";
	}
	
	@GetMapping("create")
	public String create(RequestContext requestContext, Model model, Notice notice){
		
		//List<Code> subCategoryCode = CodeUtils.getCodeList("NOTICE_SUB_CATEGORY");
		//List<Code> categoryTeamCode = CodeUtils.getCodeList("NOTICE_CATEGORY_TEAM");
		
		SellerParam sellerParam = new SellerParam();
		sellerParam.setStatusCode("2");
		model.addAttribute("sellerList", sellerService.getSellerListByParam(sellerParam));
		//model.addAttribute("subCategoryCode", subCategoryCode);
		//model.addAttribute("categoryTeamCode", categoryTeamCode);
		
		return ViewUtils.view();
	}
	
	@PostMapping("create")
	public String createAction(RequestContext requestContext, Notice notice){
		
		notice.setUserName(SecurityUtils.getCurrentUser().getUserName());
		noticeService.insertNotice(notice);
		
		return ViewUtils.redirect("/opmanager/notice/list","등록되었습니다.");
	}
	
	@GetMapping("edit/{noticeId}")
	public String edit(RequestContext requestContext, Model model, @PathVariable("noticeId") int noticeId, @ModelAttribute("searchParam") NoticeParam searchParam ){
		
		//List<Code> subCategoryCode = CodeUtils.getCodeList("NOTICE_SUB_CATEGORY");
		//List<Code> categoryTeamCode = CodeUtils.getCodeList("NOTICE_CATEGORY_TEAM");
		
		SellerParam sellerParam = new SellerParam();
		sellerParam.setStatusCode("2");
		model.addAttribute("sellerList", sellerService.getSellerListByParam(sellerParam));
		model.addAttribute("notice", noticeService.getNotice(noticeId));
		model.addAttribute("noticeSellerList", noticeService.getNoticeSellerList(noticeId));
		model.addAttribute("searchParam", searchParam);
		//model.addAttribute("subCategoryCode", subCategoryCode);
		//model.addAttribute("categoryTeamCode", categoryTeamCode);
		
		return ViewUtils.view();
	}
	
	@PostMapping("edit/{noticeId}")
	public String editAction(RequestContext requestContext, Notice notice){
		
		notice.setUserName(SecurityUtils.getCurrentUser().getUserName());
		noticeService.updateNotice(notice);
		
		return ViewUtils.redirect("/opmanager/notice/list",MessageUtils.getMessage("M00289"));
	}
	
	@PostMapping("delete/{noticeId}")
	public JsonView delete(RequestContext requestContext, @PathVariable int noticeId) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		
		
		try {
			noticeService.deleteNotice(noticeId);
		} catch (Exception e) {
			return JsonViewUtils.exception(e.getMessage());
		}
	
		
		return JsonViewUtils.success();
	}
	
	
	@PostMapping("delete-notice-seller/{noticeSellerId}")
	public JsonView deleteNoticeSeller(@PathVariable("noticeSellerId") int noticeSellerId, RequestContext requestContext) {
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}

		try{
			noticeService.deleteNoticeSeller(noticeSellerId);
			return JsonViewUtils.success();
		}catch(Exception e){
			return JsonViewUtils.failure("삭제중 오류가 발생했습니다.");
		}

		
	}
}
