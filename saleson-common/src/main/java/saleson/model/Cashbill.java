package saleson.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import saleson.common.enumeration.CashbillType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="OP_CASHBILL", indexes = {
		@Index(name = "IDX_ORDER_CODE", columnList = "orderCode")
})
public class Cashbill {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50)
    private String orderCode;

	@Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CashbillType cashbillType;

    @NotEmpty(message = "고객명을 입력해 주세요.")
	@Column(length = 50)
    private String customerName;        // 고객명

	@Column(length = 20)
    private String cashbillCode;

    @Column(length = 20)
    private String pgService;

	@Column(length = 14)
    private String createdDate;

	@Column(length = 50)
	private String createdBy; 			// 신정자 - 이름 (loginId)


    @Transient
    private String cashbillPhone1;

    @Transient
    private String cashbillPhone2;

    @Transient
    private String cashbillPhone3;

    @Transient
    private String businessNumber1;

    @Transient
    private String businessNumber2;

    @Transient
    private String businessNumber3;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCashbillPhone1() {
        return cashbillPhone1;
    }

    public void setCashbillPhone1(String cashbillPhone1) {
        this.cashbillPhone1 = cashbillPhone1;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCashbillPhone2() {
        return cashbillPhone2;
    }

    public void setCashbillPhone2(String cashbillPhone2) {
        this.cashbillPhone2 = cashbillPhone2;
    }

    public String getCashbillPhone3() {
        return cashbillPhone3;
    }

    public void setCashbillPhone3(String cashbillPhone3) {
        this.cashbillPhone3 = cashbillPhone3;
    }

    public String getBusinessNumber1() {
        return businessNumber1;
    }

    public void setBusinessNumber1(String businessNumber1) {
        this.businessNumber1 = businessNumber1;
    }

    public String getBusinessNumber2() {
        return businessNumber2;
    }

    public void setBusinessNumber2(String businessNumber2) {
        this.businessNumber2 = businessNumber2;
    }

    public String getBusinessNumber3() {
        return businessNumber3;
    }

    public void setBusinessNumber3(String businessNumber3) {
        this.businessNumber3 = businessNumber3;
    }

    public String getPgService() {
        return pgService;
    }

    public void setPgService(String pgService) {
        this.pgService = pgService;
    }

    @JsonIgnore
    public Cashbill getCopy() {
        Cashbill clone = new Cashbill();

        clone.setCashbillCode(getCashbillCode());
        clone.setCashbillType(getCashbillType());
        clone.setCreatedDate(getCreatedDate());
        clone.setCustomerName(getCustomerName());
        clone.setOrderCode(getOrderCode());

        return clone;
    }
}
