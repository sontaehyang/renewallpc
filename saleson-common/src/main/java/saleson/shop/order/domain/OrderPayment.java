package saleson.shop.order.domain;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.enumeration.CashbillType;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.ShopUtils;
import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import saleson.model.Cashbill;

public class OrderPayment {
	private static final Logger log = LoggerFactory.getLogger(OrderPayment.class);
	public static final String PAYMENT_KEY_DIVISION_STRING = "///";

	public String getPaymentKey() {
		return getOrderCode() + PAYMENT_KEY_DIVISION_STRING + getOrderSequence() + PAYMENT_KEY_DIVISION_STRING + getPaymentSequence();
	}
	
	public OrderPayment() {}
	public OrderPayment(Order order, String approvalType) {
		setOrderCode(order.getOrderCode());
		setOrderSequence(order.getOrderSequence());
		
		setApprovalType(approvalType);
		
		setDeviceType("WEB");
		if (ShopUtils.isMobilePage()) {
			setDeviceType("MOBILE");
		}
	}
	
	public OrderPayment(OrderPayment orderPayment, String approvalType) {
		setOrderCode(orderPayment.getOrderCode());
		setOrderSequence(orderPayment.getOrderSequence());
		setPaymentSequence(orderPayment.getPaymentSequence());
		
		setApprovalType(approvalType);
		
		setDeviceType("WEB");
		if (ShopUtils.isMobilePage()) {
			setDeviceType("MOBILE");
		}
	}
	
	private String orderCode;
	private int orderSequence;
	private int paymentSequence;
	private int orderPgDataId;
	private String paymentType;
	private boolean isPaymentVerificationCancel;
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	private String approvalType;
	private String deviceType;
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	private String bankVirtualNo;
	private String bankInName;
	private String bankDate;
	
	private int amount;
	private int taxFreeAmount;
	
	private int cancelAmount;
	private int remainingAmount;
	
	private String payDate;
	private String nowPaymentFlag;
	
	private String loginId;
	private String buyerName;
	private long userId;
	private String orderDate;
	
	private int totalPaymentAmount;
	private int totalCancelAmount;
	
	private int[] changeNewAmount;
	private int[] changeCancelAmount;
	
	private String changeNewApprovalType;
	private String[] changeCancelApprovalType;
	
	private String returnBankName;
	private String returnBankVirtualNo;
	private String returnBankInName;
	private String escrowStatus;


