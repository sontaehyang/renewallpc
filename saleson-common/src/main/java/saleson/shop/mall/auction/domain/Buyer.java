package saleson.shop.mall.auction.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Buyer")
@XmlAccessorType (XmlAccessType.FIELD)
public class Buyer {

	@XmlAttribute(name="Name")
	private String name;
	
	@XmlAttribute(name="Tel")
	private String tel;
	
	@XmlAttribute(name="MobileTel")
	private String mobileTel;
	
	@XmlAttribute(name="Email")
	private String email;
	
	@XmlAttribute(name="PostNo")
	private String postNo;
	
	@XmlAttribute(name="AddressPost")
	private String addressPost;
	
	@XmlAttribute(name="AddressDetail")
	private String addressDetail;
	
	@XmlAttribute(name="AddressRoadName")
	private String addressRoadName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobileTel() {
		return mobileTel;
	}

	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getAddressPost() {
		return addressPost;
	}

	public void setAddressPost(String addressPost) {
		this.addressPost = addressPost;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getAddressRoadName() {
		return addressRoadName;
	}

	public void setAddressRoadName(String addressRoadName) {
		this.addressRoadName = addressRoadName;
	}
	
}
