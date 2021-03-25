package saleson.shop.mall.auction.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetPaidOrderListResponse", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPaidOrderListResult {
	
	@XmlElementWrapper(name="GetPaidOrderListResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
	@XmlElement(name="PaidOrder", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<PaidOrder> paidOrder = new ArrayList<>();

	public List<PaidOrder> getPaidOrder() {
		return paidOrder;
	}

	public void setPaidOrder(List<PaidOrder> paidOrder) {
		this.paidOrder = paidOrder;
	}
	
}
