package saleson.shop.order.refund;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.OrderLogType;
import saleson.common.notification.ApplicationInfoService;
import saleson.common.notification.UnifiedMessagingService;
import saleson.common.utils.PointUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.erp.domain.ErpOrderType;
import saleson.erp.service.ErpOrder;
import saleson.erp.service.ErpService;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.seller.main.SellerMapper;
import saleson.seller.main.domain.Seller;
import saleson.shop.config.ConfigMapper;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.CouponMapper;
import saleson.shop.coupon.domain.OrderCoupon;
import saleson.shop.google.analytics.domain.measuring.Product;
import saleson.shop.order.OrderMapper;
import saleson.shop.order.OrderService;
import saleson.shop.order.addpayment.OrderAddPaymentMapper;
import saleson.shop.order.addpayment.domain.OrderAddPayment;
import saleson.shop.order.claimapply.OrderClaimApplyMapper;
import saleson.shop.order.claimapply.OrderClaimApplyService;
import saleson.shop.order.claimapply.domain.ClaimApply;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.domain.*;
import saleson.shop.order.giftitem.OrderGiftItemService;
import saleson.shop.order.pg.PgService;
import saleson.shop.order.refund.domain.OrderRefund;
import saleson.shop.order.refund.domain.OrderRefundDetail;
import saleson.shop.order.refund.support.OrderRefundParam;
import saleson.shop.order.shipping.OrderShippingMapper;
import saleson.shop.order.support.ChangePayment;
import saleson.shop.order.support.EditPayment;
import saleson.shop.order.support.OrderException;
import saleson.shop.order.support.OrderParam;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.AvailablePoint;
import saleson.shop.point.domain.Point;
import saleson.shop.point.domain.PointUsed;
import saleson.shop.point.exception.PointException;
import saleson.shop.remittance.RemittanceMapper;
import saleson.shop.shipmentreturn.ShipmentReturnMapper;
import saleson.shop.ums.UmsService;
import saleson.shop.ums.support.OrderRefundApproval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service("orderRefundService")
public class OrderRefundServiceImpl implements OrderRefundService {

	private static final Logger log = LoggerFactory.getLogger(OrderRefundServiceImpl.class);

	@Autowired
	private OrderRefundMapper orderRefundMapper;

	@Autowired
	private OrderClaimApplyMapper orderClaimApplyMapper;

	@Autowired
	private SellerMapper sellerMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderAddPaymentMapper orderAddPaymentMapper;

	@Autowired
	private OrderClaimApplyService orderClaimApplyService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ShipmentReturnMapper shipmentReturnMapper;

	@Autowired
	private OrderShippingMapper orderShippingMapper;

	@Autowired
	private RemittanceMapper remittanceMapper;

	@Autowired
	private CouponMapper couponMapper;

	@Autowired
	private PointService pointService;

	@Autowired
	private PgService inicisService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
	private OrderGiftItemService orderGiftItemService;

	@Autowired
	private UmsService umsService;

	@Autowired
	private UnifiedMessagingService unifiedMessagingService;

	@Autowired
	private ApplicationInfoService applicationInfoService;

	@Autowired
	private ConfigMapper configMapper;

	@Autowired
	private ErpService erpService;

