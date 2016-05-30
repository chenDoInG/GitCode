package com.chendoing.gitcode.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Years;

/**
 * Created by chenDoInG on 16/5/27.
 */
public class TimeUtil {

    public static String getDuration(DateTime time) {
        int day = Days.daysBetween(time, DateTime.now()).getDays();
        int hour = Hours.hoursBetween(time, DateTime.now()).getHours();
        int minute = Minutes.minutesBetween(time, DateTime.now()).getMinutes();

        if (day > 27) {
            return "on " + time.toString("d MMM");
        }
        if (day >= 1) {
            return day + " days ago";
        }
        if (hour >= 1) {
            return hour + " hours ago";
        }
        if (minute >= 1) {
            return minute + " minutes ago";
        }
        return "just now";
    }
}
