package saleson.shop.label.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.enumeration.LabelType;
import saleson.common.web.Param;
import saleson.model.QLabel;

import javax.validation.constraints.NotEmpty;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class LabelDto extends Param {

    @NotEmpty
    private String description;

    private LabelType labelType;

    private String image;

    private MultipartFile imageFile;

    public Predicate getPredicate() {
        QLabel label = QLabel.label;
        BooleanBuilder builder = new BooleanBuilder();

        if (getQuery() != null && !getQuery().isEmpty()) {
            if ("DESCRIPTION".equalsIgnoreCase(getWhere())) {
                builder.and(label.description.contains(getQuery()));
            }
        }

        if (getLabelType() != null) {
            Stream.of(LabelType.values())
                .filter(l -> l == getLabelType())
                .forEach(labelType ->
                    builder.and(label.labelType.eq(labelType))
                );
        }

        return builder;
    }
}
