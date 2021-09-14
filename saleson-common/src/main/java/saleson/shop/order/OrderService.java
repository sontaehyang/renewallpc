package saleson.shop.order;

import org.springframework.web.multipart.MultipartFile;
import saleson.common.enumeration.OrderCodePrefix;
import saleson.common.enumeration.OrderLogType;
import saleson.common.opmanager.count.OpmanagerCount;
import saleson.model.OrderGiftItem;
import saleson.shop.cart.support.CartParam;
import saleson.shop.config.domain.Config;
import saleson.shop.order.api.ApiOrderList;
import saleson.shop.order.api.OrderDetail;
import saleson.shop.order.claimapply.domain.*;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.domain.*;
import saleson.shop.order.pg.cj.domain.CjResult;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.shipping.support.ShippingParam;
import saleson.shop.order.shipping.support.ShippingReadyParam;
import saleson.shop.order.support.EditPayment;
import saleson.shop.order.support.OrderParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderService {
	
	/**
	 * 재고 복원
	 * @param map
	 */
	public void stockRestoration(HashMap<String, Integer> map);
	
	/**
	 * 복원해야할 재고 목록 만들기
	 * @param map
	 * @param orderItem
	 */
	public void makeStockRestorationMap(HashMap<String, Integer> map, OrderItem orderItem);
	
	/**
	 * 복원해야할 재고 목록 만들기
	 * @param map
	 * @param orderCancelApply
	 */
	public void makeStockRestorationMap(HashMap<String, Integer> map, OrderCancelApply orderCancelApply);
	
	public List<OrderList> getOrderListByParamForManager(OrderParam orderParam);
	
	/**
	 * 배송지 정보 조회
	 * @param orderParam
	 * @return
	 */
	public OrderShippingInfo getOrderShippingInfoByParam(OrderParam orderParam);
	
	/**
	 * 주문상품 단일 Row 조회
	 * @param orderParam
	 * @return
	 */
	public OrderItem getOrderItemByParam(OrderParam orderParam);
	
	/**
	 * 도서산간, 제주 지역 검색
	 * @param zipcode
	 * @return
	 */
	public String getIslandTypeByZipcode(String zipcode);
	
	/**
	 * 입금대기 결제 목록
	 * @param orderParam
	 * @return
	 */
	public List<OrderPayment> getWaitingDepositListByParam(OrderParam orderParam);
	
	/**
	 * 입금대기 목록 리스트 상태 변경
	 * @param mode
	 * @param orderParam
	 */
	public void waitingDepositListUpdate(String mode, OrderParam orderParam);
	
	/**
	 * 입금확인 취소
	 * @param orderParam
	 */
	public void paymentVerificationCancel(OrderParam orderParam);
	
	/**
	 * 입금대기 상세
	 * @param orderParam
	 * @return
	 */
	public List<WaitingDepositDetail> getWaitingDepositDetailByParam(OrderParam orderParam);
	
	/**
	 * 신규주문 리스트
	 * @param orderParam
	 * @return
	 */
	public List<OrderList> getNewOrderListByParam(OrderParam orderParam);
	
	/**
	 * 신규주문 목록 리스트 상태 변경
	 * @param mode
	 * @param shippingReadyParam
	 * @param orderItem
	 */
	public void newOrderListUpdate(String mode, ShippingReadyParam shippingReadyParam, OrderItem orderItem, OrderParam orderParam);
	
	/**
	 * 배송준비중 리스트
	 * @param orderParam
	 * @return
	 */
	public List<OrderList> getShippingReadyListByParam(OrderParam orderParam);
	
	/**
	 * 배송준비중 목록 리스트 상태 변경
	 * @param mode
	 * @param shippingParam
	 * @param orderItem
	 */
	public void shippingReadyListUpdate(String mode, ShippingParam shippingParam, OrderItem orderItem, OrderParam orderParam);
	
	/**
	 * 배송준비중 목록에서 엑셀 업로드로 배송정보 update
	 * @param orderParam
	 * @param multipartFile
	 */
	public Map<String, Object> shippingReadyExcelUpload(OrderParam orderParam, MultipartFile multipartFile);

	/**
	 * 배송정보 엑셀 업로드 후 메시지 발송
	 */
	void sendOrderDeliveryMessageByExcelUpload(List<String> keyList);

	/**
	 * 배송중 리스트
	 * @param orderParam
	 * @return
	 */
	public List<OrderList> getShippingListByParam(OrderParam orderParam);
		
	/**
	 * 배송중 목록 리스트 상태 변경
	 * @param mode
	 * @param orderParam
	 */
	public void shippingListUpdate(String mode, OrderParam orderParam, ShippingParam shippingParam);
	
	/**
	 * 구매확정 리스트
	 * @param orderParam
	 * @return
	 */
	public List<OrderList> getConfirmListByParam(OrderParam orderParam);

	/**
	 * 주문 카운트
	 * @param orderParam
	 * @return
	 */
	public int getOrderCountByParam(OrderParam orderParam);
	
	/**
	 * 주문 조회
	 * @param orderParam
	 * @return
	 */
	public Order getOrderByParam(OrderParam orderParam);
	
	/**
	 * 주문 내역
	 * @param orderParam
	 * @return
	 */
	public List<Order> getOrderListByParam(OrderParam orderParam);
	
	/**
	 * 쿠폰정보 조회
	 * @return
	 */
	public Buy getOrderCouponData(Buy buy);
	
	/**
	 * 회원 사용가능 포인트
	 * @param userId
	 * @param addPoint
	 * @return
	 */
	public int getRetentionPoint(long userId, int addPoint);
	
	/**
	 * 회원 사용가능 배송비 할인 쿠폰
	 * @param userId
	 * @param addPoint
	 * @return
	 */
	public int getShippingCoupon(long userId, int addPoint);
	
	/**
	 * 결제 수단 목록
	 * @param accountNumberList
	 * @param shopConfig
	 * @return
	 */
	public HashMap<String, BuyPayment> getPaymentType();
	
	/**
	 * Step1 주문 정보를 구성
	 * @param orderParam
	 * @return
	 */
	public Buy getBuyForStep1(OrderParam orderParam);
	
	/**
	 * 배송 정보를 조회
	 * @param buy
	 * @return
	 */
	public Buy getOrderTemp(OrderParam orderParam);
	
	/**
	 * 주문 상품 임시 데이터 조회
	 * @param orderParam
	 * @return
	 */
	public List<BuyItem> getOrderItemTempList(OrderParam orderParam);
	
	/**
	 * 주문 전체 카운트 조회 - 관리자
	 * @return
	 */
	public List<OrderCount> getOpmanagerOrderCountAll();

	/**
	 * 주문 전체 카운트 조회 - 판매자
	 * @return
	 */
	public List<OrderCount> getSellerOrderCountAll();
	
	/**
	 * 주문 전체 카운트 조회 - 사용자
	 * @return
	 */
	public List<OrderCount> getUserOrderCountAll();

	/**
	 * 주문 전체 카운트 조회 - 관리자
	 * @param month
	 * @return
	 */
	public List<OrderCount> getOpmanagerOrderCountAllByMonth(int month);

	/**
	 * 주문 전체 카운트 조회 - 판매자
	 * @return
	 */
	public List<OrderCount> getSellerOrderCountAllByMonth(int month);

	/**
	 * 주문 전체 카운트 조회 - 사용자
	 * @return
	 */
	public List<OrderCount> getUserOrderCountAllByMonth(int month);


	/**
	 * 주문 임시 저장
	 * @param buy
	 * @return
	 */
	public HashMap<String, Object> saveOrderTemp(HttpSession session, Buy buy);

	/**
	 * 렌탈 주문 임시 저장
	 * @param buy
	 * @return
	 */
	public HashMap<String, Object> saveRentalOrderTemp(HttpSession session, Buy buy);
	
	/**
	 * 주문 등록
	 * @param orderParam
	 * @param pgData
	 * @param session
	 * @param request
	 * @return
	 */
	public String insertOrder(OrderParam orderParam, Object pgData, HttpSession session, HttpServletRequest request);

	/**
	 * 렌탈 주문 등록
	 * @param orderParam
	 * @param pgData
	 * @param session
	 * @param request
	 * @return
	 */
	public String insertOrderRental(OrderParam orderParam, Object pgData, HttpSession session, HttpServletRequest request);
	
	/**
	 * 주문상품 임시 저장
	 * @param buyItem
	 */
	public void insertOrderItemTemp(BuyItem buyItem);

	/**
	 * 주문상품 렌탈정보 임시 저장
	 * @param buyItem
	 */
	public void insertRentalOrderItemTemp(CartParam cartParam);
	
	/**
	 * 주문상품 임시 저장 정보 삭제
	 * @param userId
	 * @param sessionId
	 */
	public void deleteOrderItemTemp(long userId, String sessionId);

	/**
	 * 주문상품 렌탈정보 임시 저장 정보 삭제
	 * @param userId
	 * @param sessionId
	 */
	public void deleteRentalOrderItemTem(long userId, String sessionId);

	
	/**
	 * 주문 임시 데이터 삭제
	 * @param userId
	 * @param sessionId
	 */
	public void deleteOrderTemp(long userId, String sessionId);
	
	/**
	 * PG사 입금확인
	 * @param pgData
	 */
	public String pgConfirmationOfPayment(PgData pgData);
	
	/**
	 * CJ 입금확인
	 * @param cjResult
	 * @return
	 */
	public String cjPgConfirmationOfPayment(CjResult cjResult);
	
	/**
	 * 페이코 입금확인
	 * @param jsonString
	 * @return
	 */
	public String paycoConfirmationOfPayment(String jsonString);
	
	/**
	 * 구매 확정
	 * @param orderParam
	 */
	public void updateConfirmPurchase(OrderParam orderParam);


	/**
	 * 재고 차감
	 * @param stockMap
	 */
	public void updateStockDeduction(HashMap<String, Integer> stockMap);
	
	/**
	 * 주문정보 수정
	 * @param order
	 */
	public void saveOrderInfo(Order order);
	
	/**
	 *
	 * 송장번호 수정
	 * @param orderItem
	 */
	public void changeShippingNumber(ShippingParam shippingParam);

	/**
	 * 관리자 메모 수정
	 * @param order
	 */
	public void updateAdminMemo(Order order);
	
	/**
	 * 주문관련 메시지 발송
	 * @param buy
	 */
	public void sendOrderMessageTx(Buy buy);
	
	/**
	 * 주문관련 메시지 발송
	 * @param order
	 * @param templateId
	 * @param config
	 */
	public void sendOrderMessageTx(Order order, String templateId, Config config);
	
	/** 구매확정 확인 */
	public int getOrderItemCntForReview(OrderItem orderItem);
	
	/**
	 * 이니시스 가상계좌 입금 확인
	 * @param pgData
	 * @return
	 */
	public String getOrderPaymentByPgDataForInipayVacct(PgData pgData, String deviceType);

	/**
	 * 결제 정보 변경
	 * @param editPayment 
	 * @return
	 */
	public void changePayment(EditPayment editPayment);
	
	/**
	 * 관리자 클레임 접수
	 * @param adminClaimApply
	 */
	public void adminClaimApply(AdminClaimApply adminClaimApply);
	
	/**
	 * 주문상태별 개수
	 * @param userId
	 * @return
	 */
	OrderCount getOrderCountsByUserId(long userId);
	
	/**
	 * 이상우 [2017-05-11 추가]
	 * 배송출고 지연, 교환회수 지연, 반품회수 지연 카운트
	 * @return
	 */
	public List<OpmanagerCount> getOpmanagerShippingDelayCountAll(HashMap<String, Object> map);

	/**
	 * PG거래번호 가져오기
	 * @param orderCode
	 * @return
	 */
	String getTidByParam(String orderCode);

	/**
	 * 상품정보 리스트 가져오기
	 * @param orderParam
	 * @return
	 */
	public List<OrderItem> getOrderItemListByParam(OrderParam orderParam);

	/**
	 * 주문 로그 등록
	 * @param logType 로그 타입
	 * @param orderCode 주문번호
	 * @param orderSequence 주문순번
	 * @param itemSequence 상품순번
	 */
	void insertOrderLog(OrderLogType logType, String orderCode, int orderSequence, int itemSequence, String orgOrderStatus);

	/**
	 * 주문 로그용 주문상품 조회
	 * @param orderCode
	 * @param orderSequence
	 * @param itemSequence
	 * @return
	 */
	OrderItem getOrderItemForOrderLog(String orderCode, int orderSequence, int itemSequence);

	Iterable<OrderLog> getOrderLogListByOrderCode(OrderLog orderLog);

	/**
	 * 주문 로그용 주문상품 목록 조회
	 * @param orderCode
	 * @param orderSequence
	 * @return
	 */
	List<OrderItem> getOrderItemListForOrderLog(String orderCode, int orderSequence);

	/**
	 * 관리자 주문 전체 리스트 조회
	 * @param orderParam
	 * @return
	 */
	public List<OrderList> getAllOrderListByParamForManager(OrderParam orderParam);

	/**
	 * 주문번호 채번
	 * @param orderCodePrefix
	 * @return
	 */
	String getNewOrderCode(OrderCodePrefix orderCodePrefix);

    /**
     * 주문번호로 구매자 Email 조회
     * @param orderParam
     * @return
     */
    public String getEmailByOrderCode(OrderParam orderParam);

	/**
	 * 사은품을 해당 주문상품에 추가
	 * @param orderGiftItems
	 * @param orderItem
	 */
	void setOrderGiftItemForOrderItem(List<OrderGiftItem> orderGiftItems, OrderItem orderItem);

	/**
	 * 사은품을 해당 주문상품에 추가
	 * @param orderItem
	 */
	void setOrderGiftItemForOrderItem(OrderItem orderItem);

	/**
	 * 주문번호로 OP_ORDER_PG_DATA 조회
	 * @param orderCode
	 * @return
	 */
	OrderPgData getOrderPgDataByOrderCode(String orderCode);

	List<ApiOrderList> getApiOrderList(OrderParam orderParam);
	OrderDetail getApiOrderDetail(OrderParam orderParam);
	OrderDetail getApiBuyForStep1(OrderParam orderParam);
	ReturnApplyInfo getReturnApplyInfo(OrderParam orderParam);
	ExchangeApplyInfo getExchangeApplyInfo(OrderParam orderParam);
	CancelApplyInfo getCancelApplyInfo(OrderParam orderParam);

	/**
	 * 주문번호로 구매자 Mobile 조회
	 * @param orderParam
	 * @return
	 */
	String getMobileByOrderCode(OrderParam orderParam);

	/**
	 * 본품의 MAX ITEM_SEQUENCE 조회
	 * @param orderParam
	 * @return
	 */
	int getMaxParentOrderItemSequence(OrderParam orderParam);

	/**
	 * ERP 주문 상태 연동 배치
	 */
	void erpOrderStatusBatch();
}
