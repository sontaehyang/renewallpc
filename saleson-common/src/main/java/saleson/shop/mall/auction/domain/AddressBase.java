package saleson.shop.mall.auction.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddressBase")
@XmlAccessorType (XmlAccessType.FIELD)
public class AddressBase {
	
	@XmlAttribute(name="Name")
	private String name; // 이름
	
	@XmlAttribute(name="Tel")
	private String tel; // 전화번호
	
	@XmlAttribute(name="MobileTel")
	private String mobileTel; // 휴대전화 번호
	
	@XmlAttribute(name="Email")
	private String email; // 이메일
	
	@XmlAttribute(name="PostNo")
	private String postNo; // 우편번호
	
	@XmlAttribute(name="AddressPost")
	private String addressPost; // 주소
	
	@XmlAttribute(name="AddressDetail")
	private String addressDetail; // 주소 상세
	
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
