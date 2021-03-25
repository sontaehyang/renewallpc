package saleson.shop.display.domain;

import com.onlinepowers.framework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;

import java.util.List;

public class DisplayImage {
	
	private String displayGroupCode;
	private String displaySubCode;
	private String viewTarget = "ALL";
	
	private String displayImage;
	private String displayUrl;
	private String displayContent;
	private String displayColor;
	
	private int ordering;
	private String createdDate;
	
	private List<MultipartFile> displayImages;
	private List<String> displayUrls;
	private List<String> fileNames;
	private List<String> displayContents;
	
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
	public String getDisplayImage() {
		return displayImage;
	}
	public void setDisplayImage(String displayImage) {
		this.displayImage = displayImage;
	}
	public String getDisplayUrl() {
		return displayUrl;
	}
	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}
	public String getDisplayContent() {
		return displayContent;
	}
	public void setDisplayContent(String displayContent) {
		this.displayContent = displayContent;
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
	public List<MultipartFile> getDisplayImages() {
		return displayImages;
	}
	public void setDisplayImages(List<MultipartFile> displayImages) {
		this.displayImages = displayImages;
	}
	public List<String> getDisplayUrls() {
		return displayUrls;
	}
	public void setDisplayUrls(List<String> displayUrls) {
		this.displayUrls = displayUrls;
	}
	public List<String> getFileNames() {
		return fileNames;
	}
	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}
	public List<String> getDisplayContents() {
		return displayContents;
	}
	public void setDisplayContents(List<String> displayContents) {
		this.displayContents = displayContents;
	}
	
	public String getDisplayImageSrc() {
		
		if (StringUtils.isEmpty(this.displayImage)) {
			return ShopUtils.getNoImagePath();
		}

		StringBuffer sb = new StringBuffer();
		sb.append(SalesonProperty.getUploadBaseFolder());
		sb.append("/display/");
		sb.append(this.displayGroupCode);
		sb.append("/");
		sb.append(this.displaySubCode);
		sb.append("/thumb/");
		sb.append(this.displayImage);

		return sb.toString();
	}

	public String getDisplayColor() {
		return displayColor;
	}

	public void setDisplayColor(String displayColor) {
		this.displayColor = displayColor;
	}
}
