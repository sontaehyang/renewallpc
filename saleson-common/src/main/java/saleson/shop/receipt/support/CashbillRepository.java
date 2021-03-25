package saleson.shop.receipt.support;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import saleson.model.Cashbill;

import java.util.List;

@Repository
public interface CashbillRepository extends JpaRepository<Cashbill, Long>, QuerydslPredicateExecutor<Cashbill> {

    List<Cashbill> findAll(Specification<Cashbill> cashbill);

	List<Cashbill> findAllByOrderCode(String orderCode);

	Cashbill findByOrderCode(String orderCode);

}