	// [SKC 2019.02.15] 결제 정보 추가
	private String refundFlag = "N";		// 환불 시 결제 수단별 부분 취소가 아닌 은행입급으로 직접 환불 처리한 경우
	private String paymentSummary;			// 결제 정보 요약

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}

	public String getPaymentSummary() {
		return paymentSummary;
	}

	public void setPaymentSummary(String paymentSummary) {
		this.paymentSummary = paymentSummary;
	}

	private List<CodeInfo> returnBankList;
	
	public List<CodeInfo> getReturnBankList() {
		return returnBankList;
	}

	public void setReturnBankList(List<CodeInfo> returnBankList) {
		this.returnBankList = returnBankList;
	}

	public String getReturnBankName() {
		return returnBankName;
	}

	public void setReturnBankName(String returnBankName) {
		this.returnBankName = returnBankName;
	}

	public String getReturnBankVirtualNo() {
		return returnBankVirtualNo;
	}

	public void setReturnBankVirtualNo(String returnBankVirtualNo) {
		this.returnBankVirtualNo = returnBankVirtualNo;
	}

	public String getReturnBankInName() {
		return returnBankInName;
	}

	public void setReturnBankInName(String returnBankInName) {
		this.returnBankInName = returnBankInName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getCancelAmount() {
		return cancelAmount;
	}

	public void setCancelAmount(int cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	public int getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(int remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	
	public String getNowPaymentFlag() {
		return nowPaymentFlag;
	}

	public void setNowPaymentFlag(String nowPaymentFlag) {
		this.nowPaymentFlag = nowPaymentFlag;
	}
	
	private String createdDate;
	
	// 입금 지연일
	private int delayDay;
	
	// PG 결제 데이터
	private OrderPgData orderPgData;

	private int changeOrderStatusCount;
	
	private String orderStatus;
	
	public int getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}

	public int getPaymentSequence() {
		return paymentSequence;
	}

	public void setPaymentSequence(int paymentSequence) {
		this.paymentSequence = paymentSequence;
	}

	public int getTaxFreeAmount() {
		return taxFreeAmount;
	}

	public void setTaxFreeAmount(int taxFreeAmount) {
		this.taxFreeAmount = taxFreeAmount;
	}

	public String getApprovalTypeLabel() {
		if (StringUtils.isEmpty(this.approvalType)) {
			return "";
		}
		
		CodeInfo codeInfo = CodeUtils.getCodeInfo("ORDER_PAY_TYPE", this.approvalType);
		if (codeInfo == null) {
			return "-";
		}
		
		return codeInfo.getLabel();
	}
	
	public int getChangeOrderStatusCount() {
		return changeOrderStatusCount;
	}
	public void setChangeOrderStatusCount(int changeOrderStatusCount) {
		this.changeOrderStatusCount = changeOrderStatusCount;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public int getDelayDay() {
		return delayDay;
	}
	public void setDelayDay(int delayDay) {
		this.delayDay = delayDay;
	}
	
	public String getBankVirtualNo() {
		return bankVirtualNo;
	}
	public void setBankVirtualNo(String bankVirtualNo) {
		this.bankVirtualNo = bankVirtualNo;
	}
	public String getBankInName() {
		return bankInName;
	}
	public void setBankInName(String bankInName) {
		this.bankInName = bankInName;
	}
	public String getBankDate() {
		return bankDate;
	}
	public void setBankDate(String bankDate) {
		this.bankDate = bankDate;
	}
	public OrderPgData getOrderPgData() {
		return orderPgData;
	}
	public void setOrderPgData(OrderPgData orderPgData) {
		this.orderPgData = orderPgData;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderPgDataId() {
		return orderPgDataId;
	}
	public void setOrderPgDataId(int orderPgDataId) {
		this.orderPgDataId = orderPgDataId;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getTotalPaymentAmount() {
		return totalPaymentAmount;
	}
	public void setTotalPaymentAmount(int totalPaymentAmount) {
		this.totalPaymentAmount = totalPaymentAmount;
	}
	public int getTotalCancelAmount() {
		return totalCancelAmount;
	}
	public void setTotalCancelAmount(int totalCancelAmount) {
		this.totalCancelAmount = totalCancelAmount;
	}
	public int[] getChangeNewAmount() {
		return CommonUtils.copy(changeNewAmount);
	}
	public void setChangeNewAmount(int[] changeNewAmount) {
		this.changeNewAmount = CommonUtils.copy(changeNewAmount);
	}
	public int[] getChangeCancelAmount() {
		return CommonUtils.copy(changeCancelAmount);
	}
	public void setChangeCancelAmount(int[] changeCancelAmount) {
		this.changeCancelAmount = CommonUtils.copy(changeCancelAmount);
	}
	public String getChangeNewApprovalType() {
		return changeNewApprovalType;
	}
	public void setChangeNewApprovalType(String changeNewApprovalType) {
		this.changeNewApprovalType = changeNewApprovalType;
	}
	public String[] getChangeCancelApprovalType() {
		return CommonUtils.copy(changeCancelApprovalType);
	}
	public void setChangeCancelApprovalType(String[] changeCancelApprovalType) {
		this.changeCancelApprovalType = CommonUtils.copy(changeCancelApprovalType);
	}
	public String getViewBankVirtualNo() {
		if (StringUtils.isNotEmpty(bankVirtualNo)) {
			if (bankVirtualNo.length() > 13) {
				return bankVirtualNo.substring(0, 3) + "-" + bankVirtualNo.substring(3, 9) + "-" + bankVirtualNo.substring(9, 11) + "-" + bankVirtualNo.substring(11);
			} else {
				return bankVirtualNo;
			}
		}
		
		return "-";
	}
	
	public String getPayInfo() {
		
		if (!"1".equals(getPaymentType()) && !"bank".equals(getApprovalType())) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		String pgProcInfo = null;
		if (ValidationUtils.isNull(orderPgData) == false){
			pgProcInfo = orderPgData.getPgProcInfo();
		}
			
		if (StringUtils.isEmpty(pgProcInfo) == false) {	
			
			if ("card".equals(approvalType) || "realtimebank".equals(approvalType) || "vbank".equals(approvalType)) {
				
				HashMap<String, Object> map = ShopUtils.textToHashMapForReadToassign(pgProcInfo, null);
				if ("lgdacom".equals(orderPgData.getPgServiceType())) {
					if (ValidationUtils.isNull(map.get("LGD_FINANCENAME")) == false) {
						sb.append((String) map.get("LGD_FINANCENAME"));
					}
						
					if ("card".equals(approvalType)) {	
						if (ValidationUtils.isNull(map.get("LGD_CARDNUM")) == false) {
							sb.append(" " + map.get("LGD_CARDNUM"));
						}
						
						if (ValidationUtils.isNull(map.get("LGD_PAYDATE")) == false) {
							sb.append("(" + DateUtils.date((String) map.get("LGD_PAYDATE")) + ")");
						}
					} else if ("vbank".equals(approvalType)) {
						// 가상계좌
					}
					
				} else if ("inicis".equals(orderPgData.getPgServiceType())) {
						
					if ("MOBILE".equals(getDeviceType())) {
						
						if ("card".equals(approvalType)) {	
							if (ValidationUtils.isNull(map.get("P_FN_NM")) == false) {
								
								try {
									sb.append("[" + map.get("P_FN_NM") + "]");
								} catch(Exception e) {
									log.error("ERROR: {}", e.getMessage(), e);
								}
								
							}
							
							if (ValidationUtils.isNull(map.get("P_CARD_NUM")) == false) {
								sb.append(" " + map.get("P_CARD_NUM"));
							}
							
							try {
								if (ValidationUtils.isNull(map.get("P_AUTH_DT")) == false) {
									sb.append(" (" + DateUtils.date((String) map.get("P_AUTH_DT")) + ")");
								}
							} catch(Exception e) {
								log.error("ERROR: {}", e.getMessage(), e);
							}
							
						} else if ("vbank".equals(approvalType)) {
							
							if (ValidationUtils.isNull(map.get("P_VACT_BANK_CODE")) == false) {
								
								try {
									sb.append("[" + ShopUtils.getBankName("INICIS", (String) map.get("P_VACT_BANK_CODE")) + "]");
								} catch(Exception e) {
									log.error("ERROR: {}", e.getMessage(), e);
								}
								
							}
							
							if (ValidationUtils.isNull(map.get("P_VACT_NUM")) == false) {
								sb.append(" " + map.get("P_VACT_NUM"));
							}
							
							try {
								if (ValidationUtils.isNull(map.get("P_UNAME")) == false) {
									sb.append(" (" + DateUtils.date((String) map.get("P_UNAME")) + ")");
								}
							} catch(Exception e) {
								log.error("ERROR: {}", e.getMessage(), e);
							}
						}
						
					} else {
						if ("card".equals(approvalType)) {	

							if (ValidationUtils.isNull(map.get("CARD_Code")) == false) {
								
								try {
									sb.append("[" + CodeUtils.getCodeInfo("INICIS_CARD_LIST", (String) map.get("CARD_Code")).getLabel() + "]");
								} catch(Exception e) {}
								
							}
							
							if (ValidationUtils.isNull(map.get("CARD_Num")) == false) {
								sb.append(" " + map.get("CARD_Num"));
							}
							
							try {
								if (ValidationUtils.isNull(map.get("applDate")) == false) {
									sb.append(" (" + DateUtils.date((String) map.get("applDate")) + ")");
								}
							} catch(Exception e) {
								log.error("ERROR: {}", e.getMessage(), e);
							}
							
						} else if ("vbank".equals(approvalType)) {
							if (ValidationUtils.isNull(map.get("VACT_BankCode")) == false) {
								
								try {
									sb.append("[" + ShopUtils.getBankName("INICIS", (String) map.get("VACT_BankCode")) + "]");
								} catch(Exception e) {
									log.error("ERROR: {}", e.getMessage(), e);
								}
								
							}
							
							if (ValidationUtils.isNull(map.get("VACT_Num")) == false) {
								sb.append(" " + map.get("VACT_Num"));
							}
							
							try {
								if (ValidationUtils.isNull(map.get("VACT_InputName")) == false) {
									sb.append(" (" + DateUtils.date((String) map.get("VACT_InputName")) + ")");
								}
							} catch(Exception e) {
								log.error("ERROR: {}", e.getMessage(), e);
							}
						}
					}
					
				} else if("kcp".equals(orderPgData.getPgServiceType())) {
					if ("card".equals(approvalType)) {	

						if (ValidationUtils.isNull(map.get("card_name")) == false) {							
							sb.append("[" + (String) map.get("card_name") + "]");
						}

						if (ValidationUtils.isNull(map.get("quota")) == false) {
							int halbu = Integer.parseInt((String) map.get("quota"));
							String result = halbu > 1 ? halbu+"개월 할부" : "일시불";
							sb.append(" (" + result + ")");
						}
					} else if ("vbank".equals(approvalType) || "realtimebank".equals(approvalType)) {						
						
						if (ValidationUtils.isNull(map.get("bank_name")) == false) {							
							sb.append("[" + map.get("bank_name") + "]");					
						}
						
						if (ValidationUtils.isNull(map.get("account")) == false) {
							sb.append(" " + map.get("account"));
						}
						
						try {
							if (ValidationUtils.isNull(map.get("depositor")) == false) {
								sb.append(" (" + DateUtils.date((String) map.get("depositor")) + ")");
							}
						} catch(Exception e) {
							log.error("ERROR: {}", e.getMessage(), e);
						}
					}
				} else if("easypay".equals(orderPgData.getPgServiceType())) {
					if ("card".equals(approvalType)) {	

						if (ValidationUtils.isNull(map.get("card_name")) == false) {							
							sb.append("[" + (String) map.get("card_name") + "]");
						}

						if (ValidationUtils.isNull(map.get("quota")) == false) {
							int halbu = Integer.parseInt((String) map.get("quota"));
							String result = halbu > 1 ? halbu+"개월 할부" : "일시불";
							sb.append(" (" + result + ")");
						}
					} else if ("vbank".equals(approvalType) || "realtimebank".equals(approvalType)) {						
						if (ValidationUtils.isNull(map.get("bank_nm")) == false) {							
							sb.append("[" + map.get("bank_nm") + "]");
						}
						
						if (ValidationUtils.isNull(map.get("account_no")) == false) {
							sb.append(" " + map.get("account_no"));
						}
						
//						if (ValidationUtils.isNull(map.get("deposit_nm")) == false) {
//							sb.append(" (" + DateUtils.date((String) map.get("deposit_nm")) + ")");
//						}
					}
				} else if("nicepay".equals(orderPgData.getPgServiceType())) {
					if ("card".equals(approvalType)) {

						if (ValidationUtils.isNull(map.get("CardName")) == false) {
							sb.append("[" + (String) map.get("CardName") + "]");
						}

						if (ValidationUtils.isNull(map.get("CardQuota")) == false) {
							int halbu = Integer.parseInt((String) map.get("CardQuota"));
							String result = halbu > 1 ? halbu+"개월 할부" : "일시불";
							sb.append(" (" + result + ")");
						}
					} else if ("vbank".equals(approvalType) || "realtimebank".equals(approvalType)) {
						if (ValidationUtils.isNull(map.get("VbankBankName")) == false) {
							sb.append("[" + map.get("VbankBankName") + "]");
						}

						if (ValidationUtils.isNull(map.get("VbankNum")) == false) {
							sb.append(" " + map.get("VbankNum"));
						}

						if (ValidationUtils.isNull(map.get("VbankExpDate")) == false) {
							if (getPayDate() == null) {
								sb.append(" [" + DateUtils.date(map.get("VbankExpDate").toString()) + "까지 입금 필요]");
							}
						}

						if (ValidationUtils.isNull(map.get("BankName")) == false) {
							sb.append("[" + map.get("BankName") + "]");
						}
                    } else if("naver".equals(orderPgData.getPgServiceType())) {
                        if ("card".equals(approvalType)) {

                            if (ValidationUtils.isNull(map.get("CardName")) == false) {
                                sb.append("[" + (String) map.get("CardName") + "]");
                            }

                            if (ValidationUtils.isNull(map.get("CardQuota")) == false) {
                                int halbu = Integer.parseInt((String) map.get("CardQuota"));
                                String result = halbu > 1 ? halbu + "개월 할부" : "일시불";
                                sb.append(" (" + result + ")");
                            }
                        } else if ("bank".equals(approvalType)) {
                            if (ValidationUtils.isNull(map.get("BankName")) == false) {
                                sb.append("[" + map.get("BankName") + "]");
                            }
                        }
                    }
				}
			}
		} else {
			
			// 은행입금
			if ("bank".equals(getApprovalType())) {
				if (StringUtils.isEmpty(getBankVirtualNo()) == false) {
					String payInfo = getBankVirtualNo();
					if (getPayDate() == null) {
						payInfo += " [" + DateUtils.date(getBankDate()) + "까지 입금 필요]";
					}
					sb.append(payInfo);
				}
			}
			
		}
		
		return sb.toString();
	}
	
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isPaymentVerificationCancel() {
		return isPaymentVerificationCancel;
	}

	public void setPaymentVerificationCancel(boolean isPaymentVerificationCancel) {
		this.isPaymentVerificationCancel = isPaymentVerificationCancel;
	}

	public String getEscrowStatus() {
		return escrowStatus;
	}

	public void setEscrowStatus(String escrowStatus) {
		this.escrowStatus = escrowStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	private Cashbill cashbill;

    public Cashbill getCashbill() {
        return cashbill;
    }

    public void setCashbill(Cashbill cashbill) {
        this.cashbill = cashbill;
    }
}
