package saleson.shop.display.domain;

import java.util.List;

public class Display {

	private String displayGroupCode;
	private String displaySubCode;
	
	private List<DisplayImage> displayImageList;
	private List<DisplayEditor> displayEditorList;
	private List<DisplayItem> displayItemList;
	
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
	public List<DisplayImage> getDisplayImageList() {
		return displayImageList;
	}
	public void setDisplayImageList(List<DisplayImage> displayImageList) {
		this.displayImageList = displayImageList;
	}
	public List<DisplayEditor> getDisplayEditorList() {
		return displayEditorList;
	}
	public void setDisplayEditorList(List<DisplayEditor> displayEditorList) {
		this.displayEditorList = displayEditorList;
	}
	public List<DisplayItem> getDisplayItemList() {
		return displayItemList;
	}
	public void setDisplayItemList(List<DisplayItem> displayItemList) {
		this.displayItemList = displayItemList;
	}
	
	
	
}
