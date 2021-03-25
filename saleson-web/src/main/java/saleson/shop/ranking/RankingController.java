package saleson.shop.ranking;

import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.utils.ShopUtils;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categories.support.CategoriesSearchParam;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.categoriesteamgroup.support.CategoriesTeamGroupSearchParam;
import saleson.shop.item.domain.Item;
import saleson.shop.ranking.support.RankingParam;

import java.util.List;

@Controller
@RequestMapping("/ranking")
@RequestProperty(title="랭킹관리", layout="sub")
public class RankingController {
	@Autowired
	public RankingService rankingService;
	
	@Autowired
	public CategoriesService categoriesService;
	
	@Autowired
	private CategoriesTeamGroupService categoriesTeamGroupService;
	
	/**
	 * 판매 랭킹 팀 상품 목록
	 * @param rankingParam
	 * @param model
	 * @return
	 */
	@GetMapping("/cate00team_{code}")
	public String code(@PathVariable("code") String code, Model model) {
		
		CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam = new CategoriesTeamGroupSearchParam();
		categoriesTeamGroupSearchParam.setCode(code);
		
		CategoriesTeam categoriesTeam = categoriesTeamGroupService.getCategoriesTeamById(categoriesTeamGroupSearchParam);
		
		ShopUtils.setSeo(categoriesTeam.getRankSeo());
		
		RankingParam rankingParam = new RankingParam();
		rankingParam.setCategoryTeamCode(code);
		rankingParam.setLimit(50);
		
		List<Item> rankingList = rankingService.getRankingListForGroupAndCategory(rankingParam);
		
		
		//model.addAttribute("groupRankingList",rankingService.getGroupRankingList(code));
		
		model.addAttribute("name", categoriesTeam.getName());
		model.addAttribute("rankingList", rankingList);
		model.addAttribute(code,"class=\"active\"");
		model.addAttribute("code",code);
		model.addAttribute("groupCategoryList",categoriesService.getCategoryGroupRanking(code));
		
		model.addAttribute("lnbType", "review");

		//return ViewUtils.getView("/ranking/index");
		return ViewUtils.getView("/ranking/list");
	}
	
	
	/**
	 * 판매 랭킹 그룹 상품 목록
	 * @param rankingParam
	 * @param model
	 * @return
	 */
	@GetMapping("/{rankingCode}")
	public String rankingCode(@PathVariable("rankingCode") String rankingCode, Model model) {
		String code = "";
		
		List<CategoriesTeam> teamList = categoriesTeamGroupService.getCategoriesTeamList();

		if (teamList != null && rankingCode != null) {
			for (CategoriesTeam team : teamList) {
				if(rankingCode.indexOf(team.getCode()) > -1 ){
					code = team.getCode();
				}
				rankingCode = rankingCode.replaceAll("_" + team.getCode(), "");
			}
		}
		
		// 1.코드구분 ('team', 'group', 'category')
		String categoryType = categoriesService.getCategoryTypeByCategoryCode(rankingCode);
		
		System.out.println("!!!!!!!!!!!!!!! : "+categoryType);
		
		RankingParam rankingParam = new RankingParam();
		if("group".equals(categoryType)){
			
			CategoriesTeamGroupSearchParam categoriesTeamGroupSearchParam = new CategoriesTeamGroupSearchParam();
			categoriesTeamGroupSearchParam.setCode(rankingCode);
			
			CategoriesGroup categories = categoriesTeamGroupService.getCategoriesGroupById(categoriesTeamGroupSearchParam);

			ShopUtils.setSeo(categories.getRankSeo());
			
			model.addAttribute("name",categories.getName());
			
			rankingParam.setCategoryGroupCode(rankingCode);	// 그룹 코드
			
			
		} else if("category".equals(categoryType)){
			
			CategoriesSearchParam categoriesSearchParam = new CategoriesSearchParam();
			categoriesSearchParam.setCategoryUrl(rankingCode);
			
			Categories categories = categoriesService.getCategoriesByParam(categoriesSearchParam);
			
			ShopUtils.setSeo(categories.getRankSeo());
			
			model.addAttribute("name",categories.getCategoryName());
			
			rankingParam.setCategoryUrl(rankingCode);	// 카테고리 코드
			
		}
		
		rankingParam.setLimit(10);
		
		List<Item> rankingList = rankingService.getRankingListForGroupAndCategory(rankingParam);
		
		model.addAttribute("rankingList", rankingList);
		model.addAttribute("groupCategoryList",categoriesService.getCategoryGroupRanking(code));
		model.addAttribute(code,"class=\"active\"");
		model.addAttribute("code",code);
		model.addAttribute("lnbType", "review");
		
		return ViewUtils.getView("/ranking/list");
	}
	
	
}
