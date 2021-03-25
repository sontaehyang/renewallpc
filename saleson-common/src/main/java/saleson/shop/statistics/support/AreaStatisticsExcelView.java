package saleson.shop.statistics.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

import saleson.common.Const;
import saleson.shop.statistics.domain.AreaStatsSummary;

public class AreaStatisticsExcelView extends AbstractSXSSFExcelView {
	
	private String type;
	
	public AreaStatisticsExcelView(String type) {
		this.type = type;
		setFileName("지역별 상품판매액_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
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
	    List<AreaStatsSummary> list = (List<AreaStatsSummary>) model.get("areaStatsList");
	    
		// 2. 시트별 데이터 생성
		buildUserSheet(workbook, list);
		
	}

	private void buildUserSheet(SXSSFWorkbook workbook, List<AreaStatsSummary> list) {
		String sheetTitle = "지역별 상품판매액"; 	// 상품정보

		HeaderCell[] headerCells = new HeaderCell[] {
				new HeaderCell(4096, MessageUtils.getMessage("M01367")) ,
				new HeaderCell(1024, MessageUtils.getMessage("M01355")) ,
				new HeaderCell(1024, "") ,
				new HeaderCell(1024, "") ,
				new HeaderCell(1024, "") ,
				new HeaderCell(1024, MessageUtils.getMessage("M00037")) ,
				new HeaderCell(1024, "") ,
				new HeaderCell(1024, "") ,
				new HeaderCell(1024, "") ,
				new HeaderCell(1024, MessageUtils.getMessage("M00358")),
				new HeaderCell(1024, "") ,
				new HeaderCell(1024, "") ,
				new HeaderCell(1024, "")
		};


		HeaderCell[] headerCells2 = new HeaderCell[] {
				new HeaderCell(4096, MessageUtils.getMessage("M01367")),
				new HeaderCell(1024, "건수") ,
				new HeaderCell(1024, "상품판매가") ,
				new HeaderCell(1024, MessageUtils.getMessage("M00452")) ,
				new HeaderCell(1024, MessageUtils.getMessage("M01369")) ,
				new HeaderCell(1024, "건수") ,
				new HeaderCell(1024, "상품판매가") ,
				new HeaderCell(1024, MessageUtils.getMessage("M00452")) ,
				new HeaderCell(1024, MessageUtils.getMessage("M01361")) ,
				new HeaderCell(1024, "건수"),
				new HeaderCell(1024, "상품판매가"),
				new HeaderCell(1024, MessageUtils.getMessage("M00452")) ,
				new HeaderCell(1024, MessageUtils.getMessage("M01369"))
		};
		
		// 5. Row병합(세로)이 필요한 셀 인덱스 설정;
		//int[] mergeRowCellIndexes = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 22, 23, 24, 25 };
		int[] mergeRowCellIndexes = new int[] {0};
		
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
		
		
		for (int j = 0; j < mergeRowCellIndexes.length; j++) {
			sheet.addMergedRegion(new CellRangeAddress(1, 2, mergeRowCellIndexes[j], mergeRowCellIndexes[j]));
		}
		
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 4));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 8));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 12));
		
		
		
		
		// Table Body
		int rowIndex = 3;
		int i = 1;
		for (AreaStatsSummary area : list) {
			
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			
			String address = "";
			if ("SIGUNGU".equals(type)) {
				if (StringUtils.isNotEmpty(area.getSido())) {
					address = area.getSido() + " > ";
				}
				
				address += area.getSigungu();
				
			} else {
				address = area.getSido();
			}
			
			if (StringUtils.isEmpty(address)) {
				address = "정보없음";
			}
			
			setTextLeft(sheet, rowIndex, cellIndex, address);
			
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubSaleCount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubItemPrice())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubDiscountAmount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubSaleAmount())));
			
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubCancelCount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubCancelItemPrice())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubCancelDiscountAmount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubCancelAmount())));
			
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubTotalCount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubTotalItemPrice())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubTotalDiscountAmount())));
			setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat(""+(area.getSubTotalAmount())));
			
			rowIndex++;	
			i++;
		}
	}

}
