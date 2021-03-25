package saleson.shop.condition.support;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.web.domain.SearchParam;

/**
 * @author seungil.lee
 * @since 2017-07-05
 */

public class ConditionParam extends SearchParam {
	private int conditionId;
	
	private String categoryCode;
	
	private String useYn = "Y";
	
	private String detailIds;

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

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getDetailIds() {
		return detailIds;
	}

	public void setDetailIds(String detailIds) {
		this.detailIds = detailIds;
	}
	
	public String[] getDetailIdArray() {
		String[] result = StringUtils.delimitedListToStringArray(this.detailIds, ",");
		return result;
	}
}