package saleson.shop.order.admin.support;

import com.onlinepowers.framework.util.PoiUtils;
import org.apache.poi.ss.usermodel.Row;
import saleson.common.utils.ShopUtils;

public class ExcelTemplateV01 {
	
	public ExcelTemplateV01() {}
	public ExcelTemplateV01(Row row) {
		
		setOrderGroupCode(ShopUtils.getString(row.getCell(0)));
		setLoginId(ShopUtils.getString(row.getCell(1)));
		setBuyerName(ShopUtils.getString(row.getCell(2)));
		setBuyerMobile(ShopUtils.getString(row.getCell(3)));
		setBuyerEmail(ShopUtils.getString(row.getCell(4)));
		setReceiveName(ShopUtils.getString(row.getCell(5)));
		setReceiveZipcode(ShopUtils.getString(row.getCell(6)));
		setReceiveAddress(ShopUtils.getString(row.getCell(7)));
		setReceiveAddressDetail(ShopUtils.getString(row.getCell(8)));
		setReceivePhone(ShopUtils.getString(row.getCell(9)));
		setReceiveMobile(ShopUtils.getString(row.getCell(10)));
		setMemo(ShopUtils.getString(row.getCell(11)));
		setItemUserCode(ShopUtils.getString(row.getCell(12)));
		setQuantity(PoiUtils.getInt(row.getCell(13)));
		
	}
	
	private String orderGroupCode;
	private String loginId;
	private String buyerName;
	private String buyerMobile;
	private String buyerEmail;
	private String receiveName;
	private String receiveZipcode;
	private String receiveAddress;
	private String receiveAddressDetail;
	private String receivePhone;
	private String receiveMobile;
	private String memo;
	private String itemUserCode;
	private int quantity;
	
	public String getOrderGroupCode() {
		return orderGroupCode;
	}
	public void setOrderGroupCode(String orderGroupCode) {
		this.orderGroupCode = orderGroupCode;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerMobile() {
		return buyerMobile;
	}
	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
