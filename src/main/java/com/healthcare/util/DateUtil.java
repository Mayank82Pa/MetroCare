package com.healthcare.util;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Utility class for parsing and formatting SQL Dates and Times.
 */
public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DISPLAY_DATE_FORMAT = "dd MMM yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DISPLAY_TIME_FORMAT = "hh:mm a";

    public static Date parseSqlDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            java.util.Date parsed = sdf.parse(dateStr.trim());
            return new Date(parsed.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Time parseSqlTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return null;
        }
        try {
            String sanitized = timeStr.trim();
            if (sanitized.length() == 5) {
                sanitized += ":00";
            }
            return Time.valueOf(sanitized);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static String formatDisplayDate(Date date) {
        if (date == null) return "N/A";
        SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
        return sdf.format(date);
    }

    public static String formatDisplayTime(Time time) {
        if (time == null) return "N/A";
        SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_TIME_FORMAT);
        return sdf.format(time);
    }
}
