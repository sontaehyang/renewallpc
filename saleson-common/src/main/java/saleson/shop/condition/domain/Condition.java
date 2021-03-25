package saleson.shop.condition.domain;

import java.util.List;

/**
 * @author seungil.lee
 * @since 2017-07-05
 */

public class Condition {
	// CONDITION_ID
	private int conditionId;
	
	// CATEGORY_CODE
	private String categoryCode;
	
	// CONDITION_TITLE
	private String conditionTitle;
	
	// USE_YN 
	private String useYn;
	
	// UPDATED_DATE
	private String updatedDate;
	
	// CREATED_DATE
	private String createdDate;
	
	// List<ConditionDetail>
	private List<ConditionDetail> details;
	
	public int getConditionId() {
		return conditionId;
	}

	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getConditionTitle() {
		return conditionTitle;
	}

	public void setConditionTitle(String conditionTitle) {
		this.conditionTitle = conditionTitle;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<ConditionDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ConditionDetail> details) {
		this.details = details;
	}
	
	public boolean isConditionEmpty() {
		return this.conditionId == 0 ? true : false;
	}
	
	public boolean isDetailsEmpty() {
		boolean isEmpty = false;
		if (this.details == null || this.details.isEmpty()) {
			isEmpty = true;
		}
		else if (this.details.size() == 1 && this.details.get(0).getDetailId() == 0) {
			isEmpty = true;
		}
		return isEmpty;
	}
}
