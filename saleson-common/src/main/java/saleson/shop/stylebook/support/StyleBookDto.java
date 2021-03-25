package saleson.shop.stylebook.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import saleson.common.web.Param;
import saleson.model.stylebook.QStyleBook;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StyleBookDto extends Param {

    private Long id;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QStyleBook styleBook = QStyleBook.styleBook;

        if (!StringUtils.isEmpty(getWhere()) && !StringUtils.isEmpty(getQuery())) {
            switch (getWhere()) {
                case "TITLE":
                    styleBook.title.like("%"+getQuery()+"%");
                    break;
                case "CONTENT":
                    styleBook.content.like("%"+getQuery()+"%");
                    break;
                default:
            }
        }

        return builder;
    }

}
