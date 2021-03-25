package saleson.shop.receipt.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import saleson.model.Cashbill;
import saleson.model.QCashbill;

public class CashbillPredicate {
    public static Predicate search (Cashbill param) {
        QCashbill cashbill = QCashbill.cashbill;

        BooleanBuilder builder = new BooleanBuilder();
        if (param.getOrderCode() != null) {
            builder.and(cashbill.orderCode.eq(param.getOrderCode()));
        }

        return builder;
    }
}
