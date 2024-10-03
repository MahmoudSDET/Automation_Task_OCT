
package com.web.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

//final -> We do not want any class to extend this class
public final class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";


    public static String getCurrentDate() {
        //Date date = new Date();
        LocalDate myDateObj = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = myDateObj.format(dateTimeFormatter);
        return formattedDate;
    }


    public static String getCurrentDateWithTime() {
        //Date date = new Date();
        LocalDateTime myDateObj = LocalDateTime.now();
        //       DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:ms");
        String formattedDate = myDateObj.toString().replace("-","").replace(":","");
        return formattedDate;
    }
    public static String addDaysToDate(String date, String days) {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);

        try {
            Date myDate = df.parse(date.trim());
            c.setTime(myDate);
            c.add(Calendar.DATE, Integer.parseInt(days));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String toDate = df.format(c.getTime());

        return toDate;
    }

    /**
     *
     * @param date
     * @param days
     * @return string
     */
    public static String subtractDaysFromDate(String date, String days) {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);

        try {
            Date myDate = df.parse(date.trim());
            c.setTime(myDate);
            c.add(Calendar.DATE, (Integer.parseInt(days) * -1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String toDate = df.format(c.getTime());

        return toDate;
    }


    public static int getCurrentDay() {
        //Date date = new Date();
        LocalDate currentDate = LocalDate.now();
        DayOfWeek currentDay = currentDate.getDayOfWeek();

        // Get the numerical value of the current day (1 = Monday, 7 = Sunday)
        int dayNumber = currentDay.getValue();
        return dayNumber;
    }

    public static int addingDaysToCurrentDate( String days) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(Integer.parseInt(days)).getDayOfMonth();
    }
}
