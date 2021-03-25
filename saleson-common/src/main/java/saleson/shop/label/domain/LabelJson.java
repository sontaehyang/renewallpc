package saleson.shop.label.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saleson.common.enumeration.LabelType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabelJson {
    private Long id;
    private String imageSrc;
    private String description;
    private LabelType labelType;
}
