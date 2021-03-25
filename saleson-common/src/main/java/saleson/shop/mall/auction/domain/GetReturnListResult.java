package saleson.shop.mall.auction.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetReturnListResponse", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetReturnListResult {

	@XmlElementWrapper(name="GetReturnListResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
	@XmlElement(name="ReturnList", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<ReturnList> list;

	public List<ReturnList> getList() {
		return list;
	}

	public void setList(List<ReturnList> list) {
		this.list = list;
	}
	
}
