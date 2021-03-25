package saleson.shop.ranking;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.item.domain.Item;
import saleson.shop.ranking.domain.Ranking;
import saleson.shop.ranking.support.RankingParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/opmanager/ranking")
@RequestProperty(title="랭킹관리", layout="default")
public class RankingManagerController {
	@Autowired
	public RankingService rankingService;
	
	@Autowired
	public CategoriesService categoriesService;
	
	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;
	
	/**
	 * 판매 랭킹 상품 목록
	 * @param rankingParam
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String list(@ModelAttribute RankingParam rankingParam, Model model) {
		List<Ranking> rankingList = new ArrayList<>();
		List<Item> saleRankingList = new ArrayList<>();
		List<Categories> categoryList = new ArrayList<>();
		
		if (rankingParam.hasParamValue()) {
			rankingList = rankingService.getRankingList(rankingParam);
			
			rankingParam.setLimit(100);
			saleRankingList = rankingService.getSaleRankingList(rankingParam);
			
			if (!rankingParam.getCategoryGroupCode().equals("")) {
				categoryList = categoriesService.getCategoriesListByGroupCode(rankingParam.getCategoryGroupCode());
			}
		}
		
		
		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		model.addAttribute("rankingList", rankingList);
		model.addAttribute("saleRankingList", saleRankingList);
		model.addAttribute("categoryList", categoryList);
		
		return ViewUtils.view();
	}
	
	
	@PostMapping("create")
	public JsonView create(RequestContext requestContext, RankingParam rankingParam) {
		
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}
		
		rankingService.save(rankingParam);
		
		return JsonViewUtils.success();
	}
	
}
