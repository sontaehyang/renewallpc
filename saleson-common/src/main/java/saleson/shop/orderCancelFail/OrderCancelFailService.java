package saleson.shop.orderCancelFail;

import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.Cashbill;
import saleson.model.CashbillIssue;
import saleson.model.ConfigPg;
import saleson.model.OrderCancelFail;
import saleson.shop.order.support.OrderParam;
import saleson.shop.receipt.support.CashbillResponse;

import java.util.List;

public interface OrderCancelFailService {

    public Page<OrderCancelFail> getAllOrderCancelFail(Predicate predicate, Pageable pageable);

    public Page<OrderCancelFail> findAll(Pageable pageable);

    public void save(OrderCancelFail orderCancelFail);

}
