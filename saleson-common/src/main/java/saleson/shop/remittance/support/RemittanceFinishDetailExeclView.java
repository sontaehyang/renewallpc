package saleson.shop.remittance.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinepowers.framework.util.MessageUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.remittance.domain.RemittanceDetail;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class RemittanceFinishDetailExeclView extends AbstractSXSSFExcelView{
	
	private List<RemittanceDetail> remittanceDetail;
	
	public RemittanceFinishDetailExeclView() {
		setFileName("REMITTANCE_FINISH_DETAIL_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 1.데이터 가져오기 
		remittanceDetail = (List<RemittanceDetail>) model.get("remittanceDetail");
		
		 // 2. 데이터 생성(시트생성)
	    buildItemSheet(workbook, remittanceDetail);
	}

	private void buildItemSheet(SXSSFWorkbook workbook, List<RemittanceDetail> list) {
		
		if(list == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "remittance_detail";
		String title = "정산마감상세내역";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(1000, 	"주문번호",	"주문번호"),
				new HeaderCell(1000, 	"상품타입", 	"상품타입"),
				new HeaderCell(1000, 	"상품코드", 	"상품코드"),
				new HeaderCell(6500, 	"상품명", 	"상품명"),
				new HeaderCell(6500, 	"옵션", 		"옵션"),
				new HeaderCell(3000, 	"판매가", 	"판매가"),
				new HeaderCell(3000, 	"수수료", 	"수수료"),
				new HeaderCell(3000, 	"수수료율", 	"수수료율"),
				new HeaderCell(3000, 	"구분", 	"구분"),
				new HeaderCell(3000, 	"공급가", 	"공급가"),
				new HeaderCell(3000, 	"판매자할인", 	"판매자할인"),
				new HeaderCell(3000, 	"판매자" + MessageUtils.getMessage("M00246"),"판매자" + MessageUtils.getMessage("M00246")),
				new HeaderCell(3000, 	"주문수량", 	"주문수량"),
				new HeaderCell(3000, 	"정산금액", 	"정산금액")
				
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		for(RemittanceDetail remittanceDetail : list){
			
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);

			String sortation = "-";

			// 2019.01.07 손준의 CommissionType에 따라 구분 텍스트값 설정
			if ("3".equals(remittanceDetail.getCommissionType())) {
			sortation = "공급가";
			} else if ("1".equals(remittanceDetail.getCommissionType())
						|| "2".equals(remittanceDetail.getCommissionType())) {
				sortation = "수수료";
			}
			
			CellIndex cellIndex = new CellIndex(-1);
			setText(sheet, 					rowIndex, cellIndex, remittanceDetail.getOrderCode()); // 주문번호
			setText(sheet, 					rowIndex, cellIndex, "ITEM".equals(remittanceDetail.getItemType())? "상품":"배송비"); // 상품타입
			setText(sheet, 					rowIndex, cellIndex, remittanceDetail.getItemUserCode()); // 상품코드
			setText(sheet, 					rowIndex, cellIndex, remittanceDetail.getItemName()); // 상품명
			setText(sheet, 					rowIndex, cellIndex, ShopUtils.viewOptionTextNoUl(remittanceDetail.getOptions())); // 옵션
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceDetail.getCommissionBasePrice() * remittanceDetail.getQuantity()); // 판매가
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceDetail.getCommissionPrice() * remittanceDetail.getQuantity()); // 수수료
			setText(sheet, 					rowIndex, cellIndex, remittanceDetail.getCommissionRate()+"%"); // 수수료율
			setText(sheet, 			        rowIndex, cellIndex, sortation); // 구분
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceDetail.getSupplyPrice() * remittanceDetail.getQuantity()); // 공급가
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceDetail.getSellerDiscountPrice() * remittanceDetail.getQuantity()); // 판매자할인
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceDetail.getSellerPoint() * remittanceDetail.getQuantity()); // 판매자포인트
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceDetail.getQuantity()); // 주문수량
			setNumberRight(sheet, 			rowIndex, cellIndex, (int)remittanceDetail.getRemittancePrice() * remittanceDetail.getQuantity()); // 정산금액
			
			rowIndex++;
		}
	}
}
