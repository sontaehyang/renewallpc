package saleson.shop.order.support;

import com.onlinepowers.framework.util.StringUtils;

public class StockRestoration {
	private String stockRestorationType;
	private String stockCode;
	private long sellerId;
	private int quantity;

	private int itemId;
	private int itemOptionId;
	
	public StockRestoration(String mapKey, int quantity) {
		
		if (StringUtils.isEmpty(mapKey)) {
			return;
		}
		
		String[] temp = StringUtils.delimitedListToStringArray(mapKey, "||");
		if (temp.length < 2) {
			return;
		}
		
		this.quantity = quantity;
		this.stockRestorationType = temp[0];
		if (mapKey.startsWith("STOCK")) {
			this.sellerId = Integer.parseInt(temp[1]);
			this.stockCode = temp[2];
		} else if (mapKey.startsWith("ITEM")) {
			this.itemId = Integer.parseInt(temp[1]);
		} else if (mapKey.startsWith("OPTION")) {
			this.itemOptionId = Integer.parseInt(temp[1]);
		}
	}

	public String getStockRestorationType() {
		return stockRestorationType;
	}

	public void setStockRestorationType(String stockRestorationType) {
		this.stockRestorationType = stockRestorationType;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemOptionId() {
		return itemOptionId;
	}

	public void setItemOptionId(int itemOptionId) {
		this.itemOptionId = itemOptionId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
