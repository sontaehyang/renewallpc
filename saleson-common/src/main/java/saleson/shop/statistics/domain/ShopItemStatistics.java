package saleson.shop.statistics.domain;

import java.util.List;

import org.springframework.util.StringUtils;

import saleson.common.utils.ShopUtils;


public class ShopItemStatistics {

	private int itemId;
	private String itemName;
	private String itemUserCode;
	private String itemImage;

	private List<BaseSellStatistics> groupList;

	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {

		if (StringUtils.isEmpty(itemName)) {
			return "정보 없음";
		}

		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	/**
	 * 목록 이미지 경로
	 * @return
	 */
	public String getImageSrc() {
		return ShopUtils.listImage(this.itemUserCode, this.itemImage);
	}
	public List<BaseSellStatistics> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<BaseSellStatistics> groupList) {
		this.groupList = groupList;
	}

	public double getTotalPayItemCouponDiscountAmount() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getItemCouponDiscountAmount();
		}

		return value;
	}

	public double getTotalCancelItemCouponDiscountAmount() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getCancelItemCouponDiscountAmount();
		}

		return value;
	}
	// 이상우 [2017-04-13 추가] 판매자, 스팟, 총할인 (취소,결제) 추가
	public double getTotalPaySellerDiscountPrice() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getSellerDiscountPrice();
		}

		return value;
	}

	public double getTotalCancelSellerDiscountPrice() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getCancelSellerDiscountPrice();
		}

		return value;
	}

	public double getTotalPaySpotDiscountPrice() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getSpotDiscountPrice();
		}

		return value;
	}

	public double getTotalCancelSpotDiscountPrice() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getCancelSpotDiscountPrice();
		}

		return value;
	}

	public double getTotalPayDiscountAmount() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getTotalDiscountAmount();
		}

		return value;
	}

	public double getTotalCancelDiscountAmount() {

		double value = 0;

		for (BaseSellStatistics item : groupList) {
			value += item.getCancelTotalDiscountAmount();
		}

		return value;
	}

	public double getTotalPayCount() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getPayCount();
		}

		return value;
	}

	public double getTotalPayItemPrice() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getItemPrice();
		}

		return value;
	}

	public double getTotalPay() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getPayTotal();
		}

		return value;
	}

	public double getTotalCancelCount() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getCancelCount();
		}

		return value;
	}

	public double getTotalCancelItemPrice() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getCancelItemPrice();
		}

		return value;
	}

	public double getTotalCancel() {

		double value = 0;

		for(BaseSellStatistics item : groupList) {
			value += item.getCancelTotal();
		}

		return value;
	}

	//이상우 [2017-05-02 추가] 설정에 따른 음수표현을 위해 추가
	//총 갯수(결제 갯 수 - 취소 갯 수)
	public double getTotalCount() {
		return this.getTotalPayCount() - ShopUtils.statsNegativeNnumber(this.getTotalCancelCount());
	}
	//총 상품금액(결제상품금액 - 취소상품금액)
	public double getTotalItemPrice() {
		return this.getTotalPayItemPrice() - ShopUtils.statsNegativeNnumber(this.getTotalCancelItemPrice());
	}
	//총 할인(결제할인 - 취소할인)
	public double getTotalDiscountAmount() {
		return this.getTotalPayDiscountAmount() - ShopUtils.statsNegativeNnumber(this.getTotalCancelDiscountAmount());
	}
	//총 판매액(판매액 - 취소액)
	public double getTotalAmount() {
		return this.getTotalPay() - ShopUtils.statsNegativeNnumber(this.getTotalCancel());
	}

}