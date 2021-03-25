package saleson.shop.ums.kakao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.kakao.AlimTalkTemplate;

public interface AlimTalkTemplateRepository
        extends JpaRepository<AlimTalkTemplate, Long>, QuerydslPredicateExecutor<AlimTalkTemplate> {
}
