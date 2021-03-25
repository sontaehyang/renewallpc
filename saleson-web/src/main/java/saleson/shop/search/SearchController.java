package saleson.shop.search;

import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.search.domain.Search;
import saleson.shop.search.support.SearchRecommendParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping({"search"})
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	/**
	 * 추천 검색어 정보 가져오기
	 * @return
	 */
	@PostMapping("")
	public JsonView search() {
		SearchRecommendParam searchRecommendParam = new SearchRecommendParam();
		searchRecommendParam.setDisplayFlag("Y");
		
		Search search = searchService.getSearchForFront(searchRecommendParam);
		
    	HashMap<String, Object> result = new HashMap<>();
    	result.put("search", search);
		
		return JsonViewUtils.success(result);
	}
	
	
}
