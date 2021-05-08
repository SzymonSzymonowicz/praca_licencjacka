package com.myexaminer.component;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parseStringToDate(String date){
        return LocalDateTime.parse(date, formatter);
    }

    public static String parseDateToString(LocalDateTime date){
        return date.toString();
    }

}
