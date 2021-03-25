package saleson.shop.statistics.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.shop.item.domain.Item;
import saleson.shop.statistics.domain.ShopItemDetailStatistics;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class ItemDetailExcelView extends AbstractSXSSFExcelView {

    /*

     */



	private Item item;
	
	public ItemDetailExcelView(Item item) {
		this.item = item;
		setFileName("상품별 판매 상세(회원)_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		buildItemSheet(workbook, (List<ShopItemDetailStatistics>)model.get("list"));
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<ShopItemDetailStatistics> list) {
		
		if (list == null) {
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "list";
		String title = this.item.getItemName() + "상품 매출 상세";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
			new HeaderCell(2500, 	"구분"),
			new HeaderCell(3000, 	"회원 이름"),
			new HeaderCell(4000, 	"전화번호"),
			new HeaderCell(3000, 	"웹 아이디"),
			new HeaderCell(3000, 	"거래처 코드"),
			new HeaderCell(3000, 	"우편번호"),
			new HeaderCell(12000, 	"주소"),
			new HeaderCell(2000, 	"건수"),
			new HeaderCell(3000, 	"매출액")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		int rowIndex = 2;
		for(ShopItemDetailStatistics detail : list) {
			
			CellIndex cellIndex = new CellIndex(-1);

			if ("PAY".equals(detail.getOrderType())) {

				String groupTitle = "PAY".equals(detail.getOrderType()) ? "결제" : "취소";
				String userGubun = StringUtils.isEmpty(detail.getLoginId()) ? "거래처" : "웹회원";
				setText(sheet, 			rowIndex, cellIndex, groupTitle);
				setText(sheet, 			rowIndex, cellIndex, detail.getUserName() + "("+userGubun+")");
				setText(sheet, 			rowIndex, cellIndex, detail.getTelNumber());
				setText(sheet, 			rowIndex, cellIndex, detail.getLoginId());
				setText(sheet, 			rowIndex, cellIndex, detail.getCustomerCode());
				setText(sheet, 			rowIndex, cellIndex, detail.getZipcode());
				setTextLeft(sheet, 			rowIndex, cellIndex, detail.getAddress() + " " + detail.getAddressDetail());
				
				setTextRight(sheet, 			rowIndex, cellIndex, StringUtils.numberFormat(detail.getQuantity()) + "개");
				setTextRight(sheet, 			rowIndex, cellIndex, StringUtils.numberFormat(detail.getItemPrice()) + "원");
				
				rowIndex++;
				
			}
			
			
		}
		
		for(ShopItemDetailStatistics detail : list) {
			
			CellIndex cellIndex = new CellIndex(-1);

			if ("CANCEL".equals(detail.getOrderType())) {

				String groupTitle = "PAY".equals(detail.getOrderType()) ? "결제" : "취소";
				String userGubun = StringUtils.isEmpty(detail.getLoginId()) ? "거래처" : "웹회원";
				setText(sheet, 			rowIndex, cellIndex, groupTitle);
				setText(sheet, 			rowIndex, cellIndex, detail.getUserName() + "("+userGubun+")");
				setText(sheet, 			rowIndex, cellIndex, detail.getTelNumber());
				setText(sheet, 			rowIndex, cellIndex, detail.getLoginId());
				setText(sheet, 			rowIndex, cellIndex, detail.getCustomerCode());
				setText(sheet, 			rowIndex, cellIndex, detail.getZipcode());
				setTextLeft(sheet, 			rowIndex, cellIndex, detail.getAddress() + " " + detail.getAddressDetail());
				
				setTextRight(sheet, 			rowIndex, cellIndex, StringUtils.numberFormat(detail.getQuantity()) + "개");
				setTextRight(sheet, 			rowIndex, cellIndex, StringUtils.numberFormat(detail.getItemPrice()) + "원");
				
				
				rowIndex++;
			}
			
			
		}
		
	}
	
}
