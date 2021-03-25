package saleson.shop.campaign.messageLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.campaign.KakaoLogTemp;

public interface KakaoLogTempRepository extends JpaRepository<KakaoLogTemp, Long>, QuerydslPredicateExecutor<KakaoLogTemp> {
}
