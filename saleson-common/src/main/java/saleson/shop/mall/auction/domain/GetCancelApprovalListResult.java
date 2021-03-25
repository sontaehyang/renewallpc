package saleson.shop.mall.auction.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetCancelApprovalListResponse", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetCancelApprovalListResult {

	@XmlElementWrapper(name="GetCancelApprovalListResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
	@XmlElement(name="CancelApproval", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<CancelApproval> list;

	public List<CancelApproval> getList() {
		return list;
	}

	public void setList(List<CancelApproval> list) {
		this.list = list;
	}

}
