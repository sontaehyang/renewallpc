package saleson.shop.openmarket.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.shop.label.domain.LabelJson;
import saleson.shop.order.domain.OrderItem;

public class NaverItem {
	private static final Logger log = LoggerFactory.getLogger(NaverItem.class);

	public NaverItem() {}
	// json string으로 orderItem 세팅
	public NaverItem(String json) {
		// json:: {"isValid":true, "orderType":"cart" or "item", "deviceType":"web" or "mobile", "data":[...]}
		log.info("json::" + json);

		HashMap<String, Object> naverPayMap = null;
		List<OrderItem> list = new ArrayList<>();

		try {
			naverPayMap = (HashMap<String, Object>) JsonViewUtils.jsonToObject(json, new TypeReference<HashMap<String, Object>>() {});
		} catch (Exception e) { }

		if (naverPayMap != null) {
			setOrderType((String)naverPayMap.get("orderType"));
			setDeviceType((String)naverPayMap.get("deviceType"));

			this.orderItemMap = JSONArray.fromObject(naverPayMap.get("data"));
			for (Map<String, Object> map : this.orderItemMap) {
				if (map.get("optionArray") != null) {
					List<Map<String, Object>> optionArray = JSONArray.fromObject(map.get("optionArray"));
					for (Map<String, Object> optionMap : optionArray) {
						OrderItem orderItem = new OrderItem();
						orderItem.setOptions((String)optionMap.get("optionText"));
						orderItem.setOptionPrice((int)optionMap.get("optionPrice")==0 || StringUtils.isEmpty(String.valueOf(optionMap.get("optionPrice"))) ? 0 : (int)optionMap.get("optionPrice"));
						orderItem.setQuantity((int)optionMap.get("optionQuantity"));
						orderItem.setItemId((int)map.get("itemId"));
						orderItem.setItemName((String)map.get("itemName"));
						orderItem.setPrice((int)map.get("price"));
						orderItem.setItemUserCode((String)map.get("itemUserCode"));
						orderItem.setImageSrc((String)map.get("imageSrc"));
						this.setShipping((int) map.get("shipping"));

						list.add(orderItem);
					}
				} else {
					OrderItem orderItem = new OrderItem();
					orderItem.setItemId((int)map.get("itemId"));
					orderItem.setItemName((String)map.get("itemName"));
					orderItem.setQuantity((int)map.get("quantity"));
					orderItem.setPrice((int)map.get("price"));
					orderItem.setItemUserCode((String)map.get("itemUserCode"));
					orderItem.setImageSrc((String)map.get("imageSrc"));
					this.setShipping((int) map.get("shipping"));

					list.add(orderItem);
				}
			}
		}

		this.setOrderItem(list);
	}

	private List<OrderItem> orderItem;
	
	private List<Map<String, Object>> orderItemMap;

	private String orderType;
	private String deviceType;

	private int shipping;

	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

	public List<Map<String, Object>> getOrderItemMap() {
		return orderItemMap;
	}

	public void setOrderItemMap(List<Map<String, Object>> orderItemMap) {
		this.orderItemMap = orderItemMap;
	}
	
	public int getShipping() {
		return shipping;
	}

	public void setShipping(int shipping) {
		this.shipping = shipping;
	}

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
}
