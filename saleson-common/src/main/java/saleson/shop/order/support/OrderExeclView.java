package saleson.shop.order.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.order.domain.OrderShippingInfo;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;


public class OrderExeclView extends AbstractSXSSFExcelView{


	private OrderParam orderParam;
	private List<OrderShippingInfo> orderList;
	
	public OrderExeclView() {
		
		setFileName("ORDER_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	    // 1.데이터 가져오기 
	    orderParam = (OrderParam)model.get("oderParam");
	    orderList = (List<OrderShippingInfo>)model.get("orderList");
	    
	    // 2. 데이터 생성(시트생성)
	    buildItemSheet(workbook, orderList);
	}
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<OrderShippingInfo> list) {
		
		if(list == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "order_main";
		String title = "배송준비 목록";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(1000, 	"고객명",		"고객명"),
				new HeaderCell(1000, 	"우편번호", 	"우편번호"),
				new HeaderCell(6500, 	"주소1", 		"주소1"),
				new HeaderCell(6500, 	"주소2", 		"주소2"),
				new HeaderCell(300, 	"", 		""),
				new HeaderCell(300, 	"", 		""),
				new HeaderCell(5000, 	"전화번호1", 	"전화번호1"),
				new HeaderCell(5000, 	"전환번호2", 	"전화번호2"),
				new HeaderCell(10000, 	"메세지", 		"메세지")
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
			
			CellIndex cellIndex = new CellIndex(-1);
			
			HashMap<String, String> addressMap = setAddress(order.getReceiveAddress(), order.getReceiveAddressDetail());
			
			setText(sheet, 			rowIndex, cellIndex, order.getReceiveName()); // 고객명
			setText(sheet, 			rowIndex, cellIndex, order.getReceiveZipcode()); // 우편주소
			setText(sheet, 			rowIndex, cellIndex, addressMap.get("address1")); // 주소1
			setText(sheet, 			rowIndex, cellIndex, addressMap.get("address2")); // 주소2
			setText(sheet, 			rowIndex, cellIndex, ""); // 빈값
			setText(sheet, 			rowIndex, cellIndex, ""); // 빈값
			setText(sheet, 			rowIndex, cellIndex, order.getReceiveMobile()); // 전화번호1
			setText(sheet, 			rowIndex, cellIndex, order.getReceivePhone()); // 전화번호2
			
			//skc// String orderMessage = order.getAddInfoToHashMap().get("배송요청사항");
			String orderMessage = "";
			
			setText(sheet, 			rowIndex, cellIndex, orderMessage); // 메세지
			
			rowIndex++;
		}
	}
	
	/**
	 * 배달용 주소 셋팅
	 * @param address
	 * @param addrseeDetail
	 * @return
	 */
	private HashMap<String, String> setAddress(String address, String addrseeDetail){
		
		HashMap<String, String> addressMap = new HashMap<>();
		
		String sido = ShopUtils.getSido(address);
		String sigungu = ShopUtils.getSigungu(address);
		String eupmyeondong = ShopUtils.getEupmyeondong(address);
		String dongri = "";
		
		if (eupmyeondong.endsWith("동")) {
			dongri = "";
		} else {
			dongri = ShopUtils.getDongri(address);
		}
		
		String address1 = sido+" "+sigungu+" "+eupmyeondong+" "+dongri;
		
		String address2 = address.replaceAll(address1.trim(), "") ;
		
		addressMap.put("address1", address1);
		addressMap.put("address2", address2 +" "+ addrseeDetail);
		
		return addressMap;
	}
	
}
