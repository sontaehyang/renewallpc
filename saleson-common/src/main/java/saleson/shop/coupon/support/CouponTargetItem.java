package saleson.shop.coupon.support;

import org.springframework.util.StringUtils;
import saleson.common.utils.CommonUtils;

public class CouponTargetItem {
	private String addType;
	private String title;
	
	private String[] itemIds;
	private String categoryGroupId;
	private String categoryClass1;
	private String categoryClass2;
	private String categoryClass3;
	private String categoryClass4;
	
	private String categoryClass;
	public String getCategoryClass() {
		
		if (StringUtils.isEmpty(this.categoryClass) == false) {
			return this.categoryClass;
		}
		
		if (StringUtils.isEmpty(this.categoryClass4) == false) {
			return this.categoryClass4;
			
		} else if (StringUtils.isEmpty(this.categoryClass3) == false) {
			return this.categoryClass3;
			
		} else if (StringUtils.isEmpty(this.categoryClass2) == false) {
			return this.categoryClass2;
			
		} else if (StringUtils.isEmpty(this.categoryClass1) == false) {
			return this.categoryClass1;
		}
		
		return categoryClass;
	}
	
	public void setCategoryClass(String categoryClass) {
		this.categoryClass = categoryClass;
	}
	private String where;
	private String query;
	
	
	public String getAddType() {
		return addType;
	}
	public void setAddType(String addType) {
		this.addType = addType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getItemIds() {
		return CommonUtils.copy(itemIds);
	}
	public void setItemIds(String[] itemIds) {
		this.itemIds = CommonUtils.copy(itemIds);
	}
	public String getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(String categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public String getCategoryClass1() {
		return categoryClass1;
	}
	public void setCategoryClass1(String categoryClass1) {
		this.categoryClass1 = categoryClass1;
	}
	public String getCategoryClass2() {
		return categoryClass2;
	}
	public void setCategoryClass2(String categoryClass2) {
		this.categoryClass2 = categoryClass2;
	}
	public String getCategoryClass3() {
		return categoryClass3;
	}
	public void setCategoryClass3(String categoryClass3) {
		this.categoryClass3 = categoryClass3;
	}
	public String getCategoryClass4() {
		return categoryClass4;
	}
	public void setCategoryClass4(String categoryClass4) {
		this.categoryClass4 = categoryClass4;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}	
}
