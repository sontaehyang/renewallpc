package saleson.shop.groupbanner.domain;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.onlinepowers.framework.util.FileUtils;

public class GroupBanner {
	
	private final String DEFAULT_FILE_PATH = FileUtils.getDefaultUploadPath() + File.separator + "groupBanner";
	
	private final int thumbnailWidth = 620;
	private final int thumbnailHeight = 180;
	
	private final int uploadFileMaxSize = 2;
	private final int uploadFileMinSize = 0;
	
	private int categoryGroupBannerId;
	private int categoryGroupId;
	private String title;
	private String linkUrl;
	private String fileName;
	private int displayOrder;
	private String createdDate;
	private MultipartFile uploadFile;
	private String deleteFlag;
	
	
	private List<GroupBanner> writeDatas;

	public int getThumbnailWidth() {
		return this.thumbnailWidth;
	}

	
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public int getThumbnailHeight() {
		return this.thumbnailHeight;
	}
	
	public String getDefaultFilePath() {
		return DEFAULT_FILE_PATH + File.separator + this.categoryGroupId;
	}
	
	public String getImageSrc() {
		return "/upload/groupBanner/" + this.categoryGroupId + "/" + this.fileName;
	}
	
	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public List<GroupBanner> getWriteDatas() {
		return writeDatas;
	}

	public void setWriteDatas(List<GroupBanner> writeDatas) {
		this.writeDatas = writeDatas;
	}

	public int getUploadFileMaxSize() {
		return this.uploadFileMaxSize;
	}
	
	public int getUploadFileMinSize() {
		return this.uploadFileMinSize;
	}
	
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public int getCategoryGroupBannerId() {
		return categoryGroupBannerId;
	}
	public void setCategoryGroupBannerId(int categoryGroupBannerId) {
		this.categoryGroupBannerId = categoryGroupBannerId;
	}
	public int getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(int categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
