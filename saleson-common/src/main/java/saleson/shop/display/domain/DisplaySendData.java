package saleson.shop.display.domain;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class DisplaySendData {

	private String displayGroupCode;
	private String displayTemplateCode;

	private String viewTarget = "ALL";

	private List<List<MultipartFile>> displayImages;
	private List<List<String>> displayUrls;
	private List<List<String>> fileNames;
	private List<List<String>> displayContents;
	private List<List<String>> displayColors;
	
	private List<String> displayEditorContents;
	
	private List<List<String>> displayItemIds;

	public String getDisplayGroupCode() {
		return displayGroupCode;
	}

	public void setDisplayGroupCode(String displayGroupCode) {
		this.displayGroupCode = displayGroupCode;
	}

	public String getDisplayTemplateCode() {
		return displayTemplateCode;
	}

	public void setDisplayTemplateCode(String displayTemplateCode) {
		this.displayTemplateCode = displayTemplateCode;
	}

	public List<List<MultipartFile>> getDisplayImages() {
		return displayImages;
	}

	public void setDisplayImages(List<List<MultipartFile>> displayImages) {
		this.displayImages = displayImages;
	}

	public List<List<String>> getDisplayUrls() {
		return displayUrls;
	}

	public void setDisplayUrls(List<List<String>> displayUrls) {
		this.displayUrls = displayUrls;
	}

	public List<List<String>> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<List<String>> fileNames) {
		this.fileNames = fileNames;
	}

	public List<List<String>> getDisplayContents() {
		return displayContents;
	}

	public void setDisplayContents(List<List<String>> displayContents) {
		this.displayContents = displayContents;
	}

	public List<String> getDisplayEditorContents() {
		return displayEditorContents;
	}

	public void setDisplayEditorContents(List<String> displayEditorContents) {
		this.displayEditorContents = displayEditorContents;
	}

	public List<List<String>> getDisplayItemIds() {
		return displayItemIds;
	}

	public void setDisplayItemIds(List<List<String>> displayItemIds) {
		this.displayItemIds = displayItemIds;
	}


	public List<List<String>> getDisplayColors() {
		return displayColors;
	}

	public void setDisplayColors(List<List<String>> displayColors) {
		this.displayColors = displayColors;
	}

	public String getViewTarget() {
		if (StringUtils.isEmpty(viewTarget)) {
			viewTarget = "ALL";
		}

		return viewTarget;
	}

	public void setViewTarget(String viewTarget) {
		this.viewTarget = viewTarget;
	}
}
