package saleson.shop.cart.support;

import java.util.List;

import saleson.common.utils.UserUtils;
import saleson.shop.user.domain.UserDetail;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.domain.ListParam;

public class CartParam extends ListParam {
	private String sessionId;
	private long userId;
	private int cartId;
	private int quantity;
	private List<Integer> cartIds;
	private List<Integer> itemOptionIds;
	private int itemId;
	private List<Integer> itemIds;
	private String shippingGroupCode;
	private String shippingPaymentType;
	private String campaignCode;

	private String additionItemFlag;
	
	public List<Integer> getItemIds() {
		return itemIds;
	}
	public void setItemIds(List<Integer> itemIds) {
		this.itemIds = itemIds;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public List<Integer> getItemOptionIds() {
		return itemOptionIds;
	}
	public void setItemOptionIds(List<Integer> itemOptionIds) {
		this.itemOptionIds = itemOptionIds;
	}
	public List<Integer> getCartIds() {
		return cartIds;
	}
	public void setCartIds(List<Integer> cartIds) {
		this.cartIds = cartIds;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public boolean getIsLogin() {
		
		if (this.userId > 0) {
			return true;
		}
		
		return UserUtils.isUserLogin();
	}
	
	public String getItemType() {
		
		String businessFlag = "N";
		/*if (this.userId > 0) {
			if (UserUtils.isUserLogin()) {
				businessFlag = UserUtils.getUserDetail().getBusinessFlag();
			}
		}*/
		
		if (StringUtils.isEmpty(businessFlag)) {
			businessFlag = "N";
		}
		
		return "N".equals(businessFlag) ? "1" : "";
	}
	
	public String getShippingGroupCode() {
		return shippingGroupCode;
	}
	public void setShippingGroupCode(String shippingGroupCode) {
		this.shippingGroupCode = shippingGroupCode;
	}
	public String getShippingPaymentType() {
		return shippingPaymentType;
	}
	public void setShippingPaymentType(String shippingPaymentType) {
		this.shippingPaymentType = shippingPaymentType;
	}
	public String getCampaignCode() {
		return campaignCode;
	}
	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	public String getAdditionItemFlag() {
		return additionItemFlag;
	}

	public void setAdditionItemFlag(String additionItemFlag) {
		this.additionItemFlag = additionItemFlag;
	}
}
