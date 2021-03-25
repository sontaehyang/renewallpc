package saleson.shop.order.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class BrandExcelView extends AbstractSXSSFExcelView {
	
	public BrandExcelView(String brandName) {
		setFileName( brandName + "_주문서_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		// 1.데이터 가져오기 
	    
	    // 2. 데이터 생성(시트생성)
	    buildItemSheet(workbook, (List<OrderShippingInfo>)model.get("orderList"));
	}
	
	@Override
	public void createSheetHeader(Sheet sheet, Row row,
			HeaderCell[] headerCells, String sheetTitle) {
		row.setHeight((short) 800);
		
		int columCount = headerCells.length;
		for(int i = 0; i < headerCells.length; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) + headerCells[i].getWidth());
		}
		
		Cell[] cells = new Cell[columCount];
		/*
		for(int i =0; i < cells.length; i++){
			cells[i] = row.createCell(i);
			cells[i].setCellStyle(titleStyle);
		}

		cells[1].setCellValue(sheetTitle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (columCount - 1))); 
		 */


		row = sheet.createRow((short) 0);
		row.setHeight((short) 512);
		
		for(int i = 0; i < cells.length; i++){
			cells[i] = row.createCell(i);
			cells[i].setCellStyle(headerStyle);
			cells[i].setCellType(Cell.CELL_TYPE_STRING); //개행 문자 적용
			
			// 셀 타이틀 설정.
			cells[i].setCellValue(headerCells[i].getTitle());
			
			
			// 셀 코멘트
			if (headerCells[i].getComment() != null && !headerCells[i].getComment().equals("")) {
				setComment(cells[i], 1, i, headerCells[i].getComment().replace(", ", ",").replace(",", "\n"), headerCells[i].getCommentCol(), headerCells[i].getCommentRow());
			}
			
		}
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<OrderShippingInfo> list) {
		if(list == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "list";
		String title = "";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
			new HeaderCell(10, 	"송화인명"),
			new HeaderCell(10, 	"받는이"),
			new HeaderCell(30, 	"받는이 주소"),
			new HeaderCell(10, 	"우편번호"),
			new HeaderCell(10, 	"핸드폰"),
			new HeaderCell(10, 	"전화번호"),
			new HeaderCell(10, 	"주문자"),
			new HeaderCell(30, 	"주문자주소"),
			new HeaderCell(30, 	"상품명"),
			new HeaderCell(10, 	"요금"),
			new HeaderCell(10, 	"수량"),
			new HeaderCell(10, 	"단가"),
			new HeaderCell(10, 	"사이즈"),
			new HeaderCell(10, 	"비고"),
			new HeaderCell(10, 	"송장번호")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 1;
		int orderIndex = 1;
		for(OrderShippingInfo order : list){
			
			int index = 0;
			
			//skc//
			/*
			for(OrderItem orderItem : order.getOrderItems()) {
					
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);
				
				CellIndex cellIndex = new CellIndex(-1);
				
				String s1 = Integer.toString(orderIndex);
				String s2 = order.getReceiveName();
				String s3 = order.getReceiveAddress() + " " + order.getReceiveAddressDetail();
				String s4 = order.getReceiveZipcode();
				String s5 = order.getReceiveMobile();
				String s6 = order.getReceivePhone();
				String s7 = order.getUserName();
				String s8 = order.getAddress() + " " + order.getAddressDetail();
				
				if (index > 0) {
					s1 = "";
					s2 = "";
					s3 = "";
					s4 = "";
					s5 = "";
					s6 = "";
					s7 = "";
					s8 = "";
				} else {
					if (s2.equals(s7)) {
						s7 = "좌동";
					}
					
					if (s3.equals(s8)) {
						s8 = "좌동";
					}
				}
				
				int saleItemPrice = orderItem.getItemPrice() + orderItem.getTotalRequiredOptionsPrice() - orderItem.getItemCouponDiscountAmount();
				
				setText(sheet, 			rowIndex, cellIndex, s1);
				setText(sheet, 			rowIndex, cellIndex, s2);
				setTextLeft(sheet, 		rowIndex, cellIndex, s3);
				setTextLeft(sheet, 		rowIndex, cellIndex, s4);
				setTextLeft(sheet, 		rowIndex, cellIndex, s5);
				setTextLeft(sheet, 		rowIndex, cellIndex, s6);
				setTextLeft(sheet, 		rowIndex, cellIndex, s7);
				setTextLeft(sheet, 		rowIndex, cellIndex, s8);
				setTextLeft(sheet, 		rowIndex, cellIndex, orderItem.getItemUserCode() + " " + orderItem.getItemName());
				setTextLeft(sheet, 		rowIndex, cellIndex, "선불");
				setTextRight(sheet, 	rowIndex, cellIndex, Integer.toString(orderItem.getQuantity()));
				setTextRight(sheet, 	rowIndex, cellIndex, StringUtils.numberFormat(saleItemPrice));
				setTextLeft(sheet, 		rowIndex, cellIndex, "");
				setTextLeft(sheet, 		rowIndex, cellIndex, "");
				setTextLeft(sheet, 		rowIndex, cellIndex, "");
				rowIndex++;
				index++;
			}
			
			// 하나의 주문이 끝난후 한줄 뛰고 시작
			rowIndex++;
			
			orderIndex++;
			
			*/
		}
	}
	
}
