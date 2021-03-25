package saleson.shop.order.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderList;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class ShippingDetailExcelView extends AbstractSXSSFExcelView {
	
	private OrderParam orderParam;
	private List<Order> orderdetailList;
	
	public ShippingDetailExcelView() {
		
		setFileName("SHIPPING_ORDER_DETAIL" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    
	    // 데이터 생성(시트생성)
	    buildItemSheet(workbook, (List<OrderList>)model.get("ShippingOrderDetailList"), (List<OrderList>)model.get("additionList"));
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<OrderList> list, List<OrderList> additionList) {
		
		if(list == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "OrderShippingExcel";
		String title = "주문내역";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(2500, 	"주문번호",	"주문번호"),
				new HeaderCell(2000, 	"주문자명", 	"주문자명"),
				new HeaderCell(2000, 	"주문자핸드폰", 	"주문자핸드폰"),
				new HeaderCell(1000, 	"주문방법", 	"주문방법"),
				new HeaderCell(6000, 	"상품명", 		"상품명"),
				new HeaderCell(5000, 	"상품옵션", 	"상품옵션"),
				new HeaderCell(3000, 	"상품번호", 	"상품번호"),
				new HeaderCell(2000, 	"수량", 		"수량"),
				new HeaderCell(2000, 	"수령인", 		"수령인"),
				new HeaderCell(2000, 	"수령인전화번호", "수령인전화번호"),
				new HeaderCell(2000, 	"수령인우편번호", "수령인우편번호"),
				new HeaderCell(5000, 	"수령인주소", "수령인주소"),
				new HeaderCell(5000, 	"수령인상세주소", "수령인상세주소"),
				new HeaderCell(2000, 	"판매가", 		"판매가"),
				new HeaderCell(6000, 	"배송시 요청사항", 		"배송시 요청사항"),
				new HeaderCell(2000, 	"배송업체", 		"배송업체"),
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
		
		CellIndex cellIndex = new CellIndex(-1);
		int index = 0;
		for(OrderList orderItem : list){
			
			 cellIndex = new CellIndex(-1);
			 
			 String orderCode = orderItem.getOrderCode();
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
			setText(sheet, 			rowIndex, cellIndex, buyerName); // 주문자명
			setText(sheet, 			rowIndex, cellIndex, buyerPhone); // 주문자 전화번호
			setText(sheet, 			rowIndex, cellIndex, orderItem.getDeviceType()); // 주문방법
			setText(sheet, 			rowIndex, cellIndex, orderItem.getItemName()); // 상품명
			setText(sheet, 			rowIndex, cellIndex, option); // 상품옵션
			setText(sheet, 			rowIndex, cellIndex, orderItem.getItemUserCode()); // 상품번호
			setText(sheet, 			rowIndex, cellIndex, String.valueOf(orderItem.getQuantity())); // 수량
			setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveName()); // 수령인
			setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveMobile()); // 수령인 전화번호
			setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveNewZipcode()); // 수령인 우편번호
			setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveAddress()); // 수령인주소
			setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveAddressDetail()); // 수령인상세주소
			setText(sheet, 			rowIndex, cellIndex, StringUtils.numberFormat(String.valueOf(orderItem.getSalePrice()))); // 개당 판매가
			setText(sheet, 			rowIndex, cellIndex, orderItem.getMemo()); // 배송시 요청사항
			setText(sheet, 			rowIndex, cellIndex, StringUtils.isEmpty(orderItem.getDeliveryCompanyName()) ? "" : String.valueOf(orderItem.getDeliveryCompanyName())); // 배송업체
			setText(sheet, 			rowIndex, cellIndex, StringUtils.isEmpty(orderItem.getDeliveryNumber()) ? "" : String.valueOf(orderItem.getDeliveryNumber())); // 운송장번호
			index++;
			rowIndex++;



			// 추가구성상품
			for (OrderList additionItem : additionList) {
				// 부모의 ORDER_CODE, ITEM_ID, OPTIONS 가 일치하는 경우
				if (orderCode.equals(additionItem.getOrderCode()) && orderItem.getItemId() == additionItem.getParentItemId()
						&& orderItem.getOptions().equals(additionItem.getParentItemOptions()) && orderItem.getItemSequence() == additionItem.getParentItemSequence()) {

					cellIndex = new CellIndex(-1);

					setText(sheet, 			rowIndex, cellIndex, orderCode); // 주문번호
					setText(sheet, 			rowIndex, cellIndex, buyerName); // 주문자명
					setText(sheet, 			rowIndex, cellIndex, buyerPhone); // 주문자 전화번호
					setText(sheet, 			rowIndex, cellIndex, orderItem.getDeviceType()); // 주문방법
					setText(sheet, 			rowIndex, cellIndex, additionItem.getItemName()); // 상품명
					setText(sheet, 			rowIndex, cellIndex, ""); // 상품옵션
					setText(sheet, 			rowIndex, cellIndex, additionItem.getItemUserCode()); // 상품번호
					setText(sheet, 			rowIndex, cellIndex, String.valueOf(additionItem.getQuantity())); // 수량
					setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveName()); // 수령인
					setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveMobile()); // 수령인 전화번호
					setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveNewZipcode()); // 수령인 우편번호
					setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveAddress()); // 수령인주소
					setText(sheet, 			rowIndex, cellIndex, orderItem.getReceiveAddressDetail()); // 수령인상세주소
					setText(sheet, 			rowIndex, cellIndex, StringUtils.numberFormat(String.valueOf(additionItem.getSalePrice()))); // 개당 판매가
					setText(sheet, 			rowIndex, cellIndex, orderItem.getMemo()); // 배송시 요청사항
					setText(sheet, 			rowIndex, cellIndex, StringUtils.isEmpty(orderItem.getDeliveryCompanyName()) ? "" : String.valueOf(orderItem.getDeliveryCompanyName())); // 배송업체
					setText(sheet, 			rowIndex, cellIndex, StringUtils.isEmpty(orderItem.getDeliveryNumber()) ? "" : String.valueOf(orderItem.getDeliveryNumber())); // 운송장번호
					index++;
					rowIndex++;

				}
			}
			
		}
	}
}
