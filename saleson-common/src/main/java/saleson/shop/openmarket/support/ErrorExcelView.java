package saleson.shop.openmarket.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import saleson.common.Const;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorExcelView extends AbstractSXSSFExcelView {

	private boolean makeTitle = true;
	
	public ErrorExcelView(String title) {
		
		if ("뷰티콜".equals(title)) {
			this.makeTitle = false;
		} else if ("올뎃".equals(title)) {
			this.makeTitle = false;
		}
		
		setFileName(title + "_주문 생성 실패 리포트_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
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
	    List<HashMap<String, String>> errors = (List<HashMap<String, String>>) model.get("errors");
	    String[] header = (String[]) model.get("header");
	    
	    // 2. 시트별 데이터 생성
	 	buildUserSheet(workbook, errors, header);
	}

	/**
	 * 엑셀 시트의 상단 타이틀, 데이터 테이블의 헤더를 설정한다.
	 * @param sheet
	 * @param row
	 * @param headerCells
	 * @param sheetTitle
	 */
	protected void createSheetHeader(Sheet sheet, Row row,
			HeaderCell[] headerCells) {
		
		//row.setHeight((short) 800);
		
		int columCount = headerCells.length;
		for(int i = 0; i < headerCells.length; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) + headerCells[i].getWidth());
		}
		
		Cell[] cells = new Cell[columCount];
		for(int i =0; i < cells.length; i++){
			cells[i] = row.createCell(i);
			cells[i].setCellStyle(titleStyle);
		}

		row = sheet.createRow((short) 0);
		row.setHeight((short) 512);
		
		for(int i = 0; i < cells.length; i++){
			cells[i] = row.createCell(i);
			cells[i].setCellStyle(headerStyle);
			cells[i].setCellType(Cell.CELL_TYPE_STRING); //개행 문자 적용
			
			// 셀 타이틀 설정.
			cells[i].setCellValue(headerCells[i].getTitle());
			
			
			// 셀 코멘트
			if (headerCells[i].getComment() != null && !headerCells[i].getComment().equals("")) {
				setComment(cells[i], 1, i, headerCells[i].getComment().replace(", ", ",").replace(",", "\n"), headerCells[i].getCommentCol(), headerCells[i].getCommentRow());
			}
			
		}
	}
	
	private void buildUserSheet(SXSSFWorkbook workbook, List<HashMap<String, String>> list, String[] header) {
		HeaderCell[] headerCells = new HeaderCell[0];
		
		int i = 0;
		for(String s : header) {
			headerCells = (HeaderCell[]) ArrayUtils.add(headerCells, new HeaderCell(15, s));
		}
		
		headerCells = (HeaderCell[]) ArrayUtils.add(headerCells, new HeaderCell(55, "비고"));
		headerCells = (HeaderCell[]) ArrayUtils.add(headerCells, new HeaderCell(15, "상품 번호"));
		headerCells = (HeaderCell[]) ArrayUtils.add(headerCells, new HeaderCell(15, "옵션 번호"));
		
		Sheet sheet = workbook.createSheet("list");
		Row row = sheet.createRow((short) 0);
	
		int rowIndex = 1;
		if (this.makeTitle) {
			createSheetHeader(sheet, row, headerCells, "발송준비중내역 - 등록 오류 목록");
			rowIndex = 2;
		} else {
			createSheetHeader(sheet, row, headerCells);
		}
		
		for(HashMap<String, String> map : list) {
			
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			for(String key : map.keySet()) {
				String value = (String) map.get(key);
				setTextLeft(sheet, 			rowIndex, cellIndex, value);
			}
			
			setTextLeft(sheet, 			rowIndex, cellIndex, "");
			setTextLeft(sheet, 			rowIndex, cellIndex, "");
			
			rowIndex++;
		}
	}
}
