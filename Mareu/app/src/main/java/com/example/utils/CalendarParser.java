package com.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarParser {
    public static Calendar getCalendar(String forTime) {
        try {
            Date time = new SimpleDateFormat("HH:mm:ss").parse(forTime + ":00");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
