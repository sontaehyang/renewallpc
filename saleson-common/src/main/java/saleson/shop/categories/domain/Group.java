package saleson.shop.categories.domain;

import saleson.common.utils.ShopUtils;
import saleson.shop.item.domain.Item;

import java.util.ArrayList;
import java.util.List;


public class Group {
	private String url;
	private String name;
	private String itemCount = ""; 	// 상품 검색 시 카테고리별 상품 수 저장
	private String field;		// 상품 검색
	private String code;		// 상품 검색
	private String itemList;
	
	private List<Category> categories = new ArrayList<>();
	
	private List<Item> items = new ArrayList<>();		// [skc-2017-05-26] 그룹별 상품조회시 사용.
	
	public Group() {}
	
	public Group(String url, String name, String itemCount, String field, String code) {
		this.url = url;
		this.name = name;
		this.itemCount = itemCount;
		this.field = field;
		this.code = code;
	}

	public String getItemList() {
		return itemList;
	}

	public void setItemList(String itemList) {
		this.itemList = itemList;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public String getItemCount() {
		return itemCount;
	}
	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getLink() {
		String linkPrefix = "";
		if (ShopUtils.isMobilePage()) {
			linkPrefix = "/m";
		}

		if (this.itemCount.equals("")) {
			return linkPrefix + "/categories/index/" + this.url;
		} else {
			return "javascript:findItems('" + this.field + "', '" + this.code + "')";
		}
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