	@Override
	public OrderRefund getOrderCancelRefundForUser(ClaimApply claimApply) {

		OrderRefund orderRefund = new OrderRefund(claimApply);

		OrderParam orderParam = new OrderParam();
		orderParam.setUserId(UserUtils.getUserId());
		orderParam.setOrderCode(claimApply.getOrderCode());
		orderParam.setOrderSequence(claimApply.getOrderSequence());

		if (claimApply.getOrder() == null) {
			Order order = orderService.getOrderByParam(orderParam);
			if (order == null) {
				throw new OrderException();
			}

			claimApply.setOrder(order);
		}

		List<OrderShipping> orderShippings = orderClaimApplyService.getReShippingAmountForUser(claimApply);

		List<OrderCancelApply> cancelApplyList = claimApply.getOrderCancelApplys();
		List<Long> sellerIds = new ArrayList<>();

		int totalItemReturnAmount = 0;
		if (cancelApplyList != null) {
			for(OrderCancelApply orderCancelApply : cancelApplyList) {
				totalItemReturnAmount += orderCancelApply.getClaimApplyAmount();
				appendSellerIds(sellerIds, orderCancelApply.getOrderItem().getSellerId());
			}
		}

		if (sellerIds.isEmpty()) {
			throw new OrderException();
		}

		List<OrderAddPayment> orderAddPayments = new ArrayList<>();
		int totalAddShippingAmount = 0;

		if (orderShippings != null) {
			for(OrderShipping orderShipping : orderShippings) {

				if (orderShipping.getAddPayAmount() != 0) {

					OrderAddPayment orderAddPayment = new OrderAddPayment(orderShipping);
					orderAddPayment.setAddPaymentType(orderShipping.getAddPaymentType());
					orderAddPayment.setAmount(orderShipping.getAddPayAmount());
					orderAddPayment.setRefundCode(claimApply.getRefundCode());
					orderAddPayment.setSalesDate(DateUtils.getToday());
					orderAddPayment.setSubject("?????? ????????? ?????? ????????? ??????");
					orderAddPayment.setIssueCode("CANCEL-ADD-SHIPPING-" + orderShipping.getShippingSequence());
					orderAddPayments.add(orderAddPayment);

					// ????????? ???????
					if ("1".equals(orderShipping.getAddPaymentType())) {
						totalAddShippingAmount -= orderShipping.getAddPayAmount();
					} else {
						totalAddShippingAmount += orderShipping.getAddPayAmount();
					}

				}
			}
		}

		if (orderAddPayments != null) {
			for(OrderAddPayment addPayment : orderAddPayments) {
				appendSellerIds(sellerIds, addPayment.getSellerId());
			}
		}

		List<OrderRefundDetail> details = new ArrayList<>();
		for(Long sellerId : sellerIds) {

			Seller seller = sellerMapper.getSellerById(sellerId);
			if (seller == null) {
				seller = new Seller();
				seller.setSellerId(sellerId);
				seller.setSellerName("???????????? ??????");
			}

			details.add(new OrderRefundDetail(seller, cancelApplyList, null, orderAddPayments));
		}

		// ??? ????????????
		int orderQuantity = 0;
		for (OrderShippingInfo orderShippingInfo : claimApply.getOrder().getOrderShippingInfos()) {
			for (OrderItem orderItem : orderShippingInfo.getOrderItems()) {
				orderQuantity += orderItem.getOrderQuantity();
			}
		}

		orderRefund.setOrderPayments(orderMapper.getOrderPaymentListByParam(orderParam));

		orderRefund.setGroups(details);
		orderRefund.setTotalAddShippingAmount(totalAddShippingAmount);
		orderRefund.setTotalItemReturnAmount(totalItemReturnAmount);
		orderRefund.setTotalOrderQuantity(orderQuantity);

		return orderRefund;
	}

	private void appendSellerIds(List<Long> list, long id) {

		if (list.isEmpty()) {
			list.add(id);
		} else {
			for(Long sellerId : list) {
				if (sellerId == id) {
					return;
				}
			}

			list.add(id);
		}
	}

