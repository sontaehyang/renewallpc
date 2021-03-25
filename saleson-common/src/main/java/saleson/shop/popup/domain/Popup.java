package saleson.shop.popup.domain;

import org.springframework.web.multipart.MultipartFile;

public class Popup{	
	private int popupId;
	private String popupClose;
	private String popupType;
	private String popupStyle;
	private String subject;
	private String content;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private int width;
	private int height;
	private int topPosition;
	private int leftPosition;
	private String popupImage;
	private String imageLink;
	private String backgroundColor;
	
	private MultipartFile popupImageFile;
	private String popupImageSrc;
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setPopupImageSrc(String popupImageSrc) {
		this.popupImageSrc = popupImageSrc;
	}
	public String getPopupImageSrc() {
		return "/upload/popup/" + popupId + "/popup/" + this.popupImage;
	}
	public MultipartFile getPopupImageFile() {
		return popupImageFile;
	}
	public void setPopupImageFile(MultipartFile popupImageFile) {
		this.popupImageFile = popupImageFile;
	}
	public int getPopupId() {
		return popupId;
	}
	public void setPopupId(int popupId) {
		this.popupId = popupId;
	}
	public String getPopupClose() {
		return popupClose;
	}
	public void setPopupClose(String popupClose) {
		this.popupClose = popupClose;
	}
	public String getPopupType() {
		return popupType;
	}
	public void setPopupType(String popupType) {
		this.popupType = popupType;
	}
	public String getPopupStyle() {
		return popupStyle;
	}
	public void setPopupStyle(String popupStyle) {
		this.popupStyle = popupStyle;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getTopPosition() {
		return topPosition;
	}
	public void setTopPosition(int topPosition) {
		this.topPosition = topPosition;
	}
	public int getLeftPosition() {
		return leftPosition;
	}
	public void setLeftPosition(int leftPosition) {
		this.leftPosition = leftPosition;
	}
	public String getPopupImage() {
		return popupImage;
	}
	public void setPopupImage(String popupImage) {
		this.popupImage = popupImage;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
}
