package saleson.shop.order.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import saleson.common.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class OrderListExcelView extends AbstractSXSSFExcelView {

	private String title = "주문 내역";
	public OrderListExcelView(String title) {
		setFileName("ORDER_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	public OrderListExcelView() {
		setFileName("ORDER_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//skc////skc////skc////skc////skc//
	}
	
	//skc////skc////skc////skc////skc////skc////skc//
	
}
