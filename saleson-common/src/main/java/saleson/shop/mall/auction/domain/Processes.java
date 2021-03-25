package saleson.shop.mall.auction.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Processes")
@XmlAccessorType (XmlAccessType.FIELD)
public class Processes {

	@XmlAttribute(name="Detail")
	private String detail; // 사건 발생 상세
	
	@XmlAttribute(name="EventDate")
	private String eventDate; // 사건 발생일

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	
	
	
}
