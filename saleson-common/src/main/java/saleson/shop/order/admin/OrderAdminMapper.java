package saleson.shop.order.admin;

import java.util.List;

import saleson.shop.order.admin.domain.BuyAdmin;
import saleson.shop.order.admin.domain.BuyAdminItem;
import saleson.shop.order.admin.domain.BuyAdminPayment;
import saleson.shop.order.admin.domain.BuyAdminReceiver;
import saleson.shop.order.admin.domain.BuyAdminShipping;
import saleson.shop.order.admin.domain.OrderAdmin;
import saleson.shop.order.admin.domain.OrderAdminDetail;
import saleson.shop.order.admin.support.OrderAdminData;
import saleson.shop.order.admin.support.OrderAdminParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("orderAdminMapper")
public interface OrderAdminMapper {
	
	/**
	 * 작업 대기 여부 체크
	 * @param data
	 * @return
	 */
	int getOrderAdminDetailCheckedStatus(OrderAdminData data);
	
	/**
	 * 작업 완료 처리
	 * @param data
	 */
	int updateOrderAdminDetailStatus(OrderAdminData data);
	
	/**
	 * 카운트
	 * @param orderAdminParam
	 * @return
	 */
	int getOrderAdminCountByParam(OrderAdminParam orderAdminParam);
	
	/**
	 * 리스트
	 * @param orderAdminParam
	 * @return
	 */
	List<OrderAdminDetail> getOrderAdminListByParam(OrderAdminParam orderAdminParam);
	
	/**
	 * 관리자 대량주문 업로드 마스터 등록
	 * @param orderAdmin
	 */
	void insertOrderAdmin(OrderAdmin orderAdmin);
	
	/**
	 * 관리자 대량주문 업로드 상품 정보 등록
	 * @param orderAdminDetail
	 */
	void insertOrderAdminDetail(OrderAdminDetail orderAdminDetail);
	
	/**
	 * 주문 마스터 등록
	 * @param buyAdmin
	 */
	void insertOrder(BuyAdmin buyAdmin);
	
	/**
	 * 주문 배송지 정보 등록
	 * @param receiver
	 */
	void insertOrderShippingInfo(BuyAdminReceiver receiver);
	
	/**
	 * 배송비 정보 등록
	 * @param shipping
	 */
	void insertOrderShipping(BuyAdminShipping shipping);
	
	/**
	 * 주문 상품정보 등록
	 * @param buyAdminItem
	 */
	void insertOrderItem(BuyAdminItem buyAdminItem);
	
	/**
	 * 결제 정보 등록
	 * @param buyAdminPayment
	 */
	void insertOrderPayment(BuyAdminPayment buyAdminPayment);
}
