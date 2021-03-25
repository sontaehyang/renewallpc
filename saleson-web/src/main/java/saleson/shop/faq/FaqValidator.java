package saleson.shop.faq;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class FaqValidator {

	public void validate(FaqDto faqDto, Errors errors) {
		if (faqDto.getTitle() == null) {
			errors.rejectValue("title", "wrongValue", "제목을 반드시 입력해 주세요.");
		}
	}
}