	@Override
	public OrderRefund getOrderRefundByCode(String refundCode) {

		OrderRefund orderRefund = orderRefundMapper.getOrderRefundByCode(refundCode);
		if (orderRefund == null) {
			throw new OrderException();
		}

		List<OrderAddPayment> addPaymentList = orderAddPaymentMapper.getOrderAddPaymentListByRefundCode(refundCode);
		List<OrderCancelApply> cancelApplyList = orderClaimApplyMapper.getOrderCancelApplyListByRefundCode(refundCode);
		List<OrderReturnApply> returnApplyList = orderClaimApplyMapper.getOrderReturnApplyListByRefundCode(refundCode);

		List<Long> sellerIds = new ArrayList<>();

		if (cancelApplyList != null) {
			for(OrderCancelApply orderCancelApply : cancelApplyList) {
				appendSellerIds(sellerIds, orderCancelApply.getOrderItem().getSellerId());
			}

			orderClaimApplyService.setOrderGiftItemForOrderCancelApply(cancelApplyList);


			// ?????????????????? (view?????? ????????? ????????????????????? ????????? ???????????? ?????? ??????)
			List<OrderCancelApply> additionApplyList =
					cancelApplyList.stream().filter(cancel -> "Y".equals(cancel.getOrderItem().getAdditionItemFlag())).collect(Collectors.toList());
			orderClaimApplyService.setOrderAdditionItemForOrderCancelApply(cancelApplyList, additionApplyList);
		}

		if (returnApplyList != null) {
			for(OrderReturnApply orderReturnApply : returnApplyList) {
				appendSellerIds(sellerIds, orderReturnApply.getShipmentReturnSellerId());
			}

			orderClaimApplyService.setOrderGiftItemForOrderReturnApply(returnApplyList);

			// ?????????????????? (view?????? ????????? ????????????????????? ????????? ???????????? ?????? ??????)
			List<OrderReturnApply> additionReturnApplyList =
					returnApplyList.stream().filter(cancel -> "Y".equals(cancel.getOrderItem().getAdditionItemFlag())).collect(Collectors.toList());
			orderClaimApplyService.setOrderAdditionItemForOrderReturnApply(returnApplyList, additionReturnApplyList);
		}

		if (addPaymentList != null) {
			for(OrderAddPayment addPayment : addPaymentList) {
				appendSellerIds(sellerIds, addPayment.getSellerId());
			}
		}

		if (sellerIds.isEmpty()) {
			throw new OrderException();
		}

		List<OrderRefundDetail> details = new ArrayList<>();
		for(Long sellerId : sellerIds) {

			Seller seller = sellerMapper.getSellerById(sellerId);
			if (seller == null) {
				seller = new Seller();
				seller.setSellerId(sellerId);
				seller.setSellerName("???????????? ??????");
			}

			details.add(new OrderRefundDetail(seller, cancelApplyList, returnApplyList, addPaymentList));
		}

		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(orderRefund.getOrderCode());
		orderParam.setOrderSequence(orderRefund.getOrderSequence());

		Order returnInfo = orderMapper.getOrderReturnInfo(orderParam);
		if (ValidationUtils.isNull(returnInfo) == false) {
			orderRefund.setReturnBankName(returnInfo.getReturnBankName());
			orderRefund.setReturnBankInName(returnInfo.getReturnBankInName());
			orderRefund.setReturnVirtualNo(returnInfo.getReturnVirtualNo());
		}

		List<OrderPayment> payments = orderMapper.getOrderPaymentListByParam(orderParam);
		String escrowStatus = "N";

		for(OrderPayment payment : payments) {
			if ("bank".equals(payment.getApprovalType()) || "vbank".equals(payment.getApprovalType()))  {

				payment.setReturnBankInName(orderRefund.getReturnBankInName());
				payment.setReturnBankVirtualNo(orderRefund.getReturnVirtualNo());

				OrderPgData pgData = payment.getOrderPgData();

				if ("vbank".equals(payment.getApprovalType())) {
					if ("inicis".equals(pgData.getPgServiceType())) {
						escrowStatus = orderMapper.getOrderItemByEscrow(payment.getOrderCode());
						payment.setEscrowStatus(escrowStatus);
					}
				}

				List<CodeInfo> list = ShopUtils.getBankListByKey(pgData.getPgServiceType());
				for(CodeInfo code : list) {
					if (code.getLabel().equals(orderRefund.getReturnBankName())) {
						payment.setReturnBankName(code.getKey().getId());

						break;
					}
				}

				payment.setReturnBankList(list);
			}
		}

		orderRefund.setOrderPayments(payments);
		orderRefund.setGroups(details);

		return orderRefund;

	}

	@Override
	public List<OrderRefund> getOrderRefundListByParam(OrderRefundParam param) {

		int totalCount = orderRefundMapper.getOrderRefundCountByParam(param);

		if (param.getItemsPerPage() == 10) {
			param.setItemsPerPage(50);
		}

		Pagination pagination = Pagination.getInstance(totalCount, param.getItemsPerPage());
		param.setPagination(pagination);
		param.setLanguage(CommonUtils.getLanguage());

		return orderRefundMapper.getOrderRefundListByParam(param);

	}

	@Override
	public String getActiveRefundCodeByParam(OrderRefundParam param) {

		String refundCode = orderRefundMapper.getActiveRefundCodeByParam(param);
		if (StringUtils.isEmpty(refundCode)) {
			refundCode = getNewRefundCodeByParam(param);
		}


		return refundCode;
	}

	@Override
	public String getNewRefundCodeByParam(OrderRefundParam param) {

        OrderRefund orderRefund = new OrderRefund();
        orderRefund.setOrderCode(param.getOrderCode());
        orderRefund.setOrderSequence(param.getOrderSequence());

        if (UserUtils.isManagerLogin()) {
            orderRefund.setRequestManagerUserName(UserUtils.getManagerName());
        }

        orderRefund.setReturnBankName(param.getBankName());
        orderRefund.setReturnBankInName(param.getBankInName());
        orderRefund.setReturnVirtualNo(param.getVirtualNo());

        orderRefund.setRefundStatusCode("1");
        if ("REFUND_FINISH".equals(param.getConditionType())) {
            orderRefund.setRefundStatusCode("2");
        }

        String newRefundCode = "RE-" + sequenceService.getLong("OP_ORDER_REFUND_CODE");

        orderRefund.setRefundCode(newRefundCode);

        orderRefundMapper.insertOrderRefund(orderRefund);
        return orderRefund.getRefundCode();

	}

