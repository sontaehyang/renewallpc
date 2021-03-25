package saleson.shop.userdelivery.support;

import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;

public class UserDeliveryParam extends SearchParam {
	private long userId;
	private int userDeliveryId;

	private String mode;

	private String[] id;

	public String[] getId() {
		return CommonUtils.copy(id);
	}

	public void setId(String[] id) {
		this.id = CommonUtils.copy(id);
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getUserDeliveryId() {
		return userDeliveryId;
	}
	public void setUserDeliveryId(int userDeliveryId) {
		this.userDeliveryId = userDeliveryId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
