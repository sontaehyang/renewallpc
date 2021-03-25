package saleson.shop.qna.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.shop.qna.domain.Qna;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;


public class QnaExcelView extends AbstractSXSSFExcelView {
	
	public QnaExcelView() {
		setFileName("QNA_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Cookie cookie = new Cookie("DOWNLOAD_STATUS", "complete");
		cookie.setHttpOnly(true);
		//cookie.setMaxAge(60*60*24);				// 쿠키 유지 기간 - 1일
	    cookie.setPath("/");					// 모든 경로에서 접근 가능하도록 
	    response.addCookie(cookie);				// 쿠키저장
		
		
		// 1. 데이터 가져오기.
		List<Qna> list = (List<Qna>) model.get("qna");
		
		
		// 2. 시트별 데이터 생성
		// 2-1. 상품 Sheet (OP_ITEM)
		buildQnaSheet(workbook, list);
		
	}

	
	/**
	 * 상품 SHEET
	 * @param workbook
	 * @param list
	 */
	private void buildQnaSheet(SXSSFWorkbook workbook, List<Qna> list) {
		if (list == null) {
			return;
		}
		
		// 3. 시트 생성
		String sheetTitle = "Q&A";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			 new HeaderCell(8192, 	"USER_CORD"),	
			 new HeaderCell(8192, 	"QUESTION"),	
			 new HeaderCell(8192, 	"ANSWER")
		};
		
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		createSheetHeader(sheet, row, headerCells, sheetTitle);
		
		
		// Table Body
		int rowIndex = 2;
		for (Qna qna : list) {
			
			/**
			 *  ROW병합이 있는 경우 설정
			 * list2 = user.getSubList();
				int entrySize = list2.size();
				int startRowIndex = rowIndex;
				int endRowIndex = rowIndex + entrySize;
			 */
				
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			setTextLeft(sheet, 		rowIndex, cellIndex, qna.getUserCode());			// 상품명	
			setTextLeft(sheet, 		rowIndex, cellIndex, qna.getQuestion());			// 상품명	
			setTextLeft(sheet, 		rowIndex, cellIndex, qna.getAnswer());			// 상품명	

			rowIndex++;
		}
	}
}
