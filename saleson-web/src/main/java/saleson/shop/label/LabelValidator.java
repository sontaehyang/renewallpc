package saleson.shop.label;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import saleson.shop.label.support.LabelDto;

@Component
public class LabelValidator {

    public void validate(LabelDto labelDto, Errors errors) {
        if (StringUtils.isEmpty(labelDto.getDescription())) {
            errors.rejectValue("description", "wrongValue", "라벨 설명을 입력해주세요.");
        }

        if (StringUtils.isEmpty(labelDto.getImage()) &&
                (labelDto.getImageFile() == null || labelDto.getImageFile().isEmpty() || labelDto.getImageFile().getSize() <= 0)) {
            errors.rejectValue("image", "wrongValue", "라벨 이미지를 첨부해주세요.");
        }
    }
}
