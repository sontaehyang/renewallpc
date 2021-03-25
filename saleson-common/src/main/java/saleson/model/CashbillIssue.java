package saleson.model;

import saleson.common.enumeration.CashbillIssueType;
import saleson.common.enumeration.CashbillStatus;
import saleson.common.enumeration.TaxType;

import javax.persistence.*;

@Entity
@Table(name = "OP_CASHBILL_ISSUE")
public class CashbillIssue {

    @Id
    @GeneratedValue
    private long id;

    private String mgtKey;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TaxType taxType;

    @Column(length = 200)
    private String itemName;            // 상품명

    private long amount;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CashbillIssueType cashbillIssueType;            // 발급구분 (NORMAL: 일반발급, TEMP: 자진발급)

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CashbillStatus cashbillStatus;                    // 발급상태 (PENDING, ISSUED, CANCELED)

    @Column(length = 14)
    private String createdDate;

    @Column(length = 14)
    private String issuedDate;

    @Column(length = 14)
    private String canceledDate;

    @Column(length = 14)
    private String updatedDate;            // 최종 수정일

    @Column(length = 100)
    private String updateBy;            // 최종 수정자 - 이름 (loginId)

    @Column(insertable = false, updatable = false)
    private long cashbillId;

    @ManyToOne
    @JoinColumn(name = "cashbillId")
    private Cashbill cashbill;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public CashbillIssueType getCashbillIssueType() {
        return cashbillIssueType;
    }

    public void setCashbillIssueType(CashbillIssueType cashbillIssueType) {
        this.cashbillIssueType = cashbillIssueType;
    }

    public CashbillStatus getCashbillStatus() {
        return cashbillStatus;
    }

    public void setCashbillStatus(CashbillStatus cashbillStatus) {
        this.cashbillStatus = cashbillStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getCanceledDate() {
        return canceledDate;
    }

    public void setCanceledDate(String canceledDate) {
        this.canceledDate = canceledDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public long getCashbillId() {
        return cashbillId;
    }

    public void setCashbillId(long cashbillId) {
        this.cashbillId = cashbillId;
    }

    public Cashbill getCashbill() {
        return cashbill;
    }

    public void setCashbill(Cashbill cashbill) {
        this.cashbill = cashbill;
    }

    public CashbillIssue getCopy() {
        CashbillIssue clone = new CashbillIssue();

        clone.setAmount(getAmount());
        clone.setCashbillStatus(getCashbillStatus());
        clone.setCreatedDate(getCreatedDate());
        clone.setItemName(getItemName());
        clone.setMgtKey(getMgtKey());
        clone.setTaxType(getTaxType());

        return clone;
    }


    @Override
    public String toString() {
        return "CashbillIssue{" +
                "id=" + id +
                ", mgtKey='" + mgtKey + '\'' +
                ", taxType=" + taxType +
                ", itemName='" + itemName + '\'' +
                ", amount=" + amount +
                ", cashbillIssueType=" + cashbillIssueType +
                ", cashbillStatus=" + cashbillStatus +
                ", createdDate='" + createdDate + '\'' +
                ", issuedDate='" + issuedDate + '\'' +
                ", canceledDate='" + canceledDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", cashbillId=" + cashbillId +
                '}';
    }

}
