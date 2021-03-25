package saleson.shop.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.shop.config.domain.ConfigLog;
import saleson.common.enumeration.LogType;

import java.util.List;
import java.util.Optional;

public interface ConfigLogRepository extends JpaRepository<ConfigLog, Long>, QuerydslPredicateExecutor<ConfigLog> {

    public List<ConfigLog> findConfigLogsByLogType(LogType logType);

    public Optional<ConfigLog> findConfigLogByLogTypeAndKey(LogType logType, String key);
}
