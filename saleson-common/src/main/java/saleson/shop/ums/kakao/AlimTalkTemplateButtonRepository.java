package saleson.shop.ums.kakao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.kakao.AlimTalkTemplateButton;

public interface AlimTalkTemplateButtonRepository
        extends JpaRepository<AlimTalkTemplateButton, Long>, QuerydslPredicateExecutor<AlimTalkTemplateButton> {
}
