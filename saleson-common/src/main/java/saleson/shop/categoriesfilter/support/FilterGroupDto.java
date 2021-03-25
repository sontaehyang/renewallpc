package saleson.shop.categoriesfilter.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.sun.istack.NotNull;
import lombok.*;
import saleson.common.enumeration.FilterType;
import saleson.common.web.Param;
import saleson.model.FilterCode;
import saleson.model.QFilterGroup;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class FilterGroupDto extends Param {

    @NotEmpty
    private String label;

    @NotEmpty
    private String description;

    @NotNull
    private List<FilterCode> codeList;

    private FilterType filterType;

    public Predicate getPredicate() {
        QFilterGroup filter = QFilterGroup.filterGroup;
        BooleanBuilder builder = new BooleanBuilder();

        if (getQuery() != null && !getQuery().isEmpty()) {
            if ("GROUP_LABEL".equalsIgnoreCase(getWhere())) {
                builder.and(filter.label.contains(getQuery()));
            } else if ("GROUP_DESCRIPTION".equalsIgnoreCase(getWhere())) {
                builder.and(filter.description.contains(getQuery()));
            }
        }

        if (getFilterType() != null) {
            Stream.of(FilterType.values())
                    .filter(f -> f == getFilterType())
                    .forEach(filterType ->
                        builder.and(filter.filterType.eq(filterType))
                    );
        }

        return builder;
    }
}
