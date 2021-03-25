package saleson.shop.receipt.support;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import saleson.model.CashbillIssue;

import java.util.List;

@Repository
public interface CashbillIssueRepository extends JpaRepository<CashbillIssue, Long>, QuerydslPredicateExecutor<CashbillIssue> {

	@Override
	@EntityGraph(attributePaths = "cashbill")
    Page<CashbillIssue> findAll(Predicate predicate, Pageable pageable);

//	@EntityGraph(attributePaths = "cashbill")
//	List<CashbillIssue> findAll(Iterable<Long> iterable);



    @Override
    @EntityGraph(attributePaths = "cashbill")
    List<CashbillIssue> findAll(Predicate predicate);
}
