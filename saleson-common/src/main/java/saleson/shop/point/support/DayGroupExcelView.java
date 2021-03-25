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
import saleson.shop.point.domain.PointDayGroup;

public class DayGroupExcelView extends AbstractSXSSFExcelView {
	public DayGroupExcelView() {
		setFileName(MessageUtils.getMessage("M00246") + " 일별 발생/사용현황 내역_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
	    // 1. 데이터 생성(시트생성)
	    buildItemSheet(workbook, (List<PointDayGroup>) model.get("list"));
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<PointDayGroup> list) {
		if(list == null){
			return;
		}

		// 2. 시트생성
		String sheetTitle = "list";
		String title = MessageUtils.getMessage("M00246") + " 일별 발생/사용현황 내역";
		
		// 3. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
			new HeaderCell(2000, 	"일자"),
			new HeaderCell(2000, 	MessageUtils.getMessage("M00246")+ "(적립)"),
			new HeaderCell(2000, 	MessageUtils.getMessage("M00246") + "(사용)")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		for(PointDayGroup point : list) {
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			
			setText(sheet, 			rowIndex, cellIndex, DateUtils.date(point.getGroupKey()));
			setNumberFormat(sheet, 	rowIndex, cellIndex, point.getSavedPoint());
			setNumberFormat(sheet, 	rowIndex, cellIndex, point.getUsedPoint());
			
			rowIndex++;
		}
	}
}
