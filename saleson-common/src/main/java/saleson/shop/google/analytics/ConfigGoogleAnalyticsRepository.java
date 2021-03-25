package saleson.shop.google.analytics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.google.analytics.ConfigGoogleAnalytics;

public interface ConfigGoogleAnalyticsRepository extends JpaRepository<ConfigGoogleAnalytics, Long>, QuerydslPredicateExecutor<ConfigGoogleAnalytics> {
}
