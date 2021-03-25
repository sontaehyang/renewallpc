package saleson.shop.mall.auction.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetExchangeRequestListBySearchConditionResponse", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetExchangeRequestListBySearchConditionResult {

	@XmlElementWrapper(name="GetExchangeRequestListBySearchConditionResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
	@XmlElement(name="ExchangeBase", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<ExchangeBase> list;

	public List<ExchangeBase> getList() {
		return list;
	}

	public void setList(List<ExchangeBase> list) {
		this.list = list;
	}

}
