package saleson.shop.reviewfilter.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import saleson.common.web.Param;
import saleson.model.review.QReviewFilter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ReviewFilterDto extends Param {

    private int categoryId;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QReviewFilter filter = QReviewFilter.reviewFilter;


        if (getQuery() != null && !getQuery().isEmpty()) {

        }

        if (getCategoryId() > 0) {
            builder.and(filter.categoryId.eq(getCategoryId()));
        }

        return builder;
    }
}
