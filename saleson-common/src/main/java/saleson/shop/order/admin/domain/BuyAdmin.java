package saleson.shop.order.admin.domain;

import java.util.List;

import saleson.shop.order.admin.support.OrderAdminData;

import com.onlinepowers.framework.security.userdetails.User;

public class BuyAdmin {
	
	public BuyAdmin() {}
	public BuyAdmin(OrderAdminData data) {
		
		setUserId(data.getUserId());
		setOrderSequence(0);

		User user = data.getUser();
		if (user != null) {
			setUserId(user.getUserId());
			setLoginId(user.getLoginId());
		}
		
		setOrderGroupCode(data.getOrderGroupCode());
	}
	
	private String orderGroupCode;
	public String getOrderGroupCode() {
		return orderGroupCode;
	}
	public void setOrderGroupCode(String orderGroupCode) {
		this.orderGroupCode = orderGroupCode;
	}

	private String orderCode;
	private int orderSequence;
	private String loginId;
	private long userId;
	private int orderTotalAmount;
	private int payAmount;
	private String ip;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	private BuyAdminBuyer buyer;
	
	private BuyAdminOrderPrice orderPrice;
	public BuyAdminOrderPrice getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BuyAdminOrderPrice orderPrice) {
		this.orderPrice = orderPrice;
	}

	// 배송지
	private List<BuyAdminReceiver> receivers;

	private List<OrderAdminData> datas;
	public List<OrderAdminData> getDatas() {
		return datas;
	}
	public void setDatas(List<OrderAdminData> datas) {
		this.datas = datas;
	}
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(int orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

	public BuyAdminBuyer getBuyer() {
		return buyer;
	}

	public void setBuyer(BuyAdminBuyer buyer) {
		this.buyer = buyer;
	}

	public List<BuyAdminReceiver> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<BuyAdminReceiver> receivers) {
		this.receivers = receivers;
	}
	
}
