package saleson.shop.search;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.search.domain.Search;
import saleson.shop.search.support.SearchRecommendParam;

import java.util.List;

@Controller
@RequestMapping("/opmanager/search")
@RequestProperty(title="검색어 관리", layout="default", template="opmanager")
public class SearchManagerController {
	
	@Autowired
	private SearchService searchService;
	
	/**
	 * 목록
	 * @param param
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String list(SearchRecommendParam searchRecommendParam, Model model) {

		int searchCount = searchService.getSearchCount(searchRecommendParam);
		searchRecommendParam.setPagination(searchCount);

		List<Search> searchList = searchService.getSearchList(searchRecommendParam);

		model.addAttribute("searchCount", searchCount);
		model.addAttribute("searchList", searchList);
		model.addAttribute("searchParam", searchRecommendParam);
		model.addAttribute("today", DateUtils.getToday(Const.DATETIME_FORMAT));
		model.addAttribute("pagination", searchRecommendParam.getPagination());

		return ViewUtils.view();
	}

	/**
	 * 등록
	 * @param search
	 * @param model
	 * @return
	 */
	@GetMapping(value = "create")
	public String create(Search search, Model model) {

		model.addAttribute("search", search);
		model.addAttribute("hours", ShopUtils.getHours());
		return ViewUtils.view();
	}

	/**
	 * 등록 처리
	 * @param search
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("create")
	public String createAction(Search search) {
		
		searchService.insertSearch(search);
		return ViewUtils.redirect("/opmanager/search/list", "처리되었습니다.");
	}

	/**
	 * 수정
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping(value = "edit/{searchId}")
	public String edit(@PathVariable("searchId") int searchId, Model model) {

		Search search = searchService.getSearchById(searchId);

		if (search == null) {
			throw new PageNotFoundException("search id : " + searchId );
		}
		
		model.addAttribute("search", search);
		model.addAttribute("hours", ShopUtils.getHours());
		return ViewUtils.view();
	}

	/**
	 * 수정 처리
	 * @param search
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("edit/{searchId}")
	public String editAction(Search search) {
		
		searchService.updateSearch(search);
		return ViewUtils.redirect("/opmanager/search/list", "수정되었습니다.");
	}
	
	/**
	 * 추천검색어 관리 선택 삭제
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@PostMapping("/checked-delete")
	public String checkedDelete(Model model, SearchRecommendParam searchRecommendParam) {
		
		searchService.deleteSearchByParam(searchRecommendParam);
		
		return ViewUtils.redirect("/opmanager/search/list", MessageUtils.getMessage("M00205")); // 삭제 되었습니다. 
		
	}
}
