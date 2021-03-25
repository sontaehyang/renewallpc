package saleson.shop.orderCancelFail.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import saleson.model.QOrderCancelFail;
import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
@Getter @Setter @NoArgsConstructor @ToString
public class OrderCancelFailParam extends SearchParam {

    private String orderCode;

    private String pgServiceType;

    private String approvalType;

    private String pgKey;

    private int cancelAmount;

    private int taxAmount;

    private int taxFreeAmount;

    private String cancelReason;

    private String cancelRequester;

    private String searchStartDate;

    private String searchEndDate;

    private String payDate;

    public Predicate getPredicate() {

        BooleanBuilder builder = new BooleanBuilder();
        QOrderCancelFail orderCancelFail = QOrderCancelFail.orderCancelFail;

        if ("orderCode".equals(getWhere())) {
            builder.and(orderCancelFail.orderCode.like("%" + getQuery() + "%"));
        }

        if ("pgKey".equals(getWhere())) {
            builder.and(orderCancelFail.pgKey.like("%" + getQuery() + "%"));
        }

        if (StringUtils.isNotEmpty(getPgServiceType())) {
            builder.and(orderCancelFail.pgServiceType.eq(getPgServiceType()));
        }

        if (StringUtils.isNotEmpty(getApprovalType())) {
            builder.and(orderCancelFail.approvalType.eq(getApprovalType()));
        }

        if (StringUtils.isNotEmpty(getSearchStartDate())) {
            builder.and(orderCancelFail.payDate.goe(getSearchStartDate()+"000000"));
        }

        if (StringUtils.isNotEmpty(getSearchEndDate())) {
            builder.and(orderCancelFail.payDate.loe(getSearchEndDate()+"235959"));
        }

        return builder;
    }
}
