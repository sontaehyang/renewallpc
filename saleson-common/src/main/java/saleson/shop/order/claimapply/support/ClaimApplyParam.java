package saleson.shop.order.claimapply.support;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;

@SuppressWarnings("serial")
public class ClaimApplyParam extends SearchParam {
	
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private long sellerId;
	
	private String searchStartDate;
	private String searchEndDate;
	private String deliveryType;
	private String shipmentReturnType;

	private String additionItemFlag;

	private String claimCode;

	public String getShipmentReturnType() {
		
		if (StringUtils.isEmpty(shipmentReturnType)) {
			return "";
		}
		
		return shipmentReturnType;
	}
	public void setShipmentReturnType(String shipmentReturnType) {
		this.shipmentReturnType = shipmentReturnType;
	}
	private String[] claimStatus;
	public String[] getClaimStatus() {
		
		if (claimStatus == null) {
			return new String[] {"01", "02", "10"};
		}

		return CommonUtils.copy(claimStatus);
	}
	public void setClaimStatus(String[] claimStatus) {
		this.claimStatus = CommonUtils.copy(claimStatus);
	}
	
	public String getDeliveryType() {
		
		if (StringUtils.isEmpty(deliveryType)) {
			return "";
		}
		
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public String getAdditionItemFlag() {
		return additionItemFlag;
	}
	public void setAdditionItemFlag(String additionItemFlag) {
		this.additionItemFlag = additionItemFlag;
	}
	public String getClaimCode() {
		return claimCode;
	}
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}
}
