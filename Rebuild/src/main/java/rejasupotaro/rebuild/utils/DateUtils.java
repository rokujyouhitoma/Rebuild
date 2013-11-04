package rejasupotaro.rebuild.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {

    public static final String TAG = DateUtils.class.getSimpleName();

    private DateUtils() {}

    public static String formatCurrentTime(int currentTime) {
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(new Date(currentTime));
    }

    /**
     * FIXME: This is terrible code... I should fix it soon.
     * @param source pubDate
     * @return output instance of String
     */
    public static String formatPubDate(String source) {
        String output = "";

        try {
            DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss zzz", Locale.US);
            Date date = formatter.parse(source);
            output = monthToName(date.getMonth() + 1) + " " + (date.getDate()) + "\n" + (1900 + date.getYear());
        } catch (ParseException e) {
            Log.e(TAG, e.toString(), e);
        }

        return output;
    }

    public static String monthToName(int month) {
        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "";
        }
    }
}
