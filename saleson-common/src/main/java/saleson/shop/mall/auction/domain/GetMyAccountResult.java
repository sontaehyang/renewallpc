package saleson.shop.mall.auction.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetMyAccountResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetMyAccountResult {
	
	@XmlElement(name="MyAccount", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private MyAccount myAccount;

	public MyAccount getMyAccount() {
		return myAccount;
	}

	public void setMyAccount(MyAccount myAccount) {
		this.myAccount = myAccount;
	}
		
}
