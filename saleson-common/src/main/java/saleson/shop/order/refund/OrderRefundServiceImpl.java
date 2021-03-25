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
					orderAddPayment.setSubject("주문 취소로 인한 배송비 변경");
					orderAddPayment.setIssueCode("CANCEL-ADD-SHIPPING-" + orderShipping.getShippingSequence());
					orderAddPayments.add(orderAddPayment);

					// 배송비 추가?
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
				seller.setSellerName("업체정보 없음");
			}

			details.add(new OrderRefundDetail(seller, cancelApplyList, null, orderAddPayments));
		}

		// 총 주문수량
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


			// 추가구성상품 (view에서 본품과 추가구성상품을 묶어서 보여주기 위한 용도)
			List<OrderCancelApply> additionApplyList =
					cancelApplyList.stream().filter(cancel -> "Y".equals(cancel.getOrderItem().getAdditionItemFlag())).collect(Collectors.toList());
			orderClaimApplyService.setOrderAdditionItemForOrderCancelApply(cancelApplyList, additionApplyList);
		}

		if (returnApplyList != null) {
			for(OrderReturnApply orderReturnApply : returnApplyList) {
				appendSellerIds(sellerIds, orderReturnApply.getShipmentReturnSellerId());
			}

			orderClaimApplyService.setOrderGiftItemForOrderReturnApply(returnApplyList);

			// 추가구성상품 (view에서 본품과 추가구성상품을 묶어서 보여주기 위한 용도)
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
				seller.setSellerName("업체정보 없음");
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

        // 2019.04.25 sje 환불신청 취소할 경우를 대비해서 배송비 정보 백업
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

						// 환불
						// 정산 마스터 ID가 있는경우 정산이 있는경우임 - 마이너스 정산 으로 만들어야함
						if (shipping.getRemittanceId() > 0) {
							shipping.setConditionType("UPDATE_REMITTANCE");
							shipping.setRemittanceExpectedDate(remittanceMapper.getRemittanceDateBySellerId(shipping.getSellerId()));
							shipping.setRemittanceStatusCode("2");

							shipping.setReturnFlag("Y");

							// 환불금액을 정산 대상 금액으로 셋팅 - 기존 정산 금액의 정보가 갱신된다.
							shipping.setRemittanceAmount(addPayment.getAmount());
						} else {
							shipping.setRemittanceAmount(shipping.getRemittanceAmount() - addPayment.getAmount());
						}

						// 사용자 결제 배송비랑 저장된 금액이 다르면 어쩌지??
						if (shipping.getRealShipping() != addPayment.getAmount()) {
							throw new OrderException();
						}

						shipping.setRealShipping(shipping.getRealShipping() - addPayment.getAmount());
						shipping.setPayShipping(shipping.getPayShipping() - addPayment.getAmount());
						shipping.setReturnShipping(shipping.getReturnShipping() + addPayment.getAmount());

					} else {

						// 추가 결제
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

					// 삭제
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
//                        // 추가 - 기존 배송비에 추가금을 더함
//                        shipping.setRealShipping(shipping.getRealShipping() + addPayment.getAmount());
//                        shipping.setPayShipping(shipping.getPayShipping() + addPayment.getAmount());
//                        shipping.setRemittanceAmount(shipping.getRemittanceAmount() + addPayment.getAmount());
//                        shipping.setReturnShipping(shipping.getReturnShipping());
//                    } else {
//
//                        // 환불 - 기존 배송비에 추가금을 뺌
//                        shipping.setRealShipping(shipping.getRealShipping() - addPayment.getAmount());
//                        shipping.setPayShipping(shipping.getPayShipping() - addPayment.getAmount());
//                        shipping.setRemittanceAmount(shipping.getRemittanceAmount() - addPayment.getAmount());
//                        shipping.setReturnShipping(shipping.getReturnShipping() + addPayment.getAmount());
//                    }

					if (orderShippingMapper.updateCancelShipping(shipping) == 0) {
						throw new OrderException();
					}

					// 삭제
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

		// 환불 상품은 정산 완료 여부를 파악해서 마이너스 정산을 만들어야 한다.
		List<OrderReturnApply> returnApplyList = orderClaimApplyMapper.getOrderReturnApplyListByRefundCode(orderRefund.getRefundCode());
		if (returnApplyList != null) {

			int notFrequencyPointAmount = 0;
			int frequencyPointAmount = 0;

			for(OrderReturnApply returnApply : returnApplyList) {
                if ("기타".equals(returnApply.getReturnReasonText())) {
                    refundReason = returnApply.getReturnReasonDetail();
                } else {
                    refundReason = returnApply.getReturnReasonText();
                }

				orderItem = returnApply.getOrderItem();

				if (orderRefund.getUserId() > 0) {
					int point = orderItem.getEarnPoint() * orderItem.getQuantity();

					// 포인트가 지급되었고 아직회수되지 않았다면.. 회수하자
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

				// 복원할 쿠폰 List 생성 및 재고 복원해야 하는 목록 만들기
				returnCoupons = makeReturnCouponsAndStockRestorationMap(orderItem, stockMap);

				// 사은품 반품
				try {
					orderGiftItemService
							.returnOrderGiftItem(orderItem.getOrderCode(), orderItem.getOrderSequence(), orderItem.getItemSequence());
				} catch (Exception e) {
					throw new OrderException("사은품 반품처리시 오류가 발생했습니다.");
				}

				orderItem.setReturnApply(returnApply);
			}

			// CJH 2016.11.12 환불시 포인트 회수 처리
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
                    pointUsed.setDetails("주문번호 :" + orderRefund.getOrderCode() + " 에서 적립된 " + MessageUtils.getMessage("M00246") + " 회수");
                    pointService.deductedPoint(pointUsed, orderRefund.getUserId(), PointUtils.DEFAULT_POINT_CODE);

                    // 회수하지 못한 포인트가 있으면 -적립처리
                    if (notFrequencyPointAmount > 0) {

                        Point point = new Point();
                        point.setUserId(orderRefund.getUserId());
                        point.setPoint(-notFrequencyPointAmount);
                        point.setReason("환불 주문번호 :" + orderRefund.getOrderCode() + " 에서 회수가능 " + MessageUtils.getMessage("M00246") + "가 부족하여 마이너스 처리 되었습니다.");
                        point.setOrderCode(orderRefund.getOrderCode());
                        point.setPointType(PointUtils.DEFAULT_POINT_CODE);

                        pointService.earnPoint("return", point);
                    }
                } catch (PointException e) {
                    throw new OrderException(e.getMessage());
                }
			}
		}

		// 취소 상품은 재고량을 복원한다.
		List<OrderCancelApply> cancelApplyList = orderClaimApplyMapper.getOrderCancelApplyListByRefundCode(orderRefund.getRefundCode());
		if (cancelApplyList != null) {

			for(OrderCancelApply cancelApply : cancelApplyList) {
                if ("기타".equals(cancelApply.getCancelReasonText())) {
                    refundReason = cancelApply.getCancelReasonDetail();
                } else {
                    refundReason = cancelApply.getCancelReasonText();
                }

				orderItem = cancelApply.getOrderItem();

				// 복원할 쿠폰 List 생성 및 재고 복원해야 하는 목록 만들기
				returnCoupons = makeReturnCouponsAndStockRestorationMap(orderItem, stockMap);

				// 사은품 취소
				try {
					orderGiftItemService
							.cancelOrderGiftItem(orderItem.getOrderCode(), orderItem.getOrderSequence(), orderItem.getItemSequence());
				} catch (Exception e) {
					throw new OrderException("사은품 취소처리시 오류가 발생했습니다.");
				}

				orderItem.setCancelApply(cancelApply);
			}

		}

		// 사용자 쿠폰 반환
		returnUserCoupons(returnCoupons, orderRefund.getUserId(), orderRefund.getOrderCode());

		// 실제 환불될 금액 (포인트 제외) - 메시지 발송용
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
						throw new OrderException("결제잔액을 확인하세요.");
					}

					String approvalType = eqPayment.getApprovalType();
					if (PointUtils.isPointType(approvalType)) {

						if (orderRefund.getUserId() == 0) {
							throw new OrderException("비회원 " + MessageUtils.getMessage("M00246") + " 사용 에러.");
						}

					} else {
						returnPayAmount += cancelAmount;
					}

					totalEditAmount += cancelAmount;
				}
			}
		}

		// [SKC] 결제 수단과 상관 없이 은행입금으로 환불 처리하는 경우
		if (editPayment.getRefundAmount() != null && editPayment.getRefundAmount() > 0) {
			totalEditAmount += editPayment.getRefundAmount();

			returnPayAmount += editPayment.getRefundAmount();
		}


		if (totalReturnAmount != totalEditAmount) {
			throw new OrderException("환불 금액을 확인하세요.");
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

		// 환불 마스터 수정
		orderRefundMapper.updateRefundStatus(orderRefund);

		// 주문 로그 데이터를 위해 임시 조회
		List<OrderItem> logOrderItems
				= orderService.getOrderItemListForOrderLog(orderRefund.getOrderCode(), orderRefund.getOrderSequence());

		// 상품 데이터 수정
		orderRefundMapper.updateRefundFinishedForItem(orderRefund);

		// 주문 로그
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

		// 클레임 신청정보 수정
		orderClaimApplyMapper.updateCancelStatusForRefund(orderRefund.getRefundCode());
		orderClaimApplyMapper.updateReturnStatusForRefund(orderRefund.getRefundCode());

		// 잔여 배송비 정산일자 셋팅 (oracle과 mysql로직이 다름) [2017-07-12]minae.yun
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

		// ERP 연동을 위한 취소 데이터 세팅
		cancelApplyList.forEach(apply -> {

			for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
				for (OrderItem erpOrderItem : orderShippingInfo.getOrderItems()) {

					if (apply.getItemSequence() == erpOrderItem.getItemSequence()) {
						erpOrderItem.setCancelApply(apply);
					}
				}
			}
		});

		// ERP 연동을 위한 환불 데이터 세팅
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

		// 환불 승인 시 ERP 연동
		ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
		erpService.saveOrderListGet(erpOrder);
	}

	@Override
	public void cancelRefund(String refundCode) {

		List<OrderCancelApply> cancelApplyList = orderClaimApplyMapper.getOrderRefundCancel(refundCode);

		if (!cancelApplyList.isEmpty()) {
			for(OrderCancelApply orderCancelApply : cancelApplyList) {

				// 로그 추가로 인해 임시 조회
				OrderItem logOrderItem
						= orderService.getOrderItemForOrderLog(orderCancelApply.getOrderCode(), orderCancelApply.getOrderSequence(), orderCancelApply.getItemSequence());

                // 2019.02.25 Son Jun-Eu 환불 취소 신청 시 OrderAddPayment row삭제
                orderAddPaymentMapper.deleteOrderAddPaymentByCancel(orderCancelApply);

                orderCancelApply.setCancelRefusalReasonText("환불 신청 취소");
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

				// 2019.04.25 sje 환불 신청 취소 시 배송비 정보를 재계산 이전으로 되돌림
                orderShippingMapper.updateShippingForCancelRefund(orderShipping);

				// 로그 추가
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

					// 로그 추가로 인해 임시 조회
					OrderItem logOrderItem
							= orderService.getOrderItemForOrderLog(orderReturnApply.getOrderCode(), orderReturnApply.getOrderSequence(), orderReturnApply.getItemSequence());

                    // 2019.02.25 Son Jun-Eu 환불 취소 신청 시 OrderAddPayment row삭제
                    orderAddPaymentMapper.deleteOrderAddPaymentByReturn(orderReturnApply);

                    orderReturnApply.setReturnRefusalReasonText("환불 신청 취소");
                    orderReturnApply.setRefundCode(null);
                    orderReturnApply.setReturnShippingNumber(null);
                    orderReturnApply.setReturnShippingCompanyName(null);
                    orderReturnApply.setClaimStatus("01");
                    orderReturnApply.setRefundCancelFlag("Y");

					orderClaimApplyMapper.updateOrderReturnApply(orderReturnApply);
					orderClaimApplyMapper.updateClaimQuantityForReturn(orderReturnApply);

					orderReturnApply.setRefundCode(refundCode);
					orderRefundMapper.deleteOrderRefundInfoByReturn(orderReturnApply);

					// 로그 추가
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
	 * 사용자 반환 예정 쿠폰 및 재고 복원해야하는 목록 생성
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

			// 재고 복원해야하는 목록 만들기
			orderService.makeStockRestorationMap(stockMap, orderItem);
		}


		return returnCoupons;
	}

	/**
	 * 사용자 쿠폰 반환
	 * @param returnCoupons
	 */
	private void returnUserCoupons(List<Integer> returnCoupons, Long userId, String orderCode) {
		// 쿠폰 반환
		if (!returnCoupons.isEmpty()) {

			OrderParam orderParam = new OrderParam();
			orderParam.setOrderCode(orderCode);
			orderParam.setOrderSequence(0);
			orderParam.setConditionType("OPMANAGER");

			// 주문상품 목록 조회
			List<OrderItem> orderItemList = orderMapper.getOrderItemListByParam(orderParam);

			for(Integer couponUserId : returnCoupons) {
				List<String> orderStatusCodes = Arrays.asList("60", "70", "65", "75"); // 제외 대상 주문 상태 코드
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
