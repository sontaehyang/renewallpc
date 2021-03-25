package saleson.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.enumeration.pg.CashbillServiceType;
import saleson.common.enumeration.pg.PgType;
import saleson.common.hibenate.converter.BooleanYnConverter;
import saleson.model.base.BaseEntity;
import saleson.shop.order.domain.OrderPgData;

import javax.persistence.*;

@Entity
@Table(name="OP_ORDER_CANCEL_FAIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class OrderCancelFail {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    private String orderCode;

    @Column(length = 50, nullable = false)
    private String pgServiceType;

    @Column(length = 100, nullable = false)
    private String pgKey;

    private int cancelAmount;

    private int taxAmount;

    private int taxFreeAmount;

    @Column(length = 50, nullable = false)
    private String approvalType;

    @Column(length = 255, nullable = false)
    private String cancelReason;

    @Column(length = 1, nullable = false)
    private String cancelRequester;

    @Column(length = 14, nullable = false)
    private String payDate;

    public void setUpdateData(OrderPgData orderPgData) {

        setOrderCode(orderPgData.getOrderCode());
        setPgServiceType(orderPgData.getPgServiceType());
        setPgKey(orderPgData.getPgKey());
        setApprovalType(orderPgData.getApprovalType());
        setCancelAmount(orderPgData.getCancelAmount());
        setTaxAmount(orderPgData.getCancelAmount());
        setTaxFreeAmount(0);
        setCancelReason("주문취소");
        setPayDate(orderPgData.getPayDate());
    }

}

