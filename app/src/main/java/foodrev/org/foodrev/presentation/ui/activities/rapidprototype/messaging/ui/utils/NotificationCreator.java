package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import foodrev.org.foodrev.R;

public class NotificationCreator {
    public static void createNotification(Activity activity, String channelName, String message) {
        android.support.v4.app.NotificationCompat.Builder builder =
                new NotificationCompat.Builder(activity)
                        .setSmallIcon(getNotificationIcon())
                        .setContentTitle("New message in #"+channelName)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentText(message);

        //Vibration
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        //LED
        builder.setLights(Color.BLUE, 3000, 3000);
        builder.setAutoCancel(true);

        Intent activityIntent = new Intent(activity, activity.getClass());

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(activity.getClass());
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(activityIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_VIBRATE;

        // mId allows you to update the notification later on.
        notificationManager.notify(1, notification);
    }

    private static int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_stat_ic_launchertrans : R.mipmap.ic_launcher;
    }
}
