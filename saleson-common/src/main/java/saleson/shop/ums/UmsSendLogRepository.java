package saleson.shop.ums;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.UmsSendLog;

public interface UmsSendLogRepository  extends JpaRepository<UmsSendLog, Long>, QuerydslPredicateExecutor<UmsSendLog> {
}
