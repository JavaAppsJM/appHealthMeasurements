package be.hvwebsites.healthmeasurements.typeconverters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;

public class DBTypeConverters {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate stringToDate(String stringDate){
        if (stringDate == null) {
            return null;
        } else {
            return LocalDate.parse(stringDate);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static String dateToString(LocalDate date){
        if (date == null){
            return null;
        } else {
            // Compose date to default date pattern yyyy-mm-dd
            String stringDate = date.getYear() + "-" +
                    String.valueOf(date.getMonthValue()) + "-" +
                    date.getDayOfWeek();
            return stringDate;
        }
    }
}
