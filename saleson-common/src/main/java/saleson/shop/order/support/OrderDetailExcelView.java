package saleson.shop.order.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import saleson.common.Const;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class OrderDetailExcelView extends AbstractSXSSFExcelView{
	
	private OrderParam orderParam;
	private List<Order> orderDetailList;
	private List<DeliveryCompany> deliveryCompanyList;
	
	public OrderDetailExcelView() {
		
		setFileName("ORDER_DETAIL" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    
	    // 데이터 생성(시트생성)
	    buildItemSheet(workbook, (List<OrderList>)model.get("orderDetailList"), (List<DeliveryCompany>)model.get("deliveryCompanyList"), (List<OrderList>)model.get("additionList"));
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<OrderList> list, List<DeliveryCompany> deliveryCompanyList, List<OrderList> additionList) {
		
		if(list == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "OrderShippingReadyExcel";
		String title = "주문내역";
		
		//택배사 목록 가져오기
		String companyList = ""; 
		for(DeliveryCompany company : deliveryCompanyList) {
			companyList += company.getDeliveryCompanyId()+":"+company.getDeliveryCompanyName()+System.getProperty("line.separator");
		}
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(1800, 	"주문번호",	"주문번호"),
				new HeaderCell(300, 	"주문순번",	"주문순번"),
				new HeaderCell(1000, 	"주문자명", 	"주문자명"),
				new HeaderCell(1700, 	"주문자핸드폰", 	"주문자핸드폰"),
				new HeaderCell(8000, 	"상품명(옵션)", 		"상품명(옵션)"),
				new HeaderCell(1800, 	"상품번호", 	"상품번호"),
				new HeaderCell(100, 	"수량", 		"수량"),
				new HeaderCell(1000, 	"수령인", 		"수령인"),
				new HeaderCell(1700, 	"수령인전화번호", "수령인전화번호"),
				new HeaderCell(9000, 	"수령인주소", "수령인주소"),
				new HeaderCell(4000, 	"배송시 요청사항", 		"배송시 요청사항"),
				new HeaderCell(2000, 	"택배사", 		companyList),
				new HeaderCell(2000, 	"운송장번호", 		"운송장번호")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		
		// 행 높이 설정
		row = sheet.createRow(rowIndex);
		row.setHeight((short) 400);
		
		int startRowIndex = rowIndex;
		
		int[] mergeRowCellIndexes = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 18, 19, 20, 21, 22};
		
		CellIndex cellIndex = new CellIndex(-1);
		int index = 0;
	
		for(OrderList orderItem : list){
			
			 cellIndex = new CellIndex(-1);
			 
			 String orderCode = orderItem.getOrderCode();
			 String itemSequence = Integer.toString(orderItem.getItemSequence());
			 String buyerName = orderItem.getUserName();
			 String buyerPhone = orderItem.getMobile();
			 String option = orderItem.getOptions();
			 
			 
			 if(!StringUtils.isEmpty(option)){
				 
				 String[] itemOptionText = StringUtils.delimitedListToStringArray(option, "||");
				 
				 if(itemOptionText.length > 0){
				 
					 if(itemOptionText[0].length() > 1){

						 option = "";
						 
						 for(int i = 1; i < itemOptionText.length-1; i++){									 
							 if (i % 2 != 0){
								 option += itemOptionText[i] + " : " + itemOptionText[i+1] + " ";
							 }
						 }
						 
					 }else{
						 option = itemOptionText[1] + " : " + itemOptionText[2];
					 }							 
				 }
			 }
				
				
			setText(sheet, 			rowIndex, cellIndex, orderCode); // 주문번호
			setText(sheet, 			rowIndex, cellIndex, itemSequence); // 주문아이템순번
			setText(sheet, 			rowIndex, cellIndex, buyerName); // 주문자명
			setText(sheet, 			rowIndex, cellIndex, buyerPhone); // 주문자 전화번호
			setText(sheet, 			rowIndex, cellIndex, orderItem.getItemName() + ((StringUtils.isEmpty(option)) ? "" : " [" + option + "]")); // 상품명[옵션]
			setText(sheet, 			rowIndex, cellIndex, orderItem.getItemUserCode()); // 상품번호
			setText(sheet, 			rowIndex, cellIndex, String.valueOf(orderItem.getQuantity())); // 수량
			setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveName()); // 수령인
			setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveMobile()); // 수령인 전화번호
			setText(sheet, 			rowIndex, cellIndex, "[" + orderItem.getReceiveNewZipcode() + "] " + orderItem.getReceiveAddress()
															+ " " + orderItem.getReceiveAddressDetail()); // 수령인주소
			setText(sheet, 			rowIndex, cellIndex, orderItem.getMemo()); // 배송시 요청사항
			setText(sheet, 			rowIndex, cellIndex, ""); // 택배사
			setText(sheet, 			rowIndex, cellIndex, ""); // 운송장번호
			index++;
			rowIndex++;


			// 추가구성상품
			for (OrderList additionItem : additionList) {
				// 부모의 ORDER_CODE, ITEM_ID, OPTIONS 가 일치하는 경우
				if (orderCode.equals(additionItem.getOrderCode()) && orderItem.getItemId() == additionItem.getParentItemId()
						&& orderItem.getOptions().equals(additionItem.getParentItemOptions()) && orderItem.getItemSequence() == additionItem.getParentItemSequence()) {

					cellIndex = new CellIndex(-1);

					setText(sheet, 			rowIndex, cellIndex, orderCode); // 주문번호
					setText(sheet, 			rowIndex, cellIndex, Integer.toString(additionItem.getItemSequence())); // 주문아이템순번
					setText(sheet, 			rowIndex, cellIndex, buyerName); // 주문자명
					setText(sheet, 			rowIndex, cellIndex, buyerPhone); // 주문자 전화번호
					setText(sheet, 			rowIndex, cellIndex, additionItem.getItemName()); // 상품명
					setText(sheet, 			rowIndex, cellIndex, additionItem.getItemUserCode()); // 상품번호
					setText(sheet, 			rowIndex, cellIndex, String.valueOf(additionItem.getQuantity())); // 수량
					setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveName()); // 수령인
					setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveMobile()); // 수령인 전화번호
					setText(sheet, 			rowIndex, cellIndex, "[" + orderItem.getReceiveNewZipcode() + "] " + orderItem.getReceiveAddress()
							+ " " + orderItem.getReceiveAddressDetail()); // 수령인주소
					setText(sheet, 			rowIndex, cellIndex, orderItem.getMemo()); // 배송시 요청사항
					setText(sheet, 			rowIndex, cellIndex, ""); // 택배사
					setText(sheet, 			rowIndex, cellIndex, ""); // 운송장번호
					index++;
					rowIndex++;

				}
			}
		}
	}
	
	/**
	 * 테이블 헤더 설정 
	 */
	public void createSheetHeader(Sheet sheet, Row row, HeaderCell[] headerCells, String sheetTitle) {
		row.setHeight((short) 800);
		
		int columCount = headerCells.length;
		for(int i = 0; i < headerCells.length; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) + headerCells[i].getWidth());
		}
		
		Cell[] cells = new Cell[columCount];
		for(int i =0; i < cells.length; i++){
			cells[i] = row.createCell(i);
			cells[i].setCellStyle(titleStyle);
		}

		cells[0].setCellValue(sheetTitle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (columCount - 1))); 


		row = sheet.createRow((short) 1);
		row.setHeight((short) 512);
		
		for(int i = 0; i < cells.length; i++){
			cells[i] = row.createCell(i);
			
			cells[i].setCellType(Cell.CELL_TYPE_STRING); //개행 문자 적용
			
			// 셀 타이틀 설정.
			cells[i].setCellValue(headerCells[i].getTitle().replace("(x)", ""));
			
			cells[i].setCellStyle(getHeaderCellStyle(IndexedColors.GREY_25_PERCENT.index));

			// 셀 코멘트
			if (headerCells[i].getComment() != null && !headerCells[i].getComment().equals("")) {
				setComment(cells[i], 1, i, headerCells[i].getComment().replace(", ", ",").replace(",", "\n"), headerCells[i].getCommentCol(), headerCells[i].getCommentRow());
			}
		}
		
		cells[11].setCellStyle(getHeaderCellStyle(IndexedColors.LEMON_CHIFFON.index));
		cells[12].setCellStyle(getHeaderCellStyle(IndexedColors.LEMON_CHIFFON.index));
		
		
	}
	
}
