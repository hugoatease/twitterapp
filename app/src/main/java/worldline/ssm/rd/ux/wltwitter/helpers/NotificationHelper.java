package worldline.ssm.rd.ux.wltwitter.helpers;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.WLTwitterLoginActivity;

public class NotificationHelper {

    public static void displayNotification(int nbTweets) {
        final Context context = WLTwitterApplication.getContext();


        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(String.format(getContentText(context, nbTweets), nbTweets))
                .setAutoCancel(true);

        final Intent newIntent = new Intent(context, WLTwitterLoginActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(WLTwitterLoginActivity.class);
        stackBuilder.addNextIntent(newIntent);
        final PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(42, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        final Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_VIBRATE;
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(42, notification);
    }

    private static String getContentText(Context context, int nbTweets){
        if(nbTweets == 1){
            return context.getString(R.string.new_tweet_notification);
        } else {
            return context.getString(R.string.new_tweets_notification);
        }
    }
}
