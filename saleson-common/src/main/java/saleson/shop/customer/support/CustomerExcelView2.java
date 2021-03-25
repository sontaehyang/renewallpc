package saleson.shop.customer.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.customer.domain.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerExcelView2 extends AbstractSXSSFExcelView {
	
	public CustomerExcelView2() {
		setFileName("거래처 정보_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
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
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<Customer> list = (List<Customer>) model.get("customerList");
		
	
		// Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			 new HeaderCell(2048, 	"거래처 코드"),
			 new HeaderCell(2048, 	"거래처명"),
			 new HeaderCell(2048, 	"거래처분류"), 
			 new HeaderCell(2048, 	"거래처구분"),
			 new HeaderCell(2048, 	"대표자명"),
			 new HeaderCell(2048, 	"사업자번호"),
			 new HeaderCell(2048, 	"전화번호"),
			 
			 //new HeaderCell(2048, 	"팩스국가번호"),
			 new HeaderCell(2048, 	"팩스번호"),
			 //new HeaderCell(2048, 	"홈페이지"),
			 new HeaderCell(2048, 	"주소구분"),
			 new HeaderCell(2048, 	"우편번호"),
			 new HeaderCell(80, 	"주소"),
			 new HeaderCell(2048, 	"업태"),
			 new HeaderCell(2048, 	"종목"),
			 
			 new HeaderCell(2048, 	"DM 주소구분"),
			 new HeaderCell(2048, 	"DM 우편번호"),
			 new HeaderCell(2048, 	"DM 주소"),
			 new HeaderCell(2048, 	"DM 나머지 주소"),
			 
			 new HeaderCell(2048, 	"홈페이지"),
			 new HeaderCell(2048, 	"은행"),
			 new HeaderCell(2048, 	"계좌번호"),
			 new HeaderCell(2048, 	"예금주"),
			 new HeaderCell(2048, 	"CMS코드"),

			 new HeaderCell(2048, 	"담당직원성명"),
			 new HeaderCell(2048, 	"담당자 부서명"),
			 new HeaderCell(2048, 	"담당자 전화번호"),
			 new HeaderCell(2048, 	"담당직원휴대폰"),
			 
			 new HeaderCell(2048, 	"회계연동코드"),
			 
			 new HeaderCell(2048, 	"주연락자성명"),
			 new HeaderCell(2048, 	"주연락자 부서"),
			 new HeaderCell(2048, 	"주연락자직위"),
			 new HeaderCell(2048, 	"주연락자전화번호"),
			 new HeaderCell(2048, 	"주연락자휴대폰"),
			 new HeaderCell(2048, 	"주연락자전자메일"),
			
			 
			 //new HeaderCell(2048, 	"종사업장번호"),
			 new HeaderCell(2048, 	"메모"),
			 new HeaderCell(2048, 	"활동여부"),
			 new HeaderCell(2048, 	"등록일")
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet("list");
		Row row = sheet.createRow((short) 0);
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, headerCells, "거래처 정보");
		
		int rowIndex = 1;
		for(Customer customer : list) {
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			CellIndex cellIndex = new CellIndex(-1);
			
			HashMap<String, String> addressMap = setAddress(customer.getAddress(), customer.getAddressDetail());
			
			setText(sheet, 			rowIndex, cellIndex, customer.getCustomerCode());	// 거래처 코드
			setText(sheet, 			rowIndex, cellIndex, customer.getCustomerName());	// 거래처명
			setText(sheet, 			rowIndex, cellIndex, customer.getCustomerGroup());	// 거래처분류
			setText(sheet, 			rowIndex, cellIndex, customer.getCustomerType());	// 거래처구분
			setText(sheet, 			rowIndex, cellIndex, customer.getBossName());	// 대표자명 
			
			setText(sheet, 			rowIndex, cellIndex, customer.getBusinessNumber());	// 사업자번호
			setText(sheet, 			rowIndex, cellIndex, customer.getTelNumber());	// 전화번호
			//setText(sheet, 			rowIndex, cellIndex, customer.getFaxGroup());	// 팩스국가번호
			setText(sheet, 			rowIndex, cellIndex, customer.getFaxNumber());	// 팩스번호
			setText(sheet, 			rowIndex, cellIndex, "지번");	// 주소구분
			setText(sheet, 			rowIndex, cellIndex, customer.getZipcode());	// 우편번호
			setTextLeft(sheet,		rowIndex, cellIndex, addressMap.get("address1") + " " + addressMap.get("address2"));	// 주소1
			setText(sheet, 			rowIndex, cellIndex, customer.getCategory());	// 업태
			setText(sheet, 			rowIndex, cellIndex, "");	// 종목
			
			setText(sheet, 			rowIndex, cellIndex, "지번");	// DM 주소구분
			setText(sheet, 			rowIndex, cellIndex, customer.getDmZipcode()); // DM 우편번호
			setText(sheet, 			rowIndex, cellIndex, customer.getDmAddress()); // DM 주소
			setText(sheet, 			rowIndex, cellIndex, customer.getDmAddressDetail());  // DM 나머지 주소
			
			setText(sheet, 			rowIndex, cellIndex, customer.getHomepage());	// 홈페이지
			setText(sheet, 			rowIndex, cellIndex, customer.getBankName()); // 주계좌 은행
			setText(sheet, 			rowIndex, cellIndex, customer.getBankNumber()); // 주계좌 번호 
			setText(sheet, 			rowIndex, cellIndex, customer.getBankInName()); //  주계좌 예금주명
			setText(sheet, 			rowIndex, cellIndex, customer.getBankCmsCode()); // 주계좌 CMS코드
			
			
			setText(sheet, 			rowIndex, cellIndex, customer.getStaffName());	// 담당자명
			setText(sheet, 			rowIndex, cellIndex, customer.getStaffDepartment()); // 담당자 부서명
			setText(sheet, 			rowIndex, cellIndex, customer.getStaffTelNumber()); // 담당자 전화번호
			setText(sheet, 			rowIndex, cellIndex, customer.getStaffPhoneNumber()); // 담당자 휴대전화
			
			setText(sheet, 			rowIndex, cellIndex, "");	// 회계연동코드
			
			setText(sheet, 			rowIndex, cellIndex, customer.getCustomerName()); // 거래처직원 성명
			setText(sheet, 			rowIndex, cellIndex, ""); // 거래처직원 부서
			setText(sheet, 			rowIndex, cellIndex, customer.getCustomerStaffPosition()); // 거래처직원 직위
			setText(sheet, 			rowIndex, cellIndex, customer.getCustomerStaffTelNumber()); // 거래처직원 전화번호
			setText(sheet, 			rowIndex, cellIndex, customer.getTelNumber()); // 거래처직원 휴대폰
			setText(sheet, 			rowIndex, cellIndex, customer.getCustomerStaffEmail()); // 거래처직원 전자메일
			
			setText(sheet, 			rowIndex, cellIndex, customer.getMemo());	// 메모
			setText(sheet, 			rowIndex, cellIndex, ""); // 활동여부
			setText(sheet, 			rowIndex, cellIndex, DateUtils.datetime(customer.getCreateDate()));
			
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
		
		try{
		
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
		
		}catch(Exception e){
			
			HashMap<String, String> addressMap = new HashMap<>();
			
			addressMap.put("address1", address);
			addressMap.put("address2", addrseeDetail);
			
			return addressMap;
		}
		
	}
}
