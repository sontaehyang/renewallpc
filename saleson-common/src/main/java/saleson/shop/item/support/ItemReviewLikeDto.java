package saleson.shop.item.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import saleson.common.web.Param;
import saleson.model.review.QItemReviewLike;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ItemReviewLikeDto extends Param {

    private int itemReviewId;
    private long userId;
    private String ip;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QItemReviewLike itemReviewLike = QItemReviewLike.itemReviewLike;

        builder.and(itemReviewLike.itemReviewId.eq(getItemReviewId()));

        if (!StringUtils.isEmpty(getIp())) {
            builder.and(itemReviewLike.ip.eq(getIp()));
        }

        if (getUserId() > 0) {
            builder.and(itemReviewLike.userId.eq(getUserId()));
        }

        return builder;
    }
}
