package saleson.shop.mall.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;

public class MallOrder {
	private int mallOrderId;
	private int itemId;
	private String matchedOptions;
	private String itemUserCode;
	
	private int mallConfigId;
	private String mallApiKey;
	private String mallLoginId;
	private String mallType;
	private String orderItemStatus;
	private String orderItemStatusLabel;
	private String orderCode;
	private int orderIndex;
	private String shippingGroupCode;
	private String shippingGroupFlag;
	private String additionItemFlag;
	private int parentProductCode;
	private String shippingCode;
	private String productCode;
	private String optionCode;
	private String productName;
	private String optionName;
	private int sellPrice;
	private int optionAmount;
	private int quantity;
	private int cancelQuantity;
	private int sellerDiscountAmount;
	private int saleAmount;
	private int payShipping;
	private String payShippingType;
	private int islandPayShipping;
	private String islandPayShippingType;
	
	private String mallProductCode;
	private String memberId;
	private String memberType;
	private int memberNo;
	private String buyerName;
	private String buyerTelephoneNumber;
	private String buyerPhoneNumber;
	private String buyerZipcode;
	private String buyerAddress;
	private String buyerAddressDetail;
	private String receiverName;
	private String receiverTelephoneNumber;
	private String receiverPhoneNumber;
	private String receiverZipcode;
	private String receiverAddress;
	private String receiverAddressDetail;
	private String content;
	private String systemMessage;
	private String payDate;
	private String createdDate;
	
	private String deliveryCompanyCode;
	private String deliveryNumber;
	private String deliveryDate;
	
	private Item item;
	
	private boolean isSoldOut;
	private boolean isMatched;

	/**
	 * 옵션타입이 S일경우 화면상에 옵션을 선택할수 있도록...
	 * @return
	 */
	public HashMap<String, List<ItemOption>> getItemOptionGroups() {
		if (item == null) {
			return null;
		}
		
		if (item.getItemOptions() == null) {
			return null;
		}
		
		if (!"S".equals(item.getItemOptionType())) {
			return null;
		}
		
		HashMap<String, List<ItemOption>> map = new HashMap<String, List<ItemOption>>();
		for(ItemOption itemOption : item.getItemOptions()) {
			
			if ("T".equals(itemOption.getOptionType())) {
				continue;
			}
			
			String key = itemOption.getOptionName1();
			List<ItemOption> options = map.get(key);
			if (options == null) {
				options = new ArrayList<>();
			}
			
			options.add(itemOption);
			map.put(key, options);
		}
		
		return map;
	}
	
	public int getOptionAmount() {
		return optionAmount;
	}

	public void setOptionAmount(int optionAmount) {
		this.optionAmount = optionAmount;
	}

	public int getPayShipping() {
		return payShipping;
	}

	public void setPayShipping(int payShipping) {
		this.payShipping = payShipping;
	}

	public String getPayShippingType() {
		return payShippingType;
	}

	public void setPayShippingType(String payShippingType) {
		this.payShippingType = payShippingType;
	}

	public int getIslandPayShipping() {
		return islandPayShipping;
	}

	public void setIslandPayShipping(int islandPayShipping) {
		this.islandPayShipping = islandPayShipping;
	}

	public String getIslandPayShippingType() {
		return islandPayShippingType;
	}

