package saleson.shop.mall.auction.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="TransFee")
@XmlAccessorType (XmlAccessType.FIELD)
public class TransFee {

	
	@XmlAttribute(name="BundleRequestNo")
	private int bundleRequestNo; // 묶음배송요청번호
	
	@XmlAttribute(name="GroupOrderSeqno")
	private int groupOrderSeqno; // 묶음주문번호 

	
	/**
	 * 배송비방법 
	 * Free : 무료
	 * FreeConditionSatisfied : 무료배송 조건충족
	 * PayOnArrival : 착불 
	 * Prepaid : 선결제완료 
	 */
	@XmlAttribute(name="OrderShippingPaymentType")
	private String orderShippingPaymentType; 

	// 선결제배송비
	@XmlAttribute(name="PreTransAmount")
	private BigDecimal preTransAmount;

	public int getBundleRequestNo() {
		return bundleRequestNo;
	}

	public void setBundleRequestNo(int bundleRequestNo) {
		this.bundleRequestNo = bundleRequestNo;
	}

	public int getGroupOrderSeqno() {
		return groupOrderSeqno;
	}

	public void setGroupOrderSeqno(int groupOrderSeqno) {
		this.groupOrderSeqno = groupOrderSeqno;
	}

	public String getOrderShippingPaymentType() {
		return orderShippingPaymentType;
	}

	public void setOrderShippingPaymentType(String orderShippingPaymentType) {
		this.orderShippingPaymentType = orderShippingPaymentType;
	}

	public BigDecimal getPreTransAmount() {
		return preTransAmount;
	}

	public void setPreTransAmount(BigDecimal preTransAmount) {
		this.preTransAmount = preTransAmount;
	}
	
	public int getIntPreTransAmount() {
		
		if (preTransAmount == null) {
			return 0;
		}
		
		return preTransAmount.intValue();
	}
	
}