	@Override
	public void orderRefundProcess(OrderRefund orderRefund, EditPayment editPayment) {

		int totalReturnAmount = orderRefund.getTotalReturnAmount();
		int totalEditAmount = 0;
		String refundReason = " ";
		OrderItem orderItem = new OrderItem();

		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(editPayment.getOrderCode());
		orderParam.setOrderSequence(editPayment.getOrderSequence());

        // 2019.04.25 sje ???????????? ????????? ????????? ???????????? ????????? ?????? ??????
        if (orderShippingMapper.updatePreviousShipping(orderParam) == 0) {
            throw new OrderException();
        }

		List<OrderPayment> payments = orderMapper.getOrderPaymentListByParam(orderParam);
		if (payments == null) {
			throw new PageNotFoundException();
		}

		List<OrderAddPayment> addPaymentList = orderAddPaymentMapper.getOrderAddPaymentListByRefundCode(orderRefund.getRefundCode());
		if (addPaymentList != null) {
			for(OrderAddPayment addPayment : addPaymentList) {

				if (addPayment.getIssueCode().startsWith("NOSEND-ITEM") || addPayment.getIssueCode().startsWith("RETURN-SHIPPING")) {

					String[] temp = StringUtils.delimitedListToStringArray(addPayment.getIssueCode(), "-");
					int shippingSequence = Integer.parseInt(temp[2]);

					orderParam.setShippingSequence(shippingSequence);
					OrderShipping shipping = orderShippingMapper.getOrderShippingByParam(orderParam);

					if (shipping == null) {
						throw new OrderException();
					}

                    if (addPayment.getIssueCode().startsWith("RETURN-SHIPPING")) {

						// ??????
						// ?????? ????????? ID??? ???????????? ????????? ??????????????? - ???????????? ?????? ?????? ???????????????
						if (shipping.getRemittanceId() > 0) {
							shipping.setConditionType("UPDATE_REMITTANCE");
							shipping.setRemittanceExpectedDate(remittanceMapper.getRemittanceDateBySellerId(shipping.getSellerId()));
							shipping.setRemittanceStatusCode("2");

							shipping.setReturnFlag("Y");

							// ??????????????? ?????? ?????? ???????????? ?????? - ?????? ?????? ????????? ????????? ????????????.
							shipping.setRemittanceAmount(addPayment.getAmount());
						} else {
							shipping.setRemittanceAmount(shipping.getRemittanceAmount() - addPayment.getAmount());
						}

						// ????????? ?????? ???????????? ????????? ????????? ????????? ???????????
						if (shipping.getRealShipping() != addPayment.getAmount()) {
							throw new OrderException();
						}

						shipping.setRealShipping(shipping.getRealShipping() - addPayment.getAmount());
						shipping.setPayShipping(shipping.getPayShipping() - addPayment.getAmount());
						shipping.setReturnShipping(shipping.getReturnShipping() + addPayment.getAmount());

					} else {

						// ?????? ??????
						shipping.setConditionType("UPDATE_REMITTANCE");
						shipping.setRemittanceExpectedDate(remittanceMapper.getRemittanceDateBySellerId(shipping.getSellerId()));
						shipping.setRemittanceStatusCode("1");
						shipping.setRemittanceAmount(addPayment.getAmount());

						shipping.setRealShipping(addPayment.getAmount());
						shipping.setPayShipping(addPayment.getAmount());
						shipping.setReturnShipping(shipping.getReturnShipping());

					}

                    if (orderShippingMapper.updateCancelShipping(shipping) == 0) {
                        throw new OrderException();
                    }

					// ??????
					if (orderAddPaymentMapper.deleteOrderAddPaymentById(addPayment.getAddPaymentId()) == 0) {
						throw new OrderException();
					}
				} else if (addPayment.getIssueCode().startsWith("CANCEL-ADD-SHIPPING")) {

					String[] temp = StringUtils.delimitedListToStringArray(addPayment.getIssueCode(), "-");
					int shippingSequence = Integer.parseInt(temp[3]);

					orderParam.setShippingSequence(shippingSequence);
					OrderShipping shipping = orderShippingMapper.getOrderShippingByParam(orderParam);

					if (shipping == null) {
						throw new OrderException();
					}

//                    if ("1".equals(addPayment.getAddPaymentType())) {
//
//                        // ?????? - ?????? ???????????? ???????????? ??????
//                        shipping.setRealShipping(shipping.getRealShipping() + addPayment.getAmount());
//                        shipping.setPayShipping(shipping.getPayShipping() + addPayment.getAmount());
//                        shipping.setRemittanceAmount(shipping.getRemittanceAmount() + addPayment.getAmount());
//                        shipping.setReturnShipping(shipping.getReturnShipping());
//                    } else {
//
//                        // ?????? - ?????? ???????????? ???????????? ???
//                        shipping.setRealShipping(shipping.getRealShipping() - addPayment.getAmount());
//                        shipping.setPayShipping(shipping.getPayShipping() - addPayment.getAmount());
//                        shipping.setRemittanceAmount(shipping.getRemittanceAmount() - addPayment.getAmount());
//                        shipping.setReturnShipping(shipping.getReturnShipping() + addPayment.getAmount());
//                    }

					if (orderShippingMapper.updateCancelShipping(shipping) == 0) {
						throw new OrderException();
					}

					// ??????
					if (orderAddPaymentMapper.deleteOrderAddPaymentById(addPayment.getAddPaymentId()) == 0) {
						throw new OrderException();
					}


				} else {

					addPayment.setRemittanceExpectedDate(remittanceMapper.getRemittanceDateBySellerId(addPayment.getSellerId()));
					if (remittanceMapper.updateAddPaymentRemittanceInfo(addPayment) == 0) {
						throw new OrderException();
					}

				}

			}
		}

		List<Integer> returnCoupons = new ArrayList<>();
		HashMap<String, Integer> stockMap = new HashMap<>();

		// ?????? ????????? ?????? ?????? ????????? ???????????? ???????????? ????????? ???????????? ??????.
		List<OrderReturnApply> returnApplyList = orderClaimApplyMapper.getOrderReturnApplyListByRefundCode(orderRefund.getRefundCode());
		if (returnApplyList != null) {

			int notFrequencyPointAmount = 0;
			int frequencyPointAmount = 0;

			for(OrderReturnApply returnApply : returnApplyList) {
                if ("??????".equals(returnApply.getReturnReasonText())) {
                    refundReason = returnApply.getReturnReasonDetail();
                } else {
                    refundReason = returnApply.getReturnReasonText();
                }

				orderItem = returnApply.getOrderItem();

				if (orderRefund.getUserId() > 0) {
					int point = orderItem.getEarnPoint() * orderItem.getQuantity();

					// ???????????? ??????????????? ?????????????????? ????????????.. ????????????
					if ("Y".equals(orderItem.getEarnPointFlag()) && "N".equals(orderItem.getReturnPointFlag())) {
						frequencyPointAmount += point;
					}
				}

				if (orderItem.getRemittanceId() > 0) {
					orderItem.setRemittanceExpectedDate(remittanceMapper.getRemittanceDateBySellerId(orderItem.getSellerId()));
					orderItem.setRemittanceStatusCode("2");

					if (remittanceMapper.updateItemRemittanceInfo(orderItem) == 0) {
						throw new OrderException();
					}
				} else {
					orderMapper.updateReturnPointFlag(orderItem);
				}

				// ????????? ?????? List ?????? ??? ?????? ???????????? ?????? ?????? ?????????
				returnCoupons = makeReturnCouponsAndStockRestorationMap(orderItem, stockMap);

				// ????????? ??????
				try {
					orderGiftItemService
							.returnOrderGiftItem(orderItem.getOrderCode(), orderItem.getOrderSequence(), orderItem.getItemSequence());
				} catch (Exception e) {
					throw new OrderException("????????? ??????????????? ????????? ??????????????????.");
				}

				orderItem.setReturnApply(returnApply);
			}

			// CJH 2016.11.12 ????????? ????????? ?????? ??????
			if (orderRefund.getUserId() > 0 && frequencyPointAmount > 0) {

			    try {

                    AvailablePoint availablePoint = pointService.getAvailablePointByUserId(orderRefund.getUserId(), PointUtils.DEFAULT_POINT_CODE);

                    int retentionPoint = 0;

                    if (availablePoint != null && availablePoint.getAvailablePoint() > 0) {
                        retentionPoint = availablePoint.getAvailablePoint();
                    }

                    if (retentionPoint < frequencyPointAmount) {
                        notFrequencyPointAmount = frequencyPointAmount - retentionPoint;
                    }

                    PointUsed pointUsed = new PointUsed();
                    pointUsed.setOrderCode(orderRefund.getOrderCode());
                    pointUsed.setPoint(frequencyPointAmount - notFrequencyPointAmount);
                    pointUsed.setDetails("???????????? :" + orderRefund.getOrderCode() + " ?????? ????????? " + MessageUtils.getMessage("M00246") + " ??????");
                    pointService.deductedPoint(pointUsed, orderRefund.getUserId(), PointUtils.DEFAULT_POINT_CODE);

                    // ???????????? ?????? ???????????? ????????? -????????????
                    if (notFrequencyPointAmount > 0) {

                        Point point = new Point();
                        point.setUserId(orderRefund.getUserId());
                        point.setPoint(-notFrequencyPointAmount);
                        point.setReason("?????? ???????????? :" + orderRefund.getOrderCode() + " ?????? ???????????? " + MessageUtils.getMessage("M00246") + "??? ???????????? ???????????? ?????? ???????????????.");
                        point.setOrderCode(orderRefund.getOrderCode());
                        point.setPointType(PointUtils.DEFAULT_POINT_CODE);

                        pointService.earnPoint("return", point);
                    }
                } catch (PointException e) {
                    throw new OrderException(e.getMessage());
                }
			}
		}

		// ?????? ????????? ???????????? ????????????.
		List<OrderCancelApply> cancelApplyList = orderClaimApplyMapper.getOrderCancelApplyListByRefundCode(orderRefund.getRefundCode());
		if (cancelApplyList != null) {

			for(OrderCancelApply cancelApply : cancelApplyList) {
                if ("??????".equals(cancelApply.getCancelReasonText())) {
                    refundReason = cancelApply.getCancelReasonDetail();
                } else {
                    refundReason = cancelApply.getCancelReasonText();
                }

				orderItem = cancelApply.getOrderItem();

				// ????????? ?????? List ?????? ??? ?????? ???????????? ?????? ?????? ?????????
				returnCoupons = makeReturnCouponsAndStockRestorationMap(orderItem, stockMap);

				// ????????? ??????
				try {
					orderGiftItemService
							.cancelOrderGiftItem(orderItem.getOrderCode(), orderItem.getOrderSequence(), orderItem.getItemSequence());
				} catch (Exception e) {
					throw new OrderException("????????? ??????????????? ????????? ??????????????????.");
				}

				orderItem.setCancelApply(cancelApply);
			}

		}

		// ????????? ?????? ??????
		returnUserCoupons(returnCoupons, orderRefund.getUserId(), orderRefund.getOrderCode());

		// ?????? ????????? ?????? (????????? ??????) - ????????? ?????????
		int returnPayAmount = 0;

		if (editPayment.getChangePayments() != null) {
			for(ChangePayment changePayment : editPayment.getChangePayments()) {

				int paymentSequence = changePayment.getPaymentSequence();
				int cancelAmount = changePayment.getCancelAmount();
				if (changePayment.getCancelAmount() > 0) {

					OrderPayment eqPayment = null;
					for(OrderPayment p : payments) {
						if (p.getPaymentSequence() == paymentSequence) {
							eqPayment = p;
							break;
						}
					}

					if (eqPayment == null) {
						throw new OrderException();
					}

					if (eqPayment.getRemainingAmount() < cancelAmount) {
						throw new OrderException("??????????????? ???????????????.");
					}

					String approvalType = eqPayment.getApprovalType();
					if (PointUtils.isPointType(approvalType)) {

						if (orderRefund.getUserId() == 0) {
							throw new OrderException("????????? " + MessageUtils.getMessage("M00246") + " ?????? ??????.");
						}

					} else {
						returnPayAmount += cancelAmount;
					}

					totalEditAmount += cancelAmount;
				}
			}
		}

		// [SKC] ?????? ????????? ?????? ?????? ?????????????????? ?????? ???????????? ??????
		if (editPayment.getRefundAmount() != null && editPayment.getRefundAmount() > 0) {
			totalEditAmount += editPayment.getRefundAmount();

			returnPayAmount += editPayment.getRefundAmount();
		}


		if (totalReturnAmount != totalEditAmount) {
			throw new OrderException("?????? ????????? ???????????????.");
		}

		try {
            editPayment.setRefundReason(refundReason);
			orderService.changePayment(editPayment);

		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new OrderException(e.getMessage());
		}

		if (UserUtils.isManagerLogin()) {
			orderRefund.setProcessManagerUserName(UserUtils.getManagerName());
		}

		// ?????? ????????? ??????
		orderRefundMapper.updateRefundStatus(orderRefund);

		// ?????? ?????? ???????????? ?????? ?????? ??????
		List<OrderItem> logOrderItems
				= orderService.getOrderItemListForOrderLog(orderRefund.getOrderCode(), orderRefund.getOrderSequence());

		// ?????? ????????? ??????
		orderRefundMapper.updateRefundFinishedForItem(orderRefund);

		// ?????? ??????
		try {

			List<Integer> logItemSequences= new ArrayList<>();

			for (OrderCancelApply cancelApply : cancelApplyList) {
				logItemSequences.add(cancelApply.getItemSequence());
			}
			for (OrderReturnApply returnApply : returnApplyList) {
				logItemSequences.add(returnApply.getItemSequence());
			}

			for (OrderItem logOrderItem : logOrderItems) {
				for (int itemSequence : logItemSequences) {
					if (itemSequence == logOrderItem.getItemSequence()) {
						orderService.insertOrderLog(
								OrderLogType.ORDER_REFUND,
								logOrderItem.getOrderCode(),
								logOrderItem.getOrderSequence(),
								logOrderItem.getItemSequence(),
								logOrderItem.getOrderStatus()
						);
					}
				}
			}

		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			log.error("ERROR: {}", e.getMessage(), e);
		}

		// ????????? ???????????? ??????
		orderClaimApplyMapper.updateCancelStatusForRefund(orderRefund.getRefundCode());
		orderClaimApplyMapper.updateReturnStatusForRefund(orderRefund.getRefundCode());

		// ?????? ????????? ???????????? ?????? (oracle??? mysql????????? ??????) [2017-07-12]minae.yun
		if ("oracle".equals(SalesonProperty.getConfigDatabaseVendor())) {
			List<OrderItem> refundItemList = remittanceMapper.getOrderShippingForRemittance(orderRefund.getRefundCode());

			for (int i=0; i<refundItemList.size(); i++) {
				remittanceMapper.updateResidualShippingRemittanceByRefundCodeForOracle(refundItemList.get(i));
			}

		} else {
			remittanceMapper.updateResidualShippingRemittanceByRefundCode(orderRefund.getRefundCode());
		}

		if (returnPayAmount > 0) {
			orderRefund.setReturnPayAmount(returnPayAmount);

			String templateCode = "order_refund";
			Ums ums = umsService.getUms(templateCode);

			ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(orderRefund.getUserId());
			Config config = configMapper.getShopConfig(Config.SHOP_CONFIG_ID);
			String phoneNumber = orderService.getMobileByOrderCode(orderParam);

			unifiedMessagingService.sendMessage(new OrderRefundApproval(ums, orderRefund, orderRefund.getUserId(), config, phoneNumber, applicationInfo));
		}


		orderParam.setConditionType("OPMANAGER");
		orderParam.setAdditionItemFlag("");
		Order order = orderService.getOrderByParam(orderParam);

		// ERP ????????? ?????? ?????? ????????? ??????
		cancelApplyList.forEach(apply -> {

			for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
				for (OrderItem erpOrderItem : orderShippingInfo.getOrderItems()) {

					if (apply.getItemSequence() == erpOrderItem.getItemSequence()) {
						erpOrderItem.setCancelApply(apply);
					}
				}
			}
		});

