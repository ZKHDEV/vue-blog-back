package top.kmacro.blog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    public static final String YMD = "yyyy-MM-dd";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期字符串转Date对象
     * @param pattern
     * @param dateString
     * @return
     */
    public static Date stringToDate(String pattern, String dateString){
        SimpleDateFormat ft = new SimpleDateFormat (pattern);
        Date date = null;
        try {
            date = ft.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Date对象转日期字符串
     * @param pattern
     * @param date
     * @return
     */
    public static String dateToString(String pattern, Date date){
        SimpleDateFormat ft = new SimpleDateFormat (pattern);
        return ft.format(date);
    }
}
