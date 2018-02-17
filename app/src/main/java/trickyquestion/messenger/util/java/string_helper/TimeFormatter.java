package trickyquestion.messenger.util.java.string_helper;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeFormatter {
    public static String getCurrentTime(@NonNull  final String pattern) {
        final DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String convertTime(@NonNull final String date, @NonNull String pattern, @NonNull String toPattern) {
        final DateFormat df1 = new SimpleDateFormat(pattern, Locale.getDefault());
        final DateFormat df2 = new SimpleDateFormat(toPattern, Locale.getDefault());try {
            final Date d = df1.parse(date);
            return df2.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
