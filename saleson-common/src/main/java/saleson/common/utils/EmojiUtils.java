package saleson.common.utils;

import com.onlinepowers.framework.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
public class EmojiUtils {
	private EmojiUtils() {
	}

	/**
     * 입력 값에 포함되어 있는 이모티콘 제거
     */
    public static String removeEmoticon(String value) {
        if (!StringUtils.isEmpty(value)) {
            Pattern emoji = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+");
            Matcher emojiMatcher = emoji.matcher(value);

            value = emojiMatcher.replaceAll("");
        }

        return value;
    }

}