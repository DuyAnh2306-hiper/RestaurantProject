package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;

public class XDate {

    static SimpleDateFormat formater = new SimpleDateFormat();
    
    /*
    Exam:
        String s = "01-09-1971";
        Date date = XDate.toDate(s, "dd-MM-yyyy");
        System.out.println(date);
    */
    
    public static Date stringToDate(String date, String pattern) {
        try {
            formater.applyPattern(pattern);
            return formater.parse(date);
            
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static Date datetoDate(Date date, String pattern) {
        try {
            formater.applyPattern(pattern);
            return formater.parse(formater.format(date));
            
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static Time toTime(String date, String pattern) {
        return new Time(stringToDate(date, pattern).getTime());
    }
    
    
    
    /*
    Exam:
        Date now = new Date();
        String s = XDate.dateParseString(now, "yyyy-MM-dd");
        System.out.println(s);
    */
    public static String dateParseString(Date date, String pattern) {
        formater.applyPattern(pattern);
        return formater.format(date);
    }
    
    public static String timeParseString(Time time, String pattern) {
        formater.applyPattern(pattern);
        return formater.format(time);
    }
    
    /*
    Exam:
        Date now = new Date();
        Date after = XDate.addDays(now, 5);
        System.out.println(after);
    */
    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
}
