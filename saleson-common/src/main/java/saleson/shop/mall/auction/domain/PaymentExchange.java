package saleson.shop.mall.auction.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PaymentExchange")
@XmlAccessorType (XmlAccessType.FIELD)
public class PaymentExchange {

	@XmlAttribute(name="PaymentDate")
	private String paymentDate; // 결제일자
	
	/**
	 * 추가결제구분
	 * Exchange : 교환배송비
	 * Return : 반품배송비
	 */
	@XmlAttribute(name="PaymentFeeType")
	private String paymentFeeType;
	
	
	@XmlAttribute(name="ReturnFeeAmount")
	private BigDecimal returnFeeAmount; // 반품교환배송비 추가결제액 


	public String getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}


	public String getPaymentFeeType() {
		return paymentFeeType;
	}


	public void setPaymentFeeType(String paymentFeeType) {
		this.paymentFeeType = paymentFeeType;
	}


	public BigDecimal getReturnFeeAmount() {
		return returnFeeAmount;
	}


	public void setReturnFeeAmount(BigDecimal returnFeeAmount) {
		this.returnFeeAmount = returnFeeAmount;
	}

}
