package saleson.shop.order.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import saleson.common.Const;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderPayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class OrderWaitingDetailExcelView extends AbstractSXSSFExcelView {
	
	private OrderParam orderParam;
	private List<Order> orderdetailList;
	
	public OrderWaitingDetailExcelView() {
		
		setFileName("ORDER_DETAIL" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    
	    // 데이터 생성(시트생성)
	    buildItemSheet(workbook, (List<OrderPayment>)model.get("orderWaitingdetailList"));
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<OrderPayment> list) {
		
		if(list == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "OrderWaitingExcel";
		String title = "주문내역";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(1000, 	"순번",	    "순번"),
				new HeaderCell(2500, 	"주문일시",	"주문일시"),
				new HeaderCell(1500, 	"주문번호", 	"주문번호"),
				new HeaderCell(1000, 	"결제방법", 	"결제방법"),
				new HeaderCell(2000, 	"아이디", 	"아이디"),
				new HeaderCell(1500, 	"회원명", 	"회원명"),
				new HeaderCell(1500, 	"주문자", 	"주문자"),
				new HeaderCell(2000, 	"입금자명의", 	"입금자명의"),
				new HeaderCell(1500, 	"입금예정금액","입금예정금액"),
				new HeaderCell(5000, 	"입금계좌", 	"입금계좌"),
				new HeaderCell(3000, 	"현금영수증", 	"현금영수증"),
				new HeaderCell(1500, 	"입금예정일", 	"입금예정일")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		int index = 0;
		
		for(OrderPayment orderPayment : list){
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);

					
			 cellIndex = new CellIndex(-1);
				
				
			setText(sheet, 			rowIndex, cellIndex, String.valueOf(index+1)); // 순번
			setText(sheet, 			rowIndex, cellIndex, DateUtils.datetime(orderPayment.getOrderDate())); // 주문일시 
			setText(sheet, 			rowIndex, cellIndex, orderPayment.getOrderCode()); // 주문번호
			setText(sheet, 			rowIndex, cellIndex, orderPayment.getApprovalTypeLabel()); // 결제방법
			setText(sheet, 			rowIndex, cellIndex, orderPayment.getLoginId()); // 아이디
			setText(sheet, 			rowIndex, cellIndex, orderPayment.getUserName()); // 회원명
			setText(sheet, 			rowIndex, cellIndex, orderPayment.getBuyerName()); // 주문자
			setText(sheet, 			rowIndex, cellIndex, orderPayment.getBankInName()); // 입금자명의
			setText(sheet, 			rowIndex, cellIndex, StringUtils.numberFormat(orderPayment.getAmount())); // 입금예정금액
			setText(sheet, 			rowIndex, cellIndex, orderPayment.getBankVirtualNo()); // 입금계좌

			if (orderPayment.getCashbill() != null && orderPayment.getCashbill().getCashbillType() != null) {
				setText(sheet, 			rowIndex, cellIndex, orderPayment.getCashbill().getCashbillType().getTitle()); // 현금영수증
			} else {
				setText(sheet, 			rowIndex, cellIndex, ""); // 현금영수증
			}

			setText(sheet, 			rowIndex, cellIndex, orderPayment.getBankDate()); // 입금예정일 

			index++;
			rowIndex++;

		}
	}

}
