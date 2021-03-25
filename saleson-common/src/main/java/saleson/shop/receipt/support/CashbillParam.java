package saleson.shop.receipt.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import saleson.common.enumeration.CashbillStatus;
import saleson.common.enumeration.CashbillType;
import saleson.common.enumeration.TaxType;
import saleson.common.web.Param;
import saleson.model.QCashbill;
import saleson.model.QCashbillIssue;

@SuppressWarnings("serial")
public class CashbillParam extends Param {

    private String orderCode;

    private String email;

    private CashbillType cashbillType;

    private String customerName;        // 고객명

    private String cashbillCode;

    private String mgtKey;

    private TaxType taxType;             // 과세형태 (1: 과세 , 2: 면세)

    private String itemName;            // 상품명

    private long amount;

    private String cashbillIssueDate;

    private CashbillStatus cashbillStatus;

	private String searchStartDate;

	private String searchEndStartDate;

    private String createdDate;


    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

	public CashbillType getCashbillType() {
		return cashbillType;
	}

	public void setCashbillType(CashbillType cashbillType) {
		this.cashbillType = cashbillType;
	}

	public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCashbillCode() {
        return cashbillCode;
    }

    public void setCashbillCode(String cashbillCode) {
        this.cashbillCode = cashbillCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCashbillIssueDate() {
        return cashbillIssueDate;
    }

    public void setCashbillIssueDate(String cashbillIssueDate) {
        this.cashbillIssueDate = cashbillIssueDate;
    }

    public String getMgtKey() {
        return mgtKey;
    }

    public void setMgtKey(String mgtKey) {
        this.mgtKey = mgtKey;
    }

	public TaxType getTaxType() {
		return taxType;
	}

	public void setTaxType(TaxType taxType) {
		this.taxType = taxType;
	}

	public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

	public CashbillStatus getCashbillStatus() {
		return cashbillStatus;
	}

	public void setCashbillStatus(CashbillStatus cashbillStatus) {
		this.cashbillStatus = cashbillStatus;
	}

	public String getSearchStartDate() {
        return searchStartDate;
    }

    public void setSearchStartDate(String searchStartDate) {
        this.searchStartDate = searchStartDate;
    }

    public String getSearchEndStartDate() {
        return searchEndStartDate;
    }

    public void setSearchEndStartDate(String searchEndStartDate) {
        this.searchEndStartDate = searchEndStartDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }



	public Predicate getPredicate() {
		QCashbill cashbill = QCashbill.cashbill;
		QCashbillIssue cashbillIssue = QCashbillIssue.cashbillIssue;

		BooleanBuilder builder = new BooleanBuilder();
		if (!StringUtils.isEmpty(getQuery())) {
			if ("orderCode".equals(getWhere())) {
				builder.and(cashbill.orderCode.like("%" + getQuery() + "%"));

			} else if ("name".equals(getWhere())) {
				builder.and(cashbill.customerName.like("%" + getQuery() + "%"));

			} else if ("cashbillCode".equals(getWhere())) {
				builder.and(cashbill.cashbillCode.like("%" + getQuery() + "%"));

			}
		}

		if (getCashbillStatus() != null) {
			builder.and(cashbillIssue.cashbillStatus.eq(getCashbillStatus()));
		}

		return builder;
	}
}
