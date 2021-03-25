package saleson.shop.order.admin.support;

import saleson.common.utils.CommonUtils;
import saleson.shop.categories.domain.ProductsRepCategories;
import saleson.shop.item.domain.Item;
import saleson.shop.point.domain.PointPolicy;
import saleson.shop.userlevel.domain.UserLevel;

import com.onlinepowers.framework.security.userdetails.User;

public class OrderAdminData {
	
	private String workDate;
	private int workSequence;
	private int itemSequence;
	
	private long userId;
	private String orderGroupCode;
	
	private int itemId;
	private int salePrice;
	private int quantity;
	
	private String userName;
	private String phone;
	private String mobile;
	
	private String receiveName;
	private String receiveZipcode;
	private String receiveAddress;
	private String receiveAddressDetail;
	private String receivePhone;
	private String receiveMobile;
	private String content;
	
	private String[] optionIds;
	private String options;
	
	private User user;
	private Item item;
	private PointPolicy pointPolicy;
	private UserLevel userLevel;
	
	private String updateManagerName;
	
	private ProductsRepCategories productsRepCategories;
	public ProductsRepCategories getProductsRepCategories() {
		return productsRepCategories;
	}
	public void setProductsRepCategories(ProductsRepCategories productsRepCategories) {
		this.productsRepCategories = productsRepCategories;
	}
	public UserLevel getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}
	public PointPolicy getPointPolicy() {
		return pointPolicy;
	}
	public void setPointPolicy(PointPolicy pointPolicy) {
		this.pointPolicy = pointPolicy;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getOrderGroupCode() {
		return orderGroupCode;
	}
	public void setOrderGroupCode(String orderGroupCode) {
		this.orderGroupCode = orderGroupCode;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceiveZipcode() {
		return receiveZipcode;
	}
	public void setReceiveZipcode(String receiveZipcode) {
		this.receiveZipcode = receiveZipcode;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getReceiveAddressDetail() {
		return receiveAddressDetail;
	}
	public void setReceiveAddressDetail(String receiveAddressDetail) {
		this.receiveAddressDetail = receiveAddressDetail;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public String getReceiveMobile() {
		return receiveMobile;
	}
	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getOptionIds() {
		return CommonUtils.copy(optionIds);
	}
	public void setOptionIds(String[] optionIds) {
		this.optionIds = CommonUtils.copy(optionIds);
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public int getWorkSequence() {
		return workSequence;
	}
	public void setWorkSequence(int workSequence) {
		this.workSequence = workSequence;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public String getUpdateManagerName() {
		return updateManagerName;
	}
	public void setUpdateManagerName(String updateManagerName) {
		this.updateManagerName = updateManagerName;
	}
	
}
