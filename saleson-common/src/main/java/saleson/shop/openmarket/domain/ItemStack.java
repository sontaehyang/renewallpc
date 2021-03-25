package saleson.shop.openmarket.domain;

import saleson.common.utils.CommonUtils;

public class ItemStack  {
	private String _itemId;
	private String _itemName;
	private int _itemTPrice;
	private int _itemUPrice;
	private String _selectedOption;
	private int _count;
	private String _itemImage;
	private String _itemThumb;
	private String _itemUrl;
	private String _ecMallId;

	/**
	 * @param itemId
	 *            Mall Item Code
	 * @param itemName
	 *            상품
	 * @param itemPrice
	 *            상품 개별 가격
	 * @param selectedOption
	 *            선택된 옵션. - 여러 옵션이 선택되었을 경우 '/'로 구분하는 것을 권장
	 * @param count
	 *            해당 상품 구매 갯수.
     * @param url
     *            상품 URL (찜일 경우에만 작동)
     * @param ecMallId
     *            네이버쇼핑EP의 ma_pid. 네이버 쇼핑 가맹점이면 네이버쇼핑EP의 ma_pid와 동일한 값을 입력해야 한다
	 */
	public ItemStack(String itemId, String itemName, int itemTPrice, int itemUPrice, String selectedOption,
			int count, String itemImage, String itemUrl, String ecMallId) {
		_itemId = itemId;
		_itemName = itemName;
		_itemTPrice = itemTPrice;
		_itemUPrice = itemUPrice;
		_selectedOption = selectedOption;
		_count = count;
        _itemImage = itemImage;
		_itemUrl = itemUrl;
        _ecMallId = CommonUtils.dataNvl(ecMallId);
	}
	
	public ItemStack(String itemId, String itemName, int itemUPrice, String itemImage, String itemThumb,
			String itemUrl, String ecMallId) {
		_itemId = itemId;
		_itemName = itemName;
		_itemUPrice = itemUPrice;
		_itemImage = itemImage;
		_itemThumb = itemThumb;
		_itemUrl = itemUrl;
        _ecMallId = CommonUtils.dataNvl(ecMallId);
	}

	public String getItemId() {
		return _itemId;
	}

	public String getItemName() {
		return _itemName;
	}

	public int getItemTotalPrice() {
		return _itemTPrice;
	}

	public int getItemUnitPrice() {
		return _itemUPrice;
	}

	public String getSelectedOption() {
		if (_selectedOption == null)
			return "";
		return _selectedOption;
	}

	public int getCount() {
		return _count;
	}
	
	public String getItemImage() {
		return _itemImage;
	}
	
	public String getItemThumb() {
		return _itemThumb;
	}
	
	public String getItemUrl() {
		return _itemUrl;
	}

    public String getEcMallId() {
        return _ecMallId;
    }
}
