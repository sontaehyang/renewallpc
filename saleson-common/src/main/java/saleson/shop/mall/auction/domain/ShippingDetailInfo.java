package saleson.shop.mall.auction.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OrderBase")
@XmlAccessorType (XmlAccessType.FIELD)
public class ShippingDetailInfo {
	
	@XmlAttribute(name="GroupOrderSeqno")
	private int groupOrderSeqno;
	
	@XmlAttribute(name="ShippingSeqno")
	private int shippingSeqno;
	
	@XmlAttribute(name="PayFor")
	private String payFor;
	
	@XmlAttribute(name="Amount")
	private BigDecimal amount;
	
	@XmlAttribute(name="Status")
	private String status;
	
	@XmlAttribute(name="PayDate")
	private String payDate;
	
	@XmlAttribute(name="RemittanceDate")
	private String remittanceDate;
	
	@XmlAttribute(name="CancelDate")
	private String cancelDate;
	
	@XmlAttribute(name="OrderNo")
	private int orderNo;
	
	@XmlAttribute(name="PayNo")
	private int payNo;

	public int getGroupOrderSeqno() {
		return groupOrderSeqno;
	}

	public void setGroupOrderSeqno(int groupOrderSeqno) {
		this.groupOrderSeqno = groupOrderSeqno;
	}

	public int getShippingSeqno() {
		return shippingSeqno;
	}

	public void setShippingSeqno(int shippingSeqno) {
		this.shippingSeqno = shippingSeqno;
	}

	public String getPayFor() {
		return payFor;
	}

	public void setPayFor(String payFor) {
		this.payFor = payFor;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getRemittanceDate() {
		return remittanceDate;
	}

	public void setRemittanceDate(String remittanceDate) {
		this.remittanceDate = remittanceDate;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getPayNo() {
		return payNo;
	}

	public void setPayNo(int payNo) {
		this.payNo = payNo;
	}
	
}
