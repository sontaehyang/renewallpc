package saleson.shop.statistics.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.statistics.domain.ShopDateStatistics;
import saleson.shop.statistics.domain.ShopItemDetailStatistics;
import saleson.shop.statistics.domain.ShopItemStatistics;
import saleson.shop.statistics.domain.ShopUserOrderStatistics;
import saleson.shop.statistics.domain.ShopUserStatistics;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;


public class UserStatisticsExcelView extends AbstractSXSSFExcelView {
	
	public UserStatisticsExcelView() {
		setFileName("회원별 통계_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
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
	    List<ShopUserStatistics> list = (List<ShopUserStatistics>) model.get("list");
		
		// 2. 시트별 데이터 생성
		buildUserSheet(workbook, list);
		
	}

	private void buildUserSheet(SXSSFWorkbook workbook, List<ShopUserStatistics> list) {
		String sheetTitle = "회원별 통계"; 	// 상품정보
		
		HeaderCell[] headerCells = new HeaderCell[] {
				new HeaderCell(1024, MessageUtils.getMessage("M01356")) ,
				new HeaderCell(512, MessageUtils.getMessage("M01355")) ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, MessageUtils.getMessage("M00037")) ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, MessageUtils.getMessage("M00064")),
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") ,
				new HeaderCell(512, "") 
			
		};
		
		
		HeaderCell[] headerCells2 = new HeaderCell[] {
			new HeaderCell(1024, MessageUtils.getMessage("M01377")) ,
			new HeaderCell(512, MessageUtils.getMessage("M01357")) ,
			new HeaderCell(512, "원가") ,
			new HeaderCell(512, MessageUtils.getMessage("M00627")) ,
			new HeaderCell(512, MessageUtils.getMessage("M00452")) ,
			new HeaderCell(512, MessageUtils.getMessage("M00069")) ,
			new HeaderCell(512, MessageUtils.getMessage("M01357")) ,
			new HeaderCell(512, "원가") ,
			new HeaderCell(512, MessageUtils.getMessage("M00627")) ,
			new HeaderCell(512, MessageUtils.getMessage("M00452")) ,
			new HeaderCell(512, MessageUtils.getMessage("M01361")) ,
			new HeaderCell(512, "원가") ,
			new HeaderCell(512, MessageUtils.getMessage("M00627")) ,
			new HeaderCell(512, MessageUtils.getMessage("M00452")) ,
			new HeaderCell(512, MessageUtils.getMessage("M01358")) ,
			new HeaderCell(512, "이익률") 
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		
		Row row = sheet.createRow((short) 0);
		
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, headerCells, sheetTitle);
		
		row = sheet.createRow((short) 2);
		row.setHeight((short) 512);
		
		Cell[] cells = new Cell[headerCells2.length];
		
		for (int j = 0; j < headerCells2.length; j++) {
			cells[j] = row.createCell(j);
			cells[j].setCellType(Cell.CELL_TYPE_STRING); //개행 문자 적용
			cells[j].setCellStyle(headerStyle);
			cells[j].setCellValue(headerCells2[j].getTitle());
			
		}

		sheet.autoSizeColumn(0);
		sheet.setColumnWidth(0, (short) 8192);
		
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
		
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 7));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 8, 14));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 15, 21));
		
		
		// Table Body
		int rowIndex = 3;
		int i = 3;
		for (ShopUserStatistics date : list) {
			
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 800);
			
			CellIndex cellIndex = new CellIndex(-1);
			String name = date.getUserName();
			if (StringUtils.isNotEmpty(date.getLoginId())) {
				name += "\n(" + date.getLoginId() + ")";
			}
			
			setTextLeft(sheet, rowIndex, cellIndex, name);
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalPayCount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalPayCostPrice())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalPayItemPrice())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalPayDiscountAmount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalPay())));
			
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalCancelCount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalCancelCostPrice())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalCancelItemPrice())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalCancelDiscountAmount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalCancel())));
			
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalPayCostPrice() + date.getTotalCancelCostPrice())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalPayItemPrice() + date.getTotalCancelItemPrice())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalPayDiscountAmount() + date.getTotalCancelDiscountAmount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(date.getTotalPay() + date.getTotalCancel())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+ShopUtils.getRevenuePercent(Double.toString(date.getTotalPayItemPrice() + date.getTotalCancelItemPrice()), Double.toString(date.getTotalPayCostPrice() + date.getTotalCancelCostPrice()))));
			
			
			rowIndex++;	
			i++;
		}
	}
	
}
