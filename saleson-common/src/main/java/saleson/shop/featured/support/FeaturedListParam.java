package saleson.shop.featured.support;

import com.onlinepowers.framework.web.domain.ListParam;
import saleson.common.utils.CommonUtils;


public class FeaturedListParam extends ListParam {
	
	private String[] featuredIds;
	
	private String featuredCode;
	private int startOrdering;

	public String[] getFeaturedIds() {
		return CommonUtils.copy(featuredIds);
	}

	public void setFeaturedIds(String[] featuredIds) {
		this.featuredIds = CommonUtils.copy(featuredIds);
	}
	
	public String getFeaturedCode() {
		return featuredCode;
	}

	public void setFeaturedCode(String featuredCode) {
		this.featuredCode = featuredCode;
	}

	public int getStartOrdering() {
		return startOrdering;
	}

	public void setStartOrdering(int startOrdering) {
		this.startOrdering = startOrdering;
	}
	
}

