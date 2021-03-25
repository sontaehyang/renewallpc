package saleson.shop.featuredbanner.domain;

import java.util.List;
import saleson.common.utils.CommonUtils;

public class FeaturedBanner {

	private int featuredBannerId = 1;
	private String featuredBannerColor;
	private String bannerLeftTopTitle;
	private String bannerLeftTopLink;
	private String bannerLeftTopImage;
	private String bannerLeftBottom1Title;
	private String bannerLeftBottom1Link;
	private String bannerLeftBottom1Image;
	private String bannerLeftBottom2Title;
	private String bannerLeftBottom2Link;
	private String bannerLeftBottom2Image;
	private String bannerCenterTitle;
	private String bannerCenterLink;
	private String bannerCenterImage;
	private String bannerRightTopTitle;
	private String bannerRightTopLink;
	private String bannerRightTopImage;
	private String bannerRightBottom1Title;
	private String bannerRightBottom1Link;
	private String bannerRightBottom1Image;
	private String bannerRightBottom2Title;
	private String bannerRightBottom2Link;
	private String bannerRightBottom2Image;
	private int createdUserId;
	private String createdDate;
	
	private List<BannerImage> bannerImages;
	private String[] deleteImage;


	public String[] getDeleteImage() {
		return CommonUtils.copy(deleteImage);
	}
	public void setDeleteImage(String[] deleteImage) {
		this.deleteImage = CommonUtils.copy(deleteImage);
	}
	public List<BannerImage> getBannerImages() {
		return bannerImages;
	}
	public void setBannerImages(List<BannerImage> bannerImages) {
		this.bannerImages = bannerImages;
	}
	public int getFeaturedBannerId() {
		return featuredBannerId;
	}
	public void setFeaturedBannerId(int featuredBannerId) {
		this.featuredBannerId = featuredBannerId;
	}
	public String getFeaturedBannerColor() {
		return featuredBannerColor;
	}
	public void setFeaturedBannerColor(String featuredBannerColor) {
		this.featuredBannerColor = featuredBannerColor;
	}
	public String getBannerLeftTopTitle() {
		return bannerLeftTopTitle;
	}
	public void setBannerLeftTopTitle(String bannerLeftTopTitle) {
		this.bannerLeftTopTitle = bannerLeftTopTitle;
	}
	public String getBannerLeftTopLink() {
		return bannerLeftTopLink;
	}
	public void setBannerLeftTopLink(String bannerLeftTopLink) {
		this.bannerLeftTopLink = bannerLeftTopLink;
	}
	public String getBannerLeftTopImage() {
		return bannerLeftTopImage;
	}
	public void setBannerLeftTopImage(String bannerLeftTopImage) {
		this.bannerLeftTopImage = bannerLeftTopImage;
	}
	public String getBannerLeftBottom1Title() {
		return bannerLeftBottom1Title;
	}
	public void setBannerLeftBottom1Title(String bannerLeftBottom1Title) {
		this.bannerLeftBottom1Title = bannerLeftBottom1Title;
	}
	public String getBannerLeftBottom1Link() {
		return bannerLeftBottom1Link;
	}
	public void setBannerLeftBottom1Link(String bannerLeftBottom1Link) {
		this.bannerLeftBottom1Link = bannerLeftBottom1Link;
	}
	public String getBannerLeftBottom1Image() {
		return bannerLeftBottom1Image;
	}
	public void setBannerLeftBottom1Image(String bannerLeftBottom1Image) {
		this.bannerLeftBottom1Image = bannerLeftBottom1Image;
	}
	public String getBannerLeftBottom2Title() {
		return bannerLeftBottom2Title;
	}
	public void setBannerLeftBottom2Title(String bannerLeftBottom2Title) {
		this.bannerLeftBottom2Title = bannerLeftBottom2Title;
	}
	public String getBannerLeftBottom2Link() {
		return bannerLeftBottom2Link;
	}
	public void setBannerLeftBottom2Link(String bannerLeftBottom2Link) {
		this.bannerLeftBottom2Link = bannerLeftBottom2Link;
	}
	public String getBannerLeftBottom2Image() {
		return bannerLeftBottom2Image;
	}
	public void setBannerLeftBottom2Image(String bannerLeftBottom2Image) {
		this.bannerLeftBottom2Image = bannerLeftBottom2Image;
	}
	public String getBannerCenterTitle() {
		return bannerCenterTitle;
	}
	public void setBannerCenterTitle(String bannerCenterTitle) {
		this.bannerCenterTitle = bannerCenterTitle;
	}
	public String getBannerCenterLink() {
		return bannerCenterLink;
	}
	public void setBannerCenterLink(String bannerCenterLink) {
		this.bannerCenterLink = bannerCenterLink;
	}
	public String getBannerCenterImage() {
		return bannerCenterImage;
	}
	public void setBannerCenterImage(String bannerCenterImage) {
		this.bannerCenterImage = bannerCenterImage;
	}
	public String getBannerRightTopTitle() {
		return bannerRightTopTitle;
	}
	public void setBannerRightTopTitle(String bannerRightTopTitle) {
		this.bannerRightTopTitle = bannerRightTopTitle;
	}
	public String getBannerRightTopLink() {
		return bannerRightTopLink;
	}
	public void setBannerRightTopLink(String bannerRightTopLink) {
		this.bannerRightTopLink = bannerRightTopLink;
	}
	public String getBannerRightTopImage() {
		return bannerRightTopImage;
	}
	public void setBannerRightTopImage(String bannerRightTopImage) {
		this.bannerRightTopImage = bannerRightTopImage;
	}
	public String getBannerRightBottom1Title() {
		return bannerRightBottom1Title;
	}
	public void setBannerRightBottom1Title(String bannerRightBottom1Title) {
		this.bannerRightBottom1Title = bannerRightBottom1Title;
	}
	public String getBannerRightBottom1Link() {
		return bannerRightBottom1Link;
	}
	public void setBannerRightBottom1Link(String bannerRightBottom1Link) {
		this.bannerRightBottom1Link = bannerRightBottom1Link;
	}
	public String getBannerRightBottom1Image() {
		return bannerRightBottom1Image;
	}
	public void setBannerRightBottom1Image(String bannerRightBottom1Image) {
		this.bannerRightBottom1Image = bannerRightBottom1Image;
	}
	public String getBannerRightBottom2Title() {
		return bannerRightBottom2Title;
	}
	public void setBannerRightBottom2Title(String bannerRightBottom2Title) {
		this.bannerRightBottom2Title = bannerRightBottom2Title;
	}
	public String getBannerRightBottom2Link() {
		return bannerRightBottom2Link;
	}
	public void setBannerRightBottom2Link(String bannerRightBottom2Link) {
		this.bannerRightBottom2Link = bannerRightBottom2Link;
	}
	public String getBannerRightBottom2Image() {
		return bannerRightBottom2Image;
	}
	public void setBannerRightBottom2Image(String bannerRightBottom2Image) {
		this.bannerRightBottom2Image = bannerRightBottom2Image;
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
}
