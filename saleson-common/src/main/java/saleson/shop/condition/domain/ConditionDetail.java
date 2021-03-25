package saleson.shop.condition.domain;

/**
 * @author seungil.lee
 * @since 2017-07-05
 */

public class ConditionDetail {
	// DETAIL_ID 
	private int detailId;
	
	// CONDITION_ID
	private int conditionId;
	
	// DETAIL_TITLE
	private String detailTitle;
	
	// USE_YN
	private String useYn;
	
	// ORDERING
	private int ordering;
	
	// UPDATED_DATE
	private String updatedDate;
	
	// CREATED_DATE
	private String createdDate;

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public int getConditionId() {
		return conditionId;
	}

	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}

	public String getDetailTitle() {
		return detailTitle;
	}

	public void setDetailTitle(String detailTitle) {
		this.detailTitle = detailTitle;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
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
	
	public boolean isDetailEmpty() {
		return this.detailId == 0 ? true : false;
	}
}
