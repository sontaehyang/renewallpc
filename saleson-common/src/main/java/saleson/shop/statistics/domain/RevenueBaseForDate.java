package saleson.shop.statistics.domain;

import java.util.List;

public class RevenueBaseForDate {
	private String key;
	private List<RevenueDetail> list;
	
	public int getRowspan() {
		int size = 1; // 날짜별 총합때문에 1부터 시작
		
		if (list == null) return 0;
		
		int payCount = 0;
		int cancelCount = 0;
		for(RevenueDetail detail : list) {
			size += detail.getItems().size();
			
			if ("PAY".equals(detail.getOrderType())) payCount++;
			if ("CANCEL".equals(detail.getOrderType())) cancelCount++;
		}
		
		if (payCount > 0) size++;
		if (cancelCount > 0) size++;
		
		return size;
	}
	
	public int getPayRowspan() {
		int size = 0;
		
		if (list == null) return 0;
		
		for(RevenueDetail detail : list) {
			if ("PAY".equals(detail.getOrderType())) {
				size += detail.getItems().size();
			}
		}
		
		return size;
	}
	
	public int getCancelRowspan() {
		int size = 0;
		
		if (list == null) return 0;
		
		for(RevenueDetail detail : list) {
			if ("CANCEL".equals(detail.getOrderType())) {
				size += detail.getItems().size();
			}
		}
		
		return size;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<RevenueDetail> getList() {
		return list;
	}
	public void setList(List<RevenueDetail> list) {
		this.list = list;
	}
	
	
}
