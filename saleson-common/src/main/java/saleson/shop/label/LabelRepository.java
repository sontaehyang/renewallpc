package saleson.shop.label;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.Label;
import saleson.shop.label.query.LabelQuery;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long>, QuerydslPredicateExecutor<Label> {

    @Modifying
    @Query(value = LabelQuery.DELETE_BY_IDS, nativeQuery = true)
    void deleteByIds(@Param("ids") List<Long> ids);

}
