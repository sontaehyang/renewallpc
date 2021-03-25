package saleson.shop.order.claimapply;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.CashbillStatus;
import saleson.common.enumeration.OrderLogType;
import saleson.common.utils.*;
import saleson.erp.domain.ErpOrderType;
import saleson.erp.domain.OrderLine;
import saleson.erp.service.ErpOrder;
import saleson.erp.service.ErpService;
import saleson.model.CashbillIssue;
import saleson.model.ConfigPg;
import saleson.model.OrderGiftItem;
import saleson.seller.main.SellerMapper;
import saleson.seller.main.domain.Seller;
import saleson.shop.coupon.CouponMapper;
import saleson.shop.coupon.domain.OrderCoupon;
import saleson.shop.deliverycompany.DeliveryCompanyService;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.naverpay.NaverPaymentApi;
import saleson.shop.order.OrderMapper;
import saleson.shop.order.OrderService;
import saleson.shop.order.addpayment.OrderAddPaymentMapper;
import saleson.shop.order.addpayment.OrderAddPaymentService;
import saleson.shop.order.addpayment.domain.OrderAddPayment;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderExchangeApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.claimapply.domain.*;
import saleson.shop.order.claimapply.support.ClaimApplyParam;
import saleson.shop.order.claimapply.support.ClaimException;
import saleson.shop.order.claimapply.support.ExchangeApply;
import saleson.shop.order.claimapply.support.ReturnApply;
import saleson.shop.order.domain.*;
import saleson.shop.order.giftitem.OrderGiftItemService;
import saleson.shop.order.payment.OrderPaymentMapper;
import saleson.shop.order.pg.PgService;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.order.refund.OrderRefundService;
import saleson.shop.order.refund.domain.OrderRefund;
import saleson.shop.order.refund.domain.OrderRefundDetail;
import saleson.shop.order.refund.support.OrderRefundParam;
import saleson.shop.order.shipping.OrderShippingMapper;
import saleson.shop.order.shipping.support.ShippingParam;
import saleson.shop.order.support.OrderException;
import saleson.shop.order.support.OrderParam;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.Point;
import saleson.shop.receipt.ReceiptService;
import saleson.shop.receipt.support.CashbillIssueRepository;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillRepository;
import saleson.shop.shipmentreturn.ShipmentReturnMapper;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;
import saleson.shop.shipmentreturn.support.ShipmentReturnParam;
import saleson.shop.user.domain.UserDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("orderClaimApplyService")
public class OrderClaimApplyServiceImpl implements OrderClaimApplyService {

    private static final Logger log = LoggerFactory.getLogger(OrderClaimApplyServiceImpl.class);

	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private OrderClaimApplyMapper orderClaimApplyMapper;
	
	@Autowired
	private DeliveryCompanyService deliveryCompanyService;
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRefundService orderRefundService;
	
	@Autowired
	private OrderAddPaymentService orderAddPaymentService;
	
	@Autowired
	private ShipmentReturnMapper shipmentReturnMapper;
	
	@Autowired
	private SellerMapper sellerMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private PointService pointService;

	@Autowired
	@Qualifier("inicisService")
	private PgService inicisService;
	
	@Autowired
	@Qualifier("lgDacomService")
	private PgService lgDacomService;

	@Autowired
	@Qualifier("cjService")
	private PgService cjService;
	
	@Autowired
	@Qualifier("paycoService")
	private PgService paycoService;
	
	@Autowired
	@Qualifier("kakaopayService")
	private PgService kakaopayService;
	
	@Autowired
	@Qualifier("kspayService")
	private PgService kspayService;
	
	@Autowired
	@Qualifier("kcpService")
	private PgService kcpService;
	
	@Autowired
	@Qualifier("easypayService")
	private PgService easypayService;

	@Autowired
	@Qualifier("nicepayService")
	private PgService nicepayService;
	
	@Autowired
	private OrderPaymentMapper orderPaymentMapper;
	
	@Autowired
	private CouponMapper couponMapper;

	@Autowired
    private CashbillRepository cashbillRepository;

	@Autowired
    private CashbillIssueRepository cashbillIssueRepository;

	@Autowired
    ReceiptService receiptService;

	@Autowired
    OrderAddPaymentMapper orderAddPaymentMapper;

	@Autowired
	private OrderGiftItemService orderGiftItemService;

    @Autowired
    private ConfigPgService configPgService;

    @Autowired
    private NaverPaymentApi naverPaymentApi;

    @Autowired
    private ErpService erpService;

