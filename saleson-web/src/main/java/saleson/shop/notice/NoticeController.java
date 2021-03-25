package saleson.shop.notice;

import com.onlinepowers.framework.context.ThreadContext;
import com.onlinepowers.framework.i18n.support.CodeResolver;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.context.ShopContext;
import saleson.shop.notice.domain.Notice;
import saleson.shop.notice.support.NoticeParam;

import java.util.List;

@Controller
@RequestMapping({"/notice","/board"})
@RequestProperty(title="공지사항", layout="customer")
public class NoticeController {
	private static final Logger log = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private CodeResolver codeResolver;
	
	@GetMapping("list")
	public String list(@ModelAttribute("noticeParam") NoticeParam noticeParam, Model model) {
		
		noticeParam.setVisibleType(2);
		
		int count = noticeService.getFrontNoticeListCount(noticeParam);
		
		
		Pagination pagination = Pagination.getInstance(count, noticeParam.getItemsPerPage());
		noticeParam.setPagination(pagination);
		
		//List<CodeInfo> subCategoryCode = codeResolver.getCodeList("NOTICE_SUB_CATEGORY", Locale.KOREAN);
		//List<CodeInfo> categoryTeamCode = codeResolver.getCodeList("NOTICE_CATEGORY_TEAM", Locale.KOREAN);
		
		List<Notice> noticeList = noticeService.getFrontNoticeList(noticeParam);
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("count", count);
		model.addAttribute("lnbType", "notice");
		//model.addAttribute("subCategoryCode", subCategoryCode);
		//model.addAttribute("categoryTeamCode", categoryTeamCode);
		//model.addAttribute("code","'"+noticeParam.getCategoryTeam()+"'");
		//model.addAttribute(noticeParam.getCategoryTeam(),"class=\"default\"");

		return ViewUtils.getView("/notice/list");
	}
	
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

		shopContext.getSeo().setTitle(notice.getSubject() + " (" + DateUtils.formatDate(notice.getCreatedDate().substring(0, 8), "/") + ")");
		shopContext.getSeo().setThemawordTitle(notice.getSubject());
		shopContext.getSeo().setThemawordTopTitle(notice.getSubject());

		Notice beforeNotice = new Notice();
		Notice afterNotice = new Notice();
		noticeParam.setVisibleType(2);
		List<Notice> noticeList = noticeService.getFrontNoticeList(noticeParam);
		
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
		
		return ViewUtils.getView("/notice/detail");
	}
}
