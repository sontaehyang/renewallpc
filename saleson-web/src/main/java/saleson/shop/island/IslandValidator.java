package saleson.shop.island;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class IslandValidator {

    public void validate(IslandDto islandDto, Errors errors) {
        if (islandDto.getIslandType().getTitle() == null) {
            errors.rejectValue("title", "wrongValue", "지역 구분을 반드시 입력해주세요.");
        }
    }

}
