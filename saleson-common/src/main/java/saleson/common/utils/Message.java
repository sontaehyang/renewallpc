package saleson.common.utils;


import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.util.FlashMapUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class Message {
	private Message() {
	}

	public static void set(String message) {
		RequestContextUtils.setMessage(message);
		FlashMapUtils.setMessage(message);
	}

	public static void set(Errors errors) {
		String message = errors.getFieldError().getDefaultMessage() + " (" + errors.getFieldError().getField() + ")";
		set(message);
	}
}
