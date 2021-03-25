package saleson.shop.mall.auction.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Order")
@XmlAccessorType (XmlAccessType.FIELD)
public class Order {

	@XmlAttribute(name="ItemID")
	private String itemId;
	
	@XmlAttribute(name="OrderNo")
	private int orderNo;
	
	@XmlAttribute(name="ItemName")
	private String itemName;
	
	@XmlAttribute(name="AwardQty")
	private int awardQty;
	
	@XmlAttribute(name="AwardAmount")
	private BigDecimal awardAmount;
	
	@XmlAttribute(name="BuyerName")
	private String buyerName;
	
	@XmlAttribute(name="BuyerID")
	private String buyerID;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getAwardQty() {
		return awardQty;
	}

	public void setAwardQty(int awardQty) {
		this.awardQty = awardQty;
	}

	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerID() {
		return buyerID;
	}

	public void setBuyerID(String buyerID) {
		this.buyerID = buyerID;
	}

}
