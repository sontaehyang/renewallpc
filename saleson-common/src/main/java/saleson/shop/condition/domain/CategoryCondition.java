package saleson.shop.condition.domain;

import java.util.List;

import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;


/**
 * @author seungil.lee
 * @since 2017-07-05
 */

public class CategoryCondition {
	private int categoryGroupId;
	
	private CategoriesGroupForCondition categoriesGroup;
	
	private List<Condition> conditions;

	public int getCategoryGroupId() {
		return categoryGroupId;
	}

	public void setCategoryGroupId(int categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}

	public CategoriesGroupForCondition getCategoriesGroup() {
		return categoriesGroup;
	}

	public void setCategoriesGroup(CategoriesGroupForCondition categoriesGroup) {
		this.categoriesGroup = categoriesGroup;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
	
	public boolean isConditionsEmpty() {
		boolean isEmpty = false;
		if (this.conditions != null && this.conditions.size() == 1 && this.conditions.get(0).getConditionId() == 0) {
			isEmpty = true;
		}
		return isEmpty;
	}
}
