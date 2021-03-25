package saleson.shop.mall.auction.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetOrderInfoListResponse", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetOrderInfoListResult {

	@XmlElementWrapper(name="GetOrderInfoListResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
	@XmlElement(name="OrderInfoList", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<OrderInfo> list;

	public List<OrderInfo> getList() {
		return list;
	}

	public void setList(List<OrderInfo> list) {
		this.list = list;
	}
	
}
