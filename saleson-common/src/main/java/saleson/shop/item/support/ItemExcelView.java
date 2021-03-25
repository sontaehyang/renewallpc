package saleson.shop.item.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.StringUtils;
import saleson.common.Const;
import saleson.seller.main.domain.Seller;
import saleson.shop.brand.domain.Brand;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.item.domain.*;
import saleson.shop.shipment.domain.Shipment;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class ItemExcelView extends AbstractSXSSFExcelView {
	
	private static final String[] TEAMS = new String[] {
		"DONT_USE",				// 사용하지 않음.
		"esthetic",				// 1. 화장품
		"nail",					// 2. 네일
		"matsuge_extension",	// 3. 마사지
		"hair",					// 4. 헤어
		"sale_outlets",			// 5. 세일
		"-"						// 6. 무소속
	};
	
	
	
	
	private ItemParam itemParam;
	//private List<Delivery> deliveryList;
	private List<Brand> brandList = new ArrayList<>();
	private List<DeliveryCompany> deliveryCompanyList = new ArrayList<>();
	private List<Seller> sellerList = new ArrayList<>();
	private List<Shipment> shipmentList = new ArrayList<>();
	private List<ShipmentReturn> shipmentReturnList = new ArrayList<>();
	private List<ItemNotice> itemNoticeCodes = new ArrayList<>();
	private int deliveryChargeCount = 0;
	
	public ItemExcelView() {
		setFileName("ITEM_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Cookie cookie = new Cookie("DOWNLOAD_STATUS", "complete");
		cookie.setHttpOnly(true);
		//cookie.setMaxAge(60*60*24);				// 쿠키 유지 기간 - 1일
	    cookie.setPath("/");					// 모든 경로에서 접근 가능하도록 
	    response.addCookie(cookie);				// 쿠키저장
		
		
		// 1. 데이터 가져오기.
		itemParam = (ItemParam) model.get("itemParam");
		// [SKC임시] deliveryList = (List<Delivery>) model.get("deliveryList");
		brandList = (List<Brand>) model.get("brandList");
		deliveryCompanyList = (List<DeliveryCompany>) model.get("deliveryCompanyList");
		sellerList = (List<Seller>) model.get("sellerList");
		shipmentList = (List<Shipment>) model.get("shipmentList");
		shipmentReturnList = (List<ShipmentReturn>) model.get("shipmentReturnList");
		itemNoticeCodes = (List<ItemNotice>) model.get("itemNoticeCodes");
		
		List<Item> list = (List<Item>) model.get("itemList");
		
		List<ItemInfo> itemInfoList = (List<ItemInfo>) model.get("itemInfoList");
		List<ItemInfo> itemInfoMobileList = (List<ItemInfo>) model.get("itemInfoMobileList");
		List<ExcelItemCategory> itemCategoryList = (List<ExcelItemCategory>) model.get("itemCategoryList");
		List<ExcelItemRelation> itemRelationList = (List<ExcelItemRelation>) model.get("itemRelationList");
		List<ExcelItemAddition> itemAdditionList = (List<ExcelItemAddition>) model.get("itemAdditionList");
		List<ExcelItemPointConfig> itemPointConfigList = (List<ExcelItemPointConfig>) model.get("itemPointConfigList");
		List<Item> itemKeywordList = (List<Item>) model.get("itemKeywordList");
	
		// 2. 시트별 데이터 생성
		// 2-1. item_main (OP_ITEM)
		buildItemSheet(workbook, list, itemPointConfigList);
		
		// 2-2. item_seo  (OP_ITEM)
		buildItemSubSheet(workbook, list);
		
		// 2-3. item_table  (OP_ITEM_INFO)
		buildItemInfoSheet(workbook, itemInfoList);
		
		// 2-4. item_table_mobile  (OP_ITEM_INFO)
		// buildItemInfoMobileSheet(workbook, itemInfoMobileList);
		
		// 2-5. 상품 옵션 Sheet (OP_ITEM_OPTION)
		buildItemOptionSheet(workbook, list);
		
		// 2-6. 상품 이미지 Sheet (OP_SHOP_ITEM_IMAGE)
		buildItemImageSheet(workbook, list);

		// 2-7. 상품 추가구성상품 Sheet (OP_ITEM_ADDITION)
		buildItemAdditionSheet(workbook, itemAdditionList);
				
		// 2-8. 상품 관련상품 Sheet (OP_ITEM_RELATION)
		buildItemRelationSheet(workbook, itemRelationList);
		
		// 2-9. 상품 카테고리 Sheet (OP_ITEM_CATEGORY)
		buildItemCategorySheet(workbook, itemCategoryList);
		
		/*
		// 2-10. 상품 포인터 설정 Sheet (OP_POINT_CONFIG)
		buildItemPointConfigSheet(workbook, itemPointConfigList);
		*/
		
		// 2-11. 상품 CHECK Sheet (OP_ITEM)
		buildItemCheckSheet(workbook, list);
		
		// 2-12. 상품 검색어 Sheet (OP_ITEM) - ITEM_KEYWORD
		buildItemKeywordSheet(workbook, itemKeywordList);
		
	}
	
	
	private void buildItemSheet(SXSSFWorkbook workbook, List<Item> list, List<ExcelItemPointConfig> itemPointConfigList) {
		if (list == null || !Arrays.asList(itemParam.getExcelDownloadData()).contains("item_main")) {
			return;
		}
		
		// 3. 시트 생성
		String title = MessageUtils.getMessage("M01270") + "(MAIN)";	// 상품정보 (MAIN)
		String sheetTitle = "item_main";	
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			new HeaderCell(1500, 	"구분(*)", 		"n:신규, u:업데이트, d:삭제"),
			new HeaderCell(1024, 	"상품No.(*)",	"새로운 상품등록시 반드시 하나 이상의 문자를 포함해야합니다. ex)g12345, 12345g"),	// 상품번호

			// 기본정보
			new HeaderCell(8192, 	"상품명(*)"),					// 상품명
			new HeaderCell(1524, 	"공급사(*)",			getSellerList(), 3, sellerList.size() + 2),	// 공급사
			new HeaderCell(1024, 	"제조사"),						// 제조사
			new HeaderCell(1024, 	"브랜드", 			getBrandList(), 3, brandList.size() + 2),			// 브랜드
			new HeaderCell(1024, 	"원산지"),						// 원산지
			new HeaderCell(512, 	"무게"),						// 무게
			new HeaderCell(512, 	"수량"),						// 수량
			new HeaderCell(512, 	"과세구분",				"1:과세, 2:면세"),			// 과세구분
			new HeaderCell(1524, 	"사은품 사용유무",		"Y:사용, N:사용안함"),		// 사은품 사용유무
			new HeaderCell(8192, 	"사은품 정보"),					// 사은품 정보
			new HeaderCell(8192, 	"상품간략설명"),				// 상품간략설명
			new HeaderCell(5192, 	"검색어"),						// 검색어

			// 판매설정
			new HeaderCell(512, 	"판매가격(*)", 		""),		// 판매가격
			new HeaderCell(1024, 	"수수료 설정(*)", 		"1:입점업체 수수료로 설정, 2:상품별 수수료로 설정, 3:공급가 설정"),				// 수수료 설정
			new HeaderCell(512, 	"수수료율"),							// 수수료율
			new HeaderCell(512, 	"정가", 				""),	// 정가
			/* new HeaderCell(512, 	"원가(*)", 			""),*/					// 원가
			new HeaderCell(512, 	"재고연동",			"옵션이 등록된 경우 옵션재고 수량으로 판단됩니다., Y:연동함, N:연동안함(무제한)"),	// 재고연동
			new HeaderCell(512, 	"상품재고"),						// 상품재고
			new HeaderCell(1024, 	"관리코드"),						// 관리코드
			new HeaderCell(512, 	"품절여부",			"1일시 무조건 품절로 표시됩니다., 0:품절안함, 1:품절"),	// 품절여부
			new HeaderCell(1024, 	"최소주문수량"),					// 최소주문수량
			new HeaderCell(1024, 	"최대주문수량"),					// 최대주문수량
			new HeaderCell(1024, 	"쿠폰사용여부",		"Y:사용, N:사용안함"),

			// 할인/포인트 설정
			new HeaderCell(512, 		"즉시할인",			"Y:사용, N:사용안함"),	// 즉시할인
			new HeaderCell(1024, 	"즉시할인 금액"),										// 즉시할인 금액
			new HeaderCell(512, 		MessageUtils.getMessage("M00246") + " 지급", 			"Y:사용, N:사용안함"),		// 포인트 지급
			new HeaderCell(1024, 	MessageUtils.getMessage("M00246")),			// 포인트
			new HeaderCell(1024, 	"적립구분",			"1:%, 2:P"),				// 적립구분

			// 관리항목
			new HeaderCell(1024, 	"상품공개유무(*)", 		"Y:공개, N:비공개"),	// 상품공개유무

			// 배송정보 설정
			new HeaderCell(1024, 	"배송구분(*)", 		"1:본사배송, 2:업체배송"),										// 배송구분
			new HeaderCell(1024, 	"택배사", 			getDeliveryCompanyList(), 3, deliveryCompanyList.size() + 2),	// 택배사
			new HeaderCell(1024, 	"출고지 주소(*)", 		getShipmentList(), 6, shipmentList.size() + 10),			// 출고지 주소
			new HeaderCell(2048, 	"배송비 종류(*)", 		"1:무료배송, 2:판매자조건부, 3:출고지조건부, 4:상품조건부, 5:개당배송비, 6:고정배송비"),	// 배송비 종류
			new HeaderCell(1024, 	"배송비"),																					// 배송비
			new HeaderCell(2648, 	"배송비 무료 기준금액"),																		// 배송비 무료 기준금액
			new HeaderCell(2648, 	"배송비 추가 기준수량"),																		// 배송비 추가 기준수량
			new HeaderCell(2648, 	"제주 추가배송비"),																			// 제주 추가배송비
			new HeaderCell(2648, 	"도서산간 추가배송비"),																		// 도서산간 추가배송비
			new HeaderCell(3148, 	"퀵배송 추가요금 설정",		"Y:설정, N:미설정"),									// 퀵배송 추가요금 설정
			new HeaderCell(3448, 	"반품/교환 신청 가능여부",		"Y:가능, N:불가능"),								// 반품신청 가능여부
			new HeaderCell(2648, 	"반품/교환 구분(*)", 	"1:본사반품, 2:업체반품"),									// 반품/교환 구분
			new HeaderCell(2648,	"반품/교환 주소(*)",		getShipmentReturnList(), 5, shipmentReturnList.size() + 10),		// 반품/교환 주소
			new HeaderCell(2648,	"반품/교환 배송비(*)"),						// 반품/교환 배송비
			new HeaderCell(3448,	"상품 상단 내용 사용여부", 	"Y:사용, N:사용안함"),									// 상품 상단 내용 사용여부
			new HeaderCell(3448,	"상품 하단 내용 사용여부",	"Y:사용, N:사용안함"),									// 상품 하단 내용 사용여부
			new HeaderCell(8148,	"상품 상세 설명"),							// 상품 상세 설명
			new HeaderCell(8148,	"상품 상세 설명(모바일)"),					// 상품 상세 설명(모바일)

			new HeaderCell(512, 	"조회수(x)"),								// 조회 수
			new HeaderCell(2048, 	"등록일(x)")								// 등록일
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		
		// Table Body
		int rowIndex = 2;
		int count = itemPointConfigList.size()-1;
		for (Item item : list) {
			
			/**
			 *  ROW병합이 있는 경우 설정
			 * list2 = user.getSubList();
				int entrySize = list2.size();
				int startRowIndex = rowIndex;
				int endRowIndex = rowIndex + entrySize;
			 */
			
				
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			// CELL 데이터.
			
			// Team Index ==> TEAMS 참고 
			String teamIndex = Integer.toString(Arrays.asList(TEAMS).indexOf(item.getTeam()));
			
			//int cellIndex = -1;
			CellIndex cellIndex = new CellIndex(-1);
			setText(sheet, 			rowIndex, cellIndex, "u");				// 컨트롤
			setText(sheet, 			rowIndex, cellIndex, item.getItemUserCode());	// 상품코드
			
			// 기본정보
			setTextLeft(sheet, 		rowIndex, cellIndex, item.getItemName());		// 상품명
			setTextLeft(sheet,		rowIndex, cellIndex, Long.toString(item.getSellerId()));		// 공급사
			setTextLeft(sheet, 		rowIndex, cellIndex, item.getManufacturer());	// 제조사
			setNumber(sheet, 		rowIndex, cellIndex, item.getBrandId());		// 브랜드
			setTextLeft(sheet, 		rowIndex, cellIndex, item.getOriginCountry());	// 원산지
			setTextRight(sheet, 	rowIndex, cellIndex, item.getWeight());			// 무게
			setTextRight(sheet, 	rowIndex, cellIndex, item.getDisplayQuantity());// 수량
			setText(sheet, 			rowIndex, cellIndex, item.getTaxType());		// 과세구분
			setText(sheet, 			rowIndex, cellIndex, item.getFreeGiftFlag());	// 사은품 사용유무
			setTextLeft(sheet, 		rowIndex, cellIndex, item.getFreeGiftName());	// 사은품 정보
			setTextLeft(sheet, 		rowIndex, cellIndex, item.getItemSummary());	// 상품간략설명
			setTextLeft(sheet, 		rowIndex, cellIndex, item.getItemKeyword());	// 검색어
			
			// 판매설정
			setNumberRight(sheet, 	rowIndex, cellIndex, item.getSalePrice());		// 판매가격
			setText(sheet, 			rowIndex, cellIndex, item.getCommissionType());	// 수수료 설정
			
			if ("2".equals(item.getCommissionType())) {
				setFloatFormat(sheet, 	rowIndex, cellIndex, item.getCommissionRate());	// 수수료율
			} else {
				setText(sheet, 	rowIndex, cellIndex, "");								// 수수료율
			}
			
			setTextRight(sheet, 	rowIndex, cellIndex, item.getItemPrice());			// 정가
			/*setNumberRight(sheet, 	rowIndex, cellIndex, item.getCostPrice());	*/		// 원가
			setText(sheet, 	rowIndex, cellIndex, item.getStockFlag());					// 재고연동
			setTextRight(sheet, 	rowIndex, cellIndex, item.getStockQuantity() == -1 ? "" : Integer.toString(item.getStockQuantity()));		// 상품재고
			setTextLeft(sheet, 	rowIndex, cellIndex, item.getStockCode());				// 관리코드
			setText(sheet, 	rowIndex, cellIndex, item.getSoldOut());					// 품절여부
			setTextRight(sheet, 	rowIndex, cellIndex, item.getOrderMinQuantity() == -1 ? "" : Integer.toString(item.getOrderMinQuantity()));	// 최소 주문 수량
			setTextRight(sheet, 	rowIndex, cellIndex, item.getOrderMaxQuantity() == -1 ? "" : Integer.toString(item.getOrderMaxQuantity()));	// 최대 주문 수량
			setText(sheet, 	rowIndex, cellIndex, item.getCouponUseFlag());	// 쿠폰 사용가능 여부
			
			// 할인/포인트 설정
			setText(sheet, 	rowIndex, cellIndex, item.getSellerDiscountFlag());				// 즉시할인
			setNumberRight(sheet, 	rowIndex, cellIndex, item.getSellerDiscountAmount());	// 즉시할인 금액
			setText(sheet, 	rowIndex, cellIndex, item.getSellerPointFlag());				// 포인트 지급
			
			if ("Y".equals(item.getSellerPointFlag())) {
				setFloatFormat(sheet, 	rowIndex, cellIndex, (float) itemPointConfigList.get(count).getPoint());	// 포인트
				setText(sheet, 	rowIndex, cellIndex, itemPointConfigList.get(count).getPointType());				// 적립구분
				count--;
			} else {
				setText(sheet, 	rowIndex, cellIndex, "");	// 포인트
				setText(sheet, 	rowIndex, cellIndex, "");	// 적립구분
			}
			
			// 관리항목
			setText(sheet, 			rowIndex, cellIndex, item.getDisplayFlag());			// 공개여부 
			
			// 배송정보 설정
			setText(sheet, 			rowIndex, cellIndex, item.getDeliveryType());			// 배송구분
			setNumber(sheet, 		rowIndex, cellIndex, item.getDeliveryCompanyId());		// 택배사
			setNumber(sheet, 		rowIndex, cellIndex, item.getShipmentId());				// 출고지 주소
			setText(sheet, 			rowIndex, cellIndex, item.getShippingType());			// 배송비 종류
			setNumberRight(sheet, 	rowIndex, cellIndex, item.getShipping());				// 배송비
			setNumberRight(sheet, 	rowIndex, cellIndex, item.getShippingFreeAmount());		// 배송비 무료 기준금액
			setNumberRight(sheet, 	rowIndex, cellIndex, item.getShippingItemCount());		// 배송비 추가 기준수량
			setNumberRight(sheet, 	rowIndex, cellIndex, item.getShippingExtraCharge1());	// 제주 추가배송비
			setNumberRight(sheet, 	rowIndex, cellIndex, item.getShippingExtraCharge2());	// 도서산간 추가배송비
			setText(sheet, 			rowIndex, cellIndex, item.getQuickDeliveryExtraChargeFlag());	// 퀵배송 추가요금 설정
			setText(sheet, 			rowIndex, cellIndex, item.getItemReturnFlag());			// 반품/교환 신청 가능여부
			setText(sheet, 			rowIndex, cellIndex, item.getShipmentReturnType());		// 반품/교환 구분
			setNumber(sheet, 		rowIndex, cellIndex, item.getShipmentReturnId());		// 반품/교환 주소
			setNumberRight(sheet, 	rowIndex, cellIndex, item.getShippingReturn());			// 반품/교환 배송비
			setText(sheet, 			rowIndex, cellIndex, item.getHeaderContentFlag());		// 상품 상단 내용 사용여부
			setText(sheet, 			rowIndex, cellIndex, item.getFooterContentFlag());		// 상품 하단 내용 사용여부
			setLongText(sheet, 		rowIndex, cellIndex, item.getDetailContent());			// 상품 상세 설명
			setLongText(sheet, 		rowIndex, cellIndex, item.getDetailContentMobile());	// 상품 상세 설명(모바일)
			
			setNumberRight(sheet, 	rowIndex, cellIndex, item.getHits());			// 조회 수
			setText(sheet, 			rowIndex, cellIndex, item.getCreatedDate());	// 등록일
			
			rowIndex++;
		}
	}

	
	/**
	 * 2-2. 상품 SEO
	 * @param workbook
	 * @param list
	 */
	private void buildItemSubSheet(SXSSFWorkbook workbook, List<Item> list) {
		if (list == null || !Arrays.asList(itemParam.getExcelDownloadData()).contains("item_seo")) {
			return;
		}
		
		// 3. 시트 생성
		String title = MessageUtils.getMessage("SEO 설정") + "(SEO)";	// SEO 설정 (SUB)
		String sheetTitle = "item_seo";	
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			 new HeaderCell(1024, 	"상품No.(*)"),				// 상품번호
			 new HeaderCell(8192, 	"상품명(x)"),					// 상품명
			 
			 new HeaderCell(3024,	"검색 엔진에 공개",		"Y:검색 엔진에 공개, N:검색 엔진에 비공개"),	// 검색 엔진에 공개	
			 new HeaderCell(8192, 	"브라우저 타이틀"),				// 브라우저 타이틀
			 new HeaderCell(8192, 	"Meta 키워드"),				// Meta 키워드
			 new HeaderCell(8192, 	"Meta Description"),		// Meta Description
			 new HeaderCell(8192, 	"H1"),						// H1
			 
			 //new HeaderCell(8192, 	"商品詳細説明(*)", 	""),				// 제품 상세 설명
			 //new HeaderCell(8192, 	"[M]商品詳細説明(*)", 	"モバイル")		// 제품 상세 설명 (모바일)	
		};
		
	
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		
		// Table Body
		int rowIndex = 2;
		for (Item item : list) {
			
			/**
			 *  ROW병합이 있는 경우 설정
			 * list2 = user.getSubList();
				int entrySize = list2.size();
				int startRowIndex = rowIndex;
				int endRowIndex = rowIndex + entrySize;
			 */
			
				
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 1024);
			
			// CELL 데이터.
			
			
			// Team Index ==> TEAMS 참고 
			String teamIndex = Integer.toString(Arrays.asList(TEAMS).indexOf(item.getTeam()));
			
			//int cellIndex = -1;
			CellIndex cellIndex = new CellIndex(-1);
			setText(sheet, 			rowIndex, cellIndex, item.getItemUserCode());		// 상품코드
			setTextLeft(sheet, 		rowIndex, cellIndex, item.getItemName());			// 상품명	
			
			setText(sheet, 			rowIndex, cellIndex, item.getSeo().getIndexFlag().equals("Y") ? "Y" : "N");	// SEO > 검색 엔진에 공개
			setLongText(sheet, 		rowIndex, cellIndex, item.getSeo().getTitle());					// SEO > 브라우저 타이틀
			setLongText(sheet, 		rowIndex, cellIndex, item.getSeo().getKeywords());				// SEO > Meta 키워드
			setLongText(sheet, 		rowIndex, cellIndex, item.getSeo().getDescription());			// SEO > Meta Description
			setLongText(sheet, 		rowIndex, cellIndex, item.getSeo().getHeaderContents1());		// SEO > H1
			
			//setTextLeft(sheet, 		rowIndex, cellIndex, item.getDetailContentTop());		// 제품 상세 설명1
			//setTextLeft(sheet, 		rowIndex, cellIndex, item.getDetailContent());			// 제품 상세 설명2
			
			rowIndex++;
		}
	}
	

	/**
	 * 2-3. 상품 상세정보 (테이블) Sheet (OP_ITEM_INFO)
	 * @param workbook
	 * @param list
	 */
	private void buildItemInfoSheet(SXSSFWorkbook workbook, List<ItemInfo> list) {
		if (list == null) return;
		
		// 1. 시트 생성
		String title = MessageUtils.getMessage("상품 정보고시 (테이블)");	// 상품 정보고시 (테이블)
		String sheetTitle = "item_table";	
		
		
		// 2. Cell (컬럼) 설정
		HeaderCell[] defaultHeaderCells = new HeaderCell[] {
			new HeaderCell(1024, 	"상품No.(*)"),		// 상품번호
			new HeaderCell(8192, 	"상품명(x)"),			// 상품명
			new HeaderCell(3192, 	"상품의 상품군(*)",		getItemNoticeCodes(), 7, itemNoticeCodes.size() + 2),		// 상품의 상품군
		};
		
		HeaderCell[] tableColumnHeaderCells = new HeaderCell[40];
		for (int i = 1; i <= 20; i++) {
			int index = i * 2;
			tableColumnHeaderCells[index - 2] = new HeaderCell(4096, 	"항목명" + i, "상품 상세 항목을 입력");
			tableColumnHeaderCells[index - 1] = new HeaderCell(8192, 	"값" + i, "상품 상세 항목을 입력");
		}
		
		HeaderCell[] headerCells = new HeaderCell[defaultHeaderCells.length + tableColumnHeaderCells.length];
		
		int index = 0;
		for (int i = 0; i < defaultHeaderCells.length; i++) {
			headerCells[index] = defaultHeaderCells[i];
			index++;
		}
		for (int i = 0; i < tableColumnHeaderCells.length; i++) {
			headerCells[index] = tableColumnHeaderCells[i];
			index++;
		}
	
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		
		// Table Body
		int rowIndex = 2;
		
		String oldItemUserCode = "";
		int itemInfoCount = 0;
		CellIndex cellIndex = new CellIndex(-1);
		
		int listCount = 0;
		for (ItemInfo itemInfo : list) {
			
			if (!oldItemUserCode.equals(itemInfo.getItemUserCode())) {
				
				// 공백
				if (listCount > 0) {
					for (int i = itemInfoCount; i < 20; i++) {
						setTextLeft(sheet, 		rowIndex, cellIndex, "");	// 테이블 항목명 
						setTextLeft(sheet, 		rowIndex, cellIndex, "");	// 테이블 값
					}
					
					rowIndex++;
				}
				
				
			
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 1024);
				
				// CELL 데이터.
				
				//int cellIndex = -1;
				cellIndex = new CellIndex(-1);
				
				setText(sheet, 			rowIndex, cellIndex, itemInfo.getItemUserCode());		// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, itemInfo.getItemName());			// 상품명
				setText(sheet, 		rowIndex, cellIndex, itemInfo.getItemNoticeCode());		// 상품의 상품군
				
				itemInfoCount = 0;
			}	
				
		
			setText(sheet, 		rowIndex, cellIndex, itemInfo.getTitle());			// 테이블 항목명 
			setText(sheet, 		rowIndex, cellIndex, itemInfo.getDescription());	// 테이블 값

			oldItemUserCode = itemInfo.getItemUserCode();
			itemInfoCount++;
			
			listCount++;
		}
		
		// 마지막 공백 삽입.
		for (int i = itemInfoCount; i < 20; i++) {
			setTextLeft(sheet, 		rowIndex, cellIndex, "");	// 테이블 항목명 
			setTextLeft(sheet, 		rowIndex, cellIndex, "");	// 테이블 값
		}
	}
	
	
	/**
	 * 2-2. 상품 상세정보 (테이블) Sheet (OP_ITEM_INFO)
	 * @param workbook
	 * @param list
	 */
	/*
	private void buildItemInfoMobileSheet(SXSSFWorkbook workbook, List<ItemInfo> list) {
		if (list == null) return;
		
		// 1. 시트 생성
		String title = MessageUtils.getMessage("M01271") + "[Mobile]";	// 상품상세설명(테이블)
		String sheetTitle = "item_table_mobile";	
		
		
		// 2. Cell (컬럼) 설정
		HeaderCell[] defaultHeaderCells = new HeaderCell[] {
			new HeaderCell(1024, 	"상품No.(*)"),		// 상품번호
			new HeaderCell(8192, 	"상품명(x)"),			// 상품명
			new HeaderCell(8192, 	"제품 상세 설명", 	"")		// 제품 상세 설명 (모바일)	
		};
		
		HeaderCell[] tableColumnHeaderCells = new HeaderCell[40];
		for (int i = 1; i <= 20; i++) {
			int index = i * 2;
			tableColumnHeaderCells[index - 2] = new HeaderCell(4096, 	"항목명" + i, "상품 상세 항목을 입력");
			tableColumnHeaderCells[index - 1] = new HeaderCell(8192, 	"값" + i, "상품 상세 항목을 입력");
		}
		
		HeaderCell[] headerCells = new HeaderCell[defaultHeaderCells.length + tableColumnHeaderCells.length];
		
		int index = 0;
		for (int i = 0; i < defaultHeaderCells.length; i++) {
			headerCells[index] = defaultHeaderCells[i];
			index++;
		}
		for (int i = 0; i < tableColumnHeaderCells.length; i++) {
			headerCells[index] = tableColumnHeaderCells[i];
			index++;
		}
	
		// 상단 타이틀 및 테이블 헤더 생성.
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, defaultHeaderCells, title);
		
		
		// Table Body
		int rowIndex = 2;
		
		String oldItemUserCode = "";
		int itemInfoCount = 0;
		CellIndex cellIndex = new CellIndex(-1);
		
		int listCount = 0;
		for (ItemInfo itemInfo : list) {
			
			if (!oldItemUserCode.equals(itemInfo.getItemUserCode())) {
				
				// 공백
				if (listCount > 0) {
					for (int i = itemInfoCount; i < 20; i++) {
						setTextLeft(sheet, 		rowIndex, cellIndex, "");	// 테이블 항목명 
						setTextLeft(sheet, 		rowIndex, cellIndex, "");	// 테이블 값
					}
					
					rowIndex++;
				}
			
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 1024);
				
				// CELL 데이터.
				
				//int cellIndex = -1;
				cellIndex = new CellIndex(-1);
				
				setText(sheet, 			rowIndex, cellIndex, itemInfo.getItemUserCode());		// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, itemInfo.getItemName());			// 상품명	
				setLongText(sheet, 		rowIndex, cellIndex, itemInfo.getDetailContent());		// 상세설명
				
				itemInfoCount = 0;
			}	
			
			setLongText(sheet, 		rowIndex, cellIndex, itemInfo.getTitle());			// 테이블 항목명 
			setLongText(sheet, 		rowIndex, cellIndex, itemInfo.getDescription());	// 테이블 값
			
			oldItemUserCode = itemInfo.getItemUserCode();
			itemInfoCount++;
			
			listCount++;
		}
		
		// 마지막 공백 삽입.
		for (int i = itemInfoCount; i < 20; i++) {
			setTextLeft(sheet, 		rowIndex, cellIndex, "");	// 테이블 항목명 
			setTextLeft(sheet, 		rowIndex, cellIndex, "");	// 테이블 값
		}
		
	}
	*/
	
	/**
	 * 2-5. 상품 옵션 (ITEM_OPTION)
	 * @param workbook
	 * @param list
	 */
	private void buildItemOptionSheet(SXSSFWorkbook workbook, List<Item> list) {
		if (list == null || !Arrays.asList(itemParam.getExcelDownloadData()).contains("item_option")) {
			return;
		}
		
		
		// 1. 시트 생성
		String title = MessageUtils.getMessage("M01272");	// 상품옵션
		String sheetTitle = "item_option";	
		
		// 2. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			new HeaderCell(1024, 	"상품No.(*)"),									// 상품번호
			new HeaderCell(8192, 	"상품명(x)"),									// 상품명
			new HeaderCell(2048, 	"상품옵션 형태(*)",	"S:선택형, S2:2조합형, S3:3조합형, T:텍스트형, C:옵션조합형"),		// 상품옵션 형태
			new HeaderCell(2048, 	"관리코드",		"옵션조합형일 경우에만 저장됩니다."),			// 관리코드
			new HeaderCell(2048, 	"ERP 상품코드",	"옵션조합형일 경우에만 저장됩니다."),			// ERP 상품코드
			new HeaderCell(4096, 	"옵션명1(*)"),									// 옵션명1
			new HeaderCell(4096, 	"옵션명2(*)"),									// 옵션명2
			new HeaderCell(4096, 	"옵션명3"),										// 옵션명3
			new HeaderCell(2048, 	"추가금액"),									// 추가금액
			new HeaderCell(2048, 	"원가"),										// 원가
			new HeaderCell(2048, 	"판매수량",		"옵션조합형일 경우에만 저장됩니다."),		// 판매수량
			new HeaderCell(2048, 	"재고연동(*)",	"Y:재고연동, N:무제한"),				// 재고연동
			new HeaderCell(2048, 	"재고수량", 	"입력 값이없는 경우는 무제한입니다."),	// 재고수량
			new HeaderCell(1024, 	"판매상태(*)", 	"Y:품절, N:판매가능"),	// 판매상태
			new HeaderCell(3000, 	"노출여부(*)", 	"Y:표시, N:비표시"),	// 노출여부
		};
		
	
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		
		// Table Body
		int rowIndex = 2;
		
		for (Item item : list) {
			int itemCount = 0;
			
			/**
			 *  ROW병합이 있는 경우 설정
			 * list2 = user.getSubList();
				int entrySize = list2.size();
				int startRowIndex = rowIndex;
				int endRowIndex = rowIndex + entrySize;
			 */
			
			
			for (ItemOptionGroup itemOptionGroup : item.getItemOptionGroups()) {
				
				int itemOptionGroupCount = 0;
				for (ItemOption itemOption : itemOptionGroup.getItemOptions()) {
					
					if (itemOption.getItemOptionId() == 0) {
						continue;
					}
					
					
					// 행 높이 설정
					row = sheet.createRow(rowIndex);
					row.setHeight((short) 400);
					
					// CELL 데이터.
					
					//int cellIndex = -1;
					CellIndex cellIndex = new CellIndex(-1);
					
					setText(sheet, 			rowIndex, cellIndex, item.getItemUserCode());				// 상품코드
					setTextLeft(sheet, 		rowIndex, cellIndex, item.getItemName());					// 상품명
					setText(sheet, 			rowIndex, cellIndex, itemOption.getOptionType());			// 상품옵션 형태
					setText(sheet, 			rowIndex, cellIndex, itemOption.getOptionStockCode());		// 관리코드
					setText(sheet, 			rowIndex, cellIndex, itemOption.getErpItemCode());			// ERP 상품코드 (옵션조합형)
					setTextLeft(sheet, 		rowIndex, cellIndex, itemOption.getOptionName1());			// 옵션명1
					setTextLeft(sheet, 		rowIndex, cellIndex, itemOption.getOptionName2());			// 옵션명2
					setTextLeft(sheet, 		rowIndex, cellIndex, itemOption.getOptionName3());			// 옵션명3
					setNumberRight(sheet, 	rowIndex, cellIndex, itemOption.getOptionPrice());			// 추가금액
					setNumberRight(sheet, 	rowIndex, cellIndex, itemOption.getOptionCostPrice());		// 원가
					setNumberRight(sheet, 	rowIndex, cellIndex, itemOption.getOptionQuantity());		// 판매수량
					setText(sheet, 			rowIndex, cellIndex, StringUtils.isEmpty(itemOption.getOptionStockFlag()) ? "N" : itemOption.getOptionStockFlag());		// 재고연동
					setTextRight(sheet, 	rowIndex, cellIndex, itemOption.getOptionStockQuantity() == -1 ? "" : Integer.toString(itemOption.getOptionStockQuantity()));	// 재고수량
					setText(sheet, 			rowIndex, cellIndex, StringUtils.isEmpty(itemOption.getOptionSoldOutFlag()) ? "N" : itemOption.getOptionSoldOutFlag());	// 판매상태
					setText(sheet, 			rowIndex, cellIndex, itemOption.getOptionDisplayFlag());	// 노출여부

					rowIndex++;

					/* Row 병합이 필요한 경우
					for (int i = 0; i < mergeRowCellIndexes.length; i++) {
						sheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex - 1, mergeRowCellIndexes[i], mergeRowCellIndexes[i])); 
					}
					*/
					
					itemOptionGroupCount++;
					itemCount++;
					
				}
			}
		}
	}
	
	/**
	 * 2-6. 상품 이미지 Sheet (OP_ITEM_IMAGE)
	 * @param workbook
	 * @param list
	 */
	private void buildItemImageSheet(SXSSFWorkbook workbook, List<Item> list) {
		if (list == null || !Arrays.asList(itemParam.getExcelDownloadData()).contains("item_image")) {
			return;
		}
		
		// 1. 시트 생성
		String title = MessageUtils.getMessage("M01273");	// 상품 이미지 정보
		String sheetTitle = "item_image";	
		
		// 2. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			new HeaderCell(1024, 	"상품No.(*)"),		// 상품번호
			new HeaderCell(8192, 	"상품명(x)"),			// 상품명
			new HeaderCell(4096, 	"이미지명(*)"),			// 이미지명
			new HeaderCell(1024, 	"출력순서(*)", "숫자입력")			// 출력순서
		};
	
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		
		for (Item item : list) {
			int itemCount = 0;
			
			for (ItemImage itemImage : item.getItemImages()) {
				if (itemImage.getItemImageId() == 0) {
					continue;
				}
				
				// 행 높이 설정
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);
				
				// CELL 데이터.
				
				//int cellIndex = -1;
				CellIndex cellIndex = new CellIndex(-1);
				
				/*
				if (itemCount == 0) {
					setText(sheet, 		rowIndex, cellIndex, item.getItemUserCode());		// 상품코드
					setTextLeft(sheet, 	rowIndex, cellIndex, item.getItemName());			// 상품명	
				} else {
					setText(sheet, 		rowIndex, cellIndex, "");							// 상품코드
					setTextLeft(sheet, 	rowIndex, cellIndex, "");							// 상품명	
				}
				*/
				
				setText(sheet, 		rowIndex, cellIndex, item.getItemUserCode());		// 상품코드
				setTextLeft(sheet, 	rowIndex, cellIndex, item.getItemName());			// 상품명	
				
				setTextLeft(sheet, 		rowIndex, cellIndex, itemImage.getImageName());		// 이미지명
				setNumber(sheet, 		rowIndex, cellIndex, itemImage.getOrdering());		// 이미지노출 순서
			
				rowIndex++;
				itemCount++;
			}
		}
	}

	/**
	 * 2-7. 상품 추가구성상품 Sheet (OP_ITEM_ADDITION)
	 * @param workbook
	 * @param list
	 */
	private void buildItemAdditionSheet(SXSSFWorkbook workbook,	List<ExcelItemAddition> list) {

		if (list == null) return;

		// 1. 시트 생성
		String title = MessageUtils.getMessage("추가구성상품 정보");
		String sheetTitle = "item_addition";

		// 2. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
				new HeaderCell(1024, 	"상품번호No.(*)"),				// 상품번호
				new HeaderCell(8192, 	"상품명(x)"),					// 상품명

				new HeaderCell(2048, 	"개별：상품No.(*)", 	""),	// 추가구성상품번호
				new HeaderCell(8192, 	"개별：상품명(x)", 		""),	// 추가구성상품명
				new HeaderCell(2048, 	"개별：출력순서(*)", 	"숫자입력")	// 출력순서
		};


		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);

		createSheetHeader(sheet, row, headerCells, title);

		// Table Body
		int rowIndex = 2;

		// String oldItemUserCode = "";
		for (ExcelItemAddition itemAddition : list) {

			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);

			// CELL 데이터.

			//int cellIndex = -1;
			CellIndex cellIndex = new CellIndex(-1);

			/*
			if (!itemRelation.getItemUserCode().equals(oldItemUserCode)) {
				setText(sheet, 			rowIndex, cellIndex, itemRelation.getItemUserCode());		// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, itemRelation.getItemName());			// 상품명
			} else {
				setText(sheet, 			rowIndex, cellIndex, "");							// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, "");							// 상품명
			}
			*/
			setText(sheet, 			rowIndex, cellIndex, itemAddition.getItemUserCode());		// 상품코드
			setTextLeft(sheet, 		rowIndex, cellIndex, itemAddition.getItemName());			// 상품명

			setText(sheet, 			rowIndex, cellIndex, itemAddition.getAdditionItemUserCode());	// 추가구성상품 상품번호
			setTextLeft(sheet, 		rowIndex, cellIndex, itemAddition.getAdditionItemName());		// 추가구성상품 상품명
			setNumber(sheet, 		rowIndex, cellIndex, itemAddition.getOrdering());				// 추가구성상품 노출순서


			rowIndex++;

			// oldItemUserCode = itemAddition.getItemUserCode();

		}
	}
	
	/**
	 * 2-8. 상품 관련상품 Sheet (OP_ITEM_RELATION)
	 * @param workbook
	 * @param list
	 */
	private void buildItemRelationSheet(SXSSFWorkbook workbook,	List<ExcelItemRelation> list) {
		
		if (list == null) return;
		
		// 1. 시트 생성
		String title = MessageUtils.getMessage("M01276");	// 관련상품 정보
		String sheetTitle = "item_relation";	
		
		// 2. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			new HeaderCell(1024, 	"상품번호No.(*)"),				// 상품번호
			new HeaderCell(8192, 	"상품명(x)"),					// 상품명
			
			new HeaderCell(2048, 	"개별：상품No.(*)", 	""),	// 관련상품번호
		 	new HeaderCell(8192, 	"개별：상품명(x)", 		""),	// 관련상품명
		 	new HeaderCell(2048, 	"개별：출력순서(*)", 	"숫자입력")			// 출력순서
		};
		
	
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		
		String oldItemUserCode = "";
		for (ExcelItemRelation itemRelation : list) {
				
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			// CELL 데이터.
			
			//int cellIndex = -1;
			CellIndex cellIndex = new CellIndex(-1);
			
			/*
			if (!itemRelation.getItemUserCode().equals(oldItemUserCode)) {
				setText(sheet, 			rowIndex, cellIndex, itemRelation.getItemUserCode());		// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, itemRelation.getItemName());			// 상품명	
			} else {
				setText(sheet, 			rowIndex, cellIndex, "");							// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, "");							// 상품명	
			}
			*/
			setText(sheet, 			rowIndex, cellIndex, itemRelation.getItemUserCode());		// 상품코드
			setTextLeft(sheet, 		rowIndex, cellIndex, itemRelation.getItemName());			// 상품명	
			
			setText(sheet, 			rowIndex, cellIndex, itemRelation.getRelationItemUserCode());	// 관련 상품번호
			setTextLeft(sheet, 		rowIndex, cellIndex, itemRelation.getRelationItemName());		// 관련 상품명
			setNumber(sheet, 		rowIndex, cellIndex, itemRelation.getOrdering());				// 관련상품 노출순서
			
			
			rowIndex++;
			
			oldItemUserCode = itemRelation.getItemUserCode();
		}
	}
	
	/**
	 * 2-9. 상품 카테고리 Sheet (OP_ITEM_CATEGORY)
	 * @param workbook
	 * @param list
	 */
	private void buildItemCategorySheet(SXSSFWorkbook workbook, List<ExcelItemCategory> list) {
		if (list == null) return;
		
		// 1. 시트 생성
		String title = MessageUtils.getMessage("M01274");	// 카테고리 정보
		String sheetTitle = "item_category";	
		
		// 2. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			new HeaderCell(1024, 	"상품No.(*)"),				// 상품번호
			new HeaderCell(8192, 	"상품명(x)"),					// 상품명
			new HeaderCell(2048, 	"카테고리 코드(*)", "ex), /categories/index/14100000000000 => 14100000000000, /categories/index/36300000000000 => 36300000000000"),			// 카테고리코드
			new HeaderCell(4096, 	"카테고리 이름(x)"),				// 카테고리명
			new HeaderCell(1024, 	"출력순서(*)", "숫자입력")			// 출력순서
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		// Table Body
		int rowIndex = 2;
		
		String oldItemUserCode = "";
		
		for (ExcelItemCategory itemCategory : list) {
			
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			// CELL 데이터.
			
			//int cellIndex = -1;
			CellIndex cellIndex = new CellIndex(-1);
			
			/*
			if (!itemCategory.getItemUserCode().equals(oldItemUserCode)) {
				setText(sheet, 			rowIndex, cellIndex, itemCategory.getItemUserCode());		// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, itemCategory.getItemName());			// 상품명	
			} else {
				setText(sheet, 			rowIndex, cellIndex, "");							// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, "");							// 상품명	
			}
			*/
			
			setText(sheet, 			rowIndex, cellIndex, itemCategory.getItemUserCode());		// 상품코드
			setTextLeft(sheet, 		rowIndex, cellIndex, itemCategory.getItemName());			// 상품명	
			
			setText(sheet, 			rowIndex, cellIndex, itemCategory.getCategoryUrl());	// 카테고리 코드
			setTextLeft(sheet, 		rowIndex, cellIndex, itemCategory.getCategoryName());	// 카테고리명
			setNumber(sheet, 		rowIndex, cellIndex, itemCategory.getOrdering());		// 카테고리 노출순서

			
			oldItemUserCode = itemCategory.getItemUserCode();
			
			rowIndex++;
			
		}
	}

	/*
	/**
	 * 2-10. 상품 포인터 설정 Sheet (OP_POINT_CONFIG)
	 * @param workbook
	 * @param itemPointConfigList
	 */
	private void buildItemPointConfigSheet(SXSSFWorkbook workbook, List<ExcelItemPointConfig> list) {
		if (list == null) return;
		
		/*
		// 1. 시트 생성
		String title = MessageUtils.getMessage("M01275");	// 포인트 정보
		String sheetTitle = "item_point";	
		
		// 2. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			new HeaderCell(1024, 	"상품No.(*)"),				// 상품번호
			new HeaderCell(8192, 	"상품명(x)"),					// 상품명
			new HeaderCell(2048, 	MessageUtils.getMessage("M00246") + "(*)", 		"숫자입력"),			// 포인트
			new HeaderCell(1024, 	"적립구분(*)", 		"1:%, 2:point"),					// 적립구분(*)
			new HeaderCell(2048, 	"적립기간_시작일(*)", 	"형식: YYYYMMDD (ex:20140706)"),	// 적립기간_시작일(*)
			new HeaderCell(2048, 	"적립기간_시작시간(*)", 	"형식: HH (ex:00 ~ 23)"),				// 적립기간_시작시간
			new HeaderCell(2048, 	"적립기간_종료일(*)", 	"형식: YYYYMMDD (ex:20140730)"),	// 적립기간_종료일
			new HeaderCell(2048, 	"적립기간_종료시간(*)", 	"형식: HH (ex:00 ~ 23)")				// 적립기간_종료시간
			//new HeaderCell(1024, 	"指定日", 			"形式: day")							// 지정일 (제외 요청)
		};
		
		// 상단 타이틀 및 테이블 헤더 생성.
		
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		//createSheetHeader(sheet, row, columWidth, titles, sheetTitle);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		
		// Table Body
		int rowIndex = 2;
		
		String oldItemUserCode = "";
		
		for (ExcelItemPointConfig itemPoint : list) {
			
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			// CELL 데이터.
			
			//int cellIndex = -1;
			CellIndex cellIndex = new CellIndex(-1);
			
			if (!itemPoint.getItemUserCode().equals(oldItemUserCode)) {
				setText(sheet, 			rowIndex, cellIndex, itemPoint.getItemUserCode());		// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, itemPoint.getItemName());			// 상품명	
				
			} else {
				setText(sheet, 			rowIndex, cellIndex, "");							// 상품코드
				setTextLeft(sheet, 		rowIndex, cellIndex, "");							// 상품명	
				
			}
			
			
			setNumberRight(sheet, 		rowIndex, cellIndex, itemPoint.getPoint());			// 적립포인트
			setText(sheet, 				rowIndex, cellIndex, itemPoint.getPointType());		// 적립구분 (1:비율, 2:금액)
			setText(sheet, 				rowIndex, cellIndex, itemPoint.getStartDate());		// 시작일
			setText(sheet, 				rowIndex, cellIndex, itemPoint.getStartTime());		// 시작시간
			setText(sheet, 				rowIndex, cellIndex, itemPoint.getEndDate());		// 종료일
			setText(sheet, 				rowIndex, cellIndex, itemPoint.getEndTime());		// 종료시간
			//setText(sheet, 				rowIndex, cellIndex, itemPoint.getRepeatDay());		// 특정일

			
			oldItemUserCode = itemPoint.getItemUserCode();
			
			rowIndex++;
			
		}
		*/
	}

	/**
	 * 2-11. 상품 CHECK SHEET
	 * @param workbook
	 * @param list
	 */
	private void buildItemCheckSheet(SXSSFWorkbook workbook, List<Item> list) {
		if (list == null || !Arrays.asList(itemParam.getExcelDownloadData()).contains("item_check")) {
			return;
		}
		
		// 3. 시트 생성
		String title = MessageUtils.getMessage("M01287");	// 상품 선택 정보
		String sheetTitle = "item_check";	// 상품 선택 정보
		
		
		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			 new HeaderCell(1024, 	"상품No.(*)"),				// 상품번호
			 new HeaderCell(8192, 	"상품명(*)"),					// 상품명
			 new HeaderCell(1024, 	"소속팀(*)", 		"1:에스테틱\n2:네일\n3:속눈썹\n4:헤어\n5:판매/아울렛\n6:무소속", 4, 8),		// 소속팀
			 new HeaderCell(1024, 	"공개/비공개(*)", 	"Y:공개, N:비공개"),	// 공개/비공개
			 new HeaderCell(1024, 	"INDEX(*)", 	"Y:index시킴, N:index시키지 않음"),	// Y:인덱스 시킴, N:인텍스 시키지 않음.
			 new HeaderCell(1024, 	"상품라벨(*)", 	"0:없음, 2:NEW, 3:SALE"),		// 상품라벨
			 new HeaderCell(512, 	"상품구분(*)", 	"1:자사상품, 2:업체배송"),	// 상품구분 (1:통상상품, 2:메이커직송, 3:메일편상품)
			 new HeaderCell(512, 	"상품재고", 		"입력값이 없는 경우 제한이 없습니다."),			// 상품재고 - 입력값이 없는 경우 제한이 없습니다.
			 new HeaderCell(512, 	"판매상태(*)", 	"1:품절, 2:판매종료"),	// 판매상태 - 상품재고가 0인 상품에 적용 1:입하대기, 2: 판매종료 
			 new HeaderCell(1024, 	"재입고 예정일(*)", 	"1:없음, 2:입고예정, 3:텍스트"),		// 재입고 예정일 
			 new HeaderCell(512, 	"입고예정일", 		"날짜를입력"),					// 입하예정일 
			 new HeaderCell(512, 	"텍스트", 		"「재고0개/판매종료」를 입력")					// 텍스트 
		};
		
		
	
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		
		// Table Body
		int rowIndex = 2;
		for (Item item : list) {
			
			/**
			 *  ROW병합이 있는 경우 설정
			 * list2 = user.getSubList();
				int entrySize = list2.size();
				int startRowIndex = rowIndex;
				int endRowIndex = rowIndex + entrySize;
			 */
			
				
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			// CELL 데이터.
			
			
			// Team Index ==> TEAMS 참고 
			String teamIndex = Integer.toString(Arrays.asList(TEAMS).indexOf(item.getTeam()));
			
			//int cellIndex = -1;
			CellIndex cellIndex = new CellIndex(-1);
			setText(sheet, 			rowIndex, cellIndex, item.getItemUserCode());		// 상품코드
			setTextLeft(sheet, 		rowIndex, cellIndex, item.getItemName());			// 상품명	
			setText(sheet, 			rowIndex, cellIndex, teamIndex);					// 소속팀 (1 ~ 6)
			setText(sheet, 			rowIndex, cellIndex, item.getDisplayFlag());		// 공개여부 
			setText(sheet, 			rowIndex, cellIndex, item.getSeo().getIndexFlag().equals("Y") ? "N" : "Y");	// SEO > Noindex 태그 표시 (Y:표시-index시키지 않음, N:표시안함-index시킴)
			setText(sheet, 			rowIndex, cellIndex, item.getItemLabel());			// 상품라벨 (0:없음, 2:NEW, 3:SALE, 4:사기?) 
			setText(sheet, 			rowIndex, cellIndex, item.getItemType());			// 상품구분 (1:통상상품, 2:메이커직송, 3:메일편상품)
			setTextRight(sheet, 	rowIndex, cellIndex, item.getStockQuantity() == -1 ? "" : Integer.toString(item.getStockQuantity()));		// 상품재고 
			setText(sheet, 			rowIndex, cellIndex, item.getSoldOut());	// 상품재고 0인 경우 상품 상태 (1:입하대기, 2:판매종료)
			
			String stockScheduleType = "1";
			if (item.getStockScheduleType().equals("date")) {
				stockScheduleType = "2";
			} else if (item.getStockScheduleType().equals("text")) {
				stockScheduleType = "3";
			}
			setText(sheet, 			rowIndex, cellIndex, stockScheduleType);			// 상품재고 0인 경우 재입고 예정일 표시방법 (date:입하예정일, text:입하텍스트 '':없음)
			setText(sheet, 			rowIndex, cellIndex, item.getStockScheduleDate());	// 재입고 예정일
			setText(sheet, 			rowIndex, cellIndex, item.getStockScheduleText());	// 재입고 텍스트
			
			rowIndex++;
		}
	}
	
	/**
	 * 2-12. 상품 검색어 SHEET
	 * @param workbook
	 * @param list
	 */
	private void buildItemKeywordSheet(SXSSFWorkbook workbook, List<Item> list) {
		
		setFileName("ITEM_" + DateUtils.getToday(Const.DATE_FORMAT) + ".xlsx");
		
		
		if (list == null || !Arrays.asList(itemParam.getExcelDownloadData()).contains("item_keyword")) {
			return;
		}
		
		// 3. 시트 생성
		String title = "사이트 내 검색 키워드";	// 상품 선택 정보
		String sheetTitle = "item_keyword";	// 상품 선택 정보

		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[] {
			 new HeaderCell(1024, 	"상품번호No.(*)"),				// 상품번호
			 new HeaderCell(8192, 	"사이트 내 검색 키워드(*)"),	// 사이트내 검색어 
		};
		
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);
		
		createSheetHeader(sheet, row, headerCells, title);
		
		int columnWidth = 200; // max 255
		
		sheet.setColumnWidth(1, columnWidth * 256); // max
		// Table Body
		int rowIndex = 2;
		for (Item item : list) {
			
			/**
			 *  ROW병합이 있는 경우 설정
			 * list2 = user.getSubList();
				int entrySize = list2.size();
				int startRowIndex = rowIndex;
				int endRowIndex = rowIndex + entrySize;
			 */
			
				
			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);
			
			// CELL 데이터.
			
			//int cellIndex = -1;
			CellIndex cellIndex = new CellIndex(-1);
			setText(sheet, 			rowIndex, cellIndex, item.getItemUserCode());		// 상품코드
			setTextLeft(sheet, 		rowIndex, cellIndex, item.getItemKeyword());			// 사이트내 검색어 
			
			
			rowIndex++;
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
			
			if (headerCells[i].getTitle().indexOf("(*)") > -1) {
				cells[i].setCellStyle(headerStyle);
			} else if (headerCells[i].getTitle().indexOf("(x)") > -1) {
				cells[i].setCellStyle(getHeaderCellStyle(IndexedColors.GREY_25_PERCENT.index));
			} else {
				//cells[i].setCellStyle(getHeaderCellStyle(IndexedColors.LIGHT_GREEN.index));
				cells[i].setCellStyle(getHeaderCellStyle(IndexedColors.LEMON_CHIFFON.index));
			}
			
			
			// 셀 코멘트
			if (headerCells[i].getComment() != null && !headerCells[i].getComment().equals("")) {
				setComment(cells[i], 1, i, headerCells[i].getComment().replace(", ", ",").replace(",", "\n"), headerCells[i].getCommentCol(), headerCells[i].getCommentRow());
			}
		}
	}
	
	private String getBrandList() {
		
		String brandNames = "";
		for (int i = 0; i < brandList.size(); i++) {
			brandNames += brandList.get(i).getBrandId() + ": " + brandList.get(i).getBrandName() + "\n";
		}
		return brandNames;
	}
	
	private String getDeliveryCompanyList() {
		
		String deliveryCompany = "";
		for (int i = 0; i < deliveryCompanyList.size(); i++) {
			deliveryCompany += deliveryCompanyList.get(i).getDeliveryCompanyId() + ": " + deliveryCompanyList.get(i).getDeliveryCompanyName() + "\n";
		}
		return deliveryCompany;
	}
	
	private String getSellerList() {
		
		String seller = "";
		for (int i = 0; i < sellerList.size(); i++) {
			seller += sellerList.get(i).getSellerId() + ": " + sellerList.get(i).getSellerName() + "\n";
		}
		return seller;
	}
	
	private String getShipmentList() {
		
		String shipment = "";
		for (int i = 0; i < shipmentList.size(); i++) {
			shipment += shipmentList.get(i).getShipmentId() + ": " + shipmentList.get(i).getFullAddress() + " / " 
						+ shipmentList.get(i).getShipping() + "원 (" + shipmentList.get(i).getShippingFreeAmount() + " 이상 무료)" + "\n";
		}
		return shipment;
	}
	
	private String getShipmentReturnList() {
		
		String shipmentReturn = "";
		for (int i = 0; i < shipmentReturnList.size(); i++) {
			shipmentReturn += shipmentReturnList.get(i).getShipmentReturnId() + ": " + shipmentReturnList.get(i).getFullAddress() + "\n";
		}
		return shipmentReturn;
	}
	
	private String getItemNoticeCodes() {
		
		String itemNoticeCode = "";
		for (int i = 0; i < itemNoticeCodes.size(); i++) {
			itemNoticeCode += itemNoticeCodes.get(i).getItemNoticeCode() + ": " + itemNoticeCodes.get(i).getItemNoticeTitle() + "\n";
		}
		return itemNoticeCode;
	}

	/**
	 * 배송비 안내 코멘트
	 * @return
	 */
	private String getDeliveryChargeCode() {
		String deliveryChargeComment = "배송비코드";	// 배송비 코드
		int deliveryChargeCount = 0;
		
		
		// [SKC임시]
		/*
		List<Delivery> deliveryList = this.deliveryList;
		
		for (int i = 0; i < deliveryList.size(); i++) {
			Delivery delivery = deliveryList.get(i);
			String deliveryTypeText = delivery.getDeliveryType().equals("2") ? "개별" : "소품";	// 개별 : 소품
			
			List<DeliveryCharge> deliveryChargeList = delivery.getDeliveryChargeList();
			
			for (int j = 0; j < deliveryChargeList.size(); j++) {
				DeliveryCharge deliveryCharge = deliveryChargeList.get(j);
				
				
				if (deliveryCharge.getDeliveryChargeType().equals("1")) {
					deliveryChargeComment += ", " + deliveryCharge.getDeliveryChargeId() + " : [" + deliveryTypeText + "] " + delivery.getTitle() + "(무료)";		// 무료
				
				} else if (deliveryCharge.getDeliveryChargeType().equals("2")) {
					deliveryChargeComment += ", " + deliveryCharge.getDeliveryChargeId() + " : [" + deliveryTypeText + "] " + delivery.getTitle() + "(" + deliveryCharge.getDeliveryCharge() + "원)";
				
				} else if (deliveryCharge.getDeliveryChargeType().equals("3")) {
					deliveryChargeComment += ", " + deliveryCharge.getDeliveryChargeId() + " : [" + deliveryTypeText + "] " + delivery.getTitle() + "(" + deliveryCharge.getDeliveryCharge() + "원 | " + deliveryCharge.getDeliveryFreeAmount() + "원 이상 무료)";
				}
				
				deliveryChargeCount++;
			}
		}
		this.deliveryChargeCount = deliveryChargeCount;*/
		return deliveryChargeComment;
	}

}
