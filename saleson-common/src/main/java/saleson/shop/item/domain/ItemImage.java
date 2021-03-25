package saleson.shop.item.domain;

import saleson.common.utils.ShopUtils;

public class ItemImage {
	private int itemImageId;
	private int itemId;
	private String imageName;
	private int ordering;
	private String createdDate;
	private String itemUserCode;
	
	public ItemImage() {}
	
	public ItemImage(int itemImageId, int ordering) {
		this.itemImageId = itemImageId;
		this.ordering = ordering;
	}
	
	public int getItemImageId() {
		return itemImageId;
	}
	public void setItemImageId(int itemImageId) {
		this.itemImageId = itemImageId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
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
	
	
	/**
	 * 상세 이미지 경로 
	 * @return
	 */
	public String getImageSrc() {
		return ShopUtils.detailsImage(this.itemUserCode, this.imageName);
	}
	
	
	/**
	 * 상세 섬네일 이미지 경로 
	 * @return
	 */
	public String getThumbnailImageSrc() {
		return ShopUtils.detailsThumbnail(this.itemUserCode, this.imageName);
	}
	
	
	/**
	 * 상세 확대 이미지 경로 
	 * @return
	 */
	public String getBigImageSrc() {
		return ShopUtils.detailsBigImage(this.itemUserCode, this.imageName);
	}

	public String getItemUserCode() {
		return itemUserCode;
	}

	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	
	
}
