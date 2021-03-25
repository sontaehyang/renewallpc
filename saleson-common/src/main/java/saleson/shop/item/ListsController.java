package saleson.shop.item;

import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.shop.item.domain.SearchIndexParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/lists")
@RequestProperty(title="상품관련", layout="sub")
public class ListsController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 색인 검색 - 하단 푸터에서 링크됨. (/lists/)
	 * @param model
	 * @return
	 */
	@GetMapping({"", "/"})
	@RequestProperty(title="색인검색", layout="sub")
	public String lists(SearchIndexParam searchIndexParam, Model model) {
		// 색인범위

		// 전체 index count
		List<HashMap<String, Object>> indexList = itemService.getIndexList(searchIndexParam);
				
		model.addAttribute("items", itemService.getItemIndexList(searchIndexParam));
		model.addAttribute("indexList", indexList);
		model.addAttribute("searchIndexParam", searchIndexParam);
		model.addAttribute("lnbType", "main");
		return ViewUtils.getView("/item/lists");
	}
	
	
	/**
	 * 색인 검색 - 하단 푸터에서 링크됨. (/lists/)
	 * @param model
	 * @return
	 */
	@GetMapping("/{index}")
	@RequestProperty(title="색인검색", layout="sub")
	public String listsIndex(@PathVariable("index") String index, 
			SearchIndexParam searchIndexParam, Model model) {
		
		searchIndexParam.setIndex(index);
		
		
		// 전체 index count
		List<HashMap<String, Object>> indexList = itemService.getIndexList(searchIndexParam);
		List<HashMap<String, Object>> subIndexList = itemService.getSubIndexList(searchIndexParam);
		
		model.addAttribute("items", itemService.getItemIndexList(searchIndexParam));
		model.addAttribute("indexList", indexList);
		model.addAttribute("subIndexList", subIndexList);
		model.addAttribute("searchIndexParam", searchIndexParam);
		model.addAttribute("lnbType", "main");
		return ViewUtils.getView("/item/lists");
	}
	
	
	
}
