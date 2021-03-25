package saleson.shop.mall.auction.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DoReturnApprovalResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class DoReturnApprovalResult {

	@XmlAttribute(name="ReturnApprovalResponseType")
	private String success;

	@XmlAttribute(name="FailWithResponse")
	private String errorMessage;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
