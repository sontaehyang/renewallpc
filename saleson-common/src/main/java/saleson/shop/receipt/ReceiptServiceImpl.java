package saleson.shop.receipt;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.enumeration.CashbillIssueType;
import saleson.common.enumeration.CashbillStatus;
import saleson.common.enumeration.CashbillStatusCode;
import saleson.common.enumeration.TaxType;
import saleson.common.utils.UserUtils;
import saleson.model.Cashbill;
import saleson.model.CashbillIssue;
import saleson.model.ConfigPg;
import saleson.shop.order.OrderMapper;
import saleson.shop.order.OrderService;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderPayment;
import saleson.shop.order.pg.PgService;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.order.support.OrderParam;
import saleson.shop.receipt.exception.ReceiptException;
import saleson.shop.receipt.support.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("receiptService")
public class ReceiptServiceImpl implements ReceiptService {
	private static final Logger log = LoggerFactory.getLogger(ReceiptServiceImpl.class);

	@Autowired
    private OrderService orderService;

    @Autowired
    private CashbillRepository cashbillRepository;

	@Autowired
	private CashbillIssueRepository cashbillIssueRepository;

    @Autowired
    private PopbillService popbillService;

    @Autowired
    @Qualifier("inicisService")
    private PgService inicisService;

    @Autowired
    @Qualifier("nicepayService")
    private PgService nicepayService;

    @Autowired
    private OrderMapper orderMapper;

	@Autowired
	Environment environment;

	@Autowired
	private ConfigPgService configPgService;

    @Override
    public Page<Cashbill> getAllCashbill(Predicate predicate, Pageable pageable) {
        return cashbillRepository.findAll(predicate, pageable);
    }

    @Override
    public Page<CashbillIssue> findCashbillIssueAll(Predicate predicate, Pageable pageable) {
        return cashbillIssueRepository.findAll(predicate, pageable);
    }

    @Override
    public Page<Cashbill> findAll(Pageable pageable) {
        return cashbillRepository.findAll(pageable);
    }

    @Override
	public List<Cashbill> findAllByOrderCode(String orderCode) {
		return cashbillRepository.findAllByOrderCode(orderCode);
	}

    @Override
    public List<CashbillIssue> findAllCashbillIssue(Predicate predicate) {
        return cashbillIssueRepository.findAll(predicate);
    }

    /**
     * 현금영수증 사용자 입력정보 저장
     * @param cashbill
     */
    @Override
    public void insertCashbillInfo(Cashbill cashbill) {
        cashbillRepository.save(cashbill);
    }

    @Override
    public void deleteById(Long id) {
        cashbillRepository.deleteById(id);
    }

    @Override
    public List<Cashbill> getCashbill(Cashbill cashbill) {
        return (List<Cashbill>) cashbillRepository.findAll(CashbillPredicate.search(cashbill));
    }

    @Override
    public void updateCashbillForSuccess(Cashbill cashbill) {
//        cashbillRepository.updateCashbillForSuccess(cashbill);
    }

