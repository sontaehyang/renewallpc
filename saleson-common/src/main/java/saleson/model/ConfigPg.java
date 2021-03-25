package saleson.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.enumeration.pg.CashbillServiceType;
import saleson.common.enumeration.pg.PgType;
import saleson.common.hibenate.converter.BooleanYnConverter;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_CONFIG_PG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class ConfigPg extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private PgType pgType;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private CashbillServiceType cashbillServiceType;

    @Column(length = 100, nullable = false)
    private String mid;

    @Column(name="`KEY`", length = 100)
    private String key;

    @Column(length = 100)
    private String mobileMid;

    @Column(length = 100)
    private String mobileKey;

    @Column(length = 100)
    private String sign;

    @Column(length = 100)
    private String cancelPassword;

    @Column(length=1, nullable = false)
    @Convert(converter = BooleanYnConverter.class)
    private boolean useEscroow;

    @Column(length = 100)
    private String escroowMid;

    @Column(length = 100)
    private String escroowKey;

    @Column(length = 100)
    private String mobileEscroowMid;

    @Column(length = 100)
    private String mobileEscroowKey;

    @Column(length=1, nullable = false)
    @Convert(converter = BooleanYnConverter.class)
    private boolean useVbackRefundService;

    @Column(length=1, nullable = false)
    @Convert(converter = BooleanYnConverter.class)
    private boolean useAutoCashReceipt;

    @Column(length=1, nullable = false)
    @Convert(converter = BooleanYnConverter.class)
    private boolean realtimePartcancelFlag;

    @Column(length = 100)
    private String instalment;

    @Column(length = 100)
    private String currency;

    @Column(length=1, nullable = false)
    @Convert(converter = BooleanYnConverter.class)
    private boolean useNpayOrder;

    @Column(length = 100)
    private String npayMid;

    @Column(length = 100)
    private String npayKey;

    @Column(length = 100)
    private String npayLogKey;

    @Column(length = 100)
    private String npayButtonKey;

    @Column(length=1, nullable = false)
    @Convert(converter = BooleanYnConverter.class)
    private boolean useNpayPayment;

    @Column(length = 100)
    private String npayClientId;

    @Column(length = 100)
    private String npayClientSecret;

    @Column(length = 100)
    private String npayPartnerId;

    public void setUpdateData(ConfigPg configPg) {

        setPgType(configPg.getPgType());
        setCashbillServiceType(configPg.getCashbillServiceType());
        setMid(configPg.getMid());
        setKey(configPg.getKey());
        setMobileMid(configPg.getMobileMid());
        setMobileKey(configPg.getMobileKey());
        setSign(configPg.getSign());
        setCancelPassword(configPg.getCancelPassword());
        setUseVbackRefundService(configPg.isUseVbackRefundService());
        setUseAutoCashReceipt(configPg.isUseAutoCashReceipt());
        setRealtimePartcancelFlag(configPg.isRealtimePartcancelFlag());
        setInstalment(configPg.getInstalment());
        setCurrency(configPg.getCurrency());

        setUseEscroow(configPg.isUseEscroow());
        setEscroowMid(configPg.getEscroowMid());
        setEscroowKey(configPg.getEscroowKey());
        setMobileEscroowMid(configPg.getMobileEscroowMid());
        setMobileEscroowKey(configPg.getMobileEscroowKey());

        setUseNpayOrder(configPg.isUseNpayOrder());
        setNpayMid(configPg.getNpayMid());
        setNpayKey(configPg.getNpayKey());
        setNpayLogKey(configPg.getNpayLogKey());
        setNpayButtonKey(configPg.getNpayButtonKey());

        setUseNpayPayment(configPg.isUseNpayPayment());
        setNpayClientId(configPg.getNpayClientId());
        setNpayClientSecret(configPg.getNpayClientSecret());
        setNpayPartnerId(configPg.getNpayPartnerId());
    }
}

