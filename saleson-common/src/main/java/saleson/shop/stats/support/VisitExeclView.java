package saleson.shop.stats.support;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import saleson.common.Const;
import saleson.shop.stats.StatsService;
import saleson.shop.stats.domain.DayStats;
import saleson.shop.stats.domain.MonthStats;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class VisitExeclView extends AbstractSXSSFExcelView{

	private List<MonthStats> monthStatsList;
	private StatsSearchParam searchParam;
	HashMap<String, List<DayStats>> dayStatsListByYear;
	
	public VisitExeclView() {
		setFileName("Visit_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 1.데이터 가져오기
		searchParam = (StatsSearchParam)model.get("searchParam");
		monthStatsList = (List<MonthStats>)model.get("monthStatsList");
		dayStatsListByYear =(HashMap<String, List<DayStats>>) model.get("dayStatsListByYear");
		
		// 2. 데이터 생성(시트생성)
	    buildItemSheet(workbook, monthStatsList, searchParam);
		
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<MonthStats> monthStatsList, StatsSearchParam searchParam){
		
		if(monthStatsList == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "visit_main";
		String title = "접속통계 ( "+searchParam.getStartDate()+"~"+searchParam.getEndDate()+" )";

		// 4. Cell (컬럼) 설정	
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(1000, 	"",		""),
				
				new HeaderCell(1000, 	"1",		"1"),
				new HeaderCell(1000, 	"2",		"2"),
				new HeaderCell(1000, 	"3",		"3"),
				new HeaderCell(1000, 	"4",		"4"),
				new HeaderCell(1000, 	"5",		"5"),
				new HeaderCell(1000, 	"6",		"6"),
				new HeaderCell(1000, 	"7",		"7"),
				new HeaderCell(1000, 	"8",		"8"),
				new HeaderCell(1000, 	"9",		"9"),
				new HeaderCell(1000, 	"10",		"10"),
				
				new HeaderCell(1000, 	"11",		"11"),
				new HeaderCell(1000, 	"12",		"12"),
				new HeaderCell(1000, 	"13",		"13"),
				new HeaderCell(1000, 	"14",		"14"),
				new HeaderCell(1000, 	"15",		"15"),
				new HeaderCell(1000, 	"16",		"16"),
				new HeaderCell(1000, 	"17",		"17"),
				new HeaderCell(1000, 	"18",		"18"),
				new HeaderCell(1000, 	"19",		"19"),
				new HeaderCell(1000, 	"20",		"20"),
				
				new HeaderCell(1000, 	"21",		"21"),
				new HeaderCell(1000, 	"22",		"22"),
				new HeaderCell(1000, 	"23",		"23"),
				new HeaderCell(1000, 	"24",		"24"),
				new HeaderCell(1000, 	"25",		"25"),
				new HeaderCell(1000, 	"26",		"26"),
				new HeaderCell(1000, 	"27",		"27"),
				new HeaderCell(1000, 	"28",		"28"),
				new HeaderCell(1000, 	"29",		"29"),
				new HeaderCell(1000, 	"30",		"30"),
				
				new HeaderCell(1000, 	"31",		"31"),
				
				new HeaderCell(1000, 	"총",		"총")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		
		// Table Body
		int rowIndex = 2;
		for (MonthStats monthStats : monthStatsList) {
			
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			
			setText(sheet, 			rowIndex, cellIndex, monthStats.getTitle());
			
			List<DayStats>  dayStats = dayStatsListByYear.get(monthStats.getYearMonth());
			
			int dayCount = 0;
			int totalVisitCount = 0;
			for (DayStats dayStat :  dayStats) {
				
				setText(sheet, 	rowIndex, cellIndex, dayStat.getVisitCount());
				totalVisitCount += Integer.parseInt(dayStat.getVisitCount());
				
				dayCount++;
				
			 }
			 
			int remainDay = 31 - dayCount;
			
			for (int i=0; i < remainDay;i++) {
				setText(sheet, 	rowIndex, cellIndex, "");
			}
			
			setText(sheet, 	rowIndex, cellIndex, String.valueOf(totalVisitCount));
			
			 rowIndex++;
		}
		
		
	}
	
}
