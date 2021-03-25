package saleson.shop.statistics.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.statistics.domain.RevenueBaseForDate;
import saleson.shop.statistics.domain.RevenueDetail;
import saleson.shop.statistics.domain.RevenueDetailItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class RevenueDetailExcelView extends AbstractSXSSFExcelView {

	private String mode;
	private String title;
	private String sellerIdForParam;
	private int rowIndex = 2;

	public RevenueDetailExcelView(String mode) {

		this.mode = mode;
		this.title = "주문 상세";
		if ("day".equals(mode)) {
			this.title = "일자별 상세";
		} else if ("dayConfirm".equals(mode)) {
			this.title = "일자별(구매확정일 기준) 상세";
		} else if ("month".equals(mode)) {
			this.title = "월별 상세";
		} else if ("year".equals(mode)) {
			this.title = "년도별 상세";
		} else if ("user".equals(mode)) {
			this.title = "회원별 상세";
		}

		setFileName(this.title + " 내역_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}

	@Override
	public void buildExcelDocument(Map<String, Object> map, SXSSFWorkbook sxssfWorkbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		sellerIdForParam = (String) map.get("sellerIdForParam");
		buildItemSheet(sxssfWorkbook, (List<RevenueBaseForDate>)map.get("list"));
	}


	private void buildItemSheet(SXSSFWorkbook workbook, List<RevenueBaseForDate> list) {

		if(list == null){
			return;
		}

		// 3. 시트생성
		String sheetTitle = "list";
		String title = this.title;
		HeaderCell[] headerCells = null;
		// 4. Cell (컬럼) 설정
		if (sellerIdForParam == null) {
			headerCells = new HeaderCell[]{
					new HeaderCell(1000, 	"일자"),
					new HeaderCell(1000, 	"구분"),
					new HeaderCell(1000, 	"주문번호"),
					new HeaderCell(1000, 	"주문자명"),
					new HeaderCell(10000, 	"주소"),
					new HeaderCell(1000, 	"수령인"),
					new HeaderCell(10000, 	"수령인주소"),
					new HeaderCell(1000, 	"주문방법"),
					new HeaderCell(5000, 	"브랜드"),
					new HeaderCell(5000, 	"상품명"),
					new HeaderCell(5000, 	"주문옵션"),
					new HeaderCell(1000, 	"상품번호"),
					new HeaderCell(1000, 	"수량"),
					new HeaderCell(1000, 	"상품판매가"),
					new HeaderCell(1000, 	"쿠폰할인"),
					new HeaderCell(1000, 	"추가 할인금액"),
					new HeaderCell(1000, 	"판매가"),
					new HeaderCell(1000, 	"배송비"),
					new HeaderCell(1000, 	"소계")
			};
		} else {
			headerCells = new HeaderCell[]{
					new HeaderCell(1000, 	"일자"),
					new HeaderCell(1000, 	"구분"),
					new HeaderCell(1000, 	"주문번호"),
					new HeaderCell(1000, 	"주문자명"),
					new HeaderCell(10000, 	"주소"),
					new HeaderCell(1000, 	"수령인"),
					new HeaderCell(10000, 	"수령인주소"),
					new HeaderCell(1000, 	"주문방법"),
					new HeaderCell(5000, 	"브랜드"),
					new HeaderCell(5000, 	"상품명"),
					new HeaderCell(5000, 	"주문옵션"),
					new HeaderCell(1000, 	"상품번호"),
					new HeaderCell(1000, 	"수량"),
					new HeaderCell(1000, 	"상품판매가"),
					new HeaderCell(1000, 	"쿠폰할인"),
					new HeaderCell(1000, 	"추가 할인금액"),
					new HeaderCell(1000, 	"판매가"),
					new HeaderCell(1000, 	"소계")
			};
		}
		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);

		createSheetHeader(sheet, row, headerCells, title);


		for(RevenueBaseForDate base : list) {

			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);

			CellIndex cellIndex = new CellIndex(-1);

			int startRowIndex = rowIndex;

			setText(sheet, 			rowIndex, cellIndex, base.getKey());

			this.makeRowDataByOrderType("PAY", sheet, base, cellIndex.getIndex());
			this.makeRowDataByOrderType("CANCEL", sheet, base, cellIndex.getIndex());

			// 빈칸인 데이터에 공백값 넣어주기(테두리 나오지 않는 현상 수정)
			for (int i=1; i<rowIndex-startRowIndex; i++) {
				cellIndex = new CellIndex(cellIndex.getIndex()-1);
				setText(sheet, 			startRowIndex+i, cellIndex, "");
			}

			// 일자 병합
			if (rowIndex - 1 > startRowIndex) {
				sheet.addMergedRegion(new CellRangeAddress(startRowIndex, rowIndex - 1, 0, 0));
			}
		}
	}

	private void makeRowDataByOrderType(String orderType, Sheet sheet, RevenueBaseForDate base, int cellStartIndex) {
		int startRowIndex = rowIndex;
		for(RevenueDetail detail : base.getList()) {
			if (orderType.equals(detail.getOrderType())) {
				CellIndex cellIndex = new CellIndex(cellStartIndex);

				String groupTitle = "PAY".equals(orderType) ? "결제" : "취소";
				String osType = "Admin".equals(detail.getOsType()) ? "Call" : detail.getOsType();

				setText(sheet, 			rowIndex, cellIndex, groupTitle);

				// 빈칸인 데이터에 공백값 넣어주기(테두리 나오지 않는 현상 수정)
				for (int i=1; i<detail.getItems().size(); i++) {
					cellIndex = new CellIndex(cellIndex.getIndex()-1);
					setText(sheet, 			rowIndex+i, cellIndex, "");
				}

				int orderStartIndex = cellIndex.getIndex();
				setText(sheet, 			rowIndex, cellIndex, detail.getOrderCode());
				setText(sheet, 			rowIndex, cellIndex, detail.getUserName());
				/*setText(sheet, 			rowIndex, cellIndex, detail.getCompanyName());*/
				String address = "[" + detail.getZipcode() + "] " + detail.getAddress() + " " + detail.getAddressDetail();
				setTextLeft(sheet,		rowIndex, cellIndex, address);

				String receiveAddress = "[" + detail.getReceiveZipcode() + "] " + detail.getReceiveAddress() + " " + detail.getReceiveAddressDetail();
				setText(sheet,			rowIndex, cellIndex, detail.getReceiveName());
				setTextLeft(sheet,		rowIndex, cellIndex, receiveAddress);

				setText(sheet, 			rowIndex, cellIndex, osType);

				int itemStartIndex = cellIndex.getIndex();
				int itemStartRowIndex = rowIndex;
				int itemEndIndex = 0;
				for(RevenueDetailItem item : detail.getItems()) {

					if (rowIndex > startRowIndex) {
						setText(sheet, rowIndex, new CellIndex(-1), "");
					}

					CellIndex itemCellIndex = new CellIndex(itemStartIndex);

					setText(sheet,		rowIndex, itemCellIndex, item.getSellerName());
					setTextLeft(sheet,		rowIndex, itemCellIndex, item.getItemName());

					String optionString = "";
					if (StringUtils.isNotEmpty(item.getOpenMarektOption())) {

						optionString = ShopUtils.viewOptionTextNoUl(item.getOpenMarektOption());

					} else {
						if (item.getRequiredOptionsList() != null) {
							int i = 0;
							for(ItemOption itemOption : item.getRequiredOptionsList()) {
								if (i++ > 0) {
									optionString += " | ";
								}

								optionString += itemOption.getOptionName1() + " : " + itemOption.getOptionName2();
							}
						}
					}

					setTextLeft(sheet,				rowIndex, itemCellIndex, optionString);
					setText(sheet,					rowIndex, itemCellIndex, item.getItemUserCode());
					setNumberFormat(sheet,			rowIndex, itemCellIndex, item.getQuantity());
					setNumberFormat(sheet,			rowIndex, itemCellIndex, item.getItemPrice() + detail.getSumDeliveryPrice());
					setNumberFormat(sheet,			rowIndex, itemCellIndex, item.getItemCouponDiscountAmount());
					setNumberFormat(sheet,			rowIndex, itemCellIndex, item.getVendorAddDiscountAmount());
					setNumberFormat(sheet,			rowIndex, itemCellIndex, item.getTotalItemPrice());

					if (sellerIdForParam == null) {
						setNumberFormat(sheet,		rowIndex, itemCellIndex, item.getOrderShipping()); //배송비
					}

					setNumberFormat(sheet,			rowIndex, itemCellIndex, item.getSubTotal()); //소계

					itemEndIndex = itemCellIndex.getIndex();

					rowIndex++;
				}

				CellIndex itemEndCellIndex = new CellIndex(itemEndIndex);

				if (rowIndex - 1 > itemStartRowIndex) {
					for (int i = orderStartIndex + 1; i < itemStartIndex + 1; i++) {

						for(int r = itemStartRowIndex + 1; r < rowIndex; r++) {
							setText(sheet, 			r, new CellIndex(i - 1), "");
						}

						sheet.addMergedRegion(new CellRangeAddress(itemStartRowIndex, rowIndex - 1, i, i));
					}

					for (int i = itemEndIndex + 1; i < itemEndCellIndex.getIndex() + 1; i++) {

						for(int r = itemStartRowIndex + 1; r < rowIndex; r++) {
							setText(sheet, 			r, new CellIndex(i - 1), "");
						}

						sheet.addMergedRegion(new CellRangeAddress(itemStartRowIndex, rowIndex - 1, i, i));
					}

					// [2019-03-05 KSH] 배송비, 소계 칸 합치기
					for (int i = ((sellerIdForParam == null) ? (itemEndIndex - 1) : itemEndIndex); i < itemEndCellIndex.getIndex() + 1; i++) {

						for(int r = itemStartRowIndex + 1; r < rowIndex; r++) {
							setNumberFormat(sheet, 			r, new CellIndex(i - 1), 0);
						}

						sheet.addMergedRegion(new org.apache.poi.hssf.util.CellRangeAddress(itemStartRowIndex, rowIndex - 1, i, i));
					}
				}


			}
		}

		if (rowIndex - 1 > startRowIndex) {
			sheet.addMergedRegion(new CellRangeAddress(startRowIndex, rowIndex-1, 1, 1));
		}
	}

}
