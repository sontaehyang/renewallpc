package saleson.shop.statistics.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import saleson.common.Const;
import saleson.shop.statistics.domain.ShopItemDateStatistics;
import saleson.shop.statistics.domain.ShopItemPrice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public class ItemDateStatisticsExcelView extends AbstractSXSSFExcelView {
	private List<String> hedarList;
	
	
	public ItemDateStatisticsExcelView() {
		setFileName("ITEM_DATE_STATISTICS" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
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
	    List<ShopItemDateStatistics> itemDateList = (List<ShopItemDateStatistics>) model.get("itemDateList");
		this.hedarList = (List<String>) model.get("hedarList");
		
		// 2. 시트별 데이터 생성
		buildUserSheet(workbook, itemDateList);
		
	}

	private void buildUserSheet(SXSSFWorkbook workbook, List<ShopItemDateStatistics> list) {
		String sheetTitle = "商品情報"; 	// 상품정보
		
		
		int arraySize = hedarList.size() * 2;
		HeaderCell[] headerCells;
		int i = 0;
		
		// 정렬 조건이 팀이거나 상품인 경우에는 기본 셀 앞쪽에 셀을 추가한다.
		headerCells = new HeaderCell[arraySize + 6];
		
		// 팀 
		headerCells[0] = new HeaderCell(16384, "상품명");
		headerCells[1] = new HeaderCell(512, "팀");
		headerCells[2] = new HeaderCell(2048, "그룹");
		headerCells[3] = new HeaderCell(2048, "그룹 이름");
		headerCells[4] = new HeaderCell(2048, "카테고리");
		headerCells[5] = new HeaderCell(2048, "카테고리 이름");
		
		i = 6;
		
		for (int j = 0; j < hedarList.size(); j++) {
			headerCells[i] = new HeaderCell(1024, hedarList.get(j));
			i++;
			headerCells[i] = new HeaderCell(512, "");
			i++;
		}
		
		/*headerCells[arraySize + 7] = new HeaderCell(1024, "합계");
		headerCells[arraySize + 8] = new HeaderCell(512, "");*/
		
		
		// 5. Row병합(세로)이 필요한 셀 인덱스 설정;
		//int[] mergeRowCellIndexes = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 22, 23, 24, 25 };
		int[] mergeRowCellIndexes = new int[] {};
	
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, headerCells, sheetTitle);
		int startCell = 6;
		for (int j = 0; j < hedarList.size() * 2 ; j++) {
			
			int firstCell = startCell + j;
			int lastCell = firstCell + 1;
			sheet.addMergedRegion(new CellRangeAddress(1, 1, firstCell, lastCell));
			j++;
			
		}
		
		
		// Table Body
		int rowIndex = 2;
		int price = 0;
		int count = 0;
		for (ShopItemDateStatistics item : list) {
			
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
		
			setTextLeft(sheet, rowIndex, cellIndex, item.getItemName()+" ("+ item.getItemUserCode() +") ");
			setTextLeft(sheet, rowIndex, cellIndex, item.getCode());
			setTextLeft(sheet, rowIndex, cellIndex, item.getGroupCode());
			setTextLeft(sheet, rowIndex, cellIndex, item.getGroupName());
			setTextLeft(sheet, rowIndex, cellIndex, item.getCategoryCode());
			setTextLeft(sheet, rowIndex, cellIndex, item.getCategoryName());
			
			List<ShopItemPrice> priceList = item.getDateList2();
			
			for (ShopItemPrice shopItemPrice : priceList) {
				setTextRight(sheet, rowIndex, cellIndex, shopItemPrice.getWebPriceTotal()+"원 ");
				setTextRight(sheet, rowIndex, cellIndex, shopItemPrice.getWebPayCount()+"개");
				price = price + Integer.parseInt(shopItemPrice.getWebPriceTotal());
				count = count + Integer.parseInt(shopItemPrice.getWebPayCount());
			}
			
			/*setTextRight(sheet, rowIndex, cellIndex, price+"円 ");
			setTextRight(sheet, rowIndex, cellIndex, count+"個");*/
			
			rowIndex++;	
		}
	}
	

}
