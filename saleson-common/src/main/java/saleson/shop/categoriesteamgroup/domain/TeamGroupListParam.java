package saleson.shop.categoriesteamgroup.domain;

import com.onlinepowers.framework.web.domain.ListParam;
import saleson.common.utils.CommonUtils;

public class TeamGroupListParam extends ListParam {
	private String[] ordering;

	public String[] getOrdering() {
		return CommonUtils.copy(ordering);
	}

	public void setOrdering(String[] ordering) {
		this.ordering = CommonUtils.copy(ordering);
	}
}
