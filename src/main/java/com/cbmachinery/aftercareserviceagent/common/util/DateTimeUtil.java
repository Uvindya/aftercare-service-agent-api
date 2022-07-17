package com.cbmachinery.aftercareserviceagent.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

	private static String LONG_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	private static DateTimeFormatter LONG_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(LONG_DATE_FORMAT);

	public static String fomatToLongDateTime(LocalDateTime time) {
		return time.format(LONG_DATE_TIME_FORMATTER);
	}

}
