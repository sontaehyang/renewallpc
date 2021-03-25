package saleson.shop.island;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import saleson.common.enumeration.IslandType;
import saleson.common.web.Param;
import saleson.model.QIsland;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IslandDto extends Param {

    @NotEmpty
    private String zipcode;

    @NotEmpty
    private String address;

    @NotNull
    private IslandType islandType;

    private int extraCharge;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QIsland island = QIsland.island;

        if (getQuery() != null && !getQuery().isEmpty()) {
            if ("ZIPCODE".equals(getWhere())) {
                builder.and(island.zipcode.contains(this.getQuery()));
            } else if ("ADDRESS".equals(getWhere())) {
                builder.and(island.address.contains(this.getQuery()));
            }
        }

        if (getIslandType() != null) {
            builder.and(island.islandType.eq(getIslandType()));
        }

        return builder;
    }
}
