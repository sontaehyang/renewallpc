package saleson.shop.notice;

import com.onlinepowers.framework.context.ThreadContext;
import com.onlinepowers.framework.i18n.support.CodeResolver;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.context.ShopContext;
import saleson.common.utils.SellerUtils;
import saleson.shop.notice.domain.Notice;
import saleson.shop.notice.support.NoticeParam;
import saleson.shop.seo.domain.Seo;

import java.util.List;

@Controller
@RequestMapping("/seller/notice")
@RequestProperty(title="공지사항", layout="default", template="seller")
public class SellerNoticeController extends NoticeManagerController{
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private CodeResolver codeResolver;
	
	@GetMapping("view/{noticeId}")
	public String view(@PathVariable("noticeId") int noticeId, Model model, NoticeParam noticeParam) {
		
		noticeService.addHitCount(noticeId);
		Notice notice = noticeService.getNotice(noticeId);
		
		String[] codes = StringUtils.delimitedListToStringArray(notice.getCategoryTeam(),"|");

		String code = "";  
		
		for (int i = 0; i < codes.length; i++) {
			if(i == 0){
				code += "'"+codes[i]+"'";
			} else {
				code += ", '"+codes[i]+"'"; 
			}
		}
		
		
		if(code.equals("'top'")){
			code = "'esthetic','hair','nail','matsuge_extension'";
		}
		
		//List<CodeInfo> subCategoryCode = codeResolver.getCodeList("NOTICE_SUB_CATEGORY", Locale.KOREAN);
		//List<CodeInfo> categoryTeamCode = codeResolver.getCodeList("NOTICE_CATEGORY_TEAM", Locale.KOREAN);
		
		// SEO TITLE에 제목 + 날짜
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);
		
		//2017.04.24 Jun-Eu Son
		shopContext.setSeo(new Seo());
		
		shopContext.getSeo().setTitle(notice.getSubject() + " (" + DateUtils.formatDate(notice.getCreatedDate().substring(0, 8), "/") + ")");		
		shopContext.getSeo().setThemawordTitle(notice.getSubject());
		shopContext.getSeo().setThemawordTopTitle(notice.getSubject());

		Notice beforeNotice = new Notice();
		Notice afterNotice = new Notice();
		
		noticeParam.setVisibleType(3);
		List<Notice> noticeList = noticeService.getNoticeList(noticeParam);
		
		for (int i = 0; i < noticeList.size(); i++) {
			if (noticeList.get(i).getNoticeId() == notice.getNoticeId()) {
				if (i - 1 > -1) {
					beforeNotice = noticeService.getNotice(noticeList.get(i - 1).getNoticeId());
				} else {
					beforeNotice.setNoticeId(0);
				}
				
				if (i + 1 < noticeList.size()) {
					afterNotice = noticeService.getNotice(noticeList.get(i + 1).getNoticeId());
				} else {
					afterNotice.setNoticeId(0);
				}
				break;
			}
		}
		
		model.addAttribute("beforeNotice", beforeNotice);
		model.addAttribute("afterNotice", afterNotice);
		model.addAttribute("noticeTitle", notice.getSubject());
		//model.addAttribute("subCategoryCode", subCategoryCode);
		//model.addAttribute("categoryTeamCode", categoryTeamCode);
		model.addAttribute("notice",notice);
		model.addAttribute("code",code);
		model.addAttribute("noticeParam", noticeParam);
		
		return ViewUtils.getView("/notice/detail");
	}
	
	@GetMapping("list")
	public String list(@ModelAttribute("searchParam") NoticeParam searchParam , Model model) {
		
		searchParam.setVisibleType(3);
		searchParam.setSellerId(SellerUtils.getSellerId());
		return super.list(searchParam, model);
	}
	
}
