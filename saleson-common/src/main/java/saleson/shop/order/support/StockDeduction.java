package saleson.shop.order.support;

import com.onlinepowers.framework.util.StringUtils;

public class StockDeduction {
	private String stockDeductionType;
	private String stockCode;
	private long sellerId;
	private int quantity;

	private int itemId;
	private int itemOptionId;
	
	public StockDeduction(String mapKey, int quantity) {
		
		if (StringUtils.isEmpty(mapKey)) {
			return;
		}
		
		String[] temp = StringUtils.delimitedListToStringArray(mapKey, "||");
		if (temp.length < 2) {
			return;
		}
		
		this.quantity = quantity;
		this.stockDeductionType = temp[0];
		if (mapKey.startsWith("STOCK")) {
			this.sellerId = Integer.parseInt(temp[1]);
			this.stockCode = temp[2];
		} else if (mapKey.startsWith("ITEM")) {
			this.itemId = Integer.parseInt(temp[1]);
		} else if (mapKey.startsWith("OPTION")) {
			this.itemOptionId = Integer.parseInt(temp[1]);
		}
	}
	
	public String getStockDeductionType() {
		return stockDeductionType;
	}

	public void setStockDeductionType(String stockDeductionType) {
		this.stockDeductionType = stockDeductionType;
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
