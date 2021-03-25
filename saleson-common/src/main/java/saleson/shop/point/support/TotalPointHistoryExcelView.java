package saleson.shop.point.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinepowers.framework.util.MessageUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

import saleson.common.Const;
import saleson.shop.point.domain.PointHistory;

public class TotalPointHistoryExcelView extends AbstractSXSSFExcelView {
	public TotalPointHistoryExcelView() {
		setFileName(MessageUtils.getMessage("M00246") + " 사용 내역_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
	    // 1. 데이터 생성(시트생성)
	    buildItemSheet(workbook, (List<PointHistory>) model.get("list"));
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<PointHistory> list) {
		if(list == null){
			return;
		}

		// 2. 시트생성
		String sheetTitle = "list";
		String title = MessageUtils.getMessage("M00246") + " 사용 내역";
		
		// 3. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
			new HeaderCell(1000, 	"일자"),
			new HeaderCell(1000, 	"회원 이름"),
			new HeaderCell(4000, 	"회원 아이디"),
			new HeaderCell(1000, 	"주문번호"),
			new HeaderCell(1000, 	"구분"),
			new HeaderCell(20000, 	"적요"),
			new HeaderCell(1000, 	"금액")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		for(PointHistory point : list) {
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			
			setText(sheet, 			rowIndex, cellIndex, DateUtils.date(point.getCreatedDate()));
			setText(sheet, 			rowIndex, cellIndex, point.getUserName());
			setText(sheet, 			rowIndex, cellIndex, point.getLoginId());
			setText(sheet, 			rowIndex, cellIndex, point.getOrderCodeLabel());
			setText(sheet, 			rowIndex, cellIndex, point.getSubject());
			setTextLeft(sheet, 			rowIndex, cellIndex, point.getReason());
			setNumberFormat(sheet, 	rowIndex, cellIndex, point.getPoint());
		
			rowIndex++;
		}
	}
}
