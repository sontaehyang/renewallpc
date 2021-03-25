package saleson.shop.statistics.domain;

import com.onlinepowers.framework.security.userdetails.User;

public class DoNotSellUser extends User{
	private String payCount;
	private String totalItemPrice;
	
	public String getPayCount() {
		return payCount;
	}
	public void setPayCount(String payCount) {
		this.payCount = payCount;
	}
	public String getTotalItemPrice() {
		return totalItemPrice;
	}
	public void setTotalItemPrice(String totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}
	
	
}
