package saleson.shop.mall.auction.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import saleson.shop.mall.domain.MallBase;

@XmlRootElement(name="OrderCanceled")
@XmlAccessorType (XmlAccessType.FIELD)
public class OrderCanceled extends MallBase {
	
	@XmlElement(name="OrderBase", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private OrderBase orderBase;

	@XmlAttribute(name="CategoryName")
	private String categoryName;
	
	@XmlAttribute(name="CancelType")
	private String cancelType;
	
	@XmlAttribute(name="CancelDate")
	private String cancelDate;

	public OrderBase getOrderBase() {
		return orderBase;
	}

	public void setOrderBase(OrderBase orderBase) {
		this.orderBase = orderBase;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	
	
}
