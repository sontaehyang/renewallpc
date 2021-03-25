package saleson.shop.statistics.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Growth {
	
	private String lastYear;
	private String nowYear;
	private List<ShopDateStatistics> lastList;
	private List<ShopDateStatistics> nowList;
	
	public Growth(String lastYear, String nowYear, List<ShopDateStatistics> lastList, List<ShopDateStatistics> nowList) {
		this.lastYear = lastYear;
		this.nowYear = nowYear;
		
		this.lastList = lastList;
		this.nowList = nowList;
	}

	public String getLastYear() {
		return lastYear;
	}

	public void setLastYear(String lastYear) {
		this.lastYear = lastYear;
	}

	public String getNowYear() {
		return nowYear;
	}

	public void setNowYear(String nowYear) {
		this.nowYear = nowYear;
	}

	public List<ShopDateStatistics> getLastList() {
		return lastList;
	}

	public void setLastList(List<ShopDateStatistics> lastList) {
		this.lastList = lastList;
	}

	public List<ShopDateStatistics> getNowList() {
		return nowList;
	}

	public void setNowList(List<ShopDateStatistics> nowList) {
		this.nowList = nowList;
	}
	
	public List<HashMap<String, Double>> getList() {
		
		List<HashMap<String, Double>> list = new ArrayList<>();
		
		for(int i=0; i < 12; i++) {
			list.add(getData(i + 1));
		}
		return list;
		
	}
	
	private HashMap<String, Double> getData(int i) {
		HashMap<String, Double> map = new HashMap<String, Double>();
		
		String key = this.lastYear + (i <= 9 ? "0"+i : i);
		for(ShopDateStatistics data : this.lastList) {
			if (key.equals(data.getSearchDate())) {
				map.put(key, data.getTotalPay() + data.getTotalCancel());
			}
		}
		
		String key1 = this.nowYear + (i <= 9 ? "0"+i : i);
		for(ShopDateStatistics data : this.nowList) {
			if (key1.equals(data.getSearchDate())) {
				map.put(key1, data.getTotalPay() + data.getTotalCancel());
			}
		}
		
		return map;
	}
}