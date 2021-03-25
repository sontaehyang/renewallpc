package saleson.shop.mall.auction.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetShippingDetailInfoListResponse", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetShippingDetailInfoListResult {

	@XmlElementWrapper(name="GetShippingDetailInfoListResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
	@XmlElement(name="ShippingDetailInfo", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<ShippingDetailInfo> shippingDetailInfo;

	public List<ShippingDetailInfo> getShippingDetailInfo() {
		return shippingDetailInfo;
	}

	public void setShippingDetailInfo(List<ShippingDetailInfo> shippingDetailInfo) {
		this.shippingDetailInfo = shippingDetailInfo;
	}
	
}