	@Override
	public void issueCashbillByListData(ListParam listParam) {
        List<Long> ids = Arrays.stream(listParam.getId()).map(s -> Long.parseLong(s)).collect(Collectors.toList());

		// id에 해당하는 데이터 일괄 조회 Iterable<Long> iterable
		List<CashbillIssue> cashbills = cashbillIssueRepository.findAllById(ids);

		for (CashbillIssue cashbillIssue : cashbills) {
			log.debug("[POPBILL] 현금영수증 발행 : {}", cashbillIssue);

            // 현금영수증 발행 로직이 들어감..
            CashbillResponse response = receiptIssue(cashbillIssue);

            if (response.isSuccess()) {
                cashbillIssue.setCashbillStatus(CashbillStatus.ISSUED);
                cashbillIssue.setIssuedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                cashbillIssue.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                cashbillIssue.setMgtKey(response.getMgtKey());

                if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
                    cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
                } else {
                    cashbillIssue.setUpdateBy("비회원");
                }
                cashbillIssueRepository.save(cashbillIssue);
            } else {
                throw new ReceiptException(response.getResponseMessage());
            }
		}
	}

    @Override
    public void cancelCashbillByListData(ListParam listParam) {
        List<Long> ids = Arrays.stream(listParam.getId()).map(s -> Long.parseLong(s)).collect(Collectors.toList());

        ConfigPg configPg = configPgService.getConfigPg();
        String cashbillType = "";

        if (configPg != null) {
            cashbillType = configPg.getCashbillServiceType().getCode().toLowerCase();
        } else {
            cashbillType = environment.getProperty("cashbill.service");
        }

        // id에 해당하는 데이터 일괄 조회
        List<CashbillIssue> cashbills = cashbillIssueRepository.findAllById(ids);

        CashbillParam cashbillParam = new CashbillParam();
        cashbillParam.setWhere("orderCode");
        cashbillParam.setCashbillStatus(CashbillStatus.ISSUED);

        for (CashbillIssue cashbillIssue : cashbills) {
            log.debug("[POPBILL] 현금영수증 취소 : {}", cashbillIssue);

            CashbillResponse cancelResponse = new CashbillResponse();

            if (CashbillStatus.ISSUED != cashbillIssue.getCashbillStatus()) {
                continue;
            }

            if ("popbill".equals(cashbillType)) {
                CashbillResponse getInfoResponse = popbillService.getInfo(cashbillIssue.getMgtKey());

                if (CashbillStatusCode.ISSUED == getInfoResponse.getStatusCode()) {
                    cancelResponse = popbillService.cancelIssue(cashbillIssue);
                } else if (CashbillStatusCode.SENDING == getInfoResponse.getStatusCode()) {
                    cancelResponse = popbillService.cancelIssue(cashbillIssue);
                    String errorMessage = cancelResponse.getResponseMessage() + " (현금영수증 정보를 국세청으로 전송중입니다. 전송이 완료된 이후에 취소 가능합니다)";
                    cancelResponse.setResponseMessage(errorMessage);
                } else {
                    cancelResponse = popbillService.revokeRegistIssue(cashbillIssue, getInfoResponse.getOrgConfirmNum(), getInfoResponse.getOrgTradeDate());
                }
            }  else if ("inicis".equals(cashbillType)) {
                cashbillParam.setOrderCode(cashbillIssue.getCashbill().getOrderCode());
                cashbillParam.setMgtKey(cashbillIssue.getMgtKey());

                cancelResponse = inicisService.cashReceiptCancel(cashbillParam);
            }  else if ("nicepay".equals(cashbillType)) {
                Cashbill cashbill = cashbillIssue.getCashbill();

                cashbillParam.setMgtKey(cashbillIssue.getMgtKey());
                cashbillParam.setAmount(cashbillIssue.getAmount());
                cashbillParam.setTaxType(cashbillIssue.getTaxType());
                cashbillParam.setCashbillCode(cashbill.getCashbillCode());
                cashbillParam.setCashbillType(cashbill.getCashbillType());
                cashbillParam.setOrderCode(cashbillIssue.getCashbill().getOrderCode());

                cancelResponse = nicepayService.cashReceiptCancel(cashbillParam);
            }

            if (cancelResponse.isSuccess()) {
                cashbillIssue.setCashbillStatus(CashbillStatus.CANCELED);
                cashbillIssue.setCanceledDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                cashbillIssue.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
                    cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
                } else {
                    cashbillIssue.setUpdateBy("비회원");
                }
                cashbillIssueRepository.save(cashbillIssue);
            } else {
                throw new ReceiptException(cancelResponse.getResponseMessage());
            }
        }

    }

    @Override
    public CashbillResponse receiptIssue(CashbillIssue cashbillIssue) {

        CashbillResponse response = new CashbillResponse();

        ConfigPg configPg = configPgService.getConfigPg();
        String cashbillType = "";

        if (configPg != null) {
            cashbillType = configPg.getCashbillServiceType().getCode().toLowerCase();
        } else {
            cashbillType = environment.getProperty("cashbill.service");
        }

        Cashbill cashbill = cashbillIssue.getCashbill();


        OrderParam orderParam = new OrderParam();
        orderParam.setOrderCode(cashbill.getOrderCode());
        orderParam.setOrderSequence(0);

        String email = orderService.getEmailByOrderCode(orderParam);

        CashbillParam param = new CashbillParam();

        param.setCashbillCode(cashbill.getCashbillCode());
        param.setCashbillType(cashbill.getCashbillType());
        param.setCustomerName(cashbill.getCustomerName());
        param.setOrderCode(cashbill.getOrderCode());

        param.setCreatedDate(cashbillIssue.getCreatedDate());
        param.setAmount(cashbillIssue.getAmount());
        param.setCashbillStatus(cashbillIssue.getCashbillStatus());
        param.setItemName(cashbillIssue.getItemName());
        param.setTaxType(cashbillIssue.getTaxType());

        if ("popbill".equals(cashbillType)) {
            String mgtKey = "";

            // 기취소된 현금영수증 재발행 시 관리번호를 증가시켜서 중복으로 인한 오류를 막아준다.
            if (CashbillStatus.CANCELED == cashbillIssue.getCashbillStatus()) {
                mgtKey = cashbillIssue.getMgtKey();

                int count = Integer.parseInt(mgtKey.substring(mgtKey.length()-1, mgtKey.length())) + 2;

                mgtKey = mgtKey.substring(0, mgtKey.length()-1) + count;
            } else {
                if (CashbillIssueType.NORMAL == cashbillIssue.getCashbillIssueType()) {
                    mgtKey += "A-";
                } else {
                    mgtKey += "T-";
                }

                mgtKey += DateUtils.getToday(Const.DATE_FORMAT) + "-" + cashbill.getOrderCode() + "-";

                if (TaxType.CHARGE == cashbillIssue.getTaxType()) {
                    mgtKey += "1";
                } else {
                    mgtKey += "2";
                }
            }

            param.setMgtKey(mgtKey);

            response = popbillService.cashbillRegistIssue(param);
        } else if ("inicis".equals(cashbillType)) {
            param.setEmail(email);
            response = inicisService.cashReceiptIssued(param);

        } else if ("nicepay".equals(cashbillType)) {
            param.setEmail(email);
            response = nicepayService.cashReceiptIssued(param);
        }

        return response;
    }

    @Override
    public void insertCashbillIssue(CashbillIssue cashbillIssue) {
        cashbillIssueRepository.save(cashbillIssue);
    }

    @Override
    public CashbillResponse cancelCashbill(String orderCode) {

        ConfigPg configPg = configPgService.getConfigPg();
        String cashbillType = "";

        if (configPg != null) {
            cashbillType = configPg.getCashbillServiceType().getCode().toLowerCase();
        } else {
            cashbillType = environment.getProperty("cashbill.service");
        }

        CashbillResponse cancelResponse = new CashbillResponse();
        cancelResponse.setResponseCode("ignore");

        CashbillParam cashbillParam = new CashbillParam();
        cashbillParam.setWhere("orderCode");
        cashbillParam.setQuery(orderCode); 

        Iterable<CashbillIssue> cashbillIssues = cashbillIssueRepository.findAll(cashbillParam.getPredicate());

        for (CashbillIssue cashbillIssue : cashbillIssues) {
            if (CashbillStatus.ISSUED != cashbillIssue.getCashbillStatus()) {
                cancelResponse.setSuccess(true);
                continue;
            }

            log.debug("[" + cashbillType + "] 현금영수증 취소 : {}", cashbillIssue);

            cancelResponse = new CashbillResponse();

            if ("popbill".equals(cashbillType)) {
                CashbillResponse getInfoResponse = popbillService.getInfo(cashbillIssue.getMgtKey());

                if (CashbillStatusCode.ISSUED == getInfoResponse.getStatusCode()) {
                    cancelResponse = popbillService.cancelIssue(cashbillIssue);
                } else if (CashbillStatusCode.SENDING == getInfoResponse.getStatusCode()) {
                    cancelResponse = popbillService.cancelIssue(cashbillIssue);
                    String errorMessage = cancelResponse.getResponseMessage() + " (현금영수증 정보를 국세청으로 전송중입니다. 전송이 완료된 이후에 취소 가능합니다)";
                    cancelResponse.setResponseMessage(errorMessage);
                } else {
                    cancelResponse = popbillService.revokeRegistIssue(cashbillIssue, getInfoResponse.getOrgConfirmNum(), getInfoResponse.getOrgTradeDate());
                }
            } else if ("inicis".equals(cashbillType)) {
                cashbillParam.setOrderCode(orderCode);
                cashbillParam.setMgtKey(cashbillIssue.getMgtKey());

                cancelResponse = inicisService.cashReceiptCancel(cashbillParam);
            } else if ("nicepay".equals(cashbillType)) {
                Cashbill cashbill = cashbillIssue.getCashbill();

                cashbillParam.setMgtKey(cashbillIssue.getMgtKey());
                cashbillParam.setAmount(cashbillIssue.getAmount());
                cashbillParam.setTaxType(cashbillIssue.getTaxType());
                cashbillParam.setCashbillCode(cashbill.getCashbillCode());
                cashbillParam.setCashbillType(cashbill.getCashbillType());
                cashbillParam.setOrderCode(cashbillIssue.getCashbill().getOrderCode());

                cancelResponse = nicepayService.cashReceiptCancel(cashbillParam);
            }

            if (cancelResponse.isSuccess()) {
                cashbillIssue.setCashbillStatus(CashbillStatus.CANCELED);
                cashbillIssue.setCanceledDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                cashbillIssue.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
                    cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
                } else {
                    cashbillIssue.setUpdateBy("비회원");
                }
                cashbillIssueRepository.save(cashbillIssue);
            } else {
                throw new ReceiptException("ERROR MESSAGE : " + cancelResponse.getResponseMessage());
            }
        }
        return cancelResponse;
    }

    public void cashbillReIssue(OrderParam param) {
        CashbillParam cashbillParam = new CashbillParam();
        cashbillParam.setWhere("orderCode");
        cashbillParam.setQuery(param.getOrderCode());
        cashbillParam.setCashbillStatus(CashbillStatus.ISSUED);  // [SKC] 발급 완료인 건만 조회
        List<CashbillIssue> cashbillIssues = cashbillIssueRepository.findAll(cashbillParam.getPredicate());

        long taxAmount = 0;
        long taxFreeAmount = 0;

        List<OrderPayment> orderPaymentList = orderMapper.getOrderPaymentListByParam(param);
        List<OrderItem> orderItemList = orderMapper.getOrderItemListByParam(param);

        long remainingAmount = 0;

        for (OrderPayment orderPayment : orderPaymentList) {
            if (!"point".equals(orderPayment.getApprovalType()) && "1".equals(orderPayment.getPaymentType())) {
                remainingAmount += orderPayment.getRemainingAmount();
            }
        }

        for (OrderItem orderItem : orderItemList) {
            if (!"65".equals(orderItem.getOrderStatus()) && !"75".equals(orderItem.getOrderStatus())) {
                if ("2".equals(orderItem.getTaxType())) {
                    taxFreeAmount += orderItem.getQuantity() * orderItem.getSalePrice();
                }
            }
        }

        taxAmount = remainingAmount - taxFreeAmount;

        // [SKC] 발급 완료인 건이 있는 경우 ..
        if (!cashbillIssues.isEmpty()) {
            // 2018.11.09 손준의 - 현금영수증 취소
            CashbillResponse cancelResponse = this.cancelCashbill(param.getOrderCode());

            for (CashbillIssue cashbillIssue : cashbillIssues) {
                if (TaxType.CHARGE == cashbillIssue.getTaxType()) {
                    cashbillIssue.setAmount(taxAmount);
                } else {
                    cashbillIssue.setAmount(taxFreeAmount);
                }
            }

            // 2018.11.09 손준의 - 현금영수증 재발급
            if (cancelResponse.isSuccess() && (taxAmount + taxFreeAmount > 0)) {
                for (CashbillIssue cashbillIssue : cashbillIssues) {
                    if (TaxType.CHARGE == cashbillIssue.getTaxType() && taxAmount == 0) {
                        continue;
                    } else if (TaxType.FREE == cashbillIssue.getTaxType() && taxFreeAmount == 0) {
                        continue;
                    }

                    CashbillResponse issueResponse = this.receiptIssue(cashbillIssue);

                    if (issueResponse.isSuccess()) {
                        cashbillIssue.setMgtKey(issueResponse.getMgtKey());
                        cashbillIssue.setCashbillStatus(CashbillStatus.ISSUED);
                        cashbillIssue.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                        cashbillIssue.setIssuedDate(DateUtils.getToday(Const.DATETIME_FORMAT));

                        if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
                            cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
                        } else {
                            cashbillIssue.setUpdateBy("비회원");
                        }
                        cashbillIssueRepository.save(cashbillIssue);
                    } else {
                        throw new ReceiptException(issueResponse.getResponseCode() + " : " + issueResponse.getResponseMessage());
                    }
                }
            } else if (!"ignore".equals(cancelResponse.getResponseCode()) && (taxAmount + taxFreeAmount > 0)) {
                throw new ReceiptException(cancelResponse.getResponseCode() + " : " + cancelResponse.getResponseMessage());
            }
        }
    }
}
