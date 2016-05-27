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

    public static String getDuration(DateTime time){
        int year = Years.yearsBetween(time, DateTime.now()).getYears();
        int month = Months.monthsBetween(time,DateTime.now()).getMonths();
        int day = Days.daysBetween(time,DateTime.now()).getDays();
        int hour = Hours.hoursBetween(time,DateTime.now()).getHours();
        int minute = Minutes.minutesBetween(time,DateTime.now()).getMinutes();

        if(year>1){
            return year+" years ago";
        }
        if(month>1){
            return month+" months ago";
        }
        if(day>1){
            return day+" days ago";
        }
        if(hour>1){
            return hour+" hours ago";
        }
        if(minute>1){
            return minute+" minutes ago";
        }
        return "just now";
    }
}
