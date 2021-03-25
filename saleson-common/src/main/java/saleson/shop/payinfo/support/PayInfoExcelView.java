package saleson.shop.payinfo.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

import saleson.shop.payinfo.domain.PayInfo;

public class PayInfoExcelView extends AbstractSXSSFExcelView {

    public PayInfoExcelView() {
        setFileName("결제현황_" + DateUtils.getToday("yyyyMMddHHmmss") + ".xlsx");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void buildExcelDocument(Map<String, Object> model,
                                   SXSSFWorkbook workbook, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        // 1. 데이터 가져오기.
        List<PayInfo> payInfoList = (List<PayInfo>) model.get("payInfoList");

        // 2. 시트별 데이터 생성
        buildSheet(workbook, payInfoList);
    }

    private void buildSheet(SXSSFWorkbook workbook, List<PayInfo> list) {
        if (list == null) {
            return;
        }

        String sheetTitle = "pay_info";
        String title = "회원정보";

        HeaderCell[] headerCells = new HeaderCell[]{
                new HeaderCell(300, "No."),
                new HeaderCell(800, "주문번호"),
                new HeaderCell(1000, "결제자"),
                new HeaderCell(1000, "주문일"),
                new HeaderCell(1000, "결제일"),
                new HeaderCell(1000, "결제방법"),
                new HeaderCell(1000, "결제금액")
        };

        Sheet sheet = workbook.createSheet(sheetTitle);
        Row row = sheet.createRow((short) 0);

        createSheetHeader(sheet, row, headerCells, title);

        // Table Body
        int rowIndex = 2;
        int index = 0;
        for (PayInfo payinfo : list) {

            row = sheet.createRow(rowIndex);
            row.setHeight((short) 400);

            CellIndex cellIndex = new CellIndex(-1);

            if ("bank".equals(payinfo.getApprovalType())) {
                payinfo.setApprovalType("온라인입금");
            } else if ("card".equals(payinfo.getApprovalType())) {
                payinfo.setApprovalType("신용카드");
            } else if ("vbank".equals(payinfo.getApprovalType())) {
                payinfo.setApprovalType("가상계좌");
            } else if ("point".equals(payinfo.getApprovalType())) {
                payinfo.setApprovalType("포인트");
            }

            setTextLeft(sheet, rowIndex, cellIndex, String.valueOf(++index));
            setTextLeft(sheet, rowIndex, cellIndex, payinfo.getOrderCode());
            setTextLeft(sheet, rowIndex, cellIndex, payinfo.getPayUserName());
            setTextLeft(sheet, rowIndex, cellIndex, payinfo.getOrderDate()); //주문일
            setTextLeft(sheet, rowIndex, cellIndex, payinfo.getPayDate()); //결제일
            setTextLeft(sheet, rowIndex, cellIndex, payinfo.getApprovalType()); //결제방법
            setTextRight(sheet, rowIndex, cellIndex, StringUtils.numberFormat("" + (payinfo.getAmount())));

            rowIndex++;
        }
    }
}