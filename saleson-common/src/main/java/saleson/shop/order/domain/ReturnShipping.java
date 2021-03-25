package saleson.shop.order.domain;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.util.DateUtils;

public class ReturnShipping {
	private String returnShippingStartFlag = "Y";
	
	private int returnShippingDeliveryCompanyId;
	private String returnShippingDeliveryCompanyName;
	private String returnShippingDeliveryNumber;
	private String returnShippingDeliveryCompanyUrl;
	
	private String returnShippingStartDate;

	public String getReturnShippingStartFlag() {
		return returnShippingStartFlag;
	}

	public void setReturnShippingStartFlag(String returnShippingStartFlag) {
		this.returnShippingStartFlag = returnShippingStartFlag;
	}

	public int getReturnShippingDeliveryCompanyId() {
		return returnShippingDeliveryCompanyId;
	}

	public void setReturnShippingDeliveryCompanyId(
			int returnShippingDeliveryCompanyId) {
		this.returnShippingDeliveryCompanyId = returnShippingDeliveryCompanyId;
	}

	public String getReturnShippingDeliveryCompanyName() {
		return returnShippingDeliveryCompanyName;
	}

	public void setReturnShippingDeliveryCompanyName(
			String returnShippingDeliveryCompanyName) {
		this.returnShippingDeliveryCompanyName = returnShippingDeliveryCompanyName;
	}

	public String getReturnShippingDeliveryNumber() {
		return returnShippingDeliveryNumber;
	}

	public void setReturnShippingDeliveryNumber(String returnShippingDeliveryNumber) {
		this.returnShippingDeliveryNumber = returnShippingDeliveryNumber;
	}

	public String getReturnShippingDeliveryCompanyUrl() {
		return returnShippingDeliveryCompanyUrl;
	}

	public void setReturnShippingDeliveryCompanyUrl(
			String returnShippingDeliveryCompanyUrl) {
		this.returnShippingDeliveryCompanyUrl = returnShippingDeliveryCompanyUrl;
	}

	public String getReturnShippingStartDate() {
		
		if (StringUtils.isEmpty(returnShippingStartDate)) {
			return DateUtils.getToday();
		}
		
		return returnShippingStartDate;
	}

	public void setReturnShippingStartDate(String returnShippingStartDate) {
		this.returnShippingStartDate = returnShippingStartDate;
	}

}
