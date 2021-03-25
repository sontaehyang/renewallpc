package saleson.shop.order.pg.payco.domain;

public class CellphoneSettleInfo {
	private String companyName; // 통신사명(코드)
	private String cellphoneNo; // 휴대폰 번호

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCellphoneNo() {
		return cellphoneNo;
	}
	public void setCellphoneNo(String cellphoneNo) {
		this.cellphoneNo = cellphoneNo;
	}
}
