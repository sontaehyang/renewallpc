package saleson.shop.orderCancelFail;

import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.model.ConfigPg;
import saleson.model.OrderCancelFail;

@Service("orderCancelFailService")
public class OrderCancelFailServiceImpl implements OrderCancelFailService {
    private static final Logger log = LoggerFactory.getLogger(OrderCancelFailServiceImpl.class);

    @Autowired
    private OrderCancelFailRepository orderCancelFailRepository;

    @Override
    public Page<OrderCancelFail> getAllOrderCancelFail(Predicate predicate, Pageable pageable) {
        return orderCancelFailRepository.findAll(predicate, pageable);
    }

    @Override
    public Page<OrderCancelFail> findAll(Pageable pageable) {
        return orderCancelFailRepository.findAll(pageable);
    }

    @Override
    public void save(OrderCancelFail orderCancelFail) {

        orderCancelFailRepository.save(orderCancelFail);
    }
}
