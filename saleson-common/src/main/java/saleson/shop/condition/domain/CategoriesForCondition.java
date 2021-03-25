package saleson.shop.condition.domain;

import java.util.List;

import saleson.shop.categories.domain.Categories;


/**
 * @author seungil.lee
 * @since 2017-07-05
 */

public class CategoriesForCondition extends Categories {
	private List<Condition> conditions;
	
	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public boolean isConditionsEmpty() {
		boolean isEmpty = false;
		if (this.conditions == null || this.conditions.isEmpty()) {
			isEmpty = true;
		}
		else if (this.conditions.size() == 1 && this.conditions.get(0).getConditionId() == 0) {
			isEmpty = true;
		}
		return isEmpty;
	}
}
