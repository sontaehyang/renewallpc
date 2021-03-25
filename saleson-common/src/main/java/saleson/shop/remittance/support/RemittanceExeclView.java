package saleson.shop.remittance.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinepowers.framework.util.MessageUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.shop.order.domain.OrderItem;
import saleson.shop.remittance.domain.Remittance;

import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class RemittanceExeclView extends AbstractSXSSFExcelView{
	
	private List<OrderItem> orderItems;
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 1.데이터 가져오기 
		orderItems = (List<OrderItem>) model.get("orderItems");
		
		 // 2. 데이터 생성(시트생성)
	    buildItemSheet(workbook, orderItems);
	}

	private void buildItemSheet(SXSSFWorkbook workbook, List<OrderItem> list) {
		
		if(list == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "remittance_main";
		String title = "정산상세내역";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(1000, 	"주문자",		"주문자"),
				new HeaderCell(1000, 	"구매 확정일", 	"구매 확정일"),
				new HeaderCell(6500, 	"상품명", 		"상품명"),
				new HeaderCell(6500, 	"단가", 		"단가"),
				new HeaderCell(5000, 	"수량", 	"수량"),
				new HeaderCell(5000, 	"판매가", 	"판매가"),
				new HeaderCell(10000, 	"공급가", 		"공급가"),
				new HeaderCell(10000, 	"수수료", 		"수수료"),
				new HeaderCell(10000, 	"배송비", 		"배송비"),
				new HeaderCell(10000, 	MessageUtils.getMessage("M00246") + " 발행", 		MessageUtils.getMessage("M00246") + " 발행"),
				new HeaderCell(10000, 	"정산금액", 		"정산금액")
				
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		for(OrderItem orderItem : list){
			
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			setText(sheet, 			rowIndex, cellIndex, ""); // 주문자
			setText(sheet, 			rowIndex, cellIndex, orderItem.getConfirmDate()); // 구매 확정일
			setText(sheet, 			rowIndex, cellIndex, orderItem.getItemName()+"\n"+orderItem.getOptions()); // 상품명
			setText(sheet, 			rowIndex, cellIndex, String.valueOf(orderItem.getPrice()+orderItem.getOptionPrice())); // 단가
			setText(sheet, 			rowIndex, cellIndex, String.valueOf(orderItem.getQuantity())); // 수량
			setText(sheet, 			rowIndex, cellIndex, String.valueOf(orderItem.getSaleAmount())); // 수량
			
			rowIndex++;
		}
	}
}
