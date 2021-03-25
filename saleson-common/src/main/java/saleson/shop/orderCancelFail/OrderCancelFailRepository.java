package saleson.shop.orderCancelFail;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.OrderCancelFail;

import java.util.List;

public interface OrderCancelFailRepository extends JpaRepository<OrderCancelFail, Long>, QuerydslPredicateExecutor<OrderCancelFail> {

    List<OrderCancelFail> findAll(Specification<OrderCancelFail> orderCancelFail);
}
