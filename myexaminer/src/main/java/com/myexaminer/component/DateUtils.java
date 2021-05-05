package com.myexaminer.component;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtils {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parseStringToDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    public static String parseDateToString(Date date){
        return date.toString();
    }

}
