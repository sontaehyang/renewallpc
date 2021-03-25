package saleson.shop.order.domain;

public class OrderList extends OrderItem {
	private String loginId;
	private String userName;
	private String receiveName;
	private String memo;

	private String buyerName;

	private String receiveMobile;
	private String receiveZipcode;
	private String receiveNewZipcode;
	private String receiveAddress;
	private String receiveAddressDetail;
	private String mobile;

	private String isPoint;


    public String getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(String isPoint) {
        this.isPoint = isPoint;
    }

    public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getReceiveMobile() {
		return receiveMobile;
	}
	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}
	public String getReceiveZipcode() {
		return receiveZipcode;
	}
	public void setReceiveZipcode(String receiveZipcode) {
		this.receiveZipcode = receiveZipcode;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getReceiveAddressDetail() {
		return receiveAddressDetail;
	}
	public void setReceiveAddressDetail(String receiveAddressDetail) {
		this.receiveAddressDetail = receiveAddressDetail;
	}

	public String getReceiveNewZipcode() {
		return receiveNewZipcode;
	}

	public void setReceiveNewZipcode(String receiveNewZipcode) {
		this.receiveNewZipcode = receiveNewZipcode;
	}
}
