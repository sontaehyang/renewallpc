package saleson.shop.review;

import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.utils.ShopUtils;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Breadcrumb;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categories.support.CategoriesSearchParam;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.ItemReview;
import saleson.shop.item.support.ItemParam;

import java.util.List;

@Controller
@RequestMapping({"/product_reviews","/review"})
@RequestProperty(title="리뷰목록", layout="sub")
public class ReviewController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	CategoriesService categoriesService; 
	
	@Autowired
	CategoriesTeamGroupService categoriesTeamGroupService;
	
	@GetMapping({"/list","/recent","/recent/{categoryId}"})
	public String applyForArrival(Model model, CategoriesSearchParam categoriesSearchParam, ItemParam itemSearchParam) {
		
		ItemParam param = new ItemParam();
		
		// param.setRecommendFlag("Y");
		param.setReviewDisplayFlag("Y");
		param.setReviewScore(5);
		param.setLimit(15);

		List<ItemReview> bestReviews = itemService.getItemReviewListByParam(param);
		model.addAttribute("bestReviewsList",bestReviews);
		
		List<CategoriesTeam> categoryTeamGroupList = categoriesTeamGroupService.getCategoriesTeamGroupList();
		model.addAttribute("categoryTeamGroupList",categoryTeamGroupList);
		
		if( categoriesSearchParam.getCategoryId() != null && !categoriesSearchParam.getCategoryId().equals("") ){
			
			Categories categories = categoriesService.getCategoriesById(Integer.parseInt(categoriesSearchParam.getCategoryId()));
			ShopUtils.setSeo(categories.getReviewSeo());
			
			categoriesSearchParam.setCategoryClass1(categories.getCategoryClass1());
			
			param = new ItemParam();
			
			param.setReviewDisplayFlag("Y");
			param.setCategoryCode(categories.getCategoryUrl());
			param.setConditionType("FRONT_CATEGORY");
			
			int count = itemService.getItemReviewCountByParam(param);
			
			Pagination pagination = Pagination.getInstance(count,15);
			param.setPagination(pagination);
			
			List<ItemReview> reviews = itemService.getItemReviewListByParam(param);
			
			
		
			// 현재 경로
			List<Breadcrumb> breadcrumbs = categoriesService.getBreadcrumbListByCategoryUrl(categories.getCategoryUrl());
			
			model.addAttribute("categoryTeamCode", breadcrumbs.get(0).getTeamUrl());
			model.addAttribute("categoryGroupCode", breadcrumbs.get(0).getGroupUrl());
			model.addAttribute("categories", categories);
			model.addAttribute("pagination",pagination);
			model.addAttribute("count",count);
			model.addAttribute("reviewsList",reviews);
			
			
		} else {
		
			param = new ItemParam();
			
			param.setReviewDisplayFlag("Y");
			
			int count = itemService.getItemReviewCountByParam(param);
			
			Pagination pagination = Pagination.getInstance(count,15);
			param.setPagination(pagination);
			
			List<ItemReview> reviews = itemService.getItemReviewListByParam(param);
			
			model.addAttribute("lnbType", "review");
			model.addAttribute("pagination",pagination);
			model.addAttribute("count",count);
			model.addAttribute("reviewsList",reviews);
		}
		
		model.addAttribute("itemParam",itemSearchParam);
		model.addAttribute("categoriesSearchParam",categoriesSearchParam);
		
		return ViewUtils.getView("/review/list");
	}
	
	
}
