package saleson.shop.brand.domain;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.config.SalesonProperty;

public class Brand {
	private int brandId;
	private String brandName;
	private String brandImage;
	private String brandContent;
	private String displayFlag;
	private int updatedUserId;
	private String updatedDate;
	private int createdUserId;
	private String createdDate;
	
	private String brandImageDeleteFlag;
	private MultipartFile file;
	
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandImage() {
		return brandImage;
	}
	public void setBrandImage(String brandImage) {
		this.brandImage = brandImage;
	}
	public String getBrandContent() {
		return brandContent;
	}
	public void setBrandContent(String brandContent) {
		this.brandContent = brandContent;
	}
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	public int getUpdatedUserId() {
		return updatedUserId;
	}
	public void setUpdatedUserId(int updatedUserId) {
		this.updatedUserId = updatedUserId;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(int createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getBrandImageDeleteFlag() {
		return brandImageDeleteFlag;
	}
	public void setBrandImageDeleteFlag(String brandImageDeleteFlag) {
		this.brandImageDeleteFlag = brandImageDeleteFlag;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getBrandImageSrc() {
		if (StringUtils.isEmpty(this.brandImage)) {
			return "";
		}
		return SalesonProperty.getUploadBaseFolder() + "/brand/" + this.brandId + "/" + this.brandImage;
	}
}
