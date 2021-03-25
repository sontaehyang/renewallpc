package saleson.shop.openmarket.support;

import org.apache.poi.ss.usermodel.Row;

@SuppressWarnings("serial")
public class OpenMarketException extends RuntimeException {

	private Row excelRow;
	
	public OpenMarketException() {
		super("잘못된 접근입니다.");
	}
	
	public OpenMarketException(String message) {
		super(message);
	}
	
	public OpenMarketException(Row excelRow, String message) {
		super(message);
		this.setExcelRow(excelRow);
	}

	public Row getExcelRow() {
		return excelRow;
	}

	public void setExcelRow(Row excelRow) {
		this.excelRow = excelRow;
	}
}
