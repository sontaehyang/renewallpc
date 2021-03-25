package saleson.shop.order.domain;

import java.util.List;

import saleson.common.utils.CommonUtils;
import saleson.shop.item.domain.Item;

public class EditOrderItem {
	
	private String mode;
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	private int itemId;
	private Item item;
	// [SKC임시]private Delivery delivery;
	// [SKC임시]private DeliveryCharge deliveryCharge;
	
	
	private int orderId;
	private List<String> useCouponKeys;
	private int[] userOrderItemSequences;
	private int[] orderItemIds;
	private int[] itemIds;
	private String[] itemTypes;
	private int[] itemPrices;
	private int[] totalRequiredOptionsPrices;
	private int[] quantitys;
	private String[] itemOptions;
	private String[] openMarketOptions;
	
	private int orderPayAmount;
	private int totalItemPrice;
	private int totalItemCouponDiscountAmount;
	private int totalExcisePrice;
	private int totalDeliveryCharge;
	private int totalCartCouponDiscountAmount;
	private int totalPointDiscountAmount;
	private int afterUsePoint;
	private int differencePoint;
	private int addDiscountAmount;
	private int addDeliveryPrice;
	private String dodobuhyun;
	private String differencePointReturns;
	
	private String orderStatus;

	public String[] getOpenMarketOptions() {
		return CommonUtils.copy(openMarketOptions);
	}
	public void setOpenMarketOptions(String[] openMarketOptions) {
		this.openMarketOptions = CommonUtils.copy(openMarketOptions);
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int[] getOrderItemIds() {
		return CommonUtils.copy(orderItemIds);
	}
	public void setOrderItemIds(int[] orderItemIds) {
		this.orderItemIds = CommonUtils.copy(orderItemIds);
	}
	public String getDifferencePointReturns() {
		return differencePointReturns;
	}
	public void setDifferencePointReturns(String differencePointReturns) {
		this.differencePointReturns = differencePointReturns;
	}
	public int[] getUserOrderItemSequences() {
		return CommonUtils.copy(userOrderItemSequences);
	}
	public void setUserOrderItemSequences(int[] userOrderItemSequences) {
		this.userOrderItemSequences = CommonUtils.copy(userOrderItemSequences);
	}
	public String[] getItemOptions() {
		return CommonUtils.copy(itemOptions);
	}
	public void setItemOptions(String[] itemOptions) {
		this.itemOptions = CommonUtils.copy(itemOptions);
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public List<String> getUseCouponKeys() {
		return useCouponKeys;
	}
	public void setUseCouponKeys(List<String> useCouponKeys) {
		this.useCouponKeys = useCouponKeys;
	}
	public int getOrderPayAmount() {
		return orderPayAmount;
	}
	public void setOrderPayAmount(int orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}
	public int getTotalItemPrice() {
		return totalItemPrice;
	}
	public void setTotalItemPrice(int totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}
	public int getTotalItemCouponDiscountAmount() {
		return totalItemCouponDiscountAmount;
	}
	public void setTotalItemCouponDiscountAmount(int totalItemCouponDiscountAmount) {
		this.totalItemCouponDiscountAmount = totalItemCouponDiscountAmount;
	}
	public int getTotalExcisePrice() {
		return totalExcisePrice;
	}
	public void setTotalExcisePrice(int totalExcisePrice) {
		this.totalExcisePrice = totalExcisePrice;
	}
	public int getTotalDeliveryCharge() {
		return totalDeliveryCharge;
	}
	public void setTotalDeliveryCharge(int totalDeliveryCharge) {
		this.totalDeliveryCharge = totalDeliveryCharge;
	}
	public int getTotalCartCouponDiscountAmount() {
		return totalCartCouponDiscountAmount;
	}
	public void setTotalCartCouponDiscountAmount(int totalCartCouponDiscountAmount) {
		this.totalCartCouponDiscountAmount = totalCartCouponDiscountAmount;
	}
	public int getTotalPointDiscountAmount() {
		return totalPointDiscountAmount;
	}
	public void setTotalPointDiscountAmount(int totalPointDiscountAmount) {
		this.totalPointDiscountAmount = totalPointDiscountAmount;
	}
	public int getAfterUsePoint() {
		return afterUsePoint;
	}
	public void setAfterUsePoint(int afterUsePoint) {
		this.afterUsePoint = afterUsePoint;
	}
	public int getDifferencePoint() {
		return differencePoint;
	}
	public void setDifferencePoint(int differencePoint) {
		this.differencePoint = differencePoint;
	}
	
	public int getAddDiscountAmount() {
		return addDiscountAmount;
	}
	public void setAddDiscountAmount(int addDiscountAmount) {
		this.addDiscountAmount = addDiscountAmount;
	}
	public int getAddDeliveryPrice() {
		return addDeliveryPrice;
	}
	public void setAddDeliveryPrice(int addDeliveryPrice) {
		this.addDeliveryPrice = addDeliveryPrice;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	// [SKC임시]
	/*
	public Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	public DeliveryCharge getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(DeliveryCharge deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	*/
	public String getDodobuhyun() {
		return dodobuhyun;
	}
	public void setDodobuhyun(String dodobuhyun) {
		this.dodobuhyun = dodobuhyun;
	}
	public int[] getItemIds() {
		return CommonUtils.copy(itemIds);
	}
	public void setItemIds(int[] itemIds) {
		this.itemIds = CommonUtils.copy(itemIds);
	}
	public String[] getItemTypes() {
		return CommonUtils.copy(itemTypes);
	}
	public void setItemTypes(String[] itemTypes) {
		this.itemTypes = CommonUtils.copy(itemTypes);
	}
	public int[] getItemPrices() {
		return CommonUtils.copy(itemPrices);
	}
	public void setItemPrices(int[] itemPrices) {
		this.itemPrices = CommonUtils.copy(itemPrices);
	}
	public int[] getTotalRequiredOptionsPrices() {
		return CommonUtils.copy(totalRequiredOptionsPrices);
	}
	public void setTotalRequiredOptionsPrices(int[] totalRequiredOptionsPrices) {
		this.totalRequiredOptionsPrices = CommonUtils.copy(totalRequiredOptionsPrices);
	}
	public int[] getQuantitys() {
		return CommonUtils.copy(quantitys);
	}
	public void setQuantitys(int[] quantitys) {
		this.quantitys = CommonUtils.copy(quantitys);
	}

}
