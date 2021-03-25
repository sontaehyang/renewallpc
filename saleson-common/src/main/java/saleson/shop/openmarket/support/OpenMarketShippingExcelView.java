package saleson.shop.openmarket.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import saleson.common.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class OpenMarketShippingExcelView extends AbstractSXSSFExcelView {

	private String type;
	
	public OpenMarketShippingExcelView(String type) {
		setFileName(type+"_오픈마켓 배송중 목록_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//skc//
		/*
		Cookie cookie = new Cookie("DOWNLOAD_STATUS", "complete");
		cookie.setHttpOnly(true);
	    cookie.setPath("/");					// 모든 경로에서 접근 가능하도록 
	    response.addCookie(cookie);				// 쿠키저장
	    
	    
		// 1. 데이터 가져오기.
	    List<OrderShippingInfo> list = (List<OrderShippingInfo>) model.get("list");
	    String[] header = (String[]) model.get("header");
		this.type = (String) model.get("type");
	    
	    // 2. 시트별 데이터 생성
	 	buildUserSheet(workbook, list, header);
	    */
	}

	//skc//
	/*
	private void buildUserSheet(SXSSFWorkbook workbook, List<OrderShippingInfo> list, String[] header) {
		
		
			
		if ("11st".equals(type)) {
			
			buildUserSheetBy11st(workbook, list, header);
			
		} else if ("gmarket".equals(type)) {
			
			buildUserSheetByGmarket(workbook, list, header);
			
		} else if ("storefarm".equals(type)) {
			
			buildUserSheetByStorefarm(workbook, list, header);
			
		} else if("naver".equals(type)) {
			
			buildUserSheetByNaver(workbook, list, header);
			
		} else {
			
			buildUserSheetByOther(workbook, list, header);
		}
		
		
	
	}
	
	
	private void createSheetHeader(Sheet sheet, Row row,
			HeaderCell[] headerCells) {
		row.setHeight((short) 800);
		
		int columCount = headerCells.length;
		for(int i = 0; i < headerCells.length; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) + headerCells[i].getWidth());
		}
		
		Cell[] cells = new Cell[columCount];

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
	
	private void buildUserSheetByNaver(SXSSFWorkbook workbook, List<OrderShippingInfo> list, String[] header) {
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(2500, 	"상품주문번호",		"상품주문번호"),
				new HeaderCell(2500, 	"발송일",		"발송일"),
				new HeaderCell(2500, 	"배송방법",		"배송방법"),
				new HeaderCell(2500, 	"택배사",		"택배사"),
				new HeaderCell(2500, 	"송장번호",		"송장번호")	
		};
		
		Sheet sheet = workbook.createSheet("발송처리");
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells);
		//createSheetHeader(sheet, row, headerCells, "발송내역");
	
		int rowIndex = 1;
		for(OrderShippingInfo order : list) {
			for (OrderItem orderItem : order.getOrderItems()) {
				LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) JsonViewUtils.jsonToObject(orderItem.getOpenMarketExcelRowToString(), new TypeReference<LinkedHashMap<String, String>>(){});
				
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);
				
				CellIndex cellIndex = new CellIndex(-1);
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("C")); // 상품주문번호
				setTextLeft(sheet, 			rowIndex, cellIndex, DateUtils.getToday(Const.DATE_FORMAT)); // 발송일
				setTextLeft(sheet, 			rowIndex, cellIndex, "택배,소포,등기"); // 배송방법
				setTextLeft(sheet, 			rowIndex, cellIndex, "CJ 대한통운"); // 택배사
				setTextLeft(sheet, 			rowIndex, cellIndex, orderItem.getDeliveryNumber()); // 송장번호
				rowIndex++;
			}
			
			
		}
	}
	
	private void buildUserSheetByStorefarm(SXSSFWorkbook workbook, List<OrderShippingInfo> list, String[] header) {
		
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(2500, 	"상품주문번호",		"상품주문번호"),
				new HeaderCell(2500, 	"발송일",		"발송일"),
				new HeaderCell(2500, 	"배송방법",		"배송방법"),
				new HeaderCell(2500, 	"택배사",		"택배사"),
				new HeaderCell(2500, 	"송장번호",		"송장번호")	
		};
		
		
		Sheet sheet = workbook.createSheet("발송처리");
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells);
		//createSheetHeader(sheet, row, headerCells, "발송내역");
	
		int rowIndex = 1;
		for(OrderShippingInfo order : list) {
			for (OrderItem orderItem : order.getOrderItems()) {
				LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) JsonViewUtils.jsonToObject(orderItem.getOpenMarketExcelRowToString(), new TypeReference<LinkedHashMap<String, String>>(){});
				
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);
				
				CellIndex cellIndex = new CellIndex(-1);
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("C")); // 상품주문번호
				setTextLeft(sheet, 			rowIndex, cellIndex, DateUtils.getToday(Const.DATE_FORMAT)); // 발송일
				setTextLeft(sheet, 			rowIndex, cellIndex, "택배,소포,등기"); // 배송방법
				setTextLeft(sheet, 			rowIndex, cellIndex, "CJ 대한통운"); // 택배사
				setTextLeft(sheet, 			rowIndex, cellIndex, orderItem.getDeliveryNumber()); // 송장번호
				rowIndex++;
			}
			
			
		}
		
	}
	
	private void buildUserSheetByGmarket(SXSSFWorkbook workbook, List<OrderShippingInfo> list, String[] header) {
		
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(2500, 	"아이디",		"아이디"),
				new HeaderCell(2500, 	"주문번호",		"주문번호"),
				new HeaderCell(2500, 	"택배사명(발송방법)",		"택배사명(발송방법)"),
				new HeaderCell(2500, 	"송장번호",		"송장번호")	
		};
		
		
		Sheet sheet = workbook.createSheet("Sheet1");
		Row row = sheet.createRow((short) 0);
	
		createSheetHeader(sheet, row, headerCells, "발송내역");
		
		int rowIndex = 2;
		for(OrderShippingInfo order : list) {
			for (OrderItem orderItem : order.getOrderItems()) {
				LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) JsonViewUtils.jsonToObject(orderItem.getOpenMarketExcelRowToString(), new TypeReference<LinkedHashMap<String, String>>(){});
				
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);
				
				CellIndex cellIndex = new CellIndex(-1);
				
				String deliveryCompanyName = "CJ GLS택배";
				if ("gmarket".equals(order.getOsType())) {
					deliveryCompanyName = "CJ택배";
				}
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("A")); // 아이디
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("C")); // 주문번호
				setTextLeft(sheet, 			rowIndex, cellIndex, deliveryCompanyName); // 택배사명(발송방법)
				setTextLeft(sheet, 			rowIndex, cellIndex, orderItem.getDeliveryNumber()); // 송장번호
				rowIndex++;
			}
			
			
		}
		
	}
	
	private void buildUserSheetBy11st(SXSSFWorkbook workbook, List<OrderShippingInfo> list, String[] header) {
		
		HeaderCell[] headerCells = new HeaderCell[]{
			new HeaderCell(2500, 	"번호"),
			new HeaderCell(2500, 	"주문일시"),
			new HeaderCell(2500, 	"결제완료일시"),
			new HeaderCell(2500, 	"주문번호"),
			new HeaderCell(2500, 	"배송번호"),
			new HeaderCell(2500, 	"택배사코드"),
			new HeaderCell(2500, 	"송장/등기번호"),
			new HeaderCell(2500, 	"배송방법"),
			new HeaderCell(2500, 	"상품번호"),
			new HeaderCell(2500, 	"상품명"),
			
			new HeaderCell(2500, 	"옵션/추가구성"),
			new HeaderCell(2500, 	"판매자상품코드"),
			new HeaderCell(2500, 	"판매단가"),
			new HeaderCell(2500, 	"옵션가"),
			new HeaderCell(2500, 	"수량"),
			new HeaderCell(2500, 	"주문금액"),
			new HeaderCell(2500, 	"배송비결제방식"),
			new HeaderCell(2500, 	"배송비"),
			new HeaderCell(2500, 	"도서산간배송비결제방식"),
			new HeaderCell(2500, 	"도서산간배송비"),
			
			new HeaderCell(2500, 	"구매자"),
			new HeaderCell(2500, 	"구매자ID"),
			new HeaderCell(2500, 	"수취인"),
			new HeaderCell(2500, 	"전화번호"),
			new HeaderCell(2500, 	"핸드폰"),
			new HeaderCell(2500, 	"우편번호"),
			new HeaderCell(2500, 	"배송지주소"),
			new HeaderCell(2500, 	"배송시요구사항"),
			new HeaderCell(2500, 	"판매방식"),
			new HeaderCell(2500, 	"주문상세번호"),
			
			new HeaderCell(2500, 	"전세계배송"),
			new HeaderCell(2500, 	"바코드"),
			new HeaderCell(2500, 	"바코드 출력여부"),
			new HeaderCell(2500, 	"전세계배송 수취인"),
			new HeaderCell(2500, 	"전세계배송 연락처1"),
			new HeaderCell(2500, 	"전세계배송 연락처2"),
			new HeaderCell(2500, 	"전세계배송 Zip Code"),
			new HeaderCell(2500, 	"전세계배송 Address")
		};
		
		
		Sheet sheet = workbook.createSheet("Sheet1");
		Row row = sheet.createRow((short) 0);
	
		createSheetHeader(sheet, row, headerCells, "발송내역");
		
		int rowIndex = 2;
		for(OrderShippingInfo order : list) {
			for (OrderItem orderItem : order.getOrderItems()) {
				LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) JsonViewUtils.jsonToObject(orderItem.getOpenMarketExcelRowToString(), new TypeReference<LinkedHashMap<String, String>>(){});
				
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);
				
				CellIndex cellIndex = new CellIndex(-1);
				
				String deliveryNumber = orderItem.getDeliveryNumber();
				if (StringUtils.isNotEmpty(deliveryNumber)) {
					deliveryNumber = deliveryNumber.replace("-", "");
				}
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("A")); // 번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AU")); // 주문일시
				setTextLeft(sheet, 			rowIndex, cellIndex, ""); // 결제완료일시
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("C")); // 주문번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("F")); // 배송번호
				setTextLeft(sheet, 			rowIndex, cellIndex, "00034"); // 택배사코드
				setTextLeft(sheet, 			rowIndex, cellIndex, deliveryNumber); // 송장/등기번호
				setTextLeft(sheet, 			rowIndex, cellIndex, "일반배송"); // 배송방법
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AK")); // 상품번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("G")); // 상품명
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("H")); // 옵션/추가구성
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AL")); // 판매자상품코드
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AM")); // 판매단가
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AN")); // 옵션가
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("K")); // 수량
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("L")); // 주문금액
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("X")); // 배송비결제방식
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("Y")); // 배송비
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("Z")); // 도서산간배송비결제방식
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BO")); // 도서산간배송비
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AH")); // 구매자
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AI")); // 구매ID
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("M")); // 수취인
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AD")); // 전화번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AC")); // 핸드폰
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AE")); // 우편번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AF")); // 배송지주소
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AG")); // 배송시요구사항
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AJ")); // 판매방식
				setTextLeft(sheet, 			rowIndex, cellIndex, ""); // 주문상세번호
				

				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BE")); // 전세계배송
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("I")); // 바코드
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BG")); // 바코드 출력여부
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BH")); // 전세계배송 수취인
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BI")); // 전세계배송 연락처1
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BJ")); // 전세계배송 연락처2
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BK")); // 전세계배송 ZIPCODE
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BL")); // 전세계배송 Address
				rowIndex++;
			}
			
			
		}
		
	}
	*/
	
	/**
	 * 2015.03.20일 11번가 배송처리 엑셀 형식 변경됨 - cjh
	 * @param workbook
	 * @param list
	 * @param header
	 */
	//skc//
	/*
	private void buildUserSheetBy11stBack(SXSSFWorkbook workbook, List<OrderShippingInfo> list, String[] header) {
		
		HeaderCell[] headerCells = new HeaderCell[]{
				new HeaderCell(2500, 	"번호",		"번호"),
				new HeaderCell(2500, 	"배송번호",		"배송번호"),
				new HeaderCell(2500, 	"주문번호",		"주문번호"),
				new HeaderCell(2500, 	"상품번호",		"상품번호"),
				new HeaderCell(2500, 	"판매자상품코드",		"판매자상품코드"),
				new HeaderCell(2500, 	"상품명",		"상품명"),
				new HeaderCell(2500, 	"옵션",		"옵션"),
				new HeaderCell(2500, 	"수량",		"수량"),
				new HeaderCell(2500, 	"옵션가",		"옵션가"),
				new HeaderCell(2500, 	"추가구성상품",		"추가구성상품"),
				
				new HeaderCell(2500, 	"판매단가",		"판매단가"),
				new HeaderCell(2500, 	"주문금액",		"주문금액"),
				new HeaderCell(2500, 	"추가구성 상품금액",		"추가구성 상품금액"),
				new HeaderCell(2500, 	"총 주문금액",		"총 주문금액"),
				new HeaderCell(2500, 	"판매자기본할인",		"판매자기본할인"),
				new HeaderCell(2500, 	"정산예정금액",		"정산예정금액"),
				new HeaderCell(2500, 	"결재액",		"결재액"),
				new HeaderCell(2500, 	"배송비",		"배송비"),
				new HeaderCell(2500, 	"배송비구분",		"배송비구분"),
				new HeaderCell(2500, 	"구매자",		"구매자"),
				
				new HeaderCell(2500, 	"구매자ID",		"구매자ID"),
				new HeaderCell(2500, 	"수취인",		"수취인"),
				new HeaderCell(2500, 	"전화번호",		"전화번호"),
				new HeaderCell(2500, 	"휴대폰",		"휴대폰"),
				new HeaderCell(2500, 	"배송지우편번호",		"배송지우편번호"),
				new HeaderCell(2500, 	"배송지주소",		"배송지주소"),
				new HeaderCell(2500, 	"배송시요구사항",		"배송시요구사항"),
				new HeaderCell(2500, 	"주문일시",		"주문일시"),
				new HeaderCell(2500, 	"결제완료",		"결제완료"),
				new HeaderCell(2500, 	"판매방식",		"판매방식"),
				
				new HeaderCell(2500, 	"배송방법",		"배송방법"),
				new HeaderCell(2500, 	"택배사코드",		"택배사코드"),
				new HeaderCell(2500, 	"송장/등기번호",		"송장/등기번호"),
				new HeaderCell(2500, 	"주문상세번호",		"주문상세번호"),
				new HeaderCell(2500, 	"판매자추가할인",		"판매자추가할인"),
				new HeaderCell(2500, 	"서비스이용료정책",		"서비스이용료정책"),
				new HeaderCell(2500, 	"고정서비스이용료(율)",		"고정서비스이용료(율)"),
				new HeaderCell(2500, 	"서비스이용료(%)",		"서비스이용료(%)"),
				new HeaderCell(2500, 	"전세계배송",		"전세계배송"),
				new HeaderCell(2500, 	"바코드",		"바코드"),
				
				new HeaderCell(2500, 	"바코드 출력여부",		"바코드 출력여부"),
				new HeaderCell(2500, 	"전세계배송 수취인",		"전세계배송 수취인"),
				new HeaderCell(2500, 	"전세계배송 연락처1",		"전세계배송 연락처1"),
				new HeaderCell(2500, 	"전세계배송 연락처2",		"전세계배송 연락처2"),
				new HeaderCell(2500, 	"전세계배송 Zip Code",		"전세계배송 Zip Code"),
				new HeaderCell(2500, 	"전세계배송 Address",		"전세계배송 Address")
					
		};
		
		
		Sheet sheet = workbook.createSheet("Sheet1");
		Row row = sheet.createRow((short) 0);
	
		createSheetHeader(sheet, row, headerCells, "발송내역");
		
		int rowIndex = 2;
		for(OrderShippingInfo order : list) {
			for (OrderItem orderItem : order.getOrderItems()) {
				LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) JsonViewUtils.jsonToObject(orderItem.getOpenMarketExcelRowToString(), new TypeReference<LinkedHashMap<String, String>>(){});
				
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);
				
				CellIndex cellIndex = new CellIndex(-1);
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("A")); // 번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("F")); // 배송번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("C")); // 주문번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AJ")); // 상품번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AK")); // 판매자상품코드
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("G")); // 상품명
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("H")); // 옵션
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("K")); // 수량
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AM")); // 옵션가
				setTextLeft(sheet, 			rowIndex, cellIndex, ""); // 추가구성상품
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AL")); // 판매단가
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("L")); // 주문금액
				setTextLeft(sheet, 			rowIndex, cellIndex, ""); // 추가구성 상품금액
				setTextLeft(sheet, 			rowIndex, cellIndex, ""); // 총 주문금액
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AN")); // 판매자기본할인
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AS")); // 정산예정금액
				setTextLeft(sheet, 			rowIndex, cellIndex, ""); // 결재액
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("X")); // 배송비
				setTextLeft(sheet, 			rowIndex, cellIndex,""); // 배송비구분
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AG")); // 구매자
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AH")); // 구매자ID
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("M")); // 수취인
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AC")); // 전화번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AB")); // 휴대폰
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AD")); // 배송지우편번호
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AE")); // 배송지주소
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AF")); // 배송시요구사항
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AT")); // 주문일시
				setTextLeft(sheet, 			rowIndex, cellIndex, ""); // 결제완료
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AI")); // 판매방식
				
				setTextLeft(sheet, 			rowIndex, cellIndex, "일반배송"); // 배송방법
				setTextLeft(sheet, 			rowIndex, cellIndex, "00034"); // 택배사코드
				
				String deliveryNumber = orderItem.getDeliveryNumber();
				if (StringUtils.isNotEmpty(deliveryNumber)) {
					deliveryNumber = deliveryNumber.replace("-", "");
				}
				
				setTextLeft(sheet, 			rowIndex, cellIndex, deliveryNumber); // 송장/등기번호
				setTextLeft(sheet, 			rowIndex, cellIndex, ""); // 주문상세번호`
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AO")); // 판매자추가할인
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AP")); // 서비스이용료정책
				setTextLeft(sheet, 			rowIndex, cellIndex, ""); // 고정서비스이용료(율)
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("AR")); // 서비스이용료(%)
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BE")); // 전세계배송
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("I")); // 바코드
				
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BF")); // 바코드 출력여부
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BG")); // 전세계배송 수취인
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BH")); // 전세계배송 연락처1
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BI")); // 전세계배송 연락처2
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BJ")); // 전세계배송 Zip Code
				setTextLeft(sheet, 			rowIndex, cellIndex, (String) map.get("BK")); // 전세계배송 Address
				rowIndex++;
			}
			
			
		}
		
	}
	
	private void buildUserSheetByOther(SXSSFWorkbook workbook, List<OrderShippingInfo> list, String[] header) {
		HeaderCell[] headerCells = new HeaderCell[0];
		
		int i = 0;
		for(String s : header) {
			headerCells = (HeaderCell[]) ArrayUtils.add(headerCells, new HeaderCell(15, s));
		}
		
		Sheet sheet = workbook.createSheet("list");
		Row row = sheet.createRow((short) 0);
	
		createSheetHeader(sheet, row, headerCells, "발송내역");
		
		int rowIndex = 2;
		for(OrderShippingInfo order : list) {
			
			for (OrderItem orderItem : order.getOrderItems()) {
				LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) JsonViewUtils.jsonToObject(orderItem.getOpenMarketExcelRowToString(), new TypeReference<LinkedHashMap<String, String>>(){});
				
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);
				
				CellIndex cellIndex = new CellIndex(-1);
				for(String key : map.keySet()) {
					String value = (String) map.get(key);
					
					if ("11st".equals(type)) {
						
						// 송장 번호
						if (key.equals("P")) {
							value = orderItem.getDeliveryNumber();
						}
					} else if ("gmarket".equals(type)) {
						
						// 송장 번호
						if (key.equals("X")) {
							value = orderItem.getDeliveryNumber();
						}
						
					} else if ("storefarm".equals(type)) {
						
						// 송장 번호
						if (key.equals("T")) {
							value = orderItem.getDeliveryNumber();
						}
						
					}
					
					setTextLeft(sheet, 			rowIndex, cellIndex, value);
				}

				rowIndex++;
			}
		}
	}
	*/
}
