package app.sleep.detect.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-D");

    @TypeConverter
    public static Date fromTimeStamp(String value) {
        try {
            return value == null ? null : formatter.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static String dateToTimeStamp(Date date) {
        return date == null ? null : formatter.format(date);
    }
}
