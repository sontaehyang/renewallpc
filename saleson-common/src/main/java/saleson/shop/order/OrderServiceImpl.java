package saleson.shop.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lgcns.kmpay.dto.DealApproveDto;
import com.onlinepowers.framework.common.ServiceType;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.popbill.api.CashbillService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.*;
import saleson.common.notification.ApplicationInfoService;
import saleson.common.notification.UnifiedMessagingService;
import saleson.common.opmanager.count.OpmanagerCount;
import saleson.common.utils.*;
import saleson.erp.domain.ErpOrderType;
import saleson.erp.service.ErpOrder;
import saleson.erp.service.ErpService;
import saleson.model.*;
import saleson.model.campaign.ApplicationInfo;
import saleson.seller.main.SellerMapper;
import saleson.seller.main.domain.Seller;
import saleson.shop.accountnumber.AccountNumberService;
import saleson.shop.accountnumber.domain.AccountNumber;
import saleson.shop.cart.CartMapper;
import saleson.shop.cart.domain.OrderQuantity;
import saleson.shop.cart.support.CartParam;
import saleson.shop.categories.CategoriesMapper;
import saleson.shop.categories.domain.ProductsRepCategories;
import saleson.shop.config.ConfigLogService;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.CouponMapper;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.domain.OrderCoupon;
import saleson.shop.coupon.domain.api.BuyCouponList;
import saleson.shop.coupon.domain.api.BuyShippingCouponInfo;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.deliverycompany.DeliveryCompanyMapper;
import saleson.shop.deliverycompany.DeliveryCompanyService;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.giftitem.GiftItemService;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.item.domain.api.ItemInfo;
import saleson.shop.mailconfig.MailConfigService;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.OrderMail;
import saleson.shop.naverpay.NaverPaymentApi;
import saleson.shop.order.api.ApiOrderList;
import saleson.shop.order.api.OrderDetail;
import saleson.shop.order.api.PaymentInfo;
import saleson.shop.order.api.ShippingInfo;
import saleson.shop.order.claimapply.OrderClaimApplyMapper;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderExchangeApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.claimapply.domain.*;
import saleson.shop.order.claimapply.support.ClaimException;
import saleson.shop.order.claimapply.support.ExchangeApply;
import saleson.shop.order.claimapply.support.ReturnApply;
import saleson.shop.order.domain.*;
import saleson.shop.order.giftitem.OrderGiftItemService;
import saleson.shop.order.payment.OrderPaymentMapper;
import saleson.shop.order.pg.PgService;
import saleson.shop.order.pg.cj.domain.CjResult;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.pg.easypay.domain.EasypayRequest;
import saleson.shop.order.pg.kcp.domain.KcpRequest;
import saleson.shop.order.pg.payco.domain.PayApprovalResult;
import saleson.shop.order.pg.payco.domain.PaymentDetail;
import saleson.shop.order.pg.payco.domain.ReservationResponse;
import saleson.shop.order.refund.OrderRefundService;
import saleson.shop.order.shipping.OrderShippingMapper;
import saleson.shop.order.shipping.support.ShippingParam;
import saleson.shop.order.shipping.support.ShippingReadyParam;
import saleson.shop.order.support.*;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.AvailablePoint;
import saleson.shop.point.domain.Point;
import saleson.shop.point.domain.PointUsed;
import saleson.shop.point.support.OrderPointParam;
import saleson.shop.receipt.PopbillService;
import saleson.shop.receipt.ReceiptService;
import saleson.shop.receipt.support.CashbillIssueRepository;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillRepository;
import saleson.shop.receipt.support.CashbillResponse;
import saleson.shop.remittance.RemittanceMapper;
import saleson.shop.sendmaillog.SendMailLogService;
import saleson.shop.sendmaillog.domain.SendMailLog;
import saleson.shop.sendsmslog.SendSmsLogService;
import saleson.shop.shipmentreturn.ShipmentReturnMapper;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;
import saleson.shop.shipmentreturn.support.ShipmentReturnParam;
import saleson.shop.smsconfig.SmsConfigService;
import saleson.shop.ums.UmsService;
import saleson.shop.ums.support.OrderBank;
import saleson.shop.ums.support.OrderDelivering;
import saleson.shop.ums.support.OrderNew;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.userdelivery.UserDeliveryService;
import saleson.shop.userdelivery.domain.UserDelivery;
import saleson.shop.userlevel.UserLevelMapper;
import saleson.shop.userlevel.domain.UserLevel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static saleson.common.Const.DATETIME_FORMAT;
import static saleson.common.Const.DATE_FORMAT;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	public static final String ERROR_ORDER_CODE = "ERROR: ORDER_CODE({})";
	public static final String ERROR_MARKER = "ERROR: {}";

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderPaymentMapper orderPaymentMapper;

	@Autowired
	private OrderShippingMapper orderShippingMapper;

	@Autowired
	private AccountNumberService accountNumberService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private PointService pointService;

	@Autowired
	private UserDeliveryService userDeliveryService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private SequenceService sequenceService;

	@Autowired
	private CategoriesMapper categoriesMapper;

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
	private CartMapper cartMapper;

	@Autowired
	private DeliveryCompanyService deliveryCompanyService;

	@Autowired
	private ShipmentReturnMapper shipmentReturnMapper;

	@Autowired
	private DeliveryCompanyMapper deliveryCompanyMapper;

	@Autowired
	private CouponMapper couponMapper;

	@Autowired
	private MailConfigService mailConfigService;

	@Autowired
	private SmsConfigService smsConfigService;

	@Autowired
	private SendMailLogService sendMailLogService;

	@Autowired
	private SendSmsLogService sendSmsLogService;

	@Autowired
	private RemittanceMapper remittanceMapper;

	@Autowired
	private UserLevelMapper userLevelMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderClaimApplyMapper orderClaimApplyMapper;

	@Autowired
	private SellerMapper sellerMapper;

	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderRefundService orderRefundService;

	@Autowired
	private OrderLogRepository orderLogRepository;

	@Autowired
	private ConfigLogService configLogService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private CashbillService cashbillService;

    @Autowired
    private PopbillService popbillService;

    @Autowired
    private CashbillRepository cashbillRepository;

    @Autowired
    private CashbillIssueRepository cashbillIssueRepository;

    @Autowired
	private UmsService umsService;

    @Autowired
	private GiftItemService giftItemService;

    @Autowired
	private OrderGiftItemService orderGiftItemService;

    @Autowired
	Environment environment;

    @Autowired
	private UnifiedMessagingService unifiedMessagingService;

    @Autowired
	private ApplicationInfoService applicationInfoService;

	@Autowired
    private ConfigPgService configPgService;

    @Autowired
    private NaverPaymentApi naverPaymentApi;

    @Autowired
    private ErpService erpService;

	@Override
	public OrderShippingInfo getOrderShippingInfoByParam(OrderParam orderParam) {
		return orderMapper.getOrderShippingInfoByParam(orderParam);
	}

	@Override
	public OrderItem getOrderItemByParam(OrderParam orderParam) {
		return orderMapper.getOrderItemByParam(orderParam);
	}

	@Override
	public List<OrderItem> getOrderItemListByParam(OrderParam orderParam) {
		return orderMapper.getOrderItemListByParam(orderParam);
	}

	@Override
	public String getIslandTypeByZipcode(String zipcode) {

		if (StringUtils.isEmpty(zipcode)) {
			return "";
		}

		return orderMapper.getIslandTypeByZipcode(zipcode);
	}

	/**
	 * 취소 신청시 환불 계좌 정보를 입력받아야 하는가?
	 * @param payments
	 * @return
	 */
	private boolean getIsWriteBankInfo(List<OrderPayment> payments) {
		boolean isWriteBankInfo = false;
		for(OrderPayment orderPayment : payments) {
			if ("bank".equals(orderPayment.getApprovalType()) || "vbank".equals(orderPayment.getApprovalType())) {
				if (orderPayment.getRemainingAmount() > 0) {
					isWriteBankInfo = true;
					break;
				}
			} else {
				// 부분취소가 불가능한 주문이 있는경우 환불 계좌를 입력받아야 함.
				//if ("N".equals(orderPayment.getPartCancelFlag())) {
				//	isWriteBankInfo = true;
				//	break;
				//}
			}
		}

		return isWriteBankInfo;
	}

	/**
	 * 취소 신청시 환불 계좌 은행 리스트 KEY
	 * @param payments
	 * @return
	 */
	private String getBankListKey(List<OrderPayment> payments) {
		String bankListKey = "DEFAULT_BANK_LIST";

		for(OrderPayment orderPayment : payments) {
			if ("vbank".equals(orderPayment.getApprovalType())) {
				OrderPgData orderPgData = orderPayment.getOrderPgData();
				if (orderPgData != null) {
					if ("cj".equals(orderPgData.getPgServiceType())) {
						bankListKey = "CJ_BANK_LIST";
						break;
					}
				}
			}
		}

		return bankListKey;
	}

	@Override
	public List<OrderPayment> getWaitingDepositListByParam(OrderParam orderParam) {

		int totalCount = orderPaymentMapper.getWaitingDepositCountByParam(orderParam);
		String conditionType = orderParam.getConditionType();
		if (orderParam.getItemsPerPage() == 10) {
			orderParam.setItemsPerPage(50);
		}

		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());
		orderParam.setPagination(pagination);
		orderParam.setLanguage(CommonUtils.getLanguage());

		List<OrderPayment> list = orderPaymentMapper.getWaitingDepositListByParam(orderParam);
		if (list != null) {
			for(OrderPayment payment : list) {
				isPaymentVerificationCancel(payment);
				// 엑셀다운로드가 아닌 경우 무조건 마스킹
				if(!(conditionType != null && conditionType.equals("ORDER-DETAIL-EXCEL"))){
					waitingDepositMaskingDataSet(payment);
				} else if(!(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS"))){
					waitingDepositMaskingDataSet(payment);
				}
			}
		}

		return list;
	}

	private void waitingDepositMaskingDataSet(OrderPayment payment){
		String loginId = payment.getLoginId();				// 아이디
		String userName = payment.getUserName();			// 회원명
		String buyerName = payment.getBuyerName();			// 주문자
		String bankInName = payment.getBankInName();		// 입금자명의
		String bankVirtualNo = payment.getBankVirtualNo();	// 입금계좌

		if(loginId != null){
			payment.setLoginId(UserUtils.reMasking(loginId, "email"));
		}
		if(userName != null){
			payment.setUserName(UserUtils.reMasking(userName, "name"));
		}
		if(buyerName != null){
			payment.setBuyerName(UserUtils.reMasking(buyerName, "name"));
		}
		if(bankInName != null){
			payment.setBankInName(UserUtils.reMasking(bankInName, "name"));
		}
		if(bankVirtualNo != null){
			payment.setBankVirtualNo(UserUtils.reMasking(bankVirtualNo, "account"));
		}
	}

	/**
	 * 입금 확인취소 가능여부 체크
	 * @param payment
	 */
	private void isPaymentVerificationCancel(OrderPayment payment) {
		OrderParam param = new OrderParam();
		param.setOrderCode(payment.getOrderCode());
		param.setOrderSequence(payment.getOrderSequence());

		param.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			param.setSellerId(SellerUtils.getSellerId());
			param.setConditionType("SELLER");
		}

		Order order = orderMapper.getOrderByParam(param);

		int newOrderCount = 0;
		int itemCount = 0;
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem orderItem : info.getOrderItems()) {

				if (ShopUtils.checkOrderStatusChange("payment-verification-cancel", orderItem.getOrderStatus())) {
					newOrderCount++;
				}

				itemCount++;
			}
		}

		boolean isPaymentVerificationCancel = (newOrderCount == itemCount) ? true : false;
		payment.setPaymentVerificationCancel(isPaymentVerificationCancel);
	}

	@Override
	public void waitingDepositListUpdate(String mode, OrderParam orderParam) {

		if (orderParam.getId() == null) {
			throw new OrderManagerException();
		}

		if ("cancel".equals(mode)) {
			// 입금 확인전 주문 취소
			for(String id : orderParam.getId()) {

			}
		} else if ("confirm".equals(mode)) {

			// 입금 확인
			for(String id : orderParam.getId()) {

				String[] temp = StringUtils.delimitedListToStringArray(id, OrderPayment.PAYMENT_KEY_DIVISION_STRING);

				if (temp.length != 3) {
					continue;
				}

				orderParam.setOrderCode(temp[0]);
				orderParam.setOrderSequence(Integer.parseInt(temp[1]));
				orderParam.setPaymentSequence(Integer.parseInt(temp[2]));

				OrderPayment orderPayment = orderMapper.getOrderPaymentByParam(orderParam);
				if (orderPayment == null) {
					continue;
				}

				orderParam.setAdminUserName(UserUtils.getUser().getUserName());
				orderParam.setPayAmount(orderPayment.getAmount());

				// 주문 로그 추가로 인해 임시 조회
                List<OrderItem> logOrderItems
                        = this.getOrderItemListForOrderLog(orderParam.getOrderCode(), orderParam.getOrderSequence());

				if (orderPaymentMapper.updateConfirmationOfPaymentStep1(orderParam) > 0){
					if (orderPaymentMapper.updateConfirmationOfPaymentStep2(orderParam) > 0) {
						orderPaymentMapper.updateConfirmationOfPaymentStep3(orderParam);



                        // 주문 로그
                        try {
                            for (OrderItem orderItem : logOrderItems) {

                                this.insertOrderLog(OrderLogType.WAITING_DEPOSIT,
                                        orderParam.getOrderCode(),
                                        orderParam.getOrderSequence(),
                                        orderItem.getItemSequence(),
                                        orderItem.getOrderStatus());
                            }
                        } catch (Exception e) {
                            log.error(ERROR_MARKER, e.getMessage(), e);
                        }


					} else {
						throw new OrderManagerException();
					}
				} else {
					throw new OrderManagerException();
				}

				ConfigPg configPg = configPgService.getConfigPg();
				String autoCashReceipt = "";

				if (configPg != null) {
					autoCashReceipt = configPg.isUseAutoCashReceipt() ? "Y" : "N";
				} else {
					autoCashReceipt = environment.getProperty("pg.autoCashReceipt");
				}

				if(!"Y".equals(autoCashReceipt)) {
					// 현금영수증 발행
					CashbillParam cashbillParam = new CashbillParam();

					cashbillParam.setWhere("orderCode");
					cashbillParam.setQuery(orderParam.getOrderCode());

					Iterable<CashbillIssue> cashbillIssues = cashbillIssueRepository.findAll(cashbillParam.getPredicate());

					log.debug("[CASHBILL] START ---------------------------------------------");
					log.debug("[CASHBILL] cashbillIssues Size :  {}", ((List<CashbillIssue>) cashbillIssues).size());

					CashbillResponse response = null;

					for (CashbillIssue cashbillIssue : cashbillIssues) {
						response = receiptService.receiptIssue(cashbillIssue);

						if (response == null) {
							log.debug("[CASHBILL] ERROR >> PG 통신오류(응답없음)");
							throw new OrderException("PG 통신오류(응답없음)");
						}

						log.debug("[CASHBILL] cashbillIssue :  {}", cashbillIssue);
						log.debug("[CASHBILL] CashbillResponse response.isSuccess() :  {}", response.isSuccess());
						if (response.isSuccess()) {
							cashbillIssue.setIssuedDate(DateUtils.getToday(DATETIME_FORMAT));
							cashbillIssue.setUpdatedDate(DateUtils.getToday(DATETIME_FORMAT));
							cashbillIssue.setCashbillStatus(CashbillStatus.ISSUED);
							cashbillIssue.setMgtKey(response.getMgtKey());

							if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
								cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
							} else {
								cashbillIssue.setUpdateBy("비회원");
							}

							cashbillIssueRepository.save(cashbillIssue);

						} else {
							log.debug("[CASHBILL] ERROR >> {} : {}", response.getResponseCode(), response.getResponseMessage());
							throw new OrderException("현금영수증 발행 오류 - " +response.getResponseCode() + " : " + response.getResponseMessage());
						}
					}
					log.debug("[CASHBILL] END ---------------------------------------------");
					try {

						OrderParam orderSearchParam = new OrderParam();
						orderSearchParam.setOrderCode(orderPayment.getOrderCode());
						orderSearchParam.setConditionType("OPMANAGER");
						Order order = this.getOrderByParam(orderSearchParam);

						this.sendOrderMessageTx(order, "order_cready_payment", ShopUtils.getConfig());

					} catch(Exception e) {
                    log.error(ERROR_MARKER, e.getMessage(), e);
					}
				}
            }
        } else {
            throw new OrderManagerException();
        }

	}

	@Override
	public void sendOrderMessageTx(Buy buy) {

		if (buy == null) {
			return;
		}

		Config config = ShopUtils.getConfig();
		String templateId = "0".equals(buy.getOrderStatus()) ? "order_deposit_wait" : "order_cready_payment";

		OrderSendMessageLog orderSendMessageLog = new OrderSendMessageLog();
		orderSendMessageLog.setOrderCode(buy.getOrderCode());
		orderSendMessageLog.setTemplateId(templateId);

		// 사용자 발송 체크
		if (orderMapper.getOrderSendMessageLogCount(orderSendMessageLog) == 0) {

			if ("order_cready_payment".equals(templateId)) {
				long userId = buy.getUserId();

				Buyer buyer = buy.getBuyer();
				MailConfig mailConfig = mailConfigService.getMailConfigByTemplateId(templateId);
				if (mailConfig != null) {

					if (StringUtils.isEmpty(buyer.getEmail()) == false) {

						OrderMail orderMail = new OrderMail(buy, userId, mailConfig, config);
						mailConfig = orderMail.getMailConfig();

						SendMailLog sendMailLog = new SendMailLog();
						sendMailLog.setUserId(userId);
						sendMailLog.setSendType(templateId);
						sendMailLog.setOrderCode(buy.getOrderCode());

						sendMailLogService.sendMail(mailConfig, sendMailLog, buyer.getEmail(),buyer.getUserName(), config);
					}
				}

				if (StringUtils.isEmpty(buyer.getFullMobile()) == false) {

					Ums ums = umsService.getUms(templateId);
					ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(userId);

					unifiedMessagingService.sendMessage(new OrderNew(ums, buy, userId, config, buyer.getFullMobile(), applicationInfo));

				}

				// 발송하고 로그 기록
				orderMapper.insertOrderSendMessageLog(orderSendMessageLog);

			} else if ("order_deposit_wait".equals(templateId)) {

				long userId = buy.getUserId();

				Buyer buyer = buy.getBuyer();
				MailConfig mailConfig = mailConfigService.getMailConfigByTemplateId(templateId);
				if (mailConfig != null) {

					if (StringUtils.isEmpty(buyer.getEmail()) == false) {

						OrderMail orderMail = new OrderMail(buy, userId, mailConfig, config);
						mailConfig = orderMail.getMailConfig();

						SendMailLog sendMailLog = new SendMailLog();
						sendMailLog.setUserId(userId);
						sendMailLog.setSendType(templateId);
						sendMailLog.setOrderCode(buy.getOrderCode());
						sendMailLogService.sendMail(mailConfig, sendMailLog, buyer.getEmail(),buyer.getUserName(), config);
					}
				}

				if (StringUtils.isEmpty(buyer.getFullMobile()) == false) {

					for(BuyPayment payment : buy.getPayments()) {

						if ("bank".equals(payment.getApprovalType())
							|| "vbank".equals(payment.getApprovalType())) {

							Ums ums = umsService.getUms(templateId);
							ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(userId);

							unifiedMessagingService.sendMessage(new OrderBank(ums, buy, payment, userId, config, buyer.getFullMobile(), applicationInfo));
						}
					}
				}

				// 발송하고 로그 기록
				orderMapper.insertOrderSendMessageLog(orderSendMessageLog);
			}
		}
	}

	@Override
	public void sendOrderMessageTx(Order order, String templateId, Config config) {

		if (order == null) {
			return;
		}

		OrderSendMessageLog orderSendMessageLog = new OrderSendMessageLog();
		orderSendMessageLog.setOrderCode(order.getOrderCode());
		orderSendMessageLog.setTemplateId(templateId);

		if ("order_delivering".equals(templateId)) {
			orderSendMessageLog.setDeliveryNumber(order.getMessageTargetDeliveryNumber());
		}

		// 사용자 발송 체크
		if (orderMapper.getOrderSendMessageLogCount(orderSendMessageLog) == 0) {

			long userId = order.getUserId();
			MailConfig mailConfig = mailConfigService.getMailConfigByTemplateId(templateId);
			if (mailConfig != null) {

				if (StringUtils.isEmpty(order.getEmail()) == false) {
					//배송중 메일 설정
					if ("order_delivering".equals(templateId)) {
						OrderMail orderMail = new OrderMail(order, userId, mailConfig, config);
						mailConfig = orderMail.getMailConfig();
					} else if ("order_cready_payment".equals(templateId)) {
						// 주문완료(입금대기->결제완료 바뀔때 or 주문시 바로 결제완료) 메일 설정
                        OrderMail orderMail = new OrderMail(order, userId, mailConfig, config);
						mailConfig = orderMail.getMailConfig();
					}

					SendMailLog sendMailLog = new SendMailLog();
					sendMailLog.setUserId(userId);
					sendMailLog.setSendType(templateId);
					sendMailLog.setOrderCode(order.getOrderCode());
					sendMailLogService.sendMail(mailConfig, sendMailLog, order.getEmail(), order.getUserName(), config);
				}
			}

			if (StringUtils.isEmpty(order.getMobile()) == false) {

				Ums ums = umsService.getUms(templateId);
				ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(userId);

				if ("order_delivering".equals(templateId)) {
					unifiedMessagingService.sendMessage(new OrderDelivering(order, userId, config, ums, order.getMobile(), applicationInfo));
				} else if ("order_cready_payment".equals(templateId)) {
					unifiedMessagingService.sendMessage(new OrderNew(ums, order, userId, config, order.getMobile(), applicationInfo));
				}
			}

			// 발송하고 로그 기록
			orderMapper.insertOrderSendMessageLog(orderSendMessageLog);
		}
	}

	@Override
	public void paymentVerificationCancel(OrderParam orderParam) {


//		OrderPayment orderPayment = orderMapper.getOrderPaymentByParam(orderParam);
//		if (orderPayment == null) {
//			throw new OrderManagerException();
//		}
//
//		isPaymentVerificationCancel(orderPayment);
//		if (orderPayment.isPaymentVerificationCancel() == false) {
//			throw new OrderManagerException("Error. 입금 확인취소가 적용되지 않았습니다.\n해당 요청건의 상품 처리상태가 신규주문 상태가 아닌것이 있습니다.");
//		}

        List<OrderPayment> list = orderMapper.getOrderPaymentListByParam(orderParam);

        if (list == null || list.isEmpty()) {
            throw new OrderManagerException("입금확인 취소 처리중 오류가 발생했습니다. ( " + orderParam.getOrderCode() + "의 결제정보를 찾을 수 없습니다. )");
        }

        for (OrderPayment orderPayment : list) {
            if ("bank".equals(orderPayment.getApprovalType())) {
                isPaymentVerificationCancel(orderPayment);
                if (orderPayment.isPaymentVerificationCancel() == false) {
                    throw new OrderManagerException("Error. 입금 확인취소가 적용되지 않았습니다.\n해당 요청건의 상품 처리상태가 신규주문 상태가 아닌것이 있습니다.");
                }
            }
        }

		orderParam.setAdminUserName(UserUtils.getUser().getUserName());

		if ( orderMapper.updateConfirmationOfPaymentCancelStep1(orderParam) == 0) {
			throw new OrderManagerException("Error. 입금 확인취소가 적용되지 않았습니다.\n해당 요청건의 상품 처리상태가 신규주문 상태가 아닌것이 있습니다.");
		} else {
			if ( orderMapper.updateConfirmationOfPaymentCancelStep2(orderParam) == 0) {
				throw new OrderManagerException("Error. 입금 확인취소가 적용되지 않았습니다.\n해당 요청건의 상품 처리상태가 신규주문 상태가 아닌것이 있습니다.");
			} else{
			    //2017.05.16 Son Jun-Eu - OP_ORDER_ITEM 테이블의 관련 내용 UPDATE
				if ( orderMapper.updateConfirmationOfPaymentCancelStep3(orderParam) == 0) {
					throw new OrderManagerException("Error. 입금 확인취소가 적용되지 않았습니다.\n해당 요청건의 상품 처리상태가 신규주문 상태가 아닌것이 있습니다.");
				}
			}
		}

		ConfigPg configPg = configPgService.getConfigPg();
		String autoCashReceipt = "";

		if (configPg != null) {
			autoCashReceipt = configPg.isUseAutoCashReceipt() ? "Y" : "N";
		} else {
			autoCashReceipt = environment.getProperty("pg.autoCashReceipt");
		}

		if(!"Y".equals(autoCashReceipt)) {
			List<Cashbill> cashbills = cashbillRepository.findAllByOrderCode(orderParam.getOrderCode());

			if (!cashbills.isEmpty()) {
				// 현금영수증 발행취소
				CashbillResponse cancelResponse = receiptService.cancelCashbill(orderParam.getOrderCode());

				if (!cancelResponse.isSuccess()) {
					throw new OrderManagerException("발급된 현금영수증 취소요청중 오류가 발생하였습니다. ( " + cancelResponse.getResponseMessage() + " )");
				}
			}
		}
	}

	@Override
	public List<WaitingDepositDetail> getWaitingDepositDetailByParam(OrderParam orderParam) {

		//return orderMapper.getWaitingDepositDetailByParam(orderParam);
		return null;
	}

	@Override
	public List<OrderList> getNewOrderListByParam(OrderParam orderParam) {

		String conditionType = orderParam.getConditionType();

		// 주문일자 검색을 결제일자로 디폴트
		if (StringUtils.isEmpty(orderParam.getSearchDateType())) {
			orderParam.setSearchDateType("OS.PAY_DATE");
		}

		int totalCount = orderShippingMapper.getNewOrderCountByParam(orderParam);

		if (orderParam.getItemsPerPage() == 10) {
			orderParam.setItemsPerPage(50);
		}

		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());
		orderParam.setPagination(pagination);
		orderParam.setLanguage(CommonUtils.getLanguage());

		List<OrderList> list = orderShippingMapper.getNewOrderListByParam(orderParam);

		for(OrderList order : list){
			if(!(conditionType!=null && conditionType.equals("ORDER-DETAIL-EXCEL"))){
				newOrderMaskingDataSet(order);
			} else if(!(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS"))){
				newOrderMaskingDataSet(order);
			}
		}

		// 사은품 조회
		setOrderGiftItemForOrderList(list);

		return list;
	}

	private void newOrderMaskingDataSet(OrderList order){
		String userName = order.getUserName();
		String mobile = order.getMobile();
		String receiveName = order.getReceiveName();
		String receiveMobile = order.getReceiveMobile();
		String receiveZipCode = order.getReceiveNewZipcode();
		String receiveAddress = order.getReceiveAddress();
		String receiveAddressDetail = order.getReceiveAddressDetail();

		if(userName != null){
			order.setUserName(UserUtils.reMasking(userName, "name"));
		}
		if(mobile != null){
			order.setMobile(UserUtils.reMasking(mobile, "tel"));
		}
		if(receiveName != null){
			order.setReceiveName(UserUtils.reMasking(receiveName, "name"));
		}
		if(receiveMobile != null){
			order.setReceiveMobile(UserUtils.reMasking(receiveMobile, "tel"));
		}
		if(receiveZipCode != null){
			order.setReceiveZipcode(UserUtils.reMasking(receiveZipCode, "zipCode"));
		}
		if(receiveAddress != null){
			order.setReceiveAddress(UserUtils.reMasking(receiveAddress, "addr"));
		}
		if(receiveAddressDetail != null){
			order.setReceiveAddressDetail(UserUtils.reMasking(receiveAddressDetail, "addrDetail"));
		}
	}

	@Override
	public void newOrderListUpdate(String mode, ShippingReadyParam shippingReadyParam, OrderItem orderItem, OrderParam orderParam) {

		// CJH 2016.10.20 수량이 0인 주문이 생성되지 않도록
		if (orderItem.getShippingReadyPossibleQuantity() <= 0) {
			return;
		}

		if ("shipping-direct".equals(mode)) {
			// 출고 가능 수량과 출고 지시 수량이 같으면 그냥 출고 수량 업데이트
			if (orderItem.getShippingReadyPossibleQuantity() == shippingReadyParam.getQuantity()) {

				if (orderShippingMapper.updateShippingDirect(shippingReadyParam) == 0) {
					throw new OrderException();
				}
			} else {

				// 출고 지시 수량만큼 정보 복사
				orderItem.setQuantity(shippingReadyParam.getQuantity());
				orderShippingMapper.copyOrderItemForShippingDirect(orderItem);

				if (orderMapper.updateOrderItemQuantity(orderItem) == 0) {
					throw new OrderException();
				}

			}
		} else if ("shipping-ready".equals(mode)) {

			// 출고 가능 수량과 출고 지시 수량이 같으면 그냥 출고 수량 업데이트
			if (orderItem.getShippingReadyPossibleQuantity() == shippingReadyParam.getQuantity()) {
				if (orderShippingMapper.updateShippingReady(shippingReadyParam) == 0) {
					throw new OrderException();
				}
			} else {

				// 출고 지시 수량만큼 정보 복사
				orderItem.setQuantity(shippingReadyParam.getQuantity());
				orderShippingMapper.copyOrderItemForShippingReady(orderItem);

				if (orderMapper.updateOrderItemQuantity(orderItem) == 0) {
					throw new OrderException();
				}

			}
		} else if ("deposit-check-cancel".equals(mode)) {
			//입금확인 취소
			paymentVerificationCancel(orderParam);
		} else {
			throw new OrderException();
		}

		// 주문 로그
		this.insertOrderLog(OrderLogType.ORDER_PAYMENT,
				orderItem.getOrderCode(),
				orderItem.getOrderSequence(),
				orderItem.getItemSequence(),
				orderItem.getOrderStatus());
	}

	@Override
	public List<OrderList> getShippingReadyListByParam(OrderParam orderParam) {
		String conditionType = orderParam.getConditionType();
		List<OrderList> list = orderShippingMapper.getShippingReadyListByParam(orderParam);
		// 주문일자 검색을 배송준비중 상태 변경으로 디폴트
		if (StringUtils.isEmpty(orderParam.getSearchDateType())) {
			orderParam.setSearchDateType("OS.SHIPPING_READY_DATE");
		}

		int totalCount = orderShippingMapper.getShippingReadyCountByParam(orderParam);

		if (orderParam.getItemsPerPage() == 10) {
			orderParam.setItemsPerPage(50);
		}

		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());
		orderParam.setPagination(pagination);
		orderParam.setLanguage(CommonUtils.getLanguage());

		for(OrderList order : list){
			if(!(conditionType!=null && conditionType.equals("ORDER-DETAIL-EXCEL"))){
				shippingReadyMaskingDataSet(order);
			} else if(!(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS"))){
				shippingReadyMaskingDataSet(order);
			}
		}

		// 사은품 조회
		setOrderGiftItemForOrderList(list);

		return list;
	}

	private void shippingReadyMaskingDataSet(OrderList order){
		String buyerName = order.getUserName();	// 주문자
		String buyerPhone = order.getMobile();	// 주문자 핸드폰
		String receiveName = order.getReceiveName();	// 수령인
		String receiveMobile = order.getReceiveMobile();	// 수령인 전화번호
		String receiveZipCode = order.getReceiveZipcode();	// 우편번호
		String receiveNewZipCode = order.getReceiveNewZipcode(); // 신 우편번호
		String receiveAddress = order.getReceiveAddress();	// 주소
		String receiveAddressDetail = order.getReceiveAddressDetail();	//상세주소

		if (buyerName != null) {
			order.setUserName(UserUtils.reMasking(buyerName, "name"));
		}
		if (buyerPhone != null) {
			order.setMobile(UserUtils.reMasking(buyerPhone, "tel"));
		}
		if (receiveName != null) {
			order.setReceiveName(UserUtils.reMasking(receiveName, "name"));
		}
		if (receiveMobile != null) {
			order.setReceiveMobile(UserUtils.reMasking(receiveMobile, "tel"));
		}
		if (receiveZipCode != null) {
			order.setReceiveZipcode(UserUtils.reMasking(receiveZipCode, "zipCode"));
		}
		if (receiveNewZipCode != null) {
			order.setReceiveNewZipcode(UserUtils.reMasking(receiveNewZipCode, "newZipCode"));
		}
		if (receiveAddress != null) {
			order.setReceiveAddress(UserUtils.reMasking(receiveAddress, "addr"));
		}
		if (receiveAddressDetail != null) {
			order.setReceiveAddressDetail(UserUtils.reMasking(receiveAddressDetail, "addrDetail"));
		}

	}

	@Override
	public void shippingReadyListUpdate(String mode, ShippingParam shippingParam, OrderItem orderItem, OrderParam orderMessageParam) {

		if ("shipping-cancel".equals(mode)) {

			if (orderShippingMapper.updateShippingReadyCancel(shippingParam) == 0) {
				throw new OrderException();
			}

			if (!"N".equals(orderItem.getEscrowStatus())) {
				throw new OrderException();
			}

		} else if ("shipping-start".equals(mode) || "shipping-start-send-message".equals(mode)) {

			if (!(shippingParam.getDeliveryCompanyId() > 0
				&& StringUtils.isNotEmpty(shippingParam.getDeliveryNumber()))) {
				throw new OrderException();
			}

			DeliveryCompany deliveryCompany = deliveryCompanyService.getDeliveryCompanyById(shippingParam.getDeliveryCompanyId());

			if (deliveryCompany == null) {
				throw new OrderException();
			}

			shippingParam.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
			shippingParam.setDeliveryCompanyUrl(deliveryCompany.getDeliveryCompanyUrl());

			if (orderShippingMapper.updateShippingStart(shippingParam) == 0) {
				throw new OrderException();
			}

            ConfigPg configPg = configPgService.getConfigPg();

            if (orderShippingMapper.getPreShippingCount(shippingParam) == 0) {
                OrderPgData orderPgData = this.getOrderPgDataByOrderCode(shippingParam.getOrderCode());
                if (orderPgData != null && "naverpay".equals(orderPgData.getPgServiceType())) {
                    MultiValueMap<String, Object> pointRequestMap = new LinkedMultiValueMap<String, Object>();
                    pointRequestMap.add("paymentId", orderPgData.getPgKey());

                    naverPaymentApi.point(pointRequestMap, configPg);
                }
            }

			// 메시지 전송
			if ("shipping-start-send-message".equals(mode)) {
				try {

					OrderParam orderSearchParam = new OrderParam();
					orderSearchParam.setOrderCode(orderItem.getOrderCode());
					orderSearchParam.setConditionType("OPMANAGER");
					Order order = this.getOrderByParam(orderSearchParam);

					String deliveryNumber = shippingParam.getDeliveryNumber();
					if (order != null) {

						String key = shippingParam.getKey().substring(0, shippingParam.getKey().lastIndexOf(OrderItem.ITEM_KEY_DIVISION_STRING));
						List<String> orders = new ArrayList<>();

						for (int i=0; i<orderMessageParam.getId().length; i++) {
							if (orderMessageParam.getId()[i].startsWith(key)
									&& deliveryNumber.equals(orderMessageParam.getShippings().get(i).getDeliveryNumber())) {
								orders.add(orderMessageParam.getId()[i]);
							}
						}
						order.setMessageTargetItemSequences(orders.toArray(new String[orders.size()]));
						order.setMessageTargetDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
						order.setMessageTargetDeliveryNumber(deliveryNumber);

						this.sendOrderMessageTx(order, "order_delivering", ShopUtils.getConfig());
					}

				} catch(Exception e) {
					log.error(ERROR_ORDER_CODE, orderItem.getOrderCode(), e);
				}
			}


			String pgType = "";
			String useEscrow = "";

			if (configPg != null) {
				pgType = configPg.getPgType().getCode().toLowerCase();
				useEscrow = configPg.isUseEscroow() ? "Y" : "N";
			} else {
				pgType = SalesonProperty.getPgService();
				useEscrow = environment.getProperty("pg.useEscrow");
			}

            // 에스크로 사용시
            if ("Y".equals(useEscrow) && "Y".equals(orderItem.getEscrowStatus())) {
                //이니시스 에스크로 결제완료 상태 시 PG사에 배송등록 요청
                if ("inicis".equals(pgType)) {

                    // 배송등록 요청 정보 가져오기
                    HashMap<String, Object> paramMap = new HashMap<>();

                    OrderParam orderParam = new OrderParam();
                    orderParam.setOrderCode(orderItem.getOrderCode());
                    orderParam.setOrderSequence(orderItem.getOrderSequence());
                    orderParam.setItemSequence(orderItem.getItemSequence());
                    orderParam.setShippingSequence(orderItem.getShippingSequence());

                    paramMap = orderMapper.getInicisDeliveryInfoByParam(orderParam);

                    // 무통장 입금(bank) 시 오류로인해 회피 처리 (임시 2018.02.06-KYK)
                    if (paramMap != null) {

                        //배송준비중 목록에서 배송시작 누를경우 배송등록을 신규로 설정
                        paramMap.put("dlv_report", "I");

                        if(paramMap.get("PAY_SHIPPING").toString().equals("0"))
                            paramMap.put("dlv_charge", "SH");
                        else
                            paramMap.put("dlv_charge", "BH");

                        boolean isSuccess = inicisService.delivery(paramMap);

                        // 2017.7.05 Son Jun-Eu - 에스크로 배송등록 확인 후 상태 갱신
                        orderParam.setEscrowStatus("20");
                        orderMapper.updateEscrowStatus(orderParam);

                    }


                }else if("kcp".equals(pgType)){
                    // 배송등록 요청 정보 가져오기
                    HashMap<String, Object> paramMap = new HashMap<>();

                    OrderParam orderParam = new OrderParam();
                    orderParam.setOrderCode(orderItem.getOrderCode());
                    orderParam.setOrderSequence(orderItem.getOrderSequence());
                    orderParam.setItemSequence(orderItem.getItemSequence());
                    orderParam.setShippingSequence(orderItem.getShippingSequence());

                    paramMap.put("tno",orderMapper.getTidByParam(shippingParam.getOrderCode()));
                    paramMap.put("deli_numb",shippingParam.getDeliveryNumber());
                    paramMap.put("deli_corp",shippingParam.getDeliveryCompanyName());

                    boolean isSuccess = kcpService.delivery(paramMap);

                    orderParam.setEscrowStatus("20");
                    orderMapper.updateEscrowStatus(orderParam);
                }
            }
		}

		// 주문 로그
		this.insertOrderLog(OrderLogType.ORDER_SHIPPING,
				orderItem.getOrderCode(),
				orderItem.getOrderSequence(),
				orderItem.getItemSequence(),
				orderItem.getOrderStatus());
	}

	//배송준비중 목록에서 엑셀업로드로 배송정보 일괄 업데이트[2017-09-05]minae.yun
	@Override
	public Map<String, Object> shippingReadyExcelUpload(OrderParam orderParam, MultipartFile multipartFile) {

		if (multipartFile == null) {
			throw new UserException(MessageUtils.getMessage("M01532")); // 파일을 선택해 주세요.
		}

		String fileName = multipartFile.getOriginalFilename();
		String fileExtension = FileUtils.getExtension(fileName);

		// 확장자 체크
		if (!(fileExtension.equalsIgnoreCase("xlsx"))) {
			throw new UserException(MessageUtils.getMessage("M01533"));	// 엑셀 파일(.xlsx)만 업로드가 가능합니다.
		}

		// 엑셀 셀 읽기 : http://poi.apache.org/spreadsheet/quick-guide.html#CellContents 자세한 건 여기서 확인 - skc
		XSSFWorkbook wb = null;

		String excelUploadReport = "";
		try {
			wb = new XSSFWorkbook(multipartFile.getInputStream());

			Map<String, Object> resp = processShippingReadyExcelSheet(wb.getSheet("OrderShippingReadyExcel"));

			excelUploadReport += "<p class=\"upload_file\">" + multipartFile.getOriginalFilename() + "</p>\n";
			excelUploadReport += (String) resp.get("result");

			resp.replace("result", resp.get("result"), excelUploadReport);

			return resp;

		} catch (IOException e) {
			String message = MessageUtils.getMessage("M01534") + "(" + e.getMessage()+ ")";
			log.error("{}", message , e);
			throw new UserException(message); // 엑셀 파일 로드 시 오류가 발생하였습니다.

		} catch (Exception e) {
			String message = MessageUtils.getMessage("M01534") + "(" + e.getMessage()+ ")";
			log.error("{}", message, e);

			throw new UserException(message); // 엑셀 파일 로드 시 오류가 발생하였습니다.
		}
	}

	//엑셀에서 배송정보(택배사, 운송장번호)받아서 업데이트[2017-09-05]minae.yun
	private Map<String, Object> processShippingReadyExcelSheet(XSSFSheet sheet) {

		Map<String, Object> resp = new HashMap<>();
		String result = "";
		if (sheet == null) {
			resp.put("result",  result);
			return resp;
		}

		StringBuffer executionLog = new StringBuffer();

		List<HashMap<String, String>> cellReferences = new ArrayList<>();

		List<String> key = new ArrayList<>();

		int rowDataCount = 0;			// 데이터 수 (타이틀, 헤더 제외)
		int rowErrorCount = 0;			// 오류 수
		for (Row row : sheet) {
			int rowIndex = row.getRowNum() + 1;

			if (row.getRowNum() < 2) {
				continue;
			}

			// 헤더 - 타이틀 가져오기
			for (Cell cell : row) {
				CellReference cellReference = new CellReference(cell);

				HashMap<String, String> cellInfo = new HashMap<>();
				cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
				cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

				cellReferences.add(cellInfo);
			}

			// 해당 로우의 셀 값이 전부 비어있는 경우는 SKIP
			if (PoiUtils.isEmptyAllCell(row)) {
				continue;
			}

			//주문번호
			String orderCode = ShopUtils.getString(row.getCell(0));
			//아이템 주문 순번
			int itemSequence = Integer.parseInt(ShopUtils.getString(row.getCell(1)));
			//배송정보
			DeliveryCompany deliveryCompany;
			ShippingParam shippingParam = new ShippingParam();

			shippingParam.setOrderCode(orderCode);

			int cellErrorCount = 0;
			for (Cell cell : row) {
				HashMap<String, String> cellReference = null;
				try {
					cellReference = cellReferences.get(cell.getColumnIndex());
				} catch (Exception e) {
					log.error("cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage(), e);
				}
				if (cellReference == null) {
					continue;
				}
				cellReference.put("rowIndex", Integer.toString(rowIndex));

				switch (cell.getColumnIndex()) {

					case 11:	// 택배사
						deliveryCompany = deliveryCompanyService.getDeliveryCompanyById(ShopUtils.getInt(cell));
						if (deliveryCompany != null) {
							shippingParam.setDeliveryCompanyId(ShopUtils.getInt(cell));
							shippingParam.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
							shippingParam.setDeliveryCompanyUrl(deliveryCompany.getDeliveryCompanyUrl());
						} else {
							executionLog.append(PoiUtils.log(cellReference, "잘못된 택배사를 입력하셨습니다."));
							cellErrorCount++;
							break;
						}
						break;

					case 12:	// 운송장번호
						shippingParam.setDeliveryNumber(ShopUtils.getString(cell));
						break;

					default:
						break;
				}

			} // cell

			if ( shippingParam.getDeliveryCompanyId() != 0 && !"".equals(shippingParam.getDeliveryNumber()) ) {

				shippingParam.setAdminUserName(UserUtils.getManagerName());
				shippingParam.setConditionType("OPMANAGER");
				if (ShopUtils.isSellerPage()) {
					shippingParam.setSellerId(SellerUtils.getSellerId());
					shippingParam.setConditionType("SELLER");
				}

				shippingParam.setOrderSequence(0);
				shippingParam.setItemSequence(itemSequence);

				//배송중으로 업데이트
				if (orderShippingMapper.updateShippingStart(shippingParam) == 0) {
					throw new OrderException();
				}

				// 메세지 발송용 키 세팅
				String devision = OrderItem.ITEM_KEY_DIVISION_STRING;
				key.add(new StringBuilder(orderCode).append(devision)
						.append(shippingParam.getDeliveryNumber()).append(devision)
						.append(itemSequence).append(devision)
						.append(shippingParam.getDeliveryCompanyName()).toString());
			}

			// 해당 행의 각각의 Cell의 오류를 체크하여 오류가 있는 경우 데이터를 처리하지 않음.
			if (cellErrorCount > 0) {
				rowErrorCount++;
				continue;
			}

			rowDataCount++;

		} // row

		// 처리결과
		result = "\n<p class=\"sheet\"><span>[배송정보 등록]</span> Total:" + rowDataCount
			+ ", Process:" + (rowDataCount - rowErrorCount)
			+ ", Error:" + rowErrorCount + "</p>\n";

		if (rowErrorCount > 0) {
			result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
			result += executionLog.toString();
			result += "\n";
		}

		resp.put("result", result);
		resp.put("key", key);

		return resp;
	}

	@Override
	public void sendOrderDeliveryMessageByExcelUpload(List<String> keyList) {

		/*
			key ==> orderCode///itemSequence///deliveryNumber///deliveryCompanyName
			ex) K00000001///0///123456789///CJ대한통운
		*/
		for (String key : keyList) {

			String devision = OrderItem.ITEM_KEY_DIVISION_STRING;
			String[] keyArray = key.split(devision);
			String orderCode = keyArray[0];
			String deliveryNumber = keyArray[1];
			String deliveryCompanyName = keyArray[3];

			if (StringUtils.isNotEmpty(orderCode)) {
				OrderParam orderSearchParam = new OrderParam();
				orderSearchParam.setOrderCode(orderCode);
				orderSearchParam.setConditionType("OPMANAGER");
				Order order = this.getOrderByParam(orderSearchParam);

				if (order != null) {

					// 상품코드, 운송장번호까지만 얻어오기
					int divisionIdx = org.apache.commons.lang.StringUtils.ordinalIndexOf(key, devision, 2);
					String code = key.substring(0, divisionIdx) + devision;

					List<String> orders = new ArrayList<>();

					// 엑셀파일 읽을 때 생성한 keyList로 함께 배송하는 상품의 상품 순번 얻어오기
					for (int i=0; i<keyList.size(); i++) {
						if (keyList.get(i).startsWith(code)) {
							orders.add(keyList.get(i).split(devision)[2]);
						}
					}

					order.setMessageTargetItemSequences(orders.toArray(new String[orders.size()]));
					order.setMessageTargetDeliveryCompanyName(deliveryCompanyName);
					order.setMessageTargetDeliveryNumber(deliveryNumber);

					// 메일, 메시지 발송
					this.sendOrderMessageTx(order, "order_delivering", ShopUtils.getConfig());

				}

			}

		}

	}

	@Override
	public List<OrderList> getShippingListByParam(OrderParam orderParam) {
		List<OrderList> list = orderShippingMapper.getShippingListByParam(orderParam);
		String conditionType = orderParam.getConditionType();
		// 주문일자 검색을 배송중 일자로 디폴트
		if (StringUtils.isEmpty(orderParam.getSearchDateType())) {
			orderParam.setSearchDateType("OI.DELIVERY_DATE");
		}

		int totalCount = orderShippingMapper.getShippingCountByParam(orderParam);

		if (orderParam.getItemsPerPage() == 10) {
			orderParam.setItemsPerPage(50);
		}

		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());
		orderParam.setPagination(pagination);
		orderParam.setLanguage(CommonUtils.getLanguage());

		if(list != null){
			for(OrderList order : list){
				if(!(conditionType != null && conditionType.equals("ORDER-DETAIL-EXCEL"))){
					shippingMaskingDataSet(order);
				} else if(!(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS"))){
					shippingMaskingDataSet(order);
				}
			}
		}

		// 사은품 조회
		setOrderGiftItemForOrderList(list);

		return list;
	}

	private void shippingMaskingDataSet(OrderList order){
		String buyerName = order.getUserName();
		String buyerPhone = order.getMobile();
		String receiveName = order.getReceiveName();
		String receiveMobile = order.getReceiveMobile();
		String receiveZipCode = order.getReceiveZipcode();
		String receiveNewZipCode = order.getReceiveNewZipcode();
		String receiveAddress = order.getReceiveAddress();
		String receiveAddressDetail = order.getReceiveAddressDetail();

		if (buyerName != null) {
			order.setUserName(UserUtils.reMasking(buyerName, "name"));
		}
		if (buyerPhone != null) {
			order.setMobile(UserUtils.reMasking(buyerPhone, "tel"));
		}
		if (receiveName != null) {
			order.setReceiveName(UserUtils.reMasking(receiveName, "name"));
		}
		if (receiveMobile != null) {
			order.setReceiveMobile(UserUtils.reMasking(receiveMobile, "tel"));
		}
		if (receiveZipCode != null) {
			order.setReceiveZipcode(UserUtils.reMasking(receiveZipCode, "zipCode"));
		}
		if (receiveNewZipCode != null) {
			order.setReceiveNewZipcode(UserUtils.reMasking(receiveNewZipCode, "newZipCode"));
		}
		if (receiveAddress != null) {
			order.setReceiveAddress(UserUtils.reMasking(receiveAddress, "addr"));
		}
		if (receiveAddressDetail != null) {
			order.setReceiveAddressDetail(UserUtils.reMasking(receiveAddressDetail, "addrDetail"));
		}
	}

	@Override
	public void shippingListUpdate(String mode, OrderParam orderParam, ShippingParam shippingParam) {
		if (orderParam.getId() == null) {
			throw new OrderManagerException();
		}

		// 주문 로그 기록을 위해 임시 호출
		OrderItem logOrderItem = this.getOrderItemForOrderLog(shippingParam.getOrderCode(),
				shippingParam.getOrderSequence(), shippingParam.getItemSequence());

		//운송장번호 입력 취소(배송 취소) [2017-09-06]minae.yun
		if ("cancel-delivery".equals(mode)) {

			// 2017.10.20 juneu.son 에스크로 주문건의 경우 배송취소 불가능
			if(!"N".equals(orderMapper.getOrderItemByEscrow(orderParam.getOrderCode())))
				throw new OrderManagerException("에스크로 결제 주문은 배송취소를 할 수 없습니다.");

			orderShippingMapper.updateShippingCancel(shippingParam);
		} else {
			throw new OrderManagerException();
		}

		// 주문 로그
		try {
			this.insertOrderLog(
					OrderLogType.ORDER_SHIPPING,
					shippingParam.getOrderCode(),
					shippingParam.getOrderSequence(),
					shippingParam.getItemSequence(),
					logOrderItem.getOrderStatus());
		} catch (Exception e) {
			log.error(ERROR_MARKER, e.getMessage(), e);
		}
	}

	@Override
	public List<OrderList> getConfirmListByParam(OrderParam orderParam) {

		// 주문일자 검색을 구매확정 일자로 디폴트
		if (StringUtils.isEmpty(orderParam.getSearchDateType())) {
			orderParam.setSearchDateType("OI.CONFIRM_DATE");
		}

		int totalCount = orderShippingMapper.getConfirmCountByParam(orderParam);

		if (orderParam.getItemsPerPage() == 10) {
			orderParam.setItemsPerPage(50);
		}

		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());
		orderParam.setPagination(pagination);
		orderParam.setLanguage(CommonUtils.getLanguage());

		List<OrderList> list = orderShippingMapper.getConfirmListByParam(orderParam);

		// 사은품 조회
		setOrderGiftItemForOrderList(list);

		return list;

	}

	private void makeStockRestorationMap(HashMap<String, Integer> map, OrderItem orderItem, int quantity) {
		// 재고량 복원
		int itemId = orderItem.getItemId();

		Item item = null;
		try {
			item = itemService.getItemById(itemId);
		} catch(Exception e) {
			log.warn("상품 정보가 조회되지 않음 ({}), {}", itemId, e.getMessage(), e);
		}

		if (item == null) {
			return;
		}

		if (StringUtils.isNotEmpty(orderItem.getOptions())) {
			List<ItemOption> buyOptions = ShopUtils.getRequiredItemOptions(item, orderItem.getOptions());
			if (buyOptions != null) {
				for(ItemOption itemOption : buyOptions) {
					String stockKey = "";
					if ("T".equals(itemOption.getOptionType())) {
						if ("Y".equals(item.getStockFlag())) {
							if (StringUtils.isEmpty(item.getStockCode()) == false) {
								stockKey = "STOCK||" + orderItem.getSellerId() + "||" + item.getStockCode();
							} else {
								stockKey = "ITEM||" + item.getItemId();
							}
						}
					} else {
						if ("Y".equals(itemOption.getOptionStockFlag())) {
							if (StringUtils.isEmpty(itemOption.getOptionStockCode()) == false) {
								stockKey = "STOCK||" + orderItem.getSellerId() + "||" + itemOption.getOptionStockCode();
							} else {
								stockKey = "OPTION||" + itemOption.getItemOptionId();
							}
						}
					}

					if (!"".equals(stockKey)) {
						int totalQuantity = quantity;
						if (map.get(stockKey) != null) {
							totalQuantity += map.get(stockKey);
						}

						map.put(stockKey, totalQuantity);
					}
				}
			}

		} else {

			if ("Y".equals(item.getStockFlag())) {
				String stockKey = "";
				if (StringUtils.isEmpty(item.getStockCode()) == false) {
					stockKey = "STOCK||" + orderItem.getSellerId() + "||" + item.getStockCode();
				} else {
					stockKey = "ITEM||" + item.getItemId();
				}

				int totalQuantity = quantity;
				if (map.get(stockKey) != null) {
					totalQuantity += map.get(stockKey);
				}

				map.put(stockKey, totalQuantity);
			}

		}
	}

	@Override
	public void makeStockRestorationMap(HashMap<String, Integer> map, OrderCancelApply orderCancelApply) {

		OrderItem orderItem = orderCancelApply.getOrderItem();
		if (orderItem == null) {
			return;
		}

		makeStockRestorationMap(map, orderItem, orderCancelApply.getClaimApplyQuantity());
	}

	@Override
	public void makeStockRestorationMap(HashMap<String, Integer> map, OrderItem orderItem) {
		makeStockRestorationMap(map, orderItem, orderItem.getQuantity());
	}

	/**
	 * 재고량 복원
	 * @param map
	 * @return
	 */
	@Override
	public void stockRestoration(HashMap<String, Integer> map) {
		try {
			if (map == null) {
				return;
			}

			List<StockRestoration> stocks = new ArrayList<>();
			for(String key : map.keySet()) {
				int quantity = map.get(key);
				stocks.add(new StockRestoration(key, quantity));
			}

			for(StockRestoration stock : stocks) {

				if ("STOCK".equals(stock.getStockRestorationType())) {
					orderMapper.updateStockRestorationForItem(stock);
					orderMapper.updateStockRestorationForOption(stock);
				} else if ("ITEM".equals(stock.getStockRestorationType())) {
					orderMapper.updateStockRestorationForItem(stock);
				} else if ("OPTION".equals(stock.getStockRestorationType())) {
					orderMapper.updateStockRestorationForOption(stock);
				}

			}
		} catch (Exception e) {

			log.error("재고량 복원 오류", e);
			if (ServiceType.LOCAL) {
				throw new OrderException("재고량 복원 오류");
			}
		}
	}

	@Override
	public int getOrderCountByParam(OrderParam orderParam) {
		return orderMapper.getOrderCountByParam(orderParam);
	}

	@Override
	public List<OrderList> getOrderListByParamForManager(OrderParam orderParam) {
		int totalCount = orderMapper.getOrderCountByParamForManager(orderParam);

		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());
		orderParam.setPagination(pagination);

		return orderMapper.getOrderListByParamForManager(orderParam);

	}

	@Override
	public List<Order> getOrderListByParam(OrderParam orderParam) {

		int totalCount = orderMapper.getOrderCountByParam(orderParam);

		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());

		ShopUtils.setPaginationInfo(pagination, orderParam.getConditionType(), orderParam.getPage());
		/*
		if (ShopUtils.isMobilePage() && "DEFAULT_LIST".equals(orderParam.getConditionType())) {
			if (orderParam.getPage() > 1) {
				pagination.setStartRownum((int) 0);
				pagination.setEndRownum((int) pagination.getItemsPerPage() * orderParam.getPage());
			}
		}
		*/

		orderParam.setPagination(pagination);

		List<Order> list = orderMapper.getOrderListByParam(orderParam);


		// 추가구성상품
		orderParam.setAdditionItemFlag("Y");
		List<Order> orderAddition = orderMapper.getOrderListByParam(orderParam);

		if (orderAddition != null) {
			for (Order order : list) {
				for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
					for (OrderItem orderItem : orderShippingInfo.getOrderItems()) {

						List<OrderItem> additionItems = new ArrayList<>();
						for (Order additionOrder : orderAddition) {
							for (OrderShippingInfo additionOrderShippingInfo : additionOrder.getOrderShippingInfos()) {
								for (OrderItem additionOrderItem : additionOrderShippingInfo.getOrderItems()) {

									// 부모의 ORDER_CODE, ITEM_SEQUENCE, ITEM_ID, OPTIONS, ORDER_STATUS 가 일치하는 경우
									if (additionOrder.getOrderCode().equals(orderItem.getOrderCode())
											&& orderItem.getItemSequence() == additionOrderItem.getParentItemSequence()
											&& additionOrderItem.getParentItemId() == orderItem.getItemId()
											&& orderItem.getOptions().equals(additionOrderItem.getParentItemOptions())
											&& orderItem.getOrderStatus().equals(additionOrderItem.getOrderStatus())) {

											additionItems.add(additionOrderItem);
									}
								}

								orderItem.setAdditionItemList(additionItems);
							}
						}
					}
				}
			}
		}


		if (list == null) {
			return null;
		}

		for(Order order : list) {
			bindOrder(order, orderParam);
		}

		return list;
	}

	@Override
	public Order getOrderByParam(OrderParam orderParam) {

		Order order = orderMapper.getOrderByParam(orderParam);

		if (order == null) {
			return null;
		}

		/* 교환, 반품 거절 사유 상품별로 불러오기 */
		for (OrderShippingInfo shippingInfos : order.getOrderShippingInfos()) {
			for (OrderItem orderItem : shippingInfos.getOrderItems()) {
				if("59".equals(orderItem.getOrderStatus())) { //교환 거절
					orderItem.setClaimRefusalReasonText(orderClaimApplyMapper.getClaimRefusalReasonText(orderItem));
				} else if ("69".equals(orderItem.getOrderStatus())) {
					orderItem.setClaimRefusalReasonText(orderClaimApplyMapper.getClaimRefusalReasonText(orderItem));
				}
			}
		}

		bindOrder(order, orderParam);

		// 추가구성상품 (view에서 본품과 추가구성상품을 묶어서 보여주기 위한 용도)
		orderParam.setAdditionItemFlag("Y");
		Order orderAddition = orderMapper.getOrderByParam(orderParam);

		if (orderAddition != null) {
			for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
				for (OrderItem orderItem : orderShippingInfo.getOrderItems()) {

					List<OrderItem> additionItems = new ArrayList<>();
					for (OrderShippingInfo additionOrderShippingInfo : orderAddition.getOrderShippingInfos()) {
						for (OrderItem additionOrderItem : additionOrderShippingInfo.getOrderItems()) {

							// 부모의 ITEM_ID, OPTIONS, ORDER_STATUS, ITEM_SEQUENCE 가 일치하는 경우
							if (additionOrderItem.getParentItemId() == orderItem.getItemId()
									&& orderItem.getOptions().equals(additionOrderItem.getParentItemOptions())
									&& orderItem.getOrderStatus().equals(additionOrderItem.getOrderStatus())
									&& orderItem.getItemSequence() == additionOrderItem.getParentItemSequence()) {

								additionItems.add(additionOrderItem);
							}
						}
					}

					if (additionItems.size() > 0) {
						orderItem.setAdditionItemList(additionItems);
					}
				}
			}
		}

		return order;

	}

	/**
	 * 주문정보 셋팅
	 * @param order
	 * @param orderParam
	 */
	private void bindOrder(Order order, OrderParam orderParam) {

		// 주문 결제정보 조회
		order.setOrderPayments(orderMapper.getOrderPaymentListByParam(orderParam));

		// 주문 사은품 정보 조회
		List<OrderGiftItem> orderGiftItems
				= orderGiftItemService.getOrderGiftItemListByOrderCode(order.getOrderCode());

		boolean isQuickDelivery = false;
		for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {

			for (OrderItem orderItem: orderShippingInfo.getOrderItems()) {
				setOrderGiftItemForOrderItem(orderGiftItems, orderItem);

				// 퀵배송
				if ("Y".equals(orderItem.getQuickDeliveryFlag())) {
					isQuickDelivery = true;
				}
			}
		}

		if (isQuickDelivery) {
			order.setQuickDeliveryFlag("Y");
		}

		/*
		ClaimApplyParam claimApplyParam = new ClaimApplyParam();
		claimApplyParam.setOrderCode(order.getOrderCode());
		claimApplyParam.setOrderSequence(order.getOrderSequence());

		if (ShopUtils.isOpmanagerPage()) {
			claimApplyParam.setConditionType("OPMANAGER");
		} else if (ShopUtils.isSellerPage()) {
			claimApplyParam.setSellerId(SellerUtils.getSellerId());
			claimApplyParam.setConditionType("SELLER");
		}

		// 주문취소 데이터
		List<OrderCancelApply> cancelApplyList = orderClaimApplyMapper.getOrderCancelApplyListByParam(claimApplyParam);
		if (cancelApplyList != null) {

			List<OrderCancelShipping> cancelShippingGroups = new ArrayList<>();

			/**
			 * CJH 2016.08.20
			 * 취소는 신청, 보류, 승인대기 상태의 데이터는 1Row의 데이터만 존재해야 한다.
			 * 나머지는 히스토리 만들다 보니 이렇게 되었다... 엉망이 되어가고있다..
			 *
			for(OrderCancelApply cancelApply : cancelApplyList) {
				for(OrderShippingInfo info : order.getOrderShippingInfos()) {
					for(OrderItem orderItem : info.getOrderItems()) {
						if (cancelApply.getItemSequence() == orderItem.getItemSequence()) {

							// 상품 환불금액
							cancelApply.setClaimApplyAmount(orderItem.getSalePrice() * cancelApply.getClaimApplyQuantity());

							// 상태가 거절이 아니면 클레임 수량을 반영
							if (!"99".equals(cancelApply.getClaimStatus())) {
								orderItem.setClaimApplyQuantity(orderItem.getClaimApplyQuantity() + cancelApply.getClaimApplyQuantity());
							}

							if (("01".equals(cancelApply.getClaimStatus()) || "02".equals(cancelApply.getClaimStatus())) || "03".equals(cancelApply.getClaimStatus())) {

								orderItem.setCancelApply(cancelApply);
								if (("01".equals(cancelApply.getClaimStatus()) || "02".equals(cancelApply.getClaimStatus()))) {
									OrderShipping orderShipping = orderItem.getOrderShipping();
									OrderCancelShipping shippingGroup = null;
									for(OrderCancelShipping group : cancelShippingGroups) {
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
										cancelShippingGroups.add(shippingGroup);
									}
								}
							}
						}
					}
				}
			}

			order.setCancelShippingGroups(cancelShippingGroups);
		}
		*/

		// 반품신청 데이터
		/*
		List<OrderReturnApply> returnApplyList = orderClaimApplyMapper.getOrderReturnApplyListByParam(claimApplyParam);
		if (returnApplyList != null) {

			for(OrderReturnApply returnApply : returnApplyList) {

				for(OrderShippingInfo info : order.getOrderShippingInfos()) {
					for(OrderItem orderItem : info.getOrderItems()) {
						if (returnApply.getItemSequence() == orderItem.getItemSequence()) {

							// 상품 환불금액
							returnApply.setClaimApplyAmount(orderItem.getSalePrice() * returnApply.getClaimApplyQuantity());

							// 상태가 거절이 아니면 클레임 수량을 반영
							if (!"99".equals(returnApply.getClaimStatus())) {
								orderItem.setClaimApplyQuantity(orderItem.getClaimApplyQuantity() + returnApply.getClaimApplyQuantity());
							}

							if (("01".equals(returnApply.getClaimStatus()) || "02".equals(returnApply.getClaimStatus())) || "03".equals(returnApply.getClaimStatus())
									|| "10".equals(returnApply.getClaimStatus())) {

								orderItem.setReturnApply(returnApply);
							}
						}
					}
				}


				ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
				shipmentReturnParam.setShipmentReturnId(returnApply.getShipmentReturnId());
				ShipmentReturn shipmentReturn = shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
				if (shipmentReturn != null) {
					returnApply.setShipmentReturn(shipmentReturn);
				}

				Seller shipmentReturnSeller = sellerMapper.getSellerById(returnApply.getShipmentReturnSellerId());
				if (shipmentReturnSeller != null) {
					returnApply.setSeller(shipmentReturnSeller);
				}
			}

			order.setReturnApplys(returnApplyList);
		}
		*/
	}

	@Override
	public int getShippingCoupon(long userId, int addPoint) {

		// 비회원
		if (userId == 0) {
			if (!UserUtils.isUserLogin()) {
				return 0;
			}

			userId = UserUtils.getUserId();
		}

		// 회원 사용 가능 포인트 조회
		AvailablePoint availablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.SHIPPING_COUPON_CODE);
		int retentionPoint = availablePoint.getAvailablePoint() + addPoint; // 보유 포인트
		// 0보다 작은경우가 있을수도..
		if (retentionPoint <= 0) {
			return 0;
		}

		return retentionPoint;
	}

	@Override
	public int getRetentionPoint(long userId, int addPoint) {

		// 비회원
		if (userId == 0) {
			if (!UserUtils.isUserLogin()) {
				return 0;
			}

			userId = UserUtils.getUserId();
		}

		Config shopConfig = ShopUtils.getConfig();

		// 회원 사용 가능 포인트 조회
		AvailablePoint availablePoint = pointService.getAvailablePointByUserId(userId, PointUtils.DEFAULT_POINT_CODE);
		int retentionPoint = availablePoint.getAvailablePoint() + addPoint; // 보유 포인트
		// 0보다 작은경우가 있을수도..
		if (retentionPoint <= 0) {
			return 0;
		}

		// 보유 포인트보다 사용 가능 최대 포인트가 큰경우
		int pointUseMax = shopConfig.getPointUseMax();
		int pointUseMin = shopConfig.getPointUseMin();

		// 최대 사용 가능 포인트가 0인경우는 포인트기능 OFF.. 사용 가능포인트는 0임.
		if (pointUseMax == 0) {
			return 0;
		}

		// 최대 사용 가능 포인트가 0보다 작은경우는 재한 없음.
		if (pointUseMax > 0) {
			// 최대 사용 가능 포인트가 0보다 큰경우 보유 포인트가 더 크면 최대 사용 가능 포인트로 포인트 셋팅
			if (retentionPoint > pointUseMax) {
				retentionPoint = shopConfig.getPointUseMax();
			}
		}

		// 보유 포인트가 최저 사용 가능 포인트보다 작은경우 사용 가능포인트 0
		if (retentionPoint < pointUseMin) {
			return 0;
		}

		return retentionPoint;
	}

	@Override
	public List<OrderCount> getOpmanagerOrderCountAll() {

		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("OPMANAGER");

		return orderMapper.getOrderCountListByParam(orderParam);
	}

	@Override
	public List<OrderCount> getSellerOrderCountAll() {

		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("SELLER");
		orderParam.setSellerId(SellerUtils.getSellerId());

		return orderMapper.getOrderCountListByParam(orderParam);
	}

	@Override
	public List<OrderCount> getUserOrderCountAll() {

		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("FRONT");
		orderParam.setUserId(UserUtils.getUserId());

		return orderMapper.getOrderCountListByParam(orderParam);
	}

	@Override
	public List<OrderCount> getOpmanagerOrderCountAllByMonth(int month) {
		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("OPMANAGER");

		setOrderCountAllParam(orderParam, month);

		return orderMapper.getOrderCountListByParam(orderParam);
	}

	@Override
	public List<OrderCount> getSellerOrderCountAllByMonth(int month) {
		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("SELLER");
		orderParam.setSellerId(SellerUtils.getSellerId());

		setOrderCountAllParam(orderParam, month);

		return orderMapper.getOrderCountListByParam(orderParam);
	}

	@Override
	public List<OrderCount> getUserOrderCountAllByMonth(int month) {

		OrderParam orderParam = new OrderParam();
		orderParam.setConditionType("FRONT");
		orderParam.setUserId(UserUtils.getUserId());

		setOrderCountAllParam(orderParam, month);

		return orderMapper.getOrderCountListByParam(orderParam);
	}

	private void setOrderCountAllParam(OrderParam orderParam, int month) {

		if (month > 0) {

			month = month > 0 ? month * -1 : month;

			String today = DateUtils.getToday(DATE_FORMAT);
			orderParam.setSearchEndDate(today);
			orderParam.setSearchStartDate(DateUtils.addMonth(today, month));
		}
	}

	@Override
	public HashMap<String, BuyPayment> getPaymentType() {

		HashMap<String, BuyPayment> map = new HashMap<String, BuyPayment>();

		Config shopConfig = configService.getShopConfig(Config.SHOP_CONFIG_ID);
		ConfigPg configPg = configPgService.getConfigPg();

		if ("Y".equals(shopConfig.getPaymentBank())) {
			List<AccountNumber> accountNumberList = null;
			if (shopConfig.getPaymentBank() != null) {
				accountNumberList = accountNumberService.getUseAccountNumberListAll();
				if (!accountNumberList.isEmpty()) {
					map.put("bank", new BuyPayment("bank", accountNumberList, configPg));
				}
			}
		}

		if (UserUtils.isUserLogin()) {
			for(String ptCode : PointUtils.getPointTypes()) {
				map.put(ptCode, new BuyPayment(ptCode, configPg));
			}
		}

		if ("Y".equals(shopConfig.getPaymentCard())) {
			map.put("card", new BuyPayment("card", configPg));
		}

		if ("Y".equals(shopConfig.getPaymentHp())) {
			map.put("hp", new BuyPayment("hp", configPg));
		}

		if ("Y".equals(shopConfig.getPaymentRealtime())) {
			map.put("realtimebank", new BuyPayment("realtimebank", configPg));
		}

		if ("Y".equals(shopConfig.getPaymentVbank())) {
			map.put("vbank", new BuyPayment("vbank", configPg));
		}

		if ("Y".equals(shopConfig.getPaymentEscrow())) {
			map.put("escrow", new BuyPayment("escrow", configPg));
		}

		if (configPg.isUseNpayPayment()) {
            map.put("naverpay", new BuyPayment("naverpay", configPg));
        }

		// [Kakaopay] 관리자 결제 정보 설정 DB에는 추가하지 않음.
		//map.put("kakaopay", new BuyPayment("kakaopay", configPg));

		map.put("payco", new BuyPayment("payco", configPg));

		return map;

	}

	@Override
	public Buy getBuyForStep1(OrderParam orderParam) {
		Buy buy = this.getOrderTemp(orderParam);
		UserDelivery defaultUserDelivery = userDeliveryService.getDefaultUserDelivery();
		if (buy == null) {

			buy = new Buy();
			Buyer buyer = new Buyer();
			Receiver receiver = new Receiver();

			boolean isDeliverySet = false;
			if (defaultUserDelivery != null) {
				isDeliverySet = true;
				//				receiver.setReceiveCompanyName(defaultUserDelivery.getCompanyName());
				receiver.setReceiveName(defaultUserDelivery.getUserName());
				receiver.setReceiveMobile(defaultUserDelivery.getMobile());
				receiver.setReceivePhone(defaultUserDelivery.getPhone());
				receiver.setReceiveNewZipcode(defaultUserDelivery.getNewZipcode());
				receiver.setReceiveZipcode(defaultUserDelivery.getZipcode());
				receiver.setReceiveAddress(defaultUserDelivery.getAddress());
				receiver.setReceiveAddressDetail(defaultUserDelivery.getAddressDetail());
				receiver.setReceiveSido(defaultUserDelivery.getSido());
				receiver.setReceiveSigungu(defaultUserDelivery.getSigungu());
				receiver.setReceiveEupmyeondong(defaultUserDelivery.getEupmyeondong());
			}

			// 회원 로그인 상태일경우 회원 정보를 가져옴..
			if (UserUtils.isUserLogin()) {

				User user = UserUtils.getUser();
				UserDetail userDetail = userService.getUserDetail(user.getUserId());
				//UserDetail userDetail = (UserDetail) user.getUserDetail();

				buyer.setUserName(user.getUserName());
				if (isDeliverySet == false) {
					receiver.setReceiveName(user.getUserName());
				}

                buyer.setLoginId(user.getLoginId());
				buyer.setEmail(user.getEmail());
				if (userDetail != null) {
					//					buyer.setCompanyName(userDetail.getCompanyName());

					buyer.setMobile(userDetail.getPhoneNumber());
					buyer.setPhone(userDetail.getTelNumber());
					buyer.setNewZipcode(userDetail.getNewPost());
					buyer.setZipcode(userDetail.getPost());
					buyer.setZipcode1(userDetail.getPost1());
					buyer.setZipcode2(userDetail.getPost2());
					buyer.setAddress(userDetail.getAddress());
					buyer.setAddressDetail(userDetail.getAddressDetail());

					buyer.setSido(ShopUtils.getSido(userDetail.getAddress()));
					buyer.setSigungu(ShopUtils.getSigungu(userDetail.getAddress()));
					buyer.setEupmyeondong(ShopUtils.getEupmyeondong(userDetail.getAddress()));

					if (isDeliverySet == false) {
						//receiver.setReceiveCompanyName(userDetail.getCompanyName());
						receiver.setReceiveMobile(userDetail.getPhoneNumber());
						receiver.setReceivePhone(userDetail.getTelNumber());
						receiver.setReceiveNewZipcode(userDetail.getNewPost());
						receiver.setReceiveZipcode(userDetail.getPost());
						receiver.setReceiveZipcode1(userDetail.getPost1());
						receiver.setReceiveZipcode2(userDetail.getPost2());
						receiver.setReceiveAddress(userDetail.getAddress());

						receiver.setReceiveSido(ShopUtils.getSido(userDetail.getAddress()));
						receiver.setReceiveSigungu(ShopUtils.getSigungu(userDetail.getAddress()));
						receiver.setReceiveEupmyeondong(ShopUtils.getEupmyeondong(userDetail.getAddress()));

						receiver.setReceiveAddressDetail(userDetail.getAddressDetail());
					}
				}
			}

			buy.setUserId(orderParam.getUserId());
			buy.setSessionId(orderParam.getSessionId());
			buy.setBuyer(buyer);

			// step1에 들어올때는 배송지 정보가 1개임
			List<Receiver> receivers = new ArrayList<>();
			receiver.setShippingIndex(0);
			receivers.add(receiver);
			buy.setReceivers(receivers);
		}

		List<BuyItem> items = this.getOrderItemTempList(orderParam);
		if (items == null) {
			throw new OrderException("결제가능 상품이 없습니다.", "/cart");
		}

		// CJH 추가 구성품이 있는경우 복수배송지 선택 불가
		boolean isAdditionItem = false;
		for(BuyItem item : items) {
			if ("Y".equals(item.getAdditionItemFlag())) {
				isAdditionItem = true;
			}
		}

		buy.setAdditionItem(isAdditionItem);


		HashMap<Long, String> sellerMap = new HashMap<>();
		for(Receiver receiver : buy.getReceivers()) {

			receiver.setItems(items);

			// 상품쿠폰 적용
			receiver.itemCouponUsed(false, buy, receiver.getShippingIndex());

			// 배송지별 구매상품 초기화
			List<BuyQuantity> buyQuantitys = new ArrayList<>();
			for(BuyItem buyItem : items) {
				BuyQuantity buyQuantity = new BuyQuantity();
				buyQuantity.setItemSequence(buyItem.getItemSequence());
				buyQuantity.setQuantity(buyItem.getItemPrice().getQuantity());
				buyQuantitys.add(buyQuantity);

				// 개인정보 제 3자 제공 및 수집 이용동의
				if ("2".equals(buyItem.getItem().getShippingType())) {
					Seller seller = sellerMapper.getSellerById(buyItem.getSellerId());
					if (seller != null) {
						sellerMap.put(seller.getSellerId(), seller.getSellerName());
					}
				}
			}

			receiver.setBuyQuantitys(buyQuantitys);

			String islandType = "";
			if (StringUtils.isEmpty(receiver.getReceiveZipcode()) == false) {
				islandType = orderMapper.getIslandTypeByZipcode(receiver.getReceiveZipcode());
			}


			// 상품단위로 처리되는 기존 로직을 유지하기 위해 items에 추가한 추가구성상품 삭제
			List<BuyItem> itemList = items.stream().filter(l -> "N".equals(l.getAdditionItemFlag())).collect(Collectors.toList());
			items.clear();
			items.addAll(itemList);

			receiver.setItems(items);
			receiver.setShipping(islandType);
		}

		String sellerNames = "";
		for(Long sellerId : sellerMap.keySet()) {
			sellerNames += (StringUtils.isEmpty(sellerNames) ? "" : ", ") + sellerMap.get(sellerId);
		}
		buy.setSellerNames(sellerNames);

		// 결제금액 재계산
		buy.setOrderPrice(0, configService.getShopConfig(Config.SHOP_CONFIG_ID));

		// 회원 사용가능 포인트 조회
		buy.setRetentionPoint(this.getRetentionPoint(orderParam.getUserId(), 0));

		// 회원 사용가능 배송비 할인 쿠폰
		buy.setShippingCoupon(this.getShippingCoupon(orderParam.getUserId(), 0));

		// 회원 기본배송지 정보 저장
		buy.setDefaultUserDelivery(defaultUserDelivery);

		return buy;
	}

	@Override
	public Buy getOrderTemp(OrderParam orderParam) {
		return orderMapper.getOrderTemp(orderParam);
	}

	@Override
	public void insertOrderItemTemp(BuyItem buyItem) {
		orderMapper.insertOrderItemTemp(buyItem);
	}

	@Override
	public void deleteOrderItemTemp(long userId, String sessionId) {

		OrderParam orderParam = new OrderParam();
		orderParam.setUserId(userId);
		orderParam.setSessionId(sessionId);

		orderMapper.deleteOrderItemTemp(orderParam);
	}

	@Override
	public void deleteOrderTemp(long userId, String sessionId) {
		Buy buy = new Buy();
		buy.setUserId(userId);
		buy.setSessionId(sessionId);
		orderMapper.deleteOrderTemp(buy);
	}

	private void setOrderItemInfo(List<BuyItem> list, OrderParam orderParam) {
		setOrderItemInfo(list, orderParam, null);
	}

	private void setOrderItemInfo(List<BuyItem> list, OrderParam orderParam, HashMap<String, Integer> buyQuantityMap) {
		// 상품에 사용가능한 쿠폰 조회
		List<OrderCoupon> orderCouponListForItemAll = null;
		List<OrderCoupon> orderCouponListForItem = null;
		if (UserUtils.isUserLogin()) {
			orderCouponListForItem = couponService.getUserCouponListForItemTarget(list, orderParam.getUserId(), orderParam.getViewTarget());
			orderCouponListForItemAll = couponService.getUserCouponListForItemAll(orderParam.getUserId(), orderParam.getViewTarget());

			User user = userService.getUserByUserId(UserUtils.getUserId());
			UserDetail userDetail = (UserDetail) user.getUserDetail();

			// 회원 등급별 포인트 적립액 조회
			UserLevel userLevel = userLevelMapper.getUserLevelById(userDetail.getLevelId());
			for (BuyItem buyItem : list) {
				buyItem.setUserLevel(userLevel);
			}

		}

		HashMap<String, Integer> buyQuantityItemUserCodeMap = new HashMap<>();
		for (BuyItem buyItem : list) {

			Item item = buyItem.getItem();

            item.setItemOptions(itemService.getItemOptionList(item, false));

			// 1. 상품의 필수 옵션을 구성
			buyItem.setOptionList(ShopUtils.getRequiredItemOptions(item, buyItem.getOptions()));

			String itemUserCode = item.getItemUserCode();
			if (buyQuantityItemUserCodeMap.get(itemUserCode) == null) {
				buyQuantityItemUserCodeMap.put(itemUserCode, buyItem.getItemPrice().getQuantity());
			} else {
				buyQuantityItemUserCodeMap.put(itemUserCode, buyQuantityItemUserCodeMap.get(itemUserCode) + buyItem.getItemPrice().getQuantity());
			}
		}

		// 재고 차감 + 재고 검증용
		HashMap<String, HashMap<String, Integer>> stockMap = ShopUtils.makeStockMap(list);

		if (stockMap == null) {
			stockMap = new HashMap<String, HashMap<String, Integer>>();
		}

		if (buyQuantityMap == null) {
			buyQuantityMap = new HashMap<>();
		}

		for (BuyItem buyItem : list) {

			Item item = buyItem.getItem();

			if (item == null || buyItem == null) {
				continue;
			}
			// 3. 상품의 최대 구매 가능 수량을 책정하여 판매 불가 상품인지 검증 - 필수 선택 옵션정보도 포함하여 검증
			ShopUtils.getItemMaxQuantity(buyItem, stockMap, buyQuantityMap);

			// 상품 코드가 같은 상품의 구매 총 수량이 상품에 설정된 최소 구매수량보다 적은경우 구매 못함

			boolean isError = false;
			String itemUserCode = item.getItemUserCode();
			if (buyQuantityItemUserCodeMap.get(itemUserCode) != null) {
				if (item.getOrderMinQuantity() > buyQuantityItemUserCodeMap.get(itemUserCode)) {
					isError = true;
				}
			}

			if (isError) {
				throw new OrderException("해당 상품의 최소 구매가능 수량은 "+ item.getOrderMinQuantity() +"개 입니다.", "/cart");
			}

			OrderQuantity orderQuantity = buyItem.getOrderQuantity();
			boolean isSoldOut = orderQuantity.getMaxQuantity() == 0 ? true : false;

			// 4. 상품이 판매 종료된 상품인지 검사
			if (isSoldOut) {
				throw new OrderException(item.getItemName() + "상품의 재고가 없습니다.", "/cart");
			} else {

				// 상품에 옵션이 있고 상품 옵션 구성이 null인경우 상품의 구성이 변경됨..
				if ("Y".equals(item.getItemOptionFlag())) {
					if (item.getItemOptions() != null) {

						if (buyItem.getOptionList() == null) {
							buyItem.setAvailableForSaleFlag("N");
							buyItem.setSystemComment(MessageUtils.getMessage("M00485") + MessageUtils.getMessage("M00484"));
						}
					}
				}

				if (buyItem.getAvailableForSaleFlag().equals("Y")) {
					if (StringUtils.isNotEmpty(buyItem.getOptions())) {
						// 장바구니에는 상품의 옵션정보가 선택되어있으나 상품의 구성이 변경됨.. ex) 상품의 옵션 삭제등..
						if (buyItem.getOptionList() == null) {
							buyItem.setAvailableForSaleFlag("N");
							buyItem.setSystemComment(MessageUtils.getMessage("M00485") + MessageUtils.getMessage("M00484"));
						}
					}
				}
			}

			if ("Y".equals(buyItem.getAvailableForSaleFlag())) {

				// 적립금은 로그인한 회원에게만 부여됨..
				if (UserUtils.isUserLogin()) {
					buyItem.setPointPolicy(pointService.getPointPolicyByItemId(item.getItemId()));
				}

				// 상품 금액 개산
				buyItem.setItemPrice(new ItemPrice(buyItem));

				// 전체 상품에 사용가능 쿠폰 - 일반 쿠폰
				buyItem.setItemCoupons(orderCouponListForItemAll, true);

				// 해당 상품에 사용가능 쿠폰 - 일반 쿠폰
				buyItem.setItemCoupons(orderCouponListForItem, false);

			}

			// 사은품
			if ("Y".equals(item.getFreeGiftFlag())) {

				try {
					List<GiftItem> freeGiftItemList = giftItemService.getGiftItemListForFront(item.getItemId());
					buyItem.setFreeGiftItemText(ShopUtils.makeGiftItemText(freeGiftItemList));
					buyItem.setFreeGiftItemList(ShopUtils.conventGiftItemInfoList(freeGiftItemList));
				} catch (Exception e) {
					log.error(ERROR_MARKER, e.getMessage(), e);
				}

			}
		}
	}

	@Override
	public List<BuyItem> getOrderItemTempList(OrderParam orderParam) {
		List<BuyItem> list = orderMapper.getOrderItemTempList(orderParam);

		if (ValidationUtils.isNull(list)) {
			return null;
		}

		setOrderItemInfo(list, orderParam);


		// 추가구성상품
		orderParam.setAdditionItemFlag("Y");
		List<BuyItem> additionItems = orderMapper.getOrderItemTempList(orderParam);

		setOrderItemInfo(additionItems, orderParam);

		for (int i = 0; i < list.size(); i++) {
			BuyItem buyItem = list.get(i);
			List<BuyItem> buyAdditionItems = new ArrayList<>();

			for (BuyItem additionItem : additionItems) {
				int itemId = buyItem.getItem().getItemId();

				// 부모의 ITEM_ID, OPTIONS 가 일치할 경우
				if (additionItem.getParentItemId() == itemId && buyItem.getOptions().equals(additionItem.getParentItemOptions())) {
					additionItem.setItemPrice(new ItemPrice(additionItem));
					buyAdditionItems.add(additionItem);
				}
			}

			buyItem.setAdditionItemList(buyAdditionItems);
		}

		return list;
	}

	// 결제 금액 검증
	private void payAmountVerification(Buy buy, OrderPrice postOrderPrice) {
		OrderPrice orderPrice = buy.getOrderPrice();

		Config shopConfig = ShopUtils.getConfig();
		if (postOrderPrice.getTotalPointDiscountAmount() > 0) {

			// 비회원이 포인트 사용?
			if (buy.getIsLogin() == false) {
				throw new OrderException(MessageUtils.getMessage("M00481")); // 잘못된 접근입니다.
			}

			// 사용 가능한 포인트 금액보다 더 많이씀..
			int retentionPoint = this.getRetentionPoint(buy.getUserId(), 0);
			if (postOrderPrice.getTotalPointDiscountAmount() > retentionPoint) {
				throw new OrderException(MessageUtils.getMessage("M01261"));	// 해당 주문에서 사용 가능한 포인트를 다시 확인해주세요.
			}


			int pointUseMax = shopConfig.getPointUseMax();
			int pointUseMin = shopConfig.getPointUseMin();

			if (pointUseMax == 0) {
				throw new OrderException(MessageUtils.getMessage("M01262"));	// 포인트사용 기능은 현재 사이트에서 재공되지 않습니다.
			}

			if (postOrderPrice.getTotalPointDiscountAmount() < pointUseMin) {
				throw new OrderException(MessageUtils.getMessage("M01261"));
			}

			if (postOrderPrice.getTotalPointDiscountAmount() != orderPrice.getTotalPointDiscountAmount()) {
				throw new OrderException(MessageUtils.getMessage("M01263"));	// 포인트 사용 금액이 잘못 되었습니다.
			}
		}

		if (postOrderPrice.getTotalShippingCouponUseCount() > 0) {
			int shippingCoupon = this.getShippingCoupon(buy.getUserId(), 0);
			if (postOrderPrice.getTotalShippingCouponUseCount() > shippingCoupon) {
				throw new OrderException("사용가능한 배송비 쿠폰 수량을 확인해주세요.");
			}

			if (postOrderPrice.getTotalShippingCouponDiscountAmount()  != orderPrice.getTotalShippingCouponDiscountAmount()) {
				throw new OrderException("베송비 쿠폰 사용액을 확인해주세요.");
			}
		}

		int totalCouponDiscountAmount = postOrderPrice.getTotalItemCouponDiscountAmount() + postOrderPrice.getTotalCartCouponDiscountAmount();


		if (totalCouponDiscountAmount != orderPrice.getTotalCouponDiscountAmount()) {
			throw new OrderException(MessageUtils.getMessage("M01264"));	// 쿠폰 사용 금액이 잘못 되었습니다.
		}


		if (postOrderPrice.getOrderPayAmount() != orderPrice.getOrderPayAmount()) {
			throw new OrderException(MessageUtils.getMessage("M01265"));	// 결제 금액이 변경 되어 주문을 계속 진행하실수 없습니다.
		}

		int minimumPaymentAmount = shopConfig.getMinimumPaymentAmount();
		if (minimumPaymentAmount > 0) {
			if (orderPrice.getOrderPayAmount() < minimumPaymentAmount) {
				throw new OrderException(MessageUtils.getMessage("M01046") +" (" + NumberUtils.formatNumber(minimumPaymentAmount, "#,##0") + ") "+ MessageUtils.getMessage("M01266"));	//최소 결제 가능 금액(1,000)보다 결제 시도 하시는 금액이 작아 결제를 진행하실수 없습니다.
			}
		}
	}

	private OrderPrice newOrderPrice(OrderPrice orderPrice) {

		OrderPrice newOrderPrice = new OrderPrice();
		newOrderPrice.setTotalItemCouponDiscountAmount(orderPrice.getTotalItemCouponDiscountAmount());
		newOrderPrice.setTotalCartCouponDiscountAmount(orderPrice.getTotalCartCouponDiscountAmount());
		newOrderPrice.setTotalPointDiscountAmount(orderPrice.getTotalPointDiscountAmount());
		newOrderPrice.setOrderPayAmount(orderPrice.getOrderPayAmount());
		newOrderPrice.setTotalShippingCouponUseCount(orderPrice.getTotalShippingCouponUseCount());
		newOrderPrice.setTotalShippingCouponDiscountAmount(orderPrice.getTotalShippingCouponDiscountAmount());

		return newOrderPrice;
	}

	@Override
	public HashMap<String, Object> saveOrderTemp(HttpSession session, Buy buy) {

		String escrowStatus = "N";

		OrderParam orderParam = new OrderParam();
		orderParam.setSessionId(buy.getSessionId());
		orderParam.setUserId(buy.getUserId());
		orderParam.setViewTarget(buy.getDeviceType());

		OrderPrice orderPrice = buy.getOrderPrice();

        String createdDate = DateUtils.getToday(DATETIME_FORMAT);

		// Post로 넘어온 결제 정보로 새로운 객채를 생성하여 계산된 결제 금액과 동일한지 체크함
		OrderPrice postOrderPrice = this.newOrderPrice(orderPrice);
		List<BuyItem> list = orderMapper.getOrderItemTempList(orderParam);
		if (list == null) {
			throw new OrderException("주문 가능 상품이 없습니다.", "/cart");
		}

		// 배송지 지정되지 않은 상품있는지 체크용
		HashMap<String, Integer> checkQuantityTotal = new HashMap<>();
		for(BuyItem buyItem : list) {
			ItemPrice itemPrice = buyItem.getItemPrice();
			String key = "item-" + buyItem.getItemSequence();

			int addCount = 0;
			if (checkQuantityTotal.get(key) != null) {
				addCount = checkQuantityTotal.get(key);
			}

			checkQuantityTotal.put(key, itemPrice.getQuantity() + addCount);
		}

		// 복합 배송지로인한 상품정보 재정의
		int shippingIndex = 0;
		for(Receiver receiver : buy.getReceivers()) {

			receiver.setShippingIndex(shippingIndex);
			List<BuyItem> items = new ArrayList<>();

			for(BuyQuantity buyQuantity : receiver.getBuyQuantitys()) {

				for(BuyItem buyItem : list) {
					if (buyQuantity.getItemSequence() == buyItem.getItemSequence()) {

						// 복사해야됨...ㅠㅠ
						BuyItem cloneObject;

						try {

							String checkKey = "item-" + buyQuantity.getItemSequence();
							int buyTotalCount = checkQuantityTotal.get(checkKey);
							if (buyTotalCount - buyQuantity.getQuantity() == 0) {
								checkQuantityTotal.remove(checkKey);
							} else {

								if (buyTotalCount - buyQuantity.getQuantity() > 0) {
									checkQuantityTotal.put(checkKey, buyTotalCount - buyQuantity.getQuantity());
								} else {

									// 장바구니에 담겨있는 수량보다 구매시도 수량이 많은경우 멈춰!!
									throw new OrderException("장바구니에 담겨있는 수량보다 구매시도 수량이 많습니다.");
								}
							}


							cloneObject = (BuyItem) buyItem.clone();
							cloneObject.getItemPrice().setQuantity(buyQuantity.getQuantity());

							// 퀵배송
							if ("Y".equals(buy.getQuickDeliveryFlag())) {
								cloneObject.setQuickDeliveryFlag("Y");
							}

							items.add(cloneObject);

						} catch (CloneNotSupportedException e) {
							throw new OrderException(e.getMessage(), e);
						}

						break;
					}
				}

			}

			// 상품정보 셋팅
			this.setOrderItemInfo(items, orderParam);

			// 구매가능여부 채크
			ShopUtils.buyVerification(items, items.size());

			receiver.setItems(items);

			// 상품쿠폰 적용
			receiver.itemCouponUsed(true, buy, receiver.getShippingIndex());

			// 구매 상품 정책별 그룹
			String zipcode = receiver.getReceiveZipcode();
			if (StringUtils.isEmpty(zipcode)) {
				zipcode = receiver.getFullReceiveZipcode();
			}

			receiver.setShipping(orderMapper.getIslandTypeByZipcode(zipcode));
			shippingIndex++;
		}

		if (checkQuantityTotal.keySet().size() > 0) {
			throw new OrderException("배송지가 지정되지 않은 상품이 있습니다.");
		}

		// 사용 포인트 적용
		orderPrice.setTotalPointDiscountAmount(postOrderPrice.getTotalPointDiscountAmount());

		// 배송비 할인쿠폰 적용 - 복수 배송지 사용하면 해당 기능 사용안됨
		int shippingCouponCount = 0;
		int totalShippingCouponDiscountAmount = 0;

		List<ShippingCoupon> insertShippingCoupons = new ArrayList<>();
		if (buy.getReceivers().size() <= 1 && UserUtils.isUserLogin()) {
			if (buy.getUseShippingCoupon() != null) {
				List<Shipping> shippings = buy.getReceivers().get(0).getItemGroups();
				for(String key : buy.getUseShippingCoupon().keySet()) {
					ShippingCoupon sCoupon = buy.getUseShippingCoupon().get(key);

					if ("Y".equals(sCoupon.getUseFlag())) {
						for(Shipping shipping : shippings) {

							// 2016.4.27 CJH - 상품 개당 배송비일때 쿠폰 사용 못함[고객사요청]
							if ("5".equals(shipping.getShippingType())) {
								continue;
							}

							sCoupon.setUseCouponCount(1);
							sCoupon.setUserId(UserUtils.getUserId());
							sCoupon.setDiscountAmount(shipping.getRealShipping());

							if (shipping.getRealShipping() > 0) {
								if (shipping.getShippingGroupCode().equals(sCoupon.getShippingGroupCode())) {
									shipping.setDiscountShipping(shipping.getRealShipping() - shipping.getAddDeliveryCharge());

									shipping.setPayShipping(shipping.getRealShipping() - shipping.getDiscountShipping());
									shippingCouponCount++;
									totalShippingCouponDiscountAmount += shipping.getDiscountShipping();
									insertShippingCoupons.add(sCoupon);
									break;
								}
							}
						}
					}
				}

			}
		}

		orderPrice.setTotalShippingCouponUseCount(shippingCouponCount);
		orderPrice.setTotalShippingCouponDiscountAmount(totalShippingCouponDiscountAmount);
		buy.setShippingCoupon(shippingCouponCount);

		buy.setOrderPrice(0, configService.getShopConfig(Config.SHOP_CONFIG_ID));

		HashMap<String, BuyPayment> payments = new HashMap<String, BuyPayment>();
		try {
			// 결제금액 검증
			this.payAmountVerification(buy, postOrderPrice);

			int notMixPayTypeCount = 0;
			int totalPayAmount = 0;
			int totalPointPayAmount = 0;
			HashMap<String, BuyPayment> buyPayments = buy.getBuyPayments();
			for(String key : buyPayments.keySet()) {
				BuyPayment buyPayment = buyPayments.get(key);

				//에스크로 사용여부 확인
				if(buyPayment.getEscrowStatus() != null && buyPayment.getEscrowStatus().equals("0"))
					escrowStatus = buyPayment.getEscrowStatus();

				if (buyPayment.getAmount() > 0) {

					if (ArrayUtils.contains(buy.getNotMixPayType(), key)) {
						notMixPayTypeCount++;
					}

					if (PointUtils.isPointType(key)) {
						totalPointPayAmount += buyPayment.getAmount();
					} else {
						totalPayAmount += buyPayment.getAmount();
					}

					payments.put(key, buyPayment);
				}
			}

			if (buy.getOrderPrice().getTotalPointDiscountAmount() != totalPointPayAmount) {
				throw new OrderException(MessageUtils.getMessage("M00246") + " 결제금액을 확인해주세요.");
			}

			// 결제금액 한번더 검증
			postOrderPrice.setOrderPayAmount(totalPayAmount);
			this.payAmountVerification(buy, postOrderPrice);

			if (notMixPayTypeCount > 1) {
				throw new OrderException("복합 결제가 불가능한 결제 방식을 1개이상 선택하여 결제가 진행되지 않았습니다.");
			}



		} catch (OrderException oe) {
			throw new OrderException(oe.getMessage(), oe.getRedirectUrl(), oe);
		}

		Buyer buyer = buy.getBuyer();


		// 기존 주문 임시 데이터 삭제 - 주문자 정보등만 삭제 한다. * 상품 임시 데이터는 유지
		orderMapper.deleteOrderTemp(buy);

		String productName = buy.getItems().get(0).getItem().getItemName();
		if (buy.getItems().size() > 1) {
			productName += " 외 " + (buy.getItems().size() - 1) + "개";
		}

		if (!UserUtils.isUserLogin()) {

			Receiver receiver = buy.getReceivers().get(0);

			// 비회원의 경우 주문자정보 주소 입력란이 없음.. 받는사람 주소로 대신함
			buyer.setSido(receiver.getReceiveSido());
			buyer.setSigungu(receiver.getReceiveSigungu());
			buyer.setEupmyeondong(receiver.getReceiveEupmyeondong());
			buyer.setNewZipcode(receiver.getReceiveNewZipcode());
			buyer.setZipcode(receiver.getReceiveZipcode());
			buyer.setAddress(receiver.getReceiveAddress());
			buyer.setAddressDetail(receiver.getReceiveAddressDetail());

		}

		HashMap<String, Object> map = new HashMap<>();
		map.put("productName", productName);
		map.put("userName", buyer.getUserName());
		map.put("email", buyer.getEmail());
		map.put("mobile", buyer.getFullMobile());
		map.put("orderCode", buy.getOrderCode());


		int totalTaxFreeAmount = buy.getOrderPrice().getTaxFreeAmount();

		ConfigPg configPg = configPgService.getConfigPg();
		List<String> savePaymentType = new ArrayList<>();
		for(String key : payments.keySet()) {
			BuyPayment buyPayment = payments.get(key);
			buyPayment.setData(key, configPg);

			int taxFreeAmount = 0;

			if (totalTaxFreeAmount > 0) {
				if (!PointUtils.isPointType(key)) {
					taxFreeAmount = totalTaxFreeAmount < buyPayment.getAmount() ? totalTaxFreeAmount : buyPayment.getAmount();
					totalTaxFreeAmount -= taxFreeAmount;
				}
			}

			buyPayment.setTaxFreeAmount(taxFreeAmount);

			// PG사 init이 Buy로 구현된경우 결제 금액을 전달하기위한 용도
			buy.setPgPayAmount(buyPayment.getAmount());
			if ("card".equals(key) || "vbank".equals(key) || "realtimebank".equals(key) || "escrow".equals(key)
				|| "hp".equals(key)) {

				PgData data = new PgData();

				data.setMid(buyPayment.getMid());
				data.setKeypass(buyPayment.getKey());


				data.setOrderCode(buy.getOrderCode());
				data.setAmount(Integer.toString(buyPayment.getAmount()));
				data.setTaxFreeAmount(Integer.toString(buyPayment.getTaxFreeAmount()));
				data.setDeviceType(buy.getDeviceType());

				String pgType = buyPayment.getServiceType();
				HashMap<String, Object> pgData = null;
				if ("inicis".equals(pgType)) {
					data.setOrderCode(buy.getOrderCode());
					data.setGoodname(productName);
					data.setBuyertel(buyer.getFullMobile());
					data.setBuyername(buyer.getUserName());
					data.setBuyeremail(buyer.getEmail());
					data.setUserID(Long.toString(UserUtils.getUserId()));
					data.setSessionkey(buy.getSessionId());
					data.setApprovalType(key);

					pgData = inicisService.init(data, session);
				} else if ("lgdacom".equals(pgType)) {

					/**
					 * 결제 수단 코드
					 * SC0010 : 신용카드
					 * SC0030 : 계좌이체
					 * SC0040 : 무통장입금
					 * SC0060 : 휴대폰
					 * SC0070 : 유선전화결제
					 * SC0090 : OK캐쉬백
					 * SC0111 : 문화상품권
					 * SC0112 : 게임문화 상품권
					 */

					data.setLGD_BUYER(buyer.getUserName());
					data.setLGD_BUYEREMAIL(buyer.getEmail());
					data.setLGD_CUSTOM_USABLEPAY(lgDacomService.getPayType(key));
					data.setLGD_PRODUCTINFO(productName);

					pgData = lgDacomService.init(data, session);
				} else if ("cj".equals(pgType)) {

					if (UserUtils.isUserLogin()) {
						data.setUserID(UserUtils.getLoginId());
					} else {
						data.setUserID("Guest");
					}

					data.setApprovalType(key);
					data.setUserEmail(buyer.getEmail());
					data.setUsername(buyer.getUserName());
					data.setUserPhone(buyer.getFullMobile());
					data.setGoodname(productName);
					pgData = cjService.init(data, session);
				} else if ("kspay".equals(pgType)) {

					data.setApprovalType(key);
					data.setUserEmail(buyer.getEmail());
					data.setUsername(buyer.getUserName());
					data.setUserPhone(buyer.getFullMobile().replace("-", ""));
					data.setGoodname(productName);
					data.setOrderCode(buy.getOrderCode());
					pgData = kspayService.init(data, session);
				} else if ("kcp".equals(pgType)) {

					KcpRequest kcpRequest = new KcpRequest();

					// 제품명이 30으로 제한이 걸려있음
					String goodName = StringUtils.strcut(buy.getItems().get(0).getItem().getItemName(), 20);
					if (buy.getItems().size() > 1) {
						goodName += " 외 " + (buy.getItems().size() - 1) + "개";
					}

					kcpRequest.setPay_method(key);
					kcpRequest.setOrdr_idxx(buy.getOrderCode());
					kcpRequest.setGood_name(goodName);
					kcpRequest.setGood_mny(StringUtils.integer2string(orderPrice.getOrderPayAmount()));
					kcpRequest.setBuyr_name(buyer.getUserName());
					kcpRequest.setBuyr_mail(buyer.getEmail());
					kcpRequest.setBuyr_tel1(buyer.getPhone1()+buyer.getPhone2()+buyer.getPhone3());
					kcpRequest.setBuyr_tel2(buyer.getMobile1()+buyer.getMobile2()+buyer.getMobile3());

					data.setKcpRequest(kcpRequest);

					pgData = kcpService.init(data, session);
				} else if("easypay".equals(pgType)) {
					EasypayRequest easypayRequest = new EasypayRequest();

					easypayRequest.setEp_order_no(buy.getOrderCode());
					easypayRequest.setEp_product_nm(productName);
					easypayRequest.setEp_product_amt(Integer.toString(buy.getPgPayAmount()));
					easypayRequest.setEp_user_id(buyer.getLoginId());
					easypayRequest.setEp_memb_user_no(Long.toString(buyer.getUserId()));
					easypayRequest.setEp_user_nm(buyer.getUserName());
					easypayRequest.setEp_user_mail(buyer.getEmail());
					easypayRequest.setEp_user_phone1(buyer.getFullPhone());
					easypayRequest.setEp_user_phone2(buyer.getFullMobile());
					easypayRequest.setEp_user_addr(buyer.getAddress());
					easypayRequest.setEp_pay_type(key);

					data.setEasypayRequest(easypayRequest);

					pgData = easypayService.init(data, session);
				} else if("nicepay".equals(pgType)) {

					// 휴대폰 결제일 경우 length 체크 (필드 길이 에러 발생)
					if ("hp".equals(key)) {
						productName = buy.getItems().get(0).getItem().getItemName();
						if (productName.length() > 20) {
							productName = productName.substring(0, 20) + "...";
						}

						if (buy.getItems().size() > 1) {
							productName += " 외 " + (buy.getItems().size() - 1) + "개";
						}
					}

					data.setApprovalType(key);
					data.setGoodsName(productName);
					data.setAmt(Integer.toString(buy.getPgPayAmount()));
					data.setBuyerName(buyer.getUserName());
					data.setBuyerTel(buyer.getFullMobile());
					data.setBuyerEmail(buyer.getEmail());
					data.setMoid(buy.getOrderCode());
					data.setUserIP(buy.getUserIp());

					HttpServletRequest request = RequestContextUtils.getRequestContext().getRequest();
					String token = "";

					try {
						token = JwtUtils.getToken(request);
					} catch (Exception ignore) {}

					data.setSalesonId(ShopUtils.getSalesOnIdByHeader(request));
					data.setSalesonToken(token);
					data.setSalesonTokenType(UserUtils.isUserLogin() ? "USER" : "");
					data.setSuccessUrl(buy.getSuccessUrl());
					data.setFailUrl(buy.getFailUrl());

					pgData = nicepayService.init(data, session);
				}

				map.put("pgData", pgData);

			} else if ("payco".equals(key)) {
				map.put("payco", paycoService.init(buy, session));

			} else if ("kakaopay".equals(key)) {

				String requestDealApproveUrl = environment.getProperty("kakaopay.web.path")
					+ environment.getProperty("kakaopay.msg.name");
				String MERCHANT_ID = environment.getProperty("kakaopay.mid");
				String merchantEncKey = environment.getProperty("kakaopay.merchant.enc.key");
				String merchantHashKey = environment.getProperty("kakaopay.merchant.hash.key");
				String certifiedFlag = "CN";

				String PR_TYPE = "WPM";		// 결제 요청 타입 - WPM: WEB결제, MPM: Mobile결제
				String channelType = "4";	// 채널타입 - 2:모바일웹결제 채널, 4: PC TMS 결제 채널

				if (DeviceUtils.MOBILE.equals(buy.getRealDeviceType())) {
					PR_TYPE = "MPM";		// 결제 요청 타입 - WPM: WEB결제, MPM: Mobile결제
					channelType = "2";
				}

				String requestorName = "";	// ???
				String requestorTel = "";	// ???


				String MERCHANT_TXN_NUM = buy.getOrderCode();	// 가맹점 거래번호.(주문번호)
				String PRODUCT_NAME = productName; 	// 상품명
				String AMOUNT = Integer.toString(buyPayment.getAmount());
				String serviceAmt = "0";
				String supplyAmt = "0";
				String goodsVat = "0";
				String CURRENCY = "KRW";
				String RETURN_URL = "";
				String offerPeriod = "";
				String offerPeriodFlag = "N";


				String possiCard = "";
				String fixedInt = "";
				String maxInt = "";
				String noIntYN = "N";
				String noIntOpt = "";
				String pointUseYN = "N";
				String blockCard = "";
				String blockBin = "";

				// 전문 Parameter DTO 객체 생성
				DealApproveDto approveDto = new DealApproveDto();


				// 필수값 SETTING
				approveDto.setRequestDealApproveUrl(requestDealApproveUrl); // 결제요청을 위한 URL
				approveDto.setMerchantEncKey(merchantEncKey); // 가맹점의 EncKey
				approveDto.setMerchantHashKey(merchantHashKey); // 가맹점의 HashKey

				approveDto.setCertifiedFlag(certifiedFlag); // WEB결제로 신청할시에 필수 'CN'
				approveDto.setPrType(PR_TYPE);
				approveDto.setChannelType(channelType); // TMS 및 방판 결제시 필수

				approveDto.setRequestorName(requestorName);
				approveDto.setRequestorTel(requestorTel);

				approveDto.setMerchantID(MERCHANT_ID);
				approveDto.setMerchantTxnNum(MERCHANT_TXN_NUM);

				//approveDto.setProductName(new String(PRODUCT_NAME,"UTF-8"));
				approveDto.setProductName(PRODUCT_NAME);

				approveDto.setAmount(AMOUNT);
				approveDto.setServiceAmt(serviceAmt);
				approveDto.setSupplyAmt(supplyAmt);
				approveDto.setGoodsVat(goodsVat);

				approveDto.setCurrency(CURRENCY);
				approveDto.setReturnUrl(RETURN_URL);

				approveDto.setOfferPeriod(offerPeriod);
				approveDto.setOfferPeriodFlag(offerPeriodFlag);

				approveDto.setPossiCard(possiCard);
				approveDto.setFixedInt(fixedInt);
				approveDto.setMaxInt(maxInt);
				approveDto.setNoIntYN(noIntYN);
				approveDto.setNoIntOpt(noIntOpt);
				approveDto.setPointUseYN(pointUseYN);
				approveDto.setBlockCard(blockCard);

				approveDto.setBlockBin(blockBin);

				map.put("kakaopay", kakaopayService.init(approveDto, session));
            } else if ("naverpay".equals(key)) {
                HashMap<String, Object> pgData = new HashMap<>();
                pgData.put("productName", buy.getItems().get(0).getItem().getItemName());
                pgData.put("totalPayAmount", buy.getPgPayAmount());
                pgData.put("taxScopeAmount", buy.getPgPayAmount());
                pgData.put("taxExScopeAmount", 0);

                List<HashMap<String,Object>> productItems = new ArrayList<HashMap<String,Object>>();
                int productCount = 0;
                for (BuyItem item: buy.getItems()) {
                    if (productItems.size() > 0) {

                        boolean isDuplicateItem = false;
                        int duplicateIndex = 0;

                        for(int i=0;i<productItems.size();i++) {
                            String itemCode = productItems.get(i).get("uid").toString();

                            if (itemCode.equals(item.getItemUserCode())) {
                                duplicateIndex = i;
                                isDuplicateItem = true;
                            }
                        }

                        if (isDuplicateItem) {
                            HashMap<String,Object> productItem = productItems.get(duplicateIndex);
                            productItem.put("count", (int)productItem.get("count") + item.getItemPrice().getQuantity());
                            productCount += item.getItemPrice().getQuantity();
                        } else {
                            HashMap<String,Object> newProductItem = new HashMap<String,Object>();
                            newProductItem.put("name", item.getItemName());
                            newProductItem.put("count", item.getItemPrice().getQuantity());
                            newProductItem.put("categoryType", "PRODUCT");
                            newProductItem.put("categoryId", "GENERAL");
                            newProductItem.put("uid", item.getItemUserCode());
                            productItems.add(newProductItem);
                            productCount += item.getItemPrice().getQuantity();
                        }
                    } else {
                        HashMap<String,Object> newProductItem = new HashMap<String,Object>();
                        newProductItem.put("name", item.getItemName());
                        newProductItem.put("count", item.getItemPrice().getQuantity());
                        newProductItem.put("categoryType", "PRODUCT");
                        newProductItem.put("categoryId", "GENERAL");
                        newProductItem.put("uid", item.getItemUserCode());
                        productItems.add(newProductItem);
                        productCount += item.getItemPrice().getQuantity();
                    }

                }
                pgData.put("productCount", productCount);
                pgData.put("productItems", productItems);

                map.put("naverpay", pgData);
            }


			buyPayment.setOrderCode(buy.getOrderCode());
            buyPayment.setCreatedDate(createdDate);

			orderMapper.insertOrderPaymentBuyTemp(buyPayment);

			savePaymentType.add(key);
		}

		map.put("savePaymentType", savePaymentType);

		try {
		    // 영수증 신청시 필요한 사업자등록번호, 휴대전화번호등을 조합
            String cashbillCode = "010-000-1234";

            if (CashbillType.BUSINESS == buy.getCashbill().getCashbillType()) {
                cashbillCode = buy.getCashbill().getBusinessNumber1() + "-" + buy.getCashbill().getBusinessNumber2() + "-" + buy.getCashbill().getBusinessNumber3();
            } else if (CashbillType.PERSONAL == buy.getCashbill().getCashbillType()) {
                cashbillCode = buy.getCashbill().getCashbillPhone1() + "-" + buy.getCashbill().getCashbillPhone2() + "-" + buy.getCashbill().getCashbillPhone3();
            }

            buy.getCashbill().setCashbillCode(cashbillCode);
            buy.setCreatedDate(createdDate);

			orderMapper.insertOrderTemp(buy);
		} catch (Exception e) {
			log.error(ERROR_MARKER, e.getMessage(), e);

			String errorMessage = "주문처리에 실패 했습니다. 잠시 후 다시 시도해 주십시오. - ETC";

			if (e instanceof DuplicateKeyException) {
				errorMessage = "주문처리가 잠시 지연되고 있습니다. 잠시 후 다시 시도해 주십시오. - OrderCode";
			}

			throw new OrderException(errorMessage, e);

		}

		// 배송비 쿠폰 사용 임시 저장
		if (!insertShippingCoupons.isEmpty()) {
			for(ShippingCoupon sCoupon : insertShippingCoupons) {
				sCoupon.setOrderCode(buy.getOrderCode());
				orderMapper.insertOrderShippingCouponBuyTemp(sCoupon);
			}
		}

		for(Receiver receiver : buy.getReceivers()) {

			receiver.setUserId(buy.getUserId());
			receiver.setOrderCode(buy.getOrderCode());
			receiver.setSessionId(buy.getSessionId());
            receiver.setCreatedDate(createdDate);

			orderMapper.insertOrderShippingBuyTemp(receiver);

			// 주문상품 복사
			for(BuyItem buyItem : receiver.getItems()) {
				buyItem.setOrderCode(buy.getOrderCode());
				buyItem.setShippingIndex(receiver.getShippingIndex());
				buyItem.setCampaignCode(buy.getCampaignCode());
				buyItem.setCreatedDate(createdDate);

				if(!escrowStatus.equals("N")) {
					orderMapper.insertOrderItemBuyTempForEscrow(buyItem);
				} else {
					orderMapper.insertOrderItemBuyTemp(buyItem);
				}
			}
		}

		// 주문자 정보 기본정보로 저장 체크시 2017-05-18 yulsun.yoo
		if ("1".equals(buy.getDefaultBuyerCheck())) {
			buy.getBuyer().setUserId(buy.getUserId());
			userService.updateUserDetailForOrder(buy.getBuyer());
		}

		return map;
	}

	@Override
	public Buy getOrderCouponData(Buy buy) {

		if (!UserUtils.isUserLogin()) {
			return null;
		}

		OrderParam orderParam = new OrderParam();
		orderParam.setUserId(UserUtils.getUserId());
		orderParam.setViewTarget(buy.getDeviceType());

		List<BuyItem> list = this.getOrderItemTempList(orderParam);

		if (list == null) {
			return null;
		}


		// 상품단위로 처리되는 기존 로직을 유지하기 위해 list에 추가한 추가구성상품 삭제
		List<BuyItem> itemList = list.stream().filter(l -> "N".equals(l.getAdditionItemFlag())).collect(Collectors.toList());
		list.clear();
		list.addAll(itemList);


		int shippingIndex = 0;
		for(Receiver receiver : buy.getReceivers()) {

			receiver.setShippingIndex(shippingIndex++);

			List<BuyItem> items = new ArrayList<>();

			for(BuyQuantity buyQuantity : receiver.getBuyQuantitys()) {

				for(BuyItem buyItem : list) {
					if (buyQuantity.getItemSequence() == buyItem.getItemSequence()) {

						// 복사해야됨...ㅠㅠ
						BuyItem cloneObject;

						try {

							int itemQuantity = buyQuantity.getQuantity();

							cloneObject = (BuyItem) buyItem.clone();
							cloneObject.getItemPrice().setQuantity(itemQuantity);

							// 2017.03.07 youngki.kim 쿠폰 적용이 해당 배송지 상품의 수량 대로 되도록 수정
							List<OrderCoupon> orderCouponList = new ArrayList<>();

							List<OrderCoupon> cloneObjectItemCouponList = cloneObject.getItemCoupons();

							if (ValidationUtils.isNotNull(cloneObjectItemCouponList) && !cloneObjectItemCouponList.isEmpty()) {

								for (OrderCoupon coupon : cloneObjectItemCouponList) {

									// 2: 구매 수량만큼 할인

									if ("2".equals(coupon.getCouponConcurrently())) {
										coupon.setDiscountAmount(coupon.getDiscountPrice() * itemQuantity);
									}

									orderCouponList.add(coupon);
								}

								cloneObject.setItemCoupons(orderCouponList);

							} else {
								cloneObject.setItemCoupons(cloneObjectItemCouponList);
							}

							items.add(cloneObject);

						} catch (CloneNotSupportedException e) {
							throw new OrderException(e.getMessage(), e);
						}

						break;
					}
				}

			}

			// 상품쿠폰 적용
			receiver.itemCouponUsed(false, buy, receiver.getShippingIndex());

			receiver.setItems(items);

			receiver.setShipping("");
		}

		buy.setOrderPrice(0, configService.getShopConfig(Config.SHOP_CONFIG_ID));

		return buy;
	}

	@Override
	public String getNewOrderCode(OrderCodePrefix orderCodePrefix) {
		String prefix = orderCodePrefix.getCode();
		return prefix + sequenceService.getId("OP_ORDER_CODE");
	}

	@Override
	public String insertOrder(OrderParam orderParam, Object pgData, HttpSession session, HttpServletRequest request) {

		if (pgData != null) {
			if (!(pgData instanceof PgData || pgData instanceof ReservationResponse || pgData instanceof CjResult)) {
				throw new OrderException("다른 객체가 들어옴");
			}
		}

		ErpOrder erpOrder = new ErpOrder(ErpOrderType.ORDER);

		// PG 결제
		ConfigPg configPg = configPgService.getConfigPg();
		String pgType = "";
		String autoCashReceipt = "";

		if (configPg != null) {
			pgType = configPg.getPgType().getCode().toLowerCase();
			autoCashReceipt = configPg.isUseAutoCashReceipt() ? "Y" : "N";
		} else {
			pgType = SalesonProperty.getPgService();
			autoCashReceipt = environment.getProperty("pg.autoCashReceipt");
		}

		if (StringUtils.isEmpty(orderParam.getOrderCode())){
			if ("easypay".equals(pgType)) {
				orderParam.setOrderCode(request.getParameter("sp_order_no"));
			} else if ("nicepay".equals(pgType)) {
				orderParam.setOrderCode(request.getParameter("Moid"));
			}
		}

		Buy buy = this.getOrderTemp(orderParam);

		if (buy == null) {
			throw new OrderException(MessageUtils.getMessage("M00481"), "/");
		}

		List<BuyPayment> payments = orderMapper.getOrderPaymentBuyTempList(orderParam);
		if (payments == null) {
			throw new OrderException(MessageUtils.getMessage("M00481"), "/");
		}

		String orderCode = buy.getOrderCode();

		String escrowStatus = orderMapper.getOrderItemTempByEscrow(orderCode);

		int orderSequence = 0;

		OrderPrice orderPrice = buy.getOrderPrice();

		// 10 : 결제 완료, 0 : 입금대기
		String orderStatus = "10";
		boolean isBankPayment = false;
		boolean isPointPayment = false;
		boolean isNaverpay = false;

		int bankPayAmount = 0; // 미수금
		String payMethodType = "";

		for(BuyPayment buyPayment : payments) {

			if ("bank".equals(buyPayment.getApprovalType())
				|| "ourvbank".equals(buyPayment.getApprovalType())
				|| "offlinepay".equals(buyPayment.getApprovalType())
				|| "ars".equals(buyPayment.getApprovalType())
				|| "vbank".equals(buyPayment.getApprovalType())) {

				orderStatus = "0";
				isBankPayment = true;
			}

			if ("bank".equals(buyPayment.getApprovalType())
				|| "ourvbank".equals(buyPayment.getApprovalType())
				|| "ars".equals(buyPayment.getApprovalType())
				|| "vbank".equals(buyPayment.getApprovalType())) {

				bankPayAmount += buyPayment.getAmount();
			}

			if ("point".equals(buyPayment.getApprovalType())) {
				isPointPayment = true;
			}

			if ("naverpay".equals(buyPayment.getApprovalType())) {
				isNaverpay = true;
			}

			payMethodType = buyPayment.getApprovalType();
		}

		// 초기 주문상태 Set
		buy.setOrderStatus(orderStatus);

		// OP_ORDER_TEMP에 저장된 결제 금액 정보
		OrderPrice saveOrderPrice = this.newOrderPrice(orderPrice);

		// 주문 요청 상품들을 조회
		orderParam.setOrderCode(orderCode);

		List<Receiver> list = orderMapper.getOrderShippingBuyTempList(orderParam);
		if (ValidationUtils.isNull(list)) {
			throw new OrderException("주문 가능 상품이 없습니다.", "/cart");
		}

		for(Receiver receiver : list) {
			orderParam.setShippingIndex(receiver.getShippingIndex());
			List<BuyItem> buyItems = orderMapper.getOrderBuyItemTempList(orderParam);

			if (buyItems.isEmpty()) {
				throw new OrderException("주문 가능 상품이 없습니다.", "/cart");
			}

			// 입점 업체 수수료 일때 판매자 정보에 설정된 수수료를 조회해서 셋팅
			for(BuyItem buyItem : buyItems) {
				Item item = buyItem.getItem();
				if ("1".equals(item.getCommissionType())) {
					Seller seller = sellerMapper.getSellerById(item.getSellerId());
					if (seller != null) {
						item.setCommissionRate(seller.getCommissionRate());
					}
				}
			}

			// 추가구성상품
			for(BuyItem buyItem : buyItems) {
				if ("Y".equals(buyItem.getAdditionItemFlag())) {
					for (BuyItem temp : buyItems) {
						if ("N".equals(temp.getAdditionItemFlag()) && buyItem.getParentItemId() == temp.getItemId()) {
							buyItem.setParentItemSequence(temp.getItemSequence());
							buyItem.setParentItemId(temp.getItemId());
							buyItem.setParentItemOptions(temp.getOptions());

							buyItem.setDeliveryType(temp.getItem().getDeliveryType());
							buyItem.setDeliveryCompanyId(temp.getItem().getDeliveryCompanyId());
							buyItem.setDeliveryCompanyName(temp.getItem().getDeliveryCompanyName());
							buyItem.setShipmentGroupCode(temp.getItem().getShipmentGroupCode());
							buyItem.setShipmentId(temp.getItem().getShipmentId());
							break;
						}
					}
				}
			}

			receiver.setItems(buyItems);
		}

		buy.setReceivers(list);

		HashMap<String, Integer> buyQuantityMap = new HashMap<>();
		this.setOrderItemInfo(buy.getItems(), orderParam, buyQuantityMap);

		if (buy.getReceivers() != null) {
			for (Receiver receiver : buy.getReceivers()) {

				// 상품쿠폰 적용
				receiver.itemCouponUsed(false, buy, receiver.getShippingIndex());

				// 구매 상품 정책별 그룹
				String zipcode = receiver.getReceiveZipcode();

				if (StringUtils.isEmpty(zipcode)) {
					zipcode = receiver.getFullReceiveZipcode();
				}

				receiver.setShipping(orderMapper.getIslandTypeByZipcode(zipcode));
			}
		}

		// 재고 차감 목록
		buy.setStockMap(buyQuantityMap);

		// 구매가능여부 채크
		ShopUtils.buyVerification(buy.getItems(), buy.getItems().size());

		// 사용한 포인트
		orderPrice.setTotalPointDiscountAmount(saveOrderPrice.getTotalPointDiscountAmount());

		// 배송비 할인쿠폰 적용
		if (UserUtils.isUserLogin() == true) {

			int shippingCouponCount = 0;
			int totalShippingCouponDiscountAmount = 0;
			if (buy.getReceivers().size() <= 1) {
				List<ShippingCoupon> shippingCoupons = orderMapper.getOrderShippingCouponBuyTemp(orderParam);
				if (!shippingCoupons.isEmpty()) {
					List<Shipping> shippings = buy.getReceivers().get(0).getItemGroups();

					for(ShippingCoupon sCoupon : shippingCoupons) {
						for(Shipping shipping : shippings) {
							if (shipping.getRealShipping() == sCoupon.getDiscountAmount()
								&& shipping.getShippingGroupCode().equals(sCoupon.getShippingGroupCode())) {

								shipping.setShippingCouponCount(1);
								shipping.setDiscountShipping(shipping.getRealShipping() - shipping.getAddDeliveryCharge());
								shipping.setPayShipping(shipping.getRealShipping() - shipping.getDiscountShipping());

								shippingCouponCount += shipping.getShippingCouponCount();
								totalShippingCouponDiscountAmount += shipping.getDiscountShipping();
								break;
							}
						}
					}
				}
			}

			buy.setShippingCoupon(shippingCouponCount);
			saveOrderPrice.setTotalShippingCouponUseCount(shippingCouponCount);
			saveOrderPrice.setTotalShippingCouponDiscountAmount(totalShippingCouponDiscountAmount);
		}

		buy.setOrderPrice(bankPayAmount, configService.getShopConfig(Config.SHOP_CONFIG_ID));

		try {
			// 결제금액 검증
			this.payAmountVerification(buy, saveOrderPrice);
		} catch (OrderException oe) {
			throw new OrderException(oe.getMessage(), oe.getRedirectUrl(), oe);
		}

		List<OrderPgData> successOrderPgDatas = new ArrayList<>();

		try {

			Buyer buyer = buy.getBuyer();
			buyer.setIp(saleson.common.utils.CommonUtils.getClientIp(request));
			buyer.setOrderCode(orderCode);
			buyer.setUserId(buy.getUserId());
			if (UserUtils.isUserLogin()) {
				buyer.setLoginId(UserUtils.getLoginId());
			}

			buyer.setOrderPrice(orderPrice);
			orderMapper.insertOrder(buyer);

			erpOrder.setBuyer(buyer);


			int shippingInfoSequence = 0;
			int shippingSequence = 0;
			int itemSequence = 0;
			orderSequence = buyer.getOrderSequence();

			for(Receiver receiver : buy.getReceivers()) {

				// 주문상품 배송지 정보를 저장
				OrderShippingInfo orderShippingInfo = new OrderShippingInfo(orderCode, orderSequence, shippingInfoSequence++, receiver);
				orderMapper.insertOrderShippingInfo(orderShippingInfo);

				erpOrder.addOrderShippingInfo(orderShippingInfo);

				for(Shipping shipping : receiver.getItemGroups()) {
					shipping.setOrderCode(orderCode);
					shipping.setOrderSequence(orderSequence);
					shipping.setShippingSequence(shippingSequence++);

					orderMapper.insertOrderShipping(shipping);




					// 1개상품용 쿠폰일 경우 메일링에 해당 상품 분리적용으로 인해 buyItems 생성
					List<BuyItem> buyItems = new ArrayList<>();

					if (shipping.isSingleShipping()) {
						BuyItem buyItem = shipping.getBuyItem();

						// 묶음배송 처리 데이터 추가
						if (!StringUtils.isEmpty(shipping.getShipmentGroupCode())) {
							buyItem.setShipmentGroupCode(shipping.getShipmentGroupCode());
						}

						// 상품 정보 세팅
						this.setOrderItemForBuy(itemSequence, buyItems, buyItem, buy, shipping, orderShippingInfo, escrowStatus);

						// 네이버페이로 결제할 경우 포인트 적립 안함.
						if (isNaverpay) {
							buyItem.getItemPrice().setEarnPoint(0);
							buyItem.getItemPrice().setSellerPoint(0);
						}
					} else {
						for(BuyItem buyItem : shipping.getBuyItems()) {

							// 묶음배송 처리 데이터 추가
							buyItem.setShipmentGroupCode(shipping.getShipmentGroupCode());

							// 상품 정보 세팅
							this.setOrderItemForBuy(itemSequence + buyItems.size(), buyItems, buyItem, buy, shipping, orderShippingInfo, escrowStatus);

							// 네이버페이로 결제할 경우 포인트 적립 안함.
							if (isNaverpay) {
								buyItem.getItemPrice().setEarnPoint(0);
								buyItem.getItemPrice().setSellerPoint(0);
							}
						}
					}

					itemSequence += buyItems.size();

					this.insertOrderItem(buyItems);
					shipping.setBuyItems(buyItems);

					erpOrder.addBuyItems(buyItems);
				}
			}

			int paymentSequence = 0;

			buy.setPayments(payments);

			int cnt = 1;
			int totCnt = payments.size();

			for(BuyPayment buyPayment : payments) {
				String approvalType = buyPayment.getApprovalType();

				OrderPayment orderPayment = new OrderPayment();
				orderPayment.setOrderCode(orderCode);
				orderPayment.setOrderSequence(orderSequence);
				orderPayment.setPaymentSequence(paymentSequence++);
				if ("bank".equals(approvalType)) {

					orderPayment.setBankVirtualNo(buyPayment.getBankVirtualNo());
					orderPayment.setBankInName(buyPayment.getBankInName());
					orderPayment.setBankDate(buyPayment.getBankExpirationDate());
					orderPayment.setNowPaymentFlag("N");

					// 세금계산서, 현금영수증 데이터 저장
					receiptDataSave(buy, totCnt, cnt);
				} else if (PointUtils.isPointType(approvalType)) {
					// 현금성 포인트일 경우 포인트 금액만큼 현금영수증 발급
					if(PointUtils.isPossibleToIssueReceipt(approvalType)){
						receiptDataSave(buy, totCnt, cnt);
					}

					orderPayment.setOrderPgDataId(0);
					orderPayment.setApprovalType(approvalType);
					orderPayment.setPayDate(DateUtils.getToday(DATETIME_FORMAT));
					orderPayment.setAmount(buyPayment.getAmount());
					orderPayment.setTaxFreeAmount(buyPayment.getAmount());
					orderPayment.setNowPaymentFlag("Y");

					PointUsed pointUsed = new PointUsed();
					pointUsed.setOrderCode(orderCode);
					pointUsed.setPoint(buyPayment.getAmount());
					pointUsed.setDetails("주문번호 :" + orderCode + " 사용");

					// 포인트 사용 처리
					pointService.deductedPoint(pointUsed, buy.getUserId(), approvalType);

				} else {

					if ("card".equals(approvalType) || "vbank".equals(approvalType) || "realtimebank".equals(approvalType)
							|| "escrow".equals(approvalType) || "hp".equals(approvalType)) {
						OrderPgData orderPgData = null;
						if ("inicis".equals(pgType)) {

							((PgData) pgData).setMid(buyPayment.getMid());
							((PgData) pgData).setKeypass(buyPayment.getKey());

							/**
							 * 모바일 이니시스 결제 요청후 승인 타입
							 * 신용카드 : wcard
							 * 휴대폰 : mobile
							 * 문화상품권 : culture
							 * 해피머니상품권 : hpmn
							 * 스마트문상 : dgcl
							 *
							 * ...... vbank도 nextUrl을 사용하내...
							 */
							if ((ShopUtils.isMobilePage() || ((PgData) pgData).isMobilePage())
									&& ("realtimebank".equals(approvalType) || "card".equals(approvalType) || "vbank".equals(approvalType) || "escrow".equals(approvalType) || "hp".equals(approvalType))) {
								((PgData) pgData).setTransactionType("1");
							} else if (!ShopUtils.isMobilePage() && "webStandard".equals(environment.getProperty("pg.inipay.web.type"))) {
								((PgData) pgData).setTransactionType("1");
							}

							((PgData) pgData).setTaxFreeAmount(Integer.toString(buyPayment.getTaxFreeAmount()));
							((PgData) pgData).setAmount(Integer.toString(buyPayment.getAmount()));
							((PgData) pgData).setApprovalType(approvalType);

							orderPgData = inicisService.pay(pgData, session);

							if ("vbank".equals(approvalType) || "escrow".equals(approvalType)) {
								String bankVirtualNo = ShopUtils.getBankName(pgType, orderPgData.getBankCode()) + " 계좌번호 : ";
								bankVirtualNo += orderPgData.getBankVirtualNo();
								orderPayment.setBankVirtualNo(bankVirtualNo);
								orderPayment.setBankInName(orderPgData.getBankInName());

								String closeDate = orderPgData.getBankDate();
								if (StringUtils.isNotEmpty(closeDate)) {
									if (closeDate.length() == 14) {
										closeDate = closeDate.substring(0, 8);
									}

									if (closeDate.length() != 8) {
										closeDate = "";
									}
								}

								// 만료일이 없으면 5일만 더해서 보여주자..
								if (StringUtils.isEmpty(closeDate)) {
									closeDate = DateUtils.addDay(DateUtils.getToday(DATE_FORMAT), 5);
								}

								orderPayment.setBankDate(closeDate);

								// 입금 요청 메일 전송용 데이터 구성
								buyPayment.setBankVirtualNo(bankVirtualNo);
								buyPayment.setBankExpirationDate(DateUtils.date(closeDate));
								buyPayment.setBankInName(orderPgData.getBankInName());
							}

							if ("vbank".equals(approvalType) || "realtimebank".equals(approvalType)) {
								receiptDataSave(buy, totCnt, cnt);
							}

						} else if ("lgdacom".equals(pgType)) {
							((PgData) pgData).setAmount(Integer.toString(buyPayment.getAmount()));
							((PgData) pgData).setApprovalType(approvalType);
							orderPgData = lgDacomService.pay(pgData, session);

						} else if ("cj".equals(pgType)) {
							orderPgData = cjService.pay(pgData, session);

							if ("vbank".equals(approvalType)) {

								String bankVirtualNo = "[" + ((CjResult) pgData).getCJSBankName() + "] 계좌번호 : ";
								bankVirtualNo += ((CjResult) pgData).getCJSAccountNo();
								orderPayment.setBankVirtualNo(bankVirtualNo);
								orderPayment.setBankInName(((CjResult) pgData).getCJSAccountOWNER());

								String closeDate = ((CjResult) pgData).getCloseDate();
								if (StringUtils.isNotEmpty(closeDate)) {
									if (closeDate.length() == 14) {
										closeDate = closeDate.substring(0, 8);
									}

									if (closeDate.length() != 8) {
										closeDate = "";
									}
								}

								// 만료일이 없으면 5일만 더해서 보여주자..
								if (StringUtils.isEmpty(closeDate)) {
									closeDate = DateUtils.addDay(DateUtils.getToday(DATE_FORMAT), 5);
								}

								orderPayment.setBankDate(closeDate);
							}
						} else if ("kspay".equals(pgType)) {
							((PgData) pgData).setAmount(Integer.toString(buyPayment.getAmount()));
							((PgData) pgData).setApprovalType(approvalType);
							orderPgData = kspayService.pay(pgData, session);
							orderPgData.setPgPaymentType(((PgData) pgData).getSndPaymethod());

						} else if ("kcp".equals(pgType)) {
							KcpRequest kcpRequest = new KcpRequest(request);
							kcpRequest.setPay_method(approvalType);
							orderPgData = kcpService.pay(kcpRequest, session);

							if ("vbank".equals(approvalType) || "escrow".equals(approvalType)) {
								String bankVirtualNo = "[" + orderPgData.getBankName() + "]" + " 계좌번호 : ";
								bankVirtualNo += orderPgData.getBankVirtualNo();
								orderPayment.setBankVirtualNo(bankVirtualNo);
								orderPayment.setBankInName(orderPgData.getBankInName());

								String closeDate = orderPgData.getBankDate();
								if (StringUtils.isNotEmpty(closeDate)) {
									if (closeDate.length() == 14) {
										closeDate = closeDate.substring(0, 8);
									}

									if (closeDate.length() != 8) {
										closeDate = "";
									}
								}

								// 만료일이 없으면 5일만 더해서 보여주자..
								if (StringUtils.isEmpty(closeDate)) {
									closeDate = DateUtils.addDay(DateUtils.getToday(DATE_FORMAT), 5);
								}

								orderPayment.setBankDate(closeDate);

								// 입금 요청 메일 전송용 데이터 구성
								buyPayment.setBankVirtualNo(bankVirtualNo);
								buyPayment.setBankExpirationDate(DateUtils.date(closeDate));
								buyPayment.setBankInName(orderPgData.getBankInName());
							}
						} else if("easypay".equals(pgType)) {
							EasypayRequest easypayRequest = new EasypayRequest(request);
							((PgData)pgData).setEasypayRequest(easypayRequest);

							orderPgData = easypayService.pay(pgData, session);

							if ("vbank".equals(approvalType) || "escrow".equals(approvalType)) {
								String bankVirtualNo = "[" + orderPgData.getBankName() + "]" + " 계좌번호 : ";
								bankVirtualNo += orderPgData.getBankVirtualNo();
								orderPayment.setBankVirtualNo(bankVirtualNo);
								orderPayment.setBankInName(orderPgData.getBankInName());

								String closeDate = orderPgData.getBankDate();
								if (StringUtils.isNotEmpty(closeDate)) {
									if (closeDate.length() == 14) {
										closeDate = closeDate.substring(0, 8);
									}

									if (closeDate.length() != 8) {
										closeDate = "";
									}
								}

								// 만료일이 없으면 5일만 더해서 보여주자..
								if (StringUtils.isEmpty(closeDate)) {
									closeDate = DateUtils.addDay(DateUtils.getToday(DATE_FORMAT), 5);
								}

								orderPayment.setBankDate(closeDate);

								// 입금 요청 메일 전송용 데이터 구성
								buyPayment.setBankVirtualNo(bankVirtualNo);
								buyPayment.setBankExpirationDate(DateUtils.date(closeDate));
								buyPayment.setBankInName(orderPgData.getBankInName());
							}
						} else if("nicepay".equals(pgType)) {
							((PgData) pgData).setApprovalType(approvalType);
							orderPgData = nicepayService.pay(pgData, session);

							if (!orderPgData.isSuccess()) {
								throw new OrderException(orderPgData.getErrorMessage(), "/order/step1");
							}

							if ("vbank".equals(approvalType) || "escrow".equals(approvalType)) {
								String bankVirtualNo = ShopUtils.getBankName(pgType, orderPgData.getBankCode()) + " 계좌번호 : ";
								bankVirtualNo += orderPgData.getBankVirtualNo();
								orderPayment.setBankVirtualNo(bankVirtualNo);
								orderPayment.setBankInName(orderPgData.getBankInName());

								String closeDate = orderPgData.getBankDate();
								if (StringUtils.isNotEmpty(closeDate)) {
									if (closeDate.length() == 14) {
										closeDate = closeDate.substring(0, 8);
									}

									if (closeDate.length() != 8) {
										closeDate = "";
									}
								}

								// 만료일이 없으면 5일만 더해서 보여주자..
								if (StringUtils.isEmpty(closeDate)) {
									closeDate = DateUtils.addDay(DateUtils.getToday(DATE_FORMAT), 5);
								}

								orderPayment.setBankDate(closeDate);

								// 입금 요청 메일 전송용 데이터 구성
								buyPayment.setBankVirtualNo(bankVirtualNo);
								buyPayment.setBankExpirationDate(DateUtils.date(closeDate));
								buyPayment.setBankInName(orderPgData.getBankInName());

								// 가상계좌 환불 서비스에 가입 되어있으면 부분취소 가능.
								String useVbankRefundService = "";
								if (configPg != null) {
									useVbankRefundService = configPg.isUseVbackRefundService() ? "Y" : "N";
								} else {
									useVbankRefundService = environment.getProperty("pg.useVbank.refundService");
								}
								orderPgData.setPartCancelFlag(useVbankRefundService);
							}

							if ("vbank".equals(approvalType) || "realtimebank".equals(approvalType)) {
								receiptDataSave(buy, totCnt, cnt);
							}

						}

						if (!orderPgData.isSuccess() && !"RECOVERY".equals(orderParam.getPayMode())) {
							throw new OrderException(orderPgData.getErrorMessage(), "/order/step1");
						}

						successOrderPgDatas.add(orderPgData);

						// PG 서비스 타입
						orderPgData.setOrderCode(buy.getOrderCode());
						orderPgData.setPgServiceType(pgType);
						orderPgData.setOrderPgDataId(sequenceService.getId("OP_ORDER_PG_DATA"));
						orderPgData.setOrderCode(orderCode);
						orderPgData.setPgAmount(buyPayment.getAmount());
						orderPgData.setPgServiceMid(buyPayment.getMid());
						orderPgData.setPgServiceKey(buyPayment.getKey());

						orderMapper.insertOrderPgData(orderPgData);

						orderPayment.setOrderPgDataId(orderPgData.getOrderPgDataId());

						orderPayment.setNowPaymentFlag("Y");
						if ("vbank".equals(approvalType)
								|| ("inicis".equals(pgType) && "escrow".equals(approvalType))) {

							orderPayment.setNowPaymentFlag("N");
						}

						// 0:미발행, 1:소득공제용, 2:지출증빙용
						List<String> billTypes = Arrays.asList("", "현금영수증", "세금계산서");
						if (orderPgData.getRcptType() != null) {
							erpOrder.setBillType(billTypes.get(Integer.parseInt("".equals(orderPgData.getRcptType()) ? "0" : orderPgData.getRcptType())));
						}

					} else if ("payco".equals(approvalType)) {
						OrderPgData orderPgData = null;
						orderPgData = paycoService.pay(pgData, session);

						if (!orderPgData.isSuccess()) {
							throw new OrderException(orderPgData.getErrorMessage(), "/order/step1");
						}

						// PAYCO 결제의 경우 0으로 떨어져도 무통장 입금은 아닐수도 있는데....
						// 결제 상태가 10인경우에만 체크하도록 하자.
						if ("10".equals(orderStatus)) {
							orderStatus = orderPgData.isPaymentCompletion() ? "10" : "0";
						}

						orderPayment.setNowPaymentFlag("N");
						if ("10".equals(orderStatus)) {

							orderPayment.setNowPaymentFlag("Y");
						}

						// PG 서비스 타입
						orderPgData.setOrderCode(buy.getOrderCode());
						orderPgData.setPgServiceType(approvalType);
						orderPgData.setOrderPgDataId(sequenceService.getId("OP_ORDER_PG_DATA"));
						orderMapper.insertOrderPgData(orderPgData);

						// 혹시모를 주문취소를 위한 주문취소 금액 셋팅해 놓도록 하자
						orderPgData.setRemainAmount(buyPayment.getAmount());
						successOrderPgDatas.add(orderPgData);

						orderPayment.setOrderPgDataId(orderPgData.getOrderPgDataId());

					} else if ("kakaopay".equals(approvalType)) {
						OrderPgData orderPgData = null;
						orderPgData = kakaopayService.pay(pgData, session);

						if (!orderPgData.isSuccess()) {
							throw new OrderException(orderPgData.getErrorMessage(), "/order/step1");
						}

						// 즉시결제 여부
						orderPayment.setNowPaymentFlag("Y");

						// PG 서비스 타입
						orderPgData.setOrderCode(buy.getOrderCode());
						orderPgData.setPgServiceType(approvalType);
						orderPgData.setOrderPgDataId(sequenceService.getId("OP_ORDER_PG_DATA"));
						orderMapper.insertOrderPgData(orderPgData);

						// 혹시모를 주문취소를 위한 주문취소 금액 셋팅해 놓도록 하자
						orderPgData.setRemainAmount(buyPayment.getAmount());
						successOrderPgDatas.add(orderPgData);

						orderPayment.setOrderPgDataId(orderPgData.getOrderPgDataId());

					} else if ("naverpay".equals(approvalType)) {
						MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
						requestMap.add("paymentId", ((PgData) pgData).getPaymentId());
						requestMap.add("amount", ((PgData) pgData).getAmount());

						OrderPgData orderPgData = naverPaymentApi.pay(requestMap, configPg);
						if (!orderPgData.isSuccess()) {
							throw new OrderException(orderPgData.getErrorMessage(), "/order/step1");
						}

						successOrderPgDatas.add(orderPgData);

						if (orderPgData.isAmountModification()) {
							throw new OrderException("주문금액과 결제금액이 일치하지 않습니다.", "/order/step1");
						}

						// PG 서비스 타입
						orderPgData.setPgServiceType("naverpay");
						orderPgData.setOrderPgDataId(sequenceService.getId("OP_ORDER_PG_DATA"));

						orderMapper.insertOrderPgData(orderPgData);

						// 즉시결제 여부
						orderPayment.setNowPaymentFlag("Y");
						orderPayment.setOrderPgDataId(orderPgData.getOrderPgDataId());
					}
				}

				orderPayment.setApprovalType(approvalType);
				orderPayment.setAmount(buyPayment.getAmount());
				orderPayment.setTaxFreeAmount(buyPayment.getTaxFreeAmount());

				if ("Y".equals(orderPayment.getNowPaymentFlag())) {
					orderPayment.setRemainingAmount(buyPayment.getAmount());
					orderPayment.setPayDate(DateUtils.getToday(DATETIME_FORMAT));
				}

				orderPayment.setPaymentType("1");
				orderPayment.setDeviceType(buy.getDeviceType());
				orderMapper.insertOrderPayment(orderPayment);


				// 현금영수증 발행 - 현금영수증 발행 가능한 포인트 및 실시간 계좌이체만 (은행입금, 가상계좌는 입금이 확인되는 시점에 발행)
				if (!"Y".equals(autoCashReceipt) && (PointUtils.isPossibleToIssueReceipt(approvalType) || "realtimebank".equals(approvalType))) {
					CashbillParam cashbillParam = new CashbillParam();

					cashbillParam.setWhere("orderCode");
					cashbillParam.setQuery(orderParam.getOrderCode());

					Iterable<CashbillIssue> cashbillIssues = cashbillIssueRepository.findAll(cashbillParam.getPredicate());

					log.debug("[CASHBILL] START ---------------------------------------------");
					log.debug("[CASHBILL] cashbillIssues Size :  {}", ((List<CashbillIssue>) cashbillIssues).size());

					CashbillResponse response = null;
					String cashbillService = "";

					if (configPg != null) {
						cashbillService = configPg.getCashbillServiceType().getCode().toLowerCase();
					} else {
						cashbillService = environment.getProperty("cashbill.service");
					}

					for (CashbillIssue cashbillIssue : cashbillIssues) {

						if ("popbill".equals(cashbillService)) {
							response = receiptService.receiptIssue(cashbillIssue);
						} else if ("inicis".equals(cashbillService)) {
							Cashbill cashbill = cashbillIssue.getCashbill();

							cashbillParam.setCashbillStatus(cashbillIssue.getCashbillStatus());
							cashbillParam.setAmount(cashbillIssue.getAmount());
							cashbillParam.setItemName(cashbillIssue.getItemName());
							cashbillParam.setTaxType(cashbillIssue.getTaxType());
							cashbillParam.setCashbillCode(cashbill.getCashbillCode());
							cashbillParam.setCashbillType(cashbill.getCashbillType());
							cashbillParam.setCustomerName(cashbill.getCustomerName());
							cashbillParam.setOrderCode(orderParam.getOrderCode());

							response = inicisService.cashReceiptIssued(cashbillParam);
						} else if ("nicepay".equals(cashbillService)) {
							Cashbill cashbill = cashbillIssue.getCashbill();

							cashbillParam.setCashbillStatus(cashbillIssue.getCashbillStatus());
							cashbillParam.setAmount(cashbillIssue.getAmount());
							cashbillParam.setItemName(cashbillIssue.getItemName());
							cashbillParam.setTaxType(cashbillIssue.getTaxType());
							cashbillParam.setCashbillCode(cashbill.getCashbillCode());
							cashbillParam.setCashbillType(cashbill.getCashbillType());
							cashbillParam.setCustomerName(cashbill.getCustomerName());
							cashbillParam.setEmail(buyer.getEmail());
							cashbillParam.setOrderCode(orderParam.getOrderCode());

							response = nicepayService.cashReceiptIssued(cashbillParam);
						}

						if (response == null) {
							log.debug("[CASHBILL] ERROR >> PG 통신오류(응답없음)");
							throw new OrderException("PG 통신오류(응답없음)");
						}

						log.debug("[CASHBILL] cashbillIssue :  {}", cashbillIssue);
						log.debug("[CASHBILL] CashbillResponse response.isSuccess() :  {}", response.isSuccess());
						if (response.isSuccess()) {
							cashbillIssue.setIssuedDate(DateUtils.getToday(DATETIME_FORMAT));
							cashbillIssue.setUpdatedDate(DateUtils.getToday(DATETIME_FORMAT));
							cashbillIssue.setCashbillStatus(CashbillStatus.ISSUED);
							cashbillIssue.setMgtKey(response.getMgtKey());

							if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
								cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
							} else {
								cashbillIssue.setUpdateBy("비회원");
							}

							cashbillIssueRepository.save(cashbillIssue);

						} else {
							log.debug("[CASHBILL] ERROR >> {} : {}", response.getResponseCode(), response.getResponseMessage());
							throw new OrderException(response.getResponseCode() + " : " + response.getResponseMessage());
						}
					}
					log.debug("[CASHBILL] END ---------------------------------------------");
				}


				String payMethod = "";
				if ("point".equals(orderPayment.getApprovalType())) {
					payMethod = "포인트";
				} else if ("card".equals(orderPayment.getApprovalType())) {
					payMethod = "카드";
				} else if ("realtimebank".equals(orderPayment.getApprovalType())) {
					payMethod = "실시간계좌이체";
				} else if ("vbank".equals(orderPayment.getApprovalType())) {
					payMethod = "가상계좌";
				} else if ("hp".equals(orderPayment.getApprovalType())) {
					payMethod = "휴대전화";
				} else if ("naverpay".equals(orderPayment.getApprovalType())) {
					payMethod = "네이버페이";
				}

				erpOrder.setPayMethod(payMethod);

				cnt++;
			}

			// 배송비 쿠폰
			if (buy.getShippingCoupon() > 0) {

				PointUsed pointUsed = new PointUsed();
				pointUsed.setOrderCode(orderCode);
				pointUsed.setPoint(buy.getShippingCoupon());
				pointUsed.setDetails("주문번호 :" + orderCode + " 사용");

				pointService.deductedPoint(pointUsed, buy.getUserId(), PointUtils.SHIPPING_COUPON_CODE);
			}

			try {
				// ERP 연동 - 신용카드, 실시간 계좌이체, 휴대폰, 네이버페이 결제 (가상계좌는 입금통보 시 ERP연동)
				if ("card".equals(payMethodType) || "realtimebank".equals(payMethodType) || "hp".equals(payMethodType) || "naverpay".equals(payMethodType) || "point".equals(payMethodType)) {
					erpService.saveOrderListGet(erpOrder);
				}
			} catch (Exception e) {
				log.error("ERP 연동 오류 : {}", e.getMessage(), e);
				throw new OrderException("ERP 연동 오류", "/order/step1");
			}

		} catch (OrderException e) {

			log.error(e.getMessage(), e);

			// DB 처리 에러가 발행하면 PG 취소함
			if (successOrderPgDatas != null) {
				for(OrderPgData orderPgData : successOrderPgDatas) {

					String approvalType = orderPgData.getApprovalType();
					orderPgData.setCancelReason("결제 중 오류로 주문 취소");

					if (orderPgData.isSuccess()) {
						boolean isCancelSuccess = false;
						if ("inicis".equals(orderPgData.getPgServiceType())) {
							isCancelSuccess = inicisService.cancel(orderPgData);

						} else if ("lgdacom".equals(orderPgData.getPgServiceType())) {
							isCancelSuccess = lgDacomService.cancel(orderPgData);

						} else if ("payco".equals(orderPgData.getPgServiceType())) {
							orderPgData.setRemainAmount(orderPrice.getOrderPayAmount());
							isCancelSuccess = paycoService.cancel(orderPgData);

						} else if ("kakaopay".equals(orderPgData.getPgServiceType())) {
							orderPgData.setRemainAmount(orderPrice.getOrderPayAmount());
							isCancelSuccess = kakaopayService.cancel(orderPgData);

						} else if ("cj".equals(orderPgData.getPgServiceType())) {
							//isCancelSuccess = cjService.cancel(orderPgData);

							//CJ PG는 RedirectUrl에서 실패시 취소 하도록 하자!!
							isCancelSuccess = true;
						} else if ("kspay".equals(orderPgData.getPgServiceType())) {
							isCancelSuccess = kspayService.cancel(orderPgData);

						} else if ("kcp".equals(orderPgData.getPgServiceType())) {
							orderPgData.setOrderCode(orderCode);
							isCancelSuccess = kcpService.cancel(orderPgData);

						} else if("easypay".equals(orderPgData.getPgServiceType())) {
							orderPgData.setOrderCode(orderCode);
							isCancelSuccess = easypayService.cancel(orderPgData);

						} else if("nicepay".equals(orderPgData.getPgServiceType())) {
							orderPgData.setOrderCode(orderCode);
							orderPgData.setRequest(request);
							orderPgData.setCancelAmount(orderPgData.getPgAmount());
							isCancelSuccess = nicepayService.cancel(orderPgData);
						} else if("naverpay".equals(orderPgData.getPgServiceType())) {
                            orderPgData.setCancelAmount(orderPgData.getPgAmount());
                            orderPgData = naverPaymentApi.cancel(orderPgData, configPg);

                            isCancelSuccess = orderPgData.isSuccess();
                        }

						if (isCancelSuccess == false) {
                            // 결제 취소 실패!!
                            System.out.println("TID -> " + orderPgData.getPgKey() + " -> 결제취소 실패!!");
                            OrderCancelFail orderCancelFail = new OrderCancelFail();

                            orderCancelFail.setUpdateData(orderPgData);
                            orderCancelFail.setPgServiceType(orderPgData.getPgServiceType());

                            if (UserUtils.isManagerLogin()) {
                                orderCancelFail.setCancelRequester("2");
                            } else {
                                orderCancelFail.setCancelRequester("1");
                            }

                            throw new OrderException("결제취소 실패", "/order/step1", orderCancelFail, e);
						}

						// 현금영수증 취소
						receiptService.cancelCashbill(orderCode);
					}
				}
			}

			throw new OrderException("결제 처리 중 에러가 발생하여 거래가 취소되었습니다. <br>" + e.getMessage(), "/order/step1", e);
			//throw new OrderException("결제 처리도중 에러가 발생하여 거래가 취소 되었습니다. 카드결제의 경우 승인취소 문자를 받지 못하신 경우 고객센터로 연락 바랍니다.", "/order/step1");
		} catch (Exception e) {

			log.error("OrderService.inserOrder 처리 중 오류 발생", e);

			// DB 처리 에러가 발행하면 PG 취소함
			if (successOrderPgDatas != null) {
				for(OrderPgData orderPgData : successOrderPgDatas) {
                    orderPgData.setCancelReason("결제 중 오류로 주문 취소");

					String approvalType = orderPgData.getApprovalType();

					if (orderPgData.isSuccess()) {
						boolean isCancelSuccess = false;
						if ("inicis".equals(orderPgData.getPgServiceType())) {
							isCancelSuccess = inicisService.cancel(orderPgData);

						} else if ("lgdacom".equals(orderPgData.getPgServiceType())) {
							isCancelSuccess = lgDacomService.cancel(orderPgData);

						} else if ("payco".equals(orderPgData.getPgServiceType())) {
							orderPgData.setRemainAmount(orderPrice.getOrderPayAmount());
							isCancelSuccess = paycoService.cancel(orderPgData);

						} else if ("kakaopay".equals(orderPgData.getPgServiceType())) {
							orderPgData.setRemainAmount(orderPrice.getOrderPayAmount());
							isCancelSuccess = kakaopayService.cancel(orderPgData);

						} else if ("cj".equals(orderPgData.getPgServiceType())) {
							//isCancelSuccess = cjService.cancel(orderPgData);

							//CJ PG는 RedirectUrl에서 실패시 취소 하도록 하자!!
							isCancelSuccess = true;
						} else if ("kspay".equals(orderPgData.getPgServiceType())) {
							isCancelSuccess = kspayService.cancel(orderPgData);

						} else if ("kcp".equals(orderPgData.getPgServiceType())) {
							orderPgData.setOrderCode(orderCode);
							isCancelSuccess = kcpService.cancel(orderPgData);

						} else if("easypay".equals(orderPgData.getPgServiceType())) {
							orderPgData.setOrderCode(orderCode);
							isCancelSuccess = easypayService.cancel(orderPgData);

						} else if("nicepay".equals(orderPgData.getPgServiceType())) {
							orderPgData.setOrderCode(orderCode);
                            orderPgData.setRequest(request);
                            orderPgData.setCancelAmount(orderPgData.getPgAmount());
                            orderPgData.setMessage("주문취소");
							isCancelSuccess = nicepayService.cancel(orderPgData);
						} else if("naverpay".equals(orderPgData.getPgServiceType())) {
                            orderPgData.setCancelAmount(orderPgData.getPgAmount());
                            orderPgData = naverPaymentApi.cancel(orderPgData, configPg);

                            isCancelSuccess = orderPgData.isSuccess();
                        }

						if (isCancelSuccess == false) {
                            // 결제 취소 실패!!
                            log.debug("TID -> " + orderPgData.getPgKey() + " -> 결제취소 실패!!");
                            OrderCancelFail orderCancelFail = new OrderCancelFail();

                            orderCancelFail.setUpdateData(orderPgData);
                            orderCancelFail.setPgServiceType(orderPgData.getPgServiceType());

                            if (UserUtils.isManagerLogin()) {
                                orderCancelFail.setCancelRequester("2");
                            } else {
                                orderCancelFail.setCancelRequester("1");
                            }

                            throw new OrderException("결제취소 실패", "/order/step1", orderCancelFail, e);
						}

						// 현금영수증 취소
						receiptService.cancelCashbill(orderCode);
					}
				}
			}

			throw new OrderException("결제 처리 중 에러가 발생하여 거래가 취소되었습니다. <br>" + e.getMessage(), "/order/step1", e);
			//throw new OrderException("결제 처리도중 에러가 발생하여 거래가 취소 되었습니다. 카드결제의 경우 승인취소 문자를 받지 못하신 경우 고객센터로 연락 바랍니다.", "/order/step1");
		}

		try {
			// 주문서 작성 임시 저장 정보 삭제
			orderMapper.deleteOrderItemTemp(orderParam);

			// 주문이 완료된 장바구니 상품들 삭제
			List<Integer> itemIds = new ArrayList<>();
			for(BuyItem buyItem : buy.getItems()) {
				itemIds.add(buyItem.getItemId());
			}

			// 장바구니 삭제
			if (!itemIds.isEmpty()) {
				CartParam cartParam = new CartParam();
				cartParam.setUserId(buy.getUserId());
				cartParam.setSessionId(buy.getSessionId());
				cartParam.setItemIds(itemIds);

				// 로컬에서는 테스트를 위해서 장바구니를 삭제 하지 않는다.
				if (!ServiceType.LOCAL) {
					cartMapper.deleteCartByItemIds(cartParam);
				}
			}

		} catch(Exception e) {
			log.error("주문 처리 후 임시 데이터 삭제시 ERROR: {}", e.getMessage(), e);
		}

		// Message 발송
		try {
			this.sendOrderMessageTx(buy);
		} catch(Exception e) {
			log.error("주문 메시지 발송 ERROR: {}", e.getMessage(), e);
		}

		// 재고 차감
		try {
			HashMap<String, Integer> stockMap = buy.getStockMap();
			if (stockMap == null) {
				return orderSequence + "/" + orderCode;
			}

			this.updateStockDeduction(stockMap);

		} catch(Exception e) {
			log.error("재고 차감 ERROR: {}", e.getMessage(), e);
		}

		// 로그인시 주소록 저장 선택시 기본 배송지 저장
		if (UserUtils.isUserLogin()) {
			if ("Y".equals(buy.getSaveDeliveryFlag())) {

				Receiver receiver = buy.getReceivers().get(0);

				UserDelivery userDelivery = new UserDelivery();
				userDelivery.setUserId(buy.getUserId());
				userDelivery.setDefaultFlag("Y");

				String title = buy.getSaveDeliveryName();
				if (StringUtils.isEmpty(title) == false) {
					title = title.trim();
					if ("".equals(title)) {
						title= receiver.getReceiveName();
					}
				} else {
					title = receiver.getReceiveName();
				}

				userDelivery.setTitle(title);
				userDelivery.setUserName(receiver.getReceiveName());
				userDelivery.setPhone(receiver.getFullReceivePhone());
				userDelivery.setMobile(receiver.getFullReceiveMobile());
				userDelivery.setNewZipcode(receiver.getReceiveNewZipcode());
				userDelivery.setZipcode(receiver.getReceiveZipcode());
				userDelivery.setSido(receiver.getReceiveSido());
				userDelivery.setSigungu(receiver.getReceiveSido());
				userDelivery.setEupmyeondong(receiver.getReceiveEupmyeondong());
				userDelivery.setAddress(receiver.getReceiveAddress());
				userDelivery.setAddressDetail(receiver.getReceiveAddressDetail());

				userDeliveryService.insertUserDelivery(userDelivery);
			}
		}

		return orderSequence + "/" + orderCode;
	}

	/**
	 * 상품 재고 차감
	 * @param stockMap
	 */
	@Override
	public void updateStockDeduction(HashMap<String, Integer> stockMap) {
		try {
			if (stockMap == null) {
				return;
			}

			List<StockDeduction> list = new ArrayList<>();
			for(String key : stockMap.keySet()) {
				int quantity = stockMap.get(key);
				list.add(new StockDeduction(key, quantity));
			}

			for(StockDeduction stock : list) {

				if ("STOCK".equals(stock.getStockDeductionType())) {
					orderMapper.updateStockDeductionForItem(stock);
					orderMapper.updateStockDeductionForOption(stock);
				} else if ("ITEM".equals(stock.getStockDeductionType())) {
					orderMapper.updateStockDeductionForItem(stock);
				} else if ("OPTION".equals(stock.getStockDeductionType())) {
					orderMapper.updateStockDeductionForOption(stock);
				}

			}

		} catch(Exception e) {
			log.error(ERROR_MARKER, e.getMessage(), e);
		}
	}

	/**
	 * 상품 정보를 구성
	 * @param itemSequence
	 * @param buyItems
	 * @param buyItem
	 * @param buy
	 * @param shipping
	 * @param orderShippingInfo
	 * @param escrowStatus
	 */
	private void setOrderItemForBuy(int itemSequence, List<BuyItem> buyItems, BuyItem buyItem, Buy buy, Shipping shipping,
									OrderShippingInfo orderShippingInfo, String escrowStatus) {

		buyItem.setOrderCode(buy.getOrderCode());
		buyItem.setItemSequence(itemSequence);
		buyItem.setOrderSequence(shipping.getOrderSequence());
		buyItem.setShippingInfoSequence(orderShippingInfo.getShippingInfoSequence());
		buyItem.setOrderStatus(buy.getOrderStatus());
		buyItem.setDeviceType(buy.getDeviceType());

		buyItem.setBuyShipping(shipping);

		buyItem.setErpOriginUnique(buy.getOrderCode() + "" + shipping.getOrderSequence() + "" + getTwoDisitItemSequence(itemSequence) + "00" + "00");

		// 10 : 결제완료 인경우 결제확인일을 기록
		if ("10".equals(buy.getOrderStatus())) {
			buyItem.setPayDate(DateUtils.getToday(DATETIME_FORMAT));
			escrowStatus = escrowStatus.equals("N") ? escrowStatus : "10";	//에스크로 사용시 에스크로 상태를 결제완료(10)로 변경
		} else {
			buyItem.setPayDate("00000000000000");
		}

		buyItem.setEscrowStatus(escrowStatus);

		Item item = buyItem.getItem();

		if ("Y".equals(buyItem.getAdditionItemFlag())) {
			//item.setItemName("┗(추가상품) " + item.getItemName());
		}

		buyItem.setShippingReturn(shipping.getShippingReturn());
		buyItem.setShippingSequence(shipping.getShippingSequence());

		buyItem.setUserId(buy.getUserId());

		//0 : 상품 준비중 - 신규 주문
		buyItem.setOrderItemStatus("0");
		String guestFlag = "N";
		if (buy.getUserId() == 0) {
			guestFlag = "Y";
		}

		buyItem.setSellerId(item.getSellerId());
		buyItem.setGuestFlag(guestFlag);

		// 판매 카테고리 정보를 기록
		ProductsRepCategories productsRepCategories = categoriesMapper.getProductsRepCategoriesByItemId(buyItem.getItemId());
		if (ValidationUtils.isNotNull(productsRepCategories)) {
			buyItem.setCategoryTeamId(productsRepCategories.getCategoryTeamId());
			buyItem.setCategoryGroupId(productsRepCategories.getCategoryGroupId());
			buyItem.setCategoryId(productsRepCategories.getCategoryId());
		}

		// 매출 기준일을 등록??
		Config shopConfig = ShopUtils.getConfig();
		buyItem.setRevenueSalesStatus(shopConfig.getRevenueSalesStatus());
		if (buy.getOrderStatus().equals(shopConfig.getRevenueSalesStatus())) { // 10 : 결제확인
			buyItem.setSalesDate(DateUtils.getToday(DATETIME_FORMAT));
		} else if ("0".equals(shopConfig.getRevenueSalesStatus())) { // 주문등록시 매출?
			buyItem.setSalesDate(DateUtils.getToday(DATETIME_FORMAT));
		}

		// 사은품
		if ("Y".equals(item.getFreeGiftFlag())) {
			buyItem.setFreeGiftName(item.getFreeGiftName());

			try {
				List<GiftItem> freeGiftItemList = giftItemService.getGiftItemListForFront(buyItem.getItemId());
				buyItem.setFreeGiftItemText(ShopUtils.makeGiftItemText(freeGiftItemList));
				buyItem.setFreeGiftItemList(ShopUtils.conventGiftItemInfoList(freeGiftItemList));
			} catch (Exception e) {}
		}

		// 출고지, 반송지
		buyItem.setShipmentId(item.getShipmentId());
		buyItem.setShipmentReturnId(item.getShipmentReturnId());

		// 택배사
		buyItem.setDeliveryCompanyName(item.getDeliveryCompanyName());

		// 결제시 포인트 사용하면 지급하지 않는 설정이라면...
        int usePoint = buy.getOrderPrice().getTotalPointDiscountAmount();
        // 2: 결제시 포인트 사용하면 포인트을 지급하지 않음
		if ("2".equals(shopConfig.getPointSaveType()) && usePoint > 0) {
			buyItem.getItemPrice().setEarnPoint(0);
			buyItem.getItemPrice().setSellerPoint(0);
		}

		ItemPrice itemPrice = buyItem.getItemPrice();
		int totalQuantity = itemPrice.getQuantity();

		// 상품쿠폰 사용처리
		OrderCoupon itemCoupon = buyItem.getUsedCoupon();
		if (itemCoupon != null) {

			itemCoupon.setOrderCode(buyItem.getOrderCode());
			couponService.updateCouponUserUseProcessByOrderCouponUser(itemCoupon);

			// 1개 수량만 적용 & 수량 1이상일 경우 상품 데이터 분리
			if (!"2".equals(itemCoupon.getCouponConcurrently()) && totalQuantity > 1) {
				try {
					itemPrice.setQuantity(1);
					buyItem.setItemPrice(itemPrice);
					itemPrice = new ItemPrice(buyItem);

					itemPrice.setCouponDiscountPrice(itemCoupon.getDiscountPrice());
					itemPrice.setCouponDiscountAmount(itemCoupon.getDiscountAmount());
					buyItem.setItemPrice(itemPrice);

					// 쿠폰 적용 상품 추가 (수량 1)
					buyItems.add(buyItem);

					BuyItem cloneBuyItem = (BuyItem) buyItem.clone();
					ItemPrice cloneItemPrice = cloneBuyItem.getItemPrice();

					cloneBuyItem.setItemSequence(++itemSequence);
					cloneItemPrice.setQuantity(totalQuantity - 1);

					cloneBuyItem.setItemPrice(cloneItemPrice);
					cloneBuyItem.setItemPrice(new ItemPrice(cloneBuyItem));
					cloneBuyItem.setCouponUserId(0);

					// 묶음배송 처리 데이터 추가
					cloneBuyItem.setShipmentGroupCode(shipping.getShipmentGroupCode());

					// 일반 상품 추가 (나머지)
					buyItems.add(cloneBuyItem);
				} catch (CloneNotSupportedException e) {
					log.error("쿠폰 클론 에러: {}", e.getMessage(), e);
					throw new OrderException(e.getMessage());
				}
			} else {
				buyItems.add(buyItem);
			}
		} else {
			buyItems.add(buyItem);
		}

	}

	@Override
	public String pgConfirmationOfPayment(PgData pgData) {
		ConfigPg configPg = configPgService.getConfigPg();
		String pgType = "";

		if (configPg != null) {
			pgType = configPg.getPgType().getCode().toLowerCase();
		} else {
			pgType = SalesonProperty.getPgService();
		}

		String resultCode = "";
		String orderCode = "";
		String pgKey = "";
		int payAmount = 0;
		boolean isUpdate = false;
		if ("inicis".equals(pgType)) {
			resultCode = inicisService.confirmationOfPayment(pgData);
		} else if ("lgdacom".equals(pgType)) {

			orderCode = pgData.getLGD_OID();
			pgKey = pgData.getLGD_TID();
			resultCode = lgDacomService.confirmationOfPayment(pgData);

			if (StringUtils.isNotEmpty(pgData.getLGD_AMOUNT())) {
				payAmount = Integer.parseInt(pgData.getLGD_AMOUNT());
			}


			// 무통장 입금성공일때만 업데이트한다. 나머지 상태는 뭘해야 되지??
			if ("0000".equals(pgData.getLGD_RESPCODE().trim())) {
				if( "I".equals( pgData.getLGD_CASFLAG().trim() ) ) {
					isUpdate = true;
				}
			}
		}else if ("kspay".equals(pgType)) {
			resultCode = kspayService.confirmationOfPayment(pgData);
		}

		if (StringUtils.isEmpty(orderCode)) {
			return "주문번호가 넘어오지 않았습니다.";
		}

		if (StringUtils.isEmpty(pgKey)) {
			return "거래번호가 넘어오지 않았습니다.";
		}

		if (payAmount == 0) {
			return "결제금액이 넘어오지 않았습니다.";
		}

		if ("OK".equals(resultCode)) {

			if (isUpdate) {
				try {

					OrderParam orderParam = new OrderParam();
					orderParam.setOrderCode(orderCode);
					orderParam.setPgKey(pgKey);
					orderParam.setPayAmount(payAmount);

					if (orderPaymentMapper.updateConfirmationOfPaymentStep1(orderParam) > 0){
						if (orderPaymentMapper.updateConfirmationOfPaymentStep2(orderParam) > 0) {
							orderPaymentMapper.updateConfirmationOfPaymentStep3(orderParam);
						} else {
							throw new OrderManagerException();
						}
					} else {
						throw new OrderManagerException();
					}

				} catch (Exception e) {
					log.error(ERROR_MARKER, e.getMessage(), e);
					return "결제결과 상점 DB처리에 실패하였습니다.";
				}
			}
		}


		return resultCode;
	}

	@Override
	public String cjPgConfirmationOfPayment(CjResult cjResult) {

		String orderCode = cjResult.getCJSShopOrderNo();
		String pgKey = cjResult.getCJSTradeID();
		String payAmount = cjResult.getCJSAmountTotal();

		if (StringUtils.isEmpty(orderCode)) {
			return "9999";
		}

		if (StringUtils.isEmpty(pgKey)) {
			return "9999";
		}

		if (StringUtils.isEmpty(payAmount)) {
			return "9999";
		}

		try {

			OrderParam orderParam = new OrderParam();
			orderParam.setOrderCode(orderCode);
			orderParam.setPgKey(pgKey);
			orderParam.setPayAmount(Integer.parseInt(payAmount));

			if (orderPaymentMapper.updateConfirmationOfPaymentStep1(orderParam) > 0){
				if (orderPaymentMapper.updateConfirmationOfPaymentStep2(orderParam) > 0) {
					orderPaymentMapper.updateConfirmationOfPaymentStep3(orderParam);
				} else {
					throw new OrderManagerException();
				}
			} else {
				throw new OrderManagerException();
			}

		} catch (Exception e) {
			log.error(ERROR_MARKER, e.getMessage(), e);
			return "9999";
		}

		return "0000";
	}

	@Override
	public String paycoConfirmationOfPayment(String jsonString) {

		try {
			PayApprovalResult payApprovalResult = (PayApprovalResult) JsonViewUtils.jsonToObject(jsonString, new TypeReference<PayApprovalResult>(){});

			boolean isUpdate = false;
			int payAmount = 0;
			for(PaymentDetail payment : payApprovalResult.getPaymentDetails()) {

				// 무통장
				if ("02".equals(payment.getPaymentMethodCode()) && "Y".equals(payApprovalResult.getPaymentCompletionYn())) {
					isUpdate = true;

				}

				// 결제 수단 전체를 더해야 할까?
				payAmount += payment.getPaymentAmt();
			}

			if (isUpdate) {
				OrderParam orderParam = new OrderParam();
				orderParam.setOrderCode(payApprovalResult.getSellerOrderReferenceKey());
				orderParam.setPgKey(payApprovalResult.getOrderNo());
				orderParam.setPayAmount(payAmount);

				if (orderPaymentMapper.updateConfirmationOfPaymentStep1(orderParam) > 0){
					if (orderPaymentMapper.updateConfirmationOfPaymentStep2(orderParam) > 0) {
						orderPaymentMapper.updateConfirmationOfPaymentStep3(orderParam);
					} else {
						throw new OrderManagerException();
					}
				} else {
					throw new OrderManagerException();
				}
			}

			return "OK";
		} catch(Exception e) {
			log.error(ERROR_MARKER, e.getMessage(), e);

			return "ERROR";
		}
	}

	@Override
	public void updateConfirmPurchase(OrderParam orderParam) {
		OrderItem orderItem = orderMapper.getOrderItemByParam(orderParam);
		if (orderItem == null) {
			throw new OrderException("잘못된 접근 입니다.");
		}

		if (!ShopUtils.checkOrderStatusChange("confirm", orderItem.getOrderStatus())) {
			throw new OrderException("상품 구매확정을 할수 있는 상태가 아닙니다.");
		}

		// 정산 예정일 등록
		orderParam.setRemittanceDate(remittanceMapper.getRemittanceDateBySellerId(orderItem.getSellerId()));

		if (orderShippingMapper.updateConfirmPurchase(orderParam) == 0) {
			throw new OrderException("상품 구매확정을 할수 있는 상태가 아닙니다.\n 주문상태를 확인해주세요.");
		}

		UserDetail userDetail = UserUtils.getUserDetail();
		UserCouponParam userCouponParam = new UserCouponParam();
		userCouponParam.setUserLevelId(userDetail.getLevelId());

		String orderCode = orderParam.getOrderCode();
		List<String> orderStatusOriginalList = orderParam.getOrderStatusList();

		//첫구매 확인을 위해 임시 셋팅
		orderParam.setOrderCode(null);
		List<String> orderStatusList = new ArrayList<>();
		orderStatusList.add("40"); //구매확정 상태 셋팅
		orderParam.setOrderStatusList(orderStatusList);

		//첫구매확정 쿠폰 자동발행[2017-09-15]minae.yun
		if (orderMapper.getOrderCountByParam(orderParam) == 1) {

			userCouponParam.setCouponTargetTimeType("5");
			List<Coupon> firstOrderCouponList = couponService.getCouponByTargetTimeType(userCouponParam);

			if (firstOrderCouponList != null && firstOrderCouponList.size() != 0) {
				for (Coupon coupon : firstOrderCouponList) {
					userCouponParam.setCouponId(coupon.getCouponId());
					userCouponParam.setUserId(orderParam.getUserId());
					couponService.userCouponDownload(userCouponParam);
				}
			}
		}
		//첫구매 확인 후 다시 값 셋팅
		orderParam.setOrderCode(orderCode);
		orderParam.setOrderStatusList(orderStatusOriginalList);

		//상품 구매 후 발행 쿠폰 자동발행[2017-09-18]minae.yun
		userCouponParam.setCouponTargetTimeType("4");
		List<Coupon> afterOrderCouponList = couponService.getCouponByTargetTimeType(userCouponParam);

		if (afterOrderCouponList != null && afterOrderCouponList.size() != 0) {
			for (Coupon coupon : afterOrderCouponList) {
				userCouponParam.setCouponId(coupon.getCouponId());
				userCouponParam.setUserId(orderParam.getUserId());
				couponService.userCouponDownload(userCouponParam);
			}
		}

		// 배송비 정보에 정산 예정일을 업데이트
		orderParam.setShippingSequence(orderItem.getShippingSequence());
		orderShippingMapper.updateShippingRemittanceDate(orderParam);


        // 포인트 적립
        OrderPointParam opp = new OrderPointParam();
        opp.setUserId(orderParam.getUserId());
        pointService.savePointByOrderPointParam(opp);


		// 주문로그
		this.insertOrderLog(
				OrderLogType.ORDER_CONFIRM,
				orderItem.getOrderCode(),
				orderItem.getOrderSequence(),
				orderItem.getItemSequence(),
				orderItem.getOrderStatus()
		);
	}

	private String getAdminUser(){

		String adminUser = "";

		if (SellerUtils.isSellerLogin()) {
			adminUser = SellerUtils.getSeller().getSellerName();
		} else {
			adminUser = UserUtils.getUser().getUserName();
		}

		return adminUser;
	}

	@Override
	public void saveOrderInfo(Order order) {

		if (order.getOrderShippingInfos() == null) {
			throw new OrderException();
		}

		for(OrderShippingInfo info : order.getOrderShippingInfos()) {

			info.setOrderCode(order.getOrderCode());
			info.setOrderSequence(order.getOrderSequence());

			orderMapper.updateOrderShippingInfo(info);

		}

	}

	@Override
	public void changeShippingNumber(ShippingParam shippingParam) {

		if (shippingParam.getDeliveryCompanyId() == 0
			|| StringUtils.isEmpty(shippingParam.getDeliveryNumber())) {
			throw new OrderException("잘못된 접근입니다.");
		}

		DeliveryCompany deliveryCompany = deliveryCompanyService.getDeliveryCompanyById(shippingParam.getDeliveryCompanyId());

		if (deliveryCompany == null) {
			throw new OrderException();
		}

		shippingParam.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
		shippingParam.setDeliveryCompanyUrl(deliveryCompany.getDeliveryCompanyUrl());
		if (orderShippingMapper.updateShippingNumber(shippingParam) == 0) {
			throw new OrderException();
		}

	}

	@Override
	public void updateAdminMemo(Order order) {
		orderMapper.updateAdminMemo(order);
	}

	/** 구매확정 확인 */
	@Override
	public int getOrderItemCntForReview(OrderItem orderItem) {
		return orderMapper.getOrderItemCntForReview(orderItem);
	}

	@Override
	public String getOrderPaymentByPgDataForInipayVacct(PgData pgData, String deviceType) {

		OrderPayment orderPayment = null;
		if ("WEB".equals(deviceType)) {
			orderPayment = orderPaymentMapper.getOrderPaymentByPgDataForInipayVacct(pgData);
		} else if ("MOBILE".equals(deviceType)) {
			orderPayment = orderPaymentMapper.getOrderPaymentByPgDataForInipayVacctForMobile(pgData);
		}
		if (orderPayment == null) {
			return "99";
		}

		if ("WEB".equals(deviceType)) {
			if (orderPayment.getAmount() != Integer.parseInt(pgData.getAmt_input())) {
				return "99";
			}
		} else if ("MOBILE".equals(deviceType)) {
			if (orderPayment.getAmount() != Integer.parseInt(pgData.getP_AMT())) {
				return "99";
			}
		}

		if(orderPayment.getOrderStatus().equals("0")){

			OrderParam orderParam = new OrderParam();
			orderParam.setOrderCode(orderPayment.getOrderCode());
			orderParam.setOrderSequence(orderPayment.getOrderSequence());
			orderParam.setPaymentSequence(orderPayment.getPaymentSequence());
			orderParam.setAdminUserName("system");
			orderParam.setPayAmount(orderPayment.getAmount());

			if (orderPaymentMapper.updateConfirmationOfPaymentStep1(orderParam) > 0){
				if (orderPaymentMapper.updateConfirmationOfPaymentStep2(orderParam) > 0) {
					orderPaymentMapper.updateConfirmationOfPaymentStep3(orderParam);
					if(!orderPayment.getEscrowStatus().equals("N")){	//에스크로면 에스크로 상태 결제완료로 변경
						orderParam.setEscrowStatus("10");
						orderMapper.updateEscrowStatus(orderParam);
					}

				} else {
					throw new OrderManagerException();
				}
			} else {
				throw new OrderManagerException();
			}

			try {

				OrderParam orderSearchParam = new OrderParam();
				orderSearchParam.setOrderCode(orderPayment.getOrderCode());
				orderSearchParam.setConditionType("OPMANAGER");
				Order order = this.getOrderByParam(orderSearchParam);

                // 현금영수증 발행
                CashbillParam cashbillParam = new CashbillParam();

                cashbillParam.setWhere("orderCode");
                cashbillParam.setQuery(orderParam.getOrderCode());

                Iterable<CashbillIssue> cashbillIssues = cashbillIssueRepository.findAll(cashbillParam.getPredicate());

                log.debug("[CASHBILL] START ---------------------------------------------");
                log.debug("[CASHBILL] cashbillIssues Size :  {}", ((List<CashbillIssue>) cashbillIssues).size());

                CashbillResponse response = null;

                for (CashbillIssue cashbillIssue : cashbillIssues) {

                    if ("popbill".equals(environment.getProperty("cashbill.service"))) {
                        response = receiptService.receiptIssue(cashbillIssue);
                    } else if ("inicis".equals(environment.getProperty("cashbill.service"))) {
                        Cashbill cashbill = cashbillIssue.getCashbill();

                        cashbillParam.setCashbillStatus(cashbillIssue.getCashbillStatus());
                        cashbillParam.setAmount(cashbillIssue.getAmount());
                        cashbillParam.setItemName(cashbillIssue.getItemName());
                        cashbillParam.setTaxType(cashbillIssue.getTaxType());
                        cashbillParam.setCashbillCode(cashbill.getCashbillCode());
                        cashbillParam.setCashbillType(cashbill.getCashbillType());
                        cashbillParam.setCustomerName(cashbill.getCustomerName());
                        cashbillParam.setOrderCode(orderParam.getOrderCode());

                        String email = this.getEmailByOrderCode(orderSearchParam);

                        // 이니시스 현금영수증 발행시 이메일이 필수항목
                        cashbillParam.setEmail(email);

                        response = inicisService.cashReceiptIssued(cashbillParam);
                    }

                    if (response == null) {
                        log.debug("[CASHBILL] ERROR >> PG 통신오류(응답없음)");
                        throw new OrderException("PG 통신오류(응답없음)");
                    }

                    log.debug("[CASHBILL] cashbillIssue :  {}", cashbillIssue);
                    log.debug("[CASHBILL] CashbillResponse response.isSuccess() :  {}", response.isSuccess());
                    if (response.isSuccess()) {
                        cashbillIssue.setIssuedDate(DateUtils.getToday(DATETIME_FORMAT));
                        cashbillIssue.setUpdatedDate(DateUtils.getToday(DATETIME_FORMAT));
                        cashbillIssue.setCashbillStatus(CashbillStatus.ISSUED);
                        cashbillIssue.setMgtKey(response.getMgtKey());

                        if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
                            cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
                        } else {
                            cashbillIssue.setUpdateBy("비회원");
                        }

                        cashbillIssueRepository.save(cashbillIssue);

                    } else {
                        log.debug("[CASHBILL] ERROR >> {} : {}", response.getResponseCode(), response.getResponseMessage());
                        throw new OrderException(response.getResponseCode() + " : " + response.getResponseMessage());
                    }
                }
                log.debug("[CASHBILL] END ---------------------------------------------");

				this.sendOrderMessageTx(order, "order_cready_payment", ShopUtils.getConfig());

			} catch(Exception e) {
				log.error("[CASHBILL] ERROR: {}", orderPayment.getOrderCode(), e);
				throw new OrderException(e.getMessage(), e);
			}
		} else {	//입금대기상태가 아닌데 입금통보가 오는 경우 주문번호,날짜등을 로그로 기록.
			//Make Log
			String logLoot = environment.getProperty("pg.inipay.home");
			String logPath = logLoot+"/notiLog/"+DateUtils.getToday()+"/";
			String fileNm = orderPayment.getOrderCode()+".log";
			String content = "["+DateUtils.getToday("yyyy-MM-dd HH:mm:ss")+"] "+orderPayment.getOrderCode()+" - PG사로부터 입금통보 수신확인";

			File dir = new File(logPath);
			//디렉토리가 없으면 생성
			if(!dir.isDirectory()){
				dir.mkdirs();
			}

			//파일에 내용 쓰기
			FileWriter fw = null;
			BufferedWriter out = null;
			try{
				fw = new FileWriter(new File(logPath+fileNm), true);
				fw.write(content);

				out = new BufferedWriter(fw);
				out.newLine();

				fw.flush();

			} catch (Exception e) {
				log.error(ERROR_ORDER_CODE, orderPayment.getOrderCode(), e);
			    throw new OrderException("[ERROR] " + orderPayment.getOrderCode(), e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						log.error("BufferedWriter close : {}", e.getMessage(), e);
					}
				}

				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						log.error("FileWriter close : {}", e.getMessage(), e);
					}
				}
			}
		}

		return "OK";
	}

	@Override
	public void changePayment(EditPayment editPayment) {

		ConfigPg configPg = configPgService.getConfigPg();
		String pgType = "";

		if (configPg != null) {
			pgType = configPg.getPgType().getCode();
		} else {
			pgType = SalesonProperty.getPgService();
		}

		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(editPayment.getOrderCode());
		orderParam.setOrderSequence(editPayment.getOrderSequence());
		orderParam.setPayChangeType("Y");

		orderParam.setConditionType("OPMANAGER");
		if (ShopUtils.isSellerPage()) {
			orderParam.setSellerId(SellerUtils.getSellerId());
			orderParam.setConditionType("SELLER");
		}

		Order order = this.getOrderByParam(orderParam);
		if (order == null) {
			throw new PageNotFoundException();
		}

        editPayment.setUserId(order.getUserId());

		List<OrderPayment> payments = orderMapper.getOrderPaymentListByParam(orderParam);
		if (payments == null) {
			throw new PageNotFoundException();
		}

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

                    if ("bank".equals(eqPayment.getApprovalType())
                            || "realtimebank".equals(eqPayment.getApprovalType())
                            || "vbank".equals(eqPayment.getApprovalType()) ) {
                        editPayment.setCashbillReissueFlag(true);
                    }

					if (eqPayment.getRemainingAmount() < cancelAmount) {
						throw new OrderException("결제잔액을 확인하세요.");
					}

					OrderPgData orderPgData = eqPayment.getOrderPgData();
					String approvalType = eqPayment.getApprovalType();
					if (PointUtils.isPointType(approvalType)) {

						if (order.getUserId() == 0) {
							throw new OrderException("비회원 포인트 사용 에러.");
						}

						Point point = new Point();
						point.setPoint(cancelAmount);
						point.setUserId(order.getUserId());
						point.setReason("주문번호 :" + order.getOrderCode() + " 포인트 사용 취소");
						point.setPointType(approvalType);
						point.setOrderCode(order.getOrderCode()); //[2017-05-11]minae.yun
						pointService.earnPoint("return", point);

					} else if (orderPgData.getOrderPgDataId() > 0) {

						orderPgData.setReturnAccountNo(changePayment.getReturnBankVirtualNo());
						orderPgData.setReturnBankName(changePayment.getReturnBankName());
						orderPgData.setReturnName(changePayment.getReturnBankInName());
                        orderPgData.setCancelReason(editPayment.getRefundReason());

						boolean isSuccess = false;

						// 취소 금액과 잔여액이 같으면 전체 취소
						boolean isPartCancel = cancelAmount == eqPayment.getRemainingAmount() ? false : true;

						// CJH 2016.12.07 - 이니시스의 경우 부분취소를 한번하면 부분취소만 되는듯!!
						if ("inicis".equals(orderPgData.getPgServiceType()) && "PART_CANCEL".equals(orderPgData.getPartCancelDetail())) {
							isPartCancel = true;
						}

						// 2017.05.25 Son Jun-Eu - Kspay도 한번 부분취소하면 부분취소만 할수있음.
						if ("kspay".equals(orderPgData.getPgServiceType())) {
							if(!"N".equals(orderPgData.getPartCancelDetail()))
								isPartCancel = true;
							else if(eqPayment.getRemainingAmount() != eqPayment.getTotalPaymentAmount() && eqPayment.getApprovalType().equals("realtimebank"))	//실시간계좌이체는 부분취소시 PG취소 안함
								isPartCancel = true;
						}

						// 2017.09.04 Son Jun-Eu Kcp 계좌이체,가상계좌에 부분취소 이력이 있으면 이후에도 계속 부분취소
						if ("kcp".equals(orderPgData.getPgServiceType())) {
							if(!"CARD".equals(orderPgData.getPgPaymentType()) && "PART_CANCEL".equals(orderPgData.getPartCancelDetail()))
								isPartCancel = true;
						}

						if ("nicepay".equals(orderPgData.getPgServiceType()) && "PART_CANCEL".equals(orderPgData.getPartCancelDetail())) {
							isPartCancel = true;
						}

						orderPgData.setCancelAmount(cancelAmount);
						orderPgData.setRemainAmount(eqPayment.getRemainingAmount() - cancelAmount);

						String useVbankRefundService = "";

						if (configPg != null) {
							useVbankRefundService = configPg.isUseVbackRefundService() ? "Y" : "N";
						} else {
							useVbankRefundService = environment.getProperty("pg.useVbank.refundService");
						}

						// 가상계좌 취소요청시 pg가상계좌 환불서비스 사용 안할경우 pg연동취소 사용안함.
						if ("vbank".equals(eqPayment.getApprovalType()) && "N".equals(useVbankRefundService)){
							isSuccess = true;

						} else if(!"N".equals(eqPayment.getEscrowStatus())){
							if (eqPayment.getEscrowStatus().equals("40")) {
								if("inicis".equals(orderPgData.getPgServiceType())){
									// 2017.07.05 Son Jun-Eu - 이니시스 에스크로 결제인 경우 PG사에 구매거절 확인 전문송신
									List<String> param = new ArrayList<>();

									param.add(orderPgData.getPgKey());
									param.add(environment.getProperty("pg.inipay.escrow.mid"));
									param.add(UserUtils.getManagerName());

									//									isSuccess = inicisService.escrowDenyConfirm(param);	// 현재 에스크로 배송등록 이후에는 무조건
									isSuccess = true;
								}
							}else if (eqPayment.getEscrowStatus().equals("20")) {
								isSuccess = true;

							}
						} else if (isPartCancel == false) {
							// PG 취소 - 즉시 취소
							if ("inicis".equals(orderPgData.getPgServiceType())) {
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
								isSuccess = easypayService.cancel(orderPgData);
							} else if ("nicepay".equals(orderPgData.getPgServiceType())) {
								orderPgData.setRequest(editPayment.getRequest());
								orderPgData.setResponse(editPayment.getResponse());
								isSuccess = nicepayService.cancel(orderPgData);
                            } else if ("naverpay".equals(orderPgData.getPgServiceType())) {
                                orderPgData.setCancelReason(editPayment.getRefundReason());
                                orderPgData = naverPaymentApi.cancel(orderPgData, configPg);
                                isSuccess = orderPgData.isSuccess();
                            }

						} else {

							if ("inicis".equals(orderPgData.getPgServiceType())) {
								orderPgData = inicisService.partCancel(orderPgData);
							} else if ("lgdacom".equals(orderPgData.getPgServiceType())) {
								orderPgData = lgDacomService.partCancel(orderPgData);
							} else if ("kakaopay".equals(orderPgData.getPgServiceType())) {
								orderPgData.setRemainAmount(cancelAmount);
								orderPgData = kakaopayService.partCancel(orderPgData);
							} else if ("payco".equals(orderPgData.getPgServiceType())) {

								OrderItem item = new OrderItem();
								for (OrderShippingInfo orderShippingInfo : order.getOrderShippingInfos()) {
									for (OrderItem orderItem : orderShippingInfo.getOrderItems()) {
										item.setItemUserCode(orderItem.getItemUserCode());
									}
								}

								orderPgData.setPaycoCancelProducts(PaycoUtils.makePaycoCancelProducts(item, cancelAmount, order.getShippingTotalAmount(), orderPgData.getPgProcInfo()));
								orderPgData = paycoService.partCancel(orderPgData);
							} else if ("kspay".equals(orderPgData.getPgServiceType())) {
								if(!approvalType.equals("realtimebank"))
									orderPgData = kspayService.partCancel(orderPgData);
								else
									orderPgData.setSuccess(true);

							} else if ("kcp".equals(orderPgData.getPgServiceType())) {
								orderPgData = kcpService.partCancel(orderPgData);
							} else if ("easypay".equals(orderPgData.getPgServiceType())) {
								orderPgData = easypayService.partCancel(orderPgData);
							} else if ("nicepay".equals(orderPgData.getPgServiceType())) {
								orderPgData.setRequest(editPayment.getRequest());
								orderPgData.setResponse(editPayment.getResponse());
								orderPgData = nicepayService.partCancel(orderPgData);
                            } else if ("naverpay".equals(orderPgData.getPgServiceType())) {
                                orderPgData.setCancelReason(editPayment.getRefundReason());
                                orderPgData = naverPaymentApi.cancel(orderPgData, configPg);
                            }

							isSuccess = orderPgData.isSuccess();

						}

						if (isSuccess) {

                            eqPayment.setCancelAmount(orderPgData.getCancelAmount());
                            eqPayment.setRemainingAmount(orderPgData.getRemainAmount());

							OrderPayment orderPayment = new OrderPayment(order, eqPayment.getApprovalType());

                            if(isPartCancel) {
                                // 2017.07.12 Son Jun-Eu 부분취소시 취소금액, 결제잔액 등록
                                eqPayment.setCancelAmount(orderPgData.getCancelAmount());
                                eqPayment.setRemainingAmount(orderPgData.getRemainAmount());

                                orderMapper.updateOrderPgData(orderPgData);
                            }

							orderPaymentMapper.updateOrderPaymentForCancel(eqPayment);

							orderPayment.setPaymentType("2");
							orderPayment.setCancelAmount(cancelAmount);
							orderPayment.setPayDate(DateUtils.getToday(DATETIME_FORMAT));
							orderPayment.setNowPaymentFlag("Y");
							orderPayment.setDeviceType("ADMIN");
							orderPayment.setBankInName(changePayment.getReturnBankInName());
							if (StringUtils.isEmpty(changePayment.getReturnBankName()) == false) {
								List<CodeInfo> list = ShopUtils.getBankListByKey(pgType);
								for(CodeInfo code : list) {
									if (code.getKey().getId().equals(changePayment.getReturnBankName())) {
										orderPayment.setReturnBankName(code.getLabel());
										break;
									}
								}
							}
							orderPayment.setBankVirtualNo(orderPayment.getReturnBankName()+" "+changePayment.getReturnBankVirtualNo());

							orderPaymentMapper.insertOrderPayment(orderPayment);

							continue;

						} else {

							String errorMessage = "";
							if (orderPgData.getErrorMessage() != null && !orderPgData.getErrorMessage().isEmpty()) {
								errorMessage = " (" + orderPgData.getErrorMessage() + ")";
							}
							throw new OrderException("[ERROR] PG 취소가 정상적으로 처리되지 않았습니다. " + errorMessage);
						}
					}

					OrderPayment orderPayment = new OrderPayment(eqPayment, approvalType);
					orderPayment.setDeviceType("ADMIN");
					orderPayment.setCancelAmount(eqPayment.getCancelAmount() + cancelAmount);
					orderPayment.setRemainingAmount(eqPayment.getRemainingAmount() - cancelAmount);
					orderPaymentMapper.updateOrderPaymentForCancel(orderPayment);

					orderPayment.setPaymentType("2");
					orderPayment.setOrderPgDataId(eqPayment.getOrderPgDataId());
					orderPayment.setCancelAmount(cancelAmount);
                    orderPayment.setRemainingAmount(0);
					orderPayment.setPayDate(DateUtils.getToday(DATETIME_FORMAT));
					orderPayment.setNowPaymentFlag("Y");
					orderPayment.setBankInName(changePayment.getReturnBankInName());

					if (StringUtils.isEmpty(changePayment.getReturnBankName()) == false) {
						List<CodeInfo> list = ShopUtils.getBankListByKey(pgType);
						for(CodeInfo code : list) {
							if (code.getKey().getId().equals(changePayment.getReturnBankName())) {
								orderPayment.setReturnBankName(code.getLabel());
								break;
							}
						}
					}
					orderPayment.setBankVirtualNo(orderPayment.getReturnBankName()+" "+changePayment.getReturnBankVirtualNo());

					orderPaymentMapper.insertOrderPayment(orderPayment);

					// 환불시 환불 정보 수정 2017-04-10 yulsun.yoo
					orderParam.setReturnBankName(orderPayment.getReturnBankName());
					orderParam.setReturnBankInName(changePayment.getReturnBankInName());
					orderParam.setReturnVirtualNo(changePayment.getReturnBankVirtualNo());
					orderMapper.updateOrderReturnInfo(orderParam);

				}
			}
		}

		if (editPayment.getDeletePaymentIds() != null) {
			for(int paymentSequence : editPayment.getDeletePaymentIds()) {

				orderParam.setPaymentSequence(paymentSequence);

				// 1. [SKC] OrderPgData 조회
				OrderPgData orderPgData = orderPaymentMapper.getOrderPgDataByOrderParam(orderParam);

				// 2. orderPgData 가 가상계좌 결제인 경우 (가상계좌 취소인 경우 PG 결제 요청 취소 처리)
				if (orderPgData != null && "vbank".equals(orderPgData.getPgPaymentType().toLowerCase())) {
					boolean isCanceled = inicisService.cancel(orderPgData);

					if (!isCanceled) {
						throw new OrderException("가상계좌 취소(입금요청 취소)가 실패하였습니다. ");
					}
				}

				orderPaymentMapper.deleteOrderPayment(orderParam);

			}
		}

		if (editPayment.getNewPayments() != null) {

			for(NewPayment payment : editPayment.getNewPayments()) {

				if (payment.getAmount() > 0) {

					String approvalType = payment.getApprovalType();
					if (PointUtils.isPointType(approvalType)) {

						if (order.getUserId() == 0) {
							throw new OrderException("비회원 포인트 사용 에러.");
						}

						PointUsed pointUsed = new PointUsed();
						pointUsed.setOrderCode(order.getOrderCode());
						pointUsed.setPoint(payment.getAmount());
						pointUsed.setDetails("주문번호 :" + order.getOrderCode() + " 사용");

						pointService.deductedPoint(pointUsed, order.getUserId(), approvalType);

					}

					OrderPayment orderPayment = new OrderPayment(order, approvalType);
					orderPayment.setDeviceType("ADMIN");
					orderPayment.setAmount(payment.getAmount());
					orderPayment.setNowPaymentFlag("N");
					orderPayment.setPaymentType("1");

					if (!"bank".equals(approvalType)) {
						orderPayment.setPayDate(DateUtils.getToday(DATETIME_FORMAT));
						orderPayment.setRemainingAmount(payment.getAmount());
						orderPayment.setNowPaymentFlag("Y");
					}

					orderPaymentMapper.insertOrderPayment(orderPayment);
				}
			}

		}


		// [SKC] 결제 수단과 상관 없이 은행 입금으로 환불 처리하는 경우 -> bank / 취소로 등록함.
		if (editPayment.getRefundAmount() != null && editPayment.getRefundAmount() > 0) {

			OrderPayment orderPayment = new OrderPayment(order, "bank");
			orderPayment.setPaymentType("2");
			orderPayment.setDeviceType("ADMIN");
			orderPayment.setAmount(0);
			orderPayment.setTaxFreeAmount(0);
			orderPayment.setCancelAmount(editPayment.getRefundAmount());
			orderPayment.setRemainingAmount(0);
			orderPayment.setPayDate(DateUtils.getToday(DATETIME_FORMAT));			// paydate는 현재 시간으로 설정.
			orderPayment.setNowPaymentFlag("N");
			orderPayment.setRefundFlag("Y");

			StringBuffer sb = new StringBuffer();
			sb.append("[");
			sb.append(ShopUtils.getBankName(pgType, editPayment.getRefundBankName()));
			sb.append("] ");
			sb.append(editPayment.getRefundAccountNumber());
			sb.append(" (");
			sb.append(editPayment.getRefundAccountName());
			sb.append(")");

			orderPayment.setPaymentSummary(sb.toString());

			orderPaymentMapper.insertOrderPayment(orderPayment);

		}


		//orderPaymentMapper.updateTotalPayAmount(orderParam);
        orderMapper.updateConfirmationOfPaymentCancelStep2(orderParam);
	}

	@Override
	public void adminClaimApply(AdminClaimApply adminClaimApply) {

		if (adminClaimApply.getAdminClaimApplyKey() == null) {
			return;
		}

		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(adminClaimApply.getOrderCode());
		orderParam.setOrderSequence(adminClaimApply.getOrderSequence());
		orderParam.setConditionType("OPMANAGER");

		if (ShopUtils.isSellerPage()) {
			orderParam.setConditionType("SELLER");
			orderParam.setSellerId(SellerUtils.getSellerId());
		}

		orderParam.setAdditionItemFlag("");
		Order order = orderMapper.getOrderByParam(orderParam);
		order.setOrderPayments(orderMapper.getOrderPaymentListByParam(orderParam));

		boolean isCopyItemSequence = false;
		int parentIemSequence = 0;
		int parentItemId = 0;
		String parentItemOptions = "";

		if ("1".equals(adminClaimApply.getClaimType())) { // 취소

			List<OrderItem> erpOrderItems = new ArrayList<>();
			for(String key : adminClaimApply.getAdminClaimApplyKey()) {

				OrderCancelApply apply = adminClaimApply.getOrderCancelApply();
				AdminClaimApplyItem item = adminClaimApply.getItemMap().get(key);

				if (item == null) {
					continue;
				}

				orderParam.setItemSequence(item.getItemSequence());
				OrderItem orderItem = orderMapper.getOrderItemByParam(orderParam);

				if (orderItem == null) {
					continue;
				}

				if (item.getQuantity() <= 0) {
					continue;
				}

				if (!ShopUtils.checkOrderStatusChange("cancel", orderItem.getOrderStatus())) {
					continue;
				}

				if (orderItem.getOrderQuantity() < apply.getClaimApplyQuantity() + orderItem.getClaimQuantity()) {
					continue;
				}

				// 추가구성상품과 묶인 본품이 copyOrderItemForCancelApply 처리로 신규 itemSequence가 생성됐을 때 추가구성상품의 parentItemSequence에 세팅
				if (isCopyItemSequence && "Y".equals(orderItem.getAdditionItemFlag())
						&& parentItemId == orderItem.getParentItemId() && parentItemOptions.equals(orderItem.getParentItemOptions())) {

					apply.setParentItemSequence(parentIemSequence);
				}

				// DB에 저장된 수량을 가져다가 신청수량을 빼서 비교하기
				orderItem.setQuantity(orderItem.getQuantity() - item.getQuantity());

				apply.setClaimApplyQuantity(item.getQuantity());
				apply.setItemSequence(item.getItemSequence());

				apply.setCancelReasonDetail(adminClaimApply.getCancelClaimReasonDetail());
				apply.setCancelReason(adminClaimApply.getCancelClaimReason());
				apply.setCancelReasonText(adminClaimApply.getCancelClaimReasonText());

				// 잔여 수량이 없으면 기존정보 수정
				if (orderItem.getQuantity() == 0) {
					orderClaimApplyMapper.insertOrderCancelApply(apply);
					orderClaimApplyMapper.updateClaimQuantityForCancelApply(apply);
				} else {

					orderClaimApplyMapper.copyOrderItemForCancelApply(apply);
					orderClaimApplyMapper.updateOrderItemQuantityForCancel(apply);

					// 중요!!
					apply.setItemSequence(apply.getCopyItemSequence());
					orderClaimApplyMapper.insertOrderCancelApply(apply);

					// ERP 연동 시 사용
					orderItem.setCopyItemSequence(apply.getCopyItemSequence());
					orderItem.setErpOriginUnique(orderItem.getOrderCode() + "" + orderItem.getOrderSequence() + "" + getTwoDisitItemSequence(orderItem.getItemSequence()) + "00" + "00");


					// 추가구성상품과 묶인 본품인지 체크 후 정보 세팅
					if ("N".equals(orderItem.getAdditionItemFlag())) {
						orderParam.setAdditionItemFlag("Y");
						orderParam.setParentItemId(orderItem.getItemId());
						orderParam.setParentItemOptions(orderItem.getOptions());
						List<OrderItem> additionOrderItem = getOrderItemListByParam(orderParam);

						if (additionOrderItem != null) {
							isCopyItemSequence = true;
							parentIemSequence = apply.getItemSequence();
							parentItemId = orderItem.getItemId();
							parentItemOptions = orderItem.getOptions();
						}
					}

				}

				// 주문 로그
				this.insertOrderLog(OrderLogType.CLAIM_CANCEL,
							apply.getOrderCode(),
							apply.getOrderSequence(),
							apply.getItemSequence(),
							orderItem.getOrderStatus());


				orderItem.setCancelApply(apply);
				erpOrderItems.add(orderItem);
			}

			// ERP 연동
			ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
			erpOrder.setOrderItems(erpOrderItems);

			erpService.saveOrderListGet(erpOrder);


		} else if ("2".equals(adminClaimApply.getClaimType())) {

			List<OrderItem> erpOrderItems = new ArrayList<>();
			for(String key : adminClaimApply.getAdminClaimApplyKey()) {

				OrderReturnApply apply = adminClaimApply.getOrderReturnApply();
				AdminClaimApplyItem item = adminClaimApply.getItemMap().get(key);

				if (item == null) {
					continue;
				}

				orderParam.setItemSequence(item.getItemSequence());
				OrderItem orderItem = orderMapper.getOrderItemByParam(orderParam);

				if (orderItem == null) {
					continue;
				}

				// 환불 신청 전 주문상태 저장
				apply.setPreviousOrderStatus(orderItem.getOrderStatus());

				if (item.getQuantity() <= 0) {
					continue;
				}

				if (!ShopUtils.checkOrderStatusChange("return", orderItem.getOrderStatus())) {
					continue;
				}

				if (orderItem.getOrderQuantity() < apply.getClaimApplyQuantity() + orderItem.getClaimQuantity()) {
					continue;
				}

				// 추가구성상품과 묶인 본품이 copyOrderItemForReturnApply 처리로 신규 itemSequence가 생성됐을 때 추가구성상품의 parentItemSequence에 세팅
				if (isCopyItemSequence && "Y".equals(orderItem.getAdditionItemFlag())
						&& parentItemId == orderItem.getParentItemId() && parentItemOptions.equals(orderItem.getParentItemOptions())) {

					apply.setParentItemSequence(parentIemSequence);
				}

				// DB에 저장된 수량을 가져다가 신청수량을 빼서 비교하기
				orderItem.setQuantity(orderItem.getQuantity() - item.getQuantity());

				apply.setClaimApplyQuantity(item.getQuantity());
				apply.setItemSequence(item.getItemSequence());

				orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());
				apply.setReturnShippingInfo(orderMapper.getOrderShippingInfoByParam(orderParam));

				apply.setReturnReasonDetail(adminClaimApply.getReturnClaimReasonDetail());
				apply.setReturnReason(adminClaimApply.getReturnClaimReason());
				apply.setReturnReasonText(adminClaimApply.getReturnClaimReasonText());

				ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
				shipmentReturnParam.setShipmentReturnId(orderItem.getShipmentReturnId());
				ShipmentReturn shipmentReturn = shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
				if (shipmentReturn != null) {
					apply.setShipmentReturnId(shipmentReturn.getShipmentReturnId());
				}

				// 1 : 본사반송, 2 업체 반송
				long sellerId = "2".equals(orderItem.getShipmentReturnType()) ? orderItem.getSellerId() : SellerUtils.DEFAULT_OPMANAGER_SELLER_ID;
				apply.setShipmentReturnSellerId(sellerId);

				// 잔여 수량이 없으면 기존정보 수정
				if (orderItem.getQuantity() == 0) {
					orderClaimApplyMapper.insertOrderReturnApply(apply);
					orderClaimApplyMapper.updateClaimQuantityForReturnApply(apply);
				} else {

					orderClaimApplyMapper.copyOrderItemForReturnApply(apply);
					orderClaimApplyMapper.updateOrderItemQuantityForReturn(apply);

					// 중요!!
					apply.setItemSequence(apply.getCopyItemSequence());
					orderClaimApplyMapper.insertOrderReturnApply(apply);

					// ERP 연동 시 사용
					orderItem.setCopyItemSequence(apply.getCopyItemSequence());
					orderItem.setErpOriginUnique(orderItem.getOrderCode() + "" + orderItem.getOrderSequence() + "" + getTwoDisitItemSequence(orderItem.getItemSequence()) + "00" + "00");


					// 추가구성상품과 묶인 본품인지 체크 후 정보 세팅
					if ("N".equals(orderItem.getAdditionItemFlag())) {
						orderParam.setAdditionItemFlag("Y");
						orderParam.setParentItemId(orderItem.getItemId());
						orderParam.setParentItemOptions(orderItem.getOptions());
						List<OrderItem> additionOrderItem = getOrderItemListByParam(orderParam);

						if (additionOrderItem != null) {
							isCopyItemSequence = true;
							parentIemSequence = apply.getItemSequence();
							parentItemId = orderItem.getItemId();
							parentItemOptions = orderItem.getOptions();
						}
					}

				}

				// 주문 로그
				this.insertOrderLog(OrderLogType.CLAIM_RETURN,
						apply.getOrderCode(),
						apply.getOrderSequence(),
						apply.getItemSequence(),
						orderItem.getOrderStatus());


				orderParam.setShippingSequence(orderItem.getShippingSequence());
				OrderShipping shipping = orderShippingMapper.getOrderShippingByParam(orderParam);

				orderItem.setOrderShipping(shipping);
				orderItem.setReturnApply(apply);
				erpOrderItems.add(orderItem);
			}

			// ERP 연동
			ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_CLAIM);
			erpOrder.setOrderItems(erpOrderItems);

			erpService.saveOrderListGet(erpOrder);

		} else {

			for(String key : adminClaimApply.getAdminClaimApplyKey()) {

				OrderExchangeApply apply = adminClaimApply.getOrderExchangeApply();
				AdminClaimApplyItem item = adminClaimApply.getItemMap().get(key);

				if (item == null) {
					continue;
				}

				orderParam.setItemSequence(item.getItemSequence());
				OrderItem orderItem = orderMapper.getOrderItemByParam(orderParam);

				if (orderItem == null) {
					continue;
				}

				if (item.getQuantity() <= 0) {
					continue;
				}

				if (!ShopUtils.checkOrderStatusChange("exchange", orderItem.getOrderStatus())) {
					continue;
				}

				if (orderItem.getOrderQuantity() < apply.getClaimApplyQuantity() + orderItem.getClaimQuantity()) {
					continue;
				}

				// 추가구성상품과 묶인 본품이 copyOrderItemForExchangeApply 처리로 신규 itemSequence가 생성됐을 때 추가구성상품의 parentItemSequence에 세팅
				if (isCopyItemSequence && "Y".equals(orderItem.getAdditionItemFlag())
						&& parentItemId == orderItem.getParentItemId() && parentItemOptions.equals(orderItem.getParentItemOptions())) {

					apply.setParentItemSequence(parentIemSequence);
				}

				// DB에 저장된 수량을 가져다가 신청수량을 빼서 비교하기
				orderItem.setQuantity(orderItem.getQuantity() - item.getQuantity());

				apply.setClaimApplyQuantity(item.getQuantity());
				apply.setItemSequence(item.getItemSequence());

				apply.setExchangeReasonDetail(adminClaimApply.getExchangeClaimReasonDetail());
				apply.setExchangeReason(adminClaimApply.getExchangeClaimReason());
				apply.setExchangeReasonText(adminClaimApply.getExchangeClaimReasonText());

				orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());
				apply.setExchangeShippingInfo(orderMapper.getOrderShippingInfoByParam(orderParam));

				ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
				shipmentReturnParam.setShipmentReturnId(orderItem.getShipmentReturnId());
				ShipmentReturn shipmentReturn = shipmentReturnMapper.getShipmentReturnByParam(shipmentReturnParam);
				if (shipmentReturn != null) {
					apply.setShipmentReturnId(shipmentReturn.getShipmentReturnId());
				}

				// 1 : 본사반송, 2 업체 반송
				long sellerId = "2".equals(orderItem.getShipmentReturnType()) ? orderItem.getSellerId() : SellerUtils.DEFAULT_OPMANAGER_SELLER_ID;
				apply.setShipmentReturnSellerId(sellerId);

				// 잔여 수량이 없으면 기존정보 수정
				if (orderItem.getQuantity() == 0) {
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
				this.insertOrderLog(OrderLogType.CLAIM_EXCHANGE,
						apply.getOrderCode(),
						apply.getOrderSequence(),
						apply.getItemSequence(),
						orderItem.getOrderStatus());
			}
		}
	}

	/**
	 * 주문상태별 개수
	 */
	@Override
	public OrderCount getOrderCountsByUserId(long userId) {
		return orderMapper.getOrderCountsByUserId(userId);
	}

	@Override
	public List<OpmanagerCount> getOpmanagerShippingDelayCountAll(HashMap<String, Object> map) {

		return orderMapper.getOpmanagerShippingDelayCountAll(map);
	}

	@Override
	public String getTidByParam(String orderCode) {
		return orderMapper.getTidByParam(orderCode);
	}

	@Override
	public void insertOrderLog(OrderLogType logType,
							   String orderCode, int orderSequence, int itemSequence, String orgOrderStatus) {

		// 로그 사용 여부에 따라 로그 기록
		if (logType != null && configLogService.isUsedConfigLogs(LogType.ORDER_STATUS, logType.getCode())) {

			try {

				UserType userType = this.getUserTypeForOrderLog();

				OrderItem orderItem = this.getOrderItemForOrderLog(orderCode, orderSequence, itemSequence);

				if (!ValidationUtils.isNull(orderItem)) {
					String ip = "";
					try {
						HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
						// ip = JwtUtils.getClientIpAddress(request);
                        ip = saleson.common.utils.CommonUtils.getClientIp(request);
					} catch (Exception e) {
						log.info("[insertOrderLog] 배치 처리 등과 같이 request가 없는 처리인 경우 IP는 조회할 수 없습니다.", e);
					}

					OrderLog orderLog = new OrderLog();

					orderLog.setLogType(logType);
					orderLog.setOrderCode(orderCode);
					orderLog.setOrderSequence(orderSequence);
					orderLog.setItemName(orderItem.getItemName());
					orderLog.setItemSequence(orderItem.getItemSequence());
					orderLog.setOrderStatus(ShopUtils.getOrderStatusLabel(orderItem.getOrderStatus()));
					orderLog.setOrgOrderStatus(ShopUtils.getOrderStatusLabel(orgOrderStatus));
					orderLog.setIp(ip);
					orderLog.setUserType(userType);
					orderLog.setCreatedBy(getOrderLogLoginId());
					orderLog.setUpdatedBy(getOrderLogLoginId());

					orderLogRepository.save(orderLog);
				}

			} catch (Exception e) {
				log.error(ERROR_MARKER, e.getMessage(), e);
			}

		}
	}

	private UserType getUserTypeForOrderLog() {
		UserType userType = null;

		Seller seller = SellerUtils.getSeller();
		if (ValidationUtils.isNotNull(seller) && ShopUtils.isSellerPage()) {
			userType = UserType.SELLER;
		} else {
			if (UserUtils.isManagerLogin()) {
				userType = UserType.MANAGER;
			} else {
				if (UserUtils.isUserLogin()) {
					userType = UserType.USER;
				} else {
					userType = UserType.GUEST;
				}
			}
		}

		return userType;
	}

	private void setConditionTypeForOrderLog(OrderParam orderParam) {
		String conditionType = "";

		Seller seller = SellerUtils.getSeller();
		if (ValidationUtils.isNotNull(seller) && ShopUtils.isSellerPage()) {
			conditionType = "SELLER";
			orderParam.setSellerId(SellerUtils.getSellerId());
		} else {
			if (0 < UserUtils.getManagerId()) {
				conditionType = "OPMANAGER";
			} else {
				conditionType = "";
			}
		}
		orderParam.setConditionType(conditionType);
	}

	@Override
	public OrderItem getOrderItemForOrderLog(String orderCode, int orderSequence, int itemSequence) {
		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode(orderCode);
		orderParam.setOrderSequence(orderSequence);
		orderParam.setItemSequence(itemSequence);
		this.setConditionTypeForOrderLog(orderParam);

		return orderMapper.getOrderItemByParam(orderParam);
	}

    @Override
    public List<OrderItem> getOrderItemListForOrderLog(String orderCode, int orderSequence) {

        OrderParam orderParam = new OrderParam();
        orderParam.setOrderCode(orderCode);
        orderParam.setOrderSequence(orderSequence);
        this.setConditionTypeForOrderLog(orderParam);

        return orderMapper.getOrderItemListByParam(orderParam);
    }

	@Override
	public Iterable<OrderLog> getOrderLogListByOrderCode(OrderLog orderLog) {
		return orderLogRepository.findAll(orderLog.getPredicate());
	}

	private String getOrderLogLoginId() {

		Seller seller = SellerUtils.getSeller();
		if (ValidationUtils.isNotNull(seller) && ShopUtils.isSellerPage()) {
			return new StringBuilder()
					.append(seller.getSellerName())
					.append("(")
					.append(seller.getLoginId())
					.append(")")
					.toString();
		}

		if (UserUtils.isManagerLogin()) {
			return new StringBuilder()
					.append(UserUtils.getManagerName())
					.append("(")
					.append(UserUtils.getLoginId())
					.append(")")
					.toString();
		}

		if (UserUtils.isUserLogin()) {
			return new StringBuilder()
					.append(UserUtils.getUser().getUserName())
					.append("(")
					.append(UserUtils.getLoginId())
					.append(")")
					.toString();
		}

		return "";
	}

	@Override
	public List<OrderList> getAllOrderListByParamForManager(OrderParam orderParam) {
		int totalCount = orderMapper.getAllOrderCountByParamForManager(orderParam);

		if (orderParam.getItemsPerPage() == 10) {
			orderParam.setItemsPerPage(50);
		}

		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());
		orderParam.setPagination(pagination);

		List<OrderList> list = orderMapper.getAllOrderListByParamForManager(orderParam);

		// 사은품 조회
		setOrderGiftItemForOrderList(list);

		return list;

	}

    private void receiptDataSave(Buy buy, int totCnt, int cnt) {

        if (totCnt == cnt) {
            long pointAmount = 0;

            // 포인트 사용금액 확인
            for (BuyPayment payment : buy.getPayments()) {
                if (PointUtils.isPointType(payment.getApprovalType())) {
                    pointAmount = (long) payment.getAmount();
                }
            }

            Boolean isSuccess = false;

            Cashbill cashbill = buy.getCashbill();

			ConfigPg configPg = configPgService.getConfigPg();
			String cashbillService = "";

			if (configPg != null) {
				cashbillService = configPg.getCashbillServiceType().getCode().toLowerCase();
			} else {
				cashbillService = environment.getProperty("cashbill.service");
			}

			cashbill.setPgService(cashbillService);
            cashbill.setOrderCode(buy.getOrderCode());
            cashbill.setCustomerName(buy.getBuyer().getUserName());
            cashbill.setCreatedDate(DateUtils.getToday(DATETIME_FORMAT));

            if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
                cashbill.setCreatedBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
            } else {
                cashbill.setCreatedBy("비회원");
            }

            CashbillIssueType cashbillIssueType = CashbillIssueType.NORMAL;

            if (CashbillType.NONE == buy.getCashbill().getCashbillType()
                    && buy.getOrderPrice().getOrderPayAmount() >= 100000) {     // 현금영수증 발급신청은 안했지만 10만원이상 상품구매시
                cashbill.setCashbillCode("0100001234");
                cashbill.setCashbillType(CashbillType.PERSONAL);

                cashbillRepository.save(cashbill);

                isSuccess = true;

                cashbillIssueType = CashbillIssueType.TEMP;
            } else if (CashbillType.NONE != buy.getCashbill().getCashbillType() && buy.getCashbill().getCashbillType() != null ) {                                                  // 현금영수증 발급신청했을 경우
                cashbillRepository.save(cashbill);

                isSuccess = true;
            }

            if (isSuccess) {
                CashbillIssue taxCashbillIssue = new CashbillIssue();
                CashbillIssue taxFreeCashbillIssue = new CashbillIssue();

                long taxCashbillAmount = 0;
                long taxFreeCashbillAmount = 0;

                // 배송비 현금영수증
                if (buy.getOrderPrice().getTotalShippingAmount() > 0) {
                    taxCashbillAmount += (long) buy.getOrderPrice().getTotalShippingAmount();
                }

                // 상품별 과세/면세 구분해서 현금영수증 금액에 추가
                for (BuyItem item : buy.getItems()) {

                    if ("1".equals(item.getItem().getTaxType())) {
                        taxCashbillAmount += (long) item.getItemPrice().getSaleAmount();
                    } else {
                        taxFreeCashbillAmount += (long) item.getItemPrice().getSaleAmount();
                    }
                }

                if (taxCashbillAmount <= pointAmount) {
                    pointAmount -= taxCashbillAmount;
                    taxCashbillAmount = 0;

                    taxFreeCashbillAmount -= pointAmount;
                    pointAmount = 0;
                } else {
                    taxCashbillAmount -= pointAmount;
                    pointAmount = 0;

                    if (taxCashbillAmount > 0) {
                        taxCashbillIssue.setCashbill(cashbill);
                        taxCashbillIssue.setAmount((long) taxCashbillAmount);
                        taxCashbillIssue.setCashbillStatus(CashbillStatus.PENDING);
                        taxCashbillIssue.setItemName(buy.getOrderCode() + "(과세)");
                        taxCashbillIssue.setCreatedDate(DateUtils.getToday(DATETIME_FORMAT));
                        taxCashbillIssue.setCashbillIssueType(cashbillIssueType);
                        taxCashbillIssue.setUpdateBy(buy.getBuyer().getUserName() + "(" + buy.getBuyer().getLoginId() + ")");
                        taxCashbillIssue.setUpdatedDate(DateUtils.getToday(DATETIME_FORMAT));
                        taxCashbillIssue.setTaxType(TaxType.CHARGE);

                        cashbillIssueRepository.save(taxCashbillIssue);
                    }

                    if (taxFreeCashbillAmount > 0) {
                        taxFreeCashbillIssue.setCashbill(cashbill);
                        taxFreeCashbillIssue.setAmount((long) taxFreeCashbillAmount);
                        taxFreeCashbillIssue.setCashbillStatus(CashbillStatus.PENDING);
                        taxFreeCashbillIssue.setItemName(buy.getOrderCode() + "(면세)");
                        taxFreeCashbillIssue.setCreatedDate(DateUtils.getToday(DATETIME_FORMAT));
                        taxFreeCashbillIssue.setCashbillIssueType(cashbillIssueType);
                        taxFreeCashbillIssue.setUpdateBy(buy.getBuyer().getUserName() + "(" + buy.getBuyer().getLoginId() + ")");
                        taxFreeCashbillIssue.setUpdatedDate(DateUtils.getToday(DATETIME_FORMAT));
                        taxFreeCashbillIssue.setTaxType(TaxType.FREE);

                        cashbillIssueRepository.save(taxFreeCashbillIssue);
                    }
                }
            }
        }
    }

    @Override
    public String getEmailByOrderCode(OrderParam orderParam) {
        return orderMapper.getEmailByOrderCode(orderParam);
    }

	/**
	 * 주문 상품 등록
	 * @param buyItems
	 */
	private void insertOrderItem(List<BuyItem> buyItems) {

		for (BuyItem buyItem : buyItems) {
			try {
				orderMapper.insertOrderItem(buyItem);

				orderGiftItemService
						.insertOrderGiftItemByByItemId(buyItem.getOrderCode(), buyItem.getOrderSequence(), buyItem.getItemSequence(),
								buyItem.getItemId());
			} catch (Exception e) {
				String code = buyItem.getOrderCode() + "-" + buyItem.getOrderSequence() + "-" + buyItem.getItemSequence();
				log.error("주문상품 등록 에러 : {}", e.getMessage(), e);
				throw new OrderException("주문 상품 등록시 오류가 발생했습니다. : " + code);
			}
		}
	}

	/**
	 * 주문 목록에 사은품 추가
	 * @param orderList
	 */
	private void setOrderGiftItemForOrderList(List<OrderList> orderList) {

		if (orderList != null && !orderList.isEmpty()) {

			HashSet<String> orderCodeSet = new HashSet<>();

			orderList.stream().forEach(order -> orderCodeSet.add(order.getOrderCode()));

			String[] orderCodes = orderCodeSet.toArray(new String[orderCodeSet.size()]);

			List<OrderGiftItem> orderGiftItems = orderGiftItemService.getOrderGiftItemListByOrderCodes(orderCodes);

			for (OrderList order : orderList) {
				setOrderGiftItemForOrderItem(orderGiftItems, order);
			}
		}

	}

	@Override
	public void setOrderGiftItemForOrderItem(List<OrderGiftItem> orderGiftItems, OrderItem orderItem) {

		String orderCode = orderItem.getOrderCode();
		int orderSequence = orderItem.getOrderSequence();
		int itemSequence = orderItem.getItemSequence();

		List<OrderGiftItem> giftItems = orderGiftItems.stream()
				.filter(
						giftItem -> orderCode.equals(giftItem.getOrderCode())
								&& orderSequence == giftItem.getOrderSequence()
								&& itemSequence == giftItem.getItemSequence()
				).collect(Collectors.toList());

		orderItem.setOrderGiftItemList(giftItems);

	}

	@Override
	public void setOrderGiftItemForOrderItem(OrderItem orderItem) {

		if (orderItem != null) {
			List<OrderGiftItem> orderGiftItemList = orderGiftItemService.getOrderGiftItemListByOrderCode(orderItem.getOrderCode());
			setOrderGiftItemForOrderItem(orderGiftItemList, orderItem);
		}

	}

	@Override
	public OrderPgData getOrderPgDataByOrderCode(String orderCode) {
		return orderMapper.getOrderPgDataByOrderCode(orderCode);
	}

	@Override
	public List<ApiOrderList> getApiOrderList(OrderParam orderParam) {
		List<ApiOrderList> resultList = new ArrayList<>();

		int totalCount = orderMapper.getOrderCountByParam(orderParam);

		Pagination pagination = Pagination.getInstance(totalCount, orderParam.getItemsPerPage());

		ShopUtils.setPaginationInfo(pagination, orderParam.getConditionType(), orderParam.getPage());

		orderParam.setPagination(pagination);

		List<Order> list = orderMapper.getOrderListByParam(orderParam);

		if (list == null) {
			return null;
		}


		for(int i = 0; i < list.size(); i++){

			bindOrder(list.get(i), orderParam);

			ApiOrderList order = new ApiOrderList();
			List<OrderItem> orderItems = list.get(i).getOrderShippingInfos().get(0).getOrderItems();
			List<ItemInfo> items = new ArrayList<>();
			order.setOrderCode(list.get(i).getOrderCode());
			order.setOrderSequence(list.get(i).getOrderSequence());
			order.setCreatedDate(list.get(i).getCreatedDate());


			for(int j = 0; j < orderItems.size(); j++){
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setItemUserCode(orderItems.get(j).getItemUserCode());
				itemInfo.setImageSrc(ShopUtils.loadImage(orderItems.get(j).getImageSrc(), "M"));
				itemInfo.setItemName(orderItems.get(j).getItemName());
				if(orderItems.get(j).getOptions() != null){
					itemInfo.setOptions(ShopUtils.buyViewOptionText(orderItems.get(j).getOptions()).replace("<br/>",""));
					itemInfo.setOptionString(orderItems.get(j).getOptions());
				}

				itemInfo.setItemReturnFlag(orderItems.get(j).getItemReturnFlag());
				itemInfo.setItemId(orderItems.get(j).getItemId());
				itemInfo.setQuantity(orderItems.get(j).getQuantity());
				itemInfo.setItemAmount(orderItems.get(j).getItemAmount());
				itemInfo.setOrderStatus(orderItems.get(j).getOrderStatus());
				itemInfo.setOrderStatusLabel(orderItems.get(j).getOrderStatusLabel());
				itemInfo.setClaimRefusalReasonText(orderItems.get(j).getClaimRefusalReasonText());

				itemInfo.setItemSequence(orderItems.get(j).getItemSequence());
				itemInfo.setShippingSequence(orderItems.get(j).getShippingSequence());

				itemInfo.setBrand(orderItems.get(j).getBrand());

				itemInfo.setDeliveryCompanyId(orderItems.get(j).getDeliveryCompanyId());
				itemInfo.setDeliveryCompanyName(orderItems.get(j).getDeliveryCompanyName());
				itemInfo.setDeliveryCompanyUrl(orderItems.get(j).getDeliveryCompanyUrl());
				itemInfo.setDeliveryNumber(orderItems.get(j).getDeliveryNumber());

				itemInfo.setFreeGiftName(saleson.common.utils.CommonUtils.dataNvl(orderItems.get(j).getFreeGiftName()));
				itemInfo.setFreeGiftItemList(ShopUtils.conventOrderGiftItemInfoList(orderItems.get(j).getOrderGiftItemList()));
				itemInfo.setFreeGiftItemText(ShopUtils.makeOrderGiftItemText(orderItems.get(j).getOrderGiftItemList()));

				items.add(itemInfo);
			}
			order.setItems(items);
			resultList.add(order);
		}
		return resultList;
	}

	@Override
	public OrderDetail getApiOrderDetail(OrderParam orderParam) {
		OrderDetail OrderDetail = null;
		Order order = orderMapper.getOrderByParam(orderParam);
		if (order == null) {
			return null;
		}

		/* 교환, 반품 거절 사유 상품별로 불러오기 */
		for (OrderShippingInfo shippingInfos : order.getOrderShippingInfos()) {
			for (OrderItem orderItem : shippingInfos.getOrderItems()) {
				if("59".equals(orderItem.getOrderStatus())) { //교환 거절
					orderItem.setClaimRefusalReasonText(orderClaimApplyMapper.getClaimRefusalReasonText(orderItem));
				} else if ("69".equals(orderItem.getOrderStatus())) {
					orderItem.setClaimRefusalReasonText(orderClaimApplyMapper.getClaimRefusalReasonText(orderItem));
				}
			}
		}
		bindOrder(order, orderParam);
		OrderDetail = orderDetailDataSet(order);
		return OrderDetail;
	}

	private OrderDetail orderDetailDataSet(Order order){
		OrderDetail orderDetail = new OrderDetail();
		ShippingInfo shippingInfo = new ShippingInfo();
		List<PaymentInfo> paymentList = new ArrayList<>();

		List<ItemInfo> item = new ArrayList<>();

		// 주문정보
		orderDetail.setOrderCode(order.getOrderCode());                     // 주문코드
		orderDetail.setCreatedDate(order.getCreatedDate());                   // 주문일자

		orderDetail.setTotalItemAmount(order.getTotalItemAmount());               // 상품금액
		orderDetail.setTotalShippingAmount(order.getTotalShippingAmount());           // 배송비
		orderDetail.setTotalDiscountAmount(order.getTotalDiscountAmount());           // 할인금액
		orderDetail.setTotalCouponDiscountAmount(order.getTotalCouponDiscountAmount());     // 쿠폰할인금액
		orderDetail.setTotalUserLevelDiscountAmount(order.getTotalUserLevelDiscountAmount());  // 회원할인금액
		orderDetail.setTotalOrderAmount(order.getTotalOrderAmount());              // 총 결제금액

		OrderShippingInfo orderShippingInfo = order.getOrderShippingInfos().get(0);
		OrderItem orderItem = orderShippingInfo.getOrderItems().get(0);
		OrderPayment orderPayment = order.getOrderPayments().get(0);

		// 배송정보
		shippingInfo.setReceiveName(orderShippingInfo.getReceiveName());     // 받으시는분
		shippingInfo.setReceiveMobile(orderShippingInfo.getReceiveMobile());   // 휴대폰번호
		shippingInfo.setReceivePhone(orderShippingInfo.getReceivePhone());    // 전화번호
		shippingInfo.setMemo(orderShippingInfo.getMemo());            // 배송시 요구사항
		shippingInfo.setReceiveNewZipcode(orderShippingInfo.getReceiveNewZipcode());// 신주소 우편번호
		shippingInfo.setReceiveZipcode(orderShippingInfo.getReceiveZipcode());  // 구주소 우편번호
		shippingInfo.setReceiveAddress(orderShippingInfo.getReceiveAddress());  // 주소
		shippingInfo.setReceiveAddressDetail(orderShippingInfo.getReceiveAddressDetail());// 상세주소
		shippingInfo.setPayShipping(orderItem.getOrderShipping().getPayShipping());     // 배송비(0원 일 시 무료)

		List<OrderItem> orderItems = order.getOrderShippingInfos().get(0).getOrderItems();
		for(int i = 0; i < orderItems.size(); i++){
			ItemInfo itemInfo = new ItemInfo();
			itemInfo.setItemId(orderItems.get(i).getItemId());
			itemInfo.setItemUserCode(orderItems.get(i).getItemUserCode());
			itemInfo.setImageSrc(ShopUtils.loadImage(orderItems.get(i).getImageSrc(), "M"));

			itemInfo.setItemName(orderItems.get(i).getItemName());

			if(orderItems.get(i).getOptions() != null){
				itemInfo.setOptions(ShopUtils.buyViewOptionText(orderItems.get(i).getOptions()).replace("<br/>",""));
				itemInfo.setOptionString(orderItems.get(i).getOptions());
			}

			itemInfo.setItemReturnFlag(orderItems.get(i).getItemReturnFlag());

			itemInfo.setItemSequence(orderItems.get(i).getItemSequence());
			itemInfo.setShippingSequence(orderItems.get(i).getShippingSequence());

			itemInfo.setEarnPoint(orderItems.get(i).getEarnPoint());
			itemInfo.setEarnPointFlag(orderItems.get(i).getEarnPointFlag());

			itemInfo.setQuantity(orderItems.get(i).getQuantity());
			itemInfo.setItemAmount(orderItems.get(i).getItemAmount());
			itemInfo.setOrderStatus(orderItems.get(i).getOrderStatus());
			itemInfo.setOrderStatusLabel(orderItems.get(i).getOrderStatusLabel());
			itemInfo.setClaimRefusalReasonText(orderItems.get(i).getClaimRefusalReasonText());

			itemInfo.setDeliveryCompanyId(orderItems.get(i).getDeliveryCompanyId());
			itemInfo.setDeliveryCompanyName(orderItems.get(i).getDeliveryCompanyName());
			itemInfo.setDeliveryCompanyUrl(orderItems.get(i).getDeliveryCompanyUrl());
			itemInfo.setDeliveryNumber(orderItems.get(i).getDeliveryNumber());

			itemInfo.setRealShipping(orderItems.get(i).getOrderShipping().getRealShipping());

			itemInfo.setFreeGiftName(saleson.common.utils.CommonUtils.dataNvl(orderItems.get(i).getFreeGiftName()));
			itemInfo.setFreeGiftItemList(ShopUtils.conventOrderGiftItemInfoList(orderItems.get(i).getOrderGiftItemList()));
			itemInfo.setFreeGiftItemText(ShopUtils.makeOrderGiftItemText(orderItems.get(i).getOrderGiftItemList()));

			item.add(itemInfo);
		}

		List<OrderPayment> opList = order.getOrderPayments();
		if(!opList.isEmpty()){
			for(int i = 0; i < opList.size(); i++){
				PaymentInfo paymentInfo = new PaymentInfo();
				paymentInfo.setApprovalType(opList.get(i).getApprovalType());     // 결제수단
				paymentInfo.setApprovalTypeLabel(opList.get(i).getApprovalTypeLabel());
				paymentInfo.setPaymentType(opList.get(i).getPaymentType());      // 결제구분 (1:결제, 2:취소)
				paymentInfo.setAmount(opList.get(i).getAmount());           // 금액
				paymentInfo.setRemainingAmount(opList.get(i).getRemainingAmount());  // 잔여액
				paymentInfo.setCancelAmount(opList.get(i).getCancelAmount());     // 취소금액
				paymentInfo.setPayDate(opList.get(i).getPayDate());          // 결제일
				paymentInfo.setBankVirtualNo(opList.get(i).getBankVirtualNo());    // 계좌번호
				paymentInfo.setBankInName(opList.get(i).getBankInName());       // 입금예정자
				paymentInfo.setBankDate(opList.get(i).getBankDate());       // 입금예정일(만료일)
				paymentInfo.setPayInfo(opList.get(i).getPayInfo());			// 결제정보
				paymentList.add(paymentInfo);
			}
		}
		orderDetail.setShippingInfo(shippingInfo);
		orderDetail.setPaymentList(paymentList);
		orderDetail.setItem(item);

		return orderDetail;
	}

	@Override
	public OrderDetail getApiBuyForStep1(OrderParam orderParam) {
		OrderDetail result = null;
		Buy buy = this.getOrderTemp(orderParam);
		UserDelivery defaultUserDelivery = userDeliveryService.getDefaultUserDelivery();
		if (buy == null) {

			buy = new Buy();
			Buyer buyer = new Buyer();
			Receiver receiver = new Receiver();

			boolean isDeliverySet = false;
			if (defaultUserDelivery != null) {
				isDeliverySet = true;
//				receiver.setReceiveCompanyName(defaultUserDelivery.getCompanyName());
				receiver.setReceiveName(defaultUserDelivery.getUserName());
				receiver.setReceiveMobile(defaultUserDelivery.getMobile());
				receiver.setReceivePhone(defaultUserDelivery.getPhone());
				receiver.setReceiveNewZipcode(defaultUserDelivery.getNewZipcode());
				receiver.setReceiveZipcode(defaultUserDelivery.getZipcode());
				receiver.setReceiveAddress(defaultUserDelivery.getAddress());
				receiver.setReceiveAddressDetail(defaultUserDelivery.getAddressDetail());
				receiver.setReceiveSido(defaultUserDelivery.getSido());
				receiver.setReceiveSigungu(defaultUserDelivery.getSigungu());
				receiver.setReceiveEupmyeondong(defaultUserDelivery.getEupmyeondong());
			}

			// 회원 로그인 상태일경우 회원 정보를 가져옴..
			if (UserUtils.isUserLogin()) {

				User user = UserUtils.getUser();
				UserDetail userDetail = userService.getUserDetail(user.getUserId());
				//UserDetail userDetail = (UserDetail) user.getUserDetail();

				buyer.setUserName(user.getUserName());
				if (isDeliverySet == false) {
					receiver.setReceiveName(user.getUserName());
				}

				buyer.setEmail(user.getEmail());
				if (userDetail != null) {
//					buyer.setCompanyName(userDetail.getCompanyName());

					buyer.setMobile(userDetail.getPhoneNumber());
					buyer.setPhone(userDetail.getTelNumber());
					buyer.setNewZipcode(userDetail.getNewPost());
					buyer.setZipcode(userDetail.getPost());
					buyer.setZipcode1(userDetail.getPost1());
					buyer.setZipcode2(userDetail.getPost2());
					buyer.setAddress(userDetail.getAddress());
					buyer.setAddressDetail(userDetail.getAddressDetail());

					buyer.setSido(ShopUtils.getSido(userDetail.getAddress()));
					buyer.setSigungu(ShopUtils.getSigungu(userDetail.getAddress()));
					buyer.setEupmyeondong(ShopUtils.getEupmyeondong(userDetail.getAddress()));

					if (isDeliverySet == false) {
						//receiver.setReceiveCompanyName(userDetail.getCompanyName());
						receiver.setReceiveMobile(userDetail.getPhoneNumber());
						receiver.setReceivePhone(userDetail.getTelNumber());
						receiver.setReceiveNewZipcode(userDetail.getNewPost());
						receiver.setReceiveZipcode(userDetail.getPost());
						receiver.setReceiveZipcode1(userDetail.getPost1());
						receiver.setReceiveZipcode2(userDetail.getPost2());
						receiver.setReceiveAddress(userDetail.getAddress());

						receiver.setReceiveSido(ShopUtils.getSido(userDetail.getAddress()));
						receiver.setReceiveSigungu(ShopUtils.getSigungu(userDetail.getAddress()));
						receiver.setReceiveEupmyeondong(ShopUtils.getEupmyeondong(userDetail.getAddress()));

						receiver.setReceiveAddressDetail(userDetail.getAddressDetail());
					}
				}


			}

			buy.setUserId(orderParam.getUserId());
			buy.setSessionId(orderParam.getSessionId());
			buy.setBuyer(buyer);

			// step1에 들어올때는 배송지 정보가 1개임
			List<Receiver> receivers = new ArrayList<>();
			receiver.setShippingIndex(0);
			receivers.add(receiver);
			buy.setReceivers(receivers);
		}

		List<BuyItem> items = this.getOrderItemTempList(orderParam);
		if (items == null) {
			throw new OrderException("결제가능 상품이 없습니다.", "/cart");
		}

		// CJH 추가 구성품이 있는경우 복수배송지 선택 불가
		boolean isAdditionItem = false;
		for(BuyItem item : items) {
			if ("Y".equals(item.getAdditionItemFlag())) {
				isAdditionItem = true;
			}
		}

		buy.setAdditionItem(isAdditionItem);


		HashMap<Long, String> sellerMap = new HashMap<>();
		for(Receiver receiver : buy.getReceivers()) {

			receiver.setItems(items);

			// 상품쿠폰 적용
			receiver.itemCouponUsed(false, buy, receiver.getShippingIndex());

			// 배송지별 구매상품 초기화
			List<BuyQuantity> buyQuantitys = new ArrayList<>();
			for(BuyItem buyItem : items) {
				BuyQuantity buyQuantity = new BuyQuantity();
				buyQuantity.setItemSequence(buyItem.getItemSequence());
				buyQuantity.setQuantity(buyItem.getItemPrice().getQuantity());
				buyQuantitys.add(buyQuantity);

				// 개인정보 제 3자 제공 및 수집 이용동의
				if ("2".equals(buyItem.getItem().getShippingType())) {
					Seller seller = sellerMapper.getSellerById(buyItem.getSellerId());
					if (seller != null) {
						sellerMap.put(seller.getSellerId(), seller.getSellerName());
					}
				}
			}

			receiver.setBuyQuantitys(buyQuantitys);

			String islandType = "";
			if (StringUtils.isEmpty(receiver.getReceiveZipcode()) == false) {
				islandType = orderMapper.getIslandTypeByZipcode(receiver.getReceiveZipcode());
			}

			receiver.setShipping(islandType);
		}

		String sellerNames = "";
		for(Long sellerId : sellerMap.keySet()) {
			sellerNames += (StringUtils.isEmpty(sellerNames) ? "" : ", ") + sellerMap.get(sellerId);
		}
		buy.setSellerNames(sellerNames);

		// 결제금액 재계산
		buy.setOrderPrice(0, configService.getShopConfig(Config.SHOP_CONFIG_ID));

		// 회원 사용가능 포인트 조회
		buy.setRetentionPoint(this.getRetentionPoint(orderParam.getUserId(), 0));

		// 회원 사용가능 배송비 할인 쿠폰
		buy.setShippingCoupon(this.getShippingCoupon(orderParam.getUserId(), 0));

		// 회원 기본배송지 정보 저장
		buy.setDefaultUserDelivery(defaultUserDelivery);

		result = orderPaymentInfoSet(buy);
		return result;
	}

	private OrderDetail orderPaymentInfoSet(Buy buy){
		OrderDetail orderDetail = new OrderDetail();
		List<ItemInfo> items = new ArrayList<>();       // 상품정보
		ShippingInfo shippingInfo = new ShippingInfo(); // 배송정보
		PaymentInfo paymentInfo = new PaymentInfo();    // 결제정보
		List<BuyShippingCouponInfo> shippingCouponList = new ArrayList<>(); // 배송비쿠폰 리스트
		Receiver receiver = buy.getReceivers().get(0);

		orderDetail.setOrderCode("");
		orderDetail.setCreatedDate("");
		orderDetail.setUserName(buy.getBuyer().getUserName()); // 주문자
		orderDetail.setMobile(buy.getBuyer().getMobile()); // 연락처

		// 결제금액정보
		orderDetail.setOrderPrice(buy.getOrderPrice());
		orderDetail.setBuyQuantitys(receiver.getBuyQuantitys());

		// singleShipping == true 면 shipping.buyItem / 아니면 shipping.buyItems
		// 개별배송상품여부 isSingleShipping

		// 상품정보
		if(receiver.getItemGroups().size() > 0){
			for(int i = 0; i < receiver.getItemGroups().size(); i++){
				BuyItem buyItem = null;
				int realShipping = receiver.getItemGroups().get(i).getRealShipping();
				if(receiver.getItemGroups().get(i).getBuyItems() != null){
					List<BuyItem> lbiList = receiver.getItemGroups().get(i).getBuyItems();
					for(int j = 0; j < lbiList.size(); j++){
						buyItem = lbiList.get(j);

						Shipping sh = receiver.getItemGroups().get(i);
						ItemInfo item = new ItemInfo();

						item.setItemName(buyItem.getItemName());      // 상품명
						item.setItemUserCode(buyItem.getItemUserCode());  // 상품코드
						item.setItemId(buyItem.getItemId());        // 상품아이디
						if(buyItem.getOptions() != null){
							item.setOptions(ShopUtils.buyViewOptionText(buyItem.getOptions()).replace("<br/>","")); // 상품옵션
						}

						if(buyItem.getOptionList() != null && buyItem.getOptionList().size() > 0){
							// 상품옵션 ID
							item.setOptionId(buyItem.getOptionList().get(0).getItemOptionId());
						}
						item.setImageSrc(ShopUtils.loadImage(buyItem.getItemUserCode(), buyItem.getItem().getItemImage(), "M"));  // 상품이미지
						item.setQuantity(buyItem.getItemPrice().getQuantity());
						item.setItemAmount(buyItem.getItemPrice().getSumPrice());
						item.setOrderStatus("");
						item.setClaimRefusalReasonText("");

						item.setOrderSequence(buyItem.getOrderSequence());
						item.setItemSequence(buyItem.getItemSequence());
						item.setShippingSequence(buyItem.getShippingSequence());
						item.setShippingInfoSequence(buyItem.getShippingInfoSequence());

						/*item.setUseIslandFlag(buyItem.getItem().getUseIslandFlag());*/

						item.setShippingItemCount(sh.getShippingItemCount());	// 박스당 제한수량
						item.setShippingFreeAmount(sh.getShippingFreeAmount()); // 조건부 무료배송금액
						item.setShippingExtraCharge1(sh.getShippingExtraCharge1()); // 제주도 추가 배송비
						item.setShippingExtraCharge2(sh.getShippingExtraCharge2()); //도서산간 추가배송비
						item.setShipping(sh.getShipping()); // 배송비
						item.setShippingType(sh.getShippingType()); // 배송비구분(1: 무료배송, 2: 판매자조건부, 3:출고지조건부, 4:상품조건부, 5:개당배송비, 6:고정배송비)

						item.setBaseAmountForShipping(buyItem.getItemPrice().getBaseAmountForShipping());

						item.setRealShipping(realShipping);
						item.setShippingGroupCode(receiver.getItemGroups().get(i).getShippingGroupCode());
						item.setSingleShipping(receiver.getItemGroups().get(i).isSingleShipping());

						// 사은품
						item.setFreeGiftName(buyItem.getFreeGiftName());
						item.setFreeGiftItemList(buyItem.getFreeGiftItemList());
						item.setFreeGiftItemText(buyItem.getFreeGiftItemText());

						items.add(item);

						BuyShippingCouponInfo couponInfo = new BuyShippingCouponInfo();
						couponInfo.setShippingGroupCode(receiver.getItemGroups().get(i).getShippingGroupCode());
						couponInfo.setShippingPaymentType(receiver.getItemGroups().get(i).getShippingPaymentType());
						couponInfo.setShippingSequence(receiver.getItemGroups().get(i).getShippingSequence());
						couponInfo.setShippingType(receiver.getItemGroups().get(i).getShippingType());
						couponInfo.setItemId(buyItem.getItemId());
						couponInfo.setOptionId(item.getOptionId());
						shippingCouponList.add(couponInfo);
					}
				} else {
					buyItem = receiver.getItemGroups().get(i).getBuyItem();

					Shipping sh = receiver.getItemGroups().get(i);
					ItemInfo item = new ItemInfo();

					item.setItemName(buyItem.getItemName());      // 상품명
					item.setItemUserCode(buyItem.getItemUserCode());  // 상품코드
					item.setItemId(buyItem.getItemId());        // 상품아이디
					if(buyItem.getOptions() != null){
						item.setOptions(ShopUtils.buyViewOptionText(buyItem.getOptions()).replace("<br/>","")); // 상품옵션
					}
					if(buyItem.getOptionList() != null && buyItem.getOptionList().size() > 0){
						// 상품옵션 ID
						item.setOptionId(buyItem.getOptionList().get(0).getItemOptionId());
					}
					item.setImageSrc(ShopUtils.loadImage(buyItem.getItemUserCode(), buyItem.getItem().getItemImage(), "M"));  // 상품이미지
					item.setQuantity(buyItem.getItemPrice().getQuantity());
					item.setItemAmount(buyItem.getItemPrice().getSumPrice());
					item.setOrderStatus("");
					item.setClaimRefusalReasonText("");

					item.setOrderSequence(buyItem.getOrderSequence());
					item.setItemSequence(buyItem.getItemSequence());
					item.setShippingSequence(buyItem.getShippingSequence());
					item.setShippingInfoSequence(buyItem.getShippingInfoSequence());

					/*item.setUseIslandFlag(buyItem.getItem().getUseIslandFlag());*/

					item.setShippingItemCount(sh.getShippingItemCount());	// 박스당 제한수량
					item.setShippingFreeAmount(sh.getShippingFreeAmount()); // 조건부 무료배송금액
					item.setShippingExtraCharge1(sh.getShippingExtraCharge1()); // 제주도 추가 배송비
					item.setShippingExtraCharge2(sh.getShippingExtraCharge2()); //도서산간 추가배송비
					item.setShipping(sh.getShipping()); // 배송비
					item.setShippingType(sh.getShippingType()); // 배송비구분(1: 무료배송, 2: 판매자조건부, 3:출고지조건부, 4:상품조건부, 5:개당배송비, 6:고정배송비)

					item.setBaseAmountForShipping(buyItem.getItemPrice().getBaseAmountForShipping());

					item.setRealShipping(realShipping);
					item.setShippingGroupCode(receiver.getItemGroups().get(i).getShippingGroupCode());
					item.setSingleShipping(receiver.getItemGroups().get(i).isSingleShipping());

					// 사은품
					item.setFreeGiftName(buyItem.getFreeGiftName());
					item.setFreeGiftItemList(buyItem.getFreeGiftItemList());
					item.setFreeGiftItemText(buyItem.getFreeGiftItemText());

					items.add(item);

					// 배송비 쿠폰 세팅
					BuyShippingCouponInfo couponInfo = new BuyShippingCouponInfo();
					couponInfo.setShippingGroupCode(receiver.getItemGroups().get(i).getShippingGroupCode());
					couponInfo.setShippingPaymentType(receiver.getItemGroups().get(i).getShippingPaymentType());
					couponInfo.setShippingSequence(receiver.getItemGroups().get(i).getShippingSequence());
					couponInfo.setShippingType(receiver.getItemGroups().get(i).getShippingType());
					couponInfo.setItemId(buyItem.getItemId());
					couponInfo.setOptionId(item.getOptionId());
					shippingCouponList.add(couponInfo);
				}
			}
		}

		/*
			sh.getShippingFreeAmount(); // 조건부 무료배송금액
			sh.getShippingExtraCharge1();// 제주도 추가 배송비
			sh.getShippingExtraCharge2();//도서산간 추가배송비
			sh.getShipping();   // 배송비
			sh.getShippingType();   // 배송비구분(1: 무료배송, 2: 판매자조건부, 3:출고지조건부, 4:상품조건부, 5:개당배송비, 6:고정배송비)
		*/
/*

		// 배송정보
		shippingInfo.setReceiveName(JJgUtils.dataNvl(receiver.getReceiveName())); // 배송받는사람
		shippingInfo.setReceiveSido(JJgUtils.dataNvl(receiver.getReceiveSido())); // 시도
		shippingInfo.setReceiveSigungu(JJgUtils.dataNvl(receiver.getReceiveSigungu())); // 시군구
		shippingInfo.setReceiveEupmyeondong(JJgUtils.dataNvl(receiver.getReceiveEupmyeondong())); // 읍면동
		shippingInfo.setReceiveZipcode(JJgUtils.dataNvl(receiver.getReceiveZipcode())); // 구 주소 우편번호
		shippingInfo.setReceiveNewZipcode(JJgUtils.dataNvl(receiver.getReceiveNewZipcode())); // 신 주소 우편번호
		shippingInfo.setReceiveAddress(JJgUtils.dataNvl(receiver.getReceiveAddress())); // 주소
		shippingInfo.setReceiveAddressDetail(JJgUtils.dataNvl(receiver.getReceiveAddressDetail())); // 상세주소
		shippingInfo.setReceiveMobile1(JJgUtils.dataNvl(receiver.getReceiveMobile1()));   // 휴대폰 번호 1
		shippingInfo.setReceiveMobile2(JJgUtils.dataNvl(receiver.getReceiveMobile2()));   // 휴대폰 번호 2
		shippingInfo.setReceiveMobile3(JJgUtils.dataNvl(receiver.getReceiveMobile3()));   // 휴대폰 번호 3
		shippingInfo.setMemo(JJgUtils.dataNvl(receiver.getContent())); // 배송시 요구사항

		if(!(shippingInfo.getReceiveMobile1().equals("") && shippingInfo.getReceiveMobile2().equals("") && shippingInfo.getReceiveMobile3().equals(""))){
			shippingInfo.setReceiveMobile(shippingInfo.getReceiveMobile1()+"-"+shippingInfo.getReceiveMobile2()+"-"+shippingInfo.getReceiveMobile3());
		} else {
			shippingInfo.setReceiveMobile("");
		}
*/

		shippingInfo.setReceivePhone("");

		// 할인정보
		orderDetail.setRetentionPoint(buy.getRetentionPoint()); // 보유포인트(적립금)

		// 토탈
/*
- totalItemSaleAmount : 34,700원 : 총 상품 할인 금액
- totalShippingAmount : 2,500원 : 총 배송비
- orderPayAmount : 37,200원 : 총 결제 예정 금액
- payAmount : 37,200원 : 지불 금액 (총 결제 예정 금액 동일)
- orderPayAmountTotal : 37,200원 :총 주문 금액
* */
		// 상품금액
		orderDetail.setTotalItemAmount(buy.getOrderPrice().getTotalItemPrice());
		// 할인금액
		orderDetail.setTotalDiscountAmount(buy.getOrderPrice().getTotalDiscountAmount());
		// 배송비
		orderDetail.setTotalShippingAmount(buy.getOrderPrice().getTotalShippingAmount());
		// 결제예정금액
		orderDetail.setTotalOrderAmount(buy.getOrderPrice().getOrderPayAmount());

		buy.getOrderPrice().getTotalItemCouponDiscountAmount();
		buy.getOrderPrice().getTotalCartCouponDiscountAmount();
		buy.getOrderPrice().getTotalCouponDiscountAmount();
		buy.getOrderPrice().getTotalPointDiscountAmount();
		buy.getOrderPrice().getTotalShippingCouponUseCount();
		buy.getOrderPrice().getTotalShippingCouponDiscountAmount();
		buy.getOrderPrice().getTotalItemSaleAmount();
		buy.getOrderPrice().getTotalShippingAmount();
		buy.getOrderPrice().getOrderPayAmount();
		buy.getOrderPrice().getOrderPayAmountTotal();
		buy.getOrderPrice().getPayAmount();
		buy.getOrderPrice().getTotalUserLevelDiscountAmount();

		// 결제정보
		orderDetail.setItem(items);
		orderDetail.setShippingInfo(shippingInfo);

		// 결제정보 임시 초기화
		paymentInfo.setApprovalType("");
		paymentInfo.setPaymentType("");
		paymentInfo.setAmount(0);
		paymentInfo.setRemainingAmount(0);
		paymentInfo.setCancelAmount(0);
		paymentInfo.setPayDate("");
		paymentInfo.setBankVirtualNo("");
		paymentInfo.setBankInName("");

		orderDetail.setShippingIndex(buy.getReceivers().get(0).getShippingIndex());

		// 적용가능 쿠폰 리스트 설정
		orderDetail.setCouponList(apiOrderCouponData(getOrderCouponData(buy)));

		// 배송지 쿠폰
		orderDetail.setShippingCoupon(buy.getShippingCoupon());
		orderDetail.setShippingCouponList(shippingCouponList);

		orderDetail.setPaymentInfo(paymentInfo);

		return orderDetail;
	}

	private List<BuyCouponList> apiOrderCouponData(Buy buy){
		List<BuyCouponList> resultList = new ArrayList<>();

		if (buy == null) {
			return resultList;
		}

		List<OrderCoupon> couponList = new ArrayList<>();
		// buy.getReceivers().get(0).getItemGroups().get(0).getBuyItem().getItemCoupons()
		Receiver receiver = buy.getReceivers().get(0);
		if(receiver.getItemGroups().size() > 0){
			boolean singleShipping = false;
			for(int i = 0; i < receiver.getItemGroups().size(); i++){
				BuyCouponList bcListInfo = null;
				singleShipping = receiver.getItemGroups().get(i).isSingleShipping();
				BuyItem bi = null;
				if(singleShipping){
					bcListInfo = new BuyCouponList();
					couponList = new ArrayList<>();
					bi = receiver.getItemGroups().get(i).getBuyItem();
					if(bi.getItemCoupons() != null && bi.getItemCoupons().size() > 0){
						for(int ij = 0; ij < bi.getItemCoupons().size(); ij++){
							couponList.add(bi.getItemCoupons().get(ij));
						}
					}
					bcListInfo.setItemId(bi.getItemId());
					if(bi.getOptionList() != null){
						bcListInfo.setItemOptionId(bi.getOptionList().get(0).getItemOptionId());
					} else {
						bcListInfo.setItemOptionId(0);
					}
					bcListInfo.setCouponList(couponList);
					resultList.add(bcListInfo);
				} else {
					List<BuyItem> jjbi = receiver.getItemGroups().get(i).getBuyItems();
					for(int j = 0; j < jjbi.size(); j++){
						bcListInfo = new BuyCouponList();
						couponList = new ArrayList<>();
						bi = jjbi.get(j);
						if(bi.getItemCoupons() != null && bi.getItemCoupons().size() > 0){
							for(int ij = 0; ij < bi.getItemCoupons().size(); ij++){
								couponList.add(bi.getItemCoupons().get(ij));
							}
						}
						bcListInfo.setItemId(bi.getItemId());
						if(bi.getOptionList() != null){
							bcListInfo.setItemOptionId(bi.getOptionList().get(0).getItemOptionId());
						} else {
							bcListInfo.setItemOptionId(0);
						}

						bcListInfo.setCouponList(couponList);
						resultList.add(bcListInfo);
					}
				}
			}
		}
		return resultList;
	}
	@Override
	public ReturnApplyInfo getReturnApplyInfo(OrderParam orderParam) {

		ReturnApplyInfo info = new ReturnApplyInfo();
		ReturnApply returnApply = new ReturnApply();
		OrderItem orderItem = getOrderItemByParam(orderParam);

		List<OrderPayment> list = orderMapper.getOrderPaymentListByParam(orderParam);

		boolean paymentType = list.stream().anyMatch(
				type -> type.getApprovalType().equals("bank")
						|| type.getApprovalType().equals("vbank"));

		info.setPaymentType(paymentType);

		if (orderItem == null) {
			throw new PageNotFoundException();
		}
		if (orderItem != null) {
			List<OrderGiftItem> orderGiftItemList = orderGiftItemService.getOrderGiftItemListByOrderCode(orderItem.getOrderCode());
			setOrderGiftItemForOrderItem(orderGiftItemList, orderItem);
		}
		if (!ShopUtils.checkOrderStatusChange("return", orderItem.getOrderStatus())) {
			throw new OrderException("상품을 환불 신청 하실수 있는 상태가 아닙니다.");
		}
		orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());
		OrderShippingInfo osInfo = getOrderShippingInfoByParam(orderParam);
		returnApply.setReturnApply(osInfo);
		returnApply.setOrderItem(orderItem);
		returnApply.setApplyQuantity(orderItem.getQuantity());
		returnApply.setShipmentReturnId(orderItem.getShipmentReturnId());

		if(!StringUtils.isEmpty(returnApply.getReturnReservePhone()) && !returnApply.getReturnReservePhone().equals("--")) {
			returnApply.setReturnReservePhone1(returnApply.getReturnReservePhone().split("-")[0]);
		}

		if(!StringUtils.isEmpty(returnApply.getReturnReserveMobile()) && !returnApply.getReturnReserveMobile().equals("--")) {
			returnApply.setReturnReserveMobile1(returnApply.getReturnReserveMobile().split("-")[0]);
		}

		info.setDeliveryCompanyList(deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
		info.setClaimReasons(CodeUtils.getCodeInfoList("RETURN_REASON"));
		info.setReturnApply(returnApply);

		return info;
	}

	@Override
	public ExchangeApplyInfo getExchangeApplyInfo(OrderParam orderParam) {
		ExchangeApplyInfo applyInfo = new ExchangeApplyInfo();
		ExchangeApply exchangeApply = new ExchangeApply();

		OrderItem orderItem = getOrderItemByParam(orderParam);
		if (orderItem == null) {
			throw new PageNotFoundException();
		}

		if (orderItem != null) {
			List<OrderGiftItem> orderGiftItemList = orderGiftItemService.getOrderGiftItemListByOrderCode(orderItem.getOrderCode());
			setOrderGiftItemForOrderItem(orderGiftItemList, orderItem);
		}

		if (!ShopUtils.checkOrderStatusChange("exchange", orderItem.getOrderStatus())) {
			throw new OrderException("상품을 교환 신청 하실수 있는 상태가 아닙니다.");
		}

		orderParam.setShippingInfoSequence(orderItem.getShippingInfoSequence());
		OrderShippingInfo info = getOrderShippingInfoByParam(orderParam);
		exchangeApply.setExchangeApply(info);
		exchangeApply.setOrderItem(orderItem);
		exchangeApply.setApplyQuantity(orderItem.getQuantity());
		exchangeApply.setShipmentReturnId(orderItem.getShipmentReturnId());

		if(!StringUtils.isEmpty(exchangeApply.getExchangeReceivePhone()) && !exchangeApply.getExchangeReceivePhone().equals("--")) {
			exchangeApply.setExchangeReceiveMobile1(exchangeApply.getExchangeReceivePhone().split("-")[0]);
		}

		if(!StringUtils.isEmpty(exchangeApply.getExchangeReceiveMobile()) && !exchangeApply.getExchangeReceiveMobile().equals("--")) {
			exchangeApply.setExchangeReceiveMobile1(exchangeApply.getExchangeReceiveMobile().split("-")[0]);
		}

		applyInfo.setDeliveryCompanyList(deliveryCompanyMapper.getActiveDeliveryCompanyListAll());
		applyInfo.setClaimReasons(CodeUtils.getCodeInfoList("EXCHANGE_REASON"));
		applyInfo.setExchangeApply(exchangeApply);

		return applyInfo;
	}

	@Override
	public CancelApplyInfo getCancelApplyInfo(OrderParam orderParam) {
		CancelApplyInfo cancelApplyInfo = new CancelApplyInfo();
		ClaimApply claimApply = new ClaimApply();

		claimApply.setItemSequence(orderParam.getItemSequence());

		Order order = getOrderByParam(orderParam);
		claimApply.setClaimType("1");
		if (order == null) {
			throw new PageNotFoundException();
		}

		// CJH 2016.11.13 사용자가 클릭하고 들어온 상품의 주문상태와 동일한것만 화면에 노출하는 조건 추가
		String userClickItemStatus = "";
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem orderItem : info.getOrderItems()) {
				if (orderItem.getItemSequence() == claimApply.getItemSequence()) {
					userClickItemStatus = orderItem.getOrderStatus();
					break;
				}
			}

			if (StringUtils.isEmpty(userClickItemStatus) == false) {
				break;
			}
		}

		if (StringUtils.isEmpty(userClickItemStatus)) {
			throw new ClaimException();
		}

		int count = 0;
		for(OrderShippingInfo info : order.getOrderShippingInfos()) {
			for(OrderItem orderItem : info.getOrderItems()) {

				// 신청
				if ("1".equals(claimApply.getClaimType())) {

					// 주문 취소 신청은 배송준비중, 결제 확인일때 가능함
					if (userClickItemStatus.equals(orderItem.getOrderStatus())) {
						orderItem.setClaimApplyFlag("Y");
						count++;

						orderItem.setClaimApplyItemKey(Integer.toString(orderItem.getItemSequence()));
					}

				} else if ("4".equals(claimApply.getClaimType())) { // CJH 2016.11.13 4번은 검증이 되지 않았습니다. 사용하지 말아주세요.

					// 즉시 주문 취소 - 결제 확인 상품만 가능
					if ("0".equals(orderItem.getOrderStatus()) || "10".equals(orderItem.getOrderStatus())) {
						//orderItem.setClaimApplyFlag("Y");
						//count++;

						orderItem.setClaimApplyItemKey(Integer.toString(orderItem.getItemSequence()));
					}
				}

				if (orderItem != null) {
					List<OrderGiftItem> orderGiftItemList = orderGiftItemService.getOrderGiftItemListByOrderCode(orderItem.getOrderCode());
					setOrderGiftItemForOrderItem(orderGiftItemList, orderItem);
				}
			}
		}

		if (count == 0) {
			throw new PageNotFoundException();
		}

		Order claimOrder = new Order();
		claimOrder.setOrderShippingInfos(order.getOrderShippingInfos());
		claimApply.setOrder(claimOrder);

		cancelApplyInfo.setUserClickItemStatus(userClickItemStatus);
		cancelApplyInfo.setClaimReasons(CodeUtils.getCodeInfoList("CANCEL_REASON"));
		cancelApplyInfo.setClaimApply(claimApply);

		return cancelApplyInfo;
	}

	@Override
	public String getMobileByOrderCode(OrderParam orderParam) {
		return orderMapper.getMobileByOrderCode(orderParam);
	}

	@Override
	public int getMaxParentOrderItemSequence(OrderParam orderParam) { return orderMapper.getMaxParentOrderItemSequence(orderParam); }

	@Override
	public void erpOrderStatusBatch() {
		// 0. BatchKey 생성
		String batchKey = DateUtils.getToday("yyyyMMddHHmmss");
		BatchKey batchKey10 = new BatchKey(batchKey, "10");
		BatchKey batchKey20 = new BatchKey(batchKey, "20");
		BatchKey batchKey30 = new BatchKey(batchKey, "30");
		BatchKey batchKey50 = new BatchKey(batchKey, "50");
		BatchKey batchKey55 = new BatchKey(batchKey, "55");


		// 1. 결제 완료 / 배송 준비 중인 order_item 데이터 조회
		List<OrderItem> orderItems = orderMapper.getOrderItemListForErpBatch();

		List<OrderItem> orderedList = orderItems.stream()
				.filter(o -> "10".equals(o.getOrderStatus()))
				.collect(Collectors.toList());

		List<OrderItem> deliveryReadyList = orderItems.stream()
				.filter(o -> "20".equals(o.getOrderStatus()))
				.collect(Collectors.toList());

		List<OrderItem> returnedList = orderItems.stream()
				.filter(o -> "50".equals(o.getOrderStatus()))
				.collect(Collectors.toList());


		// 2. 결제완료(10) -> 데이터 조회 및 업데이트 대상 데이터를 임시 테이블에 저장
		saveErpOrderItems(orderedList, batchKey10);

		// 3. 배송준비중(20) -> 데이터 조회 및 업데이트 대상 데이터를 임시 테이블에 저장
		saveErpOrderItems(deliveryReadyList, batchKey20);

		// 4. 회수완료(50) -> 데이터 조회 및 업데이트 대상 데이터를 임시 테이블에 저장
		saveErpOrderItems(returnedList, batchKey50);


		// 4. Update ORDER_ITEM Status
		orderMapper.updateOrderItemStatusWithErpTempTable(batchKey20);
		orderMapper.updateOrderItemStatusWithErpTempTable(batchKey30);
		orderMapper.updateOrderItemStatusWithErpTempTable(batchKey55);

		// 5. UPDATE OP_ORDER_EXCHANGE_APPLY
		orderClaimApplyMapper.updateOrderExchangeWithErpTempTable(batchKey55);
	}

	private void saveErpOrderItems(List<OrderItem> orderItems, BatchKey batchKey) {
		if (orderItems.isEmpty()) {
			log.debug("[ERP BATCH] ORDER_STATUS({}): 처리할 데이터가 없습니다. ", batchKey.getOrderStatus());
			return;
		}

		// 1. 상태가 변경된 건만 업데이트 대상으로 선별
		List<OrderItem> orderedResult = erpService.findOrderItemAll(orderItems).stream()
				.filter(o -> !batchKey.getOrderStatus().equals(o.getOrderStatus())).collect(Collectors.toList());

		log.debug("[ERP BATCH] ORDER_STATUS({}): request({}), response({})",
				batchKey.getOrderStatus(), orderItems.size(), orderedResult.size());

		if (orderedResult.isEmpty()) {
			return;
		}

		// 2. 상태가 변경된 건을 임시 테이블에 저장
		log.debug("[ERP BATCH] ORDER_STATUS({}): insert ERP_ORDER_ITEM => {}건", batchKey.getOrderStatus(), orderedResult.size());
		batchKey.setOrderItems(orderedResult);
		orderMapper.saveErpOrderItems(batchKey);
	}

	private String getTwoDisitItemSequence(int itemSequence) {
		return itemSequence < 10 ? "0" + itemSequence : "" + itemSequence;
	}
}