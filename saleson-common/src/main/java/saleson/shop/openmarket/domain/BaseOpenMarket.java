package saleson.shop.openmarket.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.PoiUtils;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.ShopUtils;

public class BaseOpenMarket {

	private String[] header;
	private List<HashMap<String, String>> errors; 
	private String openMarketOrderCodeCellString;
	private String shopItemUserCodeCellString;
	
	public BaseOpenMarket(String openMarketOrderCodeCellString, String shopItemUserCodeCellString, String[] header) {
		this.openMarketOrderCodeCellString = openMarketOrderCodeCellString;
		this.shopItemUserCodeCellString = shopItemUserCodeCellString;
		this.header = header;
	}

	public String[] getHeader() {
		return CommonUtils.copy(this.header);
	}
	
	public void setOpenMarketOrderCodeCellString(String key) {
		this.openMarketOrderCodeCellString = key;
	}
	
	public String getOpenMarketOrderCodeCellString() {
		return openMarketOrderCodeCellString;
	}
	
	public String getShopItemUserCodeCellString() {
		return shopItemUserCodeCellString;
	}
	
	public String getExcelRowToString(Row row) {
		
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		for(int i = 0; i < header.length; i++) {
			map.put(PoiUtils.convertIndexToCellString(i), ShopUtils.getString(row.getCell(i)));
		}
		
		return JsonViewUtils.objectToJson(map);
		
	}
	
	public void makeErrorRow(Row row, String errorMessage) {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		for(int i = 0; i < header.length; i++) {
			map.put(PoiUtils.convertIndexToCellString(i), ShopUtils.getString(row.getCell(i)));
		}
		
		map.put(PoiUtils.convertIndexToCellString(header.length), errorMessage);
		errors.add(map);
	}
	
	public List<HashMap<String, String>> getErrors() {
		return errors;
	}
}
