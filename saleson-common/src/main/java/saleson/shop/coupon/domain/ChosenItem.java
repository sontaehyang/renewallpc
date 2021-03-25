package saleson.shop.coupon.domain;

public class ChosenItem {
	
	//VO
	private String imageName;
	private String itemName;
	private String itemUserCode;
	private String salePrice;
	private String itemId;
	//Param
	private String categoryGroupId;
	private String categoryClass1;
	private String categoryClass2;
	private String categoryClass3;
	private String categoryClass4;
	private String where;
	private String query;
	private String categoryClass;
	
	public ChosenItem() {}
	
	public ChosenItem(String categoryGroupId, String categoryClass1, String categoryClass2, String categoryClass3,
			String categoryClass4, String where, String query) {
		super();
		this.categoryGroupId = categoryGroupId;
		this.categoryClass1 = categoryClass1;
		this.categoryClass2 = categoryClass2;
		this.categoryClass3 = categoryClass3;
		this.categoryClass4 = categoryClass4;
		this.where = where;
		this.query = query;
	}

	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
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

	public String getCategoryClass() {
		return categoryClass;
	}

	public void setCategoryClass(String categoryClass) {
		this.categoryClass = categoryClass;
	}

}
