package saleson.shop.order.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import saleson.shop.order.admin.domain.OrderAdminDetail;
import saleson.shop.order.admin.support.OrderAdminParam;

public interface OrderAdminService {

	/**
	 * 리스트
	 * @param orderAdminParam
	 * @return
	 */
	public List<OrderAdminDetail> getOrderAdminListByParam(OrderAdminParam orderAdminParam);
	
	/**
	 * 엑셀 업로드 - 확장자 (xlsx만 가능)
	 * @param multipartFile
	 * @return
	 */
	public String insertExcelData(String templateVersion, MultipartFile multipartFile);
	
	/**
	 * 관리자 주문 등록
	 * @param orderAdminParam
	 * @param request
	 */
	public void insertOrderAdmin(OrderAdminParam orderAdminParam, HttpServletRequest request);
	
}
