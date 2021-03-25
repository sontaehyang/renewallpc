package saleson.common.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class InstantUtils {
	private InstantUtils() {}

	public static String getDate(Instant instant) {
		if (instant == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
				.withLocale(Locale.getDefault())
				.withZone(ZoneId.systemDefault());
		return formatter.format(instant);
	}

	public static String getDateTime(Instant instant) {
		if (instant == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
				.withLocale(Locale.getDefault())
				.withZone(ZoneId.systemDefault());
		return formatter.format(instant);
	}
}
