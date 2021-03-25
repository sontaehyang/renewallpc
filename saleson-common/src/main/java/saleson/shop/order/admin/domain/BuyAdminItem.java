package saleson.shop.order.admin.domain;

import java.util.List;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.util.ValidationUtils;

import saleson.common.utils.ShopUtils;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.order.admin.support.OrderAdminData;
import saleson.shop.point.domain.PointPolicy;
import saleson.shop.userlevel.domain.UserLevel;

public class BuyAdminItem {

	public BuyAdminItem() {}
	public BuyAdminItem(OrderAdminData data) {
		setItem(data.getItem());
		setSalePrice(data.getSalePrice());
		setQuantity(data.getQuantity());
		
		if (StringUtils.isEmpty(data.getOptions())) {
			setOptionList(ShopUtils.getRequiredItemOptions(data.getItem(), data.getOptions()));
		}
		
		setOptions(data.getOptions());
		
		setPointPolicy(data.getPointPolicy());
		setUserLevel(data.getUserLevel());
		setDeviceType("ADMIN");
		
		setUserId(data.getUserId());
		setGuestFlag(data.getUserId() == 0 ? "Y" : "N");
		
		if (ValidationUtils.isNull(data.getProductsRepCategories()) == false) {
			setCategoryTeamId(data.getProductsRepCategories().getCategoryTeamId());
			setCategoryGroupId(data.getProductsRepCategories().getCategoryGroupId());
			setCategoryId(data.getProductsRepCategories().getCategoryId());
		}
		
	}
	
	
	private String options;
	private String deviceType;
	private Item item;
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	
	private int shippingSequence;
	private int shippingInfoSequence;
	
	private int salePrice;
	private int quantity;
	
	private String additionItemFlag;
	private int parentItemSequence;
	private long userId;
	private String guestFlag;
	
	private int categoryTeamId;
	private int categoryGroupId;
	private int categoryId;
	
	private BuyAdminItemPrice itemPrice;
	
	private List<ItemOption> optionList;
	
	// 매출 기준일
	private String revenueSalesStatus;
	private String salesDate;
	private String payDate;
	
	
	// 구매시 적립금
	private PointPolicy pointPolicy;
	// 회원 등급
	private UserLevel userLevel;
	
	public UserLevel getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}
	public List<ItemOption> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<ItemOption> optionList) {
		this.optionList = optionList;
	}

	public BuyAdminItemPrice getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BuyAdminItemPrice itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	private String shippingPaymentType;

	public String getShippingPaymentType() {
		return shippingPaymentType;
	}

	public void setShippingPaymentType(String shippingPaymentType) {
		this.shippingPaymentType = shippingPaymentType;
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

	public int getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}

	public PointPolicy getPointPolicy() {
		return pointPolicy;
	}

	public void setPointPolicy(PointPolicy pointPolicy) {
		this.pointPolicy = pointPolicy;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getShippingSequence() {
		return shippingSequence;
	}
	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
	}
	public int getShippingInfoSequence() {
		return shippingInfoSequence;
	}
	public void setShippingInfoSequence(int shippingInfoSequence) {
		this.shippingInfoSequence = shippingInfoSequence;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getAdditionItemFlag() {
		return additionItemFlag;
	}
	public void setAdditionItemFlag(String additionItemFlag) {
		this.additionItemFlag = additionItemFlag;
	}
	public int getParentItemSequence() {
		return parentItemSequence;
	}
	public void setParentItemSequence(int parentItemSequence) {
		this.parentItemSequence = parentItemSequence;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getGuestFlag() {
		return guestFlag;
	}
	public void setGuestFlag(String guestFlag) {
		this.guestFlag = guestFlag;
	}
	public int getCategoryTeamId() {
		return categoryTeamId;
	}
	public void setCategoryTeamId(int categoryTeamId) {
		this.categoryTeamId = categoryTeamId;
	}
	public int getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(int categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	public String getRevenueSalesStatus() {
		return revenueSalesStatus;
	}
	public void setRevenueSalesStatus(String revenueSalesStatus) {
		this.revenueSalesStatus = revenueSalesStatus;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	
}
