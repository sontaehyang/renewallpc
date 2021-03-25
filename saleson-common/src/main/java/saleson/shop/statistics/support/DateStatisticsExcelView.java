package saleson.shop.statistics.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;

import saleson.common.Const;
import saleson.shop.statistics.domain.ShopDateStatistics;


public class DateStatisticsExcelView extends AbstractSXSSFExcelView {
	
	public DateStatisticsExcelView() {
		setFileName("날짜별주문통계_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 1. 데이터 가져오기.
		List<ShopDateStatistics> list = (List<ShopDateStatistics>) model.get("list");
		
	
		
		// 3. 시트 생성
		String sheetTitle = "회원목록"; 
		
		
		// 4. Cell (컬럼) 설정
		short[] columWidth = new short[] {512, 4096, 4096, 8192, 4096};
		String[] titles = new String[] {"일자", "주문방법", "아이디", "이메일", "가입일"};
		
		
		// 5. Row병합(세로)이 필요한 셀 인덱스 설정;
		//int[] mergeRowCellIndexes = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 22, 23, 24, 25 };
		int[] mergeRowCellIndexes = new int[] {};
		
	
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		
		// Table Body
		int rowIndex = 2;
		for (ShopDateStatistics shopDateStatistics : list) {
			
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
			
			// CELL 데이터.
			CellIndex cellIndex = new CellIndex(-1);
			/*setNumber(sheet, 	rowIndex, cellIndex, rowIndex - 1);
			setText(sheet, 		rowIndex, cellIndex, user.getUsername());
			setText(sheet, 		rowIndex, cellIndex, user.getLoginId());
			setTextLeft(sheet, 	rowIndex, cellIndex, user.getEmail());
			setTextLeft(sheet, 	rowIndex, cellIndex, DateUtils.date(user.getCreationDate()));
*/		
			
			rowIndex++;
				

			/* Row 병합이 필요한 경우
			for (int i = 0; i < mergeRowCellIndexes.length; i++) {
				sheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex - 1, mergeRowCellIndexes[i], mergeRowCellIndexes[i])); 
			}
			*/
			
		}
	}
	
	
	/**
	public void createSheetHeader(Sheet sheet, Row row, short[] columWidth, String[] titles, String sheetTitle) {
		// 엑셀 시트의 상단 타이틀, 데이터 테이블의 헤더를 설정한다.
	}
	*/

}