	public void setIslandPayShippingType(String islandPayShippingType) {
		this.islandPayShippingType = islandPayShippingType;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public boolean isMatched() {
		return isMatched;
	}

	public void setMatched(boolean isMatched) {
		this.isMatched = isMatched;
	}

	public String getMatchedOptions() {
		return matchedOptions;
	}

	public void setMatchedOptions(String matchedOptions) {
		this.matchedOptions = matchedOptions;
	}

	public boolean isSoldOut() {
		return isSoldOut;
	}

	public void setSoldOut(boolean isSoldOut) {
		this.isSoldOut = isSoldOut;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getItemUserCode() {
		return itemUserCode;
	}

	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryCompanyCode() {
		return deliveryCompanyCode;
	}
	public void setDeliveryCompanyCode(String deliveryCompanyCode) {
		this.deliveryCompanyCode = deliveryCompanyCode;
	}
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	public String getSystemMessage() {
		return systemMessage;
	}
	public void setSystemMessage(String systemMessage) {
		this.systemMessage = systemMessage;
	}
	
	public int getCancelQuantity() {
		return cancelQuantity;
	}

	public void setCancelQuantity(int cancelQuantity) {
		this.cancelQuantity = cancelQuantity;
	}

	public int getMallOrderId() {
		return mallOrderId;
	}
	public void setMallOrderId(int mallOrderId) {
		this.mallOrderId = mallOrderId;
	}
	public int getMallConfigId() {
		return mallConfigId;
	}
	public void setMallConfigId(int mallConfigId) {
		this.mallConfigId = mallConfigId;
	}
	public String getMallApiKey() {
		return mallApiKey;
	}
	public void setMallApiKey(String mallApiKey) {
		this.mallApiKey = mallApiKey;
	}
	public String getMallLoginId() {
		return mallLoginId;
	}
	public void setMallLoginId(String mallLoginId) {
		this.mallLoginId = mallLoginId;
	}
	public String getMallType() {
		return mallType;
	}
	public void setMallType(String mallType) {
		this.mallType = mallType;
	}
	public String getOrderItemStatus() {
		return orderItemStatus;
	}
	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}
	public String getOrderItemStatusLabel() {
		return orderItemStatusLabel;
	}
	public void setOrderItemStatusLabel(String orderItemStatusLabel) {
		this.orderItemStatusLabel = orderItemStatusLabel;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getShippingGroupCode() {
		return shippingGroupCode;
	}
	public void setShippingGroupCode(String shippingGroupCode) {
		this.shippingGroupCode = shippingGroupCode;
	}
	public String getShippingGroupFlag() {
		return shippingGroupFlag;
	}
	public void setShippingGroupFlag(String shippingGroupFlag) {
		this.shippingGroupFlag = shippingGroupFlag;
	}
	public String getAdditionItemFlag() {
		return additionItemFlag;
	}
	public void setAdditionItemFlag(String additionItemFlag) {
		this.additionItemFlag = additionItemFlag;
	}
	public int getParentProductCode() {
		return parentProductCode;
	}
	public void setParentProductCode(int parentProductCode) {
		this.parentProductCode = parentProductCode;
	}
	public String getShippingCode() {
		return shippingCode;
	}
	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getOptionCode() {
		return optionCode;
	}
	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getSellerDiscountAmount() {
		return sellerDiscountAmount;
	}
	public void setSellerDiscountAmount(int sellerDiscountAmount) {
		this.sellerDiscountAmount = sellerDiscountAmount;
	}
	public int getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(int saleAmount) {
		this.saleAmount = saleAmount;
	}
	public String getMallProductCode() {
		return mallProductCode;
	}
	public void setMallProductCode(String mallProductCode) {
		this.mallProductCode = mallProductCode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerTelephoneNumber() {
		return buyerTelephoneNumber;
	}
	public void setBuyerTelephoneNumber(String buyerTelephoneNumber) {
		this.buyerTelephoneNumber = buyerTelephoneNumber;
	}
	public String getBuyerPhoneNumber() {
		return buyerPhoneNumber;
	}
	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		this.buyerPhoneNumber = buyerPhoneNumber;
	}
	public String getBuyerZipcode() {
		return buyerZipcode;
	}
	public void setBuyerZipcode(String buyerZipcode) {
		this.buyerZipcode = buyerZipcode;
	}
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public String getBuyerAddressDetail() {
		return buyerAddressDetail;
	}
	public void setBuyerAddressDetail(String buyerAddressDetail) {
		this.buyerAddressDetail = buyerAddressDetail;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverTelephoneNumber() {
		return receiverTelephoneNumber;
	}
	public void setReceiverTelephoneNumber(String receiverTelephoneNumber) {
		this.receiverTelephoneNumber = receiverTelephoneNumber;
	}
	public String getReceiverPhoneNumber() {
		return receiverPhoneNumber;
	}
	public void setReceiverPhoneNumber(String receiverPhoneNumber) {
		this.receiverPhoneNumber = receiverPhoneNumber;
	}
	public String getReceiverZipcode() {
		return receiverZipcode;
	}
	public void setReceiverZipcode(String receiverZipcode) {
		this.receiverZipcode = receiverZipcode;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverAddressDetail() {
		return receiverAddressDetail;
	}
	public void setReceiverAddressDetail(String receiverAddressDetail) {
		this.receiverAddressDetail = receiverAddressDetail;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
