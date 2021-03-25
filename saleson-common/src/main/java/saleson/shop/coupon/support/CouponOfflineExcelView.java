package saleson.shop.coupon.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import saleson.common.Const;
import saleson.shop.coupon.domain.CouponOffline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CouponOfflineExcelView extends AbstractSXSSFExcelView
{	
    private List<CouponOffline> couponOfflineList;

    public CouponOfflineExcelView()
    {
        setFileName((new StringBuilder("COUPON_OFFLINE_")).append(DateUtils.getToday(Const.DATETIME_FORMAT)).append(".xlsx").toString());
    }

	public void buildExcelDocument(Map model, SXSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	couponOfflineList = (List<CouponOffline>)model.get("couponOfflineList");
        buildCouponSheet(workbook, couponOfflineList);
    }

    private void buildCouponSheet(SXSSFWorkbook workbook, List<CouponOffline> list)
    {
        if(list == null)
            return;
        String sheetTitle = "coupon_offline_code";
        String title = "쿠폰 오프라인 코드 리스트";
        HeaderCell headerCells[] = {
    		new HeaderCell(5000, "쿠폰 오프라인 코드", "쿠폰 오프라인 코드"),
            new HeaderCell(1000, "사용유무", "사용유무"),
            new HeaderCell(3000, "사용일", "사용일"),
            new HeaderCell(3000, "발행일", "발행일")
        };
        Sheet sheet = workbook.createSheet(sheetTitle);
        Row row = sheet.createRow(0);
        createSheetHeader(sheet, row, headerCells, title);
        int rowIndex = 2;
        for(Iterator<CouponOffline> iterator = list.iterator(); iterator.hasNext();)
        {
            CouponOffline couponOffline = (CouponOffline)iterator.next();
            row = sheet.createRow(rowIndex);
            row.setHeight((short)400);
            CellIndex cellIndex = new CellIndex(-1);
            setText(sheet, rowIndex, cellIndex, couponOffline.getCouponOfflineCode());
            setText(sheet, rowIndex, cellIndex, "Y".equals(couponOffline.getCouponUsedFlag())? "사용":"미사용" );
            setText(sheet, rowIndex, cellIndex, couponOffline.getCouponUsedDate());
            setText(sheet, rowIndex, cellIndex, couponOffline.getPublishedDate());
            rowIndex++;
        }

    }


}
