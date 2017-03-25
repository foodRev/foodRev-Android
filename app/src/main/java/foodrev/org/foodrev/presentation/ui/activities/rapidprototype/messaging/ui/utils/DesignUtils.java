package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils;

import android.content.Context;
import android.text.format.DateUtils;

public class DesignUtils {

    /**
     * Use Android DateUtils to format a timestamp for messages
     * @param context
     * @param timestamp
     * @return
     */
    public static String formatTime(Context context, long timestamp) {
        return  DateUtils.getRelativeDateTimeString(context, timestamp,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.DAY_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }
}
