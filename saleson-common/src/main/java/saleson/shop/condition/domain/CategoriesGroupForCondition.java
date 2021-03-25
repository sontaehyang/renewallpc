package saleson.shop.condition.domain;

import java.util.List;

import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;


/**
 * @author seungil.lee
 * @since 2017-07-05
 */

public class CategoriesGroupForCondition extends CategoriesGroup {
	List<CategoriesForCondition> categoriesForCondition;

	public List<CategoriesForCondition> getCategoriesForCondition() {
		return categoriesForCondition;
	}

	public void setCategoriesForCondition(List<CategoriesForCondition> categoriesForCondition) {
		this.categoriesForCondition = categoriesForCondition;
	}
}
