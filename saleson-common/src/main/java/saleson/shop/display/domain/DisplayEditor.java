package saleson.shop.display.domain;

public class DisplayEditor {

	private String displayGroupCode;
	private String displaySubCode;
	private String viewTarget = "ALL";
	
	private String displayEditorContent;
	
	private int ordering;
	private String createdDate;
	
	public String getDisplayGroupCode() {
		return displayGroupCode;
	}
	public void setDisplayGroupCode(String displayGroupCode) {
		this.displayGroupCode = displayGroupCode;
	}
	public String getDisplaySubCode() {
		return displaySubCode;
	}
	public void setDisplaySubCode(String displaySubCode) {
		this.displaySubCode = displaySubCode;
	}
	public String getViewTarget() {
		return viewTarget;
	}
	public void setViewTarget(String viewTarget) {
		this.viewTarget = viewTarget;
	}
	public String getDisplayEditorContent() {
		return displayEditorContent;
	}
	public void setDisplayEditorContent(String displayEditorContent) {
		this.displayEditorContent = displayEditorContent;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
