package saleson.shop.mall.auction.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OrderBase")
@XmlAccessorType (XmlAccessType.FIELD)
public class OrderBase {
	
	@XmlAttribute(name="ItemID")
	private String itemId; // 상품 번호

	@XmlAttribute(name="OrderNo")
	private int orderNo; // 주문번호
	
	@XmlAttribute(name="ItemName")
	private String itemName;  // 상품명
	
	@XmlAttribute(name="AwardQty")
	private int awardQty; // 수량
	
	@XmlAttribute(name="AwardAmount")
	private BigDecimal awardAmount; // 금액
	
	@XmlAttribute(name="BuyerName")
	private String buyerName; // 주문자명
	
	@XmlAttribute(name="BuyerID")
	private String buyerID; // 주문자 ID

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

	public int getIntAwardAmount() {
		return awardAmount.intValue();
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
