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
import saleson.shop.remittance.domain.RemittanceConfirmDetail;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class RemittanceConfirmDetailExeclView extends AbstractSXSSFExcelView{
	
	private List<RemittanceConfirmDetail> remittanceConfirmDetail;
	
	public RemittanceConfirmDetailExeclView() {
		setFileName("REMITTANCE_CONFIRM_DETAIL_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 1.데이터 가져오기 
		remittanceConfirmDetail = (List<RemittanceConfirmDetail>) model.get("remittanceConfirmDetail");
		
		 // 2. 데이터 생성(시트생성)
	    buildItemSheet(workbook, remittanceConfirmDetail);
	}

	private void buildItemSheet(SXSSFWorkbook workbook, List<RemittanceConfirmDetail> list) {
		
		if(list == null){
			return;
		}
		
		// 3. 시트생성
		String sheetTitle = "remittance_confirm_detail";
		String title = "정산확정상세내역";
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(1000, 	"주문번호",	"주문번호"),
				new HeaderCell(1000, 	"확정일자", 	"확정일자"),
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
		for(RemittanceConfirmDetail remittanceConfirmDetail : list){

			
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);

			String sortation = "-";

			// 2019.01.07 손준의 CommissionType에 따라 구분 텍스트값 설정
			if ("3".equals(remittanceConfirmDetail.getCommissionType())) {
			sortation = "공급가";
			} else if ("1".equals(remittanceConfirmDetail.getCommissionType())
						|| "2".equals(remittanceConfirmDetail.getCommissionType())) {
				sortation = "수수료";
			}
			
			CellIndex cellIndex = new CellIndex(-1);
			setText(sheet, 					rowIndex, cellIndex, remittanceConfirmDetail.getOrderCode()); // 주문번호
			setText(sheet, 					rowIndex, cellIndex, remittanceConfirmDetail.getRemittanceDate()); // 확정일자
			setText(sheet, 					rowIndex, cellIndex, remittanceConfirmDetail.getItemName()); // 상품명
			setText(sheet, 					rowIndex, cellIndex, ShopUtils.viewOptionTextNoUl(remittanceConfirmDetail.getOptions())); // 옵션
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceConfirmDetail.getCommissionBasePrice() * remittanceConfirmDetail.getQuantity()); // 판매가
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceConfirmDetail.getCommissionPrice() * remittanceConfirmDetail.getQuantity()); // 수수료
			setText(sheet, 					rowIndex, cellIndex, remittanceConfirmDetail.getCommissionRate()+"%"); // 수수료율
			setText(sheet, 					rowIndex, cellIndex, sortation); // 구분
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceConfirmDetail.getSupplyPrice() * remittanceConfirmDetail.getQuantity()); // 공급가
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceConfirmDetail.getSellerDiscountPrice() * remittanceConfirmDetail.getQuantity()); // 판매자할인
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceConfirmDetail.getSellerPoint() * remittanceConfirmDetail.getQuantity()); // 판매자포인트
			setNumberRight(sheet, 			rowIndex, cellIndex, remittanceConfirmDetail.getQuantity()); // 주문수량
			setNumberRight(sheet, 			rowIndex, cellIndex, (int)remittanceConfirmDetail.getRemittancePrice() * remittanceConfirmDetail.getQuantity()); // 정산금액
			
			rowIndex++;
		}
	}
}
