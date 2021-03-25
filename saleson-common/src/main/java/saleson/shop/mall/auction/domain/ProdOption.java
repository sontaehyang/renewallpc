package saleson.shop.mall.auction.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ProdOption")
@XmlAccessorType (XmlAccessType.FIELD)
public class ProdOption {

	@XmlAttribute(name="OptionItemName")
	private String optionItemName; // 옵션아이템이름
	
	@XmlAttribute(name="OptionItemPrice")
	private BigDecimal optionItemPrice; // 가격
	
	@XmlAttribute(name="OptionName")
	private String optionName; // 옵션이름
	
	@XmlAttribute(name="OptionQuantity")
	private String optionQuantity; // 옵션 수량 

	public String getOptionItemName() {
		return optionItemName;
	}

	public void setOptionItemName(String optionItemName) {
		this.optionItemName = optionItemName;
	}

	public BigDecimal getOptionItemPrice() {
		return optionItemPrice;
	}

	public void setOptionItemPrice(BigDecimal optionItemPrice) {
		this.optionItemPrice = optionItemPrice;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionQuantity() {
		return optionQuantity;
	}

	public void setOptionQuantity(String optionQuantity) {
		this.optionQuantity = optionQuantity;
	}
	
}
