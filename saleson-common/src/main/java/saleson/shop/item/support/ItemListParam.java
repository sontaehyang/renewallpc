package saleson.shop.item.support;

import com.onlinepowers.framework.util.ArrayUtils;
import com.onlinepowers.framework.web.domain.ListParam;
import saleson.common.utils.CommonUtils;
import saleson.shop.item.domain.Item;

public class ItemListParam extends ListParam {
	private int categoryId;
	
	private String[] itemPrice;
	private String[] salePrice;
	private String[] salePriceNonmember;
	
	private String soldOut;
	private int stockQuantity;
	private String displayFlag;
	private String requestStatus;
	
	// 등록 보류 메세지 
	private String approvalMessage;
	
	// 간편관리.
	private String[] itemUserCodes;
	private String[] itemNames;
	private String[] costPrices;
	private String[] salePrices;
	private String[] itemPrices;
	private String[] manufacturers;
	private String[] originCountries;
	private String[] soldOuts;
	private String[] displayFlags;
	
	public String getApprovalMessage() {
		return approvalMessage;
	}

	public void setApprovalMessage(String approvalMessage) {
		this.approvalMessage = approvalMessage;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getSoldOut() {
		return soldOut;
	}

	public void setSoldOut(String soldOut) {
		this.soldOut = soldOut;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String[] getItemPrice() {
		return CommonUtils.copy(itemPrice);
	}

	public void setItemPrice(String[] itemPrice) {
		this.itemPrice = CommonUtils.copy(itemPrice);
	}

	public String[] getSalePrice() {
		return CommonUtils.copy(salePrice);
	}

	public void setSalePrice(String[] salePrice) {
		this.salePrice = CommonUtils.copy(salePrice);
	}

	public String[] getSalePriceNonmember() {
		return CommonUtils.copy(salePriceNonmember);
	}

	public void setSalePriceNonmember(String[] salePriceNonmember) {
		this.salePriceNonmember = CommonUtils.copy(salePriceNonmember);
	}

	public String[] getItemUserCodes() {
		return CommonUtils.copy(itemUserCodes);
	}

	public void setItemUserCodes(String[] itemUserCodes) {
		this.itemUserCodes = CommonUtils.copy(itemUserCodes);
	}

	public String[] getItemNames() {
		return CommonUtils.copy(itemNames);
	}

	public void setItemNames(String[] itemNames) {
		this.itemNames = CommonUtils.copy(itemNames);
	}

	public String[] getCostPrices() {
		return CommonUtils.copy(costPrices);
	}

	public void setCostPrices(String[] costPrices) {
		this.costPrices = CommonUtils.copy(costPrices);
	}

	public String[] getSalePrices() {
		return CommonUtils.copy(salePrices);
	}

	public void setSalePrices(String[] salePrices) {
		this.salePrices = CommonUtils.copy(salePrices);
	}

	public String[] getItemPrices() {
		return CommonUtils.copy(itemPrices);
	}

	public void setItemPrices(String[] itemPrices) {
		this.itemPrices = CommonUtils.copy(itemPrices);
	}

	public String[] getManufacturers() {
		return CommonUtils.copy(manufacturers);
	}

	public void setManufacturers(String[] manufacturers) {
		this.manufacturers = CommonUtils.copy(manufacturers);
	}

	public String[] getOriginCountries() {
		return CommonUtils.copy(originCountries);
	}

	public void setOriginCountries(String[] originCountries) {
		this.originCountries = CommonUtils.copy(originCountries);
	}

	public String[] getSoldOuts() {
		return CommonUtils.copy(soldOuts);
	}

	public void setSoldOuts(String[] soldOuts) {
		this.soldOuts = CommonUtils.copy(soldOuts);
	}

	public String[] getDisplayFlags() {
		return CommonUtils.copy(displayFlags);
	}

	public void setDisplayFlags(String[] displayFlags) {
		this.displayFlags = CommonUtils.copy(displayFlags);
	}
	
	/**
	 * 상품 간편 관리 일괄 업데이트를 위한 item 정보 리턴.
	 * @param index
	 * @return
	 */
	public Item getItemSimple(int index) {
		if (getId() == null || getId().length <= index) {
			return null;
		}
		
		Item item = new Item();
		item.setItemId(Integer.parseInt(ArrayUtils.get(getId(), index)));
		item.setItemUserCode(ArrayUtils.get(getItemUserCodes(), index));
		item.setItemName(ArrayUtils.get(getItemNames(), index));
		/*item.setCostPrice(Integer.parseInt(ArrayUtils.get(getCostPrices(), index)));*/
		item.setSalePrice(Integer.parseInt(ArrayUtils.get(getSalePrices(), index)));
		item.setItemPrice(ArrayUtils.get(getItemPrices(), index));
		item.setManufacturer(ArrayUtils.get(getManufacturers(), index));
		item.setOriginCountry(ArrayUtils.get(getOriginCountries(), index));
		
		item.setSoldOut(ArrayUtils.get(getSoldOuts(), index));
		item.setDisplayFlag(ArrayUtils.get(getDisplayFlags(), index));

		return item;
		
	}
	
}
