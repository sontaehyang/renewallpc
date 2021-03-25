package saleson.shop.categories.domain;

import java.util.ArrayList;
import java.util.List;

import com.onlinepowers.framework.context.util.RequestContextUtils;
import saleson.common.utils.ShopUtils;


public class Category {
	private String categoryId;
	private String url;
	private String name;
	private String itemCount = "";
	private String field;		// 상품 검색
	private String code;		// 상품 검색
	private String groupUrl;
	private Category parentCategory;	// 부모카테고리
	private List<Category> childCategories = new ArrayList<>();
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getGroupUrl() {
		return groupUrl;
	}

	public void setGroupUrl(String groupUrl) {
		this.groupUrl = groupUrl;
	}

	public Category() {}
	
	public Category(String url, String name, String itemCount, String field, String code) {
		this.url = url;
		this.name = name;
		this.itemCount = itemCount;
		this.field = field;
		this.code = code;
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
	public List<Category> getChildCategories() {
		return childCategories;
	}
	public void setChildCategories(List<Category> childCategories) {
		this.childCategories = childCategories;
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

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getLink() {
		String linkPrefix = "";
		if (ShopUtils.isMobilePage()) {
			linkPrefix = "/m";
		}
		
		if (this.itemCount.equals("")) {
			//return linkPrefix + "/categories/"+groupUrl+"/" + this.url;
			return linkPrefix + "/categories/index/" + this.url;
		} else {
			return "javascript:findItems('" + this.field + "', '" + this.code + "')";
		}
	}
	
}
