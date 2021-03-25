package saleson.shop.item.support;

import java.util.List;

public class ItemStockQuantityParam {

	private List<Integer> keys;
	private String sign; 		// 1 : + , 2 : -
	private int quantity;

	private String stockCode;
	
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public List<Integer> getKeys() {
		return keys;
	}
	public void setKeys(List<Integer> keys) {
		this.keys = keys;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
