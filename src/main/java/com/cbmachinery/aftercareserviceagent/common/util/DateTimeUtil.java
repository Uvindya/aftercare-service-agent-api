package com.cbmachinery.aftercareserviceagent.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

	private static String LONG_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static String DATE_FORMAT = "yyyy-MM-dd";
	private static DateTimeFormatter LONG_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(LONG_DATE_TIME_FORMAT);
	private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

	public static String fomatToLongDateTime(LocalDateTime time) {
		return time.format(LONG_DATE_TIME_FORMATTER);
	}

	public static LocalDateTime fomatToLongDateTime(String datetime) {
		return LocalDateTime.parse(datetime, LONG_DATE_TIME_FORMATTER);
	}

	public static LocalDate fomatToLongDate(String date) {
		return LocalDate.parse(date, DATE_FORMATTER);
	}

}
