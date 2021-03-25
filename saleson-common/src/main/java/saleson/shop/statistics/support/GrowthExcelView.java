package saleson.shop.statistics.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.statistics.domain.Growth;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class GrowthExcelView extends AbstractSXSSFExcelView {

	public GrowthExcelView() {
		setFileName("신장률_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		buildItemSheet(workbook, (Growth)model.get("growth"));
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, Growth growth) {
		
		// 3. 시트생성
		String sheetTitle = "list";
		String title = "신장률";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
			new HeaderCell(10, 	growth.getLastYear() + "년 일자"),
			new HeaderCell(10, 	growth.getLastYear() + "년 매출액"),
			new HeaderCell(10, 	growth.getNowYear() + "년 일자"),
			new HeaderCell(10, 	growth.getNowYear() + "년 매출액"),
			new HeaderCell(10, 	"신장률")
		};
		
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		int rowIndex = 2;
		for(HashMap<String, Double> list : growth.getList()) {
			String lastDate = "";
			String nowDate = "";
			
			double lastData = 0;
			double nowData = 0;
			for(String key : list.keySet()) {
				
				if (key.startsWith(growth.getLastYear())) {
					lastDate = key;
					lastData = (double) list.get(key);
				}
				
				if (key.startsWith(growth.getNowYear())) {
					nowDate = key;
					nowData = (double) list.get(key);
				}
				
			}
			
			CellIndex cellIndex = new CellIndex(-1);
			setText(sheet, 			rowIndex, cellIndex, lastDate);
			setTextRight(sheet,		rowIndex, cellIndex, StringUtils.numberFormat(lastData)+"원");
			
			if (StringUtils.isNotEmpty(nowDate)) {
				setText(sheet, 			rowIndex, cellIndex, nowDate);
				setTextRight(sheet,		rowIndex, cellIndex, StringUtils.numberFormat(nowData)+"원");
				
				setTextRight(sheet,		rowIndex, cellIndex, ShopUtils.getGrowthPercent(Double.toString(nowData), Double.toString(lastData)));
			} else {
				setText(sheet, 			rowIndex, cellIndex, "");
				setTextRight(sheet,		rowIndex, cellIndex, "");
				setTextRight(sheet,		rowIndex, cellIndex, "");
			}
			
			rowIndex++;
		}
		
	}
	
}
