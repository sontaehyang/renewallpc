package saleson.shop.mall.auction.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetOrderCanceledListResponse", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetOrderCanceledListResult {
	
	@XmlElementWrapper(name="GetOrderCanceledListResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
	@XmlElement(name="OrderCanceled", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<OrderCanceled> list;

	public List<OrderCanceled> getList() {
		return list;
	}

	public void setList(List<OrderCanceled> list) {
		this.list = list;
	}
	
}