	/**
	 * 사용자용 배송 정책 리스트 만들기
	 * @param order
	 * @param claimApply
	 * @return
	 */
	private List<OrderShipping> getShippingsForUser(Order order, ClaimApply claimApply) {

		List<OrderShipping> orderShippings = new ArrayList<>();
		List<OrderCancelApply> orderCancelApplys = new ArrayList<>();

		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem orderItem : info.getOrderItems()) {

			    // 이미 취소된 상품인 경우
				if ("Y".equals(orderItem.getCancelFlag())) {
					continue;
				}
				
				// CJH 2017.01.03 환불 대기 상태 추가
				if ("1".equals(orderItem.getRefundStatus())) {
					continue;
				}
				
				int quantity = 0;
				boolean isClaim = false;
				for(String key : claimApply.getId()) {
					
					ClaimApplyItem claimApplyItem = (claimApply.getClaimApplyItemMap()).get(key);
					
					if (claimApplyItem == null) {
						continue;
					}
					
					if (claimApplyItem.getItemSequence() == orderItem.getItemSequence()) {
						quantity = orderItem.getQuantity() - claimApplyItem.getApplyQuantity();
						
						if ("1".equals(claimApply.getClaimType()) || "4".equals(claimApply.getClaimType())) {
							OrderCancelApply orderCancelApply = new OrderCancelApply();
							orderCancelApply.setOrderCode(claimApply.getOrderCode());
							orderCancelApply.setOrderSequence(claimApply.getOrderSequence());
							orderCancelApply.setItemSequence(claimApplyItem.getItemSequence());
							orderCancelApply.setClaimApplyQuantity(claimApplyItem.getApplyQuantity());
							orderCancelApply.setClaimApplyAmount(orderItem.getSalePrice() * claimApplyItem.getApplyQuantity());
							orderCancelApply.setOrderItem(orderItem);
							orderCancelApplys.add(orderCancelApply);
						}
						
						isClaim = true;
						
						// 주문 총수량보다 기존 클래임 수량 + 신청 수량이 큰경우 에러
						if (orderItem.getOrderQuantity() < claimApplyItem.getApplyQuantity() + orderItem.getClaimQuantity()) {
							throw new OrderException();
						}
						
						break;
					}
					
				}
				
				if (isClaim == true) {
					if (quantity < 0) {
						throw new OrderException();
					}
				} else {
					quantity = orderItem.getQuantity();
				}
				
				orderItem.setQuantity(quantity);
				OrderShipping orderShipping = orderItem.getOrderShipping();
				
				boolean inArray = false;
				
				for(OrderShipping shipping : orderShippings) {
					if (shipping.getShippingSequence() == orderShipping.getShippingSequence()) {
						inArray = true;
						
						if (quantity > 0) {
							List<OrderItem> list = shipping.getOrderItems();
							if (list == null) {
								list = new ArrayList<>();
							}
							
							list.add(orderItem);
							shipping.setOrderItems(list);
						}
						
						if (isClaim == true) {

							if ("2".equals(claimApply.getClaimType()) && "1".equals(claimApply.getClaimReason())) {
								// 반품이면서 판매자 사유이면 배송비 계산 안함
							} else {
								shipping.setRePayShipping("Y");
							}
							
						}
						
						break;
					}
				}
				
				if (inArray == false) {
					
					// 도서산간 지역??
					String islandType = "";
					if (StringUtils.isEmpty(info.getReceiveZipcode()) == false) {
						islandType = orderService.getIslandTypeByZipcode(info.getReceiveZipcode());
					}
					
					orderShipping.setIslandType(islandType);
					
					if (quantity > 0) {
						List<OrderItem> list = new ArrayList<>();
						list.add(orderItem);
						orderShipping.setOrderItems(list);
					}
					
					if (isClaim == true) {

						if ("2".equals(claimApply.getClaimType()) && "1".equals(claimApply.getClaimReason())) {
							// 반품이면서 판매자 사유이면 배송비 계산 안함
						} else {
							orderShipping.setRePayShipping("Y");
						}
						
					}
					
					orderShippings.add(orderShipping);
				}
			}
		}
		
		for(OrderShipping orderShipping : orderShippings) {

			if (!"Y".equals(orderShipping.getRePayShipping())) {
				continue;
			}
			
			// 통합 물류 배송일때 같은 통합물류 정책 배송비를 재계산 한다
			if (StringUtils.isEmpty(orderShipping.getShipmentGroupCode()) == false) {
				
				if (ValidationUtils.isNull(orderShippings) == false) {
					for(OrderShipping os : orderShippings) {
						
						if (orderShipping.getShipmentGroupCode().equals(os.getShipmentGroupCode())) {
							os.setRePayShipping("Y");
						}
						
					}
				}
			}
		}
		
		claimApply.setOrderCancelApplys(orderCancelApplys);
		return orderShippings;
	}
	
	/**
	 * 관리자용 배송 정책 리스트 만들기
	 * @param order
	 * @return
	 */
	private List<OrderShipping> getShippingsForManager(Order order, ClaimApply claimApply) {
		
		List<OrderShipping> orderShippings = new ArrayList<>();
		
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem orderItem : info.getOrderItems()) {

				if ("Y".equals(orderItem.getCancelFlag())) {
					continue;
				}
				
				// CJH 2017.01.03 환불 대기 상태 추가
				if ("1".equals(orderItem.getRefundStatus())) {
					continue;
				}
				
				boolean isClaim = false;
				int quantity = 0;
				for(String key : claimApply.getCancelIds()) {
					
					OrderCancelApply orderCancelApply = claimApply.getCancelApplyMap().get(key);
					if (orderCancelApply.getItemSequence() == orderItem.getItemSequence()) {
						isClaim = true;
						
						quantity = orderItem.getQuantity() - orderCancelApply.getClaimApplyQuantity();
					}
				}
				
				OrderCancelApply cancelApply = orderItem.getCancelApply();
				if (cancelApply != null) {
					if ("03".equals(cancelApply.getClaimStatus()) || "04".equals(cancelApply.getClaimStatus())) {
						isClaim = true;
					}
				}
				
				if (isClaim == true) {
					if (quantity < 0) {
						throw new OrderException();
					}
				} else {
					quantity = orderItem.getQuantity();
				}
				
				orderItem.setQuantity(quantity);
				OrderShipping orderShipping = orderItem.getOrderShipping();
				
				boolean inArray = false;
				for(OrderShipping shipping : orderShippings) {
					if (shipping.getShippingSequence() == orderShipping.getShippingSequence()) {
						inArray = true;
						
						if (quantity > 0) {
							List<OrderItem> list = shipping.getOrderItems();
							if (list == null) {
								list = new ArrayList<>();
							}
							
							list.add(orderItem);
							shipping.setOrderItems(list);
						}
						
						if (isClaim == true) {

							if ("2".equals(claimApply.getClaimType()) && "1".equals(claimApply.getClaimReason())) {
								// 반품이면서 판매자 사유이면 배송비 계산 안함
							} else {
								shipping.setRePayShipping("Y");
							}
							
						}
						
						break;
					}
				}
				
				if (inArray == false) {
					
					// 도서산간 지역??
					String islandType = "";
					if (StringUtils.isEmpty(info.getReceiveZipcode()) == false) {
						islandType = orderService.getIslandTypeByZipcode(info.getReceiveZipcode());
					}
					
					orderShipping.setIslandType(islandType);
					
					if (quantity > 0) {
						List<OrderItem> list = new ArrayList<>();
						list.add(orderItem);
						orderShipping.setOrderItems(list);
					}
					
					if (isClaim == true) {

						if ("2".equals(claimApply.getClaimType()) && "1".equals(claimApply.getClaimReason())) {
							// 반품이면서 판매자 사유이면 배송비 계산 안함
						} else {
							orderShipping.setRePayShipping("Y");
						}
						
					}
					
					orderShippings.add(orderShipping);
				}
			}
		}
		
		for(OrderShipping orderShipping : orderShippings) {

			if (!"Y".equals(orderShipping.getRePayShipping())) {
				continue;
			}
			
			// 통합 물류 배송일때 같은 통합물류 정책 배송비를 재계산 한다
			if (StringUtils.isEmpty(orderShipping.getShipmentGroupCode()) == false) {
				
				if (ValidationUtils.isNull(orderShippings) == false) {
					for(OrderShipping os : orderShippings) {
						
						if (orderShipping.getShipmentGroupCode().equals(os.getShipmentGroupCode())) {
							os.setRePayShipping("Y");
						}
						
					}
				}
			}
		}
		
		return orderShippings;
	}
	
	@Override
	public List<OrderShipping> getReShippingAmountForUser(ClaimApply claimApply) {
		
		if (claimApply.getId() == null) {
			return null;
		}
		
		
		List<OrderShipping> orderShippings = getShippingsForUser(claimApply.getOrder(), claimApply);
		resetPayShippingAmount(orderShippings);
		return orderShippings;
	}
	
	@Override
	public List<OrderShipping> getReShippingAmountForManager(ClaimApply claimApply) {
		
		if (claimApply.getCancelIds() == null) {
			return null;
		}
		
		if (claimApply.getCancelShippingMap() == null) {
			return null;
		}

		boolean isRePayShipping = false;
		List<OrderShipping> orderShippings = null;
		for(OrderCancelShipping orderCancelShipping : claimApply.getCancelShippingMap().values()) {
			
			// 배송비 재계산??
			if ("Y".equals(orderCancelShipping.getRePayShipping())) {
				isRePayShipping = true;
				break;
				
			}
			
		}
		
		if (isRePayShipping == true) {
			
			OrderParam orderParam = new OrderParam(claimApply);
			orderParam.setConditionType("OPMANAGER");
			
			if (ShopUtils.isSellerPage()) {
				orderParam.setConditionType("SELLER");
				orderParam.setSellerId(SellerUtils.getSellerId());
			}
			
			Order order = orderService.getOrderByParam(orderParam);
			if (order == null) {
				throw new OrderException();
			}
			
			orderShippings = getShippingsForManager(order, claimApply);
			resetPayShippingAmount(orderShippings);
		}
		
		return orderShippings;
	}
	
	@Override
	public List<OrderExchangeApply> getExchangeApplyByClaimCode(String[] claimCode) {
		return orderClaimApplyMapper.getExchangeApplyByClaimCode(claimCode);
	}
	
	@Override
	public List<OrderReturnApply> getReturnApplyByClaimCode(String[] claimCode) {
		return orderClaimApplyMapper.getReturnApplyByClaimCode(claimCode);
	}
	
	@Override
	public List<OrderReturnApply> getReturnHistoryListByParam(ClaimApplyParam claimApplyParam) {
		claimApplyParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			claimApplyParam.setSellerId(SellerUtils.getSellerId());
			claimApplyParam.setConditionType("SELLER");
		}

		claimApplyParam.setAdditionItemFlag("N");
		List<OrderReturnApply> list = orderClaimApplyMapper.getReturnHistoryListByParam(claimApplyParam);

		setOrderGiftItemForOrderReturnApply(list);

		// 추가구성상품
		claimApplyParam.setAdditionItemFlag("Y");
		List<OrderReturnApply> additionItemList = orderClaimApplyMapper.getReturnHistoryListByParam(claimApplyParam);
		setOrderAdditionItemForOrderReturnApply(list, additionItemList);

		return list;
	}
	
	@Override
	public List<OrderCancelApply> getCancelHistoryListByParam(ClaimApplyParam claimApplyParam) {
		
		claimApplyParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			claimApplyParam.setSellerId(SellerUtils.getSellerId());
			claimApplyParam.setConditionType("SELLER");
		}

		claimApplyParam.setAdditionItemFlag("N");
		List<OrderCancelApply> list = orderClaimApplyMapper.getCancelHistoryListByParam(claimApplyParam);

		setOrderGiftItemForOrderCancelApply(list);

		// 추가구성상품
		claimApplyParam.setAdditionItemFlag("Y");
		List<OrderCancelApply> additionItemList = orderClaimApplyMapper.getCancelHistoryListByParam(claimApplyParam);
		setOrderAdditionItemForOrderCancelApply(list, additionItemList);

		return list;
	}
	
	@Override
	public List<OrderCancelApply> getCancelListByParam(ClaimApplyParam claimApplyParam) {

		claimApplyParam.setAdditionItemFlag("N");
		int totalCount = orderClaimApplyMapper.getCancelCountByParam(claimApplyParam);
		
		if (claimApplyParam.getItemsPerPage() == 10) {
			claimApplyParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, claimApplyParam.getItemsPerPage());
		claimApplyParam.setPagination(pagination);
		claimApplyParam.setLanguage(CommonUtils.getLanguage());

		List<OrderCancelApply> list = orderClaimApplyMapper.getCancelListByParam(claimApplyParam);

		setOrderGiftItemForOrderCancelApply(list);

		// 추가구성상품
		claimApplyParam.setAdditionItemFlag("Y");
		List<OrderCancelApply> additionList = orderClaimApplyMapper.getCancelListByParam(claimApplyParam);
		setOrderAdditionItemForOrderCancelApply(list, additionList);

		return list;
		
	}
	
	@Override
	public List<OrderReturnApply> getReturnListByParam(ClaimApplyParam claimApplyParam) {

		claimApplyParam.setAdditionItemFlag("N");
		int totalCount = orderClaimApplyMapper.getReturnCountByParam(claimApplyParam);
		
		if (claimApplyParam.getItemsPerPage() == 10) {
			claimApplyParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, claimApplyParam.getItemsPerPage());
		claimApplyParam.setPagination(pagination);
		claimApplyParam.setLanguage(CommonUtils.getLanguage());

		List<OrderReturnApply> list = orderClaimApplyMapper.getReturnListByParam(claimApplyParam);

		setOrderGiftItemForOrderReturnApply(list);

		// 추가구성상품
		claimApplyParam.setAdditionItemFlag("Y");
		List<OrderReturnApply> additionList = orderClaimApplyMapper.getReturnListByParam(claimApplyParam);
		setOrderAdditionItemForOrderReturnApply(list, additionList);

		return list;
		
	}
	
	@Override
	public List<OrderExchangeApply> getExchangeListByParam(ClaimApplyParam claimApplyParam) {

		claimApplyParam.setAdditionItemFlag("N");
		int totalCount = orderClaimApplyMapper.getExchangeCountByParam(claimApplyParam);
		
		if (claimApplyParam.getItemsPerPage() == 10) {
			claimApplyParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, claimApplyParam.getItemsPerPage());
		claimApplyParam.setPagination(pagination);
		claimApplyParam.setLanguage(CommonUtils.getLanguage());

		List<OrderExchangeApply> list = orderClaimApplyMapper.getExchangeListByParam(claimApplyParam);

		setOrderGiftItemForOrderExchangeApply(list);

		// 추가구성상품
		claimApplyParam.setAdditionItemFlag("Y");
		List<OrderExchangeApply> additionList = orderClaimApplyMapper.getExchangeListByParam(claimApplyParam);
		setOrderAdditionItemForOrderExchangeApply(list, additionList);

		return list;
		
	}
	
	@Override
	public void insertOrderReturnApply(ReturnApply returnApply) {

		OrderParam orderParam = new OrderParam(returnApply);
		
		if (UserUtils.isUserLogin()) {
			orderParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {
			
		} else {
			throw new PageNotFoundException();
		}

		OrderItem orderItem = orderMapper.getOrderItemByParam(orderParam);
		if (orderItem == null) {
			throw new OrderException();
		}

		// CJH 2016.11.01 환불 신청시 주문 수량보다 신청수량이 더 많은경우 에러
		if (orderItem.getOrderQuantity() < returnApply.getApplyQuantity() + orderItem.getClaimQuantity()) {
			throw new OrderException("환불을 신청하실수 없습니다.");
		}

		orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());

		//CJH 2016.10.09 화면에서 환불 신청정보를 넘겨받음
		//returnApply.setReturnApply(orderMapper.getOrderShippingInfoByParam(orderParam));

		OrderReturnApply detail = new OrderReturnApply(orderItem, returnApply);

		ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
		shipmentReturnParam.setShipmentReturnId(returnApply.getShipmentReturnId());
		ShipmentReturn shipmentReturn = shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
		if (shipmentReturn == null) {
			throw new OrderException();
		}

		//환불정보업데이트
		orderMapper.updateOrderReturnInfo(orderParam);

		// 1 : 본사반송, 2 업체 반송
		long sellerId = "2".equals(orderItem.getShipmentReturnType()) ? orderItem.getSellerId() : SellerUtils.DEFAULT_OPMANAGER_SELLER_ID;

		detail.setShipmentReturnId(shipmentReturn.getShipmentReturnId());
		detail.setShipmentReturnSellerId(sellerId);

		// 추가구성상품과 묶인 본품이 copyOrderItemForReturnApply 처리로 신규 itemSequence가 생성됐을 수도 있어서 본품의 max itemSequence 가져오기
		if ("Y".equals(orderItem.getAdditionItemFlag())) {
			orderParam.setAdditionItemFlag("N");
			orderParam.setItemId(orderItem.getParentItemId());
			orderParam.setOptions(orderItem.getParentItemOptions());
			orderParam.setOrderStatus("60");
			int parentItemSequence = orderService.getMaxParentOrderItemSequence(orderParam);

			detail.setParentItemSequence(parentItemSequence);
		}


		// 주문 수량과 신청수량이 같으면 기존 주문 변경
		if (orderItem.getQuantity() == detail.getClaimApplyQuantity()) {
			orderClaimApplyMapper.insertOrderReturnApply(detail);
			orderClaimApplyMapper.updateClaimQuantityForReturnApply(detail);
		} else {

			orderClaimApplyMapper.copyOrderItemForReturnApply(detail);
			orderClaimApplyMapper.updateOrderItemQuantityForReturn(detail);

			// 중요!!
			detail.setItemSequence(detail.getCopyItemSequence());
			orderClaimApplyMapper.insertOrderReturnApply(detail);

			// ERP 연동 시 사용
			orderItem.setCopyItemSequence(detail.getCopyItemSequence());
			orderItem.setErpOriginUnique(orderItem.getOrderCode() + "" + orderItem.getOrderSequence() + "" + orderItem.getItemSequence() + "00" + "00");
		}

		orderItem.setReturnApply(detail);


		orderParam.setConditionType("OPMANAGER");
		orderParam.setAdditionItemFlag("");
		Order order = orderService.getOrderByParam(orderParam);

		// ERP 연동을 위한 items 세팅
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			info.setReceiveName(returnApply.getReturnReserveName());
			info.setReceivePhone(returnApply.getReturnReservePhone());
			info.setReceiveMobile(returnApply.getReturnReserveMobile());
			info.setReceiveAddress(returnApply.getReturnReserveAddress());
			info.setReceiveAddressDetail(returnApply.getReturnReserveAddress2());
			info.setReceiveNewZipcode(returnApply.getReturnReserveZipcode());

			List<OrderItem> erpOrderItems = new ArrayList<>();
			for(OrderItem item : info.getOrderItems()) {
				if (orderItem.getItemSequence() == item.getItemSequence()) {
					item.setCopyItemSequence(orderItem.getCopyItemSequence());
					item.setOrderStatus("60");
					item.setReturnApply(detail);
					erpOrderItems.add(item);
				}
			}

			info.setOrderItems(erpOrderItems);
		}

		// 반품 신청 시  ERP 연동
		ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
		erpService.saveOrderListGet(erpOrder);

		// 주문 로그
		orderService.insertOrderLog(
				OrderLogType.CLAIM_RETURN,
				detail.getOrderCode(),
				detail.getOrderSequence(),
				detail.getItemSequence(),
				orderItem.getOrderStatus()
		);
	}
	
	@Override
	public void insertOrderCancelApply(ClaimApply claimApply) {

		ConfigPg configPg = configPgService.getConfigPg();
		String pgType = "";

		if (configPg != null) {
			pgType = configPg.getPgType().getCode().toLowerCase();
		} else {
			pgType = SalesonProperty.getPgService();
		}

		if (claimApply.getId() == null) {
			throw new OrderException();
		}

		String orderCode = claimApply.getOrderCode();
		int orderSequence = claimApply.getOrderSequence();

		OrderParam orderParam = new OrderParam();
		orderParam.setUserId(UserUtils.getUserId());
		orderParam.setOrderCode(claimApply.getOrderCode());
		orderParam.setOrderSequence(claimApply.getOrderSequence());

		Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			throw new OrderException();
		}

		claimApply.setOrder(order);
		OrderRefund orderRefund = orderRefundService.getOrderCancelRefundForUser(claimApply);
		if (orderRefund == null) {
			throw new OrderException();
		}

		OrderRefundParam orderRefundParam = new OrderRefundParam();
		orderRefundParam.setOrderCode(orderCode);
		orderRefundParam.setOrderSequence(orderSequence);
		orderRefundParam.setBankInName(claimApply.getReturnBankInName());
		orderRefundParam.setBankName(claimApply.getReturnBankName());
		orderRefundParam.setVirtualNo(claimApply.getReturnVirtualNo());



		// 카드 전체취소일 경우 부분취소 가능여부(PART_CANCEL_FLAG)와 상관없이 PG취소
		boolean isCard = false;
		for (OrderPayment payment : orderRefund.getOrderPayments()) {
			// 1. 카드 결제 여부
			if ("card".equals(payment.getApprovalType())) {
				isCard = true;
			}
		}

		// 2. 총 취소신청 수량
		int claimApplyQuantity = 0;
 		for (OrderCancelApply orderCancelApply : claimApply.getOrderCancelApplys()) {
			claimApplyQuantity += orderCancelApply.getClaimApplyQuantity();
 		}

		// 3. 즉시취소 처리를 위한 flag - 총 취소신청 수량과 주문 수량이 같으면 즉시 취소
		boolean isCardAutoCancel = false;
 		if (isCard && claimApplyQuantity == orderRefund.getTotalOrderQuantity()) {
			isCardAutoCancel = true;
		}

		// 환불 코드 따기 - 즉시환불일때는 무조건 신규 생성
		if ("1".equals(claimApply.getClaimRefundType())) {
			if (orderRefund.isAutoCancel() == false && isCardAutoCancel == false) {


				String refundCode = orderRefundService.getActiveRefundCodeByParam(orderRefundParam);
				if (StringUtils.isEmpty(refundCode)) {
					throw new OrderException();
				}

				claimApply.setRefundCode(refundCode);

			} else {

				orderRefundParam.setConditionType("REFUND_FINISH");
				String refundCode = orderRefundService.getNewRefundCodeByParam(orderRefundParam);
				if (StringUtils.isEmpty(refundCode)) {
					throw new OrderException();
				}

				claimApply.setRefundCode(refundCode);
			}
		}

		orderParam.setReturnBankInName(orderRefundParam.getBankInName());
		orderParam.setReturnBankName(orderRefundParam.getBankName());
		if (StringUtils.isEmpty(claimApply.getReturnBankName()) == false) {
			List<CodeInfo> list = ShopUtils.getBankListByKey(pgType);
			for(CodeInfo code : list) {
				if (code.getKey().getId().equals(claimApply.getReturnBankName())) {
					orderParam.setReturnBankName(code.getLabel());
					break;
				}
			}
		}

		orderParam.setReturnVirtualNo(orderRefundParam.getVirtualNo());

		//환불정보업데이트
		orderMapper.updateOrderReturnInfo(orderParam);
		OrderItem orderItem = new OrderItem();
		OrderShipping orderShipping = new OrderShipping();
		List<Integer> returnCoupons = new ArrayList<>();
		HashMap<String, Integer> stockMap = new HashMap<>();
		List<OrderItem> orderItemsForErp = new ArrayList<>();

		for(OrderRefundDetail group : orderRefund.getGroups()) {

			boolean isCopyItemSequence = false;
			int parentIemSequence = 0;
			int parentItemId = 0;
			String parentItemOptions = "";
			for(OrderCancelApply orderCancelApply : group.getOrderCancelApplys()) {
				orderItem = orderCancelApply.getOrderItem();

				// 추가구성상품과 묶인 본품이 copyOrderItemForCancelApply 처리로 신규 itemSequence가 생성됐을 때 추가구성상품의 parentItemSequence에 세팅
				if (isCopyItemSequence && "Y".equals(orderItem.getAdditionItemFlag())
						&& parentItemId == orderItem.getParentItemId() && parentItemOptions.equals(orderItem.getParentItemOptions())) {

					orderCancelApply.setParentItemSequence(parentIemSequence);
				}

				if ("1".equals(claimApply.getClaimRefundType()) && (orderRefund.isAutoCancel() || isCardAutoCancel)) {
					orderCancelApply.setRefundCode(claimApply.getRefundCode());
					orderCancelApply.setClaimStatus("04");

					if (orderItem.getCouponUserId() > 0) {
						returnCoupons.add(orderItem.getCouponUserId());
					}

					if (orderItem.getAddCouponUserId() > 0) {
						returnCoupons.add(orderItem.getAddCouponUserId());
					}

					// 재고 복원해야하는 목록 만들기
					orderService.makeStockRestorationMap(stockMap, orderCancelApply);
				} else {
					orderCancelApply.setClaimStatus("01");
				}

				orderCancelApply.setCancelReason(claimApply.getClaimReason());
				orderCancelApply.setCancelReasonText(claimApply.getClaimReasonText());
				orderCancelApply.setCancelReasonDetail(claimApply.getClaimReasonDetail());
				orderCancelApply.setItemSequence(orderItem.getItemSequence());
				orderCancelApply.setClaimApplySubject("01");

				orderCancelApply.setClaimApplyQuantity(orderCancelApply.getClaimApplyQuantity());

				if ("1".equals(claimApply.getClaimRefundType()) && (orderRefund.isAutoCancel() || isCardAutoCancel)) {
					orderCancelApply.setSalesCancel(true);
				}

				// 잔여 수량이 0이라면..
				if (orderItem.getQuantity() == 0) {
					orderClaimApplyMapper.insertOrderCancelApply(orderCancelApply);
					orderClaimApplyMapper.updateClaimQuantityForCancelApply(orderCancelApply);
				} else {

					orderClaimApplyMapper.copyOrderItemForCancelApply(orderCancelApply);
					orderClaimApplyMapper.updateOrderItemQuantityForCancel(orderCancelApply);

					// 중요!!
					orderCancelApply.setItemSequence(orderCancelApply.getCopyItemSequence());
					orderClaimApplyMapper.insertOrderCancelApply(orderCancelApply);

					// ERP 연동 시 사용
					orderItem.setCopyItemSequence(orderCancelApply.getCopyItemSequence());
					orderItem.setErpOriginUnique(orderItem.getOrderCode() + "" + orderItem.getOrderSequence() + "" + orderItem.getItemSequence() + "00" + "00");


					// 추가구성상품과 묶인 본품인지 체크 후 정보 세팅
					if ("N".equals(orderItem.getAdditionItemFlag())) {
						orderParam.setAdditionItemFlag("Y");
						orderParam.setParentItemId(orderItem.getItemId());
						orderParam.setParentItemOptions(orderItem.getOptions());
						List<OrderItem> additionOrderItem = orderService.getOrderItemListByParam(orderParam);

						if (additionOrderItem != null) {
							isCopyItemSequence = true;
							parentIemSequence = orderCancelApply.getItemSequence();
							parentItemId = orderItem.getItemId();
							parentItemOptions = orderItem.getOptions();
						}
					}
				}

				orderItem.setCancelApply(orderCancelApply);
				orderItemsForErp.add(orderItem);
			}

			if ("1".equals(claimApply.getClaimRefundType()) && (orderRefund.isAutoCancel() || isCardAutoCancel)) {
				for(OrderAddPayment orderAddPayment : group.getOrderAddPayments()) {

					if ("NO_INSERT".equals(orderAddPayment.getIssueCode())) {
						continue;
					}

					orderAddPayment.setRefundCode(claimApply.getRefundCode());
					orderAddPaymentService.insertOrderAddPayment(orderAddPayment);

					orderShipping = orderAddPayment.getOrderShipping();
					orderShipping.setPayShipping(orderShipping.getRePayShippingAmount());
					orderShipping.setRemittanceAmount(orderShipping.getRePayShippingAmount());

					if ("2".equals(orderShipping.getAddPaymentType())) {
						orderShipping.setReturnShipping(orderShipping.getReturnShipping() + orderShipping.getAddPayAmount());
					}

					orderShippingMapper.updateCancelShipping(orderShipping);

				}
			}
		}

		if ("1".equals(claimApply.getClaimRefundType()) && (orderRefund.isAutoCancel() || isCardAutoCancel)) {

			// 포인트부터 취소
			int returnAmount = orderRefund.getTotalReturnAmount();
			for (OrderPayment payment : orderRefund.getOrderPayments()) {
				if (payment.getRemainingAmount() > 0) {

					if (PointUtils.isPointType(payment.getApprovalType())) {
                        int cancelAmount = returnAmount > payment.getRemainingAmount() ? payment.getRemainingAmount() : returnAmount;

						returnPoint(order, payment, cancelAmount);
						returnAmount = returnAmount - cancelAmount;

					}
				}

				if (returnAmount <= 0) {
					break;
				}
			}

			if (returnAmount > 0) {

				for (OrderPayment payment : orderRefund.getOrderPayments()) {

					if (PointUtils.isPointType(payment.getApprovalType())) {
						continue;
					}

					if (payment.getRemainingAmount() > 0) {

						// Mall 결제 타입 말고는 PG밖에 없겠지?
						OrderPgData orderPgData = payment.getOrderPgData();
						if (orderPgData.getOrderPgDataId() > 0) {

							boolean isSuccess = false;
							int cancelAmount = returnAmount > payment.getRemainingAmount() ? payment.getRemainingAmount() : returnAmount;

							// 취소 금액과 잔여액이 같으면 전체 취소
							boolean isPartCancel = cancelAmount == payment.getRemainingAmount() ? false : true;
							// CJH 2016.12.07 - 이니시스의 경우 부분취소를 한번하면 부분취소만 되는듯!!
							if ("inicis".equals(orderPgData.getPgServiceType()) && "PART_CANCEL".equals(orderPgData.getPartCancelDetail())) {
								isPartCancel = true;
							}

							// Jun-Eu 2017.03.02 - 실시간계좌이체는 부분취소 기능 미지원
							if ("kspay".equals(orderPgData.getPgServiceType()) && !"N".equals(orderPgData.getPartCancelDetail())) {
								isPartCancel = true;
							} else if ("N".equals(orderPgData.getPartCancelFlag())) {
								isPartCancel = false;
							}

							if ("kcp".equals(orderPgData.getPgServiceType()) && "PART_CANCEL".equals(orderPgData.getPartCancelDetail())) {
								isPartCancel = true;
							}

							if ("nicepay".equals(orderPgData.getPgServiceType()) && "PART_CANCEL".equals(orderPgData.getPartCancelDetail())) {
								isPartCancel = true;
							}

                            if ("naverpay".equals(orderPgData.getPgServiceType()) && "PART_CANCEL".equals(orderPgData.getPartCancelDetail())) {
                                isPartCancel = true;
                            }

							/*else if ("N".equals(orderPgData.getPartCancelFlag())) {
								isPartCancel = false;
							} */

							// erp연동을 위한 set cancelApply
							log.debug("[orderCliamApplyServiceImpl] order.getOrderShippingInfos().size : {}", order.getOrderShippingInfos().size());

							order.getOrderShippingInfos().stream().forEach(shipping -> {
								shipping.getOrderItems().forEach(oi -> {
									Optional<OrderItem> optional = orderItemsForErp.stream().filter(oif -> oif.getOrderCode().equals(oi.getOrderCode()) && oif.getItemSequence() == oi.getItemSequence()).findFirst();
									log.debug("[orderCliamApplyServiceImpl] optional.isPresent() : {}", optional.isPresent());

									if (optional.isPresent()) {
										oi.setCancelApply(optional.get().getCancelApply());
										log.debug("[orderCliamApplyServiceImpl] optional.get().getCancelApply() : {}", optional.get().getCancelApply());
									}
								});
							});


							// 즉시 취소 시 ERP uniq값 validate
							ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
							List<OrderLine> orderLines = erpService.validateOrderListGetForClaim(erpOrder);


							orderPgData.setMessage("주문취소");
							orderPgData.setCancelAmount(cancelAmount);
							orderPgData.setRemainAmount(payment.getRemainingAmount() - cancelAmount);

							if (isPartCancel == false) {

								// PG 취소 - 즉시 취소
								if ("inicis".equals(orderPgData.getPgServiceType())) {
									if ("vbank".equals(payment.getApprovalType())) {
										orderPgData.setReturnBankName(claimApply.getReturnBankName());
										orderPgData.setReturnAccountNo(claimApply.getReturnVirtualNo());
										orderPgData.setReturnName(claimApply.getReturnBankInName());
									}

									isSuccess = inicisService.cancel(orderPgData);
								} else if ("lgdacom".equals(orderPgData.getPgServiceType())) {
									isSuccess = lgDacomService.cancel(orderPgData);
								} else if ("kakaopay".equals(orderPgData.getPgServiceType())) {
									orderPgData.setRemainAmount(cancelAmount);
									isSuccess = kakaopayService.cancel(orderPgData);
								} else if ("payco".equals(orderPgData.getPgServiceType())) {
									orderPgData.setRemainAmount(cancelAmount);
									isSuccess = paycoService.cancel(orderPgData);
								} else if ("kspay".equals(orderPgData.getPgServiceType())) {
									isSuccess = kspayService.cancel(orderPgData);
								} else if ("kcp".equals(orderPgData.getPgServiceType())) {
									isSuccess = kcpService.cancel(orderPgData);
								} else if ("easypay".equals(orderPgData.getPgServiceType())) {
									if ("vbank".equals(payment.getApprovalType())) {
										orderPgData.setReturnBankName(claimApply.getReturnBankName());
										orderPgData.setReturnAccountNo(claimApply.getReturnVirtualNo());
										orderPgData.setReturnName(claimApply.getReturnBankInName());
									}
									isSuccess = easypayService.cancel(orderPgData);
								} else if ("nicepay".equals(orderPgData.getPgServiceType())) {
									if (!" ".equals(claimApply.getClaimReasonDetail())) {
										orderPgData.setCancelReason(claimApply.getClaimReasonDetail());
									} else {
										orderPgData.setCancelReason(claimApply.getClaimReasonText());
									}
									orderPgData.setRequest(claimApply.getRequest());
									orderPgData.setResponse(claimApply.getResponse());

									if ("vbank".equals(payment.getApprovalType())) {
										orderPgData.setReturnBankName(claimApply.getReturnBankName());
										orderPgData.setReturnAccountNo(claimApply.getReturnVirtualNo());
										orderPgData.setReturnName(claimApply.getReturnBankInName());
									}

									isSuccess = nicepayService.cancel(orderPgData);

                                } else if ("naverpay".equals(orderPgData.getPgServiceType())) {

                                    if (!" ".equals(claimApply.getClaimReasonDetail())) {
                                        orderPgData.setCancelReason(claimApply.getClaimReasonDetail());
                                    } else {
                                        orderPgData.setCancelReason(claimApply.getClaimReasonText());
                                    }
                                    orderPgData = naverPaymentApi.cancel(orderPgData, configPg);

                                    isSuccess = orderPgData.isSuccess();
                                }

							} else {

								if ("inicis".equals(orderPgData.getPgServiceType())) {

									if ("vbank".equals(payment.getApprovalType())) {
										orderPgData.setReturnBankName(claimApply.getReturnBankName());
										orderPgData.setReturnAccountNo(claimApply.getReturnVirtualNo());
										orderPgData.setReturnName(claimApply.getReturnBankInName());
									}

									orderPgData = inicisService.partCancel(orderPgData);
								} else if ("lgdacom".equals(orderPgData.getPgServiceType())) {
									orderPgData = lgDacomService.partCancel(orderPgData);
								} else if ("kakaopay".equals(orderPgData.getPgServiceType())) {
									orderPgData.setRemainAmount(cancelAmount);
									orderPgData = kakaopayService.partCancel(orderPgData);
								} else if ("payco".equals(orderPgData.getPgServiceType())) {
									orderPgData.setRemainAmount(cancelAmount);
									orderPgData.setPaycoCancelProducts(PaycoUtils.makePaycoCancelProducts(orderItem, returnAmount, orderShipping.getPayShipping(), orderPgData.getPgProcInfo()));
									orderPgData = paycoService.partCancel(orderPgData);
								} else if ("kspay".equals(orderPgData.getPgServiceType())) {
									orderPgData = kspayService.partCancel(orderPgData);
								} else if ("kcp".equals(orderPgData.getPgServiceType())) {
									orderPgData = kcpService.partCancel(orderPgData);
								} else if ("easypay".equals(orderPgData.getPgServiceType())) {
									if ("vbank".equals(payment.getApprovalType())) {
										orderPgData.setReturnBankName(claimApply.getReturnBankName());
										orderPgData.setReturnAccountNo(claimApply.getReturnVirtualNo());
										orderPgData.setReturnName(claimApply.getReturnBankInName());
									}
									orderPgData = easypayService.partCancel(orderPgData);

								} else if ("nicepay".equals(orderPgData.getPgServiceType())) {
									if ("vbank".equals(payment.getApprovalType())) {
										orderPgData.setReturnBankName(claimApply.getReturnBankName());
										orderPgData.setReturnAccountNo(claimApply.getReturnVirtualNo());
										orderPgData.setReturnName(claimApply.getReturnBankInName());
									}
									if (!" ".equals(claimApply.getClaimReasonDetail())) {
										orderPgData.setCancelReason(claimApply.getClaimReasonDetail());
									} else {
										orderPgData.setCancelReason(claimApply.getClaimReasonText());
									}
									orderPgData.setRequest(claimApply.getRequest());
									orderPgData.setResponse(claimApply.getResponse());

									orderPgData = nicepayService.partCancel(orderPgData);

                                } else if ("naverpay".equals(orderPgData.getPgServiceType())) {
                                    if (!" ".equals(claimApply.getClaimReasonDetail())) {
                                        orderPgData.setCancelReason(claimApply.getClaimReasonDetail());
                                    } else {
                                        orderPgData.setCancelReason(claimApply.getClaimReasonText());
                                    }
                                    orderPgData.setPartCancelDetail("PART_CANCEL");
                                    orderPgData = naverPaymentApi.cancel(orderPgData, configPg);
                                }

								isSuccess = orderPgData.isSuccess();

							}

							if (isSuccess) {

								payment.setRemainingAmount(orderPgData.getRemainAmount());
								payment.setCancelAmount(payment.getCancelAmount() + orderPgData.getCancelAmount());

                                if (("inicis".equals(orderPgData.getPgServiceType())
                                        || "kcp".equals(orderPgData.getPgServiceType())
                                        || "nicepay".equals(orderPgData.getPgServiceType())
                                        || "naverpay".equals(orderPgData.getPgServiceType()))
                                        && isPartCancel) {
                                    orderMapper.updateOrderPgData(orderPgData);
                                }

								orderPaymentMapper.updateOrderPaymentForCancel(payment);

								OrderPayment orderPayment = new OrderPayment(order, payment.getApprovalType());
								orderPayment.setPaymentType("2");
								orderPayment.setCancelAmount(orderPgData.getCancelAmount());
								orderPayment.setPayDate(DateUtils.getToday(Const.DATETIME_FORMAT));
								orderPayment.setNowPaymentFlag("Y");

								orderPaymentMapper.insertOrderPayment(orderPayment);

								// 현금영수증 취소,재발급
								if ("realtimebank".equals(payment.getApprovalType()) || "vbank".equals(payment.getApprovalType())) {
									receiptService.cashbillReIssue(orderParam);
								}

								// 즉시 취소 시 ERP 연동
								erpService.saveOrderListGetAutoCancel(orderLines);

							} else {
								String errorMessage = "PG 취소 연동 에러";

								if (!StringUtils.isEmpty(orderPgData.getErrorMessage())) {
									errorMessage = orderPgData.getErrorMessage();
								}

								log.error("주문 취소 오류 PG사 응답 - (code:{}, message:{})", orderPgData.getPgAuthCode(), orderPgData.getErrorMessage());
								throw new OrderException(errorMessage, orderCode, orderSequence);
							}

							returnAmount = returnAmount - cancelAmount;
						}
					}

					if (returnAmount <= 0) {
						break;
					}
				}
			} else {

				// ERP 연동을 위한 items 세팅
				setErpOrderItems(order);

				// 환불금액이 남지 않고, 포인트만 남았을 경우에도 ERP 연동
				ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
				erpService.saveOrderListGet(erpOrder);
			}

			// 쿠폰 반환
			if (!returnCoupons.isEmpty()) {
				for (Integer couponUserId : returnCoupons) {

					if (order.getUserId() > 0) {
						OrderCoupon orderCoupon = new OrderCoupon();
						orderCoupon.setUserId(order.getUserId());
						orderCoupon.setCouponUserId(couponUserId);
						couponMapper.updateCouponUserReturnsByOrderCouponUser(orderCoupon);
					}

				}
			}

			// 재고량 복원
			orderService.stockRestoration(stockMap);

		} else {

			// ERP 연동을 위한 items 세팅
			setErpOrderItems(order);

			// 취소 신청 시  ERP 연동
			ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
			erpService.saveOrderListGet(erpOrder);
		}

		// 주문 로그
		OrderItem logOrderItem = new OrderItem();
		for(OrderRefundDetail group : orderRefund.getGroups()) {
			for (OrderCancelApply orderCancelApply : group.getOrderCancelApplys()) {
				logOrderItem = orderCancelApply.getOrderItem();

				orderService.insertOrderLog(
						OrderLogType.CLAIM_CANCEL,
						orderCancelApply.getOrderCode(),
						orderCancelApply.getOrderSequence(),
						logOrderItem.getItemSequence(),
						logOrderItem.getOrderStatus()
				);

			}
		}
	}

	/**
	 * 포인트 환불
	 * @param order
	 * @param payment
	 * @param amount
	 */
	private void returnPoint(Order order, OrderPayment payment, int amount) {
		Point point = new Point();
		point.setUserId(order.getUserId());
		point.setOrderCode(order.getOrderCode());
		point.setOrderSequence(order.getOrderSequence());
		point.setPoint(amount);
		point.setPointType(payment.getApprovalType());
		point.setReason(MessageUtils.getMessage("M00246") + " 환불 - 주문취소["+ order.getOrderCode() +"]");
		
		// 포인트 반환
		pointService.earnPoint("return", point);
		
		payment.setCancelAmount(amount);
		payment.setRemainingAmount(payment.getRemainingAmount()  - amount);
		
		orderPaymentMapper.updateOrderPaymentForCancel(payment);
		
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setPaymentType("2");
		orderPayment.setOrderCode(order.getOrderCode());
		orderPayment.setOrderSequence(order.getOrderSequence());
		orderPayment.setApprovalType(payment.getApprovalType());
		orderPayment.setCancelAmount(amount);
		orderPayment.setPayDate(DateUtils.getToday(Const.DATETIME_FORMAT));
		orderPayment.setDeviceType("WEB");
		
		if (ShopUtils.isMobilePage()) {
			orderPayment.setDeviceType("MOBILE");
		}
		
		orderPayment.setNowPaymentFlag("Y");
		orderPaymentMapper.insertOrderPayment(orderPayment);
	}
	
	/**
	 * 배송비 재계산
	 * @param shippings
	 */
	private void resetPayShippingAmount(List<OrderShipping> shippings) {

		if (shippings == null) {
			return;
		}
		
		for(OrderShipping shipping : shippings) {
			
			if (shipping.getOrderItems() == null) {
				continue;
			}
			
			if (!"Y".equals(shipping.getRePayShipping())) {
				continue;
			}

			int addDeliveryCharge = 0;
			if ("JEJU".equals(shipping.getIslandType())) {
				addDeliveryCharge = shipping.getShippingExtraCharge1();
			} else if ("ISLAND".equals(shipping.getIslandType())) {
				addDeliveryCharge = shipping.getShippingExtraCharge2();
			}
			
			int realShipping = 0;
			if ("1".equals(shipping.getShippingType())) { // 무료 배송
				
				realShipping = addDeliveryCharge;
				
				
			} else if ("2".equals(shipping.getShippingType()) || "3".equals(shipping.getShippingType())) { // 2 : 판매자 조건부, 3 : 출고지 조건부, 
				
				int totalItemAmount = 0;
				if ("3".equals(shipping.getShippingType())) {
					
					if (StringUtils.isEmpty(shipping.getShipmentGroupCode())) {
						
						for(OrderItem buyItem : shipping.getOrderItems()) {
							totalItemAmount += buyItem.getSaleAmount();
						}
						
					} else {
						
						List<OrderItem> list = new ArrayList<>();
						for(OrderShipping tShipping : shippings) {	
							if (ValidationUtils.isEmpty(tShipping.getShipmentGroupCode()) == false) {							
								if (tShipping.getShipmentGroupCode().equals(shipping.getShipmentGroupCode()) && tShipping.getOrderItems() != null) {
									list.addAll(tShipping.getOrderItems());
								}
							}
						}
						
						// 통합 물류 배송으로 조건부 배송 조건 금액을 물류 배송 상품 전체로 한다.
						for(OrderItem buyItem : list) {
							
							if (StringUtils.isEmpty(buyItem.getShipmentGroupCode())) {
								continue;
							}
							
							if (shipping.getShipmentGroupCode().equals(buyItem.getShipmentGroupCode())) {
								totalItemAmount += buyItem.getSaleAmount();
							}
						}
					}
				} else {
					for(OrderItem buyItem : shipping.getOrderItems()) {
						totalItemAmount += buyItem.getSaleAmount();
					}
				}
				
				//System.out.println(totalItemAmount);
				
				if (shipping.getShippingFreeAmount() <= totalItemAmount) {
					realShipping = addDeliveryCharge;
				} else {
					realShipping = shipping.getShipping() + addDeliveryCharge;
				}
				
			} else if ("4".equals(shipping.getShippingType())) { // 상품 조건부
				
				int totalItemAmount = 0;
				for(OrderItem buyItem : shipping.getOrderItems()) {
					totalItemAmount += buyItem.getSaleAmount();
				}
				
				if (shipping.getShippingFreeAmount() <= totalItemAmount) {
					realShipping = addDeliveryCharge;
				} else {
					realShipping = shipping.getShipping() + addDeliveryCharge;
				}
				
			} else if ("5".equals(shipping.getShippingType())) { // 개당배송비 - BOX 당 배송비
				
				int totalItemQuantity = 0;
				for(OrderItem buyItem : shipping.getOrderItems()) {
					totalItemQuantity += buyItem.getQuantity();
				}
				
				if (shipping.getShippingItemCount() == 0) {
					shipping.setShippingItemCount(1);
				}
				
				int boxCount = (int) Math.ceil((float) totalItemQuantity / shipping.getShippingItemCount());
				realShipping = (shipping.getShipping() + addDeliveryCharge) * boxCount;
				
			} else { // 고정 배송비
				realShipping = shipping.getShipping() + addDeliveryCharge;
			}
			
			shipping.setRealShipping(realShipping);
			shipping.setRePayShippingAmount(realShipping);
			
			// 착불인경우 사용자 배송비 금액을 0으로 잡는다
			if ("2".equals(shipping.getShippingPaymentType())) {
				shipping.setRePayShippingAmount(0);
			}
		}
		
		for(OrderShipping orderShipping : shippings) {
			
			if (!"Y".equals(orderShipping.getRePayShipping())) {
				continue;
			}
			
			int addPayAmount = 0;
			String addPaymentType = "";
			
			/**
			 * 배송정책에 상품이 안담겨있으면 배송비를 환불해줘야함..
			 */
			if (orderShipping.getOrderItems() == null) {
				if (orderShipping.getPayShipping() == 0) {
					continue;
				}
				
				addPayAmount = orderShipping.getPayShipping();
				addPaymentType = "2";
				
				orderShipping.setRePayShippingAmount(0);
			} else {
			
				if (orderShipping.getRePayShippingAmount() == orderShipping.getPayShipping()) {
					continue;
				}

				addPayAmount = orderShipping.getRePayShippingAmount() - orderShipping.getPayShipping();
				
				// 배송비 추가 : 1, 배송비 환불 : 2 
				addPaymentType = orderShipping.getRePayShippingAmount() > orderShipping.getPayShipping() ? "1" : "2";

				if (addPayAmount < 0) {
					addPayAmount = -addPayAmount;
				}
			}
			
			orderShipping.setAddPayAmount(addPayAmount);
			orderShipping.setAddPaymentType(addPaymentType);
		}
	}
	
	@Override
	public void orderCancelProcess(ClaimApply claimApply) {
		
		if (claimApply.getCancelIds() == null) {
			return;
		}
		
		boolean rePayShipping = false;
		int currentItemCount [] = {0,0};

		for(String claimCode : claimApply.getCancelIds()) {
			OrderCancelApply cancelApply = claimApply.getCancelApplyMap().get(claimCode);
			if (cancelApply == null) {
				throw new OrderException();
			}

			String claimStatus = cancelApply.getClaimStatus();
			if ("03".equals(claimStatus)) {

			    currentItemCount[cancelApply.getShippingSequence()] ++;

				for(OrderCancelShipping orderCancelShipping : claimApply.getCancelShippingMap().values()) {
                    int addPaymentCount = orderAddPaymentMapper.getOrderAddPaymentCount(cancelApply);
                    int leftItem = orderMapper.getOrderItemCountForCancel(cancelApply);

                    if (addPaymentCount > 0 && leftItem > currentItemCount[cancelApply.getShippingSequence()]) {
                        rePayShipping = false;
                        break;
                    }

					// 배송비 재계산??
					if ("Y".equals(orderCancelShipping.getRePayShipping())) {
						rePayShipping = true;
						break;
						
					}
					
				}
				
			}

			if (rePayShipping) {
				break;
			}
		}
		
			
		if (rePayShipping == true) {
			
			List<OrderShipping> orderShippings = getReShippingAmountForManager(claimApply);
			
			/**
			 * CJH 2016.08.20
			 * 주문 취소는 무조건!! 정산일자가 확정되기 전에 일어나는일이라고 가정하고 작성한다..
			 * 배송비 변경건은 OP_ORDER_SHIPPING에 정산 금액을 조절하고 OP_ADD_PAYMENT 테이블에는 환불 금액관련 정보를 기록하고 사용한다.
			 */
			if (orderShippings != null) {
				for(OrderShipping orderShipping : orderShippings) {

					if (orderShipping.getAddPayAmount() == 0) {

						if ("2".equals(orderShipping.getShippingPaymentType())) {
							orderShipping.setPayShipping(orderShipping.getRePayShippingAmount());
							orderShipping.setRemittanceAmount(orderShipping.getRePayShippingAmount());
							orderShipping.setReturnShipping(orderShipping.getReturnShipping() + orderShipping.getAddPayAmount());

							orderShippingMapper.updateCancelShipping(orderShipping);
						}

					} else {

						OrderAddPayment orderAddPayment = new OrderAddPayment(orderShipping);
						orderAddPayment.setAddPaymentType(orderShipping.getAddPaymentType());
						orderAddPayment.setAmount(orderShipping.getAddPayAmount());
						orderAddPayment.setRefundCode(claimApply.getRefundCode());
						orderAddPayment.setSalesDate(DateUtils.getToday());
						orderAddPayment.setSubject("주문 취소로 인한 배송비 변경");
						orderAddPayment.setIssueCode("CANCEL-ADD-SHIPPING-" + orderShipping.getShippingSequence());
						orderAddPaymentService.insertOrderAddPayment(orderAddPayment);

                        if ("1".equals(orderShipping.getAddPaymentType())) {
                            orderShipping.setPayShipping(orderShipping.getRePayShippingAmount());
                            orderShipping.setRemittanceAmount(orderShipping.getRePayShippingAmount());
                            orderShipping.setRealShipping(orderShipping.getRePayShippingAmount());
                        } else if ("2".equals(orderShipping.getAddPaymentType())) {
                            orderShipping.setPayShipping(orderShipping.getRePayShippingAmount());
                            orderShipping.setRemittanceAmount(orderShipping.getRePayShippingAmount());
                            orderShipping.setReturnShipping(orderShipping.getReturnShipping() + orderShipping.getAddPayAmount());
                            orderShipping.setRealShipping(orderShipping.getRePayShippingAmount());
                        }

                        orderShippingMapper.updateCancelShipping(orderShipping);
					}
				}
			}
		}
		
		for(String key : claimApply.getCancelIds()) {
			
			OrderCancelApply orderCancelApply = claimApply.getCancelApplyMap().get(key);
			if (orderCancelApply == null) {
				throw new OrderException();
			}
			
			orderCancelApply.setClaimCode(key);

			if ("98".equals(orderCancelApply.getClaimStatus())) { // 배송처리

               ShippingParam shippingParam = new ShippingParam(orderCancelApply);
				
				DeliveryCompany deliveryCompany = deliveryCompanyService.getDeliveryCompanyById(orderCancelApply.getDeliveryCompanyId());
				if (deliveryCompany == null) {
					throw new OrderException();
				}
			
				shippingParam.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
				shippingParam.setDeliveryCompanyUrl(deliveryCompany.getDeliveryCompanyUrl());
				shippingParam.setAdminUserName(UserUtils.getManagerName());
				
				shippingParam.setConditionType("OPMANAGER");
				if (ShopUtils.isSellerPage()) {
					shippingParam.setConditionType("SELLER");
					shippingParam.setSellerId(SellerUtils.getSellerId());
				}

                // 주문 로그인해 임시로 주문상품 조회
                OrderItem logOrderItem = orderService.getOrderItemForOrderLog(
                        shippingParam.getOrderCode(),
                        shippingParam.getOrderSequence(),
                        shippingParam.getItemSequence()
                );

				if (orderShippingMapper.updateShippingStart(shippingParam) == 0) {
					throw new OrderException();
				}
				
				orderCancelApply.setClaimStatus("99");

				// 주문 로그
				try {
                    orderService.insertOrderLog(
                            OrderLogType.CLAIM_CANCEL,
                            shippingParam.getOrderCode(),
                            shippingParam.getOrderSequence(),
                            shippingParam.getItemSequence(),
                            logOrderItem.getOrderStatus()
                    );
                } catch (Exception e) {
				    log.error("ERROR: {}", e.getMessage(), e);
                }
			}	
			
			// 환불 승인 대기이면 환불 코드를 등록
			if ("03".equals(orderCancelApply.getClaimStatus())) {
				orderCancelApply.setRefundCode(claimApply.getRefundCode());
			}
			
			int count = orderClaimApplyMapper.updateOrderCancelApply(orderCancelApply);
			if (("99".equals(orderCancelApply.getClaimStatus())) && count > 0) {

                // 주문 로그인해 임시로 주문상품 조회
                OrderItem logOrderItem = orderService.getOrderItemForOrderLog(
                        orderCancelApply.getOrderCode(),
                        orderCancelApply.getOrderSequence(),
                        orderCancelApply.getItemSequence()
                );

				orderClaimApplyMapper.updateClaimQuantityForCancel(orderCancelApply);

                // 주문 로그
                try {
                    orderService.insertOrderLog(
                            OrderLogType.CLAIM_CANCEL,
                            orderCancelApply.getOrderCode(),
                            orderCancelApply.getOrderSequence(),
                            orderCancelApply.getItemSequence(),
                            logOrderItem.getOrderStatus()
                    );
                } catch (Exception e) {
                    log.error("ERROR: {}", e.getMessage(), e);
                }
			} else if (("03".equals(orderCancelApply.getClaimStatus())) && count > 0) {
                orderClaimApplyMapper.updateClaimQuantityForCancelApply(orderCancelApply);
            }
		}
		
 	}

	@Override
	public void orderReturnSaveProcess(ClaimApply claimApply) {
		
		String[] returnIds = claimApply.getReturnIds();
		if (returnIds == null) {
			return;
		}
		
		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}
		orderParam.setOrderCode(claimApply.getOrderCode());
		orderParam.setOrderSequence(claimApply.getOrderSequence());
		Order order = orderMapper.getOrderByParam(orderParam);

		if (order == null) {
			throw new OrderException();
		}
		
		List<OrderItem> itemList = new ArrayList<>();
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem item : info.getOrderItems()) {
				itemList.add(item);
			}
		}
		
		List<OrderReturnApply> newApplyList = new ArrayList<>();
		long shipmentReturnSellerId = SellerUtils.getSellerId();
		for(String claimCode : returnIds) {
			
			OrderReturnApply returnApply = claimApply.getReturnApplyMap().get(claimCode);
			if (returnApply == null) {
				throw new OrderException();
			}
			
			boolean hasItem = false;
			for(OrderItem item : itemList) {
				
				if (returnApply.getItemSequence() == item.getItemSequence()) {
					
					if (shipmentReturnSellerId != returnApply.getShipmentReturnSellerId()) {
						throw new OrderException("[처리권한 없음] 반품 처리할수 없는 타입의 주문을 처리시도 하였습니다.");
					}
					
					if (returnApply.getClaimCode().equals(claimCode)) {
						
						if (!"99".equals(returnApply.getClaimStatus())) {
							item.setQuantity(item.getQuantity() - returnApply.getClaimApplyQuantity());
						}
						
						returnApply.setOrderItem(item);
						newApplyList.add(returnApply);
						
						hasItem = true;
						break;
					}
				}
				
			}
			
			if (hasItem == false) {
				throw new OrderException(claimCode + "정보 없음");
			}
			
		}
		
		if (newApplyList.isEmpty()) {
			throw new OrderException();
		}
		
		for(OrderReturnApply apply : newApplyList) {
			if ("03".equals(apply.getClaimStatus())) {
				throw new ClaimException("1000");
			}
		}	
		
		for(OrderReturnApply apply : newApplyList) {
			int count = orderClaimApplyMapper.updateOrderReturnApply(apply);
			if (count == 0) {
				throw new OrderException();
			}
			
			if ("99".equals(apply.getClaimStatus())) {
				orderClaimApplyMapper.updateClaimQuantityForReturn(apply);

				try {
					orderService.insertOrderLog(
							OrderLogType.CLAIM_RETURN,
							apply.getOrderCode(),
							apply.getOrderSequence(),
							apply.getItemSequence(),
							apply.getOrderItem().getOrderStatus()
					);
				} catch (Exception e) {
					log.error("ERROR: {}", e.getMessage(), e);
				}
			}

		}
	}
	
	@Override
	public List<OrderAddPayment> orderReturnViewData(ClaimApply claimApply, Order order) {
		
		List<OrderItem> itemList = new ArrayList<>();

		long shipmentReturnSellerId = SellerUtils.getSellerId();

		/* 선택 된 상품정보에 해당되는 판매자(sellerId) 설정 *
		 * 관리자가 반품처리 시 관리자의 ID(sellerId)로 정산처리가 되는 현상을 막기위함
		 * 판매자인 경우는 자기가 판매하는 상품이기 때문에 sellerId는 동일하며, 관리자인 경우 서버단으로 넘어올 때 서로 다른 판매자의 상품들은 넘길 수 없도록 validation 체크를 하고 있기 때문에
		 * 넘어온 상품 중 하나의 ID만 꺼내서 설정해 주면 됨
		 * */
		if(claimApply.getReturnIds() != null && claimApply.getReturnIds().length > 0){
			shipmentReturnSellerId = claimApply.getReturnApplyMap().get(claimApply.getReturnIds()[0]).getShipmentReturnSellerId();
		}

		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem item : info.getOrderItems()) {
				itemList.add(item);
			}
		}
		
		List<OrderAddPayment> orderAddPayments = new ArrayList<>();
		List<OrderReturnApply> newApplyList = new ArrayList<>();
		for(String claimCode : claimApply.getReturnIds()) {
			
			OrderReturnApply returnApply = claimApply.getReturnApplyMap().get(claimCode);
			if (returnApply == null) {
				throw new OrderException();
			}
			
			boolean hasItem = false;
			for(OrderItem item : itemList) {
				
				if (returnApply.getItemSequence() == item.getItemSequence()) {
					
					if (shipmentReturnSellerId != returnApply.getShipmentReturnSellerId()) {
						throw new OrderException("[처리권한 없음] 반품 처리할수 없는 타입의 주문을 처리시도 하였습니다.");
					}
					
					if (returnApply.getClaimCode().equals(claimCode)) {
						
						if (!"99".equals(returnApply.getClaimStatus())) {
							item.setQuantity(item.getQuantity() - returnApply.getClaimApplyQuantity());
						}
						
						returnApply.setOrderItem(item);
						returnApply.setShippingSequence(item.getShippingSequence());
						returnApply.setUserId(item.getUserId());
						newApplyList.add(returnApply);
						
						hasItem = true;
						break;
					}
				}
				
			}
			
			if (hasItem == false) {
				throw new OrderException(claimCode + "정보 없음");
			}
			
		}
		
		if (newApplyList.isEmpty()) {
			throw new OrderException();
		}
		
		int collectionShippingAmount = 0; // 회수비용
        int initShippingAmount = 0; // 초기 배송비
		
		for(OrderReturnApply apply : newApplyList) {
			
			// 고객 사유
			if ("2".equals(apply.getReturnReason())) {
				collectionShippingAmount += apply.getCollectionShippingAmount();

                // 2020.08.08 juneu.son 클레임 상품의 배송타입이 상품,출고지 조건부 일 경우
                // 클레임 처리 후 남은 잔여금액이 배송비 무료 조건을 충족시키지 못하면 초기 배송비 발생
                if ("2".equals(apply.getOrderItem().getOrderShipping().getShippingType())
                        || "3".equals(apply.getOrderItem().getOrderShipping().getShippingType())) {
                    int remainingAmount = 0;
                    int claimAmount = apply.getOrderItem().getClaimQuantity() * apply.getOrderItem().getSalePrice();

                    for (OrderItem item : order.getOrderShippingInfos().get(0).getOrderItems()) {
                        if (item.getItemId() == apply.getOrderItem().getItemId() && "N".equals(item.getCancelFlag())) {
                            remainingAmount += item.getSaleAmount();
                        }
                    }

                    if (remainingAmount - claimAmount  < apply.getOrderItem().getOrderShipping().getShippingFreeAmount()) {
                        initShippingAmount += apply.getOrderItem().getOrderShipping().getShipping();
                    }
                }
			}
		}
		
		
		// 고객사유가 있는경우
		int defaultShippingAmount = 0;
		int customersReasonCount = 0;
		
		// 판매자사유인 경우
		int sellerReasonCount = 0;
		
		List<String> deliveryNumbers = new ArrayList<>();
		for(OrderReturnApply apply : newApplyList) {
			
			OrderItem orderItem = apply.getOrderItem();
			
			if ("03".equals(apply.getClaimStatus()) && "2".equals(apply.getReturnReason())) {
				customersReasonCount++;
			}
			
			if ("03".equals(apply.getClaimStatus()) && "1".equals(apply.getReturnReason())) {
				sellerReasonCount++;
				
				String deliveryNumber = orderItem.getDeliveryNumber();
				
				boolean isNew = true;
				for(String s : deliveryNumbers) {
					if (s.equals(deliveryNumber)) {
						isNew = false;
					}
				}
				
				if (isNew) {
					if (StringUtils.isEmpty(deliveryNumber) == false) {
						deliveryNumbers.add(deliveryNumber);
					}
				}
			}
			
			defaultShippingAmount = (defaultShippingAmount == 0 || orderItem.getShippingReturn() > defaultShippingAmount) ? orderItem.getShippingReturn() : defaultShippingAmount;
		}
		
		//판매자 사유인 경우
		if(sellerReasonCount > 0) {
			
			HashMap<Integer, OrderShipping> returnShippingMap = new HashMap<Integer, OrderShipping>();
			for(OrderReturnApply apply : newApplyList) {
				
				OrderItem orderItem = apply.getOrderItem();
				OrderShipping orderShipping = orderItem.getOrderShipping();
				if (returnShippingMap.get(orderShipping.getShippingSequence()) != null) {
					continue;
				}
				
				if ("03".equals(apply.getClaimStatus()) && "1".equals(apply.getReturnReason())) {
				
					if (!"1".equals(orderShipping.getShippingType()) && orderShipping.getPayShipping() > 0) {
						
						OrderAddPayment orderAddPayment = new OrderAddPayment();
						orderAddPayment.setOrderCode(claimApply.getOrderCode());
						orderAddPayment.setOrderSequence(claimApply.getOrderSequence());
						orderAddPayment.setAddPaymentType("2");
						orderAddPayment.setAmount(orderShipping.getPayShipping());
						orderAddPayment.setRefundCode(claimApply.getRefundCode());
						orderAddPayment.setSellerId(orderShipping.getSellerId());
						orderAddPayment.setSalesDate(DateUtils.getToday());
						orderAddPayment.setSalesDate(DateUtils.getToday());
						orderAddPayment.setSubject("배송비 환불");
						
						orderAddPayment.setIssueCode("RETURN-SHIPPING-" + orderShipping.getShippingSequence());
						
						if (orderAddPaymentService.getDuplicateRegistrationCheckByIssueCode(orderAddPayment) == 0) {
							
							// CJH 2016.12.29 같은 배송정책 상품중 취소 처리 되지 않은 상품이 모두 클레임 처리되었을때만 환불..
							OrderParam orderParam = new OrderParam();
							orderParam.setOrderCode(orderItem.getOrderCode());
							orderParam.setItemSequence(orderItem.getItemSequence());
							orderParam.setShippingSequence(orderItem.getShippingSequence());
							List<OrderItem> items  = orderShippingMapper.getOrderItemListByShippingParam(orderParam);
							
							int applyCount = 0;
							for(OrderReturnApply checkApply : newApplyList) {
								if ("03".equals(checkApply.getClaimStatus()) && checkApply.getShippingSequence() == orderShipping.getShippingSequence()) {
									applyCount++;
								}
							} 
						
							boolean isReturnPayShipping = true;
							if (items != null) {
								isReturnPayShipping = (items.size() == applyCount) ? true : false;
							}
							
							if (isReturnPayShipping) {
								orderAddPayments.add(orderAddPayment);
								returnShippingMap.put(orderShipping.getShippingSequence(), orderShipping);
							}
							
						}
					}
				}
			}
		}
		
		// 환불 승인건이 있는경우 추가 배송비등의 정보를 OP_ORDER_ADD_PAYMENT 테이블에 기록
		if (customersReasonCount > 0) {
			
			// 회수 비용
			if (collectionShippingAmount > 0) {
				
				OrderAddPayment orderAddPayment = new OrderAddPayment();
				
				orderAddPayment.setOrderCode(claimApply.getOrderCode());
				orderAddPayment.setOrderSequence(claimApply.getOrderSequence());
				orderAddPayment.setSellerId(shipmentReturnSellerId);
				orderAddPayment.setAddPaymentType("1");
				orderAddPayment.setAmount(collectionShippingAmount);
				orderAddPayment.setRefundCode(claimApply.getRefundCode());
				orderAddPayment.setSalesDate(DateUtils.getToday());
				orderAddPayment.setSubject("회수비용");
				orderAddPayment.setIssueCode("COLLECTION-SHIPPING-" + shipmentReturnSellerId + "-" + claimApply.getOrderCode());
				orderAddPayments.add(orderAddPayment);
			}

			// 최초 배송비
            if (initShippingAmount > 0) {
                OrderAddPayment orderAddPayment = new OrderAddPayment();

                orderAddPayment.setOrderCode(claimApply.getOrderCode());
                orderAddPayment.setOrderSequence(claimApply.getOrderSequence());
                orderAddPayment.setAddPaymentType("1");
                orderAddPayment.setAmount(initShippingAmount);
                orderAddPayment.setRefundCode(claimApply.getRefundCode());
                orderAddPayment.setSellerId(shipmentReturnSellerId);
                orderAddPayment.setSalesDate(DateUtils.getToday());
                orderAddPayment.setSalesDate(DateUtils.getToday());
                orderAddPayment.setSubject("최초 배송비");

                orderAddPayment.setIssueCode("INIT-SHIPPING-" + shipmentReturnSellerId + "-" + claimApply.getOrderCode());

                if (orderAddPaymentService.getDuplicateRegistrationCheckByIssueCode(orderAddPayment) == 0) {
                    orderAddPayments.add(orderAddPayment);
                }
            }
			
			for(String deliveryNumber : deliveryNumbers) {
				
				boolean isFreeShipping = true;
				long shipmentSellerId = 0; // 무료 발송 배송비는 배송 주체가 배송비를 정산받음
				for(OrderItem item : itemList) {
					
					if (deliveryNumber.equals(item.getDeliveryNumber())) {
						
						OrderShipping orderShipping = item.getOrderShipping();
						shipmentSellerId = orderShipping.getSellerId();
						if (orderShipping.getPayShipping() > 0 || "2".equals(orderShipping.getShippingPaymentType())) {
							isFreeShipping = false;
						}
						
					}
					
				}
				
				if (isFreeShipping) {
					OrderAddPayment orderAddPayment = new OrderAddPayment();
					
					orderAddPayment.setOrderCode(claimApply.getOrderCode());
					orderAddPayment.setOrderSequence(claimApply.getOrderSequence());
					orderAddPayment.setAddPaymentType("1");
					orderAddPayment.setAmount(defaultShippingAmount);
					orderAddPayment.setRefundCode(claimApply.getRefundCode());
					orderAddPayment.setSellerId(shipmentSellerId);
					orderAddPayment.setSalesDate(DateUtils.getToday());
					orderAddPayment.setSalesDate(DateUtils.getToday());
					orderAddPayment.setSubject("송장번호["+ deliveryNumber +"] 무료발송 배송비");
					
					orderAddPayment.setIssueCode("FREE-SHIPPING-" + deliveryNumber);
					
					if (orderAddPaymentService.getDuplicateRegistrationCheckByIssueCode(orderAddPayment) == 0) {
						orderAddPayments.add(orderAddPayment);
					}
				}
			}
			
			
			// 잔여상품 배송비
			HashMap<Integer, OrderShipping> returnShippingMap = new HashMap<Integer, OrderShipping>();
			for(OrderItem item : itemList) {
				
				
				if ("0".equals(item.getOrderStatus()) || "10".equals(item.getOrderStatus())) {
					
					OrderShipping orderShipping = item.getOrderShipping();

					// 착불이 아니고 배송비가 없으며 조건부 인경우
					if (orderShipping.getPayShipping() == 0 && !"2".equals(orderShipping.getShippingPaymentType()) 
							&& ("2".equals(orderShipping.getShippingType()) || "3".equals(orderShipping.getShippingType()))) {
						
						returnShippingMap.put(orderShipping.getShippingSequence(), orderShipping);
					}
					
				}
				
			}
			
			
			for(int shippingSequence : returnShippingMap.keySet()) {
				
				OrderShipping shipping = returnShippingMap.get(shippingSequence);
				
				int notSendTotalItemAmount = 0;
				for(OrderItem item : itemList) {
					
					if ("0".equals(item.getOrderStatus()) || "10".equals(item.getOrderStatus())) {
						OrderShipping orderShipping = item.getOrderShipping();
						
						if (shippingSequence == orderShipping.getShippingSequence()) {
							notSendTotalItemAmount += item.getSaleAmount();//총액
						}
					}
				}
				
				if (notSendTotalItemAmount > 0 && shipping.getShipping() > 0) {
					if (notSendTotalItemAmount < shipping.getShippingFreeAmount()) {
						
						OrderAddPayment orderAddPayment = new OrderAddPayment();
						
						orderAddPayment.setOrderCode(claimApply.getOrderCode());
						orderAddPayment.setOrderSequence(claimApply.getOrderSequence());
						orderAddPayment.setAddPaymentType("1");
						orderAddPayment.setAmount(shipping.getShipping());
						orderAddPayment.setRefundCode(claimApply.getRefundCode());
						orderAddPayment.setSellerId(shipping.getSellerId());
						orderAddPayment.setSalesDate(DateUtils.getToday());
						orderAddPayment.setSalesDate(DateUtils.getToday());
						orderAddPayment.setSubject("잔여상품 배송비 - 조건부 무료배송[" + StringUtils.numberFormat(shipping.getShippingFreeAmount()) + "원 이상 무료]");
						
						orderAddPayment.setIssueCode("NOSEND-ITEM-" + shipping.getShippingSequence());
						
						if (orderAddPaymentService.getDuplicateRegistrationCheckByIssueCode(orderAddPayment) == 0) {
							orderAddPayments.add(orderAddPayment);
						}
					}
				}
			}
		}
		
		return orderAddPayments;
	}
	
	@Override
	public void orderReturnProcess(ClaimApply claimApply) {
		
		String[] returnIds = claimApply.getReturnIds();
		if (returnIds == null) {
			return;
		}
		
		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}
		orderParam.setOrderCode(claimApply.getOrderCode());
		orderParam.setOrderSequence(claimApply.getOrderSequence());
		Order order = orderMapper.getOrderByParam(orderParam);

		if (order == null) {
			throw new ClaimException();
		}
		
		List<OrderItem> itemList = new ArrayList<>();
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem item : info.getOrderItems()) {
				itemList.add(item);
			}
		}
		
		List<OrderReturnApply> newApplyList = new ArrayList<>();
		
		long shipmentReturnSellerId = SellerUtils.getSellerId();
		for(String claimCode : returnIds) {
			
			OrderReturnApply returnApply = claimApply.getReturnApplyMap().get(claimCode);
			if (returnApply == null) {
				throw new ClaimException();
			}
			
			boolean hasItem = false;
			for(OrderItem item : itemList) {
				
				if (returnApply.getItemSequence() == item.getItemSequence()) {
					
					if (shipmentReturnSellerId != returnApply.getShipmentReturnSellerId() && !ShopUtils.isOpmanagerPage()) {
						throw new OrderException("[처리권한 없음] 반품 처리할수 없는 타입의 주문을 처리시도 하였습니다.");
					}
					
					if (returnApply.getClaimCode().equals(claimCode)) {
						
						if (!"99".equals(returnApply.getClaimStatus())) {
							item.setQuantity(item.getQuantity() - returnApply.getClaimApplyQuantity());
						}
						
						returnApply.setOrderItem(item);
						newApplyList.add(returnApply);
						
						hasItem = true;
						break;
					}
				}
				
			}
			
			if (hasItem == false) {
				throw new OrderException(claimCode + "정보 없음");
			}
			
		}
		
		if (newApplyList.isEmpty()) {
			throw new ClaimException();
		}
		
		
		for(OrderReturnApply apply : newApplyList) {
			
			// 승인일경우 환불 코드를 입력
			if ("03".equals(apply.getClaimStatus())) {
				apply.setRefundCode(claimApply.getRefundCode());
			}
			
			int count = orderClaimApplyMapper.updateOrderReturnApply(apply);
			if (count == 0) {
				throw new ClaimException();
			}
			
			if ("99".equals(apply.getClaimStatus())) {
				orderClaimApplyMapper.updateClaimQuantityForReturn(apply);
			} else if ("03".equals(apply.getClaimStatus())) {
				orderClaimApplyMapper.updateClaimQuantityForReturnApply(apply);
			}

			// 주문로그 추가
			if ("99".equals(apply.getClaimStatus()) || ("03".equals(apply.getClaimStatus()))) {
                try {
                    orderService.insertOrderLog(
                            OrderLogType.CLAIM_RETURN,
                            apply.getOrderCode(),
                            apply.getOrderSequence(),
                            apply.getItemSequence(),
                            apply.getOrderItem().getOrderStatus()
                    );
                } catch (Exception e) {
                    log.error("ERROR: {}", e.getMessage(), e);
                }
            }
		}
		
		// 추가금 별도 청구가 아니면
		if (!"1".equals(claimApply.getSeparateCharges())) { 
			if (claimApply.getAddPayments() != null) {
				for(OrderAddPayment addPayment : claimApply.getAddPayments()) {

					if (addPayment.getAmount() <= 0) {
						continue;
					}
					
					addPayment.setOrderCode(claimApply.getOrderCode());
					addPayment.setOrderSequence(claimApply.getOrderSequence());
					addPayment.setRefundCode(claimApply.getRefundCode());
					addPayment.setSalesDate(DateUtils.getToday());
					addPayment.setSalesDate(DateUtils.getToday());
					addPayment.setRemittanceAmount(addPayment.getAmount());
					orderAddPaymentService.insertOrderAddPayment(addPayment);
				}
			}
		}
	}
	
	@Override
	public void orderExchangeProcess(ClaimApply claimApply) {
		String[] exchangeIds = claimApply.getExchangeIds();
		if (exchangeIds == null) {
			return;
		}

		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("OPMANAGER");
		orderParam.setOrderCode(claimApply.getOrderCode());
		orderParam.setOrderSequence(claimApply.getOrderSequence());

		Order order = orderService.getOrderByParam(orderParam);

		boolean isErpOrder = false;
		for (String id : exchangeIds) {

			OrderExchangeApply apply = claimApply.getExchangeApplyMap().get(id);
			if (apply == null) {
				throw new OrderException();
			}

			// 배송 처리
			if ("03".equals(apply.getClaimStatus())) {

				if (StringUtils.isEmpty(apply.getExchangeDeliveryNumber()) || apply.getExchangeDeliveryCompanyId() == 0) {
					throw new OrderException();
				}

				ShippingParam shippingParam = new ShippingParam(apply);

				DeliveryCompany deliveryCompany = deliveryCompanyService.getDeliveryCompanyById(apply.getExchangeDeliveryCompanyId());
				if (deliveryCompany == null) {
					throw new OrderException();
				}

				shippingParam.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
				shippingParam.setDeliveryCompanyUrl(deliveryCompany.getDeliveryCompanyUrl());
				shippingParam.setAdminUserName(UserUtils.getManagerName());

				shippingParam.setConditionType("OPMANAGER");
				if (ShopUtils.isSellerPage()) {
					shippingParam.setConditionType("SELLER");
					shippingParam.setSellerId(SellerUtils.getSellerId());
				}

				shippingParam.setMode("EXCHANGE");
				if (orderShippingMapper.updateShippingStart(shippingParam) == 0) {
					throw new OrderException();
				}


			// 회수 완료
			} else if ("11".equals(apply.getClaimStatus())) {

				for (OrderShippingInfo info : order.getOrderShippingInfos()) {
					info.setReceiveName(apply.getExchangeReceiveName());
					info.setReceivePhone(apply.getExchangeReceivePhone());
					info.setReceiveMobile(apply.getExchangeReceiveMobile());
					info.setReceiveAddress(apply.getExchangeReceiveAddress());
					info.setReceiveAddressDetail(apply.getExchangeReceiveAddress2());
					info.setReceiveNewZipcode(apply.getExchangeReceiveZipcode());

					for (OrderItem orderItem : info.getOrderItems()) {

						if (orderItem.getItemSequence() == apply.getItemSequence()) {
							orderItem.setOrderStatus("50");
							orderItem.setExchangeApply(apply);

							isErpOrder = true;
						}
					}
				}

			}

			int count = orderClaimApplyMapper.updateOrderExchangeApply(apply);
			if (count == 0) {
				throw new OrderException();
			}

			// 주문 로그로 인해 임시로 조회
			OrderItem logOrderItem = orderService.getOrderItemForOrderLog(
					apply.getOrderCode(),
					apply.getOrderSequence(),
					apply.getItemSequence()
			);

			if ("99".equals(apply.getClaimStatus())) {
				orderClaimApplyMapper.updateClaimQuantityForExchange(apply);
			}

			// 주문로그 추가
			if ("99".equals(apply.getClaimStatus()) || ("03".equals(apply.getClaimStatus()))) {
				try {
					orderService.insertOrderLog(
							OrderLogType.CLAIM_RETURN,
							apply.getOrderCode(),
							apply.getOrderSequence(),
							apply.getItemSequence(),
							logOrderItem.getOrderStatus()
					);
				} catch (Exception e) {
					log.error("ERROR: {}", e.getMessage(), e);
				}
			}
		}

		if (isErpOrder) {

			// ERP 연동을 위한 items 세팅
			for (OrderShippingInfo info : order.getOrderShippingInfos()) {
				List<OrderItem> erpOrderItems = new ArrayList<>();

				for (OrderItem item : info.getOrderItems()) {
					if (item.getExchangeApply() != null) {
						erpOrderItems.add(item);
					}
				}

				info.setOrderItems(erpOrderItems);
			}

			// ERP 연동
			ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
			erpService.saveOrderListGet(erpOrder);
		}
	}

	@Override
	public void insertOrderExchangeApply(ExchangeApply exchangeApply) {
		
		OrderParam orderParam = new OrderParam(exchangeApply);
		
		if (UserUtils.isUserLogin()) {
			orderParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {
			
		} else {
			throw new PageNotFoundException();
		}
		
		OrderItem orderItem = orderMapper.getOrderItemByParam(orderParam);
		if (orderItem == null) {
			throw new OrderException();
		}
		
		// CJH 2016.11.01 교환 신청시 주문 수량보다 신청수량이 더 많은경우 에러
		if (orderItem.getQuantity() < orderItem.getClaimQuantity() + exchangeApply.getApplyQuantity()) {
			throw new OrderException("교환을 신청하실수 없습니다.");
		}
		
		orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());
		
		// CJH 2016.11.09 회수 배송지역을 사용자 화면에서 넘겨받음
		//OrderExchangeApply apply = new OrderExchangeApply(exchangeApply, orderMapper.getOrderShippingInfoByParam(orderParam));
		OrderExchangeApply apply = new OrderExchangeApply(exchangeApply);
		
		ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
		shipmentReturnParam.setShipmentReturnId(orderItem.getShipmentReturnId());
		ShipmentReturn shipmentReturn = shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
		if (shipmentReturn == null) {
			throw new OrderException();
		}
		
		// 1 : 본사반송, 2 업체 반송
		long sellerId = "2".equals(orderItem.getShipmentReturnType()) ? orderItem.getSellerId() : SellerUtils.DEFAULT_OPMANAGER_SELLER_ID;
		
		apply.setShipmentReturnId(shipmentReturn.getShipmentReturnId());
		apply.setShipmentReturnSellerId(sellerId);

		// 추가구성상품과 묶인 본품이 copyOrderItemForReturnApply 처리로 신규 itemSequence가 생성됐을 수도 있어서 본품의 max itemSequence 가져오기
		if ("Y".equals(orderItem.getAdditionItemFlag())) {
			orderParam.setAdditionItemFlag("N");
			orderParam.setItemId(orderItem.getParentItemId());
			orderParam.setOptions(orderItem.getParentItemOptions());
			orderParam.setOrderStatus("50");
			int parentItemSequence = orderService.getMaxParentOrderItemSequence(orderParam);

			apply.setParentItemSequence(parentItemSequence);
		}

		// 주문 수량과 신청수량이 같으면 기존 주문 변경
		if (orderItem.getQuantity() == exchangeApply.getApplyQuantity()) {
			orderClaimApplyMapper.insertOrderExchangeApply(apply);
			orderClaimApplyMapper.updateClaimQuantityForExchangeApply(apply);
		} else {
			
			orderClaimApplyMapper.copyOrderItemForExchangeApply(apply);
			orderClaimApplyMapper.updateOrderItemQuantityForExchange(apply);

			// 중요!!
			apply.setItemSequence(apply.getCopyItemSequence());
			orderClaimApplyMapper.insertOrderExchangeApply(apply);

		}

		// 주문 로그
		orderService.insertOrderLog(
				OrderLogType.CLAIM_EXCHANGE,
				apply.getOrderCode(),
				apply.getOrderSequence(),
				apply.getItemSequence(),
				orderItem.getOrderStatus()
		);
	}
	
	@Override
	public List<OrderExchangeApply> getExchangeHistoryListByParam(ClaimApplyParam claimApplyParam) {

		claimApplyParam.setAdditionItemFlag("N");
		List<OrderExchangeApply> list = orderClaimApplyMapper.getExchangeHistoryListByParam(claimApplyParam);

		setOrderGiftItemForOrderExchangeApply(list);

		// 추가구성상품
		claimApplyParam.setAdditionItemFlag("Y");
		List<OrderExchangeApply> additionItemList = orderClaimApplyMapper.getExchangeHistoryListByParam(claimApplyParam);
		setOrderAdditionItemForOrderExchangeApply(list, additionItemList);

		return list;
	}
	
	@Override
	public List<OrderReturnApply> getActiveReturnListByParam(ClaimApplyParam claimApplyParam) {
		List<OrderReturnApply> list = orderClaimApplyMapper.getActiveReturnListByParam(claimApplyParam);
		
		if (ValidationUtils.isNull(list) == false) {
			for(OrderReturnApply apply : list) {
				
				OrderParam orderParam = new OrderParam();
				orderParam.setOrderCode(apply.getOrderCode());
				orderParam.setOrderSequence(apply.getOrderSequence());
				
				Order returnInfo = orderMapper.getOrderReturnInfo(orderParam);
				if (ValidationUtils.isNull(returnInfo) == false) {
					apply.setReturnBankName(returnInfo.getReturnBankName());
					apply.setReturnBankInName(returnInfo.getReturnBankInName());
					apply.setReturnVirtualNo(returnInfo.getReturnVirtualNo());
				}
				
				OrderItem orderItem = apply.getOrderItem();
				
				// 상품 환불금액
				apply.setClaimApplyAmount(orderItem.getSalePrice() * apply.getClaimApplyQuantity());
				
				ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
				shipmentReturnParam.setShipmentReturnId(apply.getShipmentReturnId());
				ShipmentReturn shipmentReturn = shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
				if (shipmentReturn != null) {
					apply.setShipmentReturn(shipmentReturn);
				}
				
				Seller shipmentReturnSeller = sellerMapper.getSellerById(apply.getShipmentReturnSellerId());
				if (shipmentReturnSeller != null) {
					apply.setSeller(shipmentReturnSeller);
				}
				 
				// CJH 2016.11.13 환불이 신청중일때 회수비용을 상품에 설정된것으로 디폴트 설정
				if ("01".equals(apply.getClaimStatus()) && apply.getCollectionShippingAmount() == 0) {
					apply.setCollectionShippingAmount(orderItem.getShippingReturn());
				}
			}

			setOrderGiftItemForOrderReturnApply(list);

			// 추가구성상품 (view에서 본품과 추가구성상품을 묶어서 보여주기 위한 용도)
			claimApplyParam.setAdditionItemFlag("Y");
			List<OrderReturnApply> additionList = orderClaimApplyMapper.getActiveReturnListByParam(claimApplyParam);
			setOrderAdditionItemForOrderReturnApply(list, additionList);
		}

		return list;
	}
	
	@Override
	public List<OrderExchangeApply> getActiveExchangeListByParam(ClaimApplyParam claimApplyParam) {

		List<OrderExchangeApply> list = orderClaimApplyMapper.getActiveExchangeListByParam(claimApplyParam);
		if (list != null) {
			for(OrderExchangeApply apply : list) {
				ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
				shipmentReturnParam.setShipmentReturnId(apply.getShipmentReturnId());
				ShipmentReturn shipmentReturn = shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
				if (shipmentReturn != null) {
					apply.setShipmentReturn(shipmentReturn);
				}
				
				Seller shipmentReturnSeller = sellerMapper.getSellerById(apply.getShipmentReturnSellerId());
				if (shipmentReturnSeller != null) {
					apply.setSeller(shipmentReturnSeller);
				}
			}

			setOrderGiftItemForOrderExchangeApply(list);

			// 추가구성상품 (view에서 본품과 추가구성상품을 묶어서 보여주기 위한 용도)
			claimApplyParam.setAdditionItemFlag("Y");
			List<OrderExchangeApply> additionList = orderClaimApplyMapper.getActiveExchangeListByParam(claimApplyParam);
			setOrderAdditionItemForOrderExchangeApply(list, additionList);
		}
		
		return list;
	}
	
	@Override
	public List<OrderCancelShipping> getActiveCancelListByParam(ClaimApplyParam claimApplyParam) {

		claimApplyParam.setAdditionItemFlag("");
		List<OrderCancelApply> list = orderClaimApplyMapper.getActiveCancelListByParam(claimApplyParam);
		List<OrderCancelShipping> groups = null;
		if (ValidationUtils.isNull(list) == false) {
			groups = new ArrayList<>();

			for(OrderCancelApply apply : list) {
				
				OrderItem orderItem = apply.getOrderItem();
				
				// 상품 환불금액
				apply.setClaimApplyAmount(orderItem.getSalePrice() * apply.getClaimApplyQuantity());
				orderItem.setCancelApply(apply);
				
				OrderShipping orderShipping = orderItem.getOrderShipping();
				OrderCancelShipping shippingGroup = null;
				for(OrderCancelShipping group : groups) {
					if (orderShipping.getShippingSequence() == group.getShippingSequence()) {
						
						List<OrderItem> items = group.getOrderItems();
						items.add(orderItem);
						shippingGroup = group;
						break;
						
					}
				}
				
				if (ValidationUtils.isNull(shippingGroup)) {
					shippingGroup = new OrderCancelShipping(orderShipping);
					List<OrderItem> items = new ArrayList<>();
					items.add(orderItem);
					shippingGroup.setOrderItems(items);
					groups.add(shippingGroup);
				}
			}

			setOrderGiftItemForOrderCancelApply(list);

			// 추가구성상품 (view에서 본품과 추가구성상품을 묶어서 보여주기 위한 용도)
			claimApplyParam.setAdditionItemFlag("Y");
			List<OrderCancelApply> additionItems = orderClaimApplyMapper.getActiveCancelListByParam(claimApplyParam);

			setOrderAdditionItemForOrderCancelApply(list, additionItems);
		}

		return groups;
	}
	
	@Override
	public void orderCancelAllProcess(OrderParam orderParam, HttpServletRequest request) {

		boolean isCancelBatch = "CANCEL-BATCH".equals(orderParam.getConditionType());

		if (UserUtils.isUserLogin()) {
			orderParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {
			User user = UserUtils.getGuestLogin();
			UserDetail userDetail = (UserDetail) user.getUserDetail();
			
			orderParam.setGuestUserName(user.getUserName());
			orderParam.setGuestPhoneNumber(userDetail.getPhoneNumber());

		} else if(UserUtils.isManagerLogin()) {

		} else if(isCancelBatch) {
			if (orderParam.getUserId() > 0L) {
				orderParam.setUserId(orderParam.getUserId());
			} else {
				orderParam.setGuestUserName(orderParam.getUserName());
			}
		} else {
			throw new PageNotFoundException();
		}
		
		Order order = orderService.getOrderByParam(orderParam);
		if (order == null) {
			throw new OrderException();
		}

		boolean isAllCancel = true;
		List<Integer> returnCoupons = new ArrayList<>();
		HashMap<String, Integer> stockMap = new HashMap<>();
		List<OrderGiftItem> orderGiftItemList = new ArrayList<>();

		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem item : info.getOrderItems()) {

				if (!"0".equals(item.getOrderStatus())) {
					throw new ClaimException("1001", "[주문전체취소] 입금대기 상태가 아닌 주문 건이 있습니다. (" + orderParam.getOrderCode() + ")");
				}

				if (item.getCouponUserId() > 0) {
					returnCoupons.add(item.getCouponUserId());
				}

				if (item.getAddCouponUserId() > 0) {
					returnCoupons.add(item.getAddCouponUserId());
				}

				// 재고 복원해야하는 목록 만들기
				orderService.makeStockRestorationMap(stockMap, item);

				// 사은품 목록 만들기

				List<OrderGiftItem> tempOrderGiftItemList = item.getOrderGiftItemList();

				if (tempOrderGiftItemList != null && !tempOrderGiftItemList.isEmpty()) {

					tempOrderGiftItemList.stream().forEach(
							orderGiftItem -> {
								orderGiftItemList.add(orderGiftItem);
							}
					);

				}
			}
		}

		List<OrderPayment> payments = order.getOrderPayments();
		ConfigPg configPg = configPgService.getConfigPg();

		if (isAllCancel) {
			for(OrderPayment payment : payments) {
				OrderPgData pgData = payment.getOrderPgData();
				if ((StringUtils.isEmpty(payment.getPayDate()) == false && ("bank".equals(payment.getApprovalType()) || ("vbank".equals(payment.getApprovalType()) && !configPg.isUseVbackRefundService())))) {

					// 무통장 입금타입 중에 결제 확인이 된것이 있으면 즉시취소 불가..
					isAllCancel = false;
					break;

				} else if (ValidationUtils.isNull(pgData.getPgServiceType()) == false && "Y".equals(payment.getNowPaymentFlag())) {

					// PG 결제 타입중에 부분취소 기록이 있으면 즉시취소 불가
					if (payment.getAmount() != payment.getRemainingAmount()) {
						isAllCancel = false;
						break;
					}

				}
			}
		}

		if (isAllCancel == false) {
			throw new ClaimException("1000", "[주문전체취소] 결제가 확인된 건이 있거나 부분 취소 기록이 있습니다. (" + orderParam.getOrderCode() + ")");
		}

		for(OrderPayment payment : payments) {

			// CJH 2016.11.13 잔여액이 0보다 작거나 같은데 즉시 결제 타입이면 무시 - 반대의 경우는 무통장?
			if (payment.getRemainingAmount() <= 0 && "Y".equals(payment.getNowPaymentFlag())) {
				continue;
			}

			String approvalType = payment.getApprovalType();
			if (PointUtils.isPointType(approvalType)) {
				returnPoint(order, payment, payment.getRemainingAmount());

			} else if ("bank".equals(approvalType) && !isCancelBatch) { // 무통장입금 대기건 배치 돌 때 취소X.  사용자나 관리자가 직접 취소시엔 가능.
				if (StringUtils.isEmpty(payment.getPayDate()) == true) {
					orderPaymentMapper.updateOrderPaymentForBankCancel(payment);

					// 현금영수증 상태 업데이트
					CashbillParam cashbillParam = new CashbillParam();
					cashbillParam.setWhere("orderCode");
					cashbillParam.setQuery(payment.getOrderCode());

					Iterable<CashbillIssue> cashbillIssues = cashbillIssueRepository.findAll(cashbillParam.getPredicate());

					for (CashbillIssue cashbillIssue : cashbillIssues) {
						cashbillIssue.setCashbillStatus(CashbillStatus.CANCELED);
						cashbillIssue.setCanceledDate(DateUtils.getToday(Const.DATETIME_FORMAT));
						cashbillIssue.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));

						if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
							cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
						} else {
							cashbillIssue.setUpdateBy("관리자");
						}
					}
				} else {
					payment.setCancelAmount(payment.getRemainingAmount());
					orderPaymentMapper.updateOrderPaymentForCancel(payment);

					OrderPayment orderPayment = new OrderPayment();
					orderPayment.setPaymentType("2");
					orderPayment.setOrderCode(order.getOrderCode());
					orderPayment.setOrderSequence(order.getOrderSequence());
					orderPayment.setApprovalType(approvalType);
					orderPayment.setCancelAmount(payment.getRemainingAmount());
					orderPayment.setPayDate(DateUtils.getToday(Const.DATETIME_FORMAT));
					orderPayment.setDeviceType("WEB");

					if (ShopUtils.isMobilePage()) {
						orderPayment.setDeviceType("MOBILE");
					}

					orderPayment.setNowPaymentFlag("Y");
					orderPaymentMapper.insertOrderPayment(orderPayment);
				}
			} else if (ValidationUtils.isNull(payment.getOrderPgData()) == false) {
				OrderPgData orderPgData = payment.getOrderPgData();
				if (orderPgData.getOrderPgDataId() > 0) {
					boolean isSuccess = false;

					// PG 취소 - 즉시 취소
					if ("inicis".equals(orderPgData.getPgServiceType())) {
						isSuccess = inicisService.cancel(orderPgData);
					} else if ("lgdacom".equals(orderPgData.getPgServiceType())) {
						isSuccess = lgDacomService.cancel(orderPgData);

					} else if ("kakaopay".equals(orderPgData.getPgServiceType())) {
						orderPgData.setRemainAmount(payment.getRemainingAmount());
						isSuccess = kakaopayService.cancel(orderPgData);

					} else if ("payco".equals(orderPgData.getPgServiceType())) {
						orderPgData.setRemainAmount(payment.getRemainingAmount());
						isSuccess = paycoService.cancel(orderPgData);

					}else if ("kspay".equals(orderPgData.getPgServiceType())) {
						orderPgData.setRemainAmount(payment.getRemainingAmount());
						isSuccess = kspayService.cancel(orderPgData);
					}else if ("kcp".equals(orderPgData.getPgServiceType())) {
						orderPgData.setRemainAmount(payment.getRemainingAmount());
						isSuccess = kcpService.cancel(orderPgData);
					}else if ("easypay".equals(orderPgData.getPgServiceType())) {
						orderPgData.setRemainAmount(payment.getRemainingAmount());
						isSuccess = easypayService.cancel(orderPgData);
					} else if ("nicepay".equals(orderPgData.getPgServiceType())) {
						orderPgData.setRequest(request);
						orderPgData.setCancelAmount(orderPgData.getPgAmount());
						orderPgData.setRemainAmount(payment.getRemainingAmount());
						orderPgData.setCancelReason("주문취소");
						isSuccess = nicepayService.cancel(orderPgData);
                    } else if ("naverpay".equals(orderPgData.getPgServiceType())) {
                        orderPgData.setCancelAmount(orderPgData.getPgAmount());
                        orderPgData.setRemainAmount(payment.getRemainingAmount());
                        orderPgData = naverPaymentApi.cancel(orderPgData, configPg);

                        isSuccess = orderPgData.isSuccess();
                    }

					// 입금지연 주문 취소 배치 돌 때는 pg로 취소요청 보내지 않음(pg 기취소)
					if (isCancelBatch) {
						isSuccess = true;
					}

					if (isSuccess) {

						if ("vbank".equals(approvalType) && StringUtils.isEmpty(payment.getPayDate()) == true) {	//2017.06.21 손준의 가상계좌 입금대기상태의 주문인경우
							orderPaymentMapper.updateOrderPaymentForBankCancel(payment);

							// 현금영수증 상태 업데이트
							CashbillParam cashbillParam = new CashbillParam();
							cashbillParam.setWhere("orderCode");
							cashbillParam.setQuery(payment.getOrderCode());

							Iterable<CashbillIssue> cashbillIssues = cashbillIssueRepository.findAll(cashbillParam.getPredicate());

							for (CashbillIssue cashbillIssue : cashbillIssues) {
								cashbillIssue.setCashbillStatus(CashbillStatus.CANCELED);
								cashbillIssue.setCanceledDate(DateUtils.getToday(Const.DATETIME_FORMAT));
								cashbillIssue.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));

								if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
									cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
								} else {
									cashbillIssue.setUpdateBy("관리자");
								}
							}
						} else {
							payment.setCancelAmount(payment.getRemainingAmount());
							orderPaymentMapper.updateOrderPaymentForCancel(payment);

							OrderPayment orderPayment = new OrderPayment();
							orderPayment.setPaymentType("2");
							orderPayment.setOrderCode(order.getOrderCode());
							orderPayment.setOrderSequence(order.getOrderSequence());
							orderPayment.setApprovalType(approvalType);
							orderPayment.setCancelAmount(payment.getRemainingAmount());
							orderPayment.setPayDate(DateUtils.getToday(Const.DATETIME_FORMAT));
							orderPayment.setDeviceType("WEB");

							if (ShopUtils.isMobilePage()) {
								orderPayment.setDeviceType("MOBILE");
							}

							orderPayment.setNowPaymentFlag("Y");
							orderPaymentMapper.insertOrderPayment(orderPayment);
						}
					} else {
						throw new OrderException("PG 취소 연동 에러.");
					}
				}
			}
		}

		// 무통장입금 대기건 배치 돌 때 취소X.  사용자나 관리자가 직접 취소 가능.
		if (!isCancelBatch) {

			// 쿠폰 반환
			if (!returnCoupons.isEmpty()) {
				for (Integer couponUserId : returnCoupons) {

					if (order.getUserId() > 0) {
						OrderCoupon orderCoupon = new OrderCoupon();
						orderCoupon.setUserId(order.getUserId());
						orderCoupon.setCouponUserId(couponUserId);
						couponMapper.updateCouponUserReturnsByOrderCouponUser(orderCoupon);
					}

				}
			}

			// 재고량 복원
			orderService.stockRestoration(stockMap);

			orderMapper.updateOrderCancelAll(orderParam);

			// 사은품 취소 처리
			try {

				if (orderGiftItemList != null && !orderGiftItemList.isEmpty()) {

					HashSet<String> orderGiftItemKeySet = new HashSet<>();

					for (OrderGiftItem giftItem : orderGiftItemList) {

						StringBuffer sb = new StringBuffer();
						String orderCode = giftItem.getOrderCode();
						int orderSequence = giftItem.getOrderSequence();
						int itemSequence = giftItem.getItemSequence();

						sb.append(orderCode);
						sb.append("-");
						sb.append(orderSequence);
						sb.append("-");
						sb.append(itemSequence);

						String key = sb.toString();

						if (orderGiftItemKeySet.contains(key)) {
							continue;
						}

						orderGiftItemService.cancelOrderGiftItem(orderCode, orderSequence, itemSequence);

						orderGiftItemKeySet.add(key);
					}
				}

			} catch (Exception e) {
				throw new ClaimException("1002", "[주문전체취소] 사은품 취소시 문제가 발생했습니다. (" + orderParam.getOrderCode() + ")");
			}

			// 주문 로그
			List<OrderShippingInfo> logOrderShippingInfos = order.getOrderShippingInfos();
			if (logOrderShippingInfos != null && !logOrderShippingInfos.isEmpty()) {
				for (OrderShippingInfo orderShippingInfo : logOrderShippingInfos) {

					List<OrderItem> orderItems = orderShippingInfo.getOrderItems();
					if (orderItems != null && !orderItems.isEmpty()) {
						for (OrderItem orderItem : orderItems) {
							orderService.insertOrderLog(
									OrderLogType.CLAIM_CANCEL,
									orderItem.getOrderCode(),
									orderItem.getOrderSequence(),
									orderItem.getItemSequence(),
									orderItem.getOrderStatus()
							);
						}
					}

				}
			}

		} // if(!isCancelBatch) end

	}

	@Override
	public void orderCancelAllProcessNewTx(OrderParam orderParam) {

		orderCancelAllProcess(orderParam, null);
	}

	@Override
	public List<OrderCancelApply> getAdminApplyCancelListByIds(String[] ids) {
		
		if (ids == null) {
			return null;
		}
		
		List<HashMap<String, String>> param = new ArrayList<>();
		
		for(String key : ids) {
			HashMap<String, String> map = new HashMap<>();
			
			String[] temp = StringUtils.delimitedListToStringArray(key, "-");
			if (temp.length == 3) {
				map.put("orderCode", temp[0]);
				map.put("orderSequence", temp[1]);
				map.put("itemSequence", temp[2]);
				
				param.add(map);
			}
		}
		
		return orderClaimApplyMapper.getAdminApplyCancelListByParam(param);
	}



	@Override
	public void setOrderGiftItemForOrderExchangeApply(List<OrderExchangeApply> list) {

		if (list != null && !list.isEmpty()) {

			HashSet<String> orderCodeSet = new HashSet<>();

			list.stream().forEach(applay -> orderCodeSet.add(applay.getOrderCode()));

			String[] orderCodes = orderCodeSet.toArray(new String[orderCodeSet.size()]);

			List<OrderGiftItem> orderGiftItems = orderGiftItemService.getOrderGiftItemListByOrderCodes(orderCodes);

			for (OrderExchangeApply apply : list) {
				orderService.setOrderGiftItemForOrderItem(orderGiftItems, apply.getOrderItem());
			}
		}

	}

	@Override
	public void setOrderGiftItemForOrderReturnApply(List<OrderReturnApply> list) {

		if (list != null && !list.isEmpty()) {

			HashSet<String> orderCodeSet = new HashSet<>();

			list.stream().forEach(applay -> orderCodeSet.add(applay.getOrderCode()));

			String[] orderCodes = orderCodeSet.toArray(new String[orderCodeSet.size()]);

			List<OrderGiftItem> orderGiftItems = orderGiftItemService.getOrderGiftItemListByOrderCodes(orderCodes);

			for (OrderReturnApply apply : list) {
				orderService.setOrderGiftItemForOrderItem(orderGiftItems, apply.getOrderItem());
			}
		}

	}

	@Override
	public void setOrderGiftItemForOrderCancelApply(List<OrderCancelApply> list) {

		if (list != null && !list.isEmpty()) {

			HashSet<String> orderCodeSet = new HashSet<>();

			list.stream().forEach(applay -> orderCodeSet.add(applay.getOrderCode()));

			String[] orderCodes = orderCodeSet.toArray(new String[orderCodeSet.size()]);

			List<OrderGiftItem> orderGiftItems = orderGiftItemService.getOrderGiftItemListByOrderCodes(orderCodes);

			for (OrderCancelApply apply : list) {
				orderService.setOrderGiftItemForOrderItem(orderGiftItems, apply.getOrderItem());
			}
		}
	}

	@Override
	public void setOrderAdditionItemForOrderCancelApply(List<OrderCancelApply> list, List<OrderCancelApply> additionList) {

		for (OrderCancelApply cancelApply : list) {
			OrderItem orderItem = cancelApply.getOrderItem();

			List<OrderItem> additionItems = new ArrayList<>();
			for (OrderCancelApply temp : additionList) {

				// 클레임 상태가 같을 경우
				if (cancelApply.getClaimStatus().equals(temp.getClaimStatus())) {
					OrderItem additionItem = temp.getOrderItem();

					// 부모의 ORDER_CODE, ITEM_ID, OPTIONS 가 일치하는 경우
					if (orderItem.getOrderCode().equals(additionItem.getOrderCode())
							&& orderItem.getItemId() == additionItem.getParentItemId()
							&& orderItem.getOptions().equals(additionItem.getParentItemOptions())
							&& orderItem.getItemSequence() == additionItem.getParentItemSequence()) {

						// 상품 환불금액
						temp.setClaimApplyAmount(additionItem.getSalePrice() * temp.getClaimApplyQuantity());
						additionItem.setCancelApply(temp);

						Optional optional = additionItems.stream()
								.filter(ai -> ai.getOrderCode().equals(additionItem.getOrderCode()) && ai.getItemSequence() == additionItem.getItemSequence()).findFirst();

						if (!optional.isPresent()) {
							additionItems.add(additionItem);
						}
					}
				}
			}

			if (additionItems.size() > 0) {
				orderItem.setAdditionItemList(additionItems);
			}
		}
	}

	@Override
	public void setOrderAdditionItemForOrderReturnApply(List<OrderReturnApply> list, List<OrderReturnApply> additionList) {

		for (OrderReturnApply returnApply : list) {
			OrderItem orderItem = returnApply.getOrderItem();

			List<OrderItem> additionItems = new ArrayList<>();
			for (OrderReturnApply temp : additionList) {

				// 클레임 상태가 같을 경우
				if (returnApply.getClaimStatus().equals(temp.getClaimStatus())) {
					OrderItem additionItem = temp.getOrderItem();

					// 부모의 ORDER_CODE, ITEM_ID, OPTIONS 가 일치하는 경우
					if (orderItem.getOrderCode().equals(additionItem.getOrderCode())
							&& orderItem.getItemId() == additionItem.getParentItemId()
							&& orderItem.getOptions().equals(additionItem.getParentItemOptions())
							&& orderItem.getItemSequence() == additionItem.getParentItemSequence()) {

						temp.setClaimApplyAmount(additionItem.getSalePrice() * temp.getClaimApplyQuantity());
						additionItem.setReturnApply(temp);
						additionItem.setClaimCode(temp.getClaimCode());

						Optional optional = additionItems.stream()
								.filter(ai -> ai.getOrderCode().equals(additionItem.getOrderCode()) && ai.getItemSequence() == additionItem.getItemSequence()).findFirst();

						if (!optional.isPresent()) {
							additionItems.add(additionItem);
						}
					}
				}
			}

			if (additionItems.size() > 0) {
				orderItem.setAdditionItemList(additionItems);
			}
		}
	}

	@Override
	public void setOrderAdditionItemForOrderExchangeApply(List<OrderExchangeApply> list, List<OrderExchangeApply> additionList) {

		for (OrderExchangeApply exchangeApply : list) {
			OrderItem orderItem = exchangeApply.getOrderItem();

			List<OrderItem> additionItems = new ArrayList<>();
			for (OrderExchangeApply temp : additionList) {

				// 클레임 상태가 같을 경우
				if (exchangeApply.getClaimStatus().equals(temp.getClaimStatus())) {
					OrderItem additionItem = temp.getOrderItem();

					// 부모의 ORDER_CODE, ITEM_ID, OPTIONS 가 일치하는 경우
					if (orderItem.getOrderCode().equals(additionItem.getOrderCode())
							&& orderItem.getItemId() == additionItem.getParentItemId()
							&& orderItem.getOptions().equals(additionItem.getParentItemOptions())
							&& orderItem.getItemSequence() == additionItem.getParentItemSequence()) {

						additionItem.setClaimCode(temp.getClaimCode());

						Optional optional = additionItems.stream()
								.filter(ai -> ai.getOrderCode().equals(additionItem.getOrderCode()) && ai.getItemSequence() == additionItem.getItemSequence()).findFirst();

						if (!optional.isPresent()) {
							additionItems.add(additionItem);
						}
					}
				}
			}

			if (additionItems.size() > 0) {
				orderItem.setAdditionItemList(additionItems);
			}
		}

	}

	@Override
	public void setOrderAdditionItemForOrderItem(OrderItem orderItem, List<OrderItem> additionList) {

		List<OrderItem> additionItems = new ArrayList<>();
		for (OrderItem additionItem : additionList) {

			// 부모의 ORDER_CODE, ITEM_ID, OPTIONS 가 일치하는 경우
			if (orderItem.getOrderCode().equals(additionItem.getOrderCode())
					&& orderItem.getItemId() == additionItem.getParentItemId()
					&& orderItem.getOptions().equals(additionItem.getParentItemOptions())
					&& orderItem.getItemSequence() == additionItem.getParentItemSequence()) {

				additionItems.add(additionItem);
			}
		}

		orderItem.setAdditionItemList(additionItems);
	}

	@Override
	public void insertAdditionExchangeApply(ExchangeApply exchangeApply) {
		OrderParam orderParam = new OrderParam(exchangeApply);

		if (UserUtils.isUserLogin()) {
			orderParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {

		} else {
			throw new PageNotFoundException();
		}

		OrderItem orderItem = orderService.getOrderItemByParam(orderParam);

		orderParam.setAdditionItemFlag("Y");
		orderParam.setParentItemId(orderItem.getItemId());
		orderParam.setParentItemOptions(orderItem.getOptions());
		List<OrderItem> additionOrderList = orderService.getOrderItemListByParam(orderParam);

		ExchangeApply additionExchangeApply = exchangeApply;

		for (OrderItem addition :  additionOrderList) {
			if (orderItem.getItemSequence() == addition.getParentItemSequence()) {
				additionExchangeApply.setItemSequence(addition.getItemSequence());
				additionExchangeApply.setOrderSequence(addition.getOrderSequence());
				additionExchangeApply.setApplyQuantity(addition.getQuantity());

				insertOrderExchangeApply(additionExchangeApply);
			}
		}
	}

	@Override
	public void insertAdditionReturnApply(ReturnApply returnApply) {
		OrderParam orderParam = new OrderParam(returnApply);

		if (UserUtils.isUserLogin()) {
			orderParam.setUserId(UserUtils.getUserId());
		} else if (UserUtils.isGuestLogin()) {

		} else {
			throw new PageNotFoundException();
		}

		OrderItem orderItem = orderService.getOrderItemByParam(orderParam);

		orderParam.setAdditionItemFlag("Y");
		orderParam.setParentItemId(orderItem.getItemId());
		orderParam.setParentItemOptions(orderItem.getOptions());
		List<OrderItem> additionOrderList = orderService.getOrderItemListByParam(orderParam);

		ReturnApply additionReturnApply = returnApply;

		for (OrderItem addition :  additionOrderList) {
			if (orderItem.getItemSequence() == addition.getParentItemSequence()) {
				additionReturnApply.setItemSequence(addition.getItemSequence());
				additionReturnApply.setOrderSequence(addition.getOrderSequence());
				additionReturnApply.setApplyQuantity(addition.getQuantity());

				insertOrderReturnApply(additionReturnApply);
			}
		}
	}

	private void setErpOrderItems(Order order) {
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			List<OrderItem> erpOrderItems = new ArrayList<>();

			for(OrderItem item : info.getOrderItems()) {
				if (item.getCancelApply() != null) {
					erpOrderItems.add(item);
				}
			}

			info.setOrderItems(erpOrderItems);
		}
	}

}
