package trickyquestion.messenger.util.formatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeFormatter {
    public static String getCurrentTime(final String pattern) {
        final DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
