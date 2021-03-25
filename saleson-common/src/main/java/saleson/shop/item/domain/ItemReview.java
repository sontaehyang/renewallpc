package saleson.shop.item.domain;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.model.review.ItemReviewFilter;

import java.io.File;
import java.util.List;

public class ItemReview {
	private int itemReviewId;
	private int itemId;
	private String itemCode;
	private String subject;
	private String content;
	private int score;
	private String recommendFlag;
	private long userId;
	private String userName;
	private long sellerId;
	private String sellerName;
	private String displayFlag = "N";
	private String createdDate;
	private String pointPayment = "N";
	private int point;
	private String pointPaymentDate;

	private List<ItemReviewImage> itemReviewImages;

	private ItemBase item;

	private String starScore;
	
	private String loginId;
	private String orderCode; //kye 추가

	private String displayOptionsFlag = "N";
	private String options;
	private String adminComment;
	private int likeCount = 0;

	private List<ItemReviewFilter> itemReviewFilters;

	public String getStarScore() {
		return starScore;
	}
	public void setStarScore(String starScore) {
		this.starScore = starScore;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	// 상품리뷰이미지
	private MultipartFile itemReviewImageFile;
	private List<MultipartFile> itemReviewImageFiles;

	public String getPointPayment() {
		return pointPayment;
	}
	public void setPointPayment(String pointPayment) {
		this.pointPayment = pointPayment;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getPointPaymentDate() {
		return pointPaymentDate;
	}
	public void setPointPaymentDate(String pointPaymentDate) {
		this.pointPaymentDate = pointPaymentDate;
	}
	public int getItemReviewId() {
		return itemReviewId;
	}
	public void setItemReviewId(int itemReviewId) {
		this.itemReviewId = itemReviewId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getRecommendFlag() {
		return recommendFlag;
	}
	public void setRecommendFlag(String recommendFlag) {
		this.recommendFlag = recommendFlag;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public ItemBase getItem() {
		return item;
	}
	public void setItem(ItemBase item) {
		this.item = item;
	}

	/**
	 * 리뷰 이미지 경로
	 * @return
	 */
	public String getImageUploadPath() {

		StringBuffer sb = new StringBuffer();

		sb.append(File.separator);
		sb.append("item-review");
		sb.append(File.separator);
		sb.append(this.itemReviewId);

		return sb.toString();
	}

	public String getThumbnailSrc() {

		if (this.itemReviewImages != null && !this.itemReviewImages.isEmpty()) {
			for (ItemReviewImage itemReviewImage : this.itemReviewImages) {
				StringBuffer sb = itemReviewImage.getDefaultSrc();
				sb.append("/thumb_");
				sb.append(itemReviewImage.getReviewImage());

				return StringUtils.isEmpty(itemReviewImage.getReviewImage()) ? ShopUtils.getNoImagePath() : sb.toString();
			}
		}

		return ShopUtils.getNoImagePath();
	}

	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getMaskUsername() {
		
		try {
			if (StringUtils.isNotEmpty(this.userName)) {
				return this.userName.substring(0, this.userName.length() - 2) + "**";
			}
		} catch(Exception e) {
			return "";
		}
		
		return "";
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public List<ItemReviewImage> getItemReviewImages() {
		return itemReviewImages;
	}

	public void setItemReviewImages(List<ItemReviewImage> itemReviewImages) {
		this.itemReviewImages = itemReviewImages;
	}

	public MultipartFile getItemReviewImageFile() {
		return itemReviewImageFile;
	}

	public void setItemReviewImageFile(MultipartFile itemReviewImageFile) {
		this.itemReviewImageFile = itemReviewImageFile;
	}

	public List<MultipartFile> getItemReviewImageFiles() {
		return itemReviewImageFiles;
	}

	public void setItemReviewImageFiles(List<MultipartFile> itemReviewImageFiles) {
		this.itemReviewImageFiles = itemReviewImageFiles;
	}

	public String getDisplayOptionsFlag() {
		return displayOptionsFlag;
	}

	public void setDisplayOptionsFlag(String displayOptionsFlag) {
		this.displayOptionsFlag = displayOptionsFlag;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getAdminComment() {
		return adminComment;
	}

	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public List<ItemReviewFilter> getItemReviewFilters() {
		return itemReviewFilters;
	}

	public void setItemReviewFilters(List<ItemReviewFilter> itemReviewFilters) {
		this.itemReviewFilters = itemReviewFilters;
	}
}
