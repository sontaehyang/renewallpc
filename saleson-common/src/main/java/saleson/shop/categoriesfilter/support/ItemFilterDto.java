package saleson.shop.categoriesfilter.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import saleson.common.web.Param;
import saleson.model.QItemFilter;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ItemFilterDto extends Param {

    private int itemId;
    private List<Integer> itemIds;
    private List<Long> keepFilterGroupIds;

    public Predicate getPredicate() {

        BooleanBuilder builder = new BooleanBuilder();
        QItemFilter itemFilter = QItemFilter.itemFilter;

        if (getItemId() > 0) {
            builder.and(itemFilter.itemId.eq(getItemId()));
        }

        List<Integer> itemIds = getItemIds();
        if (itemIds != null && !itemIds.isEmpty()) {
            builder.and(itemFilter.itemId.in(itemIds));
        }

        List<Long> keepFilterGroupIds = getKeepFilterGroupIds();
        if (keepFilterGroupIds != null && !keepFilterGroupIds.isEmpty()) {
            builder.and(itemFilter.filterGroupId.notIn(keepFilterGroupIds));
        }

        return builder;
    }

}
