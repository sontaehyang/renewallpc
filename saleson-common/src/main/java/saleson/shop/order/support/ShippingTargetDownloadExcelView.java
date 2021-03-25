package saleson.shop.order.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.order.domain.OrderItem;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class ShippingTargetDownloadExcelView extends AbstractSXSSFExcelView {

	public ShippingTargetDownloadExcelView() {
		setFileName("송장등록 대상 주문 목록_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 1.데이터 가져오기 
		List<OrderShippingInfo> list = (List<OrderShippingInfo>)model.get("orderList");
		List<DeliveryCompany> deliveryList = (List<DeliveryCompany>)model.get("activeDeliveryCompanyList");
		
		
	    // 2. 데이터 생성(시트생성)
	    buildItemSheet(workbook, list, deliveryList);
	    
	    responseHeader(request, response);
	    
	}

	private void buildItemSheet(SXSSFWorkbook workbook, List<OrderShippingInfo> list, List<DeliveryCompany> deliveryList) {
		
		if(list == null){
			return;
		}
		
		String deliveryInfo = "";
		String deliveryInfo1 = "";
		int i = 0;
		for(DeliveryCompany company : deliveryList) {
			deliveryInfo += company.getDeliveryCompanyId() + " -> " + company.getDeliveryCompanyName() + "\n";
			deliveryInfo1 += (i > 0 ? ", " : "") + company.getDeliveryCompanyId() + " -> " + company.getDeliveryCompanyName();

			i++;
		}
		
		// 3. 시트생성
		String sheetTitle = "order_main";
		String title = "배송준비 목록 (배송 업체 코드 : " + deliveryInfo1 + ")";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
			new HeaderCell(12, 	"주문번호"),
			new HeaderCell(12, 	"고객명"),
			new HeaderCell(10, 	"우편번호"),
			new HeaderCell(30, 	"주소1"),
			new HeaderCell(30, 	"주소2"),
			new HeaderCell(10, 	"전화번호1"),
			new HeaderCell(10, 	"전화번호2"),
			new HeaderCell(10, 	"상품번호",	"수정불가"),
			new HeaderCell(25, 	"상품명"),
			new HeaderCell(10, 	"수량"),
			new HeaderCell(10, 	"배송 구분",	"일반배송, 교환배송"),
			new HeaderCell(15, 	"배송업체 코드", deliveryInfo),
			new HeaderCell(15, 	"송장번호")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
	
		// Table Body
		int rowIndex = 2;
		for(OrderShippingInfo order : list){
			
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);

			/*
			for (OrderItem orderItem : order.getOrderItems()) {

				CellIndex cellIndex = new CellIndex(-1);
				
				setText(sheet, 			rowIndex, cellIndex, order.getOrderCode()); // 고객명
				setText(sheet, 			rowIndex, cellIndex, order.getReceiveName()); // 고객명
				setText(sheet, 			rowIndex, cellIndex, order.getReceiveZipcode()); // 우편주소
				setText(sheet, 			rowIndex, cellIndex, order.getReceiveAddress()); // 주소1
				setText(sheet, 			rowIndex, cellIndex, order.getReceiveAddressDetail()); // 주소2
				setText(sheet, 			rowIndex, cellIndex, order.getReceiveMobile()); // 전화번호1
				setText(sheet, 			rowIndex, cellIndex, order.getReceivePhone()); // 전화번호2
				
				setText(sheet, 			rowIndex, cellIndex, Integer.toString(orderItem.getOrderItemId()));
				setText(sheet, 			rowIndex, cellIndex, orderItem.getItemName()); 
				setText(sheet, 			rowIndex, cellIndex, Integer.toString(orderItem.getQuantity()));
				
				setText(sheet, 			rowIndex, cellIndex, orderItem.getOrderStatus().equals("1") ? "일반배송" : "교환배송");
				
				setText(sheet, 			rowIndex, cellIndex, ""); 
				setText(sheet, 			rowIndex, cellIndex, ""); 
				rowIndex++;
			
			}
			*/
		}
		
	}
	
}
