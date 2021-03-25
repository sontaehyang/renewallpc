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
import saleson.shop.statistics.domain.BaseRevenueStatistics;
import saleson.shop.statistics.domain.PaymentStatistics;
import saleson.shop.statistics.domain.ShopDateStatistics;
import saleson.shop.statistics.domain.ShopItemStatistics;
import saleson.shop.statistics.domain.TotalRevenueStatistics;

import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;


public class PaymentStatisticsExcelView extends AbstractSXSSFExcelView {
	
	public PaymentStatisticsExcelView() {
		setFileName("결제타입별 통계_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
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
	    List<CodeInfo> codeList = (List<CodeInfo>) model.get("approvalTypes");
	    List<PaymentStatistics> list = (List<PaymentStatistics>) model.get("list");
	    List<String> days = (List<String>) model.get("days");
		
		// 2. 시트별 데이터 생성
		buildUserSheet(workbook, codeList, list, days);
		
	}

	private void buildUserSheet(SXSSFWorkbook workbook, List<CodeInfo> codeList, List<PaymentStatistics> list, List<String> days) {
		String sheetTitle = "결제타입별 통계"; 	// 결제타입별
		
		HeaderCell[] headerCells = new HeaderCell[ 2 + (codeList.size()*3) ];
		
		headerCells[0] = new HeaderCell(512, "날짜");
		headerCells[1] = new HeaderCell(512, "매출총액");
		
		int index = 2;
		
		for (CodeInfo date : codeList) {
			headerCells[index] = new HeaderCell(512, date.getLabel());
			index++;
			headerCells[index] = new HeaderCell(512, "");
			index++;
			headerCells[index] = new HeaderCell(512, "");
			index++;
		}
		
		HeaderCell[] headerCells2 = new HeaderCell[ 2 + (codeList.size()*3) ];
		
		headerCells2[0] = new HeaderCell(512, "날짜");
		headerCells2[1] = new HeaderCell(512, "매출총액");
		
		int index2 = 2;
		
		for (CodeInfo date : codeList) {
			headerCells2[index2] = new HeaderCell(512, "PC");
			index2++;
			headerCells2[index2] = new HeaderCell(512, "모바일");
			index2++;
			headerCells2[index2] = new HeaderCell(512, "기타");
			index2++;
		}
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		
		Row row = sheet.createRow((short) 0);
		
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
		
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
		
		int idx = 2;
		for (CodeInfo date : codeList) {
			sheet.addMergedRegion(new CellRangeAddress(1, 1, idx, idx+2));
			idx = idx+3;
		}

		
		// Table Body
		int rowIndex = 3;
		int i = 1;
		for (String day : days) {
			
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			
			int dayTotal = 0;
			
			for(PaymentStatistics item : list){
				if(day.equals(item.getPayDate())){
					dayTotal = dayTotal + (item.getPayAmount() - item.getCancelAmount());
				}
			}
			
			setText(sheet, rowIndex, cellIndex, DateUtils.date(day));
			setNumberFormat(sheet, rowIndex, cellIndex, dayTotal);
			
			for (CodeInfo type : codeList) {
				int value1 = 0;
				int value2 = 0;
				int value3 = 0;
				for(PaymentStatistics item : list){
					String approvalType = item.getApprovalType();
					if(approvalType.equals(type.getKey().getId()) && day.equals(item.getPayDate()) && "WEB".equals(item.getDeviceType())){
						value1 = value1 + (item.getPayAmount() - item.getCancelAmount());
					}
					if(approvalType.equals(type.getKey().getId()) && day.equals(item.getPayDate()) && "MOBILE".equals(item.getDeviceType())){
						value2 = value2 + (item.getPayAmount() - item.getCancelAmount());
					}
					if(approvalType.equals(type.getKey().getId()) && day.equals(item.getPayDate()) && !("WEB".equals(item.getDeviceType()) || "MOBILE".equals(item.getDeviceType()))){
						value3 = value3 + (item.getPayAmount() - item.getCancelAmount());
					}
				}
				setNumberFormat(sheet, rowIndex, cellIndex, value1);
				setNumberFormat(sheet, rowIndex, cellIndex, value2);
				setNumberFormat(sheet, rowIndex, cellIndex, value3);
			}
			
			rowIndex++;
			i++;
		}
		
	}
	
}
