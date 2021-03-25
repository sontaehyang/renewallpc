package saleson.shop.order.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.order.domain.OrderItem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class OrderForErpExcelView2 extends AbstractSXSSFExcelView {

	public OrderForErpExcelView2(String fileName) {
		setFileName(fileName);
	}
	
	public OrderForErpExcelView2() {
		setFileName("원가일괄등록엑셀_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			SXSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//skc////skc////skc////skc////skc//
	}
	
	//skc////skc////skc////skc////skc////skc////skc////skc////skc////skc//
}