		// ERP ????????? ?????? ?????? ????????? ??????
		returnApplyList.forEach(apply -> {

			for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
				for (OrderItem erpOrderItem : orderShippingInfo.getOrderItems()) {

					if (apply.getItemSequence() == erpOrderItem.getItemSequence()) {
						erpOrderItem.setReturnApply(apply);
					}
				}
			}
		});


		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			List<OrderItem> erpOrderItems = new ArrayList<>();

			for(OrderItem item : info.getOrderItems()) {
				if (item.getCancelApply() != null && item.getCancelApply().getItemSequence() == item.getItemSequence()) {
					erpOrderItems.add(item);

				} else if (item.getReturnApply() != null && item.getReturnApply().getItemSequence() == item.getItemSequence()) {
					erpOrderItems.add(item);
				}
			}

			info.setOrderItems(erpOrderItems);
		}

		// ?????? ?????? ??? ERP ??????
		ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
		erpService.saveOrderListGet(erpOrder);
	}

	@Override
	public void cancelRefund(String refundCode) {

		List<OrderCancelApply> cancelApplyList = orderClaimApplyMapper.getOrderRefundCancel(refundCode);

		if (!cancelApplyList.isEmpty()) {
			for(OrderCancelApply orderCancelApply : cancelApplyList) {

				// ?????? ????????? ?????? ?????? ??????
				OrderItem logOrderItem
						= orderService.getOrderItemForOrderLog(orderCancelApply.getOrderCode(), orderCancelApply.getOrderSequence(), orderCancelApply.getItemSequence());

                // 2019.02.25 Son Jun-Eu ?????? ?????? ?????? ??? OrderAddPayment row??????
                orderAddPaymentMapper.deleteOrderAddPaymentByCancel(orderCancelApply);

                orderCancelApply.setCancelRefusalReasonText("?????? ?????? ??????");
                orderCancelApply.setRefundCode(null);
                orderCancelApply.setClaimStatus("01");
                orderCancelApply.setRefundCancelFlag("Y");

				orderClaimApplyMapper.updateOrderCancelApply(orderCancelApply);
				orderClaimApplyMapper.updateClaimQuantityForCancel(orderCancelApply);

				orderCancelApply.setRefundCode(refundCode);
				orderRefundMapper.deleteOrderRefundInfo(orderCancelApply);

                OrderShipping orderShipping = new OrderShipping();
                orderShipping.setOrderCode(orderCancelApply.getOrderCode());
                orderShipping.setOrderSequence(orderCancelApply.getOrderSequence());
                orderShipping.setShippingSequence(orderCancelApply.getShippingSequence());

				// 2019.04.25 sje ?????? ?????? ?????? ??? ????????? ????????? ????????? ???????????? ?????????
                orderShippingMapper.updateShippingForCancelRefund(orderShipping);

				// ?????? ??????
				try {
					orderService.insertOrderLog(
							OrderLogType.ORDER_REFUND,
							orderCancelApply.getOrderCode(),
							orderCancelApply.getOrderSequence(),
							orderCancelApply.getItemSequence(),
							logOrderItem.getOrderStatus()
					);
				} catch (Exception e) {
					log.error("ERROR: {}", e.getMessage(), e);
				}
			}
		} else {

			List<OrderReturnApply> returnApplyList = orderClaimApplyMapper.getOrderRefundReturn(refundCode);

			if (!returnApplyList.isEmpty()) {
				for (OrderReturnApply orderReturnApply : returnApplyList) {

					// ?????? ????????? ?????? ?????? ??????
					OrderItem logOrderItem
							= orderService.getOrderItemForOrderLog(orderReturnApply.getOrderCode(), orderReturnApply.getOrderSequence(), orderReturnApply.getItemSequence());

                    // 2019.02.25 Son Jun-Eu ?????? ?????? ?????? ??? OrderAddPayment row??????
                    orderAddPaymentMapper.deleteOrderAddPaymentByReturn(orderReturnApply);

                    orderReturnApply.setReturnRefusalReasonText("?????? ?????? ??????");
                    orderReturnApply.setRefundCode(null);
                    orderReturnApply.setReturnShippingNumber(null);
                    orderReturnApply.setReturnShippingCompanyName(null);
                    orderReturnApply.setClaimStatus("01");
                    orderReturnApply.setRefundCancelFlag("Y");

					orderClaimApplyMapper.updateOrderReturnApply(orderReturnApply);
					orderClaimApplyMapper.updateClaimQuantityForReturn(orderReturnApply);

					orderReturnApply.setRefundCode(refundCode);
					orderRefundMapper.deleteOrderRefundInfoByReturn(orderReturnApply);

					// ?????? ??????
					try {
						orderService.insertOrderLog(
								OrderLogType.ORDER_REFUND,
								orderReturnApply.getOrderCode(),
								orderReturnApply.getOrderSequence(),
								orderReturnApply.getItemSequence(),
								logOrderItem.getOrderStatus()
						);
					} catch (Exception e) {
						log.error("ERROR: {}", e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
	 * ????????? ?????? ?????? ?????? ??? ?????? ?????????????????? ?????? ??????
	 * @param orderItem
	 * @param stockMap
	 * @return
	 */
	private List<Integer> makeReturnCouponsAndStockRestorationMap(OrderItem orderItem, HashMap<String, Integer> stockMap) {

		List<Integer> returnCoupons = new ArrayList<>();

		if (orderItem != null) {

			if (orderItem.getCouponUserId() > 0) {
				returnCoupons.add(orderItem.getCouponUserId());
			}

			if (orderItem.getAddCouponUserId() > 0) {
				returnCoupons.add(orderItem.getAddCouponUserId());
			}

			// ?????? ?????????????????? ?????? ?????????
			orderService.makeStockRestorationMap(stockMap, orderItem);
		}


		return returnCoupons;
	}

	/**
	 * ????????? ?????? ??????
	 * @param returnCoupons
	 */
	private void returnUserCoupons(List<Integer> returnCoupons, Long userId, String orderCode) {
		// ?????? ??????
		if (!returnCoupons.isEmpty()) {

			OrderParam orderParam = new OrderParam();
			orderParam.setOrderCode(orderCode);
			orderParam.setOrderSequence(0);
			orderParam.setConditionType("OPMANAGER");

			// ???????????? ?????? ??????
			List<OrderItem> orderItemList = orderMapper.getOrderItemListByParam(orderParam);

			for(Integer couponUserId : returnCoupons) {
				List<String> orderStatusCodes = Arrays.asList("60", "70", "65", "75"); // ?????? ?????? ?????? ?????? ??????
				boolean refundAll =  (int)orderItemList.stream()
						.filter(i -> couponUserId == i.getCouponUserId() && !orderStatusCodes.contains(i.getOrderStatus()))
						.count() > 0 ? false : true;

				if (userId > 0 && refundAll) {
					OrderCoupon orderCoupon = new OrderCoupon();
					orderCoupon.setUserId(userId);
					orderCoupon.setCouponUserId(couponUserId);
					couponMapper.updateCouponUserReturnsByOrderCouponUser(orderCoupon);
				}

			}
		}
	}



}
