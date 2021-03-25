package saleson.shop.categoriesfilter.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import saleson.common.web.Param;
import saleson.model.QCategoriesFilter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CategoriesFilterDto extends Param {

    private int categoryId;

    public Predicate getPredicate() {
        QCategoriesFilter filter = QCategoriesFilter.categoriesFilter;
        BooleanBuilder builder = new BooleanBuilder();

        if (getQuery() != null && !getQuery().isEmpty()) {

        }

        if (getCategoryId() > 0) {
            builder.and(filter.categoryId.eq(getCategoryId()));
        }

        return builder;
    }
}
