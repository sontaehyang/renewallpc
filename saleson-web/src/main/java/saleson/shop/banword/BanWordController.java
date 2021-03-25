package saleson.shop.banword;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.BusinessException;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.SearchParam;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.UserUtils;
import saleson.shop.banword.domain.BanWord;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;

import javax.validation.Valid;

@Controller
@RequestMapping({"/ban-word", "/opmanager/ban-word"})
public class BanWordController {
	private static final Logger log = LoggerFactory.getLogger(BanWordController.class);
	@Autowired
	private BanWordService banWordService;
	
	@Autowired
	private ConfigService configService;
	
	
	
	@GetMapping("list")
	@RequestProperty(layout="default", title="금칙어관리")
	public String list(@ModelAttribute SearchParam searchParam, Model model) {
		
		Pagination pagination = Pagination.getInstance(banWordService.getBanWordCount(searchParam));
		searchParam.setPagination(pagination);
		
		model.addAttribute("list", banWordService.getBanWordList(searchParam));
		model.addAttribute("pagination", pagination);
		
		return ViewUtils.view();
	}
	
	
	@GetMapping("create")
	public String create(@Valid BanWord banWord, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return ViewUtils.redirect("/opmanager/ban-word/list", MessageUtils.getMessage("M00292"));
		}
		
		try {
			banWordService.insertBanWord(banWord);
			
		} catch (BusinessException e) {
			return ViewUtils.redirect("/opmanager/ban-word/list", e.getMessage());
		}
	
	
		
		return ViewUtils.redirect("/opmanager/ban-word/list", MessageUtils.getMessage("M00288"));
	}
	
	@PostMapping("delete/{banWordId}")
	public JsonView delete(RequestContext requestContext, @PathVariable int banWordId) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		
		BanWord banWord = banWordService.getBanWordByBanWordId(banWordId);
		
		if (banWord == null) {
			return JsonViewUtils.failure(MessageUtils.getMessage("M00293"));
		}
		
		if (!UserUtils.isSupervisor() && banWord.getUserId() != UserUtils.getManagerId()) {
			return JsonViewUtils.failure(MessageUtils.getMessage("M00294"));
		}
		
		try {
			banWordService.deleteBanWordByBanWordId(banWordId);
		} catch (Exception e) {
			return JsonViewUtils.exception(e.getMessage());
		}
	
		
		return JsonViewUtils.success();
	}
	
	
	
	@GetMapping("check")
	public JsonView check(@RequestParam("text") String text) {

		if (text == null) {
			return JsonViewUtils.failure("");
		}

		String bannedWord = banWordService.checkBanWord(text);
		
		log.debug("Banned Word : {}" , bannedWord);
		
		if (bannedWord == null) {
			return JsonViewUtils.success();       
		} else {
			return JsonViewUtils.failure(bannedWord);   
			
		}
	}
	
	
	@GetMapping("list-all")
	public JsonView listAll() {
		//List<BanWord> bannedWords = banWordService.getBanWordListAll();
		//return JsonViewUtils.success(bannedWords);       
		Config config = configService.getShopConfigCache(Config.SHOP_CONFIG_ID);
		return JsonViewUtils.success(config.getBanWords());       
	}
	
}
