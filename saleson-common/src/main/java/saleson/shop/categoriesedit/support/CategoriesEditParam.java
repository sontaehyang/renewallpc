package saleson.shop.categoriesedit.support;


public class CategoriesEditParam {
	
	private int categoryEditId;
	private String type = "";
	private String code;
	private String editKind;
	private String editPosition;
	private String editTitle;
	
	public int getCategoryEditId() {
		return categoryEditId;
	}

	public void setCategoryEditId(int categoryEditId) {
		this.categoryEditId = categoryEditId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEditKind() {
		return editKind;
	}

	public void setEditKind(String editKind) {
		this.editKind = editKind;
	}

	public String getEditPosition() {
		return editPosition;
	}

	public void setEditPosition(String editPosition) {
		this.editPosition = editPosition;
	}

	public String getEditTitle() {
		return editTitle;
	}

	public void setEditTitle(String editTitle) {
		this.editTitle = editTitle;
	}
	
	
}

