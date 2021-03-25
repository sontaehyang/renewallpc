package saleson.shop.group.support;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.ItemUtils;

@SuppressWarnings("serial")
public class GroupSearchParam extends SearchParam {
	private String groupCode;
	private String groupName;
	private String prefix;
	
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public String getPrefix() {
		if (StringUtils.isEmpty(prefix)) {
			prefix = ItemUtils.PREFIX_GROUP;
		}

		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
