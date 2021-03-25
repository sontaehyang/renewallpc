package saleson.common.utils;

import saleson.common.Const;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateUtils {

	private LocalDateUtils() {
	}

	public static DateTimeFormatter getDateTimeFormatter(String pattern) {
		return DateTimeFormatter.ofPattern(pattern)
				.withLocale(Locale.getDefault())
				.withZone(ZoneId.systemDefault());
	}

	public static String getDate(LocalDateTime date) {
		return localDateTimeToString(date, "yyyy-MM-dd");
	}

	public static String getDateTime(LocalDateTime date) {
		return localDateTimeToString(date, "yyyy-MM-dd HH:mm:ss");
	}



	public static String localDateToString(LocalDate date, String pattern) {
		if (date == null) {
			return "";
		}
		return getDateTimeFormatter(pattern).format(date);
	}

	public static String localDateToString(LocalDate date) {
		if (date == null) {
			return "";
		}
		return localDateToString(date, Const.DATE_FORMAT);
	}


	public static String localDateTimeToString(LocalDateTime date, String pattern) {
		if (date == null) {
			return "";
		}
		return getDateTimeFormatter(pattern).format(date);
	}

	public static String localDateTimeToString(LocalDateTime date) {
		if (date == null) {
			return "";
		}
		return getDateTimeFormatter(Const.DATETIME_FORMAT).format(date);
	}


	public static LocalDate getLocalDate(String date, String pattern) {
		DateTimeFormatter formatter = getDateTimeFormatter(pattern);

		return LocalDate.parse(date, formatter);
	}


	public static LocalDate getLocalDate(String date) {
		return getLocalDate(date, Const.DATE_FORMAT);
	}

	public static String processMessage(String message) {
		return "SKC" + message;
	}

	public static LocalDateTime getLocalDateTime(String date, String pattern) {
		DateTimeFormatter formatter = getDateTimeFormatter(pattern);
		return LocalDateTime.parse(date, formatter);
	}

	public static LocalDateTime getLocalDateTime(String date) {
		return getLocalDateTime(date, Const.DATETIME_FORMAT);
	}

	public static String getDateTime (Instant instant) {
		return LocalDateUtils.getDateTime(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
	}
}
