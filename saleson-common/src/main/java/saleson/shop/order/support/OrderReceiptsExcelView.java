package saleson.shop.order.support;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.utils.ShopUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.order.domain.OrderReceiptsExcel;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.NumberUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;

public class OrderReceiptsExcelView extends AbstractSXSSFExcelView {

	public OrderReceiptsExcelView(String orderCode) {
		setFileName(orderCode + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Cookie cookie = new Cookie("DOWNLOAD_STATUS", "complete");
		cookie.setHttpOnly(true);
	    cookie.setPath("/");					// 모든 경로에서 접근 가능하도록 
	    response.addCookie(cookie);				// 쿠키저장
		
	    // 1. 데이터 가져오기.
		OrderReceiptsExcel order = (OrderReceiptsExcel) model.get("order");
		
		// 2. 시트별 데이터 생성
		buildUserSheet(workbook, order);
	}

	private void buildUserSheet(SXSSFWorkbook workbook, OrderReceiptsExcel order) {
		String sheetTitle = MessageUtils.getMessage("M01056");	// 영 수 증 
		
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		// 제목 생성
		createSheetHeader(sheet, row, sheetTitle);
		
		// 빈칸 삽입
		row = sheet.createRow((short) 1);
		row.setHeight((short) 200);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
		
		// 주문번호
		buildRow(sheet, 2, "No", order.getOrderCode() + " ("+ MessageUtils.getMessage("M00013") +")", 400);
		
		// 이름
		buildRow(sheet, 3, MessageUtils.getMessage("M00005"), order.getUserName() + " ("+ MessageUtils.getMessage("M00014") +")", 400);
		
		// 금액
		buildRow(sheet, 4, MessageUtils.getMessage("M00654"), NumberUtils.formatNumber(order.getOrderPayAmount(), "#,##0") + "원 ("+ MessageUtils.getMessage("M01057") +")", 400);
		
		// 비고 - 상품 지불금액으로서 상기의 금액에 영수하였습니다.
		buildRow(sheet, 5, MessageUtils.getMessage("M01058"), MessageUtils.getMessage("M01059"), 400);
		
		
		Config shopConfig = ShopUtils.getConfig();
		
		// 회사명
		buildRow(sheet, 6, MessageUtils.getMessage("M00105"), shopConfig.getCompanyName(), 400);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(shopConfig.getPost() + " " + shopConfig.getAddress() + " " + shopConfig.getAddressDetail() + "\n");
		sb.append("TEL : " + shopConfig.getTelNumber() + "\n");
		sb.append("FAX : " + shopConfig.getFaxNumber());
		
		// 주소
		buildRow(sheet, 7, MessageUtils.getMessage("M00118"), sb.toString(), 900);
	}

	protected void createSheetHeader(Sheet sheet, Row row, String sheetTitle) {
		row.setHeight((short) 800);

		CellIndex cellIndex = new CellIndex(0);
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
		
		sheet.setColumnWidth(0, 512);
		sheet.setColumnWidth(1, 8192);
		sheet.setColumnWidth(2, 20000);

		//setText(sheet, 0, cellIndex, "");
		setText(sheet, 0, cellIndex, sheetTitle);
		setText(sheet, 0, cellIndex, "");
	}
	
	private void buildRow(Sheet sheet, int rowIndex, String title, String value, int rowHeight) {
		
		Row row = sheet.createRow((short) rowIndex);
		row.setHeight((short) rowHeight);
		
		CellIndex cellIndex = new CellIndex(0);
		//setText(sheet, rowIndex, cellIndex, "");
		
		setText(sheet, rowIndex, cellIndex, title);
		Cell cell = getCell(sheet, rowIndex, cellIndex.getIndex());
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellStyle(headerStyle);
		
		setTextLeft(sheet, rowIndex, cellIndex, value);
	}
}