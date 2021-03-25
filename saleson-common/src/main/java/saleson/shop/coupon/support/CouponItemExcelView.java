package saleson.shop.coupon.support;

import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CouponItemExcelView extends AbstractSXSSFExcelView {

    public CouponItemExcelView() {
        setFileName("COUPON_ITEM_SAMPLE.xlsx");
    }

    @Override
	public void buildExcelDocument(Map<String, Object> model,
                                      SXSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        Cookie cookie = new Cookie("DOWNLOAD_STATUS", "complete");
		cookie.setHttpOnly(true);
        cookie.setPath("/");					// 모든 경로에서 접근 가능하도록
        response.addCookie(cookie);				// 쿠키저장


        // 1. 데이터 가져오기

        // 2. 시트별 데이터 생성
        buildCouponItemSheet(workbook);
    }

    private void buildCouponItemSheet(SXSSFWorkbook workbook) {

        // 3. 시트 생성
        String title = "쿠폰 상품 (SAMPLE)";
        String sheetTitle = "sample";

        // 4. Cell (컬럼) 설정
        HeaderCell[] headerCells = new HeaderCell[] {
                new HeaderCell(4000, 	"상품번호(*)", 		"사용중인 상품번호를 입력해주세요., (EX: G2000481912)"),
        };

        /**
         * 상단 타이틀 및 테이블 헤더 생성.
         */
        Sheet sheet = workbook.createSheet(sheetTitle);
        Row row = sheet.createRow((short) 0);

        createSheetHeader(sheet, row, headerCells, title);
    }
}
