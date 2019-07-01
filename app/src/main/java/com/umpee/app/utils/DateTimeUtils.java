package com.umpee.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by King on 2016.4.22..
 */
public class DateTimeUtils {

    public static String FMT_FULL = "MM/dd/yyyy HH:mm:ss";
    public static String FMT_NAME = "yyyy-MM-dd-HH-mm-ss";
    public static String FMT_TIME = "hh:mm a";
    public static String FMT_DATE = "yyyy-MM-dd";
    public static String FMT_REVTIME = "MM.dd.yyyy";
    public static String FMT_SIMPLE = "MM/dd/yyyy";

    public static String convert(String nowFmt, String newFmt, String datetime) {
        SimpleDateFormat srcDf = new SimpleDateFormat(nowFmt);
        try {
            Date date = srcDf.parse(datetime);
            SimpleDateFormat destDf = new SimpleDateFormat(newFmt);
            String mydate = destDf.format(date);
            mydate = mydate.replace("p.m.", "pm");
            mydate = mydate.replace("a.m.", "am");
            return mydate;
        } catch (ParseException e) {
            e.printStackTrace();
            return datetime;
        }
    }

    public static String getDateTimeName() {
        SimpleDateFormat sdf = new SimpleDateFormat(FMT_NAME);
        return sdf.format(new Date());
    }

    public static String getUTCDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(FMT_FULL);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(FMT_FULL);
        return sdf.format(new Date());
    }

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getLocalTimeFromUTC(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(time);
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            if (now.getTime() - date.getTime() < 1000 * 60) {
                return newSdf.format(now);
            } else {
                newSdf.setTimeZone(calendar.getTimeZone());
                return newSdf.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getTimeString(int sec) {
        int min = sec / 60;
        if (min == 0) {
            return sec + " SEC";
        } else {
            return min + " MIN  " + (sec % 60) + " SEC";
        }
    }
}
