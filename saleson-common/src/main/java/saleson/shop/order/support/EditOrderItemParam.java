package saleson.shop.order.support;

import java.util.ArrayList;
import java.util.List;

public class EditOrderItemParam {
	private List<Integer> itemIds;
	private int vendorId;
	
	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public List<Integer> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Integer> itemIds) {
		this.itemIds = itemIds;
	}
	
	public void convertItemIds(int[] itemIds) {
		
		List<Integer> t = new ArrayList<>();
		if (itemIds.length == 0) {
			return;
		}
		
		for(Integer i : itemIds) {
			t.add(i);
		}
		
		this.itemIds = t;
	}
}
