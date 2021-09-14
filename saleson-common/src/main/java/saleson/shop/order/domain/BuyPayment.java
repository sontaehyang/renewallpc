package saleson.shop.order.domain;

import com.onlinepowers.framework.util.DateUtils;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.PointUtils;
import saleson.common.utils.ShopUtils;
import saleson.model.ConfigPg;
import saleson.shop.accountnumber.domain.AccountNumber;

import java.util.ArrayList;
import java.util.List;

public class BuyPayment {
	private String orderCode;
	private String approvalType;
	private String serviceType;
	private int amount;
	private int taxFreeAmount;
	
	private String bankVirtualNo;
	private String bankInName;
	private String bankExpirationDate;
	private List<AccountNumber> accountNumbers;
	private List<String> expirationDates;
	
	private String mid;
	private String key;
	private String escrowStatus;
	private String createdDate;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public BuyPayment() {}

	public void setData(String approvalType, ConfigPg configPg) {
		this.approvalType = approvalType;
		if ("bank".equals(approvalType)) {

			this.serviceType = "mall";
			
		} else if (PointUtils.isPointType(approvalType)) {
			
			this.serviceType = "mall";
			
		} else if ("card".equals(approvalType) || "vbank".equals(approvalType) || "realtimebank".equals(approvalType) || "escrow".equals(approvalType)
				|| "hp".equals(approvalType)) {

			if (configPg != null) {
				this.serviceType = configPg.getPgType().getCode().toLowerCase();
			} else {
				this.serviceType = SalesonProperty.getPgService();
			}
			
			if ("lgdacom".equals(this.serviceType)) {
				this.mid = SalesonProperty.getPgLgdacomMid();
				this.key = SalesonProperty.getPgLgdacomKey();

			} else if ("inicis".equals(this.serviceType)) {
				
				if ("escrow".equals(approvalType)) {
					this.mid = SalesonProperty.getPgInipayEscrowMid();
					this.key = SalesonProperty.getPgInipayEscrowKeypass();
				} else {
					if (getEscrowStatus() != null && !getEscrowStatus().equals("N")) {		//에스크로 사용시 에스크로 계약된 상점아이디로 변경(실제 상점아이디 발급후엔 없어도 되는 과정)
						this.mid = SalesonProperty.getPgInipayEscrowMid();
						this.key = SalesonProperty.getPgInipayEscrowKeypass();
					} else {
						if (ShopUtils.isMobilePage()) {
							this.mid = SalesonProperty.getPgInipayMobileMid();
							this.key = SalesonProperty.getPgInipayMobileKeypass();
						} else {
							this.mid = SalesonProperty.getPgInipayMid();
							this.key = SalesonProperty.getPgInipayKeypass();
						}
					}
				}
			} else if ("cj".equals(this.serviceType)) {
				this.mid = SalesonProperty.getPgCjMid();
				this.key = "";
			} else if ("kspay".equals(this.serviceType)) {
				this.mid = SalesonProperty.getPgKspayMid();
				this.key = "";
			} else if ("kcp".equals(this.serviceType)) {
				this.mid = SalesonProperty.getPgKcpGConfSiteCd();
				this.key = SalesonProperty.getPgKcpGConfSiteKey();
			} else if("easypay".equals(this.serviceType)) {
				this.mid = SalesonProperty.getPgEasypayGConfSiteCd();
				this.key = "";
			} else if("nicepay".equals(this.serviceType)) {

				if (configPg != null) {
					this.mid = configPg.getMid();
					this.key = configPg.getKey();
				}
			}
			
		} else if ("payco".equals(approvalType)) {
			
			this.serviceType = "payco";
			this.mid = SalesonProperty.getPaycoSellerCpId();
			this.key = SalesonProperty.getPaycoSellerKey();

		} else if ("kakaopay".equals(approvalType)) {
			
			this.serviceType = "kakaopay";
			this.mid = SalesonProperty.getKakaopayMid();
			this.key = SalesonProperty.getKakaopayEncodeKey();

        } else if ("naverpay".equals(approvalType)) {

            this.serviceType = "naverpay";
            this.mid = configPg.getNpayClientId();
            this.key = configPg.getNpayClientSecret();

        } else if ("rentalpay".equals(approvalType)) {

			this.serviceType = "bsrental";
			this.mid = SalesonProperty.getRentalpaySellerCode();
			this.key = SalesonProperty.getRentalpaySellerKey();
		}
	}
	
	public BuyPayment(String approvalType, ConfigPg configPg) {
		this(approvalType, null, configPg);
	}

	public BuyPayment(String approvalType, List<AccountNumber> accountNumbers, ConfigPg configPg) {
		
		if (accountNumbers != null) {
			this.accountNumbers = accountNumbers;
			
			int bankInDateLimit = 7;
			List<String> expirationDates = new ArrayList<>();
			for(int i = 0; i < bankInDateLimit; i++) {
				expirationDates.add(DateUtils.addDay(DateUtils.getToday(), i));
			}
			
			this.expirationDates = expirationDates;
		}
		
		this.setData(approvalType, configPg);
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTaxFreeAmount() {
		return taxFreeAmount;
	}

	public void setTaxFreeAmount(int taxFreeAmount) {
		this.taxFreeAmount = taxFreeAmount;
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

	public String getBankExpirationDate() {
		return bankExpirationDate;
	}

	public void setBankExpirationDate(String bankExpirationDate) {
		this.bankExpirationDate = bankExpirationDate;
	}

	public List<AccountNumber> getAccountNumbers() {
		return accountNumbers;
	}

	public void setAccountNumbers(List<AccountNumber> accountNumbers) {
		this.accountNumbers = accountNumbers;
	}

	public List<String> getExpirationDates() {
		return expirationDates;
	}

	public void setExpirationDates(List<String> expirationDates) {
		this.expirationDates = expirationDates;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEscrowStatus() {
		return escrowStatus;
	}

	public void setEscrowStatus(String escrowStatus) {
		this.escrowStatus = escrowStatus;
	}
	
}
