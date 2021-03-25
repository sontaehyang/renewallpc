package saleson.shop.order.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinepowers.framework.util.MessageUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

import saleson.common.Const;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.order.support.OrderParam;

public class ShippingReadyDetailExcelView extends AbstractSXSSFExcelView {
	
	private OrderParam orderParam;
	private List<Order> orderdetailList;
	
	public ShippingReadyDetailExcelView() {
		
		setFileName("ORDER_DETAIL" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    
	    // 데이터 생성(시트생성)
	    buildItemSheet(workbook, (List<Order>)model.get("ShippingReadyOrderDetailList"));
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<Order> list) {
		
		if(list == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "OrderShippingReadyExcel";
		String title = "주문내역";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(2500, 	"주문번호",	"주문번호"),
				new HeaderCell(1000, 	"주문순번", 	"주문순번"),
				new HeaderCell(2000, 	"주문자명", 	"주문자명"),
				new HeaderCell(2000, 	"주문자핸드폰", 	"주문자핸드폰"),
				new HeaderCell(1000, 	"주문방법", 	"주문방법"),
				new HeaderCell(6000, 	"상품명", 		"상품명"),
				new HeaderCell(5000, 	"상품옵션", 	"상품옵션"),
				new HeaderCell(5000, 	"상품번호", 	"상품번호"),
				new HeaderCell(2000, 	"수량", 		"수량"),
				new HeaderCell(2000, 	"수령인", 		"수령인"),
				new HeaderCell(2000, 	"수령인전화번호", "수령인전화번호"),
				new HeaderCell(2000, 	"수령인우편번호", "수령인우편번호"),
				new HeaderCell(5000, 	"수령인주소", "수령인주소"),
				new HeaderCell(5000, 	"수령인상세주소", "수령인상세주소"),
				new HeaderCell(2000, 	"판매가", 		"판매가"),
				new HeaderCell(2000, 	MessageUtils.getMessage("M00246"), 		MessageUtils.getMessage("M00246")),
				new HeaderCell(2000, 	"배송비", 		"배송비"),
				new HeaderCell(2000, 	"배송업체", 		"배송업체"),
				new HeaderCell(2000, 	"운송장번호", 		"운송장번호"),
				new HeaderCell(2000, 	"소계", 		"소계")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		
		for(Order order : list){
			
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			
			for(OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()){
				
				int index = 0;
				
				for(OrderItem orderItem :  orderShippingInfo.getOrderItems()){
					
					cellIndex = new CellIndex(-1);
					 
					 String OrderCode = order.getOrderCode();
					 String BuyerName =order.getBuyerName();
					 String BuyerPhone = order.getMobile();
					 String TotalAmount = String.valueOf(order.getOrderTotalAmount());
					 
					 if(index > 0 ){
						 OrderCode = "";
						 BuyerName = "";
						 BuyerPhone = "";
						 TotalAmount = "";	 
					 }

					 index++;


					 setText(sheet, 			rowIndex, cellIndex, OrderCode); // 주문번호
					 setText(sheet, 			rowIndex, cellIndex, String.valueOf(index)); // 주문순번
					 setText(sheet, 			rowIndex, cellIndex, BuyerName); // 주문자명
					 setText(sheet, 			rowIndex, cellIndex, BuyerPhone); // 주문자 전화번호
					 setText(sheet, 			rowIndex, cellIndex, orderItem.getDeviceType()); // 주문방법
					 setText(sheet, 			rowIndex, cellIndex, orderItem.getItemName()); // 상품명
					 setText(sheet, 			rowIndex, cellIndex, orderItem.getOptions()); // 상품옵션
					 setText(sheet, 			rowIndex, cellIndex, orderItem.getItemUserCode()); // 상품번호
					 setText(sheet, 			rowIndex, cellIndex, String.valueOf(orderItem.getQuantity())); // 수량
					 setText(sheet, 			rowIndex, cellIndex, orderShippingInfo.getReceiveName()); // 수령인
					 setText(sheet, 			rowIndex, cellIndex, orderShippingInfo.getFullReceiveMobile()); // 수령인 전화번호
					 setText(sheet, 			rowIndex, cellIndex, orderShippingInfo.getFullReceiveZipcode()); // 수령인 우편번호
					 setText(sheet, 			rowIndex, cellIndex, orderShippingInfo.getReceiveAddress()); // 수령인주소
					 setText(sheet, 			rowIndex, cellIndex, orderShippingInfo.getReceiveAddressDetail()); // 수령인상세주소
					 setText(sheet, 			rowIndex, cellIndex, String.valueOf(orderItem.getSalePrice())); // 개당 판매가
					 setText(sheet, 			rowIndex, cellIndex, "  "); // 포인트
					 setText(sheet, 			rowIndex, cellIndex, String.valueOf(orderItem.getRealShipping())); // 개당 배송비
					 setText(sheet, 			rowIndex, cellIndex, "  "); // 배송업체(입력받을 값)
					 setText(sheet, 			rowIndex, cellIndex, "  "); // 운송장번호(입력받을 값)
					 setText(sheet, 			rowIndex, cellIndex, TotalAmount); // 총 주문 금액
					

				}	
			}
		}	
	}


}
