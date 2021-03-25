package saleson.shop.categoriesteamgroup.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;

public class CategoriesTeamGroupUtils {
	
	public static HashMap<String,String> getCategoriesTeamListToHaspMap(List<CategoriesTeam> categoriesList){
		
		HashMap<String,String> map = new HashMap<>();
		for (CategoriesTeam categoriesTeam : categoriesList) {
			map.put(""+categoriesTeam.getCategoryTeamId(), categoriesTeam.getName());
		}
		
		return map;
		
	}
}
