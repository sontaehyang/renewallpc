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

import saleson.common.Const;
import saleson.shop.statistics.domain.*;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;


public class YearStatisticsExcelView extends AbstractSXSSFExcelView {

	public YearStatisticsExcelView() {
		setFileName("년도별 통계_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
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
		List<DateStatsSummary> list = (List<DateStatsSummary>) model.get("list");
		StatsSummary total = (StatsSummary) model.get("total");

		// 2. 시트별 데이터 생성
		buildUserSheet(workbook, list, total);

	}

	private void buildUserSheet(SXSSFWorkbook workbook, List<DateStatsSummary> list, StatsSummary total) {
		String sheetTitle = "년도별 통계"; 	// 상품정보

		HeaderCell[] headerCells = new HeaderCell[] {
				new HeaderCell(512, MessageUtils.getMessage("M01377")) , //일자
				new HeaderCell(512, MessageUtils.getMessage("M01368")) , //주문방법

				new HeaderCell(812, MessageUtils.getMessage("M01355")) , //결제
				new HeaderCell(2012, "") ,
				new HeaderCell(2012, "") ,
				new HeaderCell(2012, "") ,
				new HeaderCell(2012, "") ,
				new HeaderCell(2012, "") ,

				new HeaderCell(812, MessageUtils.getMessage("M00037")) , //취소
				new HeaderCell(2012, "") ,
				new HeaderCell(2012, "") ,
				new HeaderCell(2012, "") ,
				new HeaderCell(2012, "") ,
				new HeaderCell(2012, "") ,

				new HeaderCell(2012, "합계"), //소계
				new HeaderCell(2012, "") ,
				new HeaderCell(2012, "") ,
		};


		HeaderCell[] headerCells2 = new HeaderCell[] {
				new HeaderCell(512, MessageUtils.getMessage("M01377")) , //일자
				new HeaderCell(512, MessageUtils.getMessage("M01368")) , //주문방법

				new HeaderCell(712, MessageUtils.getMessage("M01357")) , //건수
				new HeaderCell(2012, "상품판매가") ,
				new HeaderCell(2012, "할인") ,
				new HeaderCell(2012, "판매금액") ,
				new HeaderCell(2012, "배송비") ,
				new HeaderCell(2012, "소계") ,

				new HeaderCell(712, MessageUtils.getMessage("M01357")) , //건수
				new HeaderCell(2012, "상품판매가") ,
				new HeaderCell(2012, "할인") ,
				new HeaderCell(2012, "판매금액") ,
				new HeaderCell(2012, "배송비") ,
				new HeaderCell(2012, "소계") ,

				new HeaderCell(2012, "판매가") ,
				new HeaderCell(2012, "배송비") ,
				new HeaderCell(2012, "합계")
		};

		// 5. Row병합(세로)이 필요한 셀 인덱스 설정;
		//int[] mergeRowCellIndexes = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 22, 23, 24, 25 };

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

		sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));

		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 7));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 8, 13));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 14, 16));

		// Table Body
		int rowIndex = 3;
		int i = 1;
		for (DateStatsSummary date : list) {

			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);

			CellIndex cellIndex = new CellIndex(-1);

			for(BaseStats data : date.getGroupStats()){
				setTextLeft(sheet, rowIndex, cellIndex, DateUtils.date(date.getSearchDate()));
				setText(sheet, rowIndex, cellIndex, data.getDeviceType());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getSaleCount());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getItemPrice());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getDiscountAmount());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getSaleAmount());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getShipping());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getPayAmount());

				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getCancelCount());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getCancelItemPrice());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getCancelDiscountAmount());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getCancelAmount());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getCancelShipping());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getCancelPayAmount());

				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getTotalAmount());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getTotalShipping());
				setNumberFormat(sheet, rowIndex, cellIndex, (int)data.getTotalPayAmount());

				cellIndex = new CellIndex(-1);

				rowIndex++;
				i++;

				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);
			}
		}

		CellIndex cellIndex = new CellIndex(-1);

		row = sheet.createRow(rowIndex);
		row.setHeight((short) 400);

		setText(sheet, rowIndex, cellIndex, MessageUtils.getMessage("M00358"));
		setText(sheet, rowIndex, cellIndex, MessageUtils.getMessage("M00358"));
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getSaleCount());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getItemPrice());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getDiscountAmount());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getSaleAmount());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getShipping());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getPayAmount());

		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getCancelCount());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getCancelItemPrice());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getCancelDiscountAmount());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getCancelAmount());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getCancelShipping());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getCancelPayAmount());

		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getTotalAmount());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getTotalShipping());
		setNumberFormat(sheet, rowIndex, cellIndex, (int)total.getTotalPayAmount());

		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));

		rowIndex++;
		i++;
	}

}