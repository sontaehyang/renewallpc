package saleson.shop.order.admin.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.utils.ShopUtils;
import saleson.shop.mall.auction.AuctionServiceImpl;
import saleson.shop.order.admin.support.OrderAdminData;

public class BuyAdminReceiver {
	private static final Logger log = LoggerFactory.getLogger(BuyAdminReceiver.class);

	public BuyAdminReceiver() {}
	public BuyAdminReceiver(OrderAdminData data) {
		
		setReceiveZipcode(data.getReceiveZipcode());
		setReceiveAddress(data.getReceiveAddress());
		setReceiveAddressDetail(data.getReceiveAddressDetail());
		setReceiveName(data.getReceiveName());
		
		try {
			setReceiveSido(ShopUtils.getSido(data.getReceiveAddress()));
			setReceiveSigungu(ShopUtils.getSigungu(data.getReceiveAddress()));
			setReceiveEupmyeondong(ShopUtils.getEupmyeondong(data.getReceiveAddress()));
		} catch(Exception e) {
			log.error("ERROR: 시도, 시군구 정보 파싱 오류 ({})", e.getMessage(), e);
		}
		
		setReceivePhone(data.getReceivePhone());
		setReceiveMobile(data.getReceiveMobile());
		setContent(data.getContent());
	}
	
	private String orderCode;
	private int orderSequence;
	private int shippingInfoSequence;
	private String receiveZipcode;
	private String receiveNewZipcode;
	private String receiveCompanyName;
	private String receiveSido;
	private String receiveSigungu;
	private String receiveEupmyeondong;
	private String receiveAddress;
	private String receiveAddressDetail;
	private String receiveName;
	private String receivePhone;
	private String receiveMobile;
	private String content;
	
	public void setShipping(String islandType, String shippingPaymentType) {
		BuyAdminShipping shipping = new BuyAdminShipping();
		if (this.items.isEmpty()) {
			return;
		}

		this.itemGroups = shipping.getShippingGroups(this.items, islandType, shippingPaymentType);
	}
	
	private List<BuyAdminShipping> itemGroups;
	private List<BuyAdminItem> items;
	public List<BuyAdminItem> getItems() {
		return items;
	}
	public void setItems(List<BuyAdminItem> items) {
		this.items = items;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getShippingInfoSequence() {
		return shippingInfoSequence;
	}
	public void setShippingInfoSequence(int shippingInfoSequence) {
		this.shippingInfoSequence = shippingInfoSequence;
	}
	public String getReceiveZipcode() {
		return receiveZipcode;
	}
	public void setReceiveZipcode(String receiveZipcode) {
		this.receiveZipcode = receiveZipcode;
	}
	public String getReceiveNewZipcode() {
		return receiveNewZipcode;
	}
	public void setReceiveNewZipcode(String receiveNewZipcode) {
		this.receiveNewZipcode = receiveNewZipcode;
	}
	public String getReceiveCompanyName() {
		return receiveCompanyName;
	}
	public void setReceiveCompanyName(String receiveCompanyName) {
		this.receiveCompanyName = receiveCompanyName;
	}
	public String getReceiveSido() {
		return receiveSido;
	}
	public void setReceiveSido(String receiveSido) {
		this.receiveSido = receiveSido;
	}
	public String getReceiveSigungu() {
		return receiveSigungu;
	}
	public void setReceiveSigungu(String receiveSigungu) {
		this.receiveSigungu = receiveSigungu;
	}
	public String getReceiveEupmyeondong() {
		return receiveEupmyeondong;
	}
	public void setReceiveEupmyeondong(String receiveEupmyeondong) {
		this.receiveEupmyeondong = receiveEupmyeondong;
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
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public String getReceiveMobile() {
		return receiveMobile;
	}
	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<BuyAdminShipping> getItemGroups() {
		return itemGroups;
	}
	public void setItemGroups(List<BuyAdminShipping> itemGroups) {
		this.itemGroups = itemGroups;
	}
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	
}
