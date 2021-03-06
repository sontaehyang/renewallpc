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
	 * ???????????? ?????? ?????? ????????? ?????????
	 * @param order
	 * @param claimApply
	 * @return
	 */
	private List<OrderShipping> getShippingsForUser(Order order, ClaimApply claimApply) {

		List<OrderShipping> orderShippings = new ArrayList<>();
		List<OrderCancelApply> orderCancelApplys = new ArrayList<>();

		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem orderItem : info.getOrderItems()) {

			    // ?????? ????????? ????????? ??????
				if ("Y".equals(orderItem.getCancelFlag())) {
					continue;
				}
				
				// CJH 2017.01.03 ?????? ?????? ?????? ??????
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
						
						// ?????? ??????????????? ?????? ????????? ?????? + ?????? ????????? ????????? ??????
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
								// ??????????????? ????????? ???????????? ????????? ?????? ??????
							} else {
								shipping.setRePayShipping("Y");
							}
							
						}
						
						break;
					}
				}
				
				if (inArray == false) {
					
					// ???????????? ????????
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
							// ??????????????? ????????? ???????????? ????????? ?????? ??????
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
			
			// ?????? ?????? ???????????? ?????? ???????????? ?????? ???????????? ????????? ??????
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
	 * ???????????? ?????? ?????? ????????? ?????????
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
				
				// CJH 2017.01.03 ?????? ?????? ?????? ??????
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
								// ??????????????? ????????? ???????????? ????????? ?????? ??????
							} else {
								shipping.setRePayShipping("Y");
							}
							
						}
						
						break;
					}
				}
				
				if (inArray == false) {
					
					// ???????????? ????????
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
							// ??????????????? ????????? ???????????? ????????? ?????? ??????
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
			
			// ?????? ?????? ???????????? ?????? ???????????? ?????? ???????????? ????????? ??????
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
			
			// ????????? ???????????
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

		// ??????????????????
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

		// ??????????????????
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

		// ??????????????????
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

		// ??????????????????
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

		// ??????????????????
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

		// CJH 2016.11.01 ?????? ????????? ?????? ???????????? ??????????????? ??? ???????????? ??????
		if (orderItem.getOrderQuantity() < returnApply.getApplyQuantity() + orderItem.getClaimQuantity()) {
			throw new OrderException("????????? ??????????????? ????????????.");
		}

		orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());

		//CJH 2016.10.09 ???????????? ?????? ??????????????? ????????????
		//returnApply.setReturnApply(orderMapper.getOrderShippingInfoByParam(orderParam));

		OrderReturnApply detail = new OrderReturnApply(orderItem, returnApply);

		ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
		shipmentReturnParam.setShipmentReturnId(returnApply.getShipmentReturnId());
		ShipmentReturn shipmentReturn = shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
		if (shipmentReturn == null) {
			throw new OrderException();
		}

		//????????????????????????
		orderMapper.updateOrderReturnInfo(orderParam);

		// 1 : ????????????, 2 ?????? ??????
		long sellerId = "2".equals(orderItem.getShipmentReturnType()) ? orderItem.getSellerId() : SellerUtils.DEFAULT_OPMANAGER_SELLER_ID;

		detail.setShipmentReturnId(shipmentReturn.getShipmentReturnId());
		detail.setShipmentReturnSellerId(sellerId);

		// ????????????????????? ?????? ????????? copyOrderItemForReturnApply ????????? ?????? itemSequence??? ???????????? ?????? ????????? ????????? max itemSequence ????????????
		if ("Y".equals(orderItem.getAdditionItemFlag())) {
			orderParam.setAdditionItemFlag("N");
			orderParam.setItemId(orderItem.getParentItemId());
			orderParam.setOptions(orderItem.getParentItemOptions());
			orderParam.setOrderStatus("60");
			int parentItemSequence = orderService.getMaxParentOrderItemSequence(orderParam);

			detail.setParentItemSequence(parentItemSequence);
		}


		// ?????? ????????? ??????????????? ????????? ?????? ?????? ??????
		if (orderItem.getQuantity() == detail.getClaimApplyQuantity()) {
			orderClaimApplyMapper.insertOrderReturnApply(detail);
			orderClaimApplyMapper.updateClaimQuantityForReturnApply(detail);
		} else {

			orderClaimApplyMapper.copyOrderItemForReturnApply(detail);
			orderClaimApplyMapper.updateOrderItemQuantityForReturn(detail);

			// ??????!!
			detail.setItemSequence(detail.getCopyItemSequence());
			orderClaimApplyMapper.insertOrderReturnApply(detail);

			// ERP ?????? ??? ??????
			orderItem.setCopyItemSequence(detail.getCopyItemSequence());
			orderItem.setErpOriginUnique(orderItem.getOrderCode() + "" + orderItem.getOrderSequence() + "" + orderItem.getItemSequence() + "00" + "00");
		}

		orderItem.setReturnApply(detail);


		orderParam.setConditionType("OPMANAGER");
		orderParam.setAdditionItemFlag("");
		Order order = orderService.getOrderByParam(orderParam);

		// ERP ????????? ?????? items ??????
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

		// ?????? ?????? ???  ERP ??????
		ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
		erpService.saveOrderListGet(erpOrder);

		// ?????? ??????
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



		// ?????? ??????????????? ?????? ???????????? ????????????(PART_CANCEL_FLAG)??? ???????????? PG??????
		boolean isCard = false;
		for (OrderPayment payment : orderRefund.getOrderPayments()) {
			// 1. ?????? ?????? ??????
			if ("card".equals(payment.getApprovalType())) {
				isCard = true;
			}
		}

		// 2. ??? ???????????? ??????
		int claimApplyQuantity = 0;
 		for (OrderCancelApply orderCancelApply : claimApply.getOrderCancelApplys()) {
			claimApplyQuantity += orderCancelApply.getClaimApplyQuantity();
 		}

		// 3. ???????????? ????????? ?????? flag - ??? ???????????? ????????? ?????? ????????? ????????? ?????? ??????
		boolean isCardAutoCancel = false;
 		if (isCard && claimApplyQuantity == orderRefund.getTotalOrderQuantity()) {
			isCardAutoCancel = true;
		}

		// ?????? ?????? ?????? - ????????????????????? ????????? ?????? ??????
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

		//????????????????????????
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

				// ????????????????????? ?????? ????????? copyOrderItemForCancelApply ????????? ?????? itemSequence??? ???????????? ??? ????????????????????? parentItemSequence??? ??????
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

					// ?????? ?????????????????? ?????? ?????????
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

				// ?????? ????????? 0?????????..
				if (orderItem.getQuantity() == 0) {
					orderClaimApplyMapper.insertOrderCancelApply(orderCancelApply);
					orderClaimApplyMapper.updateClaimQuantityForCancelApply(orderCancelApply);
				} else {

					orderClaimApplyMapper.copyOrderItemForCancelApply(orderCancelApply);
					orderClaimApplyMapper.updateOrderItemQuantityForCancel(orderCancelApply);

					// ??????!!
					orderCancelApply.setItemSequence(orderCancelApply.getCopyItemSequence());
					orderClaimApplyMapper.insertOrderCancelApply(orderCancelApply);

					// ERP ?????? ??? ??????
					orderItem.setCopyItemSequence(orderCancelApply.getCopyItemSequence());
					orderItem.setErpOriginUnique(orderItem.getOrderCode() + "" + orderItem.getOrderSequence() + "" + orderItem.getItemSequence() + "00" + "00");


					// ????????????????????? ?????? ???????????? ?????? ??? ?????? ??????
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

			// ??????????????? ??????
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

						// Mall ?????? ?????? ????????? PG?????? ??????????
						OrderPgData orderPgData = payment.getOrderPgData();
						if (orderPgData.getOrderPgDataId() > 0) {

							boolean isSuccess = false;
							int cancelAmount = returnAmount > payment.getRemainingAmount() ? payment.getRemainingAmount() : returnAmount;

							// ?????? ????????? ???????????? ????????? ?????? ??????
							boolean isPartCancel = cancelAmount == payment.getRemainingAmount() ? false : true;
							// CJH 2016.12.07 - ??????????????? ?????? ??????????????? ???????????? ??????????????? ?????????!!
							if ("inicis".equals(orderPgData.getPgServiceType()) && "PART_CANCEL".equals(orderPgData.getPartCancelDetail())) {
								isPartCancel = true;
							}

							// Jun-Eu 2017.03.02 - ???????????????????????? ???????????? ?????? ?????????
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

							// erp????????? ?????? set cancelApply
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


							// ?????? ?????? ??? ERP uniq??? validate
							ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
							List<OrderLine> orderLines = erpService.validateOrderListGetForClaim(erpOrder);


							orderPgData.setMessage("????????????");
							orderPgData.setCancelAmount(cancelAmount);
							orderPgData.setRemainAmount(payment.getRemainingAmount() - cancelAmount);

							if (isPartCancel == false) {

								// PG ?????? - ?????? ??????
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

								// ??????????????? ??????,?????????
								if ("realtimebank".equals(payment.getApprovalType()) || "vbank".equals(payment.getApprovalType())) {
									receiptService.cashbillReIssue(orderParam);
								}

								// ?????? ?????? ??? ERP ??????
								erpService.saveOrderListGetAutoCancel(orderLines);

							} else {
								String errorMessage = "PG ?????? ?????? ??????";

								if (!StringUtils.isEmpty(orderPgData.getErrorMessage())) {
									errorMessage = orderPgData.getErrorMessage();
								}

								log.error("?????? ?????? ?????? PG??? ?????? - (code:{}, message:{})", orderPgData.getPgAuthCode(), orderPgData.getErrorMessage());
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

				// ERP ????????? ?????? items ??????
				setErpOrderItems(order);

				// ??????????????? ?????? ??????, ???????????? ????????? ???????????? ERP ??????
				ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
				erpService.saveOrderListGet(erpOrder);
			}

			// ?????? ??????
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

			// ????????? ??????
			orderService.stockRestoration(stockMap);

		} else {

			// ERP ????????? ?????? items ??????
			setErpOrderItems(order);

			// ?????? ?????? ???  ERP ??????
			ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
			erpService.saveOrderListGet(erpOrder);
		}

		// ?????? ??????
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
	 * ????????? ??????
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
		point.setReason(MessageUtils.getMessage("M00246") + " ?????? - ????????????["+ order.getOrderCode() +"]");
		
		// ????????? ??????
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
	 * ????????? ?????????
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
			if ("1".equals(shipping.getShippingType())) { // ?????? ??????
				
				realShipping = addDeliveryCharge;
				
				
			} else if ("2".equals(shipping.getShippingType()) || "3".equals(shipping.getShippingType())) { // 2 : ????????? ?????????, 3 : ????????? ?????????, 
				
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
						
						// ?????? ?????? ???????????? ????????? ?????? ?????? ????????? ?????? ?????? ?????? ????????? ??????.
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
				
			} else if ("4".equals(shipping.getShippingType())) { // ?????? ?????????
				
				int totalItemAmount = 0;
				for(OrderItem buyItem : shipping.getOrderItems()) {
					totalItemAmount += buyItem.getSaleAmount();
				}
				
				if (shipping.getShippingFreeAmount() <= totalItemAmount) {
					realShipping = addDeliveryCharge;
				} else {
					realShipping = shipping.getShipping() + addDeliveryCharge;
				}
				
			} else if ("5".equals(shipping.getShippingType())) { // ??????????????? - BOX ??? ?????????
				
				int totalItemQuantity = 0;
				for(OrderItem buyItem : shipping.getOrderItems()) {
					totalItemQuantity += buyItem.getQuantity();
				}
				
				if (shipping.getShippingItemCount() == 0) {
					shipping.setShippingItemCount(1);
				}
				
				int boxCount = (int) Math.ceil((float) totalItemQuantity / shipping.getShippingItemCount());
				realShipping = (shipping.getShipping() + addDeliveryCharge) * boxCount;
				
			} else { // ?????? ?????????
				realShipping = shipping.getShipping() + addDeliveryCharge;
			}
			
			shipping.setRealShipping(realShipping);
			shipping.setRePayShippingAmount(realShipping);
			
			// ??????????????? ????????? ????????? ????????? 0?????? ?????????
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
			 * ??????????????? ????????? ?????????????????? ???????????? ??????????????????..
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
				
				// ????????? ?????? : 1, ????????? ?????? : 2 
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

					// ????????? ???????????
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
			 * ?????? ????????? ?????????!! ??????????????? ???????????? ?????? ???????????????????????? ???????????? ????????????..
			 * ????????? ???????????? OP_ORDER_SHIPPING??? ?????? ????????? ???????????? OP_ADD_PAYMENT ??????????????? ?????? ???????????? ????????? ???????????? ????????????.
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
						orderAddPayment.setSubject("?????? ????????? ?????? ????????? ??????");
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

			if ("98".equals(orderCancelApply.getClaimStatus())) { // ????????????

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

                // ?????? ???????????? ????????? ???????????? ??????
                OrderItem logOrderItem = orderService.getOrderItemForOrderLog(
                        shippingParam.getOrderCode(),
                        shippingParam.getOrderSequence(),
                        shippingParam.getItemSequence()
                );

				if (orderShippingMapper.updateShippingStart(shippingParam) == 0) {
					throw new OrderException();
				}
				
				orderCancelApply.setClaimStatus("99");

				// ?????? ??????
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
			
			// ?????? ?????? ???????????? ?????? ????????? ??????
			if ("03".equals(orderCancelApply.getClaimStatus())) {
				orderCancelApply.setRefundCode(claimApply.getRefundCode());
			}
			
			int count = orderClaimApplyMapper.updateOrderCancelApply(orderCancelApply);
			if (("99".equals(orderCancelApply.getClaimStatus())) && count > 0) {

                // ?????? ???????????? ????????? ???????????? ??????
                OrderItem logOrderItem = orderService.getOrderItemForOrderLog(
                        orderCancelApply.getOrderCode(),
                        orderCancelApply.getOrderSequence(),
                        orderCancelApply.getItemSequence()
                );

				orderClaimApplyMapper.updateClaimQuantityForCancel(orderCancelApply);

                // ?????? ??????
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
						throw new OrderException("[???????????? ??????] ?????? ???????????? ?????? ????????? ????????? ???????????? ???????????????.");
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
				throw new OrderException(claimCode + "?????? ??????");
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

		/* ?????? ??? ??????????????? ???????????? ?????????(sellerId) ?????? *
		 * ???????????? ???????????? ??? ???????????? ID(sellerId)??? ??????????????? ?????? ????????? ????????????
		 * ???????????? ????????? ????????? ???????????? ???????????? ????????? sellerId??? ????????????, ???????????? ?????? ??????????????? ????????? ??? ?????? ?????? ???????????? ???????????? ?????? ??? ????????? validation ????????? ?????? ?????? ?????????
		 * ????????? ?????? ??? ????????? ID??? ????????? ????????? ?????? ???
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
						throw new OrderException("[???????????? ??????] ?????? ???????????? ?????? ????????? ????????? ???????????? ???????????????.");
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
				throw new OrderException(claimCode + "?????? ??????");
			}
			
		}
		
		if (newApplyList.isEmpty()) {
			throw new OrderException();
		}
		
		int collectionShippingAmount = 0; // ????????????
        int initShippingAmount = 0; // ?????? ?????????
		
		for(OrderReturnApply apply : newApplyList) {
			
			// ?????? ??????
			if ("2".equals(apply.getReturnReason())) {
				collectionShippingAmount += apply.getCollectionShippingAmount();

                // 2020.08.08 juneu.son ????????? ????????? ??????????????? ??????,????????? ????????? ??? ??????
                // ????????? ?????? ??? ?????? ??????????????? ????????? ?????? ????????? ??????????????? ????????? ?????? ????????? ??????
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
		
		
		// ??????????????? ????????????
		int defaultShippingAmount = 0;
		int customersReasonCount = 0;
		
		// ?????????????????? ??????
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
		
		//????????? ????????? ??????
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
						orderAddPayment.setSubject("????????? ??????");
						
						orderAddPayment.setIssueCode("RETURN-SHIPPING-" + orderShipping.getShippingSequence());
						
						if (orderAddPaymentService.getDuplicateRegistrationCheckByIssueCode(orderAddPayment) == 0) {
							
							// CJH 2016.12.29 ?????? ???????????? ????????? ?????? ?????? ?????? ?????? ????????? ?????? ????????? ????????????????????? ??????..
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
		
		// ?????? ???????????? ???????????? ?????? ??????????????? ????????? OP_ORDER_ADD_PAYMENT ???????????? ??????
		if (customersReasonCount > 0) {
			
			// ?????? ??????
			if (collectionShippingAmount > 0) {
				
				OrderAddPayment orderAddPayment = new OrderAddPayment();
				
				orderAddPayment.setOrderCode(claimApply.getOrderCode());
				orderAddPayment.setOrderSequence(claimApply.getOrderSequence());
				orderAddPayment.setSellerId(shipmentReturnSellerId);
				orderAddPayment.setAddPaymentType("1");
				orderAddPayment.setAmount(collectionShippingAmount);
				orderAddPayment.setRefundCode(claimApply.getRefundCode());
				orderAddPayment.setSalesDate(DateUtils.getToday());
				orderAddPayment.setSubject("????????????");
				orderAddPayment.setIssueCode("COLLECTION-SHIPPING-" + shipmentReturnSellerId + "-" + claimApply.getOrderCode());
				orderAddPayments.add(orderAddPayment);
			}

			// ?????? ?????????
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
                orderAddPayment.setSubject("?????? ?????????");

                orderAddPayment.setIssueCode("INIT-SHIPPING-" + shipmentReturnSellerId + "-" + claimApply.getOrderCode());

                if (orderAddPaymentService.getDuplicateRegistrationCheckByIssueCode(orderAddPayment) == 0) {
                    orderAddPayments.add(orderAddPayment);
                }
            }
			
			for(String deliveryNumber : deliveryNumbers) {
				
				boolean isFreeShipping = true;
				long shipmentSellerId = 0; // ?????? ?????? ???????????? ?????? ????????? ???????????? ????????????
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
					orderAddPayment.setSubject("????????????["+ deliveryNumber +"] ???????????? ?????????");
					
					orderAddPayment.setIssueCode("FREE-SHIPPING-" + deliveryNumber);
					
					if (orderAddPaymentService.getDuplicateRegistrationCheckByIssueCode(orderAddPayment) == 0) {
						orderAddPayments.add(orderAddPayment);
					}
				}
			}
			
			
			// ???????????? ?????????
			HashMap<Integer, OrderShipping> returnShippingMap = new HashMap<Integer, OrderShipping>();
			for(OrderItem item : itemList) {
				
				
				if ("0".equals(item.getOrderStatus()) || "10".equals(item.getOrderStatus())) {
					
					OrderShipping orderShipping = item.getOrderShipping();

					// ????????? ????????? ???????????? ????????? ????????? ?????????
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
							notSendTotalItemAmount += item.getSaleAmount();//??????
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
						orderAddPayment.setSubject("???????????? ????????? - ????????? ????????????[" + StringUtils.numberFormat(shipping.getShippingFreeAmount()) + "??? ?????? ??????]");
						
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
						throw new OrderException("[???????????? ??????] ?????? ???????????? ?????? ????????? ????????? ???????????? ???????????????.");
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
				throw new OrderException(claimCode + "?????? ??????");
			}
			
		}
		
		if (newApplyList.isEmpty()) {
			throw new ClaimException();
		}
		
		
		for(OrderReturnApply apply : newApplyList) {
			
			// ??????????????? ?????? ????????? ??????
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

			// ???????????? ??????
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
		
		// ????????? ?????? ????????? ?????????
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

			// ?????? ??????
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


			// ?????? ??????
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

			// ?????? ????????? ?????? ????????? ??????
			OrderItem logOrderItem = orderService.getOrderItemForOrderLog(
					apply.getOrderCode(),
					apply.getOrderSequence(),
					apply.getItemSequence()
			);

			if ("99".equals(apply.getClaimStatus())) {
				orderClaimApplyMapper.updateClaimQuantityForExchange(apply);
			}

			// ???????????? ??????
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

			// ERP ????????? ?????? items ??????
			for (OrderShippingInfo info : order.getOrderShippingInfos()) {
				List<OrderItem> erpOrderItems = new ArrayList<>();

				for (OrderItem item : info.getOrderItems()) {
					if (item.getExchangeApply() != null) {
						erpOrderItems.add(item);
					}
				}

				info.setOrderItems(erpOrderItems);
			}

			// ERP ??????
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
		
		// CJH 2016.11.01 ?????? ????????? ?????? ???????????? ??????????????? ??? ???????????? ??????
		if (orderItem.getQuantity() < orderItem.getClaimQuantity() + exchangeApply.getApplyQuantity()) {
			throw new OrderException("????????? ??????????????? ????????????.");
		}
		
		orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());
		
		// CJH 2016.11.09 ?????? ??????????????? ????????? ???????????? ????????????
		//OrderExchangeApply apply = new OrderExchangeApply(exchangeApply, orderMapper.getOrderShippingInfoByParam(orderParam));
		OrderExchangeApply apply = new OrderExchangeApply(exchangeApply);
		
		ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
		shipmentReturnParam.setShipmentReturnId(orderItem.getShipmentReturnId());
		ShipmentReturn shipmentReturn = shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
		if (shipmentReturn == null) {
			throw new OrderException();
		}
		
		// 1 : ????????????, 2 ?????? ??????
		long sellerId = "2".equals(orderItem.getShipmentReturnType()) ? orderItem.getSellerId() : SellerUtils.DEFAULT_OPMANAGER_SELLER_ID;
		
		apply.setShipmentReturnId(shipmentReturn.getShipmentReturnId());
		apply.setShipmentReturnSellerId(sellerId);

		// ????????????????????? ?????? ????????? copyOrderItemForReturnApply ????????? ?????? itemSequence??? ???????????? ?????? ????????? ????????? max itemSequence ????????????
		if ("Y".equals(orderItem.getAdditionItemFlag())) {
			orderParam.setAdditionItemFlag("N");
			orderParam.setItemId(orderItem.getParentItemId());
			orderParam.setOptions(orderItem.getParentItemOptions());
			orderParam.setOrderStatus("50");
			int parentItemSequence = orderService.getMaxParentOrderItemSequence(orderParam);

			apply.setParentItemSequence(parentItemSequence);
		}

		// ?????? ????????? ??????????????? ????????? ?????? ?????? ??????
		if (orderItem.getQuantity() == exchangeApply.getApplyQuantity()) {
			orderClaimApplyMapper.insertOrderExchangeApply(apply);
			orderClaimApplyMapper.updateClaimQuantityForExchangeApply(apply);
		} else {
			
			orderClaimApplyMapper.copyOrderItemForExchangeApply(apply);
			orderClaimApplyMapper.updateOrderItemQuantityForExchange(apply);

			// ??????!!
			apply.setItemSequence(apply.getCopyItemSequence());
			orderClaimApplyMapper.insertOrderExchangeApply(apply);

		}

		// ?????? ??????
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

		// ??????????????????
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
				
				// ?????? ????????????
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
				 
				// CJH 2016.11.13 ????????? ??????????????? ??????????????? ????????? ?????????????????? ????????? ??????
				if ("01".equals(apply.getClaimStatus()) && apply.getCollectionShippingAmount() == 0) {
					apply.setCollectionShippingAmount(orderItem.getShippingReturn());
				}
			}

			setOrderGiftItemForOrderReturnApply(list);

			// ?????????????????? (view?????? ????????? ????????????????????? ????????? ???????????? ?????? ??????)
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

			// ?????????????????? (view?????? ????????? ????????????????????? ????????? ???????????? ?????? ??????)
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
				
				// ?????? ????????????
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

			// ?????????????????? (view?????? ????????? ????????????????????? ????????? ???????????? ?????? ??????)
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
					throw new ClaimException("1001", "[??????????????????] ???????????? ????????? ?????? ?????? ?????? ????????????. (" + orderParam.getOrderCode() + ")");
				}

				if (item.getCouponUserId() > 0) {
					returnCoupons.add(item.getCouponUserId());
				}

				if (item.getAddCouponUserId() > 0) {
					returnCoupons.add(item.getAddCouponUserId());
				}

				// ?????? ?????????????????? ?????? ?????????
				orderService.makeStockRestorationMap(stockMap, item);

				// ????????? ?????? ?????????

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

					// ????????? ???????????? ?????? ?????? ????????? ????????? ????????? ???????????? ??????..
					isAllCancel = false;
					break;

				} else if (ValidationUtils.isNull(pgData.getPgServiceType()) == false && "Y".equals(payment.getNowPaymentFlag())) {

					// PG ?????? ???????????? ???????????? ????????? ????????? ???????????? ??????
					if (payment.getAmount() != payment.getRemainingAmount()) {
						isAllCancel = false;
						break;
					}

				}
			}
		}

		if (isAllCancel == false) {
			throw new ClaimException("1000", "[??????????????????] ????????? ????????? ?????? ????????? ?????? ?????? ????????? ????????????. (" + orderParam.getOrderCode() + ")");
		}

		for(OrderPayment payment : payments) {

			// CJH 2016.11.13 ???????????? 0?????? ????????? ????????? ?????? ?????? ???????????? ?????? - ????????? ????????? ??????????
			if (payment.getRemainingAmount() <= 0 && "Y".equals(payment.getNowPaymentFlag())) {
				continue;
			}

			String approvalType = payment.getApprovalType();
			if (PointUtils.isPointType(approvalType)) {
				returnPoint(order, payment, payment.getRemainingAmount());

			} else if ("bank".equals(approvalType) && !isCancelBatch) { // ??????????????? ????????? ?????? ??? ??? ??????X.  ???????????? ???????????? ?????? ???????????? ??????.
				if (StringUtils.isEmpty(payment.getPayDate()) == true) {
					orderPaymentMapper.updateOrderPaymentForBankCancel(payment);

					// ??????????????? ?????? ????????????
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
							cashbillIssue.setUpdateBy("?????????");
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

					// PG ?????? - ?????? ??????
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
						orderPgData.setCancelReason("????????????");
						isSuccess = nicepayService.cancel(orderPgData);
                    } else if ("naverpay".equals(orderPgData.getPgServiceType())) {
                        orderPgData.setCancelAmount(orderPgData.getPgAmount());
                        orderPgData.setRemainAmount(payment.getRemainingAmount());
                        orderPgData = naverPaymentApi.cancel(orderPgData, configPg);

                        isSuccess = orderPgData.isSuccess();
                    }

					// ???????????? ?????? ?????? ?????? ??? ?????? pg??? ???????????? ????????? ??????(pg ?????????)
					if (isCancelBatch) {
						isSuccess = true;
					}

					if (isSuccess) {

						if ("vbank".equals(approvalType) && StringUtils.isEmpty(payment.getPayDate()) == true) {	//2017.06.21 ????????? ???????????? ????????????????????? ???????????????
							orderPaymentMapper.updateOrderPaymentForBankCancel(payment);

							// ??????????????? ?????? ????????????
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
									cashbillIssue.setUpdateBy("?????????");
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
						throw new OrderException("PG ?????? ?????? ??????.");
					}
				}
			}
		}

		// ??????????????? ????????? ?????? ??? ??? ??????X.  ???????????? ???????????? ?????? ?????? ??????.
		if (!isCancelBatch) {

			// ?????? ??????
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

			// ????????? ??????
			orderService.stockRestoration(stockMap);

			orderMapper.updateOrderCancelAll(orderParam);

			// ????????? ?????? ??????
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
				throw new ClaimException("1002", "[??????????????????] ????????? ????????? ????????? ??????????????????. (" + orderParam.getOrderCode() + ")");
			}

			// ?????? ??????
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

				// ????????? ????????? ?????? ??????
				if (cancelApply.getClaimStatus().equals(temp.getClaimStatus())) {
					OrderItem additionItem = temp.getOrderItem();

					// ????????? ORDER_CODE, ITEM_ID, OPTIONS ??? ???????????? ??????
					if (orderItem.getOrderCode().equals(additionItem.getOrderCode())
							&& orderItem.getItemId() == additionItem.getParentItemId()
							&& orderItem.getOptions().equals(additionItem.getParentItemOptions())
							&& orderItem.getItemSequence() == additionItem.getParentItemSequence()) {

						// ?????? ????????????
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

				// ????????? ????????? ?????? ??????
				if (returnApply.getClaimStatus().equals(temp.getClaimStatus())) {
					OrderItem additionItem = temp.getOrderItem();

					// ????????? ORDER_CODE, ITEM_ID, OPTIONS ??? ???????????? ??????
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

				// ????????? ????????? ?????? ??????
				if (exchangeApply.getClaimStatus().equals(temp.getClaimStatus())) {
					OrderItem additionItem = temp.getOrderItem();

					// ????????? ORDER_CODE, ITEM_ID, OPTIONS ??? ???????????? ??????
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

			// ????????? ORDER_CODE, ITEM_ID, OPTIONS ??? ???????????? ??????
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
